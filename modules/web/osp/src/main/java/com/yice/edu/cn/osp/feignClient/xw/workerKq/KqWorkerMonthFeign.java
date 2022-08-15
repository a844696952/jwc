package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "KqWorkerMonthFeign",path = "/kqWorkerMonth")
public interface KqWorkerMonthFeign {
    @GetMapping("/findKqWorkerMonthById/{id}")
    KqWorkerMonth findKqWorkerMonthById(@PathVariable("id") String id);
    @PostMapping("/saveKqWorkerMonth")
    KqWorkerMonth saveKqWorkerMonth(KqWorkerMonth kqWorkerMonth);
    @PostMapping("/findKqWorkerMonthListByCondition")
    List<KqWorkerMonth> findKqWorkerMonthListByCondition(KqWorkerMonth kqWorkerMonth);
    @PostMapping("/findOneKqWorkerMonthByCondition")
    KqWorkerMonth findOneKqWorkerMonthByCondition(KqWorkerMonth kqWorkerMonth);
    @PostMapping("/findKqWorkerMonthCountByCondition")
    long findKqWorkerMonthCountByCondition(KqWorkerMonth kqWorkerMonth);
    @PostMapping("/updateKqWorkerMonth")
    void updateKqWorkerMonth(KqWorkerMonth kqWorkerMonth);
    @GetMapping("/deleteKqWorkerMonth/{id}")
    void deleteKqWorkerMonth(@PathVariable("id") String id);
    @PostMapping("/deleteKqWorkerMonthByCondition")
    void deleteKqWorkerMonthByCondition(KqWorkerMonth kqWorkerMonth);
    @PostMapping("/findWorkerMonthRecordList")
    public List<KqWorkerMonth> findWorkerMonthRecordList( KqWorkerMonth kqWorkerMonth);


}
