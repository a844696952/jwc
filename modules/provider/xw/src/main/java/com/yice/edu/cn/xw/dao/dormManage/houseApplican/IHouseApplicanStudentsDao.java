package com.yice.edu.cn.xw.dao.dormManage.houseApplican;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHouseApplicanStudentsDao {
    List<HouseApplicanStudents> findHouseApplicanStudentsListByCondition(HouseApplicanStudents houseApplicanStudents);

    long findHouseApplicanStudentsCountByCondition(HouseApplicanStudents houseApplicanStudents);

    HouseApplicanStudents findOneHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents);

    HouseApplicanStudents findHouseApplicanStudentsById(@Param("id") String id);

    void saveHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);

    void updateHouseApplicanStudents1(HouseApplicanStudents houseApplicanStudents);

    void deleteHouseApplicanStudents(@Param("id") String id);

    void deleteHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents);

    void batchSaveHouseApplicanStudents(List<HouseApplicanStudents> houseApplicanStudentss);

    List<HouseApplicanStudents> findHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);

    long findHouseApplicanStudentsCount(HouseApplicanStudents houseApplicanStudents);

    List<HouseApplicanStudents> findStudentIdByhouseApplicanId(@Param("houseApplicanId") String houseApplicanId);

    List<HouseApplicanStudents> findStudentIdByhouseApplicanId1(HouseApplicanStudents houseApplicanStudents);

    void saveHouseApplicanStudents1(HouseApplicanStudents houseApplicanStudents);

    long lookHouseApplicanStudentsCount(String houseApplicanId);

}
