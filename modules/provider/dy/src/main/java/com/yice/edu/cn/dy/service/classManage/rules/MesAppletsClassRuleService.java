package com.yice.edu.cn.dy.service.classManage.rules;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.Query;
import com.yice.edu.cn.dy.dao.classManage.rules.IMesAppletsClassRuleDao;
import com.yice.edu.cn.dy.dao.classManage.rules.IMesAppletsRuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MesAppletsClassRuleService {
    @Autowired
    private IMesAppletsClassRuleDao mesAppletsClassRuleDao;

    @Autowired
    private IMesAppletsRuleDao mesAppletsRuleDao;

    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesAppletsClassRule findMesAppletsClassRuleById(String id) {
        return mesAppletsClassRuleDao.findMesAppletsClassRuleById(id);
    }



    @Transactional(rollbackFor = Exception.class)
    public void saveMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule) {
        MesAppletsClassRule find = new MesAppletsClassRule();
        find.setRuleId(mesAppletsClassRule.getRuleId());
        find.setClassId(mesAppletsClassRule.getClassId());
        if(mesAppletsClassRuleDao.findMesAppletsClassRuleCountByCondition(find) == 0){
            mesAppletsClassRule.setId(sequenceId.nextId());
            mesAppletsClassRuleDao.saveMesAppletsClassRule(mesAppletsClassRule);
        }

    }



    @Transactional
    public List<MesAppletsClassRule> findMesAppletsClassRuleListByCondition(MesAppletsClassRule mesAppletsClassRule) {
        init(mesAppletsClassRule);
        return mesAppletsClassRuleDao.findMesAppletsClassRuleListByCondition(mesAppletsClassRule);
    }
    @Transactional(readOnly = true)
    public MesAppletsClassRule findOneMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleDao.findOneMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }
    @Transactional(readOnly = true)
    public long findMesAppletsClassRuleCountByCondition(MesAppletsClassRule mesAppletsClassRule) {
        return mesAppletsClassRuleDao.findMesAppletsClassRuleCountByCondition(mesAppletsClassRule);
    }
    @Transactional
    public void updateMesAppletsClassRule(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleDao.updateMesAppletsClassRule(mesAppletsClassRule);
    }
    @Transactional
    public void updateMesAppletsClassRuleForAll(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleDao.updateMesAppletsClassRuleForAll(mesAppletsClassRule);
    }
    @Transactional
    public void deleteMesAppletsClassRule(String id) {
        mesAppletsClassRuleDao.deleteMesAppletsClassRule(id);
    }
    @Transactional
    public void deleteMesAppletsClassRuleByCondition(MesAppletsClassRule mesAppletsClassRule) {
        mesAppletsClassRuleDao.deleteMesAppletsClassRuleByCondition(mesAppletsClassRule);
    }
    @Transactional
    public void batchSaveMesAppletsClassRule(List<MesAppletsClassRule> mesAppletsClassRules){
        mesAppletsClassRules.forEach(mesAppletsClassRule -> mesAppletsClassRule.setId(sequenceId.nextId()));
        mesAppletsClassRuleDao.batchSaveMesAppletsClassRule(mesAppletsClassRules);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson index(MesAppletsClassRule mesAppletsClassRule) {
        init(mesAppletsClassRule);
        List<MesAppletsClassRule> list = mesAppletsClassRuleDao.findMesAppletsClassRuleListByCondition(mesAppletsClassRule);
        //在查推荐标签的应用
        List<MesAppletsRule> data = new ArrayList<>();
        Query query  = new Query();
        query.setMesAppletsClassRules(list);
        query.setTagType(mesAppletsClassRule.getTagType());
        List<MesAppletsRule> parents = mesAppletsRuleDao.findIndex(query);
        parents.forEach(p ->{
            //说明推荐标签有一级分组
            MesAppletsRule rule = mesAppletsRuleDao.findMesAppletsRuleById(p.getParentId());
            query.setParentId(rule.getId());
            rule.setChildren(mesAppletsRuleDao.findIndexNotGroupBy(query));
            data.add(rule);
        });

        return new ResponseJson(list,data);
    }

    private void init(MesAppletsClassRule mesAppletsClassRule){
        MesAppletsClassRule find = new MesAppletsClassRule();
        find.setSchoolId(mesAppletsClassRule.getSchoolId());
        find.setClassId(mesAppletsClassRule.getClassId());
        List<MesAppletsClassRule> list = mesAppletsClassRuleDao.findMesAppletsClassRuleListByCondition(find);
        if(CollUtil.isEmpty(list)){
            //表示需要初始化
            MesAppletsRule appletsRule = new MesAppletsRule();
            appletsRule.setLevel(2);
            appletsRule.setAppType(0);
            List<MesAppletsRule> mesAppletsRuleList = mesAppletsRuleDao.findMesAppletsRuleListByCondition(appletsRule);
            MesAppletsClassRule save = new MesAppletsClassRule();
            mesAppletsRuleList.forEach(m ->{
                save.setId(sequenceId.nextId());
                save.setClassId(mesAppletsClassRule.getClassId());
                save.setRuleId(m.getId());
                save.setScore(m.getScore());
                save.setSortNumber(m.getSortNumber());
                save.setSchoolId(mesAppletsClassRule.getSchoolId());
                mesAppletsClassRuleDao.saveMesAppletsClassRule(save);
            });
        }
    }

    /**
    * 前端移动小程序标签请求接口
    * */
    public void move(List<MesAppletsClassRule> mesAppletsClassRule) {
         //首先删库，
        MesAppletsClassRule delete = new MesAppletsClassRule();
        delete.setClassId(mesAppletsClassRule.get(0).getClassId());
        delete.setSchoolId(mesAppletsClassRule.get(0).getSchoolId());
        mesAppletsClassRuleDao.deleteMesAppletsClassRuleByCondition(delete);
        //然后把传入进来的顺序自动排序
        for (int i = 0; i < mesAppletsClassRule.size(); i++) {
            mesAppletsClassRule.get(i).setSortNumber(i+1);
            mesAppletsClassRuleDao.updateMesAppletsClassRule(mesAppletsClassRule.get(i));
        }

    }
}
