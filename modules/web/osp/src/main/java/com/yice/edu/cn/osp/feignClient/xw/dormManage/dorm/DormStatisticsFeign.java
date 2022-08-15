package com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormStatistics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormStatisticsFeign",path = "/dormStatistics")
public interface DormStatisticsFeign {

    @PostMapping("/findDormInfoStatistics")
    List<DormStatistics> findDormInfoStatistics(DormBuildVo dormBuildVo);
    @PostMapping("/findDormInfoCountStatistics")
    long findDormInfoCountStatistics(DormBuildVo dormBuildVo);
    @PostMapping("/findDormBuildStatistics")
    DormStatistics findDormBuildStatistics(DormBuildVo dormBuildVo);
    @PostMapping("/findDormBuildFloorsByCondition")
    List<DormBuildingPersonInfo> findDormBuildFloorsByCondition(Building building);

}
