package com.yice.edu.cn.jw.service.electiveCourse;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.jw.dao.electiveCourse.IElectiveStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElectiveStudentService {
    @Autowired
    private IElectiveStudentDao electiveStudentDao;
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ElectiveStudent findElectiveStudentById(String id) {
        return electiveStudentDao.findElectiveStudentById(id);
    }
    @Transactional
    public void  saveElectiveStudent(ElectiveStudent electiveStudent)  throws Exception  {

            ElectiveCourse electiveCourse=electiveCourseService.findElectiveCourseAndStuCountById(electiveStudent.getElectiveId());
            if(electiveCourse.getClassesPeopleNum()<electiveCourse.getMaxNum()){
                int num=electiveCourseService.updateElectiveCourseVersion(electiveCourse);
              if(num==1){
                  electiveStudent.setId(sequenceId.nextId());
                  electiveStudent.setScore(electiveCourse.getScore());
                  electiveStudentDao.saveElectiveStudent(electiveStudent);
              }else {
                  throw new RuntimeException();
              }

            }else {
                throw new RuntimeException();
            }


    }
    @Transactional(readOnly = true)
    public List<ElectiveStudent> findElectiveStudentListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findElectiveStudentListByCondition(electiveStudent);
    }
    @Transactional(readOnly = true)
    public ElectiveStudent findOneElectiveStudentByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findOneElectiveStudentByCondition(electiveStudent);
    }
    @Transactional(readOnly = true)
    public long findElectiveStudentCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findElectiveStudentCountByCondition(electiveStudent);
    }
    @Transactional
    public void updateElectiveStudent(ElectiveStudent electiveStudent) {
        electiveStudentDao.updateElectiveStudent(electiveStudent);
    }
    @Transactional
    public void deleteElectiveStudent(String id) {
        electiveStudentDao.deleteElectiveStudent(id);
    }
    @Transactional
    public void deleteElectiveStudentByCondition(ElectiveStudent electiveStudent) {
        electiveStudentDao.deleteElectiveStudentByCondition(electiveStudent);
    }

//    @Transactional
//    public void batchSaveElectiveStudent(List<ElectiveStudent> electiveStudents){
//
//        electiveStudents.forEach(electiveStudent -> {
//                saveElectiveStudent(electiveStudent);
//        });
//    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<ElectiveStudent> findMyElectiveStudentListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findMyElectiveStudentListByCondition(electiveStudent);
    }

    public long findMyElectiveStudentCountByCondition(ElectiveStudent electiveStudent) {
       return electiveStudentDao.findMyElectiveStudentCountByCondition(electiveStudent);
    }

    public List<ElectiveCourse> getCanSelectCourseList(ElectiveStudent electiveStudent) {
        return electiveStudentDao.getCanSelectCourseList(electiveStudent);
    }

    public List<ElectiveStudent> findSchoolYearElectiveStudentsByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findSchoolYearElectiveStudentsByCondition(electiveStudent);
    }

    public long findSchoolYearElectiveStudentsCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findSchoolYearElectiveStudentsCountByCondition(electiveStudent);
    }

    public List<ElectiveCourse> getAlreadySelectCourseList(ElectiveStudent electiveStudent) {
        return electiveStudentDao.getAlreadySelectCourseList(electiveStudent);
    }

    public List<ElectiveStudent> findSchoolYearStudentScoreListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findSchoolYearStudentScoreListByCondition(electiveStudent);
    }

    public long findSchoolYearStudentScoreCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findSchoolYearStudentScoreCountByCondition(electiveStudent);

    }

    public long getAlreadySelectCourseCount(ElectiveStudent electiveStudent) {
        return electiveStudentDao.getAlreadySelectCourseCount(electiveStudent);
    }

    public long checkTimeRepeatCount(ElectiveStudent es) {
        return electiveStudentDao.checkTimeRepeatCount(es);
    }


    public List<ElectiveStudent> findElectiveStudentScoreListByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findElectiveStudentScoreListByCondition(electiveStudent);
    }

    public long findElectiveStudentScoreCountByCondition(ElectiveStudent electiveStudent) {
        return electiveStudentDao.findElectiveStudentScoreCountByCondition(electiveStudent);
    }





}
