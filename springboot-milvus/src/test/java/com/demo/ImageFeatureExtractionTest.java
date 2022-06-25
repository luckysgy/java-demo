package com.demo;

import cn.hutool.core.util.StrUtil;
import com.concise.component.core.utils.Base64Util;

import com.demo.impl.ImageFeatureExtractionServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 特征向量测试
 * 调用我写的python 特征提取接口
 * @author shenguangyang
 * @date 2022-01-19 20:22
 */
//@SpringBootTest
public class ImageFeatureExtractionTest {
//    @Autowired
    private ImageFeatureExtractionService imageFeatureExtractionService = new ImageFeatureExtractionServiceImpl();

    @Test
    public void extraction() throws InterruptedException {
        for (int i = 2; i <= 2; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    String imageBase64 = Base64Util.encode(IOUtils.toByteArray(new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", finalI))));
                    imageFeatureExtractionService.extraction(imageBase64);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }
}
