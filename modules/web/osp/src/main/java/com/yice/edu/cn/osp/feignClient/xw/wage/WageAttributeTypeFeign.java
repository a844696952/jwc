package com.yice.edu.cn.osp.feignClient.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttributeType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "wageAttributeTypeFeign",path = "/wageAttributeType")
public interface WageAttributeTypeFeign {
    @GetMapping("/findWageAttributeTypeById/{id}")
    WageAttributeType findWageAttributeTypeById(@PathVariable("id") String id);
    @PostMapping("/saveWageAttributeType")
    WageAttributeType saveWageAttributeType(WageAttributeType wageAttributeType);
    @PostMapping("/findWageAttributeTypeListByCondition")
    List<WageAttributeType> findWageAttributeTypeListByCondition(WageAttributeType wageAttributeType);
    @PostMapping("/findOneWageAttributeTypeByCondition")
    WageAttributeType findOneWageAttributeTypeByCondition(WageAttributeType wageAttributeType);
    @PostMapping("/findWageAttributeTypeCountByCondition")
    long findWageAttributeTypeCountByCondition(WageAttributeType wageAttributeType);
    @PostMapping("/updateWageAttributeType")
    void updateWageAttributeType(WageAttributeType wageAttributeType);
    @GetMapping("/deleteWageAttributeType/{id}")
    void deleteWageAttributeType(@PathVariable("id") String id);
    @PostMapping("/deleteWageAttributeTypeByCondition")
    void deleteWageAttributeTypeByCondition(WageAttributeType wageAttributeType);

    @GetMapping("/findWageAttributeTypeByTypeId/{id}")
    List<WageAttributeType> findWageAttributeTypeByTypeId(@PathVariable("id") String id);
}
