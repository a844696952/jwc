package com.yice.edu.cn.xw.service.kq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.common.pojo.xw.kq.KqPunchRules;
import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqBasicRulesService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public KqBasicRules findKqBasicRulesById(String id) {
        return mot.findById(id, KqBasicRules.class);
    }

    public void saveKqBasicRules(KqBasicRules kqBasicRules) {
        kqBasicRules.setCreateTime(DateUtil.now());
        kqBasicRules.setId(sequenceId.nextId());
        mot.insert(kqBasicRules);
    }

    public List<KqBasicRules> findKqBasicRulesListByCondition(KqBasicRules kqBasicRules) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqBasicRules.class)).find(MongoKit.buildMatchDocument(kqBasicRules));
        Pager pager = kqBasicRules.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqBasicRules> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqBasicRules.class, document)));
        return list;
    }

    public long findKqBasicRulesCountByCondition(KqBasicRules kqBasicRules) {
        return mot.getCollection(mot.getCollectionName(KqBasicRules.class)).countDocuments(MongoKit.buildMatchDocument(kqBasicRules));
    }

    public KqBasicRules findOneKqBasicRulesByCondition(KqBasicRules kqBasicRules) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqBasicRules.class)).find(MongoKit.buildMatchDocument(kqBasicRules));
        MongoKit.appendProjection(query, kqBasicRules.getPager());
        return mot.getConverter().read(KqBasicRules.class, query.first());
    }

    public void updateKqBasicRules(KqBasicRules kqBasicRules) {
        kqBasicRules.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqBasicRules.getId())), MongoKit.update(kqBasicRules), KqBasicRules.class);
    }

    public void deleteKqBasicRules(String id) {
        mot.remove(query(where("id").is(id)), KqBasicRules.class);
    }

    public void deleteKqBasicRulesByCondition(KqBasicRules kqBasicRules) {
        mot.getCollection(mot.getCollectionName(KqBasicRules.class)).deleteMany(MongoKit.buildMatchDocument(kqBasicRules));
    }

    public void batchSaveKqBasicRules(List<KqBasicRules> kqBasicRuless) {
        kqBasicRuless.forEach(kqBasicRules -> kqBasicRules.setId(sequenceId.nextId()));
        mot.insertAll(kqBasicRuless);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/

    //次日统计时更新基础规则
    public void dailyStatisUpdateBasicRlues(KqBasicRules kqBasicRules){
        List<KqBasicRules> kqBasicRulesList = findKqBasicRulesListByCondition(kqBasicRules);
        if (kqBasicRulesList!=null&&kqBasicRulesList.size()==2){
            KqBasicRules old = new KqBasicRules();
            KqBasicRules theNew = new KqBasicRules();
            for (KqBasicRules k:kqBasicRulesList){
                if(k.getRule().equals("0")){
                    old = k;
                }
                if (k.getRule().equals("1")){
                    theNew = k;
                }
            }
            theNew.setRule("0");
            if (!StrUtil.isEmpty(old.getId())&&!StrUtil.isEmpty(theNew.getId())){
                deleteKqBasicRules(old.getId());
                updateKqBasicRules(theNew);
            }else {
                System.out.println("规则没做更新操作");
            }

        }
        if (kqBasicRulesList!=null&&kqBasicRulesList.size()==1){
            if (kqBasicRulesList.get(0).getRule().equals("1")){
                kqBasicRulesList.get(0).setRule("0");
                updateKqBasicRules(kqBasicRulesList.get(0));
            }
        }
    }
}
