package com.yice.edu.cn.jw.service.exam.buildPaper.myPaper;

import cn.hutool.core.date.DateUtil;
import com.mongodb.client.AggregateIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.jw.service.exam.buildPaper.answerSheet.AnswerSheetService;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.jw.service.exam.examManage.SchoolExamService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MyPaperService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private PaperService paperService;
    @Autowired
    private MongoConverter mongoConverter;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnswerSheetService answerSheetService;

    public Paper findOneTestPaperByCondition(Paper paper) {

        //判断当前试题篮是否有试卷
        Paper paper1 = findPaperQuestion(paper.getCreateUserId());
        if(paper1!=null){
            return paper1;
        }

        //查询当前试卷是否作为考试试卷
        Criteria criteria1 = Criteria.where("courses.paperId").is(paper.getId());
        Query query = new Query(criteria1);
        SchoolExam schoolExam = mot.findOne(query,SchoolExam.class);
        if(schoolExam!=null){
            return null;
        }

        return  findOnePaper(paper);

    }

    //删除试卷前，判断是否被使用
    public long deletePaper(String id){
        //查询当前试卷是否作为考试试卷
        Criteria criteria1 = Criteria.where("courses.paperId").is(id);
        Query query = new Query(criteria1);
        SchoolExam schoolExam = mot.findOne(query,SchoolExam.class);
        if(schoolExam!=null){
            return 1;
        }
        paperService.deleteTestPaper(id);
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setPaperId(id);
        answerSheetService.deleteAnswerSheetByCondition(answerSheet);
        return 0;
    }





    //将试题篮保存到数据库中
    public Paper updatePaperQuestion(Paper paper){
        Criteria criteria = Criteria.where("createUserId").is(paper.getCreateUserId()).andOperator(Criteria.where("currentType").is(1));
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("currentType",2);
        mot.updateFirst(query,update,"paper");

        Paper paper1 =  findOnePaper(paper);
        return paperClone(paper1);
    }

    //覆盖试题篮 传递试卷Id
    public Paper coveringPaper(Paper paper){
        //删除当前试题篮试卷信息
        Paper paper1 = findPaperQuestion(paper.getCreateUserId());
        if(paper1!=null){
            paperService.deleteTestPaper(paper1.getId());
        }
        //查找到对应试卷
        Paper paper2 =  findOnePaper(paper);
        String id = paper2.getId();
        //克隆出一份试卷放入试题篮
        Paper paper3 = paperClone(paper2);
        //克隆完试卷，判断当前考试有没有在使用
        Criteria criteria1 = Criteria.where("courses.paperId").is(id);
        Query query = new Query(criteria1);
        SchoolExam schoolExam = mot.findOne(query,SchoolExam.class);
        //如果没有加上克隆试卷Id
        if(!(schoolExam!=null)){
            paper3.setClonePaperId(id);
            //添加被克隆试卷的Id
            Query query1 = new Query(Criteria.where("_id").is(paper3.getId()));
            Update update = new Update();
            update.set("clonePaperId",id);
            mot.updateFirst(query1,update,Paper.class);
        }
        return paper3;
    }

    /**
     *
     * 将方法分开，循环调用
     *
     */

    //查询当前用户在试题篮是否存在试卷
    public Paper findPaperQuestion(String createUserId){
        Criteria criterias = Criteria.where("currentType").is(1).andOperator(Criteria.where("createUserId").is(createUserId));
        Query query = new Query(criterias);
        Paper paperOne = mot.findOne(query,Paper.class);
        return paperOne;
    }

    //更改试卷状态
    public void updatePaperQuest(Paper paper){
        //列表点击查看试卷详细信息时，将试卷状态改变，添加到试题篮
        Criteria criteria = Criteria.where("_id").is(paper.getId());
        Query querys = new Query(criteria);
        Update update = new Update();
        update.set("currentType", 1);
        mot.updateFirst(querys, update, "paper");

    }
    //将试卷信息克隆一份，并存放到试题篮
    public Paper paperClone(Paper paper){

        List<PaperQuestion> paperQuestions = paper.getSubject();
        String id = sequenceId.nextId();
        paper.setId(id);
        paper.setCurrentType(1);
        paper.setCreateTime(DateUtil.now());
        paper.setUpdateTime(null);
        paper.setSubject(null);
        paper.setNumbers(0);
        mot.insert(paper,"paper");

        paperQuestions.forEach(f->{
            f.setId(sequenceId.nextId());
            f.setTestPaperId(id);
        });
        mot.insertAll(paperQuestions);
        paper.setSubject(paperQuestions);
        return  paper;
    }

    //通过试卷Id获取试卷的完整信息
    public Paper findOnePaper(Paper paper){
        Document documentMatch;//主试卷返回字段
        Document documentProject;//大题表返回字段

        documentMatch = new Document("$match", new Document("_id", paper.getId()));//不删减，返回所有的试卷信息
        documentProject = new Document("$project", new Document("scoreNumber", new Document("$sum", "$topicsList.score")).append("size", new Document("$size", "$topicsList")).append("typeId", 1).append("typeName", 1).append("testPaperId", 1).append("topicsList", 1).append("createUserId", 1).append("title",1));//大题的信息
        Document document = new Document("$lookup",new Document("from","paperQuestion").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$eq",Arrays.asList("$testPaperId","$$id")))),documentProject)).append("as","subject"));

        AggregateIterable<Document>  documents = mot.getCollection("paper").aggregate(Arrays.asList(documentMatch, document));

        Document d = documents.first();
        //将document对象转化成Paper对象
        Paper paper1 = mongoConverter.read(Paper.class, d);
        paper1.setPaperTypeId(2);
        return paper1;
    }



}
