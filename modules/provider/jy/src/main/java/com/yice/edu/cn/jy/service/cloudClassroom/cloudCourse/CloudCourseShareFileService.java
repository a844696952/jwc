package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseShareFile;

import cn.hutool.core.date.DateUtil;

@Service
public class CloudCourseShareFileService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudCourseShareFile findCloudCourseShareFileById(String id) {
        return mot.findById(id,CloudCourseShareFile.class);
    }
    public void saveCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile) {
        cloudCourseShareFile.setCreateTime(DateUtil.now());
        cloudCourseShareFile.setId(sequenceId.nextId());
        mot.insert(cloudCourseShareFile);
    }
    public List<CloudCourseShareFile> findCloudCourseShareFileListByCondition(CloudCourseShareFile cloudCourseShareFile) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseShareFile.class)).find(MongoKit.buildMatchDocument(cloudCourseShareFile));
        Pager pager = cloudCourseShareFile.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudCourseShareFile> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudCourseShareFile.class, document)));
        return list;
    }
    public long findCloudCourseShareFileCountByCondition(CloudCourseShareFile cloudCourseShareFile) {
        return mot.getCollection(mot.getCollectionName(CloudCourseShareFile.class)).countDocuments(MongoKit.buildMatchDocument(cloudCourseShareFile));
    }
    public CloudCourseShareFile findOneCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudCourseShareFile.class)).find(MongoKit.buildMatchDocument(cloudCourseShareFile));
       MongoKit.appendProjection(query,cloudCourseShareFile.getPager());
       return mot.getConverter().read(CloudCourseShareFile.class,query.first());
    }

    public void updateCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile) {
        mot.updateFirst(query(where("id").is(cloudCourseShareFile.getId())), MongoKit.update(cloudCourseShareFile),CloudCourseShareFile.class);
    }
    public void deleteCloudCourseShareFile(String id) {
        mot.remove(query(where("id").is(id)),CloudCourseShareFile.class);
    }
    public void deleteCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile) {
        mot.getCollection(mot.getCollectionName(CloudCourseShareFile.class)).deleteMany(MongoKit.buildMatchDocument(cloudCourseShareFile));
    }
    public void batchSaveCloudCourseShareFile(List<CloudCourseShareFile> cloudCourseShareFiles){
        cloudCourseShareFiles.forEach(cloudCourseShareFile -> cloudCourseShareFile.setId(sequenceId.nextId()));
        mot.insertAll(cloudCourseShareFiles);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
