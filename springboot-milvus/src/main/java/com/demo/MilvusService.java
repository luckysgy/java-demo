package com.demo;

import io.milvus.grpc.DescribeCollectionResponse;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.grpc.ShowPartitionsResponse;
import io.milvus.param.IndexType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;

/**
 * @author shenguangyang
 * @date 2022/1/19 10:52
 */
public interface MilvusService {
    /**
     * 创建集合超时时间
     */
    long CREATE_COLLECTION_TIMEOUT_MILISECONDS = 2000;

    String COLLECTION_NAME = "DEMO";
    String PARTITION_NAME = "DEMO";
    String DATA_ID_FIELD = "dataId";
    String VECTOR_FIELD = "vector";
    String TIMESTAMP_FIELD = "timestamp";

//    public static final String PROFILE_FIELD = "userProfile";
//    public static final Integer BINARY_DIM = 128;

    IndexType INDEX_TYPE = IndexType.IVF_FLAT;
    String INDEX_PARAM = "{\"nlist\":128}";

    // nlist代表聚类数，根据数据量多少设置
    String SEARCH_PARAM = "{\"nprobe\":10}";

    /**
     * 创建集合
     * @return
     */
    default R<RpcStatus> createCollection() {
        throw new UnsupportedOperationException("createCollection no supported");
    }

    /**
     * 删除集合
     * @return
     */
    default R<RpcStatus> dropCollection() {
        throw new UnsupportedOperationException("dropCollection no supported");
    }

    /**
     * 判断是否有集合
     * @return
     */
    default R<Boolean> hasCollection() {
        throw new UnsupportedOperationException("hasCollection no supported");
    }

    /**
     * 将collection加载到内存
     * @return
     */
    default R<RpcStatus> loadCollection() {
        throw new UnsupportedOperationException("loadCollection no supported");
    }

    /**
     * 描述集合
     * @return
     */
    default R<DescribeCollectionResponse> describeCollection() {
        throw new UnsupportedOperationException("describeCollection no supported");
    }

    /**
     * 创建分区
     * @return
     */
    default R<RpcStatus> createPartition() {
        throw new UnsupportedOperationException("createPartition no supported");
    }

    /**
     * 删除分区
     * @return
     */
    default R<RpcStatus> dropPartition() {
        throw new UnsupportedOperationException("dropPartition no supported");
    }

    /**
     * 是否有分区
     * @return
     */
    default R<Boolean> hasPartition() {
        throw new UnsupportedOperationException("hasPartition no supported");
    }

    /**
     * 展示分区
     * @return
     */
    default R<ShowPartitionsResponse> showPartitions() {
        throw new UnsupportedOperationException("showPartitions no supported");
    }

    default R<RpcStatus> createIndex() {
        throw new UnsupportedOperationException("createIndex no supported");
    }

    default R<MutationResult> insert(MilvusData milvusData) {
        throw new UnsupportedOperationException("insert no supported");
    }

    default R<RpcStatus> dropIndex() {
        throw new UnsupportedOperationException("dropIndex no supported");
    }

    /**
     * 搜索
     * @param startTimestamp 起始事件
     * @param vector 搜索的向量
     * @return
     */
    default R<SearchResults> search(Long startTimestamp, String vector) {
        throw new UnsupportedOperationException("search no supported");
    }
}
