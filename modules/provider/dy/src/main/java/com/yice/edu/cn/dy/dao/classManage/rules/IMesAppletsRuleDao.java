package com.yice.edu.cn.dy.dao.classManage.rules;



import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesAppletsRuleDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAppletsRule> findMesAppletsRuleListByCondition(MesAppletsRule mesAppletsRule);

    long findMesAppletsRuleCountByCondition(MesAppletsRule mesAppletsRule);

    MesAppletsRule findOneMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule);

    MesAppletsRule findMesAppletsRuleById(@Param("id") String id);

    void saveMesAppletsRule(MesAppletsRule mesAppletsRule);

    void updateMesAppletsRule(MesAppletsRule mesAppletsRule);

    void updateMesAppletsRuleForAll(MesAppletsRule mesAppletsRule);

    void deleteMesAppletsRule(@Param("id") String id);

    void deleteMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule);

    void batchSaveMesAppletsRule(List<MesAppletsRule> mesAppletsRules);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    List<MesAppletsRule> findGroupByParentId(MesAppletsRule mesAppletsRule);


    List<MesAppletsRule> findNotInId(MesAppletsRule mesAppletsRule);

    List<MesAppletsRule> findIndex(Query query);


    List<MesAppletsRule> findIndexNotGroupBy(Query query);

    List<MesAppletsRule> selectMesAppletsRulesByCondition(MesAppletsRule mesAppletsRule);

    List<MesAppletsRule> selectMesAppletsRulesByParentId(@Param("parentId") String parentId,@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    Long selectDyTotalClasses();

    Long selectReferenceClassNumByRuleId(@Param("ruleId") String ruleId);

    long selectClickTimesByRuleId(@Param("ruleId") String ruleId);
}
