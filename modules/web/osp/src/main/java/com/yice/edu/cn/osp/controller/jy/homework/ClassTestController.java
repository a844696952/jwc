package com.yice.edu.cn.osp.controller.jy.homework;


import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.osp.service.jy.homework.ClassTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classTest")
@Api(value = "/classTest", description = "课堂检测")
public class ClassTestController {

    @Autowired
    private ClassTestService classTestService;

    @PostMapping("/findTopDetailListByCondition")
    @ApiOperation(value = "根据条件查找习题详情列表", notes = "习题详情列表")
    public ResponseJson findTopDetailListByCondition(
            @ApiParam(value = "习题详情对象")
            @RequestBody TopicDetail topicDetail){
        List<TopicDetail> data = classTestService.findTopDetailListByCondition(topicDetail);
        long count=classTestService.findTopDetailCountByCondition(topicDetail);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findClassTestListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测列表", notes = "返回课堂检测列表")
    public ResponseJson findClassTestListByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody ClassTest classTest){
        classTest.setSchoolId (mySchoolId ());
        classTest.setTeacherId(myId());
        if(Objects.nonNull(classTest) && StringUtils.isNotBlank(classTest.getBeginTime()) && StringUtils.isNotBlank(classTest.getEndTime())){
            classTest.setBeginTime(DateUtils.getOriginalDateTime(classTest.getBeginTime(),1));
            classTest.setEndTime(DateUtils.getOriginalDateTime(classTest.getEndTime(),2));
        }
        List<ClassTest> classTestListByCondition = classTestService.findClassTestListByCondition (classTest);
        Long count = classTestService.findClassTestCountByCondition (classTest);
        return new ResponseJson (classTestListByCondition,count);
    }


    @PostMapping("/findListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测列表Demo", notes = "返回课堂检测列表")
    public ResponseJson findListByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody ClassTest classTest){
        classTest.setSchoolId (mySchoolId ());
        classTest.setTeacherId(myId());
        List<ClassTest> classTestListByCondition = classTestService.findListByCondition (classTest);
        return new ResponseJson (classTestListByCondition);
    }



    @PostMapping("/saveClassTest")
    @ApiOperation(value = "根据条件查找课堂检测列表", notes = "返回课堂检测列表")
    public ResponseJson saveClassTest(
            @ApiParam(value = "布置作业对象")
            @RequestBody ClassTest classTest){
        classTest.setSchoolId (mySchoolId ());
        classTest.setTeacherId (myId());
        Boolean result=classTestService.addClassTest(classTest);
        if(result){
            return new ResponseJson ("新增成功",true);
        }
        return new ResponseJson ("新增失败",false);
    }

    @PostMapping("/deleteClassTest")
    @ApiOperation(value = "删除课堂检测", notes = "返回课堂检测对象")
    public void deleteClassTest(
            @ApiParam(value = "课堂检测对象", required = true)
            @RequestBody List<String> classId){
        if (CollUtil.isNotEmpty(classId)){
            classTestService.deleteClassTest(classId);
        }
    }



}

