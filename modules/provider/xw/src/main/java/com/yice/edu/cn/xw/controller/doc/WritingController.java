package com.yice.edu.cn.xw.controller.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.xw.service.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writing")
@Api(value = "/writing",description = "模块")
public class WritingController {
    @Autowired
    private WritingService writingService;

    @GetMapping("/findWritingById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Writing findWritingById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return writingService.findWritingById(id);
    }

    @PostMapping("/saveWriting")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Writing saveWriting(
            @ApiParam(value = "对象", required = true)
            @RequestBody DocDepartment docDepartment){
        writingService.saveWriting(docDepartment);
        return docDepartment.getWriting();
    }

    @PostMapping("/findWritingListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Writing> findWritingListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Writing writing){
        return writingService.findWritingListByCondition(writing);
    }
    @PostMapping("/findWritingCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWritingCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Writing writing){
        return writingService.findWritingCountByCondition(writing);
    }

    @PostMapping("/updateWriting")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWriting(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DocDepartment docDepartment){
        writingService.updateWriting(docDepartment);
    }

    @GetMapping("/deleteWriting/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWriting(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        writingService.deleteWriting(id);
    }
    @PostMapping("/deleteWritingByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWritingByCondition(
            @ApiParam(value = "对象")
            @RequestBody Writing writing){
        writingService.deleteWritingByCondition(writing);
    }
    @PostMapping("/findOneWritingByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Writing findOneWritingByCondition(
            @ApiParam(value = "对象")
            @RequestBody Writing writing){
        return writingService.findOneWritingByCondition(writing);
    }

    @PostMapping("/getdepartmentUpdate")
    @ApiOperation(value = "判断组织架构树部门是否被修改",notes = "返回布尔型")
    public Boolean getdepartmentUpdate(
            @ApiParam(value = "部门架构树与发文id")
            @RequestBody DocDepartment docDepartment
    ){
        return writingService.getdepartmentUpdate(docDepartment);
    }

    @GetMapping("/getWritingRejectUpdate/{id}")
    @ApiOperation(value = "修改驳回状态的查看,并返回公文的记录",notes = "返回公文记录")
    public Writing getWritingRejectUpdate(
            @PathVariable String id
    ){
        return writingService.getWritingRejectUpdate(id);
    }

}
