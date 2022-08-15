package com.yice.edu.cn.oa.service.processBusinessData;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.process.ProcessForm;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.LeaveStatis;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.LeaveTotalStatis;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessUrge;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.oa.feignClient.TeacherFeign;
import com.yice.edu.cn.oa.service.schoolProcess.OAPush;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProcessBusinessDataService {
    private static final Logger log = LoggerFactory.getLogger(ProcessBusinessDataService.class);

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ProcessBusinessData findProcessBusinessDataById(String id) {
        return mot.findById(id,ProcessBusinessData.class);
    }
    public void saveProcessBusinessData(ProcessBusinessData processBusinessData) {
        processBusinessData.setCreateTime(DateUtil.now());
        processBusinessData.setId(sequenceId.nextId());
        mot.insert(processBusinessData);
    }
    public List<ProcessBusinessData> findProcessBusinessDataListByCondition(ProcessBusinessData processBusinessData) {
        Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        final Criteria criteria = new Criteria().alike(example);
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        //关联processLib表 获取流程配置信息
        //operations.add(Aggregation.lookup("processLib","processLibId","_id","processLib"));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(processBusinessData.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(ProcessBusinessData.class,operations),ProcessBusinessData.class).getMappedResults();

        }
        public ProcessBusinessData findOneProcessBusinessDataByCondition(ProcessBusinessData processBusinessData) {
            Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
            List<AggregationOperation> operations = new ArrayList<>();
            operations.add(Aggregation.match(new Criteria().alike(example)));
            Pager pager = processBusinessData.getPager();
            if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(ProcessBusinessData.class,operations),ProcessBusinessData.class).getUniqueMappedResult();
    }
    public long findProcessBusinessDataCountByCondition(ProcessBusinessData processBusinessData) {
        Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        Criteria criteria = new Criteria().alike(example);
        final String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        Query query = query(criteria);
        return mot.count(query, ProcessBusinessData.class);
    }
    public void updateProcessBusinessData(ProcessBusinessData processBusinessData) {
        mot.updateFirst(query(where("id").is(processBusinessData.getId())), MongoKit.update(processBusinessData),ProcessBusinessData.class);
    }
    public void deleteProcessBusinessData(String id) {
        mot.remove(query(where("id").is(id)),ProcessBusinessData.class);
    }
    public void deleteProcessBusinessDataByCondition(ProcessBusinessData processBusinessData) {
        Example<ProcessBusinessData> example = Example.of(processBusinessData, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, ProcessBusinessData.class);
    }
    public void batchSaveProcessBusinessData(List<ProcessBusinessData> processBusinessDatas){
        processBusinessDatas.forEach(processBusinessData -> processBusinessData.setId(sequenceId.nextId()));
        mot.insertAll(processBusinessDatas);
    }
    //销假
    public ResponseJson clearLeave(ProcessBusinessData ps) {
        ProcessBusinessData processBusinessData = this.findProcessBusinessDataById(ps.getId());
        boolean isCancel = StrUtil.isEmpty(processBusinessData.getCancelReason());
        if(processBusinessData.getClearLeaveTime()!=null){
            return new ResponseJson(false, "该流程已销假,无需重复操作");
        }
        if(processBusinessData.getClearLeave()==null||!processBusinessData.getClearLeave()){
            return new ResponseJson(false, "该流程无需销假");
        }
        if(isCancel){
            // 没有撤销
            if(processBusinessData.getStatus()!=Constant.OA.SUCCESS_COMPLETE){
                return new ResponseJson(false, "该流程没有审批通过,不能销假");
            }
        }else{
            // 已撤销
            if(processBusinessData.getStatus() != Constant.OA.FAIL_COMPLETE){
                return new ResponseJson(false, "该撤销流程已审批通过,不能销假");
            }
        }
        String format = "";
        for (ProcessForm form : processBusinessData.getProcessForms()) {
            if(form.getName().equals(Constant.OA.BEGIN_TIME)){
                format=form.getTimeFormat();
                break;
            }
        }
        LocalDateTime beginTime = LocalDateTime.parse(processBusinessData.getFormData().get(Constant.OA.BEGIN_TIME).toString(), DateTimeFormatter.ofPattern(format));
       // LocalDateTime endTime = LocalDateTime.parse(processBusinessData.getFormData().get(Constant.OA.END_TIME).toString(), DateTimeFormatter.ofPattern(format));
        LocalDateTime now = LocalDateTime.now();
        if(now.compareTo(beginTime)<0){
            return new ResponseJson(false, "还未进入请假时间,不能销假");
        }
        /*if(now.compareTo(endTime)>0){
            return new ResponseJson(false,"请假时间已过,无法销假");
        }*/
        mot.updateFirst(query(where("id").is(ps.getId())), Update.update("clearLeaveTime", ps.getClearLeaveTime()).set("status",Constant.OA.SUCCESS_COMPLETE), ProcessBusinessData.class);
        List<String> approver = processBusinessData.getApprover().stream().map(v -> v.getId()).collect(Collectors.toList());
        Optional.ofNullable(processBusinessData.getCopyFor()).ifPresent(v -> v.forEach(f ->approver.add(f.getId())));
        processBusinessData.setClearLeaveTime(ps.getClearLeaveTime());
        // 已抄送通道发送需要添加抄送记录
        List<ProcessCopy> processCopyList = approver.stream().map(a -> {
            ProcessCopy processCopy = new ProcessCopy();
            processCopy.setId(sequenceId.nextId());
            processCopy.setCreateTime(processBusinessData.getCreateTime());
            processCopy.setInitiator(processBusinessData.getInitiator());
            processCopy.setLooked(false);
            processCopy.setProcessBusinessDataId(processBusinessData.getId());
            processCopy.setTeacherId(a);
            processCopy.setType(processBusinessData.getType());
            processCopy.setSchoolId(processBusinessData.getSchoolId());
            return processCopy;
        }).collect(Collectors.toList());
        mot.insertAll(processCopyList);
        processCopyList.forEach(copy -> OAPush.push(processBusinessData,
                new String[]{copy.getTeacherId()},
                "确认到校通知",
                processBusinessData.getInitiator() + "提交的确认到校通知抄送给你,请知晓",
                Extras.OA_RECEIVE_COPY,
                stringRedisTemplate,
                copy.getId()
        ));
        return new ResponseJson();
    }

    public ResponseJson calculateLeaveStatis(ProcessBusinessData processBusinessData) {
        //一.总数统计
        //1.请假总次数
        LeaveTotalStatis leaveTotalStatis = this.queryLeaveTotalStatis(processBusinessData);
        //2.获取请假的三个group(用户id，部门id，请假类型)的列表
        List<LeaveStatis> leaveStatises=this.queryLeaveStatises(processBusinessData);
        long count = this.queryLeaveStatisCount(processBusinessData);
        return new ResponseJson(leaveTotalStatis,leaveStatises,count);
    }

    private long queryLeaveStatisCount(ProcessBusinessData processBusinessData) {
        Document matchDocument = this.buildStatisMatchDocument(processBusinessData);
        Document groupDocument = new Document("$group", new Document("_id", new Document("initiatorId", "$initiatorId").append("department", "$formData.department").append("leaveType", "$formData.leaveType")));
        Document groupAllDocument = new Document("$group", new Document("_id", null).append("count", new Document("$sum", 1)));
        AggregateIterable<Document> documents = mot.getCollection("processBusinessData").aggregate(Arrays.asList(matchDocument,groupDocument,groupAllDocument));
        return documents.first()!=null?documents.first().getInteger("count"):0;
    }

    private List<LeaveStatis> queryLeaveStatises(ProcessBusinessData processBusinessData) {
        List<Bson> steps = new ArrayList<>();
        Document matchDocument = this.buildStatisMatchDocument(processBusinessData);
        Document groupDocument = new Document("$group", new Document("_id", new Document("initiatorId", "$initiatorId").append("department", "$formData.department").append("leaveType", "$formData.leaveType"))
                .append("count", new Document("$sum", 1))
                .append("duration", new Document("$sum", "$formData.duration"))
                .append("initiator", new Document("$first", "$formData.teacherName"))
                .append("departmentName", new Document("$first", "$formData.departmentName"))
                .append("leaveTypeName", new Document("$first", "$formData.leaveTypeName"))
        );
        steps.add(matchDocument);
        steps.add(groupDocument);
        if(processBusinessData.getPager().getSortField()!=null){
            Document sortDocument = new Document("$sort", new Document(processBusinessData.getPager().getSortField(), processBusinessData.getPager().getSortOrder().equals(Pager.ASC) ? 1 : -1));
            steps.add(sortDocument);
        }
        steps.add(new Document("$skip", processBusinessData.getPager().getBeginRow()));
        steps.add(new Document("$limit",processBusinessData.getPager().getPageSize()));
        AggregateIterable<Document> documents = mot.getCollection("processBusinessData").aggregate(steps);
        List<LeaveStatis> result = new ArrayList<>();
        MongoCursor<Document> iterator = documents.iterator();
        while(iterator.hasNext()){
            Document next = iterator.next();
            LeaveStatis ls = new LeaveStatis(next.getInteger("count"), next.getDouble("duration"), next.getString("initiator"), next.getString("departmentName"), next.getString("leaveTypeName"));
            result.add(ls);
        }
        return result;
    }

    /**
     * 查找请假最多类别
     * db.processBusinessData.aggregate([
     *    {
     *        $match:{
     *              "schoolId":"1735873773630742528",
     *              "processLibId":"20181027163655001",
     *              "status":1,
     *              //$and:[{"formData.beginTime":{$lt:"2018-11-25"}},{"formData.endTime":{$gt:"2018-11-11"}}],
     *              //"formData.leaveType":"0",
     *              //"initiator":"二阿哥"
     *
     *            }
     *
     *        },
     *
     *        {
     *            $group:{
     *                _id :"$formData.leaveType",
     *                count:{$sum:1},
     *                maxType:{$first:"$formData.leaveTypeName"}
     *
     *                }
     *
     *         },
     *         {
     *            $sort:{ count : -1}
     *         },
     *         { $limit : 1 }
     *
     *
     * ])
     * @param processBusinessData
     * @return
     */
    private LeaveTotalStatis queryLeaveTotalStatis(ProcessBusinessData processBusinessData){
        Document matchDocument = buildStatisMatchDocument(processBusinessData);
        Document totalCountDocument = new Document("$group", new Document("_id", null).append("totalCount", new Document("$sum", 1)));
        Document totalPeopleGroup = new Document("$group", new Document("_id", "$initiatorId"));
        Document totalPeopleCount = new Document("$count", "totalPeople");
        Document maxTypeGroup = new Document("$group", new Document("_id", "$formData.leaveType").append("count", new Document("$sum", 1)).append("maxType", new Document("$first", "$formData.leaveTypeName")));
        Document sort = new Document("$sort", new Document("count", -1));
        Document limit = new Document("$limit", 1);
        Document maxDepartmentNameGroup = new Document("$group", new Document("_id", "$formData.department").append("count", new Document("$sum", 1)).append("maxDepartmentName", new Document("$first", "$formData.departmentName")));


        MongoCollection<Document> collection = mot.getCollection("processBusinessData");
        AggregateIterable<Document> totalCountResult = collection.aggregate(Arrays.asList(matchDocument, totalCountDocument));
        AggregateIterable<Document> totalPeopleResult = collection.aggregate(Arrays.asList(matchDocument, totalPeopleGroup,totalPeopleCount));
        AggregateIterable<Document> maxTypeResult = collection.aggregate(Arrays.asList(matchDocument, maxTypeGroup, sort, limit));
        AggregateIterable<Document> maxDepartmentNameResult = collection.aggregate(Arrays.asList(matchDocument, maxDepartmentNameGroup, sort, limit));
        return new LeaveTotalStatis(
                totalCountResult.first()!=null?totalCountResult.first().getInteger("totalCount"):0,
                totalPeopleResult.first()!=null?totalPeopleResult.first().getInteger("totalPeople"):0,
                maxTypeResult.first()!=null?maxTypeResult.first().getString("maxType"):"暂无",
                maxDepartmentNameResult.first()!=null?maxDepartmentNameResult.first().getString("maxDepartmentName"):"暂无");
    }

    private Document buildStatisMatchDocument(ProcessBusinessData processBusinessData){
        Document matchObj=new Document("schoolId",processBusinessData.getSchoolId()).append("processLibId","20181027163655001").append("status",Constant.OA.SUCCESS_COMPLETE);
        if(processBusinessData.getRangeTime()!=null&&processBusinessData.getRangeTime().length==2){
            matchObj.append("$and", Arrays.asList(new Document("formData.beginTime",new Document("$lt",processBusinessData.getRangeTime()[1])),new Document("formData.endTime",new Document("$gt",processBusinessData.getRangeTime()[0]))));
        }
        if(processBusinessData.getFormData()!=null&&processBusinessData.getFormData().get("leaveType")!=null){
            matchObj.append("formData.leaveType",processBusinessData.getFormData().get("leaveType"));
        }
        if(processBusinessData.getInitiatorId()!=null){
            matchObj.append("initiatorId", processBusinessData.getInitiatorId());
        }
        return  new Document("$match",matchObj);
    }

    public ResponseJson sendMessageByProcessDataById(String id) {
        ResponseJson rs = new ResponseJson();
        ProcessBusinessData processBusinessData = mot.findById(id,ProcessBusinessData.class);
        if(processBusinessData.getStatus() == Constant.OA.SUCCESS_COMPLETE){
            return new ResponseJson(false,"该流程已审批结束,不能发起催办");
        }
        //当前需要是讲 每一条申请记录每天不能超过10次 若以后变更为每一种审批类型每天不超过10次 替换 processLibId
       long count =  mot.count(query(where("processBusinessId").is(processBusinessData.getId()).and("urgeDate").is(DateUtil.today())), ProcessUrge.class);
        if(count >= Constant.OA.SEND_SMS_MAX_COUNT){
            return new ResponseJson(false,"催办过于频繁已超过每天10条限制");
        }
        Optional<OaPeople> oaPeople= processBusinessData.getApprover().stream().filter(p -> Objects.nonNull(p.getStatus()) && p.getStatus() == Constant.OA.WAIT_TO_APPROVE).findFirst();
        if(oaPeople.isPresent()){
            Teacher teacher =  teacherFeign.findTeacherById(oaPeople.get().getId());
            Map<String,String> msg = new HashMap<>();
            msg.put("approver",teacher.getName());
            msg.put("initiator",processBusinessData.getInitiator());
            msg.put("type",processBusinessData.getType());
            ProcessUrge pu = new ProcessUrge();
               pu.setId(sequenceId.nextId());
               pu.setProcessBusinessId(processBusinessData.getId());
               pu.setProcessLibId(processBusinessData.getProcessLibId());
               pu.setUrgeDate(DateUtil.today());
               pu.setTeacherId(teacher.getId());
               pu.setSendTeacherName(teacher.getName());
               pu.setSendTeacherTel(teacher.getTel());
               pu.setCreateTime(DateUtil.now());
               pu.setSchoolId(processBusinessData.getSchoolId());
               mot.insert(pu);
            final Sms sms = new Sms(teacher.getTel(),Constant.MCS_SIGN_NAME.YCJD,Constant.MCS_TEMPLATE.SMS_OA_URGE,msg);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));
            log.info("发送人:{},接收人:{},发送手机号:{} ",processBusinessData.getInitiator(),oaPeople.get().getName(),teacher.getTel());
        }else{
            rs = new ResponseJson(false,"未找到需要通知的审批人");
        }
        return rs;
    }

    public ResponseJson cancelFlow(String id, ProcessBusinessData processBusinessData) {
        ProcessBusinessData ps = mot.findById(id,ProcessBusinessData.class);
        if(StrUtil.isEmpty(processBusinessData.getCancelReason())){//普通撤销
            /*ps.getApprover().forEach(v ->{
                if (Objects.isNull(v.getStatus()) || v.getStatus() != 1) {
                    v.setStatus(Constant.OA.CANCEL_COMPLETE);
                }
            });
            ps.setStatus(Constant.OA.CANCEL_COMPLETE);
            ps.setCanceDate(DateUtil.now());
            mot.updateFirst(query(where("_id").is(id)), MongoKit.update(ps),ProcessBusinessData.class);//直接删除
            */
            if(Objects.isNull(ps)){
                return new ResponseJson(false,"当前流程不存在,请刷新重试");
            }
            if(ps.getStatus() == Constant.OA.SUCCESS_COMPLETE){
                return new ResponseJson(false,"当前流程已审批通过,不允许撤销");
            }
            mot.remove(query(where("_id").is(id)),ProcessBusinessData.class);
            return new ResponseJson();
        }
            // 请假流程 撤销重新发起流程
            if(ps.getApprover().stream().allMatch(v ->  v.getStatus() == Constant.OA.SUCCESS_COMPLETE)){
                ps.setCancelApprover(ps.getApprover());
                ps.setCancelReason(processBusinessData.getCancelReason());
                List<OaPeople> ops= ps.getApprover().stream().map(v->{
                    OaPeople op = new OaPeople();
                    BeanUtil.copyProperties(v,op,"status","approveTime");
                    return op;
                }).collect(Collectors.toList());
                ops.get(0).setStatus(Constant.OA.WAIT_TO_APPROVE);
                ps.setApprover(ops);
            }else{
                return new ResponseJson(false,"该流程还未审批通过,不能发起撤销流程");
            }
            ps.setCanceDate(DateUtil.now());
            ps.setStatus(Constant.OA.WAIT_TO_APPROVE);
        mot.updateFirst(query(where("_id").is(id)), MongoKit.update(ps),ProcessBusinessData.class);
        //推送审批人
        OAPush.push(ps,
                new String[]{ ps.getApprover().get(0).getId()},
                "OA审批通知",
                "您有一个新的OA审批任务",
                Extras.OA_APPROVE_TASK,
                stringRedisTemplate
        );
        return new ResponseJson();
    }
    public ResponseJson pushUrge(String id) {
        ProcessBusinessData processBusinessData =  findProcessBusinessDataById(id);
        if(Objects.isNull(processBusinessData)){
            return new ResponseJson(false,"催办流程不存在");
        }
        if(processBusinessData.getStatus() != Constant.OA.WAIT_TO_APPROVE){
            return new ResponseJson(false,"当前流程已审批完成,不能发送催办通知");
        }
        String[] alias =processBusinessData.getApprover().stream().filter(p -> p.getStatus()!=null&&Constant.OA.WAIT_TO_APPROVE == p.getStatus())
                .flatMap(p -> Stream.of(p.getId())).toArray(String[]::new);
        if(ArrayUtil.isEmpty(alias)){
            return new ResponseJson(false,"未找到当前流程审批人,不能发送催办通知");
        }
        OAPush.push(
                processBusinessData,
                alias,
                "流程催办通知",
                StrUtil.format("{}提醒您审批他的{}",processBusinessData.getInitiator(),processBusinessData.getType()),
                Extras.OA_APPROVE_TASK,
                stringRedisTemplate
                );
        return new ResponseJson();
    }
    public List<ProcessBusinessData> findProcessBusinessDataListByConditionForKq(ProcessBusinessData processBusinessData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(MongoKit.buildMatchDocument(processBusinessData));
        Pager pager = processBusinessData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ProcessBusinessData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ProcessBusinessData.class, document)));
        return list;
    }

}
