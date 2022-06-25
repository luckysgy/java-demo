package com.demo.mysql_big_data;

import cn.hutool.core.util.RandomUtil;
import com.concise.component.core.utils.id.UUIDUtil;
import com.github.yitter.idgen.YitIdHelper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-05-05 20:40
 */
@SpringBootTest
class MysqlBigDataTest {
    private static final Logger log = LoggerFactory.getLogger(MysqlBigDataTest.class);
    @Resource
    private BigDataMapper bigDataMapper;

    @Test
    public void test1() {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 8000000 * 10; i++) {
            String s = UUIDUtil.randomUUID();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("end: " + (t2 - t1) + " ms");
    }

    /**
     * 一条sql插入多条
     * 通过扩展插入的方式插入数据 insert into big_data values (),(),()
     *
     */
    @Test
    public void testInsertByExtendedInsert() {
        // 分批次循环获得
        int batchSize = 10000;
        int totalSize = 10000000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < totalSize; i += batchSize) {
            List<BigDataPO> list = new ArrayList<>();
            for (int j = 0; j < batchSize; j++) {
                list.add(createBigDataPO());
            }
            long before = System.currentTimeMillis();
            bigDataMapper.batchInsertBigData(list);
            log.debug("count: {}, insert time: {} ms", (i / batchSize), (System.currentTimeMillis() - before));
        }
        log.info("time: {} ms", (System.currentTimeMillis() - t1));
    }

    private BigDataPO createBigDataPO() {
        BigDataPO bigDataPO = new BigDataPO();
        bigDataPO.setId(String.valueOf(YitIdHelper.nextId()));
        bigDataPO.setCreateTime(System.currentTimeMillis());
        bigDataPO.setAvatarUrl(RandomUtil.randomString(100));
        bigDataPO.setUsername(RandomUtil.randomString(100));
        bigDataPO.setPassword(RandomUtil.randomString(100));
        bigDataPO.setNickname(RandomUtil.randomString(16));
        bigDataPO.setHomeAddress(RandomUtil.randomString(100));
        bigDataPO.setEmail(RandomUtil.randomString(24));
        bigDataPO.setPhone(RandomUtil.randomNumbers(13));
        bigDataPO.setIdCode(RandomUtil.randomNumbers(16));
        bigDataPO.setDeleteFlag(RandomUtil.randomInt(1, 2));
        bigDataPO.setSex(RandomUtil.randomInt(1, 2));
        bigDataPO.setStatus(RandomUtil.randomInt(1, 5));
        bigDataPO.setLastLogin(System.currentTimeMillis());
        bigDataPO.setUpdateTime(System.currentTimeMillis());
        bigDataPO.setUserId(String.valueOf(RandomUtil.randomLong(1, 10000)));
        return bigDataPO;
    }
}