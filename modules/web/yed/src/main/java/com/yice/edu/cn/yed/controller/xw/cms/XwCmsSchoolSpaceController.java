package com.yice.edu.cn.yed.controller.xw.cms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.yed.service.xw.cms.XwCmsSchoolSpaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/xwCmsSchoolSpace")
@Api(value = "/xwCmsSchoolSpace",description = "CMS学校空间表模块")
public class XwCmsSchoolSpaceController {
    @Autowired
    private XwCmsSchoolSpaceService xwCmsSchoolSpaceService;

    @PostMapping("/saveXwCmsSchoolSpace")
    @ApiOperation(value = "保存CMS学校空间表对象", notes = "返回保存好的CMS学校空间表对象", response= XwCmsSchoolSpace.class)
    public ResponseJson saveXwCmsSchoolSpace(
            @ApiParam(value = "CMS学校空间表对象", required = true)
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        if(Objects.nonNull(xwCmsSchoolSpace) && StringUtils.isNotEmpty(xwCmsSchoolSpace.getSecondDomain())){
            XwCmsSchoolSpace xwCmsSchoolSpaceByDomain = xwCmsSchoolSpaceService.findXwCmsSchoolSpaceByDomain(xwCmsSchoolSpace.getSecondDomain());
            if(Objects.nonNull(xwCmsSchoolSpaceByDomain)){
                return new ResponseJson(false,"当前域名被占用");
            }
            xwCmsSchoolSpace.setOperateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
            xwCmsSchoolSpaceService.saveXwCmsSchoolSpace(xwCmsSchoolSpace);
            return new ResponseJson(true,"新增成功!");
        }
        return new ResponseJson(false,"二级域名不能为空");
    }

    @GetMapping("/update/findXwCmsSchoolSpaceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找CMS学校空间表", notes = "返回响应对象", response=XwCmsSchoolSpace.class)
    public ResponseJson findXwCmsSchoolSpaceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsSchoolSpace xwCmsSchoolSpace=xwCmsSchoolSpaceService.findXwCmsSchoolSpaceById(id);
        return new ResponseJson(xwCmsSchoolSpace);
    }

    @PostMapping("/update/updateXwCmsSchoolSpace")
    @ApiOperation(value = "修改CMS学校空间表对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsSchoolSpace(
            @ApiParam(value = "被修改的CMS学校空间表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpace.setOperateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        if(Objects.nonNull(xwCmsSchoolSpace) && StringUtils.isNotEmpty(xwCmsSchoolSpace.getSecondDomain()) && StringUtils.isNotEmpty(xwCmsSchoolSpace.getId())){
            XwCmsSchoolSpace xwCmsSchoolSpaceByDomain = xwCmsSchoolSpaceService.findXwCmsSchoolSpaceByDomain(xwCmsSchoolSpace.getSecondDomain());
            if(Objects.nonNull(xwCmsSchoolSpaceByDomain) && !Objects.equals(xwCmsSchoolSpace.getId(),xwCmsSchoolSpaceByDomain.getId())){
                return new ResponseJson(false,"当前域名被占用");
            }
        }
        xwCmsSchoolSpaceService.updateXwCmsSchoolSpace(xwCmsSchoolSpace);
        return new ResponseJson();
    }

    @PostMapping("/update/updateXwCmsSchoolSpaceBatch")
    @ApiOperation(value = "批量修改CMS学校空间表对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsSchoolSpaceBatch(
            @ApiParam(value = "批量关闭学校空间", required = true)
            @RequestBody List<String> ids){
        if(CollUtil.isNotEmpty(ids)){
            ids.forEach(x->{
                XwCmsSchoolSpace xwCmsSchoolSpace=new XwCmsSchoolSpace();
                xwCmsSchoolSpace.setId(x);
                xwCmsSchoolSpace.setOperateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
                xwCmsSchoolSpace.setStatus(0);
                xwCmsSchoolSpaceService.updateXwCmsSchoolSpace(xwCmsSchoolSpace);
            });
        }
        return new ResponseJson(true,"新增成功!");
    }


    @PostMapping("/checkSecondDomain/{secondDomain}")
    @ApiOperation(value = "检测当前域名是否被占用", notes = "true-没占用 false被占用")
    public ResponseJson checkSecondDomain(
            @ApiParam(value = "查询二级域名是否被占用", required = true)
            @PathVariable("secondDomain")String secondDomain){
        if(StringUtils.isNotEmpty(secondDomain)){
            XwCmsSchoolSpace xwCmsSchoolSpaceByDomain = xwCmsSchoolSpaceService.findXwCmsSchoolSpaceByDomain(secondDomain);
            if(Objects.nonNull(xwCmsSchoolSpaceByDomain)){
                return new ResponseJson(false,"域名被占用");
            }
        }
        return new ResponseJson(true,"域名可用");
    }


    @GetMapping("/look/lookXwCmsSchoolSpaceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找CMS学校空间表", notes = "返回响应对象", response=XwCmsSchoolSpace.class)
    public ResponseJson lookXwCmsSchoolSpaceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsSchoolSpace xwCmsSchoolSpace=xwCmsSchoolSpaceService.findXwCmsSchoolSpaceById(id);
        return new ResponseJson(xwCmsSchoolSpace);
    }

    @PostMapping("/findXwCmsSchoolSpacesByCondition")
    @ApiOperation(value = "根据条件查找CMS学校空间表", notes = "返回响应对象", response=XwCmsSchoolSpace.class)
    public ResponseJson findXwCmsSchoolSpacesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpace.setOutTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd"));
        xwCmsSchoolSpace.setSchoolStatus("42");
        List<XwCmsSchoolSpace> data=xwCmsSchoolSpaceService.findXwCmsSchoolSpaceListByCondition(xwCmsSchoolSpace);
        long count=xwCmsSchoolSpaceService.findXwCmsSchoolSpaceCountByCondition(xwCmsSchoolSpace);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwCmsSchoolSpaceByCondition")
    @ApiOperation(value = "根据条件查找单个CMS学校空间表,结果必须为单条数据", notes = "没有时返回空", response=XwCmsSchoolSpace.class)
    public ResponseJson findOneXwCmsSchoolSpaceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        XwCmsSchoolSpace one=xwCmsSchoolSpaceService.findOneXwCmsSchoolSpaceByCondition(xwCmsSchoolSpace);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwCmsSchoolSpace/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsSchoolSpace(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwCmsSchoolSpaceService.deleteXwCmsSchoolSpace(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwCmsSchoolSpaceListByCondition")
    @ApiOperation(value = "根据条件查找CMS学校空间表列表", notes = "返回响应对象,不包含总条数", response=XwCmsSchoolSpace.class)
    public ResponseJson findXwCmsSchoolSpaceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsSchoolSpace xwCmsSchoolSpace){
        xwCmsSchoolSpace.setOutTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd"));
        xwCmsSchoolSpace.setSchoolStatus("42");
        List<XwCmsSchoolSpace> data=xwCmsSchoolSpaceService.findXwCmsSchoolSpaceListByCondition(xwCmsSchoolSpace);
        return new ResponseJson(data);
    }

    @PostMapping("/findProvinceList")
    @ApiOperation(value = "返回当前省份列表", notes = "返回省份列表")
    public ResponseJson findProvinceList(){
        List<XwCmsSchoolSpace> provinceList = xwCmsSchoolSpaceService.findProvinceList();
        return new ResponseJson(provinceList);
    }


}
