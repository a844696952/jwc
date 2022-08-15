package com.yice.edu.cn.xw.dao.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDormBuildAdminDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin);

    long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin);

    DormBuildAdmin findOneDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin);

    DormBuildAdmin findDormBuildAdminById(@Param("id") String id);

    void saveDormBuildAdmin(DormBuildAdmin dormBuildAdmin);

    void updateDormBuildAdmin(DormBuildAdmin dormBuildAdmin);

    void deleteDormBuildAdmin(@Param("id") String id);

    void deleteDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin);

    void batchSaveDormBuildAdmin(List<DormBuildAdmin> dormBuildAdmins);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<Building> getBuildingList(Building building);

    List<DormBuildAdmin> findDormBuildAdminListByConditionConnect(DormBuildAdmin dormBuildAdmin);

    long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin);

    List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin);

    List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin);

    List<Building> findDormBuildListByCondition(DormBuildAdmin dormBuildAdmin);
}
