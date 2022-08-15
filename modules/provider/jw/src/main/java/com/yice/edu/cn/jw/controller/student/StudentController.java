package com.yice.edu.cn.jw.controller.student;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentScoreNum;
import com.yice.edu.cn.common.pojo.jw.student.StuParentVo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentForSchoolNotify;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.jw.service.classes.JwClassesService;
import com.yice.edu.cn.jw.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@Api(value = "/student", description = "学生信息模块")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService jwClassesService;

    @GetMapping("/findStudentById/{id}")
    @ApiOperation(value = "通过id查找学生信息", notes = "返回学生信息对象")
    public Student findStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return studentService.findStudentById(id);
    }



    @PostMapping("/findOneStudentByCondition")
    @ApiOperation(value = "通过条件查找学生信息", notes = "返回学生信息对象")
    public Student findOneStudentByCondition(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody Student student) {
        return studentService.findOneStudentByCondition(student);
    }

    @PostMapping("/findStudentListByStudentIdsAndSchoolId")
    @ApiOperation(value = "根据学校id和学生id集合查找学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListByStudentIdsAndSchoolId(
            @ApiParam(value = "学生信息对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        return studentService.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
    }
    @PostMapping("/findStudentListByExcludeStudentIdsAndSchoolId")
    @ApiOperation(value = "根据学校id和排除学生id集合查找学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListByExcludeStudentIdsAndSchoolId(
            @ApiParam(value = "学生信息对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson) {
        return studentService.findStudentListByExcludeStudentIdsAndSchoolId(kqDeviceGroupPerson);
    }
    @PostMapping("/saveStudent")
    @ApiOperation(value = "保存学生信息", notes = "返回学生信息对象")
    public Student saveStudent(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody Student student) {

        studentService.saveStudent(student);
        return student;
    }

    @PostMapping("/findStudentListByCondition")
    @ApiOperation(value = "根据条件查找学生信息列表，name具有模糊查询", notes = "返回学生信息列表")
    public List<Student> findStudentListByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByCondition(student);
    }

    @PostMapping("/findStudentListForSchoolNotify")
    @ApiOperation(value = "根据条件查找学生信息列表", notes = "返回学生信息列表")
    public List<StudentForSchoolNotify> findStudentListForSchoolNotify(
            @ApiParam(value = "学生信息对象")
            @RequestBody StudentForSchoolNotify student){
        return studentService.findStudentListForSchoolNotify(student);
    }

    @PostMapping("/findStudentListByConditionim")
    @ApiOperation(value = "根据条件查找学生信息列表，name具有模糊查询", notes = "返回学生信息列表")
    public List<Student> findStudentListByConditionim(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByConditionim(student);
    }

    @PostMapping("/findStudentListByCondition4Like")
    @ApiOperation(value = "根据条件查找学生信息列表，name具有模糊查询", notes = "返回学生信息列表")
    public List<Student> findStudentListByCondition4Like(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByCondition4Like(student);
    }

    @PostMapping("/findStudentListByConditionWithBmp")
    @ApiOperation(value = "根据条件查找学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListByConditionWithBmp(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByConditionWithBmp(student);
    }

    @PostMapping("/findStudentCountByCondition")
    @ApiOperation(value = "根据条件查找学生信息列表个数,带有模糊查询功能", notes = "返回学生信息总个数")
    public long findStudentCountByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentCountByCondition(student);
    }

    @PostMapping("/findStudentCodeCountByCondition")
    @ApiOperation(value = "根据条件查找学生信息列表个数", notes = "返回学生信息总个数")
    public long findStudentCodeCountByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentCodeCountByCondition(student);
    }

    @PostMapping("/updateStudent")
    @ApiOperation(value = "修改学生信息", notes = "学生信息对象必传")
    public void updateStudent(
            @ApiParam(value = "学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody Student student) {
        studentService.updateStudent(student);
    }

    @PostMapping("/updateStudentById")
    @ApiOperation(value = "修改学生信息", notes = "学生信息对象必传")
    public void updateStudentById(
            @ApiParam(value = "学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody Student student) {
        studentService.updateStudentById(student);
    }

    @PostMapping("/updateStudentByCondition")
    @ApiOperation(value = "根据条件修改学生信息", notes = "学生信息对象和条件信息必传")
    public void updateStudentByCondition(
            @ApiParam(value = "学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody Map<String,Student>map) {
        Student studentData = map.get("student");
        Student studentCondition = map.get("condition");
        studentService.updateStudentByCondition(studentData,studentCondition);
    }

    @PostMapping("/updateStudentNew")
    @ApiOperation(value = "修改学生信息-osp用的", notes = "学生信息对象必传")
    public void updateStudentNew(
            @ApiParam(value = "学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody Student student) {
        studentService.updateStudentNew(student);
    }

    @GetMapping("/deleteStudent/{id}")
    @ApiOperation(value = "通过id删除学生信息")
    public void deleteStudent(
            @ApiParam(value = "学生信息对象", required = true)
            @PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/deleteStudentByCondition")
    @ApiOperation(value = "根据条件删除学生信息")
    public void deleteStudentByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        studentService.deleteStudentByCondition(student);
    }


    @PostMapping("/findStudentListByConditionWithFamily")
    @ApiOperation(value = "根据条件查找学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListByConditionWithFamily(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByConditionWithFamily(student);
    }
    @PostMapping("/findStudentCountByConditionWithFamily")
    @ApiOperation(value = "根据条件查找学生信息列表个数", notes = "返回学生信息总个数")
    public long findStudentCountByConditionWithFamily(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student){
        return studentService.findStudentCountByConditionWithFamily(student);
    }

    @PostMapping("/findStudentListForClassesByCondition")
    @ApiOperation(value = "根据条件查找学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListForClassesByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student){
        return studentService.findStudentListForClassesByCondition(student);
    }
    @PostMapping("/findStudentCountForClassesByCondition")
    @ApiOperation(value = "根据条件查找学生信息列表个数", notes = "返回学生信息总个数")
    public long findStudentCountForClassesByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student){
        return studentService.findStudentCountForClassesByCondition(student);
    }

    @PostMapping("/batchSaveStudent")
    public void batchSaveStudent(@RequestBody List<Student> studentList) {

        studentService.batchSaveStudent(studentList);
    }

    @GetMapping("/findStudentCodeForUpdateByStudentCode/{studentCode}")
    public long findStudentCodeForUpdateByStudentCode(
            @ApiParam(value = "需要用到的学籍号", required = true)
            @PathVariable String studentCode) {
        return studentService.findStudentCodeForUpdateByStudentCode(studentCode);
    }

    @PostMapping("/findStudentCodeAllListByCondition")
    public List<String> findStudentCodeAllListByCondition(
            @RequestBody Student student) {
        return studentService.findStudentCodeAllListByCondition(student);
    }

    @PostMapping("/findStudentCountByConditionForUpdate")
    public long findStudentCountByConditionForUpdate(
            @RequestBody Student student) {
        return studentService.findStudentCountByConditionForUpdate(student);
    }


    @PostMapping("/findCorrespondencesByStudent")
    @ApiOperation(value = "根据条件查找学生信息通讯录", notes = "返回学生通讯录")
    public List<Student> findCorrespondencesByStudent(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findCorrespondencesByStudent(student);
    }

    @PostMapping("/findCorrespondencesByStudentApp")
    @ApiOperation(value = "根据条件查找学生APP端信息通讯录", notes = "返回学生通讯录")
    public List<Student> findCorrespondencesByStudentApp(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {

        return studentService.findCorrespondencesByStudentApp(student);
    }

    @PostMapping("/findTeacherClassList")
    @ApiOperation(value = "根据条件查询老师当前所教的班级", notes = "返回老师所教班级的信息(学生通讯录)")
    public List<Student> findTeacherClassList(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findTeacherClassList(student);
    }

    @PostMapping("/findTeacherClassListim")
    @ApiOperation(value = "根据条件查询老师当前所教的班级", notes = "返回老师所教班级的信息(学生通讯录)")
    public List<Student> findTeacherClassListim(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findTeacherClassListim(student);
        //return studentService.findTeacherClassList(student);
    }

    @PostMapping("/findClassStudentByclassTitle")
    @ApiOperation(value = "班级classesId查询所有学生所有信息,当模糊查询的时候只要输入姓名(name)或学号(studentCode)就行", notes = "返回当前点击班级，教师对应所教的学生信息和数量(学生通讯录)")
    public List<Student> findClassStudentByclassTitle(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findClassStudentByclassTitle(student);
    }

    @PostMapping("/findStudentByTitleim")
    @ApiOperation(value = "班级classesId和title", notes = "返回当前点击班级，教师对应所教的学生信息和数量(学生通讯录)")
    public List<Student> findStudentByTitleim(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentByTitleim(student);
        //return studentService.findClassStudentByclassTitle(student);
    }

    @PostMapping("/findOneStudentMaxSeatNumberByCondition")
    @ApiOperation(value = "根据条件查询学生的信息", notes = "返回学生的信息")
    public Student findOneStudentMaxSeatNumberByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findOneStudentMaxSeatNumberByCondition(student);
    }

    @PostMapping("/findAllSchoolStudentListByCondition")
    @ApiOperation(value = "根据条件查询所有学校的学生", notes = "返回学生列表信息")
    public List<Student> findAllSchoolStudentListByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findAllSchoolStudentListByCondition(student);
    }

    @PostMapping("/findAllSchoolStudentCountByCondition")
    @ApiOperation(value = "根据条件查找所有学校学生信息列表个数", notes = "返回学生信息总个数")
    public long findAllSchoolStudentCountByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findAllSchoolStudentCountByCondition(student);
    }

    @PostMapping("/findStudentListForExportStudentByCondition")
    @ApiOperation(value = "导出根据条件查询所有学生,不分页", notes = "返回学生列表信息")
    public List<Student> findStudentListForExportStudentByCondition(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListForExportStudentByCondition(student);
    }

    @PostMapping("/findStudentRowNumber")
    @ApiOperation(value = "根据条件一键排号班级所有学生", notes = "返回学生列表")
    public void findStudentRowNumber(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        studentService.findStudentRowNumber(student);
    }

    @PostMapping("/batchUpdateStudentRegisterStatus")
    @ApiOperation(value = "通过学校id,进行所有学生一键IM注册")
    public long batchUpdateStudentRegisterStatus(
            @RequestBody Student student) {
        return studentService.batchUpdateStudentRegisterStatus(student);
    }

    @PostMapping("/findStudentCountClassesByCondition")
    @ApiOperation(value = "查询多个班级总人数")
    public List<ClassesStudentScoreNum> findStudentCountClassesByCondition(@RequestBody Map map) {
        return studentService.findStudentCountClassesByCondition(map);
    }

    @PostMapping("/findStudentInfoAndFamily")
    @ApiOperation(value = "查询学生以及家庭信息")
    public List<Student> findStudentInfoAndFamily(@RequestBody Student student) {
        return studentService.findStudentInfoAndFamily(student);
    }

    @PostMapping("/findStudentListScoreByCondition")
    @ApiOperation(value = "输入班号查询学生信息")
    public List<Student> findStudentListScoreByCondition(@RequestBody Student student) {
        JwClasses classes = new JwClasses();
        classes.setNumber(student.getClassesNumber());
        classes.setGradeId(student.getGradeId());
        classes.setSchoolId(student.getSchoolId());
        List<JwClasses> classes1 = jwClassesService.findJwClassesListByCondition(classes);
        if (classes1.size() > 0) {
            Student student1 = new Student();
            student1.setSchoolId(student.getSchoolId());
            student1.setClassesId(classes1.get(0).getId());
            return studentService.findStudentListByCondition(student1);
        }
        List<Student> studentList = new ArrayList<>();
        return studentList;
    }

    @PostMapping("/findStudentListClassesByCondition")
    @ApiOperation(value = "输入班级信息查询学生信息")
    public List<Map<String, Object>> findStudentListClassesByCondition(@RequestBody List<JwClasses> jwClassesList) {
        return studentService.findStudentListClassesByCondition(jwClassesList);
    }

    @PostMapping("/findStudentListByClazzIds")
    public List<ExamStudentInfo> findStudentListByClazzIds(@RequestBody List<String> clazzIds) {
        return studentService.findStudentListByClazzIds(clazzIds);
    }

    @PostMapping("/findStudentListAndClassByClazzIdList")
    public List<StudentPojo> findStudentListAndClassByClazzIdList(@RequestBody List<String> clazzIds) {
        return studentService.findStudentListAndClassByClazzIdList(clazzIds);
    }

    @PostMapping("/findStudentListForExamByCondition")
    @ApiOperation(value = "输入条件查询学生信息")
    public List<Student> findStudentListForExamByCondition(@RequestBody Student student) {
        return studentService.findStudentListForExamByCondition(student);
    }
    @PostMapping("/batchUpdateStudent")
    public void batchUpdateStudent(@RequestBody List<Student> studentList) {
        studentService.batchUpdateStudent(studentList);
    }

    @PostMapping("/findStudentWithParent")
    @ApiOperation(value = "关联家长表查询学生信息")
    public List<Student> findStudentWithParent(@RequestBody StuParentVo stuParentVo){
        return studentService.findStudentWithParent(stuParentVo);
    }

    @PostMapping("/findStudentLogin")
    @ApiOperation(value = "关联学生账户信息查询学生")
    public List<Student> findStudentLogin(@RequestBody StudentAccount studentAccount){
        return studentService.findStudentLogin(studentAccount);
    }

    @GetMapping("/getStudentLoginInfo/{id}")
    @ApiOperation(value = "获取平板端学生登陆的详细信息")
    public Student getStudentLoginInfo(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return studentService.getStudentLoginInfo(id);
    }

    @PostMapping("/findStudentsByGroupId/{groupId}")
    @ApiOperation(value = "关联学生账户信息查询学生")
    public List<Student> findStudentsByIds(@PathVariable("groupId") String groupId){
        return studentService.findStudentsByIds(groupId);
    }


    @PostMapping("/findStudentListByClassIdAndName")
    @ApiOperation(value = "根据班级id查找未分组学生信息列表", notes = "返回学生信息列表")
    public List<Student> findStudentListByClassIdAndName(
            @ApiParam(value = "学生信息对象")
            @RequestBody Student student) {
        return studentService.findStudentListByClassIdAndName(student);
    }


    @GetMapping("/findStudentSummaryBySchool4Index/{schoolId}")
    @ApiOperation(value = "学校学生进行汇总得出总数，去年学生总数，男学生比例，女学生比例，学生增长率")
    public Map<String,Long> findStudentSummaryBySchool4Index(@PathVariable("schoolId")String schoolId){
        return studentService.findStudentSummaryBySchool4Index(schoolId);
    }

    @GetMapping("/findGradeStudentSummaryBySchool4Index/{schoolId}")
    @ApiOperation(value = "学校教师进行汇总 按年级信息进行统计数据")
    public List<Map<String,Object>> findGradeStudentSummaryBySchool4Index(@PathVariable("schoolId")String schoolId){
        return studentService.findGradeStudentSummaryBySchool4Index(schoolId);
    }

    @PostMapping("/findStudentByIds")
    public List<Student> findStudentByIds(@RequestBody List<String> studentIds) {
        return studentService.findStudentByIds(studentIds);
    }


    @GetMapping("/findSchoolStudentNowStatusNumGroupByClassesId/{schoolId}")
    @ApiOperation(value = "根据学校id查找每个班级每种nowStatus在校状态的人数")
    public List<Map<String,Object>> findSchoolStudentNowStatusNumGroupByClassesId(@PathVariable("schoolId")String schoolId){
        return studentService.findSchoolStudentNowStatusNumGroupByClassesId(schoolId);
    }

    @GetMapping("/findSchoolStudentNowStatusNum/{schoolId}")
    @ApiOperation(value = "根据学校id查找全校每种nowStatus在校状态的人数")
    public List<Map<String,Object>> findSchoolStudentNowStatusNum(@PathVariable("schoolId")String schoolId){
        return studentService.findSchoolStudentNowStatusNum(schoolId);
    }

    @PostMapping("/findStudentNoCountByStudentNo")
    @ApiOperation(value = "通过学号查找学生数量", notes = "返回学生个数")
    public long findStudentNoCountByStudentNo(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestBody Student student) {
        return studentService.findStudentNoCountByStudentNo(student.getStudentNo(),student.getSchoolId());
    }
    @PostMapping("findStudentNoCountByStudentNoExceptSelf")
    @ApiOperation(value = "通过学号查找学生数量除了自己", notes = "返回学生个数")
    public long findStudentNoCountByStudentNoExceptSelf(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestBody Student student) {
        return studentService.findStudentNoCountByStudentNoExceptSelf(student.getStudentNo(),student.getSchoolId(),student.getId());
    }

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody List<String> studentIds) {
        studentService.resetPassword(studentIds);
    }

    @PostMapping("/findClassStudentByClassesId")
    @ApiOperation(value = "通过班级id查找学生信息", notes = "返回学生信息对象")
    public List<Student> findClassStudentByClassesId(
            @ApiParam(value = "需要用到的classesId", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp) {
        return studentService.findClassStudentByClassesId(dmActivitySiginUp);
    }
}
