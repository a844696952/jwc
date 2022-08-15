package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesTimeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesTimeStatus")
@Api(value = "/mesTimeStatus", description = "时间状态表模块")
public class MesTimeStatusController {
    @Autowired
    private MesTimeStatusService mesTimeStatusService;

    @PostMapping("/saveMesTimeStatus")
    @ApiOperation(value = "保存时间状态表对象", notes = "返回保存好的时间状态表对象", response = MesTimeStatus.class)
    public ResponseJson saveMesTimeStatus(
            @ApiParam(value = "时间状态表对象", required = true)
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setSchoolId(mySchoolId());
        MesTimeStatus s = mesTimeStatusService.saveMesTimeStatus(mesTimeStatus);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesTimeStatusById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找时间状态表", notes = "返回响应对象", response = MesTimeStatus.class)
    public ResponseJson findMesTimeStatusById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesTimeStatus mesTimeStatus = mesTimeStatusService.findMesTimeStatusById(id);
        return new ResponseJson(mesTimeStatus);
    }

    @PostMapping("/update/updateMesTimeStatus")
    @ApiOperation(value = "修改时间状态表对象", notes = "返回响应对象")
    public ResponseJson updateMesTimeStatus(
            @ApiParam(value = "被修改的时间状态表对象,对象属性不为空则修改", required = true)
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setSchoolId(mySchoolId());
        mesTimeStatusService.updateMesTimeStatus(mesTimeStatus);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesTimeStatusById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找时间状态表", notes = "返回响应对象", response = MesTimeStatus.class)
    public ResponseJson lookMesTimeStatusById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesTimeStatus mesTimeStatus = mesTimeStatusService.findMesTimeStatusById(id);
        return new ResponseJson(mesTimeStatus);
    }

    @PostMapping("/findMesTimeStatussByCondition")
    @ApiOperation(value = "根据条件查找时间状态表", notes = "返回响应对象", response = MesTimeStatus.class)
    public ResponseJson findMesTimeStatussByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setSchoolId(mySchoolId());
        List<MesTimeStatus> data = mesTimeStatusService.findMesTimeStatusListByCondition(mesTimeStatus);
        long count = mesTimeStatusService.findMesTimeStatusCountByCondition(mesTimeStatus);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesTimeStatusByCondition")
    @ApiOperation(value = "根据条件查找单个时间状态表,结果必须为单条数据", notes = "没有时返回空", response = MesTimeStatus.class)
    public ResponseJson findOneMesTimeStatusByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setSchoolId(mySchoolId());
        MesTimeStatus one = mesTimeStatusService.findOneMesTimeStatusByCondition(mesTimeStatus);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesTimeStatus/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesTimeStatus(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesTimeStatusService.deleteMesTimeStatus(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesTimeStatusListByCondition")
    @ApiOperation(value = "根据条件查找时间状态表列表", notes = "返回响应对象,不包含总条数", response = MesTimeStatus.class)
    public ResponseJson findMesTimeStatusListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setSchoolId(mySchoolId());
        List<MesTimeStatus> data = mesTimeStatusService.findMesTimeStatusListByCondition(mesTimeStatus);
        return new ResponseJson(data);
    }


}
