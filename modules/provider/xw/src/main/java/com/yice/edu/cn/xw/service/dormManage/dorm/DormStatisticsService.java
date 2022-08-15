package com.yice.edu.cn.xw.service.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormStatistics;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormStatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DormStatisticsService {
    @Autowired
    private IDormStatisticsDao dormStatisticsDao;

    @Transactional
    public List<DormStatistics> findDormInfoStatistics(DormBuildVo dormBuildVo){
        return dormStatisticsDao.findDormInfoStatistics(dormBuildVo);
    }
    @Transactional
    public long findDormInfoCountStatistics(DormBuildVo dormBuildVo){
        return dormStatisticsDao.findDormInfoCountStatistics(dormBuildVo);

    }

    @Transactional
    public DormStatistics findDormBuildStatistics(DormBuildVo dormBuildVo){
        DormStatistics dormBuildStatistics = dormStatisticsDao.findDormBuildStatistics(dormBuildVo);
        if (dormBuildStatistics!=null){
            List<DormStatistics> dormBuildStatisticsSexList = dormStatisticsDao.findDormBuildStatisticsSex(dormBuildVo);
            DormStatistics dormBuildStatisticsForFloors = dormStatisticsDao.findDormBuildStatisticsForFloors(dormBuildVo);
            if (dormBuildStatisticsSexList!=null&&dormBuildStatisticsSexList.size()>0){
                dormBuildStatisticsSexList.stream().filter(dormBuildStatisticsSex -> "1".equals(dormBuildStatisticsSex.getSex())).mapToInt(DormStatistics::getSexCount).forEach(dormBuildStatistics::setBuildManPersonNum);
                dormBuildStatisticsSexList.stream().filter(dormBuildStatisticsSex -> "2".equals(dormBuildStatisticsSex.getSex())).mapToInt(DormStatistics::getSexCount).forEach(dormBuildStatistics::setBuildWomanPersonNum);
            }

            if (dormBuildStatisticsForFloors!=null){
                dormBuildStatistics.setFloors(dormBuildStatisticsForFloors.getFloors());
            }
        }

        return dormBuildStatistics;

    }

    @Transactional
    public List<DormBuildingPersonInfo> findDormBuildFloorsByCondition(Building building){
        return dormStatisticsDao.findDormBuildFloorsByCondition(building);
    }

}
