package com.demo.application;

import com.concise.component.storage.common.registerstoragemanage.StorageManage;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-02-26 10:26
 */
@Component
public class FilePathListStorageManage implements StorageManage {
    @Override
    public String getObjectNamePre() {
        return "";
    }
}
