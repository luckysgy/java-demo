package com.demo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author shenguangyang
 * @date 2022-02-09 20:05
 */
public class CopyFile {
    public static void start(String sourceFilePath, String targetFilePath) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFilePath));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFilePath));
        byte[] by = new byte[1024];
        int len;
        while ((len = bis.read(by)) != -1) {
            bos.write(by, 0, len);
        }
        bos.close();
        bis.close();

    }
}
