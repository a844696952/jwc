package com.yice.edu.cn.osp.feignClient.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "wageValueFeign",path = "/wageValue")
public interface WageValueFeign {
    @GetMapping("/findWageValueById/{id}")
    WageValue findWageValueById(@PathVariable("id") String id);
    @PostMapping("/saveWageValue")
    WageValue saveWageValue(WageValue wageValue);
    @PostMapping("/findWageValueListByCondition")
    List<WageValue> findWageValueListByCondition(WageValue wageValue);
    @PostMapping("/findOneWageValueByCondition")
    WageValue findOneWageValueByCondition(WageValue wageValue);
    @PostMapping("/findWageValueCountByCondition")
    long findWageValueCountByCondition(WageValue wageValue);
    @PostMapping("/updateWageValue")
    void updateWageValue(WageValue wageValue);
    @GetMapping("/deleteWageValue/{id}")
    void deleteWageValue(@PathVariable("id") String id);
    @PostMapping("/deleteWageValueByCondition")
    void deleteWageValueByCondition(WageValue wageValue);
}
