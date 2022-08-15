package com.yice.edu.cn.jy.dao.prepareLessons;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;

@Mapper
public interface ItemPackageDao {
	
	
	List<ItemPackage> findItemPackageByCondition(ItemPackage itemPackage);
	
	int save(ItemPackage itemPackage);
	
	ItemPackage findItemPackageById(@Param("id") String id);
	
	long findItemPackageCountByCondition(ItemPackage itemPackage);
	
	int removeItemPackage(@Param("id") String id);
	
	List<TopicCart> findTopicCat(@Param("teachingPlanId") String teachingPlanId);
	
	int addTopic2Cart(TopicCart topicCart);
	
	int removeTopic2Cart(@Param("teachingPlanId") String teachingPlanId,@Param("topicId") String topicId);
	
	int batchRemoveTopic2Cart(@Param("teachingPlanId") String teachingPlanId,@Param("topicIds") List<String> topicIds );
	
	int batchSaveTopicCart(List<TopicCart> topicCarts);
	
	int removeTopic2CartByTPlanId(@Param("teachingPlanId") String teachingPlanId);
	
	List<ItemPackage> findItemPackageByIds(@Param("ids")List<String> ids);
}
