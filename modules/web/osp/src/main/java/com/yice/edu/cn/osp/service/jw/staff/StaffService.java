package com.yice.edu.cn.osp.service.jw.staff;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.jw.staff.StaffFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StaffService {
    @Autowired
    private StaffFeign staffFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private SchoolFeign schoolFeign;
    public Teacher findStaffById(String id) {
        return staffFeign.findStaffById(id);
    }

    public Teacher saveStaff(Teacher staff) {
        //判断手机号
        Teacher t = new Teacher();
        t.setTel(staff.getTel());
        long c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("手机号重复");
            return t;
        }
        t = new Teacher();
        t.setDocumentNum(staff.getDocumentNum());
        c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("证件号码重复");
            return t;
        }
        t = new Teacher();
        t.setWorkNumber(staff.getWorkNumber());
        t.setSchoolId(mySchoolId());
        c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {//同个学校内限制
            t.setCode("222");
            t.setMsg("工号重复");
            return t;
        }
        staff.setSchoolId(mySchoolId());
        School school = schoolFeign.findSchoolById(mySchoolId());
        if(school==null){
            t.setCode("222");
            t.setMsg("非正常学校");
            return t;
        }
        staff.setSchoolName(school.getName());
        staff.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        staff.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        t = staffFeign.saveStaff(staff);
        t.setCode("200");
        t.setMsg("添加成功");
        return t;
    }

    public List<Teacher> findStaffListByCondition(Teacher staff) {
        return staffFeign.findStaffListByCondition(staff);
    }

    public Teacher findOneStaffByCondition(Teacher staff) {
        return staffFeign.findOneStaffByCondition(staff);
    }

    public long findStaffCountByCondition(Teacher staff) {
        return staffFeign.findStaffCountByCondition(staff);
    }

    public Teacher updateStaff(Teacher staff) {
       return staffFeign.updateStaff(staff);
    }

    public void deleteStaff(String id) {
        staffFeign.deleteStaff(id);
    }

    public void deleteStaffByCondition(Teacher staff) {
        staffFeign.deleteStaffByCondition(staff);
    }
}
