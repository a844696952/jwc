package com.yice.edu.cn.xw.service.pcdData;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import com.yice.edu.cn.common.pojo.xw.pcdData.son.StudentWorkAverAge;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PcdTeachingDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PcdStudentDataService pcdStudentDataService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdTeachingData findPcdTeachingDataById(String id) {
        return mot.findById(id,PcdTeachingData.class);
    }
    public void savePcdTeachingData(PcdTeachingData pcdTeachingData) {
        pcdTeachingData.setCreateTime(DateUtil.now());
        pcdTeachingData.setId(sequenceId.nextId());
        mot.insert(pcdTeachingData);
    }
    public List<PcdTeachingData> findPcdTeachingDataListByCondition(PcdTeachingData pcdTeachingData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).find(MongoKit.buildMatchDocument(pcdTeachingData));
        Pager pager = pcdTeachingData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdTeachingData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdTeachingData.class, document)));
        return list;
    }
    public long findPcdTeachingDataCountByCondition(PcdTeachingData pcdTeachingData) {
        return mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).countDocuments(MongoKit.buildMatchDocument(pcdTeachingData));
    }
    public PcdTeachingData findOnePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).find(MongoKit.buildMatchDocument(pcdTeachingData));
       MongoKit.appendProjection(query,pcdTeachingData.getPager());
       return mot.getConverter().read(PcdTeachingData.class,query.first());
    }

    public void updatePcdTeachingData(PcdTeachingData pcdTeachingData) {
        pcdTeachingData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).updateOne(new Document("_id",pcdTeachingData.getId()), MongoKit.buildUpdateDocument(pcdTeachingData,false));
    }
    public void updatePcdTeachingDataForAll(PcdTeachingData pcdTeachingData) {
        pcdTeachingData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).updateOne(new Document("_id",pcdTeachingData.getId()), MongoKit.buildUpdateDocument(pcdTeachingData,true));
    }
    public void deletePcdTeachingData(String id) {
        mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData) {
        mot.getCollection(mot.getCollectionName(PcdTeachingData.class)).deleteMany(MongoKit.buildMatchDocument(pcdTeachingData));
    }
    public void batchSavePcdTeachingData(List<PcdTeachingData> pcdTeachingDatas){
        pcdTeachingDatas.forEach(pcdTeachingData -> pcdTeachingData.setId(sequenceId.nextId()));
        mot.insertAll(pcdTeachingDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public PcdTeachingData findPcdTeachingDataByIdKong(String id){
        PcdTeachingData pcdTeachingData = findPcdTeachingDataById(id);
        if(pcdTeachingData!=null&&pcdTeachingData.getData()!=null){
            //获取学生总人数
            PcdStudentData pcdStudentData = pcdStudentDataService.findPcdStudentDataById(id);
            Map<String,Object> map = pcdTeachingData.getData();
            Object primary = map.get(Constant.PCD_DATA.PRIMARY);
            Object middle = map.get(Constant.PCD_DATA.MIDDLE);
            Object high = map.get(Constant.PCD_DATA.HIGH);
            JSONObject jsonPrimary = JSONUtil.parseObj(primary);
            JSONObject jsonMiddle = JSONUtil.parseObj(middle);
            JSONObject jsonHigh = JSONUtil.parseObj(high);
            //学生平均做题情况
            Map<String,Map<String,StudentWorkAverAge>> stringListMap = new HashMap<>();
            //学生平均做题情况
            if(pcdStudentData!=null){
                getStudentWorkCase(jsonPrimary,stringListMap,Constant.PCD_DATA.PRIMARY,pcdStudentData.getCount());
                getStudentWorkCase(jsonMiddle,stringListMap,Constant.PCD_DATA.MIDDLE,pcdStudentData.getCount());
                getStudentWorkCase(jsonHigh,stringListMap,Constant.PCD_DATA.HIGH,pcdStudentData.getCount());
            }
            pcdTeachingData.setStudentWorkMap(stringListMap);
        }

        return pcdTeachingData;
    }
    public void savePcdTeachingDataKong(PcdTeachingData pcdTeachingData){

        pcdTeachingData.setUpdateTime(DateUtil.now());
        Map<String,Object> map = pcdTeachingData.getData();
        //全区教学资源情况总数量
        Map<String,Long> count = new HashMap<>();
        //全区教学资源情况比例
        Map<String,List<String>> ratioMap = new HashMap<>();
        //小初高学校教学资源总数量
        Map<String,Long> schoolCount = new HashMap<>();

        Object primary = map.get(Constant.PCD_DATA.PRIMARY);
        Object middle = map.get(Constant.PCD_DATA.MIDDLE);
        Object high = map.get(Constant.PCD_DATA.HIGH);
        JSONObject jsonPrimary = JSONUtil.parseObj(primary);
        JSONObject jsonMiddle = JSONUtil.parseObj(middle);
        JSONObject jsonHigh = JSONUtil.parseObj(high);
        // 全区教学资源情况总数量
        count.put(Constant.PCD_DATA.PRIMARY,getResourceOfAllArea(jsonPrimary,ratioMap,Constant.PCD_DATA.PRIMARY));
        count.put(Constant.PCD_DATA.MIDDLE,getResourceOfAllArea(jsonMiddle,ratioMap,Constant.PCD_DATA.MIDDLE));
        count.put(Constant.PCD_DATA.HIGH,getResourceOfAllArea(jsonHigh,ratioMap,Constant.PCD_DATA.HIGH));

        //小初高学校教学资源总数量
        getResourceOfSchoolCount(jsonPrimary,schoolCount,Constant.PCD_DATA.PRIMARY);
        getResourceOfSchoolCount(jsonMiddle,schoolCount,Constant.PCD_DATA.MIDDLE);
        getResourceOfSchoolCount(jsonHigh,schoolCount,Constant.PCD_DATA.HIGH);

        //全区教学资源情况比例
        pcdTeachingData.setRatioMap(ratioMap);
        pcdTeachingData.setCount(count);
        pcdTeachingData.setSchoolCountMap(schoolCount);
        mot.save(pcdTeachingData);
    }

    // 全区教学资源情况总数量
    public Long getResourceOfAllArea(JSONObject jsonObject,Map<String,List<String>> map,String name){
        DecimalFormat df2 = new DecimalFormat("0.00%");
        long sum = 0;
        List<String> list = new ArrayList<>();
        // 全区教学资源情况
        Object o = jsonObject.get(Constant.PCD_DATA.RESOURCEOFALLAREA);
        JSONArray reourceOfAllArea = JSONUtil.parseArray(o);
        for(int i =0;i<reourceOfAllArea.size();i++){
            JSONObject jsonObject1 = JSONUtil.parseObj(reourceOfAllArea.get(i));
            Long num = Long.parseLong(jsonObject1.get("num")!=null?jsonObject1.get("num").toString():"0");
            sum+=num;
        }
        for(int i =0;i<reourceOfAllArea.size();i++){
            JSONObject jsonObject1 = JSONUtil.parseObj(reourceOfAllArea.get(i));
            Long num = Long.parseLong(jsonObject1.get("num").toString());
            if(sum==0){
                list.add("0%");
            }else{
                list.add(df2.format(num.doubleValue()/sum));
            }
        }
        map.put(name,list);
        return sum;
    }

    //小初高学校教学资源总数量
    public void getResourceOfSchoolCount(JSONObject jsonObject,Map<String,Long> map,String name){
        Object o = jsonObject.get(Constant.PCD_DATA.RESOURCEOFSCHOOL);
        JSONArray jsonArray = JSONUtil.parseArray(o);
        long count = jsonArray.size();
        map.put(name,count);
    }

    //学生作业情况
    public void getStudentWorkCase(JSONObject jsonObject, Map<String,Map<String,StudentWorkAverAge>> map,String name,Map<String,Long> studentCount){
        Object o = jsonObject.get(Constant.PCD_DATA.HOMEWORK);
        Long l = studentCount.get(name);
        DecimalFormat df2 = new DecimalFormat("0.00%");
        Long workSum = 0L;
        Double workTime = 0.0;
        Double workRate = 0.0;
        JSONArray jsonArray = JSONUtil.parseArray(o);
        Map<String,StudentWorkAverAge> averAges = new HashMap<>();
        //作业平均数量和作业平均时长
        for(int i =0;i<jsonArray.size();i++){
            StudentWorkAverAge swaa = new StudentWorkAverAge();
            JSONObject jsonObject1 = JSONUtil.parseObj(jsonArray.get(i));
            Long online = Long.parseLong(jsonObject1.get(Constant.PCD_DATA.ONLINE).toString());
            Long underLine = Long.parseLong(jsonObject1.get(Constant.PCD_DATA.UNDERLINE).toString());
            Double duration = Double.parseDouble( jsonObject1.get(Constant.PCD_DATA.DURATION).toString());
            workSum+=online+underLine;
            workTime+=duration;
            workRate+=Double.parseDouble(jsonObject1.get(Constant.PCD_DATA.RATE).toString());
            if(l!=0){
                swaa.setAverTopicCount(Math.round((online+underLine)/l.doubleValue()*100)/100.0);
                swaa.setAverTopicTime(Math.round(duration/l*100)/100.0);
            }

            if(jsonObject1.get(Constant.PCD_DATA.SUBJECT)!=null){
                averAges.put(jsonObject1.get(Constant.PCD_DATA.SUBJECT).toString(),swaa);
            }

        }
        //作业平均提交率
        for(int i =0;i<jsonArray.size();i++){
            JSONObject jsonObject1 = JSONUtil.parseObj(jsonArray.get(i));
            if(jsonObject1.get(Constant.PCD_DATA.SUBJECT)!=null){
                StudentWorkAverAge swaa = averAges.get(jsonObject1.get(Constant.PCD_DATA.SUBJECT).toString());
                Double rate = Double.parseDouble(jsonObject1.get(Constant.PCD_DATA.RATE)!=null?jsonObject1.get(Constant.PCD_DATA.RATE).toString():"0");
                if(l!=0)
                    swaa.setAverWorkRatio(df2.format(rate/100));
            }
        }
        StudentWorkAverAge st = new StudentWorkAverAge();
        //全部
        if(l!=0){
            st.setAverTopicCount(Math.round(workSum/l.doubleValue()*100)/100.0);
            st.setAverTopicTime(Math.round(workTime/l.doubleValue()*100)/100.0);
            st.setAverWorkRatio(df2.format(workRate/jsonArray.size()/100));
        }
        averAges.put("全部",st);
        map.put(name,averAges);
    }

}
