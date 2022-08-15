package com.yice.edu.cn.ecc.feignClient.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmLogFeign",path = "/dmLog")
public interface DmLogFeign {
    @GetMapping("/findDmLogById/{id}")
    DmLog findDmLogById(@PathVariable("id") String id);


    @PostMapping("/findOneDmLogByCondition")
    DmLog findOneDmLogByCondition(DmLog dmLog);


    @PostMapping("/findDmLogListByCondition")
    List<DmLog> findDmLogListByCondition(DmLog dmLog);

    @PostMapping("/findDmLogCountByCondition")
    long findDmLogCountByCondition(DmLog dmLog);





}
