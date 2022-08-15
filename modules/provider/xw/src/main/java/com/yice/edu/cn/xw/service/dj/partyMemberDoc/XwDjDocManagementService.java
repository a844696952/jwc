package com.yice.edu.cn.xw.service.dj.partyMemberDoc;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.common.util.object.ObjectKit;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class XwDjDocManagementService {

    @Autowired
    private MongoTemplate mot;

    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private XwDjDocService docService;

    //查询我接收的公文记录
    public List<Doc> findDocListByCondition(Doc doc){
        //将公文记录和当前教师返回 注：docManagement[0]是当前教师(docManagement数组只有一个对象，即当前教师)
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
        //根据是否已读（正序），收文时间（倒序）排序
        Document document3 = new Document("$sort",new Document("docManagement.0.type",1).append("receiptTime",-1));
        int page = (doc.getPager().getPage()-1)*doc.getPager().getPageSize();
        Document document4 = new Document("$skip",page);
        Document document5 = new Document("$limit",doc.getPager().getPageSize());
        AggregateIterable<Document> aggregateIterable;
        //公文类型添加（3 党建公文只差党建公文）
        Document document = new Document("$match",new Document("docNumberType",3));
        aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document,document1,document2,document3,document4,document5));

        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<Doc> docs = new ArrayList<>();
        while(mongoCursor.hasNext()){
            Doc doc1 = mot.getConverter().read(Doc.class,mongoCursor.next());
            docs.add(doc1);
        }

        return docs;


    }

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

        Document document = new Document("$match",new Document("docNumberType",3));
        aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document,document1,document2));

        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<Doc> docs = new ArrayList<>();
        while(mongoCursor.hasNext()){
            Doc doc1 = mot.getConverter().read(Doc.class,mongoCursor.next());
            docs.add(doc1);
        }
        return docs.size();
    }




    //筛选未读或者已读人员
    public List<DocManagement> findDocManagementReadList(DocManagement docManagement) {
        docManagement.setFlag(false);//排除掉抄送人
        docManagement.setDepartmentType(1);//排除掉组织架构
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(docManagement, docManagement.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(docManagement.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(DocManagement.class,operations),DocManagement.class).getMappedResults();

    }


    //查看详情并修改查看状态
    public Doc fingOneDocUpdateManagement(String docId,String docObjectId){
        Criteria criteria1 = Criteria.where("_id").is(docId);
        Query query = new Query(criteria1);

        Doc doc = docService.findDocById(docId);

        Criteria criteria11 = Criteria.where("docObjectId").is(docObjectId).andOperator(Criteria.where("docId").is(docId));
        Query query1 = new Query(criteria11);
        Update update = new Update();
        update.set("type",2);

        List<DocManagement> docManagement = mot.find(query1,DocManagement.class);
        docManagement.forEach(f->{
            if(f.getType()!=2&&!f.getFlag()){
                Update update1 = new Update();
                update1.inc("readNum",1);
                update1.inc("unReadNum",-1);
                mot.updateFirst(query,update1,Doc.class);

            }
        });

        mot.updateMulti(query1,update,"docManagement");
        return doc;
    }


    public List<Department> getDocManagementReadOrUnRead(DocManagement docManagement){
        docManagement.setDepartmentType(1);
        List<DocManagement> docManagement1 = findDocManagementByList(docManagement);

        docManagement.setType(null);
        docManagement.setDepartmentType(0);
        List<DocManagement> docManagement2 = findDocManagementByList(docManagement);

        return buildTree(docManagement1,docManagement2);
    }

    public  List<Department> buildTree(List<DocManagement> data1 , List<DocManagement> data2){
        List<DocManagement> data=new ArrayList<>();
        data.addAll(data1);
        data.addAll(data2);
        List<Department> departmentList=new ArrayList<>();
        data.forEach(f -> {
            Department department=new Department();
            department.setId(f.getDocObjectId());
            department.setName(f.getDocObjectName());
            department.setType(f.getDepartmentType());
            department.setParentId(f.getDepartmentParentId());
            department.setImgUrl(f.getImgUrl());
            departmentList.add(department);
        });
        return ObjectKit.buildTree(departmentList,"-1");

    }

    public List<DocManagement>  findDocManagementByList(DocManagement docManagement){
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(docManagement, docManagement.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(docManagement.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(DocManagement.class,operations),DocManagement.class).getMappedResults();
    }

    public long findDocManagementCountByCondition(DocManagement docManagement) {
        Criteria criteria = MongoKit.buildCriteria(docManagement, docManagement.getPager());
        return mot.count(query(criteria), DocManagement.class);
    }

    public List<DocManagement> findDocManagementListByCondition(DocManagement docManagement) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(docManagement, docManagement.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(docManagement.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(DocManagement.class,operations),DocManagement.class).getMappedResults();

    }
}
