package com.yice.edu.cn.xw.controller.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import com.yice.edu.cn.xw.service.dormManage.houseApplican.HouseApplicanStudentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houseApplicanStudents")
@Api(value = "/houseApplicanStudents",description = "模块")
public class HouseApplicanStudentsController {
    @Autowired
    private HouseApplicanStudentsService houseApplicanStudentsService;

    @GetMapping("/findHouseApplicanStudentsById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HouseApplicanStudents findHouseApplicanStudentsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return houseApplicanStudentsService.findHouseApplicanStudentsById(id);
    }

    @PostMapping("/saveHouseApplicanStudents")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplicanStudents saveHouseApplicanStudents(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudentsService.saveHouseApplicanStudents(houseApplicanStudents);
        return houseApplicanStudents;
    }

    @PostMapping("/findHouseApplicanStudentsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplicanStudents> findHouseApplicanStudentsListByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
    }
    @PostMapping("/findHouseApplicanStudentsCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHouseApplicanStudentsCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.findHouseApplicanStudentsCountByCondition(houseApplicanStudents);
    }

    @PostMapping("/updateHouseApplicanStudents")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHouseApplicanStudents(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudentsService.updateHouseApplicanStudents(houseApplicanStudents);
    }

    @GetMapping("/deleteHouseApplicanStudents/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHouseApplicanStudents(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        houseApplicanStudentsService.deleteHouseApplicanStudents(id);
    }
    @PostMapping("/deleteHouseApplicanStudentsByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHouseApplicanStudentsByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudentsService.deleteHouseApplicanStudentsByCondition(houseApplicanStudents);
    }
    @PostMapping("/findOneHouseApplicanStudentsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public HouseApplicanStudents findOneHouseApplicanStudentsByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.findOneHouseApplicanStudentsByCondition(houseApplicanStudents);
    }


    @PostMapping("/findHouseApplicanStudents")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplicanStudents> findHouseApplicanStudents(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.findHouseApplicanStudents(houseApplicanStudents);
    }
    @PostMapping("/findHouseApplicanStudentsCount")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHouseApplicanStudentsCount(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.findHouseApplicanStudentsCount(houseApplicanStudents);
    }

    @GetMapping("/lookHouseApplicanStudentsByhouseApplicanId/{houseApplicanId}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HouseApplicanStudents lookHouseApplicanStudentsByhouseApplicanId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String houseApplicanId){
        return houseApplicanStudentsService.lookHouseApplicanStudentsByhouseApplicanId(houseApplicanId);
    }

    @PostMapping("/saveHouseApplicanStudents1")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplicanStudents saveHouseApplicanStudents1(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudentsService.saveHouseApplicanStudents1(houseApplicanStudents);
        return houseApplicanStudents;
    }


    @PostMapping("/lookHouseApplicanStudents")
    @ApiOperation(value = "通过houseApplicanId查找", notes = "返回对象")
    public List<HouseApplicanStudents> lookHouseApplicanStudents(
            @ApiParam(value = "需要用到的houseApplicanId", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        return houseApplicanStudentsService.lookHouseApplicanStudents(houseApplicanStudents);
    }


    @GetMapping("/lookHouseApplicanStudentsCount/{houseApplicanId}")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long lookHouseApplicanStudentsCount(
            @ApiParam(value = "对象")
            @PathVariable String houseApplicanId){
        return houseApplicanStudentsService.lookHouseApplicanStudentsCount(houseApplicanId);
    }
}
