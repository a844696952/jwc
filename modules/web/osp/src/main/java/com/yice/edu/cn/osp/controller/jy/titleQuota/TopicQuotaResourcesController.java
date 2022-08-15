package com.yice.edu.cn.osp.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.osp.service.jy.titleQuota.TopicQuotaResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/topicQuotaResources")
@Api(value = "/topicQuotaResources",description = "题目额度资源表模块")
public class TopicQuotaResourcesController {
    @Autowired
    private TopicQuotaResourcesService topicQuotaResourcesService;

    @GetMapping("/getBaiscInfo/{schoolId}")
    @ApiOperation(value = "获取该学校题库基本情况", notes = "返回发送结果")
    public ResponseJson getBaiscInfo(@PathVariable String schoolId) {
        schoolId=mySchoolId();
        TopicQuotaResources topicQuotaResources = topicQuotaResourcesService.getBaiscInfo(schoolId);
        return new ResponseJson(topicQuotaResources);
    }


    @PostMapping("/findPaltFormByCondition")
    @ApiOperation(value = "传入schoolId", notes = "返回单个学生答题记录表,没有时为空")
    public List<ResourcePlatform> findPaltFormByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findPaltFormByCondition(topicQuotaResources);
    }

}
