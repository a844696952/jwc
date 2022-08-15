package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CloudCourseFileResourceService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudCourseFileResource findCloudCourseFileResourceById(String id) {
        return mot.findById(id, CloudCourseFileResource.class);
    }
    public void saveCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource) {
        CloudCourseFileResource.setId(sequenceId.nextId());
        mot.insert(CloudCourseFileResource);
    }
    public List<CloudCourseFileResource> findCloudCourseFileResourceListByCondition(CloudCourseFileResource CloudCourseFileResource) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).find(MongoKit.buildMatchDocument(CloudCourseFileResource));
        Pager pager = CloudCourseFileResource.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudCourseFileResource> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourseFileResource.class, document)));
        return list;
    }
    public long findCloudCourseFileResourceCountByCondition(CloudCourseFileResource CloudCourseFileResource) {
        return mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).countDocuments(MongoKit.buildMatchDocument(CloudCourseFileResource));
    }
    public CloudCourseFileResource findOneCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).find(MongoKit.buildMatchDocument(CloudCourseFileResource));
       MongoKit.appendProjection(query,CloudCourseFileResource.getPager());
       return mot.getConverter().read(CloudCourseFileResource.class,query.first());
    }

    public void updateCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource) {
        mot.updateFirst(query(where("id").is(CloudCourseFileResource.getId())), MongoKit.update(CloudCourseFileResource,false), CloudCourseFileResource.class);
    }
    public void updateCloudCourseFileResourceForAll(CloudCourseFileResource CloudCourseFileResource) {
        mot.updateFirst(query(where("id").is(CloudCourseFileResource.getId())), MongoKit.update(CloudCourseFileResource,true), CloudCourseFileResource.class);
    }
    public void deleteCloudCourseFileResource(String id) {
        mot.remove(query(where("id").is(id)), CloudCourseFileResource.class);
    }
    public void deleteCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource) {
        mot.getCollection(mot.getCollectionName(CloudCourseFileResource.class)).deleteMany(MongoKit.buildMatchDocument(CloudCourseFileResource));
    }
    public void batchSaveCloudCourseFileResource(List<CloudCourseFileResource> CloudCourseFileResources){
        CloudCourseFileResources.forEach(CloudCourseFileResource -> CloudCourseFileResource.setId(sequenceId.nextId()));
        mot.insertAll(CloudCourseFileResources);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
