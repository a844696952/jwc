package com.yice.edu.cn.jw.service.department;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.department.IDepartmentDao;
import com.yice.edu.cn.jw.dao.department.IDepartmentTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentDao departmentDao;
    @Autowired
    private IDepartmentTeacherDao  departmentTeacherDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Department findDepartmentById(String id) {
        return departmentDao.findDepartmentById(id);
    }
    @Transactional
    public void saveDepartment(Department department) {
        department.setId(sequenceId.nextId());
        departmentDao.saveDepartment(department);
    }
    @Transactional(readOnly = true)
    public List<Department> findDepartmentListByCondition(Department department) {
        return departmentDao.findDepartmentListByCondition(department);
    }
    @Transactional(readOnly = true)
    public Department findOneDepartmentByCondition(Department department) {
        return departmentDao.findOneDepartmentByCondition(department);
    }
    @Transactional(readOnly = true)
    public long findDepartmentCountByCondition(Department department) {
        return departmentDao.findDepartmentCountByCondition(department);
    }
    @Transactional
    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }
    @Transactional
    public void deleteDepartment(String id) {
        departmentDao.deleteDepartment(id);
        DepartmentTeacher departmentTeacher = new DepartmentTeacher();
        departmentTeacher.setDepartmentId(id);
        departmentTeacherDao.deleteDepartmentTeacherByCondition(departmentTeacher);
        //同时删除所有子节点和对应在departmentTeacher数据
        deleteDepartmentRecursive(id);
    }
    private void deleteDepartmentRecursive(String pId){
        Department department = new Department();
        department.setParentId(pId);
        List<Department> departments = departmentDao.findDepartmentListByCondition(department);
        for (Department d : departments) {
            DepartmentTeacher departmentTeacher = new DepartmentTeacher();
            departmentTeacher.setDepartmentId(d.getId());
            departmentTeacherDao.deleteDepartmentTeacherByCondition(departmentTeacher);
            departmentDao.deleteDepartment(d.getId());
            deleteDepartmentRecursive(d.getId());
        }
    }

    @Transactional
    public void deleteDepartmentByCondition(Department department) {
        departmentDao.deleteDepartmentByCondition(department);
    }
    @Transactional
    public void batchSaveDepartment(List<Department> departments){
        departmentDao.batchSaveDepartment(departments);
    }
    @Transactional(readOnly = true)
    public List<Department> findDepartmentTreeBySchoolId(String schoolId, boolean notEmpty, int personType) {
        List<Department> departments=departmentDao.findDepartmentTreeBySchoolId(schoolId,personType);
        List<Department> dps = ObjectKit.buildTree(departments, "-1");
        if(notEmpty){
            removeEmptyNode(dps);
        }
        return dps;
    }
    private void removeEmptyNode(List<Department> departments){
        for (int i = departments.size()-1; i >=0; i--) {
            Department department = departments.get(i);
            if(isEmptyDepartment(department)){
                departments.remove(i);
            }else if(department.getChildren()!=null&&department.getChildren().size()>0){
                removeEmptyNode(departments.get(i).getChildren());
            }
        }
    }
    private boolean isEmptyDepartment(Department department){
        if(department.getType()==1)return false;
        List<Department> children = department.getChildren();
        if(children==null||children.size()==0)return true;
        for (Department child : children) {
            if(!isEmptyDepartment(child)){
                return false;
            }
        }
        return true;
    }
    @Transactional(readOnly = true)
    public List<Department> findDepartmentTreeToSchoolNotifyBySchoolId(String schoolId) {
        return  departmentDao.findDepartmentTreeToSchoolNotifyBySchoolId(schoolId);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findSelectTeachers(String departmentId) {
        return departmentDao.findSelectTeachers(departmentId);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findAllTeacherBySchoolId(String schoolId) {
        return departmentDao.findAllTeacherBySchoolId(schoolId);
    }
    @Transactional
    public void updateDepartmentMember(String departmentId, List<DepartmentTeacher> departmentTeachers) {
        DepartmentTeacher con = new DepartmentTeacher();
        con.setDepartmentId(departmentId);
        departmentTeacherDao.deleteDepartmentTeacherByCondition(con);
        if(CollectionUtil.isNotEmpty(departmentTeachers)){
            for (DepartmentTeacher dt : departmentTeachers) {
                dt.setId(sequenceId.nextId());
            }
            departmentTeacherDao.batchSaveDepartmentTeacher(departmentTeachers);
        }

    }
    @Transactional(readOnly = true)
    public List<Department> getMyDepartmentNames(String id) {
        return departmentDao.getMyDepartmentNames(id);
    }
    @Transactional(readOnly = true)
    public List<Department> findDepartmentInIds(List<String> ids, String schoolId) {
        return departmentDao.findDepartmentInIds(ids,schoolId);
    }
    @Transactional(readOnly = true)
    public List<Department> findTeacherInIds(List<String> ids, String schoolId) {
        return departmentDao.findTeacherInIds(ids,schoolId);
    }
}
