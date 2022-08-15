package com.yice.edu.cn.common.pojo.jy.topics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value = "题类")
public enum TopicClass {
    ZHENTI("真题",1),FREQUENTLY("常考题",2),EASYERROR("易错题",3),GOOD("好题",4),FINALE("压轴题",5)
    //,LISTENING("听力题",6) 2019/08/19 产品说 去掉
    ,SIMULATION("模拟题",7);

    // 成员变量
    @ApiModelProperty(value = "题型名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "题型id",dataType = "int")
    private int id;
    // 构造方法
    TopicClass(String name, int id) {
        this.name = name;
        this.id = id;
    }
    // 普通方法
    public static String getName(int id) {
        for (TopicClass c : TopicClass.values()) {
            if (c.getId() == id) {
                return c.name;
            }
        }
        return null;
    }
    public static TopicClass[] getTopicClass(){
        return  TopicClass.values();
    }
    public static List<Map<String,Object>> turnToListMap(){
        TopicClass[] topicClasses = TopicClass.getTopicClass();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        for (int i = 0; i < topicClasses.length; i++) {
            TopicClass topicDifficult = topicClasses[i];
            map = new HashMap<>();
            map.put("id",topicDifficult.getId());
            map.put("name",topicDifficult.getName());
            result.add(map);
        }
        return result;
    }
    // get set 方法
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
