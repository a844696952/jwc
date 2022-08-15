package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.jw.service.teacher.TeacherClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacherClasses")
@Api(value = "/teacherClasses", description = "教师在班级信息，关联班级模块")
public class TeacherClassesController {
    @Autowired
    private TeacherClassesService teacherClassesService;

    @GetMapping("/findTeacherClassesById/{id}")
    @ApiOperation(value = "通过id查找教师在班级信息，关联班级", notes = "返回教师在班级信息，关联班级对象")
    public TeacherClasses findTeacherClassesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return teacherClassesService.findTeacherClassesById(id);
    }

    @PostMapping("/saveTeacherClasses")
    @ApiOperation(value = "保存教师在班级信息，关联班级", notes = "返回教师在班级信息，关联班级对象")
    public TeacherClasses saveTeacherClasses(
            @ApiParam(value = "教师在班级信息，关联班级对象", required = true)
            @RequestBody TeacherClasses teacherClasses) {
        teacherClassesService.saveTeacherClasses(teacherClasses);
        return teacherClasses;
    }

    @PostMapping("/findTeacherClassesListByCondition")
    @ApiOperation(value = "根据条件查找教师在班级信息，关联班级列表", notes = "返回教师在班级信息，关联班级列表")
    public List<TeacherClasses> findTeacherClassesListByCondition(
            @ApiParam(value = "教师在班级信息，关联班级对象")
            @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findTeacherClassesListByCondition(teacherClasses);
    }

    @PostMapping("/findTeacherClassesCountByCondition")
    @ApiOperation(value = "根据条件查找教师在班级信息，关联班级列表个数", notes = "返回教师在班级信息，关联班级总个数")
    public long findTeacherClassesCountByCondition(
            @ApiParam(value = "教师在班级信息，关联班级对象")
            @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findTeacherClassesCountByCondition(teacherClasses);
    }

    @PostMapping("/updateTeacherClasses")
    @ApiOperation(value = "修改教师在班级信息，关联班级", notes = "教师在班级信息，关联班级对象必传")
    public void updateTeacherClasses(
            @ApiParam(value = "教师在班级信息，关联班级对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherClasses teacherClasses) {
        teacherClassesService.updateTeacherClasses(teacherClasses);
    }

    @GetMapping("/deleteTeacherClasses/{id}")
    @ApiOperation(value = "通过id删除教师在班级信息，关联班级")
    public void deleteTeacherClasses(
            @ApiParam(value = "教师在班级信息，关联班级对象", required = true)
            @PathVariable String id) {
        teacherClassesService.deleteTeacherClasses(id);
    }

    @PostMapping("/deleteTeacherClassesByCondition")
    @ApiOperation(value = "根据条件删除教师在班级信息，关联班级")
    public void deleteTeacherClassesByCondition(
            @ApiParam(value = "教师在班级信息，关联班级对象")
            @RequestBody TeacherClasses teacherClasses){
        teacherClassesService.deleteTeacherClassesByCondition(teacherClasses);
    }

    @PostMapping("/editTeacherPostCourse")
    @ApiOperation(value = "编辑教师教学课程", notes = "返回响应对象")
    public ResponseJson editTeacherPostCourse(@ApiParam(value = "教师教学课程信息", required = true) @RequestBody TeacherClasses teacherClasses) {
        return new ResponseJson(teacherClassesService.editTeacherCourse(teacherClasses));
    }

    @PostMapping("/findTeacherClassPostCourseList")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public List<Map<String, Object>> findTeacherClassPostCourseList(
            @ApiParam(value = "教师在班级信息")
            @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
    }

    @PostMapping("/findTeacherClassPostCourseListBySchoolId")
    @ApiOperation(value = "根据schoolId查询教师在班级任职教学信息")
    public List<Map<String, Object>> findTeacherClassPostCourseListBySchoolId(
            @ApiParam(value = "教师在班级信息")
            @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
    }

    @PostMapping("/findClassTeacherListByClasses")
    @ApiOperation(value = "根据班级条件查找任职教师信息", notes = "返回班级的任职教师信息列表")
    public List<Teacher4Classes> findClassTeacherListByClasses(
            @ApiParam(value = "所在班级信息", required = true)
            @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findClassTeacherListByClasses(teacherClasses);
    }

    @GetMapping("/findTeacherClassesByTeacher/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所任职的班级", notes = "返回班级列表")
    public List<TeacherClasses> findTeacherClassesByTeacher(@ApiParam(value = "教师id", required = true)
                                                            @PathVariable String teacherId) {
        return teacherClassesService.findTeacherClassesByTeacher(teacherId);
    }

    @GetMapping("/findGradeByTeacher/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所任职的年级", notes = "返回年级列表")
    public List<TeacherClasses> findGradeByTeacher(@ApiParam(value = "教师id", required = true)
                                                   @PathVariable String teacherId) {
        return teacherClassesService.findGradeByTeacher(teacherId);
    }

    @PostMapping("/findCourseByTeacherGrade")
    @ApiOperation(value = "查询教师在年级中教的科目", notes = "返回科目列表")
    public List<TeacherClassesCourse> findCourseByTeacherGrade(@ApiParam(value = "教师年级", required = true)
                                                               @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findCourseByTeacherGrade(teacherClasses);
    }

    @PostMapping("/findCourseByTeacherGrade2")
    @ApiOperation(value = "查询教师在年级中教的科目", notes = "返回科目列表")
    public List<TeacherClassesCourse> findCourseByTeacherGrade2(@ApiParam(value = "教师年级", required = true)
                                                               @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findCourseByTeacherGrade2(teacherClasses);
    }

    @PostMapping("/findCourseByTeacherGrade3")
    @ApiOperation(value = "查询教师在年级中教的科目", notes = "返回科目列表")
    public List<TeacherClassesCourse> findCourseByTeacherGrade3(@ApiParam(value = "教师年级", required = true)
                                                               @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findCourseByTeacherGrade3(teacherClasses);
    }

    @PostMapping("/findClassesByTeacherCourse")
    @ApiOperation(value = "通过年级和课程id获取教师对应所教的班级", notes = "返回班级列表")
    public List<TeacherClasses> findClassesByTeacherCourse(@ApiParam(value = "教师id、课程id、年级id", required = true)
                                                           @RequestBody TeacherClassVo teacherClassVo) {
        return teacherClassesService.findClassesByTeacherCourse(teacherClassVo.getTeacherId(), teacherClassVo.getCourseId(), teacherClassVo.getGradeId());
    }
    @PostMapping("/findCourse4TeacherClasses")
    @ApiOperation(value = "通过班级获取教师对应所教班级的课程", notes = "返回班级列表")
    public List<TeacherClassesCourse> findCourse4TeacherClasses(@ApiParam(value = "教师id、课程id、年级id", required = true)
                                                           @RequestBody TeacherClasses teacherClasses) {
        return teacherClassesService.findCourse4TeacherClasses(teacherClasses);
    }

    @GetMapping("/findCourseNameList4Teacher/{teacherId}")
    @ApiOperation(value = "通过教师id获取教师所有课程名称列表")
    public List<String> findCourseNameList4Teacher(@PathVariable("teacherId") String teacherId) {
        return teacherClassesService.findCourseNameList4Teacher(teacherId);
    }

    @PostMapping("/findClassesByTeacherInfo")
    @ApiOperation(value = "通过教师信息获取教师及对应所教的班级", notes = "返回教师findTeacherClassPostCourseListXq班级列表")
    public List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(@ApiParam(value = "教师姓名", required = true)
                                                                     @RequestBody TeacherClassesForQusSurvey teacher5Classes) {
        return teacherClassesService.findClassesByTeacherInfo(teacher5Classes);
    }

    @PostMapping("/findTeacherClassPostCourseListXq")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public List<Map<String,String>> findTeacherClassPostCourseListXq(
            @ApiParam(value = "教师在班级信息")
            @RequestBody TeacherClasses teacherClasses) {
        String teacherId = teacherClasses.getTeacherId();
        return teacherClassesService.findTeacherClassCourseByTeacherId(teacherId);
        //return teacherClassesService.findTeacherClassPostCourseListXq(teacherClasses);
    }

    /**
     * 作业分析
     */
    @GetMapping("/findTeacherClassPostCourseListHomeworkAnalyse/{teacherId}")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public List<Map<String, String>> findTeacherClassPostCourseListHomeworkAnalyse(
            @PathVariable("teacherId") String teacherId) {
        return teacherClassesService.findTeacherClassPostCourseListHomeworkAnalyse(teacherId);
    }
    @GetMapping("/findTeacherClassCourseListHomeworkAnalyse/{teacherClassesId}")
    @ApiOperation(value = "根据条件查询教师在班级课程信息")
    public List<TeacherClassesCourse> findTeacherClassCourseListHomeworkAnalyse(@PathVariable String teacherClassesId ){
        return teacherClassesService.findTeacherClassCourseListHomeworkAnalyse(teacherClassesId);
    }

    @GetMapping("/findTeacherClassByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所教的班级信息（含年级）")
    public List<JwClasses> findTeacherClassByTeacherId(@PathVariable("teacherId") String teacherId) {
        return teacherClassesService.findTeacherClassByTeacherId(teacherId);
    }

    @PostMapping("/findTeacherClassByTeacherIdAndPost/{teacherId}")
    @ApiOperation(value = "通过教师id和职务查询 对应的任职信息 包含(年级和班级信息)")
    public TeacherClasses findTeacherClassByTeacherIdAndPost(@PathVariable("teacherId") String teacherId, @RequestParam("postName") String postName) {
        return teacherClassesService.findTeacherClassByTeacherIdAndPost(teacherId, postName);
    }

    /**
     * 作业分析
     */
    @GetMapping("/findClassTeacherListHomeworkAnalyseByClasses/{classesId}")
    @ApiOperation(value = "根据班级id查询班级教师情况")
    public List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(
            @ApiParam(value = "班级教师信息")
            @PathVariable("classesId") String classesId) {
        return teacherClassesService.findClassTeacherListHomeworkAnalyseByClasses(classesId);
    }
    @PostMapping("/findClassesCourseListHomeworkAnalyseByTeacherClassesIds")
    @ApiOperation(value = "根据班级TeacherClassesIds查询班级教师所教课程情况")
    public List<Map<String,Object>> findClassesCourseListHomeworkAnalyseByTeacherClassesIds(
            @RequestBody Map map
    ){
        return teacherClassesService.findClassesCourseListHomeworkAnalyseByTeacherClassesIds(map);
    }
    @GetMapping("/findTeacherClassCourseByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所教的课程列表包含对应班级年级信息")
    public List<Map<String,String>> findTeacherClassCourseByTeacherId(
            @PathVariable("teacherId") String teacherId){
        return teacherClassesService.findTeacherClassCourseByTeacherId(teacherId);
    }

    @GetMapping("/findTeacherClassesHomeworkAnalyseByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所教的课程列表包含对应班级年级信息")
    public List<Map<String,String>> findTeacherClassesHomeworkAnalyseByTeacherId(
            @PathVariable("teacherId") String teacherId){
        return teacherClassesService.findTeacherClassesHomeworkAnalyseByTeacherId(teacherId);
    }


    @GetMapping("/findTeacherClassesByTeacherId/{id}")
    @ApiOperation(value = "通过id查找教师在班级信息，关联班级", notes = "返回教师在班级信息，关联班级对象")
    public  List<TeacherClasses> findTeacherClassesByTeacherId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return teacherClassesService.findTeacherClassesByTeacherId(id);
    }


    @PostMapping("/findHeadmasterByClasses")
    @ApiOperation(value = "查找班级班主任")
    public Teacher findHeadmasterByClasses(@RequestBody TeacherClasses teacherClasses ){
        return teacherClassesService.findHeadmasterByClasses(teacherClasses);
    }


    @GetMapping("/findTeacherByClassAndCourse/{classId}/{courseId}")
    @ApiOperation(value = "通过班级id和课程id获取教师信息")
    public List<Teacher> findTeacherByClassAndCourse(@PathVariable("classId") String classId,@PathVariable("courseId") String courseId){
        return teacherClassesService.findTeacherByClassAndCourse(classId,courseId);
    }

    @GetMapping("/findTeacherClasses/{id}")
    @ApiOperation(value = "通过id查找教师对应班级", notes = "返回教师在班级信息")
    public  List<TeacherClasses> findTeacherClasses(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return teacherClassesService.findTeacherClasses(id);
    }


    @PostMapping("/batchSaveTeaching")
    @ApiOperation(value = "批量导入授课信息", notes = "返回数量")
    public Map<String,Object> batchSaveTeaching(@RequestParam("schoolId")String schoolId,@RequestBody List<TeachingInfo> teachingInfos){
        return teacherClassesService.batchSaveTeaching(schoolId,teachingInfos);
    }
}
