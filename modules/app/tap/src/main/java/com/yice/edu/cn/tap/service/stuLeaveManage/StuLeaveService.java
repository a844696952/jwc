package com.yice.edu.cn.tap.service.stuLeaveManage;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.tap.feignClient.parent.ParentFeign;
import com.yice.edu.cn.tap.feignClient.stuLeaveManage.NotifyPersonFeign;
import com.yice.edu.cn.tap.feignClient.stuLeaveManage.StuLeaveFeign;
import com.yice.edu.cn.tap.service.student.StudentService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StuLeaveService {
    @Autowired
    private StuLeaveFeign stuLeaveFeign;

    @Autowired
    private ParentFeign parentFeign;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private NotifyPersonFeign notifyPersonFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuLeave findStuLeaveById(String id) {
        return stuLeaveFeign.findStuLeaveById(id);
    }

    public StuLeave saveStuLeave(StuLeave stuLeave) {
        //保存学年学期
        curSchoolYearService.setSchoolYearTerm(stuLeave, stuLeave.getSchoolId());

        Teacher teacher = teacherService.findTeacherById(myId());
        stuLeave.setApplicatiorType("1");
        stuLeave.setTeacherId(myId());
        stuLeave.setTeacherName(teacher.getName());
        stuLeave.setTeacherTel(teacher.getTel());
        stuLeave.setStudentName(stuLeave.getStudent().getName());
        stuLeave.setApproveStatus("0");

        ParentStudentFile parentMsg = parentFeign.findParentMsgByStudentId(stuLeave.getStudent().getId());
        if (parentMsg == null) {
            stuLeave.setParentsId(null);
            stuLeave.setStuRelationship("该学生未绑定家长");
            stuLeave.setParentsTel("无");
        } else {
            stuLeave.setParentsId(parentMsg.getParentId());
            stuLeave.setStuRelationship(parentMsg.getRelationship());
            stuLeave.setParentsTel(parentMsg.getGuardianContact());
        }
        Student ss = studentService.findStudentById(stuLeave.getStudent().getId());
        if (stuLeave.getInOrOutSchool().equals("0")) {
            //更改学生状态
            ss.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
            studentService.updateStudent(ss);
            stuLeave.getStudent().setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
            //推送人员
            NotifyPerson notifyPerson = new NotifyPerson();
            notifyPerson.setSchoolId(mySchoolId());
            List<NotifyPerson> notiffyList = notifyPersonFeign.findNotifyPersonListByCondition(notifyPerson);
            if (notiffyList != null && notiffyList.size() > 0) {
                List<String> list = notiffyList.stream().map(NotifyPerson::getTeacherId).collect(Collectors.toList());
                String[] notifyArr = new String[list.size()];
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(list.toArray(notifyArr), "学生请假管理", "学生请假离校，请注意检查!", Extras.NOTIFY_PERSON);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
        } else if (stuLeave.getInOrOutSchool().equals("1")) {
            ss.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN);
            studentService.updateStudent(ss);
            stuLeave.getStudent().setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN);
        }
        String[] studentList = new String[]{stuLeave.getStudent().getId()};
        final Push push1 = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList, "学生请假管理", "您有一条新的请假抄送提醒!", Extras.STU_LEAVE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));
        return stuLeaveFeign.saveStuLeave(stuLeave);
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

    public ResponseJson updateStuLeave(StuLeave stuLeave) {
        if (stuLeave.getApproveStatus().equals("0")) {
            //更改学生状态
            Student ss = studentService.findStudentById(stuLeave.getStudent().getId());
            ss.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
            studentService.updateStudent(ss);
            stuLeave.getStudent().setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
            //推送
            String[] studentList = new String[]{stuLeave.getStudent().getId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList, "学生请假管理", "您提交的请假已审批通过!", Extras.STU_LEAVE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            stuLeaveFeign.updateStuLeave(stuLeave);
            return new ResponseJson(true, "审批通过");
        } else if (stuLeave.getApproveStatus().equals("1")) {
            //推送
            String[] studentList = new String[]{stuLeave.getStudent().getId()};
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList, "学生请假管理", "您提交的请假申请审批不通过!", Extras.STU_LEAVE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            stuLeaveFeign.updateStuLeave(stuLeave);
            return new ResponseJson(false, "审批不通过");
        }
        return new ResponseJson();
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
