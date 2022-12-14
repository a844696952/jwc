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
@Api(value = "/homeworkStuStatus", description = "????????????????????????")
public class HomeworkStuStatusController {
	@Autowired
	private HomeworkStudentService homeworkStudentService;
	@Autowired
	private TopicsRecordService topicsRecordService;
	@Autowired
	private TopicsRecordFeign topicsRecordFeign;
	
    @PostMapping("/findOneHomeworkStudentByCondition")
    @ApiOperation(value = "????????????sqId???studentId??????????????????????????????", notes = "?????????????????????????????????,???????????????",response = HomeworkStudent.class)
    public ResponseJson findOneHomeworkStudentByCondition(
            @ApiParam(value = "???????????????????????????")
            @RequestBody StuHomeworkQueryInfoQueryVo stuHomeworkQueryInfoQueryVo){
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	homeworkStudent.setStudentId(stuHomeworkQueryInfoQueryVo.getStudentSqId());
    	Homework obj = new Homework();
    	obj.setId(stuHomeworkQueryInfoQueryVo.getHomeworkSqId());
    	homeworkStudent.setHomeworkObj(obj);
        return new ResponseJson(homeworkStudentService.findOneHomeworkStudentByCondition(homeworkStudent));
    }
	
	@PostMapping("/findHomeworkStuOffListByCondition")
	@ApiOperation(value = "?????????????????????????????????", notes = "????????????????????????",response = StuHomeworkOffViewVo.class)
	public ResponseJson findHomeworkStuOffListByCondition(
			@ApiParam(value = "??????sqId???type 1.???????????? 2.?????????  3.????????????replyWay 1.???????????? 2.????????????",required=true) @RequestBody CompleteHomeworkQueryVo remarkHomeworkQueryVo) {

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
	@ApiOperation(value = "?????????????????????????????????", notes = "????????????????????????")
	public ResponseJson findHomeworkStuOnListByCondition(
			@ApiParam(value = "??????sqId???type 1.???????????? 2.?????????  3.?????????",required=true) @RequestBody CompleteHomeworkQueryVo remarkHomeworkQueryVo) {

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
	@ApiOperation(value = "????????????????????????")
	public ResponseJson remarkStudentHomework(
			@ApiParam(value = "????????????id???remarkNote????????????") @RequestBody RemarkOfflineHomeworkVo remarkOfflineHomeworkVo) {
		HomeworkStudent homeworkStudent = new HomeworkStudent();
		homeworkStudent.setId(remarkOfflineHomeworkVo.getHomewordStudentId());
		homeworkStudent.setRemarkNote(remarkOfflineHomeworkVo.getRemarkNote());
		homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_HAS);
		homeworkStudent.setRemarkTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		homeworkStudentService.remakrStuHomework(homeworkStudent);
		return new ResponseJson();
	}
	
	@GetMapping("/delRemarkStudentHomework/{homewordStudentId}")
	@ApiOperation(value = "?????????????????????????????????")
	public ResponseJson delRemarkStudentHomework(
			@ApiParam(value = "????????????id") @PathVariable String homewordStudentId) {
		if(homewordStudentId==null) {
			return new ResponseJson(false,"????????????!");
		}
		homeworkStudentService.delRemarkNoteByHomeworkStudentId(homewordStudentId);
		return new ResponseJson();
	}
	
	@PostMapping("/findOneStuDoHomeworkInfo") 
	@ApiOperation(value = "??????????????????", notes = "??????TopicsRecord")
	public ResponseJson findOneStuDoHomeworkInfo(
	   @ApiParam(value = "??????sqId?????????id", required = true) 
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
			// ??????????????????????????????????????????
			List<StuHomeRecordVo> stuHomeRecordVoList = topicsRecordService
					.queryHomeworkCorrectRateByHomeworkId(homeworkId);
			int topicNum = homeworkStudentList.get(0).getHomeworkObj().getTopicArr().length;
			
			Map<String,StuHomeRecordVo> stuHomeRecordVoMap = new HashMap<String,StuHomeRecordVo>();
			for(StuHomeRecordVo stuHomeRecordVo : stuHomeRecordVoList) {
				stuHomeRecordVoMap.put(stuHomeRecordVo.getStudentId(), stuHomeRecordVo);
			}
			
			// ????????????????????????????????????
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
	@ApiOperation(value = "??????????????????????????????", notes = "??????????????????-HomeworkStudent??????")
	public ResponseJson findHomeworkStudentsByCondition(
			@ApiParam(value = "????????????id?????????id??????????????????????????????{\n" +
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
    @ApiOperation(value = "??????????????????????????????", notes = "????????????")
    public ResponseJson findHomeworkStudentCountByCondition(
            @ApiParam(value = "????????????id?????????id??????????????????????????????{\n" +
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
    @ApiOperation(value = "????????????????????????????????????", notes = "??????StuHomeworkOffViewVo??????")
    public ResponseJson findStuHomeworkStatusNum(
            @ApiParam(value = "Homework??????sqId", required = true)
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
    @ApiOperation(value = "????????????????????????????????????", notes = "??????StuHomeworkOffViewVo??????")
    public ResponseJson stuHomeworkStatusNum(
            @ApiParam(value = "??????id", required = true)
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
    @ApiOperation(value = "???????????????????????????", notes = "??????homeStudentList????????????")
    public ResponseJson findStuHomeowrkStatus(
            @ApiParam(value = "??????id???type 1.???????????? 2.?????????  3.?????????", required = true)
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
    @ApiOperation(value = "????????????????????????????????????", notes = "??????TopicsRecord??????")
    public ResponseJson findStuRecordInfo(
            @ApiParam(value = "??????sqid?????????id?????????sqid", required = true)
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
    @ApiOperation(value = "???????????????????????????????????????????????????", notes = "???????????????????????????????????????????????????")
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(
            @ApiParam(value = "?????????????????????????????????: yyyy-MM-dd HH:mm:ss", required = true)
            @RequestBody HomeworkCountQueryVo vo){
        return homeworkStudentService.findSchoolHomeworkNumByDateAndStatus(vo);
    }
}
