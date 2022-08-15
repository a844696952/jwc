package com.yice.edu.cn.osp.controller.wb.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.wb.latticePager.DmPagerNumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmPagerNumber")
@Api(value = "/dmPagerNumber",description = "点阵试卷页码表模块")
public class DmPagerNumberController {
    @Autowired
    private DmPagerNumberService dmPagerNumberService;

    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/saveDmPagerNumber")
    @ApiOperation(value = "保存点阵试卷页码表对象", notes = "返回保存好的点阵试卷页码表对象", response=ResponseJson.class)
    public ResponseJson saveDmPagerNumber(
            @ApiParam(value = "点阵试卷页码表对象", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumber.setSchoolId(mySchoolId());
        dmPagerNumber.setTeacherId(myId());
        curSchoolYearService.setSchoolYearTerm(dmPagerNumber,mySchoolId());
        dmPagerNumberService.saveDmPagerNumber(dmPagerNumber);
        return new ResponseJson();
    }

    @GetMapping("/findDmPagerNumberById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找点阵试卷页码表", notes = "返回响应对象", response=ResponseJson.class)
    public ResponseJson findDmPagerNumberById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmPagerNumber dmPagerNumber=dmPagerNumberService.findDmPagerNumberById(id);
        return new ResponseJson(dmPagerNumber);
    }

    @PostMapping("/updateDmPagerNumber")
    @ApiOperation(value = "修改点阵试卷页码表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmPagerNumber(
            @ApiParam(value = "被修改的点阵试卷页码表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.updateDmPagerNumber(dmPagerNumber);
        return new ResponseJson();
    }

    @PostMapping("/updateDmPagerNumberForAll")
    @ApiOperation(value = "修改点阵试卷页码表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmPagerNumberForAll(
            @ApiParam(value = "被修改的点阵试卷页码表对象", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.updateDmPagerNumberForAll(dmPagerNumber);
        return new ResponseJson();
    }


    @PostMapping("/findDmPagerNumbersByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷页码表", notes = "返回响应对象", response=ResponseJson.class)
    public ResponseJson findDmPagerNumbersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumber.setSchoolId(mySchoolId());
        dmPagerNumber.setTeacherId(myId());
        curSchoolYearService.setSchoolYearId(dmPagerNumber,mySchoolId());
        List<DmPagerNumber> data=dmPagerNumberService.findDmPagerNumberListByCondition(dmPagerNumber);
        long count=dmPagerNumberService.findDmPagerNumberCountByCondition(dmPagerNumber);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmPagerNumberByCondition")
    @ApiOperation(value = "根据条件查找单个点阵试卷页码表,结果必须为单条数据", notes = "没有时返回空", response=ResponseJson.class)
    public ResponseJson findOneDmPagerNumberByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmPagerNumber dmPagerNumber){
        DmPagerNumber one=dmPagerNumberService.findOneDmPagerNumberByCondition(dmPagerNumber);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmPagerNumber/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmPagerNumber(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmPagerNumberService.deleteDmPagerNumber(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmPagerNumberListByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷页码表列表", notes = "返回响应对象,不包含总条数", response=ResponseJson.class)
    public ResponseJson findDmPagerNumberListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPagerNumber dmPagerNumber){
       dmPagerNumber.setSchoolId(mySchoolId());
        List<DmPagerNumber> data=dmPagerNumberService.findDmPagerNumberListByCondition(dmPagerNumber);
        return new ResponseJson(data);
    }



    @GetMapping("/findDmPagerBackgroundImg/{id}")
    @ApiOperation(value = "通过id预览试卷只显示绑定页码的试卷", notes = "返回响应对象,只显示绑定页码的试卷", response=ResponseJson.class)
    public ResponseJson findDmPagerBackgroundImg(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        return new ResponseJson(dmPagerNumberService.findDmPagerBackgroundImg(id));
    }


    @PostMapping("/updateRecover")
    @ApiOperation(value = "点击更近可回收的页码", notes = "返回响应对象", response=ResponseJson.class)
    public ResponseJson updateRecover(
            @ApiParam(value = "点击更近可回收的页码，传当前id", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        return dmPagerNumberService.updateRecover(dmPagerNumber);
    }





}
