package org.bytedeco.javacppexample.function_pointer;

import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;

/**
 * library = "javacpp_function_pointer" 生成的库和头文件名成都是 javacpp_function_pointer
 * @author shenguangyang
 * @date 2022/3/9 9:22
 */
@Platform(include="<algorithm>", library = "javacpp_function_pointer")
@Namespace("std")
public class JavacppFunctionPointerExample {
    static { Loader.load(); }

    public static class Callback extends FunctionPointer {
        // Loader.load() and allocate() are required only when explicitly creating an instance
        static { Loader.load(); }
        protected Callback() { allocate(); }
        private native void allocate();

        public @Name("foo") boolean call(int a, int b) {
            System.out.println("java FunctionPointer call" + ", a + b = " + (a + b));
            return true;
        }
    }

    // We can also pass (or get) a FunctionPointer as argument to (or return value from) other functions
    public static native void stable_sort(IntPointer first, IntPointer last, Callback compare);

    // And to pass (or get) it as a C++ function object, annotate with @ByVal or @ByRef
    public static native void sort(IntPointer first, IntPointer last, @ByVal Callback compare);
}
