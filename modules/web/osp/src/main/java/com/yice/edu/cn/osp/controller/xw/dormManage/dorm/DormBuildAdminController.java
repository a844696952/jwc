package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormPersonFeign;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentSchoolId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormBuildAdmin")
@Api(value = "/dormBuildAdmin",description = "管理人员模块")
public class DormBuildAdminController {
    @Autowired
    private DormBuildAdminService dormBuildAdminService;
    @Autowired
    private DormPersonService dormPersonService;

    @PostMapping("/saveDormBuildAdmin")
    @ApiOperation(value = "保存宿管人员和宿管老师对象")
    public ResponseJson saveDormBuildAdmin(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdmin.setSchoolId(mySchoolId());
        boolean flag = dormBuildAdminService.saveDormBuildAdmin(dormBuildAdmin);
        if (flag){
            return new ResponseJson();
        }else {
            return new ResponseJson(false,"该宿舍老师已存在!");
        }

    }

    @GetMapping("/update/findDormBuildAdminById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildAdminById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DormBuildAdmin dormBuildAdmin=dormBuildAdminService.findDormBuildAdminById(id);
        return new ResponseJson(dormBuildAdmin);
    }

    @PostMapping("/update/updateDormBuildAdmin")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDormBuildAdmin(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdmin.setSchoolId(mySchoolId());
        if(dormBuildAdmin.getStaffIds()!=null){
            dormBuildAdminService.deleteDormBuildAdminByCondition(dormBuildAdmin);
            dormBuildAdminService.saveDormBuildAdmin(dormBuildAdmin);
        }
        return new ResponseJson();
    }

    @GetMapping("/look/lookDormBuildAdminById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DormBuildAdmin.class)
    public ResponseJson lookDormBuildAdminById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DormBuildAdmin dormBuildAdmin=dormBuildAdminService.findDormBuildAdminById(id);
        return new ResponseJson(dormBuildAdmin);
    }

    @PostMapping("/findDormBuildAdminsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildAdminsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdmin.setSchoolId(mySchoolId());
        List<DormBuildAdmin> data=dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        long count=dormBuildAdminService.findDormBuildAdminCountByCondition(dormBuildAdmin);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDormBuildAdminByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DormBuildAdmin.class)
    public ResponseJson findOneDormBuildAdminByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        DormBuildAdmin one=dormBuildAdminService.findOneDormBuildAdminByCondition(dormBuildAdmin);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDormBuildTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDormBuildTeacher(
            @ApiParam(value = "被删除宿管老师记录的id", required = true)
            @PathVariable String id){
        dormBuildAdminService.deleteDormBuildAdmin(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteDormBuildAdmin/{dormBuildId}")
    @ApiOperation(value = "根据dormBuildId删除", notes = "返回响应对象")
    public ResponseJson deleteDormBuildAdmin(
            @ApiParam(value = "被删除宿管人员记录的宿舍楼 dormBuildId", required = true)
            @PathVariable String dormBuildId){
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setSchoolId(mySchoolId());
        dormBuildVo.setDormBuildId(dormBuildId);
        long count = dormPersonService.findDormMoveIntoPersonNumByCondition(dormBuildVo);
        if (count>0){
            return new ResponseJson(false,"该宿舍楼有宿舍人员不允许删除！");
        }else {
            DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
            dormBuildAdmin.setDormBuildId(dormBuildId);
            dormBuildAdmin.setSchoolId(mySchoolId());
            dormBuildAdminService.deleteDormBuildAdminByCondition(dormBuildAdmin);
            return new ResponseJson();
        }
    }


    @PostMapping("/findDormBuildAdminListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildAdminListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormBuildAdmin dormBuildAdmin){
       dormBuildAdmin.setSchoolId(mySchoolId());
        List<DormBuildAdmin> data=dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        return new ResponseJson(data);
    }

/*-----------------------------------------------------------------------------------------------------------------*/
    @GetMapping("/getBuildingList")
    @ApiOperation(value = "查询场地类型为宿舍的楼栋列表", notes = "返回楼栋列表", response=Building.class)
    public ResponseJson getBuildingList(){
        Building building = new Building();
        building.setSchoolId(mySchoolId());
       List<Building> buildingList =  dormBuildAdminService.getBuildingList(building);
       return new ResponseJson(buildingList);
    }

    @PostMapping("/findDormBuildAdminListByConditionConnect")
    @ApiOperation(value = "根据条件查找宿舍管理员列表", notes = "返回响应对象,包含总条数", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildAdminListByConditionConnect(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdmin.setSchoolId(mySchoolId());
        List<DormBuildAdmin> data=dormBuildAdminService.findDormBuildAdminListByConditionConnect(dormBuildAdmin);
        long count = dormBuildAdminService.findDormBuildAdminListCountConnect(dormBuildAdmin);
        return new ResponseJson(data,count);
    }

    @GetMapping("/update/findDormBuildAdminByDormBuildId/{dormBuildId}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildAdminByDormBuildId(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String dormBuildId){
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setDormBuildId(dormBuildId);
        dormBuildAdmin.setSchoolId(mySchoolId());
        List<DormBuildAdmin> list = dormBuildAdminService.findDormBuildAdminListByConditionConnect(dormBuildAdmin);
        if (list.size()>0){
            return new ResponseJson(list.get(0));
        }else {
            return new ResponseJson();
        }

    }


    @PostMapping("/findDormBuildTeacherByConditionConnect")
    @ApiOperation(value = "根据条件查找宿舍老师", notes = "返回宿舍老师对象列表", response=DormBuildAdmin.class)
    public ResponseJson findDormBuildTeacherByConditionConnect(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdmin.setSchoolId(mySchoolId());
        dormBuildAdmin.setStaffType(Constant.DORM_STAFF_TYPE.DORM_TEACHER);
        List<DormBuildAdmin> data=dormBuildAdminService.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
        long count=dormBuildAdminService.findDormBuildAdminCountByCondition(dormBuildAdmin);
        return new ResponseJson(data,count);
    }

    @GetMapping("/findDormBuildingByLogin")
    @ApiOperation(value = "根据登录人判断是否有楼栋", notes = "返回楼栋条数")
    public ResponseJson findDormBuildingByLogin(){
        List<Building> dormBuilding = dormBuildAdminService.findDormBuildingByLogin();
        return new ResponseJson(dormBuilding.size());
    }

    @GetMapping("/findDormBuildListByCondition")
    @ApiOperation(value = "查询场地类型为宿舍的楼栋", notes = "返回楼栋列表")
    public ResponseJson findDormBuildListByCondition(){
        List<Building> dormBuilding = dormBuildAdminService.findDormBuildListByCondition();
        return new ResponseJson(dormBuilding);
    }
}
