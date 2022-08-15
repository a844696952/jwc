package com.yice.edu.cn.bmp.controller.xwDormManage.houseApplican;

import com.yice.edu.cn.bmp.service.xwDormManage.houseApplican.HouseApplicanTeachersService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/houseApplicanTeachers")
@Api(value = "/houseApplicanTeachers",description = "模块")
public class HouseApplicanTeachersController {
    @Autowired
    private HouseApplicanTeachersService houseApplicanTeachersService;

    @PostMapping("/saveHouseApplicanTeachers")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplicanTeachers.class)
    public ResponseJson saveHouseApplicanTeachers(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
       houseApplicanTeachers.setSchoolId(mySchoolId());
        HouseApplicanTeachers s=houseApplicanTeachersService.saveHouseApplicanTeachers(houseApplicanTeachers);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHouseApplicanTeachersById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=HouseApplicanTeachers.class)
    public ResponseJson findHouseApplicanTeachersById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanTeachers houseApplicanTeachers=houseApplicanTeachersService.findHouseApplicanTeachersById(id);
        return new ResponseJson(houseApplicanTeachers);
    }

    @PostMapping("/update/updateHouseApplicanTeachers")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHouseApplicanTeachers(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        houseApplicanTeachersService.updateHouseApplicanTeachers(houseApplicanTeachers);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHouseApplicanTeachersById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HouseApplicanTeachers.class)
    public ResponseJson lookHouseApplicanTeachersById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanTeachers houseApplicanTeachers=houseApplicanTeachersService.findHouseApplicanTeachersById(id);
        return new ResponseJson(houseApplicanTeachers);
    }

    @PostMapping("/findHouseApplicanTeacherssByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=HouseApplicanTeachers.class)
    public ResponseJson findHouseApplicanTeacherssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
       houseApplicanTeachers.setSchoolId(mySchoolId());
        List<HouseApplicanTeachers> data=houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
        long count=houseApplicanTeachersService.findHouseApplicanTeachersCountByCondition(houseApplicanTeachers);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHouseApplicanTeachersByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=HouseApplicanTeachers.class)
    public ResponseJson findOneHouseApplicanTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
        HouseApplicanTeachers one=houseApplicanTeachersService.findOneHouseApplicanTeachersByCondition(houseApplicanTeachers);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHouseApplicanTeachers/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHouseApplicanTeachers(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        houseApplicanTeachersService.deleteHouseApplicanTeachers(id);
        return new ResponseJson();
    }


    @PostMapping("/findHouseApplicanTeachersListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=HouseApplicanTeachers.class)
    public ResponseJson findHouseApplicanTeachersListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanTeachers houseApplicanTeachers){
       houseApplicanTeachers.setSchoolId(mySchoolId());
        List<HouseApplicanTeachers> data=houseApplicanTeachersService.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
        return new ResponseJson(data);
    }



}
