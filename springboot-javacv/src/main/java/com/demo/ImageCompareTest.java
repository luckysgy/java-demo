package com.demo;

import com.concise.component.core.utils.file.FileUtils;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shenguangyang
 * @date 2021-12-29 7:43
 */
public class ImageCompareTest {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Mat> data = new ConcurrentHashMap<>();
        List<String> allFile = FileUtils.getAllFile("/mnt/16_resize", false, null);
        for (String file : allFile) {
            Mat imread = opencv_imgcodecs.imread(file);
            data.put(file, imread);
        }

        Mat target = opencv_imgcodecs.imread("/mnt/16_resize/resize_image444.jpg");
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            for (Map.Entry<String, Mat> entry : data.entrySet()) {
                Mat next = entry.getValue();
                if (ImageCompare.matchImage(next, target) >= 0.98) {
                    System.out.println("图片相同: " + entry.getKey());
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) + " ms");
        }
    }
}
