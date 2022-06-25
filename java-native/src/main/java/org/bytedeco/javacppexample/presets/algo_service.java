package org.bytedeco.javacppexample.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

/**
 * @author shenguangyang
 * @date 2022-03-12 15:34
 */
@Properties(
        inherit = {javacpp.class},
        target = "org.bytedeco.javacppexample.jni",
        global = "org.bytedeco.javacppexample.global.algo_service_global",
        value = {
                @Platform(
                        // 如果c++中有智能指针等, 必须在这定义宏, 而不是在c++文件中定义, 否则会出现意向不到的事情
                        // 比如编译报错
                        // 如果你在c++文件中定义 #define SHARED_PTR_NAMESPACE std, 那编译也会报错, 因为javacpp将std
                        // 当初了整形变量, 定义在XxxxGolbal中了

                        // 如果你在c++文件中定义#define SHARED_PTR_NAMESPACE, 有可能编译不报错, 但是很有可能出现内存
                        // 泄露或者程序运行一段时间崩溃的问题
                        define = { "SHARED_PTR_NAMESPACE std", "UNIQUE_PTR_NAMESPACE std" },
                        value = {"linux-x86", "linux-x86_64", "macosx-x86_64", "windows-x86", "windows-x86_64"},
                        include = {"algo_service.h", "algo_request.h", "algo_response.h"},
                        link = { "algo_service"}, linkpath = {"/opt/cpp-demo/lib"}, includepath = { "/opt/cpp-demo/include", "/usr/local/include/opencv4", "/usr/local/include/concurrentqueue/moodycamel"},
                        preloadpath = {"/opt/cpp-demo/lib"}
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
public class algo_service implements InfoMapper {
    // static { Loader.checkVersion("com.concise", "demo"); }
    @Override
    public void map(InfoMap infoMap) {
        infoMap
                .put(new Info("AlgoService::opencv_decode_ptr", "AlgoService::streamUrl", "matToJpeg").skip())
                // 用户传递对象集合数据
                .put(new Info("std::shared_ptr<AlgoResult>")
                        .annotations("@SharedPtr")
                        .valueTypes("@Cast({\"\", \"std::shared_ptr<AlgoResult>\"}) AlgoResult")
                        .pointerTypes("AlgoResult"));

        infoMap.put(new Info("AlgoResult::getData").javaText("public native @StdString String getData();"));

        infoMap.put(new Info("media_data").skip());
        infoMap.put(new Info("std::shared_ptr<MediaData>")
                .annotations("@SharedPtr")
                .valueTypes("@Cast({\"\", \"std::shared_ptr<MediaData>\"}) MediaData")
                .pointerTypes("MediaData"));
        // > > 之间一定要有一个空格, 且要加上.define()
        infoMap.put(new Info("std::vector<std::shared_ptr<MediaData> >").pointerTypes("MediaDataVector").define());
    }
}
