package com.yice.edu.cn.osp.service.jw.classes;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.classes.rise.RiseClazzVo;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;

@Service
public class JwClassesService {
    @Autowired
    private JwClassesFeign jwClassesFeign;
    
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    
    @Autowired
    private SchoolFeign schoolFeign;
    
    @Autowired
    private TeacherPostService teacherPostService;
    
    @Autowired
    private TeacherClassesService teacherClassesService;
    
    @Autowired
    private DdService ddService;
    
    public JwClasses findJwClassesById(String id) {
        return jwClassesFeign.findJwClassesById(id);
    }

    public JwClasses saveJwClasses(JwClasses jwClasses) {
        return jwClassesFeign.saveJwClasses(jwClasses);
    }

    public List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses) {
        return jwClassesFeign.findJwClassesListByCondition(jwClasses);
    }

    public long findJwClassesCountByCondition(JwClasses jwClasses) {
        return jwClassesFeign.findJwClassesCountByCondition(jwClasses);
    }

    public void updateJwClasses(JwClasses jwClasses) {
        jwClassesFeign.updateJwClasses(jwClasses);
    }

    public void deleteJwClasses(String id) {
        jwClassesFeign.deleteJwClasses(id);
    }
    
    
    public void batchCreateClasses(CreateClassesVo createClassesVo) {
        jwClassesFeign.batchCreateClasses(createClassesVo);
    }
    
    public List<JwClasses> findJwClassesListByConditionAndRelate(JwClasses jwClasses){
    	return jwClassesFeign.findJwClassesListByConditionAndRelate(jwClasses);
    }
    
    public List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses){
    	return teacherClassesFeign.findClassTeacherListByClasses(teacherClasses);
    }
    public void updateStuClass(Map<String,String> map) {
    	jwClassesFeign.updateStuClass(map);
    }
    public List<JwClasses> findJwClassesListWithStuNum(JwClasses jwClasses) {
        return jwClassesFeign.findJwClassesListWithStuNum(jwClasses);
    }
    public List<JwClasses> getClassesNumber(JwClasses jwClasses){
        return jwClassesFeign.getClassesNumber(jwClasses);
    }
    
    public void riseClazz(RiseClazzVo riseClazzVo){
        jwClassesFeign.riseClazz(riseClazzVo);
    }
    
	public Integer findMaxClassesNoByCondition(JwClasses jwClasses) {
		return jwClassesFeign.findMaxClassesNoByCondition(jwClasses);
	}
	
	public Integer prepareRise(String schoolId) {
		return schoolFeign.prepareRise(schoolId);
	}
	
	/**
	 * 
	 * @param postValue 职务id
	 * @param teacherId 教师id
	 * @param schoolId  学校id
	 * @return
	 */
	public List<Dd> getTeacherOwnGrade(String postValue,String teacherId,String schoolId){
		List<Dd> data = new ArrayList<Dd>();
		if(postValue.equals(Constant.TEACHER_POST.DEAN)
				|| postValue.equals(Constant.TEACHER_POST.GENERAL_MANAGER) 
				|| postValue.equals(Constant.TEACHER_POST.PRINCIPAL)) {
	        Dd queryDd = new Dd();
	        queryDd.setLevelType(currentTeacher().getSchool().getTypeId());
	        queryDd.setTypeId(Constant.DD_TYPE.GRADE);
	        queryDd.setPager(new Pager().setSortField("sort").setSortOrder("ASC"));
	        data=ddService.findDdListByCondition(queryDd);
		}
		if(postValue.equals(Constant.TEACHER_POST.CLASS_TEACHER)
				|| postValue.equals(Constant.TEACHER_POST.GRADE_LEADER)) {
			TeacherPost queryTeacherPost = new TeacherPost();
			queryTeacherPost.setTeacherId(teacherId);
			queryTeacherPost.setDdId(postValue);
			queryTeacherPost.setSchoolId(schoolId);
			List<TeacherPost> teacherPostList = teacherPostService.findTeacherPostListByCondition(queryTeacherPost);
//			teacherPostList = teacherPostList.stream().collect(
//	                Collectors.collectingAndThen(
//	                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TeacherPost::getGradeId))), ArrayList::new));
		    data = teacherPostList.stream().map(teacherPost->{
		    	Dd dd = new Dd();
		    	dd.setId(teacherPost.getGradeId());
		    	dd.setName(teacherPost.getGradeName());
		    	return dd;
		    }).collect(Collectors.toList());
		}
		
		if(postValue.equals(Constant.TEACHER_POST.ORDINARY_TEACHER) ) {
			TeacherClasses queryTeacherClasses = new TeacherClasses();
			queryTeacherClasses.setTeacherId(teacherId);
			queryTeacherClasses.setPager(new Pager().setIncludes("gradeId","gradeName").setPaging(false));
			List<TeacherClasses> teacherClassesList = teacherClassesService.findTeacherClassesListByCondition(queryTeacherClasses);
		    data = teacherClassesList.stream().map(teacherClasses->{
		    	Dd dd = new Dd();
		    	dd.setId(teacherClasses.getGradeId());
		    	dd.setName(teacherClasses.getGradeName());
		    	return dd;
		    }).collect(Collectors.toList());
		}
		
		return data;
	}
	
	/**
	 * 根据班主任和任课老师职务查询拥有的班级id列表
	 * 
	 * @param postValue 职务id(必填)
	 * @param teacherId 教师id(必填)
	 * @param schoolId  学校id(必填)
	 * @param gradeId  年级id(非必填)
	 * @return 该教师该职务拥有的班级id列表
	 */
	public List<String> getTeacherOwnClazzId(String postValue,String teacherId,String schoolId,String gradeId){
		List<String> clazzIdList = new ArrayList<String>();
		if(Constant.TEACHER_POST.CLASS_TEACHER.equals(postValue)) {
			TeacherPost queryTeacherPost = new TeacherPost();
			queryTeacherPost.setTeacherId(teacherId);
			queryTeacherPost.setDdId(postValue);
			queryTeacherPost.setSchoolId(schoolId);
			List<TeacherPost> teacherPostList = teacherPostService.findTeacherPostListByCondition(queryTeacherPost);
			if(gradeId!=null) {
				teacherPostList = teacherPostList.stream().filter(ss-> gradeId.equals(ss.getGradeId())).collect(Collectors.toList());
			}
			clazzIdList = teacherPostList.stream().map(ss-> {return ss.getClassId();}).collect(Collectors.toList());
		}
		
		if(Constant.TEACHER_POST.ORDINARY_TEACHER.equals(postValue)) {
			TeacherClasses queryTeacherClasses = new TeacherClasses();
			queryTeacherClasses.setTeacherId(teacherId);
			queryTeacherClasses.setPager(new Pager().setIncludes("classesId"));
			List<TeacherClasses> teacherClassesList = teacherClassesService.findTeacherClassesListByCondition(queryTeacherClasses);
			if(gradeId!=null) {
				teacherClassesList.stream().filter(ss-> gradeId.equals(ss.getGradeId())).collect(Collectors.toList());
			}
			clazzIdList = teacherClassesList.stream().map(ss-> {return ss.getClassesId();}).collect(Collectors.toList());
		}
		return clazzIdList;
	}

	public List<JwClasses> findJwClassessByConditionForExam(JwClasses jwClasses) {
		return jwClassesFeign.findJwClassessByConditionForExam(jwClasses);
	}
}
