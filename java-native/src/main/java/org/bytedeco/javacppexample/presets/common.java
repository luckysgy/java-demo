package org.bytedeco.javacppexample.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

/**
 * @author shenguangyang
 * @date 2022-05-08 13:15
 */
@Properties(
        inherit = {javacpp.class},
        target = "org.bytedeco.javacppexample.jni",
        global = "org.bytedeco.javacppexample.global.common_global",

        value = {
                @Platform(
                        // 如果c++中有智能指针等, 必须在这定义宏, 而不是在c++文件中定义, 否则会出现意向不到的事情
                        // 比如编译报错
                        // 如果你在c++文件中定义 #define SHARED_PTR_NAMESPACE std, 那编译也会报错, 因为javacpp将std
                        // 当初了整形变量, 定义在XxxxGolbal中了

                        // 如果你在c++文件中定义#define SHARED_PTR_NAMESPACE, 有可能编译不报错, 但是很有可能出现内存
                        // 泄露或者程序运行一段时间崩溃的问题
                        define = { "SHARED_PTR_NAMESPACE std", "UNIQUE_PTR_NAMESPACE std"},
                        value = { "linux-arm64", "linux-x86", "linux-x86_64", "macosx-x86_64", "windows-x86", "windows-x86_64"},
                        include = {"media_data.h"}, includepath = {"/opt/cpp-demo/include", "/usr/local/include/opencv4"}
                ),
                @Platform(value = "android", preload = ""),
                @Platform(value = "ios", preload = {"liblibjpeg", "liblibpng", "liblibprotobuf", "liblibwebp", "libzlib", "libopencv_core"}),
                @Platform(value = "linux",        preloadpath = {"/usr/lib/", "/usr/lib32/", "/usr/lib64/"}),
                @Platform(value = "linux-armhf",  preloadpath = {"/usr/arm-linux-gnueabihf/lib/", "/usr/lib/arm-linux-gnueabihf/"}),
                @Platform(value = "linux-arm64",  preloadpath = {"/usr/aarch64-linux-gnu/lib/", "/usr/lib/aarch64-linux-gnu/"}),
                @Platform(value = "linux-x86",    preloadpath = {"/usr/lib32/", "/usr/lib/"}),
                @Platform(value = "linux-x86_64", preloadpath = {"/usr/lib64/", "/usr/lib/"})
        }
)
public class common implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {

    }
}
