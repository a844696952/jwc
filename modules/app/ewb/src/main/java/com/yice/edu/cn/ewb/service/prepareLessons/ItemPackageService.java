package com.yice.edu.cn.ewb.service.prepareLessons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.ItemPackageFeign;


@Service
public class ItemPackageService {
	
	@Autowired
	private ItemPackageFeign feign;
	
	public ResponseJson findTopicsListByCondition(TopicConditions condition){
		
		return feign.findTopicsListByCondition(condition);
		
	}
	
	public ResponseJson findItemPackageByCondition(ItemPackage itemPackage){
		
		return feign.findItemPackageByCondition(itemPackage);
	}
	
	
	
	
	public ItemPackage findItemPackageById( String id) {
		
		return feign.findItemPackageById(id);
	}
	
	
	public TopicCart findTopicCart( String teachingPlanId) {
		
		return feign.findTopicCart(teachingPlanId);
	}
	
	
	
}
