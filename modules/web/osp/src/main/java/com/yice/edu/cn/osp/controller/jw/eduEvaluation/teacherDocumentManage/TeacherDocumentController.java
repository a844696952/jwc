package com.yice.edu.cn.osp.controller.jw.eduEvaluation.teacherDocumentManage;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.osp.service.jw.department.DepartmentTeacherService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeFileService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeTeacherService;
import com.yice.edu.cn.osp.service.jw.role.RoleService;
import com.yice.edu.cn.osp.service.jw.teacher.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/teacherDocument")
@Api(value = "/teacherDocument", description = "教师信息模块")
public class TeacherDocumentController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherQuitService teacherQuitService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private RewardsaPunishmentService rewardsaPunishmentService;
    @Autowired
    private PracticeService practiceService;
    @Autowired
    private PracticeFileService practiceFileService;
    @Autowired
    private PracticeTeacherService practiceTeacherService;
    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @GetMapping("/look/lookTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson lookTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @PostMapping("/find/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody @Validated Teacher teacher) {
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        List<Teacher> data = teacherService.findTeacherListByCondition4Like(teacher);
        long count = teacherService.findTeacherCountByCondition4Like(teacher);
        return new ResponseJson(data, count);
    }

    @GetMapping("/ignore/findTeacherPosts/{teacherId}")
    @ApiOperation(value = "查询教师职务", notes = "返回响应对象")
    public ResponseJson findTeacherPosts(@PathVariable("teacherId") String teacherId){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacherId);
        teacherPost.setPager(new Pager().setPaging(false).setSortField("sort").setSortOrder(Pager.ASC).setIncludes("id","name","ddId","gradeId","gradeName","classId","className","sort"));
        return new ResponseJson(teacherPostService.findTeacherPostListByCondition(teacherPost));
    }

    @PostMapping("/ignore/classes/findTeacherClassPostCourseList")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourseList(
            @ApiParam(value = "教师在班级信息")
            @RequestBody TeacherClasses teacherClasses) {
        teacherClasses.setPager(new Pager("update_time",Pager.ASC));
        teacherClasses.setSchoolId(mySchoolId());
        List<Map<String, Object>> t = teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
        return new ResponseJson(t);
    }

    @PostMapping("/find/findTeacherQuitsByCondition")
    @ApiOperation(value = "根据条件查找离职教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherQuitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherQuit teacherQuit) {
        teacherQuit.setSchoolId(mySchoolId());
        teacherQuit.setStatus(Constant.STATUS.TEACHER_QUIT_LINE);
        Pager pager = teacherQuit.getPager()!=null?teacherQuit.getPager():new Pager().setPaging(false);
        pager.setLike("name");
        teacherQuit.setPager(pager);
        List<TeacherQuit> data = teacherQuitService.findTeacherQuitListByCondition(teacherQuit);
        long count = teacherQuitService.findTeacherQuitCountByCondition(teacherQuit);
        return new ResponseJson(data, count);
    }
    @GetMapping("/look/findTeacherQuitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherQuitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        return new ResponseJson(teacherQuitService.findTeacherQuitById(id));
    }
    @PostMapping("/ignore/findRewardsaPunishmentsByCondition")
    @ApiOperation(value = "根据条件查找教师奖惩情况", notes = "返回响应对象")
    public ResponseJson findRewardsaPunishmentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RewardsaPunishment rewardsaPunishment){
        List<RewardsaPunishment> data=rewardsaPunishmentService.findRewardsaPunishmentListByCondition4Like(rewardsaPunishment);
        long count=rewardsaPunishmentService.findRewardsaPunishmentCountByCondition4Like(rewardsaPunishment);
        return new ResponseJson(data,count);
    }
    @PostMapping("/ignore/saveRewardsaPunishment")
    @ApiOperation(value = "保存教师奖惩情况对象", notes = "返回响应对象")
    public ResponseJson saveRewardsaPunishment(
            @ApiParam(value = "教师奖惩情况对象", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        RewardsaPunishment s=rewardsaPunishmentService.saveRewardsaPunishment(rewardsaPunishment);
        return new ResponseJson(s);
    }

    @GetMapping("/ignore/findRewardsaPunishmentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教师奖惩情况", notes = "返回响应对象")
    public ResponseJson findRewardsaPunishmentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RewardsaPunishment rewardsaPunishment=rewardsaPunishmentService.findRewardsaPunishmentById(id);
        return new ResponseJson(rewardsaPunishment);
    }
    @PostMapping("/ignore/findPracticeListByTeacherId")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findPracticeListByTeacherId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Practice practice){
        if(practice.getRangeTime()!=null&&!practice.getRangeTime().equals("")){
            practice.setPracticeStartdate(practice.getRangeTime()[0]);
            practice.setPracticeEnddate(practice.getRangeTime()[1]);
        }
        List<Practice> data=practiceService.findPracticeListByTeacherId(practice);
        long count=practiceService.findPracticeCountByTeacherId(practice);
        return new ResponseJson(data,count);
    }
    @GetMapping("/ignore/findPracticeFileListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeFileListById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<PracticeFile> practiceFileList=practiceFileService.findPracticeFileListById(id);
        return new ResponseJson(practiceFileList);
    }
    @GetMapping("/ignore/findPracticeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Practice practice=practiceService.findPracticeById(id);
        return new ResponseJson(practice);
    }
    @GetMapping("/ignore/findPracticeTeacherListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPracticeTeacherListById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<PracticeTeacher>practiceTeacherList=practiceTeacherService.findPracticeTeacherListById(id);
        return new ResponseJson(practiceTeacherList);
    }
    @GetMapping("/ignore/findDepartmentsByTeacher/{id}")
    @ApiOperation(value = "获取教师部门,通过id查找", notes = "返回响应对象")
    public ResponseJson findDepartmentsByTeacher(@ApiParam(value = "需要用到的教师id", required = true)
                                                     @PathVariable("id") String id){
        DepartmentTeacher dt = new DepartmentTeacher();
        dt.setTeacherId(id);
        return new ResponseJson(departmentTeacherService.findDepartmentTeacherListByCondition(dt));
    }
    @GetMapping("/ignore/findDepartmentByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所属部门", notes = "返回响应对象")
    public ResponseJson findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId){
        return new ResponseJson(departmentTeacherService.findDepartmentByTeacherId(teacherId));
    }

}
