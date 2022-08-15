package com.yice.edu.cn.xw.dao.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDormDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<Dorm> findDormListByCondition(Dorm dorm);

    long findDormCountByCondition(Dorm dorm);

    Dorm findOneDormByCondition(Dorm dorm);

    Dorm findDormById(@Param("id") String id);

    void saveDorm(Dorm dorm);

    void updateDorm(Dorm dorm);

    void deleteDorm(@Param("id") String id);

    void deleteDormByCondition(Dorm dorm);

    void batchSaveDorm(List<Dorm> dorms);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<Dorm> findDormBuildingTreeByCondition(DormBuildVo dormBuildVo);

    List<Dorm> findDormFloorNum(DormBuildVo dormBuildVo);

    void updateDormByDormId(Dorm dorm);

    List<Dorm> findDormBuildingTreeByConditionTap(DormBuildVo dormBuildVo);

    List<DormBuildingPersonInfo> findDormListByTypeAndCategory(Dorm dorm);

    Building findBuildingById(@Param("id") String id);

    void batchDeleteDormByDormIdList(@Param("dormIdList") List<String> dormIdList);

    List<Building> findDormBuildingAndFloor(@Param("schoolId") String schoolId);

    List<Dorm> findDormByFloorId(Building building);
}
