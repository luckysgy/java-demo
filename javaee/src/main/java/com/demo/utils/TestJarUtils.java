package com.demo.utils;

import com.concise.component.core.utils.file.JarUtils;
import com.concise.component.core.test.data1.DataPackageMark;

/**
 * @author shenguangyang
 * @date 2022-03-26 16:38
 */
public class TestJarUtils {

    public static void copyDir() {
//        JarUtils.copyDataFromClasses("/temp/jar-file-data", BaseDataPackageMark.class);
         JarUtils.copyDataFromLib("component-util-file-1.0.0.jar", "/temp/jar-file-data", DataPackageMark.class);
    }
}
