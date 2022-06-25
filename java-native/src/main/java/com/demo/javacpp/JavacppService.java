package com.demo.javacpp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacppexample.jni.JavaCppExample;
import org.bytedeco.javacppexample.jni.MediaData;
import org.bytedeco.javacppexample.jni.StringVector;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * javac -cp javacpp-1.5.7.jar com/concise/demo/javacpp/MyFunc.java
 * javacpp框架测试, 当频繁传输图片数据时候, 发现内存占用比较高
 * @author shenguangyang
 * @date 2022-04-17 13:21
 */
public class JavacppService {
    private static final String IMAGE_PATH_PRE = "/mnt/project/javacpp-native/images";
    static ExecutorService threadPool = Executors.newFixedThreadPool(30);

    public static void demo1() {
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        System.out.println(javaCppExample.demo1(new String("我是神".getBytes(), StandardCharsets.UTF_8)));
        System.out.println(javaCppExample.demo1("123123123"));
        javaCppExample.deallocate();
    }

    public static void demo2() {
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        System.out.println("=========== 传输字符串到c++容器中 ===========");
        StringVector stringVector = new StringVector();
        for (int i = 0; i < 10; i++) {
            System.out.println("count: " + i);
            stringVector.push_back(UUID.randomUUID().toString());
        }
        // 获取字符串容器中的数据
        System.out.println("\n\n=========== (java from c++) 获取字符串容器中的数据 ===========");
        BytePointer[] bytePointers = stringVector.get();
        for (BytePointer bytePointer : bytePointers) {
            System.out.println("vector = " + bytePointer.getString(StandardCharsets.UTF_8));
        }

        System.out.println("\n\n===========  (java to c++) 传输自定义对象容器(容器只包含一个对象) invoke =========== ");
        javaCppExample.demo2(stringVector);
        stringVector.deallocate();

        javaCppExample.deallocate();
    }

//    /**
//     * 传递集合数据 === 集合中是对象, jni调用采用多线程运行, 很有可能崩溃
//     * 猜测是std::vector<std::shared_ptr<ImageBuffer>>中的ImageBuffer有嵌套了一个shared_ptr属性缘故
//     */
//    public static void demo3() throws InterruptedException {
//        System.out.println("\n\n=========== 传输自定义对象容器(容器包含多个对象) invoke1 ===========");
//        TimeUnit.SECONDS.sleep(5);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 50000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            threadPool.execute(() -> {
//                try {
//                    JavaCppExample exampleForInvoke1 = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//                    ImageBufferVector imageBufferVector = new ImageBufferVector();
//                    List<BytePointer> bytePointerList = new ArrayList<>();
//                    for (int j = 0; j < 20; j++) {
//                        byte[] newBytes = bytes.clone();
//                        BytePointer bytePointer = new BytePointer(newBytes);
//                        // 在ImageDataTrans 构造器中, 如果直接传java中的byte[], 则会导致cpp中不能正确的读取到图片,
//                        // 我在测试的时候, 往本地写入图片数据, 发现图片打开是失败的
//
//                        // 当我使用了javacpp中提供的 new BytePointer(), 作为参数传入到构造器时, 在c++接收到的图片数据是可以正确的
//                        // 写入到本地并可以打开图片
//
//                        // 注意: BytePointer需要进行手动释放, 否则会有内存溢出的风险
//
//                        // 因此, java在与jni交互的时候最好, 使用javacpp提供的一套, 比如 Pointer类的子类 (BytePointer就是其中的子类)
//                        ImageBuffer imageTrans = new ImageBuffer(bytePointer, newBytes.length);
//                        imageBufferVector.push_back(imageTrans);
//                        bytePointerList.add(bytePointer);
//                    }
//                    for (BytePointer bytePointer : bytePointerList) {
//                        System.out.println("deallocate bytePointer");
//                        bytePointer.deallocate();
//                    }
//
//                    exampleForInvoke1.demo3(imageBufferVector);
//                    imageBufferVector.deallocate();
//                    System.out.println("deallocate imageBufferVector");
//
//                    exampleForInvoke1.deallocate();
//                    System.out.println("deallocate JavaCppExample");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//    }

//    public static void demo4() throws InterruptedException {
//        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        System.out.println("\n\n=========== (java to c++) 传输自定义对象 invoke2 ===========");
//        TimeUnit.SECONDS.sleep(5);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 100000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    BytePointer bytePointer = new BytePointer(bytes);
//                    ImageBuffer imageData = new ImageBuffer(bytePointer, bytes.length);
//                    javaCppExample.invoke2(imageData);
//                    imageData.deallocate();
//                    bytePointer.deallocate();
//                    System.out.println("invoke2 ---- " + finalI);
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//        javaCppExample.deallocate();
//    }

//    public static void demo5() throws InterruptedException {
//        System.out.println("\n\n=========== 传输自定义对象容器(容器包含多个对象) invoke3 ===========");
//        TimeUnit.SECONDS.sleep(5);
//        JavaCppExample exampleForInvoke1 = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 100000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            threadPool.execute(() -> {
//                try {
//                    ImageBufferVector1 imageBufferVector = new ImageBufferVector1();
//                    List<BytePointer> bytePointerList = new ArrayList<>();
//                    for (int j = 0; j < 20; j++) {
//                        BytePointer bytePointer = new BytePointer(bytes);
//                        // 在ImageDataTrans 构造器中, 如果直接传java中的byte[], 则会导致cpp中不能正确的读取到图片,
//                        // 我在测试的时候, 往本地写入图片数据, 发现图片打开是失败的
//
//                        // 当我使用了javacpp中提供的 new BytePointer(), 作为参数传入到构造器时, 在c++接收到的图片数据是可以正确的
//                        // 写入到本地并可以打开图片
//
//                        // 注意: BytePointer需要进行手动释放, 否则会有内存溢出的风险
//
//                        // 因此, java在与jni交互的时候最好, 使用javacpp提供的一套, 比如 Pointer类的子类 (BytePointer就是其中的子类)
//                        ImageBuffer imageTrans = new ImageBuffer(bytePointer, bytes.length);
//                        imageBufferVector.push_back(imageTrans);
//                        bytePointerList.add(bytePointer);
//                    }
//                    for (BytePointer bytePointer : bytePointerList) {
//                        System.out.println("deallocate bytePointer");
//                        bytePointer.deallocate();
//                    }
//
//                    exampleForInvoke1.invoke3(imageBufferVector);
//                    imageBufferVector.deallocate();
//                    System.out.println("deallocate imageBufferVector");
//
//                    // exampleForInvoke1.deallocate();
//                    // System.out.println("deallocate JavaCppExample");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//    }

//    public static void demo6() throws InterruptedException {
//        System.out.println("\n\n=========== 传输自定义对象容器(容器包含多个对象) invoke4 ===========");
//        JavaCppExample exampleForInvoke1 = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        TimeUnit.SECONDS.sleep(3);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 2000000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    ImageBufferPlusVector imageBufferVector = new ImageBufferPlusVector();
//                    List<BytePointer> bytePointerList = new ArrayList<>();
//                    for (int j = 0; j < 20; j++) {
//                        byte[] data = new byte[bytes.length];
//                        BytePointer bytePointer = new BytePointer(data);
//                        // 在ImageDataTrans 构造器中, 如果直接传java中的byte[], 则会导致cpp中不能正确的读取到图片,
//                        // 我在测试的时候, 往本地写入图片数据, 发现图片打开是失败的
//
//                        // 当我使用了javacpp中提供的 new BytePointer(), 作为参数传入到构造器时, 在c++接收到的图片数据是可以正确的
//                        // 写入到本地并可以打开图片
//
//                        // 注意: BytePointer需要进行手动释放, 否则会有内存溢出的风险
//
//                        // 因此, java在与jni交互的时候最好, 使用javacpp提供的一套, 比如 Pointer类的子类 (BytePointer就是其中的子类)
//                        ImageBufferPlus imageBufferPlus = new ImageBufferPlus(bytePointer, data.length);
//                        imageBufferVector.push_back(imageBufferPlus);
////                        bytePointerList.add(bytePointer);
//                    }
////                    for (BytePointer bytePointer : bytePointerList) {
////                        bytePointer.releaseReference();
////                    }
//                    imageBufferVector.close();
//
//                    //exampleForInvoke1.invoke4(imageBufferVector);
//                    System.out.println("java invoke4, count: " + finalI);
//                    // System.out.println("deallocate JavaCppExample");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//    }

//    public static void invoke5() throws InterruptedException {
//        System.out.println("\n\n=========== invoke5 ===========");
//        TimeUnit.SECONDS.sleep(5);
//        JavaCppExample exampleForInvoke1 = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 200000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    ImageBufferPlusVector2 imageBufferVector = new ImageBufferPlusVector2();
//                    List<ImageBufferPlus> bytePointerList = new ArrayList<>();
//                    for (int j = 0; j < 20; j++) {
//                        BytePointer bytePointer = new BytePointer(bytes);
//                        // 在ImageDataTrans 构造器中, 如果直接传java中的byte[], 则会导致cpp中不能正确的读取到图片,
//                        // 我在测试的时候, 往本地写入图片数据, 发现图片打开是失败的
//
//                        // 当我使用了javacpp中提供的 new BytePointer(), 作为参数传入到构造器时, 在c++接收到的图片数据是可以正确的
//                        // 写入到本地并可以打开图片
//
//                        // 注意: BytePointer需要进行手动释放, 否则会有内存溢出的风险
//
//                        // 因此, java在与jni交互的时候最好, 使用javacpp提供的一套, 比如 Pointer类的子类 (BytePointer就是其中的子类)
//                        ImageBufferPlus imageBufferPlus = new ImageBufferPlus(bytePointer, bytes.length);
//                        imageBufferVector.push_back(imageBufferPlus);
//                        bytePointerList.add(imageBufferPlus);
//                    }
//                    for (ImageBufferPlus bytePointer : bytePointerList) {
//                        bytePointer.deallocate();
//                    }
//
//                    exampleForInvoke1.invoke5(imageBufferVector);
//                    imageBufferVector.deallocate();
//                    System.out.println("java invoke4, count: " + finalI);
//
//                    // exampleForInvoke1.deallocate();
//                    // System.out.println("deallocate JavaCppExample");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//    }
//
//
//    public static void invoke6() throws InterruptedException {
//        System.out.println("\n\n=========== invoke6 ===========");
//        TimeUnit.SECONDS.sleep(5);
//        JavaCppExample exampleForInvoke1 = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        byte[] bytes = FileUtil.readBytes(new File("/mnt/project/javacpp-native/demo.jpg"));
//        int count = 20000000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    ByteVectorVector byteVectorVector = new ByteVectorVector();
////                    List<BytePointer> bytePointerList = new ArrayList<>();
//                    for (int j = 0; j < 10; j++) {
////                        BytePointer bytePointer = new BytePointer(bytes);
//                        // 在ImageDataTrans 构造器中, 如果直接传java中的byte[], 则会导致cpp中不能正确的读取到图片,
//                        // 我在测试的时候, 往本地写入图片数据, 发现图片打开是失败的
//
//                        // 当我使用了javacpp中提供的 new BytePointer(), 作为参数传入到构造器时, 在c++接收到的图片数据是可以正确的
//                        // 写入到本地并可以打开图片
//
//                        // 注意: BytePointer需要进行手动释放, 否则会有内存溢出的风险
//
//                        // 因此, java在与jni交互的时候最好, 使用javacpp提供的一套, 比如 Pointer类的子类 (BytePointer就是其中的子类)
////                        ImageBufferPlus imageBufferPlus = new ImageBufferPlus(bytePointer, bytes.length);
//                        byteVectorVector.put(bytes);
////                        bytePointerList.add(bytePointer);
//                    }
////                    for (BytePointer bytePointer : bytePointerList) {
////                        bytePointer.deallocate();
////                    }
//
//                    exampleForInvoke1.invoke6(byteVectorVector);
//                    byteVectorVector.deallocate();
//                    System.out.println("java invoke6, count: " + finalI);
//
//                    // exampleForInvoke1.deallocate();
//                    // System.out.println("deallocate JavaCppExample");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//    }

//    public static void getImageData() throws InterruptedException {
//        System.out.println("\n\n=========== (java from c++) 获取图片数据 getImageData ===========");
//        TimeUnit.SECONDS.sleep(3);
//        int count = 10000;
//        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    ImageBuffer imageData = javaCppExample.getImageData();
//                    String imagePath = IMAGE_PATH_PRE + "/getImageData-" + finalI % 10 + ".jpg";
//                    if (imageData != null) {
//                        System.out.println("getImageData, count: " + finalI + ", length: " + imageData.length());
//                        byte[] data = new byte[(int) imageData.length()];
//                        imageData.data().get(data);
//                        FileUtil.writeBytes(data, imagePath);
//                        imageData.data().close();
//                        imageData.data().deallocate();
//                        imageData.close();
//                        imageData.deallocate();
//                    }
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//        javaCppExample.deallocate();
//    }

