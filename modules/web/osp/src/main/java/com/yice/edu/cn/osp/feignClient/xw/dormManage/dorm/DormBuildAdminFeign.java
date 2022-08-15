package com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormBuildAdminFeign",path = "/dormBuildAdmin")
public interface DormBuildAdminFeign {
    @GetMapping("/findDormBuildAdminById/{id}")
    DormBuildAdmin findDormBuildAdminById(@PathVariable("id") String id);
    @PostMapping("/saveDormBuildAdmin")
    DormBuildAdmin saveDormBuildAdmin(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildAdminListByCondition")
    List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findOneDormBuildAdminByCondition")
    DormBuildAdmin findOneDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildAdminCountByCondition")
    long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/updateDormBuildAdmin")
    void updateDormBuildAdmin(DormBuildAdmin dormBuildAdmin);
    @GetMapping("/deleteDormBuildAdmin/{id}")
    void deleteDormBuildAdmin(@PathVariable("id") String id);
    @PostMapping("/deleteDormBuildAdminByCondition")
    void deleteDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin);
    /*------------------------------------------------------------------------*/
    @PostMapping("/getBuildingList")
    List<Building> getBuildingList(Building building);
    @PostMapping("/findDormBuildAdminListByConditionConnect")
    List<DormBuildAdmin> findDormBuildAdminListByConditionConnect(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildAdminListCountConnect")
    long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildTeacherByConditionConnect")
    List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findCreateDormBuildingList")
    List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin);
    @PostMapping("/findDormBuildListByCondition")
    List<Building> findDormBuildListByCondition(DormBuildAdmin dormBuildAdmin);

}
