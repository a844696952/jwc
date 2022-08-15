package com.yice.edu.cn.jw.service.exam.buildPaper.paper;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PaperQuestionService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public PaperQuestion findPaperQuestionById(String id) {
        return mot.findById(id,PaperQuestion.class);
    }



    //试题蓝添加题目
    public void savePaperQuestion(PaperQuestion paperQuestion) {
        //使用聚合查询查符合条件的列表
        //查找当前题目是否已经有同一大题创建的记录
        Aggregation aggregation = Aggregation.newAggregation((
                Aggregation.match(Criteria.where("typeId").is(paperQuestion.getTypeId()).andOperator(Criteria.where("testPaperId").is(paperQuestion.getTestPaperId())))
        ));

        PaperQuestion paperQuestion1 =  mot.aggregate(aggregation,"paperQuestion",PaperQuestion.class).getUniqueMappedResult();

        if(paperQuestion1!=null){
                //如果已有相同类型题目的记录
                Criteria criteria = Criteria.where("_id").is(paperQuestion1.getId());
                Query query = new Query(criteria);
                Update update = new Update();
                //则添加进数组里
                update.push("topicsList", paperQuestion.getPaperTopics());
                mot.updateFirst(query,update,PaperQuestion.class);
        }else{
            //没找到题目类型的大题记录，则插入新的纪录
            paperQuestion.setCreateTime(DateUtil.now());
            List<PaperTopics> topicsList = new ArrayList<>();
            topicsList.add(paperQuestion.getPaperTopics());
            paperQuestion.setTopicsList(topicsList);
            paperQuestion.setId(sequenceId.nextId());
            paperQuestion.setPaperTopics(null);
            mot.insert(paperQuestion,"paperQuestion");
        }

    }
    public List<PaperQuestion> findPaperQuestionListByCondition(PaperQuestion paperQuestion) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(paperQuestion, paperQuestion.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(paperQuestion.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(PaperQuestion.class,operations),PaperQuestion.class).getMappedResults();

    }
    public long findPaperQuestionCountByCondition(PaperQuestion paperQuestion) {
        Criteria criteria = MongoKit.buildCriteria(paperQuestion, paperQuestion.getPager());
        return mot.count(query(criteria), PaperQuestion.class);
    }
    public PaperQuestion findOnePaperQuestionByCondition(PaperQuestion paperQuestion) {
        Example<PaperQuestion> example = Example.of(paperQuestion, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = paperQuestion.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(PaperQuestion.class,operations),PaperQuestion.class).getUniqueMappedResult();
    }

    public void updatePaperQuestion(PaperQuestion paperQuestion) {
        paperQuestion.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(paperQuestion.getId())), MongoKit.update(paperQuestion),PaperQuestion.class);
    }

    //清空某一道大题
    public void deletePaperQuestion(String id) {
        mot.remove(query(where("id").is(id)),PaperQuestion.class);
    }

    public void deletePaperQuestionByCondition(PaperQuestion paperQuestion) {
        Example<PaperQuestion> example = Example.of(paperQuestion, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, PaperQuestion.class);
    }
    public void batchSavePaperQuestion(List<PaperQuestion> paperQuestions){
        paperQuestions.forEach(PaperQuestion -> PaperQuestion.setId(sequenceId.nextId()));
        mot.insertAll(paperQuestions);
    }




    //修改某道小题的数据(暂不使用)
    public void updatePaperQuestionKong(PaperTopics paperTopics){
//        Document document = new Document("$match",new Document("testPaperId",paperTopics.getTestPaperId()).append("typeId",paperTopics.getTypeId()).append("topicsList.sqId",paperTopics.getSqId()));

        Criteria criteria1 = Criteria.where("_id").is(paperTopics.getId());
        Criteria criteria2 = Criteria.where("topicsList").elemMatch(criteria1);

        Criteria criteria = Criteria.where("typeId").is(paperTopics.getTypeId()).andOperator(Criteria.where("testPaperId").is(paperTopics.getTestPaperId()),criteria2);
        Query query = new Query(criteria);
        Update update = new Update();

        update.set("topicsList.$.score",paperTopics.getScore());
        mot.updateFirst(query,update,PaperQuestion.class);
    }

    //拖拽大题修改顺序（传递对象为大题数组对象，暂不使用）
    public void dragSortBigPaperQuestionKong(List<PaperQuestion> paperQuestionList){
        paperQuestionList.forEach(f->{
            Criteria criteria = Criteria.where("id").is(f.getId());
            Query query = new Query(criteria);
            Update update = new Update();
            update.set("sort",f.getSort());
            mot.updateFirst(query,update,PaperQuestion.class);
        });
    }




    //拖拽小题修改数据（暂不使用）
    public void dragSortMinPaperQuestionKong(PaperQuestion paperQuestion){
        paperQuestion.setUpdateTime(DateUtil.now());
        mot.save(paperQuestion);
    }


    //标准题目格式(暂不使用)
    public ResponseJson findStandardPaperQuestionKong(String testPaperId){
/*
        Criteria criteria = Criteria.where("isShow").is(false);
        Criteria criteria1 = Criteria.where("volumes").elemMatch(criteria);
        Criteria criteria2 = Criteria.where("_id").is(testPaperId).elemMatch(criteria1);*/
        Criteria criteria3 = Criteria.where("volumes.isShow").is(false);
        Query query = new Query(criteria3);
        Update update = new Update();
        update.set("volumes",true);
        mot.updateMulti(query,update,Paper.class);

        return new ResponseJson();
    }

    //返回试卷详细信息
    public List<PaperQuestion> findListQuestionListKong(Paper paper){
        Document document = new Document("$match",new Document("testPaperId",paper.getId()));
        Document document1 = new Document("$unwind","$topicsList");
        Document document4 = new Document("$project",new Document("typeId",1).append("paperTopics","$topicsList").append("typeName",1).append("scoreNumber",1).append("createUserId",1).append("testPaperId",1).append("subjectText",1).append("schoolId",1));
        List<Document> documentList = new ArrayList<>();
        documentList.add(document);
        documentList.add(document1);
        documentList.add(document4);
        if(paper.getPager()!=null&&paper.getPager().getPaging()){
            Document document2 = new Document("$skip",(paper.getPager().getPage()-1)*paper.getPager().getPageSize());
            Document document3 = new Document("$limit",paper.getPager().getPageSize());
            documentList.add(document2);
            documentList.add(document3);
        }
        AggregateIterable<Document> documents  = mot.getCollection("paperQuestion").aggregate(documentList);
        MongoCursor<Document> mongoCursor = documents.iterator();
        List<PaperQuestion> paperQuestionList = new ArrayList<>();
        while (mongoCursor.hasNext()){
             paperQuestionList.add(mot.getConverter().read(PaperQuestion.class,mongoCursor.next()));
        }
        return paperQuestionList;
    }


    //返回试卷数量
    public long findCountQuestionCountKong(Paper paper){
        Document document = new Document("$match",new Document("testPaperId",paper.getId()));
        Document document1 = new Document("$unwind","$topicsList");
        Document document4 = new Document("$project",new Document("typeId",1).append("paperTopics","$topicsList").append("typeName",1).append("scoreNumber",1).append("createUserId",1).append("testPaperId",1).append("subjectText",1).append("schoolId",1));
        AggregateIterable<Document> documents = mot.getCollection("paperQuestion").aggregate(Arrays.asList(document,document1,document4));

        MongoCursor<Document> mongoCursor = documents.iterator();
        List<PaperQuestion> paperQuestionList = new ArrayList<>();
        while (mongoCursor.hasNext()){
            paperQuestionList.add(mot.getConverter().read(PaperQuestion.class,mongoCursor.next()));
        }
        return paperQuestionList.size();
    }

    //通过试卷Id和小题Id查询
    public PaperTopics findOnePaperTopicsOneKong(String paperId,String topicsId){
        Document document = new Document("$match",new Document("testPaperId",paperId));
        Document document1 = new Document("$unwind","$topicsList");
        Document document2 = new Document("$project",new Document("typeId",1).append("paperTopics","$topicsList").append("typeName",1).append("scoreNumber",1).append("createUserId",1).append("testPaperId",1).append("subjectText",1).append("schoolId",1));
        Document document3 = new Document("$match",new Document("paperTopics._id",topicsId));

        AggregateIterable<Document> documents  = mot.getCollection("paperQuestion").aggregate(Arrays.asList(document,document1,document2,document3));
        PaperQuestion paperQuestion = mot.getConverter().read(PaperQuestion.class,documents.first());
        return paperQuestion.getPaperTopics();
    }


}
