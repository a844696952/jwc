package com.yice.edu.cn.jy.controller.homework;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.jy.service.homework.ClassTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 课堂检测
 * @author mayn
 */
@RestController
@RequestMapping("/classTest")
@Api(value = "/classTest",description = "课堂测试模块")
public class ClassTestController {

    @Autowired
    private ClassTestService classTestService;

    @GetMapping("/findClassTestById/{id}")
    @ApiOperation(value = "通过id查找布置作业", notes = "返回布置作业对象")
    public ClassTest findClassTestById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classTestService.findClassTestById(id);
    }

    @PostMapping("/saveClassTest")
    @ApiOperation(value = "保存课堂检测", notes = "返回课堂检测对象")
    public Boolean saveClassTest(
            @ApiParam(value = "课堂检测对象", required = true)
            @RequestBody ClassTest classTest){
      return classTestService.addCourseTestInfo (classTest);

    }

    @PostMapping("/findClassTestListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测列表", notes = "返回布课堂检测列表")
    public List<ClassTest> findClassTestListByCondition(
            @ApiParam(value = "课堂检测对象")
            @RequestBody ClassTest classTest) {
        if (Objects.nonNull(classTest)) {
            return classTestService.findClassTestByCondition(classTest);
        }
        return new ArrayList<>();
    }

    @PostMapping("/findListByCondition")
    @ApiOperation(value = "根据条件查找课堂检测列表Argateion", notes = "返回布课堂检测列表")
    public List<ClassTest> findListByCondition(
            @ApiParam(value = "课堂检测对象")
            @RequestBody ClassTest classTest) {
        if (Objects.nonNull(classTest)) {
           // return classTestService.findListByCondition(classTest, StringUtils.isNotEmpty(classTest.getSearchKey())?classTest.getSearchKey():"",StringUtils.isNotEmpty(classTest.getBeginTime())?classTest.getBeginTime():"",StringUtils.isNotEmpty(classTest.getEndTime())?classTest.getEndTime():"");
            return classTestService.findClassTestByJoinTable(classTest);
        }
        return new ArrayList<>();
    }


    @PostMapping("/findClassTestCountByCondition")
    @ApiOperation(value = "根据条件查找课堂检测总数", notes = "课堂检测总数")
    public  Long findClassTestCountByCondition(
            @ApiParam(value = "课堂检测对象")
            @RequestBody ClassTest classTest){
        return classTestService.findClassTestCount(classTest);
    }


    @PostMapping("/findTopDetailListByCondition")
    @ApiOperation(value = "根据条件查找习题详情列表", notes = "习题详情列表")
      public List<TopicDetail> findTopDetailListByCondition(
              @ApiParam(value = "习题详情对象")
              @RequestBody TopicDetail topicDetail){
            if(Objects.nonNull(topicDetail)){
                return classTestService.findTopDetailListByCondition(topicDetail);
            }
            return new ArrayList<>();
    }

    @PostMapping("/findTopDetailCountByCondition")
    @ApiOperation(value = "根据条件查找习题详情总数", notes = "习题详情总数")
      public Long findTopDetailCountByCondition(
        @ApiParam(value = "习题详情对象")
        @RequestBody TopicDetail topicDetail){
        return classTestService.findTopDetailCountByCondition(topicDetail);
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