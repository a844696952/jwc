package com.yice.edu.cn.osp.controller.xw.dj;

import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjMyStudyTeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjStudyResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjMyStudyTeacher")
@Api(value = "/xwDjMyStudyTeacher", description = "我的学习模块（教师）")
public class XwDjMyStudyTeacherController {
    @Autowired
    private XwDjMyStudyTeacherService xwDjMyStudyTeacherService;

    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 学习资源发送按钮
     *
     * @param list
     * @return
     */
    @PostMapping("/saveXwDjMyStudyTeacher")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson saveXwDjMyStudyTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<XwDjMyStudyTeacher> list) {
        if (!list.isEmpty()) {
            for (XwDjMyStudyTeacher xwDjMyStudyTeacher : list) {
                xwDjMyStudyTeacher.setSchoolId(mySchoolId());
                xwDjMyStudyTeacher.setState(Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_NO_STUDY);
                XwDjMyStudyTeacher s = xwDjMyStudyTeacherService.saveXwDjMyStudyTeacher(xwDjMyStudyTeacher);
            }
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findXwDjMyStudyTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson findXwDjMyStudyTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwDjMyStudyTeacher xwDjMyStudyTeacher = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherById(id);
        String studyResourceId = xwDjMyStudyTeacher.getStudyResourceId();
        XwDjStudyResource xwDjStudyResource = xwDjStudyResourceService.findXwDjStudyResourceById(studyResourceId);
        xwDjMyStudyTeacher.setTypeName(xwDjStudyResource.getTypeName());
        xwDjMyStudyTeacher.setXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson(xwDjMyStudyTeacher);
    }

    @PostMapping("/update/updateXwDjMyStudyTeacher")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwDjMyStudyTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacher.setState(Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_STUDY);
        xwDjMyStudyTeacherService.updateXwDjMyStudyTeacher(xwDjMyStudyTeacher);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjMyStudyTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson lookXwDjMyStudyTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwDjMyStudyTeacher xwDjMyStudyTeacher = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherById(id);
        //得到资源信息
        if (!StringUtils.isEmpty(xwDjMyStudyTeacher)) {
            String studyResourceId = xwDjMyStudyTeacher.getStudyResourceId();
            XwDjStudyResource study = xwDjStudyResourceService.findXwDjStudyResourceById(studyResourceId);
            xwDjMyStudyTeacher.setTypeName(study.getTypeName());
            xwDjMyStudyTeacher.setXwDjStudyResource(study);
        }
        return new ResponseJson(xwDjMyStudyTeacher);
    }

    @PostMapping("/findXwDjMyStudyTeachersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson findXwDjMyStudyTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacher.setSchoolId(mySchoolId());
//        List<XwDjMyStudyTeacher> data = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
//        long count = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);
        List<XwDjMyStudyTeacher> data1 = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherByTime(xwDjMyStudyTeacher);
        long count1 = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByTime(xwDjMyStudyTeacher);
        return new ResponseJson(data1, count1);
    }

    @PostMapping("/findOneXwDjMyStudyTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = XwDjMyStudyTeacher.class)
    public ResponseJson findOneXwDjMyStudyTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        XwDjMyStudyTeacher one = xwDjMyStudyTeacherService.findOneXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwDjMyStudyTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjMyStudyTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwDjMyStudyTeacherService.deleteXwDjMyStudyTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjMyStudyTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = XwDjMyStudyTeacher.class)
    public ResponseJson findXwDjMyStudyTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacher.setSchoolId(mySchoolId());
        List<XwDjMyStudyTeacher> data = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
        return new ResponseJson(data);
    }

    @PostMapping("/findXwDjMyStudyTeacherListByTeacherId")
    @ApiOperation(value = "根据教师id条件查找", notes = "返回响应对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson findXwDjMyStudyTeacherListByTeacherId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StudyTeacherDto studyTeacherDto) {
        studyTeacherDto.setSchoolId(mySchoolId());
        List<StudyTeacherDto> data = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByTeacherId(studyTeacherDto);
        long count = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByTeacherId(studyTeacherDto);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findXwDjMyStudyTeachersByStudyResourceId")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = XwDjMyStudyTeacher.class)
    public ResponseJson findXwDjMyStudyTeachersByStudyResourceId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacher.setSchoolId(mySchoolId());
        List<XwDjMyStudyTeacher> data = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByStudyResourceId(xwDjMyStudyTeacher);

        List<XwDjMyStudyTeacher> dd = data.stream().map(skt -> {
            String teacherId = skt.getTeacherId();
            Teacher tt = teacherService.findTeacherById(teacherId);
            Optional.ofNullable(tt.getName()).ifPresent(name -> skt.setName(name));
            Optional.ofNullable(tt.getImgUrl()).ifPresent(img -> skt.setImgUrl(img));
            return skt;
        }).collect(Collectors.toList());

        //已学习
        List<XwDjMyStudyTeacher> yetStudy = dd.stream().
                filter(skt -> skt.getState().equals(Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_STUDY)).
                collect(Collectors.toList());
        //未学习
        List<XwDjMyStudyTeacher> notStudy = dd.stream().
                filter(skt -> skt.getState().equals(Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_NO_STUDY)).
                collect(Collectors.toList());

        return new ResponseJson(yetStudy, notStudy);
    }


}
