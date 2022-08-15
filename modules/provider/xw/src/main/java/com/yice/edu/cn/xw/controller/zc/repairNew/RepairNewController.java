package com.yice.edu.cn.xw.controller.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.xw.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repairNew")
@Api(value = "/repairNew",description = "新报修表模块")
public class RepairNewController {
    @Autowired
    private RepairNewService repairNewService;

    @GetMapping("/findRepairNewById/{id}")
    @ApiOperation(value = "通过id查找新报修表", notes = "返回新报修表对象")
    public RepairNew findRepairNewById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return repairNewService.findRepairNewById(id);
    }

    @PostMapping("/saveRepairNew")
    @ApiOperation(value = "保存新报修表", notes = "返回新报修表对象")
    public RepairNew saveRepairNew(
            @ApiParam(value = "新报修表对象", required = true)
            @RequestBody RepairNew repairNew){
        repairNewService.saveRepairNew(repairNew);

        return repairNew;
    }

    @PostMapping("/findRepairNewListByCondition")
    @ApiOperation(value = "根据条件查找新报修表列表", notes = "返回新报修表列表")
    public List<RepairNew> findRepairNewListByCondition(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewListByCondition(repairNew);
    }
    @PostMapping("/findRepairNewCountByCondition")
    @ApiOperation(value = "根据条件查找新报修表列表个数", notes = "返回新报修表总个数")
    public long findRepairNewCountByCondition(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewCountByCondition(repairNew);
    }

    @PostMapping("/updateRepairNew")
    @ApiOperation(value = "修改新报修表", notes = "新报修表对象必传")
    public void updateRepairNew(
            @ApiParam(value = "新报修表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew){
        repairNewService.updateRepairNew(repairNew);
    }

    @GetMapping("/deleteRepairNew/{id}")
    @ApiOperation(value = "通过id删除新报修表")
    public void deleteRepairNew(
            @ApiParam(value = "新报修表对象", required = true)
            @PathVariable String id){
        repairNewService.deleteRepairNew(id);
    }
    @PostMapping("/deleteRepairNewByCondition")
    @ApiOperation(value = "根据条件删除新报修表")
    public void deleteRepairNewByCondition(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        repairNewService.deleteRepairNewByCondition(repairNew);
    }
    @PostMapping("/findOneRepairNewByCondition")
    @ApiOperation(value = "根据条件查找单个新报修表,结果必须为单条数据", notes = "返回单个新报修表,没有时为空")
    public RepairNew findOneRepairNewByCondition(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findOneRepairNewByCondition(repairNew);
    }


    @PostMapping("/updateRepairNewPerson")
    @ApiOperation(value = "修改报修信息表", notes = "报修信息表对象必传")
    public void updateRepairNewPerson(
            @ApiParam(value = "报修信息表对象,对象属性不为空则修改", required = true)
            @RequestBody  RepairNew repairNew){
        repairNewService.updateRepairNewPerson(repairNew);
    }


    @PostMapping("/scrapRepairNew")
    @ApiOperation(value = "修改新报修表", notes = "新报修表对象必传")
    public void scrapRepairNew(
            @ApiParam(value = "新报修表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew){
        repairNewService.scrapRepairNew(repairNew);
    }


    @GetMapping("/lookRepairNewByAssetNo/{assetNo}")
    @ApiOperation(value = "通过id查找新报修表", notes = "返回新报修表对象")
    public RepairNew lookRepairNewByAssetNo(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String assetNo){
        return repairNewService.lookRepairNewByAssetNo(assetNo);
    }


    @GetMapping("/findRepairNewByIdNew/{id}")
    @ApiOperation(value = "通过id查找新报修表", notes = "返回新报修表对象")
    public RepairNew findRepairNewByIdNew(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return repairNewService.findRepairNewByIdNew(id);
    }


    @PostMapping("/findRepairNewUpkeepCostsBySchool")
    @ApiOperation(value = "根据学校查找新报修表维修总费用", notes = "报修表维修总费用")
    public double findRepairNewUpkeepCostsBySchool(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewUpkeepCostsBySchool(repairNew);
    }

    @PostMapping("/findRepairNewsCountByWeek")
    @ApiOperation(value = "根据条件查找当前周报修次数", notes = "返回报修次数")
    public long findRepairNewsCountByWeek(
            @ApiParam(value = "报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewsCountByWeek(repairNew);
    }


    @PostMapping("/findRepairNewsCountByWeekLose1")
    @ApiOperation(value = "根据条件查找当前周报修次数", notes = "返回报修次数")
    public long findRepairNewsCountByWeekLose1(
            @ApiParam(value = "报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewsCountByWeekLose1(repairNew);
    }


    @PostMapping("/selectBuildingNameList")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public List<RepairNew> selectBuildingNameList(
            @ApiParam(value = "保修表中楼栋对象")
            @RequestBody RepairNew repairNew) {
        return repairNewService.selectBuildingNameList(repairNew);
    }

    @PostMapping("/selectRoomName")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public List<RepairNew> selectRoomName(
            @ApiParam(value = "保修表中楼栋对象")
            @RequestBody RepairNew repairNew) {
        return repairNewService.selectRoomName(repairNew);
    }
    @PostMapping("/findRepairNewsBySchoolIds")
    @ApiOperation(value = "根据条件查找新报修表列表", notes = "返回新报修表列表")
    public List<RepairNew> findRepairNewsBySchoolIds(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewsBySchoolIds(repairNew);
    }
    @PostMapping("/findRepairNewCountBySchoolIds")
    @ApiOperation(value = "根据条件查找新报修表列表个数", notes = "返回新报修表总个数")
    public long findRepairNewCountBySchoolIds(
            @ApiParam(value = "新报修表对象")
            @RequestBody RepairNew repairNew){
        return repairNewService.findRepairNewCountBySchoolIds(repairNew);
    }

}
