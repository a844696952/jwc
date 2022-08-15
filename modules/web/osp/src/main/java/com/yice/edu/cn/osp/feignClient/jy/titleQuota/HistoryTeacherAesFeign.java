package com.yice.edu.cn.osp.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jy",contextId = "historyTeacherAesFeign",path = "/historyTeacherAes")
public interface HistoryTeacherAesFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findHistoryTeacherAesById/{id}")
    HistoryTeacherAes findHistoryTeacherAesById(@PathVariable("id") String id);
    @PostMapping("/saveHistoryTeacherAes")
    HistoryTeacherAes saveHistoryTeacherAes(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/batchSaveHistoryTeacherAes")
    void batchSaveHistoryTeacherAes(List<HistoryTeacherAes> historyTeacherAess);
    @PostMapping("/findHistoryTeacherAesListByCondition")
    List<HistoryTeacherAes> findHistoryTeacherAesListByCondition(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findOneHistoryTeacherAesByCondition")
    HistoryTeacherAes findOneHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findHistoryTeacherAesCountByCondition")
    long findHistoryTeacherAesCountByCondition(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/updateHistoryTeacherAes")
    void updateHistoryTeacherAes(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/updateHistoryTeacherAesForNotNull")
    void updateHistoryTeacherAesForAll(HistoryTeacherAes historyTeacherAes);
    @GetMapping("/deleteHistoryTeacherAes/{id}")
    void deleteHistoryTeacherAes(@PathVariable("id") String id);
    @PostMapping("/deleteHistoryTeacherAesByCondition")
    void deleteHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findIsExist")
    HistoryTeacherAes findIsExist(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findIsDownload")
    HistoryTeacherAes findIsDownload(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findByTeacherIdVist")
    HistoryTeacherAes findByTeacherIdVist(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findHistoryTeacherAesCountByCondition4Like")
    List<HistoryTeacherAes> findHistoryTeacherAesCountByCondition4Like(HistoryTeacherAes historyTeacherAes);
    @PostMapping("/findHistoryTeacherAesListByCondition4Like")
    List<Map<String,Object>> findHistoryTeacherAesListByCondition4Like(HistoryTeacherAes historyTeacherAes);
}
