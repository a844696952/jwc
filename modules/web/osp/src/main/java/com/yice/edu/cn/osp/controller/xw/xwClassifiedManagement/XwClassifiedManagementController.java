package com.yice.edu.cn.osp.controller.xw.xwClassifiedManagement;

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
@RequestMapping("/xwClassifiedManagement")
@Api(value = "/xwClassifiedManagement",description = "规章分类管理模块")
public class XwClassifiedManagementController {
    @Autowired
    private XwClassifiedManagementService xwClassifiedManagementService;
    @Autowired
    private  XwRegulatoryFrameworkService xwRegulatoryFrameworkService;

    @PostMapping("/saveXwClassifiedManagement")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveXwClassifiedManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        xwClassifiedManagement.setSchoolId(mySchoolId());
        XwClassifiedManagement s=xwClassifiedManagementService.saveXwClassifiedManagement(xwClassifiedManagement);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwClassifiedManagementById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findXwClassifiedManagementById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwClassifiedManagement xwClassifiedManagement=xwClassifiedManagementService.findXwClassifiedManagementById(id);
        return new ResponseJson(xwClassifiedManagement);
    }

    @PostMapping("/update/updateXwClassifiedManagement")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwClassifiedManagement(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        xwClassifiedManagementService.updateXwClassifiedManagement(xwClassifiedManagement);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwClassifiedManagementById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookXwClassifiedManagementById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwClassifiedManagement xwClassifiedManagement=xwClassifiedManagementService.findXwClassifiedManagementById(id);
        return new ResponseJson(xwClassifiedManagement);
    }

    @PostMapping("/findXwClassifiedManagementsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwClassifiedManagementsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        List<XwClassifiedManagement> data=xwClassifiedManagementService.findXwClassifiedManagementListByCondition(xwClassifiedManagement);
        long count=xwClassifiedManagementService.findXwClassifiedManagementCountByCondition(xwClassifiedManagement);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findOneXwClassifiedManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneXwClassifiedManagementByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        XwClassifiedManagement one=xwClassifiedManagementService.findOneXwClassifiedManagementByCondition(xwClassifiedManagement);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwClassifiedManagement/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwClassifiedManagement(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwClassifiedManagementService.deleteXwClassifiedManagement(id);
        return new ResponseJson();
    }


    @GetMapping("/findXwClassifiedManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwClassifiedManagementListByCondition(){
        XwClassifiedManagement xwClassifiedManagement = new XwClassifiedManagement();
        List<XwClassifiedManagement> data=xwClassifiedManagementService.findXwClassifiedManagementListByCondition(xwClassifiedManagement);
        return new ResponseJson(data);
    }



    @PostMapping("/findXwClassifiedManagementsByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwClassifiedManagementsByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        Pager pager = xwClassifiedManagement.getPager();
        pager.setLike("cname");
        xwClassifiedManagement.setPager(pager);
        xwClassifiedManagement.setSchoolId(mySchoolId());
        List<XwClassifiedManagement> data=xwClassifiedManagementService.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
        long count=xwClassifiedManagementService.findXwClassifiedManagementCountByCondition2(xwClassifiedManagement);
        return new ResponseJson(data,count);
    }



    @GetMapping("/findCountByIdForDelete/{id}")
    @ApiOperation(value = "根据id查找", notes = "返回响应对象")
    public ResponseJson findCountByIdForDelete(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @PathVariable String id){
        XwRegulatoryFramework xwRegulatoryFramework =new XwRegulatoryFramework();
        xwRegulatoryFramework.setCmId(id);
        List<XwRegulatoryFramework> data= xwRegulatoryFrameworkService.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
          if(data.size()!=0){
              return new ResponseJson(false);
          }else{
              xwClassifiedManagementService.deleteXwClassifiedManagement(id);
              return new ResponseJson(true);
          }




    }




}
