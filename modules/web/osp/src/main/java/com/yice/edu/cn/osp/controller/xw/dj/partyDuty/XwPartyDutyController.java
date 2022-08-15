package com.yice.edu.cn.osp.controller.xw.dj.partyDuty;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import com.yice.edu.cn.osp.service.xw.dj.partyDuty.XwPartyDutyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwPartyDuty")
@Api(value = "/xwPartyDuty",description = "党员职务管理模块")
public class XwPartyDutyController {
    private static final Object SYNCOBJ = new Object();
    @Autowired
    private XwPartyDutyService xwPartyDutyService;

    @PostMapping("/saveXwPartyDuty")
    @ApiOperation(value = "保存党员职务管理对象", notes = "返回保存好的党员职务管理对象", response=XwPartyDuty.class)
    public ResponseJson saveXwPartyDuty(
            @ApiParam(value = "党员职务管理对象", required = true)
            @RequestBody XwPartyDuty xwPartyDuty){
        synchronized (SYNCOBJ){
            ResponseJson responseJson = new ResponseJson();
            xwPartyDuty.setSchoolId(mySchoolId());
            XwPartyDuty queryParam = new XwPartyDuty();
            queryParam.setSchoolId(mySchoolId());
            queryParam.setName(xwPartyDuty.getName());
            final XwPartyDuty record = xwPartyDutyService.findOneXwPartyDutyByCondition(queryParam);
            if(record !=null){
                responseJson.getResult().setSuccess(false);
                responseJson.getResult().setMsg("党员职务名称不能重复！");
            }else{
                XwPartyDuty s=xwPartyDutyService.saveXwPartyDuty(xwPartyDuty);
                responseJson.setData(s);
            }
            return responseJson;
        }
    }

    @GetMapping("/update/findXwPartyDutyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党员职务管理", notes = "返回响应对象", response=XwPartyDuty.class)
    public ResponseJson findXwPartyDutyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwPartyDuty xwPartyDuty=xwPartyDutyService.findXwPartyDutyById(id);
        return new ResponseJson(xwPartyDuty);
    }

    @PostMapping("/update/updateXwPartyDuty")
    @ApiOperation(value = "修改党员职务管理对象", notes = "返回响应对象")
    public ResponseJson updateXwPartyDuty(
            @ApiParam(value = "被修改的党员职务管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyDuty xwPartyDuty){
        synchronized (SYNCOBJ){
            ResponseJson responseJson = new ResponseJson();
            xwPartyDuty.setCreateTime(null);
            xwPartyDuty.setUpdateTime(null);
            XwPartyDuty record = xwPartyDutyService.findXwPartyDutyByName(xwPartyDuty.getName());
            if(record !=null){
                responseJson.getResult().setSuccess(false);
                responseJson.getResult().setMsg("党员职务名称不能重复！");
            }else{
                xwPartyDutyService.updateXwPartyDuty(xwPartyDuty);
            }
            return responseJson;
        }
    }

    @GetMapping("/look/lookXwPartyDutyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党员职务管理", notes = "返回响应对象", response=XwPartyDuty.class)
    public ResponseJson lookXwPartyDutyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwPartyDuty xwPartyDuty=xwPartyDutyService.findXwPartyDutyById(id);
        return new ResponseJson(xwPartyDuty);
    }

    @PostMapping("/findXwPartyDutysByCondition")
    @ApiOperation(value = "根据条件查找党员职务管理", notes = "返回响应对象", response=XwPartyDuty.class)
    public ResponseJson findXwPartyDutysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyDuty xwPartyDuty){
       xwPartyDuty.setSchoolId(mySchoolId());
        List<XwPartyDuty> data=xwPartyDutyService.findXwPartyDutyListByCondition(xwPartyDuty);
        long count=xwPartyDutyService.findXwPartyDutyCountByCondition(xwPartyDuty);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwPartyDutyByCondition")
    @ApiOperation(value = "根据条件查找单个党员职务管理,结果必须为单条数据", notes = "没有时返回空", response=XwPartyDuty.class)
    public ResponseJson findOneXwPartyDutyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwPartyDuty xwPartyDuty){
        XwPartyDuty one=xwPartyDutyService.findOneXwPartyDutyByCondition(xwPartyDuty);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwPartyDuty/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwPartyDuty(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        return xwPartyDutyService.deleteXwPartyDuty(id);
    }


    @PostMapping("/findXwPartyDutyListByCondition")
    @ApiOperation(value = "根据条件查找党员职务管理列表", notes = "返回响应对象,不包含总条数", response=XwPartyDuty.class)
    public ResponseJson findXwPartyDutyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyDuty xwPartyDuty){
       xwPartyDuty.setSchoolId(mySchoolId());
        List<XwPartyDuty> data=xwPartyDutyService.findXwPartyDutyListByCondition(xwPartyDuty);
        return new ResponseJson(data);
    }



}
