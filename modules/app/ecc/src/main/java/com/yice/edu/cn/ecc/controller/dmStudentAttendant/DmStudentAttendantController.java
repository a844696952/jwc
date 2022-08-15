package com.yice.edu.cn.ecc.controller.dmStudentAttendant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import com.yice.edu.cn.ecc.service.dmStudentAttendant.DmStudentAttendantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmStudentAttendant")
@Api(value = "/dmStudentAttendant",description = "值日生表模块")
public class DmStudentAttendantController {
    @Autowired
    private DmStudentAttendantService dmStudentAttendantService;

    @PostMapping("/saveDmStudentAttendant")
    @ApiOperation(value = "保存值日生表对象", notes = "返回保存好的值日生表对象", response=DmStudentAttendant.class)
    public ResponseJson saveDmStudentAttendant(
            @ApiParam(value = "值日生表对象", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
       dmStudentAttendant.setSchoolId(mySchoolId());
        DmStudentAttendant s=dmStudentAttendantService.saveDmStudentAttendant(dmStudentAttendant);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmStudentAttendantById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找值日生表", notes = "返回响应对象", response=DmStudentAttendant.class)
    public ResponseJson findDmStudentAttendantById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmStudentAttendant dmStudentAttendant=dmStudentAttendantService.findDmStudentAttendantById(id);
        return new ResponseJson(dmStudentAttendant);
    }

    @PostMapping("/update/updateDmStudentAttendant")
    @ApiOperation(value = "修改值日生表对象所有非@Transient注释的字段", notes = "返回响应对象")
    public ResponseJson updateDmStudentAttendant(
            @ApiParam(value = "被修改的值日生表对象", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.updateDmStudentAttendant(dmStudentAttendant);
        return new ResponseJson();
    }

    @PostMapping("/update/updateDmStudentAttendantForNotNull")
    @ApiOperation(value = "修改值日生表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmStudentAttendantForNotNull(
            @ApiParam(value = "被修改的值日生表对象,对象属性不为空则修改", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.updateDmStudentAttendantForNotNull(dmStudentAttendant);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmStudentAttendantById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找值日生表", notes = "返回响应对象", response=DmStudentAttendant.class)
    public ResponseJson lookDmStudentAttendantById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmStudentAttendant dmStudentAttendant=dmStudentAttendantService.findDmStudentAttendantById(id);
        return new ResponseJson(dmStudentAttendant);
    }

    @PostMapping("/findDmStudentAttendantsByCondition")
    @ApiOperation(value = "根据条件查找值日生表", notes = "返回响应对象", response=DmStudentAttendant.class)
    public ResponseJson findDmStudentAttendantsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmStudentAttendant dmStudentAttendant){
       dmStudentAttendant.setSchoolId(mySchoolId());
        List<DmStudentAttendant> data=dmStudentAttendantService.findDmStudentAttendantListByCondition(dmStudentAttendant);
        long count=data.size();
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmStudentAttendantByCondition")
    @ApiOperation(value = "根据条件查找单个值日生表,结果必须为单条数据", notes = "没有时返回空", response=DmStudentAttendant.class)
    public ResponseJson findOneDmStudentAttendantByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmStudentAttendant dmStudentAttendant){
        DmStudentAttendant one=dmStudentAttendantService.findOneDmStudentAttendantByCondition(dmStudentAttendant);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmStudentAttendant/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmStudentAttendant(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmStudentAttendantService.deleteDmStudentAttendant(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmStudentAttendantListByCondition")
    @ApiOperation(value = "根据条件查找值日生表列表", notes = "返回响应对象,不包含总条数", response=DmStudentAttendant.class)
    public ResponseJson findDmStudentAttendantListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmStudentAttendant dmStudentAttendant){
       dmStudentAttendant.setSchoolId(mySchoolId());
        List<DmStudentAttendant> data=dmStudentAttendantService.findDmStudentAttendantListByCondition(dmStudentAttendant);
        return new ResponseJson(data);
    }

}
