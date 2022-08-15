package com.yice.edu.cn.xw.controller.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.xw.service.zc.repairNew.RepairFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repairFile")
@Api(value = "/repairFile",description = "新报修文件表模块")
public class RepairFileController {
    @Autowired
    private RepairFileService repairFileService;

    @GetMapping("/findRepairFileById/{id}")
    @ApiOperation(value = "通过id查找新报修文件表", notes = "返回新报修文件表对象")
    public RepairFile findRepairFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return repairFileService.findRepairFileById(id);
    }

    @PostMapping("/saveRepairFile")
    @ApiOperation(value = "保存新报修文件表", notes = "返回新报修文件表对象")
    public RepairFile saveRepairFile(
            @ApiParam(value = "新报修文件表对象", required = true)
            @RequestBody RepairFile repairFile){
        repairFileService.saveRepairFile(repairFile);
        return repairFile;
    }

    @PostMapping("/findRepairFileListByCondition")
    @ApiOperation(value = "根据条件查找新报修文件表列表", notes = "返回新报修文件表列表")
    public List<RepairFile> findRepairFileListByCondition(
            @ApiParam(value = "新报修文件表对象")
            @RequestBody RepairFile repairFile){
        return repairFileService.findRepairFileListByCondition(repairFile);
    }
    @PostMapping("/findRepairFileCountByCondition")
    @ApiOperation(value = "根据条件查找新报修文件表列表个数", notes = "返回新报修文件表总个数")
    public long findRepairFileCountByCondition(
            @ApiParam(value = "新报修文件表对象")
            @RequestBody RepairFile repairFile){
        return repairFileService.findRepairFileCountByCondition(repairFile);
    }

    @PostMapping("/updateRepairFile")
    @ApiOperation(value = "修改新报修文件表", notes = "新报修文件表对象必传")
    public void updateRepairFile(
            @ApiParam(value = "新报修文件表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairFile repairFile){
        repairFileService.updateRepairFile(repairFile);
    }

    @GetMapping("/deleteRepairFile/{id}")
    @ApiOperation(value = "通过id删除新报修文件表")
    public void deleteRepairFile(
            @ApiParam(value = "新报修文件表对象", required = true)
            @PathVariable String id){
        repairFileService.deleteRepairFile(id);
    }
    @PostMapping("/deleteRepairFileByCondition")
    @ApiOperation(value = "根据条件删除新报修文件表")
    public void deleteRepairFileByCondition(
            @ApiParam(value = "新报修文件表对象")
            @RequestBody RepairFile repairFile){
        repairFileService.deleteRepairFileByCondition(repairFile);
    }
    @PostMapping("/findOneRepairFileByCondition")
    @ApiOperation(value = "根据条件查找单个新报修文件表,结果必须为单条数据", notes = "返回单个新报修文件表,没有时为空")
    public RepairFile findOneRepairFileByCondition(
            @ApiParam(value = "新报修文件表对象")
            @RequestBody RepairFile repairFile){
        return repairFileService.findOneRepairFileByCondition(repairFile);
    }
}
