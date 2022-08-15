package com.yice.edu.cn.osp.feignClient.oa.processLib;

import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId = "processLibFeign",path = "/processLib")
public interface ProcessLibFeign {
    @GetMapping("/findProcessLibById/{id}")
    ProcessLib findProcessLibById(@PathVariable("id") String id);
    @PostMapping("/saveProcessLib")
    ProcessLib saveProcessLib(ProcessLib processLib);
    @PostMapping("/findProcessLibListByCondition")
    List<ProcessLib> findProcessLibListByCondition(ProcessLib processLib);
    @PostMapping("/findOneProcessLibByCondition")
    ProcessLib findOneProcessLibByCondition(ProcessLib processLib);
    @PostMapping("/findProcessLibCountByCondition")
    long findProcessLibCountByCondition(ProcessLib processLib);
    @PostMapping("/updateProcessLib")
    void updateProcessLib(ProcessLib processLib);
    @GetMapping("/deleteProcessLib/{id}")
    void deleteProcessLib(@PathVariable("id") String id);
    @PostMapping("/deleteProcessLibByCondition")
    void deleteProcessLibByCondition(ProcessLib processLib);
}
