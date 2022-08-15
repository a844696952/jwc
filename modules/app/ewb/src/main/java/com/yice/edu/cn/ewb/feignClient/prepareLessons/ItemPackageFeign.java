package com.yice.edu.cn.ewb.feignClient.prepareLessons;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;


@FeignClient(value="jy",contextId = "itemPackageFeign",path="/itemPackage")
public interface ItemPackageFeign {
	
	@PostMapping("/findTopicsListByCondition")
	public ResponseJson findTopicsListByCondition(@RequestBody TopicConditions condition) ;
	
	@PostMapping("/findItemPackageByCondition")
	public ResponseJson findItemPackageByCondition(@RequestBody ItemPackage itemPackage);
	
	@GetMapping("/findItemPackageById/{id}")
	public ItemPackage findItemPackageById(@PathVariable("id") String id);
	
	@GetMapping("/findTopicCart/{teachingPlanId}")
	public TopicCart findTopicCart(@PathVariable("teachingPlanId") String teachingPlanId);
}
