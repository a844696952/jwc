package com.yice.edu.cn.osp.feignClient.oa.processCopy;

import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="oa",contextId = "processCopyFeign",path = "/processCopy")
public interface ProcessCopyFeign {
    @GetMapping("/findProcessCopyById/{id}")
    ProcessCopy findProcessCopyById(@PathVariable("id") String id);
    @PostMapping("/saveProcessCopy")
    ProcessCopy saveProcessCopy(ProcessCopy processCopy);
    @PostMapping("/findProcessCopyListByCondition")
    List<ProcessCopyVo> findProcessCopyListByCondition(ProcessCopy processCopy);
    @PostMapping("/findOneProcessCopyByCondition")
    ProcessCopy findOneProcessCopyByCondition(ProcessCopy processCopy);
    @PostMapping("/findProcessCopyCountByCondition")
    long findProcessCopyCountByCondition(ProcessCopy processCopy);
    @PostMapping("/updateProcessCopy")
    void updateProcessCopy(ProcessCopy processCopy);
    @GetMapping("/deleteProcessCopy/{id}")
    void deleteProcessCopy(@PathVariable("id") String id);
    @PostMapping("/deleteProcessCopyByCondition")
    void deleteProcessCopyByCondition(ProcessCopy processCopy);
    @PostMapping("/batchLookProcessCopyByIds")
    void batchLookProcessCopyByIds(String[] ids);
}
