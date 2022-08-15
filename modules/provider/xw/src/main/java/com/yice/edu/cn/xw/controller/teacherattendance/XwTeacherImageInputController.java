package com.yice.edu.cn.xw.controller.teacherattendance;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.xw.service.teacherattendance.XwTeacherImageInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwTeacherImageInput")
@Api(value = "/xwTeacherImageInput", description = "模块")
public class XwTeacherImageInputController {
    @Autowired
    private XwTeacherImageInputService xwTeacherImageInputService;

    @GetMapping("/findXwTeacherImageInputById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwTeacherImageInput findXwTeacherImageInputById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return xwTeacherImageInputService.findXwTeacherImageInputById(id);
    }

    @GetMapping("/findXwTeacherImageInputByTeacherId/{teacherId}/{schoolId}")
    @ApiOperation(value = "通过teacherId查找", notes = "返回对象")
    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherId(
            @ApiParam(value = "需要用到的teacherId", required = true)
            @PathVariable("teacherId") String teacherId,
            @PathVariable("schoolId") String schoolId) {
        return xwTeacherImageInputService.findXwTeacherImageInputByTeacherId(teacherId, schoolId);
    }

    @PostMapping("/saveXwTeacherImageInput")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwTeacherImageInput saveXwTeacherImageInput(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputService.saveXwTeacherImageInput(xwTeacherImageInput);
        return xwTeacherImageInput;
    }

    @PostMapping("/findXwTeacherImageInputListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwTeacherImageInput> findXwTeacherImageInputListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputService.findXwTeacherImageInputListByCondition(xwTeacherImageInput);
    }

    @PostMapping("/findXwTeacherImageInputCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwTeacherImageInputCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputService.findXwTeacherImageInputCountByCondition(xwTeacherImageInput);
    }

    @PostMapping("/updateXwTeacherImageInput")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwTeacherImageInput(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputService.updateXwTeacherImageInput(xwTeacherImageInput);
    }

    @GetMapping("/deleteXwTeacherImageInput/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwTeacherImageInput(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        xwTeacherImageInputService.deleteXwTeacherImageInput(id);
    }

    @PostMapping("/deleteXwTeacherImageInputByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwTeacherImageInputByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputService.deleteXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    @PostMapping("/findOneXwTeacherImageInputByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwTeacherImageInput findOneXwTeacherImageInputByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputService.findOneXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    @PostMapping("/findXwTeacherImageInputListAlls")
    public List<XwTeacherImageInput> findXwTeacherImageInputListAlls(
            @ApiParam(value = "对象")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputService.findXwTeacherImageInputListAlls(xwTeacherImageInput);
    }

    @GetMapping("/findXwTeacherImageInputListAllCount/{schoolId}/{status}")
    @ApiOperation(value = "查询全表符合条件条数", notes = "返回全部结果")
    public long findXwTeacherImageInputListAllCount(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId,
            @PathVariable("status") int status) {
        long count = xwTeacherImageInputService.findXwTeacherImageInputListAllCount(schoolId,status);
        return count;
    }

    @GetMapping("/findXwTeacherImageInputByTeacherName/{teacherName}/{schoolId}")
    @ApiOperation(value = "根据教师名称查询", notes = "返回一条结果")
    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherName(
            @ApiParam(value = "教师名称")
            @PathVariable("teacherName") String teacherName,
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId
    ) {
        return xwTeacherImageInputService.findXwTeacherImageInputByTeacherName(teacherName, schoolId);
    }

    @GetMapping("/findXwTeacherImageInputByTeacherNameCount/{teacherName}/{schoolId}")
    @ApiOperation(value = "根据教师名称查询返回符合条件的数量", notes = "返回结果")
    public long findXwTeacherImageInputByTeacherNameCount(
            @ApiParam(value = "教师名称")
            @PathVariable("teacherName") String teacherName,
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId
    ) {
        return xwTeacherImageInputService.findXwTeacherImageInputByTeacherNameCount(teacherName, schoolId);
    }

    @GetMapping("/findXwTeacherImageleft/{schoolId}")
    @ApiOperation(value = "查询教师表中已删除图像表中未删除的数据", notes = "返回结果list")
    public List<String> findXwTeacherleft(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId
    ) {
        return xwTeacherImageInputService.findXwTeacherleft(schoolId);
    }

    @PostMapping("/batchSaveXwTeacherImageInput")
    public void batchSaveXwTeacherImageInput(
            @ApiParam(value = "对象")
            @RequestBody List<XwTeacherImageInput> xwTeacherImageInputs) {
        xwTeacherImageInputService.batchSaveXwTeacherImageInput(xwTeacherImageInputs);
    }

}
