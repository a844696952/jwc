package com.yice.edu.cn.tap.controller.homework;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.StuHomeRecordVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.CompleteHomeworkQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.RemarkOfflineHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkOffViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkOnlineObjViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkOnlineViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkQueryInfoQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkStatusVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuTopicRecordVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.tap.feignClient.topics.TopicsRecordFeign;
import com.yice.edu.cn.tap.service.homework.HomeworkStudentService;
import com.yice.edu.cn.tap.service.topics.TopicsRecordService;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/homeworkStuStatus")
@Api(value = "/homeworkStuStatus", description = "查询学生作业状态")
public class HomeworkStuStatusController {
	@Autowired
	private HomeworkStudentService homeworkStudentService;
	@Autowired
	private TopicsRecordService topicsRecordService;
	@Autowired
	private TopicsRecordFeign topicsRecordFeign;
	
    @PostMapping("/findOneHomeworkStudentByCondition")
    @ApiOperation(value = "根据作业sqId和studentId条件查找学生作业状态", notes = "返回单个学生作业状态表,没有时为空",response = HomeworkStudent.class)
    public ResponseJson findOneHomeworkStudentByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody StuHomeworkQueryInfoQueryVo stuHomeworkQueryInfoQueryVo){
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	homeworkStudent.setStudentId(stuHomeworkQueryInfoQueryVo.getStudentSqId());
    	Homework obj = new Homework();
    	obj.setId(stuHomeworkQueryInfoQueryVo.getHomeworkSqId());
    	homeworkStudent.setHomeworkObj(obj);
        return new ResponseJson(homeworkStudentService.findOneHomeworkStudentByCondition(homeworkStudent));
    }
	
	@PostMapping("/findHomeworkStuOffListByCondition")
	@ApiOperation(value = "获取学生线下作业的列表", notes = "返回学生作业列表",response = StuHomeworkOffViewVo.class)
	public ResponseJson findHomeworkStuOffListByCondition(
			@ApiParam(value = "作业sqId和type 1.准时提交 2.未提交  3.已逾期和replyWay 1.拍照上传 2.确认回复",required=true) @RequestBody CompleteHomeworkQueryVo remarkHomeworkQueryVo) {

		StuHomeworkOffViewVo returnVo = new StuHomeworkOffViewVo();

		HomeworkStudent homeworkStudent = new HomeworkStudent();

		Homework homeworkObj = new Homework();
		homeworkObj.setId(remarkHomeworkQueryVo.getId());
		/*homeworkObj.setReplyWay(remarkHomeworkQueryVo.getReplyWay());*/
		homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE);
		homeworkObj.setTeacherId(myId());
		homeworkStudent.setHomeworkObj(homeworkObj);

		homeworkStudent.setStatus(remarkHomeworkQueryVo.getType().intValue());
		List<HomeworkStudent> homeworkStudentList = homeworkStudentService
				.findHomeworkStudentListByCondition(homeworkStudent);
		
	  if(remarkHomeworkQueryVo.getType()!=Constant.HOMEWORK.SUBMIT_NOT) {
		/*if(remarkHomeworkQueryVo.getReplyWay().intValue()==Constant.HOMEWORK.REPLY_WAY_PHOTO) {*/
			List<HomeworkStudent> hasRemarkhomeworkStudentList = new ArrayList<HomeworkStudent>();
			List<HomeworkStudent> notRemarkhomeworkStudentList = new ArrayList<HomeworkStudent>();
			homeworkStudentList.forEach(homeworkStudentModel ->{
				if(homeworkStudentModel.getRemarkStatus().intValue()==Constant.HOMEWORK.REMARK_HAS) {
					hasRemarkhomeworkStudentList.add(homeworkStudentModel);
				}else {
					notRemarkhomeworkStudentList.add(homeworkStudentModel);
				}
			});
			returnVo.setHasRemarkhomeworkStudentList(hasRemarkhomeworkStudentList);
			returnVo.setNotRemarkhomeworkStudentList(notRemarkhomeworkStudentList);
		/*}*/

		/*if(remarkHomeworkQueryVo.getReplyWay().intValue()==Constant.HOMEWORK.REPLY_WAY_COMFIRM) {
			returnVo.setHomeworkStudentList(homeworkStudentList);
		}*/

		}else {
			returnVo.setHomeworkStudentList(homeworkStudentList);
		}

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
		long hasComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasCompleteNum(hasComplete);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotCompleteNum(hasNotCompleteNum);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
		long hasOutTimeCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasOutTimeCompleteNum(hasOutTimeCompleteNum);

		return new ResponseJson(returnVo);
	}

	@PostMapping("/findHomeworkStuOnListByCondition")
	@ApiOperation(value = "获取学生线上作业的列表", notes = "返回学生作业列表")
	public ResponseJson findHomeworkStuOnListByCondition(
			@ApiParam(value = "作业sqId和type 1.准时提交 2.未提交  3.已逾期",required=true) @RequestBody CompleteHomeworkQueryVo remarkHomeworkQueryVo) {

		StuHomeworkOnlineViewVo returnVo = new StuHomeworkOnlineViewVo();

		HomeworkStudent homeworkStudent = new HomeworkStudent();

		Homework homeworkObj = new Homework();
		homeworkObj.setId(remarkHomeworkQueryVo.getId());
		homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE);
		homeworkObj.setTeacherId(myId());
		homeworkStudent.setHomeworkObj(homeworkObj);

		homeworkStudent.setStatus(remarkHomeworkQueryVo.getType().intValue());
		List<HomeworkStudent> homeworkStudentList = homeworkStudentService
				.findHomeworkStudentListByCondition(homeworkStudent);

		if (!homeworkStudentList.isEmpty()) {
			returnVo.setStuHomeworkOnlineObjViewVo(tranfStuRecord(remarkHomeworkQueryVo.getType().intValue(),
					homeworkStudentList, remarkHomeworkQueryVo.getId()));
		}else {
			returnVo.setStuHomeworkOnlineObjViewVo(new ArrayList<StuHomeworkOnlineObjViewVo>());
		}

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
		long hasComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasCompleteNum(hasComplete);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotCompleteNum(hasNotCompleteNum);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
		long hasOutTimeCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasOutTimeCompleteNum(hasOutTimeCompleteNum);

		return new ResponseJson(returnVo);
	}

	@PostMapping("/remarkStudentHomework")
	@ApiOperation(value = "点评学生线下作业")
	public ResponseJson remarkStudentHomework(
			@ApiParam(value = "学生作业id和remarkNote点评内容") @RequestBody RemarkOfflineHomeworkVo remarkOfflineHomeworkVo) {
		HomeworkStudent homeworkStudent = new HomeworkStudent();
		homeworkStudent.setId(remarkOfflineHomeworkVo.getHomewordStudentId());
		homeworkStudent.setRemarkNote(remarkOfflineHomeworkVo.getRemarkNote());
		homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_HAS);
		homeworkStudent.setRemarkTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		homeworkStudentService.remakrStuHomework(homeworkStudent);
		return new ResponseJson();
	}
	
	@GetMapping("/delRemarkStudentHomework/{homewordStudentId}")
	@ApiOperation(value = "删除学生线下作业的点评")
	public ResponseJson delRemarkStudentHomework(
			@ApiParam(value = "学生作业id") @PathVariable String homewordStudentId) {
		if(homewordStudentId==null) {
			return new ResponseJson(false,"参数错误!");
		}
		homeworkStudentService.delRemarkNoteByHomeworkStudentId(homewordStudentId);
		return new ResponseJson();
	}
	
	@PostMapping("/findOneStuDoHomeworkInfo") 
	@ApiOperation(value = "学生作业报告", notes = "返回TopicsRecord")
	public ResponseJson findOneStuDoHomeworkInfo(
	   @ApiParam(value = "作业sqId和学生id", required = true) 
       @RequestBody StuHomeworkStatusVo vo) {
		TopicsRecord topicsRecord = new TopicsRecord();
		topicsRecord.setChannelId(vo.getHomeworkSqId());
		topicsRecord.setStudentId(vo.getStudentId());
		topicsRecord.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
		List<TopicsRecord> topicsRecordList =  topicsRecordService.findTopicsRecordListByCondition(topicsRecord);
		return new ResponseJson(topicsRecordList);
	}

	private List<StuHomeworkOnlineObjViewVo> tranfStuRecord(int type, List<HomeworkStudent> homeworkStudentList,
			String homeworkId) {
		List<StuHomeworkOnlineObjViewVo> stuHomeworkOnlineObjViewVo = new ArrayList<StuHomeworkOnlineObjViewVo>();
		if (type == 1 || type == 3) {
			// 查询每个学生线上作业的正确率
			List<StuHomeRecordVo> stuHomeRecordVoList = topicsRecordService
					.queryHomeworkCorrectRateByHomeworkId(homeworkId);
			int topicNum = homeworkStudentList.get(0).getHomeworkObj().getTopicArr().length;
			
			Map<String,StuHomeRecordVo> stuHomeRecordVoMap = new HashMap<String,StuHomeRecordVo>();
			for(StuHomeRecordVo stuHomeRecordVo : stuHomeRecordVoList) {
				stuHomeRecordVoMap.put(stuHomeRecordVo.getStudentId(), stuHomeRecordVo);
			}
			
			// 循环遍历添加学生正确率值
			for (HomeworkStudent homeworkStudentObj : homeworkStudentList) {
				StuHomeworkOnlineObjViewVo obj = new StuHomeworkOnlineObjViewVo();
				 if (stuHomeRecordVoMap.get(homeworkStudentObj.getStudentId())!=null) {
					double rate = stuHomeRecordVoMap.get(homeworkStudentObj.getStudentId()).getRightItemNum() / (topicNum * 1.0) * 100;
					obj.setCorrectRate((int)Math.round(rate));
			      }else {
			 	    obj.setCorrectRate(0);
			      }
				  obj.setStudentId(homeworkStudentObj.getStudentId());
				  obj.setStudentName(homeworkStudentObj.getStudentName());
				  obj.setCompleteTime(homeworkStudentObj.getCompleteTime());
				  obj.setStuImage(homeworkStudentObj.getStudent()!=null?homeworkStudentObj.getStudent().getImgUrl():"");
				  obj.setSex(homeworkStudentObj.getStudent()!=null?homeworkStudentObj.getStudent().getSex():"");

				  stuHomeworkOnlineObjViewVo.add(obj);
		 }
			stuHomeworkOnlineObjViewVo.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		}
		
		if (type == 2) {
			for (HomeworkStudent homeworkStudentObj : homeworkStudentList) {
				StuHomeworkOnlineObjViewVo obj = new StuHomeworkOnlineObjViewVo();
				obj.setStudentId(homeworkStudentObj.getStudentId());
				obj.setStudentName(homeworkStudentObj.getStudentName());
				obj.setCompleteTime(homeworkStudentObj.getCompleteTime());
				obj.setStuImage(homeworkStudentObj.getStudent()!=null?homeworkStudentObj.getStudent().getImgUrl():"");
				obj.setSex(homeworkStudentObj.getStudent()!=null?homeworkStudentObj.getStudent().getSex():"");
				stuHomeworkOnlineObjViewVo.add(obj);
			}
		}

		return stuHomeworkOnlineObjViewVo;

	}

	@PostMapping("/findHomeworkStudentsByCondition")
	@ApiOperation(value = "教师查找学生作业列表", notes = "返回响应对象-HomeworkStudent集合")
	public ResponseJson findHomeworkStudentsByCondition(
			@ApiParam(value = "传递班级id、学生id、状态类型和分页数据{\n" +
                    "  \"pager\": {\n" +
                    "    \"page\": 0,\n" +
                    "    \"pageSize\": 0,\n" +
                    "    \"sortField\": \"string\",\n" +
                    "    \"sortOrder\": \"string\"\n" +
                    "  },\n" +
                    "  \"classesId\": \"string\",\n" +
                    "  \"studentId\": \"string\",\n" +
                    "  \"status\": \"int\"\n" +
                    "}")
			@Validated({GroupTwo.class})
			@RequestBody HomeworkStudent homeworkStudent){
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homework);
		List<HomeworkStudent> data=homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
		return new ResponseJson(data);
	}
    @PostMapping("/findHomeworkStudentCountByCondition")
    @ApiOperation(value = "教师查找学生作业数量", notes = "作业数量")
    public ResponseJson findHomeworkStudentCountByCondition(
            @ApiParam(value = "传递班级id、学生id、状态类型和分页数据{\n" +
                    "  \"pager\": {\n" +
                    "    \"page\": 0,\n" +
                    "    \"pageSize\": 0,\n" +
                    "    \"sortField\": \"string\",\n" +
                    "    \"sortOrder\": \"string\"\n" +
                    "  },\n" +
                    "  \"classesId\": \"string\",\n" +
                    "  \"studentId\": \"string\",\n" +
                    "  \"status\": \"int\"\n" +
                    "}")
            @Validated({GroupTwo.class})
            @RequestBody HomeworkStudent homeworkStudent){
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homework);
        long count=homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
        return new ResponseJson(count);
    }
    
    @GetMapping("/findStuHomeworkStatusNum/{sqId}")
    @ApiOperation(value = "教师查找学生作业状态数量", notes = "返回StuHomeworkOffViewVo对象")
    public ResponseJson findStuHomeworkStatusNum(
            @ApiParam(value = "Homework作业sqId", required = true)
            @PathVariable String sqId){
    	StuHomeworkOffViewVo returnVo = new StuHomeworkOffViewVo();
    	
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homework);
        
		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
		long hasComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasCompleteNum(hasComplete);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotCompleteNum(hasNotCompleteNum);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
		long hasOutTimeCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasOutTimeCompleteNum(hasOutTimeCompleteNum);
		
        return new ResponseJson(returnVo);
    }
    
    @GetMapping("/stuHomeworkStatusNum/{studentId}")
    @ApiOperation(value = "学生所有作业状态数量查询", notes = "返回StuHomeworkOffViewVo对象")
    public ResponseJson stuHomeworkStatusNum(
            @ApiParam(value = "学生id", required = true)
            @PathVariable String studentId){
    	StuHomeworkOffViewVo returnVo = new StuHomeworkOffViewVo();
    	
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homework);
        homeworkStudent.setStudentId(studentId);
        
		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
		long hasComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasCompleteNum(hasComplete);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
		long hasNotCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasNotCompleteNum(hasNotCompleteNum);

		homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
		long hasOutTimeCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
		returnVo.setHasOutTimeCompleteNum(hasOutTimeCompleteNum);
		
        return new ResponseJson(returnVo);
    }
    
    
    @PostMapping("/findStuHomeowrkStatus")
    @ApiOperation(value = "查询单个学生的作业", notes = "返回homeStudentList集合对象")
    public ResponseJson findStuHomeowrkStatus(
            @ApiParam(value = "学生id和type 1.准时提交 2.未提交  3.已逾期", required = true)
            @RequestBody CompleteHomeworkQueryVo vo){
    	
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homework);
        homeworkStudent.setStudentId(vo.getId());
        
		homeworkStudent.setStatus(vo.getType());
		List<HomeworkStudent>  homeStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
		
        return new ResponseJson(homeStudentList);
    }
    
    @PostMapping("/findStuRecordInfo")
    @ApiOperation(value = "查看学生线上题目做题详情", notes = "返回TopicsRecord对象")
    public ResponseJson findStuRecordInfo(
            @ApiParam(value = "作业sqid、学生id和题目sqid", required = true)
            @RequestBody StuTopicRecordVo vo){
    	
    	TopicsRecord topicsRecord = new TopicsRecord();
    	topicsRecord.setStudentId(vo.getStudentId());
    	topicsRecord.setId(vo.getTopicSqId());
    	topicsRecord.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
    	topicsRecord.setChannelId(vo.getHomeworkSqId());
    	TopicsRecord topicsRecordReturn = topicsRecordFeign.findOneTopicsRecordByCondition(topicsRecord);
    	
        return new ResponseJson(topicsRecordReturn);
    } 
    
    @PostMapping("/findSchoolHomeworkNumByDateAndStatus")
    @ApiOperation(value = "查询一段时间内学校布置的作业总数量", notes = "查询一段时间内学校布置的作业总数量")
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(
            @ApiParam(value = "开始时间和结束时间格式: yyyy-MM-dd HH:mm:ss", required = true)
            @RequestBody HomeworkCountQueryVo vo){
        return homeworkStudentService.findSchoolHomeworkNumByDateAndStatus(vo);
    }
}
