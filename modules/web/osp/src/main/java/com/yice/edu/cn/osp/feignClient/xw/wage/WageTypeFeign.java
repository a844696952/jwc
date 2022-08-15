package com.yice.edu.cn.osp.feignClient.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "wageTypeFeign",path = "/wageType")
public interface WageTypeFeign {
    @GetMapping("/findWageTypeById/{id}")
    WageType findWageTypeById(@PathVariable("id") String id);
    @PostMapping("/saveWageType")
    WageType saveWageType(WageType wageType);
    @PostMapping("/findWageTypeListByCondition")
    List<WageType> findWageTypeListByCondition(WageType wageType);
    @PostMapping("/findWageTypeListByConditionNotState")
    List<WageType> findWageTypeListByConditionNotState(WageType wageType);

    @PostMapping("/findOneWageTypeByCondition")
    WageType findOneWageTypeByCondition(WageType wageType);
    @PostMapping("/findWageTypeCountByCondition")
    long findWageTypeCountByCondition(WageType wageType);
    @PostMapping("/updateWageType")
    void updateWageType(WageType wageType);
    @GetMapping("/deleteWageType/{id}")
    void deleteWageType(@PathVariable("id") String id);
    @PostMapping("/deleteWageTypeByCondition")
    void deleteWageTypeByCondition(WageType wageType);

    @PostMapping("/findWageTypeListByCondition1")
    List<WageType> findWageTypeListByCondition1(WageType wageType);
    @GetMapping("/updateWageTypeState/{id}")
    void updateWageTypeState(@PathVariable("id") String id);

    @PostMapping("/findWageTypeListByState")
    List<WageType> findWageTypeListByState(WageType wageType);

    @PostMapping("/findWageAttributeListByTypeId")
    List<WageType> findWageAttributeListByTypeId(WageType wageType);

}
