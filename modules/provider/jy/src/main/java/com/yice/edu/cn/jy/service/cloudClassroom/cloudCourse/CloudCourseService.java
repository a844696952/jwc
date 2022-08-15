package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CloudCourseService {
	@Autowired
	private MongoTemplate mot;
	@Autowired
	private SequenceId sequenceId;

	/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
	public CloudCourse findCloudCourseById(String id) {
		return mot.findById(id, CloudCourse.class);
	}

	public void saveCloudCourse(CloudCourse cloudCourse) {
		cloudCourse.setCreateTime(DateUtil.now());
		cloudCourse.setUpdateTime(DateUtil.now());
		cloudCourse.setId(sequenceId.nextId());
		mot.insert(cloudCourse);
	}

	public List<CloudCourse> findCloudCourseListByCondition(CloudCourse cloudCourse) {
		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class))
				.find(MongoKit.buildMatchDocument(cloudCourse));
		Pager pager = cloudCourse.getPager();
		MongoKit.appendSort(query, pager);
		MongoKit.appendPage(query, pager);
		MongoKit.appendProjection(query, pager);
		List<CloudCourse> list = new ArrayList<>();
		query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourse.class, document)));
		return list;
	}

	public long findCloudCourseCountByCondition(CloudCourse cloudCourse) {
		return mot.getCollection(mot.getCollectionName(CloudCourse.class))
				.countDocuments(MongoKit.buildMatchDocument(cloudCourse));
	}

	public CloudCourse findOneCloudCourseByCondition(CloudCourse cloudCourse) {
		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class))
				.find(MongoKit.buildMatchDocument(cloudCourse));
		MongoKit.appendProjection(query, cloudCourse.getPager());
		return mot.getConverter().read(CloudCourse.class, query.first());
	}

	public void updateCloudCourse(CloudCourse cloudCourse) {
		cloudCourse.setUpdateTime(DateUtil.now());
		mot.updateFirst(query(where("id").is(cloudCourse.getId())), MongoKit.update(cloudCourse), CloudCourse.class);
	}

	public void deleteCloudCourse(String id) {
		mot.remove(query(where("id").is(id)), CloudCourse.class);
	}

	public void deleteCloudCourseByCondition(CloudCourse cloudCourse) {
		mot.getCollection(mot.getCollectionName(CloudCourse.class))
				.deleteMany(MongoKit.buildMatchDocument(cloudCourse));
	}

	public void batchSaveCloudCourse(List<CloudCourse> cloudCourses) {
		cloudCourses.forEach(cloudCourse -> cloudCourse.setId(sequenceId.nextId()));
		mot.insertAll(cloudCourses);
	}
	/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

	/**
	 * 我的云课堂他人创建搜索
	 * @param cloudCourse
	 * @return
	 */
	public List<CloudCourse> findCloudCoursesByConditionOther(CloudCourse cloudCourse) {
		List<AggregationOperation> operationList = CollUtil.newArrayList();
        Criteria criteria = Criteria.where("createTeacher._id").ne(cloudCourse.getCreateTeacher().getId())
                .and("_id").in(cloudCourse.getCloudCourseIdList());
        if(StrUtil.isNotBlank(cloudCourse.getCreateName())) {
            criteria.and("createTeacher.name").regex(".*" + cloudCourse.getCreateName() + ".*");
        }
        operationList.add(Aggregation.match(criteria));
		MongoKit.sortPageInclude(cloudCourse.getPager(),operationList);
		List<CloudCourse> list = mot.aggregate(Aggregation.newAggregation(CloudCourse.class, operationList), CloudCourse.class).getMappedResults();
		return list;
	}

	/**
	 * 我的云课堂他人创建搜索数量
	 * @param cloudCourse
	 * @return
	 */
	public long findCloudCourseCountByConditionOther(CloudCourse cloudCourse) {
		Criteria criteria = MongoKit.buildCriteria(cloudCourse, null);
		criteria.and("createTeacher._id").ne(cloudCourse.getCreateTeacher().getId())
				.and("_id").in(cloudCourse.getCloudCourseIdList());
        if(StrUtil.isNotBlank(cloudCourse.getCreateName())) {
            criteria.and("createTeacher.name").regex(".*" + cloudCourse.getCreateName() + ".*");
        }
		return mot.count(Query.query(criteria),CloudCourse.class);
	}

	/**
	 * 我的云课堂我的创建搜索
	 * @param cloudCourse
	 * @return
	 */
    public List<CloudCourse> findCloudCoursesByConditionMine(CloudCourse cloudCourse) {
        List<AggregationOperation> operationList = CollUtil.newArrayList();
        operationList.add(Aggregation.match(Criteria.where("createTeacher._id").is(cloudCourse.getCreateTeacher().getId())
                .and("_id").in(cloudCourse.getCloudCourseIdList())));
        MongoKit.sortPageInclude(cloudCourse.getPager(),operationList);
        List<CloudCourse> list = mot.aggregate(Aggregation.newAggregation(CloudCourse.class, operationList), CloudCourse.class).getMappedResults();
        return list;
    }

	/**
	 * 我的云课堂我的创建搜索 数量
	 * @param cloudCourse
	 * @return
	 */
    public long findCloudCourseCountByConditionMine(CloudCourse cloudCourse) {
        Criteria criteria = MongoKit.buildCriteria(cloudCourse, null);
        criteria.and("createTeacher._id").is(cloudCourse.getCreateTeacher().getId())
                .and("_id").in(cloudCourse.getCloudCourseIdList());
        return mot.count(Query.query(criteria),CloudCourse.class);
    }


