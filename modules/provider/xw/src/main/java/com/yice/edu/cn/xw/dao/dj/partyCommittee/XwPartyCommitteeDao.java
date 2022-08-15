package com.yice.edu.cn.xw.dao.dj.partyCommittee;

import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XwPartyCommitteeDao {
    List<XwPartyCommittee> findXwPartyCommitteeListByCondition(XwPartyCommittee xwPartyCommittee);

    /**
     * 查询党组织机构树结构
     * @param xwPartyCommittee
     * @return
     */
    List<XwPartyCommittee> getPartyCommitteeTree(@Param("schoolId")String schoolId);

    long findXwPartyCommitteeCountByCondition(XwPartyCommittee xwPartyCommittee);

    XwPartyCommittee findOneXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee);

    XwPartyCommittee findXwPartyCommitteeById(@Param("id") String id);

    void saveXwPartyCommittee(XwPartyCommittee xwPartyCommittee);

    void updateXwPartyCommittee(XwPartyCommittee xwPartyCommittee);

    void deleteXwPartyCommittee(@Param("id") String id);

    void deleteXwPartyCommitteeByCondition(XwPartyCommittee xwPartyCommittee);

    void batchSaveXwPartyCommittee(List<XwPartyCommittee> xwPartyCommittees);

    List<XwPartyCommittee> findCommitteeWithParentName(XwPartyCommittee xwPartyCommittee);
}
