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
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import com.yice.edu.cn.common.pojo.xw.pcdData.son.EduInputPrev;
import com.yice.edu.cn.common.pojo.xw.pcdData.son.EduInputSchool;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdEducationData;
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
public class PcdEducationDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PcdStudentDataService pcdStudentDataService;
    @Autowired
    private PcdTeacherDataService pcdTeacherDataService;
    @Autowired
    private PcdTeachingDataService pcdTeachingDataService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdEducationData findPcdEducationDataById(String id) {
        return mot.findById(id,PcdEducationData.class);
    }
    public void savePcdEducationData(PcdEducationData pcdEducationData) {
        pcdEducationData.setCreateTime(DateUtil.now());
        pcdEducationData.setId(sequenceId.nextId());
        mot.insert(pcdEducationData);
    }
    public List<PcdEducationData> findPcdEducationDataListByCondition(PcdEducationData pcdEducationData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdEducationData.class)).find(MongoKit.buildMatchDocument(pcdEducationData));
        Pager pager = pcdEducationData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdEducationData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdEducationData.class, document)));
        return list;
    }
    public long findPcdEducationDataCountByCondition(PcdEducationData pcdEducationData) {
        return mot.getCollection(mot.getCollectionName(PcdEducationData.class)).countDocuments(MongoKit.buildMatchDocument(pcdEducationData));
    }
    public PcdEducationData findOnePcdEducationDataByCondition(PcdEducationData pcdEducationData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdEducationData.class)).find(MongoKit.buildMatchDocument(pcdEducationData));
       MongoKit.appendProjection(query,pcdEducationData.getPager());
       return mot.getConverter().read(PcdEducationData.class,query.first());
    }

    public void updatePcdEducationData(PcdEducationData pcdEducationData) {
        pcdEducationData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdEducationData.class)).updateOne(new Document("_id",pcdEducationData.getId()), MongoKit.buildUpdateDocument(pcdEducationData,false));
    }
    public void updatePcdEducationDataForAll(PcdEducationData pcdEducationData) {
        pcdEducationData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdEducationData.class)).updateOne(new Document("_id",pcdEducationData.getId()), MongoKit.buildUpdateDocument(pcdEducationData,true));
    }
    public void deletePcdEducationData(String id) {
        mot.getCollection(mot.getCollectionName(PcdEducationData.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdEducationDataByCondition(PcdEducationData pcdEducationData) {
        mot.getCollection(mot.getCollectionName(PcdEducationData.class)).deleteMany(MongoKit.buildMatchDocument(pcdEducationData));
    }
    public void batchSavePcdEducationData(List<PcdEducationData> pcdEducationDatas){
        pcdEducationDatas.forEach(pcdEducationData -> pcdEducationData.setId(sequenceId.nextId()));
        mot.insertAll(pcdEducationDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePcdEducationDataKong(PcdEducationData pcdEducationData){
        DecimalFormat df2 = new DecimalFormat("0.00%");
        Map<String,Object> map = pcdEducationData.getData();
        // 教育建设投入（单位：万元）
        Object o = map.get("eduInput");
        // 往年教育建设投入（单位：万元）
        Object eduInputPrev = map.get("eduInputPrev");
        // 学校教育建设投入（单位：万元）
        Object eduInputSchool = map.get("eduInputSchool");
        JSONObject jsonObject = JSONUtil.parseObj(o);
        JSONArray jsonObject1 = JSONUtil.parseArray(eduInputPrev);
        JSONObject jsonObject2 = JSONUtil.parseObj(eduInputSchool);

        //小学、初中高中金额
        Double primary = jsonObject!=null?Double.parseDouble(jsonObject.get(Constant.PCD_DATA.PRIMARY).toString()):0.0;
        Double middle = jsonObject!=null?Double.parseDouble(jsonObject.get(Constant.PCD_DATA.MIDDLE).toString()):0.0;
        Double high = jsonObject!=null?Double.parseDouble(jsonObject.get(Constant.PCD_DATA.HIGH).toString()):0.0;
        Double count = primary+middle+high;
        Map<String,List<String>> porp = new HashMap<>();
        //学校名称
        List<String> listSchool = new ArrayList<>();
        listSchool.add(Constant.PCD_DATA.XIAOXUE);
        listSchool.add(Constant.PCD_DATA.CHUZHONG);
        listSchool.add(Constant.PCD_DATA.GAOZHONG);
        //学校数量
        List<String> listTwo = new ArrayList<>();
        listTwo.add(primary.toString());
        listTwo.add(middle.toString());
        listTwo.add(high.toString());
        //其他学校总数量
        List<String> listThree = new ArrayList<>();
        listThree.add((count-primary)+"");
        listThree.add((count-middle)+"");
        listThree.add((count-high)+"");
        //学校比例
        List<String> listFour = new ArrayList<>();
        if(count==0){
            listFour.add("0.00%");
            listFour.add("0.00%");
            listFour.add("0.00%");
        }else {
            listFour.add(df2.format(primary/count));
            listFour.add(df2.format(middle/count));
            listFour.add(df2.format(high/count));
        }


        porp.put("school",listSchool);
        porp.put("number",listTwo);
        porp.put("surplus",listThree);
        porp.put("ratio",listFour);
        // 往年教育建设投入（单位：万元）
        List<EduInputPrev> eduInputPrevs = new ArrayList<>();

        jsonObject1.forEach(f->{
            JSONObject jsonObject3 = JSONUtil.parseObj(f);
            EduInputPrev eduInputPrev1 = new EduInputPrev();
            eduInputPrev1.setYear(Integer.parseInt(jsonObject3.get(Constant.PCD_DATA.YEAR).toString()));
            eduInputPrev1.setPrimary(Integer.parseInt(jsonObject3.get(Constant.PCD_DATA.PRIMARY).toString()));
            eduInputPrev1.setMiddle(Integer.parseInt(jsonObject3.get(Constant.PCD_DATA.MIDDLE).toString()));
            eduInputPrev1.setHigh(Integer.parseInt(jsonObject3.get(Constant.PCD_DATA.HIGH).toString()));
            eduInputPrevs.add(eduInputPrev1);
        });


        // 学校教育建设投入（单位：万元）
        Map<String,List<EduInputSchool>> map1 = new HashMap<>();
        List<EduInputSchool> primaryList = new ArrayList<>();
        List<EduInputSchool> middleList = new ArrayList<>();
        List<EduInputSchool> highList = new ArrayList<>();
        Object o1 = jsonObject2.get(Constant.PCD_DATA.PRIMARY);
        Object o2 = jsonObject2.get(Constant.PCD_DATA.MIDDLE);
        Object o3 = jsonObject2.get(Constant.PCD_DATA.HIGH);
        JSONArray primaryArray = JSONUtil.parseArray(o1);
        JSONArray middleArray = JSONUtil.parseArray(o2);
        JSONArray highArray = JSONUtil.parseArray(o3);
        forJsonArray(primaryArray,primaryList,df2);
        forJsonArray(middleArray,middleList,df2);
        forJsonArray(highArray,highList,df2);

        map1.put("primary",primaryList);
        map1.put("middle",middleList);
        map1.put("high",highList);

        pcdEducationData.setCount(count);
        pcdEducationData.setPorp(porp);
        pcdEducationData.setSchoolMap(map1);
        pcdEducationData.setUpdateTime(DateUtil.now());
        mot.save(pcdEducationData);
    }


    public void forJsonArray(JSONArray jsonArray,List<EduInputSchool> strings,DecimalFormat df2){
        jsonArray.forEach(f->{
            EduInputSchool eduInputSchool = new EduInputSchool();
            JSONObject jsonObject3 = JSONUtil.parseObj(f);
            Object l = jsonObject3.get("list");
            JSONArray list = JSONUtil.parseArray(l);
            Double yearMoney = 0.0;
            Double money = 0.0;
            JSONObject jsonObject4 = JSONUtil.parseObj(list.get(0));
            money = Double.parseDouble(jsonObject4.get("money").toString());
            JSONObject jsonObject5 = JSONUtil.parseObj(list.get(1));
            yearMoney = Double.parseDouble(jsonObject5.get("money").toString());
            if(yearMoney==0){
                eduInputSchool.setNumber("100%");
            }else{
                eduInputSchool.setNumber(df2.format(Math.abs(money-yearMoney)/money));
            }
            eduInputSchool.setFlag(money-yearMoney>0?true:false);
            strings.add(eduInputSchool);
        });
    }


    public Map<String,Object> findIndexDataByEehId(String eehId){
        Map<String,Object> map = new HashMap<>();
        //教育建设数据,学生数据,教师数据,教学数据
        PcdEducationData p = findPcdEducationDataById(eehId);
        PcdStudentData stu = pcdStudentDataService.findPcdStudentDataById(eehId);
        PcdTeacherData tea = pcdTeacherDataService.findPcdTeacherDataById(eehId);
        PcdTeachingData teaching = pcdTeachingDataService.findPcdTeachingDataByIdKong(eehId);
        map.put("educationData",p);
        map.put("studentData",stu);
        map.put("teacherData",tea);
        map.put("teachingData",teaching);
        return map;
    }

}
