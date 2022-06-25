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
        global = "org.bytedeco.javacppexample.global.javacpp_example_global",
        value = {
                @Platform(
                        // 如果c++中有智能指针等, 必须在这定义宏, 而不是在c++文件中定义, 否则会出现意向不到的事情
                        // 比如编译报错
                        // 如果你在c++文件中定义 #define SHARED_PTR_NAMESPACE std, 那编译也会报错, 因为javacpp将std
                        // 当初了整形变量, 定义在XxxxGolbal中了

                        // 如果你在c++文件中定义#define SHARED_PTR_NAMESPACE, 有可能编译不报错, 但是很有可能出现内存
                        // 泄露或者程序运行一段时间崩溃的问题
                        define = { "SHARED_PTR_NAMESPACE std", "UNIQUE_PTR_NAMESPACE std" },
                        value = { "linux-arm64", "linux-x86", "linux-x86_64", "macosx-x86_64", "windows-x86", "windows-x86_64"},
                        include = {"javacpp_example.h"}, link = { "javacpp_example"}, preloadpath = {"/opt/cpp-demo/lib"}, linkpath = {"/opt/cpp-demo/lib"},
                        includepath = { "/opt/cpp-demo/include", "/usr/local/include/opencv4"}
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
public class javacpp_example implements InfoMapper {
    // static { Loader.checkVersion("com.concise", "demo"); }
    @Override
    public void map(InfoMap infoMap) {
        infoMap
                .put(new Info("JavaCppExample::javacppDemo", "JavaCppExample::frame",
                        "JavaCppExample::demo13_solve_function", "JavaCppExample::demo13_solve_javacpp_example1",
                        "JavaCppExample::demo13SolveThread", "JavaCppExample::demo14_javacpp_example1",
                        "JavaCppExample::demo14Thread", "JavaCppExample::demo15Callback", "JavaCppExample::demo15_javacpp_example1",
                        "JavaCppExample::demo15_javacpp_example1", "JavaCppExample::demo16Thread",
                        "JavaCppExample::demo16_javacpp_example1", "JavaCppExample::demo17_javacpp_example_data",
                        "JavaCppExample::demo17_javacpp_example1", "JavaCppExample::demo17Callback_1", "JavaCppExample::demo17Callback_2",
                        "JavaCppExample::demo18_javacpp_example_data", "JavaCppExample::demo18_javacpp_example1",
                        "JavaCppExample::demo18_javacpp_example2", "JavaCppExample::demo18Callback_1").skip())
                .put(new Info("std::enable_shared_from_this<JavaCppExample>").pointerTypes("Pointer"))
                // .put(new Info("std::shared_ptr<ImageTrans>").valueTypes("ImageTrans"))
                // 1. 如果std::vector中嵌套 std::vector 或者 std::shared_ptr, 最后一个>前要空一格, 否则底层解析会报错
                // 错误示范: std::vector<std::shared_ptr<ImageTrans>>
                // 正确: std::vector<std::shared_ptr<ImageTrans> >
                // 2. std::vector嵌套std::vector或者std::shared_ptr, 则需要单独put一下new Info,
                // cppNams = std::shared_ptr<ImageTrans>, pointerTypes指定容器元素类型
                // 也即是说: javacpp每次只能处理一个嵌套层级std::vector<...> ,我估计内部是通过循环检查来实现的
                // .put(new Info("std::shared_ptr<ImageBuffer>").pointerTypes("ImageBuffer").define())

                // 用户传递对象集合数据
                .put(new Info("std::shared_ptr<MediaData>")
                        .annotations("@SharedPtr")
                        .valueTypes("@Cast({\"\", \"std::shared_ptr<MediaData>\"}) MediaData")
                        .pointerTypes("MediaData"))

                // .put(new Info("std::shared_ptr<ImageBuffer>&")
                //         .annotations("@UniquePtr", "@Const")
                //         .valueTypes("@Cast({\"\", \"std::shared_ptr<ImageBuffer>&\"}) ImageBuffer")
                //         .pointerTypes("ImageBuffer"))

                // > > 之间一定要有一个空格, 且要加上.define()
                .put(new Info("std::vector<std::shared_ptr<MediaData> >").pointerTypes("MediaDataVector").define())
                // .put(new Info("std::vector<MediaData>").pointerTypes("ImageBufferVector1").define())

                .put(new Info("std::shared_ptr<MediaData>")
                        .annotations("@SharedPtr")
                        .valueTypes("@Cast({\"\", \"std::shared_ptr<MediaData>\"}) MediaData")
                        .pointerTypes("MediaData"))

                .put(new Info("std::vector<std::vector<uint8_t> >").pointerTypes("ByteVectorVector").define())
                // .put(new Info("std::vector<ImageBuffer>").pointerTypes("ImageBufferVector2").define())
                // 用于传递字符串集合数据
                .put(new Info("std::vector<std::string>").pointerTypes("StringVector").define())

                // .put(new Info("JavaCppExample::getImageData").javaText(
                //         "public native @ByVal ImageBuffer getImageData();"))

                // java methods, 用于java和c++互相回调
                .put(new Info("JavaCppExample::callback1").javaText(
                        "@Virtual public native void callback1(@Cast(\"uint8_t*\") BytePointer img, int length);"))
                .put(new Info("JavaCppExample::callback2").javaText(
                        "@Virtual public native void callback2(@SharedPtr @Cast({\"\", \"std::shared_ptr<ImageBuffer>\"}) ImageBuffer data);"));
    }
}
