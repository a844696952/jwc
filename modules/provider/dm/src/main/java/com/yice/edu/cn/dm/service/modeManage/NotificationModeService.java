package com.yice.edu.cn.dm.service.modeManage;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.dm.dao.classCard.IDmClassCardDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NotificationModeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ModeTaskService modeTaskService;
    @Autowired
    private IDmClassCardDao dmClassCardDao;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public NotificationMode findNotificationModeById(String id) {
        return mot.findById(id,NotificationMode.class);
    }
    public int saveNotificationMode(NotificationMode notificationMode) {
        notificationMode.setId(sequenceId.nextId());
        notificationMode.setCreateTime(DateUtils.Nowss());
        Boolean flag = select(notificationMode,0);
        if(flag){
            return 0;
        }else if(modeTaskService.checkDateTimeReply(null,notificationMode.getStartDate(),notificationMode.getEndDate(),null,notificationMode.getSchoolId())){
            return 2;
        }else{
            ExamTask examTask = new ExamTask();
            examTask.setCreateTime(DateUtils.Nowss());
            examTask.setTaskId(sequenceId.nextId());
            examTask.setBeginTime(notificationMode.getStartDate());
            examTask.setEndTime(notificationMode.getEndDate());
            examTask.setCreateDate(DateUtil.now());
            examTask.setTaskType(1);
            examTask.setTaskName(notificationMode.getTitle());
            examTask.setStatus(0);
            examTask.setSchoolId(notificationMode.getSchoolId());
            List<DmClassCard> dmClassCards = dmClassCardDao.selectUserNameListBySchoolId(notificationMode.getSchoolId());
            List<String> userName = new ArrayList<>(dmClassCards.size());
            dmClassCards.forEach(dmClassCard ->
                    userName.add(dmClassCard.getUserName())
            );
            examTask.setUserName(userName);
            notificationMode.setCreateDate(DateUtil.now());
            notificationMode.setTaskId(examTask.getTaskId());
            mot.insert(notificationMode);
            modeTaskService.addExamTask(examTask);
            return 1;

        }
    }
    public List<NotificationMode> findNotificationModeListByCondition(NotificationMode notificationMode) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(NotificationMode.class)).find(MongoKit.buildMatchDocument(notificationMode));
        Pager pager = notificationMode.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<NotificationMode> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(NotificationMode.class, document)));
        if (CollectionUtil.isNotEmpty(list)) {
            String nowDate = DateUtil.now();
            list.forEach(item -> {
                int one = nowDate.compareTo(item.getStartDate());
                int two = nowDate.compareTo(item.getEndDate());
                if (item.getShowStatus() == null) {
                    if (two > 0) {
                        item.setShowStatus(2);
                    }
                    if (one < 0) {
                        item.setShowStatus(1);
                    }
                    if (one > 0 && two < 0) {
                        item.setShowStatus(0);
                    }
                }
            });
        }
        return list;
    }

    public long findNotificationModeCountByCondition(NotificationMode notificationMode) {
        return mot.getCollection(mot.getCollectionName(NotificationMode.class)).countDocuments(MongoKit.buildMatchDocument(notificationMode));
    }
    public NotificationMode findOneNotificationModeByCondition(NotificationMode notificationMode) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(NotificationMode.class)).find(MongoKit.buildMatchDocument(notificationMode));
       MongoKit.appendProjection(query,notificationMode.getPager());
       return mot.getConverter().read(NotificationMode.class,query.first());
    }

    public int updateNotificationMode(NotificationMode notificationMode) {
        Boolean flag = select(notificationMode,1);
        if(flag){
            return 0;
        }else if(modeTaskService.checkDateTimeReply(notificationMode.getTaskId(),notificationMode.getStartDate(),notificationMode.getEndDate(),null,notificationMode.getSchoolId())){
            return 2;
        }else{
            ExamTask examTask = new ExamTask();
            examTask.setTaskId(notificationMode.getTaskId());
            examTask.setBeginTime(notificationMode.getStartDate());
            examTask.setEndTime(notificationMode.getEndDate());
            examTask.setTaskType(1);
            examTask.setTaskName(notificationMode.getTitle());
            modeTaskService.updateExamTask(examTask);
            mot.updateFirst(query(where("id").is(notificationMode.getId())), MongoKit.update(notificationMode),NotificationMode.class);
            return 1;
        }
    }

    private Boolean select(NotificationMode notificationMode, int isUpdate) {
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("schoolId").is(notificationMode.getSchoolId()));
        criteriaList.add(Criteria.where("showStatus").ne(3));
        if(isUpdate==1){
            criteriaList.add(Criteria.where("_id").ne(notificationMode.getId()));
        }
        Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        List<NotificationMode> notificationModeList = mot.find(query, NotificationMode.class);
        ArrayList<String> dateList = new ArrayList<>(notificationModeList.size() + 1);
        notificationModeList.forEach(n ->
                dateList.add(n.getStartDate()+"~"+n.getEndDate())
        );
        dateList.add(notificationMode.getStartDate()+"~"+notificationMode.getEndDate());
        return DateUtils.DateRangeJudge(dateList);

    }

    public void deleteNotificationMode(String id) {
        mot.remove(query(where("id").is(id)),NotificationMode.class);
    }

    public void deleteNotificationModeByCondition(NotificationMode notificationMode) {
        mot.getCollection(mot.getCollectionName(NotificationMode.class)).deleteMany(MongoKit.buildMatchDocument(notificationMode));
        modeTaskService.deleteExamTask(notificationMode.getTaskId());
    }
    public void batchSaveNotificationMode(List<NotificationMode> notificationModes){
        notificationModes.forEach(notificationMode -> notificationMode.setId(sequenceId.nextId()));
        mot.insertAll(notificationModes);
    }

    public List<NotificationMode> findNotificationModeByNowTime(String schoolId) {
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("schoolId").is(schoolId));
        criteriaList.add(Criteria.where("endDate").gte(DateUtil.now()));
        criteriaList.add(Criteria.where("showStatus").ne(3));
        Query query = query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        List<NotificationMode> notificationModeList = mot.find(query, NotificationMode.class);
        NotificationMode notificationMode = new NotificationMode();
        if(notificationModeList.size()!=0){
            notificationModeList.forEach(notificationMode1 ->notificationMode1.setTimeStamp(System.currentTimeMillis()) );
            return notificationModeList;
        }
        notificationMode.setTimeStamp(System.currentTimeMillis());
        notificationModeList.add(notificationMode);
        return notificationModeList;
    }

    public void closeNotificationMode(String id) {
        NotificationMode detail = findNotificationModeById(id);
        if (detail != null) {
            detail.setShowStatus(3);
            mot.updateFirst(query(where("id").is(id)), MongoKit.update(detail), NotificationMode.class);
            modeTaskService.sendCloseAndUpdate(detail.getTaskId());
        }
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
