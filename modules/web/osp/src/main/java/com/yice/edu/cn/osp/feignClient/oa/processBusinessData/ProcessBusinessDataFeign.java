package com.yice.edu.cn.osp.feignClient.oa.processBusinessData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId = "processBusinessDataFeign",path = "/processBusinessData")
public interface ProcessBusinessDataFeign {
    @GetMapping("/findProcessBusinessDataById/{id}")
    ProcessBusinessData findProcessBusinessDataById(@PathVariable("id") String id);
    @PostMapping("/saveProcessBusinessData")
    ProcessBusinessData saveProcessBusinessData(ProcessBusinessData processBusinessData);
    @PostMapping("/findProcessBusinessDataListByCondition")
    List<ProcessBusinessData> findProcessBusinessDataListByCondition(ProcessBusinessData processBusinessData);
    @PostMapping("/findOneProcessBusinessDataByCondition")
    ProcessBusinessData findOneProcessBusinessDataByCondition(ProcessBusinessData processBusinessData);
    @PostMapping("/findProcessBusinessDataCountByCondition")
    long findProcessBusinessDataCountByCondition(ProcessBusinessData processBusinessData);
    @PostMapping("/updateProcessBusinessData")
    void updateProcessBusinessData(ProcessBusinessData processBusinessData);
    @GetMapping("/deleteProcessBusinessData/{id}")
    void deleteProcessBusinessData(@PathVariable("id") String id);
    @PostMapping("/deleteProcessBusinessDataByCondition")
    void deleteProcessBusinessDataByCondition(ProcessBusinessData processBusinessData);
    @GetMapping("/clearLeave/{id}")
    ResponseJson clearLeave(@PathVariable("id") String id);
    @PostMapping("/calculateLeaveStatis")
    ResponseJson calculateLeaveStatis(ProcessBusinessData processBusinessData);
    @PostMapping("/urge/{id}")
    ResponseJson sendMessageByProcessDataById(@PathVariable("id") String id);
    @PostMapping("/cancel/{id}")
    ResponseJson cancelFlow(@PathVariable("id") String id, ProcessBusinessData processBusinessData);
    @PostMapping("/findProcessBusinessDataListByConditionForKq")
    List<ProcessBusinessData> findProcessBusinessDataListByConditionForKq(ProcessBusinessData processBusinessData);
}
