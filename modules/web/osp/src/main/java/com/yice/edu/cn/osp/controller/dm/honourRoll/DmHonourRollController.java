package com.yice.edu.cn.osp.controller.dm.honourRoll;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import com.yice.edu.cn.osp.service.dm.classes.DmClassDescService;
import com.yice.edu.cn.osp.service.dm.honourRoll.DmHonourRollService;
import com.yice.edu.cn.osp.service.dm.honourRoll.DmHonourRollStudentService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmHonourRoll")
@Api(value = "/dmHonourRoll",description = "光荣榜，管理模块")
public class DmHonourRollController {
    @Autowired
    private DmHonourRollService dmHonourRollService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;
    @Autowired
    private DmClassCardService dmClassCardService;
    @Autowired
    private DmClassDescService dmClassDescService;
    @PostMapping("/saveDmHonourRoll")
    @ApiOperation(value = "保存光荣榜，管理对象", notes = "返回保存好的光荣榜，管理对象", response=DmHonourRoll.class)
    @EccJpush(type = Extras.DM_PHOTO_MSG,content = "新增光荣榜")
    public ResponseJson saveDmHonourRoll(
            @ApiParam(value = "光荣榜，管理对象", required = true)
            @RequestBody DmHonourRoll dmHonourRoll){

        DmClassCard dm = new DmClassCard();
        dm.setClassId(dmHonourRoll.getClassId());
        DmClassCard dmClassCard = dmClassCardService.findOneDmClassCardByCondition(dm);
        if(dmClassCard==null){
            return new ResponseJson(false,"该班级未绑定班牌，请先绑定");
        }
        dmHonourRoll.setSchoolId(mySchoolId());
        dmHonourRoll.setTeacherId(myId());
        DmHonourRoll d = new DmHonourRoll();
        d.setSchoolId(dmHonourRoll.getSchoolId());
        d.setClassId(dmHonourRoll.getClassId());
        d.setHonourTime(dmHonourRoll.getHonourTime());
        d.setType(dmHonourRoll.getType());
        long cnt = dmHonourRollService.findDmHonourRollCountByCondition(d);
        if(cnt==0){
            DmHonourRoll s=dmHonourRollService.saveDmHonourRoll(dmHonourRoll);
            return new ResponseJson(s);
        }else{
            return new ResponseJson(false,dmHonourRoll.getHonourTime()+"日期存在，请更换日期");
        }

    }

    @GetMapping("/update/findDmHonourRollById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找光荣榜，管理", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRoll dmHonourRoll=dmHonourRollService.findDmHonourRollById(id);
        return new ResponseJson(dmHonourRoll);
    }

