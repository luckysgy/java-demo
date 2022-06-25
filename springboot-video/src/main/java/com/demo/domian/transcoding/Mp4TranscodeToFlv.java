package com.demo.domian.transcoding;

import com.concise.component.core.exception.BizException;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * mp4转码为flv文件, 添加自定义sei
 * ffmpeg  -i oceans-flv.flv -c:v copy -bsf:v h264_metadata=sei_user_data='086f3693-b7b3-4f2c-9653-21492feee5b8+HelloWorld' oceans.sei.flv
 * @author shenguangyang
 * @date 2022-02-19 8:59
 */
public class Mp4TranscodeToFlv {
    private static final Logger log = LoggerFactory.getLogger(Mp4TranscodeToFlv.class);
    /**
     * 拉流器
     */
    private FFmpegFrameGrabber grabber;
    /**
     * 推流录制器
     */
    private FFmpegFrameRecorder recorder;

    private int width;
    private int height;
    private int bitrate;
    private double frameRate = 25.0;
    /**
     * 运行状态
     */
    private final AtomicBoolean running = new AtomicBoolean(true);

    private final String mp4FilePath;
    private final String flvFilePath;

    public Mp4TranscodeToFlv(String mp4FilePath, String flvFilePath) {
        this.mp4FilePath = mp4FilePath;
        this.flvFilePath = flvFilePath;
    }

    /**
     * 创建拉流器
     *
     */
    public Mp4TranscodeToFlv from() {
        // 拉流器
        grabber = new FFmpegFrameGrabber(mp4FilePath);
        grabber.setOption("threads", "1");

        grabber.setFormat("mp4");
        try {
            grabber.startUnsafe();
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
            frameRate = grabber.getFrameRate();
            bitrate = grabber.getVideoBitrate();
            log.info("\r\n{}\r\n启动拉流器成功", mp4FilePath);
            return this;
        } catch (FrameGrabber.Exception e) {
            throw new BizException("\r\n{}\r\n启动拉流器失败，网络超时或视频源不可用", mp4FilePath);
        }
    }

    /**
     * 创建转码推流录制器
     */
    public Mp4TranscodeToFlv to() {
        recorder = new FFmpegFrameRecorder(flvFilePath, width, height);
        recorder.setFormat("flv");
        // 转码
        recorder.setInterleaved(true);
        // 降低编码延时
        recorder.setVideoOption("tune", "zerolatency");
        // 提升编码速度
        recorder.setVideoOption("preset", "ultrafast");
        // 视频质量参数(详见 https://trac.ffmpeg.org/wiki/Encode/H.264)
        recorder.setVideoOption("crf", "28");
        recorder.setVideoOption("threads", "1");
        // 设置帧率
        recorder.setFrameRate(frameRate);
        // 设置gop,与帧率相同，相当于间隔1秒chan's一个关键帧
        recorder.setGopSize((int) frameRate);
        recorder.setVideoBitrate(bitrate);
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
    }

    public void go() {
        int frameCount = 1;
        // 086f3693-b7b3-4f2c-9653-21492feee5b8
        String seiUserDataUuid = "086f3693-b7b3-4f2c-9653-21492feee5b8";
        while (this.running.get()){
            try {
                long startGrab = System.currentTimeMillis();
                // 转码
                // 这边判断相机断网，正常50左右，断线15000
                Frame frame = grabber.grabImage();
                if ((System.currentTimeMillis() - startGrab) > 5000) {
                    log.info("\r\n{}\r\n视频流网络异常>>>", flvFilePath);
                    break;
                }

                if (frame != null) {
                    if (frameCount % 50 == 0) {
                        recorder.record(frame, seiUserDataUuid, String.valueOf(frameCount));
                    } else {
                        recorder.record(frame);
                    }
                } else {
                    break;
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
        log.info("关闭媒体流-javacv，{} ", mp4FilePath);
    }

    /**
     * 关闭流媒体
     */
    public void close() {
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
