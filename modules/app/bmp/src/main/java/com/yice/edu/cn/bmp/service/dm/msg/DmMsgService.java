package com.yice.edu.cn.bmp.service.dm.msg;

import com.yice.edu.cn.bmp.feignClient.dm.classcard.DmClassCardFeign;
import com.yice.edu.cn.bmp.feignClient.dm.msg.DmMsgFeign;
import com.yice.edu.cn.bmp.feignClient.dm.msg.StudentReceiveMsgFeign;
import com.yice.edu.cn.bmp.feignClient.dm.msg.TeacherReceiveMsgFeign;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import com.yice.edu.cn.common.pojo.dm.msg.TeacherReceiveMsg;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class DmMsgService {
    @Autowired
    private DmMsgFeign dmMsgFeign;

    @Autowired
    private StudentReceiveMsgFeign studentReceiveMsgFeign;

    @Autowired
    private TeacherReceiveMsgFeign teacherReceiveMsgFeign;

    @Autowired
    private DmClassCardFeign dmClassCardFeign;

    @Autowired
    private PushMsgService pushMsgService;

    public DmMsg findDmMsgById(String id) {
        return dmMsgFeign.findDmMsgById(id);
    }

    public DmMsg saveDmMsg(DmMsg dmMsg) {
        return dmMsgFeign.saveDmMsg(dmMsg);
    }

    public List<DmMsg> findDmMsgListByCondition(DmMsg dmMsg) {
        return dmMsgFeign.findDmMsgListByCondition(dmMsg);
    }

    public DmMsg findOneDmMsgByCondition(DmMsg dmMsg) {
        return dmMsgFeign.findOneDmMsgByCondition(dmMsg);
    }

    public long findDmMsgCountByCondition(DmMsg dmMsg) {
        return dmMsgFeign.findDmMsgCountByCondition(dmMsg);
    }

    public void updateDmMsg(DmMsg dmMsg) {
        dmMsgFeign.updateDmMsg(dmMsg);
    }

    public void deleteDmMsg(String id) {
        dmMsgFeign.deleteDmMsg(id);
    }

    public void deleteDmMsgByCondition(DmMsg dmMsg) {
        dmMsgFeign.deleteDmMsgByCondition(dmMsg);
    }


    public void sendMsg(DmMsg dmMsg, Student student){

        final DmClassCard classCard = dmClassCardFeign.findDmClassCardByStudentId(myStudentId());
        final String teacherId = classCard.getTeacherId();
        final String userName = classCard.getUserName();
        dmMsg.setTeacherId(teacherId);
        DmMsg msg =dmMsgFeign.saveDmMsg(dmMsg);
        TeacherReceiveMsg teacherReceiveMsg = new TeacherReceiveMsg(msg);
        StudentReceiveMsg studentReceiveMsg = new StudentReceiveMsg(msg);
        studentReceiveMsg.setStudent(student);
        studentReceiveMsg.setClassesId(student.getClassesId());
        studentReceiveMsg.setDmUser(userName);
        studentReceiveMsgFeign.saveReceiveMsg(studentReceiveMsg);
        teacherReceiveMsgFeign.saveReceiveMsg(teacherReceiveMsg);
        pushMsgService.pushMsg(msg);
    };
}
