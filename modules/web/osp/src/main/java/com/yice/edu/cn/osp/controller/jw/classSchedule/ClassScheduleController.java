package com.yice.edu.cn.osp.controller.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleInitService;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleNoonBreakService;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule", description = "模块")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @Autowired
    private ClassScheduleInitService classScheduleInitService;
    @Autowired
    private ClassScheduleNoonBreakService classScheduleNoonBreakService;

    @PostMapping("/saveClassSchedule")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveClassSchedule(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassSchedule classSchedule) {
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        ClassSchedule s = classScheduleService.saveClassSchedule(classSchedule);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findClassScheduleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findClassScheduleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        ClassSchedule classSchedule = classScheduleService.findClassScheduleById(id);
        return new ResponseJson(classSchedule);
    }

    @PostMapping("/update/updateClassSchedule")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateClassSchedule(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClassSchedule classSchedule) {
        classScheduleService.updateClassSchedule(classSchedule);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClassScheduleById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookClassScheduleById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        ClassSchedule classSchedule = classScheduleService.findClassScheduleById(id);
        return new ResponseJson(classSchedule);
    }


    //传递班级id(classId)
    @PostMapping("/findClassSchedulesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findClassSchedulesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody  ClassSchedule classSchedule) {
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
        classScheduleService.checkoutErrorClassSchedule(list);
        long count = classScheduleService.findClassScheduleCountByCondition(classSchedule);
        //传递年级和班级名称
        JwClasses jwClasses = classScheduleService.findJwClassesById(classSchedule.getClassId());
        String s = jwClasses.getGradeName() + "(" + jwClasses.getNumber() + ")班";
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        ClassScheduleNoonBreak classScheduleNoonBreak = new ClassScheduleNoonBreak();
        classScheduleNoonBreak.setSchoolId(mySchoolId());
        Pager pager1 = new Pager();
        pager1.setIncludes("number");
        pager1.setPaging(false);
        classScheduleNoonBreak.setPager(pager1);
        ClassScheduleNoonBreak classScheduleNoonBreaks = classScheduleNoonBreakService.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
        if(!(classScheduleNoonBreaks!=null)){
            classScheduleNoonBreaks = new ClassScheduleNoonBreak();
            classScheduleNoonBreaks.setNumber(Constant.CLASS_SCHEDULE.CLASS_NOON_BREAK);
        }
        return new ResponseJson(list, count, s,classScheduleInits,classScheduleNoonBreaks);
    }

    @GetMapping("/ignore/getTeacherThisWeekClassSchedule")
    public ResponseJson getTeacherThisWeekClassSchedule(){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(mySchoolId());
        classSchedule.setTeacherId(myId());
        Pager pager = new Pager();
        pager.setIncludes("id,weekId,numberId");
        classSchedule.setPager(pager);
        List<List<ClassSchedule>>  classScheduleList = classScheduleService.getTeacherThisWeekClassSchedule(classSchedule);
        return new ResponseJson(classScheduleList);
    }

    @PostMapping("/findOneClassScheduleByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneClassScheduleByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClassSchedule classSchedule) {
        ClassSchedule classSchedules = classScheduleService.findOneClassScheduleByCondition(classSchedule);
        return new ResponseJson(classSchedules);
    }

    @GetMapping("/delete/deleteClassSchedule/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClassSchedule(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        classScheduleService.deleteClassSchedule(id);
        return new ResponseJson();
    }

    @GetMapping("/delete/deleteClassScheduleByCondition")
    @ApiOperation(value = "根据条件删除", notes = "返回响应对象")
    public ResponseJson deleteClassScheduleByCondition(
            @ApiParam(value = "被删除的对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody ClassSchedule classSchedule) {
        classScheduleService.deleteClassScheduleByCondition(classSchedule);
        return new ResponseJson();
    }

    @GetMapping("/look/findGradesBySchoolId/{scheduleId}")
    @ApiOperation(value = "根据学校id获取学校的班级")
    public ResponseJson findGradesBySchoolId(@PathVariable("scheduleId")String scheduleId) {
        List<Dd> dds = classScheduleService.findGradesBySchoolId(mySchoolId(),scheduleId);
        return new ResponseJson(dds);
    }


    //传递班级ID,weekId,numberId,班级Id获取到学校对应年级的所有课程,weekId,numberId来判断教师是否同一时间段已经存在课程
    @PostMapping("/look/getJwCourseAll")
    @ApiOperation(value = "通过年级id获取到学校对应的课程列表", notes = "返回对应的课程列表")
    public ResponseJson findTeacherClassPostCourseList(
            @ApiParam(value = "获取年级id，例子:{ gradeId:'***',classId:''}", required = true)
            @RequestBody  ClassSchedule classSchedule) {
        JwClasses jwClasses = classScheduleService.findJwClassesById(classSchedule.getClassId());//通过班级Id查询对应的班级

        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(classSchedule.getClassId());
        teacherClasses.setSchoolId(LoginInterceptor.mySchoolId());
        //通过班级id获得老师班级课程表的多条信息
        List<Map<String, String>> list = classScheduleService.findCourseTeacherByClass(teacherClasses);
        return new ResponseJson(list,jwClasses);
    }

    //通过课程courseId和班级classId,weekId,numberId获取已被选中的场地列表
    @PostMapping("/look/getJwCourseOne")
    @ApiOperation(value = "通过课程id获取课程的详细信息，对应的场地类别", notes = "返回一条详细信息")
    public ResponseJson getJwCourseOne(
            @ApiParam(value = "不能为空", required = true)
            @RequestBody ClassSchedule classSchedule
    ) {
        //通过课程Id得到课程的详细信息
        JwCourse jwCourse = classScheduleService.getJwCourseOne(classSchedule.getCourseId());
        //传入课程信息里的场地类型Id得到所有属于这个类型的场地
        /*List<JwSpace> jwSpaceList = classScheduleService.getJwSpaceAll(jwCourse.getBuildingTypeId());*/
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        String classId = classSchedule.getClassId();
        classSchedule.setClassId(null);//把班级Id变为null
        classSchedule.setCourseId(null);//把课程Id变为Null
        return new ResponseJson(jwCourse);
    }




    @PostMapping("/download")
    public void exportClassSchedule(
            @ApiParam(value = "课程表")
            @RequestBody ClassSchedule classSchedule,
            HttpServletResponse response) {
        classSchedule.setSchoolId(mySchoolId());
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");

        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = classScheduleService.exportClassSchedule(classSchedule);
//            response.setContentLength();
            workbook.write(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //删除当前班级的课程表信息,通过班级的Id
    @PostMapping("/update/batchdelete")
    @ApiOperation(value = "班级信息")
    public ResponseJson batchdelete(
            @ApiParam(value = "那个班级的信息", required = true)
            @RequestBody ClassSchedule classSchedule
    ) {
        classSchedule.setSchoolId(mySchoolId());
        classScheduleService.deletebatchdelete(classSchedule);
        return new ResponseJson();
    }

    //传递需要CRUD的信息，进行添加或者修改
    @PostMapping("/update/updateorfind")
    @ApiOperation(value = "需要CRUD的数据", notes = "添加或者修改数据库的信息")
    public ResponseJson updateorFind(
            @ApiParam(value = "需要CRUD的数据", required = true)
            @RequestBody ClassSchedule classSchedule
    ) {
        String school = mySchoolId();
        if(classSchedule.getClassScheduleList()!=null){
            classSchedule.getClassScheduleList().stream().filter(c->c!=null).forEach(c -> {
                c.setSchoolId(school);
            });
        }

        classScheduleService.updateorfind(classSchedule);
        return new ResponseJson();
    }

    @PostMapping("/look/getTeacherList")
    @ApiOperation(value = "通过课程Id查询对应教师列表",notes = "返回对应教师列表")
    public ResponseJson getTeacherList(
            @ApiParam(value = "例子:{ courseId:'***',classId:'****',weekId:'',numberId:'',courseName:'***'}")
            @RequestBody ClassSchedule classSchedule) {
        classSchedule.setSchoolId(mySchoolId());
       return classScheduleService.getTeacherList(classSchedule);
    }

    @GetMapping("/todayClassScheduleList/{classId}")
    @ApiOperation(value = "传递学生的班级id查询",notes = "返回学生今日课表")
    public  ResponseJson todayClassScheduleList(@PathVariable String classId){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        Date date = new Date();
        int s = date.getDay();
        if(s==0){
            s=7;
        }
        classSchedule.setWeekId(s);
        List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
        return  new ResponseJson(classSchedules);

    }

    @PostMapping("/upload/uploadClassSchedule/{scheduleId}")
    public ResponseJson uploadTeacher(@PathVariable("scheduleId")String scheduleId, MultipartFile file) {
        return new ResponseJson(classScheduleService.uploadClassScheduleNew(scheduleId,file));
    }

    @PostMapping("/upload/exportTemplate")
    @ApiModelProperty(value = "传递班级id，年级id导出对应的班级,选择全部时为空,例子：id:'',gradeId:''")
    public void exportTemplate(@RequestBody JwClasses jwClasses, HttpServletResponse response){
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = classScheduleService.exportTemplateNew(jwClasses);
            workbook.write(s);
        } catch (Exception e) {

        }
    }

    @GetMapping("/ignore/myClassSchedule/{classId}")
    @ApiOperation(value = "传递学生的班级Id查询", notes = "返回班级完整课表")
    public ResponseJson findList(@PathVariable String classId) {
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassId(classId);
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
        classScheduleService.checkoutErrorClassSchedule(list);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return new ResponseJson(list,classScheduleInits);
    }


    @GetMapping("/findClassScheduleGroupClassId/{number}")
    public ResponseJson findClassScheduleGroupClassId(@PathVariable("number") Integer number){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(mySchoolId());
        classSchedule.setNumberId(number);
        List<ClassSchedule> classSchedules = classScheduleService.findClassScheduleGroupClassId(classSchedule);
        return new ResponseJson(classSchedules);

    }
}
