package com.yice.edu.cn.osp.controller.jw.teacher;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.department.DepartmentTeacherService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeFileService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeService;
import com.yice.edu.cn.osp.service.jw.practice.PracticeTeacherService;
import com.yice.edu.cn.osp.service.jw.role.RoleService;
import com.yice.edu.cn.osp.service.jw.teacher.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/teacher")
@Api(value = "/teacher", description = "教师信息模块")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherQuitService teacherQuitService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private JwClassesService classesService;
    @Autowired
    private JwCourseService courseService;
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
    @PostMapping("/saveTeacher")
    @ApiOperation(value = "保存教师信息对象", notes = "返回响应对象")
    public ResponseJson saveTeacher(
            @ApiParam(value = "教师信息对象", required = true)
            @RequestBody Teacher teacher) {
        Teacher s = teacherService.saveTeacher(teacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @PostMapping("/update/updateTeacher")
    @ApiOperation(value = "修改教师信息对象", notes = "返回响应对象")
    public ResponseJson updateTeacher(
            @ApiParam(value = "被修改的教师信息对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher teacher) {
        return new ResponseJson(teacherService.updateTeacherNew(teacher));
    }

    @GetMapping("/look/lookTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson lookTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @PostMapping("/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody @Validated Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherListByCondition4Like(teacher);
        long count = teacherService.findTeacherCountByCondition4Like(teacher);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findTeacherListByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody @Validated Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.getPager().setPaging(false);
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherListByCondition(teacher);
        return new ResponseJson(data);
    }

    @GetMapping("/deleteTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        teacherService.deleteTeacher(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteTeacherByCondition")
    @ApiOperation(value = "根据条件删除教师信息", notes = "返回响应对象")
    public ResponseJson deleteTeacherByCondition(
            @ApiParam(value = "被删除的教师信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Teacher teacher) {
        teacher.setSchoolId(mySchoolId());
        teacherService.deleteTeacherByCondition(teacher);
        return new ResponseJson();
    }

    @GetMapping("/findTeacherTreeMenu")
    @ApiOperation(value = "获取登录用户的权限树")
    public ResponseJson findTeacherTreeMenu() {
        List<Perm> perms = teacherService.findTeacherTreeMenu(myId());
        return new ResponseJson(perms);
    }

    @GetMapping("/findRolesByTeacherId/{id}")
    @ApiOperation(value = "根据教师id获取教师的角色列表和勾选id")
    public ResponseJson findRolesByTeacherId(@PathVariable String id) {
        Role role = new Role();
        role.setSchoolId(currentTeacher().getSchoolId());
        List<Role> roles = roleService.findRoleListByCondition(role);
        List<String> checked = teacherService.findCheckedRolesByTeacherId(id);
        return new ResponseJson(roles, checked);
    }

    @PostMapping("/delsertTeacherRoles")
    @ApiOperation(value = "修改教师的角色")
    public ResponseJson delsertTeacherRoles(@RequestBody TeacherRole teacherRole) {
        teacherRole.setSchoolId(mySchoolId());
        teacherService.delsertTeacherRoles(teacherRole);
        return new ResponseJson();
    }

    @PostMapping("/update/post/editTeacherPost")
    @ApiOperation(value = "编辑教师职务", notes = "返回响应对象")
    public ResponseJson editTeacherPost(@ApiParam(value = "教师职务列表", required = true) @RequestBody TeacherPost teacherPost) {
        //map {String teacherId,String schoolId,List<TeacherPost> teacherPostList}
        teacherPost.setSchoolId(mySchoolId());
        return new ResponseJson(teacherPostService.editTeacherPost(teacherPost));
    }
    @GetMapping("/update/post/delTeacherPost/{id}")
    @ApiOperation(value = "删除教师职务", notes = "返回响应对象")
    public ResponseJson editTeacherPost(@ApiParam(value = "教师职务列表", required = true) @PathVariable String id) {
        teacherPostService.deleteTeacherPost(id);
        return new ResponseJson();
    }

    @PostMapping("/update/classes/editTeacherPostCourse")
    @ApiOperation(value = "编辑教师教学课程", notes = "返回响应对象")
    public ResponseJson editTeacherPostCourse(@ApiParam(value = "教师教学课程信息", required = true) @RequestBody TeacherClasses teacherClasses) {
        teacherClasses.setSchoolId(mySchoolId());
        return teacherClassesService.editTeacherPostCourse(teacherClasses);
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

    @PostMapping("/update/classes/findTeacherClasses")
    @ApiOperation(value = "通过id查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeacherClassesById(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestBody TeacherClasses teacherClasses) {
        teacherClasses = teacherClassesService.findTeacherClasses4EditShow(teacherClasses);
        return new ResponseJson(teacherClasses);
    }

    @PostMapping("/update/classes/deleteTeacherClasses")
    @ApiOperation(value = "通过id删除教师在班级信息，关联班级")
    public ResponseJson deleteTeacherClasses(
            @ApiParam(value = "教师在班级信息，关联班级对象", required = true)
            @RequestBody TeacherClasses teacherClasses) {
        teacherClassesService.deleteTeacherClassesByCondition(teacherClasses);
        return new ResponseJson();
    }

    @PostMapping("/download")
    @ApiOperation(value = "导出教师信息")
    public void exportTeacher(@ApiParam(value = "教师信息对象")
                              @RequestBody TeacherVo teacherVo, HttpServletResponse response) {
        //Workbook w = teacherService.exportTeacher();
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = teacherService.exportTeacher(teacherVo);
            workbook.write(s);
        } catch (Exception e) {

        }
    }

    @GetMapping("/upload/exportTemplate")
    @ApiOperation(value = "导出教师模板")
    public void exportTemplate(HttpServletResponse response) {
        //Workbook w = teacherService.exportTeacher();
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = teacherService.exportTemplate();
            workbook.write(s);
        } catch (Exception e) {

        }
    }

    @GetMapping("/update/classes/findCourse")
    @ApiOperation(value = "查询课程")
    public ResponseJson findCourse() {
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        return new ResponseJson(courseService.findJwCourseListByCondition(jwCourse));
    }

    @GetMapping("/update/classes/findClassesByGradeId/{gradeId}")
    @ApiOperation(value = "通过年级查询班级", notes = "返回响应对象")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        JwClasses c  = new JwClasses();
        c.setGradeId(gradeId);
        c.setSchoolId(mySchoolId());
        return new ResponseJson(classesService.findJwClassesListByCondition(c));
    }

    @PostMapping("/upload/uploadTeacher")
    @ApiOperation(value = "导入教师", notes = "返回响应对象")
    public ResponseJson uploadTeacher(MultipartFile file) {
        return new ResponseJson(teacherService.uploadTeacher(file));
    }


    @PostMapping("/saveTeacherAdmin")
    @ApiOperation(value = "添加学校管理员", notes = "返回响应对象")
    public ResponseJson saveTeacherAdmin(
            @ApiParam(value = "教师信息对象", required = true)
            @RequestBody Teacher teacher){
        Teacher s=teacherService.saveTeacherAdmin(teacher);
        return new ResponseJson(s);
    }
    @PostMapping("/updateTeacherAdmin")
    @ApiOperation(value = "修改学校管理员", notes = "返回响应对象")
    public ResponseJson updateTeacherAdmin(
            @ApiParam(value = "教师信息对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.updateTeacherAdmin(teacher);
        return new ResponseJson();
    }

    @PostMapping("/findMySchoolTeachersByCondition")
    public ResponseJson findMySchoolTeachersByCondition(@RequestBody Teacher teacher){
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setSchoolId(mySchoolId());
        Pager pager = teacher.getPager()==null?new Pager().setPaging(false):teacher.getPager();
        pager.setLike("name");
        teacher.setPager(pager);
        List<Teacher> list = teacherService.findTeacherListInfoByCondition(teacher);
        list.forEach(t->{
            t.setPassword(null);
        });
        long count = teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(list,count);
    }

    @GetMapping("/ignore/findMySelf")
    public ResponseJson findMySelf(){
        Teacher teacher = teacherService.findTeacherById(myId());
        teacher.setPassword(null);
        return new ResponseJson(teacher);

    }

    @PostMapping("/ignore/updateMySelf")
    public ResponseJson updateMySelf(@RequestBody Teacher teacher){
        teacher.setId(myId());
        teacher.setPassword(null);
        final Teacher t = new Teacher();
        t.setId(myId());
        t.setImgUrl(teacher.getImgUrl());
        t.setName(teacher.getName());
        t.setSex(teacher.getSex());
        t.setSchoolId(teacher.getSchoolId());
        t.setSchoolName(teacher.getSchoolName());
        Teacher result = teacherService.updateMySelf(t);
        return new ResponseJson(result);
    }

    @PostMapping("/ignore/updateMyPassword")
    public ResponseJson updateMyPassword(@Validated(value = GroupOne.class) @RequestBody Teacher teacher){
        teacher.setId(myId());
        Teacher t = teacherService.findTeacherById(myId());
        String newPassword=DigestUtil.sha1Hex(teacher.getNewPassword());
        String oldPassword=DigestUtil.sha1Hex(teacher.getPassword());
        if(!t.getPassword().equals(oldPassword)){
            return new ResponseJson(false,"原密码错误");
        }
        t.setPassword(newPassword);
        teacherService.updateTeacherAdmin(t);
        return new ResponseJson();
    }

    @PostMapping("/quit/findTeacherQuitsByCondition")
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
    @GetMapping("/quit/findTeacherQuitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherQuitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        return new ResponseJson(teacherQuitService.findTeacherQuitById(id));
    }
    @PostMapping("/quit/download")
    public void exportTeacherQuit(@ApiParam(value = "教师信息对象")
                              @RequestBody TeacherQuit teacherQuit, HttpServletResponse response) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            teacherQuit.setSchoolId(mySchoolId());
            teacherQuit.setStatus(Constant.STATUS.TEACHER_QUIT_LINE);
            teacherQuit.setPager(teacherQuit.getPager().setPaging(false));
            Workbook workbook = teacherQuitService.exportTeacher(teacherQuit);
            workbook.write(s);
        } catch (Exception e) {

        }
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

    @PostMapping("/ignore/updateRewardsaPunishment")
    @ApiOperation(value = "修改教师奖惩情况对象", notes = "返回响应对象")
    public ResponseJson updateRewardsaPunishment(
            @ApiParam(value = "被修改的教师奖惩情况对象,对象属性不为空则修改", required = true)
            @RequestBody RewardsaPunishment rewardsaPunishment){
        rewardsaPunishmentService.updateRewardsaPunishment(rewardsaPunishment);
        return new ResponseJson();
    }
    @GetMapping("/ignore/deleteRewardsaPunishment/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRewardsaPunishment(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        rewardsaPunishmentService.deleteRewardsaPunishment(id);
        return new ResponseJson();
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
    @GetMapping("/update/deleteDepartmentTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDepartmentTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        departmentTeacherService.deleteDepartmentTeacher(id);
        return new ResponseJson();
    }
    @PostMapping("/update/updateDepartmentMember/{teacherId}")
    @ApiOperation(value = "通过教师id维护教师所属部门", notes = "返回响应对象")
    public ResponseJson updateDepartmentMember(
            @ApiParam(value = "要添加的所属部门", required = true)
            @PathVariable("teacherId")String teacherId,
            @RequestBody List<DepartmentTeacher> departmentTeachers){
        departmentTeacherService.updateDepartmentMember(teacherId,departmentTeachers);
        return new ResponseJson();
    }
    @GetMapping("/ignore/findDepartmentByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所属部门", notes = "返回响应对象")
    public ResponseJson findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId){
        return new ResponseJson(departmentTeacherService.findDepartmentByTeacherId(teacherId));
    }
    @GetMapping("/ignore/batchUpdateTeacherRegisterStatus")
    @ApiOperation(value = "按学校给一键激活教师IM", notes = "返回响应对象")
    public ResponseJson batchUpdateTeacherRegisterStatus(){
        Teacher t = new Teacher();
        t.setSchoolId(mySchoolId());
        return new ResponseJson(teacherService.batchUpdateTeacherRegisterStatus(t));
    }

    @PostMapping("/findTeacherListSchoolId")
    @ApiOperation(value = "查询当前学校所有教师信息列表", notes = "返回教师信息列表")
    public ResponseJson findTeacherListSchoolId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher){
        teacher.setSchoolId(mySchoolId());
        teacher.setId(myId());
        List<Teacher> data=teacherService.findTeacherListSchoolId(teacher);
        return new ResponseJson(data);
    }
    @PostMapping("/update/uploadAvatar")
    @ApiOperation(value = "图片上传,根据图片名称去修改对应教师头像，返回成功或者失败")
    public ResponseJson uploadAvatar(MultipartFile file) {
        //获取图片名称
        return new ResponseJson(teacherService.uploadAvatar(file));
    }


    @GetMapping("/findTeacherClassesByTeacherId")
    @ApiOperation(value = "通过教师id查询教师对应的班级", notes = "返回响应对象")
    public ResponseJson findTeacherClassesByTeacherId(){
        List<TeacherClasses> teacherClassesList = teacherService.findTeacherClasses(myId());
        return new ResponseJson(teacherClassesList);
    }

    @PostMapping("/findTeacherManagerById")
    @ApiOperation(value = "查询教师拥有的班牌权限", notes = "返回响应对象")
    public ResponseJson findTeacherManagerById(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.getPager().addExcludes("password");
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherManagerById(teacher);
        long count = teacherService.findTeacherCountByCondition4Like(teacher);
        return new ResponseJson(data, count);
    }
    @PostMapping("/findTeacherListByPost")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherListByPost(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody @Validated TeacherVo teacher) {
        return teacherService.findTeacherListByPost(teacher);
    }

    @ApiOperation(value = "授课信息模板导出")
    @GetMapping("/upload/exportTeachingTemplate")
    public void exportTeachingTemplate(HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = teacherService.exportTeachingTemplate();
            workbook.write(s);
        } catch (Exception e) {

        }
    }
    @PostMapping("/upload/uploadTeaching")
    @ApiOperation(value = "导入教师授课信息", notes = "返回响应对象")
    public ResponseJson uploadTeaching(MultipartFile file) {
        return new ResponseJson(teacherService.uploadTeaching(file));
    }

    @PostMapping("/downloadTeaching")
    @ApiOperation(value = "导出教师授课信息")
    public void downloadTeaching(@ApiParam(value = "教师信息对象")
                              @RequestBody TeacherVo teacherVo, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teacher.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = teacherService.exportTeaching(teacherVo);
            workbook.write(s);
        } catch (Exception e) {

        }
    }
}
