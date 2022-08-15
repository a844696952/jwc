package com.yice.edu.cn.osp.controller.jw.building;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.osp.service.jw.building.BuildingService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/building")
@Api(value = "/building",description = "楼栋表模块")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DormPersonService dormPersonService;
    @PostMapping("/saveBuilding")
    @ApiOperation(value = "保存楼栋表对象", notes = "返回保存好的楼栋表对象", response=Building.class)
    public ResponseJson saveBuilding(
            @ApiParam(value = "楼栋表对象", required = true)
            @RequestBody Building building){
       building.setSchoolId(mySchoolId());
        Building s=buildingService.saveBuilding(building);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findBuildingById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找楼栋表", notes = "返回响应对象", response=Building.class)
    public ResponseJson findBuildingById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Building building=buildingService.findBuildingById(id);
        return new ResponseJson(building);
    }

    @PostMapping("/update/updateBuilding")
    @ApiOperation(value = "修改楼栋表对象", notes = "返回响应对象")
    public ResponseJson updateBuilding(
            @ApiParam(value = "被修改的楼栋表对象,对象属性不为空则修改", required = true)
            @RequestBody Building building){
        if(building.getFloors()<building.getOldFloors()){
            List<String> list=new ArrayList<>();
            for (int i = building.getFloors(); i <building.getOldFloors() ; i++) {
                Building b1=new Building();
                b1.setName("楼层"+(i+1));
                b1.setParentId(building.getId());
                Building building1=buildingService.findOneBuildingByCondition(b1);
                list.add(building1.getId());
            }

            List<String> spaceIdList=buildingService.findBuildingListByParentIds(list);
            if(spaceIdList.size()>0){
                boolean isok=dormPersonService.findDormIsPersonByDormId(spaceIdList);
                if(isok){
                    return new ResponseJson(false,"所删楼层有人员入住，不允许删除");
                }else {
                    buildingService.updateBuilding(building);
                    dormPersonService.deleteDormAndDormPerson(spaceIdList);
                    return new ResponseJson();
                }
            }else {
                buildingService.updateBuilding(building);
                return new ResponseJson();
            }

        }else {
            Building building1=buildingService.findBuildingById(building.getId());
            List<String> list=new ArrayList<>();
            list.add(building.getId());
            boolean isok=dormPersonService.findDormIsPersonByDormId(list);
            if(isok){
                if(Integer.parseInt(building.getCapacity())<Integer.parseInt(building1.getCapacity())){
                    return new ResponseJson(false,"该场地有人员入住，场地容量不能减小，只能增加");
                }else {
                    dormPersonService.updateDormCapacityByDormId(building);
                    buildingService.updateBuilding(building);
                    return new ResponseJson();
                }

            }else {
                dormPersonService.updateDormCapacityByDormId(building);
                buildingService.updateBuilding(building);
                return new ResponseJson();
            }

        }


    }

    @GetMapping("/look/lookBuildingById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找楼栋表", notes = "返回响应对象", response=Building.class)
    public ResponseJson lookBuildingById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Building building=buildingService.findBuildingById(id);
        return new ResponseJson(building);
    }

    @PostMapping("/findBuildingsByCondition")
    @ApiOperation(value = "根据条件查找楼栋表", notes = "返回响应对象", response=Building.class)
    public ResponseJson findBuildingsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Building building){
       building.setSchoolId(mySchoolId());
        List<Building> data=buildingService.findBuildingListByCondition(building);
        long count=buildingService.findBuildingCountByCondition(building);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneBuildingByCondition")
    @ApiOperation(value = "根据条件查找单个楼栋表,结果必须为单条数据", notes = "没有时返回空", response=Building.class)
    public ResponseJson findOneBuildingByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Building building){
        Building one=buildingService.findOneBuildingByCondition(building);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteBuilding/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteBuilding(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        List<String> spaceIdList=new ArrayList<>();
        spaceIdList.add(id);
        boolean isok=dormPersonService.findDormIsPersonByDormId(spaceIdList);
        if(isok){
            return new ResponseJson(false,"当前场地有人员入住，不允许删除");
        }else {
            buildingService.deleteBuilding(id);
            dormPersonService.deleteDormAndDormPerson(spaceIdList);
        }
        return new ResponseJson();
    }


    @PostMapping("/findBuildingListByCondition")
    @ApiOperation(value = "根据条件查找楼栋表列表", notes = "返回响应对象,不包含总条数", response=Building.class)
    public ResponseJson findBuildingListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Building building){
       building.setSchoolId(mySchoolId());
        List<Building> data=buildingService.findBuildingListByCondition(building);
        return new ResponseJson(data);
    }

    @GetMapping("/getBuildingSpaceTree")
    @ApiOperation(value = "获取楼栋场地树", notes = "返回楼栋信息列表")
    public ResponseJson getBuildingSpaceTree(){

        List<Building> data= buildingService.getBuildingSpaceTree(mySchoolId());
        return  new ResponseJson(data);
    }



    @GetMapping("/deleteWholeBuilding/{id}")
    @ApiOperation(value = "根据id删除整个楼栋，包括底下的楼层场地", notes = "返回响应对象")
    public ResponseJson deleteWholeBuilding(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        Building building=new Building();
        building.setParentId(id);
        List<Building> buildingList=buildingService.findBuildingListByCondition(building);
        List<String> spaceIdList=buildingService.findBuildingListByParentIds(buildingList.stream().map(Building::getId).collect(Collectors.toList()));
        if(spaceIdList.size()>0){
            boolean isok=dormPersonService.findDormIsPersonByDormId(spaceIdList);
            if(isok){
                return new ResponseJson(false,"当前楼栋有人员入住，不允许删除");
            }else{
                buildingService.deleteWholeBuilding(id);
                dormPersonService.deleteDormAndDormPerson(spaceIdList);
            }
        }else {
            buildingService.deleteWholeBuilding(id);
        }

        return new ResponseJson();
    }


    @PostMapping("/findBuildingsByConditionPagingFalse")
    @ApiOperation(value = "根据条件查找楼栋表，不分页", notes = "返回响应对象", response=Building.class)
    public ResponseJson findBuildingsByConditionPagingFalse(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Building building){
        building.setSchoolId(mySchoolId());
        List<Building> data=buildingService.findBuildingListByCondition(building);
        long count=buildingService.findBuildingCountByCondition(building);
        return new ResponseJson(data,count);
    }
}
