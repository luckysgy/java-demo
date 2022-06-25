package com.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shenguangyang
 * @date 2022/1/19 16:28
 */
@ConfigurationProperties(prefix = "milvus")
public class MilvusProperties {
    private String host;
    private Integer port;
    /**
     * 一个向量的容量, 比如512 / 1024
     */
    private Integer vectorDim = 512;

    /**
     * 在搜索向量时候返回向量距离较小的前5条数据
     */
    private Integer topK = 5;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getVectorDim() {
        return vectorDim;
    }

    public void setVectorDim(Integer vectorDim) {
        this.vectorDim = vectorDim;
    }

    public Integer getTopK() {
        return topK;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }
}
