package com.demo.impl;

import com.concise.component.core.exception.BizException;
import com.concise.component.core.utils.CollectionUtils;
import com.concise.component.core.utils.StringUtils;
import com.demo.MilvusData;
import com.demo.MilvusProperties;
import com.demo.MilvusService;
import io.milvus.Response.DescCollResponseWrapper;
import io.milvus.Response.SearchResultsWrapper;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.index.DropIndexParam;
import io.milvus.param.partition.CreatePartitionParam;
import io.milvus.param.partition.DropPartitionParam;
import io.milvus.param.partition.HasPartitionParam;
import io.milvus.param.partition.ShowPartitionsParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022/1/19 11:17
 */
@Service
public class MilvusServiceImpl implements MilvusService {
    private static final Logger log = LoggerFactory.getLogger(MilvusServiceImpl.class);
    @Autowired
    private MilvusServiceClient milvusServiceClient;

    @Autowired
    private MilvusProperties milvusProperties;

    public void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new BizException(r.getMessage());
        }
    }

    @Override
    public R<RpcStatus> createCollection() {
        FieldType idField = FieldType.newBuilder()
                .withName(DATA_ID_FIELD)
                .withDescription("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .build();

        FieldType vectorField = FieldType.newBuilder()
                .withName(VECTOR_FIELD)
                .withDescription("vector")
                .withDataType(DataType.FloatVector)
                .withDimension(milvusProperties.getVectorDim())
                .build();

        FieldType timestampField = FieldType.newBuilder()
                .withName(TIMESTAMP_FIELD)
                .withDescription("timestamp")
                .withDataType(DataType.Int64)
                .build();

        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withDescription("customer info")
                .withShardsNum(2)
                .addFieldType(idField)
                .addFieldType(vectorField)
                .addFieldType(timestampField)
                .build();
        R<RpcStatus> response = milvusServiceClient.withTimeout(CREATE_COLLECTION_TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS)
                .createCollection(createCollectionReq);
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<RpcStatus> dropCollection() {
        return milvusServiceClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
    }

    @Override
    public R<Boolean> hasCollection() {
        R<Boolean> response = milvusServiceClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<RpcStatus> loadCollection() {
        R<RpcStatus> response = milvusServiceClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<DescribeCollectionResponse> describeCollection() {
        R<DescribeCollectionResponse> response = milvusServiceClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        DescCollResponseWrapper wrapper = new DescCollResponseWrapper(response.getData());
        log.info(wrapper.toString());
        return response;
    }

    @Override
    public R<RpcStatus> createPartition() {
        R<RpcStatus> response = milvusServiceClient.createPartition(CreatePartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(PARTITION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<RpcStatus> dropPartition() {
        R<RpcStatus> response = milvusServiceClient.dropPartition(DropPartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(PARTITION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<Boolean> hasPartition() {
        R<Boolean> response = milvusServiceClient.hasPartition(HasPartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(PARTITION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<ShowPartitionsResponse> showPartitions() {
        R<ShowPartitionsResponse> response = milvusServiceClient.showPartitions(ShowPartitionsParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<RpcStatus> createIndex() {
        R<RpcStatus> response = milvusServiceClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .withIndexType(INDEX_TYPE)
                .withMetricType(MetricType.L2)
                .withExtraParam(INDEX_PARAM)
                .withSyncMode(Boolean.TRUE)
                .build());
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<MutationResult> insert(MilvusData milvusData) {
        List<List<Float>> vectorData = new ArrayList<>();
        List<Float> vector = vectorStrToList(milvusData.getVector());
        vectorData.add(vector);

        List<Long> timestamp = new ArrayList<>();
        timestamp.add(milvusData.getTimestamp());

        List<Long> idList = new ArrayList<>();
        idList.add(milvusData.getDataId());

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field(DATA_ID_FIELD, DataType.Int64, idList));
        fields.add(new InsertParam.Field(VECTOR_FIELD, DataType.FloatVector, vectorData));
        fields.add(new InsertParam.Field(TIMESTAMP_FIELD, DataType.Int64, timestamp));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(PARTITION_NAME)
                .withFields(fields)
                .build();

        R<MutationResult> response = milvusServiceClient.insert(insertParam);
        handleResponseStatus(response);
        return response;
    }

    @Override
    public R<RpcStatus> dropIndex() {
        R<RpcStatus> response = milvusServiceClient.dropIndex(DropIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .build());
        handleResponseStatus(response);
        return response;
    }

    private List<Float> vectorStrToList(String vector) {
        if (StringUtils.isEmpty(vector)) {
            throw new BizException("vector is null");
        }

        List<Float> vectorList = new ArrayList<>();
        String replaceVector = vector.replace("[", "").replace("]", "");
        String[] split = replaceVector.split(",");
        for (String vectorStr : split) {
            if (StringUtils.isEmpty(vectorStr)) {
                continue;
            }
            vectorList.add(Float.valueOf(vectorStr));
        }
        return vectorList;
    }

    @Override
    public R<SearchResults> search(Long startTimestamp, String vector) {
        List<List<Float>> vectorRow = new ArrayList<>();

        List<String> outFields = Collections.singletonList(TIMESTAMP_FIELD);
        List<Float> vectorData = vectorStrToList(vector);
        vectorRow.add(vectorData);

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(MetricType.L2)
                .withOutFields(outFields)
                .withTopK(milvusProperties.getTopK())
                .withVectors(vectorRow)
                .withVectorFieldName(VECTOR_FIELD)
                .withExpr(TIMESTAMP_FIELD + " > " + startTimestamp)
                .withParams(SEARCH_PARAM)
                .build();

        R<SearchResults> response = milvusServiceClient.search(searchParam);
        handleResponseStatus(response);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(0);
        if (CollectionUtils.isEmpty(scores)) {
            log.warn("not search result");
            return response;
        }
        for (int i = 0; i < scores.size(); i++) {
            /*
            getLongID()  == insert方法中往集合中添加的第一个字段
            List<InsertParam.Field> fields = new ArrayList<>();
            fields.add(new InsertParam.Field(DATA_ID_FIELD, DataType.Int64, idList));
            fields.add(new InsertParam.Field(VECTOR_FIELD, DataType.FloatVector, vectorData));
            fields.add(new InsertParam.Field(TIMESTAMP_FIELD, DataType.Int64, timestamp));

            如果TIMESTAMP_FIELD是第一个添加到集合中的话，则此时获取到的 getLongID() == timestamp
            */
            log.info("search score: {}, id: {}", scores.get(i).getScore(), scores.get(i).getLongID());
        }

        return response;
    }
}
