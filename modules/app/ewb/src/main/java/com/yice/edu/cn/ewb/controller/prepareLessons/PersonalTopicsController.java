package com.yice.edu.cn.ewb.controller.prepareLessons;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.ewb.interceptor.LoginInterceptor;
import com.yice.edu.cn.ewb.service.prepareLessons.PersonalTopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personalTopics")
@Api(value = "/personalTopics",description = "模块")
public class PersonalTopicsController {


    @Autowired
    private PersonalTopicsService personalTopicsService;


    @PostMapping("/find/findPersonalTopicssByCondition")
    @ApiOperation(value = "根据条件查找个人题库", notes = "返回响应对象", response=PersonalTopics.class)
    public ResponseJson findPersonalTopicssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PersonalTopics personalTopics){
        personalTopics.setContentText(StringUtils.specialCharacterConvert(personalTopics.getContentText()));
        personalTopics.setSchoolId(LoginInterceptor.mySchoolId());
        personalTopics.setTeacherId(LoginInterceptor.myId());
        personalTopics.getPager().setLike("contentText");
        personalTopics.getPager().setSortField("createTime");
        personalTopics.getPager().setSortOrder(Pager.DESC);
        List<PersonalTopics> data=personalTopicsService.findPersonalTopicsListByCondition(personalTopics);
        long count=personalTopicsService.findPersonalTopicsCountByCondition(personalTopics);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "多平台查询题目详情", notes = "返回响应对象,含总条数",response = PersonalTopics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(personalTopicsService.findTopicDetail(resourceVo));
    }


}
