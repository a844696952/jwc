package com.yice.edu.cn.osp.feignClient.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "kqBasicRulesFeign",path = "/kqBasicRules")
public interface KqBasicRulesFeign {
    @GetMapping("/findKqBasicRulesById/{id}")
    KqBasicRules findKqBasicRulesById(@PathVariable("id") String id);
    @PostMapping("/saveKqBasicRules")
    KqBasicRules saveKqBasicRules(KqBasicRules kqBasicRules);
    @PostMapping("/findKqBasicRulesListByCondition")
    List<KqBasicRules> findKqBasicRulesListByCondition(KqBasicRules kqBasicRules);
    @PostMapping("/findOneKqBasicRulesByCondition")
    KqBasicRules findOneKqBasicRulesByCondition(KqBasicRules kqBasicRules);
    @PostMapping("/findKqBasicRulesCountByCondition")
    long findKqBasicRulesCountByCondition(KqBasicRules kqBasicRules);
    @PostMapping("/updateKqBasicRules")
    void updateKqBasicRules(KqBasicRules kqBasicRules);
    @GetMapping("/deleteKqBasicRules/{id}")
    void deleteKqBasicRules(@PathVariable("id") String id);
    @PostMapping("/deleteKqBasicRulesByCondition")
    void deleteKqBasicRulesByCondition(KqBasicRules kqBasicRules);
}
