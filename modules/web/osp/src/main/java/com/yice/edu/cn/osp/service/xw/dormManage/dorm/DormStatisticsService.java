package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormStatistics;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormStatisticsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormStatisticsService {
    @Autowired
    private DormStatisticsFeign dormStatisticsFeign;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;

    public List<DormBuildingPersonInfo> findDormBuildingAndFloor() {
        List<DormBuildingPersonInfo> list = new ArrayList<>();
        List<Building> buildingList = dormBuildAdminService.findDormBuildingByLogin();
        if (buildingList!=null&&buildingList.size()>0){
            buildingList.forEach(b->{
                //设置宿舍楼
                DormBuildingPersonInfo dormBuild = new DormBuildingPersonInfo();
                dormBuild.setDormBuildId(b.getId());
                dormBuild.setDormBuildName(b.getName());
                dormBuild.setParentId(b.getParentId());
                list.add(dormBuild);
                //设置宿舍楼层
                Building building = new Building();
                building.setParentId(b.getId());
                List<DormBuildingPersonInfo> dormBuildFloors = dormStatisticsFeign.findDormBuildFloorsByCondition(building);
                list.addAll(dormBuildFloors);
            });
        }
        List<DormBuildingPersonInfo> collect = list.stream().distinct().collect(Collectors.toList());
        return collect;
    }


    public List<DormStatistics> findDormInfoStatistics(DormBuildVo dormBuildVo){
        return dormStatisticsFeign.findDormInfoStatistics(dormBuildVo);
    }

    public long findDormInfoCountStatistics(DormBuildVo dormBuildVo){
        return dormStatisticsFeign.findDormInfoCountStatistics(dormBuildVo);
    }


    public DormStatistics findDormBuildStatistics(DormBuildVo dormBuildVo) {
        return dormStatisticsFeign.findDormBuildStatistics(dormBuildVo);
    }
}
