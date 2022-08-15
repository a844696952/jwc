package com.yice.edu.cn.tap.feignClient.dy.classManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",path = "/mesAppletsClassRule")
public interface MesAppletsClassRuleFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findMesAppletsClassRuleById/{id}")
    MesAppletsClassRule findMesAppletsClassRuleById(@PathVariable("id") String id);
    @PostMapping("/saveMesAppletsClassRule")
    MesAppletsClassRule saveMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule);
    @PostMapping("/batchSaveMesAppletsClassRule")
    void batchSaveMesAppletsClassRule(List<MesAppletsClassRule> mesAppletsClassRules);
    @PostMapping("/findMesAppletsClassRuleListByCondition")
    List<MesAppletsClassRule> findMesAppletsClassRuleListByCondition(MesAppletsClassRule mesAppletsClassRule);
    @PostMapping("/findOneMesAppletsClassRuleByCondition")
    MesAppletsClassRule findOneMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule);
    @PostMapping("/findMesAppletsClassRuleCountByCondition")
    long findMesAppletsClassRuleCountByCondition(MesAppletsClassRule mesAppletsClassRule);
    @PostMapping("/updateMesAppletsClassRule")
    void updateMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule);
    @PostMapping("/updateMesAppletsClassRuleForAll")
    void updateMesAppletsClassRuleForAll(MesAppletsClassRule mesAppletsClassRule);
    @GetMapping("/deleteMesAppletsClassRule/{id}")
    void deleteMesAppletsClassRule(@PathVariable("id") String id);
    @PostMapping("/deleteMesAppletsClassRuleByCondition")
    void deleteMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/index")
    ResponseJson index(MesAppletsClassRule mesAppletsClassRule);

    @PostMapping("/move")
    void move(List<MesAppletsClassRule> mesAppletsClassRule);
}
