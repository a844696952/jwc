package com.yice.edu.cn.ecc.service.msg;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.ParentReceiveMsg;
import com.yice.edu.cn.common.pojo.dm.msg.TeacherReceiveMsg;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.ecc.feignClient.classCard.ClassCardFeign;
import com.yice.edu.cn.ecc.feignClient.msg.DmMsgFeign;
import com.yice.edu.cn.ecc.feignClient.msg.ParentReceiveMsgFeign;
import com.yice.edu.cn.ecc.feignClient.msg.TeacherReceiveMsgFeign;
import com.yice.edu.cn.ecc.feignClient.parent.ParentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmMsgService {
    @Autowired
    private DmMsgFeign dmMsgFeign;

    @Autowired
    private ParentReceiveMsgFeign parentReceiveMsgFeign;

    @Autowired
    private TeacherReceiveMsgFeign teacherReceiveMsgFeign;

    @Autowired
    private ParentFeign parentFeign;

    @Autowired
    private ClassCardFeign classCardFeign;

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
        final DmClassCard classCard = classCardFeign.findDmClassCardByStudentId(student.getId());
        final String teacherId = classCard.getTeacherId();
        String parentId = null;
        ParentStudent ps = new ParentStudent();
        ps.setStudentId(student.getId());
        List<ParentStudent> parentStudentList = parentFeign.findParentStudentListByCondition(ps);
        if(parentStudentList!=null && !parentStudentList.isEmpty()){
            parentId = parentStudentList.get(0).getParentId();
        }

        dmMsg.setTeacherId(teacherId);
        dmMsg.setParentId(parentId);

        DmMsg msg =dmMsgFeign.saveDmMsg(dmMsg);
        TeacherReceiveMsg teacherReceiveMsg = new TeacherReceiveMsg(msg);
        teacherReceiveMsg.setTeacherId(teacherId);
        ParentReceiveMsg parentReceiveMsg = new ParentReceiveMsg(msg);
        parentReceiveMsg.setParentId(parentId);
        parentReceiveMsgFeign.saveReceiveMsg(parentReceiveMsg);
        teacherReceiveMsgFeign.saveReceiveMsg(teacherReceiveMsg);
        pushMsgService.pushMsg(msg);
    };


}
