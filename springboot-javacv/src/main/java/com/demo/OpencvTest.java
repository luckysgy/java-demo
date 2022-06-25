package com.demo;

import org.junit.jupiter.api.Test;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author shenguangyang
 * @date 2021-12-28 22:31
 */
public class OpencvTest {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test
    public void matchTemplate() throws IOException {
        BufferedImage source = ImageIO.read(new File("/mnt/copy.jpg"));
        BufferedImage template = ImageIO.read(new File("/mnt/copy2.jpg"));
        OpencvMatchImage opencvMatchImage = new OpencvMatchImage();
        for (int i = 0; i < 200; i++) {
            long start = System.currentTimeMillis();
            opencvMatchImage.matchImage(source, template);
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) + " ms");
        }
    }
}
