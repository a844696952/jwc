package com.yice.edu.cn.oa.service.schoolProcess;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.oa.process.*;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.oa.feignClient.DepartmentTeacherFeign;
import com.yice.edu.cn.oa.feignClient.TeacherFeign;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolProcessService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public SchoolProcess findSchoolProcessById(String id) {
        return mot.findById(id,SchoolProcess.class);
    }
    public void saveSchoolProcess(SchoolProcess schoolProcess) {
        schoolProcess.setId(sequenceId.nextId());
        schoolProcess.setCreateTime(DateUtil.now());
        mot.insert(schoolProcess);
    }
    public List<SchoolProcess> findSchoolProcessListByCondition(SchoolProcess schoolProcess) {
        Example<SchoolProcess> example = Example.of(schoolProcess, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(schoolProcess.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getMappedResults();

    }
    public SchoolProcess findOneSchoolProcessByCondition(SchoolProcess schoolProcess) {
        Example<SchoolProcess> example = Example.of(schoolProcess, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = schoolProcess.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getUniqueMappedResult();
    }
    public long findSchoolProcessCountByCondition(SchoolProcess schoolProcess) {
        Example<SchoolProcess> example = Example.of(schoolProcess, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, SchoolProcess.class);
    }
    public void updateSchoolProcess(SchoolProcess schoolProcess) {
        schoolProcess.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(schoolProcess.getId())), MongoKit.update(schoolProcess),SchoolProcess.class);
    }
    public void deleteSchoolProcess(String id) {
        mot.remove(query(where("id").is(id)),SchoolProcess.class);
    }
    public void deleteSchoolProcessByCondition(SchoolProcess schoolProcess) {
        Example<SchoolProcess> example = Example.of(schoolProcess, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SchoolProcess.class);
    }
    public void batchSaveSchoolProcess(List<SchoolProcess> schoolProcesss){
        schoolProcesss.forEach(schoolProcess -> schoolProcess.setId(sequenceId.nextId()));
        mot.insertAll(schoolProcesss);
    }

    public List<SchoolProcess> findSchoolProcessListFromProcessLib(String schoolId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(where("schoolId").is(schoolId).and("processLibId").exists(true)));
        operations.add(Aggregation.project("id","type","processLibId"));
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getMappedResults();
    }

    public void addNewProcessLib(List<String> processLibIds, String schoolId) {
        List<ProcessLib> processLibs = mot.find(query(where("id").in(processLibIds)), ProcessLib.class);
        if(processLibs.size()>0){
            List<SchoolProcess> schoolProcesses = new ArrayList<>();
            Map<String,ProcessSort> cacheGroup = Maps.newHashMap();
            processLibs.forEach(processLib -> {
                SchoolProcess schoolProcess = new SchoolProcess();
                BeanUtil.copyProperties(processLib,schoolProcess);
                schoolProcess.setId(sequenceId.nextId());
                schoolProcess.setProcessLibId(processLib.getId());
                schoolProcess.setSchoolId(schoolId);
                schoolProcess.setUpdateTime(DateUtil.now());
                schoolProcess.setStatus(true);
                schoolProcess.setSequential(true);
                schoolProcess.setCreateTime(DateUtil.now());
                if(StrUtil.isNotEmpty(processLib.getDefaultGroupName())){
                    setDefaultGroup(processLib.getDefaultGroupName(), schoolProcess,cacheGroup);
                }
                schoolProcesses.add(schoolProcess);
            });
            mot.insertAll(schoolProcesses);
        }
    }

    private void setDefaultGroup(String defaultGroup, SchoolProcess schoolProcess, Map<String, ProcessSort> cache) {
        //获取分类里面是否存在当前分类
        ProcessSort group;
        if(cache.containsKey(defaultGroup)){
            group = cache.get(defaultGroup);
        }else{
            group= mot.aggregate(Aggregation.newAggregation(ProcessSort.class,Aggregation.match(where("sortName").is(defaultGroup)
                    .and("schoolId").is(schoolProcess.getSchoolId()))),ProcessSort.class).getUniqueMappedResult();
            cache.put(defaultGroup,group);
        }
        if(Objects.isNull(group)){
            group = new ProcessSort();
            group.setId(sequenceId.nextId());
            group.setSortName(defaultGroup);
            group.setSchoolId(schoolProcess.getSchoolId());
            group.setAppNum("考勤休假".equals(defaultGroup)?1:2);
            group.setCreateTime(DateUtil.now());
            mot.insert(group);
            cache.put(defaultGroup,group);
        }
        schoolProcess.setGroupId(group.getId());
        schoolProcess.setGroupName(group.getSortName());
    }

    public List<SchoolProcess> listValidSchoolProcessBySchoolId(String schoolId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(where("schoolId").is(schoolId).and("status").is(true)));
        operations.add(Aggregation.project("id","type","appIcon","processLibId","imageIcon","groupId","groupName"));//暂时列表这里只需要返回这三个字段，且不需要分页
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getMappedResults();
    }

    public void start(ProcessBusinessData processBusinessData) {
        processBusinessData.setId(sequenceId.nextId());
        processBusinessData.setCreateTime(DateUtil.now());
        mot.insert(processBusinessData);
    }


    public SchoolProcess findSchoolProcessByIdForStart(String schoolProcessId, String myId) {
        SchoolProcess schoolProcess = this.findSchoolProcessById(schoolProcessId);
        if(Objects.isNull(schoolProcess)){
            return null;
        }
        schoolProcess.setStatus(null);
        //处理默认值里带有占位符的,目前只有teacherName,后期有需要另外维护
        List<ProcessForm> processForms = schoolProcess.getProcessForms();
        if(processForms!=null&&processForms.size()>0){
            processForms.forEach(form -> {
                if("${teacherName}".equals(form.getDefaultValue())){
                    Teacher teacher=teacherFeign.findTeacherById(myId);
                    form.setDefaultValue(teacher.getName());
                }
                if("${departmentName}".equals(form.getDefaultValue())){
                   List<DepartmentTeacher> list =  departmentTeacherFeign.findDepartmentByTeacherId(myId);
                    if(CollUtil.isNotEmpty(list)){
                        form. setDefaultValue(list.get(0).getDepartmentId());
                    }else{
                        form.setDefaultValue("");//没有部门的为空
                    }
                }
            });
        }
        if(CollUtil.isEmpty(schoolProcess.getApprover())){
            ProcessDefaultApprover def = mot.aggregate(Aggregation.newAggregation(ProcessDefaultApprover.class,
                    Aggregation.match(new Criteria("schoolProcessId").is(schoolProcess.getId())
                         .and("initiatorId").is(myId))),ProcessDefaultApprover.class).getUniqueMappedResult();
            if(Objects.nonNull(def)){
                schoolProcess.setApprover(def.getApprover());
                schoolProcess.setCopyFor(Optional.ofNullable(def.getCopyFor()).orElse(Lists.newArrayList()));
            }
        }
        return schoolProcess;
    }

    /**
     * 过期, 请使用 com.yice.edu.cn.oa.service.schoolProcess.SchoolProcessService#startProcess(com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData)
     * @param formData
     * @param schoolProcessId
     * @param id
     * @return
     */
    @Deprecated
    public ResponseJson startProcess(Map<String, Object> formData, String schoolProcessId, String id) {
        Teacher teacher = teacherFeign.findTeacherById(id);
        SchoolProcess schoolProcess = this.findSchoolProcessById(schoolProcessId);
        //1.验证所有必填项,长度和正则表达式,以及select增加的冗余字段,最后还有数据类型转换
        boolean hasBeginTime=false;
        String beginTimeFormat="";
        if(schoolProcess==null){
            return new ResponseJson(false, "不存在该流程类型");
        }
        for (ProcessForm form : schoolProcess.getProcessForms()) {
            if(form.getName().equals(Constant.OA.BEGIN_TIME)){
                hasBeginTime=true;
                beginTimeFormat=form.getTimeFormat();
            }
            Object val = formData.get(form.getName());
            if(form.getRequired()&&(val==null||"".equals(val))){
                return new ResponseJson(false,String.format("%s必填",form.getName()));
            }else if(!form.getRequired()&&(val==null||"".equals(val))){
                continue;
            }
            String value = String.valueOf(val).trim();

            if(form.getLen()!=null&&form.getDataType().equals(Constant.OA.DATA_TYPE_STRING)){
                if(value.toString().length()>form.getLen()){
                    return new ResponseJson(false, String.format("%s长度必须小于%s", form.getName(), form.getLen()));
                }
            }
            if(form.getRegexp()!=null){
                Pattern pattern = Pattern.compile(form.getRegexp());
                Matcher matcher = pattern.matcher(value.toString());
                if(!matcher.matches()){
                    return new ResponseJson(false, form.getRegexpTip());
                }
            }
            if(form.getFormType().equals("select")){
                Object selectName = formData.get(form.getName() + Constant.OA.FORM_SELECT_SUFFIX);
                if(selectName==null||selectName.toString().trim().equals("")){
                    return new ResponseJson(false,"select控件的Name冗余字段必须传递");
                }
            }
            //根据dataType转换数据类型
            if(form.getDataType().equals(Constant.OA.DATA_TYPE_INTEGER)){
                formData.put(form.getName(),Integer.valueOf(value));
            }else if(form.getDataType().equals(Constant.OA.DATA_TYPE_DOUBLE)){
                formData.put(form.getName(), Double.valueOf(value));
            }
            if(form.getDataType().equals(Constant.OA.DATA_TYPE_ARRAY)){
                if(!(formData.get(form.getName()) instanceof List)){
                    return new ResponseJson(false, "dataType为Array的表单控件值必须是数组");
                }
            }
        }
        LocalDateTime beginTime=null;
        LocalDateTime endTime=null;
        //2.验证结束时间必须大于开始时间
        if(hasBeginTime){
            beginTime = LocalDateTime.parse(formData.get(Constant.OA.BEGIN_TIME).toString(), DateTimeFormatter.ofPattern(beginTimeFormat));
            endTime = LocalDateTime.parse(formData.get(Constant.OA.END_TIME).toString(), DateTimeFormatter.ofPattern(beginTimeFormat));
            if(beginTime.compareTo(endTime)>=0){
                return new ResponseJson(false, "结束时间必须大于开始时间");
            }
        }
        //4.验证timeSpan
        if(schoolProcess.getTimeSpan()!=null){
            beginTime = LocalDateTime.parse(formData.get(Constant.OA.BEGIN_TIME).toString(), DateTimeFormatter.ofPattern(beginTimeFormat));
            endTime = LocalDateTime.parse(formData.get(Constant.OA.END_TIME).toString(), DateTimeFormatter.ofPattern(beginTimeFormat));
            LocalDateTime to = endTime.minusHours(schoolProcess.getTimeSpan());
            if(to.compareTo(beginTime) >= 0){
                return new ResponseJson(false, String.format("申请时间必须小于%s小时", schoolProcess.getTimeSpan()));
            }
        }
        //5.验证occupancy
        if(schoolProcess.getOccupancy()!=null&&schoolProcess.getOccupancy()){//表示场地在使用时间上具有占用性
            if(schoolProcess.getProcessLibId().equals("20181027163655006")){//会议室申请
                List<ProcessBusinessData> exists = mot.find(query(where("formData.room")
                        .is(formData.get(Constant.OA.ROOM))
                        .and("formData.endTime").gte(formData.get(Constant.OA.BEGIN_TIME))
                        .and("formData.beginTime").lte(formData.get(Constant.OA.END_TIME))
                        .orOperator(where("status").is(Constant.OA.SUCCESS_COMPLETE),where("status").is(Constant.OA.WAIT_TO_APPROVE))), ProcessBusinessData.class);
                if(exists.size()>0){
                    return new ResponseJson(false,String.format("该时间段内%s已被占用,请重新选择时间",formData.get("roomName")));
                }
            }else if(schoolProcess.getProcessLibId().equals("20181027163655009")){//专用教室申请
                Document findDocument = new Document("formData.room", formData.get(Constant.OA.ROOM))
                        .append("formData.applyTime", formData.get(Constant.OA.APPLY_TIME))
                        .append("formData.timeInterval", formData.get(Constant.OA.TIME_INTERVAL))
                        .append("formData.classHour", new Document("$in", formData.get(Constant.OA.CLASS_HOUR)))
                        .append("status",new Document("$ne",Constant.OA.FAIL_COMPLETE));
                System.out.println(findDocument);
                FindIterable<Document> documents = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(findDocument);
                List<ProcessBusinessData> exists = new ArrayList<>();
                documents.forEach((Block<Document>) document -> exists.add(mot.getConverter().read(ProcessBusinessData.class, document)));
                if(exists.size()>0){
                    ProcessBusinessData exist = exists.get(0);
                    return new ResponseJson(false,String.format("%s %s,%s %s 已被%s老师申请使用,请重新选择时间",formData.get(Constant.OA.ROOM+"Name"),formData.get(Constant.OA.APPLY_TIME)
                            ,formData.get(Constant.OA.TIME_INTERVAL+"Name"),exist.getFormData().get(Constant.OA.CLASS_HOUR+"Name"),exist.getInitiator()
                    ));
                }
            }

        }
        ProcessBusinessData processBusinessData = new ProcessBusinessData();
        BeanUtil.copyProperties(schoolProcess,processBusinessData,"status","approver");
        processBusinessData.setFormData(formData);
        processBusinessData.setSchoolProcessId(schoolProcessId);
        processBusinessData.setInitiatorId(teacher.getId());
        processBusinessData.setInitiator(teacher.getName());
        processBusinessData.setCreateTime(DateUtil.now());
        processBusinessData.setStatus(Constant.OA.WAIT_TO_APPROVE);
        processBusinessData.setImgUrl(teacher.getImgUrl());
        List<OaPeople> approver = schoolProcess.getApprover();
        if(schoolProcess.getSequential()){
            approver.get(0).setStatus(Constant.OA.WAIT_TO_APPROVE);
        }else{
            approver.forEach(app->{
                app.setStatus(Constant.OA.WAIT_TO_APPROVE);
            });
        }
        processBusinessData.setApprover(approver);
        processBusinessData.setId(sequenceId.nextId());
        mot.insert(processBusinessData);
        //发推送通知待审批的用户给tap应用
        Push push = Push.newBuilder(JpushApp.TAP).setAlias(approver.stream().filter(p -> p.getStatus()!=null&&Constant.OA.WAIT_TO_APPROVE == p.getStatus()).flatMap(p -> Stream.of(p.getId())).toArray(String[]::new))
                .setNotification(Push.Notification.newBuilder().setTitle("oa审批任务通知").setAlert("您有新的由" + processBusinessData.getInitiator() + "所发起的流程审批任务")
                        .setExtras(Extras.newBuilder().setId(processBusinessData.getId()).setType(Extras.OA_APPROVE_TASK).build())
                        .build())
                .setMessage(Push.Message.newBuilder().setTitle("oa审批任务通知").setAlert("您有新的由" + processBusinessData.getInitiator() + "所发起的流程审批任务")
                        .setExtras(Extras.newBuilder().setId(processBusinessData.getId()).setType(Extras.OA_APPROVE_TASK).build())
                        .build())
                .build();
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

        return new ResponseJson(processBusinessData);
    }


    /**
     * v1版本启动流程
     * @param processBusinessData
     * @return
     */
    public ResponseJson startProcess(ProcessBusinessData processBusinessData) {
        SchoolProcess schoolProcess = this.findSchoolProcessById(processBusinessData.getSchoolProcessId());
        if(schoolProcess==null){
            return new ResponseJson(false, "不存在该流程类型");
        }
        if(!schoolProcess.getStatus()){
            return new ResponseJson(false, "流程已被禁用,不允许申请");
        }
        if(processBusinessData.getApprover().size() > Constant.OA.MAX_APPROVER){
            return new ResponseJson(false, StrUtil.format("流程审批人最大不能超过{}人,当前审批人数为:{}", Constant.OA.MAX_APPROVER,processBusinessData.getApprover().size()));
        }
        //1.验证所有必填项,长度和正则表达式,以及select增加的冗余字段,最后还有数据类型转换
        boolean hasBeginTime=false;
        SimpleDateFormat  beginTimeFormat=null;
        Map<String, Object> formData = processBusinessData.getFormData();
        for (ProcessForm form : schoolProcess.getProcessForms()) {
            if(form.getFormType().equals("divideLine")){
                continue; //过滤分割线
            }
            if(form.getName().equals(Constant.OA.BEGIN_TIME)){
                hasBeginTime=true;
                beginTimeFormat=new SimpleDateFormat(form.getTimeFormat());
            }
            System.out.println(form.getFormType()+":"+form.getName());
            Object val = formData.get(form.getName());
            if(val==null){
                continue;
            }
            if(form.getRequired() && "".equals(val)){
                return new ResponseJson(false,String.format("%s必填",form.getName()));
            }else if(!form.getRequired() && "".equals(val)){
                continue;
            }
            String value = String.valueOf(val).trim();
            if(form.getLen()!=null&&form.getDataType().equals(Constant.OA.DATA_TYPE_STRING)){
                if(value.length()>form.getLen()){
                    return new ResponseJson(false, String.format("%s长度必须小于%s", form.getName(), form.getLen()));
                }
            }
            if(form.getRegexp()!=null){
                Pattern pattern = Pattern.compile(form.getRegexp());
                Matcher matcher = pattern.matcher(value);
                if(!matcher.matches()){
                    return new ResponseJson(false, form.getRegexpTip());
                }
            }
            /*if(form.getFormType().toLowerCase().contains("select")){
                Object selectName = formData.get(form.getName() + Constant.OA.FORM_SELECT_SUFFIX);
                if(selectName==null||selectName.toString().trim().equals("")){
                    return new ResponseJson(false,"select和multiSelect控件的Name冗余字段必须传递");
                }
            }*/
            //根据dataType转换数据类型
            if(form.getDataType().equals(Constant.OA.DATA_TYPE_INTEGER) ||
                    Constant.OA.DATA_TYPE_NUMBER.equals(form.getDataType()) ){
                formData.put(form.getName(),Integer.valueOf(value));
            }else if(form.getDataType().equals(Constant.OA.DATA_TYPE_DOUBLE)){
                formData.put(form.getName(), Double.valueOf(value));
            }
            //2.验证数组数据类型
            if(form.getDataType().equals(Constant.OA.DATA_TYPE_ARRAY)){
                if(!(formData.get(form.getName()) instanceof List)){
                    return new ResponseJson(false, "dataType为Array的表单控件值必须是数组");
                }
            }
        }
        //3.验证结束时间必须大于开始时间
        DateTime beginTime=null;
        DateTime endTime=null;
        if(hasBeginTime){
            beginTime =DateUtil.parse(formData.get(Constant.OA.BEGIN_TIME).toString(),beginTimeFormat);
            endTime =DateUtil.parse(formData.get(Constant.OA.END_TIME).toString(),beginTimeFormat);
            if(beginTime.compareTo(endTime)>=0){
                return new ResponseJson(false, "结束时间必须大于开始时间");
            }
        }
        //4.验证两个东西,timeSpan
        if(schoolProcess.getTimeSpan()!=null){
            boolean endtime=false,durationBol=false;
            DateTime to = DateUtil.offsetHour(endTime, -schoolProcess.getTimeSpan());
            if(to.isAfter(beginTime)){
                endtime = true;
            }
            if(formData.containsKey("duration")){
                int duration = MapUtil.getInt(formData,"duration");
                if(schoolProcess.getTimeSpan() < duration){
                    durationBol = true;
                }
            }
            if(endtime || durationBol){
                return new ResponseJson(false, String.format("申请时间必须小于%s小时", schoolProcess.getTimeSpan()));
            }
        }
        //5.验证occupancy
        if(schoolProcess.getOccupancy()!=null&&schoolProcess.getOccupancy()){//表示场地在使用时间上具有占用性
            if(schoolProcess.getProcessLibId().equals("20181027163655006")){//会议室申请
                List<ProcessBusinessData> exists = mot.find(query(where("formData.room")
                        .is(formData.get(Constant.OA.ROOM))
                        .and("formData.endTime").gte(formData.get(Constant.OA.BEGIN_TIME))
                        .and("formData.beginTime").lte(formData.get(Constant.OA.END_TIME))
                        .orOperator(where("status").is(Constant.OA.SUCCESS_COMPLETE),where("status").is(Constant.OA.WAIT_TO_APPROVE))), ProcessBusinessData.class);
                if(exists.size()>0){
                    return new ResponseJson(false,StrUtil.format("该时间段内已被{}老师占用,请重新选择时间",exists.get(0).getInitiator()));
                }
            }else if(schoolProcess.getProcessLibId().equals("20181027163655009")){//专用教室申请
                Document findDocument = new Document("formData.room", formData.get(Constant.OA.ROOM))
                        .append("formData.applyTime", formData.get(Constant.OA.APPLY_TIME))
                        .append("formData.timeInterval", formData.get(Constant.OA.TIME_INTERVAL))
                        .append("formData.classHour", new Document("$in", formData.get(Constant.OA.CLASS_HOUR)))
                        .append("status",new Document("$ne",Constant.OA.FAIL_COMPLETE));
                FindIterable<Document> documents = mot.getCollection(mot.getCollectionName(ProcessBusinessData.class)).find(findDocument);
                List<ProcessBusinessData> exists = new ArrayList<>();
                documents.forEach((Block<Document>) document -> exists.add(mot.getConverter().read(ProcessBusinessData.class, document)));
                if(exists.size()>0){
                    ProcessBusinessData exist = exists.get(0);
                    return new ResponseJson(false,StrUtil.format("该专用教室已被{}老师申请,请重新选择时间",exist.getInitiator()));
                }
            }
        }
        //6分流程类型验证
        ResponseJson responseJson = this.validLeaveProcess(processBusinessData);
        if(!responseJson.getResult().isSuccess()){
            return responseJson;
        }
        //7 验证多条件
        // this.matchCondition(schoolProcess,processBusinessData);

        BeanUtil.copyProperties(schoolProcess,processBusinessData,"status","approver","copyFor","sequential","passCount");
        processBusinessData.setCreateTime(DateUtil.now());
        processBusinessData.setStatus(Constant.OA.WAIT_TO_APPROVE);
        List<OaPeople> approver = processBusinessData.getApprover();
        if(processBusinessData.getSequential()){
            approver.get(0).setStatus(Constant.OA.WAIT_TO_APPROVE);
        }else{
            approver.forEach(app-> app.setStatus(Constant.OA.WAIT_TO_APPROVE));
        }
        processBusinessData.setId(sequenceId.nextId());
        mot.insert(processBusinessData);
        //发推送通知待审批的用户给tap应用
        OAPush.push(processBusinessData,
                approver.stream().filter(p -> p.getStatus()!=null&&Constant.OA.WAIT_TO_APPROVE == p.getStatus()).flatMap(p -> Stream.of(p.getId())).toArray(String[]::new),
                "oa审批任务通知",
                StrUtil.format("您有新的由{}所发起的{}流程审批任务",processBusinessData.getInitiator(),processBusinessData.getType()),
                Extras.OA_APPROVE_TASK,
                stringRedisTemplate);
        //判断是否初次填写审批人,没有则添加
        saveProcessDefaultAppover(schoolProcess,processBusinessData);
        return new ResponseJson(processBusinessData);
    }

    private void matchInitiator(ProcessBusinessData processBusinessData,ProcessCondition ps,List<Boolean> match){
        //匹配发起人
        if(CollUtil.isNotEmpty(ps.getInitiators())) {

            boolean matchInitiator=ps.getInitiators().stream().anyMatch(cur -> ObjectUtil.equal(processBusinessData.getInitiatorId(),cur.getId()));
            match.add(matchInitiator);
        }
    }
    private void matchLeaveType(ProcessBusinessData processBusinessData,ProcessCondition ps,List<Boolean> match){
        Map<String, Object> formData = processBusinessData.getFormData();
        if(CollUtil.isNotEmpty(ps.getVerifyType())) {
            boolean matchLeaveType = ps.getVerifyType().stream().anyMatch( vt -> ObjectUtil.equal(vt.getId(),formData.get("leaveType")) );// && ObjectUtil.equal(vt.getId(),formData.get("leaveType")) //
            match.add(matchLeaveType);
        }
    }

    private void matchRule(ProcessBusinessData processBusinessData,ProcessCondition ps,List<Boolean> match){
        boolean matchRule;
        Map<String, Object> formData = processBusinessData.getFormData();
        if(ObjectUtil.isNull(ps.getVerifyRule()) && ObjectUtil.isNull(ps.getConditionVal())){
            return; //未填写验证条件 不执行
        }
        //匹配字段验证
        double verifyValue = 0;
        if(formData.containsKey(ps.getVerifyField())){ // 验证字段是否存在表单
            try {
                verifyValue = Double.parseDouble(String.valueOf(formData.get(ps.getVerifyField())));
            }catch(NumberFormatException e){
                e.printStackTrace();
                Console.error("格式化验证值失败 :{}",e.getMessage());
            }
        }else{
            return;
        }
        if(Double.compare(verifyValue, 0) == 1) {
            double _verifyValue = verifyValue;
            String conditionUnit = ps.getConditionUnit();//验证单位 默认是天 如果是小时 默认按24小时制
            if("天".equals(conditionUnit)) {
                _verifyValue= NumberUtil.round(NumberUtil.mul(verifyValue, 24), 2).doubleValue(); //默认四舍五入 保留两位
            }
            Optional<Double> value = Optional.ofNullable(ps.getConditionVal());
            if(! value.isPresent()) {//没有填写验证值
                match.add(false); return ;
            }
            String rule = ps.getVerifyRule();//验证规则  <      >       =   > =   <=
            int rs = Double.compare(_verifyValue,value.orElse(0.0));
            Console.log("{} {} {} ={} ",_verifyValue,rule,value.get(),rs);
            switch(rule) {
                case "gt"://>
                    matchRule = (rs == 1);
                    break;
                case "lt"://<
                    matchRule = (rs == -1);
                    break;
                case "gte":// >=
                    matchRule =  (rs == 0 || rs == 1);
                    break;
                case "lte": // <=
                    matchRule = (rs ==0 || rs == -1);
                    break;
                case "eq": // =
                    matchRule = (rs == 0);
                    break;
                default:
                    matchRule=false;
            }
            match.add(matchRule);
        }
    }


    /**
     * 验证多条件是否满足
     * @param processBusinessData
     * @return
     */
    private ProcessCondition matchCondition(SchoolProcess schoolProcess,ProcessBusinessData processBusinessData) {
        List<ProcessCondition> list = schoolProcess.getProcessConditions();
        if(null == list) {//未找到流程条件  流程走默认配置
            Console.log("{} 未配置 条件验证规则 ",schoolProcess.getType());
            return null;
        }
       /* Optional<ProcessCondition> cod = list.stream().sorted(Comparator.comparing(ProcessCondition::getWeight)).filter(ps ->{
            if(CollUtil.isNotEmpty(ps.getInitiators())) {
                return ps.getInitiators().stream().anyMatch(cuur -> ObjectUtil.equal(processBusinessData.getInitiator(),cuur.getName()));
            }
            return false;
        }).findFirst();*/
        Optional<ProcessCondition> cod = list.stream().sorted(Comparator.comparing(ProcessCondition::getWeight)).filter(ps ->{
            Console.log("优先级 {} 开始匹配 ",ps.getWeight());
            List<Boolean> match = new ArrayList<>();//需要验证的条件的结果
            matchInitiator(processBusinessData,ps,match);//验证发起人
            if(schoolProcess.getProcessLibId().equals("20181027163655001")){//请假申请
                matchLeaveType(processBusinessData,ps,match);//验证请假类型
                matchRule(processBusinessData,ps,match);//验证规则
            }
            if(schoolProcess.getProcessLibId().equals("20181027163655004")){//报销申请 验证规则
                matchRule(processBusinessData,ps,match);//验证规则
            }
            Console.log("优先级 {} 匹配结束;  {}",ps.getWeight(),match);
            return match.stream().allMatch(m -> m.booleanValue());
        }).findFirst();
        if(cod.isPresent()){
            ProcessCondition ps = cod.get(); //如果匹配成功  设置几点:审批人 抄送人 是否会签 passCount
            processBusinessData.setSequential(ps.getSequential());
            if(!ps.getSequential()){
                processBusinessData.setPassCount(ps.getPassCount());
            }
            processBusinessData.setApprover(ps.getApprover());
            processBusinessData.setCopyFor(ps.getCopyFor());
            return ps;
        }
        return  null;
    }


    /**
     * 验证请假流程
     * @param processBusinessData
     * @return
     */
    public ResponseJson validLeaveProcess(ProcessBusinessData processBusinessData){
        List<String> libIds=Arrays.asList("20181027163655001","20181027163655010","20181027163655002","20181027163655003");//请假 公出 出差 加班
        if(libIds.contains(processBusinessData.getProcessLibId())){
            Map<String, Object> formData = processBusinessData.getFormData();
            Criteria where=  where("initiatorId").is(processBusinessData.getInitiatorId())
                    .and("processLibId").in(libIds).and("formData.endTime").gte(formData.get(Constant.OA.BEGIN_TIME))
                    .and("formData.beginTime").lte(formData.get(Constant.OA.END_TIME));
                  // where.and("status").ne(Constant.OA.FAIL_COMPLETE);
            List<ProcessBusinessData> exists = mot.find(query(where),ProcessBusinessData.class);
            // 未撤销并审批失败的不具有暂有性
           // exists=exists.stream().filter(v ->  !(StrUtil.isEmpty(v.getCancelReason()) && v.getStatus() == Constant.OA.FAIL_COMPLETE)).collect(Collectors.toList());
            //请假撤销通过后时间不具有暂有性
            exists=exists.stream().filter(v ->  !(StrUtil.isEmpty(v.getCancelReason()) && v.getStatus() == Constant.OA.FAIL_COMPLETE))
                    .filter(v ->
                        !("20181027163655001".equals(v.getProcessLibId()) &&
                                StrUtil.isNotEmpty(v.getCancelReason()) &&
                                v.getStatus() == Constant.OA.SUCCESS_COMPLETE)
            ).collect(Collectors.toList());
            if(exists.size()>0){
                return new ResponseJson(false,StrUtil.format("该时间段内您的{}正在审批或者审批通过,请重新选择时间",exists.stream().map(v-> v.getType()).collect(Collectors.joining(","))));
            }
        }
        return new ResponseJson();
    }

    private ProcessForm getProcessFormByName(List<ProcessForm> processForms,String name){
        for (ProcessForm processForm : processForms) {
            if(processForm.getName().equals(name)){
                return processForm;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DateTime begin = DateUtil.parse("2019-02-19", new SimpleDateFormat("yyyy-MM-dd"));
        DateTime end = DateUtil.parse("2019-02-19 12:30:30", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        System.out.println(begin.equals(end));
//        LocalDate.parse("2019-02-19", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public ResponseJson findFlowConditionByFormData(ProcessBusinessData processBusinessData) {
        ResponseJson rs = new ResponseJson();
        SchoolProcess schoolProcess = this.findSchoolProcessById(processBusinessData.getSchoolProcessId());
        if(schoolProcess==null){
            return new ResponseJson(false, "不存在该流程类型");
        }
        //未验证表单数据
        ProcessCondition ps= this.matchCondition(schoolProcess,processBusinessData);
        rs.setData(ps);
        rs.setData2(processBusinessData);
        return rs;
    }

    /**
     * 获取学校流程分组
     * @param schoolId
     * @return
     */
    public List<SchoolProcess> listSchoolProcessGroup(String schoolId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(where("schoolId").is(schoolId).and("status").is(true)));
        operations.add(Aggregation.group("groupId").first("groupId").as("groupId").first("groupName").as("groupName"));
        operations.add(Aggregation.lookup(
                mot.getCollectionName(ProcessSort.class),
                "groupId",
                "_id",
                "groups"
        ));
        operations.add(Aggregation.sort(Sort.Direction.ASC,"groups.appNum"));
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getMappedResults();
    }

    public List<SchoolProcess> findSchoolProcessForSelect(String schoolId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(where("schoolId").is(schoolId).and("status").is(true)));
        operations.add(Aggregation.project("id","type","processForms","processLibId"));
        return mot.aggregate(Aggregation.newAggregation(SchoolProcess.class,operations),SchoolProcess.class).getMappedResults();
    }

    public void deleteSchoolProcessAll(String schoolId, String id) {
        mot.remove(query(where("_id").is(id)),SchoolProcess.class);
        //同时删除 实例表  抄送表
        List<ProcessBusinessData> ids= mot.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(where("schoolProcessId").is(id)),
                        Aggregation.project("_id")
                ),
                mot.getCollectionName(ProcessBusinessData.class),
                ProcessBusinessData.class
        ).getMappedResults();
        if(ids.size() > 0){
            //删除 实例表数据
            mot.remove(query(where("schoolProcessId").is(id)),ProcessBusinessData.class);
            List<String> processId= ids.stream().map(v -> v.getId()).collect(Collectors.toList());
            //删除抄送表
            mot.remove(query(where("processBusinessDataId").in(processId)), ProcessCopy.class);
        }
        // 删除单个用户默认审批人
        mot.remove(query(where("schoolProcessId").is(id)),ProcessDefaultApprover.class);
    }
    private void saveProcessDefaultAppover(SchoolProcess schoolProcess, ProcessBusinessData processBusinessData){
        Predicate<SchoolProcess> isEmptyApprover = (v)-> CollUtil.isEmpty(v.getApprover());
       /*Predicate<SchoolProcess> isExistConditionApprover = (s)->{
            List<ProcessCondition> con = s.getProcessConditions();
            if(Objects.isNull(con)){
                return false;
            }
          return con.stream().anyMatch(v-> {
                if(CollUtil.isNotEmpty(v.getInitiators())){
                    return v.getInitiators().stream().anyMatch( f -> Objects.equals(f.getId(),processBusinessData.getInitiatorId()));
                }
                return false;
            });
        };*/
        if(isEmptyApprover.test(schoolProcess) /*|| !isExistConditionApprover.test(schoolProcess)*/){
            ProcessDefaultApprover def = new ProcessDefaultApprover();
            def.setSchoolProcessId(schoolProcess.getId());
            def.setApprover(processBusinessData.getApprover());
            def.setCopyFor(processBusinessData.getCopyFor());
            def.setInitiator(processBusinessData.getInitiator());
            def.setInitiatorId(processBusinessData.getInitiatorId());
            def.setCreateTime(DateUtil.now());
            def.setSchoolId(schoolProcess.getSchoolId());
            long count = mot.getCollection(mot.getCollectionName(ProcessDefaultApprover.class)).countDocuments(new Document("schoolProcessId",schoolProcess.getId()).append("initiatorId",processBusinessData.getInitiatorId()));
            if(count > 0){
                mot.updateFirst(query(where("schoolProcessId").is(schoolProcess.getId()).and("initiatorId").is(processBusinessData.getInitiatorId())),
                        MongoKit.update(def),
                        mot.getCollectionName(ProcessDefaultApprover.class));
            }else{
                def.setId(sequenceId.nextId());
                mot.insert(def);
            }
        }
    }
}
