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
public class ClCustomMaterialService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ClWeightService clWeightService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClCustomMaterial findClCustomMaterialById(String id) {
        return mot.findById(id,ClCustomMaterial.class);
    }
    public void saveClCustomMaterial(ClCustomMaterial clCustomMaterial) {
        clCustomMaterial.setCreateTime(DateUtil.now());
        clCustomMaterial.setId(sequenceId.nextId());
        mot.insert(clCustomMaterial);
    }
    public List<ClCustomMaterial> findClCustomMaterialListByCondition(ClCustomMaterial clCustomMaterial) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClCustomMaterial.class)).find(MongoKit.buildMatchDocument(clCustomMaterial));
        Pager pager = clCustomMaterial.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ClCustomMaterial> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ClCustomMaterial.class, document)));
        return list;
    }
    public long findClCustomMaterialCountByCondition(ClCustomMaterial clCustomMaterial) {
        return mot.getCollection(mot.getCollectionName(ClCustomMaterial.class)).countDocuments(MongoKit.buildMatchDocument(clCustomMaterial));
    }
    public ClCustomMaterial findOneClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClCustomMaterial.class)).find(MongoKit.buildMatchDocument(clCustomMaterial));
       MongoKit.appendProjection(query,clCustomMaterial.getPager());
       return mot.getConverter().read(ClCustomMaterial.class,query.first());
    }

    public void updateClCustomMaterial(ClCustomMaterial clCustomMaterial) {
        clCustomMaterial.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clCustomMaterial.getId())), MongoKit.update(clCustomMaterial,false),ClCustomMaterial.class);
    }
    public void updateClCustomMaterialForAll(ClCustomMaterial clCustomMaterial) {
        clCustomMaterial.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(clCustomMaterial.getId())), MongoKit.update(clCustomMaterial,true),ClCustomMaterial.class);
    }
    public void deleteClCustomMaterial(String id) {
        mot.remove(query(where("id").is(id)),ClCustomMaterial.class);
    }
    public void deleteClCustomMaterialByCondition(ClCustomMaterial clCustomMaterial) {
        mot.getCollection(mot.getCollectionName(ClCustomMaterial.class)).deleteMany(MongoKit.buildMatchDocument(clCustomMaterial));
    }
    public void batchSaveClCustomMaterial(List<ClCustomMaterial> clCustomMaterials){
        clCustomMaterials.forEach(clCustomMaterial -> clCustomMaterial.setId(sequenceId.nextId()));
        mot.insertAll(clCustomMaterials);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public List<ClCustomMaterial> findClCustomMaterialListByConditionKong(ClCustomMaterial clCustomMaterial) {
        Document match = new Document();
       if(clCustomMaterial.getStuOrTea()!=null&&clCustomMaterial.getCname()==null){
           match = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()).append("stuOrTea",clCustomMaterial.getStuOrTea()));
       }else if(clCustomMaterial.getStuOrTea()!=null&&clCustomMaterial.getCname()!=null){
           match = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()).append("stuOrTea",clCustomMaterial.getStuOrTea()).append("cname",new Document("$regex",clCustomMaterial.getCname()).append("$options","i")));
       }else if(clCustomMaterial.getStuOrTea()==null&&clCustomMaterial.getCname()!=null){
           match = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()).append("cname",new Document("$regex",clCustomMaterial.getCname()).append("$options","i")));
       }else{
           match = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()));
       }
       Document lookup = new Document("$lookup",new Document("from","clCustomMaterialData").append("localField","_id").append("foreignField","parentId").append("as","clCustomMaterialDataList"));
       Document documentSort = new Document();
       if(clCustomMaterial.getPager().getSortField()==null){
           documentSort.append("_id",-1);
       }else{
           String[] fields = clCustomMaterial.getPager().getSortField().split(",");
           String[] order = clCustomMaterial.getPager().getSortOrder().split(",");
           for(int i = 0;i<fields.length;i++){
               documentSort.append(fields[i],order[i].equalsIgnoreCase(Pager.ASC)?1:-1);
           }
       }

       Document sort = new Document("$sort",documentSort);
       Document skip  = new Document("$skip",(clCustomMaterial.getPager().getPage() - 1) * clCustomMaterial.getPager().getPageSize());
       Document limt = new Document("$limit",clCustomMaterial.getPager().getPageSize());

       Document project = new Document("$project",new Document("createTime",1).append("cname",1).append("remark",1).append("schoolId",1).append("type",1).append("stuOrTea",1).append("weight",1).append("count",new Document("$size","$clCustomMaterialDataList")));
       AggregateIterable<Document> aggregateIterable = mot.getCollection("clCustomMaterial").aggregate(Arrays.asList(match,lookup,sort,skip,limt,project));
       MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
       List<ClCustomMaterial> list = new ArrayList<>();
       while (mongoCursor.hasNext()){
           list.add(mot.getConverter().read(ClCustomMaterial.class,mongoCursor.next()));
       }

       return list;
    }
    /**
     * 查询权重最大值
     * @param clCustomMaterial
     * @return
     */
    public Long findClCustomMaterialMaxWeight(ClCustomMaterial clCustomMaterial){
        Document document;
        if(clCustomMaterial.getStuOrTea()!=null){
            document = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()).append("stuOrTea",clCustomMaterial.getStuOrTea()));
        }else{
            document = new Document("$match",new Document("schoolId",clCustomMaterial.getSchoolId()).append("type",clCustomMaterial.getType()));
        }
        Document document1 = new Document("$group",new Document("_id",null).append("weight",new Document("$max","$weight")));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("clCustomMaterial").aggregate(Arrays.asList(document,document1));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<ClCustomMaterial> clCustomMaterials = new ArrayList<>();
        while (mongoCursor.hasNext()){
            Document document2 = mongoCursor.next();
            clCustomMaterials.add(mot.getConverter().read(ClCustomMaterial.class,document2));
        }
        if(clCustomMaterials!=null&&clCustomMaterials.size()>0&&clCustomMaterials.get(0)!=null){
            long l = clCustomMaterials.get(0).getWeight()==null?0:clCustomMaterials.get(0).getWeight()+10<=Constant.CL_CUSTOM_MATERIAL.WEIGHT?clCustomMaterials.get(0).getWeight():Constant.CL_CUSTOM_MATERIAL.WEIGHT-10;
            return l;
        }else{
            return 0L;
        }
    }

    public void saveClCustomMaterialDataAndClWeight(ClCustomMaterial clCustomMaterial){
        ClWeight clWeight = new ClWeight();
        BeanUtil.copyProperties(clCustomMaterial,clWeight);
        Long weight = clWeightService.addMaxWeight(clWeight);
        clCustomMaterial.setWeight(weight);
        saveClCustomMaterial(clCustomMaterial);
    }


}
