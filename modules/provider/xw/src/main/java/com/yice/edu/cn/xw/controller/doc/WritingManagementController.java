package com.yice.edu.cn.xw.controller.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import com.yice.edu.cn.xw.service.doc.WritingManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writingManagement")
@Api(value = "/writingManagement",description = "模块")
public class WritingManagementController {
    @Autowired
    private WritingManagementService writingManagementService;

    @GetMapping("/findWritingManagementById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WritingManagement findWritingManagementById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return writingManagementService.findWritingManagementById(id);
    }

    @PostMapping("/saveWritingManagement")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WritingManagement saveWritingManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody WritingManagement writingManagement){
        writingManagementService.saveWritingManagement(writingManagement);
        return writingManagement;
    }

    @PostMapping("/findWritingManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WritingManagement> findWritingManagementListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingManagement writingManagement){
        return writingManagementService.findWritingManagementListByCondition(writingManagement);
    }
    @PostMapping("/findWritingManagementCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWritingManagementCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingManagement writingManagement){
        return writingManagementService.findWritingManagementCountByCondition(writingManagement);
    }

    @PostMapping("/updateWritingManagement")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWritingManagement(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WritingManagement writingManagement){
        writingManagementService.updateWritingManagement(writingManagement);
    }

    @GetMapping("/deleteWritingManagement/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWritingManagement(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        writingManagementService.deleteWritingManagement(id);
    }
    @PostMapping("/deleteWritingManagementByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWritingManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingManagement writingManagement){
        writingManagementService.deleteWritingManagementByCondition(writingManagement);
    }
    @PostMapping("/findOneWritingManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WritingManagement findOneWritingManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingManagement writingManagement){
        return writingManagementService.findOneWritingManagementByCondition(writingManagement);
    }

    @PostMapping("/findWritingAndManagementListByCondtion")
    @ApiOperation(value = "根据条件查找列表", notes = "返回多个,没有时为空")
    public List<Writing> findWritingAndManagementListByCondtion(
            @ApiParam(value = "对象")
            @RequestBody WritingManagement writingManagement){
        return writingManagementService.findWritingAndManagementListByCondtion(writingManagement);
    }

    @PostMapping("/lookAndupdateWritingAndWritingManagement")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Writing lookAndupdateWritingAndWritingManagement(
            @ApiParam(value = "需要用的对象")
            @RequestBody Writing writing){
        return writingManagementService.lookAndupdateWritingAndWritingManagement(writing);
    }


    @PostMapping("/findWritingAndWritingManagement")
    @ApiOperation(value = "根据条件查找列表",notes = "返回多个，没有时为空")
    public List<Writing> findWritingAndWritingManagement(
            @ApiParam(value = "对象")
            @RequestBody Writing writing
    ){
        return writingManagementService.findWritingAndWritingManagement(writing);
    }

    @PostMapping("/findWritingAndWritingManagementLong")
    @ApiOperation(value = "根据条件查找总量",notes = "返回总数量")
    public long findWritingAndWritingManagementLong(
            @ApiParam(value = "对象")
            @RequestBody Writing writing
    ){
        return writingManagementService.findWritingAndWritingManagementLong(writing);
    }

    @PostMapping("/getDocManagementReadOrUnRead")
    public List<Department> getDocManagementReadOrUnRead(
            @RequestBody WritingManagement writingManagement
    ){
        return writingManagementService.getDocManagementReadOrUnRead(writingManagement);
    }

}
