package com.yice.edu.cn.yed.service.jw.yedHomePage;

import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.yed.*;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.yed.feignClient.jw.yedHomePage.RegionFeign;
import com.yice.edu.cn.yed.feignClient.jw.yedHomePage.YedFeign;
import com.yice.edu.cn.yed.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;

@Service
public class YedService {
    @Autowired
    private YedFeign yedFeign;
    @Autowired
    private RegionFeign regionFeign;
    public List<Yed> findNewbornList(){
        return yedFeign.findNewbornList(currentAdmin().getEducationBureauId());
    }

    public List<JournalCircle> findJournalStatic() {
        return yedFeign.findJournalStatic(currentAdmin().getEducationBureauId());
    }

    public StudentCheckWork findStudentCheckWork( ){
       return yedFeign.findStudentCheckWork(currentAdmin().getEducationBureauId());
    }

   /* public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(HomeworkCountQueryVo vo){
        return yedFeign.findSchoolHomeworkNumByDateAndStatus(vo);
    }

    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDate(HomeworkCountQueryVo vo){
        return yedFeign.findSchoolHomeworkNumByDate(vo);
    }*/

   /* public School findSchoolById(String id){
        return  regionFeign.findSchoolById(id);
    }*/

    public  List<Yed> findSpaceByRoleList(){
        return  yedFeign.findSpaceByRoleList(currentAdmin().getEducationBureauId());
    }

    public  List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified){
        return  yedFeign.findSchoolNotifiedListByCondition(schoolNotified);
    }

    public List<Object> findEnrolmentList(){
       Enrolment enrolment=  new Enrolment();
       enrolment.setEducationBureauId(currentAdmin().getEducationBureauId());
        return yedFeign.findEnrolmentList(enrolment);
    }
   /* public  List<School>findSchoolByEducation(){
        return yedFeign.findSchoolByEducation(currentAdmin().getEducationBureauId());
    }*/

    public List<AreaOperationVolume>findTaskSituation(){
        return yedFeign.findTaskSituation(currentAdmin().getEducationBureauId());
    }



}
