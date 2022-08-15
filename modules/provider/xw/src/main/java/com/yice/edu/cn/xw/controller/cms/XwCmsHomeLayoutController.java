package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHomeLayout;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsLayoutCondition;
import com.yice.edu.cn.xw.service.cms.XwCmsColumnService;
import com.yice.edu.cn.xw.service.cms.XwCmsHomeLayoutService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/xwCmsHomeLayout")
public class XwCmsHomeLayoutController {

    @Autowired
    private XwCmsHomeLayoutService cmsHomeLayoutService;
    @Autowired
    private XwCmsColumnService xwCmsColumnService;


    @PostMapping("/saveCmsHomeLayout")
    @ApiOperation(value = "修改或添加处理CMS栏目和首页布局", notes = "校务CMS栏目对象集合必传")
    public Boolean saveCmsHomeLayout(
            @ApiParam(value = "校务CMS栏目对象集合")
            @RequestBody List<Map<String,Object>> rows,
            @RequestParam("schoolId") String schoolId
    ) {
        return cmsHomeLayoutService.saveCmsHomeLayout(rows,schoolId);
    }

    @GetMapping("/findAllCmsHomeLayout/{schoolId}")
    @ApiOperation(value = "查询首页布局", notes = "返回校务CMS首页布局列表")
    public List<Map<String,Object>> findAllCmsHomeLayout(
            @ApiParam(value = "学校id", required = true)
            @PathVariable("schoolId") String schoolId
    ){
        return cmsHomeLayoutService.findAllCmsHomeLayout(schoolId);
    }

    @GetMapping("/findLiftHomeLayoutList/{schoolId}")
    @ApiOperation(value = "根据条件查找cms首页布局表列表", notes = "返回cms首页布局表列表")
    public List<XwCmsColumn> findLiftHomeLayoutList(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId){
        return cmsHomeLayoutService.findLiftHomeLayoutList(schoolId);
    }

    @GetMapping("/findRigntHomeLayoutList/{schoolId}")
    @ApiOperation(value = "根据条件查找cms首页布局表列表", notes = "返回cms首页布局表列表")
    public List<XwCmsColumn> findRigntHomeLayoutList(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId){
        return cmsHomeLayoutService.findRigntHomeLayoutList(schoolId);
    }

    @PostMapping("/updateXwCmsHomeLayout")
    @ApiOperation(value = "修改校务CMS首页布局中单个栏目", notes = "cms首页布局对象必传")
    public void updateXwCmsHomeLayout(
            @ApiParam(value = "cms首页布局对象")
            @RequestBody XwCmsHomeLayout xwCmsHomeLayout
    ){
        cmsHomeLayoutService.updateXwCmsHomeLayout(xwCmsHomeLayout);
    }

    @PostMapping("/addOrDeleteXwCmsHomeLayoutRow")
    @ApiOperation(value = "首页布局新增或删除行", notes = "首页布局新增或删除行条件对象必传")
    public Boolean addOrDeleteXwCmsHomeLayoutRow(
            @ApiParam(value = "首页布局新增或删除行条件对象,ture -> 新增,false -> 删除")
            @RequestBody XwCmsLayoutCondition xwCmsLayoutCondition
    ){
        return cmsHomeLayoutService.addOrDeleteXwCmsHomeLayoutRow(xwCmsLayoutCondition);
    }


    @PostMapping("/initCmsHomeLayout/{schoolId}")
    @ApiOperation(value = "初始化学校CMS首页布局", notes = "返回成功或者失败")
    public Boolean initCmsHomeLayout(
            @ApiParam(value = "需要用到的schoolId", required = true)
            @PathVariable String schoolId){
        return cmsHomeLayoutService.initCmsHomeLayout(schoolId);
    }

    @GetMapping("/checkXwCmsHomeLayout/{columnId}")
    @ApiOperation(value = "检查数据是否存在", notes = "返回是否存在")
    public Boolean checkXwCmsHomeLayout(
            @ApiParam(value = "需要用到的columnId", required = true)
            @PathVariable String columnId
    ){
        XwCmsColumn xwCmsColumn = xwCmsColumnService.findXwCmsColumnById(columnId);
        if(Objects.nonNull(cmsHomeLayoutService.findCmsHomeLayoutByCid(columnId)) && !xwCmsColumn.getParentId().equals("-1")){
            return true;
        }
        return false;
    }

    @PostMapping("/checkCmsHomeLayouTopRow")
    @ApiOperation(value = "校验布局首行删除", notes = "返回布尔值")
    public Boolean checkCmsHomeLayouTopRow(
            @ApiParam(value = "布局对象")
            @RequestBody XwCmsHomeLayout xwCmsHomeLayout
    ){
        return cmsHomeLayoutService.checkCmsHomeLayouTopRow(xwCmsHomeLayout);
    }
}
