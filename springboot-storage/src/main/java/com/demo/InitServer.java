package com.demo;

import com.concise.component.storage.common.service.StorageService;

import com.demo.application.UploadFileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-01-16 7:22
 */
@Component
public class InitServer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitServer.class);
    @Resource
    private StorageService storageService;
    @Resource
    private UploadFileClient uploadFileClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//          uploadFileClient.uploadNotStream();
//        uploadFileClient.upload();
//        for (int i = 0; i < 200; i++) {
//            InputStream fileInputStream = null;
//            try  {
//                fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i));
//                byte[] bytes = IOUtils.toByteArray(fileInputStream);
//                String encode = Base64Util.encode(bytes);
//
//                long s1 = System.currentTimeMillis();
//                byte[] decode = Base64Util.decode(encode);
//                InputStream inputStream = new ByteArrayInputStream(decode);
//                storageService.uploadFile(DemoStorageBucket.class, inputStream, "image/jpeg", "1.jpg");
//                long e1 = System.currentTimeMillis();
//
//                log.info("test\t" + (e1 - s1));
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
////                FileUtils.deleteFile(filePath + "/" + fileName);
////                if (ObjectUtil.isNotNull(fileInputStream1)) {
////                    try {
////                        fileInputStream1.close();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
//                if (ObjectUtil.isNotNull(fileInputStream)) {
//                    fileInputStream.close();
//                }
//            }
//        }
    }
}
