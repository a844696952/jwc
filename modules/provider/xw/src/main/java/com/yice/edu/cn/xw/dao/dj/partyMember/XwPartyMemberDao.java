package com.yice.edu.cn.xw.dao.dj.partyMember;

import com.yice.edu.cn.common.pojo.xw.dj.partyMember.IdAndName;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberVaild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface XwPartyMemberDao {
    List<XwPartyMember> findXwPartyMemberListByCondition(XwPartyMember xwPartyMember);

    /**
     * 获取党员类别组织结构树
     * @param schoolId
     * @return
     */
    List<XwPartyMember> getPartyMemberTree(@Param("schoolId")String schoolId);

    long findXwPartyMemberCountByCondition(XwPartyMember xwPartyMember);

    XwPartyMember findOneXwPartyMemberByCondition(XwPartyMember xwPartyMember);

    XwPartyMember findXwPartyMemberById(@Param("id") String id);

    void saveXwPartyMember(XwPartyMember xwPartyMember);

    void updateXwPartyMember(XwPartyMember xwPartyMember);

    void deleteXwPartyMember(@Param("id") String id);

    void deleteXwPartyMemberByCondition(XwPartyMember xwPartyMember);

    void batchSaveXwPartyMember(List<XwPartyMember> xwPartyMembers);

    List<XwPartyMember> findXwPartyMemberListByConditions(XwPartyMember xwPartyMember);

    long findXwPartyMemberCountByConditions(XwPartyMember xwPartyMember);

    void updateParty(XwPartyMember xwPartyMember);

    void batchUpdateParty(List<XwPartyMember> xwPartyMembers);

    List<IdAndName> getTeacherList(String schoolId);

    List<XwPartyMember> findXwPartyMemberListByArray(XwPartyMember xwPartyMember);

    String getTeacherNameListByString(XwPartyMember xwPartyMember);

    void bachUpdateByArray(XwPartyMember xwPartyMember);

    void batchSaveXwPartyMemberByRowData(List<XwPartyMember> xwPartyMembers);

    XwPartyMemberVaild lookXwPartyMemberById(@Param("id") String id);

    List<XwPartyMember> findXwPartyMemberListByTeacherIds(@Param("teacherIds") Set<String> teacherIds);
}
