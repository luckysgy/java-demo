package com.demo.mysql_big_data;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-05-05 21:06
 */
@Mapper
public interface BigDataMapper extends BaseMapper<BigDataPO> {
    int batchInsertBigData(List<BigDataPO> list);
}
