package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Subject;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.ExaminationPointServiceClone;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.answerSheet.AnswerSheetService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperSubjectService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.SubjectMaterialServerClone;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.TopicsServiceClone;
import com.yice.edu.cn.osp.service.jw.qusBankResouece.PersonalTopicsService;
import com.yice.edu.cn.osp.service.jw.qusBankResouece.SchoolQusBankService;
import com.yice.edu.cn.osp.service.jy.topics.TopicsService;
import com.yice.edu.cn.osp.service.source21.Source21Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/paper")
@Api(value = "/paper",description = "模块")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private SubjectMaterialServerClone subjectMaterialServerClone;

    @Autowired
    private ExaminationPointServiceClone examinationPointServiceClone;

    @Autowired
    private PaperSubjectService paperSubjectService;

    @Autowired
    private PersonalTopicsService personalTopicsService;

    @Autowired
    private SchoolQusBankService schoolQusBankService;

    @Autowired
    private TopicsServiceClone topicsServiceClone;

    @Autowired
    private AnswerSheetService answerSheetService;

    @Autowired
    private Source21Service source21Service;
    @Autowired
    private TopicsService topicsService;

    @PostMapping("/saveTestPaper")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveTestPaper(
            @ApiParam(value = "对象", required = true)
            @RequestBody Paper paper){

        Paper paper1 = paperService.findTestPaperById(paper.getId());
        paper.setSchoolId(mySchoolId());
        String[] i = paperService.saveTestPaper(paper);
        return new ResponseJson(i);
    }

    @GetMapping("/look/findTestPaperById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findTestPaperById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Paper paper = paperService.findTestPaperById(id);
        return new ResponseJson(paper);
    }

    @PostMapping("/update/updateTestPaper")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateTestPaper(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Paper paper){
        paperService.updateTestPaper(paper);
        return new ResponseJson();
    }

    @GetMapping("/look/lookTestPaperById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookTestPaperById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Paper paper = paperService.findTestPaperById(id);
        return new ResponseJson(paper);
    }

    @PostMapping("/findTestPapersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findTestPapersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Paper paper){//App端调用的接口
        paper.setCreateUserId(myId());
        paper.setCurrentType(2);//只显示已保存的试卷
        List<Paper> data= paperService.findTestPaperListByCondition(paper);
        return new ResponseJson(data);
    }

    //不传递任何参数，则是查询当前用户是否在试题篮里有试卷
    //有的话返回被裁减字段的对象，没有的话返回空
    //传递试卷id的话，则是查询试卷的详细信息，返回字段无删减
    @PostMapping("/look/findOneTestPaperByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneTestPaperByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Paper paper){
        paper.setCreateUserId(myId());
        Paper one= paperService.findOneTestPaperByCondition(paper);
        if(one==null&&paper!=null&&paper.getId()!=null){
            return new ResponseJson(false,"试卷已被删除");
        }
        return new ResponseJson(one);
    }
    @GetMapping("/deleteTestPaper/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTestPaper(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        paperService.deleteTestPaper(id);
        return new ResponseJson();
    }


    @PostMapping("/findTestPaperListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTestPaperListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Paper paper){//pc端调用的接口
        paper.setCreateUserId(myId());
        paper.setCurrentType(2);
        List<Paper> data= paperService.findTestPaperListByCondition(paper);
        long count = paperService.findTestPaperCountByCondition(paper);
        return new ResponseJson(data,count);
    }

    //查出当前用户的学段（小学，初中，高中）
    @PostMapping("/ignore/findSubjectMaterialListByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SubjectMaterial subjectMaterial){
        subjectMaterial.setAnnualPeriodId(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        subjectMaterial.setLeaf(2);
        List<SubjectMaterial> data=subjectMaterialServerClone.findSubjectMaterialListByCondition(subjectMaterial);

        PaperSubject p = paperSubjectService.findOnePaperSubjectKong(LoginInterceptor.myId());
        if(p!=null){
            return new ResponseJson(data,p);
        }
        return new ResponseJson(data);

    }

    //根据科目查找考点树
    @GetMapping("/ignore/findExaminationPointTreeByGradeId/{subjectId}")
    @ApiOperation(value = "根据科目查找考点树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findExaminationPointTreeBySubjectId(
            @ApiParam(value = "科目id")
            @PathVariable String subjectId){
        ExaminationPoint examinationPoint = new ExaminationPoint();
        examinationPoint.setSubjectId(subjectId);
        Pager pager = new Pager();
        pager.setSortField("sort");
        pager.setSortOrder("desc");
        pager.setPaging(false);
        examinationPoint.setPager(pager);
        List<ExaminationPoint> data=examinationPointServiceClone.findExaminationPointListByCondition(examinationPoint);
        List<ExaminationPoint> treeData = ObjectKit.buildTree(data, "-1");

        /*//记录当前选择
        PaperSubject paperSubject = new PaperSubject();
        paperSubject.setCreateUserId(LoginInterceptor.myId());
        paperSubject.setSubjectId(subjectId);

        paperSubjectService.savePaperSubject(paperSubject);*/

        return new ResponseJson(treeData);
    }

    //查找关联的知识点列表
    @PostMapping("/ignore/findKnowledgePointListByExamPoint")
    @ApiOperation(value = "根据考点条件查找关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByExamPoint(
            @ApiParam(value = "考点对象{examPointId:考点id}")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        List<KnowledgePoint> data =  examinationPointServiceClone.findKnowledgePointListByExamPoint(examPointKnowledge);
        long count = examinationPointServiceClone.findKnowledgePointCountByExamPoint(examPointKnowledge);
        return new ResponseJson(data,count);
    }

    //后台运营题库
    @PostMapping("/ignore/findTopicsListByCondition4Muti/{oneKnowledgeId}/{twoKnowledgeId}")
    @ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTopicsListByCondition4Muti(
            @Validated
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Topics topics,
            @PathVariable("oneKnowledgeId") String oneKnowledgeId,
            @PathVariable("twoKnowledgeId") String twoKnowledgeId
            ){
        String subjectId  = topics.getSubjectId();
        String subjectName = topics.getSubjectName();
        topics.setSubjectId(null);
        topics.setSubjectName(null);
        //topics.setContentText(StringUtils.specialCharacterConvert(topics.getContentText()));
        topics.getPager().setLike("content");
        topics.getPager().setSortField("createTime");
        topics.getPager().setSortOrder(Pager.DESC);
        List<Topics> data=topicsServiceClone.findTopicsListByCondition4Muti(topics);
        long count = topicsServiceClone.findTopicsCountByCondition4Muti(topics);

        //记录当前选择
        PaperSubject p = new PaperSubject();

        p.setSubjectId(subjectId);
        p.setSubjectName(subjectName);
        p.setOneKnowledgeId(oneKnowledgeId);
        p.setTwoKnowledgeId(twoKnowledgeId);
        p.setCreateUserId(LoginInterceptor.myId());
        p.setKnowledgeId(topics.getKnowledges().get(0).getId());
        p.setKnowledgeName(topics.getKnowledges().get(0).getName());
        p.setTypeId(topics.getTypeId());
        p.setTopicSource(0);
        paperSubjectService.savePaperSubject(p);

        return new ResponseJson(data,count);
    }

    //老师个人题库
    @PostMapping("/ignore/findPersonalTopicssByCondition/{oneKnowledgeId}/{twoKnowledgeId}")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PersonalTopics.class)
    public ResponseJson findPersonalTopicssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PersonalTopics personalTopics,
            @PathVariable("oneKnowledgeId") String oneKnowledgeId,
            @PathVariable("twoKnowledgeId") String twoKnowledgeId){
        String subjectId  = personalTopics.getSubjectId();
        String subjectName = personalTopics.getSubjectName();
        personalTopics.setSubjectId(null);
        personalTopics.setSubjectName(null);
        //personalTopics.setContentText(StringUtils.specialCharacterConvert(personalTopics.getContentText()));
        personalTopics.setSchoolId(mySchoolId());
        personalTopics.setTeacherId(myId());
        personalTopics.getPager().setLike("content");
        personalTopics.getPager().setSortField("createTime");
        personalTopics.getPager().setSortOrder(Pager.DESC);
        List<PersonalTopics> data=personalTopicsService.findPersonalTopicsListByCondition(personalTopics);
        long count=personalTopicsService.findPersonalTopicsCountByCondition(personalTopics);

        //记录当前选择
        PaperSubject p = new PaperSubject();
        p.setOneKnowledgeId(oneKnowledgeId);
        p.setTwoKnowledgeId(twoKnowledgeId);
        p.setSubjectId(subjectId);
        p.setSubjectName(subjectName);
        p.setCreateUserId(LoginInterceptor.myId());
        p.setKnowledgeId(personalTopics.getKnowledges().get(0).getId());
        p.setKnowledgeName(personalTopics.getKnowledges().get(0).getName());
        p.setTypeId(personalTopics.getTypeId());
        p.setTopicSource(2);
        paperSubjectService.savePaperSubject(p);

        return new ResponseJson(data,count);
    }

    //校本题库
    @PostMapping("/ignore/findSchoolQusBanksByCondition/{oneKnowledgeId}/{twoKnowledgeId}")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findSchoolQusBanksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolQusBank schoolQusBank,
            @PathVariable("oneKnowledgeId") String oneKnowledgeId,
            @PathVariable("twoKnowledgeId") String twoKnowledgeId) {
        String subjectId  = schoolQusBank.getSubjectId();
        String subjectName = schoolQusBank.getSubjectName();
        schoolQusBank.setSubjectId(null);
        schoolQusBank.setSubjectName(null);
        //schoolQusBank.setContentText(StringUtils.specialCharacterConvert(schoolQusBank.getContentText()));
        schoolQusBank.getPager().setLike("content");
        schoolQusBank.getPager().setSortField("createTime");
        schoolQusBank.getPager().setSortOrder(Pager.DESC);
        schoolQusBank.setSchoolId(mySchoolId());
        List<SchoolQusBank> data = schoolQusBankService.findSchoolQusBankListByCondition(schoolQusBank);
        long count = schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);

        //记录当前选择
        PaperSubject p = new PaperSubject();
        p.setOneKnowledgeId(oneKnowledgeId);
        p.setTwoKnowledgeId(twoKnowledgeId);
        p.setSubjectId(subjectId);
        p.setSubjectName(subjectName);
        p.setCreateUserId(LoginInterceptor.myId());
        p.setKnowledgeId(schoolQusBank.getKnowledges().get(0).getId());
        p.setKnowledgeName(schoolQusBank.getKnowledges().get(0).getName());
        p.setTypeId(schoolQusBank.getTypeId());
        p.setTopicSource(1);
        paperSubjectService.savePaperSubject(p);

        return new ResponseJson(data, count);
    }


    @PostMapping("/save/savePaperAndPaperQuestionKong")
    @ApiOperation(value = "添加试题,传题目对象即可",notes = "返回添加后的对象")
    public ResponseJson savePaperAndPaperQuestion(
            @ApiParam(value = "添加试题")
            @RequestBody PaperTopics paperTopics
            ){
        paperTopics.setSchoolId(mySchoolId());
        paperTopics.setCreateUserId(myId());
        Paper paper = paperService.savePaperAndPaperQuestion(paperTopics);
        return new ResponseJson(paper);
    }

    @PostMapping("/save/removePaperQuestionKong")
    @ApiOperation(value = "根据题目对象删除",notes = "返回被删除的对象")
    public  ResponseJson removePaperQuestionKong(
            @ApiParam(value = "需要删除的题目对象")
            @RequestBody PaperTopics paperTopics){
        paperTopics.setCreateUser(LoginInterceptor.myId());
        paperService.removePaperQuestionKong(paperTopics);
        return  new ResponseJson();
    }


    @PostMapping("/update/updateAnswerSheet")
    @ApiOperation(value = "修改答题卡对象", notes = "返回响应对象")
    public ResponseJson updateAnswerSheet(
            @ApiParam(value = "被修改的答题卡对象,对象属性不为空则修改", required = true)
            @RequestBody AnswerSheet answerSheets){
        answerSheetService.updateAnswerSheet(answerSheets);
        return new ResponseJson();
    }

    @PostMapping("/save/saveAnswerSheet")
    @ApiOperation(value = "保存答题卡对象", notes = "返回保存好的答题卡对象", response=AnswerSheet.class)
    public ResponseJson saveAnswerSheet(
            @ApiParam(value = "答题卡对象", required = true)
            @RequestBody AnswerSheet answerSheet){
        answerSheet.setSchoolId(mySchoolId());
        answerSheet.setCreateUserId(myId());
        AnswerSheet s=answerSheetService.saveAnswerSheet(answerSheet);
        return new ResponseJson(s);
    }
    /**==============================21世纪题库相关接口 start==============================================**/
    //查出当前用户的学段（小学，初中，高中）
    @GetMapping("/ignore/findSchoolSubject421")
    @ApiOperation(value = "查询当前学校科目-21世纪", notes = "返回响应对象,不包含总条数",response = Subject.class)
    public ResponseJson findSchoolSubject421(){
        List<Subject> data = source21Service.getSubject4stage(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/findVersionsBySubject421/{subjectId}")
    @ApiOperation(value = "通过科目获取教材版本-21世纪", notes = "返回响应对象,不包含总条数",response = Version.class)
    public ResponseJson findVersionsBySubject421(
            @NotEmpty
            @PathVariable("subjectId") String subjectId){
        List<Version> versions = source21Service.getVersionsBySubject(LoginInterceptor.currentTeacher().getSchool().getTypeId(),subjectId);
        return new ResponseJson(versions);
    }
    @GetMapping("/ignore/findBooksByVersion421/{versionId}")
    @ApiOperation(value = "通过教材版本获取书籍-21世纪", notes = "返回响应对象,不包含总条数",response = Book.class)
    public ResponseJson findBooksByVersion421(
            @NotEmpty
            @PathVariable("versionId") String versionId){
        List<Book> books = source21Service.getBooksByVersion(versionId);
        return new ResponseJson(books);
    }
    @GetMapping("/ignore/findChaptersByBook421/{bookId}")
    @ApiOperation(value = "通过书籍id获取章节-21世纪", notes = "返回响应对象,不包含总条数",response = Chapter.class)
    public ResponseJson findChaptersByBook421(
            @NotEmpty
            @PathVariable("bookId") String bookId
    ){
        List<Chapter> chapters = source21Service.getChaptersByBook(bookId);
        return new ResponseJson(chapters);
    }
//    @GetMapping("/ignore/findTypesBySubject421/{subjectId}")
//    @ApiOperation(value = "通过科目题型-21世纪", notes = "返回响应对象,不包含总条数",response = QuestionType.class)
//    public ResponseJson findTypesBySubject421(
//            @NotEmpty
//            @PathVariable("subjectId") String subjectId){
//        List<QuestionType> types = source21Service.getTypesBySubject(LoginInterceptor.currentTeacher().getSchool().getTypeId(),subjectId);
//        return new ResponseJson(types);
//    }
    @PostMapping("/ignore/findQuestionsByCondition421")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Question.class)
    public ResponseJson findQuestionsByCondition421(@RequestBody SearchParam searchParam){
        //设置学段
        searchParam.setStage(Integer.parseInt(LoginInterceptor.currentTeacher().getSchool().getTypeId()));
        APIResult<Question> questionAPIResult = source21Service.getQuestions(searchParam);
        List<Question> questions = questionAPIResult.getData();
        questions.forEach(question ->
                question.setStem(question.getStem().replaceAll("\\{#blank#\\}\\d+\\{#/blank#\\}","( )"))
        );
        return new ResponseJson(questions, Optional.ofNullable(Optional.ofNullable(questionAPIResult.getPage()).orElse(new APIResult.Page()).getTotalCount()).orElse(0));
    }
    @GetMapping("/ignore/findQuestionDetail421/{questionId}")
    @ApiOperation(value = "查询题目详情-21世纪", notes = "返回响应对象,含总条数",response = Question.class)
    public ResponseJson findQuestionDetail421(
            @NotEmpty
            @PathVariable("questionId") String questionId){
        return new ResponseJson(source21Service.getQuestionDetail(questionId));
    }
    /**==============================21世纪题库相关接口 end==============================================**/
    @PostMapping("/findTopicList")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        return topicsService.findTopicList(searchParam);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(topicsService.findTopicDetail(resourceVo));
    }
    @GetMapping("/findTopicUseCount/{topicId}")
    @ApiOperation(value = "获取近几个月题目使用次数", notes = "返回题目使用次数",response = Long.class)
    public ResponseJson findTopicUseCount(@PathVariable String topicId){
        return new ResponseJson(topicsService.findTopicUseCount(topicId));
    }
}
