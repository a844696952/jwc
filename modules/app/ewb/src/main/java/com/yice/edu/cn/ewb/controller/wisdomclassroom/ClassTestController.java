package com.yice.edu.cn.ewb.controller.wisdomclassroom;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.ewb.service.wisdomclassroom.ClassTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classTest")
@Api(value = "/classTest",description = "课堂测试模块")
public class ClassTestController {

    @Autowired
    private ClassTestService classTestService;

    @GetMapping("/findHomeworkById/{id}")
    @ApiOperation(value = "通过id查找布置作业", notes = "返回布置作业对象")
    public ClassTest findHomeworkById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return classTestService.findClassTestById(id);
    }

    @PostMapping("/saveClassTest")
    @ApiOperation(value = "保存课堂检测", notes = "返回布尔值")
    public ResponseJson saveClassTest(
            @ApiParam(value = "保存课堂检测", required = true)
            @RequestBody ClassTest classTest){
        classTest.setSchoolId (mySchoolId());
        classTest.setTeacherId (myId());
        Boolean result=classTestService.saveClassTest(classTest);
        if(result){
            return new ResponseJson("新增成功",true);
        }
        return new ResponseJson ("新增失败",false);
    }

    @PostMapping("/findHomeworkListByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<ClassTest> findHomeworkListByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody ClassTest classTest){
        return classTestService.findClassTestByCondition(classTest);
    }
}
