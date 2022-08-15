package com.yice.edu.cn.jw.service.appIndex;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DocManagementService {
    @Autowired
    private MongoTemplate mot;

    public long findDocManagementCountByCondition(Doc doc) {
        Document document1 = new Document("$lookup",new Document("from","docManagement").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$id")),new Document("$eq",Arrays.asList("$docObjectId",doc.getUserId())))))))).append("as","docManagement"));

        Document document2 = new Document();
        //时间段筛选和是否查看筛选条件
        if(doc.getType()!=null&&doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            document2 = new Document("$match",new Document("receiptTime",new Document("$gte",doc.getSearchTimeZone()[0]).append("$lt",doc.getSearchTimeZone()[1])).append("docManagement",new Document("$elemMatch",new Document("type",doc.getType()).append("docObjectId",doc.getUserId()))));
        }else if(doc.getType()!=null){
            //是否查看筛选条件
            document2 = new Document("$match",new Document("docManagement",new Document("$elemMatch",new Document("docObjectId",doc.getUserId()).append("type",doc.getType()))));
        }else if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            //时间段筛选添加
            document2 = new Document("$match",new Document("docManagement",new Document("$elemMatch",new Document("docObjectId",doc.getUserId()))).append("receiptTime",new Document("$gte",doc.getSearchTimeZone()[0]).append("$lt",doc.getSearchTimeZone()[1])));
        }else{
            //无条件查询
            document2 = new Document("$match",new Document("docManagement",new Document("$elemMatch",new Document("docObjectId",doc.getUserId()))));
        }

        AggregateIterable<Document> aggregateIterable;
        if(doc.getDocNumberType()!=null){
            Document document = new Document("$match",new Document("docNumberType",doc.getDocNumberType()));
            aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document,document1,document2));
        }else{
            aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document1,document2));
        }
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<Doc> docs = new ArrayList<>();
        while(mongoCursor.hasNext()){
            Doc doc1 = mot.getConverter().read(Doc.class,mongoCursor.next());
            docs.add(doc1);
        }
        return docs.size();
    }
}
