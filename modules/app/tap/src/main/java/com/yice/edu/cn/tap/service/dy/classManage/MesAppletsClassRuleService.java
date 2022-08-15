package com.yice.edu.cn.tap.service.dy.classManage;

import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.common.util.wx.WxUtil;
import com.yice.edu.cn.tap.feignClient.dy.classManage.MesAppletsClassRuleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MesAppletsClassRuleService {
    @Autowired
    private MesAppletsClassRuleFeign mesAppletsClassRuleFeign;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public MesAppletsClassRule findMesAppletsClassRuleById(String id) {
        return mesAppletsClassRuleFeign.findMesAppletsClassRuleById(id);
    }

    public MesAppletsClassRule saveMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleFeign.saveMesAppletsClassRule(mesAppletsClassRule);
    }

    public void batchSaveMesAppletsClassRule(List<MesAppletsClassRule> mesAppletsClassRules){
        mesAppletsClassRuleFeign.batchSaveMesAppletsClassRule(mesAppletsClassRules);
    }

    public List<MesAppletsClassRule> findMesAppletsClassRuleListByCondition(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleFeign.findMesAppletsClassRuleListByCondition(mesAppletsClassRule);
    }

    public MesAppletsClassRule findOneMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleFeign.findOneMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }

    public long findMesAppletsClassRuleCountByCondition(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleFeign.findMesAppletsClassRuleCountByCondition(mesAppletsClassRule);
    }

    public void updateMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleFeign.updateMesAppletsClassRule(mesAppletsClassRule);
    }

    public void updateMesAppletsClassRuleForAll(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleFeign.updateMesAppletsClassRuleForAll(mesAppletsClassRule);
    }

    public void deleteMesAppletsClassRule(String id) {
        mesAppletsClassRuleFeign.deleteMesAppletsClassRule(id);
    }

    public void deleteMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleFeign.deleteMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public ResponseJson index(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleFeign.index(mesAppletsClassRule);
    }

    public void move(List<MesAppletsClassRule> mesAppletsClassRule) {
        mesAppletsClassRuleFeign.move(mesAppletsClassRule);
    }


}
