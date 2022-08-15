package com.yice.edu.cn.jy.controller.prepareLessons;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.service.prepareLessons.ItemPackageService;


@RestController
@RequestMapping("/itemPackage")
public class ItemPackageController {
	
	@Autowired
	private ItemPackageService itemPackageService;
	
	@PostMapping("/findTopicsListByCondition")
	public ResponseJson findTopicsListByCondition(@RequestBody TopicConditions condition) {

		List<Topics> topics = itemPackageService.findTopicsListByCondition(condition);
		long count=itemPackageService.findTopicsCountByCondition(condition);
		return new ResponseJson(topics,count );
	}
	
	@PostMapping("/findItemPackageByCondition")
	public ResponseJson findItemPackageByCondition(@RequestBody ItemPackage itemPackage){
		
		ResponseJson itemPackages=itemPackageService.findItemPackageByCondition(itemPackage);
		return itemPackages;
	}
	
	@GetMapping("/findTopicCart/{teachingPlanId}")
	public TopicCart findTopicCart(@PathVariable("teachingPlanId") String teachingPlanId) {
		
		TopicCart topicCart = itemPackageService.findTopicCart(teachingPlanId);
		return topicCart;
	}
	
	@PostMapping("/save")
	public ResponseJson save(@RequestBody ItemPackage itemPackage) {
		int successRow=itemPackageService.save(itemPackage);
		return successRow==1?new ResponseJson(true, "保存成功"):new ResponseJson(false, "保存失败");
	}
	
	@GetMapping("/delete/{id}")
	public int removeItemPackage(@PathVariable("id") String id) {
		
		return itemPackageService.removeItemPackage(id);
	}
	
	@GetMapping("/findItemPackageById/{id}")
	public ItemPackage findItemPackageById(@PathVariable("id") String id) {
		
		return itemPackageService.findItemPackageById(id);
	}
	
	@PostMapping("/addTopic2Cart")
	public ResponseJson addTopic2Cart(@RequestBody TopicCart topicCart) {
		int successRow=itemPackageService.addTopic2Cart(topicCart);
		return successRow==1?new ResponseJson(true, "保存成功"):new ResponseJson(false, "保存失败");
	}
	
	@GetMapping("/removeTopicFromCart")
	public ResponseJson removeTopicFromCart(@RequestParam("teachingPlanId") String teachingPlanId, @RequestParam("topicId") String topicId) {
		int successRow=itemPackageService.removeTopicFromCart(teachingPlanId,topicId);
		return successRow>=1?new ResponseJson(true, "操作成功"):new ResponseJson(false, "操作失败");
	}
	
	@GetMapping("/batchRemoveTopic2Cart")
	public ResponseJson batchRemoveTopic2Cart(@RequestParam("teachingPlanId") String teachingPlanId,@RequestParam("topicIds") String topicIds ) {
		int successRow=itemPackageService.batchRemoveTopic2Cart(teachingPlanId,topicIds);
		return successRow>=1?new ResponseJson(true, "操作成功"):new ResponseJson(false, "操作失败");
	}
	
	@PostMapping("/batchAddTopicCart")
	public ResponseJson batchAddTopicCart(TopicCart topicCart) {
		int successRow=itemPackageService.batchSaveTopicCart(topicCart);
		return successRow==1?new ResponseJson(true, "操作成功"):new ResponseJson(false, "操作失败");
	}
	
	
}
