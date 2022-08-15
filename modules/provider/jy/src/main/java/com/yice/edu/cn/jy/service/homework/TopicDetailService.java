package com.yice.edu.cn.jy.service.homework;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicDetailService {

    @Autowired
    private MongoTemplate mot;




    public TopicDetail findTopicDetailId(String id) {
        return mot.findById(id, TopicDetail.class);
    }



    public List<TopicDetail> findTopicDetailListByCondition(TopicDetail topicDetail) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TopicDetail.class)).find(MongoKit.buildMatchDocument(topicDetail));
        Pager pager = topicDetail.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<TopicDetail> topicDetails = new ArrayList<>();
        query.forEach((Block<Document>) document -> topicDetails.add(mot.getConverter().read(TopicDetail.class, document)));
        return topicDetails;
    }


}
