package com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormFeign",path = "/dorm")
public interface DormFeign {
    @GetMapping("/findDormById/{id}")
    Dorm findDormById(@PathVariable("id") String id);
    @PostMapping("/saveDorm")
    Dorm saveDorm(Dorm dorm);
    @PostMapping("/findDormListByCondition")
    List<Dorm> findDormListByCondition(Dorm dorm);
    @PostMapping("/findOneDormByCondition")
    Dorm findOneDormByCondition(Dorm dorm);
    @PostMapping("/findDormCountByCondition")
    long findDormCountByCondition(Dorm dorm);
    @PostMapping("/updateDorm")
    void updateDorm(Dorm dorm);
    @GetMapping("/deleteDorm/{id}")
    void deleteDorm(@PathVariable("id") String id);
    @PostMapping("/deleteDormByCondition")
    void deleteDormByCondition(Dorm dorm);

    /*-------------------------------------------------------------------------------*/
    @PostMapping("/findDormBuildingTreeByCondition")
    List<Dorm> findDormBuildingTreeByCondition(DormBuildVo dormBuildVo);
    @PostMapping("/findDormFloorNum")
    List<Dorm> findDormFloorNum(DormBuildVo dormBuildVo);
    @PostMapping("/updateDormByDormId")
    void updateDormByDormId(Dorm dorm);
    @PostMapping("/findDormListByTypeAndCategory")
    List<DormBuildingPersonInfo> findDormListByTypeAndCategory(Dorm dorm);
    @PostMapping("/findDormListIsNullBunkByType")
    List<DormBuildingPersonInfo> findDormListIsNullBunkByType(Dorm dorm);
    @PostMapping("/batchSaveDormType")
    void batchSaveDormType(Dorm dorm);
    @GetMapping("/findDormBuildingAndFloor/{schoolId}")
    List<Building> findDormBuildingAndFloor(@PathVariable("schoolId") String schoolId);
    @PostMapping("/findDormByFloorId")
    List<Dorm> findDormByFloorId(Building building);
}
