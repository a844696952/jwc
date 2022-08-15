package com.yice.edu.cn.bmp.service.stuLeaveManage;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.bmp.feignClient.parent.ParentFeign;
import com.yice.edu.cn.bmp.feignClient.stuLeaveManage.StuLeaveFeign;
import com.yice.edu.cn.bmp.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myParentId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class StuLeaveService {
    @Autowired
    private StuLeaveFeign stuLeaveFeign;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private ParentService parentService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ParentFeign parentFeign;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuLeave findStuLeaveById(String id) {
        return stuLeaveFeign.findStuLeaveById(id);
    }

    public ResponseJson saveStuLeave(StuLeave stuLeave) {
        //当前孩子班主任
        Student student = studentService.findStudentById(myStudentId());
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(student.getClassesId());
        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
        if (teacher == null) {
            return new ResponseJson(false, "该学生所在班级未绑定班主任");
        } else {
            //保存学年学期
            curSchoolYearService.setSchoolYearTerm(stuLeave, stuLeave.getSchoolId());
            //亲属关系
            ParentStudentFile parentMsg = parentFeign.findParentMsgByStudentId(myStudentId());
            //保存要用信息
            stuLeave.setTeacherName(teacher.getName());
            stuLeave.setTeacherTel(teacher.getTel());
            stuLeave.setTeacherId(teacher.getId());

            stuLeave.setStuRelationship(parentMsg.getRelationship());
            stuLeave.setParentsTel(parentMsg.getGuardianContact());
            stuLeave.setParentsId(parentMsg.getParentId());

            stuLeave.setStudent(student);
            stuLeave.setStudentName(student.getName());
            stuLeave.setApproveStatus("2");
            stuLeave.setApplicatiorType("0");
            //推送
            String[] teacherList = new String[]{teacher.getId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList, "学生请假管理", "您有一条新的请假申请!", Extras.STU_LEAVE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            //System.out.println("您有一条新的请假申请!");
            stuLeaveFeign.saveStuLeave(stuLeave);
            return new ResponseJson();
        }
    }

    public void batchSaveStuLeave(List<StuLeave> stuLeaves) {
        stuLeaveFeign.batchSaveStuLeave(stuLeaves);
    }

    public List<StuLeave> findStuLeaveListByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveListByCondition(stuLeave);
    }

    public StuLeave findOneStuLeaveByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findOneStuLeaveByCondition(stuLeave);
    }

    public long findStuLeaveCountByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveCountByCondition(stuLeave);
    }

    public void updateStuLeave(StuLeave stuLeave) {
        stuLeaveFeign.updateStuLeave(stuLeave);
    }

    public void updateStuLeaveForAll(StuLeave stuLeave) {
        stuLeaveFeign.updateStuLeaveForAll(stuLeave);
    }

    public void deleteStuLeave(String id) {
        stuLeaveFeign.deleteStuLeave(id);
    }

    public void deleteStuLeaveByCondition(StuLeave stuLeave) {
        stuLeaveFeign.deleteStuLeaveByCondition(stuLeave);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
