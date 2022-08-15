package com.yice.edu.cn.xw.dao.dormManage.houseApplican;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplican;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHouseApplicanDao {
    List<HouseApplican> findHouseApplicanListByCondition(HouseApplican houseApplican);

    long findHouseApplicanCountByCondition(HouseApplican houseApplican);

    HouseApplican findOneHouseApplicanByCondition(HouseApplican houseApplican);

    HouseApplican findHouseApplicanById(@Param("id") String id);

    void saveHouseApplican(HouseApplican houseApplican);

    void updateHouseApplican(HouseApplican houseApplican);

    void deleteHouseApplican(@Param("id") String id);

    void deleteHouseApplicanByCondition(HouseApplican houseApplican);

    void batchSaveHouseApplican(List<HouseApplican> houseApplicans);


    List<HouseApplican> findApprovalPending(HouseApplican houseApplican);

    long findApprovalPendingCount(HouseApplican houseApplican);


    List<HouseApplican> findPassPending(HouseApplican houseApplican);

    long findPassPendingCount(HouseApplican houseApplican);

    void saveHouseApplicanFromParent(HouseApplican houseApplican);

    void updateHouseApplicanAndTeacher(HouseApplican houseApplican);

    List<HouseApplican> findMyApproval(HouseApplican houseApplican);

    long findMyApprovalCount(HouseApplican houseApplican);
}
