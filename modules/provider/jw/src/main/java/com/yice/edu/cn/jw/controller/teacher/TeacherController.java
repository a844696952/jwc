package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.jw.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
@Api(value = "/teacher",description = "教师信息模块")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/findTeacherById/{id}")
    @ApiOperation(value = "通过id查找教师信息", notes = "返回教师信息对象")
    public Teacher findTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherService.findTeacherById(id);
    }


    @GetMapping("/findTeacherByTel/{tel}")
    @ApiOperation(value = "通过tel查找教师信息", notes = "返回教师信息对象")
    public Teacher findTeacherByTel(  @ApiParam(value = "手机号", required = true)
                                      @PathVariable String tel){
        return teacherService.findTeacherByTel(tel);
    }

    @PostMapping("/saveTeacher")
    @ApiOperation(value = "保存教师信息", notes = "返回教师信息对象")
    public Teacher saveTeacher(
            @ApiParam(value = "教师信息对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.saveTeacher(teacher);
        return teacher;
    }

    @PostMapping("/findTeacherListByCondition")
    @ApiOperation(value = "根据条件查找教师信息列表", notes = "返回教师信息列表")
    public List<Teacher> findTeacherListByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherListByCondition(teacher);
    }
    @PostMapping("/findOneTeacherByCondition")
    @ApiOperation(value = "根据条件查找教师信息-需保证查询到的数据唯一", notes = "返回教师信息")
    public Teacher findOneTeacherByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findOneTeacherByCondition(teacher);
    }

    @PostMapping("/findTeacherListByConditionRegister")
    @ApiOperation(value = "根据条件查找教师信息列表", notes = "返回教师信息列表")
    public List<Teacher> findTeacherListByConditionRegister(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherListByConditionRegister(teacher);
    }
    @PostMapping("/findTeacherListByCondition4Like")
    @ApiOperation(value = "根据条件查找教师信息列表模糊查询", notes = "返回教师信息列表")
    public List<Teacher> findTeacherListByCondition4Like(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        //历史遗留问题问题 先有新规则支持模糊查询 这里只是名称模糊查询
        teacher.setPager(Optional.ofNullable(teacher.getPager()).orElse(new Pager().setPaging(false)).setLike("name"));
        return teacherService.findTeacherListByCondition(teacher);
    }

    @PostMapping("/findCorrespondencesByTeacher")
    @ApiOperation(value = "根据条件查找教师信息列表", notes = "教师通讯录")
    public List<Teacher> findCorrespondencesByTeacher(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findCorrespondencesByTeacher(teacher);
    }

    @PostMapping("/findStudentTeachers")
    @ApiOperation(value = "根据条件查找教师信息列表", notes = "教师通讯录")
    public List<Teacher> findStudentTeachers(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        //return teacherService.findCorrespondencesByTeacher(teacher);
        return teacherService.findStudentTeachers(teacher);
    }

    @PostMapping("/findTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找教师信息列表个数", notes = "返回教师信息总个数")
    public long findTeacherCountByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherCountByCondition(teacher);
    }
    @PostMapping("/findTeacherCountByCondition4Like")
    @ApiOperation(value = "根据条件查找教师信息列表个数-模糊查询", notes = "返回教师信息总个数")
    public long findTeacherCountByCondition4Like(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherCountByCondition4Like(teacher);
    }

    @PostMapping("/updateTeacher")
    @ApiOperation(value = "修改教师信息", notes = "教师信息对象必传")
    public Teacher updateTeacher(
            @ApiParam(value = "教师信息对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher teacher){
        teacherService.updateTeacher(teacher);
        return teacher;
    }
    @PostMapping("/updateTeacherNew")
    @ApiOperation(value = "修改教师信息", notes = "教师信息对象必传")
    public Teacher updateTeacherNew(
            @ApiParam(value = "教师信息对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher teacher){
        teacherService.updateTeacherNew(teacher);
        return teacher;
    }

    @GetMapping("/deleteTeacher/{id}")
    @ApiOperation(value = "通过id删除教师信息")
    public void deleteTeacher(
            @ApiParam(value = "教师信息对象", required = true)
            @PathVariable String id){
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/login")
    @ApiOperation(value = "教师账号登录")
    public Teacher login(@RequestBody Teacher teacher){
        return teacherService.login(teacher);
    }

    @GetMapping("/findTeacherTreeMenu/{id}")
    @ApiOperation(value = "根据教师id获取该教师的权限树")
    public List<Perm> findTeacherTreeMenu(@PathVariable String id){
        return teacherService.findTeacherTreeMenu(id);
    }

    @GetMapping("/findCheckedRolesByTeacherId/{id}")
    @ApiOperation(value = "根据教师id获取教师的角色id列表")
    public List<String> findCheckedRolesByTeacherId(@PathVariable String id){
        return teacherService.findCheckedRolesByTeacherId(id);
    }
    @PostMapping("/delsertTeacherRoles")
    public void delsertTeacherRoles(@RequestBody TeacherRole teacherRole){
        teacherService.delsertTeacherRoles(teacherRole);
    }

    @PostMapping("/deleteTeacherByCondition")
    @ApiOperation(value = "根据条件删除教师信息")
    public void deleteTeacherByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        teacherService.deleteTeacherByCondition(teacher);
    }
    @PostMapping("/batchSaveTeacher")
    public Map<String,Object> batchSaveTeacher(@RequestBody List<Teacher> teacherList){
        return teacherService.batchSaveTeacher(teacherList);
    }

    @GetMapping("/findTeacherFuncPermsByTeacherId/{id}")
    @ApiOperation(value = "通过教师id获取教师的按钮权限")
    public List<Perm> findTeacherFuncPermsByTeacherId(@PathVariable String id){
        return teacherService.findTeacherFuncPermsByTeacherId(id);
    }

    @GetMapping("/findDmssTeacherFuncPermsByTeacherId/{id}")
    @ApiOperation(value = "通过教师id获取是否有H5权限教师的按钮权限")
    public List<Perm> findDmssTeacherFuncPermsByTeacherId(@PathVariable String id){
        return teacherService.findDmssTeacherFuncPermsByTeacherId(id);
    }


    @PostMapping("/updateTeacherAdmin")
    public void updateTeacherAdmin(@RequestBody Teacher teacher){
        teacherService.updateTeacherAdmin(teacher);
    }

    @GetMapping("/findTeacherInfoById/{id}")
    @ApiOperation("根据id获取教师的信息,包含学校信息")
    public Teacher findTeacherInfoById(@PathVariable("id") String id){
        return teacherService.findTeacherInfoById(id);
    }


    @PostMapping("/findTeacherListInfoByCondition")
    @ApiOperation("根据条件获取教师的信息,包含角色")
    public List<Teacher> findTeacherListInfoByCondition(@RequestBody Teacher teacher){
        return teacherService.findTeacherListInfoByCondition(teacher);
    }
    @PostMapping("/findTeachers4Yed")
    @ApiOperation(value = "根据条件查找教师信息列表模糊查询", notes = "返回教师信息列表")
    public List<Teacher> findTeachers4Yed(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeachers4Yed(teacher);
    }
    @PostMapping("/findTeachersCount4Yed")
    @ApiOperation(value = "根据条件查找教师信息列表个数-模糊查询", notes = "返回教师信息总个数")
    public long findTeachersCount4Yed(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeachersCount4Yed(teacher);
    }
    @PostMapping("/findAdminBySchool")
    @ApiOperation(value = "通过学校id获取学校管理员，唯一存在account的存在")
    public Teacher findAdminBySchool(@RequestBody Teacher teacher){
       return teacherService.findAdminBySchool(teacher);
    }

    @PostMapping("/batchUpdateTeacherRegisterStatus")
    @ApiOperation(value = "通过学校id,进行所有老师意见IM注册")
    public int batchUpdateTeacherRegisterStatus(@RequestBody Teacher teacher){
        return teacherService.batchUpdateTeacherRegisterStatus(teacher);
    }

    @PostMapping("/findTeacherListSchoolId")
    @ApiOperation(value = "查询当前学校所有教师信息列表", notes = "返回教师信息列表")
    public List<Teacher> findTeacherListSchoolId(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherListSchoolId(teacher);
    }

    @GetMapping("/findTeacherAdminTreeMenu/{schoolId}")
    public List<Perm> findTeacherAdminTreeMenu(@PathVariable("schoolId") String schoolId){
        return teacherService.findTeacherAdminTreeMenu(schoolId);
    }

    @PostMapping("/rpmLogin")
    @ApiOperation(value = "阅卷机登录")
    public Teacher rpmLogin(@RequestBody Teacher teacher){
        return teacherService.rpmLogin(teacher);
    }
    @GetMapping("/findTeacherMenu4App/{teacherId}")
    @ApiOperation(value = "给教师端登录教师查询所有菜单权限")
    public List<Perm> findTeacherMenu4App(@PathVariable("teacherId") String teacherId){
        return teacherService.findTeacherMenu4App(teacherId);
    }

    @GetMapping("/findClass4AssociationByTeacherId/{teacherId}")
    @ApiOperation(value = "查询教师所有关联的班级（所授课以及所任班主任的班级）")
    public List<JwClasses> findClass4AssociationByTeacherId(@PathVariable("teacherId") String teacherId){
        return teacherService.findClass4AssociationByTeacherId(teacherId);
    }

    @PostMapping("/findClassTeacherIsDirector")
    @ApiOperation(value = "查找该教师当班主任的班级")
    public List<Teacher> findClassTeacherIsDirector(@RequestBody Teacher teacher ){
        return teacherService.findClassTeacherIsDirector(teacher);
    }
    @PostMapping("/bindOpenId2Teacher")
    @ApiOperation(value = "教师绑定openId")
    public void bindOpenId2Teacher(@RequestBody Teacher teacher ){
        teacherService.bindOpenId2Teacher(teacher);
    }


    @PostMapping("/ccLogin")
    public Teacher ccLogin(@RequestBody LoginObj loginObj){
        return teacherService.ccLogin(loginObj);
    }

    @PostMapping("/findTeacherImgListByCondition")
    @ApiOperation(value = "查询教师/职工 同时关联部门和认证头像列表")
    public List<Teacher> findTeacherImgListByCondition(@RequestBody Teacher teacher ){
        return teacherService.findTeacherImgListByCondition(teacher);
    }

    @PostMapping("/findTeacherImgCountByCondition")
    @ApiOperation(value = "查询教师/职工 同时关联部门和认证头像数量")
    public long findTeacherImgCountByCondition(@RequestBody Teacher teacher ){
        return teacherService.findTeacherImgCountByCondition(teacher);
    }

    @PostMapping("/findTeacherImgListByConditionWithoutType")
    @ApiOperation(value = "查询教师/职工 不区分类型 同时关联部门和认证头像列表")
    public List<Teacher> findTeacherImgListByConditionWithoutType(@RequestBody Teacher teacher ){
        return teacherService.findTeacherImgListByConditionWithoutType(teacher);
    }


    @GetMapping("/findTeacherSummaryBySchool4Index/{schoolId}")
    @ApiOperation(value = "学校教师进行汇总得出总数，男老师数量，女老师数量")
    public Map<String,Long> findTeacherSummaryBySchool4Index(@PathVariable("schoolId")String schoolId){
        return teacherService.findTeacherSummaryBySchool4Index(schoolId);
    }
    @GetMapping("/findCourseTeacherSummaryBySchool4Index/{schoolId}")
    @ApiOperation(value = "学校教师进行汇总 按课程信息进行统计数据 课程信息关联到字典表取字典课程名称")
    public List<Map<String,Object>> findCourseTeacherSummaryBySchool4Index(@PathVariable("schoolId")String schoolId){
        return teacherService.findCourseTeacherSummaryBySchool4Index(schoolId);
    }
    /**
     * 查询教师拥有的班牌权限
     * 列表
     * @param teacher
     * @return
     */
    @PostMapping("/findTeacherManagerById")
    @ApiOperation(value = "查询教师拥有的班牌权限", notes = "返回教师信息列表")
    public List<Teacher> findTeacherManagerById(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherService.findTeacherManagerById(teacher);
    }
    @PostMapping("/findTeacherListInClassByCondition")
    @ApiOperation(value = "查询教师列表,内联班级信息", notes = "返回教师信息列表")
    public List<TeacherShow> findTeacherListInClassByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody TeacherVo teacherVo){
        return teacherService.findTeacherListInClassByCondition(teacherVo);
    }
    @PostMapping("/findTeacherCountInClassByCondition")
    @ApiOperation(value = "查询教师列表数量,内联班级信息", notes = "返回数量")
    public long findTeacherCountInClassByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody TeacherVo teacherVo){
        return teacherService.findTeacherCountInClassByCondition(teacherVo);
    }
    @PostMapping("/findTeacherListWithTeaching")
    @ApiOperation(value = "查询教师列表包含授课信息", notes = "返回数量")
    public List<TeacherShow> findTeacherListWithTeaching(@ApiParam(value = "教师信息对象")
                                                             @RequestBody TeacherVo teacherVo){
        return teacherService.findTeacherListWithTeaching(teacherVo);
    }

    @PostMapping("/findTeachingInfoByCondition")
    @ApiOperation(value = "查询教师授课信息", notes = "返回数量")
    public List<TeachingInfo> findTeachingInfoByCondition(@ApiParam(value = "教师信息对象")
                                                             @RequestBody TeacherVo teacherVo){
        return teacherService.findTeachingInfoByCondition(teacherVo);
    }
}
