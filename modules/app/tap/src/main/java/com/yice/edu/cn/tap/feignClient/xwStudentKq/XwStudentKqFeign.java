package com.yice.edu.cn.tap.feignClient.xwStudentKq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author:xushu
 * @date:2019/3/8.
 * 徐庶
 */
@FeignClient(value = "xw", path = "/kqDailyStatistical")
public interface XwStudentKqFeign {
    @PostMapping("/inTimeCountByRules")
    KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData);

    @PostMapping("/findKqDailyStatisticalListByCondition")
    List<KqDailyStatistical>  findKqDailyStatisticalListByCondition(KqDailyStatistical kqDailyStatistical);

    @PostMapping("/findKqDailyStatisticalCountByCondition")
    long   findKqDailyStatisticalCountByCondition(KqDailyStatistical kqDailyStatistical);
}
