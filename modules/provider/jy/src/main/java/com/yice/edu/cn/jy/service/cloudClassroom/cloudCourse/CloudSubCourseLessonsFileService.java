package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CloudSubCourseLessonsFileService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudSubCourseLessonsFile findCloudSubCourseLessonsFileById(String id) {
        return mot.findById(id,CloudSubCourseLessonsFile.class);
    }
    public void saveCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        cloudSubCourseLessonsFile.setCreateTime(DateUtil.now());
        cloudSubCourseLessonsFile.setId(sequenceId.nextId());
        mot.insert(cloudSubCourseLessonsFile);
    }
    public List<CloudSubCourseLessonsFile> findCloudSubCourseLessonsFileListByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourseLessonsFile.class)).find(MongoKit.buildMatchDocument(cloudSubCourseLessonsFile));
        Pager pager = cloudSubCourseLessonsFile.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CloudSubCourseLessonsFile> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CloudSubCourseLessonsFile.class, document)));
        return list;
    }
    public long findCloudSubCourseLessonsFileCountByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        return mot.getCollection(mot.getCollectionName(CloudSubCourseLessonsFile.class)).countDocuments(MongoKit.buildMatchDocument(cloudSubCourseLessonsFile));
    }
    public CloudSubCourseLessonsFile findOneCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CloudSubCourseLessonsFile.class)).find(MongoKit.buildMatchDocument(cloudSubCourseLessonsFile));
       MongoKit.appendProjection(query,cloudSubCourseLessonsFile.getPager());
       return mot.getConverter().read(CloudSubCourseLessonsFile.class,query.first());
    }

    public void updateCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        mot.updateFirst(query(where("id").is(cloudSubCourseLessonsFile.getId())), MongoKit.update(cloudSubCourseLessonsFile,false),CloudSubCourseLessonsFile.class);
    }
    public void updateCloudSubCourseLessonsFileForAll(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        mot.updateFirst(query(where("id").is(cloudSubCourseLessonsFile.getId())), MongoKit.update(cloudSubCourseLessonsFile,true),CloudSubCourseLessonsFile.class);
    }
    public void deleteCloudSubCourseLessonsFile(String id) {
        mot.remove(query(where("id").is(id)),CloudSubCourseLessonsFile.class);
    }
    public void deleteCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        mot.getCollection(mot.getCollectionName(CloudSubCourseLessonsFile.class)).deleteMany(MongoKit.buildMatchDocument(cloudSubCourseLessonsFile));
    }
    public void batchSaveCloudSubCourseLessonsFile(List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFiles){
        cloudSubCourseLessonsFiles.forEach(cloudSubCourseLessonsFile -> cloudSubCourseLessonsFile.setId(sequenceId.nextId()));
        mot.insertAll(cloudSubCourseLessonsFiles);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
