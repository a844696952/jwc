package com.yice.edu.cn.tap.service.dy.classManage;


import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.tap.feignClient.dy.classManage.MesAppletsRuleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MesAppletsRuleService {
    @Autowired
    private MesAppletsRuleFeign mesAppletsRuleFeign;


    public MesAppletsRule findMesAppletsRuleById(String id) {
        return mesAppletsRuleFeign.findMesAppletsRuleById(id);
    }




}
