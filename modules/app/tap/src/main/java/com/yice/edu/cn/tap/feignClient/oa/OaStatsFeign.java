package com.yice.edu.cn.tap.feignClient.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "oa",contextId="oaStatsFeign",path = "/oaStats")
public interface OaStatsFeign {
    @PostMapping("/findTotalStats/{schoolId}")
    ResponseJson findTotalStats(@PathVariable("schoolId") String schoolId, OaStats os);
    @PostMapping("/findStatsDetail/{needCount}")
    ResponseJson findStatsDetail(ProcessBusinessData processBusinessData, @PathVariable("needCount") boolean needCount);
    @PostMapping("/findTotalMoney")
    ResponseJson findTotalMoney(ProcessBusinessData processBusinessData);
}
