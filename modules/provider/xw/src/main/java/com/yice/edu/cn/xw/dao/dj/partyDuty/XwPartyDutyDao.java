package com.yice.edu.cn.xw.dao.dj.partyDuty;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface XwPartyDutyDao {
    List<XwPartyDuty> findXwPartyDutyListByCondition(XwPartyDuty xwPartyDuty);

    long findXwPartyDutyCountByCondition(XwPartyDuty xwPartyDuty);

    XwPartyDuty findOneXwPartyDutyByCondition(XwPartyDuty xwPartyDuty);

    XwPartyDuty findXwPartyDutyById(@Param("id") String id);

    void saveXwPartyDuty(XwPartyDuty xwPartyDuty);

    void updateXwPartyDuty(XwPartyDuty xwPartyDuty);

    void deleteXwPartyDuty(@Param("id") String id);

    void deleteXwPartyDutyByCondition(XwPartyDuty xwPartyDuty);

    void batchSaveXwPartyDuty(List<XwPartyDuty> xwPartyDutys);

    XwPartyDuty findXwPartyDutyByName(@Param("name") String name);

}
