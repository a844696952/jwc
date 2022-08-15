package com.yice.edu.cn.ecc.service.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.ecc.feignClient.parentMsg.DmParentQuickReplyFeign;
import com.yice.edu.cn.ecc.service.parent.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmParentQuickReplyService {
    @Autowired
    private DmParentQuickReplyFeign dmParentQuickReplyFeign;
    @Autowired
    private ParentService parentService;

    public DmParentQuickReply findDmParentQuickReplyById(String id) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyById(id);
    }

    public DmParentQuickReply saveDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.saveDmParentQuickReply(dmParentQuickReply);
    }

    public List<DmParentQuickReply> findDmParentQuickReplyListByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyListByCondition(dmParentQuickReply);
    }

    public DmParentQuickReply findOneDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findOneDmParentQuickReplyByCondition(dmParentQuickReply);
    }

    public long findDmParentQuickReplyCountByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyCountByCondition(dmParentQuickReply);
    }

    public void updateDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyFeign.updateDmParentQuickReply(dmParentQuickReply);
    }

    public void deleteDmParentQuickReply(String id) {
        dmParentQuickReplyFeign.deleteDmParentQuickReply(id);
    }

    public void deleteDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyFeign.deleteDmParentQuickReplyByCondition(dmParentQuickReply);
    }

    public List<DmParentQuickReply> getDmParentQuickReplyListByCard(String id) {
        ParentStudent parentStudent = new ParentStudent();
        parentStudent.setStudentId(id);
        List<ParentStudent> parentStudentList = parentService.findParentStudentListByCondition(parentStudent);
        if(parentStudentList==null || parentStudentList.size()>1){
            return null;
        }else{
            DmParentQuickReply dmParentQuickReply = new DmParentQuickReply();
            dmParentQuickReply.setParentId(parentStudentList.get(0).getParentId());
            List<DmParentQuickReply> dmParentQuickReplyList = dmParentQuickReplyFeign.findDmParentQuickReplyListByCondition(dmParentQuickReply);
            return dmParentQuickReplyList;
        }
    }
}
