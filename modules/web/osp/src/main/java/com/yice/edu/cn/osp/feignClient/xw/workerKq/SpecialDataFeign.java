package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "specialDataFeign",path = "/specialData")
public interface SpecialDataFeign {
    @GetMapping("/findSpecialDataById/{id}")
    SpecialData findSpecialDataById(@PathVariable("id") String id);
    @PostMapping("/saveSpecialData")
    SpecialData saveSpecialData(SpecialData specialData);
    @PostMapping("/findSpecialDataListByCondition")
    List<SpecialData> findSpecialDataListByCondition(SpecialData specialData);
    @PostMapping("/findOneSpecialDataByCondition")
    SpecialData findOneSpecialDataByCondition(SpecialData specialData);
    @PostMapping("/findSpecialDataCountByCondition")
    long findSpecialDataCountByCondition(SpecialData specialData);
    @PostMapping("/updateSpecialData")
    void updateSpecialData(SpecialData specialData);
    @GetMapping("/deleteSpecialData/{id}")
    void deleteSpecialData(@PathVariable("id") String id);
    @PostMapping("/deleteSpecialDataByCondition")
    void deleteSpecialDataByCondition(SpecialData specialData);
}
