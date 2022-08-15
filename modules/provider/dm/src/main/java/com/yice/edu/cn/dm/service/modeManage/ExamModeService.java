package com.yice.edu.cn.dm.service.modeManage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamInfo;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.dm.dao.classCard.IDmClassCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 *考试模式
 */
@Service
public class ExamModeService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private IDmClassCardDao dmClassCardDao;

    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private ModeTaskService modeTaskService;


    /**
     * 新增考试信息
     * @param examMode
     * @return
     */
    public ExamMode saveExamMode(ExamMode examMode){

        String beginTime = DateUtils.getDateByMinute(examMode.getExamBeginTime(), examMode.getBeforeMinute(), false);
        String endTime = DateUtils.getDateByMinute(examMode.getExamEndTime(), examMode.getAfterMinute(), true);
        if(modeTaskService.checkDateTimeReply(null,beginTime,endTime,examMode.getGradeId()) || modeTaskService.checkNotification(beginTime,endTime,examMode.getSchoolId())){
            return null;
        }
        examMode.setId(sequenceId.nextId());
        examMode.setCreateTime(DateUtil.now());
        examMode.setExamBeginTime(beginTime);
        examMode.setExamEndTime(endTime);
        if(Objects.nonNull(examMode)){
            examMode.setCreateDate(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
            //绑定设备Id
            if(CollectionUtil.isNotEmpty(examMode.getClasses())){
                List<ExamInfo> examInfoList = new ArrayList<>();
                List<String> userName = new ArrayList<>();
                montage(examMode,examInfoList,userName);
                ExamTask task = new ExamTask();
                String taskId = sequenceId.nextId();
                task.setTaskId(taskId);
                task.setTaskName(examMode.getExamName());
                task.setBeginTime(beginTime);
                task.setGradeId(examMode.getGradeId());
                task.setEndTime(endTime);
                task.setCreateDate(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
                task.setTaskType(2);
                task.setStatus(0);
                task.setUserName(userName);
                task.setSchoolId(examMode.getSchoolId());
                mongoTemplate.insert(task);
                examMode.setTaskId(taskId);
            }
            return mongoTemplate.insert(examMode);
        }
        return null;
    }


    /**
     *
     考试模式表更新
     */
    public ExamMode updateExamMode(ExamMode examMode) {
        String beginTime = DateUtils.getDateByMinute(examMode.getExamBeginTime(), examMode.getBeforeMinute(), false);
        String endTime = DateUtils.getDateByMinute(examMode.getExamEndTime(), examMode.getAfterMinute(), true);
        if(modeTaskService.checkDateTimeReply(examMode.getTaskId(),beginTime,endTime,examMode.getGradeId()) || modeTaskService.checkNotification(beginTime,endTime,examMode.getSchoolId())){
            return null;
        }
        if(Objects.nonNull(examMode) && CollectionUtil.isNotEmpty(examMode.getClasses())){
            examMode.setExamBeginTime(beginTime);
            examMode.setExamEndTime(endTime);
            List<ExamInfo> examInfoList = new ArrayList<>();
            List<String> userName = new ArrayList<>();
            montage(examMode,examInfoList,userName);
            //更新任务 首先拿出来taskId,查询task任务表
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(examMode.getTaskId()));
            Update update = new Update();
            update.set("taskName", examMode.getExamName());
            update.set("beginTime",beginTime);
            update.set("endTime",endTime);
            mongoTemplate.updateFirst(query,update,ExamTask.class);
            mongoTemplate.save(examMode);
        }
        return examMode;
    }

    public ExamMode updateExamInfo(ExamMode examMode) {
        if(Objects.nonNull(examMode)){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(examMode.getId()));
            Update update = new Update();
            update.set("examInfo", examMode.getExamInfo());
            update.set("invigilatorCount",examMode.getInvigilatorCount());
            update.set("studentCount",examMode.getStudentCount());
            update.set("fileName",examMode.getFileName());
            mongoTemplate.updateFirst(query,update,ExamMode.class);
            return examMode;
        }
        return null;
    }




    private void closeExam(ExamMode examMode){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(examMode.getId()));
        Update update = new Update();
        update.set("status", -1);
        mongoTemplate.updateFirst(query,update,ExamMode.class);
    }



    /**
     * 解析前端参数并拼接
     * */
    private void montage(ExamMode examMode,List<ExamInfo> examInfoList,List<String> userName){
        examMode.getClasses().forEach(c->{
            DmClassCard dmClassCard = new DmClassCard();
            dmClassCard.setSchoolId(examMode.getSchoolId());
            dmClassCard.setGradeId(examMode.getGradeId());
            //将班牌查出来
            dmClassCard.setClassId(c.getId());
            DmClassCard card = dmClassCardDao.findOneDmClassCardByCondition(dmClassCard);
            //考试信息
            ExamInfo examInfo = new ExamInfo();
            examInfo.setClassId(c.getId());
            examInfo.setClassName(examMode.getGradeName().concat(c.getNumber()).concat("班"));
            if(Objects.nonNull(card)){
                //设置用户名
                userName.add(card.getUserName());
                examInfo.setDeviceId(card.getUserName());
            }
            //科目信息
            examInfo.setSubjectList(examMode.getSubjectList());
            examInfoList.add(examInfo);
        });
        examMode.setExamInfo(examInfoList);
    }






    /**
     * 删除考试信息
     * @param id
     * @return
     */
    public boolean deleteExamModeById(String id){
        if(StringUtils.isNotEmpty(id)){
            ExamMode mode = findExamModeById(id);
            if(Objects.nonNull(mode)){
                modeTaskService.deleteExamTask(mode.getTaskId());
            }
            ExamMode examMode = ExamMode.of().setId(id);
            mongoTemplate.remove(examMode);
        }
        return true;
    }



    /**
     * 关闭考试信息
     * @param id
     * @return
     */
    public boolean close(String id){
        if(StringUtils.isNotEmpty(id)){
            ExamMode mode = findExamModeById(id);
            if(Objects.nonNull(mode)){
                closeExam(mode);
                //调用任务的相关接口
                modeTaskService.sendCloseAndUpdate(mode.getTaskId());
            }
        }
        return true;
    }








    /**
     * 查询考试信息
     * @param id
     * @return
     * */
    public ExamMode findExamModeById(String id){
        if(StringUtils.isNotEmpty(id)){
            List<ExamMode> examModes = mongoTemplate.find(Query.query(where("_id").is(id)), ExamMode.class);
            if(CollUtil.isNotEmpty(examModes)){
                return examModes.get(0);
            }
        }
        return  null;
    }




    /**
     *  根据条件查询考试模式列表
     * */
    public List<ExamMode> findExamModeListByCondition(ExamMode examMode){
        Query query = getExamModeQuery(examMode);
        if(Objects.nonNull(examMode.getPager()) && examMode.getPager().getPaging()){
            query.with(examMode.getPager());
        }
        List<ExamMode> modes = mongoTemplate.find(query, ExamMode.class);
        if(CollectionUtil.isNotEmpty(modes)){
            findStatus(modes);
        }
        return  modes;

    }


    /**
     *  根据当前时间轮询判断状态
     * */
    private void findStatus(List<ExamMode> modes){
         modes.forEach(m->{
             if(Objects.nonNull(m.getStatus()) && m.getStatus() == -1){
                return;
             }
            //首先拿出来taskId,查询task任务表
            List<ExamTask> tasks = mongoTemplate.find(Query.query(Criteria.where("_id").is(m.getTaskId())), ExamTask.class);
            if(CollectionUtil.isNotEmpty(tasks)){
                ExamTask task = tasks.get(0);
                //系统时间在开始时间之前，肯定是待展示
                if(DateUtils.compareDate(DateUtils.Nowss(),task.getBeginTime()) == -1 ){
                    m.setStatus(1);
                }else if(DateUtils.compareDate(DateUtils.Nowss(),task.getEndTime()) == 1 ){
                    //系统时间在结束时间之后，已结束
                    m.setStatus(2);
                }else{
                    //其余展示中
                    m.setStatus(0);
                }
             }
        });
    }








    /**
     * 组装ExamMode查询条件
     * @param examMode
     * @return
     */
    private Query getExamModeQuery(ExamMode examMode) {
        List<Criteria> list=new ArrayList<>();
        if(StringUtils.isNotEmpty(examMode.getExamName())){
            list.add(where("examName").regex(".*?"+examMode.getExamName()+".*"));
        }
        if(StringUtils.isNotEmpty(examMode.getSchoolId())){
            list.add(where("schoolId").is(examMode.getSchoolId()));
        }
        Query query=new Query();
        if(CollUtil.isNotEmpty(list)){
            query=new Query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
        }
        //排序
        query.with(new Sort(Sort.Direction.DESC,"createDate"));
        return query;
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getNow(){
        return DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 查询考试模式详情列表总数
     * @param examMode
     * @return
     */
    public Long findExamModeCountByCondition(ExamMode examMode){
        Query query=getExamModeQuery(examMode);
        return mongoTemplate.count(query, ExamMode.class);
    }

    /**
     * 查询当前时间范围内的考试模式
     * @return
     */
    public List<ExamMode> findByCExamModeByTime(ExamMode exam){
        List<Criteria> list=new ArrayList<> ();
        list.add(Criteria.where("schoolId").is(exam.getSchoolId()));
        list.add(Criteria.where("status").ne(-1));
       /* list.add (Criteria.where("examBeginTime").lte(getNow()).and("examEndTime").gte(getNow()));*/
        Query query=Query.query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
        List<ExamMode> modes = mongoTemplate.find(query, ExamMode.class);
        if(CollectionUtil.isNotEmpty(modes)){
            modes.forEach(m->m.setTimestamp(System.currentTimeMillis()));
            return  modes;
        }
        return null;
    }





}
