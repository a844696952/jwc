package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;import com.yice.edu.cn.common.pojo.cc.cloudCourse.UploadCloudCourseParam;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CloudCourseResourceService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudCourseResource findCloudCourseResourceById(String id) {
        return mot.findById(id,CloudCourseResource.class);
    }
    public void saveCloudCourseResource(CloudCourseResource cloudCourseResource) {
        cloudCourseResource.setCreateTime(DateUtil.now());
        cloudCourseResource.setUpdateTime(DateUtil.now());
        cloudCourseResource.setId(sequenceId.nextId());
        mot.insert(cloudCourseResource);
    }
    public List<CloudCourseResource> findCloudCourseResourceListByCondition(CloudCourseResource cloudCourseResource) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseResource.class)).find(MongoKit.buildMatchDocument(cloudCourseResource));
        Pager pager = cloudCourseResource.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudCourseResource> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourseResource.class, document)));
        return list;
    }
    public long findCloudCourseResourceCountByCondition(CloudCourseResource cloudCourseResource) {
        return mot.getCollection(mot.getCollectionName(CloudCourseResource.class)).countDocuments(MongoKit.buildMatchDocument(cloudCourseResource));
    }
    public CloudCourseResource findOneCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseResource.class)).find(MongoKit.buildMatchDocument(cloudCourseResource));
       MongoKit.appendProjection(query,cloudCourseResource.getPager());
       return mot.getConverter().read(CloudCourseResource.class,query.first());
    }

    public void updateCloudCourseResource(CloudCourseResource cloudCourseResource) {
        mot.updateFirst(query(where("id").is(cloudCourseResource.getId())), MongoKit.update(cloudCourseResource),CloudCourseResource.class);
    }
    public void deleteCloudCourseResource(String id) {
        mot.remove(query(where("id").is(id)),CloudCourseResource.class);
    }
    public void deleteCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource) {
        mot.getCollection(mot.getCollectionName(CloudCourseResource.class)).deleteMany(MongoKit.buildMatchDocument(cloudCourseResource));
    }
    public void batchSaveCloudCourseResource(List<CloudCourseResource> cloudCourseResources){
        cloudCourseResources.forEach(cloudCourseResource -> cloudCourseResource.setId(sequenceId.nextId()));
        mot.insertAll(cloudCourseResources);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<CloudCourseResource> findRecordingAndBroadcastingResources(CloudCourseResource cloudCourseResource) {
        //1.获取云课堂上课信息资源
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseResource.class)).find(MongoKit.buildMatchDocument(cloudCourseResource));
        Pager pager = cloudCourseResource.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudCourseResource> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourseResource.class, document)));
        //2.获取云课堂资料对应云课堂上课资源表
        List<CloudCourseResource> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for(int i=0;i<list.size();i++){
                CloudCourseResource temp = list.get(i);
                CloudCourseFileResource cCFileResource = new CloudCourseFileResource();
                cCFileResource.setCloudCourseResourceId(temp.getId());
                long count = mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).countDocuments(MongoKit.buildMatchDocument(cCFileResource));
                temp.setCount(count);
                result.add(temp);
            }
        }
        return result;
    }

    public void deleteCloudCourseResourceRe(String id) {
        //1.先删除云课堂上课资源信息表
        CloudCourseFileResource cCFileResource = new CloudCourseFileResource();
        cCFileResource.setCloudCourseResourceId(id);
        mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).deleteMany(MongoKit.buildMatchDocument(cCFileResource));
        //2.后删除云课堂上课信息资源
        mot.remove(query(where("id").is(id)),CloudCourseResource.class);
    }

}
