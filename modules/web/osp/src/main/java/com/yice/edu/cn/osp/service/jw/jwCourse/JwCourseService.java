package com.yice.edu.cn.osp.service.jw.jwCourse;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.jw.jwCourse.MaterialFeigin;
import com.yice.edu.cn.osp.feignClient.jw.jwCourse.JwCourseFeign;
import com.yice.edu.cn.osp.feignClient.jw.jwCourse.SubjectMaterialFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentSchoolId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class JwCourseService {
    @Autowired
    private JwCourseFeign jwCourseFeign;

    @Autowired
    private SubjectMaterialFeign subjectMaterialFeign;

    @Autowired
    private MaterialFeigin materialFeigin;

    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private DdFeign ddFeign;

    public JwCourse findJwCourseById(String id) {
        return jwCourseFeign.findJwCourseById(id);
    }

    public long saveJwCourse(JwCourse jwCourse) {
        return jwCourseFeign.saveJwCourse(jwCourse);
    }

    public List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseListByCondition(jwCourse);
    }

    public JwCourse findOneJwCourseByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findOneJwCourseByCondition(jwCourse);
    }
    public long findJwCourseCountByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseCountByCondition(jwCourse);
    }

    public void updateJwCourse(JwCourse jwCourse) {
        jwCourseFeign.updateJwCourse(jwCourse);
    }

    public void deleteJwCourse(String id) {
        jwCourseFeign.deleteJwCourse(id);
    }
    @CacheInvalidate(name="dd_subject_school_",key="#schoolId")
    public void deleteJwCourseByCondition(JwCourse jwCourse) {
        jwCourseFeign.deleteJwCourseByCondition(jwCourse);
    }

    public List<Dd> queryAllByTypeIdGrade() {
        return jwCourseFeign.queryAllByTypeIdGrade();
    }

    public List<Dd> queryAllByTypeIdCourse() {
        return jwCourseFeign.queryAllByTypeIdCourse();
    }

    public List<Dd> queryAllByTypeIdType() {
        return jwCourseFeign.queryAllByTypeIdType();
    }

    public List<Dd> queryAllByTypeIdBuilding() {
        return jwCourseFeign.queryAllByTypeIdBuilding();
    }

    public Dd queryOneById(String id) {
        return jwCourseFeign.queryOneById(id);
    }

    public long distinctJwCourse(JwCourse jwCourse) {
        return jwCourseFeign.distinctJwCourse(jwCourse);
    }

    public long findTeacherClassesCourseCountByCondition(String id) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setSchoolId(mySchoolId());
        teacherClasses.setCourseId(id);
        return teacherClassesFeign.findTeacherClassesCountByCondition(teacherClasses);
    }

    public void deleteTeacherClassesCourseByCondition(JwCourse jwCourse) {
        jwCourseFeign.deleteTeacherClassesCourseByCondition(jwCourse);
    }

    public void updateTeacherClassesCourseByCondition(JwCourse jwCourse){
        jwCourseFeign.updateTeacherClassesCourseByCondition(jwCourse);
    }

    public List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeign.findSubjectMaterialListByCondition(subjectMaterial);
    }

    public  List<Material> findMaterialListByCondition(Material material){
        return  materialFeigin.findMaterialListByCondition(material);
    }

    public List<Teacher> findTeachersByNameId(String nameId, String schoolId) {
        return jwCourseFeign.findTeachersByNameId(nameId,schoolId);
    }

    public  List<JwCourse> findJwCourseListByConditionKong(JwCourse jwCourse){
        return jwCourseFeign.findJwCourseListByConditionKong(jwCourse);
    }

    public long findJwCourseCountByConditionKong(JwCourse jwCourse){
        return jwCourseFeign.findJwCourseCountByConditionKong(jwCourse);
    }
    @CacheInvalidate(name="dd_subject_school_",key="#schoolId")
    public void clearDdSubjectSchool(String schoolId){

    }
    /**
     * 学校查询可以创建题目的科目
     * 查询规则
     * 1、学校创建的同时勾选参与考试的科目
     * 2、平台允许创建题目的科目
     * 3、以上的交集
     * @param schoolId,sectionId
     */
    @Cached(name="dd_subject_school_",key="#schoolId",expire = 60)
    public List<Dd> findSchoolEaxmCourseList(String schoolId){
        List<JwCourse> jwCourses = jwCourseFeign.findSchoolEaxmCourseList(schoolId);
        if(jwCourses==null||jwCourses.size()==0)
            return null;
        return jwCourses.stream().map(c->{
            Dd dd = new Dd();
            dd.setId(c.getId());
            dd.setName(c.getName());
            return dd;
        }).collect(Collectors.toList());
    }


    /**
     * 我们平台转21世纪科目
     * @return
     */
    public static String getSubjectToMap(String subjectId){
        Map<String,String> sMap = new HashMap<>();
        //小学
        sMap.put("130","2");//语文
        sMap.put("131","3");//数学
        sMap.put("132","4");//外语
        sMap.put("133","9");//品德与生活
        sMap.put("134","5");//科学
        //初中
        sMap.put("150","2");//语文
        sMap.put("151","3");//数学
        sMap.put("152","4");//外语
        sMap.put("153","8");//历史
        sMap.put("154","10");//地理
        sMap.put("155","11");//生物
        sMap.put("156","6");//物理
        sMap.put("157","7");//化学
        sMap.put("158","9");//思想品德
        sMap.put("162","14");//信息技术

        sMap.put("166","5");//科学
        sMap.put("167","20");//历史与社会
        sMap.put("168","21");//社会思品
        //高中
        sMap.put("180","2");//语文
        sMap.put("181","3");//数学
        sMap.put("182","4");//外语
        sMap.put("183","8");//历史
        sMap.put("184","10");//地理
        sMap.put("185","11");//生物
        sMap.put("186","6");//物理
        sMap.put("187","7");//化学
        sMap.put("188","9");//政治
        sMap.put("191","14");//信息技术
        sMap.put("18202","20");//历史与社会
        sMap.put("18203","21");//社会思品
        return sMap.get(subjectId);
    }

    public List<Dd> findFiltrationJwCouserBySchoolId(Dd dd){
        return jwCourseFeign.findFiltrationJwCouserBySchoolId(dd);
    }
}
