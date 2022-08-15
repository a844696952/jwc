package com.yice.edu.cn.osp.feignClient.oa.processSort;

import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId = "processSortFeign",path = "/processSort")
public interface ProcessSortFeign {
    @GetMapping("/findProcessSortById/{id}")
    ProcessSort findProcessSortById(@PathVariable("id") String id);
    @PostMapping("/saveProcessSort")
    ProcessSort saveProcessSort(ProcessSort processSort);
    @PostMapping("/findProcessSortListByCondition")
    List<ProcessSort> findProcessSortListByCondition(ProcessSort processSort);
    @PostMapping("/findOneProcessSortByCondition")
    ProcessSort findOneProcessSortByCondition(ProcessSort processSort);
    @PostMapping("/findProcessSortCountByCondition")
    long findProcessSortCountByCondition(ProcessSort processSort);
    @PostMapping("/updateProcessSort")
    void updateProcessSort(ProcessSort processSort);
    @GetMapping("/deleteProcessSort/{id}")
    void deleteProcessSort(@PathVariable("id") String id);
    @PostMapping("/deleteProcessSortByCondition")
    void deleteProcessSortByCondition(ProcessSort processSort);
    @GetMapping("/findProcessSortList/{schoolId}")
    List<ProcessSort> getProcessSortList(@PathVariable("schoolId") String schoolId);
}
