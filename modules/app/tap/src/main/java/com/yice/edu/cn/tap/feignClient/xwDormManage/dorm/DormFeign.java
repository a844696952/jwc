package com.yice.edu.cn.tap.feignClient.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormFeign",path = "/dorm")
public interface DormFeign {
    @PostMapping("/findDormBuildingTreeByConditionTap")
    List<Dorm> findDormBuildingTreeByConditionTap(DormBuildVo dormBuildVo);
    @PostMapping("/findDormFloorNum")
    List<Dorm> findDormFloorNum(DormBuildVo dormBuildVo);
}
