package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorm")
@Api(value = "/dorm",description = "模块")
public class DormController {
    @Autowired
    private DormService dormService;

    @GetMapping("/findDormById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Dorm findDormById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dormService.findDormById(id);
    }

    @PostMapping("/saveDorm")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Dorm saveDorm(
            @ApiParam(value = "对象", required = true)
            @RequestBody Dorm dorm){
       return dormService.saveDorm(dorm);
    }

    @PostMapping("/findDormListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Dorm> findDormListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        return dormService.findDormListByCondition(dorm);
    }
    @PostMapping("/findDormCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDormCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        return dormService.findDormCountByCondition(dorm);
    }

    @PostMapping("/updateDorm")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDorm(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Dorm dorm){
        dormService.updateDorm(dorm);
    }

    @GetMapping("/deleteDorm/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDorm(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dormService.deleteDorm(id);
    }
    @PostMapping("/deleteDormByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDormByCondition(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        dormService.deleteDormByCondition(dorm);
    }
    @PostMapping("/findOneDormByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Dorm findOneDormByCondition(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        return dormService.findOneDormByCondition(dorm);
    }

    /*------------------------------------------------------------------------------------------------*/

    @PostMapping("/findDormBuildingTreeByCondition")
    @ApiOperation(value = "根据条件查找宿舍楼列表树", notes = "返回宿舍楼列表树,没有时为空")
    public List<Dorm> findDormBuildingTreeByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormService.findDormBuildingTreeByCondition(dormBuildVo);
    }


    @PostMapping("/findDormFloorNum")
    @ApiOperation(value = "根据条件查找楼层容量与入住人数", notes = "返回楼层列表,没有时为空")
    public List<Dorm> findDormFloorNum(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormService.findDormFloorNum(dormBuildVo);
    }

    @PostMapping("/updateDormByDormId")
    @ApiOperation(value = "根据宿舍id修改,字段不为空时则修改")
    public void updateDormByDormId(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        dormService.updateDormByDormId(dorm);
    }

    @PostMapping("/findDormBuildingTreeByConditionTap")
    @ApiOperation(value = "Tap端根据条件查找宿舍楼列表树", notes = "返回宿舍楼列表树,没有时为空")
    public List<Dorm> findDormBuildingTreeByConditionTap(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormService.findDormBuildingTreeByConditionTap(dormBuildVo);
    }
    @PostMapping("/findDormListByTypeAndCategory")
    @ApiOperation(value = "根据条件查找宿舍列表", notes = "返回宿舍楼列表,没有时为空")
    public List<DormBuildingPersonInfo> findDormListByTypeAndCategory(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm){
        return dormService.findDormListByTypeAndCategory(dorm);
    }


    @PostMapping("/batchSaveDormType")
    @ApiOperation(value = "批量保存宿舍类型")
    public void batchSaveDormType(
            @ApiParam(value = "对象")
            @RequestBody Dorm dorm) {
        dormService.batchSaveDormType(dorm);
    }

    @GetMapping("/findDormBuildingAndFloor/{schoolId}")
    @ApiOperation(value = "根据学校id查询宿舍和楼层")
    public List<Building> findDormBuildingAndFloor(
            @ApiParam(value = "学校id")
            @PathVariable String schoolId) {
        return dormService.findDormBuildingAndFloor(schoolId);
    }

    @PostMapping("/findDormByFloorId")
    @ApiOperation(value = "根据楼层id查询宿舍楼")
    public List<Dorm> findDormByFloorId(
            @ApiParam(value = "对象")
            @RequestBody Building building) {
        return dormService.findDormByFloorId(building);
    }
}
