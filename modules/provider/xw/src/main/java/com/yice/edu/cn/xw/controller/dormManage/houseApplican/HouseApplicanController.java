package com.yice.edu.cn.xw.controller.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplican;
import com.yice.edu.cn.xw.service.dormManage.houseApplican.HouseApplicanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houseApplican")
@Api(value = "/houseApplican",description = "模块")
public class HouseApplicanController {
    @Autowired
    private HouseApplicanService houseApplicanService;

    @GetMapping("/findHouseApplicanById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HouseApplican findHouseApplicanById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return houseApplicanService.findHouseApplicanById(id);
    }

    @PostMapping("/saveHouseApplican")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplican saveHouseApplican(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.saveHouseApplican(houseApplican);
        return houseApplican;
    }

    @PostMapping("/findHouseApplicanListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplican> findHouseApplicanListByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findHouseApplicanListByCondition(houseApplican);
    }
    @PostMapping("/findHouseApplicanCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHouseApplicanCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findHouseApplicanCountByCondition(houseApplican);
    }

    @PostMapping("/updateHouseApplican")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHouseApplican(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.updateHouseApplican(houseApplican);
    }

    @GetMapping("/deleteHouseApplican/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHouseApplican(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        houseApplicanService.deleteHouseApplican(id);
    }
    @PostMapping("/deleteHouseApplicanByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHouseApplicanByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.deleteHouseApplicanByCondition(houseApplican);
    }
    @PostMapping("/findOneHouseApplicanByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public HouseApplican findOneHouseApplicanByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findOneHouseApplicanByCondition(houseApplican);
    }



    @PostMapping("/findApprovalPending")
    @ApiOperation(value = "我的批阅 待审批", notes = "返回列表")
    public List<HouseApplican> findApprovalPending(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findApprovalPending(houseApplican);
    }
    @PostMapping("/findApprovalPendingCount")
    @ApiOperation(value = "我的批阅 待审批 根据条件查找列表个数", notes = "返回总个数")
    public long findApprovalPendingCount(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findApprovalPendingCount(houseApplican);
    }


    @PostMapping("/findPassPending")
    @ApiOperation(value = "已审批", notes = "返回列表")
    public List<HouseApplican> findPassPending(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findPassPending(houseApplican);
    }
    @PostMapping("/findPassPendingCount")
    @ApiOperation(value = "已审批 根据条件查找列表个数", notes = "返回总个数")
    public long findPassPendingCount(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findPassPendingCount(houseApplican);
    }

    @PostMapping("/saveHouseApplicanFromParent")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplican saveHouseApplicanFromParent(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.saveHouseApplicanFromParent(houseApplican);
        return houseApplican;
    }


    @PostMapping("/updateHouseApplicanAndTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHouseApplicanAndTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplican houseApplican){
        houseApplicanService.updateHouseApplicanAndTeacher(houseApplican);
    }

    @PostMapping("/findMyApproval")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplican> findMyApproval(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findMyApproval(houseApplican);
    }
    @PostMapping("/findMyApprovalCount")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findMyApprovalCount(
            @ApiParam(value = "对象")
            @RequestBody HouseApplican houseApplican){
        return houseApplicanService.findMyApprovalCount(houseApplican);
    }

}
