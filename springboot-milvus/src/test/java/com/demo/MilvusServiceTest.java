package com.demo;

import cn.hutool.core.util.StrUtil;
import com.concise.component.core.utils.Base64Util;
import io.milvus.grpc.DescribeCollectionResponse;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenguangyang
 * @date 2022/1/19 12:47
 */
@SpringBootTest
class MilvusServiceTest {
    private static final Logger log = LoggerFactory.getLogger(MilvusServiceTest.class);
    @Autowired
    private MilvusService milvusService;

    @Autowired
    private ImageFeatureExtractionService imageFeatureExtractionService;

    /**
     * 图片特征向量检索
     * 1. 先获取我本地的1-7张图片发送给算法获取特征向量，然后将向量添加到milvus中
     * 2. 将我本地的第8张图片发送给算法获取特征向量，然后将返回的特征向量作为搜索参数，从milvus中搜索最佳匹配的向量
     * 3. 我本地模拟的图片数据中 , 7 和 8 图片最为相似，所以milvus返回的结果中, 如果 dataId = 7 则是正确的
     * note: 本特征向量提取算法接口不准确, 所以并未实现上述效果
     * @throws IOException
     */
    @Test
    void test() throws IOException, InterruptedException {
        milvusService.dropCollection();
        final R<Boolean> booleanR = milvusService.hasCollection();
        if (!booleanR.getData()) {
            milvusService.createCollection();
        }
        R<DescribeCollectionResponse> describeCollectionResponseR = milvusService.describeCollection();
        log.info("2\t" + describeCollectionResponseR.toString());
        milvusService.loadCollection();

        milvusService.createPartition();
        milvusService.hasPartition();
        milvusService.showPartitions();

        milvusService.dropIndex();
        final R<RpcStatus> index = milvusService.createIndex();
        log.info("3\t" + index);

        Map<String, String> vectorMap = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            try {
                String imageBase64 = Base64Util.encode(IOUtils.toByteArray(new FileInputStream(StrUtil.format("/mnt/images/source/{}.jpg", i))));
                vectorMap.put(i + "", imageFeatureExtractionService.extraction(imageBase64));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String imageBase64 = Base64Util.encode(IOUtils.toByteArray(new FileInputStream("/mnt/images/source/10.jpg")));
        String keywordVector = imageFeatureExtractionService.extraction(imageBase64);

        for (Map.Entry<String, String> entry : vectorMap.entrySet()) {
            MilvusData milvusData = new MilvusData();
            milvusData.setTimestamp(System.currentTimeMillis());
            milvusData.setDataId(Long.parseLong(entry.getKey()));
            milvusData.setVector(entry.getValue());
            R<MutationResult> insert = milvusService.insert(milvusData);
            log.info("4\t" + insert.getData().getIDs());
        }

        R<SearchResults> search = milvusService.search(0L, keywordVector);
    }
}