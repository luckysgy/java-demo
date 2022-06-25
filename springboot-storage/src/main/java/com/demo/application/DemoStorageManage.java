package com.demo.application;

import com.concise.component.storage.common.registerstoragemanage.StorageManage;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-01-01 13:17
 */
@Component
public class DemoStorageManage implements StorageManage {

    @Override
    public String getObjectNamePre() {
        return "";
    }
}
