package com.yice.edu.cn.oa.service.myApprove;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.oa.service.schoolProcess.OAPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MyApproveService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public List<ProcessBusinessData> findWaitApproveData(String id,  ProcessBusinessData processBusinessData) {
        List<AggregationOperation> operations = new ArrayList<>();
       // Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        Criteria criteria = MongoKit.buildCriteria(processBusinessData,processBusinessData.getPager());
        criteria.and("approver").elemMatch(where("id").is(id).and("status").is(Constant.OA.WAIT_TO_APPROVE));
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }

        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(processBusinessData.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(ProcessBusinessData.class,operations),ProcessBusinessData.class).getMappedResults();
    }

    public long findWaitApproveCount(String id, ProcessBusinessData processBusinessData) {
        Criteria criteria = MongoKit.buildCriteria(processBusinessData,processBusinessData.getPager());
        criteria.and("approver").elemMatch(where("id").is(id).and("status").is(Constant.OA.WAIT_TO_APPROVE));
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        return mot.count(query(criteria), ProcessBusinessData.class);
    }
    public List<ProcessBusinessData> findHasApproveData(String id, ProcessBusinessData processBusinessData) {
        List<AggregationOperation> operations = new ArrayList<>();
        Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        Criteria criteria = new Criteria().alike(example);
        criteria.and("approver").elemMatch(new Criteria().orOperator(where("id").is(id).and("status").is(Constant.OA.SUCCESS_COMPLETE),where("id").is(id).and("status").is(Constant.OA.FAIL_COMPLETE)));
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(processBusinessData.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(ProcessBusinessData.class,operations),ProcessBusinessData.class).getMappedResults();
    }

    public long findHasApproveCount(String id, ProcessBusinessData processBusinessData) {
        Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        Criteria criteria = new Criteria().alike(example);
        criteria.and("approver").elemMatch(new Criteria().orOperator(where("id").is(id).and("status").is(Constant.OA.SUCCESS_COMPLETE),where("id").is(id).and("status").is(Constant.OA.FAIL_COMPLETE)));
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        return mot.count(query(criteria), ProcessBusinessData.class);
    }
    public ResponseJson completeApprove(OaPeople oaPeople, String processBusinessId) {
        if(oaPeople.getStatus()==null||(oaPeople.getStatus()<0||oaPeople.getStatus()>2)){
            return new ResponseJson(false,"status wrong");
        }
        if(oaPeople.getStatus()==Constant.OA.FAIL_COMPLETE&& StrUtil.isEmpty(oaPeople.getReason())){
            return new ResponseJson(false, "when status is Fail,reason is required!");
        }
        if(oaPeople.getStatus()==0){
            return new ResponseJson(false, "status should be 1 or 2");
        }
        ProcessBusinessData processBusinessData = mot.findById(processBusinessId, ProcessBusinessData.class);
        if(Objects.isNull(processBusinessData)){
            return new ResponseJson(false, "当前流程已被申请人删除,不能进行审批");
        }
        List<OaPeople> approver = processBusinessData.getApprover();
        int index=-1;
        for (int i = 0; i < approver.size(); i++) {
            OaPeople people = approver.get(i);
            if(people.getId().equals(oaPeople.getId())&&people.getStatus()==Constant.OA.WAIT_TO_APPROVE){
                index=i;
                break;
            }
        }
        if(index==-1){
            return new ResponseJson(false,"不存在该审批人");
        }
        oaPeople.setName(approver.get(index).getName());
        oaPeople.setImgUrl(approver.get(index).getImgUrl());
        oaPeople.setApproveTime(DateUtil.now());
        if(processBusinessData.getSequential()){//串行
            if(oaPeople.getStatus()== Constant.OA.SUCCESS_COMPLETE){//审批通过
                if(index==approver.size()-1){//表示都审批完了
                    mot.updateFirst(query(where("id").is(processBusinessId)), Update.update("status", Constant.OA.SUCCESS_COMPLETE).set("approver." + index, oaPeople).set("endTime",DateUtil.now()), ProcessBusinessData.class);
                    processBusinessData.setStatus(Constant.OA.SUCCESS_COMPLETE);//推送需要知道
                    List<OaPeople> copyFor = processBusinessData.getCopyFor();
                    //发推送给申请人
                    OAPush.push(processBusinessData,
                            new String[]{processBusinessData.getInitiatorId()},
                            "OA申请通知",
                            StrUtil.format("您的{}{}已经通过",processBusinessData.getType(),cancleFun.apply(processBusinessData)),
                            Extras.OA_APPROVE_RESULT,
                            stringRedisTemplate
                    );
                    //保存抄送人并推送抄送人
                    this.sendCopy(copyFor,processBusinessData);
                }else{
                    mot.updateFirst(query(where("id").is(processBusinessId)), Update.update("approver." + index, oaPeople).set("approver."+(index+1)+".status",Constant.OA.WAIT_TO_APPROVE), ProcessBusinessData.class);
                    //发推送给下一个审批人
                    OAPush.push(processBusinessData,
                            new String[]{approver.get(index+1).getId()},
                            "OA审批通知",
                            "您有一个新的OA审批任务",
                            Extras.OA_APPROVE_TASK,
                            stringRedisTemplate
                            );
                }
            }else if(oaPeople.getStatus()==Constant.OA.FAIL_COMPLETE){//审批不同意
                mot.updateFirst(query(where("id").is(processBusinessId)), Update.update("status", Constant.OA.FAIL_COMPLETE).set("approver." + index, oaPeople).set("endTime",DateUtil.now()), ProcessBusinessData.class);
                processBusinessData.setStatus(Constant.OA.FAIL_COMPLETE);//推送需要知道
                //发推送给申请人
                OAPush.push(processBusinessData,
                        new String[]{processBusinessData.getInitiatorId()},
                        "OA申请通知",
                        StrUtil.format("您的{}{}没有通过", processBusinessData.getType(),cancleFun.apply(processBusinessData)),
                        Extras.OA_APPROVE_RESULT,
                        stringRedisTemplate
                );
            }else{
                return new ResponseJson(false,"审批结果参数错误");
            }
        }else{//并行
            ProcessBusinessData businessData = mot.findAndModify(query(where("id").is(processBusinessId)), Update.update("approver." + index, oaPeople), FindAndModifyOptions.options().returnNew(true), ProcessBusinessData.class);
            //验证是否都审批过
            boolean allComplete = businessData.getApprover().stream().allMatch(p -> p.getStatus() != Constant.OA.WAIT_TO_APPROVE);
            if(allComplete){//表示全部审批完了
                long count = businessData.getApprover().stream().filter(p -> p.getStatus() == Constant.OA.SUCCESS_COMPLETE).count();
                boolean approvePass = count >= businessData.getPassCount();
                mot.updateFirst(query(where("id").is(processBusinessId)), Update.update("status", approvePass ? Constant.OA.SUCCESS_COMPLETE: Constant.OA.FAIL_COMPLETE).set("endTime",DateUtil.now()), ProcessBusinessData.class);
                processBusinessData.setStatus(Constant.OA.SUCCESS_COMPLETE);//推送需要知道
                //发推送给申请人
                OAPush.push(processBusinessData,
                        new String[]{processBusinessData.getInitiatorId()},
                        "OA申请通知",
                        StrUtil.format("您的{}{} {}",processBusinessData.getType(),cancleFun.apply(processBusinessData),approvePass?"已经通过":"没有通过"),
                        Extras.OA_APPROVE_RESULT,
                        stringRedisTemplate
                );
                if(approvePass){
                    //保存抄送人并推送抄送人
                    this.sendCopy(processBusinessData.getCopyFor(),businessData);
                }
            }
        }
        return new ResponseJson();
    }
    private void sendCopy(List<OaPeople> copyFor,ProcessBusinessData processBusinessData){
        if(CollUtil.isEmpty(copyFor)){ return; }
        List<ProcessCopy> processCopies = new ArrayList<>();
        Map<String,String[]> data = Maps.newHashMap();
        copyFor.forEach(p->{
            ProcessCopy processCopy = new ProcessCopy();
            processCopy.setId(sequenceId.nextId());
            processCopy.setCreateTime(processBusinessData.getCreateTime());
            processCopy.setInitiator(processBusinessData.getInitiator());
            processCopy.setLooked(false);
            processCopy.setProcessBusinessDataId(processBusinessData.getId());
            processCopy.setTeacherId(p.getId());
            processCopy.setType(processBusinessData.getType());
            processCopy.setSchoolId(processBusinessData.getSchoolId());
            processCopies.add(processCopy);
            data.put(processCopy.getId(),new String[]{p.getId()});
        });
        mot.insertAll(processCopies);
        //添加完成推送抄送人
        data.forEach((k,v)-> OAPush.push(processBusinessData,v,
                "OA抄送通知",
                "您有一条OA申请的抄送",
                Extras.OA_RECEIVE_COPY,
                stringRedisTemplate,
                k
        ));
    }

    private Function<ProcessBusinessData,String> cancleFun = (ps)->{
        String cancle="";
        if(StrUtil.isNotEmpty(ps.getCancelReason()) ){
            cancle = "-[撤销]";
        }
        return cancle;
    };
}
