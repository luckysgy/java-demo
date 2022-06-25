package com.demo.application;

import com.concise.component.storage.common.service.StorageService;
import com.concise.component.storage.common.url.UrlTypesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-01-01 13:16
 */
@Service
public class DemoStorageService {
    @Autowired
    private StorageService storageService;

    public void upload(String filename) throws Exception {
        storageService.uploadFile(DemoStorageManage.class, new FileInputStream(filename),
                "image/jpeg", "test.jpg");
    }

    public void getFilePathList() {
        List<String> filePathList = storageService.listFilePath(FilePathListStorageManage.class, "software-package/");
        for (String filePath : filePathList) {
            System.out.println(filePath);
        }
    }

    public InputStream getFile() {
        return storageService.getFile(DemoStorageManage.class, "test.jpg");
    }

    public void deleteObjects() throws Exception {
        List<String> objectNames = new ArrayList<>();
        objectNames.add("test.jpg");
        objectNames.add("copy1.jpg");
        storageService.deleteObjects(DemoStorageManage.class, objectNames);
    }

    public void deleteObject() throws Exception {
        storageService.deleteObject(DemoStorageManage.class, "copy2.jpg");
    }

    public void uploadDir() {
        storageService.uploadDir(DemoStorageManage.class, "/code/my/springboot-demo/demo-ui/dist");
    }

    public void getUrl() {
        System.out.println(storageService.getPresignedObjectUrl(DemoStorageManage.class, "favicon.ico", UrlTypesEnum.INTERNAL));
        System.out.println(storageService.getPermanentObjectUrl(DemoStorageManage.class, "favicon.ico", UrlTypesEnum.INTERNAL));
    }
}
