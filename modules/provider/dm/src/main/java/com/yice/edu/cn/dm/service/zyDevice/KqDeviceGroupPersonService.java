package com.yice.edu.cn.dm.service.zyDevice;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqDeviceGroupPersonService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KqDeviceGroupPerson findKqDeviceGroupPersonById(String id) {
        return mot.findById(id,KqDeviceGroupPerson.class);
    }
    public void saveKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDeviceGroupPerson kq1 = new KqDeviceGroupPerson();
        List<KqDeviceGroupPerson> kqs = findKqDeviceGroupPersonListByCondition(kq1);
        if (CollectionUtil.isNotEmpty(kqs)&&kqs.stream().anyMatch(k->k.getFactoryType()==null)){
            List<KqDeviceGroupPerson> collect = kqs.stream().filter(k -> k.getFactoryType() == null).collect(Collectors.toList());
            for (KqDeviceGroupPerson k:collect){
                k.setFactoryType("1");
                updateKqDeviceGroupPerson(k);
            }
        }
        kqDeviceGroupPerson.setCreateTime(DateUtil.now());
        kqDeviceGroupPerson.setId(sequenceId.nextId());
        mot.insert(kqDeviceGroupPerson);
    }
    public List<KqDeviceGroupPerson> findKqDeviceGroupPersonListByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDeviceGroupPerson.class)).find(MongoKit.buildMatchDocument(kqDeviceGroupPerson));
        Pager pager = kqDeviceGroupPerson.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<KqDeviceGroupPerson> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDeviceGroupPerson.class, document)));
        return list;
    }
    public long findKqDeviceGroupPersonCountByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return mot.getCollection(mot.getCollectionName(KqDeviceGroupPerson.class)).countDocuments(MongoKit.buildMatchDocument(kqDeviceGroupPerson));
    }
    public KqDeviceGroupPerson findOneKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDeviceGroupPerson.class)).find(MongoKit.buildMatchDocument(kqDeviceGroupPerson));
       MongoKit.appendProjection(query,kqDeviceGroupPerson.getPager());
       return mot.getConverter().read(KqDeviceGroupPerson.class,query.first());
    }

    public void updateKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqDeviceGroupPerson.getId())), MongoKit.update(kqDeviceGroupPerson),KqDeviceGroupPerson.class);
    }
    public void deleteKqDeviceGroupPerson(String id) {
        mot.remove(query(where("id").is(id)),KqDeviceGroupPerson.class);
    }
    public void deleteKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        mot.getCollection(mot.getCollectionName(KqDeviceGroupPerson.class)).deleteMany(MongoKit.buildMatchDocument(kqDeviceGroupPerson));
    }
    public void batchSaveKqDeviceGroupPerson(List<KqDeviceGroupPerson> kqDeviceGroupPersons){
        KqDeviceGroupPerson kq1 = new KqDeviceGroupPerson();
        List<KqDeviceGroupPerson> kqs = findKqDeviceGroupPersonListByCondition(kq1);
        if (CollectionUtil.isNotEmpty(kqs)&&kqs.stream().anyMatch(k->k.getFactoryType()==null)){
            List<KqDeviceGroupPerson> collect = kqs.stream().filter(k -> k.getFactoryType() == null).collect(Collectors.toList());
            for (KqDeviceGroupPerson k:collect){
                k.setFactoryType("1");
                updateKqDeviceGroupPerson(k);
            }
        }
        kqDeviceGroupPersons.forEach(kqDeviceGroupPerson -> {
            kqDeviceGroupPerson.setId(sequenceId.nextId());
            kqDeviceGroupPerson.setCreateTime(DateUtil.now());
        });
        mot.insertAll(kqDeviceGroupPersons);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
/*    db.kqDeviceGroupPerson.aggregate([

            {$match:{"$and":[
										{"schoolId" : "1741058039675183104"},
										{ "personId" : { "$in" : ["1869326825653362688" , "1791910315020869632" ]}},


									]}
						}
         ,


						{$lookup:
									 {
										 from: "kqDevicePerson",
										 localField: "deviceGroupId",
										 foreignField: "groupId",
										 as: "deviceGroupId"
									 }
						}

					,
						{ $unwind : "$deviceGroupId" }

					,
						{$match:{"deviceGroupId.deviceType" : "2"}}

          ,
            {$group:{
                            _id:{personId:"$personId",schoolId:"$schoolId"},
                   deviceNoList:{$push:"$deviceGroupId.deviceNo"},
                            }

						}
					,
						{ "$project" : { "personId" : "$_id.personId" ,  "deviceNoList" : "$deviceNoList" ,_id:0}
						}
 ])
 */
    //查找人员绑定的所有设备
    public List< KqDeviceGroupPerson> findPersonsBoundDevices(KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(kqDeviceGroupPerson.getSchoolId()));
        criterias.add(new Criteria().where("factoryType").is(kqDeviceGroupPerson.getFactoryType()));
        criterias.add(new Criteria().where("personId").in(kqDeviceGroupPerson.getPersonIdList()));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.lookup("kqDevicePerson","deviceGroupId","groupId","deviceGroupId"),
                Aggregation.unwind("deviceGroupId"),
                Aggregation.match(new Criteria().where("deviceGroupId.deviceType").is("2")),
                Aggregation.group( "personId", "$schoolId")
                        .push("deviceGroupId.deviceNo").as("deviceNoList"),
                Aggregation.project().and("_id.personId").as("personId").and("deviceNoList").as("deviceNoList")
        );
        AggregationResults<KqDeviceGroupPerson> outputTypeCount1 =
                mot.aggregate(aggregation, KqDeviceGroupPerson.class, KqDeviceGroupPerson.class);
        List<KqDeviceGroupPerson> kqDeviceGroupPersons = outputTypeCount1.getMappedResults();
        return kqDeviceGroupPersons;
    }



}
