package com.yice.edu.cn.xw.service.dj.partyMemberDoc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class XwDjDocLeaderService {
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private XwDjDocService xwDjDocService;

    /**
     * 返回前台展示数据
     */
    public List<Doc> findDocAndDocLeaderList(Doc doc) {
        Criteria criteria = getCondition(doc);
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(criteria));
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "documentType"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "receiptTime"));
        if (doc.getPager() != null && doc.getPager().getPage() != null && doc.getPager().getPage() < 1) {
            doc.getPager().setPage(1);
        }
        int page = (Objects.requireNonNull(doc.getPager()).getPage() - 1) * doc.getPager().getPageSize();
        operations.add(Aggregation.sort(new Sort(orders)));
        operations.add(Aggregation.skip(page));
        operations.add(Aggregation.limit(doc.getPager().getPageSize()));
        List<Doc> mappedResults = mot.aggregate(Aggregation.newAggregation(Doc.class, operations), Doc.class).getMappedResults();
        //修改已读未读问题
        if(CollUtil.isNotEmpty(mappedResults)){
            mappedResults.forEach(x->{
                Doc docInfo = xwDjDocService.findDocById(x.getId());
                if(Objects.nonNull(docInfo)){
                    Map<Integer, List<DocManagement>> collect = docInfo.getSendObjects().stream().filter(y -> !y.getFlag()).collect(groupingBy(DocManagement::getType));
                    if(CollUtil.isNotEmpty(collect) ){
                        if(CollUtil.isNotEmpty(collect.get(1))){
                            x.setUnReadList(collect.get(1));
                            x.setUnReadNum(collect.get(1).size());
                        }else{
                            x.setUnReadNum(0);
                        }
                        if(CollUtil.isNotEmpty(collect.get(2))){
                            x.setReadList(collect.get(2));
                            x.setReadNum(collect.get(2).size());
                        }else{
                            x.setReadNum(0);
                        }
                    }
                }
            });
        }
        return mappedResults;
    }

    private Criteria getCondition(Doc doc) {
        Criteria criteria2 = new Criteria();
        String userId = doc.getUserId();
        doc.setUserId(null);
        doc.setDocumentType(doc.getDocumentType() == null ? 4 : doc.getDocumentType());
        Criteria criteria = MongoKit.buildCriteria(doc, doc.getPager());
        Criteria criteria3 = Criteria.where("leaderId").is(userId);
        Criteria criteria4 = new Criteria();
        Criteria criteria1 = Criteria.where("leaderList").elemMatch(criteria3);
        boolean flag = false;
        if (ArrayUtil.length(doc.getSearchTimeZone()) == 2) {
            criteria4 = Criteria.where("receiptTime").gte(doc.getSearchTimeZone()[0]).lte(doc.getSearchTimeZone()[1]);
            flag = true;
        }
        Criteria criteriaType = where("docNumberType").is(doc.getDocNumberType());
        //分审核状态查询
        judgeDocumentType(doc.getDocumentType(), doc, userId, criteria, criteria1, criteria2, criteria4, criteriaType, flag);
        return criteria;
    }

    private void judgeDocumentType(
            int documentType, Doc doc, String userId, Criteria criteria, Criteria criteria1,
            Criteria criteria2, Criteria criteria4, Criteria criteriaType, boolean flag) {
        switch (documentType) {
            //待我审核
            case 1:
                handleData1(doc, userId, criteria, criteria1, criteria2, criteria4, criteriaType, flag);
                break;
            //完成
            case 2:
                handleData2(doc, criteria, criteria1, criteria2, criteria4, criteriaType, flag);
                break;
            //待他人审核
            case 3:
                handleData3(doc, userId, criteria, criteria1, criteria2, criteria4, criteriaType, flag);
                break;
            //查询出所有有我参与的记录
            default:
                handleData(doc, criteria, criteria1, criteria4, criteriaType, flag);
                break;
        }
    }

    private void setCriteria(Criteria criteria, Criteria oneCriteria,
                             Criteria twoCriteria, Criteria threeCriteria, boolean flag) {
        if (flag) {
            criteria.andOperator(oneCriteria, twoCriteria, threeCriteria);
        } else {
            criteria.andOperator(oneCriteria, threeCriteria);
        }
    }

    private void handleData1(Doc doc, String userId, Criteria criteria, Criteria criteria1,
                             Criteria criteria2, Criteria criteria4, Criteria criteriaType, boolean flag) {
        doc.setDocumentType(null);
        criteria2 = Criteria.where("leaderId").is(userId).andOperator(Criteria.where("documentType").is(1), criteria1);
        setCriteria(criteria, criteria2, criteria4, criteriaType, flag);
    }

    private void handleData2(Doc doc, Criteria criteria, Criteria criteria1, Criteria criteria2,
                             Criteria criteria4, Criteria criteriaType, boolean flag) {
        criteria2 = Criteria.where("documentType").is(doc.getDocumentType()).andOperator(criteria1);
        setCriteria(criteria, criteria2, criteria4, criteriaType, flag);
    }

    private void handleData3(Doc doc, String userId, Criteria criteria, Criteria criteria1,
                             Criteria criteria2, Criteria criteria4, Criteria criteriaType, boolean flag) {
        doc.setDocumentType(null);
        criteria2 = Criteria.where("leaderId").ne(userId).andOperator(Criteria.where("documentType").is(1), criteria1);
        setCriteria(criteria, criteria2, criteria4, criteriaType, flag);
    }

    private void handleData(Doc doc, Criteria criteria, Criteria criteria1,
                            Criteria criteria4, Criteria criteriaType, boolean flag) {
        doc.setDocumentType(null);
        setCriteria(criteria, criteria1, criteria4, criteriaType, flag);
    }

    public long findDocCountByCondition(Doc doc) {
        Criteria criteria = getCondition(doc);
        return mot.count(query(criteria), Doc.class);
    }

    /**
     * 审核完成调用的方法
     */
    public void saveUpdateDocCompletion(Doc doc) {
        List<DocLeader> list = doc.getLeaderList();
        doc.setUpdateTime(DateUtil.now());
        list.get(list.size() - 1).setUpdateTime(DateUtil.now());
        doc.setLeaderList(list);
        //脏数据不入库（App端）
        doc.setSendObjects(null);
        doc.setDocManagement(null);
        doc.setReadNum(null);
        doc.setUnReadNum(null);
        mot.save(doc, "doc");
        //推送给创建用户
        String[] userId = new String[]{doc.getUserId()};
        String msg = "您有一条公文通知已通过批阅，请注意查收！";
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(userId, "党建-公文通知", msg, Extras.XW_DJ_DOC_TYPE,doc.getId());
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }

    /**
     * 审批完成后，添加发送对象
     */
    public Doc saveDocManagement(DocDepartment docDepartment, String docId) {
        List<SendObject> sendObjects = docDepartment.getSendObjects();
        Criteria criteria1 = Criteria.where("_id").is(docId);
        Update update = new Update();
        Query query = new Query(criteria1);
        //已经发送给教师,记录未读已读人数
        update.set("flag", true);
        update.set("unReadNum", sendObjects.size());
        update.set("readNum", 0);
        Doc doc = mot.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Doc.class);
        String[] sendObject = new String[sendObjects.size()];
        insertManagement(sendObjects, docId);
        for (int i = 0; i < sendObjects.size(); i++) {
            sendObject[i] = sendObjects.get(i).getTeacherId();
        }
        String msg = "您有一条新的公文通知，请及时查收！";
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(sendObject, "党建-公文通知", msg, Extras.XW_DJ_DOC_TYPE,doc.getId());
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        return doc;
    }

    private void insertManagement(List<SendObject> sendObjects, String docId) {
        List<DocManagement> docManagements = new ArrayList<>();
        //添加发送对象
        sendObjects.forEach(f -> {
            DocManagement docManagement = new DocManagement();
            docManagement.setDocObjectId(f.getTeacherId());
            docManagement.setDocId(docId);
            docManagement.setType(1);
            docManagement.setId(sequenceId.nextId());
            docManagement.setDocObjectName(f.getName());
            docManagement.setDepartmentParentId(f.getParentId());
            docManagement.setCreateTime(DateUtil.now());
            docManagement.setSchoolId(f.getSchoolId());
            docManagement.setImgUrl(StrUtil.isNotBlank(f.getImgUrl()) ? f.getImgUrl() : "/headProfile/man.png");
            docManagement.setDepartmentType(f.getType() == 2 ? 1 : 0);
            docManagement.setFlag(false);
            docManagements.add(docManagement);
        });
        removeDuplicate(docManagements);
        mot.insertAll(docManagements);
    }

    /**
     * 去除重复
     *
     * @param list 待处理的list
     * @return 去重后的数据
     */
    private static List removeDuplicate(List<DocManagement> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if(Objects.equals(list.get(j).getDocObjectId(),list.get(i).getDocObjectId())){
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 转他人审核调用的方法
     */
    public void saveUpdateDocLeaterCompletion(Doc doc) {
        doc.setUpdateTime(DateUtil.now());
        List<DocLeader> list = doc.getLeaderList();
        //修改审核人的批阅时间
        list.get(list.size() - 1).setUpdateTime(DateUtil.now());
        doc.setLeaderList(list);
        //脏数据不入库（App端）
        doc.setDocManagement(null);
        doc.setReadNum(null);
        doc.setSendObjects(null);
        doc.setUnReadNum(null);
        mot.save(doc, "doc");

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
        update.push("leaderList", leader);
        mot.updateFirst(query, update, "doc");

        String[] leaderIdList = new String[]{doc.getLeaderId()};
        String msg = "您有一条新的公文通知，请及时审批！";
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(leaderIdList, "党建-公文通知", msg, Extras.XW_DJ_DOC_TYPE,doc.getId());
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }
}
