// Targeted by JavaCPP version 1.5.7: DO NOT EDIT THIS FILE

package org.bytedeco.javacppexample.jni;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;


@NoOffset @Properties(inherit = org.bytedeco.javacppexample.presets.algo_service.class)
public class AlgoRequest extends Pointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public AlgoRequest(Pointer p) { super(p); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public AlgoRequest(long size) { super((Pointer)null); allocateArray(size); }
    private native void allocateArray(long size);
    @Override public AlgoRequest position(long position) {
        return (AlgoRequest)super.position(position);
    }
    @Override public AlgoRequest getPointer(long i) {
        return new AlgoRequest((Pointer)this).offsetAddress(i);
    }

    public AlgoRequest() { super((Pointer)null); allocate(); }
    private native void allocate();
    
    public native @StdVector MediaData get_media_data();
    public native void set_media_data(@StdVector MediaData media_data);

    public native @StdString BytePointer get_body();
    public native void set_body(@StdString BytePointer data);
    public native void set_body(@StdString String data);

    public native long get_length();
    public native void set_length(long length);

    // 一定要加上析构函数，否则会有内存溢出问题
}
