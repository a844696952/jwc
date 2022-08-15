package com.yice.edu.cn.osp.feignClient.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "kqSpecialDateFeign",path = "/kqSpecialDate")
public interface KqSpecialDateFeign {
    @GetMapping("/findKqSpecialDateById/{id}")
    KqSpecialDate findKqSpecialDateById(@PathVariable("id") String id);
    @PostMapping("/saveKqSpecialDate")
    KqSpecialDate saveKqSpecialDate(KqSpecialDate kqSpecialDate);
    @PostMapping("/findKqSpecialDateListByCondition")
    List<KqSpecialDate> findKqSpecialDateListByCondition(KqSpecialDate kqSpecialDate);
    @PostMapping("/findOneKqSpecialDateByCondition")
    KqSpecialDate findOneKqSpecialDateByCondition(KqSpecialDate kqSpecialDate);
    @PostMapping("/findKqSpecialDateCountByCondition")
    long findKqSpecialDateCountByCondition(KqSpecialDate kqSpecialDate);
    @PostMapping("/updateKqSpecialDate")
    void updateKqSpecialDate(KqSpecialDate kqSpecialDate);
    @GetMapping("/deleteKqSpecialDate/{id}")
    void deleteKqSpecialDate(@PathVariable("id") String id);
    @PostMapping("/deleteKqSpecialDateByCondition")
    void deleteKqSpecialDateByCondition(KqSpecialDate kqSpecialDate);

}
