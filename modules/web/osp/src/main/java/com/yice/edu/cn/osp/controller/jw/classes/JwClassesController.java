package com.yice.edu.cn.osp.controller.jw.classes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.constants.RiseClassesConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import com.yice.edu.cn.osp.service.dm.classCard.DmTimedTaskService;
import com.yice.edu.cn.osp.service.jw.classes.JwClaCadresStuService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jwClasses")
@Api(value = "/jwClasses", description = "班级信息模块")
public class JwClassesController {
	@Autowired
	private JwClassesService jwClassesService;
	@Autowired
	private JwClaCadresStuService jwClaCadresStuService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private DmClassCardService dmClassCardService;
	@Autowired
	private DmTimedTaskService dmTimedTaskService;
	

	@GetMapping("/findJwClassesById/{id}")
	@ApiOperation(value = "通过id查找班级信息", notes = "返回响应对象")
	public ResponseJson findJwClassesById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		JwClasses jwClasses = jwClassesService.findJwClassesById(id);
		return new ResponseJson(jwClasses);
	}

	@PostMapping("/saveJwClasses")
	@ApiOperation(value = "保存班级信息对象", notes = "返回响应对象")
	public ResponseJson saveJwClasses(@ApiParam(value = "班级信息对象", required = true) @RequestBody JwClasses jwClasses) {
		JwClasses s = jwClassesService.saveJwClasses(jwClasses);
		return new ResponseJson(s);
	}

	@PostMapping("/updateJwClasses")
	@ApiOperation(value = "修改班级信息对象", notes = "返回响应对象")
	public ResponseJson updateJwClasses(
			@ApiParam(value = "被修改的班级信息对象,对象属性不为空则修改", required = true) @RequestBody JwClasses jwClasses) {
		jwClassesService.updateJwClasses(jwClasses);
		return new ResponseJson();
	}

	@PostMapping("/findJwClassessByCondition")
	@ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
	public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
		List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);
		return new ResponseJson(data);
	}

	@GetMapping("/deleteJwClasses/{id}")
	@ApiOperation(value = "根据id删除", notes = "返回响应对象")
	public ResponseJson deleteJwClasses(@ApiParam(value = "被删除记录的id", required = true) @PathVariable String id) {
		// 判断该班级是否还有绑定学生
		Student student = new Student();
		student.setClassesId(id);
		student.setSchoolId(LoginInterceptor.mySchoolId());
		student.setDel(Constant.DELSIGN.NORMAL);
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		long count = studentService.findStudentCountByCondition(student);
		if (count > 0) {
			return new ResponseJson(false, "请先移除学生!");
		}
		jwClassesService.deleteJwClasses(id);
		//删除该班级的 云班牌账号 及 定时 数据
		DmClassCard dmClassCard = new DmClassCard();
		dmClassCard.setClassId(id);
		DmClassCard dm =dmClassCardService.findOneDmClassCardByCondition(dmClassCard);
		if(dm==null) {
			return new ResponseJson();
		}
		DmTimedTask dt = new DmTimedTask();
		dt.setEquipmentId(dm.getUserName());
		dmTimedTaskService.deleteDmTimedTaskByCondition(dt);
//		dmClassCardService.deleteDmClassCardByCondition(dmClassCard);

		return new ResponseJson();
	}

	@GetMapping("/deleteJwClasses/queryHasClassesStu/{id}")
	@ApiOperation(value = "根据id删除", notes = "返回响应对象")
	public ResponseJson queryHasClassesStu(@ApiParam(value = "班级id", required = true) @PathVariable String id) {
		// 判断该班级是否还有绑定学生
		Student student = new Student();
		student.setClassesId(id);
		student.setSchoolId(LoginInterceptor.mySchoolId());
		student.setDel(Constant.DELSIGN.NORMAL);
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		long count = studentService.findStudentCountByCondition(student);
		if (count > 0) {
			return new ResponseJson(false, "请先移除学生!");
		}
		return new ResponseJson();
	}
	@PostMapping("/batchCreateClasses")
	@ApiOperation(value = "保存班级信息对象", notes = "返回响应对象")
	public ResponseJson batchCreateClasses(
			@ApiParam(value = "班级信息对象", required = true) @RequestBody CreateClassesVo createClassesVo) {
		//确定当前全部年级升完学后才能创建班级(保证当前年级的应届年份一样)
		JwClasses nowJwClasses = new JwClasses();
		nowJwClasses.setSchoolId(LoginInterceptor.mySchoolId());
		nowJwClasses.setGradeName(createClassesVo.getGradeName());
		List<JwClasses> jwClassesList = jwClassesService.findJwClassesListByCondition(nowJwClasses);
		if(!jwClassesList.isEmpty()) {
			for(JwClasses model:jwClassesList) {
				if(!createClassesVo.getEnrollYear().equals(model.getEnrollYear())) {
					return new ResponseJson(false,"与当前入学年份不一致或者当前班级升班完成后才能创建班级!");
				}
			}
		}else {
			
		Integer createGradeIndex = RiseClassesConstants.returnGradeIndex(createClassesVo.getGradeName());	
			
		//判断当前学校的学校是否已经有创建班级
		JwClasses hasJwClasses = new JwClasses();
		hasJwClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<JwClasses> classesList = jwClassesService.findJwClassesListByCondition(hasJwClasses);
		if(!classesList.isEmpty()) {
			for(JwClasses model:classesList ) {
				Integer otherClassGradeIndex = RiseClassesConstants.returnGradeIndex(model.getGradeName());
				if((createGradeIndex.intValue()-otherClassGradeIndex.intValue()) !=
						(Integer.valueOf(model.getEnrollYear()).intValue()-Integer.valueOf(createClassesVo.getEnrollYear()).intValue())) {
					return new ResponseJson(false,"入学年份应该为"+((otherClassGradeIndex.intValue()-createGradeIndex.intValue())+(Integer.valueOf(model.getEnrollYear()).intValue())));
				}
			}
		}
		}
		createClassesVo.setSchoolId(LoginInterceptor.mySchoolId());
		jwClassesService.batchCreateClasses(createClassesVo);
		return new ResponseJson();
	}

	@PostMapping("/findJwClassesListByConditionAndRelate")
	@ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
	public ResponseJson findJwClassesListByConditionAndRelate(@RequestBody JwClasses jwClasses) {
		String schoolId = LoginInterceptor.mySchoolId();
		String loginUserId = LoginInterceptor.myId();
		jwClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<JwClasses> clazzList = jwClassesService.findJwClassesListByConditionAndRelate(jwClasses);
		List<String> clazzIdList = jwClassesService.getTeacherOwnClazzId(jwClasses.getPostValue(), loginUserId, schoolId,null);
		if(!clazzIdList.isEmpty()) {
			clazzList = clazzList.stream().filter(ss->clazzIdList.stream().anyMatch(classId->ss.getId().equals(classId))).collect(Collectors.toList());
		}
		return new ResponseJson(clazzList);
	}

	@PostMapping("/update/updateJwClassesInfo")
	@ApiOperation(value = "修改班级信息对象", notes = "返回响应对象")
	public ResponseJson updateJwClassesInfo(
			@ApiParam(value = "被修改的班级信息对象,对象属性不为空则修改", required = true) @RequestBody JwClasses jwClasses) {
		jwClassesService.updateJwClasses(jwClasses);
		return new ResponseJson();
	}

	@GetMapping("/update/queryJwClassesById/{id}")
	@ApiOperation(value = "通过id查找班级信息", notes = "返回响应对象")
	public ResponseJson queryJwClassesById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		JwClasses jwClasses = jwClassesService.findJwClassesById(id);
		Student student = new Student();
		student.setClassesId(id);
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		long studentCount = studentService.findStudentCountByCondition(student);
		jwClasses.setStudentCount(String.valueOf(studentCount));
		return new ResponseJson(jwClasses);
	}

	@GetMapping("/update/queryJwStudentByClassesId/{id}")
	@ApiOperation(value = "通过班级id查找学生职务信息", notes = "返回响应对象")
	public ResponseJson queryJwStudentByClassesId(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		List<JwClaCadresStu> jwClaCadresStuList = jwClaCadresStuService.queryJwStudentByClassesId(id);
		return new ResponseJson(jwClaCadresStuList);
	}

	@GetMapping("/update/queryJwTeacherByClassesId/{id}")
	@ApiOperation(value = "通过班级id查找老师职务信息", notes = "返回响应对象")
	public ResponseJson queryJwTeacherByClassesId(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		TeacherClasses teacherClasses = new TeacherClasses();
		teacherClasses.setClassesId(id);
		teacherClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<Teacher4Classes> list = jwClassesService.findClassTeacherListByClasses(teacherClasses);
		return new ResponseJson(list);
	}

	@GetMapping("/query/queryJwClassesById/{id}")
	@ApiOperation(value = "通过id查找班级信息", notes = "返回响应对象")
	public ResponseJson qqueryJwClassesById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		JwClasses jwClasses = jwClassesService.findJwClassesById(id);
		Student student = new Student();
		student.setClassesId(id);
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		long studentCount = studentService.findStudentCountByCondition(student);
		jwClasses.setStudentCount(String.valueOf(studentCount));
		return new ResponseJson(jwClasses);
	}

	@GetMapping("/query/queryJwStudentByClassesId/{id}")
	@ApiOperation(value = "通过班级id查找学生职务信息", notes = "返回响应对象")
	public ResponseJson qqueryJwStudentByClassesId(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		List<JwClaCadresStu> jwClaCadresStuList = jwClaCadresStuService.queryJwStudentByClassesId(id);
		return new ResponseJson(jwClaCadresStuList);
	}

	@GetMapping("/query/queryJwTeacherByClassesId/{id}")
	@ApiOperation(value = "通过班级id查找老师职务信息", notes = "返回响应对象")
	public ResponseJson qqueryJwTeacherByClassesId(
			@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
		TeacherClasses teacherClasses = new TeacherClasses();
		teacherClasses.setClassesId(id);
		teacherClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<Teacher4Classes> list = jwClassesService.findClassTeacherListByClasses(teacherClasses);
		return new ResponseJson(list);
	}

	@PostMapping("/takeApart/queryStudents")
	@ApiOperation(value = "通过班级id查找学生信息", notes = "返回响应对象")
	public ResponseJson queryStudents(@ApiParam(value = "需要用到的班级id", required = true) @RequestBody Student student) {
		student.setSchoolId(LoginInterceptor.mySchoolId());
		student.setDel(Constant.DELSIGN.NORMAL);
		if(student.getName()!=null) {
			student.setName("%"+student.getName()+"%");
		}
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		List<Student> list = studentService.findStudentListForClassesByCondition(student);
		return new ResponseJson(list);
	}

	@GetMapping("/takeApart/queryClassesInfoByGradeId/{id}")
	@ApiOperation(value = "通过条件查找班级信息", notes = "返回响应对象")
	public ResponseJson queryClassesInfoByGradeId(
			@ApiParam(value = "需要用到的年级id", required = true) @PathVariable String id) {
		JwClasses jwClasses = new JwClasses();
		jwClasses.setGradeId(id);
		jwClasses.setSchoolId(LoginInterceptor.mySchoolId());
		List<JwClasses> list = jwClassesService.findJwClassesListWithStuNum(jwClasses);
		return new ResponseJson(list);
	}

	@PostMapping("/takeApart/takeApartStuToClasses")
	@ApiOperation(value = "给学生分配班级", notes = "返回响应对象")
	public ResponseJson takeApartStuToClasses(
			@ApiParam(value = "studentIds:wxTempConfig,wxTempConfig;newClassId:", required = true) @RequestBody Map<String, String> map) {
		jwClassesService.updateStuClass(map);
		return new ResponseJson();

	}

	@PostMapping("/riseGrade/queryStudentsForRise")
	@ApiOperation(value = "通过班级id查找学生信息", notes = "返回响应对象")
	public ResponseJson queryStudentsForRise(
			@ApiParam(value = "需要用到的班级id", required = true) @RequestBody Student student) {
		student.setSchoolId(LoginInterceptor.mySchoolId());
		student.setDel(Constant.DELSIGN.NORMAL);
		student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
		List<Student> list = studentService.findStudentListByCondition(student);
		long count = studentService.findStudentCountByCondition(student);
		return new ResponseJson(list, count);
	}
	
	@GetMapping("/queryTeacherTeachGrade/{postValue}")
	@ApiOperation(value = "查询该教师拥有的年级", notes = "返回响应对象")
	public ResponseJson queryTeacherTeachGrade(@PathVariable("postValue") String postValue) {
		String schoolId = LoginInterceptor.mySchoolId();
		String loginUserId = LoginInterceptor.myId();
		List<Dd> data = jwClassesService.getTeacherOwnGrade(postValue, loginUserId, schoolId);
		
		return new ResponseJson(data);
	}
	
    @PostMapping("/findStudentListByConditionWithFamily")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByConditionWithFamily(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody  Student student){
        student.setSchoolId(LoginInterceptor.mySchoolId());

        List<Student> data=studentService.findStudentListByConditionWithFamily(student);

        long count=studentService.findStudentCountByConditionWithFamily(student);
        return new ResponseJson(data,count);
    }
}
