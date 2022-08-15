package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesTimeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesTimeStatus")
@Api(value = "/mesTimeStatus", description = "时间状态表模块")
public class MesTimeStatusController {
    @Autowired
    private MesTimeStatusService mesTimeStatusService;

    @GetMapping("/findMesTimeStatusById/{id}")
    @ApiOperation(value = "通过id查找时间状态表", notes = "返回时间状态表对象")
    public MesTimeStatus findMesTimeStatusById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesTimeStatusService.findMesTimeStatusById(id);
    }

    @PostMapping("/saveMesTimeStatus")
    @ApiOperation(value = "保存时间状态表", notes = "返回时间状态表对象")
    public MesTimeStatus saveMesTimeStatus(
            @ApiParam(value = "时间状态表对象", required = true)
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatusService.saveMesTimeStatus(mesTimeStatus);
        return mesTimeStatus;
    }

    @PostMapping("/findMesTimeStatusListByCondition")
    @ApiOperation(value = "根据条件查找时间状态表列表", notes = "返回时间状态表列表")
    public List<MesTimeStatus> findMesTimeStatusListByCondition(
            @ApiParam(value = "时间状态表对象")
            @RequestBody MesTimeStatus mesTimeStatus) {
        return mesTimeStatusService.findMesTimeStatusListByCondition(mesTimeStatus);
    }

    @PostMapping("/findMesTimeStatusCountByCondition")
    @ApiOperation(value = "根据条件查找时间状态表列表个数", notes = "返回时间状态表总个数")
    public long findMesTimeStatusCountByCondition(
            @ApiParam(value = "时间状态表对象")
            @RequestBody MesTimeStatus mesTimeStatus) {
        return mesTimeStatusService.findMesTimeStatusCountByCondition(mesTimeStatus);
    }

    @PostMapping("/updateMesTimeStatus")
    @ApiOperation(value = "修改时间状态表", notes = "时间状态表对象必传")
    public void updateMesTimeStatus(
            @ApiParam(value = "时间状态表对象,对象属性不为空则修改", required = true)
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatusService.updateMesTimeStatus(mesTimeStatus);
    }

    @GetMapping("/deleteMesTimeStatus/{id}")
    @ApiOperation(value = "通过id删除时间状态表")
    public void deleteMesTimeStatus(
            @ApiParam(value = "时间状态表对象", required = true)
            @PathVariable String id) {
        mesTimeStatusService.deleteMesTimeStatus(id);
    }

    @PostMapping("/deleteMesTimeStatusByCondition")
    @ApiOperation(value = "根据条件删除时间状态表")
    public void deleteMesTimeStatusByCondition(
            @ApiParam(value = "时间状态表对象")
            @RequestBody MesTimeStatus mesTimeStatus) {
        mesTimeStatusService.deleteMesTimeStatusByCondition(mesTimeStatus);
    }

    @PostMapping("/findOneMesTimeStatusByCondition")
    @ApiOperation(value = "根据条件查找单个时间状态表,结果必须为单条数据", notes = "返回单个时间状态表,没有时为空")
    public MesTimeStatus findOneMesTimeStatusByCondition(
            @ApiParam(value = "时间状态表对象")
            @RequestBody MesTimeStatus mesTimeStatus) {
        return mesTimeStatusService.findOneMesTimeStatusByCondition(mesTimeStatus);
    }
}
