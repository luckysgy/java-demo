package com.demo.javassist;

import javassist.*;

import java.lang.reflect.Method;

public class JavassistMain {
    public static void main(String[] args) throws Exception {
        // 创建ClassPool
        ClassPool cp = ClassPool.getDefault();
        //生成的类的名称为 com.concise.demo.base.javassist
        CtClass clazz = cp.makeClass("com.concise.demo.base.javassist.JavassistTest");
        StringBuffer body = null;
        //创建字段，指定了字段类型、字段名称、字段所属的类
        CtField field = new CtField(cp.get("java.lang.String"), "dictLabel", clazz);
        //指定该字段使用private修饰
        field.setModifiers(Modifier.PRIVATE);

        // 设置父类
        clazz.setSuperclass(cp.get("com.concise.demo.base.javassist.MyJavassist"));

        //设置prop字段的getter/setter方法
        clazz.addMethod(CtNewMethod.getter("getDictLabel",field));
        clazz.addMethod(CtNewMethod.setter("setDictLabel",field));
        //设置prop字段的初始化值，并将prop字段添加到clazz中
        clazz.addField(field,CtField.Initializer.constant("MyName"));
        //创建构造方法，指定了构造方法的参数类型和构造方法所属的类
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, clazz);
        //设置方法体
        body = new StringBuffer();
        body.append("{\n dictLabel=\"MyName\";\n}");
        ctConstructor.setBody(body.toString());
        //将构造方法添加到clazz中
        clazz.addConstructor(ctConstructor);
        //创建execute()方法，指定了方法的返回值、方法名称、方法参数列表以及方法所属的类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, clazz);
        //指定方法使用public修饰
        ctMethod.setModifiers(Modifier.PUBLIC);
        //设置方法体
        body = new StringBuffer();
        body.append("{\n System.out.println(\"execute(): \" + this.dictLabel);");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        //将execute()方法添加到clazz中
        clazz.addMethod(ctMethod);
        //将上面定义的JavassistTest类保存到指定的目录
        // clazz.writeFile("E:\\IOC\\target\\classes");
        //加载clazz类，并创建对象
        Class<?> c = clazz.toClass();
        Object o = c.newInstance();
        //调用execute()方法
        Method method = o.getClass().getMethod("execute", new Class[]{});

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Object o1 = c.newInstance();
            Method setName = o1.getClass().getMethod("setName", String.class);
            setName.invoke(o1, "test111111111111");
            //System.out.println(new Gson().toJson(o1));
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        method.invoke(o,new Object[]{});
    }
}