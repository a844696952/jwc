package com.yice.edu.cn.tap.feignClient.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormBuildAdminFeign",path = "/dormBuildAdmin")
public interface DormBuildAdminFeign {

    @PostMapping("/findDormBuildAdminListByCondition")
    List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findCreateDormBuildingList")
    List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildAdminListCountConnect")
    long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildTeacherByConditionConnect")
    List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildAdminCountByCondition")
    long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findOneDormBuildAdminByCondition")
    DormBuildAdmin findOneDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildListByCondition")
    List<Building> findDormBuildListByCondition(DormBuildAdmin dormBuildAdmin);
}
