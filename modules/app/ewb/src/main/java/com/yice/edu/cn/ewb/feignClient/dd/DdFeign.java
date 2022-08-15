package com.yice.edu.cn.ewb.feignClient.dd;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "ddFeign",path = "/dd")
public interface DdFeign {
    @GetMapping("/findDdById/{id}")
    Dd findDdById(@PathVariable("id") String id);
    @PostMapping("/saveDd")
    Dd saveDd(Dd dd);
    @PostMapping("/findDdListByCondition")
    List<Dd> findDdListByCondition(Dd dd);
    @PostMapping("/findDdCountByCondition")
    long findDdCountByCondition(Dd dd);
    @PostMapping("/updateDd")
    void updateDd(Dd dd);
    @GetMapping("/deleteDd/{id}")
    void deleteDd(@PathVariable("id") String id);
}
