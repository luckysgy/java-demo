package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Converter {
    Converter INSTANCE = Mappers.getMapper(Converter.class);
    Target1 toTarget1(Source1 source);

    @Mapping(source = "name", target = "username")
    Target2 toTarget2(Source1 source);

    Source1 toSource1(Target1 target);

    // =============================================== 多转1 =================================================
    /**
     * 我们在实际的业务中少不了将多个对象转换成一个的场景。 MapStruct 当然也支持多转一的操作
     *
     * 在多对一转换时， 遵循以下几个原则
     * 1. 当多个对象中， 有其中一个为 null， 则会直接返回 null
     * 2. 如一对一转换一样， 属性通过名字来自动匹配。 因此， 名称和类型相同的不需要进行特殊处理
     * 3. 当多个原对象中，有相同名字的属性时，需要通过 @Mapping 注解来具体的指定， 以免出现歧义（不指定会报错）。 如上面的 description
     * 4. 属性也可以直接从传入的参数来赋值。
     */
    @Mapping(source = "person.description", target = "description")
    @Mapping(source = "address.houseNo", target = "houseNumber")
    DeliveryAddress toDeliveryAddress(Person person, Address address);

    // hn 直接赋值给 houseNumber
    @Mapping(source = "person.description", target = "description")
    @Mapping(source = "hn", target = "houseNumber")
    DeliveryAddress personAndAddressToDeliveryAddressDto(Person person, Integer hn);

    // =============================================== 更新 Bean 对象 =================================================
    /**
     * 更新 Bean 对象
     * 有时候， 我们不是想返回一个新的 Bean 对象， 而是希望更新传入对象的一些属性。这个在实际的时候也会经常使用到。
     * 更新， 使用 Address 来补全 DeliveryAddress 信息。 注意注解 @MappingTarget
     * 注解 @MappingTarget后面跟的对象会被更新
     */
    void updateDeliveryAddressFromAddress(Address address, @MappingTarget DeliveryAddress deliveryAddress);

    // =============================================== 枚举转换 =================================================
    /**
     * 相同枚举之间的转换
     */
    @Mappings({
            @Mapping(source = "myage", target = "age"),
            @Mapping(source = "sexEnum",target = "sex2Enum")
    })
    UserDto mapperSameEnum(User user);

    /**
     * 映射枚举值不同
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "myage", target = "age"),
            @Mapping(source = "sexEnum",target = "sex3Enum")
    })
    UserDto2 mapperEnumValueDifferent(User user);
    @ValueMappings({
            @ValueMapping(source = "MAN",target = "WOMAN3"),
            @ValueMapping(source = "WOMAN",target = "MAN3"),
            @ValueMapping(source = "SECRECY",target = "SECRECY3")
    })
    Sex3Enum mapperEnumValueDifferentCustomConveter(SexEnum sexEnum);


    /**
     * 映射类型不同的枚举
     */
    @Mappings({
            @Mapping(source = "myage", target = "age"),
            @Mapping(source = "sexEnum.desc",target = "sex")
    })
    UserDto3 mapperEnumTypeDifferent(User user);


    @Mappings({
            @Mapping(source = "age", target = "myage"),
            @Mapping(source = "sex",target = "sexEnum")
    })
    User mapperEnumTypeDifferent(UserDto3 userDto3);
    default SexEnum mapperEnumTypeDifferentCustomSexToEnum(String sex) {
        return SexEnum.get(sex);
    }


    @Mappings({
            @Mapping(source = "myage", target = "age"),
            @Mapping(source = "sexEnum",target = "sex")
    })
    UserDto3 mapperEnumTypeDifferent2(User user);
    default  String mapperEnumTypeDifferent2CustomConveter(SexEnum sexEnum){
        if(sexEnum.getDesc().equals("男")){
            return "你是男的";
        }
        else {
            return "其他";
        }
    }

    // =============================================== 嵌套对象转换 =================================================
    /**
     * 映射枚举值不同
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "myage", target = "age"),
            @Mapping(source = "sexEnum",target = "sex3Enum"),
            @Mapping(source = "id",target = "id.id")
    })
    User1 mapperNestedObject(User user);

//    @ValueMappings({
//            @ValueMapping(source = "id",target = "id"),
//    })
//    UserId mapperNestedObjectCustomConveter(Integer id);

    // =============================================== 将自定义方法映射器映射到Mapstruct =================================================
    @Mappings({
            @Mapping(source = "item", target = "name", qualifiedByName = "processName")
    })
    UserDto2 toUserDto2(UserDto item);

    @Named("processName")
    default String processNameMethod(UserDto item) {
        return item.getName() + UUIDUtil.randomUUID();
    }
}