    /**
     * 将c++的mat转为jpeg图片指针数据, 返回给java
     */
    public static void demo10() throws Exception {
        System.out.println("\n\n=========== (java from c++) 获取图片数据 demo10 ===========");
        TimeUnit.SECONDS.sleep(3);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                while (true) {
                    long count = 1;
                    JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
                    while (count < 1000) {
                        MediaData mediaData = javaCppExample.demo10();
                        BytePointer imagePointer = null;
                        try {
                            count++;
                            if (mediaData != null && count % 100 == 0) {
                                String imagePath = IMAGE_PATH_PRE + "/demo10-" + finalI % 10 + ".jpg";
                                System.out.println("demo10, id: " + finalI + ", count: " + count + ", length: " + mediaData.get_length());
                                byte[] data = new byte[(int) mediaData.get_length()];
                                imagePointer = mediaData.get_data();
                                imagePointer.get(data, 0, (int) mediaData.get_length());
                                FileUtil.writeBytes(data, imagePath);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (mediaData != null) {
                                if (imagePointer != null) {
                                    imagePointer.close();
                                    imagePointer.deallocate();
                                }
                                mediaData.close();
                                mediaData.deallocate();
                            }
                        }
                    }
                    javaCppExample.releaseReference();
                    Console.log("releaseReference, id: {}", finalI);
                }
            });
        }
    }

