package com.yice.edu.cn.dm.service.modeManage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 模式任务服务接口
 */
@Service
public class ModeTaskService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增任务
     * @param examTask
     * @return
     */
    public ExamTask addExamTask(ExamTask examTask){
        if(Objects.nonNull(examTask)){
            examTask.setCreateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
          return   mongoTemplate.insert(examTask);
        }
        return null;
    }

    /**
     * 查询当前学校的上课模式是否存在
     * @param schoolId
     * @return
     */
    public List<ExamTask> findClassModeTask(String schoolId){
        if(StringUtils.isNotEmpty(schoolId)){
          return   mongoTemplate.find(Query.query(where("schoolId").is(schoolId).and("taskType").is(3)),ExamTask.class);
        }
        return null;
    }

    /**
     * 删除任务
     * @param id
     * @return
     */
    public boolean deleteExamTask(String id){
        if(StringUtils.isNotEmpty(id)){
            ExamTask examTask=new ExamTask();
            examTask.setTaskId(id);
            mongoTemplate.remove(examTask);
        }
        return true;
    }


    /**
     * 修改任务类型
     * @param examTask
     * @return
     */
    public boolean updateExamTask(ExamTask examTask){
        if(Objects.nonNull(examTask) && StringUtils.isNotEmpty(examTask.getTaskId())){
            mongoTemplate.updateFirst(Query.query(where("_id").is(examTask.getTaskId())),new Update().set("taskName",examTask.getTaskName())
                    .set("beginTime",examTask.getBeginTime()).set("endTime",examTask.getEndTime()).
                            set("createDate", DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"))
                    .set("status",examTask.getStatus()),ExamTask.class);
        }
        return true;
    }

    /**
     * 更新上课模式状态
     * @param examTask
     * @return
     */
    public boolean updateClassModeTask(ExamTask examTask){
        if(Objects.nonNull(examTask) && StringUtils.isNotEmpty(examTask.getSchoolId()) && Objects.nonNull(examTask.getStatus())){
            mongoTemplate.updateFirst(Query.query(where("schoolId").is(examTask.getSchoolId()).and("taskType").is(3)),
                    new Update().set("status",examTask.getStatus()),ExamTask.class);
        }
        return true;
    }


    /**
     *更新上课模式任务调度
     * @param examTask
     * @return
     */
    public boolean updateClassModeTaskSchedule(ExamTask examTask){
        if(Objects.nonNull(examTask)){
            if(StringUtils.isNotEmpty(examTask.getSchoolId())){
                mongoTemplate.updateFirst(Query.query(where("schoolId").is(examTask.getSchoolId()).and("taskType").is(3)),
                        new Update().set("pushDate",DateUtil.format(DateTime.now(),"yyyy-MM-dd")),ExamTask.class);
            }
        }
        return true;
    }

    /**
     * 更新并推送上课模式消息
     * @param examTasks
     * @return
     */
    public boolean updateAndPushClassMode(List<ExamTask> examTasks){
        examTasks.forEach(x->{
            updateClassModeTaskSchedule(x);
            sendModeMsg(x);
        });
        return true;
    }



    /**
     * 获取当前时间
     * @return
     */
    private String getNow(){
        return DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 查询要执行的任务
     * @return
     */
    public List<ExamTask> findExamTaskByCondition(){
        List<Criteria> list=new ArrayList<>();
        list.add(Criteria.where("endTime").gte(getNow()).and("beginTime").lte(getNow()).and("status").is(0));
        Query query=Query.query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
       return mongoTemplate.find(query,ExamTask.class);
    }


    /**
     *根据条件查询当前上课模式任务列表
     * @return
     */
    public List<ExamTask> findClassTaskByCondition(){
        String format = DateUtil.format(DateTime.now(), "yyyy-MM-dd");
        List<Criteria> list=new ArrayList<>();
        list.add(Criteria.where("beginTime").gte(getNow()).and("taskType").is(3).and("status").is(1));
        list.add(new Criteria().orOperator(Criteria.where("pushDate").is(null),Criteria.where("pushDate").lt(format)));
        Query query=Query.query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
        return mongoTemplate.find(query,ExamTask.class);
    }


    /**
     * 发送关闭消息
     * @param taskId
     * @return
     */
    public boolean sendCloseAndUpdate(String taskId){
        if(StringUtils.isNotEmpty(taskId)){
            List<ExamTask> examTask = mongoTemplate.find(Query.query(where("_id").is(taskId)), ExamTask.class);
            if(CollUtil.isNotEmpty(examTask)){
                examTask.forEach(x->{
                    x.setStatus(2);
                    updateExamTask(x);
                    sendModeMsg(x);
                });
            }
        }
        return true;
    }

    /**
     * 向移动端推送并更新状态
     * @param list
     * @return
     */
    public boolean sendAndUpdate(List<ExamTask> list){
        if(CollUtil.isNotEmpty(list)){
            list.forEach(x->{
                if(Objects.nonNull(x)){
                    x.setStatus(1);
                    updateExamTask(x);
                    //推送
                    sendModeMsg(x);
                }
            });
        }
        return true;
    }

    /**
     * 检测当前任务是否存在冲突
     * @param beginTime
     * @param endTime
     * @return
     */
    public boolean checkDateTimeReply(String id,String beginTime,String endTime,String gradeId,String schoolId){
        boolean isReplay=false;
        if(StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)){
            List<ExamTask> all;
            if(StringUtils.isNotEmpty(id)){
                all=mongoTemplate.find(Query.query(where("_id").ne(id).and("status").ne(2).and("schoolId").is(schoolId)),ExamTask.class);
            }else{
                all=mongoTemplate.find(Query.query(where("status").ne(2).and("schoolId").is(schoolId)),ExamTask.class);
            }
            isReplay = isReplay(beginTime, endTime, isReplay, all);
        }
        return isReplay;
    }

    /**
     * 检测提交时间是否于系统其他模式时间冲突(通知、考试模式）
     * @param beginTime
     * @param endTime
     * @param isReplay
     * @param all
     * @return
     */
    private boolean isReplay(String beginTime, String endTime, boolean isReplay, List<ExamTask> all) {
        if (CollUtil.isNotEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                if (StringUtils.isNotEmpty(all.get(i).getBeginTime()) && StringUtils.isNotEmpty(all.get(i).getEndTime())) {
                    isReplay = checkDate(beginTime, endTime, all.get(i).getBeginTime(), all.get(i).getEndTime());
                    if (isReplay) {
                        break;
                    }
                }
            }
        }
        return isReplay;
    }

    /**
     * checkDateTimeReply()方法的重载,多传一个gradeId,检测考试模式的任务是否存在冲突
     * @param id
     * @param beginTime
     * @param endTime
     * @param gradeId
     * @return
     */
    public boolean checkDateTimeReply(String id,String beginTime,String endTime,String gradeId){
        boolean isReplay=false;
        if(StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)){
            List<ExamTask> all;
            if(StringUtils.isNotEmpty(id)){
                all=mongoTemplate.find(Query.query(where("_id").ne(id).and("gradeId").is(gradeId).and("status").ne(2)),ExamTask.class);
            }else{
                all= mongoTemplate.find(Query.query(where("gradeId").is(gradeId).and("status").ne(2)),ExamTask.class);
            }
            isReplay = isReplay(beginTime, endTime, isReplay, all);
        }
        return isReplay;
    }

    /**
     * 查询通知任务列表
     * @param beginTime
     * @param endTime
     * @param schoolId
     * @return
     */
    public boolean checkNotification(String beginTime,String endTime,String schoolId){
        boolean res=false;
        if(StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(schoolId)){
            List<ExamTask> list=mongoTemplate.find(Query.query(where("status").ne(2).and("schoolId").is(schoolId).and("taskType").is(1)),ExamTask.class);
            res = isReplay(beginTime, endTime, res, list);
        }
        return res;
    }


    /**
     * 比较两个时间段是否有交集
     * @param beginTime
     * @param endTime
     * @param compareBeginTime
     * @param compareEndTime
     * @return
     */
    private boolean checkDate(String beginTime,String endTime,String compareBeginTime,String compareEndTime){
        DateTime beginDate = DateUtil.parse(beginTime);
        DateTime endDate=DateUtil.parse(endTime);
        DateTime compareBeginDate=DateUtil.parse(compareBeginTime);
        DateTime compareEndDate= DateUtil.parse(compareEndTime);
        if(beginDate.isBeforeOrEquals(compareBeginDate) && endDate.isAfterOrEquals(compareBeginDate)){
            return  true;
        }else if(beginDate.isAfterOrEquals(compareBeginDate) && beginDate.isBeforeOrEquals(compareEndDate)){
            return true;
        }else{
            return  false;
        }
    }


    /**
     * 向移动端推送消息
     * @param examTask
     */
    public void sendModeMsg(ExamTask examTask){
        String[] userNames = examTask.getUserName().toArray(new String[0]);
        Push push = buildModePushMsg(Push.newBuilder(JpushApp.ECC).setAlias(userNames),examTask);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }

    /**
     * 封装极光推送消息信息
     * @param builder
     * @param examTask
     * @return
     */
    private Push buildModePushMsg(Push.Builder builder,ExamTask examTask){
         return builder.setMessage(Push.Message.newBuilder()
                .setMsgContent(JSONUtil.parse(examTask).toString())
                .setTitle("模式推送消息")
                .setAlert("收到模式切换消息")
                .setContentType(ContentType.TEXT_PLAIN.toString())
                .setExtras(Extras.newBuilder()
                        .setType(Extras.DM_MSG).setId(examTask.getTaskId()).setJSON(JSONUtil.parse(examTask).toString()).build())
                .build()).build();
    }

}
