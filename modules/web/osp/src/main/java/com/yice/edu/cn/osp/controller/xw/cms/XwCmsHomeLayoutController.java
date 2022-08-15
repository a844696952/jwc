package com.yice.edu.cn.osp.controller.xw.cms;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHomeLayout;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsLayoutCondition;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsHomeLayoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwCmsHomeLayout")
@Api(value = "/xwCmsHomeLayout",description = "首页布局模块")
public class XwCmsHomeLayoutController {

    @Autowired
    private XwCmsHomeLayoutService cmsHomeLayoutService;


    @PostMapping("/saveCmsHomeLayout")
    @ApiOperation(value = "修改或添加处理CMS栏目和首页布局", notes = "校务CMS栏目对象集合必传")
    public ResponseJson updateOrSaveCmsHomeLayout(
            @ApiParam(value = "校务CMS栏目对象集合")
            @RequestBody List<Map<String,Object>> rows
    ) {
        Boolean flag = cmsHomeLayoutService.saveCmsHomeLayout(rows,mySchoolId());
        if(flag){
            return new ResponseJson(true,"修改成功");
        }else {
            return new ResponseJson(false,"首页布局无栏目");
        }
    }

    @GetMapping("/findAllCmsHomeLayout")
    @ApiOperation(value = "查询首页布局", notes = "返回校务CMS首页布局列表")
    public ResponseJson findAllCmsHomeLayout(){
        List<Map<String,Object>> rows = cmsHomeLayoutService.findAllCmsHomeLayout(mySchoolId());
        return new ResponseJson(rows);
    }

    @PostMapping("/ignore/initCmsHomeLayout")
    @ApiOperation(value = "初始化学校CMS首页布局", notes = "返回成功或者失败")
    public Boolean initCmsHomeLayout(){
        return cmsHomeLayoutService.initCmsHomeLayout(mySchoolId());
    }

    @PostMapping("/findHomeLayoutList")
    @ApiOperation(value = "查找左右侧栏目", notes = "返回成功或者失败")
    public ResponseJson findHomeLayoutList(){
        List<XwCmsColumn> data = cmsHomeLayoutService.findLiftHomeLayoutList( mySchoolId());
        List<XwCmsColumn> data1 = cmsHomeLayoutService.findRigntHomeLayoutList( mySchoolId());
        return new ResponseJson(data,data1);
    }

    @PostMapping("/updateXwCmsHomeLayout")
    @ApiOperation(value = "修改校务CMS首页布局中单个栏目", notes = "cms首页布局对象必传")
    public ResponseJson updateXwCmsHomeLayout(
            @ApiParam(value = "cms首页布局对象")
            @RequestBody XwCmsHomeLayout xwCmsHomeLayout
    ){
        cmsHomeLayoutService.updateXwCmsHomeLayout(xwCmsHomeLayout);
        return new ResponseJson();
    }

    @PostMapping("/addOrDeleteXwCmsHomeLayoutRow")
    @ApiOperation(value = "首页布局新增或删除行", notes = "首页布局新增或删除行条件对象必传")
    public ResponseJson addOrDeleteXwCmsHomeLayoutRow(
            @ApiParam(value = "首页布局新增或删除行条件对象,ture -> 新增,false -> 删除")
            @RequestBody XwCmsLayoutCondition xwCmsLayoutCondition
    ){
        xwCmsLayoutCondition.setSchoolId(mySchoolId());
        if(cmsHomeLayoutService.addOrDeleteXwCmsHomeLayoutRow(xwCmsLayoutCondition)){
            return new ResponseJson(true,"操作成功");
        }else {
            return new ResponseJson(false,"操作失败");
        }
    }

    @GetMapping("/checkXwCmsHomeLayout/{columnId}")
    @ApiOperation(value = "检查数据是否存在", notes = "返回是否存在")
    public ResponseJson checkXwCmsHomeLayout(
            @ApiParam(value = "需要用到的columnId", required = true)
            @PathVariable String columnId
    ){
        if (cmsHomeLayoutService.findCmsHomeLayoutByCid(columnId)){
            return new ResponseJson(true);
        }else {
            return new ResponseJson(false);
        }
    }

    @PostMapping("/checkCmsHomeLayouTopRow")
    @ApiOperation(value = "校验布局首行删除", notes = "返回布尔值")
    public ResponseJson checkCmsHomeLayouTopRow(
            @ApiParam(value = "布局对象")
            @RequestBody XwCmsHomeLayout xwCmsHomeLayout
    ){
        xwCmsHomeLayout.setSchoolId(mySchoolId());
        if(cmsHomeLayoutService.checkCmsHomeLayouTopRow(xwCmsHomeLayout)){
            return new ResponseJson(true);
        }else {
            return new ResponseJson(false);
        }
    }
}
