package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.jy.service.titleQuota.TopicQuotaResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicQuotaResources")
@Api(value = "/topicQuotaResources",description = "题目额度资源表模块")
public class TopicQuotaResourcesController {
    @Autowired
    private TopicQuotaResourcesService topicQuotaResourcesService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTopicQuotaResourcesById/{id}")
    @ApiOperation(value = "通过id查找题目额度资源表", notes = "返回题目额度资源表对象")
    public TopicQuotaResources findTopicQuotaResourcesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return topicQuotaResourcesService.findTopicQuotaResourcesById(id);
    }

    @PostMapping("/saveTopicQuotaResources")
    @ApiOperation(value = "保存题目额度资源表", notes = "返回题目额度资源表对象")
    public TopicQuotaResources saveTopicQuotaResources(
            @ApiParam(value = "题目额度资源表对象", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources){
        topicQuotaResourcesService.saveTopicQuotaResources(topicQuotaResources);
        return topicQuotaResources;
    }

    @PostMapping("/batchSaveTopicQuotaResources")
    @ApiOperation(value = "批量保存题目额度资源表")
    public void batchSaveTopicQuotaResources(
            @ApiParam(value = "题目额度资源表对象集合", required = true)
            @RequestBody List<TopicQuotaResources> topicQuotaResourcess){
        topicQuotaResourcesService.batchSaveTopicQuotaResources(topicQuotaResourcess);
    }

    @PostMapping("/findTopicQuotaResourcesListByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源表列表", notes = "返回题目额度资源表列表")
    public List<TopicQuotaResources> findTopicQuotaResourcesListByCondition(
            @ApiParam(value = "题目额度资源表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findTopicQuotaResourcesListByCondition(topicQuotaResources);
    }
    @PostMapping("/findTopicQuotaResourcesCountByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源表列表个数", notes = "返回题目额度资源表总个数")
    public long findTopicQuotaResourcesCountByCondition(
            @ApiParam(value = "题目额度资源表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findTopicQuotaResourcesCountByCondition(topicQuotaResources);
    }

    @PostMapping("/updateTopicQuotaResources")
    @ApiOperation(value = "修改题目额度资源表有值的字段", notes = "题目额度资源表对象必传")
    public void updateTopicQuotaResources(
            @ApiParam(value = "题目额度资源表对象,对象属性不为空则修改", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources){
        topicQuotaResourcesService.updateTopicQuotaResources(topicQuotaResources);
    }
    @PostMapping("/updateTopicQuotaResourcesForAll")
    @ApiOperation(value = "修改题目额度资源表所有字段", notes = "题目额度资源表对象必传")
    public void updateTopicQuotaResourcesForAll(
            @ApiParam(value = "题目额度资源表对象", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources){
        topicQuotaResourcesService.updateTopicQuotaResourcesForAll(topicQuotaResources);
    }

    @GetMapping("/deleteTopicQuotaResources/{id}")
    @ApiOperation(value = "通过id删除题目额度资源表")
    public void deleteTopicQuotaResources(
            @ApiParam(value = "题目额度资源表对象", required = true)
            @PathVariable String id){
        topicQuotaResourcesService.deleteTopicQuotaResources(id);
    }
    @PostMapping("/deleteTopicQuotaResourcesByCondition")
    @ApiOperation(value = "根据条件删除题目额度资源表")
    public void deleteTopicQuotaResourcesByCondition(
            @ApiParam(value = "题目额度资源表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        topicQuotaResourcesService.deleteTopicQuotaResourcesByCondition(topicQuotaResources);
    }
    @PostMapping("/findOneTopicQuotaResourcesByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度资源表,结果必须为单条数据", notes = "返回单个题目额度资源表,没有时为空")
    public TopicQuotaResources findOneTopicQuotaResourcesByCondition(
            @ApiParam(value = "题目额度资源表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findOneTopicQuotaResourcesByCondition(topicQuotaResources);
    }


/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateTopicQuotaResources4Person")
    @ApiOperation(value = "修改题目额度资源表", notes = "题目额度资源表对象必传")
    public TopicQuotaResources updateTopicQuotaResources4Person(
            @ApiParam(value = "题目额度资源表对象", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.updateTopicQuotaResources4Person(topicQuotaResources);
    }

    @GetMapping("/getBaiscInfo/{id}")
    @ApiOperation(value = "查询题库基本信息", notes = "返回题目额度资源表对象")
    public TopicQuotaResources getBaiscInfo(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return topicQuotaResourcesService.getBaiscInfo(id);
    }


    @PostMapping("/findPaltFormByCondition")
    @ApiOperation(value = "传入schoolId", notes = "返回单个学生答题记录表,没有时为空")
    public List<ResourcePlatform> findPaltFormByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findPaltFormByCondition(topicQuotaResources);
    }

    @PostMapping("/findTeacherAndSchoolRemain")
    @ApiOperation(value = "传入schoolId和teacherId", notes = "返回单个学生答题记录表,没有时为空")
    public long findTeacherAndSchoolRemain(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findTeacherAndSchoolRemain(topicQuotaResources);
    }
}
