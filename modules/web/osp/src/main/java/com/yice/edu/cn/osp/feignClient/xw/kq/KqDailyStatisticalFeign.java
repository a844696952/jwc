package com.yice.edu.cn.osp.feignClient.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqMonthStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw",contextId = "kqDailyStatisticalFeign",path = "/kqDailyStatistical")
public interface KqDailyStatisticalFeign {
    @GetMapping("/findKqDailyStatisticalById/{id}")
    KqDailyStatistical findKqDailyStatisticalById(@PathVariable("id") String id);

    @PostMapping("/saveKqDailyStatistical")
    KqDailyStatistical saveKqDailyStatistical(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findKqDailyStatisticalListByCondition")
    List<KqDailyStatistical> findKqDailyStatisticalListByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findOneKqDailyStatisticalByCondition")
    KqDailyStatistical findOneKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findKqDailyStatisticalCountByCondition")
    long findKqDailyStatisticalCountByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/updateKqDailyStatistical")
    void updateKqDailyStatistical(KqDailyStatistical kqDailyStatistical);

    @GetMapping("/deleteKqDailyStatistical/{id}")
    void deleteKqDailyStatistical(@PathVariable("id") String id);

    @PostMapping("/deleteKqDailyStatisticalByCondition")
    void deleteKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findKqMonthStatisticalListByCondition")
    public List<KqMonthStatistical> findKqMonthStatisticalListByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findKqMonthStatisticalListAll")
    public List<KqMonthStatistical> findKqMonthStatisticalListAll(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/inTimeCountByRules")
    public KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData);

   /* @PostMapping("/inTimeCountByRulesForClass")
    public List<KqDailyStatistical> inTimeCountByRulesForClass(KqOriginalData kqOriginalData);
*/
}