//    public static void getImageBufferPlusFromLocalImage() throws InterruptedException {
//        System.out.println("\n\n=========== (java from c++) 获取图片数据 getImageBufferPlusFromLocalImage ===========");
//        TimeUnit.SECONDS.sleep(3);
//        int count = 10000;
//        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            int finalI = i;
//            threadPool.execute(() -> {
//                try {
//                    ImageBufferPlus imageDataPlus = javaCppExample.getImageBufferPlusFromLocalImage();
//                    String imagePath = IMAGE_PATH_PRE + "/getImageBufferPlusFromLocalImage-" + finalI % 10 + ".jpg";
//                    if (imageDataPlus != null) {
//                        System.out.println("getImageBufferPlusFromLocalImage, count: " + finalI + ", length: " + imageDataPlus.getLength());
//                        byte[] data = new byte[(int) imageDataPlus.getLength()];
//                        imageDataPlus.getData().get(data);
//                        FileUtil.writeBytes(data, imagePath);
//                        imageDataPlus.getData().close();
//                        imageDataPlus.getData().deallocate();
//                        imageDataPlus.close();
//                        imageDataPlus.deallocate();
//                    }
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//        javaCppExample.deallocate();
//    }

    /**
     * 内部启动一个线程调用本类中的某个函数 (demo12Thread)
     * 验证java手动销毁该类会有什么问题发生, 这里睡眠5s, demo12内部启动的线程循环20s结束
     *
     * 没有任何问题, 依旧可以正常运行
     */
    public static void demo12() throws Exception {
        System.out.println("======================================>> demo12");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo12();
        TimeUnit.SECONDS.sleep(5);
        javaCppExample.releaseReference();
        // javaCppExample.deallocate();
        System.out.println("demo12 end");
    }

    /**
     * 内部先定义函数std::function, 然后将javacpp_example1类中的某个函数与其进行绑定
     * 然后启动一个线程不断的回调javacpp_example1中的对应的函数, 在java端手动释放javacpp_example1
     * 对象, 看看会发生什么问题
     *
     * 会导致java虚拟机崩溃
     */
    public static void demo13() throws Exception {
        System.out.println("======================================>> demo13");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo13();
        TimeUnit.SECONDS.sleep(5);
//        while (true) {
//            System.out.println("demo13 ===> referenceCount: " + javaCppExample.referenceCount());
//            TimeUnit.SECONDS.sleep(2);
//        }
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo13 end");
    }

    /**
     * 解决demo13中的问题, 在demo13Solve函数中重新创建当前类, 然后插入到静态Map结合中
     * 在java中, 调用demo13SolveClose函数从map中移除JavaCppExample类
     *
     * 原理是依靠c++的智能指针自动释放能力, 当线程结束之后会自动释放对象
     */
    public static void demo13Solve() throws Exception {
        System.out.println("======================================>> demo13Solve");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo13Solve();
        TimeUnit.SECONDS.sleep(5);
        javaCppExample.demo13SolveClose();
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo13Solve end");
    }

    /**
     * 功能: 如果java调用demo14且内部创建一个JavaCppExample1对象并将其绑定到demo14_javacpp_example1属性上,
     * 然后启动一个线程并将其传入, java端在线程还结束的时候销毁当前对象, 是否会发生问题
     *
     * 结论: 测试通过
     */
    public static void demo14() throws Exception {
        System.out.println("======================================>> demo14");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo14();

        System.out.println("add 100 to count");
        javaCppExample.demo14UpdateThreadCount(100);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("add 100 to count");
        javaCppExample.demo14UpdateThreadCount(100);
        TimeUnit.SECONDS.sleep(2);

        javaCppExample.demo14PrintThreadCount();
        TimeUnit.SECONDS.sleep(2);
        javaCppExample.demo14PrintThreadCount();

        TimeUnit.SECONDS.sleep(5);
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo14 end");
    }

    /**
     * 功能: 调用 javacpp_example1 中的 demo15 函数,JavaCppExample1#demo15, 内部先绑定
     * demo15Callback函数, 然后启动一个线程不断的回调, 在运行一小会之后, java主动释放当前类
     *
     * @throws Exception
     */
    public static void demo15() throws Exception {
        System.out.println("======================================>> demo15");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo15();
        TimeUnit.SECONDS.sleep(5);
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo15 end");
    }

    /**
     * 功能: demo17内部启动一个线程调用javacpp_example1中的一个函数, 并传递javacpp_example_data
     * 类数据, 以及将javacpp_example_data类中的std::funtion绑定到当前类中的demo17Callback_1中
     * javacpp_example1#demo17 内部会进行循环操作
     * 在javacpp_example1#demo17循环过程中, 在java端销毁本类看是否会崩溃
     */
    public static void demo17() throws Exception {
        System.out.println("======================================>> demo17");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo17();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 0; i < 10; i++) {
            javaCppExample.demo17PrintfJavaCppExampleData();
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 10; i++) {
            javaCppExample.demo17UpdateJavaCppExampleData();
        }
        TimeUnit.SECONDS.sleep(2);
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo17 end");
    }

    /**
     * 功能: 与demo17的区别是在回调中调用其他对象中函数
     *
     */
    public static void demo18() throws Exception {
        System.out.println("======================================>> demo18");
        TimeUnit.SECONDS.sleep(2);
        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
        javaCppExample.demo18();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("releaseReference");
        javaCppExample.releaseReference();
        System.out.println("======================================>> demo18 end");
    }
