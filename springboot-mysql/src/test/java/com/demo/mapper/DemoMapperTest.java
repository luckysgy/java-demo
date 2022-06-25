package com.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.concise.component.core.utils.id.UUIDUtil;
import com.demo.entity.DemoPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-03-29 20:32
 */
@SpringBootTest
class DemoMapperTest {
    @Resource
    private DemoMapper demoMapper;

    @Test
    public void save() {
        DemoPO demoPO = new DemoPO();
        demoPO.setName(UUIDUtil.randomUUID());
        demoMapper.insert(demoPO);

        IPage<DemoPO> demoPOPage = demoMapper.selectPage(new Page<>(1, 5), new QueryWrapper<>());
    }

    @Test
    public void findById() {
        demoMapper.selectById("1508785174318833674");
    }
}