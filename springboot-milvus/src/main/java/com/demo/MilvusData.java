package com.demo;

/**
 * 注意: 经过我的测试, 字段不要起名为 vectorId / id
 * 如果你使用了这两个字段会，您将不会正确的从搜索向量结果获取起名为vectorId/id字段的值
 * @author shenguangyang
 * @date 2022/1/19 11:08
 */
public class MilvusData {
    private Long dataId;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 向量格式为 "[
     * 0.005228336434811354, -0.0007952402229420841, 0.033497154712677, -0.015743914991617203,
     * -0.03257768973708153, -0.008545062504708767, 0.023332715034484863, 0.03491530194878578,
     * -0.05688280612230301, 0.016819793730974197, 0.0036810997407883406, -0.004693312104791403,
     * -0.028037693351507187, 0.014961494132876396, 0.029848024249076843, 0.037265874445438385...
     * ]"
     */
    private String vector;


    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }
}
