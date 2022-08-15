package com.yice.edu.cn.tap.controller.xw.djStudy;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.tap.service.xw.dj.study.XwDjMyStudyTeacherService;
import com.yice.edu.cn.tap.service.xw.dj.study.XwDjStudyResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjMyStudyTeacher")
@Api(value = "/xwDjMyStudyTeacher", description = "我的学习模块（教师）")
public class XwDjMyStudyTeacherController {
    @Autowired
    private XwDjMyStudyTeacherService xwDjMyStudyTeacherService;

    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

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
        List<XwDjMyStudyTeacher> data = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
        long count = xwDjMyStudyTeacherService.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);
        return new ResponseJson(data, count);
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


}
