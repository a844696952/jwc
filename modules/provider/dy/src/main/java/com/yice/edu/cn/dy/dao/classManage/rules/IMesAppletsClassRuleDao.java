package com.yice.edu.cn.dy.dao.classManage.rules;

import java.util.List;

import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMesAppletsClassRuleDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAppletsClassRule> findMesAppletsClassRuleListByCondition(MesAppletsClassRule mesAppletsClassRule);

    long findMesAppletsClassRuleCountByCondition(MesAppletsClassRule mesAppletsClassRule);

    MesAppletsClassRule findOneMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule);

    MesAppletsClassRule findMesAppletsClassRuleById(@Param("id") String id);

    void saveMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule);

    void updateMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule);

    void updateMesAppletsClassRuleForAll(MesAppletsClassRule mesAppletsClassRule);

    void deleteMesAppletsClassRule(@Param("id") String id);

    void deleteMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule);

    void batchSaveMesAppletsClassRule(List<MesAppletsClassRule> mesAppletsClassRules);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
