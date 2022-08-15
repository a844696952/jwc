package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw", contextId = "kqDailyStatusDetailFeign", path = "/kqDailyStatusDetail")
public interface KqDailyStatusDetailFeign {
    @GetMapping("/findKqDailyStatusDetailById/{id}")
    KqDailyStatusDetail findKqDailyStatusDetailById(@PathVariable("id") String id);

    @PostMapping("/saveKqDailyStatusDetail")
    KqDailyStatusDetail saveKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail);

    @PostMapping("/findKqDailyStatusDetailListByCondition")
    List<KqDailyStatusDetail> findKqDailyStatusDetailListByCondition(KqDailyStatusDetail kqDailyStatusDetail);

    @PostMapping("/findOneKqDailyStatusDetailByCondition")
    KqDailyStatusDetail findOneKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail);

    @PostMapping("/findKqDailyStatusDetailCountByCondition")
    long findKqDailyStatusDetailCountByCondition(KqDailyStatusDetail kqDailyStatusDetail);

    @PostMapping("/updateKqDailyStatusDetail")
    void updateKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail);

    @GetMapping("/deleteKqDailyStatusDetail/{id}")
    void deleteKqDailyStatusDetail(@PathVariable("id") String id);

    @PostMapping("/deleteKqDailyStatusDetailByCondition")
    void deleteKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail);

    @GetMapping("/saveOaKqDailyStatusDetail")
    void saveOaKqDailyStatusDetail();
}