//	public void saveCloudCourseAndGenCode(CloudCourse cloudCourse) throws Exception {
//		cloudCourse.setCreateTime(DateUtil.now());
//		cloudCourse.setUpdateTime(DateUtil.now());
//		cloudCourse.setId(sequenceId.nextId());
//		// 生成课堂码
//		cloudCourse.setBroadcastCode(DigestUtil.md5Hex(cloudCourse.getId()));
//		mot.insert(cloudCourse);
//	}
//
//	public void updateCloudCourseAndGenCode(CloudCourse cloudCourse) throws Exception {
//		cloudCourse.setUpdateTime(DateUtil.now());
//		mot.updateFirst(query(where("id").is(cloudCourse.getId())), MongoKit.update(cloudCourse), CloudCourse.class);
//	}

//	public List<CloudCourse> findCloudCourseListForTeacher(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
//		matchDoc.append("inTime", new Document("$lte", now)).append("outTime", new Document("$gte", now));
//		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class)).find(matchDoc);
//		Pager pager = cloudCourse.getPager();
//		MongoKit.appendSort(query, pager);
//		MongoKit.appendPage(query, pager);
//		MongoKit.appendProjection(query, pager);
//		List<CloudCourse> list = new ArrayList<>();
//		query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourse.class, document)));
//		return list;
//	}

//	public long findCloudCourseCountForTeacher(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
//		matchDoc.append("inTime", new Document("$lte", now)).append("outTime", new Document("$gte", now));
//		return mot.getCollection(mot.getCollectionName(CloudCourse.class)).countDocuments(matchDoc);
//	}

//	public List<CloudCourse> findCloudCourseListForOther(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
//		matchDoc.append("inTime", new Document("$lte", now)).append("outTime", new Document("$gte", now)).append("$or",
//				Arrays.asList(new Document("listenTeachers._id", cloudCourse.getLoginId()),
//						new Document("otherSchoolAccounts._id", cloudCourse.getLoginId())));
//		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class)).find(matchDoc);
//		Pager pager = cloudCourse.getPager();
//		MongoKit.appendSort(query, pager);
//		MongoKit.appendPage(query, pager);
//		MongoKit.appendProjection(query, pager);
//		List<CloudCourse> list = new ArrayList<>();
//		query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourse.class, document)));
//		return list;
//	}

//	public long findCloudCourseCountForOther(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
//		matchDoc.append("inTime", new Document("$lte", now)).append("outTime", new Document("$gte", now)).append("$or",
//				Arrays.asList(new Document("listenTeachers._id", cloudCourse.getLoginId()),
//						new Document("otherSchoolAccounts._id", cloudCourse.getLoginId())));
//		return mot.getCollection(mot.getCollectionName(CloudCourse.class)).countDocuments(matchDoc);
//	}
	
//	public List<CloudCourse> findCloudCourseListForAll(CloudCourse cloudCourse) {
//		Document matchDoc =findCloudCourseListForAllCondition(cloudCourse);
//		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class)).find(matchDoc);
//		Pager pager = cloudCourse.getPager();
//		MongoKit.appendSort(query, pager);
//		MongoKit.appendPage(query, pager);
//		MongoKit.appendProjection(query, pager);
//		List<CloudCourse> list = new ArrayList<>();
//		query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourse.class, document)));
//		return list;
//	}

//	public long findCloudCourseCountForAll(CloudCourse cloudCourse) {
//		Document matchDoc =findCloudCourseListForAllCondition(cloudCourse);
//		return mot.getCollection(mot.getCollectionName(CloudCourse.class)).countDocuments(matchDoc);
//	}
	
//	private Document findCloudCourseListForAllCondition(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
//		matchDoc.append("inTime", new Document("$lte", now)).append("outTime", new Document("$gte", now)).append("$or",
//				Arrays.asList(new Document("listenTeachers._id", cloudCourse.getLoginId()),
//						new Document("otherSchoolAccounts._id", cloudCourse.getLoginId()),new Document("createTeacherId",cloudCourse.getLoginId())));
//
//		return matchDoc;
//	}
	
//	public List<CloudCourse> findCloudCourseValid(CloudCourse cloudCourse) {
//		Document matchDoc = MongoKit.buildMatchDocument(cloudCourse);
//		//当天的云课堂都是有效的云课堂
//		String now = DateUtil.format(new Date(), "yyyy-MM-dd ")+"00:00";
//		matchDoc.append("outTime", new Document("$gte", now));
//		FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourse.class)).find(matchDoc);
//
//		List<CloudCourse> list = new ArrayList<>();
//		query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourse.class, document)));
//		return list;
//	}
}
