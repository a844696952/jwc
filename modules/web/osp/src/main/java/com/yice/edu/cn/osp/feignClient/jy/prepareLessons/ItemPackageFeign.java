package com.yice.edu.cn.osp.feignClient.jy.prepareLessons;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;


@FeignClient(value="jy",contextId = "itemPackageFeign",path="/itemPackage")
public interface ItemPackageFeign {
	
	@PostMapping("/findTopicsListByCondition")
	ResponseJson findTopicsListByCondition(@RequestBody TopicConditions condition) ;
	
	@PostMapping("/findItemPackageByCondition")
	ResponseJson findItemPackageByCondition(@RequestBody ItemPackage itemPackage);
	
	@GetMapping("/delete/{id}")
	int removeItemPackage(@PathVariable("id") String id);
	
	@PostMapping("/save")
	ResponseJson save(@RequestBody ItemPackage itemPackage);
	
	@GetMapping("/findItemPackageById/{id}")
	ItemPackage findItemPackageById(@PathVariable("id") String id);
	
	@GetMapping("/findTopicCart/{teachingPlanId}")
	TopicCart findTopicCart(@PathVariable("teachingPlanId") String teachingPlanId);
	
	@PostMapping("/addTopic2Cart")
	ResponseJson addTopic2Cart(@RequestBody TopicCart topicCart) ;
	
	@GetMapping("/removeTopicFromCart")
	ResponseJson removeTopicFromCart(@RequestParam("teachingPlanId") String teachingPlanId, @RequestParam("topicId") String topicId) ;
	
}
