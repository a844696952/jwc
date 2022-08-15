package com.yice.edu.cn.xw.dao.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDormStatisticsDao {

    List<DormStatistics> findDormInfoStatistics(DormBuildVo dormBuildVo);

    long findDormInfoCountStatistics(DormBuildVo dormBuildVo);

    DormStatistics findDormBuildStatistics(DormBuildVo dormBuildVo);

    List<DormStatistics> findDormBuildStatisticsSex(DormBuildVo dormBuildVo);

    DormStatistics findDormBuildStatisticsForFloors(DormBuildVo dormBuildVo);

    List<DormBuildingPersonInfo> findDormBuildFloorsByCondition(Building building);
}
