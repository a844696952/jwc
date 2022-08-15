package com.yice.edu.cn.xw.controller.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import com.yice.edu.cn.xw.service.dormManage.houseApplican.HouseApplicanTeachersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houseApplicanTeachers")
@Api(value = "/houseApplicanTeachers",description = "模块")
public class HouseApplicanTeachersController {
    @Autowired
    private HouseApplicanTeachersService houseApplicanTeachersService;

    @GetMapping("/findHouseApplicanTeachersById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HouseApplicanTeachers findHouseApplicanTeachersById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return houseApplicanTeachersService.findHouseApplicanTeachersById(id);
    }

    @PostMapping("/saveHouseApplicanTeachers")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplicanTeachers saveHouseApplicanTeachers(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        houseApplicanTeachersService.saveHouseApplicanTeachers(houseApplicanTeachers);
        return houseApplicanTeachers;
    }

    @PostMapping("/findHouseApplicanTeachersListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplicanTeachers> findHouseApplicanTeachersListByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        return houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
    }
    @PostMapping("/findHouseApplicanTeachersCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHouseApplicanTeachersCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        return houseApplicanTeachersService.findHouseApplicanTeachersCountByCondition(houseApplicanTeachers);
    }

    @PostMapping("/updateHouseApplicanTeachers")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHouseApplicanTeachers(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        houseApplicanTeachersService.updateHouseApplicanTeachers(houseApplicanTeachers);
    }

    @GetMapping("/deleteHouseApplicanTeachers/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHouseApplicanTeachers(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        houseApplicanTeachersService.deleteHouseApplicanTeachers(id);
    }
    @PostMapping("/deleteHouseApplicanTeachersByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHouseApplicanTeachersByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        houseApplicanTeachersService.deleteHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }
    @PostMapping("/findOneHouseApplicanTeachersByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public HouseApplicanTeachers findOneHouseApplicanTeachersByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        return houseApplicanTeachersService.findOneHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }
}
