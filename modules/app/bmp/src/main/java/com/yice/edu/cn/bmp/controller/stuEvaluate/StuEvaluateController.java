package com.yice.edu.cn.bmp.controller.stuEvaluate;

import com.yice.edu.cn.bmp.service.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;


@RestController
@RequestMapping("/stuEvaluate")
@Api(value = "/stuEvaluate",description = "学生评价信息模块")
public class StuEvaluateController {
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;

    @PostMapping("/findStuEvaluateContentListByCondition")
    @ApiOperation(value = "查找评价列表(传入evaluateState:'2' 已评价)", notes = "返回响应对象,不包含总条数")
    public ResponseJson findStuEvaluateContentListByCondition(
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContent.setStudentId(myStudentId());
        stuEvaluateContent.setEvaluateState("2");
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
        long count=stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
        return new ResponseJson(data,count);
    }

}
