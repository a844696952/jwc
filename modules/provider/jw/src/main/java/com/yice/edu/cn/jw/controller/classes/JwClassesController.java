package com.yice.edu.cn.jw.controller.classes;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.classes.*;
import com.yice.edu.cn.common.pojo.jw.classes.rise.RiseClazzVo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.jw.service.classes.JwClaCadresService;
import com.yice.edu.cn.jw.service.classes.JwClassesService;
import com.yice.edu.cn.jw.service.student.StudentService;
import com.yice.edu.cn.jw.service.teacher.TeacherClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jwClasses")
@Api(value = "/jwClasses",description = "班级信息模块")
public class JwClassesController {
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private JwClaCadresService jwClaCadresService;
	@Autowired
	private TeacherClassesService teacherClassesService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/findJwClassesById/{id}")
    @ApiOperation(value = "通过id查找班级信息", notes = "返回班级信息对象")
    public JwClasses findJwClassesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jwClassesService.findJwClassesById(id);
    }
 
    @PostMapping("/saveJwClasses")
    @ApiOperation(value = "保存班级信息", notes = "返回班级信息对象")
    public JwClasses saveJwClasses(
            @ApiParam(value = "班级信息对象", required = true)
            @RequestBody JwClasses jwClasses){
        jwClassesService.saveJwClasses(jwClasses);
        return jwClasses;
    }

    @PostMapping("/findJwClassesListByCondition")
    @ApiOperation(value = "根据条件查找班级信息列表", notes = "返回班级信息列表")
    public List<JwClasses> findJwClassesListByCondition(
            @ApiParam(value = "班级信息对象")
            @RequestBody JwClasses jwClasses){
        return jwClassesService.findJwClassesListByCondition(jwClasses);
    }
    @PostMapping("/findJwClassesCountByCondition")
    @ApiOperation(value = "根据条件查找班级信息列表个数", notes = "返回班级信息总个数")
    public long findJwClassesCountByCondition(
            @ApiParam(value = "班级信息对象")
            @RequestBody JwClasses jwClasses){
        return jwClassesService.findJwClassesCountByCondition(jwClasses);
    }

    @PostMapping("/updateJwClasses")
    @ApiOperation(value = "修改班级信息", notes = "班级信息对象必传")
    public void updateJwClasses(
            @ApiParam(value = "班级信息对象,对象属性不为空则修改", required = true)
            @RequestBody JwClasses jwClasses){
        jwClassesService.updateJwClasses(jwClasses);
    }

    @GetMapping("/deleteJwClasses/{id}")
    @ApiOperation(value = "通过id删除班级信息")
    public void deleteJwClasses(
            @ApiParam(value = "班级信息对象", required = true)
            @PathVariable String id){
        jwClassesService.deleteJwClasses(id);
    }
    
    @PostMapping("/batchCreateClasses")
    @ApiOperation(value = "批量创建班级", notes = "返回班级信息对象")
    public Boolean batchCreateClasses(
            @ApiParam(value = "班级对象", required = true)
            @RequestBody CreateClassesVo classes){
    	jwClassesService.batchSaveClasses(classes);
    	
        return true;
    }
    @PostMapping("/findJwClassesListByConditionAndRelate")
    @ApiOperation(value = "根据条件查找班级信息列表和楼栋、场地名称", notes = "返回班级信息列表")
    public List<JwClasses> findJwClassesListByConditionAndRelate(
            @ApiParam(value = "班级信息对象")
            @RequestBody JwClasses jwClasses){
    	List<JwClasses> list = jwClassesService.findJwClassesListByConditionAndRelate(jwClasses);
		Collections.sort(list,Comparator.comparing(JwClasses::getGradeId,(gradeF,gradeS)->Integer.compare(Integer.parseInt(gradeF),Integer.parseInt(gradeS))).thenComparing(JwClasses::getNumber,(classNumF,classNumS)->Integer.compare(Integer.parseInt(classNumF),Integer.parseInt(classNumS))));
        return list;
    }
    
    @GetMapping("/deleteJwClassesLogic/{id}")
    @ApiOperation(value = "通过id逻辑删除班级信息")
    public ReturnMsgVo deleteJwClassesLogic(
            @ApiParam(value = "班级信息对象", required = true)
            @PathVariable String id){
    	ReturnMsgVo vo = new ReturnMsgVo();
    	JwClasses updateModel = new JwClasses();
    	updateModel.setId(id);
    	updateModel.setDel(Constant.DELSIGN.NORMAL);
        jwClassesService.updateJwClasses(updateModel);
        vo.setSign(true);
        
        return vo;
    }
    
    @PostMapping("/updateStuClass")
    @ApiOperation(value = "修改学生所属班级")
    public void udpateStuClass(
            @ApiParam(value = "studentIds:,;newClassId:")
            @RequestBody Map map){
    	if(map.get("studentIds")==null || map.get("newClassId")==null) {
    		return;
    	}
        jwClassesService.updateStuClass(map);
    }
    @PostMapping("/findJwClassesListWithStuNum")
    @ApiOperation(value = "根据条件查找班级信息列表个数和所拥有的学生", notes = "返回班级信息总个数")
    public List<JwClasses> findJwClassesListWithStuNum(
            @ApiParam(value = "班级信息对象")
            @RequestBody JwClasses jwClasses){
        return jwClassesService.findJwClassesListWithStuNum(jwClasses);
    }
    
    @PostMapping("/getClassesTeacherAndMaster")
    @ApiOperation(value = "查询班级班主任和班长信息")
    public ClassesInfoViewVo getClassesTeacherAndMaster(
    		@ApiParam(value = "班级id、学校id、学生状态")@RequestBody JwClasses jwClasses) {
    	
    	ClassesInfoViewVo vo = new ClassesInfoViewVo();
    	
    	JwClaCadres jwClaCadres = new JwClaCadres();
    	jwClaCadres.setClassesId(jwClasses.getId());
    	jwClaCadres.setName("班长");
    	List<JwClaCadres>  jwClaCadresList = jwClaCadresService.findJwClaCadresListWithSName(jwClaCadres);
    	if(!jwClaCadresList.isEmpty()) {
    		vo.setClassMasterName(jwClaCadresList.get(0).getStudentName());
    	}
    	
		TeacherClasses teacherClasses = new TeacherClasses();
		teacherClasses.setClassesId(jwClasses.getId());
		teacherClasses.setSchoolId(jwClasses.getSchoolId());
		Teacher teacher = teacherClassesService.findHeadmasterByClasses(teacherClasses);
		if (teacher != null) {
			vo.setHomeroomTeacher(teacher.getName());
			vo.setHomeroomTeacherImg(teacher.getImgUrl());
			teacherClasses.setTeacherId(teacher.getId());
			List<TeacherClassesCourse>  courseList = teacherClassesService.findCourse4TeacherClasses(teacherClasses);
			if(!courseList.isEmpty()) {
				String courseStr = courseList.stream().map(TeacherClassesCourse::getCourseName).collect(Collectors.joining(","));
				vo.setCourse(courseStr);
			}
		}
		
		JwClasses jwClass = jwClassesService.findJwClassesById(jwClasses.getId());
		vo.setGradeName(jwClass.getGradeName());
		vo.setClassNo(jwClass.getNumber());
		
		Student student = new Student();
		student.setClassesId(jwClasses.getId());
		student.setSchoolId(jwClasses.getSchoolId());
		student.setDel(Constant.DELSIGN.NORMAL);
		long stuNum = studentService.findStudentCountByCondition(student);
		vo.setStuNum(stuNum);
    	return vo;
    }

    @PostMapping("/findOneJwClassesByCondition")
    @ApiOperation(value = "返回单个班级对象")
    public JwClasses findOneJwClassesByCondition(
            @ApiParam(value = "班级对象")
            @RequestBody  JwClasses jwClasses){
        return jwClassesService.findOneJwClassesByCondition(jwClasses);
    }
    @GetMapping("/getClassTree/{schoolId}/{teacherId}")
    @ApiOperation(value = "通过id查找班级,年级信息", notes = "返回班级信息对象")
    public List<JwClasses> getClassTree(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable("schoolId") String schoolId,@PathVariable("teacherId") String teacherId){
        return jwClassesService.getClassTree(schoolId,teacherId);
    }

    @PostMapping("/getClassesNumber")
    public List<JwClasses> getClassesNumber(@RequestBody JwClasses jwClasses){
        return jwClassesService.getClassesNumber(jwClasses);
    }
    
    @ApiOperation(value = "获取年级的最大班号", notes = "返回班级信息对象")
    @PostMapping("/findMaxClassesNo")
	public Integer findMaxClassesNoByCondition(
			@ApiParam(value = "{gradeId:年级id,schoolId:学校id}", required = true)
			@RequestBody JwClasses jwClasses) {
		return jwClassesService.findMaxClassesNoByCondition(jwClasses);
	}
    /**
     * 升班
     * @param id 学校id
     */
    @PostMapping("/riseClazz")
    @ApiOperation(value = "通过学校升学")
    public void riseClazz(
    		@RequestBody
    		RiseClazzVo riseClazzVo) {
    	jwClassesService.riseClazz(riseClazzVo);
    }


    @PostMapping("/findJwClassessByConditionForExam")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public List<JwClasses> findJwClassessByConditionForExam(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {

          return  jwClassesService.findJwClassessByConditionForExam(jwClasses);

    }


    @GetMapping("/findTeacherClassesByTeacherId/{teacherId}/{activityId}")
    @ApiOperation(value = "去查看页面,通过教师id查找对应班级")
    public List<JwClasses> findTeacherClassesByTeacherId(
            @PathVariable("teacherId") String teacherId,@PathVariable("activityId") String activityId){
        List<JwClasses> jwClassesList = jwClassesService.findTeacherClassesByTeacherId(teacherId,activityId);
        return jwClassesList;
    }

    @PostMapping("/findClassListByJwClassesKong")
    public List<JwClasses> findClassListByJwClassesKong(@RequestBody JwClasses jwClasses){
        return jwClassesService.findClassListByJwClassesKong(jwClasses);
    }
}
