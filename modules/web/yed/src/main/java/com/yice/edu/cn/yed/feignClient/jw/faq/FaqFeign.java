package com.yice.edu.cn.yed.feignClient.jw.faq;

import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "faqFeign",path = "/faq")
public interface FaqFeign {
    @GetMapping("/findFaqById/{id}")
    Faq findFaqById(@PathVariable("id") String id);
    @PostMapping("/saveFaq")
    Faq saveFaq(Faq faq);
    @PostMapping("/findFaqListByCondition")
    List<Faq> findFaqListByCondition(Faq faq);
    @PostMapping("/findOneFaqByCondition")
    Faq findOneFaqByCondition(Faq faq);
    @PostMapping("/findFaqCountByCondition")
    long findFaqCountByCondition(Faq faq);
    @PostMapping("/updateFaq")
    void updateFaq(Faq faq);
    @GetMapping("/deleteFaq/{id}")
    void deleteFaq(@PathVariable("id") String id);
    @PostMapping("/deleteFaqByCondition")
    void deleteFaqByCondition(Faq faq);
}
