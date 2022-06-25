package com.demo;

import com.concise.component.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-01-03 9:38
 */
public class MethodParse {
    private static final Logger log = LoggerFactory.getLogger(MethodParse.class);
    private Class<?> interClass;
    // 获取构造方法的参数名
    private LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * @param interClass 接口类
     */
    public MethodParse(Class<?> interClass) {
        this.interClass = interClass;
    }

    /**
     * 解析类中所有的方法
     * @return
     */
    public List<MapObjectDefinition> parse() {
        List<MapObjectDefinition> mapObjectDefinitions = new ArrayList<>();
        try {
            Method[] methods = this.interClass.getDeclaredMethods();
            String methodName = "";
            Class<?> returnType;
            List<Class<?>> methodParameterTypes = new ArrayList<>();
            for (Method method : methods) {
                methodName = method.getName();
                log.info("parse method name: {}", methodName);
                // 获取本方法所有参数类型，存入数组
                Class<?>[] getTypeParameters = method.getParameterTypes();
                if(getTypeParameters.length==0){
                    throw new BizException(methodName + " 方法没有参数");
                }
                for (Class<?> class1 : getTypeParameters) {
                    methodParameterTypes.add(class1);
                    log.info("parse method param type: {}", class1.getName());
                }
                returnType = method.getReturnType();
                log.info("parse method return type: {}", returnType.getName());
//                List<Class<?>> constructorParameterType = getConstructorParameterType(returnType);
                List<ConstructorParamTypeNode> constructorParameterTypeNodes = getConstructorParameterTypeNodes(returnType);
                log.info("********************************************************");

                MapObjectDefinition mapObjectDefinition = new MapObjectDefinition(
                        methodParameterTypes, returnType, null, methodName
                );
                mapObjectDefinitions.add(mapObjectDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapObjectDefinitions;
    }

    private List<ConstructorParamTypeNode> getConstructorParameterTypeNodes(Class<?> clazz) {
        List<ConstructorParamTypeNode> parameterTypes = new ArrayList<>();
        List<ConstructorParam> constructorParam = getConstructorParam(clazz);
        for (ConstructorParam param : constructorParam) {
            ConstructorParamTypeNode constructorParamTypeNode = new ConstructorParamTypeNode();
            constructorParamTypeNode.addLastNode(param.getType(), param.getParamName());
            if (!isBaseType(param.getType())) {
                List<Class<?>> constructorParameterType = getConstructorParameterType(param.getType());
                for (Class<?> aClass1 : constructorParameterType) {
                    constructorParamTypeNode.addLastNode(param.getType(), param.getParamName());
                }
            }
            parameterTypes.add(constructorParamTypeNode);
        }
        return parameterTypes;
    }

    /**
     * 获取构造器参数
     * @param clazz
     * @return
     */
    private List<ConstructorParam> getConstructorParam(Class<?> clazz) {
        List<ConstructorParam> constructorParamList = new ArrayList<>();
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes1 = constructor.getParameterTypes();
            String[] parameterNames = this.localVariableTableParameterNameDiscoverer.getParameterNames(constructor);
            assert parameterNames != null;
            for (int i = 0; i < parameterTypes1.length; i++) {
                ConstructorParam constructorParam = new ConstructorParam(parameterTypes1[i], parameterNames[i]);
                constructorParamList.add(constructorParam);
            }
            return constructorParamList;
        }
        return constructorParamList;
    }

    private List<Class<?>> getConstructorParameterType(Class<?> clazz) {
        List<Class<?>> parameterTypes = new ArrayList<>();

        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes1 = constructor.getParameterTypes();
            parameterTypes.addAll(Arrays.asList(parameterTypes1));
            String[] parameterNames = this.localVariableTableParameterNameDiscoverer.getParameterNames(constructor);
            assert parameterNames != null;
            for (String parameterName : parameterNames) {
                System.out.print(parameterName + " ");
            }
            return parameterTypes;
        }
        return parameterTypes;
    }
    /**
     * 判断是否为基本类型
     * @param parameterType
     * @return
     */
    private boolean isBaseType(Class<?> parameterType) {
        String name = parameterType.getName();
        if ("java.lang.String".equals(name)) {
            return true;
        }

        if ("java.lang.Double".equals(name) || "double".equals(name)) {
            return true;
        }

        if ("java.lang.Integer".equals(name) || "int".equals(name)) {
            return true;
        }

        if ("java.lang.Long".equals(name) || "long".equals(name)) {
            return true;
        }

        if ("java.lang.Float".equals(name) || "float".equals(name)) {
            return true;
        }

        if ("java.lang.Short".equals(name) || "short".equals(name)) {
            return true;
        }
        System.out.println(name);
        return false;
    }
}
