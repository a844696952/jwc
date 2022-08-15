package com.yice.edu.cn.jw.service.department;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.jw.dao.department.IDepartmentTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentTeacherService {
    @Autowired
    private IDepartmentTeacherDao departmentTeacherDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DepartmentTeacher findDepartmentTeacherById(String id) {
        return departmentTeacherDao.findDepartmentTeacherById(id);
    }
    @Transactional
    public void saveDepartmentTeacher(DepartmentTeacher departmentTeacher) {
        departmentTeacher.setId(sequenceId.nextId());
        departmentTeacherDao.saveDepartmentTeacher(departmentTeacher);
    }
    @Transactional(readOnly = true)
    public List<DepartmentTeacher> findDepartmentTeacherListByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherDao.findDepartmentTeacherListByCondition(departmentTeacher);
    }
    @Transactional(readOnly = true)
    public DepartmentTeacher findOneDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherDao.findOneDepartmentTeacherByCondition(departmentTeacher);
    }
    @Transactional(readOnly = true)
    public long findDepartmentTeacherCountByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherDao.findDepartmentTeacherCountByCondition(departmentTeacher);
    }
    @Transactional
    public void updateDepartmentTeacher(DepartmentTeacher departmentTeacher) {
        departmentTeacherDao.updateDepartmentTeacher(departmentTeacher);
    }
    @Transactional
    public void deleteDepartmentTeacher(String id) {
        departmentTeacherDao.deleteDepartmentTeacher(id);
    }
    @Transactional
    public void deleteDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher) {
        departmentTeacherDao.deleteDepartmentTeacherByCondition(departmentTeacher);
    }
    @Transactional
    public void batchSaveDepartmentTeacher(List<DepartmentTeacher> departmentTeachers){
        departmentTeacherDao.batchSaveDepartmentTeacher(departmentTeachers);
    }
    @Transactional
    public void updateDepartmentMember(String teacherId, List<DepartmentTeacher> departmentTeachers) {
        DepartmentTeacher con = new DepartmentTeacher();
        con.setTeacherId(teacherId);
        departmentTeacherDao.deleteDepartmentTeacherByCondition(con);
        if(CollectionUtil.isNotEmpty(departmentTeachers)){
            for (DepartmentTeacher dt : departmentTeachers) {
                dt.setId(sequenceId.nextId());
            }
            departmentTeacherDao.batchSaveDepartmentTeacher(departmentTeachers);
        }
    }
    @Transactional(readOnly = true)
    public List<DepartmentTeacher> findDepartmentByTeacherId(String teacherId) {
        return departmentTeacherDao.findDepartmentByTeacherId(teacherId);
    }
    @Transactional(readOnly = true)
    public List<Department> findDepartmentBySchoolId4Staff(String schoolId){
        return departmentTeacherDao.findDepartmentBySchoolId4Staff(schoolId);
    }
}
