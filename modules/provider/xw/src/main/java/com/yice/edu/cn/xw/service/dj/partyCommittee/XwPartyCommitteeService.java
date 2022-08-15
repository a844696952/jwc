package com.yice.edu.cn.xw.service.dj.partyCommittee;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.xw.dao.dj.partyCommittee.XwPartyCommitteeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class XwPartyCommitteeService {
    @Autowired
    private XwPartyCommitteeDao xwPartyCommitteeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwPartyCommittee findXwPartyCommitteeById(String id) {
        return xwPartyCommitteeDao.findXwPartyCommitteeById(id);
    }
    @Transactional
    public void saveXwPartyCommittee(XwPartyCommittee xwPartyCommittee) {
        xwPartyCommittee.setId(sequenceId.nextId());
        xwPartyCommitteeDao.saveXwPartyCommittee(xwPartyCommittee);
    }
    @Transactional(readOnly = true)
    public List<XwPartyCommittee> findXwPartyCommitteeListByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeDao.findXwPartyCommitteeListByCondition(xwPartyCommittee);
    }
    @Transactional(readOnly = true)
    public XwPartyCommittee findOneXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeDao.findOneXwPartyCommitteeByCondition(xwPartyCommittee);
    }
    @Transactional(readOnly = true)
    public long findXwPartyCommitteeCountByCondition(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeDao.findXwPartyCommitteeCountByCondition(xwPartyCommittee);
    }
    @Transactional
    public void updateXwPartyCommittee(XwPartyCommittee xwPartyCommittee) {
        xwPartyCommitteeDao.updateXwPartyCommittee(xwPartyCommittee);
    }
    @Transactional
    public void deleteXwPartyCommittee(String id) {
        xwPartyCommitteeDao.deleteXwPartyCommittee(id);
    }
    @Transactional
    public void deleteXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee) {
        xwPartyCommitteeDao.deleteXwPartyCommitteeByCondition(xwPartyCommittee);
    }
    @Transactional
    public void batchSaveXwPartyCommittee(List<XwPartyCommittee> xwPartyCommittees){
        xwPartyCommittees.forEach(xwPartyCommittee -> xwPartyCommittee.setId(sequenceId.nextId()));
        xwPartyCommitteeDao.batchSaveXwPartyCommittee(xwPartyCommittees);
    }

    /**
     * 查询党组织机构数
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<XwPartyCommittee> getPartyCommitteeTree(String schoolId) {
        List<XwPartyCommittee> partyCommitteeList = xwPartyCommitteeDao.getPartyCommitteeTree(schoolId);
        if(CollUtil.isNotEmpty (partyCommitteeList)){
            //筛选parentId不为空的列表
            partyCommitteeList= partyCommitteeList.stream ().filter (x-> Objects.nonNull (x.getParentId ())).collect(Collectors.toList());
           return ObjectKit.buildTree(partyCommitteeList, "-1");

        }
        return new ArrayList<> ();
    }

    public List<XwPartyCommittee> findCommitteeWithParentName(XwPartyCommittee xwPartyCommittee) {
        return xwPartyCommitteeDao.findCommitteeWithParentName(xwPartyCommittee);
    }
}
