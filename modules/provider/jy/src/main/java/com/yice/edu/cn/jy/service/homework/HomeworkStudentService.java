package com.yice.edu.cn.jy.service.homework;

import com.google.gson.Gson;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsAnswer;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.jy.service.topics.TopicsRecordService;
import com.yice.edu.cn.jy.service.topics.WrongTopicsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class HomeworkStudentService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TopicsRecordService topicsRecordService;
    @Autowired
    private WrongTopicsService wrongTopicsService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public HomeworkStudent findHomeworkStudentById(String id) {
        return mot.findOne(query(where("id").is(id)), HomeworkStudent.class);
    }

    public void saveHomeworkStudent(HomeworkStudent homeworkStudent) {
        String id = sequenceId.nextId();
        homeworkStudent.setId(id);
        homeworkStudent.setCreateTime(DateUtil.now());
        mot.insert(homeworkStudent);
    }

    public List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.DEFAULT));
        Query query = query(new Criteria().alike(example));
        if (homeworkStudent.getPager() != null && homeworkStudent.getPager().getPaging()) {
            query.with(homeworkStudent.getPager());
        }
//
//        LookupOperation lookupOperation = LookupOperation.newLookup().from("topicsRecord")
//                .localField("studentId")
//                .foreignField("studentId")
//                .as("tr");
//        Aggregation agg = Aggregation.newAggregation(match(Criteria.where("homeworkObj.sqId").is(homeworkStudent.getHomeworkObj().getSqId()))
//                ,lookupOperation
//                ,match(Criteria.where("tr.channelId").is(homeworkStudent.getHomeworkObj().getSqId()).and("tr.correct").is(1))
//                ,group(Arrays.stream(HomeworkStudent.class.getDeclaredFields()).map(Field::getName).toArray(String[]::new)).count().as("tc"));
//        return mot.aggregate(agg,"homeworkStudent",HomeworkStudent.class).getMappedResults();
        return mot.find(query, HomeworkStudent.class);
    }
    public List<HomeworkStudent> findHomeworkStudents4Bmp(HomeworkStudent homeworkStudent) {
        Criteria c = new Criteria("studentId").is(homeworkStudent.getStudentId());
        c.and("status").is(homeworkStudent.getStatus());
        if(StringUtils.isNotEmpty(homeworkStudent.getSubjectId()))
            c.and("subjectId").is(homeworkStudent.getSubjectId());
        if(StringUtils.isNotEmpty(homeworkStudent.getCreateTime()))
            c.and("createTime").gte(homeworkStudent.getCreateTime());
        if(homeworkStudent.getHomeworkObj()!=null&&StringUtils.isNotEmpty(homeworkStudent.getHomeworkObj().getHomeworkName())){
            c.and("homeworkObj.homeworkName").regex(homeworkStudent.getHomeworkObj().getHomeworkName());
        }
        if(homeworkStudent.getHomeworkObj().getType()!=null){
            c.and("homeworkObj.type").is(homeworkStudent.getHomeworkObj().getType());
        }
        if(homeworkStudent.getSchoolYearId()!=null){
            c.and("schoolYearId").is(homeworkStudent.getSchoolYearId());
        }
        if(homeworkStudent.getFromTo()!=null){
            c.and("fromTo").is(homeworkStudent.getFromTo());
        }
        if(homeworkStudent.getTerm()!=null){
            c.and("term").is(homeworkStudent.getTerm());
        }
        Query query = query(c);

        if (homeworkStudent.getPager() != null && homeworkStudent.getPager().getPaging()) {
            query.with(homeworkStudent.getPager());
        }
        return mot.find(query, HomeworkStudent.class);
    }

    public HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        return mot.findOne(query, HomeworkStudent.class);
    }

    public long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        return mot.count(query, HomeworkStudent.class);
    }

    public void updateHomeworkStudent(HomeworkStudent homeworkStudent) {
        mot.updateFirst(query(where("id").is(homeworkStudent.getId())), MongoKit.update(homeworkStudent), HomeworkStudent.class);
    }

    public void deleteHomeworkStudent(String id) {
        mot.remove(query(where("id").is(id)), HomeworkStudent.class);
    }

    public void deleteHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        mot.remove(query, HomeworkStudent.class);
    }
    public void deleteHomeworkStudentByHomeworkId(String id){
        mot.remove(query(where("homeworkObj._id").is(id)), HomeworkStudent.class);
    }

    public void batchSaveHomeworkStudent(List<HomeworkStudent> homeworkStudents) {
        mot.insertAll(homeworkStudents);
    }

    /**
     * 提交线上作业
     *
     * @param homeworkVo
     */
    public ResponseJson submitOnlineHomework(HomeworkVo homeworkVo) {
        //查询学生的作业内容
        HomeworkStudent homeworkStudent = this.findHomeworkStudentById(homeworkVo.getHomeworkId());
        if(homeworkStudent==null||homeworkStudent.getHomeworkObj().getType()!=Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE){
            return new ResponseJson(false,"提交作业异常");
        }
        if(homeworkStudent.getStatus()==1||homeworkStudent.getStatus()==3){
            return new ResponseJson(false,"作业已提交过");
        }

        //进行配置做题记录
        //通过作业中的题目进行对应匹配
        Topics[] topics = homeworkStudent.getHomeworkObj().getTopicArr();
        final String nowTime = DateUtil.now();
        List<TopicsRecord> topicsRecords = Arrays.stream(topics).map(t -> {
            TopicsRecord tr = new TopicsRecord();
            tr.setId(sequenceId.nextId());
            tr.setTopicsObj(t);
            tr.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
            tr.setChannelId(homeworkStudent.getHomeworkObj().getId());
            tr.setStudentId(homeworkStudent.getStudentId());
            tr.setStudentName(homeworkStudent.getStudentName());
            tr.setStudent(homeworkStudent.getStudent());
            tr.setAnswer(Arrays.stream(homeworkVo.getTopicsAnswers()).filter(ta -> ta.getTopicsId().equals(t.getId())).map(s->Optional.ofNullable(s.getAnswer()).orElse("")).collect(Collectors.joining()));
            if(StringUtils.isEmpty(tr.getAnswer()))
                tr.setCorrect(Constant.TOPICS.NONE);
            else
                tr.setCorrect(tr.getAnswer().equalsIgnoreCase(t.getAnswer()[0].trim()) ? Constant.TOPICS.CORRECT : Constant.TOPICS.WRONG);
            tr.setAnswerTime(nowTime);
            tr.setGradeId(homeworkStudent.getGradeId());
            tr.setGradeName(homeworkStudent.getGradeName());
            tr.setClassesId(homeworkStudent.getClassesId());
            tr.setEnrollYear(homeworkStudent.getEnrollYear());
            tr.setClassesName(homeworkStudent.getClassesName());
            tr.setSubjectId(homeworkStudent.getHomeworkObj().getSubjectId());
            tr.setSubjectName(homeworkStudent.getHomeworkObj().getSubjectName());
            tr.setSchoolYearId(homeworkVo.getSchoolYearId());
            tr.setFromTo(homeworkVo.getFromTo());
            tr.setTerm(homeworkVo.getTerm());
            return tr;
        }).collect(Collectors.toList());
        //记录做题记录
        topicsRecordService.batchSaveTopicsRecord(topicsRecords);
        //错题本添加纪录 空题和错题都是错题
        wrongTopicsService.batchSaveWrongTopics(topicsRecords.stream().filter(tr -> tr.getCorrect() == Constant.TOPICS.WRONG||tr.getCorrect() == Constant.TOPICS.NONE).map(tr -> {
            stringRedisTemplate.opsForValue().increment("WRONG_TOPIC_"+"_"+tr.getSubjectId()+"_"+tr.getStudentId(),1);
            WrongTopics wrongTopics = new WrongTopics();
            BeanUtils.copyProperties(tr, wrongTopics);
            return wrongTopics;
        }).collect(Collectors.toList()));
        //修改学生作业状态
        homeworkStudent.setCompleteTime(nowTime);

        String incKey;
        if (nowTime.compareTo(homeworkStudent.getHomeworkObj().getEndTime()) > 0) {
            incKey = "overdueNum";
            homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
        } else {
            incKey = "punctualNum";
            homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
        }
        //学生提交 修改教师作业 提交人数 或者 逾期人数
        homeworkService.inc4HomeworkSubmit(homeworkStudent.getHomeworkObj().getId(), incKey);
        //先上作业要进行题目完成情况统计
        //对题统计
        homeworkService.inc4HomeworkTopicsCount(homeworkStudent.getHomeworkObj().getId(),topicsRecords.stream().filter(tr->tr.getCorrect() == Constant.TOPICS.CORRECT).map(tr->tr.getTopicsObj().getId()).toArray(String[]::new),"correctNum");
        //错题统计
        homeworkService.inc4HomeworkTopicsCount(homeworkStudent.getHomeworkObj().getId(),topicsRecords.stream().filter(tr->tr.getCorrect() == Constant.TOPICS.WRONG||tr.getCorrect() == Constant.TOPICS.NONE).map(tr->tr.getTopicsObj().getId()).toArray(String[]::new),"wrongNum");
        this.updateHomeworkStudent(homeworkStudent);
        return new ResponseJson();
    }

    /**
     * 提交线下作业
     *
     * @param homeworkVo
     */
    public ResponseJson submitUnderlineHomework(HomeworkVo homeworkVo) {
        final String nowTime = DateUtil.now();
        //查询学生的作业内容
        HomeworkStudent homeworkStudent = this.findHomeworkStudentById(homeworkVo.getHomeworkId());
        if(homeworkStudent==null||homeworkStudent.getHomeworkObj().getType()!=Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE){
            return new ResponseJson(false,"提交作业异常");
        }
        if(homeworkStudent.getStatus()==1||homeworkStudent.getStatus()==3){
            return new ResponseJson(false,"作业已提交过");
        }
        //判断类型
       /* if (homeworkStudent.getHomeworkObj().getReplyWay() == Constant.HOMEWORK.REPLY_WAY_PHOTO) {
            //拍照上传 进行图片添加
            if (homeworkVo.getImgs()!=null&&homeworkVo.getImgs().length > 0)
                homeworkStudent.setHomeworkImgArr(homeworkVo.getImgs());
            else
                return new ResponseJson(false,200,"图片上传类型 必须上传图片");//图片上传类型 必须上传图片
        }*/

        homeworkStudent.setContent(homeworkVo.getContent());
        homeworkStudent.setHomeworkImgArr(homeworkVo.getImgs());
        homeworkStudent.setHomeworkStudentAudioVoList(homeworkVo.getHomeworkStudentAudioVoList());
        homeworkStudent.setCompleteTime(nowTime);
        String incKey;
        if (nowTime.compareTo(homeworkStudent.getHomeworkObj().getEndTime()) > 0) {
            incKey = "overdueNum";
            homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
        } else {
            incKey = "punctualNum";
            homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
        }
        //学生提交 修改教师作业 提交人数 或者 逾期人数
        homeworkService.inc4HomeworkSubmit(homeworkStudent.getHomeworkObj().getId(), incKey);
        this.updateHomeworkStudent(homeworkStudent);
        return new ResponseJson();
    }
    
    
    /**
     * 删除批阅消息 
     * @param id HomeworkStudentId
     */
    public void delRemarkNoteByHomeworkStudentId(String id) {
    	Query  query = query(where("id").is(id));
    	Update update = new Update();
    	update.unset("remarkNote");
    	update.unset("remarkTime");
    	update.set("remarkStatus",Constant.HOMEWORK.REMARK_NOT);
    	mot.updateFirst(query, update, HomeworkStudent.class);
    }
    
    /**
     * 查询已提交和逾期提交的线下作业列表
     * @param homeworkStudent
     * @param queryStatus
     * @return
     */
    public List<HomeworkStudent> findHasCompleteHomeworkStuListByCondition(HomeworkStudent homeworkStudent,Object[] queryStatus) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example).and("status").in(queryStatus));
        if (homeworkStudent.getPager() != null && homeworkStudent.getPager().getPaging()) {
            query.with(homeworkStudent.getPager());
        }

        return mot.find(query, HomeworkStudent.class);
    }
    
    /**
     * 查询已提交和逾期提交的线下作业列表数量
     * @param homeworkStudent
     * @param queryStatus
     * @return
     */
    public long findHasCompleteHomeworkStuCountByCondition(HomeworkStudent homeworkStudent,Object[] queryStatus) {
        Example<HomeworkStudent> example = Example.of(homeworkStudent, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example).and("status").in(queryStatus));
        if (homeworkStudent.getPager() != null && homeworkStudent.getPager().getPaging()) {
            query.with(homeworkStudent.getPager());
        }
        return mot.count(query, HomeworkStudent.class);
    }

    /**
     * 学生查找指定day发布的作业
     * @param homeworkStudent
     * @return
     */
    public List<HomeworkStudent> findTodayHomeworkByStudent(HomeworkStudent homeworkStudent){
        return mot.find(query(new Criteria("studentId").is(homeworkStudent.getStudentId()).and("homeworkObj.publishTime").regex(homeworkStudent.getHomeworkObj().getPublishTime())),HomeworkStudent.class);
    }
    
    /**
     * 批阅作业
     * @param homeworkStudent
     */
    public void remakrStuHomework(HomeworkStudent homeworkStudent) {
        mot.updateFirst(query(where("id").is(homeworkStudent.getId())), MongoKit.update(homeworkStudent), HomeworkStudent.class);
        
    	//查询该作业的studentid
    	HomeworkStudent queryHomeworkStudent = mot.findOne(query(where("id").is(homeworkStudent.getId())), HomeworkStudent.class);

        //发送消息
        final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(new String[]{queryHomeworkStudent.getStudentId()},"作业通知",queryHomeworkStudent.getSubjectName()+"作业已经点评！",Extras.SYSTEM_BROADCAST_HOMEWORK);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }

}
