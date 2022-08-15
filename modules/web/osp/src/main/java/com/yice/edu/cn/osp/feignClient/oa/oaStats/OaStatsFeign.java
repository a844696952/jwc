package com.yice.edu.cn.osp.feignClient.oa.oaStats;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "oa",contextId = "oaStatsFeign",path = "/oaStats")
public interface OaStatsFeign {
    @PostMapping("/findTotalStats/{schoolId}")
    ResponseJson findTotalStats(@PathVariable("schoolId") String schoolId, OaStats oaStats);
    @PostMapping("/findStatsDetail/{needCount}")
    ResponseJson findStatsDetail(ProcessBusinessData processBusinessData,@PathVariable("needCount") boolean needCount);

    @PostMapping("/findProcessesByRangeTime/{id}")
    ResponseJson findProcessesByRangeTime(@PathVariable("id") String id, @RequestBody Pager pager);
    @PostMapping("/findTotalMoney")
    ResponseJson findTotalMoney(ProcessBusinessData processBusinessData);
}
