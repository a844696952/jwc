package com.yice.edu.cn.osp.controller.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.service.jy.topics.TopicsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform")
public class PlatformTopicController {
    @Autowired
    private TopicsService topicsService;

    @PostMapping("/findTopicList")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        return topicsService.findTopicList(searchParam);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(topicsService.findTopicDetail(resourceVo));
    }

    @PostMapping("/copyTopicToSchoolQusBank")
    @ApiOperation(value = "平台题库移动到校本题库")
    public ResponseJson copyTopicToSchoolQusBank(@RequestBody ResourceVo resourceVo){
        return topicsService.copyTopicToSchoolQusBank(resourceVo);
    }
}
