package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormBuildAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dormBuildAdmin")
@Api(value = "/dormBuildAdmin",description = "模块")
public class DormBuildAdminController {
    @Autowired
    private DormBuildAdminService dormBuildAdminService;

    @GetMapping("/findDormBuildAdminById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DormBuildAdmin findDormBuildAdminById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dormBuildAdminService.findDormBuildAdminById(id);
    }

    @PostMapping("/saveDormBuildAdmin")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DormBuildAdmin saveDormBuildAdmin(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdminService.saveDormBuildAdmin(dormBuildAdmin);
        return dormBuildAdmin;
    }

    @PostMapping("/findDormBuildAdminListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DormBuildAdmin> findDormBuildAdminListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
    }
    @PostMapping("/findDormBuildAdminCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDormBuildAdminCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildAdminCountByCondition(dormBuildAdmin);
    }

    @PostMapping("/updateDormBuildAdmin")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDormBuildAdmin(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdminService.updateDormBuildAdmin(dormBuildAdmin);
    }

    @GetMapping("/deleteDormBuildAdmin/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDormBuildAdmin(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dormBuildAdminService.deleteDormBuildAdmin(id);
    }
    @PostMapping("/deleteDormBuildAdminByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDormBuildAdminByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        dormBuildAdminService.deleteDormBuildAdminByCondition(dormBuildAdmin);
    }
    @PostMapping("/findOneDormBuildAdminByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DormBuildAdmin findOneDormBuildAdminByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findOneDormBuildAdminByCondition(dormBuildAdmin);
    }

    /*----------------------------------------------------------------------------------------------*/
    @PostMapping("/getBuildingList")
    @ApiOperation(value = "根据条件查找楼栋列表", notes = "返回楼栋列表")
    List<Building> getBuildingList(
            @ApiParam(value = "对象")
            @RequestBody Building building){
        return dormBuildAdminService.getBuildingList(building);
    }

    @PostMapping("/findDormBuildAdminListByConditionConnect")
    @ApiOperation(value = "根据条件查找楼栋管理员", notes = "返回楼栋管理员列表")
    List<DormBuildAdmin> findDormBuildAdminListByConditionConnect(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildAdminListByConditionConnect(dormBuildAdmin);
    }

    @PostMapping("/findDormBuildAdminListCountConnect")
    @ApiOperation(value = "根据条件查找楼栋列表条数", notes = "返回条数")
    long findDormBuildAdminListCountConnect(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildAdminListCountConnect(dormBuildAdmin);
    }


    @PostMapping("/findDormBuildTeacherByConditionConnect")
    @ApiOperation(value = "根据条件查找宿管老师", notes = "返回宿管老师列表")
    List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
    }


    @PostMapping("/findCreateDormBuildingList")
    @ApiOperation(value = "根据条件查找宿管员所管理的宿舍楼", notes = "返回楼栋列表")
    public List<Building> findCreateDormBuildingList(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findCreateDormBuildingList(dormBuildAdmin);
    }

    @PostMapping("/findDormBuildListByCondition")
    @ApiOperation(value = "根据条件查找所有的宿舍楼", notes = "返回楼栋列表")
    public List<Building> findDormBuildListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminService.findDormBuildListByCondition(dormBuildAdmin);
    }

}
