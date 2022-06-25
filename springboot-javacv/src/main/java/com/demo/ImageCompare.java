package com.demo;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片识别
 * @author shenguangyang
 * @date 2021-12-01 7:30
 */
public class ImageCompare {
    private static final Logger log = LoggerFactory.getLogger(ImageCompare.class);

    /**
     * 标识两张图片完全相同
     */
    public static final double SAME = 1.0;

    /**
     * 匹配图片
     *
     * @param mat1 图片1
     * @param mat2 图片2
     * @return 返回相似度
     */
    public static double matchImage(Mat mat1, Mat mat2) {
        if(mat1.cols() == 0 || mat2.cols() == 0 || mat1.rows() == 0 || mat2.rows() == 0) {
            log.warn("图片文件路径异常，获取的图片大小为0，无法读取");
            return 0.0;
        }
        if(mat1.cols() != mat2.cols() || mat1.rows() != mat2.rows()) {
            log.warn("两张图片大小不同，无法比较");
            return 0.0;
        }
        mat1.convertTo(mat1, CvType.CV_8UC1);
        mat2.convertTo(mat2, CvType.CV_8UC1);
        Mat mat1_gray = new Mat();
        opencv_imgproc.cvtColor(mat1, mat1_gray, Imgproc.COLOR_BGR2GRAY);
        Mat mat2_gray = new Mat();
        opencv_imgproc.cvtColor(mat2, mat2_gray, Imgproc.COLOR_BGR2GRAY);
        mat1_gray.convertTo(mat1_gray, CvType.CV_32F);
        mat2_gray.convertTo(mat2_gray, CvType.CV_32F);
        // 此处结果为1则为完全相同
        return opencv_imgproc.compareHist(mat1_gray, mat2_gray, Imgproc.CV_COMP_CORREL);
    }
}
