package com.demo;

import org.junit.jupiter.api.Test;

/**
 * @author shenguangyang
 * @date 2022-01-06 20:37
 */
class ConverterTest {

    @Test
    void toTarget1() {
        Source1 source1 = new Source1(12, "xxx");
        Target1 target1 = Converter.INSTANCE.toTarget1(source1);
        System.out.println(target1);
    }

    @Test
    void toSource1() {
        Target1 Target1 = new Target1(12, "xxx");
        Source1 source1 = Converter.INSTANCE.toSource1(Target1);
        System.out.println(source1);
    }

    @Test
    void toTarget2() {
        Source1 source1 = new Source1(12, "xxx");
        Target2 target2 = Converter.INSTANCE.toTarget2(source1);
        System.out.println(target2);
    }

    @Test
    void toDeliveryAddress() {
        Person person = new Person();
        person.setDescription("描述");
        person.setHeight(120);
        person.setFirstName("shen");
        person.setLastName("guangyang");

        Address address = new Address();
        address.setDescription("辽宁");
        address.setHouseNo(123);
        address.setZipCode(124);
        address.setStreet("street");

        DeliveryAddress deliveryAddress = Converter.INSTANCE.toDeliveryAddress(person, address);
        System.out.println(deliveryAddress);
    }

    @Test
    void updateDeliveryAddressFromAddress() {
        Person person = new Person();
        person.setDescription("描述");
        person.setHeight(120);
        person.setFirstName("shen");
        person.setLastName("guangyang");

        Address address = new Address();
        address.setDescription("辽宁");
        address.setHouseNo(123);
        address.setZipCode(124);
        address.setStreet("street");

        DeliveryAddress deliveryAddress = Converter.INSTANCE.toDeliveryAddress(person, address);
        Address address1 = new Address();
        address1.setDescription("addressDescription");
        address1.setHouseNo(29);
        address1.setStreet("street");
        address1.setZipCode(344);

        Converter.INSTANCE.updateDeliveryAddressFromAddress(address1, deliveryAddress);
        System.out.println(deliveryAddress);
    }

    @Test
    void mapperSameEnum() {
        User user= new User();
        user.setId(11);
        user.setMyage(12);
        user.setName("张三");
        user.setSexEnum(SexEnum.MAN);
        UserDto userDto = Converter.INSTANCE.mapperSameEnum(user);
        System.out.println(userDto.toString());
    }

    @Test
    void mapperEnumValueDifferent() {
        User user= new User();
        user.setId(11);
        user.setMyage(12);
        user.setName("张三");
        user.setSexEnum(SexEnum.MAN);
        UserDto2 userDto2 = Converter.INSTANCE.mapperEnumValueDifferent(user);
        System.out.println(userDto2);
    }

    @Test
    void mapperEnumTypeDifferent() {
        User user= new User();
        user.setId(11);
        user.setMyage(12);
        user.setName("张三");
        user.setSexEnum(SexEnum.MAN);
        UserDto3 userDto = Converter.INSTANCE.mapperEnumTypeDifferent(user);
        System.out.println(userDto.toString());
    }

    @Test
    void mapperEnumTypeDifferent2() {
        User user= new User();
        user.setId(11);
        user.setMyage(12);
        user.setName("张三");
        user.setSexEnum(SexEnum.MAN);
        UserDto3 userDto = Converter.INSTANCE.mapperEnumTypeDifferent2(user);
        System.out.println(userDto.toString());
    }

    @Test
    void testMapperEnumTypeDifferent() {
        UserDto3 userDto3 = new UserDto3();
        userDto3.setId(11);
        userDto3.setAge(12);
        userDto3.setName("张三");
        userDto3.setSex("男");
        User user = Converter.INSTANCE.mapperEnumTypeDifferent(userDto3);
        System.out.println(user.toString());
    }

    @Test
    void mapperNestedObject() {
        User user = new User();
        user.setId(0);
        user.setMyage(12);
        user.setName("张三");
        user.setSexEnum(SexEnum.MAN);
        User1 user1 = Converter.INSTANCE.mapperNestedObject(user);
        System.out.println(user1.toString());
    }

    @Test
    void toUserDto2() {
        UserDto userDto = new UserDto();
        userDto.setName("name");
        userDto.setAge(123);
        UserDto2 userDto2 = Converter.INSTANCE.toUserDto2(userDto);
        System.out.println("------");
    }
}