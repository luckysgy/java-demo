package com.demo.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class FileReader {
    private static PipedInputStream pipedInputStream = new PipedInputStream();
    private static PipedOutputStream pipedOutputStream = new PipedOutputStream();

    public static void main(String args[]) throws Exception {
        new Thread(() -> {
            try {
                CopyFile.start("/mnt/demo.mp4", "/mnt/demo1.mp4");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                // do something
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        File file = new File("/mnt/demo1.mp4");

        System.out.println(file.getAbsolutePath());

        if (file.exists() && file.canRead()) {
            long fileLength = file.length();

            readFile(file, 0L);

            while (true) {
                if (fileLength > 0) {
                    readFile(file, fileLength);
                    fileLength = file.length();
                }
                TimeUnit.MILLISECONDS.sleep(600);
            }

        } else{
            System.out.println("no file to read");

        }

    }

    public static void readFile(File file, Long fileLength) throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(new java.io.FileReader(file));
        in.skip(fileLength);
        while ((line = in.readLine()) != null) {
            // System.out.println(line);
//            System.out.println("------------");
            pipedOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
        }
        in.close();
    }
}

