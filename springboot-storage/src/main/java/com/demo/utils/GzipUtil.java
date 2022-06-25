package com.demo.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.concise.component.core.utils.Base64Util;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.TextUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZip工具类
 * zip压缩解压并使用Base64进行编码工具类
 * 调用：
 * 压缩
 *  GZipUtil.compress(str)
 * 解压
 * GZipUtil.uncompressToString(bytes)
 */
public class GzipUtil {
    private static final String TAG = "GzipUtil";
    /**
     * 将字符串进行gzip压缩
     *
     * @param data
     * @return
     */
    public static String compress(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data.getBytes(StandardCharsets.UTF_8));
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64Util.encode(out.toByteArray());
    }

    public static String uncompress(String data) throws IOException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        byte[] decode = Base64Util.decode(data);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(decode);) {
            GZIPInputStream gzipStream = null;
            gzipStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void main(String[] args) throws IOException {
        List<byte[]> byteList = new ArrayList<>();
        for (int i = 200; i < 300; i++) {
            try (InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i));) {
                byte[] bytes = IOUtils.toByteArray(fileInputStream);
                byteList.add(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String target = JSON.toJSONString(byteList);
        System.out.println(StrUtil.format("未压缩总大小: {} bytes", target.length()));
        long s1 = System.currentTimeMillis();
        String compress = GzipUtil.compress(target);

        System.out.println(StrUtil.format("压缩后字节数组长度：{}, time: {} ms" , compress.length(), (System.currentTimeMillis() - s1)));
        String uncompress = GzipUtil.uncompress(compress);
        System.out.println("解压缩字节数组长度：" + (uncompress == null ? 0 : uncompress.length()));
    }
}