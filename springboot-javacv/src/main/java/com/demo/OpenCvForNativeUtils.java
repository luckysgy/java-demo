package com.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_COLOR;

/**
 * opencv工具类 -- 原生方式
 * 所谓原生方式就是通过 java直接调用opencv, 而不是通过javacv去调用opencv
 * 但是你运行的环境中必须有opencv环境
 *
 * note: 当前工具类加载opencv库会和javacv有冲突, 因此实际上要分开
 * 目前来看javacv已经很完善了, 直接使用 {@link OpenCvUtils} 即可
 * @author shenguangyang
 * @date 2021-11-27 18:04
 */
public class OpenCvForNativeUtils {
    private static final Logger log = LoggerFactory.getLogger(OpenCvForNativeUtils.class);
    // 定义一个颜色
    public static Scalar color = new Scalar(0, 0, 255);

    static {
        log.info("load lib: {}", Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //NativeUtils.loadFromJar("org/bytedeco/opencv/windows-x86_64/opencv_java.dll");
    }

    /**
     * 初始化中什么也没做, 主要用于执行static代码块
     */
    public static void init() {

    }

    /**
     * 裁剪图片
     * @param srcMat 原图片Mat
     * @param startX 左上角起始x
     * @param startY 左上角起始Y
     * @param width 宽
     * @param height 高
     * @return 返回Mat, 可以通过 {@link #matToInputStream(Mat)} 转成 InputStream
     */
    public static Mat cutImage(Mat srcMat, int startX, int startY, int width, int height) {
        Mat imgDesc = null;
        Mat imgROI = null;
        try {
            // 目标Mat
            imgDesc = new Mat(width, height, CvType.CV_8UC3);
            // 设置ROI
            imgROI = new Mat(srcMat, new Rect(startX, startY, width, height));
            // 从ROI中剪切图片
            imgROI.copyTo(imgDesc);
            return imgDesc;
        } catch (Exception e) {
            throw e;
        } finally {
//            if (ObjectUtil.isNotNull(srcMat)) {
//                matList.add(srcMat);
//            }
//            if (ObjectUtil.isNotNull(imgDesc)) {
//                matList.add(imgDesc);
//            }
//            if (ObjectUtil.isNotNull(imgROI)) {
//                matList.add(imgROI);
//            }
        }
    }

    /**
     * 将图片字节转成 Mat
     *
     * bytes必须是 {@link #toBytesByEncode(Mat)} 获取到的
     * @param bytes
     * @return
     */
    public static Mat toMatByDecode(byte[] bytes) {
        MatOfByte mob = new MatOfByte(bytes);
        return Imgcodecs.imdecode(mob, IMREAD_COLOR);
    }

    /**
     * 将图片字节转成 IplImage
     * @param img
     * @return
     */
    public static byte[] toBytesByEncode(Mat img) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, mob);
        return mob.toArray();
    }

    /**
     * 输入流转mat
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Mat inputStreamToMat(InputStream inputStream) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            os.close();
            bis.close();

            Mat encoded = new Mat(1, os.size(), 0);
            encoded.put(0, 0, os.toByteArray());

            Mat decoded = Imgcodecs.imdecode(encoded, -1);
            encoded.release();
            return decoded;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * mat转输入流
     * @param mat
     * @return
     */
    public static InputStream matToInputStream(Mat mat) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mob);
        byte[] byteArray = mob.toArray();
        return new ByteArrayInputStream(byteArray);
    }

    /**
     * 使用原生opencv 画框
     * @param x1 矩形的左上角坐标位置 x坐标
     * @param y1 矩形的左上角坐标位置 y坐标
     * @param w 宽
     * @param h 高
     */
    public static void drawBox(Mat img, int x1, int y1, int w, int h){
        Imgproc.rectangle(
                img,
                new Point(x1, y1),
                new Point(x1 + w, y1 + h),
                color,
                // 线条的宽度:正值就是线宽，负值填充矩形,例如CV_FILLED，值为-1
                4,
                // 线条的类型(0,8,4)
                4
        );
    }

}
