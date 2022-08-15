package com.yice.edu.cn.tap.controller.classes;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.classes.app.StuQueryVo;
import com.yice.edu.cn.common.pojo.jw.classes.app.TapTeachClassesInfoResultVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.classes.JwClaCadresStuService;
import com.yice.edu.cn.tap.service.classes.JwClassesService;
import com.yice.edu.cn.tap.service.dd.DdService;
import com.yice.edu.cn.tap.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

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
	private DdService ddService;

	@PostMapping("/query/queryJwStudentByStuNameAndClassesId")
	@ApiOperation(value = "通过班级id查找学生职务信息", notes = "返回响应对象")
	public ResponseJson queryJwStudentByStuNameAndClassesId(
			@ApiParam(value = "需要用到的班级id、学生姓名", required = true) @RequestBody StuQueryVo queryVo) {
		JwClaCadresStu jwClaCadresStu = new JwClaCadresStu();
		jwClaCadresStu.setClassesId(queryVo.getClassesId());
		if(queryVo.getQueryCondition()!=null && !"".equals(queryVo.getQueryCondition())) {
			jwClaCadresStu.setStudentName("%"+queryVo.getQueryCondition()+"%");
			jwClaCadresStu.setStudentCode("%"+queryVo.getQueryCondition()+"%");
		}
		List<JwClaCadresStu> jwClaCadresStuList = jwClaCadresStuService.findStuAndCadresByClassesIdAndName(jwClaCadresStu);
		return new ResponseJson(jwClaCadresStuList);
	}
	
	@GetMapping("/query/queryJwStudentByClassesId")
	@ApiOperation(value = "查找该老师的授课班级", notes = "返回响应班级信息")
	public ResponseJson qqueryJwStudentByClassesId() {
		List<TeacherClasses> teacherClassesList = jwClassesService.findTeacherClassesByTeacher(LoginInterceptor.myId());
		List<TapTeachClassesInfoResultVo> returnList = new ArrayList<TapTeachClassesInfoResultVo>();
		teacherClassesList.forEach(teacherClasses->{
			JwClasses queryJwClasses = new JwClasses();
			queryJwClasses.setId(teacherClasses.getClassesId());
			List<JwClasses> jwClassesList = jwClassesService.findJwClassesListByConditionAndRelate(queryJwClasses);
			if(!jwClassesList.isEmpty()) {
				JwClasses model = jwClassesList.get(0);
				TapTeachClassesInfoResultVo vo = new TapTeachClassesInfoResultVo();
				vo.setClassesId(teacherClasses.getClassesId());
				vo.setGradeName(model.getGradeName());
				vo.setGradeId(model.getGradeId());
				vo.setClassNum(model.getNumber());
				vo.setMasterTeacher(model.getHeadTeacher()==null?"":model.getHeadTeacher());
//				vo.setPlaceName(model.getAcademicBuildingName()==null?"":model.getAcademicBuildingName()+model.getFloor()==null?"":model.getFloor()+model.getSpaceName()==null?"":model.getSpaceName());
				vo.setStudentNum(model.getStudentCount()==null?"0":model.getStudentCount());
				returnList.add(vo);
			}
		});
		Collections.sort(returnList,Comparator.comparing(TapTeachClassesInfoResultVo::getGradeId,(gradeF,gradeS)->Integer.compare(Integer.parseInt(gradeF),Integer.parseInt(gradeS))).thenComparing(TapTeachClassesInfoResultVo::getClassNum,(classNumF,classNumS)->Integer.compare(Integer.parseInt(classNumF),Integer.parseInt(classNumS))));
		return new ResponseJson(returnList);
	}

	@GetMapping("/findClassesByGradeId/{id}")
	@ApiOperation(value = "根据班级id查找班级", notes = "返回班级信息")
	public ResponseJson findClassesByGradeId(
			@ApiParam(value = "班级id", required = true) @PathVariable String id
	){
		JwClasses jwClasses = new JwClasses();
		jwClasses.setGradeId(id);
		jwClasses.setSchoolId(mySchoolId());
		List<JwClasses> jwClassesList = jwClassesService.findClassesByGradeId(jwClasses);;

		if(CollUtil.isNotEmpty(jwClassesList)){
			Collections.sort(jwClassesList,(o1,o2) -> {
				return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
			} );
		}
		return new ResponseJson(jwClassesList);
	}

	@GetMapping("/findGradesCurrentSchool")
	@ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
	public ResponseJson findGradesCurrentSchool(){
		Dd dd = new Dd();
		dd.setLevelType(currentTeacher().getSchool().getTypeId());
		dd.setTypeId(Constant.DD_TYPE.GRADE);
		List<Dd> data=ddService.findDdListByCondition(dd);
		return new ResponseJson(data);
	}

	@GetMapping("/findAllClassAndGrade")
	@ApiOperation(value = "查找当前学校所有的班级", notes = "返回响应对象")
	public ResponseJson findAllClassAndGrade(){
		Dd dd = new Dd();
		dd.setLevelType(currentTeacher().getSchool().getTypeId());
		dd.setTypeId(Constant.DD_TYPE.GRADE);
		List<Dd> ddList=ddService.findDdListByCondition(dd);
		List<Map<String,Object>> list = new ArrayList<>();
		if(CollUtil.isNotEmpty(ddList)){

			JwClasses jwClasses = new JwClasses();
			jwClasses.setSchoolId(mySchoolId());
			Map<String,Object> map;
			for (Dd d : ddList) {
				map = new HashMap<>();
				jwClasses.setGradeId(d.getId());
				List<JwClasses> jwClassesList = jwClassesService.findClassesByGradeId(jwClasses);;

				if(CollUtil.isNotEmpty(jwClassesList)){
					Collections.sort(jwClassesList,(o1,o2) -> {
						return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
					} );
				}
				map.put("gradeName",d.getName());
				map.put("jwClassesList",jwClassesList);
				list.add(map);
			}

		}

		return new ResponseJson(list);
	}

	@GetMapping("/findTeacherClassesByTeacherId/{activityId}")
	@ApiOperation(value = "去查看页面,通过教师id和活动id查找对应班级")
	public ResponseJson findTeacherClassesByTeacherIdAndActivityId(@PathVariable String activityId){
		List<JwClasses> jwClassesList = jwClassesService.findTeacherClassesByTeacherIdAndActivityId(currentTeacher().getId(),activityId);
		return new ResponseJson(jwClassesList);
	}
}
