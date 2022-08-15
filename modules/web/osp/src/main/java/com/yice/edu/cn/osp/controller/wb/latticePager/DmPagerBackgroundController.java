package com.yice.edu.cn.osp.controller.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.osp.service.wb.latticePager.DmPagerBackgroundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmPagerBackground")
@Api(value = "/dmPagerBackground",description = "点阵试卷背景表模块")
public class DmPagerBackgroundController {
    @Autowired
    private DmPagerBackgroundService dmPagerBackgroundService;
    @PostMapping("/saveDmPagerBackground")
    @ApiOperation(value = "保存点阵试卷背景表对象", notes = "返回保存好的点阵试卷背景表对象", response=DmPagerBackground.class)
    public ResponseJson saveDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
       dmPagerBackground.setSchoolId(mySchoolId());
        dmPagerBackgroundService.saveDmPagerBackground(dmPagerBackground);
        return new ResponseJson();
    }

    @GetMapping("/findDmPagerBackgroundById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找点阵试卷背景表", notes = "返回响应对象", response=DmPagerBackground.class)
    public ResponseJson findDmPagerBackgroundById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmPagerBackground dmPagerBackground=dmPagerBackgroundService.findDmPagerBackgroundById(id);
        return new ResponseJson(dmPagerBackground);
    }

    @PostMapping("/updateDmPagerBackground")
    @ApiOperation(value = "修改点阵试卷背景表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmPagerBackground(
            @ApiParam(value = "被修改的点阵试卷背景表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.updateDmPagerBackground(dmPagerBackground);
        return new ResponseJson();
    }

    @PostMapping("/updateDmPagerBackgroundForAll")
    @ApiOperation(value = "修改点阵试卷背景表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmPagerBackgroundForAll(
            @ApiParam(value = "被修改的点阵试卷背景表对象", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.updateDmPagerBackgroundForAll(dmPagerBackground);
        return new ResponseJson();
    }


    @PostMapping("/findDmPagerBackgroundsByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷背景表", notes = "返回响应对象", response=DmPagerBackground.class)
    public ResponseJson findDmPagerBackgroundsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPagerBackground dmPagerBackground){
       dmPagerBackground.setSchoolId(mySchoolId());
        List<DmPagerBackground> data=dmPagerBackgroundService.findDmPagerBackgroundListByCondition(dmPagerBackground);
        long count=dmPagerBackgroundService.findDmPagerBackgroundCountByCondition(dmPagerBackground);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmPagerBackgroundByCondition")
    @ApiOperation(value = "根据条件查找单个点阵试卷背景表,结果必须为单条数据", notes = "没有时返回空", response=DmPagerBackground.class)
    public ResponseJson findOneDmPagerBackgroundByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmPagerBackground dmPagerBackground){
        DmPagerBackground one=dmPagerBackgroundService.findOneDmPagerBackgroundByCondition(dmPagerBackground);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmPagerBackground/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmPagerBackground(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmPagerBackgroundService.deleteDmPagerBackground(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmPagerBackgroundListByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷背景表列表", notes = "返回响应对象,不包含总条数", response=DmPagerBackground.class)
    public ResponseJson findDmPagerBackgroundListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPagerBackground dmPagerBackground){
       dmPagerBackground.setSchoolId(mySchoolId());
        List<DmPagerBackground> data=dmPagerBackgroundService.findDmPagerBackgroundListByCondition(dmPagerBackground);
        return new ResponseJson(data);
    }


    @PostMapping("/upload/{latticeId}")
    @ApiOperation(value = "试卷上传背景", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file,
                               @PathVariable("latticeId") String latticeId){
        return  dmPagerBackgroundService.upload(file,latticeId);
    }


    @PostMapping ("/textUpdateDmPagerBackground")
    @ApiOperation(value = "修改点阵试卷背景表对象非空字段", notes = "返回响应对象")
    public ResponseJson textUpdateDmPagerBackground(
            @ApiParam(value = "被修改的点阵试卷背景表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        return dmPagerBackgroundService.textUpdateDmPagerBackground(dmPagerBackground);
    }


    @PostMapping("/reUpload/{id}")
    @ApiOperation(value = "单条记录重新试卷上传背景", notes = "返回状态和资源的url")
    public ResponseJson reUpload(@ApiParam(value = "上传的文件", required = true) MultipartFile file,
                               @PathVariable("id") String id){
        return  dmPagerBackgroundService.reUpload(file,id);
    }




}
