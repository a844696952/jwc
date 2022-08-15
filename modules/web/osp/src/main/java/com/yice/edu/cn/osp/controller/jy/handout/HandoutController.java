package com.yice.edu.cn.osp.controller.jy.handout;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jy.handout.HandoutFileService;
import com.yice.edu.cn.osp.service.jy.handout.HandoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/handout")
@Api(value = "/handout",description = "模块")
public class HandoutController {
    @Autowired
    private HandoutService handoutService;
    @Autowired
    private HandoutFileService handoutFileService;
    @Autowired
    private TeacherClassesService teacherClassesService;

    @PostMapping("/saveHandout")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveHandout(
            @ApiParam(value = "对象", required = true)
            @RequestBody Handout handout){
        handout.setTeacherId(myId());
        handout.setSchoolId(mySchoolId());
        Handout s=handoutService.saveHandout(handout);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHandoutById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findHandoutById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Handout handout=handoutService.findHandoutById(id);
        HandoutFile handoutFile = new HandoutFile();
        handoutFile.setHandoutId(id);
        List<HandoutFile> handoutFiles = handoutFileService.findHandoutFileListByCondition(handoutFile);
        handout.setHandoutFiles(handoutFiles);
        return new ResponseJson(handout);
    }

    @PostMapping("/update/updateHandout")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHandout(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Handout handout){
        handout.setSchoolId(mySchoolId());
        handoutService.updateHandout(handout);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHandoutById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookHandoutById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Handout handout=handoutService.findHandoutById(id);
        return new ResponseJson(handout);
    }

    @PostMapping("/find/findHandoutsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findHandoutsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Handout handout){
        handout.setTeacherId(myId());
        List<Handout> data=handoutService.findHandoutListByCondition(handout);
        long count=handoutService.findHandoutCountByCondition(handout);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHandoutByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneHandoutByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Handout handout){
        handout.setTeacherId(myId());
        Handout one=handoutService.findOneHandoutByCondition(handout);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHandout/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHandout(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        handoutService.deleteHandout(id);
        return new ResponseJson();
    }


    @PostMapping("/list/findHandoutListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findHandoutListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Handout handout){
        handout.setTeacherId(myId());
        List<Handout> data=handoutService.findHandoutListByCondition(handout);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/uploadFile")
    public FileObj uploadHandout(MultipartFile file){
        FileObj fileObj = new FileObj();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_HANDOUT));
        }catch (Exception e){
           fileObj.setSuccess(false);
           fileObj.setPath("文件上传出错了");
        }
        return fileObj;
    }
    @PostMapping("/find/findHandoutsByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public ResponseJson findHandoutsByCondition2(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        handout.setTeacherId(myId());
        List<Handout> data=handoutService.findHandoutsByCondition2(handout);
        long count=handoutService.findHandoutCountByCondition2(handout);
        return new ResponseJson(data,count);
    }

    @GetMapping("/find/findTeacherCourses")
    @ApiOperation(value = "获得老师所教课程列表", notes = "返回列表")
    public List<String> findTeacherCourses(){
        //System.out.println( teacherClassesService.findCourseNameList4Teacher(myId()));
        return teacherClassesService.findCourseNameList4Teacher(myId());
    }

}
