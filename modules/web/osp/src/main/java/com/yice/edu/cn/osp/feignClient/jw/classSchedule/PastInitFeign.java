package com.yice.edu.cn.osp.feignClient.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "pastInitFeign",path = "/pastInit")
public interface PastInitFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPastInitById/{id}")
    PastInit findPastInitById(@PathVariable("id") String id);
    @PostMapping("/savePastInit")
    PastInit savePastInit(PastInit pastInit);
    @PostMapping("/batchSavePastInit")
    void batchSavePastInit(List<PastInit> pastInits);
    @PostMapping("/findPastInitListByCondition")
    List<PastInit> findPastInitListByCondition(PastInit pastInit);
    @PostMapping("/findOnePastInitByCondition")
    PastInit findOnePastInitByCondition(PastInit pastInit);
    @PostMapping("/findPastInitCountByCondition")
    long findPastInitCountByCondition(PastInit pastInit);
    @PostMapping("/updatePastInit")
    void updatePastInit(PastInit pastInit);
    @PostMapping("/updatePastInitForAll")
    void updatePastInitForAll(PastInit pastInit);
    @GetMapping("/deletePastInit/{id}")
    void deletePastInit(@PathVariable("id") String id);
    @PostMapping("/deletePastInitByCondition")
    void deletePastInitByCondition(PastInit pastInit);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
