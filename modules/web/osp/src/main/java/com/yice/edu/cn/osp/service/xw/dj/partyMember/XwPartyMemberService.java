package com.yice.edu.cn.osp.service.xw.dj.partyMember;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.IdAndName;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberExcel;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberVaild;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMember.XwPartyMemberFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class XwPartyMemberService {
    @Autowired
    private XwPartyMemberFeign xwPartyMemberFeign;

    @Autowired
    SequenceId sequenceId;

    public XwPartyMember findXwPartyMemberById(String id) {
        return xwPartyMemberFeign.findXwPartyMemberById(id);
    }

    public XwPartyMember saveXwPartyMember(XwPartyMember xwPartyMember) {
        xwPartyMember.computeAndSetIntact();
        return xwPartyMemberFeign.saveXwPartyMember(xwPartyMember);
    }

    public List<XwPartyMember> findXwPartyMemberListByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findXwPartyMemberListByCondition(xwPartyMember);
    }

    public XwPartyMember findOneXwPartyMemberByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findOneXwPartyMemberByCondition(xwPartyMember);
    }

    public long findXwPartyMemberCountByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findXwPartyMemberCountByCondition(xwPartyMember);
    }

    public void updateXwPartyMember(XwPartyMember xwPartyMember) {
        xwPartyMember.computeAndSetIntact();
        xwPartyMemberFeign.updateXwPartyMember(xwPartyMember);
//        XwPartyMemberVaild xwPartyMemberVaild = xwPartyMemberFeign.lookXwPartyMemberById(xwPartyMember.getId());
//        if(xwPartyMemberVaild.getIntact()== Constant.PARTY_MEMBER_INTACT.NOCOMPLETE){
//            //如果资料全部填上，则修改资料的完整度
//            if(!checkObjAllFieldsIsNull(xwPartyMemberVaild)){
//                XwPartyMember xwPartyMember1 = new XwPartyMember();
//                xwPartyMember1.setId(xwPartyMemberVaild.getId());
//                //设置资料的完整度
//                xwPartyMember1.setIntact(Constant.PARTY_MEMBER_INTACT.COMPLETE);
//                xwPartyMemberFeign.updateXwPartyMember(xwPartyMember1);
//            }
//        }
    }

    public void deleteXwPartyMember(String id) {
        xwPartyMemberFeign.deleteXwPartyMember(id);
    }

    public void deleteXwPartyMemberByCondition(XwPartyMember xwPartyMember) {
        xwPartyMemberFeign.deleteXwPartyMemberByCondition(xwPartyMember);
    }
    public List<XwPartyMember> findXwPartyMemberListByConditions(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findXwPartyMemberListByConditions(xwPartyMember);
    }

    public long findXwPartyMemberCountByConditions(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findXwPartyMemberCountByConditions(xwPartyMember);
    }

    public void uploadExcel(List<XwPartyMemberExcel> list) {

        xwPartyMemberFeign.uploadExcel(list);
    }

    public List<XwPartyMember> getPartyMemberTree(String schoolId){
        return xwPartyMemberFeign.getPartyMemberTree(schoolId);
    }

    public List<IdAndName> getTeacherList(String schoolId) {
        return xwPartyMemberFeign.getTeacherList(schoolId);
    }

    public void batchSaveXwPartyMember(List<XwPartyMember> xwPartyMemberList) {
        xwPartyMemberFeign.batchSaveXwPartyMember(xwPartyMemberList);
    }

    public List<XwPartyMember> findXwPartyMemberListByArray(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.findXwPartyMemberListByArray(xwPartyMember);
    }
    public String getTeacherNameListByString(XwPartyMember xwPartyMember) {
        return xwPartyMemberFeign.getTeacherNameListByString(xwPartyMember);
    }
    public void bachUpdateByArray(XwPartyMember xwPartyMember){
        xwPartyMemberFeign.bachUpdateByArray(xwPartyMember);
    }

    public void batchUpdateParty(List<XwPartyMember> list) {
        xwPartyMemberFeign.batchUpdateParty(list);
    }
    public void batchSaveXwPartyMemberByRowData(List<XwPartyMember> xwPartyMembers){
        xwPartyMembers.forEach(XwPartyMember::computeAndSetIntact);
        xwPartyMemberFeign.batchSaveXwPartyMemberByRowData(xwPartyMembers);
    }

    public XwPartyMemberVaild lookXwPartyMemberById(String id) {
        XwPartyMemberVaild xwPartyMemberVaild = xwPartyMemberFeign.lookXwPartyMemberById(id);
        //如果资料全部填上，则修改资料的完整度
        if(xwPartyMemberVaild.getIntact()==Constant.PARTY_MEMBER_INTACT.NOCOMPLETE){
            if(!checkObjAllFieldsIsNull(xwPartyMemberVaild)){
                XwPartyMember xwPartyMember1 = new XwPartyMember();
                xwPartyMember1.setId(xwPartyMemberVaild.getId());
                //设置资料的完整度
                xwPartyMember1.setIntact(Constant.PARTY_MEMBER_INTACT.COMPLETE);
                xwPartyMemberFeign.updateXwPartyMember(xwPartyMember1);
            }
        }
        return xwPartyMemberVaild;
    }
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if(null == f.get(object)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
