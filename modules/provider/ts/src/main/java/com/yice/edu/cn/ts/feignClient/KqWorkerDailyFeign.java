package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "KqWorkerDailyFeign",path = "/kqWorkerDaily")
public interface KqWorkerDailyFeign {
    @GetMapping("/findKqWorkerDailyById/{id}")
    KqWorkerDaily findKqWorkerDailyById(@PathVariable("id") String id);
    @PostMapping("/saveKqWorkerDaily")
    KqWorkerDaily saveKqWorkerDaily(KqWorkerDaily kqWorkerDaily);
    @PostMapping("/findKqWorkerDailyListByCondition")
    List<KqWorkerDaily> findKqWorkerDailyListByCondition(KqWorkerDaily kqWorkerDaily);
    @PostMapping("/findOneKqWorkerDailyByCondition")
    KqWorkerDaily findOneKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily);
    @PostMapping("/findKqWorkerDailyCountByCondition")
    long findKqWorkerDailyCountByCondition(KqWorkerDaily kqWorkerDaily);
    @PostMapping("/updateKqWorkerDaily")
    void updateKqWorkerDaily(KqWorkerDaily kqWorkerDaily);
    @GetMapping("/deleteKqWorkerDaily/{id}")
    void deleteKqWorkerDaily(@PathVariable("id") String id);
    @PostMapping("/deleteKqWorkerDailyByCondition")
    void deleteKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily);
    @PostMapping("/createWorkerKqDailyRecord")
    void createWorkerKqDailyRecord();
    @GetMapping("/syncStatusDetailAndOaMsg")
    void syncStatusDetailAndOaMsg();
    @GetMapping("/reCalculateKqWorkerDaily")
    void reCalculateKqWorkerDaily();
}
