package com.yice.edu.cn.osp.controller.xw.searchOwner;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.osp.service.xw.searchOwner.XwSearchOwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwSearchOwner")
@Api(value = "/xwSearchOwner",description = "寻找失主表模块")
public class XwSearchOwnerController {
    @Autowired
    private XwSearchOwnerService xwSearchOwnerService;


    @PostMapping("/saveXwSearchOwner")
    @ApiOperation(value = "保存寻找失主表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找失主表列表")
    public ResponseJson saveXwSearchOwner(
            @ApiParam(value = "寻找失主表对象", required = true)
            @RequestBody XwSearchOwner xwSearchOwner){
       xwSearchOwner.setSchoolId(mySchoolId());
        XwSearchOwner s=xwSearchOwnerService.saveXwSearchOwner(xwSearchOwner);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwSearchOwnerById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找寻找失主表", notes = "返回响应对象")
    public ResponseJson findXwSearchOwnerById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchOwner xwSearchOwner=xwSearchOwnerService.findXwSearchOwnerById(id);
        return new ResponseJson(xwSearchOwner);
    }

    @PostMapping("/update/updateXwSearchOwner")
    @ApiOperation(value = "修改寻找失主表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找失主表列表")
    public ResponseJson updateXwSearchOwner(
            @ApiParam(value = "被修改的寻找失主表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchOwner xwSearchOwner){
        xwSearchOwnerService.updateXwSearchOwner(xwSearchOwner);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwSearchOwnerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找寻找失主表", notes = "返回响应对象")
    public ResponseJson lookXwSearchOwnerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwSearchOwner xwSearchOwner=xwSearchOwnerService.findXwSearchOwnerById(id);
        return new ResponseJson(xwSearchOwner);
    }

    @PostMapping("/findXwSearchOwnersByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表", notes = "返回响应对象")
    public ResponseJson findXwSearchOwnersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwSearchOwner xwSearchOwner){
       xwSearchOwner.setSchoolId(mySchoolId());

       if( xwSearchOwner.getStartTime() != null && xwSearchOwner.getEndTime() != null){
        String startTime = DateUtil.format( DateUtil.beginOfDay(DateUtil.parse(xwSearchOwner.getStartTime())), "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.format( DateUtil.endOfDay(DateUtil.parse(xwSearchOwner.getEndTime())), "yyyy-MM-dd HH:mm:ss");
        xwSearchOwner.setStartTime(startTime);
        xwSearchOwner.setEndTime(endTime);
       }

        List<XwSearchOwner> data=xwSearchOwnerService.findXwSearchOwnerListByCondition(xwSearchOwner);
        long count=xwSearchOwnerService.findXwSearchOwnerCountByCondition(xwSearchOwner);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwSearchOwnerByCondition")
    @ApiOperation(value = "根据条件查找单个寻找失主表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneXwSearchOwnerByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwSearchOwner xwSearchOwner){
        XwSearchOwner one=xwSearchOwnerService.findOneXwSearchOwnerByCondition(xwSearchOwner);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwSearchOwner/{id}")
    @EccJpush(type = Extras.DM_LOSTANDFOUND_MSG,content = "寻找失主表列表")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwSearchOwner(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwSearchOwnerService.deleteXwSearchOwner(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwSearchOwnerListByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwSearchOwnerListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwSearchOwner xwSearchOwner){
       xwSearchOwner.setSchoolId(mySchoolId());
        List<XwSearchOwner> data=xwSearchOwnerService.findXwSearchOwnerListByCondition(xwSearchOwner);
        return new ResponseJson(data);
    }




}
