package com.yice.edu.cn.xw.dao.dormManage.houseApplican;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHouseApplicanFilesDao {
    List<HouseApplicanFiles> findHouseApplicanFilesListByCondition(HouseApplicanFiles houseApplicanFiles);

    long findHouseApplicanFilesCountByCondition(HouseApplicanFiles houseApplicanFiles);

    HouseApplicanFiles findOneHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles);

    HouseApplicanFiles findHouseApplicanFilesById(@Param("id") String id);

    void saveHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles);

    void updateHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles);

    void deleteHouseApplicanFiles(@Param("id") String id);

    void deleteHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles);

    void batchSaveHouseApplicanFiles(List<HouseApplicanFiles> houseApplicanFiless);
}
