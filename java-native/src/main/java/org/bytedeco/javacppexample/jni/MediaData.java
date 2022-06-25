// Targeted by JavaCPP version 1.5.7: DO NOT EDIT THIS FILE

package org.bytedeco.javacppexample.jni;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.NoOffset;
import org.bytedeco.javacpp.annotation.Properties;

import java.nio.ByteBuffer;


/**
 * \brief 媒体信息, 只存放视频和图片
 *
 */
@NoOffset @Properties(inherit = org.bytedeco.javacppexample.presets.common.class)
public class MediaData extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public MediaData(Pointer p) { super(p); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public MediaData(long size) { super((Pointer)null); allocateArray(size); }
    private native void allocateArray(long size);
    @Override public MediaData position(long position) {
        return (MediaData)super.position(position);
    }
    @Override public MediaData getPointer(long i) {
        return new MediaData((Pointer)this).offsetAddress(i);
    }

    public MediaData() { super((Pointer)null); allocate(); }
    private native void allocate();

    // 一定要加上析构函数，否则会有内存溢出问题

    // MediaData(MediaData&& m) {};
    // MediaData& operator = (MediaData&& m);
    // MediaData& operator = (const MediaData& m);

    public MediaData(@Cast("uchar*") BytePointer data, long length) { super((Pointer)null); allocate(data, length); }
    private native void allocate(@Cast("uchar*") BytePointer data, long length);
    public MediaData(@Cast("uchar*") ByteBuffer data, long length) { super((Pointer)null); allocate(data, length); }
    private native void allocate(@Cast("uchar*") ByteBuffer data, long length);
    public MediaData(@Cast("uchar*") byte[] data, long length) { super((Pointer)null); allocate(data, length); }
    private native void allocate(@Cast("uchar*") byte[] data, long length);

    public native @Cast("uchar*") BytePointer get_data();
    public native void set_data(@Cast("uchar*") BytePointer data);
    public native void set_data(@Cast("uchar*") ByteBuffer data);
    public native void set_data(@Cast("uchar*") byte[] data);

    public native long get_length();
    public native void set_length(long length);

    public native @Cast("const MediaType") int get_type();
    public native void set_type(@Cast("const MediaType") int type);

    public native int get_width();
    public native void set_width(int width);

    public native int get_height();
    public native void set_height(int height);
}