//
//    public static void callback1() throws InterruptedException {
//        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34) {
//            private final Logger log = LoggerFactory.getLogger(JniApplication.class);
//            private final AtomicInteger count = new AtomicInteger(0);
//            /**
//             * c++ 回调java
//             */
//            @Override
//            public void callback1(BytePointer data, int length) {
//                byte[] bytes = new byte[length];
//                data.get(bytes, 0, length);
//                log.info("callback1, count: {}, length: {}", count.get(), length);
//                FileUtil.writeBytes(bytes, IMAGE_PATH_PRE + "/java-callback1-" + count.incrementAndGet() + ".jpg");
//                data.deallocate();
//            }
//        };
//
//        System.out.println("\n\n=========== 启动c++回调java(参数为图片指针和长度) startCallback1 ===========");
//        javaCppExample.startCallback1();
//
//        TimeUnit.SECONDS.sleep(10);
//        // 正常要先进行停止(自定义native停止方法),再销毁
//        // javaCppExample.deallocate();
//    }
//
//    public static void callback2() throws InterruptedException {
//        JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34) {
//            private final Logger log = LoggerFactory.getLogger(JniApplication.class);
//            private final AtomicInteger count = new AtomicInteger(0);
//
//            @Override
//            public void callback2(ImageBuffer data) {
//                int length = (int) data.length();
//                byte[] bytes = new byte[length];
//                data.data().get(bytes, 0, length);
//                log.info("callback2, count: {}, length: {}", count.get(), length);
//                FileUtil.writeBytes(bytes, IMAGE_PATH_PRE + "/java-callback2-" + count.incrementAndGet() + ".jpg");
//                data.deallocate();
//            }
//        };
//
//        System.out.println("\n\n=========== 启动c++回调java(参数为c++对象类) startCallback2 ===========");
//        javaCppExample.startCallback2();
//        TimeUnit.SECONDS.sleep(10);
//        // 正常要先进行停止(自定义native停止方法),再销毁
//        // javaCppExample.deallocate();
//    }
}
