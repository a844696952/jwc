package com.yice.edu.cn.jy.controller.topics;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.service.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@Api(value = "/topics",description = "题目表 mongodb 专用模块")
public class TopicsController {
    @Autowired
    private TopicsService topicsService;

    @GetMapping("/findTopicsById/{id}")
    @ApiOperation(value = "通过id查找题目表 mongodb 专用", notes = "返回题目表 mongodb 专用对象")
    public Topics findTopicsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return topicsService.findTopicsById(id);
    }

    @PostMapping("/saveTopics")
    @ApiOperation(value = "保存题目表 mongodb 专用", notes = "返回题目表 mongodb 专用对象")
    public Topics saveTopics(
            @ApiParam(value = "题目表 mongodb 专用对象", required = true)
            @RequestBody Topics topics){
        topicsService.saveTopics(topics);
        return topics;
    }

    @PostMapping("/findTopicsListByCondition")
    @ApiOperation(value = "根据条件查找题目表 mongodb 专用列表", notes = "返回题目表 mongodb 专用列表")
    public List<Topics> findTopicsListByCondition(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        return topicsService.findTopicsListByCondition(topics);
    }
    @PostMapping("/findTopicsCountByCondition")
    @ApiOperation(value = "根据条件查找题目表 mongodb 专用列表个数", notes = "返回题目表 mongodb 专用总个数")
    public long findTopicsCountByCondition(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        return topicsService.findTopicsCountByCondition(topics);
    }

    @PostMapping("/updateTopics")
    @ApiOperation(value = "修改题目表 mongodb 专用", notes = "题目表 mongodb 专用对象必传")
    public void updateTopics(
            @ApiParam(value = "题目表 mongodb 专用对象,对象属性不为空则修改", required = true)
            @RequestBody Topics topics){
        topicsService.updateTopics(topics);
    }

    @GetMapping("/deleteTopics/{id}")
    @ApiOperation(value = "通过id删除题目表 mongodb 专用")
    public void deleteTopics(
            @ApiParam(value = "题目表 mongodb 专用对象", required = true)
            @PathVariable String id){
        topicsService.deleteTopics(id);
    }
    @PostMapping("/deleteTopicsByCondition")
    @ApiOperation(value = "根据条件删除题目表 mongodb 专用")
    public void deleteTopicsByCondition(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        topicsService.deleteTopicsByCondition(topics);
    }
    @PostMapping("/findOneTopicsByCondition")
    @ApiOperation(value = "根据条件查找单个题目表 mongodb 专用,结果必须为单条数据", notes = "返回单个题目表 mongodb 专用,没有时为空")
    public Topics findOneTopicsByCondition(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        return topicsService.findOneTopicsByCondition(topics);
    }
    @PostMapping("/findTopicsListByCondition4Muti")
    @ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回题目列表 ")
    public List<Topics> findTopicsListByCondition4Muti(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        return topicsService.findTopicsListByCondition4Muti(topics);
    }
    @PostMapping("/findTopicsCountByCondition4Muti")
    @ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回题目列表 ")
    public long findTopicsCountByCondition4Muti(
            @ApiParam(value = "题目表 mongodb 专用对象")
            @RequestBody Topics topics){
        return topicsService.findTopicsCountByCondition4Muti(topics);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "平台题库统一查询题目详情", notes = "返回题目 ",response = Topics.class)
    public Object findTopicDetail(@RequestBody TopicParam topicParam){
        return topicsService.findTopicDetail(topicParam);
    }
    @PostMapping("/findTopicList")
    @ApiOperation(value = "平台题库统一查询题目列表", notes = "返回题目列表 ",response = Topics.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        return topicsService.findTopicList(searchParam);
    }
    @PostMapping("/findTopicUseCount")
    @ApiOperation(value = "查询老师对该题最近几个月使用的次数",notes = "返回使用数量",response = Long.class)
    public Long findTopicUseCount(@RequestBody TopicParam topicParam){
        return topicsService.findTopicUseCount(topicParam);
    }
}
