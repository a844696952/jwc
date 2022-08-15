package com.yice.edu.cn.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * OBJECTIVE_TOPIC("客观题", 8, false)
 *
 * 以后的题型添加 不要用到 id 为 8 因为这个是给客观题用的
 */
@ApiModel("题目类型")
public enum TopicType {
    JUDGE("判断题", 1, false), SINGLE("单选题", 2, false), MULTIPLE("多选题", 3, false), FILL("填空题", 4, true), QAA("问答题", 5, true), CHINESE_WRITING("语文作文题", 6, true), ENGLISH_WRITING("英语作文题", 7, true), CLOZE("完形填空", 9, false), READING("阅读题", 10, true);
    // 成员变量
    @ApiModelProperty(value = "题型名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "题型id", dataType = "int")
    private int id;
    @ApiModelProperty(value = "是否主观题", dataType = "boolean")
    private boolean subjective;

    // 构造方法
    TopicType(String name, int id, boolean subjective) {
        this.name = name;
        this.id = id;
        this.subjective = subjective;
    }

    // 普通方法
    public static String getName(int id) {
        for (TopicType c : TopicType.values()) {
            if (c.getId() == id) {
                return c.name;
            }
        }
        return null;
    }

    public static TopicType[] getTopicTypes() {
        return TopicType.values();
    }

    public static List<Map<String, Object>> turnToListMap() {
        TopicType[] topicTypes = TopicType.getTopicTypes();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        for (int i = 0; i < topicTypes.length; i++) {
            TopicType topicType = topicTypes[i];
            map = new HashMap<>();
            map.put("id", topicType.getId());
            map.put("name", topicType.getName());
            map.put("subjective", topicType.isSubjective());
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

    public boolean isSubjective() {
        return subjective;
    }

    public void setSubjective(boolean subjective) {
        this.subjective = subjective;
    }

}

