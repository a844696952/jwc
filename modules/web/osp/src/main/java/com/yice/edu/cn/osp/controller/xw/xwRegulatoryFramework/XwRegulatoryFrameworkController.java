package com.yice.edu.cn.osp.controller.xw.xwRegulatoryFramework;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.osp.service.xw.xwClassifiedManagement.XwClassifiedManagementService;
import com.yice.edu.cn.osp.service.xw.xwRegulatoryFramework.XwRegulatoryFrameworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwRegulatoryFramework")
@Api(value = "/xwRegulatoryFramework",description = "规章制度模块")
public class XwRegulatoryFrameworkController {
    @Autowired
    private XwRegulatoryFrameworkService xwRegulatoryFrameworkService;
    @Autowired
    private XwClassifiedManagementService xwClassifiedManagementService;
    @PostMapping("/saveXwRegulatoryFramework")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveXwRegulatoryFramework(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        xwRegulatoryFramework.setSchoolId(mySchoolId());
        XwRegulatoryFramework s=xwRegulatoryFrameworkService.saveXwRegulatoryFramework(xwRegulatoryFramework);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwRegulatoryFrameworkById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findXwRegulatoryFrameworkById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwRegulatoryFramework xwRegulatoryFramework=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkById(id);
        return new ResponseJson(xwRegulatoryFramework);
    }

    @PostMapping("/update/updateXwRegulatoryFramework")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwRegulatoryFramework(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        xwRegulatoryFrameworkService.updateXwRegulatoryFramework(xwRegulatoryFramework);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwRegulatoryFrameworkById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookXwRegulatoryFrameworkById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwRegulatoryFramework xwRegulatoryFramework=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkById(id);
        return new ResponseJson(xwRegulatoryFramework);
    }

    @PostMapping("/find/findXwRegulatoryFrameworksByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwRegulatoryFrameworksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        Pager pager=xwRegulatoryFramework.getPager();
        pager.setLike("theme");
        xwRegulatoryFramework.setPager(pager);
        xwRegulatoryFramework.setSchoolId(mySchoolId());
        List<XwRegulatoryFramework> data=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
        long count=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkCountByCondition(xwRegulatoryFramework);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwRegulatoryFrameworkByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneXwRegulatoryFrameworkByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        XwRegulatoryFramework one=xwRegulatoryFrameworkService.findOneXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwRegulatoryFramework/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwRegulatoryFramework(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwRegulatoryFrameworkService.deleteXwRegulatoryFramework(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwRegulatoryFrameworkListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwRegulatoryFrameworkListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        List<XwRegulatoryFramework> data=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
        long count=xwRegulatoryFrameworkService.findXwRegulatoryFrameworkCountByCondition(xwRegulatoryFramework);
        return new ResponseJson(data,count);
    }



    /*查询类表的所有类名*/
    @GetMapping("/update/findXwClassifiedManagementsByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwClassifiedManagementsByCondition2(){
        XwClassifiedManagement xwClassifiedManagement = new XwClassifiedManagement();
        xwClassifiedManagement.setSchoolId(mySchoolId());
        List<XwClassifiedManagement> data=xwClassifiedManagementService.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
        return new ResponseJson(data);
    }

    //与查询一个前缀  防止只有查看 权限后 报错
    @GetMapping("/find/findXwClassifiedManagementsByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwClassifiedManagementsByCondition3(){
        XwClassifiedManagement xwClassifiedManagement = new XwClassifiedManagement();
        xwClassifiedManagement.setSchoolId(mySchoolId());
        List<XwClassifiedManagement> data=xwClassifiedManagementService.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
        return new ResponseJson(data);
    }

}
