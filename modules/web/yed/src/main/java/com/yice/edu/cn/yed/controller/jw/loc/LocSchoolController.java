package com.yice.edu.cn.yed.controller.jw.loc;

import cn.hutool.json.JSON;
import com.qiniu.util.Json;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.loc.LocSchool;
import com.yice.edu.cn.common.pojo.loc.LocSchoolExt;
import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.yed.service.jw.loc.LocSchoolService;
import com.yice.edu.cn.yed.service.jw.loc.LocSchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/locSchool")
@Api(value = "/locSchool",description = "学校模块")
public class LocSchoolController {
    @Autowired
    private LocSchoolService locSchoolService;
    @Autowired
    private LocSchoolYearService locSchoolYearService;
    @PostMapping("/saveLocSchool")
    @ApiOperation(value = "保存学校对象", notes = "返回保存好的学校对象", response=LocSchool.class)
    public ResponseJson saveLocSchool(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody LocSchool locSchool){
        LocSchool s=locSchoolService.saveLocSchool(locSchool);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findLocSchoolById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校", notes = "返回响应对象", response=LocSchool.class)
    public ResponseJson findLocSchoolById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        LocSchool locSchool=locSchoolService.findLocSchoolById(id);
        return new ResponseJson(locSchool);
    }

    @PostMapping("/update/updateLocSchool")
    @ApiOperation(value = "修改学校对象非空字段", notes = "返回响应对象")
    public ResponseJson updateLocSchool(
            @ApiParam(value = "被修改的学校对象,对象属性不为空则修改", required = true)
            @RequestBody LocSchool locSchool){
        locSchoolService.updateLocSchool(locSchool);
        return new ResponseJson();
    }

    @PostMapping("/update/updateLocSchoolForAll")
    @ApiOperation(value = "修改学校对象所有字段", notes = "返回响应对象")
    public ResponseJson updateLocSchoolForAll(
            @ApiParam(value = "被修改的学校对象", required = true)
            @RequestBody LocSchool locSchool){
        long count = locSchoolService.findIpRepetitionCount(locSchool);
        if(count>0){
            return new ResponseJson(false,"ip已存在，请修改服务器ip");
        }
        locSchoolService.updateLocSchoolForAll(locSchool);
        return new ResponseJson();
    }

    @GetMapping("/look/lookLocSchoolById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校", notes = "返回响应对象", response=LocSchool.class)
    public ResponseJson lookLocSchoolById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        LocSchool locSchool=locSchoolService.findLocSchoolById(id);
        return new ResponseJson(locSchool);
    }

    @PostMapping("/findLocSchoolsByCondition")
    @ApiOperation(value = "根据条件查找学校", notes = "返回响应对象", response=LocSchool.class)
    public ResponseJson findLocSchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LocSchool locSchool){
        List<LocSchool> data=locSchoolService.findLocSchoolListByCondition(locSchool);
        long count=locSchoolService.findLocSchoolCountByCondition(locSchool);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneLocSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个学校,结果必须为单条数据", notes = "没有时返回空", response=LocSchool.class)
    public ResponseJson findOneLocSchoolByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LocSchool locSchool){
        LocSchool one=locSchoolService.findOneLocSchoolByCondition(locSchool);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteLocSchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLocSchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        locSchoolService.deleteLocSchoolAndLocSchoolYear(id);
        return new ResponseJson();
    }


    @PostMapping("/findLocSchoolListByCondition")
    @ApiOperation(value = "根据条件查找学校列表", notes = "返回响应对象,不包含总条数", response=LocSchool.class)
    public ResponseJson findLocSchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LocSchool locSchool){
        List<LocSchool> data=locSchoolService.findLocSchoolListByCondition(locSchool);
        return new ResponseJson(data);
    }


    @PostMapping("/saveLocSchoolAndSaveLocSchoolYear")
    @ApiModelProperty(value = "添加本地化部署学校",notes = "无返回值")
    public ResponseJson saveLocSchoolAndSaveLocSchoolYear(
            @RequestBody LocSchoolExt locSchoolExt
            ){
        long count = locSchoolService.findIpRepetitionCount(locSchoolExt);
        if(count>0){
            return new ResponseJson(false,"ip已存在，请修改服务器ip");
        }
        locSchoolService.saveLocSchoolAndSaveLocSchoolYear(locSchoolExt);
        return new ResponseJson();
    }

    @GetMapping("/findOneLocSchoolByHttps")
    public ResponseJson findList(HttpServletRequest request){
        String ip = HttpKit.getHttpsServletRequestIp(request);
        LocSchool locSchool = new LocSchool();
        locSchool.setIp(ip);
        LocSchool locSchool1 = locSchoolService.findOneLocSchoolByCondition(locSchool);
        if(locSchool1==null){
            return new ResponseJson(false,"未找到对应的学校");
        }
        LocSchoolYear locSchoolYear = new LocSchoolYear();
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortOrder("desc");
        pager.setSortField("fromYear");
        locSchoolYear.setPager(pager);
        locSchoolYear.setSchoolId(locSchool1.getId());
        List<LocSchoolYear> locSchoolYearList = locSchoolYearService.findLocSchoolYearListByCondition(locSchoolYear);
        return new ResponseJson(locSchool1,locSchoolYearList);
    }

}
