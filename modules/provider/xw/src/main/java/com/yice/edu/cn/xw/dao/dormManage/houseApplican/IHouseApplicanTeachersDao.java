package com.yice.edu.cn.xw.dao.dormManage.houseApplican;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IHouseApplicanTeachersDao {
    List<HouseApplicanTeachers> findHouseApplicanTeachersListByCondition(HouseApplicanTeachers houseApplicanTeachers);

    long findHouseApplicanTeachersCountByCondition(HouseApplicanTeachers houseApplicanTeachers);

    HouseApplicanTeachers findOneHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers);

    HouseApplicanTeachers findHouseApplicanTeachersById(@Param("id") String id);

    void saveHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers);

    void updateHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers);

    void deleteHouseApplicanTeachers(@Param("id") String id);

    void deleteHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers);

    void batchSaveHouseApplicanTeachers(List<HouseApplicanTeachers> houseApplicanTeacherss);

    void updateHouseApplicanTeachers1(HouseApplicanTeachers houseApplicanTeachers);
}
