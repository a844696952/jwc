package com.yice.edu.cn.jw.service.eduEvaluation;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetailHistory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CompareQualityService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private CompareQualityDetailService compareQualityDetailService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CompareQuality findCompareQualityById(String id) {
        return mot.findById(id,CompareQuality.class);
    }
    public void saveCompareQuality(CompareQuality compareQuality) {
        compareQuality.setId(sequenceId.nextId());
        compareQuality.setCreateTime(DateUtil.now());
        mot.insert(compareQuality);
    }
    public List<CompareQuality> findCompareQualityListByCondition(CompareQuality compareQuality) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CompareQuality.class)).find(MongoKit.buildMatchDocument(compareQuality));
        Pager pager = compareQuality.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CompareQuality> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CompareQuality.class, document)));
        return list;
    }
    public long findCompareQualityCountByCondition(CompareQuality compareQuality) {
        return mot.getCollection(mot.getCollectionName(CompareQuality.class)).countDocuments(MongoKit.buildMatchDocument(compareQuality));
    }
    public CompareQuality findOneCompareQualityByCondition(CompareQuality compareQuality) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CompareQuality.class)).find(MongoKit.buildMatchDocument(compareQuality));
       MongoKit.appendProjection(query,compareQuality.getPager());
       return mot.getConverter().read(CompareQuality.class,query.first());
    }

    public void updateCompareQuality(CompareQuality compareQuality) {
        mot.updateFirst(query(where("id").is(compareQuality.getId())), MongoKit.update(compareQuality),CompareQuality.class);
    }
    public void deleteCompareQuality(String id) {
        mot.remove(query(where("id").is(id)),CompareQuality.class);
    }
    public void deleteCompareQualityByCondition(CompareQuality compareQuality) {
        mot.getCollection(mot.getCollectionName(CompareQuality.class)).deleteMany(MongoKit.buildMatchDocument(compareQuality));
    }
    public void batchSaveCompareQuality(List<CompareQuality> compareQualitys){
        compareQualitys.forEach(compareQuality -> compareQuality.setId(sequenceId.nextId()));
        mot.insertAll(compareQualitys);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void deleteCompareQualityByIdList(List<String> idList) {
        idList.forEach(id->{
            CompareQualityDetail compareQualityDetail=new CompareQualityDetail();
            compareQualityDetail.setCompareQualityId(findCompareQualityById(id).getId());
            compareQualityDetailService.deleteCompareQualityDetailByCondition(compareQualityDetail);
            deleteCompareQuality(id);
        });
    }


    public List<CompareQualityDetailHistory> findCompareQualityDetailListByClassId(List<String>classIdList) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        cl.add(new Criteria("classId").in(classIdList));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        return mot.aggregate(Aggregation.newAggregation(CompareQualityDetail.class,operations),CompareQualityDetailHistory.class).getMappedResults();

    }

    public void batchSaveCompareQualityDetailHistory(List<CompareQualityDetailHistory> compareQualityDetailHistories){
        mot.insertAll(compareQualityDetailHistories);
    }

    public void moveCompareQualityDataToHistory(List<String> classIdList) {
        List<CompareQualityDetailHistory> compareQualityDetailHistoryList= findCompareQualityDetailListByClassId(classIdList);

        batchSaveCompareQualityDetailHistory(compareQualityDetailHistoryList);
        compareQualityDetailHistoryList.forEach(c->{
            compareQualityDetailService.deleteCompareQualityDetail(c.getId());
        });

        deleteCompareQuality(compareQualityDetailHistoryList.get(0).getCompareQuality().getId());
    }

}
