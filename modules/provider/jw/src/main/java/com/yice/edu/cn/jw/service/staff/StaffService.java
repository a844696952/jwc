package com.yice.edu.cn.jw.service.staff;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.dao.teacher.ITeacherDao;
import com.yice.edu.cn.jw.service.department.DepartmentTeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private ITeacherDao staffDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @Transactional(readOnly = true)
    public Teacher findStaffById(String id) {
        return staffDao.findTeacherById(id);
    }
    @Transactional
    public void saveStaff(Teacher staff) {
        staff.setId(sequenceId.nextId());
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);//这边所有创建的教师都是职工类型
        //判断有没有部门
        if(StringUtils.isNotEmpty(staff.getDepartmentId())){
            //添加部门
            DepartmentTeacher departmentTeacher = new DepartmentTeacher();
            departmentTeacher.setTeacherId(staff.getId());
            departmentTeacher.setDepartmentId(staff.getDepartmentId());
            departmentTeacherService.saveDepartmentTeacher(departmentTeacher);
        }
        staffDao.saveTeacher(staff);
    }
    @Transactional(readOnly = true)
    public List<Teacher> findStaffListByCondition(Teacher staff) {
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        return staffDao.findTeacherImgListByCondition(staff);
    }
    @Transactional(readOnly = true)
    public Teacher findOneStaffByCondition(Teacher staff) {
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        return staffDao.findOneTeacherByCondition(staff);
    }
    @Transactional(readOnly = true)
    public long findStaffCountByCondition(Teacher staff) {
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        return staffDao.findTeacherImgCountByCondition(staff);
    }
    @Transactional
    public Teacher updateStaff(Teacher staff) {
        //判断手机号
        Teacher t = new Teacher();
        t.setTel(staff.getTel());
        t.setId(staff.getId());
        long c = staffDao.findRepeatByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("手机号重复");
            return t;
        }
        t = new Teacher();
        t.setId(staff.getId());
        t.setDocumentNum(staff.getDocumentNum());
        c = staffDao.findRepeatByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("证件号码重复");
            return t;
        }
        t = new Teacher();
        t.setWorkNumber(staff.getWorkNumber());
        t.setId(staff.getId());
        t.setSchoolId(staff.getSchoolId());
        c = staffDao.findRepeatByCondition(t);
        if (c > 0) {//同个学校内限制
            t.setCode("222");
            t.setMsg("工号重复");
            return t;
        }

        //判断有没有部门
        if(StringUtils.isNotEmpty(staff.getDepartmentId())){
            //添加部门
            DepartmentTeacher departmentTeacher = new DepartmentTeacher();
            departmentTeacher.setTeacherId(staff.getId());
            departmentTeacherService.deleteDepartmentTeacherByCondition(departmentTeacher);
            departmentTeacher.setDepartmentId(staff.getDepartmentId());
            departmentTeacherService.saveDepartmentTeacher(departmentTeacher);
        }
        staffDao.updateTeacher(staff);
        t.setCode("200");
        t.setMsg("添加成功");
        return t;
    }
    @Transactional
    public void deleteStaff(String id) {
        DepartmentTeacher departmentTeacher = new DepartmentTeacher();
        departmentTeacher.setTeacherId(id);
        departmentTeacherService.deleteDepartmentTeacherByCondition(departmentTeacher);
        staffDao.deleteTeacher(id);
    }
    @Transactional
    public void deleteStaffByCondition(Teacher staff) {
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        staffDao.deleteTeacherByCondition(staff);
    }
    @Transactional
    public void batchSaveStaff(List<Teacher> staffs){
        staffs.forEach(staff -> {
            staff.setId(sequenceId.nextId());
            staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        });
        staffDao.batchSaveTeacher(staffs);
    }

}
