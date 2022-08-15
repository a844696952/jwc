package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dorm")
@Api(value = "/dorm",description = "住宿管理模块")
public class DormController {
    @Autowired
    private DormService dormService;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;

    @PostMapping("/saveDorm")
    @ApiOperation(value = "单个设置宿舍类型", notes = "返回保存好的对象")
    public ResponseJson saveDorm(
            @ApiParam(value = "对象", required = true)
            @RequestBody Dorm dorm){
        dorm.setSchoolId(mySchoolId());
        Dorm d = dormService.saveDorm(dorm);
        return new ResponseJson(d);
    }

    @GetMapping("/update/findDormById/{id}")
    @ApiOperation(value = "去修改页面,通过id查找", notes = "返回响应对象", response=Dorm.class)
    public ResponseJson findDormById(
            @ApiParam(value = "去修改页面,需要用到的id", required = true)
            @PathVariable String id){
        Dorm dorm=dormService.findDormById(id);
        return new ResponseJson(dorm);
    }

    @PostMapping("/update/updateDorm")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDorm(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Dorm dorm){
        dormService.updateDorm(dorm);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDormById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=Dorm.class)
    public ResponseJson lookDormById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Dorm dorm=dormService.findDormById(id);
        return new ResponseJson(dorm);
    }

    @PostMapping("/findDormsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=Dorm.class)
    public ResponseJson findDormsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Dorm dorm){
       dorm.setSchoolId(mySchoolId());
        List<Dorm> data=dormService.findDormListByCondition(dorm);
        long count=dormService.findDormCountByCondition(dorm);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDormByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=Dorm.class)
    public ResponseJson findOneDormByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Dorm dorm){
        Dorm one=dormService.findOneDormByCondition(dorm);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDorm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDorm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dormService.deleteDorm(id);
        return new ResponseJson();
    }


    @PostMapping("/findDormListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=Dorm.class)
    public ResponseJson findDormListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Dorm dorm){
       dorm.setSchoolId(mySchoolId());
        List<Dorm> data=dormService.findDormListByCondition(dorm);
        return new ResponseJson(data);
    }

    /*----------------------------------------------------------------------------------------------------------*/
    @PostMapping("/findDormBuildingTreeByCondition")
    @ApiOperation(value = "根据条件查找宿舍楼列表树", notes = "返回宿舍楼列表树", response=Dorm.class)
    public ResponseJson findDormBuildingTreeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormBuildVo dormBuildVo){
        List<Building> dormBuilding = dormBuildAdminService.findDormBuildingByLogin();
        if (dormBuilding!=null&&dormBuilding.size()>0){
            List<String> dormBuildIdList = dormBuilding.stream().map(Building::getId).collect(Collectors.toList());
            dormBuildVo.setDormBuildIdList(dormBuildIdList);
            List<Dorm> list = dormService.findDormBuildingTreeByCondition(dormBuildVo);
            return new ResponseJson(list);
        }else {
            return new ResponseJson();
        }
    }


    @PostMapping("/batchSaveDormType")
    @ApiOperation(value = "批量设置宿舍类型")
    public ResponseJson batchSaveDormType(
            @ApiParam(value = "对象", required = true)
            @RequestBody Dorm dorm){
        dorm.setSchoolId(mySchoolId());
        dormService.batchSaveDormType(dorm);
        return new ResponseJson();
    }


    @GetMapping("/findDormBuildingAndFloor")
    @ApiOperation(value = "根据学校查找宿舍楼和楼层" ,response=Building.class)
    public ResponseJson findDormBuildingAndFloor(){
        List<Building> data = dormService.findDormBuildingAndFloor(mySchoolId());
        return new ResponseJson(data);
    }

    @GetMapping("/findDormByFloorId/{floorId}")
    @ApiOperation(value = "根据楼层id查找宿舍" ,response=Building.class)
    public ResponseJson findDormBuildingAndFloor(
            @ApiParam(value = "楼层id", required = true)
            @PathVariable String floorId){
        Building building = new Building();
        building.setId(floorId);
        building.setSchoolId(mySchoolId());
        List<Dorm> data = dormService.findDormByFloorId(building);
        return new ResponseJson(data);
    }
}
