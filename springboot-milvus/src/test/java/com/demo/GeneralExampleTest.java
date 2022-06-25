package com.demo;

import io.milvus.Response.MutationResultWrapper;
import io.milvus.grpc.MutationResult;
import io.milvus.param.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.demo.GeneralExample.AGE_FIELD;

/**
 * @author shenguangyang
 * @date 2022-01-17 21:21
 */
class GeneralExampleTest {
    public static void main(String[] args) {
        GeneralExample example = new GeneralExample();

        example.dropCollection();
        example.createCollection(2000);
        example.hasCollection();
        example.describeCollection();
        example.showCollections();
        example.loadCollection();

        final String partitionName = "p1";
        example.createPartition(partitionName);
        example.hasPartition(partitionName);
        example.showPartitions();

        final int row_count = 100;
        List<Long> deleteIds = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < 100; ++i) {
            R<MutationResult> result = example.insert(partitionName, row_count);
            MutationResultWrapper wrapper = new MutationResultWrapper(result.getData());
            List<Long> ids = wrapper.getLongIDs();
            deleteIds.add(ids.get(ran.nextInt(row_count)));
        }
        example.getCollectionStatistics();

        example.createIndex();
        example.describeIndex();
        example.getIndexBuildProgress();
        example.getIndexState();

        String deleteExpr = GeneralExample.ID_FIELD + " in " + deleteIds.toString();
        example.delete(partitionName, deleteExpr);
        String queryExpr = AGE_FIELD + " == 60";
        example.query(queryExpr);
        String searchExpr = AGE_FIELD + " > 50";
        example.searchFace(searchExpr);
//        searchExpr = AGE_FIELD + " <= 30";
//        example.searchProfile(searchExpr);
        example.calDistance();
        example.compact();
        example.getCollectionStatistics();

//        example.releasePartition(partitionName); // releasing partitions after loading collection is not supported currently
        example.releaseCollection();
        example.dropPartition(partitionName);
        example.dropIndex();
        example.dropCollection();
    }
}