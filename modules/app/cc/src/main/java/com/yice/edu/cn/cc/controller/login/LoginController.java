package com.yice.edu.cn.cc.controller.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.cc.service.cloudCourse.CloudCourseService;
import com.yice.edu.cn.cc.service.login.LoginService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private CloudCourseService cloudCourseService;

	@PostMapping("/login")
	@ApiOperation(value = "1.登录,登录成功后获取到token的值,请在后续的请求里放入请求头,请求头的名字为token，值为登录后获取的值")
	public ResponseJson login(
			@ApiParam(value = "{tel:(校内账号使用手机号，校外账号使用用户名),password:密码(校内账号password请使用md5加密后小写),type:账户类型 0校内账号 1校外账号} ") 
			@RequestBody LoginObj loginObj) {
		if (StrUtil.isEmpty(loginObj.getTel())) {
			return new ResponseJson(false, "手机号码或者账号必填");
		}
		if (StrUtil.isEmpty(loginObj.getPassword())) {
			return new ResponseJson(false, "密码必填");
		}
		Map<String, Object> map = new HashMap<>();
		if (loginObj.getType() == Constant.CCourse.ACCOUNT_TYPE_INSIDE) {// 校内账号
			loginObj.setPassword(DigestUtil.sha1Hex(loginObj.getPassword()));
			Teacher teacher = loginService.teacherLogin(loginObj);
			if (teacher == null) {
				return new ResponseJson(false, "账号或密码错误");
			}
			map.put("id", teacher.getId());
			map.put("name", teacher.getName());
			map.put("imgUrl", teacher.getImgUrl());
			map.put("tel", teacher.getTel());
			map.put("schoolId", teacher.getSchoolId());
			String token = JwtUtil.createJWT(teacher.getId(), "{}", null, -1);
			return new ResponseJson(token, map);
		}

		if (loginObj.getType() == Constant.CCourse.ACCOUNT_TYPE_OUTSIDE) {
			OtherSchoolAccount otherSchoolAccount = loginService.otherLogin(loginObj);
			if (otherSchoolAccount == null) {
				return new ResponseJson(false, "账号或密码错误");
			}
			String expireDate = otherSchoolAccount.getExpireDate();
			// 判断是否超过使用期限
			if (expireDate != null
					&& DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN).compareTo(expireDate) > 0) {
				return new ResponseJson(false, "账号已经到期");
			}
			map.put("id", otherSchoolAccount.getId());
			map.put("name", otherSchoolAccount.getName());
			map.put("schoolId", otherSchoolAccount.getSchoolId());
			String token = JwtUtil.createJWT(otherSchoolAccount.getId(), "{}", null, -1);
			return new ResponseJson(token, map);
		}
		return new ResponseJson(false, "找不到该账户类型!");
	}
	
//	@PostMapping("/h5GenerateToken")
//	public ResponseJson h5BroadcastToken(
//			@RequestBody
//			CloudCourse cloudCourse) {
//		if(cloudCourse.getBroadcastCode()==null || "".equals(cloudCourse.getBroadcastCode())) {
//			return new ResponseJson(false,"房间不存在");
//		}
//		cloudCourse.setAllowListen(true);
//		cloudCourseService.findCloudCourseCountByCondition(cloudCourse);
//		String token = JwtUtil.createJWT(cloudCourse.getBroadcastCode(), "{}", null, 86400000);
//		return new ResponseJson(token);
//
//	}
}
