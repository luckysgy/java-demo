package com.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author shenguangyang
 * @date 2022-02-01 19:55
 */
@TableName("demo")
public class DemoPO {
//    @TableId(value = "demo_id", type = IdType.AUTO)
//    private String demoId;

    // 如果使用了自定义主键类型, 不需要指定type
    @TableId(type = IdType.AUTO)
    private String id;

    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
