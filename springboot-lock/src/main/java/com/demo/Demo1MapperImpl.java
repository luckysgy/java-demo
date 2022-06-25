package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenguangyang
 * @date 2021-12-26 9:12
 */
@Service
public class Demo1MapperImpl implements Demo1Mapper {
    @Override
    public List<Demo1DO> list() {
        List<Demo1DO> demo1DOList = new ArrayList<>();
        Demo1DO demo1DO = new Demo1DO();
        demo1DO.setPassword(UUIDUtil.randomUUID());
        demo1DO.setUsername(UUIDUtil.randomUUID());
        demo1DOList.add(demo1DO);
        return demo1DOList;
    }
}
