package com.yice.edu.cn.jy.service.prepareLessons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.ItemPackage;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicCart;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TopicConditions;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.dao.knowledgePoint.IKnowledgePointDao;
import com.yice.edu.cn.jy.dao.prepareLessons.ItemPackageDao;

@Service
public class ItemPackageService {
	
	@Autowired
	private IKnowledgePointDao knowledgePointDao;
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Autowired
	private ItemPackageDao itemDao;
	
    @Autowired
    private SequenceId sequenceId;
	
    /**
     * 
    * @Title: findTopicsListByCondition  
    * @Description: 根据条件查询 题目List
    * @param @param condition 查询条件
    * @return List<Topics>    题目List  
    * @throws
     */
	public List<Topics> findTopicsListByCondition(TopicConditions condition) {
		Query query=buildQuery(condition);
		List<Topics> topics = mongoTemplate.find(query, Topics.class);
		return topics;
	}
	
	public Long findTopicsCountByCondition(TopicConditions condition) {
		Query query=buildQuery(condition);
		long count = mongoTemplate.count(query, Topics.class);
		return count;
	}
	
	public ResponseJson findItemPackageByCondition(ItemPackage itemPackage){
		
		List<ItemPackage> itemPackages=itemDao.findItemPackageByCondition(itemPackage);
		long count=itemDao.findItemPackageCountByCondition(itemPackage);
		return new ResponseJson(itemPackages, count);
	}
	
	/**
	 * 
	* @Title: findItemPackageById  
	* @Description: 通过题包Id查询题包详情  
	* @param @param id  题包Id 
	* @return ItemPackage    题包  
	* @throws
	 */
	public ItemPackage findItemPackageById(String id) {
		
		ItemPackage item=itemDao.findItemPackageById(id);
		
		if(item!=null) {
			String topicIds = item.getTopicIds();
			if(StringUtils.isNotBlank(topicIds)) {
				TopicConditions to=new TopicConditions();
				to.setSqIds(topicIds);
				item.setTopics(findTopicsListByCondition(to));
			}
			
		}
		return item;
	}
	
	public int removeItemPackage(String id) {
		
		return itemDao.removeItemPackage(id);
	}
	
	public TopicCart findTopicCart(String teachingPlanId) {
		TopicCart tCart=new TopicCart();
		List<TopicCart> topicCart = itemDao.findTopicCat(teachingPlanId);
		if(topicCart!=null&&topicCart.size()>0) {
			String topicIds = topicCart.stream().map(TopicCart::getTopicId).collect(Collectors.joining(","));
			if(StringUtils.isNotBlank(topicIds)) {
				TopicConditions to=new TopicConditions();
				to.setSqIds(topicIds);
				tCart.setTopics(findTopicsListByCondition(to));
			}
		}
		return tCart;
	}
	
	@Transactional
	public int save(ItemPackage itemPackage) {
		int successRow=0;
		//需要改
		itemPackage.setId(sequenceId.nextId());
		if(StringUtils.isBlank(itemPackage.getName())) {
			String time=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
			itemPackage.setName(itemPackage.getTeachingPlanName()+time);
		}
		itemDao.removeTopic2CartByTPlanId(itemPackage.getTeacherId());
		successRow=itemDao.save(itemPackage);
		//根据需求，删除保存的这样，不会回显。
		batchRemoveTopic2Cart(itemPackage.getTeachingPlanId(),itemPackage.getTopicIds());
		return successRow;
	}
	
	@Transactional
	public int addTopic2Cart(TopicCart topicCart) {
		int successRow=0;
		topicCart.setId(sequenceId.nextId());
		successRow=itemDao.addTopic2Cart(topicCart);
		return successRow;
	}
	
	@Transactional
	public int removeTopicFromCart( String teachingPlanId, String topicId) {
		int successRow=0;
		successRow=itemDao.removeTopic2Cart(teachingPlanId, topicId);
		return successRow;
	}
	@Transactional
	public int batchRemoveTopic2Cart( String teachingPlanId,String topicIds ) {
		int successRow=0;
		successRow=itemDao.batchRemoveTopic2Cart(teachingPlanId, Arrays.asList(topicIds.split(",")));
		return successRow;
	}
	
	@Transactional
	public int batchSaveTopicCart(TopicCart topicCarts) {
		
		int successRow=0;
		List<String> topicIds = Arrays.asList(topicCarts.getTopicIds().split(","));
		if(topicIds!=null&&topicIds.size()>0) {
			List<TopicCart> to=new ArrayList<>();
			topicIds.forEach(id->{
				TopicCart topic=new TopicCart();
				topic.setId(sequenceId.nextId());
				topic.setTeachingPlanId(topicCarts.getTeachingPlanId());
				topic.setTopicId(id);
				to.add(topic);
			});
			successRow=itemDao.batchSaveTopicCart(to);
		}
		
		return successRow;
	}
	
	private Query buildQuery(TopicConditions condition) {
		
		Query query = new Query();
		if (StringUtils.isNotBlank(condition.getChapterId())) {
			List<String> knowledgePointIds = knowledgePointDao.findKnowledgePointIdByChapterId(condition.getChapterId());
			query.addCriteria(Criteria.where("knowledges._id").in(knowledgePointIds));
		}
		if (StringUtils.isNotBlank(condition.getSqIds())) {
			
			query.addCriteria(Criteria.where("_id").in(Arrays.asList(condition.getSqIds().split(","))));
		}
		if (StringUtils.isNotBlank(condition.getContent())) {
			query.addCriteria(Criteria.where("content").regex(String.format(".*%s.*", condition.getContent())));
		}
		if (StringUtils.isNotBlank(condition.getTypeIds())) {
			List<String> asList = Arrays.asList(condition.getTypeIds().split(","));
			query.addCriteria(Criteria.where("typeId").in(asList.stream().map(Integer::valueOf).collect(Collectors.toList())));
		}
		if(condition.getPager()!=null&&condition.getPager().getPaging()){
            query.with(condition.getPager());
        }
		return query;
	}
	
}
