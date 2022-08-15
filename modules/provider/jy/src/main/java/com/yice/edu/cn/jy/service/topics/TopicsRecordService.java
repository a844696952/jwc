package com.yice.edu.cn.jy.service.topics;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.homework.StuHomeRecordVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class TopicsRecordService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
  /*  @Autowired
    private SemesterFeign semesterFeign;*/

    public TopicsRecord findTopicsRecordById(String id) {
        return mot.findById(id,TopicsRecord.class);
    }
    public void saveTopicsRecord(TopicsRecord topicsRecord) {
        topicsRecord.setId(sequenceId.nextId());
        mot.insert(topicsRecord);
    }
    public List<TopicsRecord> findTopicsRecordListByCondition(TopicsRecord topicsRecord) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TopicsRecord.class)).find(MongoKit.buildMatchDocument(topicsRecord));
        Pager pager = topicsRecord.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<TopicsRecord> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(TopicsRecord.class, document)));
        return list;
    }
    public long findTopicsRecordCountByCondition(TopicsRecord topicsRecord) {
        return mot.getCollection(mot.getCollectionName(TopicsRecord.class)).countDocuments(MongoKit.buildMatchDocument(topicsRecord));
    }
    public TopicsRecord findOneTopicsRecordByCondition(TopicsRecord topicsRecord) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TopicsRecord.class)).find(MongoKit.buildMatchDocument(topicsRecord));
        MongoKit.appendProjection(query,topicsRecord.getPager());
        return mot.getConverter().read(TopicsRecord.class,query.first());
    }

    public void updateTopicsRecord(TopicsRecord topicsRecord) {
        mot.updateFirst(query(where("id").is(topicsRecord.getId())), MongoKit.update(topicsRecord),TopicsRecord.class);
    }
    public void deleteTopicsRecord(String id) {
        mot.remove(query(where("id").is(id)),TopicsRecord.class);
    }
    public void deleteTopicsRecordByCondition(TopicsRecord topicsRecord) {
        mot.getCollection(mot.getCollectionName(TopicsRecord.class)).deleteMany(MongoKit.buildMatchDocument(topicsRecord));
    }
    public void batchSaveTopicsRecord(List<TopicsRecord> topicsRecords){
        topicsRecords.forEach(topicsRecord -> topicsRecord.setId(sequenceId.nextId()));
        mot.insertAll(topicsRecords);
    }

    /**
     * 查询每个学生的线上作业正确率
     * @param homeworkSqId
     */
    public List<StuHomeRecordVo> queryHomeworkCorrectRateByHomeworkId(String homeworkSqId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("channelId").is(homeworkSqId).and("correct").is(Constant.TOPICS.CORRECT)),
                Aggregation.group("id","studentId").count().as("rightItemNum"));

        AggregationResults<StuHomeRecordVo> outputTypeCount =
                mot.aggregate(aggregation, "topicsRecord", StuHomeRecordVo.class);

        return outputTypeCount.getMappedResults();
    }

    public List<TopicsRecord> findTopicsRecordBy4Like(TopicsRecord topicsRecord) {
        Aggregation aggregation = Aggregation.newAggregation(
                TopicsRecord.class,
                Aggregation.match(where("channelId").in(topicsRecord.getChannelId())
                        .and("channelType").in(topicsRecord.getChannelType())
                        .and("topicsObj._id").in(topicsRecord.getTopicsObj().getId())
                        .and("studentId").in(topicsRecord.getStudentId())));
        AggregationResults<TopicsRecord> outputTypeCount =
                mot.aggregate(aggregation, "topicsRecord", TopicsRecord.class);

        return outputTypeCount.getMappedResults();
    }

    /*//获取学年
    private String[] getStudyYear(Homework homework){
        String[] arry = new String[2];
        Semester s = new Semester();
        List<Semester> list1 = semesterFeign.findSemesterSchoolYearTime(s);
        if(list1==null||list1.size()==0){//学校没有建立新学年
            arry=null;
        }
        List<String> list = new ArrayList<>();
        list.add(list1.get(0).getStartTime());
        list.add(list1.get(1).getEndTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = DateUtil.parse(list.get(0));
        Date date2 = DateUtil.parse(list.get(1));
        arry[0] = sdf.format(DateUtil.beginOfDay(date1));//学年开始时间
        arry[1] = sdf.format(DateUtil.endOfDay(date2));//学年结束时间
        return arry;
    }*/



}