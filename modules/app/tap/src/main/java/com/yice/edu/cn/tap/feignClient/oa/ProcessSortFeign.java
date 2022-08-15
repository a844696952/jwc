package com.yice.edu.cn.tap.feignClient.oa;

import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId = "processSortFeign",path = "/processSort")
public interface ProcessSortFeign {
    @GetMapping("/findProcessSortList/{schoolId}")
    List<ProcessSort> getProcessSortList(@PathVariable("schoolId") String schoolId);
}
