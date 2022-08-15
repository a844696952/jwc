package com.yice.edu.cn.xw.service.dj.partyMember;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.IdAndName;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberExcel;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberVaild;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.xw.dao.dj.partyMember.XwPartyMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class XwPartyMemberService {
    @Autowired
    private XwPartyMemberDao xwPartyMemberDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwPartyMember findXwPartyMemberById(String id) {
        return xwPartyMemberDao.findXwPartyMemberById(id);
    }
    @Transactional
    public void saveXwPartyMember(XwPartyMember xwPartyMember) {
        xwPartyMember.setId(sequenceId.nextId());
        xwPartyMemberDao.saveXwPartyMember(xwPartyMember);
    }
    @Transactional(readOnly = true)
    public List<XwPartyMember> findXwPartyMemberListByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberDao.findXwPartyMemberListByCondition(xwPartyMember);
    }
    @Transactional(readOnly = true)
    public XwPartyMember findOneXwPartyMemberByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberDao.findOneXwPartyMemberByCondition(xwPartyMember);
    }
    @Transactional(readOnly = true)
    public long findXwPartyMemberCountByCondition(XwPartyMember xwPartyMember) {
        return xwPartyMemberDao.findXwPartyMemberCountByCondition(xwPartyMember);
    }
    @Transactional
    public void updateXwPartyMember(XwPartyMember xwPartyMember) {
        xwPartyMemberDao.updateXwPartyMember(xwPartyMember);
    }
    @Transactional
    public void deleteXwPartyMember(String id) {
        xwPartyMemberDao.deleteXwPartyMember(id);
    }
    @Transactional
    public void deleteXwPartyMemberByCondition(XwPartyMember xwPartyMember) {
        xwPartyMemberDao.deleteXwPartyMemberByCondition(xwPartyMember);
    }

    @Transactional(readOnly = true)
    public List<XwPartyMember> findXwPartyMemberListByConditions(XwPartyMember xwPartyMember) {
        return xwPartyMemberDao.findXwPartyMemberListByConditions(xwPartyMember);
    }
    @Transactional(readOnly = true)
    public long findXwPartyMemberCountByConditions(XwPartyMember xwPartyMember) {
        return xwPartyMemberDao.findXwPartyMemberCountByConditions(xwPartyMember);
    }

    @Transactional
    public void batchSaveXwPartyMember(List<XwPartyMember> xwPartyMembers){
        xwPartyMembers.stream().forEach(e->{
            e.setId(sequenceId.nextId());
        });
        xwPartyMemberDao.batchSaveXwPartyMember(xwPartyMembers);
    }

    @Transactional(readOnly = true)
    public List<IdAndName> getTeacherList(String schoolId) {
        return xwPartyMemberDao.getTeacherList(schoolId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateParty(List<XwPartyMember> list) {
        xwPartyMemberDao.batchUpdateParty(list);
    }




    @Transactional(rollbackFor = Exception.class)
    public void uploadExcel(List<XwPartyMemberExcel> list){

        if(list!=null && !list.isEmpty()){
            final String now = DateUtil.now();
            final Set<String> teacherIds = list.stream().map(XwPartyMemberExcel::teacherId).collect(Collectors.toSet());
            //先查询这些teacherIds在数据库中存不存在
            final List<XwPartyMember> databaseList = xwPartyMemberDao.findXwPartyMemberListByTeacherIds(teacherIds);
            final Map<String,String> databaseMap = databaseList.stream().collect(Collectors.toMap(XwPartyMember::getTeacherId,XwPartyMember::getId));

            final String insertFlag = "insert";
            final String updateFlag = "update";
            final Map<String, List<XwPartyMember>> insertOrUpdateList = list.stream().collect(
                    Collectors.groupingBy(m -> databaseMap.get(m.teacherId()) == null?insertFlag:updateFlag,
                            Collectors.mapping(t->{
                                final String id = databaseMap.get(t.teacherId());
                                if(id == null){
                                    return t.toXwPartyMemberForInsert(now,sequenceId.nextId());
                                }else{
                                    return t.toXwPartyMemberForUpdate(now,id);
                                }
                            },Collectors.toList())
                    )
            );
            final List<XwPartyMember> insertList = insertOrUpdateList.get(insertFlag);
            final List<XwPartyMember> updateList = insertOrUpdateList.get(updateFlag);

            if(insertList!=null && !insertList.isEmpty()){
                xwPartyMemberDao.batchSaveXwPartyMember(insertList);
            }

            if(updateList!=null && !updateList.isEmpty()){
                xwPartyMemberDao.batchUpdateParty(updateList);
            }

        }
    }

    /**
     * 根据党员类别获取树
     * @param schoolId
     * @return
     */
    public List<XwPartyMember> getPartyMemberTree(String schoolId){
        List<XwPartyMember> partyMemberTree = xwPartyMemberDao.getPartyMemberTree(schoolId);
        if(CollUtil.isNotEmpty (partyMemberTree)){
            partyMemberTree= partyMemberTree.stream ().filter (x-> Objects.nonNull (x.getParentId ())).collect(Collectors.toList());
            return ObjectKit.buildTree(partyMemberTree,"-1");
        }
        return new ArrayList<> ();

    }

    @Transactional(readOnly = true)
    public List<XwPartyMember> findXwPartyMemberListByArray(XwPartyMember xwPartyMember){
        return xwPartyMemberDao.findXwPartyMemberListByArray(xwPartyMember);
    }

    @Transactional(readOnly = true)
    public String getTeacherNameListByString(XwPartyMember xwPartyMember){
        return xwPartyMemberDao.getTeacherNameListByString(xwPartyMember);
    }

    @Transactional
    public void bachUpdateByArray(XwPartyMember xwPartyMember){
        xwPartyMemberDao.bachUpdateByArray(xwPartyMember);
    }

    @Transactional
    public void batchSaveXwPartyMemberByRowData(List<XwPartyMember> xwPartyMembers){
        xwPartyMemberDao.batchSaveXwPartyMemberByRowData(xwPartyMembers);
    }

    @Transactional(readOnly = true)
    public XwPartyMemberVaild lookXwPartyMemberById(String id) {
        return xwPartyMemberDao.lookXwPartyMemberById(id);
    }
}