    @PostMapping("/update/updateDmHonourRoll")
    @ApiOperation(value = "修改光荣榜，管理对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_PHOTO_MSG,content = "修改光荣榜")
    public ResponseJson updateDmHonourRoll(
            @ApiParam(value = "被修改的光荣榜，管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRoll dmHonourRoll){
        DmHonourRoll d = new DmHonourRoll();
        d.setSchoolId(dmHonourRoll.getSchoolId());
        d.setClassId(dmHonourRoll.getClassId());
        d.setHonourTime(dmHonourRoll.getHonourTime());
        d.setId(dmHonourRoll.getId());
        long cnt = dmHonourRollService.findDmHonourRoll(d);
        if(cnt==0){
            dmHonourRollService.updateDmHonourRoll(dmHonourRoll);
            return new ResponseJson();
        }else{
            return new ResponseJson(false,dmHonourRoll.getHonourTime()+"日期存在，请更换日期");
        }

    }

    @GetMapping("/look/lookDmHonourRollById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找光荣榜，管理", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson lookDmHonourRollById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRoll dmHonourRoll=dmHonourRollService.findDmHonourRollById(id);
        return new ResponseJson(dmHonourRoll);
    }

    @PostMapping("/findDmHonourRollsByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理", notes = "返回响应对象", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRoll dmHonourRoll){
        if(StringUtils.isEmpty(dmHonourRoll.getClassId())){
            JwClasses jwClasses = getOne();
            if(jwClasses==null){
                return new ResponseJson();
            }else{
                dmHonourRoll.setClassId(jwClasses.getId());
            }
        }
        dmHonourRoll.setSchoolId(mySchoolId());
        List<DmHonourRoll> data=dmHonourRollService.findDmHonourRollListByCondition(dmHonourRoll);
        long count=dmHonourRollService.findDmHonourRollCountByCondition(dmHonourRoll);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmHonourRollByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜，管理,结果必须为单条数据", notes = "没有时返回空", response=DmHonourRoll.class)
    public ResponseJson findOneDmHonourRollByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmHonourRoll dmHonourRoll){
        DmHonourRoll one=dmHonourRollService.findOneDmHonourRollByCondition(dmHonourRoll);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmHonourRoll/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_PHOTO_MSG,content = "删除光荣榜")
    public ResponseJson deleteDmHonourRoll(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmHonourRollService.deleteDmHonourRoll(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmHonourRollListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理列表", notes = "返回响应对象,不包含总条数", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRoll dmHonourRoll){
       dmHonourRoll.setSchoolId(mySchoolId());
        List<DmHonourRoll> data=dmHonourRollService.findDmHonourRollListByCondition(dmHonourRoll);
        return new ResponseJson(data);
    }
    @GetMapping("/getTeacherClassPostCourseList")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourseList() {
        JwClasses jwClasses = new JwClasses();
        jwClasses.setPager(new Pager());
        jwClasses.setHeadTeacher(myId());
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> t = dmClassDescService.findDmClassesListByCardCondition(jwClasses);
        if(t.size()==0){
            return new ResponseJson();
        }
        return new ResponseJson(t);
    }
    @GetMapping("/getStudentListByConditionWithFamily/{id}")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByConditionWithFamily(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Student student = new Student();
        student.setClassesId(id);
        student.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setPaging(false);
        student.setPager(pager);
        List<Student> data=studentService.findStudentListByConditionWithFamily(student);
        return new ResponseJson(data);
    }
    @GetMapping("/findTeacherClassPostCourse")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourse() {
        return new ResponseJson(getOne());
    }
    public JwClasses getOne(){
        JwClasses jwClasses = new JwClasses();
        jwClasses.setPager(new Pager());
        jwClasses.setHeadTeacher(myId());
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> t = dmClassDescService.findDmClassesListByCardCondition(jwClasses);
        if(t.size()==0){
            return null;
        }
        return t.get(0);
    }
    @PostMapping("/explorerHonourRoll")
    public void explorerHonourRoll(@ApiParam(value = "光荣榜")
                                  @RequestBody DmHonourRoll dmHonourRoll, HttpServletResponse response) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=honourRoll.xls");
        dmHonourRoll.setPager(new Pager());
        dmHonourRoll.setSchoolId(mySchoolId());
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dmHonourRollService.explorerHonourRoll(dmHonourRoll);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/findTeacherClassPostCourseOne/{id}")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourse(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JwClasses jwClasses = jwClassesService.findJwClassesById(id);
        if(jwClasses==null){
            return new ResponseJson(new JwClasses());
        }
        return new ResponseJson(jwClasses);
    }

    @PostMapping("/findDmHonourRollStudentsByConditions")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者", notes = "返回响应对象", response=DmHonourRollStudent.class)
    public ResponseJson findDmHonourRollStudentsByConditions(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        List<DmHonourRollStudent> data=dmHonourRollStudentService.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
        long count=dmHonourRollStudentService.findDmHonourRollStudentCountByConditions(dmHonourRollStudent);
        return new ResponseJson(data,count);
    }

    @PostMapping("/explorerHonourRollStudent")
    public void explorerHonourRollStudent(@ApiParam(value = "班级学生荣誉导出")
                                          @RequestBody DmHonourRollStudent dmHonourRollStudent, HttpServletResponse response) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=honourRoll.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dmHonourRollStudentService.explorerHonourRoll(dmHonourRollStudent);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
