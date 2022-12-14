package com.yice.edu.cn.jw.service.pushDetail;

import cn.hutool.core.util.ObjectUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PushDetailService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PushDetail findPushDetailById(String id) {
        return mot.findById(id,PushDetail.class);
    }
    public void savePushDetail(PushDetail pushDetail) {
        pushDetail.setId(sequenceId.nextId());
        mot.insert(pushDetail);
    }
    public List<PushDetail> findPushDetailListByCondition(PushDetail pushDetail) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PushDetail.class)).find(MongoKit.buildMatchDocument(pushDetail));
        Pager pager = pushDetail.getPager();
        List<PushDetail> list = getPushDetails (query, pager);
        return list;
    }
    public long findPushDetailCountByCondition(PushDetail pushDetail) {
        return mot.getCollection(mot.getCollectionName(PushDetail.class)).countDocuments(MongoKit.buildMatchDocument(pushDetail));
    }
    public PushDetail findOnePushDetailByCondition(PushDetail pushDetail) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PushDetail.class)).find(MongoKit.buildMatchDocument(pushDetail));
       MongoKit.appendProjection(query,pushDetail.getPager());
       return mot.getConverter().read(PushDetail.class,query.first());
    }

    public void updatePushDetail(PushDetail pushDetail) {
        mot.updateFirst(query(where("id").is(pushDetail.getId())), MongoKit.update(pushDetail),PushDetail.class);
    }
    public void deletePushDetail(String id) {
        mot.remove(query(where("id").is(id)),PushDetail.class);
    }
    public void deletePushDetailByCondition(PushDetail pushDetail) {
        mot.getCollection(mot.getCollectionName(PushDetail.class)).deleteMany(MongoKit.buildMatchDocument(pushDetail));
    }
    public void batchSavePushDetail(List<PushDetail> pushDetails){
        pushDetails.forEach(pushDetail -> pushDetail.setId(sequenceId.nextId()));
        mot.insertAll(pushDetails);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePushDetail4Push(PushDetail pushDetail) {
        //?????????????????????
        pushDetail.setReceiverList(Optional.ofNullable(pushDetail.getReceiverIds()).map(ids->ids.stream().distinct().map(id->new Receiver(id,false)).collect(Collectors.toList())).orElse(pushDetail.getReceiverList()));
        mot.insert(pushDetail);
    }

    /**
     * ??????????????????????????????????????????????????????
     * @param receiver
     */
    public void updatePushDetail2Read(Receiver receiver) {
        mot.updateFirst(query(where("id").is(receiver.getPushId()).and("receiverList.receiverId").is(receiver.getReceiverId())), Update.update("receiverList.$.isRead",true),PushDetail.class);
    }

    /**
     * ???????????? ???????????? ????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findPushDetailListByCondition4Receiver(Receiver receiver) {
        PushDetail pushDetail = new PushDetail();
        Document match=MongoKit.buildMatchDocument(pushDetail);
        if(Optional.ofNullable(receiver).isPresent()){
            //Document?????????????????????bean?????? ?????????????????? ????????????bean?????????document???????????????
            //match.append("receiverList",new Document("$in", Arrays.asList(new Document("receiverId",receiver.getReceiverId()).append("isRead",false))));
            //???????????? receiver ????????????
            Document a = new Document();
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            if(Optional.ofNullable(receiver.getIsRead()).isPresent())
                a.append("isRead",receiver.getIsRead());
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return new ArrayList<>();
        return getPushDetails (receiver, match);
    }
    /**
     * ?????? ????????? ????????? ??????list????????????
     * ????????????????????????????????? ??? ????????????
     * @param receiver
     * @return
     */
    public long findPushDetailCountByCondition4Receiver(Receiver receiver) {
        PushDetail pushDetail = new PushDetail();
        Document match=MongoKit.buildMatchDocument(pushDetail);
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            if(Optional.ofNullable(receiver.getIsRead()).isPresent())
                a.append("isRead",receiver.getIsRead());
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return 0;
        return mot.getCollection(mot.getCollectionName(PushDetail.class)).countDocuments(match);
    }

    /**
     * ????????? ????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findPushDetailList4Work(Receiver receiver) {
        //?????? ?????????????????????
        Document match = new Document("type", new Document("$ne",Extras.XW_STUDENT_IN_OUT_SCHOOL));
        if(Optional.ofNullable(receiver.getType()).isPresent()){
            match = new Document("type",receiver.getType());
        }
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            else
                return new ArrayList<>();
            if(Optional.ofNullable(receiver.getIsRead()).isPresent())
                a.append("isRead",receiver.getIsRead());
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return new ArrayList<>();
        return getPushDetails (receiver, match);
    }
    /**
     * ????????? ??????????????????????????????
     * @param receiver
     * @return
     */
    public long findPushDetailCount4Work(Receiver receiver) {
        //?????? ?????????????????????
        Document match = new Document("type", new Document("$ne",Extras.XW_STUDENT_IN_OUT_SCHOOL));
        if(Optional.ofNullable(receiver.getType()).isPresent()){
            match = new Document("type",receiver.getType());
        }
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            else
                return 0;
            a.append("isRead",Optional.ofNullable(receiver.getIsRead()).orElse(false));
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return 0;
        return mot.getCollection(mot.getCollectionName(PushDetail.class)).countDocuments(match);
    }
    /**
     * ????????? ???????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findPushDetailList4InOutSchool(Receiver receiver) {
        //?????? ?????????????????????
        Document match = new Document("type", Extras.XW_STUDENT_IN_OUT_SCHOOL);
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            if(Optional.ofNullable(receiver.getIsRead()).isPresent())
                a.append("isRead",receiver.getIsRead());
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            else
                return new ArrayList<>();
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return new ArrayList<>();
        return getPushDetails (receiver, match);
    }

    /**
     * ???????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findPushDetailAllList(Receiver receiver){
        Document match = new Document();
        getPushDetailInfo (receiver, match);
        return getPushDetails (receiver, match);
    }

    /**
     * ????????????????????????
     * @param receiver
     * @return
     */
    public long findPushDetailCount(Receiver receiver){
        Document match = new Document();
        getAllPushInfo (receiver, match);
        return mot.getCollection (mot.getCollectionName (PushDetail.class)).countDocuments (match);

    }

    private void getAllPushInfo(Receiver receiver, Document match) {
        if (Optional.ofNullable (receiver).isPresent ()) {
            Document filter = new Document ();
            if (Optional.ofNullable (receiver.getIsRead ()).isPresent ()) {
                filter.append ("isRead", receiver.getIsRead ());
            }
            if (Optional.ofNullable (receiver.getReceiverId ()).isPresent ()) {
                filter.append ("receiverId", receiver.getReceiverId ());
            }
            match.append ("receiverList", new Document ("$elemMatch", filter));
        }
    }


    private void getPushDetailInfo(Receiver receiver, Document match) {
        getAllPushInfo (receiver, match);
    }


    private List<PushDetail> getPushDetails(Receiver receiver, Document match) {
        FindIterable<Document> query = mot.getCollection (mot.getCollectionName (PushDetail.class)).find (match);
        Pager pager = receiver.getPager () == null ? new Pager ().setPaging (false) : receiver.getPager ();
        pager.setSortOrder (Pager.DESC).setSortField ("sendDate").setIncludes ("type", "content", "sendDate","referenceId","receiverList");
        List<PushDetail> list = getPushDetails (query, pager);
        return list;
    }




    /**
     * ????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findPushActiveList(Receiver receiver){
        Document match=new Document ("type",receiver.getType ());
        getPushDetailInfo (receiver, match);
        FindIterable<Document> query = mot.getCollection (mot.getCollectionName (PushDetail.class)).find (match);
        Pager pager=receiver.getPager ()==null?new Pager ().setPaging (false):receiver.getPager ();
        pager.setSortOrder (Pager.DESC).setSortOrder ("sendDate").setIncludes ("type","content","sendDate");
        List<PushDetail> list = getPushDetails (query, pager);
        return list;
    }

    private List<PushDetail> getPushDetails(FindIterable<Document> query, Pager pager) {
        MongoKit.appendSort (query, pager);
        MongoKit.appendPage (query, pager);
        MongoKit.appendProjection (query, pager);
        List<PushDetail> list = new ArrayList<> ();
        query.forEach ((Block<Document>) document -> list.add (mot.getConverter ().read (PushDetail.class, document)));
        return list;
    }

    /**
     * ????????????????????????
     * @param receiver
     * @return
     */
    public long findPushActiveCount(Receiver receiver){
        Document match=new Document ("type",receiver.getType ());
        if(Optional.ofNullable (receiver).isPresent ()){
            Document filter = new Document();
            filter.append ("isRead",Optional.ofNullable (receiver.getIsRead ()).orElse (false));
            if(Optional.ofNullable (receiver.getReceiverId ()).isPresent ()){
                filter.append ("receiverId",receiver.getReceiverId ());
            }
            match.append ("receiverList",new Document("$elemMatch",filter));
        }
       return mot.getCollection (mot.getCollectionName (PushDetail.class)).countDocuments (match);
    }


    /**
     * ????????? ?????????????????????????????????
     * @param receiver
     * @return
     */
    public long findPushDetailCount4InOutSchool(Receiver receiver) {
        //?????? ?????????????????????
        Document match = new Document("type",Extras.XW_STUDENT_IN_OUT_SCHOOL);
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            a.append("isRead",Optional.ofNullable(receiver.getIsRead()).orElse(false));
            if(Optional.ofNullable(receiver.getReceiverId()).isPresent())
                a.append("receiverId",receiver.getReceiverId());
            else
                return 0;
            match.append("receiverList",new Document("$elemMatch",a));
        }else
            return 0;
        return mot.getCollection(mot.getCollectionName(PushDetail.class)).countDocuments(match);
    }


    /**
     * ????????? ????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findDyPushDetailListByConditionReceiver(Receiver receiver) {
        Document match = new Document("type", Extras.DY_RATE_BMP);
        if(Optional.ofNullable(receiver.getType()).isPresent()){
            match = new Document("type",receiver.getType());
        }
        if(Optional.ofNullable(receiver).isPresent()){
            //???????????? receiver ????????????
            Document a = new Document();
            if(receiver.getReceiverId()!=null){
                a.append("receiverId",receiver.getReceiverId());
            }else{
                return new ArrayList<>();
            }
            match.append("receiverList",new Document("$elemMatch",a));
            if(receiver.getIsRead()!=null){
                a.append("isRead",receiver.getIsRead());
            }
        }else{
            return new ArrayList<>();
        }
        List<PushDetail> pushDetails = getPushDetails(receiver, match);
        pushDetails = distinctRecever(pushDetails,receiver);
        return pushDetails;
    }


    /**
     * ????????? ??????????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findSchoolActivePushDetailListByConditionReceiver(Receiver receiver) {
        Document match = new Document("type", Extras.DM_BMP_ACTIVITY);
        List<PushDetail> pushDetails = findActivePushDetail(match, receiver);
        return pushDetails;
    }


    private List<PushDetail> distinctRecever(List<PushDetail> pushDetails, Receiver receiver) {
        for (PushDetail p : pushDetails) {
            List<Receiver> receivers = p.getReceiverList().stream().filter(r -> ObjectUtil.equal(receiver.getReceiverId(), r.getReceiverId())).collect(Collectors.toList());
            p.setReceiverList(receivers);
        }
        return pushDetails;
    }


    /**
     * ????????? ??????????????????????????????
     * @param receiver
     * @return
     */
    public List<PushDetail> findSchoolActivePushDetailListByConditionTeacherReceiver(Receiver receiver) {
        Document match = new Document("type", Extras.DM_TAP_ACTIVITY);
        List<PushDetail> pushDetails = findActivePushDetail(match,receiver);
        return pushDetails;
    }

    private List<PushDetail> findActivePushDetail(Document match, Receiver receiver) {
        //???????????? receiver ????????????
        Document a = new Document();
        a.append("receiverId",receiver.getReceiverId());
        match.append("receiverList",new Document("$elemMatch",a));
        if(receiver.getIsRead()!=null){
            a.append("isRead",receiver.getIsRead());
        }
        List<PushDetail> pushDetails = getPushDetails(receiver, match);
        pushDetails = distinctRecever(pushDetails,receiver);
        return pushDetails;
    }


}
