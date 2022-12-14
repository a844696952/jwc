package com.yice.edu.cn.jw.service.electiveCourse;

import cn.hutool.core.date.DateUtil;
import com.netflix.discovery.converters.Auto;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.GradeClassesPojo;
import com.yice.edu.cn.jw.dao.electiveCourse.IElectiveCourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElectiveCourseService {
    @Autowired
    private IElectiveCourseDao electiveCourseDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ElectiveStudentService electiveStudentService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ElectiveCourse findElectiveCourseById(String id) {
        return electiveCourseDao.findElectiveCourseById(id);
    }

    @Transactional(readOnly = true)
    public ElectiveCourse findElectiveCourseAndStuCountById(String id) {
        return electiveCourseDao.findElectiveCourseAndStuCountById(id);
    }
    @Transactional
    public ElectiveCourse saveElectiveCourse(ElectiveCourse electiveCourse) {
        electiveCourse.setId(sequenceId.nextId());
        electiveCourse.setCreateTime(DateUtil.now());
        electiveCourseDao.saveElectiveCourse(electiveCourse);
        return electiveCourse;
    }
    @Transactional(readOnly = true)
    public List<ElectiveCourse> findElectiveCourseListByCondition(ElectiveCourse electiveCourse) {
        List<ElectiveCourse> electiveCourseList= electiveCourseDao.findElectiveCourseListByCondition(electiveCourse);
        DoReturnDataStatus(electiveCourseList);


        return  electiveCourseList;
    }
    @Transactional(readOnly = true)
    public ElectiveCourse findOneElectiveCourseByCondition(ElectiveCourse electiveCourse) {
        return electiveCourseDao.findOneElectiveCourseByCondition(electiveCourse);
    }
    @Transactional(readOnly = true)
    public long findElectiveCourseCountByCondition(ElectiveCourse electiveCourse) {
        return electiveCourseDao.findElectiveCourseCountByCondition(electiveCourse);
    }
    @Transactional
    public void updateElectiveCourse(ElectiveCourse electiveCourse) {
        electiveCourseDao.updateElectiveCourse(electiveCourse);
    }
    @Transactional
    public void deleteElectiveCourse(String id) {
        electiveCourseDao.deleteElectiveCourse(id);
    }
    @Transactional
    public void deleteElectiveCourseByCondition(ElectiveCourse electiveCourse) {
        electiveCourseDao.deleteElectiveCourseByCondition(electiveCourse);
    }
    @Transactional
    public void batchSaveElectiveCourse(List<ElectiveCourse> electiveCourses){
        electiveCourses.forEach(electiveCourse -> electiveCourse.setId(sequenceId.nextId()));
        electiveCourseDao.batchSaveElectiveCourse(electiveCourses);
    }

    public ElectiveCourse findElectiveCourseWithClassInfoById(String id) {
        List<GradeClassesPojo> classesNameList=new ArrayList<>();
       List<ElectiveCourse>  electiveCourseList=electiveCourseDao.findElectiveCourseWithClassInfoById(id);
       long count=electiveStudentService.findElectiveStudentCountByCondition(new ElectiveStudent(){{this.setElectiveId(id);}});
       ElectiveCourse electiveCourse=electiveCourseList.get(0);
       electiveCourseList.forEach(e->{
           GradeClassesPojo cp=new GradeClassesPojo();
           cp.setGradeName(e.getGradeName());
           cp.setClassesName(e.getClassesNumber().toString()+'???');
           cp.setClassesId(e.getClassesId());
           cp.setGradeId(e.getGradeId());
           classesNameList.add(cp);
       });
         electiveCourse.setCheckList(classesNameList);
         electiveCourse.setClassesPeopleNum((int) count);
        return electiveCourse;
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<ElectiveCourse> findElectiveCoursesByConditionForStu(ElectiveCourse electiveCourse) {
        List<ElectiveCourse> electiveCourseList= electiveCourseDao.findElectiveCoursesByConditionForStu(electiveCourse);
        DoReturnDataStatus(electiveCourseList);


        return  electiveCourseList;
    }

    private void DoReturnDataStatus(List<ElectiveCourse> electiveCourseList) {
        electiveCourseList.forEach(e->{
            //?????????: 1???????????????????????????
            if(DateUtil.date().before(DateUtil.parse(e.getSignUpStartTime()))){
                e.setSignUpStatus(1);
                //????????????: 1???????????????????????????  2???????????? 3???????????? 4???????????????<??????????????????
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()<e.getMinNum()&&DateUtil.date().before(DateUtil.parse(e.getCourseEndTime()))
                    ){
                e.setSignUpStatus(2);

                //?????????:1???????????????????????????  2???????????? 3???????????? 4???????????????>=?????????????????? 5???????????????<?????????????????? 6???????????????????????????
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&e.getClassesPeopleNum()<e.getMaxNum()
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseStartTime()))
                    ){
                e.setSignUpStatus(3);
                //?????????:1???????????????????????????  2???????????? 3???????????? 4???????????????>=?????????????????? 5???????????????=?????????????????? 6???????????????????????????
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&e.getClassesPeopleNum().equals(e.getMaxNum())
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseStartTime()))
                    ){
                e.setSignUpStatus(4);

                //?????????:1???????????????????????????  2???????????? 3???????????? 4???????????????>=??????????????????  5??????????????????????????? 6???????????????????????????
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpEndTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&DateUtil.date().after(DateUtil.parse(e.getCourseStartTime()))
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseEndTime()))
                    ){
                e.setSignUpStatus(5);

                //?????????:1??? ????????? 3????????????  5???????????????????????????
            }else if((e.getClassEndStatus().equals(1) &&e.getCloseStatus().equals(0))||
                    (DateUtil.date().after(DateUtil.parse(e.getCourseEndTime())) &&e.getCloseStatus().equals(0))
                    ){
                e.setSignUpStatus(6);
                //?????????:1????????????
            }else if(e.getCloseStatus().equals(1)){
                e.setSignUpStatus(7);
            }

        });
    }


    public long findElectiveCourseCountByConditionForStu(ElectiveCourse electiveCourse) {
        return electiveCourseDao.findElectiveCourseCountByConditionForStu(electiveCourse);
    }

    @Transactional
    public int updateElectiveCourseVersion(ElectiveCourse electiveCourse) {
        return electiveCourseDao.updateElectiveCourseVersion(electiveCourse);
    }
}
