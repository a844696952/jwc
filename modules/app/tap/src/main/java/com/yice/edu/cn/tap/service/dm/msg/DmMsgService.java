package com.yice.edu.cn.tap.service.dm.msg;

import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.ParentReceiveMsg;
import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.feignClient.dm.classCard.DmClassCardFeign;
import com.yice.edu.cn.tap.feignClient.dm.msg.DmMsgFeign;
import com.yice.edu.cn.tap.feignClient.dm.msg.ParentReceiveMsgFeign;
import com.yice.edu.cn.tap.feignClient.dm.msg.StudentReceiveMsgFeign;
import com.yice.edu.cn.tap.feignClient.parent.ParentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmMsgService{
    @Autowired
    private DmMsgFeign dmMsgFeign;

    @Autowired
    private ParentReceiveMsgFeign parentReceiveMsgFeign;

    @Autowired
    private StudentReceiveMsgFeign studentReceiveMsgFeign;

    @Autowired
    private ParentFeign parentFeign;



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
        final DmClassCard classCard = dmClassCardFeign.findDmClassCardByStudentId(dmMsg.getStudentId());
        final String classId = classCard.getClassId();
        final String userName = classCard.getUserName();
        String parentId = null;

        ParentStudent ps = new ParentStudent();
        ps.setStudentId(student.getId());
        List<ParentStudent> parentStudentList = parentFeign.findParentStudentListByCondition(ps);
        if(parentStudentList!=null && !parentStudentList.isEmpty()){
            parentId = parentStudentList.get(0).getParentId();
        }
        dmMsg.setParentId(parentId);
        dmMsg.setStudentId(student.getId());
        DmMsg msg =dmMsgFeign.saveDmMsg(dmMsg);

        StudentReceiveMsg studentReceiveMsg = new StudentReceiveMsg(msg);
        studentReceiveMsg.setDmUser(userName);
        studentReceiveMsg.setClassesId(classId);
        studentReceiveMsg.setStudent(student);
        ParentReceiveMsg parentReceiveMsg = new ParentReceiveMsg(msg);
        parentReceiveMsg.setParentId(parentId);
        parentReceiveMsgFeign.saveReceiveMsg(parentReceiveMsg);
        studentReceiveMsgFeign.saveReceiveMsg(studentReceiveMsg);
        pushMsgService.pushMsg(msg);
    };
}
