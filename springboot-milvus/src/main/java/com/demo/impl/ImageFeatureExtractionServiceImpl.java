package com.demo.impl;

import cn.hutool.http.HttpUtil;
import com.concise.component.core.exception.BizException;
import com.demo.ImageFeatureExtractionService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenguangyang
 * @date 2022-01-19 21:55
 */
@Service
public class ImageFeatureExtractionServiceImpl implements ImageFeatureExtractionService {
    private static final Logger log = LoggerFactory.getLogger(ImageFeatureExtractionServiceImpl.class);

    public static class ApiResult {
        private Integer code;
        private String message;
        private String data;


        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    @Override
    public String extraction(String imageBase64) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("image_base64", imageBase64);
            String result = HttpUtil.post("http://192.168.190.70:44080/api/image/feature/extraction", new Gson().toJson(data));
            Gson gson = new Gson();
            ApiResult apiResult = gson.fromJson(result, ApiResult.class);
            if (apiResult.getCode() != 200) {
                throw new BizException("无特征向量返回");
            }
            String imageFeature = apiResult.getData();
            log.info("imageFeature: {}", imageFeature);
            return imageFeature;
        } catch (Exception e) {
            throw new BizException("无特征向量返回");
        }
    }
}
