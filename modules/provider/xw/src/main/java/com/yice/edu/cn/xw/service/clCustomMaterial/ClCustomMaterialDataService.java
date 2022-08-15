package com.yice.edu.cn.xw.service.clCustomMaterial;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ClCustomMaterialDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ClWeightService clWeightService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClCustomMaterialData findClCustomMaterialDataById(String id) {
        return mot.findById(id,ClCustomMaterialData.class);
    }
    public void saveClCustomMaterialData(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialData.setCreateTime(DateUtil.now());
        clCustomMaterialData.setId(sequenceId.nextId());
        mot.insert(clCustomMaterialData);
    }
    public List<ClCustomMaterialData> findClCustomMaterialDataListByCondition(ClCustomMaterialData clCustomMaterialData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClCustomMaterialData.class)).find(MongoKit.buildMatchDocument(clCustomMaterialData));
        Pager pager = clCustomMaterialData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ClCustomMaterialData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ClCustomMaterialData.class, document)));
        return list;
    }
    public long findClCustomMaterialDataCountByCondition(ClCustomMaterialData clCustomMaterialData) {
        return mot.getCollection(mot.getCollectionName(ClCustomMaterialData.class)).countDocuments(MongoKit.buildMatchDocument(clCustomMaterialData));
    }
    public ClCustomMaterialData findOneClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClCustomMaterialData.class)).find(MongoKit.buildMatchDocument(clCustomMaterialData));
       MongoKit.appendProjection(query,clCustomMaterialData.getPager());
       return mot.getConverter().read(ClCustomMaterialData.class,query.first());
    }

    public void updateClCustomMaterialData(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialData.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clCustomMaterialData.getId())), MongoKit.update(clCustomMaterialData,false),ClCustomMaterialData.class);
    }
    public void updateClCustomMaterialDataForAll(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialData.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clCustomMaterialData.getId())), MongoKit.update(clCustomMaterialData,true),ClCustomMaterialData.class);
    }
    public void deleteClCustomMaterialData(String id) {
        mot.remove(query(where("id").is(id)),ClCustomMaterialData.class);
    }
    public void deleteClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData) {
        mot.getCollection(mot.getCollectionName(ClCustomMaterialData.class)).deleteMany(MongoKit.buildMatchDocument(clCustomMaterialData));
    }
    public void batchSaveClCustomMaterialData(List<ClCustomMaterialData> clCustomMaterialDatas){
        clCustomMaterialDatas.forEach(clCustomMaterialData -> clCustomMaterialData.setId(sequenceId.nextId()));
        mot.insertAll(clCustomMaterialDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public Long findClCustomMaterialDataMaxWeight(ClCustomMaterialData clCustomMaterialData){
        Document d = new Document("$match",new Document("parentId",clCustomMaterialData.getParentId()));
        Document document1 = new Document("$group",new Document("_id",null).append("weight",new Document("$max","$weight")));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("clCustomMaterialData").aggregate(Arrays.asList(d,document1));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        ClCustomMaterialData clCustomMaterialData1 = new ClCustomMaterialData();
        while (mongoCursor.hasNext()){
            Document document2 = mongoCursor.next();
            clCustomMaterialData1 =  mot.getConverter().read(ClCustomMaterialData.class,document2);
        }
        if(clCustomMaterialData1!=null){
            return clCustomMaterialData1.getWeight()==null?0:clCustomMaterialData1.getWeight()+10<Constant.CL_CUSTOM_MATERIAL.WEIGHT?clCustomMaterialData1.getWeight():Constant.CL_CUSTOM_MATERIAL.WEIGHT-10;
        }else {
            return 0L;
        }
    }

    public void  saveClCustomMaterialDataAndClWeight(ClCustomMaterialData clCustomMaterialData){
        ClWeight clWeight = new ClWeight();
        BeanUtil.copyProperties(clCustomMaterialData,clWeight);
        clWeight.setType(clCustomMaterialData.getType());
        Long weight = clWeightService.addMaxWeight(clWeight);
        clCustomMaterialData.setWeight(weight);
        saveClCustomMaterialData(clCustomMaterialData);
    }

    public List<ClCustomMaterialData> findClCustomMaterialDataListByConditionKong(ClCustomMaterialData clCustomMaterialData){
        Document match = new Document();
        Document d = new Document();
        if(clCustomMaterialData.getSearchTimeZone()!=null&&clCustomMaterialData.getSearchTimeZone().length>0){
            List a = Arrays.asList(new Document("createTime",new Document("$gte",clCustomMaterialData.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",clCustomMaterialData.getSearchTimeZone()[1])));
            d.append("$and",a);
        }
        if(clCustomMaterialData.getMaterialTimeList()!=null&&clCustomMaterialData.getMaterialTimeList().length>0){
            d.append("$and",Arrays.asList(new Document("materialTime",new Document("$gte",clCustomMaterialData.getMaterialTimeList()[0])),new Document("materialTime",new Document("$lte",clCustomMaterialData.getMaterialTimeList()[1]))));
        }
        if(clCustomMaterialData.getName()!=null){
            d.append("name",new Document("$regex",clCustomMaterialData.getName()).append("$options","i"));
        }
        if(clCustomMaterialData.getUserName()!=null){
            d.append("userName",new Document("$regex",clCustomMaterialData.getUserName()).append("$options","i"));
        }
        d.append("parentId",clCustomMaterialData.getParentId());
        match = new Document("$match",d);
        Document documentSort = new Document();
        if(clCustomMaterialData.getPager().getSortField()==null){
            documentSort.append("_id",-1);
        }else{
            String[] fields = clCustomMaterialData.getPager().getSortField().split(",");
            String[] order = clCustomMaterialData.getPager().getSortOrder().split(",");
            for(int i = 0;i<fields.length;i++){
                documentSort.append(fields[i],order[i].equalsIgnoreCase(Pager.ASC)?1:-1);
            }
        }

        Document sort = new Document("$sort",documentSort);
        Document skip  = new Document("$skip",(clCustomMaterialData.getPager().getPage() - 1) * clCustomMaterialData.getPager().getPageSize());
        Document limt = new Document("$limit",clCustomMaterialData.getPager().getPageSize());
        AggregateIterable<Document> aggregateIterable = mot.getCollection("clCustomMaterialData").aggregate(Arrays.asList(match,sort,skip,limt));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<ClCustomMaterialData> clCustomMaterialData1 = new ArrayList<>();
        while (mongoCursor.hasNext()){
            Document document2 = mongoCursor.next();
            clCustomMaterialData1.add(mot.getConverter().read(ClCustomMaterialData.class,document2));
        }
        return clCustomMaterialData1;

    }

    public long findClCustomMaterialDataCountByConditionKong(ClCustomMaterialData clCustomMaterialData){
        Document match = new Document();
        Document d = new Document();
        if(clCustomMaterialData.getSearchTimeZone()!=null&&clCustomMaterialData.getSearchTimeZone().length>0){
            List a = Arrays.asList(new Document("createTime",new Document("$gte",clCustomMaterialData.getSearchTimeZone()[0])),new Document("createTime",new Document("$lt",clCustomMaterialData.getSearchTimeZone()[1])));
            d.append("$and",a);
        }
        if(clCustomMaterialData.getMaterialTimeList()!=null&&clCustomMaterialData.getMaterialTimeList().length>0){
            d.append("$and",Arrays.asList(new Document("materialTime",new Document("$gte",clCustomMaterialData.getMaterialTimeList()[0])),new Document("materialTime",new Document("$lt",clCustomMaterialData.getMaterialTimeList()[1]))));
        }
        if(clCustomMaterialData.getName()!=null){
            d.append("name",new Document("$regex",clCustomMaterialData.getName()).append("$options","i"));
        }
        if(clCustomMaterialData.getUserName()!=null){
            d.append("userName",new Document("$regex",clCustomMaterialData.getUserName()).append("$options","i"));
        }
        d.append("parentId",clCustomMaterialData.getParentId());
        match = new Document("$match",d);
        AggregateIterable<Document> aggregateIterable = mot.getCollection("clCustomMaterialData").aggregate(Arrays.asList(match));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<ClCustomMaterialData> clCustomMaterialData1 = new ArrayList<>();
        while (mongoCursor.hasNext()){
            Document document2 = mongoCursor.next();
            clCustomMaterialData1.add(mot.getConverter().read(ClCustomMaterialData.class,document2));
        }
        return clCustomMaterialData1.size();

    }
}
