package com.demo.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 文件通道
 * @author shenguangyang
 * @date 2021-12-09 21:00
 */
public class NIOFileChannelTest {

    public static void main(String[] args) throws IOException {
        // writeFile("我是阳哥");
//        System.out.println(readFile());
        rwFileByBuffer();
        rwFileByCopyChannel();
    }

    public static void writeFile(String src) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("/mnt/test.txt")) {
            // 通过fileOutputStream 获取对应的channel
            FileChannel channel = fileOutputStream.getChannel();
            // 创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(src.getBytes(StandardCharsets.UTF_8));

            byteBuffer.flip();
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File("/mnt/test.txt"))) {
            FileChannel channel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);

            byteBuffer.flip();

            byte[] data = new byte[byteBuffer.limit()];
            byteBuffer.get(data);
            return new String(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 使用一个buffer完成文件的读写
     */
    public static void rwFileByBuffer() {
        try (FileInputStream fileInputStream = new FileInputStream("/mnt/demo.mp4");
             FileOutputStream fileOutputStream = new FileOutputStream("/mnt/demo_copy.mp4")) {
            FileChannel channelSrc = fileInputStream.getChannel();
            FileChannel channelTarget = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
            long start = System.currentTimeMillis();
            while (true) {
                byteBuffer.clear();
                if (channelSrc.read(byteBuffer) == -1) {
                    break;
                }
                byteBuffer.flip();
                channelTarget.write(byteBuffer);
            }
            channelSrc.close();
            channelTarget.close();
            long end = System.currentTimeMillis();
            System.out.println("[rwFileByBuffer] time: " + (end - start) + " ms");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void rwFileByCopyChannel() {
        try (FileInputStream fileInputStream = new FileInputStream("/mnt/demo.mp4");
             FileOutputStream fileOutputStream = new FileOutputStream("/mnt/demo_copy.mp4")) {
            FileChannel channelSrc = fileInputStream.getChannel();
            FileChannel channelTarget = fileOutputStream.getChannel();

            long start = System.currentTimeMillis();
            channelTarget.transferFrom(channelSrc, 0,  channelSrc.size());
            long end = System.currentTimeMillis();
            channelSrc.close();
            channelTarget.close();
            System.out.println("[rwFileByCopyChannel] time: " + (end - start) + " ms");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
