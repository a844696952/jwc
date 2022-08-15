package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.xw.service.cms.XwCmsColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsColumn")
@Api(value = "/xwCmsColumn",description = "校务CMS栏目表模块")
public class XwCmsColumnController {
    @Autowired
    private XwCmsColumnService xwCmsColumnService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findXwCmsColumnById/{id}")
    @ApiOperation(value = "通过id查找校务CMS栏目表", notes = "返回校务CMS栏目表对象")
    public XwCmsColumn findXwCmsColumnById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwCmsColumnService.findXwCmsColumnById(id);
    }

    @PostMapping("/saveXwCmsColumn")
    @ApiOperation(value = "保存校务CMS栏目表", notes = "返回校务CMS栏目表对象")
    public Integer saveXwCmsColumn(
            @ApiParam(value = "校务CMS栏目表对象", required = true)
            @RequestBody @Validated(value = GroupOne.class) XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.saveXwCmsColumn(xwCmsColumn);
    }

    @PostMapping("/batchSaveXwCmsColumn")
    @ApiOperation(value = "批量保存校务CMS栏目表")
    public void batchSaveXwCmsColumn(
            @ApiParam(value = "校务CMS栏目表对象集合", required = true)
            @RequestBody List<XwCmsColumn> xwCmsColumns){
        xwCmsColumnService.batchSaveXwCmsColumn(xwCmsColumns);
    }

    @PostMapping("/findXwCmsColumnListByCondition")
    @ApiOperation(value = "根据条件查找校务CMS栏目表列表", notes = "返回校务CMS栏目表列表")
    public List<XwCmsColumn> findXwCmsColumnListByCondition(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,true);
    }

    @PostMapping("/findXwCmsColumnList")
    @ApiOperation(value = "根据条件查找校务CMS栏目表列表", notes = "返回校务CMS栏目表列表")
    public List<XwCmsColumn> findXwCmsColumnList(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,false);
    }


    @PostMapping("/move")
    @ApiOperation(value = "根据条件移动", notes = "返回移动后列表")
    public Boolean move(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.move(xwCmsColumn);
    }





    @PostMapping("/findXwCmsColumnCountByCondition")
    @ApiOperation(value = "根据条件查找校务CMS栏目表列表个数", notes = "返回校务CMS栏目表总个数")
    public long findXwCmsColumnCountByCondition(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.findXwCmsColumnCountByCondition(xwCmsColumn);
    }

    @PostMapping("/updateXwCmsColumn")
    @ApiOperation(value = "修改校务CMS栏目表有值的字段", notes = "校务CMS栏目表对象必传")
    public Boolean updateXwCmsColumn(
            @ApiParam(value = "校务CMS栏目表对象,对象属性不为空则修改", required = true)
            @RequestBody @Validated(value = GroupTwo.class) XwCmsColumn xwCmsColumn){
       return xwCmsColumnService.updateXwCmsColumn(xwCmsColumn);
    }
    @PostMapping("/updateXwCmsColumnForAll")
    @ApiOperation(value = "修改校务CMS栏目表所有字段", notes = "校务CMS栏目表对象必传")
    public void updateXwCmsColumnForAll(
            @ApiParam(value = "校务CMS栏目表对象", required = true)
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumnService.updateXwCmsColumnForAll(xwCmsColumn);
    }

    @GetMapping("/deleteXwCmsColumn/{id}")
    @ApiOperation(value = "通过id删除校务CMS栏目表")
    public void deleteXwCmsColumn(
            @ApiParam(value = "校务CMS栏目表对象", required = true)
            @PathVariable String id){
        xwCmsColumnService.deleteXwCmsColumn(id);
    }
    @PostMapping("/deleteXwCmsColumnByCondition")
    @ApiOperation(value = "根据条件删除校务CMS栏目表")
    public void deleteXwCmsColumnByCondition(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumnService.deleteXwCmsColumnByCondition(xwCmsColumn);
    }
    @PostMapping("/findOneXwCmsColumnByCondition")
    @ApiOperation(value = "根据条件查找单个校务CMS栏目表,结果必须为单条数据", notes = "返回单个校务CMS栏目表,没有时为空")
    public XwCmsColumn findOneXwCmsColumnByCondition(
            @ApiParam(value = "校务CMS栏目表对象")
            @RequestBody XwCmsColumn xwCmsColumn){
        return xwCmsColumnService.findOneXwCmsColumnByCondition(xwCmsColumn);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/initXwCmsColumn/{schoolId}")
    @ApiOperation(value = "初始化学校CMS门户菜单栏目", notes = "返回成功或者失败")
    public Boolean initXwCmsColumn(
            @ApiParam(value = "需要用到的schoolId", required = true)
            @PathVariable String schoolId){
        return xwCmsColumnService.initXwCmsColumn(schoolId);
    }


    @GetMapping("/selectBannerAndApp/{mySchoolId}")
    @ApiOperation(value = "通过id删除校务CMS栏目表")
    public List<XwCmsColumn> selectBannerAndApp(
            @ApiParam(value = "校务CMS栏目表对象", required = true)
            @PathVariable String mySchoolId){
        return xwCmsColumnService.selectBannerAndApp(mySchoolId);
    }


}
