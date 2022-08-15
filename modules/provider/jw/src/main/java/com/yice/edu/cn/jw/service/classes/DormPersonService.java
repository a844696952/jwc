package com.yice.edu.cn.jw.service.classes;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.jw.dao.riseClazz.IDormPersonDao;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherClassesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormPersonService {

    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private IDormPersonDao dormPersonDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ITeacherClassesDao teacherClassesDao;

    //对接毕业学生的方法:对毕业生进行退宿
    @Transactional
    public void LeaveDormByClassesIds(Student student) {
        //毕业的学生
        List<Student> schoolStudentList = studentDao.findSchoolStudentByClazzIdList(student);
        //根据毕业的学生在宿舍人员表查询学生所在宿舍的详情
        List<String> stringList = schoolStudentList.stream().map(Student::getId).collect(Collectors.toList());
        DormPerson dormPerson = new DormPerson();
        dormPerson.setPersonIds(stringList);
        List<DormPerson> dormPersonList = dormPersonDao.findDormPersonListByPersonIds(dormPerson);
        if (dormPersonList!=null&&dormPersonList.size()>0){
            //批量退宿
            this.batchLeaveDorm(dormPersonList);
        }
    }

    //批量退宿
    private void batchLeaveDorm(List<DormPerson> dormPersonList){
        List<String> ids = dormPersonList.stream().map(DormPerson::getId).collect(Collectors.toList());
        //退宿
        DormPerson dormPerson = new DormPerson();
        dormPerson.setIds(ids);
        long l = dormPersonDao.batchLeaveDormByIds(dormPerson);
        if (l>0){
            //更新宿舍入住人数
            this.UpdatePersonNum(dormPersonList);
            //批量保存退宿人员和退宿人员日志
            this.batchSaveDormPersonOutAndDormPersonLog(dormPersonList);

        }
    }


    //更新宿舍入住人数
    private void UpdatePersonNum(List<DormPerson> dormPersonList){
        List<String> dormIdList = dormPersonList.stream().map(DormPerson::getDormId).distinct().collect(Collectors.toList());
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setDormIdList(dormIdList);
        List<Dorm> dormList = dormPersonDao.findDormMoveIntoPersonNumByCondition(dormBuildVo);
        if (dormList!=null&&dormList.size()>0){
            dormList.forEach(dorm -> {
                dormPersonDao.updateDormByDormId(dorm);
            });
        }
    }

    private void batchSaveDormPersonOutAndDormPersonLog(List<DormPerson> dormPersonList) {
        //保存退宿人员信息
        List<String> studentIds = dormPersonList.stream().map(DormPerson::getPersonId).collect(Collectors.toList());
        List<Student> studentList = studentDao.findStudentByIds(studentIds);
        List<DormPersonOut> dormPersonOutList = new ArrayList<>();
        studentList.forEach(student -> {
              TeacherClasses teacherClasses = new TeacherClasses();
              teacherClasses.setClassesId(student.getClassesId());
              Teacher headmaster = teacherClassesDao.findHeadmasterByClasses(teacherClasses);
              DormPersonOut dormPersonOut = new DormPersonOut();
              dormPersonOut.setId(sequenceId.nextId());
              dormPersonOut.setPersonName(student.getName());
              dormPersonOut.setPersonId(student.getId());
              dormPersonOut.setPersonType("1");
              dormPersonOut.setSex(student.getSex());
              dormPersonOut.setGuardianContact(student.getGuardianContact());
              dormPersonOut.setImgUrl(student.getImgUrl());
              dormPersonOut.setStudentNo(student.getStudentNo());
              dormPersonOut.setClassesId(student.getClassesId());
              dormPersonOut.setSchoolId(student.getSchoolId());
              if (headmaster!=null){
                  dormPersonOut.setTeacherName(headmaster.getName());
                  dormPersonOut.setTeacherTel(headmaster.getTel());
              }
              SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
              String outTime = sDF.format(new Date());
              dormPersonOut.setOutTime(outTime);
            dormPersonOutList.add(dormPersonOut);
        });
        dormPersonDao.batchSaveDormPersonOut(dormPersonOutList);
        //保存退宿人员日志
        List<String> ids = dormPersonList.stream().map(DormPerson::getId).collect(Collectors.toList());
        List<DormPersonLog> dormPersonLogList = new ArrayList<>();
        List<DormBuildingPersonInfo> dormBuildingPersonInfoList = dormPersonDao.getDormBuildingByIds(ids);
        dormBuildingPersonInfoList.forEach(dormBuildingPersonInfo -> {
            DormPersonLog dormPersonLog = new DormPersonLog();
            dormPersonLog.setId(sequenceId.nextId());
            dormPersonLog.setDormBuildName(dormBuildingPersonInfo.getDormBuildName());
            dormPersonLog.setFloor(dormBuildingPersonInfo.getFloor());
            dormPersonLog.setDormName(dormBuildingPersonInfo.getDormName());
            dormPersonLog.setBunkName(dormBuildingPersonInfo.getBunkName());
            SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
            String optime = sDF.format(new Date());
            dormPersonLog.setOptime(optime);
            dormPersonLog.setOptType("2");
            dormPersonList.forEach(dormPerson -> {
               if (dormPerson.getDormId().equals(dormBuildingPersonInfo.getDormId())){
                   dormPersonLog.setPersonId(dormPerson.getPersonId());
                   dormPersonLog.setPersonType(dormPerson.getPersonType());
                   dormPersonLog.setSchoolId(dormPerson.getSchoolId());
                   dormPersonLog.setRemarks(dormPerson.getRemarks());
               }
            });
            dormPersonLogList.add(dormPersonLog);
        });
        dormPersonDao.batchSaveDormPersonLog(dormPersonLogList);
    }

}
