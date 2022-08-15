package com.yice.edu.cn.tap.feignClient.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId="myApproveFeign",path = "/myApprove")
public interface MyApproveFeign {
    @PostMapping("/findWaitApproveData/{id}")
    List<ProcessBusinessData> findWaitApproveData(@PathVariable("id") String id, ProcessBusinessData processBusinessData);
    @PostMapping("/findWaitApproveCount/{id}")
    long findWaitApproveCount(@PathVariable("id") String id, ProcessBusinessData processBusinessData);
    @PostMapping("/findHasApproveData/{id}")
    List<ProcessBusinessData> findHasApproveData(@PathVariable("id") String id, ProcessBusinessData processBusinessData);
    @PostMapping("/findHasApproveCount/{id}")
    long findHasApproveCount(@PathVariable("id") String id, ProcessBusinessData processBusinessData);

    @PostMapping("/completeApprove/{processBusinessId}")
    ResponseJson completeApprove(OaPeople oaPeople, @PathVariable("processBusinessId") String processBusinessId);



}
