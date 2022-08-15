package com.yice.edu.cn.xw.service.doc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.document.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DocLeaderService {
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DocService docService;

    @Autowired
    private DocManagementService docManagementService;


    //返回前台展示数据
    public List<Doc> findDocAndDocLeaderList(Doc doc){

        Criteria criteria2 = new Criteria();
         String userId = doc.getUserId();
           doc.setUserId(null);
        if(doc.getDocumentType()==null){
            doc.setDocumentType(4);
        }
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        Criteria criteria3 = Criteria.where("leaderId").is(userId);
        Criteria criteria1 = Criteria.where("leaderList").elemMatch(criteria3);
        Criteria criteria4 = new Criteria();
        boolean flag = false;
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteria4 = Criteria.where("receiptTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]);
            flag = true;
        }
        Criteria criteriaType = new Criteria();
        if (Objects.isNull(doc.getDocNumberType())) {
            criteriaType = where("docNumberType").in(1, 2);
        }
        if(doc.getDocumentType()==2){//完成
            criteria2 = Criteria.where("documentType").is(doc.getDocumentType()).andOperator(criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else if(doc.getDocumentType()==1){//待我审核
            doc.setDocumentType(null);
            criteria2 = Criteria.where("leaderId").is(userId).andOperator(Criteria.where("documentType").is(1),criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else if(doc.getDocumentType()==3){//待他人审核
            doc.setDocumentType(null);
            criteria2 = Criteria.where("leaderId").ne(userId).andOperator(Criteria.where("documentType").is(1),criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else{//查询出所有有我参与的记录
            doc.setDocumentType(null);
            if(flag){
                criteria.andOperator(criteria1, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria1, criteriaType);
            }
        }

        operations.add(Aggregation.match(criteria));
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
        List<Doc> mappedResults = mot.aggregate(Aggregation.newAggregation(Doc.class, operations), Doc.class).getMappedResults();
        return mappedResults;

    }

    public long findDocCountByCondition(Doc doc) {
        Criteria criteria2 = new Criteria();
        String userId = doc.getUserId();
        doc.setUserId(null);
        if(doc.getDocumentType()==null){
            doc.setDocumentType(4);
        }
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        Criteria criteria3 = Criteria.where("leaderId").is(userId);
        Criteria criteria1 = Criteria.where("leaderList").elemMatch(criteria3);
        Criteria criteria4 = new Criteria();
        boolean flag = false;
        if(doc.getSearchTimeZone()!=null&&doc.getSearchTimeZone().length==2){
            criteria4 = Criteria.where("createTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]);
            flag = true;
        }
        Criteria criteriaType = new Criteria();
        if (Objects.isNull(doc.getDocNumberType())) {
            criteriaType = where("docNumberType").in(1, 2);
        }
        if(doc.getDocumentType()==2){//完成
            criteria2 = Criteria.where("documentType").is(doc.getDocumentType()).andOperator(criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else if(doc.getDocumentType()==1){//待我审核
            doc.setDocumentType(null);
            criteria2 = Criteria.where("leaderId").is(userId).andOperator(Criteria.where("documentType").is(1),criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else if(doc.getDocumentType()==3){//待他人审核
            doc.setDocumentType(null);
            criteria2 = Criteria.where("leaderId").ne(userId).andOperator(Criteria.where("documentType").is(1),criteria1);
            if(flag){
                criteria.andOperator(criteria2, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria2, criteriaType);
            }
        }else{//查询出所有有我参与的记录
            doc.setDocumentType(null);
            if(flag){
                criteria.andOperator(criteria1, criteria4, criteriaType);
            }else{
                criteria.andOperator(criteria1, criteriaType);
            }
        }
        return mot.count(query(criteria), Doc.class);
    }

    //审核完成调用的方法
    public void saveUpdateDocCompletion(Doc doc){
        doc.setUpdateTime(DateUtil.now());
        List<DocLeader> list = doc.getLeaderList();
        list.get(list.size()-1).setUpdateTime(DateUtil.now());
        doc.setLeaderList(list);
        //脏数据不入库（App端）
        doc.setDocManagement(null);
        doc.setSendObjects(null);
        doc.setUnReadNum(null);
        doc.setReadNum(null);
        mot.save(doc,"doc");

        //推送给创建用户

        String[] userId = new String[]{doc.getUserId()};
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(userId,"公文通知","您有一条公文申请已通过批阅，请注意查收！",Extras.XW_DOC_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

    }

    //审批完成后，添加发送对象
    public Doc saveDocManagement(DocDepartment docDepartment, String docId){
        List<SendObject> sendObjects = docDepartment.getSendObjects();
        List<Department> departmentList = docDepartment.getDepartments();
        Criteria criteria1 = Criteria.where("_id").is(docId);
        Query query = new Query(criteria1);
        Update update = new Update();
        //已经发送给教师,记录未读已读人数
        update.set("flag",true);
        update.set("readNum",0);
        update.set("unReadNum",sendObjects.size());
        Doc doc = mot.findAndModify(query,update,FindAndModifyOptions.options().returnNew(true),Doc.class);

        /*DocManagement docManagement1 = new DocManagement();
        docManagement1.setDocId(docId);
        List<DocManagement> docManagementList = docManagementService.findDocManagementListByCondition(docManagement1);
*/
        String[] sendObject = new String[sendObjects.size()];
        List<DocManagement> docManagements = new ArrayList<>();
        //添加发送对象
        sendObjects.forEach(f->{
                DocManagement docManagement = new DocManagement();
                docManagement.setDocObjectId(f.getId());
                docManagement.setDocId(docId);
                docManagement.setId(sequenceId.nextId());
                docManagement.setType(1);
                docManagement.setDocObjectName(f.getName());
                docManagement.setCreateTime(DateUtil.now());
                docManagement.setDepartmentParentId(f.getParentId());
                docManagement.setSchoolId(f.getSchoolId());
                if(f.getImgUrl()!=null){
                    docManagement.setImgUrl(f.getImgUrl());
                }else{
                    docManagement.setImgUrl("/headProfile/man.png");
                }

                docManagement.setDepartmentType(f.getType());
                docManagement.setFlag(false);
                docManagements.add(docManagement);
               /* mot.insert(docManagement,"docManagement");*/
        });
        removeDuplicate(docManagements);
       /*  组织架构
       List<DocManagement> docManagements1 = removeDuplicate(addMyParentId(docManagements,departmentList));
        docManagements1.forEach(f->{
            f.setId(sequenceId.nextId());
            f.setDocId(docId);
            docManagement.setFlag(false);
        });
        docManagements.addAll(docManagements1);*/
        mot.insertAll(docManagements);

        for(int i =0;i<sendObjects.size();i++){
            sendObject[i] = sendObjects.get(i).getId();
        }

//        Push push= Push.newBuilder(JpushApp.TAP)
//                .setAlias(sendObject)
//                .setNotification(Push.Notification.newBuilder().setTitle("您有一条新的公文通知，请及时查收！").setAlert("公文通知").setExtras(Extras.newBuilder().setType(Extras.XW_DOC_TYPE).build()).build())
//                .setMessage(Push.Message.newBuilder().setAlert("公文通知").setMsgContent("您有一条新的公文通知，请及时查收！").setTitle("您有一条新的公文通知，请及时查收！").build())
//                .setOptions(Push.Options.newBuilder().build())
//                .build();
//
//        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH,JSONUtil.toJsonStr(push));

        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(sendObject,"公文通知","您有一条新的公文通知，请及时查收！",Extras.XW_DOC_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

        return doc;
    }

    //转他人审核调用的方法
    public void saveUpdateDocLeaterCompletion(Doc doc){
        doc.setUpdateTime(DateUtil.now());
        List<DocLeader> list = doc.getLeaderList();
        //修改审核人的批阅时间
        list.get(list.size()-1).setUpdateTime(DateUtil.now());
        doc.setLeaderList(list);
        //脏数据不入库（App端）
        doc.setDocManagement(null);
        doc.setSendObjects(null);
        doc.setReadNum(null);
        doc.setUnReadNum(null);
        mot.save(doc,"doc");

        Criteria criteria = Criteria.where("_id").is(doc.getId());
        Query query = new Query(criteria);
        Update update = new Update();
        DocLeader leader = new DocLeader();

        leader.setType(1);
        leader.setLeaderId(doc.getLeaderId());
        leader.setLeaderName(doc.getLeaderName());
        leader.setCreateTime(DateUtil.now());
        leader.setReceiptTime(DateUtil.now());
        //添加转发审核人对象
        update.push("leaderList",leader);
        mot.updateFirst(query,update,"doc");

        String[] leaderIdList = new String[]{doc.getLeaderId()};
        //推送给批阅领导
//        Push push= Push.newBuilder(JpushApp.TAP)
//                .setAlias(leaderIdList)
//                .setNotification(Push.Notification.newBuilder().setTitle("您有一条新的公文通知，请及时审批！").setAlert("公文通知").setExtras(Extras.newBuilder().setType(Extras.XW_DOC_TYPE).build()).build())
//                .setMessage(Push.Message.newBuilder().setAlert("公文通知").setMsgContent("您有一条新的公文通知，请及时审批！").setTitle("您有一条新的公文通知，请及时审批！").build())
//                .setOptions(Push.Options.newBuilder().build())
//                .build();
//
//        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH,JSONUtil.toJsonStr(push));
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(leaderIdList,"公文通知","您有一条新的公文通知，请及时审批！",Extras.XW_DOC_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }



    /**
     * 去除重复
     * @param list
     * @return
     */
    public   static   List  removeDuplicate(List<DocManagement> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).getDocObjectId().equals(list.get(i).getDocObjectId()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 通过发送对象筛选出所有的上级节点
     * @param writingManagements
     * @param departmentList
     * @return
     */
    public List<DocManagement> addMyParentId(List<DocManagement> writingManagements,List<Department> departmentList){
        ArrayList<DocManagement> writingManagements1=new ArrayList<>();
        for  ( int  i  =   0 ; i  <  writingManagements.size() ; i ++ )  {
            List<DocManagement> resultList=findMyParentId(writingManagements.get(i).getDepartmentParentId(),departmentList,writingManagements1);
            if(resultList!=null)
                writingManagements1.addAll(resultList);
        }
        return writingManagements1;
    }

    public List<DocManagement> findMyParentId(String parentId, List<Department> departmentList, ArrayList<DocManagement> writingManagementArrayList){
        for (int i = 0; i <departmentList.size(); i++) {
            if(departmentList.get(i).getId().equals(parentId)){
                DocManagement writingManagement=new DocManagement();
                writingManagement.setDocObjectId(departmentList.get(i).getId());
                writingManagement.setDepartmentParentId(departmentList.get(i).getParentId());
                writingManagement.setDocObjectName(departmentList.get(i).getName());
                writingManagement.setDepartmentType(departmentList.get(i).getType());
                writingManagement.setSchoolId(departmentList.get(i).getSchoolId());
                writingManagementArrayList.add(writingManagement);
                if(!departmentList.get(i).getParentId().equals("-1")){
                    findMyParentId(departmentList.get(i).getParentId(),departmentList,writingManagementArrayList);
                }else {
                    return writingManagementArrayList;
                }
            }
        }
        return null;
    }

}
