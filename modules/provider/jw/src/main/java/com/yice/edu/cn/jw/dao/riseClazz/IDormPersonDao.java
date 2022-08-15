package com.yice.edu.cn.jw.dao.riseClazz;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDormPersonDao {

    //通过人员id数组查询宿舍人员入住情况
    List<DormPerson> findDormPersonListByPersonIds(DormPerson dormPerson);

    List<DormBuildingPersonInfo> getDormBuildingByIds(@Param("ids") List<String> ids);

    long batchLeaveDormByIds(DormPerson dormPerson);

    //根据条件查询楼栋,楼层,宿舍的学生入住人数
    List<Dorm> findDormMoveIntoPersonNumByCondition(DormBuildVo dormBuildVo);

    void updateDormByDormId(Dorm dorm);

    //批量保存退宿人员日志
    void batchSaveDormPersonLog(List<DormPersonLog> dormPersonLogs);

    //批量保存退宿人员信息
    void batchSaveDormPersonOut(List<DormPersonOut> dormPersonOuts);
}
