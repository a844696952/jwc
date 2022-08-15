package com.yice.edu.cn.dm.service.student;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.dm.dao.student.IDmMeritStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmMeritStudentService {
    @Autowired
    private IDmMeritStudentDao dmMeritStudentDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public DmMeritStudent findDmMeritStudentById(String id) {
        return dmMeritStudentDao.findDmMeritStudentById(id);
    }


    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<DmMeritStudent> findDmMeritStudentListByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentDao.findDmMeritStudentListByCondition(dmMeritStudent);
    }
    @Transactional(readOnly = true)
    public DmMeritStudent findOneDmMeritStudentByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentDao.findOneDmMeritStudentByCondition(dmMeritStudent);
    }
    @Transactional(readOnly = true)
    public long findDmMeritStudentCountByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentDao.findDmMeritStudentCountByCondition(dmMeritStudent);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateDmMeritStudent(DmMeritStudent dmMeritStudent) {
        dmMeritStudentDao.updateDmMeritStudent(dmMeritStudent);
    }
    @Transactional
    public void updateDmMeritStudentForAll(DmMeritStudent dmMeritStudent) {
        dmMeritStudentDao.updateDmMeritStudentForAll(dmMeritStudent);
    }
    @Transactional
    public void deleteDmMeritStudent(String id) {
        dmMeritStudentDao.deleteDmMeritStudent(id);
    }
    @Transactional
    public void deleteDmMeritStudentByCondition(DmMeritStudent dmMeritStudent) {
        dmMeritStudentDao.deleteDmMeritStudentByCondition(dmMeritStudent);
    }
    @Transactional
    public void batchSaveDmMeritStudent(List<DmMeritStudent> dmMeritStudents){
        dmMeritStudents.forEach(dmMeritStudent -> dmMeritStudent.setId(sequenceId.nextId()));
        dmMeritStudentDao.batchSaveDmMeritStudent(dmMeritStudents);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<AllStudent> findAllJwClassesAndStudents(String schoolId) {
        List<AllStudent> list = dmMeritStudentDao.findAllJwClassesAndStudents(schoolId);
        if (CollUtil.isNotEmpty(list)) {
            return   list.parallelStream().peek(x->{
                x.setType("0");
                List<Student> studentsByClassId =dmMeritStudentDao.findStudentsByClassId(x.getId());
                x.setStudentList(studentsByClassId);
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<DmMeritStudent> findAll(String schoolId) {
        return dmMeritStudentDao.findAll(schoolId);
    }


    private List<DmMeritStudent> intersection(List<DmMeritStudent> first,List<DmMeritStudent> second){
        return first.stream().filter(second::contains).collect(Collectors.toList());
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveDmMeritStudentOrUpdate(List<DmMeritStudent> dmMeritStudent) {
        //首先判断数据库有没有值
        List<DmMeritStudent> students = findAll(dmMeritStudent.get(0).getSchoolId());
        if(CollectionUtil.isNotEmpty(students)){
            //如果有值，开始求 students 的交集
            List<DmMeritStudent> oldIntersection = intersection(students, dmMeritStudent);
            //在开始插入的交集，这里不能先处理，因为对象在堆里面的指针问题和堆的模型，只能先求出来，否则数据错乱
            List<DmMeritStudent> newIntersection = intersection(dmMeritStudent, students);
            if(CollectionUtil.isEmpty(oldIntersection)){
                //说明没有交集，则新传入的直接插入
                batchSaveDmMeritStudent(dmMeritStudent);
            }else{
                //分别除去交集剩下的分别处理
                oldIntersection.forEach(students::remove);
                students.forEach(this::deleteDmMeritStudentByCondition);
                newIntersection.forEach(dmMeritStudent::remove);
                if(CollectionUtil.isNotEmpty(dmMeritStudent)){
                    batchSaveDmMeritStudent(dmMeritStudent);
                }
            }
            return;
        }
        batchSaveDmMeritStudent(dmMeritStudent);
    }


}
