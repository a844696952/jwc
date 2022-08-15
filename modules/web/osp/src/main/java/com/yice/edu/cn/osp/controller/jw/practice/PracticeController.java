package com.yice.edu.cn.osp.controller.jw.practice;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.practice.FileObject;
import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.practice.PracticeFileService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeTeacherService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/practice")
@Api(value = "/practice",description = "模块")
public class PracticeController {
    @Autowired
    private PracticeService practiceService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PracticeFileService practiceFileService;
    @Autowired
    private PracticeTeacherService practiceTeacherService;

    @PostMapping("/savePractice")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson savePractice(
            @ApiParam(value = "对象", required = true)
            @RequestBody Practice practice){
       practice.setSchoolId(mySchoolId());
        Practice s=practiceService.savePractice(practice);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findPracticeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Practice practice=practiceService.findPracticeById(id);
        return new ResponseJson(practice);
    }

    @PostMapping("/update/updatePractice")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updatePractice(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Practice practice){
        practiceService.updatePractice(practice);
        return new ResponseJson();
    }

    @GetMapping("/look/lookPracticeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookPracticeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Practice practice=practiceService.findPracticeById(id);
        return new ResponseJson(practice);
    }

    @PostMapping("/findPracticesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findPracticesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Practice practice){
       practice.setSchoolId(mySchoolId());
        List<Practice> data=practiceService.findPracticeListByCondition(practice);
        long count=practiceService.findPracticeCountByCondition(practice);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findPracticesByCondition1")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findPracticesByCondition1(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Practice practice){
        practice.setSchoolId(mySchoolId());
        if(practice.getRangeTime()!=null&&!practice.getRangeTime().equals("")){
            practice.setPracticeStartdate(practice.getRangeTime()[0]);
            practice.setPracticeEnddate(practice.getRangeTime()[1]);
        }
        List<Practice> data=practiceService.findPracticeListByCondition1(practice);
        long count=practiceService.findPracticeCountByCondition1(practice);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePracticeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOnePracticeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Practice practice){
        Practice one=practiceService.findOnePracticeByCondition(practice);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePractice/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePractice(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        practiceService.deletePractice(id);
        return new ResponseJson();
    }


    @PostMapping("/findPracticeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findPracticeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Practice practice){
       practice.setSchoolId(mySchoolId());
        List<Practice> data=practiceService.findPracticeListByCondition(practice);
        return new ResponseJson(data);
    }


    @PostMapping("/update/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody  Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherListByCondition4Like(teacher);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findPracticeFileListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeFileListById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<PracticeFile> practiceFileList=practiceFileService.findPracticeFileListById(id);
        return new ResponseJson(practiceFileList);
    }

    @GetMapping("/update/findPracticeTeacherListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeTeacherListById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<PracticeTeacher>practiceTeacherList=practiceTeacherService.findPracticeTeacherListById(id);
        return new ResponseJson(practiceTeacherList);
    }

    @GetMapping("/update/findPracticeTeacherNameById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeTeacherNameById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<PracticeTeacher>practiceTeacherList=practiceTeacherService.findPracticeTeacherNameById(id);
        for (int i = 0; i < practiceTeacherList.size(); i++) {
            if(practiceTeacherList.get(i).getTeacherId()==null){
                practiceTeacherList.get(i).setTeacherId(practiceTeacherList.get(i).getQuitteacherId());
            }
        }
        return new ResponseJson(practiceTeacherList);
    }


    @PostMapping("/ignore/uploadFile")
    public FileObject uploadPractice(MultipartFile file){
        FileObject fileObj = new FileObject();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_PRACTICE));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return fileObj;
    }


}
