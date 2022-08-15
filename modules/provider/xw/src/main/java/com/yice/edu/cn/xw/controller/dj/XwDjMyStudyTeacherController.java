package com.yice.edu.cn.xw.controller.dj;

import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.xw.service.dj.XwDjMyStudyTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjMyStudyTeacher")
@Api(value = "/xwDjMyStudyTeacher", description = "模块")
public class XwDjMyStudyTeacherController {
    @Autowired
    private XwDjMyStudyTeacherService xwDjMyStudyTeacherService;

    @GetMapping("/findXwDjMyStudyTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwDjMyStudyTeacher findXwDjMyStudyTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherById(id);
    }

    @PostMapping("/saveXwDjMyStudyTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwDjMyStudyTeacher saveXwDjMyStudyTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherService.saveXwDjMyStudyTeacher(xwDjMyStudyTeacher);
        return xwDjMyStudyTeacher;
    }

    @PostMapping("/findXwDjMyStudyTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
    }

    @PostMapping("/findXwDjMyStudyTeacherByTime")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherByTime(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherByTime(xwDjMyStudyTeacher);
    }

    @PostMapping("/findXwDjMyStudyTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwDjMyStudyTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);
    }

    @PostMapping("/findXwDjMyStudyTeacherCountByTime")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwDjMyStudyTeacherCountByTime(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByTime(xwDjMyStudyTeacher);
    }

    @PostMapping("/updateXwDjMyStudyTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwDjMyStudyTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherService.updateXwDjMyStudyTeacher(xwDjMyStudyTeacher);
    }

    @GetMapping("/deleteXwDjMyStudyTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwDjMyStudyTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        xwDjMyStudyTeacherService.deleteXwDjMyStudyTeacher(id);
    }

    @PostMapping("/deleteXwDjMyStudyTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwDjMyStudyTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherService.deleteXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    @PostMapping("/findOneXwDjMyStudyTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwDjMyStudyTeacher findOneXwDjMyStudyTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findOneXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    @PostMapping("/findXwDjMyStudyTeacherListByTeacherId")
    @ApiOperation(value = "根据条件查找,结果必须为集合", notes = "返回单个,没有时为空")
    public List<StudyTeacherDto> findXwDjMyStudyTeacherListByTeacherId(
            @ApiParam(value = "对象")
            @RequestBody StudyTeacherDto studyTeacherDto
    ) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByTeacherId(studyTeacherDto);
    }

    @PostMapping("/findXwDjMyStudyTeacherCountByTeacherId")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwDjMyStudyTeacherCountByTeacherId(
            @ApiParam(value = "对象")
            @RequestBody StudyTeacherDto studyTeacherDto) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByTeacherId(studyTeacherDto);
    }

    @PostMapping("/findXwDjMyStudyTeacherListByStudyResourceId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByStudyResourceId(
            @ApiParam(value = "对象")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByStudyResourceId(xwDjMyStudyTeacher);
    }
}
