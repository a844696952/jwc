package com.yice.edu.cn.xw.service.dj.partyMemberDoc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.AggregateIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocLeader;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.common.pojo.xw.document.SendObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class XwDjDocService {



    @Autowired
    private MongoTemplate mot;

    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public Doc findDocById(String id) {
        Document document = new Document("$match",new Document("_id",id));
        Document document1 = new Document("$lookup",new Document("from","docManagement").append("let",new Document("id","$_id")).append("pipeline", Arrays.asList(new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$id")),new Document("$eq",Arrays.asList("$departmentType",1)))))))).append("as","docManagement"));
        Document document2 = new Document("$lookup",new Document("from","docManagement").append("let",new Document("id","$_id")).append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$id")),new Document("$ne",Arrays.asList("$departmentType",0)))))))).append("as","sendObjects"));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("doc").aggregate(Arrays.asList(document,document1,document2));
        Doc data = mot.getConverter().read(Doc.class, aggregateIterable.first());
        if (CollectionUtil.isNotEmpty(data.getSendObjects())) {
            long unReadNum = data.getSendObjects().stream().filter(item -> !item.getFlag() && item.getType() == 1).count();
            long readNum = data.getSendObjects().stream().filter(item -> !item.getFlag() && item.getType() == 2).count();
            data.setUnReadNum((int) unReadNum);
            data.setReadNum((int) readNum);
        }
        return data;
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
                docManagement.setDocObjectId(f.getTeacherId());
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

        // 推送给批阅领导
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(leaderIdList,"党建-公文通知","您有一条新的公文通知，请及时审批！", Extras.XW_DJ_DOC_TYPE,id);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        //加入推荐详情
        if(CollectionUtil.isNotEmpty(sendObjects)){
            String[] sendObject = new String[sendObjects.size()];
            for(int i=0;i<sendObjects.size();i++){
                sendObject[i] = sendObjects.get(i).getTeacherId();
            }
            final Push push1 = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(sendObject,"党建-公文通知","您有一条新的公文通知，请及时查收！",Extras.XW_DJ_DOC_TYPE,id);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));
        }
    }



   /* public  void buildPushDetail(String msg, String[] array,String docId){
        PushDetail pushDetail = new PushDetail ();
        Map<String,Object> content = new HashMap<>(16);
        List<Receiver> receiverList = new ArrayList<>();
        Receiver receiver ;
        if (Objects.nonNull (array) && array.length > 0) {
            for(int i=0;i<array.length;i++){
                receiver = new Receiver();
                receiver.setIsRead(false);
                receiver.setReceiverId(array[i]);
                receiverList.add(receiver);
            }
        }
        content.put("title","党建公文通知");
        content.put("content","您有一条新的公文通知，请及时"+msg+"！");
        pushDetail.setId(sequenceId.nextId());
        pushDetail.setType(Extras.XW_DJ_DOC_TYPE);
        pushDetail.setContent(content);
        pushDetail.setReferenceId(docId);
        pushDetail.setReceiverList(receiverList);
        pushDetail.setSendDate (DateUtil.format (DateTime.now (),"yyyy-MM-dd HH:mm:ss"));
        if (Optional.ofNullable (pushDetail).isPresent ()) {
            mot.insert (pushDetail);
        }
    }*/


    public List<Doc> findDocListByCondition(Doc doc) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        operations.add(Aggregation.match(criteria));
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteria.andOperator(Criteria.where("receiptTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]));
        }
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
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteria.andOperator(Criteria.where("createTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]));
        }
        return mot.count(query(criteria), Doc.class);
    }





}
