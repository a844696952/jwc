package com.yice.edu.cn.bmp.feignClient.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/kqOriginalData")
public interface KqOriginalDataFeign {
    @GetMapping("/findKqOriginalDataById/{id}")
    KqOriginalData findKqOriginalDataById(@PathVariable("id") String id);
    @PostMapping("/saveKqOriginalData")
    KqOriginalData saveKqOriginalData(KqOriginalData kqOriginalData);
    @PostMapping("/findKqOriginalDataListByCondition")
    List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData);
    @PostMapping("/findOneKqOriginalDataByCondition")
    KqOriginalData findOneKqOriginalDataByCondition(KqOriginalData kqOriginalData);
    @PostMapping("/findKqOriginalDataCountByCondition")
    long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData);
    @PostMapping("/updateKqOriginalData")
    void updateKqOriginalData(KqOriginalData kqOriginalData);
    @GetMapping("/deleteKqOriginalData/{id}")
    void deleteKqOriginalData(@PathVariable("id") String id);
    @PostMapping("/deleteKqOriginalDataByCondition")
    void deleteKqOriginalDataByCondition(KqOriginalData kqOriginalData);
    @PostMapping("/judgePunchStatusByrules")
    KqDailyStatistical judgePunchStatusByrules(KqOriginalData kqOriginalData);
    @PostMapping("/dayBasicRecords")
    KqDailyStatistical dayBasicRecords(KqOriginalData kqOriginalData);
    @PostMapping("/inTimeCountByRules")
    KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData);
    @PostMapping("/statusOfKqOriginalData")
    boolean statusOfKqOriginalData(KqOriginalData kqOriginalData);
}
