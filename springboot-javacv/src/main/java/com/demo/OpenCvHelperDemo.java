package com.demo;

import cn.hutool.core.util.StrUtil;
import com.concise.component.core.utils.file.FileUtils;
import com.concise.component.javacv.opencv.OpenCvHelper;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shenguangyang
 * @date 2022-01-15 9:34
 */
public class OpenCvHelperDemo {
    public static void main(String[] args) throws InterruptedException {
        matAndBytesConvert();
    }
    public static void matAndBytesConvert() throws InterruptedException {
        for (int k = 0; k < 1000; k++) {
            List<byte[]> dataList = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                try (InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i))){
                    byte[] bytes1 = IOUtils.toByteArray(fileInputStream);
                    dataList.add(bytes1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AtomicInteger count = new AtomicInteger();
            List<List<byte[]>> partition = Lists.partition(dataList, 100);
            CountDownLatch countDownLatch = new CountDownLatch(partition.size());
            for (List<byte[]> byteList : partition) {
                new Thread(() -> {
                    for (byte[] bytes : byteList) {
                        try (OpenCvHelper openCvHelper = new OpenCvHelper()){
                            int i = count.addAndGet(1);
                            long start = System.currentTimeMillis();
                            Mat imread = openCvHelper.bytesToMatByDecode(bytes);
                            Mat mat = openCvHelper.cutImage(imread, 100, 100, 200, 400);
                            byte[] resultBytes = openCvHelper.matToBytes(mat);
                            long end = System.currentTimeMillis();
                            System.out.println(StrUtil.format("count: {}, w: {}, h: {}, time: {} ms", i, imread.cols(), imread.rows(), (end - start)));
                            FileUtils.writeBytesToFile(resultBytes, "/mnt/cutImages/" , StrUtil.format("{}.jpg", i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    countDownLatch.countDown();
                }).start();
            }
            countDownLatch.await();
            System.gc();
        }
        TimeUnit.SECONDS.sleep(160);
    }
}
