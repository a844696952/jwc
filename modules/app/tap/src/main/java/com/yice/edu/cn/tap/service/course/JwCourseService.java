package com.yice.edu.cn.tap.service.course;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.tap.feignClient.course.JwCourseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JwCourseService {
    @Autowired
    private JwCourseFeign jwCourseFeign;


    public JwCourse findJwCourseById(String id) {
        return jwCourseFeign.findJwCourseById(id);
    }

    public long saveJwCourse(JwCourse jwCourse) {
        return jwCourseFeign.saveJwCourse(jwCourse);
    }

    public List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseListByCondition(jwCourse);
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

    public void deleteTeacherClassesCourseByCondition(@RequestBody String id) {
        System.out.println(id + ":b");
        jwCourseFeign.deleteTeacherClassesCourseByCondition(id);

    }

    public void updateTeacherClassesCourseByCondition(JwCourse jwCourse){
        jwCourseFeign.updateTeacherClassesCourseByCondition(jwCourse);
    }

    /**
     * ???????????????????????????????????????
     * ????????????
     * 1???????????????????????????????????????????????????
     * 2????????????????????????????????????
     * 3??????????????????
     * @param schoolId,sectionId
     */
    @Cached(name="dd_subject_school_",key="#schoolId",expire = 3,timeUnit = TimeUnit.DAYS)
    public List<Dd> findSchoolEaxmCourseList(String schoolId){
        List<JwCourse> jwCourses = jwCourseFeign.findSchoolEaxmCourseList(schoolId);
        return jwCourses.stream().map(c->{
            Dd dd = new Dd();
            dd.setId(c.getId());
            dd.setName(c.getName());
            return dd;
        }).collect(Collectors.toList());
    }
    /**
     * ???????????????21????????????
     * @return
     */
    public static String getSubjectToMap(String subjectId){
        Map<String,String> sMap = new HashMap<>();
        //??????
        sMap.put("130","2");//??????
        sMap.put("131","3");//??????
        sMap.put("132","4");//??????
        sMap.put("133","9");//???????????????
        sMap.put("134","5");//??????
        //??????
        sMap.put("150","2");//??????
        sMap.put("151","3");//??????
        sMap.put("152","4");//??????
        sMap.put("153","8");//??????
        sMap.put("154","10");//??????
        sMap.put("155","11");//??????
        sMap.put("156","6");//??????
        sMap.put("157","7");//??????
        sMap.put("158","9");//????????????
        sMap.put("162","14");//????????????

        sMap.put("166","5");//??????
        sMap.put("167","20");//???????????????
        sMap.put("168","21");//????????????
        //??????
        sMap.put("180","2");//??????
        sMap.put("181","3");//??????
        sMap.put("182","4");//??????
        sMap.put("183","8");//??????
        sMap.put("184","10");//??????
        sMap.put("185","11");//??????
        sMap.put("186","6");//??????
        sMap.put("187","7");//??????
        sMap.put("188","9");//??????
        sMap.put("191","14");//????????????
        sMap.put("18202","20");//???????????????
        sMap.put("18203","21");//????????????
        return sMap.get(subjectId);
    }
}
