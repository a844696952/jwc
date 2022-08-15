package com.yice.edu.cn.jw.service.exam.buildPaper.paper;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.structure.*;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.jw.service.exam.buildPaper.answerSheet.AnswerSheetService;
import com.yice.edu.cn.jw.service.exam.examManage.SchoolExamService;
import org.springframework.data.mongodb.core.convert.MongoConverter;
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

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class

PaperService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private MongoConverter mongoConverter;

    @Autowired
    private PaperQuestionService paperQuestionService;

    @Autowired
    private AnswerSheetService answerSheetService;

    @Autowired
    private SchoolExamService schoolExamService;

    //查看试卷详情(通过Id查询，不放入试题篮中)
    public Paper findTestPaperById(String id) {
        Document documentMatch;//主试卷返回字段
        Document documentProject;//大题表返回字段
        documentMatch = new Document("$match",new Document("_id",id));//不删减，返回所有的试卷信息
        documentProject = new Document("$project",new Document("scoreNumber",new Document("$sum","$topicsList.score")).append("size",new Document("$size","$topicsList")).append("typeId",1).append("typeName",1).append("testPaperId",1).append("topicsList",1).append("createUserId",1));//大题的信息
        Document document = new Document("$lookup",new Document("from","paperQuestion").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$eq",Arrays.asList("$testPaperId","$$id")))),documentProject)).append("as","subject"));
        AggregateIterable<Document> documents = mot.getCollection("paper").aggregate(Arrays.asList(documentMatch,document));
        Document d= documents.first();
        Paper paper = mongoConverter.read(Paper.class,d);
        return paper;
    }

    //添加试卷
    public Paper savePaperAndPaperQuestion(PaperTopics paperTopics){
        Paper paper = new Paper();
        PaperQuestion paperQuestion = new PaperQuestion();
        //单选与非单选
        if(paperTopics.getTypeId()==2){
            paperQuestion.setTopicsType(1);
        }else{
            paperQuestion.setTopicsType(2);
        }
        paperQuestion.setCreateUserId(paperTopics.getCreateUserId());
        paperQuestion.setTypeId(paperTopics.getTypeId());
        paperQuestion.setTypeName(paperTopics.getTypeName());
        paperQuestion.setPaperTopics(paperTopics);
        paperQuestion.setSchoolId(paperTopics.getSchoolId());
        //如果有Id则添加题目到对应的大题表中
        if(paperTopics.getTestPaperId()!=null){
            Criteria criteria = Criteria.where("_id").is(paperTopics.getTestPaperId());
            Query query = new Query(criteria);
            Paper s = mot.findOne(query,Paper.class);
            if(s!=null){
                paperQuestion.setTestPaperId(paperTopics.getTestPaperId());
                paperQuestionService.savePaperQuestion(paperQuestion);
            }
            return paper;
        }else{//如果没有 创建试卷
            Paper paper1 = new Paper();
            //初始化固定试卷数据
            init(paper1);
            paper1.setCreateUserId(paperTopics.getCreateUserId());
            paper1.setSubjectId(paperTopics.getSubjectId());
            String id = sequenceId.nextId();
            paper1.setId(id);
            paper1.setCurrentType(1);
            //插入数据库
            mot.insert(paper1,"paper");
            //把试卷id赋给大题表
            paperQuestion.setTestPaperId(id);
            paperQuestionService.savePaperQuestion(paperQuestion);
            return  paper1;
        }

    }

    //保存试卷数据
    public String[] saveTestPaper(Paper paper) {
        if(paper.getId()==null){
            paper.setId(sequenceId.nextId());
        }
        String[] strings = new String[2];
        //清空试卷的大题
        Criteria criteria = Criteria.where("testPaperId").is(paper.getId());
        Query query = new Query(criteria);
        mot.remove(query,"paperQuestion");
        //添加试卷的大题
        List<PaperQuestion> paperQuestion = new ArrayList<>();
        //判断大题里是否有小题
        for(int i=0;i<paper.getSubject().size();i++) {
            if(paper.getSubject().size()!=0&&paper.getSubject().get(i).getTopicsList().size()!=0){
                paperQuestion.add(paper.getSubject().get(i));
            }
        }
        //判断是否是克隆的试卷
        if(paper.getCurrentType()!=null&&paper.getCurrentType()==2&&paper.getClonePaperId()!=null){
            //查询克隆试卷是否作为考试试卷
            Criteria criteria6 = Criteria.where("courses.paperId").is(paper.getClonePaperId());
            Query query6 = new Query(criteria6);
            SchoolExam schoolExam = mot.findOne(query6,SchoolExam.class);
            if(schoolExam!=null){
                paper.setClonePaperId(null);
                save(paper,paperQuestion);
                strings[1] = paper.getId();
            }else{
                Criteria criteria1 = Criteria.where("testPaperId").is(paper.getClonePaperId());
                Query query1 = new Query(criteria1);
                //删除被克隆试卷大题信息
                mot.remove(query1,"paperQuestion");
                //判断是否有大题需要添加
                if(paper.getSubject().size()!=0){
                    //添加新的大题信息
                    paperQuestion.forEach(f->{
                        f.setTestPaperId(paper.getClonePaperId());
                        f.setCreateTime(DateUtil.now());
                        f.setSchoolId(paper.getSchoolId());
                    });
                    paperQuestionService.batchSavePaperQuestion(paperQuestion);
                }
                //删除克隆试卷信息
                mot.remove(Query.query(where("_id").is(paper.getId())),"paper");
                //修改被克隆试卷信息
                paper.setSubject(null);
                paper.setId(paper.getClonePaperId());
                paper.setClonePaperId(null);
                paper.setUpdateTime(DateUtil.now());
                mot.save(paper,"paper");
            }
        }else {
            save(paper,paperQuestion);
        }


        if(paper.getCurrentType()!=null&&paper.getCurrentType()==2){
            AnswerSheet answerSheet = new AnswerSheet();
            answerSheet.setPaperId(paper.getId());
            AnswerSheet answerSheet1 = answerSheetService.findOneAnswerSheetByCondition(answerSheet);
            if(answerSheet1!=null){
                strings[0] = answerSheet1.getId();
                return  strings;
            }else{
                //表示新建
                strings[0] = "0";
                return  strings;
            }
        }
        strings[0] = "1";
        return  strings;

    }
    public List<Paper> findTestPaperListByCondition(Paper testPaper) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(testPaper, testPaper.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(testPaper.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(Paper.class,operations),Paper.class).getMappedResults();

    }
    public long findTestPaperCountByCondition(Paper testPaper) {
        Criteria criteria = MongoKit.buildCriteria(testPaper, testPaper.getPager());
        return mot.count(query(criteria), Paper.class);
    }


    public Paper findOneTestPaperByCondition(Paper paper) {
       /* Example<Paper> example = Example.of(paper, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        operations.add(Aggregation.lookup("paperQuestion","_id","testPaperId","subject"));
        Pager pager = paper.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }*/

        Document documentMatch;//主试卷返回字段
        Document documentProject;//大题表返回字段
        //主试卷的返回字段
        Document paperProject = new Document("$project",new Document("subject",1).append("currentType",1).append("createUserId",1));
        if(paper.getId()!=null){
            //如果是通过点击查找，返回所有的字段不删减字段
            documentMatch = new Document("$match",new Document("_id",paper.getId()));//不删减，返回所有的试卷信息
            documentProject = new Document("$project",new Document("scoreNumber",new Document("$sum","$topicsList.score")).append("size",new Document("$size","$topicsList")).append("typeId",1).append("typeName",1).append("testPaperId",1).append("topicsList",1).append("createUserId",1).append("title",1));//大题的信息
        }else{
            //如果是通过当前登录用户查询，删减一些字段
            documentMatch = new Document("$match",new Document("currentType",1).append("createUserId",paper.getCreateUserId()));//试卷的信息
            documentProject = new Document("$project",new Document("scoreNumber",new Document("$sum","$topicsList.score")).append("size",new Document("$size","$topicsList")).append("typeId",1).append("typeName",1).append("testPaperId",1).append("topicsList",1));//大题的信息
        }

//        Document document = new Document("$lookup",new Document("from","paperQuestion").append("localField","_id").append("foreignField","testPaperId").append("as","subject"));
        Document document = new Document("$lookup",new Document("from","paperQuestion").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$eq",Arrays.asList("$testPaperId","$$id")))),documentProject)).append("as","subject"));
        AggregateIterable<Document> documents;
        if(paper.getId()!=null){
            documents = mot.getCollection("paper").aggregate(Arrays.asList(documentMatch,document));
        }else{
            documents = mot.getCollection("paper").aggregate(Arrays.asList(documentMatch,document,paperProject));
        }

        Document d  = documents.first();
        //将document对象转化成Paper对象
        Paper paper1 = mongoConverter.read(Paper.class,d);
        return  paper1;


    }

    public void updateTestPaper(Paper paper) {
        paper.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(paper.getId())), MongoKit.update(paper),Paper.class);
    }

    //清空试卷
    public void deleteTestPaper(String id) {
        mot.remove(query(where("_id").is(id)),Paper.class);
        PaperQuestion paperQuestion = new PaperQuestion();
        paperQuestion.setTestPaperId(id);
        paperQuestionService.deletePaperQuestionByCondition(paperQuestion);

        mot.remove(query(where("paperId").is(id)),AnswerSheet.class);
    }
    public void deleteTestPaperByCondition(Paper paper) {
        Example<Paper> example = Example.of(paper, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, Paper.class);
    }
    public void batchSaveTestPaper(List<Paper> papers){
        papers.forEach(testPaper -> testPaper.setId(sequenceId.nextId()));
        mot.insertAll(papers);
    }

    //删除某道题（传递对象为小题对象）
    public void removePaperQuestionKong(PaperTopics paperTopics){
        Integer typeId = paperTopics.getTypeId();
        Criteria criteria = Criteria.where("typeId").is(typeId).andOperator(Criteria.where("testPaperId").is(paperTopics.getTestPaperId()));
        Query query = new Query(criteria);

        Update update = new Update();
        BasicDBObject basicDBObject =new BasicDBObject();
        basicDBObject.put("_id",paperTopics.getId());
        update.pull("topicsList",basicDBObject);
        mot.updateFirst(query,update,PaperQuestion.class);

    }
    //修改试卷是否被考试使用

    /**
     *
     * flag为true则自增，为false则自减
     * @param schoolExam
     * @param flag
     */
    public void setPaperFlags(SchoolExam schoolExam,boolean flag){
        List<JwCourse> jwCourses = schoolExam.getCourses();
        List<String> paperId = new ArrayList<>();
        for(JwCourse s:jwCourses){
            paperId.add(s.getPaperId());
        }
        if(paperId.size()>0&&paperId!=null){
            Criteria criteria = Criteria.where("_id").in(paperId);
            Query query = new Query(criteria);
            Update update = new Update();
            if(flag){
                update.inc("numbers",1);
            }else{
                update.inc("numbers",-1);
            }
            mot.updateMulti(query,update,"paper");
            if(!flag){
                List<Paper> paperList = mot.find(query,Paper.class);
                List<String> stringList = new ArrayList<>();
                for(Paper p:paperList){
                    if(p.getNumbers()<0){
                        stringList.add(p.getId());
                    }
                }

                if(stringList!=null&&stringList.size()>0){
                    Update update1 = new Update();
                    update1.set("numbers",0);
                    mot.updateMulti(Query.query(where("_id").in(stringList)),update1,"paper");
                }
            }
        }

    }

    public List<Paper> findTestPaperListByConditionKong(Paper paper) {
        Document document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("currentType",paper.getCurrentType()));

        if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()!=null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==0){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("mainTitle.text",new Document("$regex",paper.getMainTitle().getText()).append("$options","i")).append("currentType",paper.getCurrentType()));
        }else if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()==null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==2){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("$and",Arrays.asList(new Document("updateTime",new Document("$gt",paper.getSearchTimeZone()[0])),new Document("updateTime",new Document("$lt",paper.getSearchTimeZone()[1])))).append("currentType",paper.getCurrentType()));
        }else if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()!=null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==2){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("mainTitle.text",new Document("$regex",paper.getMainTitle().getText()).append("$options","i")).append("$and",Arrays.asList(new Document("updateTime",new Document("$gt",paper.getSearchTimeZone()[0])),new Document("updateTime",new Document("$lt",paper.getSearchTimeZone()[1])))).append("currentType",paper.getCurrentType()));
        }
        Document document1 = new Document("$sort",new Document("updateTime",-1));
        Document document2  = new Document("$skip",(paper.getPager().getPage() - 1) * paper.getPager().getPageSize());
        Document document3 = new Document("$limit",paper.getPager().getPageSize());
        Document document4 = new Document("$lookup",new Document("from","answerSheet").append("localField","_id").append("foreignField","paperId").append("as","answerSheet"));
        Document document5 = new Document("$unwind","$answerSheet");
        Document document6 = new Document("$project",new Document("createTime",1).append("updateTime",1).append("subjectId",1).append("mainTitle",1).append("answerSheetName","$answerSheet.name").append("examineeInfo",1).append("gutter",1).append("paperInfo",1).append("numbers",1).append("paperTypeId",1).append("currentType",1).append("version","$answerSheet.version").append("answerSheetId","$answerSheet._id"));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("paper").aggregate(Arrays.asList(document,document1,document2,document3,document4,document5,document6));
        MongoCursor<Document>  cursor = aggregateIterable.iterator();
        List<Paper> paperList = new ArrayList<>();
        while (cursor.hasNext()){
            paperList.add(mot.getConverter().read(Paper.class,cursor.next()));
        }
        return paperList;
    }
    public long findTestPaperCountByConditionKong(Paper paper) {
        Document document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("currentType",paper.getCurrentType()));

        if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()!=null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==0){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("mainTitle.text",new Document("$regex",paper.getMainTitle().getText()).append("$options","i")).append("currentType",paper.getCurrentType()));
        }else if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()==null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==2){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("$and",Arrays.asList(new Document("updateTime",new Document("$gt",paper.getSearchTimeZone()[0])),new Document("updateTime",new Document("$lt",paper.getSearchTimeZone()[1])))).append("currentType",paper.getCurrentType()));
        }else if(paper.getMainTitle()!=null&&paper.getMainTitle().getText()!=null&&paper.getSearchTimeZone()!=null&&paper.getSearchTimeZone().length==2){
            document = new Document("$match",new Document("createUserId",paper.getCreateUserId()).append("mainTitle.text",new Document("$regex",paper.getMainTitle().getText()).append("$options","i")).append("$and",Arrays.asList(new Document("updateTime",new Document("$gt",paper.getSearchTimeZone()[0])),new Document("updateTime",new Document("$lt",paper.getSearchTimeZone()[1])))).append("currentType",paper.getCurrentType()));
        }
        AggregateIterable<Document> aggregateIterable = mot.getCollection("paper").aggregate(Arrays.asList(document));
        MongoCursor<Document>  cursor = aggregateIterable.iterator();
        List<Paper> paperList = new ArrayList<>();
        while (cursor.hasNext()){
            paperList.add(mot.getConverter().read(Paper.class,cursor.next()));
        }

        return paperList.size();
    }

    /**
     *  传递 SearchTimeZone[0] 起始时间，SearchTimeZone[1]结束时间,学校id
     * @param paper
     * @return
     */
    //按天分组，查询一个时间段每天的试卷数量
    public List<PaperDayMonth>  findEveryDayPaper(Paper paper){
        Document documentAnd = new Document("$and",Arrays.asList(new Document("createTime",new Document("$gte",paper.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",paper.getSearchTimeZone()[1]))));
        Document document = new Document("$match",new Document(documentAnd).append("schoolId",paper.getSchoolId()).append("currentType",2));
        Document fanhui = new Document("$project",new Document("time",new Document("$dateFromString",new Document("dateString","$createTime"))));
        Document document3 = new Document("$group",new Document("_id",new Document("year",new Document("$year","$time")).append("month",new Document("$month","$time")).append("day",new Document("$dayOfMonth","$time"))));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("paper").aggregate(Arrays.asList(document,fanhui,document3));
        MongoCursor<Document> cursor = aggregateIterable.iterator();
        List<PaperDayMonth> paperDayMonths = new ArrayList<>();
        while (cursor.hasNext()){
            paperDayMonths.add(mot.getConverter().read(PaperDayMonth.class,cursor.next()));
        }
        return paperDayMonths;
    }

    /**
     * 初始化固定组卷数据
     */
    public Paper init(Paper paper1){
        ExamineeInfo examineeInfo = new ExamineeInfo();
        examineeInfo.setFieldDispName(PaperStatic.EXAMINEEINFOFIELDDISPNAME);
        examineeInfo.setFieldName(PaperStatic.EXAMINEEINFOFIELDNAME);
        examineeInfo.setShow(PaperStatic.SHOW);
        examineeInfo.setText(PaperStatic.EXAMINEEINFOTEXT);

        Gutter gutter = new Gutter();
        gutter.setFieldDispName(PaperStatic.GUTTERFIELDISPNAME);
        gutter.setFieldName(PaperStatic.GUTTERFIELDNAME);
        gutter.setShow(PaperStatic.SHOW);

        MainTitle mainTitle = new MainTitle();
        mainTitle.setShow(PaperStatic.SHOW);
        mainTitle.setFieldDispName(PaperStatic.MAINTITLEFIELDDISPNAME);
        mainTitle.setFieldName(PaperStatic.MAINTITLEFIELDNAME);
        mainTitle.setText(PaperStatic.MAINTITLETEXT);

        PaperInfo paperInfo = new PaperInfo();
        paperInfo.setFieldDisName(PaperStatic.PAPERINFOFIELDDISPNAME);
        paperInfo.setFieldName(PaperStatic.PAPERINFOFIELDNAME);
        paperInfo.setShow(PaperStatic.SHOW);
        paperInfo.setText(PaperStatic.PAPERINFOTEXT);

        Precaution precaution = new Precaution();
        precaution.setFieldDisName(PaperStatic.PRECAUTIONFIELDDISPNAME);
        precaution.setFieldName(PaperStatic.PRECAUTIONFIELDNAME);
        precaution.setShow(PaperStatic.SHOW);
        precaution.setText(PaperStatic.PRECAUTIONTEXT);

        ScoreBox scoreBox = new ScoreBox();
        scoreBox.setFieldDisName(PaperStatic.SCOREBOXFIELDDISPNAME);
        scoreBox.setFieldName(PaperStatic.SCOREBOXFIELDNAME);
        scoreBox.setShow(PaperStatic.SHOW);

        SecretMark secretMark = new SecretMark();
        secretMark.setFieldDisName(PaperStatic.SECRETMARKFIELDDISPNAME);
        secretMark.setFieldName(PaperStatic.SECRETMARKFIELDNAME);
        secretMark.setShow(PaperStatic.SHOW);
        secretMark.setText(PaperStatic.SECRETMARKTEXT);

        SubTitle subTitle = new SubTitle();
        subTitle.setFieldDisName(PaperStatic.SUBTITLEFIELDDISPNAME);
        subTitle.setFieldName(PaperStatic.SUBTITLEFIELDNAME);
        subTitle.setShow(PaperStatic.SHOW);
        subTitle.setText(PaperStatic.SUBTITLETEXT);

        List<VolumeOne> volumes = new ArrayList<>();
        VolumeOne volumeOne = new VolumeOne();
        VolumeOne volumeTwo = new VolumeOne();
        volumeTwo.setFieldDisName("第二部分卷标");
        volumeTwo.setFieldName("volumeTwo");
        volumeTwo.setText("第二部分（非选择题）");
        volumeTwo.setShow(false);
        volumes.add(volumeOne);
        volumes.add(volumeTwo);


        paper1.setVolumes(volumes);
        paper1.setPrecaution(precaution);
        paper1.setExamineeInfo(examineeInfo);
        paper1.setGutter(gutter);
        paper1.setMainTitle(mainTitle);
        paper1.setPaperInfo(paperInfo);
        paper1.setScoreBox(scoreBox);
        paper1.setSecretMark(secretMark);
        paper1.setSubTitle(subTitle);
        paper1.setCreateTime(DateUtil.now());
        paper1.setNumbers(0);

        return paper1;
    }


    public void save(Paper paper,List<PaperQuestion> paperQuestion){
        if(paper.getSubject().size()!=0){
            paperQuestion.forEach(f->{
                f.setCreateTime(DateUtil.now());
                f.setSchoolId(paper.getSchoolId());
                if(f.getTestPaperId()==null){
                    f.setTestPaperId(paper.getId());
                }
            });
            paperQuestionService.batchSavePaperQuestion(paperQuestion);
        }
        //修改试卷的字段信息
        paper.setUpdateTime(DateUtil.now());
        paper.setSubject(null);
        mot.save(paper,"paper");
    }


    public void addSharePaper(Paper paper,List<PaperQuestion> paperQuestionList,AnswerSheet answerSheet){
        String id = sequenceId.nextId();
        paper.setId(id);
        paper.setCreateTime(DateUtil.now());
        paperQuestionList.stream().forEach(f->{
            f.setTestPaperId(id);
            f.setCreateTime(paper.getCreateTime());
        });

        paperQuestionService.batchSavePaperQuestion(paperQuestionList);
        mot.insert(paper);
        answerSheet.setPaperId(id);
        answerSheetService.saveAnswerSheet(answerSheet);
    }


    public List<PaperQuestion> findBySchoolExamIdAndCouserId(String schoolExamId,String courseId){
        SchoolExam schoolExam = schoolExamService.findSchoolExamById(schoolExamId);
        JwCourse jwCourse = schoolExam.getCourses().stream().filter(f->f.getId().equals(courseId)).findFirst().get();
        if(jwCourse.getPaperId()==null){
            return new ArrayList<>();
        }
        Paper paper = new Paper();
        paper.setId(jwCourse.getPaperId());
        Paper paper1 = findOneTestPaperByCondition(paper);
        return paper1.getSubject();
    }
}


