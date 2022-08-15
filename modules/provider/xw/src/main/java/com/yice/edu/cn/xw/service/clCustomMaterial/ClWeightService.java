package com.yice.edu.cn.xw.service.clCustomMaterial;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ClWeightService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClWeight findClWeightById(String id) {
        return mot.findById(id,ClWeight.class);
    }
    public void saveClWeight(ClWeight clWeight) {
        clWeight.setCreateTime(DateUtil.now());
        clWeight.setId(sequenceId.nextId());
        mot.insert(clWeight);
    }
    public List<ClWeight> findClWeightListByCondition(ClWeight clWeight) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClWeight.class)).find(MongoKit.buildMatchDocument(clWeight));
        Pager pager = clWeight.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ClWeight> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ClWeight.class, document)));
        return list;
    }
    public long findClWeightCountByCondition(ClWeight clWeight) {
        return mot.getCollection(mot.getCollectionName(ClWeight.class)).countDocuments(MongoKit.buildMatchDocument(clWeight));
    }
    public ClWeight findOneClWeightByCondition(ClWeight clWeight) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClWeight.class)).find(MongoKit.buildMatchDocument(clWeight));
       MongoKit.appendProjection(query,clWeight.getPager());
       return mot.getConverter().read(ClWeight.class,query.first());
    }

    public void updateClWeight(ClWeight clWeight) {
        clWeight.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clWeight.getId())), MongoKit.update(clWeight,false),ClWeight.class);
    }
    public void updateClWeightForAll(ClWeight clWeight) {
        clWeight.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clWeight.getId())), MongoKit.update(clWeight,true),ClWeight.class);
    }
    public void deleteClWeight(String id) {
        mot.remove(query(where("id").is(id)),ClWeight.class);
    }
    public void deleteClWeightByCondition(ClWeight clWeight) {
        mot.getCollection(mot.getCollectionName(ClWeight.class)).deleteMany(MongoKit.buildMatchDocument(clWeight));
    }
    public void batchSaveClWeight(List<ClWeight> clWeights){
        clWeights.forEach(clWeight -> clWeight.setId(sequenceId.nextId()));
        mot.insertAll(clWeights);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    /**
     * 查询权重最大值,没有则添加，有则权重加10
     * @param clCustomMaterial
     * @return
     */
    public Long addMaxWeight(ClWeight clCustomMaterial){

        ClWeight clWeight = findOneClWeightByCondition(clCustomMaterial);
        if(clWeight==null){
            clWeight = new ClWeight();
            BeanUtil.copyProperties(clCustomMaterial,clWeight);
            clWeight.setWeight(10L);
            saveClWeight(clWeight);
            return 10L;
        }else {
            Long weight = clWeight.getWeight()+10>Constant.CL_CUSTOM_MATERIAL.WEIGHT?Constant.CL_CUSTOM_MATERIAL.WEIGHT: clWeight.getWeight()+10;
            clWeight.setWeight(weight);
            mot.save(clWeight);
            return weight;
        }

    }
}
