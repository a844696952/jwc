package com.yice.edu.cn.osp.controller.jw.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import com.yice.edu.cn.osp.service.jw.classSchedule.PastInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/pastInit")
@Api(value = "/pastInit",description = "往期课表的节数模块")
public class PastInitController {
    @Autowired
    private PastInitService pastInitService;
    @PostMapping("/savePastInit")
    @ApiOperation(value = "保存往期课表的节数对象", notes = "返回保存好的往期课表的节数对象", response=PastInit.class)
    public ResponseJson savePastInit(
            @ApiParam(value = "往期课表的节数对象", required = true)
            @RequestBody PastInit pastInit){
       pastInit.setSchoolId(mySchoolId());
        pastInitService.savePastInit(pastInit);
        return new ResponseJson();
    }

    @GetMapping("/findPastInitById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找往期课表的节数", notes = "返回响应对象", response=PastInit.class)
    public ResponseJson findPastInitById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PastInit pastInit=pastInitService.findPastInitById(id);
        return new ResponseJson(pastInit);
    }

    @PostMapping("/updatePastInit")
    @ApiOperation(value = "修改往期课表的节数对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePastInit(
            @ApiParam(value = "被修改的往期课表的节数对象,对象属性不为空则修改", required = true)
            @RequestBody PastInit pastInit){
        pastInitService.updatePastInit(pastInit);
        return new ResponseJson();
    }

    @PostMapping("/updatePastInitForAll")
    @ApiOperation(value = "修改往期课表的节数对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePastInitForAll(
            @ApiParam(value = "被修改的往期课表的节数对象", required = true)
            @RequestBody PastInit pastInit){
        pastInitService.updatePastInitForAll(pastInit);
        return new ResponseJson();
    }


    @PostMapping("/findPastInitsByCondition")
    @ApiOperation(value = "根据条件查找往期课表的节数", notes = "返回响应对象", response=PastInit.class)
    public ResponseJson findPastInitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PastInit pastInit){
       pastInit.setSchoolId(mySchoolId());
        List<PastInit> data=pastInitService.findPastInitListByCondition(pastInit);
        long count=pastInitService.findPastInitCountByCondition(pastInit);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePastInitByCondition")
    @ApiOperation(value = "根据条件查找单个往期课表的节数,结果必须为单条数据", notes = "没有时返回空", response=PastInit.class)
    public ResponseJson findOnePastInitByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PastInit pastInit){
        PastInit one=pastInitService.findOnePastInitByCondition(pastInit);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePastInit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePastInit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pastInitService.deletePastInit(id);
        return new ResponseJson();
    }


    @PostMapping("/findPastInitListByCondition")
    @ApiOperation(value = "根据条件查找往期课表的节数列表", notes = "返回响应对象,不包含总条数", response=PastInit.class)
    public ResponseJson findPastInitListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PastInit pastInit){
       pastInit.setSchoolId(mySchoolId());
        List<PastInit> data=pastInitService.findPastInitListByCondition(pastInit);
        return new ResponseJson(data);
    }



}
