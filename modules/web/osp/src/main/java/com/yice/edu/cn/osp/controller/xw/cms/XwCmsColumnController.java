package com.yice.edu.cn.osp.controller.xw.cms;

import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * @author  xiezhi
 * */
@RestController
@RequestMapping("/xwCmsColumn")
@Api(value = "/xwCmsColumn",description = "校务CMS栏目表模块")
public class XwCmsColumnController {
    @Autowired
    private XwCmsColumnService xwCmsColumnService;

    @PostMapping("/saveXwCmsColumn")
    @ApiOperation(value = "保存校务CMS栏目表对象", notes = "返回保存好的校务CMS栏目表对象", response= XwCmsColumn.class)
    public ResponseJson saveXwCmsColumn(
            @ApiParam(value = "校务CMS栏目表对象", required = true)
            @RequestBody XwCmsColumn xwCmsColumn){

        if(ObjectUtil.equal("应用中心",xwCmsColumn.getColumnName()) || ObjectUtil.equal("Banner",xwCmsColumn.getColumnName())){
            return new ResponseJson(false,"栏目命名错误");
        }
        xwCmsColumn.setSchoolId(mySchoolId());
        Integer integer = xwCmsColumnService.saveXwCmsColumn(xwCmsColumn);
        if(integer == 10000){
            return new ResponseJson(false,"根目录异常状态");
        }else if(integer == 10001){
            return new ResponseJson(false,"同级栏目命名错误");
        }else{
            XwCmsColumn find = new XwCmsColumn();
            find.setSchoolId(mySchoolId());
            find.setBanner(false);
            Pager pager = new Pager();
            pager.setPageSize(100);
            find.setPager(pager);
            return new ResponseJson(xwCmsColumnService.findXwCmsColumnListByCondition(find));
        }
    }

    @GetMapping("/update/findXwCmsColumnById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找校务CMS栏目表", notes = "返回响应对象", response= XwCmsColumn.class)
    public ResponseJson findXwCmsColumnById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsColumn xwCmsColumn=xwCmsColumnService.findXwCmsColumnById(id);
        return new ResponseJson(xwCmsColumn);
    }

    @PostMapping("/update/updateXwCmsColumn")
    @ApiOperation(value = "修改校务CMS栏目表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateXwCmsColumn(
            @ApiParam(value = "被修改的校务CMS栏目表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsColumn xwCmsColumn){

        if(ObjectUtil.equal("应用中心",xwCmsColumn.getColumnName()) || ObjectUtil.equal("Banner",xwCmsColumn.getColumnName())){
            return new ResponseJson(false,"栏目命名错误");
        }
        if(!xwCmsColumnService.updateXwCmsColumn(xwCmsColumn)){
            return new ResponseJson(false,"栏目名称重复，请重新输入");
        }
        return new ResponseJson();
    }

    @PostMapping("/update/updateXwCmsColumnForAll")
    @ApiOperation(value = "修改校务CMS栏目表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateXwCmsColumnForAll(
            @ApiParam(value = "被修改的校务CMS栏目表对象", required = true)
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumnService.updateXwCmsColumnForAll(xwCmsColumn);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwCmsColumnById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找校务CMS栏目表", notes = "返回响应对象", response= XwCmsColumn.class)
    public ResponseJson lookXwCmsColumnById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsColumn xwCmsColumn=xwCmsColumnService.findXwCmsColumnById(id);
        return new ResponseJson(xwCmsColumn);
    }

    @PostMapping("/findXwCmsColumnsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS栏目表", notes = "返回响应对象", response= XwCmsColumn.class)
    public ResponseJson findXwCmsColumnsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsColumn xwCmsColumn){
       xwCmsColumn.setSchoolId(mySchoolId());
        List<XwCmsColumn> data=xwCmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn);
        long count=xwCmsColumnService.findXwCmsColumnCountByCondition(xwCmsColumn);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwCmsColumnByCondition")
    @ApiOperation(value = "根据条件查找单个校务CMS栏目表,结果必须为单条数据", notes = "没有时返回空", response= XwCmsColumn.class)
    public ResponseJson findOneXwCmsColumnByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsColumn xwCmsColumn){
        XwCmsColumn one=xwCmsColumnService.findOneXwCmsColumnByCondition(xwCmsColumn);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwCmsColumn/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsColumn(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwCmsColumnService.deleteXwCmsColumn(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwCmsColumnList")
    @ApiOperation(value = "根据条件查找校务CMS栏目表列表", notes = "返回响应对象,不包含总条数", response= XwCmsColumn.class)
    public ResponseJson findXwCmsColumnList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumn.setSchoolId(mySchoolId());
        List<XwCmsColumn> data=xwCmsColumnService.findXwCmsColumnList(xwCmsColumn);
        return new ResponseJson(data);
    }


    @PostMapping("/move")
    @ApiOperation(value = "根据请求移动栏目", notes = "返回移动状态", response= XwCmsColumn.class)
    public ResponseJson move(
            @ApiParam(value = "banner（false 上移动  true 下移动）")
            @Validated
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumn.setSchoolId(mySchoolId());
        if(xwCmsColumnService.move(xwCmsColumn)){
            return new ResponseJson(true,"移动成功");
        }else{
            return new ResponseJson(false,"操作错误无法移动");
        }
    }



    @GetMapping("/selectBannerAndApp")
    @ApiOperation(value = "获取Banner和应用中心栏目id", notes = "返回响应对象")
    public ResponseJson selectBannerAndApp(){
        List<XwCmsColumn> xwCmsColumns = xwCmsColumnService.selectBannerAndApp(mySchoolId());
        return new ResponseJson(xwCmsColumns);
    }






}
