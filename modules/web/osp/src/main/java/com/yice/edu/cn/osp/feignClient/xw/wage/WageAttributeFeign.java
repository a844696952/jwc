package com.yice.edu.cn.osp.feignClient.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttribute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "wageAttributeFeign",path = "/wageAttribute")
public interface WageAttributeFeign {
    @GetMapping("/findWageAttributeById/{id}")
    WageAttribute findWageAttributeById(@PathVariable("id") String id);
    @PostMapping("/saveWageAttribute")
    WageAttribute saveWageAttribute(WageAttribute wageAttribute);
    @PostMapping("/findWageAttributeListByCondition")
    List<WageAttribute> findWageAttributeListByCondition(WageAttribute wageAttribute);
    @PostMapping("/findOneWageAttributeByCondition")
    WageAttribute findOneWageAttributeByCondition(WageAttribute wageAttribute);
    @PostMapping("/findWageAttributeCountByCondition")
    long findWageAttributeCountByCondition(WageAttribute wageAttribute);
    @PostMapping("/updateWageAttribute")
    void updateWageAttribute(WageAttribute wageAttribute);
    @GetMapping("/deleteWageAttribute/{id}")
    void deleteWageAttribute(@PathVariable("id") String id);
    @PostMapping("/deleteWageAttributeByCondition")
    void deleteWageAttributeByCondition(WageAttribute wageAttribute);
}
