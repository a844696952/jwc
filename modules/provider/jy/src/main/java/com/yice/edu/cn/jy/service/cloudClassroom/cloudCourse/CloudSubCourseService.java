package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CloudSubCourseService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudSubCourse findCloudSubCourseById(String id) {
        return mot.findById(id,CloudSubCourse.class);
    }
    public void saveCloudSubCourse(CloudSubCourse cloudSubCourse) {
        cloudSubCourse.setCreateTime(DateUtil.now());
        cloudSubCourse.setId(sequenceId.nextId());
        mot.insert(cloudSubCourse);
    }
    public List<CloudSubCourse> findCloudSubCourseListByCondition(CloudSubCourse cloudSubCourse) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).find(MongoKit.buildMatchDocument(cloudSubCourse));
        Pager pager = cloudSubCourse.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudSubCourse> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudSubCourse.class, document)));
        return list;
    }
    public long findCloudSubCourseCountByCondition(CloudSubCourse cloudSubCourse) {
        return mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).countDocuments(MongoKit.buildMatchDocument(cloudSubCourse));
    }
    public CloudSubCourse findOneCloudSubCourseByCondition(CloudSubCourse cloudSubCourse) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).find(MongoKit.buildMatchDocument(cloudSubCourse));
       MongoKit.appendProjection(query,cloudSubCourse.getPager());
       return mot.getConverter().read(CloudSubCourse.class,query.first());
    }

    public void updateCloudSubCourse(CloudSubCourse cloudSubCourse) {
        mot.updateFirst(query(where("id").is(cloudSubCourse.getId())), MongoKit.update(cloudSubCourse,false),CloudSubCourse.class);
    }
    public void updateCloudSubCourseForAll(CloudSubCourse cloudSubCourse) {
        mot.updateFirst(query(where("id").is(cloudSubCourse.getId())), MongoKit.update(cloudSubCourse,true),CloudSubCourse.class);
    }
    public void deleteCloudSubCourse(String id) {
        mot.remove(query(where("id").is(id)),CloudSubCourse.class);
    }
    public void deleteCloudSubCourseByCondition(CloudSubCourse cloudSubCourse) {
        mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).deleteMany(MongoKit.buildMatchDocument(cloudSubCourse));
    }
    public void batchSaveCloudSubCourse(List<CloudSubCourse> cloudSubCourses){
        cloudSubCourses.forEach(cloudSubCourse -> {
            cloudSubCourse.setCreateTime(DateUtil.now());
            cloudSubCourse.setId(sequenceId.nextId());
            //初始化待上课状态
            cloudSubCourse.setStatus(1);
            cloudSubCourse.setBroadcastCode(DigestUtil.md5Hex(cloudSubCourse.getId()));
        });
        mot.insertAll(cloudSubCourses);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * 根据主课程id查询下属子课程id集合
     * @param id
     * @return
     */
    public List<String> findCloudSubCourseIdListByCloudCourseId(String id) {
        List<AggregationOperation> operationList = CollUtil.newArrayList();
        operationList.add(Aggregation.match(Criteria.where("cloudCourse._id").is(id)));
        operationList.add(Aggregation.project("id"));
        List<CloudSubCourse> list = mot.aggregate(Aggregation.newAggregation(CloudSubCourse.class, operationList), CloudSubCourse.class).getMappedResults();
        List<String> idList = list.stream().map(cloudSubCourse -> cloudSubCourse.getId()).collect(Collectors.toList());
        return idList;
    }

    /**
     * 根据授课老师查询现在主课程id集合
     * @param teacher
     * @return
     */
    public List<String> findCloudCourseIdListByTeacher(Teacher teacher) {
        List<AggregationOperation> operationList = CollUtil.newArrayList();
        operationList.add(Aggregation.match(Criteria.where("teacher._id").is(teacher.getId())));
        operationList.add(Aggregation.group("cloudCourse.id"));
        List<CloudCourse> list = mot.aggregate(Aggregation.newAggregation(CloudSubCourse.class, operationList), CloudCourse.class).getMappedResults();
        List<String> idList = list.stream().map(cloudCourse -> cloudCourse.getId()).collect(Collectors.toList());
        return idList;
    }

    /**
     * 根据子课程id删除课程
     * @param cloudSubCourseIdList
     */
    public void deleteCloudSubCourseByIds(List<String> cloudSubCourseIdList) {
        Query query = Query.query(Criteria.where("id").in(cloudSubCourseIdList));
        mot.remove(query,CloudSubCourse.class);
    }

    /**
     * 批量修改子课程信息，如果没有该子课程执行插入
     * @param cloudSubCourseList
     */
    public void batchUpsertCloudSubCourse(List<CloudSubCourse> cloudSubCourseList) {
        for (CloudSubCourse cloudSubCourse : cloudSubCourseList) {
            if(cloudSubCourse.getId()==null){
                cloudSubCourse.setCreateTime(DateUtil.now());
                cloudSubCourse.setId(sequenceId.nextId());
                //初始化待上课状态
                cloudSubCourse.setStatus(1);
                cloudSubCourse.setBroadcastCode(DigestUtil.md5Hex(cloudSubCourse.getId()));
            }
            mot.upsert(query(where("id").is(cloudSubCourse.getId())),MongoKit.update(cloudSubCourse,false),CloudSubCourse.class);
        }

    }

    public List<CloudSubCourse> findCloudCourseValid(CloudSubCourse cloudSubCourse) {
        Document matchDoc = MongoKit.buildMatchDocument(cloudSubCourse);
        //当天的云课堂都是有效的云课堂
        String now = DateUtil.format(new Date(), "yyyy-MM-dd ")+"00:00";
        matchDoc.append("endTime", new Document("$gte", now));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).find(matchDoc);

        List<CloudSubCourse> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudSubCourse.class, document)));
        return list;
    }

    /**
     * 查询当前用户所参与的课程列表
     * @param cloudSubCourse
     * @return
     */
    public List<CloudSubCourse> findCloudSubCourseListByLoginIdAndNow(CloudSubCourse cloudSubCourse) {
        Document matchDoc = MongoKit.buildMatchDocument(cloudSubCourse);
        String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
        matchDoc.append("startTime", new Document("$lte", now)).append("endTime", new Document("$gte", now)).append("status",new Document("$ne",3)).append("$or",
                Arrays.asList(new Document("cloudCourse.listenTeachers._id", cloudSubCourse.getLoginId()),
                        new Document("cloudCourse.otherSchoolAccounts._id", cloudSubCourse.getLoginId()),
                        new Document("teacher._id",cloudSubCourse.getLoginId())));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourse.class)).find(matchDoc);
        Pager pager = cloudSubCourse.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<CloudSubCourse> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudSubCourse.class, document)));
        return list;
    }

    public void pushCloudSubCourse10(){
        List<CloudSubCourse> cloudSubCourses = this.pushCloudSubCourse10List();
        cloudSubCourses.forEach(s->{
            String[] teacherId = new String[]{s.getTeacher().getId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherId, "云课堂情况", "您有云课堂课程即将开始，请做好准备，点击查看详情", Extras.TAP_CLOUDSUBCOURSE,s.getId());
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        });
    }

    public void pushCloudSubCourse21(){
        List<CloudSubCourse> cloudSubCourses = this.pushCloudSubCourse21List();
        cloudSubCourses.forEach(s->{
            String[] teacherId = new String[]{s.getTeacher().getId()};
            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherId, "云课堂情况", "您有云课堂课程即将开始，请做好准备，点击查看详情", Extras.TAP_CLOUDSUBCOURSE,s.getId());
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        });
    }


    public List<CloudSubCourse> pushCloudSubCourse21List() {
        Date beginOfDay = DateUtil.beginOfDay(DateUtil.tomorrow());
        DateTime newDate3 = DateUtil.offsetHour(beginOfDay, 12);

        String start=  DateUtil.format(beginOfDay,"yyyy-MM-dd HH:mm:ss");
        String end=  DateUtil.format(newDate3,"yyyy-MM-dd HH:mm:ss");

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(where("startTime").gte(start));
        criterias.add(where("startTime").lte(end));
        Query query = query(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
        return mot.find(query, CloudSubCourse.class);
    }

    public List<CloudSubCourse> pushCloudSubCourse10List() {
        Date beginOfDay = DateUtil.beginOfDay(DateUtil.parse(DateUtil.now()));
        DateTime newDate3 = DateUtil.offsetHour(beginOfDay, 12);
        Date endOfDay = DateUtil.endOfDay(DateUtil.parse(DateUtil.now()));

        String start=  DateUtil.format(newDate3,"yyyy-MM-dd HH:mm:ss");
        String end=  DateUtil.format(endOfDay,"yyyy-MM-dd HH:mm:ss");

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(where("startTime").gte(start));
        criterias.add(where("startTime").lte(end));

        Query query = query(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
        return mot.find(query, CloudSubCourse.class);
    }


    private long StringToDateBetween(String dateStr1,String dateStr2){
        Date date1 = DateUtil.parse(dateStr1);
        Date date2 = DateUtil.parse(dateStr2);
        return DateUtil.between(date1, date2, DateUnit.DAY);
    }

    /**
     * 根据正在进行中和已结束状态的子课程数量
     * @param cloudSubCourse
     * @return
     */
    public long findOnGoingOrFinishCloudSubCourseCountByCondition(CloudSubCourse cloudSubCourse) {
        Criteria criteria = MongoKit.buildCriteria(cloudSubCourse, null);
        criteria.and("status").in(Arrays.asList(2,3));
        return mot.count(Query.query(criteria),CloudSubCourse.class);
    }
}
