package com.yice.edu.cn.yed.service.dy.rules;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.yed.feignClient.dy.rules.MesAppletsRuleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAppletsRuleService {
    @Autowired
    private MesAppletsRuleFeign mesAppletsRuleFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public MesAppletsRule findMesAppletsRuleById(String id) {
        return mesAppletsRuleFeign.findMesAppletsRuleById(id);
    }

    public ResponseJson saveMesAppletsRule(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.saveMesAppletsRule(mesAppletsRule);
    }

    public void batchSaveMesAppletsRule(List<MesAppletsRule> mesAppletsRules){
        mesAppletsRuleFeign.batchSaveMesAppletsRule(mesAppletsRules);
    }

    public List<MesAppletsRule> findMesAppletsRuleListByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.findMesAppletsRuleListByCondition(mesAppletsRule);
    }

    public MesAppletsRule findOneMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.findOneMesAppletsRuleByCondition(mesAppletsRule);
    }

    public long findMesAppletsRuleCountByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.findMesAppletsRuleCountByCondition(mesAppletsRule);
    }

    public ResponseJson updateMesAppletsRule(MesAppletsRule mesAppletsRule) {
       return  mesAppletsRuleFeign.updateMesAppletsRule(mesAppletsRule);
    }

    public void updateMesAppletsRuleForAll(MesAppletsRule mesAppletsRule) {
        mesAppletsRuleFeign.updateMesAppletsRuleForAll(mesAppletsRule);
    }

    public void deleteMesAppletsRule(String id) {
        mesAppletsRuleFeign.deleteMesAppletsRule(id);
    }

    public void deleteMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule) {
        mesAppletsRuleFeign.deleteMesAppletsRuleByCondition(mesAppletsRule);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public ResponseJson updateMove(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.updateMove(mesAppletsRule);
    }

    public List<MesAppletsRule> findMesAppletsRulesAndStatisticDataByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleFeign.findMesAppletsRulesAndStatisticDataByCondition(mesAppletsRule);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
