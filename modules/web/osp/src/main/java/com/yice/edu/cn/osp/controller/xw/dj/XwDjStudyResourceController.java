package com.yice.edu.cn.osp.controller.xw.dj;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjMyStudyTeacherService;
import com.yice.edu.cn.osp.service.xw.dj.XwDjStudyResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjStudyResource")
@Api(value = "/xwDjStudyResource", description = "学习资源模块")
public class XwDjStudyResourceController {
    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 直接发布
     *
     * @param xwDjStudyResource
     * @return
     */
    @PostMapping("/saveXwDjStudyResource")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = XwDjStudyResource.class)
    public ResponseJson saveXwDjStudyResource(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        Teacher teacher = teacherService.findTeacherById(xwDjStudyResource.getTeacherId());
        xwDjStudyResource.setTeacherName(teacher.getName());
        xwDjStudyResource.setSchoolId(mySchoolId());
        xwDjStudyResource.setState(Constant.STUDY_RESOURCE.STUDY_ISSUE);
        XwDjStudyResource s = xwDjStudyResourceService.saveXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson(s);
    }

    /**
     * 存为草稿
     *
     * @param xwDjStudyResource
     * @return
     */
    @PostMapping("/saveXwDjStudyResourceDraft")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = XwDjStudyResource.class)
    public ResponseJson saveXwDjStudyResourceDraft(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        Teacher teacher = teacherService.findTeacherById(xwDjStudyResource.getTeacherId());
        xwDjStudyResource.setTeacherName(teacher.getName());
        xwDjStudyResource.setSchoolId(mySchoolId());
        xwDjStudyResource.setState(Constant.STUDY_RESOURCE.STUDY_NO_ISSUE);
        XwDjStudyResource s = xwDjStudyResourceService.saveXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwDjStudyResourceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = XwDjStudyResource.class)
    public ResponseJson findXwDjStudyResourceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwDjStudyResource xwDjStudyResource = xwDjStudyResourceService.findXwDjStudyResourceById(id);
        return new ResponseJson(xwDjStudyResource);
    }

    /**
     * 修改发布
     *
     * @param xwDjStudyResource
     * @return
     */
    @PostMapping("/update/updateXwDjStudyResource")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwDjStudyResource(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResource.setState(Constant.STUDY_RESOURCE.STUDY_ISSUE);
        xwDjStudyResourceService.updateXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson();
    }

    /**
     * 修改存草稿
     *
     * @param xwDjStudyResource
     * @return
     */
    @PostMapping("/update/updateXwDjStudyResourceDraft")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwDjStudyResourceDraft(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResource.setState(Constant.STUDY_RESOURCE.STUDY_NO_ISSUE);
        xwDjStudyResourceService.updateXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson();
    }


    @PostMapping("/update/updateXwDjStudyResourceClose")
    @ApiOperation(value = "关闭", notes = "返回响应对象")
    public ResponseJson updateXwDjStudyResourceClose(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResource.setState(Constant.STUDY_RESOURCE.STUDY_CLOSE);
        xwDjStudyResourceService.updateXwDjStudyResource(xwDjStudyResource);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjStudyResourceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = XwDjStudyResource.class)
    public ResponseJson lookXwDjStudyResourceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwDjStudyResource xwDjStudyResource = xwDjStudyResourceService.findXwDjStudyResourceById(id);
        return new ResponseJson(xwDjStudyResource);
    }

    @PostMapping("/findXwDjStudyResourcesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = XwDjStudyResource.class)
    public ResponseJson findXwDjStudyResourcesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResource.setSchoolId(mySchoolId());
        List<XwDjStudyResource> data = xwDjStudyResourceService.findXwDjStudyResourceListByCondition(xwDjStudyResource);
        long count = xwDjStudyResourceService.findXwDjStudyResourceCountByCondition(xwDjStudyResource);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwDjStudyResourceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = XwDjStudyResource.class)
    public ResponseJson findOneXwDjStudyResourceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        XwDjStudyResource one = xwDjStudyResourceService.findOneXwDjStudyResourceByCondition(xwDjStudyResource);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwDjStudyResource/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjStudyResource(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwDjStudyResourceService.deleteXwDjStudyResource(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjStudyResourceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = XwDjStudyResource.class)
    public ResponseJson findXwDjStudyResourceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResource.setSchoolId(mySchoolId());
        List<XwDjStudyResource> data = xwDjStudyResourceService.findXwDjStudyResourceListByCondition(xwDjStudyResource);
        return new ResponseJson(data);
    }
}
