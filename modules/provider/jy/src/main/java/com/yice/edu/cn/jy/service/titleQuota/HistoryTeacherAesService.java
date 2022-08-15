package com.yice.edu.cn.jy.service.titleQuota;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.common.util.LocalDateTimeUtils;
import com.yice.edu.cn.jy.dao.titleQuota.ITeacherAccessConfigurationDao;
import com.yice.edu.cn.jy.dao.titleQuota.ITopicQuotaResourcesDao;
import com.yice.edu.cn.jy.feignClient.teacher.TeacherFeign;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class HistoryTeacherAesService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ITeacherAccessConfigurationDao teacherAccessConfigurationDao;
    @Autowired
    private ITopicQuotaResourcesDao topicQuotaResourcesDao;
    @Autowired
    private TeacherFeign teacherFeign;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public HistoryTeacherAes findHistoryTeacherAesById(String id) {
        return mot.findById(id,HistoryTeacherAes.class);
    }
    public void saveHistoryTeacherAes(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAes.setCreateTime(DateUtil.now());
        historyTeacherAes.setId(sequenceId.nextId());
        mot.insert(historyTeacherAes);
    }

    public long findHistoryTeacherAesCountByCondition(HistoryTeacherAes historyTeacherAes) {
        return mot.getCollection(mot.getCollectionName(HistoryTeacherAes.class)).countDocuments(MongoKit.buildMatchDocument(historyTeacherAes));
    }
    public HistoryTeacherAes findOneHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(HistoryTeacherAes.class)).find(MongoKit.buildMatchDocument(historyTeacherAes));
       MongoKit.appendProjection(query,historyTeacherAes.getPager());
       return mot.getConverter().read(HistoryTeacherAes.class,query.first());
    }

    public void updateHistoryTeacherAes(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAes.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(historyTeacherAes.getId())), MongoKit.update(historyTeacherAes,false),HistoryTeacherAes.class);
    }
    public void updateHistoryTeacherAesForAll(HistoryTeacherAes historyTeacherAes) {
        historyTeacherAes.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(historyTeacherAes.getId())), MongoKit.update(historyTeacherAes,true),HistoryTeacherAes.class);
    }
    public void deleteHistoryTeacherAes(String id) {
        mot.remove(query(where("id").is(id)),HistoryTeacherAes.class);
    }
    public void deleteHistoryTeacherAesByCondition(HistoryTeacherAes historyTeacherAes) {
        mot.getCollection(mot.getCollectionName(HistoryTeacherAes.class)).deleteMany(MongoKit.buildMatchDocument(historyTeacherAes));
    }
    public void batchSaveHistoryTeacherAes(List<HistoryTeacherAes> historyTeacherAess){
        historyTeacherAess.forEach(historyTeacherAes -> historyTeacherAes.setId(sequenceId.nextId()));
        mot.insertAll(historyTeacherAess);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public HistoryTeacherAes findIsExist(HistoryTeacherAes historyTeacherAes) {
        long num = findHistoryTeacherAesCountByCondition(historyTeacherAes);
        if(num>0){
            historyTeacherAes.setCode("204");
            historyTeacherAes.setMsg("校本题库已存在");
        }
        if(num==0){
            historyTeacherAes.setCode("200");
            historyTeacherAes.setMsg("校本题库不存在");
        }
        return  historyTeacherAes;
    }

    public HistoryTeacherAes findIsDownload(HistoryTeacherAes historyTeacherAes) {
        long totalDay = totalDay(historyTeacherAes);//查询当前教师今日访问了几次
        long num2 = resultNum2(historyTeacherAes);//查询当前教师使用每日分配的额度
        if(num2==0){//该教师尚未分配访问额度
            historyTeacherAes.setCode("204");
            historyTeacherAes.setMsg("该教师尚未分配访问额度");
            return historyTeacherAes;
        }
        if(totalDay>=num2) {
            historyTeacherAes.setCode("204");
            historyTeacherAes.setMsg("今日余额不足不可以下载");
            return historyTeacherAes;
        }
        TopicQuotaResources temp3 = new TopicQuotaResources();//判断是否超出学校剩余额度
        temp3.setSchoolId(historyTeacherAes.getSchoolId());
        TopicQuotaResources result2 = topicQuotaResourcesDao.findOneTopicQuotaResourcesByCondition(temp3);
        long schoolTotal = (result2.getTotalMargin()-result2.getRemainingMargin());

        String time1 = result2.getClosingDate();//判断是否到截止日期
        String time2 = LocalDateTimeUtils.nowDateString();//现在的时间"yyyy-MM-dd"
        Date date1 = DateUtil.parse(time1);
        Date date2 = DateUtil.parse(time2);
        if(date1.before(date2)){
            historyTeacherAes.setCode("204");
            historyTeacherAes.setMsg("已到截止日期，不可使用");
            return historyTeacherAes;
        }
        if(schoolTotal<=0){
            historyTeacherAes.setCode("204");
            historyTeacherAes.setMsg("余额不足，不可以下载");
            return historyTeacherAes;
        }else{
            historyTeacherAes.setCode("200");
            historyTeacherAes.setMsg("余额充足可以继续下载");
            return historyTeacherAes;
        }

    }

    private long totalDay(HistoryTeacherAes historyTeacherAes){//查询当前教师今日访问了几次
        HistoryTeacherAes temp1 = createHistoryTeacherAes(historyTeacherAes);//查询当前教师今日访问了几次
        String start = DateUtil.beginOfDay(new Date()).toString();
        String end = DateUtil.endOfDay(new Date()).toString();
        temp1.getPager().setRangeArray(new String[]{start,end});
        temp1.getPager().setRangeField("createTime");
        return findHistoryTeacherAesCountByCondition(temp1);
    }

    private HistoryTeacherAes createHistoryTeacherAes(HistoryTeacherAes historyTeacherAes){//创建访问实体
        HistoryTeacherAes temp1 = new HistoryTeacherAes();
        temp1.setSchoolId(historyTeacherAes.getSchoolId());
        temp1.setTeacherId(historyTeacherAes.getTeacherId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setSortOrder("desc");
        pager.setPaging(false);
        temp1.setPager(pager);
        return temp1;
    }

    private long resultNum2(HistoryTeacherAes historyTeacherAes){//查询当前教师使用每日分配的额度
        TeacherAccessConfiguration teacherAccessConfiguration = new TeacherAccessConfiguration();//查询当前教师使用每日分配的额度
        teacherAccessConfiguration.setSchoolId(historyTeacherAes.getSchoolId());
        teacherAccessConfiguration.setTeacherId(historyTeacherAes.getTeacherId());
        TeacherAccessConfiguration result = teacherAccessConfigurationDao.findOneTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
        if(result!=null){
           return result.getDailyVisits();
        }else{
            return 0L;//老师分配每日额度
        }

    }


    public HistoryTeacherAes findByTeacherIdVist(HistoryTeacherAes historyTeacherAes) {
        TopicQuotaResources temp1 = new TopicQuotaResources();
        temp1.setSchoolId(historyTeacherAes.getSchoolId());
        topicQuotaResourcesDao.updateTopicQuotaResourcesByOne(temp1);
        String name = teacherFeign.findTeacherById(historyTeacherAes.getTeacherId()).getName();//查找教師名字
        historyTeacherAes.setTeacherName(name);
        historyTeacherAes.setTeacherId(historyTeacherAes.getTeacherId());
        historyTeacherAes.setSchoolId(historyTeacherAes.getSchoolId());
        historyTeacherAes.setTopicId(historyTeacherAes.getTopicId());
        //查找这个老师最大访问数据
//        HistoryTeacherAes temp = createHistoryTeacherAes(historyTeacherAes);
//        HistoryTeacherAes temp2 = findOneHistoryTeacherAesByCondition(temp);
//        int num = 1;
//        if(temp2!=null){
//            num = temp2.getNumTeacherVisits()+1;
//        }
        int num = 0;
        historyTeacherAes.setNumTeacherVisits(num);
        saveHistoryTeacherAes(historyTeacherAes);
        return historyTeacherAes;
    }

    public List<HistoryTeacherAes> findHistoryTeacherAesListByCondition(HistoryTeacherAes historyTeacherAes) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(HistoryTeacherAes.class)).find(MongoKit.buildMatchDocument(historyTeacherAes));
        Pager pager = historyTeacherAes.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<HistoryTeacherAes> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(HistoryTeacherAes.class, document)));
        return list;

    }


    public List<HistoryTeacherAes> findHistoryTeacherAesCountByCondition4Like(HistoryTeacherAes historyTeacherAes) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(historyTeacherAes.getSchoolId()));//学校id
        if(historyTeacherAes.getTeacherName()!=null){
            criterias.add(Criteria.where("teacherName").regex(historyTeacherAes.getTeacherName()));
        }
        if(historyTeacherAes.getPager()!=null && historyTeacherAes.getPager().getRangeArray()!=null){
            criterias.add(where("createTime").gte(historyTeacherAes.getPager().getRangeArray()[0]));
            criterias.add(where("createTime").lte(historyTeacherAes.getPager().getRangeArray()[1]));
        }
        Aggregation aggregation = Aggregation.newAggregation(//按照访问量来排序
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.group("teacherId").count().as("numTeacherVisits"),Aggregation.project().
                        and("_id").as("teacherId").and("numTeacherVisits").as("numTeacherVisits").andExclude("_id"),
                Aggregation.sort(new Sort(Sort.Direction.DESC,"numTeacherVisits")));

        AggregationResults<HistoryTeacherAes> outputTypeCount =
                mot.aggregate(aggregation, HistoryTeacherAes.class, HistoryTeacherAes.class);
        List<HistoryTeacherAes> historyTeacherAesList = outputTypeCount.getMappedResults();
        return historyTeacherAesList;
    }

    /**
     *  db.historyTeacherAes.aggregate([
     *     {
     *         $match: {
     *             "schoolId": "1741058039675183104",
     *             "createTime": {
     *                 "$gte": "2018-09-01 00:00:00",
     *                 "$lte": "2019-07-24 00:00:00"
     *             },
     * 						"teacherName":{"$regex":"小"}
     *         },
     *     },
     *     {
     *         $group: {
     *             _id: {
     *                 teacherId: "$teacherId",
     *             },
     * 						teacherName:{$first:"$teacherName"},
     *             'numTeacherVisits': {
     *                 $sum: 1
     *             }
     *         }
     *     },
     *                {"$sort": {"numTeacherVisits":-1}},
     *        {"$project":{teacherId:"$_id.teacherId",teacherName:1,numTeacherVisits:1,_id:0}}
     * ])
     * @param historyTeacherAes
     * @return
     */
    public List<Map<String, Object>> findHistoryTeacherAesListByCondition4Like(HistoryTeacherAes historyTeacherAes) {
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        matchOpts.append("schoolId",historyTeacherAes.getSchoolId());
        if(historyTeacherAes.getPager()!=null && historyTeacherAes.getPager().getRangeArray()!=null){
            matchOpts.append("createTime",new Document("$gte",historyTeacherAes.getPager().getRangeArray()[0]).
                    append("$lte",historyTeacherAes.getPager().getRangeArray()[1]));
        }
        if(historyTeacherAes.getTeacherName()!=null){//教师名单
            matchOpts.append("teacherName",new Document("$regex",historyTeacherAes.getTeacherName()));
        }
        if(historyTeacherAes.getTeacherId()!=null){//教师id
            matchOpts.append("teacherId",historyTeacherAes.getTeacherId());
        }
        Document match1 = new Document("$match",matchOpts);//基本条件(有括号就new一个document)
        Document match2  = new Document("$group",new Document("_id",new Document("teacherId","$teacherId"))
            .append("teacherName", new Document("$first","$teacherName"))
            .append("numTeacherVisits", new Document("$sum",1)));
        Document match3 = new Document("$sort",new Document("numTeacherVisits",-1));
        Document match4 = new Document("teacherId","$_id.teacherId").append("teacherName", 1)
                .append("numTeacherVisits", 1).append("_id", 0);
        operas.addAll(Arrays.asList(match1,match2,match3));
        //分页处理
        if(historyTeacherAes.getPager()!=null && historyTeacherAes.getPager().getPaging()){
            operas.add(new Document("$skip",historyTeacherAes.getPager().getBeginRow()));
            operas.add(new Document("$limit",historyTeacherAes.getPager().getPageSize()));
        }

        MongoCollection<Document> collection = mot.getCollection("historyTeacherAes");//将查出的结果映射到对应实体中
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        return rlist;

    }

}
