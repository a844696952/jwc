package com.yice.edu.cn.tap.service.classes;

import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.tap.feignClient.classes.JwClassesFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JwClassesService {
    @Autowired
    private JwClassesFeign jwClassesFeign;
    
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    

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
    
    public void riseTopClasses(Map<String,String> map){
    	jwClassesFeign.riseTopClasses(map);
    }
    
    public void riseGeneralClasses(Map<String,String> map){
    	jwClassesFeign.riseGeneralClasses(map);
    }
    
    public List<TeacherClasses> findTeacherClassesByTeacher(String classesId){
    	return teacherClassesFeign.findTeacherClassesByTeacher(classesId);
    }
    

	public List<JwClasses> getClassTree(String schoolId,String teacherId){
		return  jwClassesFeign.getClassTree(schoolId,teacherId);
	}

    public List<JwClasses> findClassesByGradeId(JwClasses jwClasses) {
        return jwClassesFeign.findClassesByGradeId(jwClasses);
    }

    public List<JwClasses> findTeacherClassesByTeacherIdAndActivityId(String teacherId,String activityId) {
        return jwClassesFeign.findTeacherClassesByTeacherIdAndActivityId(teacherId,activityId);
    }
}
