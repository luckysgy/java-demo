package com.demo.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * @Description:
 */
public class GZIPUtils {
    private static final Logger log = LoggerFactory.getLogger(GZIPUtils.class);
    /**
     * 压缩消息
     * @param src -- 要压缩的消息
     * @param level -- 压缩级别
     * @return
     * @throws IOException
     */
    public static byte[] compress(final byte[] src, final int level) throws IOException {
        byte[] result = src;
        //压缩后的消息将被写入
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(src.length);
        //压缩级别
        java.util.zip.Deflater defeater = new java.util.zip.Deflater(level);
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, defeater);
        try {
            deflaterOutputStream.write(src);
            deflaterOutputStream.finish();
            deflaterOutputStream.close();
            //存储压缩后的消息内容
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            defeater.end();
            throw e;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException ignored) {
            }

            defeater.end();
        }

        return result;
    }

    /**
     * 解压
     * @param src -- 解压源
     * @return
     * @throws IOException
     */
    public static byte[] uncompress(final byte[] src) throws IOException {
        byte[] result = src;
        byte[] uncompressData = new byte[src.length];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(src);
        InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(src.length);

        try {
            while (true) {
                int len = inflaterInputStream.read(uncompressData, 0, uncompressData.length);
                if (len <= 0) {
                    break;
                }
                byteArrayOutputStream.write(uncompressData, 0, len);
            }
            byteArrayOutputStream.flush();
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                log.error("Failed to close the stream", e);
            }
            try {
                inflaterInputStream.close();
            } catch (IOException e) {
                log.error("Failed to close the stream", e);
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                log.error("Failed to close the stream", e);
            }
        }

        return result;
    }
 
    public static void main(String[] args) throws IOException {
        InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", 1));
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        System.out.println(bytes.length);
        System.out.println("原字节数组长度：" + bytes.length);
        byte[] compress = GZIPUtils.compress(bytes, 8);
        System.out.println("压缩后字节数组长度：" + compress.length);
        byte[] uncompress = GZIPUtils.uncompress(compress);
        System.out.println("解压缩字节数组长度：" + uncompress.length);
        fileInputStream.close();
    }
}