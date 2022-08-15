package com.yice.edu.cn.xw.service.doc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.AggregateIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocLeader;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.common.pojo.xw.document.SendObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DocService {
    @Autowired
    private MongoTemplate mot;

    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DocLeaderService docLeaderService;



    public Doc findDocById(String id) {
        Document document = new Document("$match",new Document("_id",id));
        Document document1 = new Document("$lookup",new Document("from","docManagement").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$id")),new Document("$eq",Arrays.asList("$departmentType",1)))))))).append("as","docManagement"));
        Document document2 = new Document("$lookup",new Document("from","docManagement").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$id")),new Document("$ne",Arrays.asList("$departmentType",0)))))))).append("as","sendObjects"));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document,document1,document2));
        return  mot.getConverter().read(Doc.class,aggregateIterable.first());
    }


    public void saveDoc(Doc doc) {
        String id = sequenceId.nextId();
        //抄送人对象
        List<SendObject> sendObjects = doc.getSendObject();
        doc.setSendObject(null);
        List<DocManagement> docManagements = new ArrayList<>();
        if(sendObjects!=null){
            //插入关系表
            sendObjects.forEach(f->{
                DocManagement docManagement = new DocManagement();
                docManagement.setId(sequenceId.nextId());
                docManagement.setDocObjectId(f.getId());
                docManagement.setDocObjectName(f.getName());
                docManagement.setDocId(id);
                docManagement.setType(1);
                docManagement.setFlag(true);
                docManagement.setCreateTime(DateUtil.now());
                docManagement.setSchoolId(doc.getSchoolId());
                if(f.getImgUrl()!=null){
                    docManagement.setImgUrl(f.getImgUrl());
                }else{
                    docManagement.setImgUrl("/headProfile/man.png");
                }
                docManagements.add(docManagement);
               /* mot.insert(docManagement,"docManagement");*/
            });
        }
        mot.insertAll(docManagements);


        doc.setCreateTime(DateUtil.now());
        doc.setId(id);
        doc.setDocumentType(1);
        doc.setFileUrlList(doc.getFileUrl().split("\\|"));
        doc.setFileNameList(doc.getFileName().split("\\|"));
        doc.setFlag(false);

        //批阅领导
        DocLeader docLeader = new DocLeader();
        docLeader.setLeaderId(doc.getLeaderId());
        docLeader.setLeaderName(doc.getLeaderName());
        docLeader.setReceiptTime(doc.getReceiptTime());
        docLeader.setCreateTime(DateUtil.now());
        docLeader.setType(1);
        List<DocLeader> leaders = new ArrayList<>();
        leaders.add(docLeader);
        doc.setLeaderList(leaders);
        mot.insert(doc);

        String[] leaderIdList = new String[]{doc.getLeaderId()};
        //推送给批阅领导
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(leaderIdList,"公文通知","您有一条新的公文通知，请及时审批！",Extras.XW_DOC_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));


        if(sendObjects!=null){
            String[] sendObject = new String[sendObjects.size()];
            for(int i=0;i<sendObjects.size();i++){
                sendObject[i] = sendObjects.get(i).getId();
            }

            final Push push1 = Push.newBuilder(JpushApp.TAP).getSimplePush(sendObject,"公文通知","您有一条新的公文通知，请及时查收！",Extras.XW_DOC_TYPE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));
        }

    }

    public List<Doc> findDocListByCondition(Doc doc) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        operations.add(Aggregation.match(criteria));
        Criteria criteriaTime = new Criteria();
        Criteria criteriaType = new Criteria();
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteriaTime = where("receiptTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]);
        }
        if(Objects.isNull(doc.getDocNumberType())){
            criteriaType = where("docNumberType").in(1,2);
        }
        criteria.andOperator(criteriaTime,criteriaType);
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"documentType");
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"receiptTime");
        orders.add(order);
        orders.add(order1);
        Sort sort = new Sort(orders);
        if(doc.getPager()!=null&&doc.getPager().getPage()!=null&&doc.getPager().getPage()<1){
            doc.getPager().setPage(1);
        }
        int page = (doc.getPager().getPage()-1)*doc.getPager().getPageSize();
        operations.add(Aggregation.sort(sort));
        operations.add(Aggregation.skip(page));
        operations.add(Aggregation.limit(doc.getPager().getPageSize()));
        return mot.aggregate(Aggregation.newAggregation(Doc.class,operations),Doc.class).getMappedResults();
    }
    public long findDocCountByCondition(Doc doc) {
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        Criteria criteriaTime = new Criteria();
        Criteria criteriaType = new Criteria();
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteriaTime = where("receiptTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]);
        }
        if(Objects.isNull(doc.getDocNumberType())){
            criteriaType = where("docNumberType").in(1,2);
        }
        criteria.andOperator(criteriaTime,criteriaType);
        return mot.count(query(criteria), Doc.class);
    }

    public Doc findOneDocByCondition(Doc doc) {
        Example<Doc> example = Example.of(doc, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = doc.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(Doc.class,operations),Doc.class).getUniqueMappedResult();
    }

    public void updateDoc(Doc doc) {
        doc.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(doc.getId())), MongoKit.update(doc),Doc.class);
    }
    public void deleteDoc(String id) {
        mot.remove(Query.query(where("id").is(id)),Doc.class);
    }
    public void deleteDocByCondition(Doc doc) {
        Example<Doc> example = Example.of(doc, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, Doc.class);
    }
    public void batchSaveDoc(List<Doc> docs){
        docs.forEach(doc -> doc.setId(sequenceId.nextId()));
        mot.insertAll(docs);
    }
}
