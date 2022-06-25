package com.demo;

import com.concise.component.core.utils.StringUtils;
import com.demo.domain.Demo1;
import com.demo.domain.Username;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * @author shenguangyang
 * @date 2022-01-02 21:53
 */
public class TestJavaPost {
    public static void main(String[] args) throws IOException {
        MethodSpec test = MethodSpec
                //定义方面名
                .methodBuilder("test")
                //定义修饰符
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                //定义返回结果
                .returns(Demo1.class)
                //添加方法参数
                .addParameter(Demo1.class, "from")
                //添加方法内容
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .addStatement("$T $N = new $T($N,$S)", Username.class,
                        StringUtils.uncapitalize(Username.class.getSimpleName()), Username.class, "demo2.getPassword()", "JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec
                //构造一个类,类名
                .classBuilder("HelloWorld")
                //定义类的修饰符
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                //添加类的方法,也就是上面生成的MethodSpec对象
                .addMethod(test)
                .build();

        JavaFile javaFile = JavaFile.builder("com.concise.demo.javapost", helloWorld)//定义生成的包名,和类
                .build();

        javaFile.writeTo(System.out);//输出路径,可以收一个file地址
    }
}
