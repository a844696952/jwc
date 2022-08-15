package com.yice.edu.cn.common.pojo.jy.topics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value = "题目难度")
public enum TopicDifficult {
    EASILY("简单",1),EASIER("较易",2),ORDINARY("普通",3),HARDER("较难",4),DIFFICULT("困难",5);

    // 成员变量
    @ApiModelProperty(value = "题型名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "题型id",dataType = "int")
    private int id;
    // 构造方法
    TopicDifficult(String name, int id) {
        this.name = name;
        this.id = id;
    }
    // 普通方法
    public static String getName(int id) {
        for (TopicDifficult c : TopicDifficult.values()) {
            if (c.getId() == id) {
                return c.name;
            }
        }
        return null;
    }
    public static TopicDifficult[] getTopicDifficult(){
        return  TopicDifficult.values();
    }
    public static List<Map<String,Object>> turnToListMap(){
        TopicDifficult[] topicDifficultArr = TopicDifficult.getTopicDifficult();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        for (int i = 0; i < topicDifficultArr.length; i++) {
            TopicDifficult topicDifficult = topicDifficultArr[i];
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
