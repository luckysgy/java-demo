package com.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.concise.component.core.utils.file.FileUtils;
import com.concise.component.core.utils.file.ImageUtils;
import com.concise.component.javacv.opencv.OpenCvHelper;
import com.concise.component.javacv.opencv.OpencvImgcodecsHelper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2021-11-28 12:31
 */
public class OpenCvForNativeUtilsDemo {
    private static final Logger log = LoggerFactory.getLogger(OpenCvForNativeUtilsDemo.class);
    static {
        OpenCvForNativeUtils.init();
    }

    /**
     * 测试编码之后再解码然后画框 (原生方式)
     */
    @Test
    public void testCodecAndDrawBox() {
        log.info("testCodecAndDrawBox");
        List<Mat> mats = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Mat mat = Imgcodecs.imread("/mnt/data/1.jpg");
            System.out.println("channel: " + mat.channels());
            mats.add(mat);
        }

        for (Mat mat : mats) {
            byte[] bytes = OpenCvForNativeUtils.toBytesByEncode(mat);
            long start = System.currentTimeMillis();
            Mat matDecode = OpenCvForNativeUtils.toMatByDecode(bytes);
            OpenCvForNativeUtils.drawBox(matDecode, 200, 200, RandomUtil.randomInt(800), RandomUtil.randomInt(800));
//            OpenCvForNativeUtils.drawBox(mat, 200, 200, RandomUtil.randomInt(800), RandomUtil.randomInt(800));
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            Imgcodecs.imwrite("/mnt/data/3_test.jpg", mat);
        }
    }

    /**
     * 此代码有问题
     * @throws InterruptedException
     */
    @Test
    public void testCutImage() throws InterruptedException {
        for (int j = 0; j < 1; j++) {
            new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    try (OpenCvHelper openCvHelper = new OpenCvHelper();
                         OpencvImgcodecsHelper imgcodecsHelper = new OpencvImgcodecsHelper();
                         InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i))){
                        byte[] bytes1 = IOUtils.toByteArray(fileInputStream);
                        long start = System.currentTimeMillis();

                        Mat mat1 = OpenCvForNativeUtils.toMatByDecode(bytes1);
                        Mat mat = OpenCvForNativeUtils.cutImage(mat1, 100, 100, 200, 400);
//                        byte[] bytes = openCvHelper.matToBytes(mat);
                        byte[] bytes = OpenCvForNativeUtils.toBytesByEncode(mat);
                        long end = System.currentTimeMillis();
                        System.out.println(StrUtil.format("count: {}, w: {}, h: {}, time: {} ms", i, mat1.cols(), mat1.rows(), (end - start)));
                        FileUtils.writeBytesToFile(bytes, "/mnt/cutImages/" , StrUtil.format("{}.jpg", i));
                        mat1.release();
                        mat.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }

    /**
     * 测试对输入流画框
     */
    @Test
    public void testInputStreamDrawBox() throws IOException {
        log.info("testInputStreamDrawBox");
        List<InputStream> inputStreamList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            InputStream inputStream = new FileInputStream("/mnt/data/1.jpg");
            inputStreamList.add(inputStream);
        }

        for (InputStream inputStream : inputStreamList) {
            long start = System.currentTimeMillis();
            Mat mat = OpenCvForNativeUtils.inputStreamToMat(inputStream);
            OpenCvForNativeUtils.drawBox(mat, 200, 200, RandomUtil.randomInt(800), RandomUtil.randomInt(800));
            InputStream newInputStream = OpenCvForNativeUtils.matToInputStream(mat);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            ImageUtils.save(newInputStream, "/mnt/data/testInputStreamDrawBoxForNative.jpg");
        }
    }
}
