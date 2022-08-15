package com.yice.edu.cn.jy.service.homeworkAnalyse;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.jy.service.homework.HomeworkStudentService;
import org.apache.commons.lang.StringUtils;
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
public class HomeworkAnalyseService {
    @Autowired
    private MongoTemplate mot;

    @Autowired
    private HomeworkStudentService homeworkStudentService;

    public Homework findHomeworkAnalyseById(String id) {    //按id查找作业
        return mot.findOne(query(where("id").is(id)), Homework.class);
    }

    public List<Homework> findHomeworkAnalyseListByConditionNew(Homework homework){
        //按条件查询作业分析列表
        List<Criteria> c = new ArrayList<>();
        if(  !StringUtils.isNotEmpty(homework.getTeacherId()) ) {

                c.add(new Criteria().where("teacherId").in(homework.getTeacherIds()));//教师数组

        }else{
            c.add(new Criteria().where("teacherId").is(homework.getTeacherId())); //教师id
        }
        c.add(new Criteria().where("type").is(1));   //线下作业
        c.add(new Criteria().where("publishStatus").is(1));  //已发布

        if(StringUtils.isNotEmpty(homework.getClassesId())){  //班级查询
            c.add(new Criteria().where("classesId").is(homework.getClassesId()));
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){  //课程查询
            c.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        }
        final String[] rangeTime = homework.getRangeTime();
        if(rangeTime !=null && rangeTime.length!=0){                                //布置时间范围查询
            rangeTime[0] = DateUtil.format( DateUtil.beginOfDay(DateUtil.parse(rangeTime[0])), "yyyy-MM-dd HH:mm:ss");
            rangeTime[1] = DateUtil.format( DateUtil.endOfDay(DateUtil.parse(rangeTime[1])), "yyyy-MM-dd HH:mm:ss");
            c.add(new Criteria("publishTime").gte(rangeTime[0]));
            c.add(new Criteria("publishTime").lte(rangeTime[1]));
        }
        if(homework.getSchoolYearId()!=null){
            c.add(new Criteria().where("schoolYearId").is(homework.getSchoolYearId()));
        }
        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        if(homework.getPager()!= null && homework.getPager().getPaging()){  //分页
             query.with(homework.getPager());
        }
        return mot.find(query,Homework.class);

    }

    public long findHomeworkAnalyseCountByConditionNew(Homework homework){
        //按条件查询作业分析返回总条数
        List<Criteria> c = new ArrayList<>();
        if( !StringUtils.isNotEmpty(homework.getTeacherId()))  {
                c.add(new Criteria().where("teacherId").in(homework.getTeacherIds()));//教师数组
        }else{
            c.add(new Criteria().where("teacherId").is(homework.getTeacherId())); //教师id
        }
        c.add(new Criteria().where("type").is(1));   //线下作业
        c.add(new Criteria().where("publishStatus").is(1));  //已发布
        if(StringUtils.isNotEmpty(homework.getClassesId())){  //班级查询
            c.add(new Criteria().where("classesId").is(homework.getClassesId()));
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){  //课程查询
            c.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        }
        final String[] rangeTime = homework.getRangeTime();
        if( rangeTime != null && rangeTime.length !=0 ){                                //布置时间范围查询
            rangeTime[0] = DateUtil.format( DateUtil.beginOfDay(DateUtil.parse(rangeTime[0])), "yyyy-MM-dd HH:mm:ss");
            rangeTime[1] = DateUtil.format( DateUtil.endOfDay(DateUtil.parse(rangeTime[1])), "yyyy-MM-dd HH:mm:ss");
            c.add(new Criteria("publishTime").gte(rangeTime[0]));
            c.add(new Criteria("publishTime").lte(rangeTime[1]));
        }
        if(homework.getSchoolYearId()!=null){
            c.add(new Criteria().where("schoolYearId").is(homework.getSchoolYearId()));
        }
        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        if(homework.getPager()!= null && homework.getPager().getPaging()){  //分页
            query.with(homework.getPager());
        }
        return mot.count(query,Homework.class);
    }

    public List<HomeworkStudent> findHomeworkStudentListAnalyseByCondition(HomeworkStudent homeworkStudent){
        List<Criteria> c = new ArrayList<>();
        c.add(new Criteria().where("homeworkObj._id").is(homeworkStudent.getHomeworkObj().getId()));
        c.add(new Criteria().where("homeworkObj.type").is(homeworkStudent.getHomeworkObj().getType()));
        c.add(new Criteria().where("status").in(Constant.HOMEWORK.SUBMIT_HAS,Constant.HOMEWORK.SUBMIT_OUT_TIME));
        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        return mot.find(query,HomeworkStudent.class) ;
    }

    public List<TopicsRecord> findTopicsRecordListAnalyseByCondition(TopicsRecord topicsRecord) {
        List<Criteria> c = new ArrayList<>();
        c.add(new Criteria().where("studentId").in(topicsRecord.getStudentIds()));//studentId
        c.add(new Criteria().where("channelId").is(topicsRecord.getChannelId()));//homework的sqId
        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        return mot.find(query, TopicsRecord.class);

    }
}
