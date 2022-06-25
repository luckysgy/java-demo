package com.demo.domain;

import com.demo.MapperFiled;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-01-02 21:57
 */
public class Demo2 {
    @MapperFiled(Username.class)
    private Username username;

    @MapperFiled(String.class)
    private String password;

    public Demo2(Username username, String password, Double data1, Integer data2) {
        System.out.println("username: " + username + "\t password: " + password);
        this.username = username;
        this.password = password;
    }

    public static void main(String[] args) throws Exception {
        // 获取构造方法的参数名
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        // 获取构造方法的参数名
        Class<?> forName = Class.forName(Demo2.class.getName());
        Constructor<?>[] constructors = forName.getConstructors();
        for (Constructor<?> constructor : constructors) {
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                if (parameterType.getSimpleName().equals("Username")) {
                    System.out.println(parameterType.getName());

                    Constructor<Username> usernameConstructor = Username.class.getConstructor(String.class);
                    Username username = usernameConstructor.newInstance("反射创建对象");
                    constructor.newInstance(username, "密码");
                }

            }
            String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(constructor);
            for (String parameterName : parameterNames) {
                System.out.print(parameterName + " ");
            }
            System.out.println();
        }
    }

    /**
     * 合并该类和对应父类的所有Fields
     * @return List<Field>
     */
    public static List<Field> mergeAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        //排除父级元素,可自定义
        while (clazz != null && !Object.class.getName().equals(clazz.getName())) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }


    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
