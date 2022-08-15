package com.yice.edu.cn.jy.controller.homework;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.jy.service.homework.TopicDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/topicDetail")
@Api(value = "/topicDetail",description = "习题详情模块")
public class TopicDetailController {
    @Autowired
    private TopicDetailService topicDetailService;


    @PostMapping("/findTopicDetailListByCondition")
    @ApiOperation(value = "根据条件查找习题列表", notes = "返回布置习题")
    public List<TopicDetail> findTopicDetailListByCondition(
            @ApiParam(value = "布置习题")
            @RequestBody TopicDetail topicDetail){
        return topicDetailService.findTopicDetailListByCondition(topicDetail);
    }



}
