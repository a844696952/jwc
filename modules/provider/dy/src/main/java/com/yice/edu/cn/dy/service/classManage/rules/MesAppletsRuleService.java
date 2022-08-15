package com.yice.edu.cn.dy.service.classManage.rules;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsClassRule;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.dy.dao.classManage.rules.IMesAppletsClassRuleDao;
import com.yice.edu.cn.dy.dao.classManage.rules.IMesAppletsRuleDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MesAppletsRuleService {
    @Autowired
    private IMesAppletsRuleDao mesAppletsRuleDao;

    @Autowired
    private IMesAppletsClassRuleDao mesAppletsClassRuleDao;

    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesAppletsRule findMesAppletsRuleById(String id) {
        return mesAppletsRuleDao.findMesAppletsRuleById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseJson saveMesAppletsRule(MesAppletsRule mesAppletsRule) {
        mesAppletsRule.setId(sequenceId.nextId());
        //要判断加入的制度是排序号
        MesAppletsRule find = new MesAppletsRule();
        if(StringUtils.isNotEmpty(mesAppletsRule.getParentId())){
            find.setLevel(2);
            find.setParentId(mesAppletsRule.getParentId());
            //判断分值 如果是表扬默认 1分  待改进 默认-1
            mesAppletsRule.setScore(Optional.ofNullable(mesAppletsRule.getTagType()).
                    isPresent() ? mesAppletsRule.getTagType() == 0 ? -1:1:null);
        }else{
            find.setLevel(1);
        }
        //判断是否重名
        List<MesAppletsRule> list = mesAppletsRuleDao.findMesAppletsRuleListByCondition(find);
        for (MesAppletsRule mes:list
             ) {
            if(ObjectUtil.equal(mes.getName(),mesAppletsRule.getName())){
                return new ResponseJson(false,"制度名称不能重复");
            }
        }
        mesAppletsRule.setSortNumber(list.size()+1);
        mesAppletsRuleDao.saveMesAppletsRule(mesAppletsRule);
        return new ResponseJson();
    }


    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<MesAppletsRule> findMesAppletsRuleListByCondition(MesAppletsRule mesAppletsRule) {
        MesAppletsRule find = new MesAppletsRule();
        if(ObjectUtil.isNotNull(mesAppletsRule.getAppType())){
            //说明有了切换默认和推荐的选项,首先分组根据parent_id
            List<MesAppletsRule> groups = mesAppletsRuleDao.findGroupByParentId(mesAppletsRule);
            List<MesAppletsRule> result = new ArrayList<>();
            if(CollUtil.isNotEmpty(groups)){
                groups.forEach(x->{
                    MesAppletsRule rule = findMesAppletsRuleById(x.getParentId());
                    find.setParentId(x.getParentId());
                    find.setAppType(mesAppletsRule.getAppType());
                    rule.setChildren(mesAppletsRuleDao.findMesAppletsRuleListByCondition(find));
                    result.add(rule);
                });
            }
            return result;
        }else{
            List<MesAppletsRule> list = mesAppletsRuleDao.findMesAppletsRuleListByCondition(mesAppletsRule);
            if(CollUtil.isNotEmpty(list)){
                //说明是外层一级制度,查子类
                list.forEach(x->{
                    find.setParentId(x.getId());
                    x.setChildren(mesAppletsRuleDao.findMesAppletsRuleListByCondition(find));
                });
            }
            return list;
        }
    }


    @Transactional(readOnly = true)
    public MesAppletsRule findOneMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleDao.findOneMesAppletsRuleByCondition(mesAppletsRule);
    }
    @Transactional(readOnly = true)
    public long findMesAppletsRuleCountByCondition(MesAppletsRule mesAppletsRule) {
        return mesAppletsRuleDao.findMesAppletsRuleCountByCondition(mesAppletsRule);
    }



    @Transactional(rollbackFor = Exception.class)
    public ResponseJson updateMesAppletsRule(MesAppletsRule mesAppletsRule) {
        MesAppletsRule find = new MesAppletsRule();
        find.setId(mesAppletsRule.getId());
        //前端分值的请求判断,分值接口不用判断制度名称
        if(ObjectUtil.isNotNull(mesAppletsRule.getScore())){
            MesAppletsRule rule = findMesAppletsRuleById(mesAppletsRule.getId());
            rule.setScore(mesAppletsRule.getScore());
            mesAppletsRuleDao.updateMesAppletsRule(rule);
        }else{
            if(StringUtils.isEmpty(mesAppletsRule.getParentId())){
                find.setLevel(1);
            }else{
                find.setLevel(2);
            }
            //查询去重判断
            List<MesAppletsRule> list = mesAppletsRuleDao.findNotInId(find);
            for (MesAppletsRule mes:list
            ) {
                if(ObjectUtil.equal(mes.getName(),mesAppletsRule.getName())){
                    return new ResponseJson(false,"制度名称不能重复");
                }
            }
            mesAppletsRuleDao.updateMesAppletsRule(mesAppletsRule);
        }
        return new ResponseJson();
    }



    @Transactional
    public void updateMesAppletsRuleForAll(MesAppletsRule mesAppletsRule) {
        mesAppletsRuleDao.updateMesAppletsRuleForAll(mesAppletsRule);
    }



    @Transactional(rollbackFor = Exception.class)
    public void deleteMesAppletsRule(String id) {
        MesAppletsRule rule = findMesAppletsRuleById(id);
        MesAppletsRule find = new MesAppletsRule();
        MesAppletsClassRule delete = new MesAppletsClassRule();
        if(rule.getLevel() == 1){
            //删除的是一级，所以首先把该旗下的二级标签删掉
            find.setParentId(rule.getId());
            List<MesAppletsRule> subList = mesAppletsRuleDao.findMesAppletsRuleListByCondition(find);
            subList.forEach(sub -> {
                mesAppletsRuleDao.deleteMesAppletsRule(sub.getId());
                //这里为班级规章所准备，删掉之后还要删除班规的对应数据
                delete.setRuleId(sub.getId());
                mesAppletsClassRuleDao.deleteMesAppletsClassRuleByCondition(delete);
            });

            //删掉之后对应的该对象之后的数据都要移动
            find.setParentId(null);
            find.setLevel(1);
        }else{
            //二级标签的删除，先删除备份表班规的数据,同上预留
            delete.setRuleId(id);
            mesAppletsClassRuleDao.deleteMesAppletsClassRuleByCondition(delete);

            //在设置查询移动的list条件
            find.setLevel(2);
            find.setParentId(rule.getParentId());
        }
        List<MesAppletsRule> list = mesAppletsRuleDao.findMesAppletsRuleListByCondition(find);
        list.stream().filter(x -> x.getSortNumber()>rule.getSortNumber()).forEach(c ->{
            c.setSortNumber(c.getSortNumber()-1);
            mesAppletsRuleDao.updateMesAppletsRule(c);
        });
        mesAppletsRuleDao.deleteMesAppletsRule(id);
    }




    @Transactional
    public void deleteMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule) {
        mesAppletsRuleDao.deleteMesAppletsRuleByCondition(mesAppletsRule);
    }
    @Transactional
    public void batchSaveMesAppletsRule(List<MesAppletsRule> mesAppletsRules){
        mesAppletsRules.forEach(mesAppletsRule -> mesAppletsRule.setId(sequenceId.nextId()));
        mesAppletsRuleDao.batchSaveMesAppletsRule(mesAppletsRules);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * 移动接口
     * */
    public ResponseJson updateMove(MesAppletsRule mesAppletsRule) {
        MesAppletsRule rule = findMesAppletsRuleById(mesAppletsRule.getId());
        MesAppletsRule find = new MesAppletsRule();
        find.setParentId(rule.getParentId());
        find.setLevel(rule.getLevel());
        if(ObjectUtil.equal("1",mesAppletsRule.getMoveStatus())){
            //向上移动 把当前的上一条查出来
            find.setSortNumber(rule.getSortNumber()-1);
        }else if (ObjectUtil.equal("2",mesAppletsRule.getMoveStatus())){
            find.setSortNumber(rule.getSortNumber()+1);
        }else{
            return new ResponseJson(false,"错误参数");
        }
        List<MesAppletsRule> list = mesAppletsRuleDao.findMesAppletsRuleListByCondition(find);
        if(CollUtil.isNotEmpty(list) && list.size()>1){
            return new ResponseJson(false,"数据库数据紊乱");
        }
        //进行移动逻辑
        int findSort = list.get(0).getSortNumber();
        list.get(0).setSortNumber(rule.getSortNumber());
        rule.setSortNumber(findSort);
        mesAppletsRuleDao.updateMesAppletsRule(rule);
        mesAppletsRuleDao.updateMesAppletsRule(list.get(0));
        return new ResponseJson("移动成功");
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public List<MesAppletsRule> findMesAppletsRulesAndStatisticDataByCondition(MesAppletsRule mesAppletsRule) {
        if(ObjectUtil.isNotNull(mesAppletsRule.getBeginTime())){
            mesAppletsRule.setBeginTime(mesAppletsRule.getBeginTime()+" 00:00:00");
            mesAppletsRule.setEndTime(mesAppletsRule.getEndTime()+" 23:59:59");
        }
        long totalClass = mesAppletsRuleDao.selectDyTotalClasses();
        List<MesAppletsRule> firstMesAppletsRules = mesAppletsRuleDao.selectMesAppletsRulesByCondition(mesAppletsRule);
        firstMesAppletsRules.forEach(m->{
            List<MesAppletsRule> secondMesAppletsRules = mesAppletsRuleDao.selectMesAppletsRulesByParentId(m.getId(),mesAppletsRule.getBeginTime(),mesAppletsRule.getEndTime());
            if(CollUtil.isNotEmpty(secondMesAppletsRules)){
                secondMesAppletsRules.forEach(s->{
                    s.setTotalClass(totalClass);
                    long referenceClassNum = mesAppletsRuleDao.selectReferenceClassNumByRuleId(s.getId());
                    long clickTimes = mesAppletsRuleDao.selectClickTimesByRuleId(s.getId());
                    s.setReferenceClassNum(referenceClassNum);
                    s.setClickTimes(clickTimes);
                    m.setClickTimes(clickTimes+m.getClickTimes());
                });
                m.setChildren(secondMesAppletsRules);
            }
        });
        return firstMesAppletsRules;
    }
}
