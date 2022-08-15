package com.yice.edu.cn.osp.controller.jy.courseware;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.ResType;
import com.yice.edu.cn.common.pojo.validateClass.*;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jy.courseware.CourseResService;
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
@RequestMapping("/courseRes")
@Api(value = "/courseRes",description = "课件资源表模块")
public class CourseResController {
    @Autowired
    private CourseResService courseResService;

    @PostMapping("/saveCourseRes")
    @ApiOperation(value = "保存课件资源表对象", notes = "返回保存好的课件资源表对象", response= CourseRes.class)
    public ResponseJson saveCourseRes(
            @ApiParam(value = "课件资源表对象", required = true)
            @Validated(GroupThree.class)
            @RequestBody CourseRes courseRes){
        if(courseRes.getResType().equals(ResType.COURSE_TEST)){
            return new ResponseJson("此接口不允许上传课件");
        }
        //判断资源类型和实际文件的后缀名是否匹配
        if(courseRes.notMatchType()){
            return new ResponseJson("资源格式和资源类型不匹配");
        }
        courseRes.setSchoolId(mySchoolId());
        courseRes.setTeacherId(myId());
        CourseRes s=courseResService.saveCourseRes(courseRes);
        return new ResponseJson(s);
    }

    @PostMapping("/saveTestCourseRes")
    @ApiOperation(value = "保存课堂检测", notes = "返回保存好的课件资源表对象", response= CourseRes.class)
    public ResponseJson saveTestCourseRes(
            @ApiParam(value = "保存课堂检测", required = true)
            @Validated(GroupFive.class)
            @RequestBody CourseRes courseRes){
        //判断资源类型和实际文件的后缀名是否匹配
        courseRes.setResType(ResType.COURSE_TEST);
        if(courseRes.notMatchType()){
            return new ResponseJson("资源格式和资源类型不匹配");
        }
        courseRes.setSchoolId(mySchoolId());
        courseRes.setTeacherId(myId());
        CourseRes s=courseResService.saveTestCourseRes(courseRes);
        return new ResponseJson(s);
    }


    @PostMapping("/updateCourseRes")
    @ApiOperation(value = "修改课件资源表对象", notes = "返回响应对象")
    public ResponseJson updateCourseRes(
            @ApiParam(value = "被修改的课件资源表对象,对象属性不为空则修改", required = true)
            @Validated(GroupFour.class)
            @RequestBody CourseRes courseRes){
        courseResService.updateCourseRes(courseRes);
        return new ResponseJson();
    }

    @GetMapping("/findCourseResById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找课件资源表", notes = "返回响应对象", response=CourseRes.class)
    public ResponseJson findCourseResById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CourseRes courseRes=courseResService.findCourseResById(id);
        return new ResponseJson(courseRes);
    }

    @GetMapping("/deleteCourseRes/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCourseRes(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        courseResService.deleteCourseRes(id);
        return new ResponseJson();
    }


    @PostMapping("/findCourseResListByCondition")
    @ApiOperation(value = "根据条件查找课件资源表列表", notes = "返回响应对象,不包含总条数", response=CourseRes.class)
    public ResponseJson findCourseResListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CourseRes courseRes){
        courseRes.setSchoolId(mySchoolId());
        courseRes.setTeacherId(myId());
        List<CourseRes> data=courseResService.findCourseResListByCondition(courseRes);
        return new ResponseJson(data);
    }

    @PostMapping("/findCourseResPageByCondition")
    @ApiOperation(value = "根据条件查找课件资源表分页列表", notes = "返回响应对象,不包含总条数", response=CourseRes.class)
    public ResponseJson findCourseResPageByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CourseRes courseRes){
        courseRes.setSchoolId(mySchoolId());
        courseRes.setTeacherId(myId());
        List<CourseRes> data=courseResService.findCourseResListByCondition(courseRes);
        long count = courseResService.findCourseResCountByCondition(courseRes);
        return new ResponseJson(data,count);
    }


    @PostMapping("/upload")
    @ApiOperation(value = "单个资源上传",notes = "返回图片路径字符串")
    public ResponseJson uploadAvatar(@RequestParam("file") MultipartFile file){

        final String path = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR);
        return new ResponseJson(path);
    }


    @ApiOperation(value = "单个资源重命名", notes = "返回单个课件资源表,没有时为空")
    @PostMapping("/rename")
    public ResponseJson rename(
            @ApiParam(value = "单个资源重命名")
            @Validated(GroupTwo.class)
            @RequestBody CourseRes courseRes
    ){
        final CourseRes result = courseResService.rename(courseRes);
        if(result==null){
            return new ResponseJson(false,"资源不存在！请刷新列表");
        }else{
            return new ResponseJson(result);
        }

    }

    @ApiOperation(value = "单个资源移动", notes = "返回单个课件资源表,没有时为空")
    @PostMapping("/mv")
    public ResponseJson mv(
            @ApiParam(value = "单个资源移动")
            @Validated(GroupOne.class)
            @RequestBody CourseRes courseRes
    ){
        final CourseRes result = courseResService.mv(courseRes);
        if(result==null){
            return new ResponseJson(false,"资源不存在！请刷新列表");
        }else{
            return new ResponseJson(result);
        }
    }

    @ApiOperation(value = "批量资源移动")
    @PostMapping("/mvs")
    public ResponseJson mvs(
            @ApiParam(value = "批量资源移动")
            @Validated(GroupSix.class)
            @RequestBody CourseRes courseRes
    ){
        CourseRes cr = new CourseRes();
        cr.setLv1(courseRes.getLv1());
        cr.setSubjectMateriaId(courseRes.getSubjectMateriaId());
        cr.setIds(courseRes.getIds());
        courseResService.mvs(cr);
        return new ResponseJson(true,"移动成功");
    }

    @ApiOperation(value = "批量资源删除")
    @PostMapping("/deletes")
    public ResponseJson deletes(
            @ApiParam(value = "批量资源删除")
            @RequestBody List<String> ids
    ){
        courseResService.deletes(ids);
        return new ResponseJson(true,"删除成功");
    }


}
