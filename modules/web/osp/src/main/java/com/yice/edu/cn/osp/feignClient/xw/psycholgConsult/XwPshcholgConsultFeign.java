package com.yice.edu.cn.osp.feignClient.xw.psycholgConsult;

import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw",contextId = "xwPshcholgConsultFeign",path = "/xwPshcholgConsult")
public interface XwPshcholgConsultFeign {
    @GetMapping("/findXwPshcholgConsultById/{id}")
    XwPshcholgConsult findXwPshcholgConsultById(@PathVariable("id") String id);

    @PostMapping("/saveXwPshcholgConsult")
    XwPshcholgConsult saveXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/findXwPshcholgConsultListByCondition")
    List<XwPshcholgConsult> findXwPshcholgConsultListByCondition(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/findOneXwPshcholgConsultByCondition")
    XwPshcholgConsult findOneXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/findXwPshcholgConsultCountByCondition")
    long findXwPshcholgConsultCountByCondition(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/updateXwPshcholgConsult")
    void updateXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult);

    @GetMapping("/deleteXwPshcholgConsult/{id}")
    void deleteXwPshcholgConsult(@PathVariable("id") String id);

    @PostMapping("/deleteXwPshcholgConsultByCondition")
    void deleteXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/findXwPshcholgConsultByCondition2")
    List<XwPshcholgConsult> findXwPshcholgConsultByCondition2(XwPshcholgConsult xwPshcholgConsult);

    @PostMapping("/findXwPshcholgConsultCountByCondition2")
    Long findXwPshcholgConsultCountByCondition2(XwPshcholgConsult xwPshcholgConsult);
}
