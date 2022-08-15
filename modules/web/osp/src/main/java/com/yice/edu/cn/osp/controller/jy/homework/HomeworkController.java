package com.yice.edu.cn.osp.controller.jy.homework;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

import java.util.*;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.RemarkHomeworkViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.RemarkHomeworkQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.RemarkHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.StudentTopicRecordQueryVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.KnowkedgeTreeNodeViewVo;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkStudentService;
import com.yice.edu.cn.osp.service.jy.knowledge.JyKnowledgeService;
import com.yice.edu.cn.osp.service.jy.topics.TopicsRecordService;
import com.yice.edu.cn.osp.service.jy.topics.TopicsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/homework")
@Api(value = "/homework", description = "布置作业模块")
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private HomeworkStudentService homeworkStudentService;
    @Autowired
    private JyKnowledgeService jyKnowledgeService;
    @Autowired
    private TopicsRecordService topicsRecordService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/saveHomework")
    @ApiOperation(value = "保存布置作业对象", notes = "返回响应对象")
    public ResponseJson saveHomework(
            @ApiParam(value = "布置作业对象", required = true)
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        homework.setTeacherId(myId());
        homework.setTeacherName(currentTeacher().getName());
        homework.setTeacherImg(currentTeacher().getImgUrl());
        Homework s = homeworkService.saveHomework(homework);
        return new ResponseJson(s);
    }

    @GetMapping("/ignore/findHomeworkById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworkById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Homework homework = homeworkService.findHomeworkById(id);
        return new ResponseJson(homework);
    }

    @PostMapping("/ignore/updateHomework")
    @ApiOperation(value = "修改布置作业对象", notes = "返回响应对象")
    public ResponseJson updateHomework(
            @ApiParam(value = "被修改的布置作业对象,对象属性不为空则修改", required = true)
            @RequestBody Homework homework) {
        homeworkService.updateHomework(homework);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHomeworkById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找布置作业", notes = "返回响应对象")
    public ResponseJson lookHomeworkById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Homework homework = homeworkService.findHomeworkById(id);
        return new ResponseJson(homework);
    }

    @PostMapping("/findHomeworksByCondition")
    @ApiOperation(value = "根据条件查找布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Homework homework) {
        homework.setTeacherId(myId());
        List<Homework> data = homeworkService.findHomeworkListByCondition(homework);
        long count = homeworkService.findHomeworkCountByConditionNew(homework);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneHomeworkByCondition")
    @ApiOperation(value = "根据条件查找单个布置作业,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneHomeworkByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Homework homework) {
        Homework one = homeworkService.findOneHomeworkByCondition(homework);
        return new ResponseJson(one);
    }

    @PostMapping("/ignore/findHomeworksByCondition")
    @ApiOperation(value = "根据条件查找布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworksByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Homework homework) {
        homework.setTeacherId(myId());
        curSchoolYearService.setSchoolYearId(homework,mySchoolId());
        List<Homework> data = homeworkService.findHomeworkListByCondition(homework);
        long count = homeworkService.findHomeworkCountByConditionNew(homework);
        return new ResponseJson(data, count);
    }

    @GetMapping("/ignore/findClassByTeacherId")
    @ApiOperation(value = "根据当前教师查找所交班级", notes = "返回响应对象")
    public ResponseJson findClassByTeacherId() {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setPager(new Pager().setPaging(false).setIncludes("gradeName","classesName","classesId","gradeId").setSortField("classesName"));
        List<TeacherClasses> teacherClassesList = teacherClassesService.findTeacherClassesListByCondition(teacherClasses);
        if(teacherClassesList!=null && teacherClassesList.size() > 0)
            return new ResponseJson(teacherClassesList.stream().sorted(Comparator.comparing(TeacherClasses::getGradeId)).collect(Collectors.toList()));
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/ignore/deleteHomework/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHomework(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        homeworkService.deleteHomework(id);
        return new ResponseJson();
    }

    @GetMapping("/ignore/deleteHomeworkByCondition")
    @ApiOperation(value = "根据条件删除布置作业", notes = "返回响应对象")
    public ResponseJson deleteHomeworkByCondition(
            @ApiParam(value = "被删除的布置作业对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Homework homework) {
        homeworkService.deleteHomeworkByCondition(homework);
        return new ResponseJson();
    }

    @PostMapping("/ignore/deleteHomeworkByHomeworkId")
    @ApiOperation(value = "根据条件删除布置作业", notes = "返回响应对象")
    public ResponseJson deleteHomeworkByHomeworkId(
            @ApiParam(value = "被删除的布置作业对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Homework homework) {
        /*if(homework.getPublishStatus()==1){
            return new ResponseJson(false,"该作业已发布");
        }else{*/

            homeworkService.deleteHomework(homework.getId());
            return new ResponseJson(true,"删除成功");
        /*}*/
    }


    @PostMapping("/findHomeworkListByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findHomeworkListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<Homework> data = homeworkService.findHomeworkListByCondition(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findTopicsListByCondition4Muti")
    @ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTopicsListByCondition4Muti(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Topics topics) {
        if(topics.getTypeIds()==null||topics.getTypeIds().length<=0){
            //作业布置限制选择判断题目
            topics.setTypeIds(new Integer[]{1,2,3});
        }
        topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
        List<Topics> data = topicsService.findTopicsListByCondition4Muti(topics);
        Long count = topicsService.findTopicsCountByCondition4Muti(topics);
        return new ResponseJson(data,count);
    }

    @GetMapping("/ignore/findGradeByTeacher")
    @ApiOperation(value = "通过教师id查询教师所任职的年级", notes = "返回年级列表")
    public ResponseJson findGradeByTeacher() {
        return new ResponseJson(homeworkService.findGradeByTeacher(myId()));
    }

    @GetMapping("/ignore/findCourseByTeacherGrade/{gradeId}")
    @ApiOperation(value = "查询教师在年级中教的科目", notes = "返回科目列表")
    public ResponseJson findCourseByTeacherGrade(@ApiParam(value = "教师年级", required = true)
                                                 @PathVariable String gradeId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setGradeId(gradeId);
        return new ResponseJson(homeworkService.findCourseByTeacherGrade(teacherClasses));
    }

    @GetMapping("/ignore/findCourseByTeacherGrade2/{gradeId}")
    @ApiOperation(value = "查询教师在年级中教的科目", notes = "返回科目列表")
    public ResponseJson findCourseByTeacherGrade2(@ApiParam(value = "教师年级", required = true)
                                                 @PathVariable String gradeId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setGradeId(gradeId);
        return new ResponseJson(homeworkService.findCourseByTeacherGrade2(teacherClasses));
    }


    @PostMapping("/ignore/findClassesByTeacherCourse")
    @ApiOperation(value = "通过年级和课程id获取教师对应所教的班级", notes = "返回班级列表")
    public ResponseJson findClassesByTeacherCourse(@ApiParam(value = "课程id和年级id", required = true)
                                                   @RequestBody TeacherClassVo teacherClassVo) {
        teacherClassVo.setTeacherId(myId());
        return new ResponseJson(homeworkService.findClassesByTeacherCourse(teacherClassVo));
    }

    @PostMapping("/ignore/publishHomework")
    @ApiOperation(value = "布置作业", notes = "返回布置作业对象")
    public ResponseJson publishHomework(
            @ApiParam(value = "布置作业对象", required = true)
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        homework.setTeacherId(myId());
        homework.setTeacherName(currentTeacher().getName());
        homework.setTeacherImg(currentTeacher().getImgUrl());
        curSchoolYearService.setSchoolYearTerm(homework,mySchoolId());

        return new ResponseJson(homeworkService.publishHomework(homework));
    }

    @GetMapping("/ignore/findGradeTreeDic/{id}")
    @ApiOperation(value = "查询年级下的树", notes = "返回响应对象")
    public ResponseJson findGradeTreeDic(@ApiParam(value = "知识树id", required = true) @PathVariable String id) {
        JyKnowledge jyKnowledge = new JyKnowledge();
        jyKnowledge.setParentId(id);
        jyKnowledge.setDel(Constant.DELSIGN.NORMAL);
        List<JyKnowledge> knowledgeList = jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
        List<KnowkedgeTreeNodeViewVo> treeNodeQueryQueryVoList = new ArrayList<KnowkedgeTreeNodeViewVo>();
        for (JyKnowledge jyKnowledgeModel : knowledgeList) {
            KnowkedgeTreeNodeViewVo viewVo = new KnowkedgeTreeNodeViewVo();
            viewVo.setName(jyKnowledgeModel.getName());
            viewVo.setId(jyKnowledgeModel.getId());
            viewVo.setLeaf(Integer.parseInt(jyKnowledgeModel.getLeaf()) == 1 ? true : false);
            treeNodeQueryQueryVoList.add(viewVo);
        }

        return new ResponseJson(treeNodeQueryQueryVoList);
    }

    @PostMapping("/ignore/findHomeworkStudentListByCondition")
    @ApiOperation(value = "获取学生要点评的线下作业的列表", notes = "返回学生作业状态表列表")
    public ResponseJson findHomeworkStudentListByCondition(
            @ApiParam(value = "查询对象")
            @RequestBody RemarkHomeworkQueryVo remarkHomeworkQueryVo){

    	RemarkHomeworkViewVo returnVo = new RemarkHomeworkViewVo();

    	HomeworkStudent homeworkStudent = new HomeworkStudent();

    	Homework homeworkObj = new Homework();
    	homeworkObj.setId(remarkHomeworkQueryVo.getId());
    	homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE);
    	/*homeworkObj.setReplyWay(Constant.HOMEWORK.REPLY_WAY_PHOTO);*/
    	homeworkStudent.setHomeworkObj(homeworkObj);

    	List<HomeworkStudent> homeworkStudentList = new ArrayList<HomeworkStudent>();
    	//类型 1.未点评 2.已点评 3.未提交
    	if(remarkHomeworkQueryVo.getType().intValue()==1) {
    		homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_NOT);
        	homeworkStudentList = homeworkStudentService.findHasCompleteHomeworkStuListByCondition(homeworkStudent);
        	
    	}else if(remarkHomeworkQueryVo.getType().intValue()==2) {
    		homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_HAS);
        	homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
    	}else if(remarkHomeworkQueryVo.getType().intValue()==3) {
    		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
        	homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
    	}
    	returnVo.setHomeworkStudentList(homeworkStudentList);
		
    	homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_HAS);
    	homeworkStudent.setStatus(null);
		long hasRemarkNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasRemarkNum(hasRemarkNum);
		
		homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_NOT);
		long hasNotRemarkNum = homeworkStudentService.findHasCompleteHomeworkStuCountByCondition(homeworkStudent);
		returnVo.setHasNotRemarkNum(hasNotRemarkNum);
		
		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotComplete(hasNotComplete);
		
        return new ResponseJson(returnVo);
    }


    @PostMapping("/ignore/remarkStudentHomework")
    @ApiOperation(value = "点评学生线下作业", notes = "返回学生作业状态表列表")
    public ResponseJson remarkStudentHomework(
            @ApiParam(value = "查询对象")
            @RequestBody RemarkHomeworkVo remarkHomeworkVo){
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	homeworkStudent.setId(remarkHomeworkVo.getHomewordStudentId());
    	homeworkStudent.setRemarkNote(remarkHomeworkVo.getRemarkNote());
    	homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_HAS);
    	homeworkStudent.setRemarkTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    	homeworkStudentService.remakrStuHomework(homeworkStudent);
        return new ResponseJson();
    }

    @PostMapping("/ignore/findConfirmHomeworkByCondition")
    @ApiOperation(value = "获取学生要确认的线下作业的列表", notes = "返回学生作业状态表列表")
    public ResponseJson findConfirmHomeworkByCondition(
            @ApiParam(value = "查询对象")
            @RequestBody RemarkHomeworkQueryVo remarkHomeworkQueryVo){
    	
    	RemarkHomeworkViewVo returnVo = new RemarkHomeworkViewVo();
    	
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	
    	Homework homeworkObj = new Homework();
    	homeworkObj.setId(remarkHomeworkQueryVo.getId());
    	homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE);
    	/*homeworkObj.setReplyWay(Constant.HOMEWORK.REPLY_WAY_COMFIRM);*/
    	homeworkStudent.setHomeworkObj(homeworkObj);

    	//类型 3.未提交 4.已提交
    	if(remarkHomeworkQueryVo.getType().intValue()==3) {
    		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
        	List<HomeworkStudent>  homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
        	returnVo.setHomeworkStudentList(homeworkStudentList);
    		
    	}
    	if(remarkHomeworkQueryVo.getType().intValue()==4) {
    		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
        	List<HomeworkStudent>  homeworkStudentList = homeworkStudentService.findHasCompleteHomeworkStuListByCondition(homeworkStudent);
        	returnVo.setHomeworkStudentList(homeworkStudentList);
    	}
    	

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
		long hasConfirmNum = homeworkStudentService.findHasCompleteHomeworkStuCountByCondition(homeworkStudent);
		returnVo.setHasComplete(hasConfirmNum);
		
		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotComplete(hasNotComplete);
		
        return new ResponseJson(returnVo);
    }
    
    @PostMapping("/ignore/findStudentDoHomeworkTopics")
    @ApiOperation(value = "题目的学生做题情况", notes = "返回学生做题记录情况")
    public ResponseJson findSubjectAnalyse(
            @ApiParam(value = "查询对象")
            @RequestBody StudentTopicRecordQueryVo queryVo){

    	TopicsRecord queryModel = new TopicsRecord();
    	queryModel.setChannelId(queryVo.getHomeworkSqId());
    	queryModel.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
    	
    	Topics topicsObj = new Topics();
    	topicsObj.setId(queryVo.getTopicId());
    	queryModel.setTopicsObj(topicsObj);
    	String type = queryVo.getType();
    	if("1".equals(type)) {
    		//做对
    		queryModel.setCorrect(Constant.TOPICS.CORRECT);
    	}
    	if("2".equals(type)) {
    		//做错
    		//queryModel.setCorrect(Constant.TOPICS.WRONG);
    		queryModel.setPager(new Pager().setPaging(false).setRangeField("correct").setRangeArray(new Integer[]{2,3}));
    	}
    	
    	 List<TopicsRecord>  topicsRecordList= topicsRecordService.findTopicsRecordListByCondition(queryModel);
    	
        return new ResponseJson(topicsRecordList);
    }
    
    @GetMapping("/ignore/findUnCompleteHomeworkStu/{id}")
    @ApiOperation(value = "获取学生未提交的作业列表", notes = "返回学生作业状态表列表")
    public ResponseJson findUnCompleteHomeworkStu(
            @ApiParam(value = "作业对象id")
            @PathVariable String id){
    	
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	
    	Homework homeworkObj = new Homework();
    	homeworkObj.setId(id);
    	homeworkStudent.setHomeworkObj(homeworkObj);
		
		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		List<HomeworkStudent> homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
		
        return new ResponseJson(homeworkStudentList,homeworkStudentList.size());
    }
    @GetMapping("/ignore/deleteRemarkStudentHomework/{id}")
    @ApiOperation(value = "删除已点评的作业评论", notes = "返回学生作业状态")
    public ResponseJson deleteRemarkStudentHomework(
            @ApiParam(value = "作业对象id")
            @PathVariable String id){
        if(id==null) {
            return new ResponseJson(false,"参数错误!");
        }
        HomeworkStudent homeworkStudent = homeworkStudentService.findHomeworkStudentById(id);
        if(Constant.HOMEWORK.REMARK_HAS == homeworkStudent.getRemarkStatus()){
            homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_NOT);
            homeworkStudentService.delRemarkNoteByHomeworkStudentId(id);

        }
        return new ResponseJson();
    }

    /**==============================21世纪题库相关接口 end==============================================**/
    @PostMapping("/findTopicList")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Question.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        if(StringUtils.isEmpty(searchParam.getBaseType()))
            searchParam.setBaseType("1,2,3");
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
