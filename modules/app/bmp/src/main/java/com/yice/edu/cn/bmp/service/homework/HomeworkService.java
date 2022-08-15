package com.yice.edu.cn.bmp.service.homework;

import com.yice.edu.cn.bmp.feignClient.homework.HomeworkFeign;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.jy.topics.app.WrongTopicsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkFeign homeworkFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Homework publishHomework(Homework homework){
        return homeworkFeign.publishHomework(homework);
    }

    public List<Map<String, Object>> findHomeworkListByConditionXq(Homework homework) {
        //大于30%的概率
        List<Map<String,Object>>  list = null;
        list = homeworkFeign.findHomeworkListByConditionXq(homework);
        if(list==null ||list.size()==0){
            return list;
        }
        list = list.stream().filter(s->Double.valueOf(s.get("divideCount").toString())>=0.3).
                limit(6).collect(Collectors.toList());
        return list;
    }

    public  List<Map<String, Object>> findHomeworksByConditionDetail(Homework homework) {
        return homeworkFeign.findHomeworksByConditionDetail(homework);
    }

    public List<HomeworkNew> findKnowlegAllMoreDetailByCondition(Homework homework) {
        return homeworkFeign.findKnowlegAllMoreDetailByCondition(homework);
    }

    public HomeworkNew findCurrentTopicDetail(Homework homework) {
        return homeworkFeign.findCurrentTopicDetail(homework);
    }

    public List<Map<String,Object>> findOneError(Homework homework) {
        return homeworkFeign.findOneError(homework);
    }

    public List<Map<String,Object>> findOneStudentDetail(Homework homework) {
        //大于30%的概率
        List<Map<String,Object>>  list = null;
        list = homeworkFeign.findOneStudentDetail(homework);
        if(list==null ||list.size()==0){
            return list;
        }
        list = list.stream().filter(s->Double.valueOf(s.get("divideCount").toString())>=0.3)
                .collect(Collectors.toList());
        return list;
        //return homeworkFeign.findOneStudentDetail(homework);
    }

    public List<HomeworkNew> findOneStudentKnoledgeContext(Homework homework) {
        return homeworkFeign.findOneStudentKnoledgeContext(homework);
    }

    public List<WrongTopics> mistakesCollection(Homework homework) {
        //移除缓存数量
        WrongTopics wrongTopics = new WrongTopics();
        wrongTopics.setStudentId(myStudentId());
        wrongTopics.setSubjectId(homework.getSubjectId());
        stringRedisTemplate.delete("WRONG_TOPIC_"+"_"+homework.getSubjectId()+"_"+myStudentId());
        return homeworkFeign.mistakesCollection(homework);
    }

    public HomeworkNew mistakesCollectionDetail(Homework homework) {
        return homeworkFeign.mistakesCollectionDetail(homework);
    }

    public List<Map<String,Object>> findWrongtopicInfo(Homework homework) {
        return homeworkFeign.findWrongtopicInfo(homework);
    }

    public Integer mistakesCollectionCount(WrongTopicsVo wrongTopicsVo) {
        Homework homework = new Homework();
        homework.setStudentId(wrongTopicsVo.getStudentId());
        homework.setSubjectId(wrongTopicsVo.getSubjectId());
        homework.setClassesId(wrongTopicsVo.getClassesId());
        homework.setTypeId("23");
        homework.setSchoolId(wrongTopicsVo.getSchoolId());
        return homeworkFeign.mistakesCollectionCount(homework);
    }
}
