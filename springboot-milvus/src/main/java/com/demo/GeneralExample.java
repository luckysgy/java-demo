package com.demo;

import io.milvus.Response.DescCollResponseWrapper;
import io.milvus.Response.GetCollStatResponseWrapper;
import io.milvus.Response.QueryResultsWrapper;
import io.milvus.Response.SearchResultsWrapper;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.*;
import io.milvus.param.collection.*;
import io.milvus.param.control.ManualCompactionParam;
import io.milvus.param.dml.*;
import io.milvus.param.index.*;
import io.milvus.param.partition.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GeneralExample {
    public static final MilvusServiceClient milvusClient;

    static {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost("192.168.190.70")
                .withPort(19530)
                .build();
        milvusClient = new MilvusServiceClient(connectParam);
    }

    public static final String COLLECTION_NAME = "TEST";
    public static final String ID_FIELD = "userID";
    public static final String VECTOR_FIELD = "userFace";
    public static final Integer VECTOR_DIM = 2048;
    public static final String AGE_FIELD = "userAge";
//    public static final String PROFILE_FIELD = "userProfile";
//    public static final Integer BINARY_DIM = 128;

    public static final IndexType INDEX_TYPE = IndexType.IVF_FLAT;
    public static final String INDEX_PARAM = "{\"nlist\":128}";

    public static final Integer SEARCH_K = 5;
    public static final String SEARCH_PARAM = "{\"nprobe\":10}";

    public void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException(r.getMessage());
        }
    }

    public R<RpcStatus> createCollection(long timeoutMiliseconds) {
        System.out.println("========== createCollection() ==========");
        FieldType fieldType1 = FieldType.newBuilder()
                .withName(ID_FIELD)
                .withDescription("user identification")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build();

        FieldType fieldType2 = FieldType.newBuilder()
                .withName(VECTOR_FIELD)
                .withDescription("face embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(VECTOR_DIM)
                .build();

        FieldType fieldType3 = FieldType.newBuilder()
                .withName(AGE_FIELD)
                .withDescription("user age")
                .withDataType(DataType.Int8)
                .build();

//        FieldType fieldType4 = FieldType.newBuilder()
//                .withName(PROFILE_FIELD)
//                .withDescription("user profile")
//                .withDataType(DataType.BinaryVector)
//                .withDimension(BINARY_DIM)
//                .build();

        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withDescription("customer info")
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
//                .addFieldType(fieldType4)
                .build();
        R<RpcStatus> response = milvusClient.withTimeout(timeoutMiliseconds, TimeUnit.MILLISECONDS)
                                            .createCollection(createCollectionReq);
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    /**
     * ????????????
     * @return
     */
    public R<RpcStatus> dropCollection() {
        System.out.println("========== dropCollection() ==========");
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        System.out.println(response);
        return response;
    }

    public R<Boolean> hasCollection() {
        System.out.println("========== hasCollection() ==========");
        R<Boolean> response = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> loadCollection() {
        System.out.println("========== loadCollection() ==========");
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> releaseCollection() {
        System.out.println("========== releaseCollection() ==========");
        R<RpcStatus> response = milvusClient.releaseCollection(ReleaseCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<DescribeCollectionResponse> describeCollection() {
        System.out.println("========== describeCollection() ==========");
        R<DescribeCollectionResponse> response = milvusClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        DescCollResponseWrapper wrapper = new DescCollResponseWrapper(response.getData());
        System.out.println(wrapper.toString());
        return response;
    }

    public R<GetCollectionStatisticsResponse> getCollectionStatistics() {
        System.out.println("========== getCollectionStatistics() ==========");
        R<GetCollectionStatisticsResponse> response = milvusClient.getCollectionStatistics(
                GetCollectionStatisticsParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .build());
        handleResponseStatus(response);
        GetCollStatResponseWrapper wrapper = new GetCollStatResponseWrapper(response.getData());
        System.out.println("Collection row count: " + wrapper.getRowCount());
        return response;
    }

    public R<ShowCollectionsResponse> showCollections() {
        System.out.println("========== showCollections() ==========");
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder()
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> createPartition(String partitionName) {
        System.out.println("========== createPartition() ==========");
        R<RpcStatus> response = milvusClient.createPartition(CreatePartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> dropPartition(String partitionName) {
        System.out.println("========== dropPartition() ==========");
        R<RpcStatus> response = milvusClient.dropPartition(DropPartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<Boolean> hasPartition(String partitionName) {
        System.out.println("========== hasPartition() ==========");
        R<Boolean> response = milvusClient.hasPartition(HasPartitionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> releasePartition(String partitionName) {
        System.out.println("========== releasePartition() ==========");
        R<RpcStatus> response = milvusClient.releasePartitions(ReleasePartitionsParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .addPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<ShowPartitionsResponse> showPartitions() {
        System.out.println("========== showPartitions() ==========");
        R<ShowPartitionsResponse> response = milvusClient.showPartitions(ShowPartitionsParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> createIndex() {
        System.out.println("========== createIndex() ==========");
        R<RpcStatus> response = milvusClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .withIndexType(INDEX_TYPE)
                .withMetricType(MetricType.L2)
                .withExtraParam(INDEX_PARAM)
                .withSyncMode(Boolean.TRUE)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<RpcStatus> dropIndex() {
        System.out.println("========== dropIndex() ==========");
        R<RpcStatus> response = milvusClient.dropIndex(DropIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<DescribeIndexResponse> describeIndex() {
        System.out.println("========== describeIndex() ==========");
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<GetIndexStateResponse> getIndexState() {
        System.out.println("========== getIndexState() ==========");
        R<GetIndexStateResponse> response = milvusClient.getIndexState(GetIndexStateParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<GetIndexBuildProgressResponse> getIndexBuildProgress() {
        System.out.println("========== getIndexBuildProgress() ==========");
        R<GetIndexBuildProgressResponse> response = milvusClient.getIndexBuildProgress(
                GetIndexBuildProgressParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<MutationResult> delete(String partitionName, String expr) {
        System.out.println("========== delete() ==========");
        DeleteParam build = DeleteParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .withExpr(expr)
                .build();
        R<MutationResult> response = milvusClient.delete(build);
        handleResponseStatus(response);
        System.out.println(response.getData());
        return response;
    }

    public R<SearchResults> searchFace(String expr) {
        System.out.println("========== searchFace() ==========");

        List<String> outFields = Collections.singletonList(AGE_FIELD);
        List<List<Float>> vectors = generateFloatVectors(5);

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(MetricType.L2)
                .withOutFields(outFields)
                .withTopK(SEARCH_K)
                .withVectors(vectors)
                .withVectorFieldName(VECTOR_FIELD)
                .withExpr(expr)
                .withParams(SEARCH_PARAM)
                .build();

        R<SearchResults> response = milvusClient.search(searchParam);
        handleResponseStatus(response);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        for (int i = 0; i < vectors.size(); ++i) {
            System.out.println("Search result of No." + i);
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            System.out.println(scores);
            System.out.println("Output field data for No." + i);
            System.out.println(wrapper.getFieldData(AGE_FIELD, i));
        }

        return response;
    }

//    public R<SearchResults> searchProfile(String expr) {
//        System.out.println("========== searchProfile() ==========");
//
//        List<String> outFields = Collections.singletonList(AGE_FIELD);
//        List<ByteBuffer> vectors = generateBinaryVectors(5);
//
//        SearchParam searchParam = SearchParam.newBuilder()
//                .withCollectionName(COLLECTION_NAME)
//                .withMetricType(MetricType.HAMMING)
//                .withOutFields(outFields)
//                .withTopK(SEARCH_K)
//                .withVectors(vectors)
//                .withVectorFieldName(PROFILE_FIELD)
//                .withExpr(expr)
//                .withParams(SEARCH_PARAM)
//                .build();
//
//
//        R<SearchResults> response = milvusClient.search(searchParam);
//        handleResponseStatus(response);
//        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
//        for (int i = 0; i < vectors.size(); ++i) {
//            System.out.println("Search result of No." + i);
//            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
//            System.out.println(scores);
//            System.out.println("Output field data for No." + i);
//            System.out.println(wrapper.getFieldData(AGE_FIELD, i));
//        }
//
//        return response;
//    }

    public R<CalcDistanceResults> calDistance() {
        System.out.println("========== calDistance() ==========");
        Random ran = new Random();
        List<Float> vector1 = new ArrayList<>();
        List<Float> vector2 = new ArrayList<>();
        for (int d = 0; d < VECTOR_DIM; ++d) {
            vector1.add(ran.nextFloat());
            vector2.add(ran.nextFloat());
        }

        CalcDistanceParam calcDistanceParam = CalcDistanceParam.newBuilder()
                .withVectorsLeft(Collections.singletonList(vector1))
                .withVectorsRight(Collections.singletonList(vector2))
                .withMetricType(MetricType.L2)
                .build();
        R<CalcDistanceResults> response = milvusClient.calcDistance(calcDistanceParam);
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public R<QueryResults> query(String expr) {
        System.out.println("========== query() ==========");
        List<String> fields = Arrays.asList(ID_FIELD, AGE_FIELD);
        QueryParam test = QueryParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withExpr(expr)
                .withOutFields(fields)
                .build();
        R<QueryResults> response = milvusClient.query(test);
        handleResponseStatus(response);
        QueryResultsWrapper wrapper = new QueryResultsWrapper(response.getData());
        System.out.println(ID_FIELD + ":" + wrapper.getFieldWrapper(ID_FIELD).getFieldData().toString());
        System.out.println(AGE_FIELD + ":" + wrapper.getFieldWrapper(AGE_FIELD).getFieldData().toString());
        System.out.println("Query row count: " + wrapper.getFieldWrapper(ID_FIELD).getRowCount());
        return response;
    }

    public R<ManualCompactionResponse> compact() {
        System.out.println("========== compact() ==========");
        R<ManualCompactionResponse> response = milvusClient.manualCompaction(ManualCompactionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        return response;
    }

    public R<MutationResult> insert(String partitionName, int count) {
        System.out.println("========== insert() ==========");
        List<List<Float>> vectors = generateFloatVectors(count);
//        List<ByteBuffer> profiles = generateBinaryVectors(count);

        Random ran = new Random();
        List<Integer> ages = new ArrayList<>();
        for (long i = 0L; i < count; ++i) {
            ages.add(ran.nextInt(99));
        }

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field(VECTOR_FIELD, DataType.FloatVector, vectors));
//        fields.add(new InsertParam.Field(PROFILE_FIELD, DataType.BinaryVector, profiles));
        fields.add(new InsertParam.Field(AGE_FIELD, DataType.Int8, ages));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .withFields(fields)
                .build();

        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        return response;
    }

    public List<List<Float>> generateFloatVectors(int count) {
        Random ran = new Random();
        List<List<Float>> vectors = new ArrayList<>();
        for (int n = 0; n < count; ++n) {
            List<Float> vector = new ArrayList<>();
            for (int i = 0; i < VECTOR_DIM; ++i) {
                vector.add(ran.nextFloat());
            }
            vectors.add(vector);
        }

        return vectors;
    }

//    public List<ByteBuffer> generateBinaryVectors(int count) {
//        Random ran = new Random();
//        List<ByteBuffer> vectors = new ArrayList<>();
//        int byteCount = BINARY_DIM/8;
//        for (int n = 0; n < count; ++n) {
//            ByteBuffer vector = ByteBuffer.allocate(byteCount);
//            for (int i = 0; i < byteCount; ++i) {
//                vector.put((byte)ran.nextInt(Byte.MAX_VALUE));
//            }
//            vectors.add(vector);
//        }
//        return vectors;
//    }

    
}