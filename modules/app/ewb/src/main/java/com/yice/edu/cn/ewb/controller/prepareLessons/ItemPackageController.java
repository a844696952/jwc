package com.yice.edu.cn.ewb.controller.prepareLessons;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;
import com.yice.edu.cn.ewb.service.prepareLessons.ItemPackageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.currentTeacher;


@RestController
@RequestMapping("/itemPackage")
@Api(value = "/itemPackage",description = "模块")
public class ItemPackageController {
	
	@Autowired
	private ItemPackageService itemPackageService;
	
	@PostMapping("/findTopicsListByCondition")
	@ApiOperation(value = "通过条件查找题目", notes = "返回对象")
	public ResponseJson findTopicsListByCondition(@RequestBody TopicConditions condition) {
		ResponseJson topics = itemPackageService.findTopicsListByCondition(condition);
		return topics;
	}
	
	@PostMapping("/findItemPackageByCondition")
	@ApiOperation(value = "通过条件查找题包", notes = "返回对象")
	public ResponseJson findItemPackageByCondition(@RequestBody ItemPackage itemPackage){
		itemPackage.setTeacherId(currentTeacher().getId());
		ResponseJson itemPackages=itemPackageService.findItemPackageByCondition(itemPackage);
		return itemPackages;
	}
	
	
	@GetMapping("/findItemPackageById/{id}")
	@ApiOperation(value = "通过Id查找题包", notes = "返回对象")
	public ItemPackage findItemPackageById(@PathVariable("id") String id) {
		
		return itemPackageService.findItemPackageById(id);
	}
	
	@GetMapping("/findTopicCart/{teachingPlanId}")
	@ApiOperation(value = "通过教案Id查找题目车", notes = "返回对象")
	public TopicCart findTopicCart(@PathVariable("teachingPlanId") String teachingPlanId) {
		
		return itemPackageService.findTopicCart(teachingPlanId);
		
	}
}
