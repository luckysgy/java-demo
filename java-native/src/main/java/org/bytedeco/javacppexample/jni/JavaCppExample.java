// Targeted by JavaCPP version 1.5.7: DO NOT EDIT THIS FILE

package org.bytedeco.javacppexample.jni;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

import java.nio.ByteBuffer;


/**
 * \brief shared_from_this() 返回一个当前类的std::share_ptr,
 *
 */
@NoOffset @Properties(inherit = org.bytedeco.javacppexample.presets.javacpp_example.class)
public class JavaCppExample extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public JavaCppExample(Pointer p) { super(p); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public JavaCppExample(long size) { super((Pointer)null); allocateArray(size); }
    private native void allocateArray(long size);
    @Override public JavaCppExample position(long position) {
        return (JavaCppExample)super.position(position);
    }
    @Override public JavaCppExample getPointer(long i) {
        return new JavaCppExample((Pointer)this).offsetAddress(i);
    }

    public native @Cast("char*") BytePointer name(); public native JavaCppExample name(BytePointer setter);
    public native @StdString BytePointer data(); public native JavaCppExample data(BytePointer setter);
    public native int age(); public native JavaCppExample age(int setter);
    public JavaCppExample() { super((Pointer)null); allocate(); }
    private native void allocate();
    public JavaCppExample(@Cast("char*") BytePointer name, @StdString BytePointer data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") BytePointer name, @StdString BytePointer data, int age);
    public JavaCppExample(@Cast("char*") ByteBuffer name, @StdString String data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") ByteBuffer name, @StdString String data, int age);
    public JavaCppExample(@Cast("char*") byte[] name, @StdString BytePointer data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") byte[] name, @StdString BytePointer data, int age);
    public JavaCppExample(@Cast("char*") BytePointer name, @StdString String data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") BytePointer name, @StdString String data, int age);
    public JavaCppExample(@Cast("char*") ByteBuffer name, @StdString BytePointer data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") ByteBuffer name, @StdString BytePointer data, int age);
    public JavaCppExample(@Cast("char*") byte[] name, @StdString String data, int age) { super((Pointer)null); allocate(name, data, age); }
    private native void allocate(@Cast("char*") byte[] name, @StdString String data, int age);

    // 对于回调函数, 一定要定义virtual析构函数, 否则派生类对象销毁不完整

    public native @StdString BytePointer demo1(@StdString BytePointer say);
    public native @StdString String demo1(@StdString String say);

    // 传递集合数据
    public native @StdString BytePointer demo2(@ByVal StringVector data);

    // 传递集合数据 === 集合中是对象, jni调用采用多线程运行, 很有可能崩溃
    // 猜测是std::vector<std::shared_ptr<ImageBuffer>>中的ImageBuffer有嵌套了一个shared_ptr属性缘故
    public native @StdString BytePointer demo3(@ByVal MediaDataVector data);

    public native @StdString BytePointer demo4(@SharedPtr @Cast({"", "std::shared_ptr<MediaData>"}) MediaData data);

    // std::string demo5(std::vector<ImageBuffer> data);

    // // 会出现 double free or corruption (out) 问题
    // std::string demo6(std::vector<std::shared_ptr<ImageBuffer>>& data);
    // // 有问题
    // std::string demo7(std::shared_ptr<std::vector<ImageBuffer>> data);

    // // (推荐) 这种可行, 没有内存泄漏问题, 也没有释放野指针和重复释放问题, 但是相对invoke4会慢, 但是猜测这是正常的
    // std::string demo8(std::vector<std::vector<uint8_t>> data);

    // // java调c++获取图片数据
    // std::shared_ptr<ImageBuffer> demo9();
    // // 推荐使用该函数, 获取图片数据
    public native @SharedPtr @Cast({"", "std::shared_ptr<MediaData>"}) MediaData demo10();

    public native @ByVal MediaData demo10_1();

    // std::shared_ptr<ImageBuffer> demo11();

    /**
     * 功能: 内部启动一个线程调用本类中的某个函数 (demo12Thread)
     * 验证java手动销毁该类会有什么问题发生
     *
     * 没有任何问题, 依旧可以正常运行
     */
    public native void demo12();

    /**
     * 功能: 内部先定义函数std::function, 然后将javacpp_example1类中的某个函数与其进行绑定
     * 然后启动一个线程不断的回调javacpp_example1中的对应的函数, 在java端手动释放javacpp_example1
     * 对象, 看看会发生什么问题
     *
     * 会导致java虚拟机崩溃
     */
    public native void demo13();

    /**
     * 功能: 解决demo13中的问题, 在demo13Solve函数中重新创建当前类, 然后插入到静态Map结合中
     * 在java中, 调用demo13SolveClose函数从map中移除JavaCppExample类
     *
     * 原理是依靠c++的智能指针自动释放能力, 当线程结束之后会自动释放对象
     *
     */
    public native void demo13Solve();
    public native void demo13SolveClose();




    /**
     * 功能: 如果java调用demo14且内部创建一个JavaCppExample1对象并将其绑定到demo14_javacpp_example1属性上,
     * 然后启动一个线程并将其传入, java端在线程还结束的时候销毁当前对象, 是否会发生问题
     *
     * 结论: 测试通过
     */
    public native void demo14();
    /**
     * 功能: 更新demo14_javacpp_example1对象中的 demo14_thread_count值, 效果就是会影响到demo14Thread线程
     * 中调用 JavaCppExample1#demo14Thread 所打印的值 (引用传递)
     */
    public native void demo14UpdateThreadCount(int add);
    /**
     * 功能: 打印 demo14_javacpp_example1对象中的 demo14_thread_count 值, JavaCppExample1#demo14Thread线程更新的
     * 值会影响到当前调用demo14PrintThreadCount线程所打印的 JavaCppExample1#demo14_thread_count 值 (引用传递)
     */
    public native void demo14PrintThreadCount();




    /**
     * 功能: 调用 javacpp_example1 中的 demo15 函数,JavaCppExample1#demo15, 用于测试
     * java销毁当前类, 对于多线程和std::bind的影响
     */
    public native void demo15();

    public native void demo15Thread();



    /**
     * 功能: demo16内部启动一个线程, 然后将demo16_javacpp_example1作为参数传递到
     * demo16Thread函数中, 主要验证demo16_javacpp_example1释放问题
     */
    public native void demo16();




    /**
     * 功能: demo17内部启动一个线程调用javacpp_example1中的一个函数, 并传递javacpp_example_data
     * 类数据, 以及将javacpp_example_data类中的std::funtion绑定到当前类中的demo17Callback_1中
     *
     * javacpp_example1#demo17 内部会进行循环操作
     *
     * 在javacpp_example1#demo17循环过程中, 在java端销毁本类看是否会崩溃  (没有崩溃)
     */
    public native void demo17();
    public native void demo17PrintfJavaCppExampleData();
    public native void demo17UpdateJavaCppExampleData();






    /**
     * 功能: 与demo17的区别是在回调中调用其他对象中函数
     *
     */
    public native void demo18();





}
