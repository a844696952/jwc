package com.yice.edu.cn.xw.controller.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import com.yice.edu.cn.xw.service.dormManage.houseApplican.HouseApplicanFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houseApplicanFiles")
@Api(value = "/houseApplicanFiles",description = "模块")
public class HouseApplicanFilesController {
    @Autowired
    private HouseApplicanFilesService houseApplicanFilesService;

    @GetMapping("/findHouseApplicanFilesById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HouseApplicanFiles findHouseApplicanFilesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return houseApplicanFilesService.findHouseApplicanFilesById(id);
    }

    @PostMapping("/saveHouseApplicanFiles")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HouseApplicanFiles saveHouseApplicanFiles(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        houseApplicanFilesService.saveHouseApplicanFiles(houseApplicanFiles);
        return houseApplicanFiles;
    }

    @PostMapping("/findHouseApplicanFilesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HouseApplicanFiles> findHouseApplicanFilesListByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        return houseApplicanFilesService.findHouseApplicanFilesListByCondition(houseApplicanFiles);
    }
    @PostMapping("/findHouseApplicanFilesCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHouseApplicanFilesCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        return houseApplicanFilesService.findHouseApplicanFilesCountByCondition(houseApplicanFiles);
    }

    @PostMapping("/updateHouseApplicanFiles")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHouseApplicanFiles(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        houseApplicanFilesService.updateHouseApplicanFiles(houseApplicanFiles);
    }

    @GetMapping("/deleteHouseApplicanFiles/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHouseApplicanFiles(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        houseApplicanFilesService.deleteHouseApplicanFiles(id);
    }
    @PostMapping("/deleteHouseApplicanFilesByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHouseApplicanFilesByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        houseApplicanFilesService.deleteHouseApplicanFilesByCondition(houseApplicanFiles);
    }
    @PostMapping("/findOneHouseApplicanFilesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public HouseApplicanFiles findOneHouseApplicanFilesByCondition(
            @ApiParam(value = "对象")
            @RequestBody HouseApplicanFiles houseApplicanFiles){
        return houseApplicanFilesService.findOneHouseApplicanFilesByCondition(houseApplicanFiles);
    }
}
