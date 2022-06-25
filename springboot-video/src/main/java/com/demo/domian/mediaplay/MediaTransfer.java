package com.demo.domian.mediaplay;

import com.concise.component.core.exception.BizException;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <b>支持转复用或转码线程<b> <b> 什么情况下会转复用?</b>
 * <p>
 * 视频源的音视频编码必须是浏览器和flv规范两者同时支持的编码，比如H264/AAC，
 * </p>
 * <p>
 * 否则将进行转码。
 * </p>
 * <p>
 * 转封装暂不支持hevc、vvc、vp8、vp9、g711、g771a等编码
 * </p>
 * <b> 转码累积延迟补偿暂未实现。</b> *
 * 由于转流过程中的拉流解码和编码是个线性串联链，多线程转码也不能解决该问题，后面可能需要采用主动跳包方式来解决
 * @author shenguangyang
 * @date 2021-10-30 9:37
 */
public class MediaTransfer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MediaTransfer.class);
    /**
     * 运行状态
     */
    private final AtomicBoolean running = new AtomicBoolean(true);

    /**
     * 拉流器
     */
    private FFmpegFrameGrabber grabber;
    /**
     * 推流录制器
     */
    private FFmpegFrameRecorder recorder;
    /**
     * true:转复用,false:转码
     * 默认转码
     */
    private volatile boolean transferFlag = false;

    private int width;
    private int height;
    private double frameRate = 25.0;

    private final MediaInfo mediaInfo;

    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public MediaTransfer(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public MediaTransfer(MediaInfo mediaInfo, boolean transferFlag) {
        this.transferFlag = transferFlag;
        this.mediaInfo = mediaInfo;
    }

    public boolean isRunning() {
        return this.running.get();
    }

    public void stop() {
        this.running.set(false);
    }

    @Override
    public void run() {
        try {
            from().to().go();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stop();
            close();
        }
    }

    /**
     * 创建拉流器
     *
     * @return
     */
    protected MediaTransfer from() {
        // 拉流器
        grabber = new FFmpegFrameGrabber(mediaInfo.getPullUrl());
        // 超时时间(15秒)
        grabber.setOption("stimeout", mediaInfo.getNetTimeout());
        grabber.setOption("threads", "3");
        // 设置缓存大小，提高画质、减少卡顿花屏
        grabber.setOption("buffer_size", "1024000");

        // 读写超时，适用于所有协议的通用读写超时
        grabber.setOption("rw_timeout", mediaInfo.getReadOrWriteTimeout());
        // 探测视频流信息，为空默认5000000微秒
        grabber.setOption("probesize", mediaInfo.getReadOrWriteTimeout());
        // 解析视频流信息，为空默认5000000微秒
        grabber.setOption("analyzeduration", mediaInfo.getReadOrWriteTimeout());
        grabber.setFormat("mp4");
        // 如果为rtsp流，增加配置
        if ("rtsp".equals(mediaInfo.getPullUrl().substring(0, 4))) {
            // 设置打开协议tcp / udp
            grabber.setOption("rtsp_transport", "tcp");
            // 首选TCP进行RTP传输
            grabber.setOption("rtsp_flags", "prefer_tcp");
        } else if ("rtmp".equals(mediaInfo.getPullUrl().substring(0, 4))) {
            // rtmp拉流缓冲区，默认3000毫秒
            grabber.setOption("rtmp_buffer", "1000");
            // 默认rtmp流为直播模式，不允许seek
            grabber.setOption("rtmp_live", "live");

        } else if ("desktop".equals(mediaInfo.getPullUrl())) {
            // 支持本地屏幕采集，可以用于监控屏幕、局域网和wifi投屏等
            grabber.setFormat("gdigrab");
            grabber.setOption("draw_mouse", "1");// 绘制鼠标
            grabber.setNumBuffers(0);
            grabber.setOption("fflags", "nobuffer");
            grabber.setOption("framerate", "25");
            grabber.setFrameRate(25);
        }
        try {
            grabber.startUnsafe();
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
            frameRate = grabber.getFrameRate();
            int bitrate = grabber.getVideoBitrate();
            log.info("\r\n{}\r\n启动拉流器成功", mediaInfo.getPullUrl());
            return this;
        } catch (FrameGrabber.Exception e) {
            throw new BizException("\r\n{}\r\n启动拉流器失败，网络超时或视频源不可用", mediaInfo.getPullUrl());
        }
    }

    /**
     * 创建转码推流录制器
     */
    protected MediaTransfer to() throws FFmpegFrameRecorder.Exception, FrameGrabber.Exception {
        recorder = new FFmpegFrameRecorder(mediaInfo.getPushUrl(), width, height);
        recorder.setFormat("flv");
        if (!transferFlag) {
            // 转码
            recorder.setInterleaved(false);
            // 降低编码延时
            recorder.setVideoOption("tune", "zerolatency");
            // 提升编码速度
            recorder.setVideoOption("preset", "ultrafast");
            // 视频质量参数(详见 https://trac.ffmpeg.org/wiki/Encode/H.264)
            recorder.setVideoOption("crf", "28");
            recorder.setVideoOption("threads", "4");
            // 设置帧率
            recorder.setFrameRate(frameRate);
            // 设置gop,与帧率相同，相当于间隔1秒chan's一个关键帧
            recorder.setGopSize((int) frameRate);
            recorder.setImageWidth(width);
            recorder.setImageHeight(height);
            // recorder.setVideoBitrate(bitrate % 50000);
			// recorder.setVideoCodecName("libx264");
            //javacv 1.5.5无法使用libx264名称，请使用下面方法
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            // recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
			// recorder.setAudioCodecName("aac");
            /*
              启用RDOQ算法，优化视频质量 1：在视频码率和视频质量之间取得平衡 2：最大程度优化视频质量（会降低编码速度和提高码率）
             */
            recorder.setTrellis(1);
            // 设置延迟
            recorder.setMaxDelay(0);
            try {
                recorder.startUnsafe();
                return this;
            } catch (Exception e1) {
                log.error("启动转码录制器失败", e1);
                throw new BizException("启动转码录制器失败");
            }
        } else {
            // 转复用
            // 不让recorder关联关闭outputStream
            recorder.setCloseOutputStream(false);
            try {
                recorder.start(grabber.getFormatContext());
                grabber.flush();
                return this;
            } catch (FrameRecorder.Exception e) {
                log.error("\r\n{}\r\n启动转复用录制器失败", mediaInfo.getPushUrl());
                // 如果转复用失败，则自动切换到转码模式
                transferFlag = false;
                if (recorder != null) {
                    try {
                        recorder.stop();
                    } catch (FrameRecorder.Exception e1) {
                        log.error("停止转码失败: {}", e1.getMessage());
                        throw e1;
                    }
                }
                try {
                    to();
                    log.warn("\r\n{}\r\n切换到转码模式", mediaInfo.getPushUrl());
                } catch (FFmpegFrameRecorder.Exception ex) {
                    log.error("\r\n{}\r\n切换转码模式失败", mediaInfo.getPushUrl());
                    throw new BizException("切换转码模式失败");
                }
            } catch (FrameGrabber.Exception e) {
                throw e;
            }
        }
        return this;
    }

//    /**
//     * 是否支持flv的音视频编码
//     */
//    private boolean supportFlvFormatCodec() {
//        int vcodec = grabber.getVideoCodec();
//        int acodec = grabber.getAudioCodec();
//        return (mediaInfo.getType() == 0)
//                && ("desktop".equals(mediaInfo.getPullUrl()) || avcodec.AV_CODEC_ID_H264 == vcodec
//                || avcodec.AV_CODEC_ID_H263 == vcodec)
//                && (avcodec.AV_CODEC_ID_AAC == acodec || avcodec.AV_CODEC_ID_AAC_LATM == acodec);
//    }

    public void go() {
        // 时间戳计算
        long startTime = 0;
        long videoTS = 0;
        // 累积延迟计算
//		long latencyDifference = 0;// 累积延迟
//		long lastLatencyDifference = 0;// 当前最新一组gop的延迟
//		long maxLatencyThreshold = 30000000;// 最大延迟阈值，如果lastLatencyDifference-latencyDifference>maxLatencyThreshold，则重启拉流器
//		long processTime = 0;// 上一帧处理耗时，用于延迟时间补偿，处理耗时不算进累积延迟
        int frameCount = 1;
        while (this.running.get()){
            try {
                long startGrab = System.currentTimeMillis();
                if (transferFlag) {
                    // 转复用
                    AVPacket pkt = grabber.grabPacket();
                    if ((System.currentTimeMillis() - startGrab) > 5000) {
                        log.info("\r\n{}\r\n视频流网络异常>>>", mediaInfo.getPullUrl());
                        break;
                    }
                    if (null != pkt && !pkt.isNull()) {
                        if (startTime == 0) {
                            startTime = System.currentTimeMillis();
                        }
                        videoTS = 1000 * (System.currentTimeMillis() - startTime);
                        // 判断时间偏移
                        if (videoTS > recorder.getTimestamp()) {
                            // System.out.println("矫正时间戳: " + videoTS + " : " + recorder.getTimestamp() + "
                            // -> "
                            // + (videoTS - recorder.getTimestamp()));
                            recorder.setTimestamp((videoTS));
                        }
                        recorder.recordPacket(pkt);
                    }
                } else {
                    // 转码
                    // 这边判断相机断网，正常50左右，断线15000
                    Frame frame = grabber.grabImage();
                    if ((System.currentTimeMillis() - startGrab) > 5000) {
                        log.info("\r\n{}\r\n视频流网络异常>>>", mediaInfo.getMediaKey());
                        break;
                    }
                    if (frame != null) {
                        if (startTime == 0) {
                            startTime = System.currentTimeMillis();
                        }
                        videoTS = 1000 * (System.currentTimeMillis() - startTime);
                        // 判断时间偏移
                        if (videoTS > recorder.getTimestamp()) {
                            // System.out.println("矫正时间戳: " + videoTS + " : " + recorder.getTimestamp() + "
                            // -> "
                            // + (videoTS - recorder.getTimestamp()));
                            recorder.setTimestamp((videoTS));
                        }
                        recorder.record(doProcessorFrame(frameCount, frame, converter));
                    } else {
                        break;
                    }
                }
                long endGrab = System.currentTimeMillis();
                log.debug("frameCount: {}, time: {} ms", frameCount, endGrab - startGrab);
                frameCount++;
            } catch (FrameGrabber.Exception e) {
                log.error("error: ", e);
                throw new BizException("grabber, error: {}", e.getMessage());
            } catch (FrameRecorder.Exception e) {
                log.error("error: ", e);
                throw new BizException("recorder, error: {}", e.getMessage());
            } catch (Exception e) {
                throw new BizException("play error");
            }
        }
        close();
        log.info("关闭媒体流-javacv，{} ", mediaInfo.getPullUrl());
    }

    /**
     * 完成对帧的处理, 比如对图片进行画框处理
     * 如果你想要对每帧图片画框操作, 不要使用 BufferedImage, 经过测试发现通过 Java2DFrameConverter 将
     * Frame转成BufferedImage 以及将 BufferedImage 转成 Frame, 比较耗时, 因此不建议使用BufferedImage
     * 对图片画框操作
     *
     * 建议使用
     * @param frame 帧数据
     * @param frameNum 第几帧
     * @param converter 用于对帧做相应的转换
     * @return 处理完成的帧
     */
    private Frame doProcessorFrame(int frameNum, Frame frame, OpenCVFrameConverter.ToIplImage converter) throws IOException {
        IplImage image = converter.convert(frame);
        // Graphics2DUtils.drawRect(image, 100 + frameNum % 1800, 200, 100, 700);
        // BufferedImage pic2 = image.getSubimage(100 + frameNum % 1800, 200, 100, 700);
        // ImageIO.write(pic2, "jpg", new File("/mnt/images/" + frameNum + ".jpg"));
        // OpenCvUtils.drawBox(image, 100 + frameNum % 1800, 200, 100, 700);
        return converter.convert(image);
    }

//    /**
//     * 将视频源转换为flv
//     */
//    protected void transferStream2Flv() {
//        if (!createGrabber()) {
//            return;
//        }
//        transferFlag = supportFlvFormatCodec();
//        if (!createRecorder()) {
//            return;
//
//        running = true;
//        start();
//    }

    /**
     * 关闭流媒体
     */
    private void close() {
        // 启动失败，直接关闭， close包含stop和release方法。录制文件必须保证最后执行stop()方法
        try {
            if (recorder != null) {
                recorder.close();
            }

            if (grabber != null) {
                grabber.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
