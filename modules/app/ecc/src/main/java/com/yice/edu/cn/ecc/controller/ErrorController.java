package com.yice.edu.cn.ecc.controller;

import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import springfox.documentation.annotations.ApiIgnore;


/**
 * 当出现逻辑错误，需要给前端返回友好提示，的时候
 * @author dengfengfeng
 *
 */
@RestController
@RequestMapping("/error")
@ApiIgnore
public class ErrorController {

	@RequestMapping("/noCheckInSetting")
	public ResponseJson noCheckInSetting() {
		return new ResponseJson(false,500,"没有设置打卡");
	}
	
	@RequestMapping("/noSchoolId")
	public ResponseJson noSchoolId() {
		return new ResponseJson(false,400,"没有学校id");
	}

	@RequestMapping("/noIccard")
	public ResponseJson noIccard() {
		return new ResponseJson(false,400,"没有ic卡信息");
	}

	@RequestMapping("/noClassesId")
	public ResponseJson noClassesId() {
		return new ResponseJson(false,400,"没有班级信息");
	}

	@RequestMapping("/noNeedCheckIn")
	public ResponseJson noNeedCheckIn(@RequestAttribute("msg") String msg) throws JsonProcessingException {
		return new ResponseJson(false,302,msg);
	}
	
	@RequestMapping("/invalidCheckIn")
	public ResponseJson invalidCheckIn() {
		return new ResponseJson(false,302,"打卡无效");
	}

	@RequestMapping("/invalidIccard")
	public ResponseJson invalidIccard() {
		return new ResponseJson(false,402,"无效卡");
	}
}
