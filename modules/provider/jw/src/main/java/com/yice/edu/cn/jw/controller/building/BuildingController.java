package com.yice.edu.cn.jw.controller.building;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.jw.service.building.BuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
@Api(value = "/building",description = "楼栋表模块")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping("/findBuildingById/{id}")
    @ApiOperation(value = "通过id查找楼栋表", notes = "返回楼栋表对象")
    public Building findBuildingById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return buildingService.findBuildingById(id);
    }

    @PostMapping("/saveBuilding")
    @ApiOperation(value = "保存楼栋表", notes = "返回楼栋表对象")
    public Building saveBuilding(
            @ApiParam(value = "楼栋表对象", required = true)
            @RequestBody Building building){
        buildingService.saveBuilding(building);
        return building;
    }

    @PostMapping("/findBuildingListByCondition")
    @ApiOperation(value = "根据条件查找楼栋表列表", notes = "返回楼栋表列表")
    public List<Building> findBuildingListByCondition(
            @ApiParam(value = "楼栋表对象")
            @RequestBody Building building){
        return buildingService.findBuildingListByCondition(building);
    }
    @PostMapping("/findBuildingCountByCondition")
    @ApiOperation(value = "根据条件查找楼栋表列表个数", notes = "返回楼栋表总个数")
    public long findBuildingCountByCondition(
            @ApiParam(value = "楼栋表对象")
            @RequestBody Building building){
        return buildingService.findBuildingCountByCondition(building);
    }

    @PostMapping("/updateBuilding")
    @ApiOperation(value = "修改楼栋表", notes = "楼栋表对象必传")
    public void updateBuilding(
            @ApiParam(value = "楼栋表对象,对象属性不为空则修改", required = true)
            @RequestBody Building building){
        buildingService.updateBuilding(building);
    }

    @GetMapping("/deleteBuilding/{id}")
    @ApiOperation(value = "通过id删除楼栋表")
    public void deleteBuilding(
            @ApiParam(value = "楼栋表对象", required = true)
            @PathVariable String id){
        buildingService.deleteBuilding(id);
    }
    @PostMapping("/deleteBuildingByCondition")
    @ApiOperation(value = "根据条件删除楼栋表")
    public void deleteBuildingByCondition(
            @ApiParam(value = "楼栋表对象")
            @RequestBody Building building){
        buildingService.deleteBuildingByCondition(building);
    }
    @PostMapping("/findOneBuildingByCondition")
    @ApiOperation(value = "根据条件查找单个楼栋表,结果必须为单条数据", notes = "返回单个楼栋表,没有时为空")
    public Building findOneBuildingByCondition(
            @ApiParam(value = "楼栋表对象")
            @RequestBody Building building){
        return buildingService.findOneBuildingByCondition(building);
    }


    @GetMapping("/getBuildingSpaceTree/{schoolId}")
    @ApiOperation(value = "获取楼栋场地树", notes = "返回楼栋信息列表")
    public List<Building> getBuildingSpaceTree(@PathVariable String schoolId){
        return  buildingService.getBuildingSpaceTree(schoolId);
    }

    @GetMapping("/deleteWholeBuilding/{id}")
    @ApiOperation(value = "根据id删除整个楼栋，包括底下的楼层场地", notes = "返回响应对象")
    public void deleteWholeBuilding(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        buildingService.deleteWholeBuilding(id);
    }

    @GetMapping("/findSchoolNumberRooms/{schoolId}")
    public List<Building> findSchoolNumberRooms(@PathVariable String schoolId){
        return buildingService.findSchoolNumberRooms(schoolId);
    }

    @PostMapping("/findBuildingRoomNameAll")
    @ApiOperation(value = "根据楼栋id查询,所有场地", notes = "返回楼栋表列表")
    public List<Building> findBuildingRoomNameAll(
            @ApiParam(value = "楼栋id")
            @RequestBody Building building){
        return buildingService.findBuildingRoomNameAll(building);
    }

    @PostMapping("/findBuildingListByParentIds")
    List<String> findBuildingListByParentIds(@RequestBody  List<String> list){
        return buildingService.findBuildingListByParentIds(list);
    }

}
