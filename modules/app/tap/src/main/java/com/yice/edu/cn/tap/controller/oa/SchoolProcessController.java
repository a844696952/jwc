package com.yice.edu.cn.tap.controller.oa;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.common.SchoolProcessListView;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.tap.service.oa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.*;

@Api(value = "/schoolProcess", description = "oa中的和流程相关的api都在这")
@RestController
@RequestMapping("/schoolProcess")
@Validated
public class SchoolProcessController {
    @Autowired
    private SchoolProcessService schoolProcessService;
    @Autowired
    private MyApproveService myApproveService;
    @Autowired
    private ProcessCopyService processCopyService;
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;
    @Autowired
    private OaStatsService oaStatsService;
    @Autowired
    private ProcessSortService processSortService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    /**
              * 列出学校可用流程和我的待审批个数
     * @return
     */
    @ApiOperation(value = "1.列出学校可用流程(只返回id,type,appIcon三个字段)和我的待审批个数",notes = "data字段是流程列表,totalCount字段是我的待审批个数",response = SchoolProcess.class)
    @GetMapping("/findSchoolProcessesAndApproveCount")
    public ResponseJson findSchoolProcessesAndApproveCount(){
        List<SchoolProcess> schoolProcesses = schoolProcessService.listValidSchoolProcessBySchoolId(mySchoolId());
        List<ProcessSort> groups = processSortService.getProcessSortList(mySchoolId());
        List<SchoolProcessListView> viewList = new ArrayList<>();
        Function<List<SchoolProcess>,List<Object>> fun = (list)-> list.stream().peek(model ->  model.setType(model.getType().replace("申请", ""))).collect(Collectors.toList()) ;
        groups.forEach(g ->{
            List<SchoolProcess>  temp = schoolProcesses.stream().filter(f-> StrUtil.isNotEmpty(f.getGroupId()) ).filter(f ->
                    f.getGroupId().equals(g.getId())).collect(Collectors.toList());
            if(temp.size() > 0){
                SchoolProcessListView view = new SchoolProcessListView();
                view.setKey(g.getSortName());
                view.setValue(fun.apply(temp));
                viewList.add(view);
            }
        });
        List<SchoolProcess>  def= schoolProcesses.stream().filter(f -> StrUtil.isEmpty(f.getGroupId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(def)){
            SchoolProcessListView view = new SchoolProcessListView();
            view.setKey("系统分类");
            view.setValue(fun.apply(def));
            viewList.add(view);
        }
        long count = myApproveService.findWaitApproveCount(myId(), new ProcessBusinessData());
        return new ResponseJson(viewList,count);
    }

    /**
     * 获取启动流程时的数据
     * @param schoolProcessId
     * @return
     */
    @ApiOperation(value = "2.获取启动流程时的数据",notes = "请查看Model,processForms字段里是表单控件,approver是审批人,copyFor是抄送人",response = SchoolProcess.class)
    @GetMapping("/findSchoolProcessById/{schoolProcessId}")
    public ResponseJson findSchoolProcessById(@PathVariable String schoolProcessId){
        return new ResponseJson(schoolProcessService.findSchoolProcessByIdForStart(schoolProcessId,myId()));
    }


    /**
     * 启动流程
      * @param formData
     * @param schoolProcessId
     * @return
     */
    @ApiOperation(value = "3.启动流程(作废)", notes = "需提交processForms指定的那些控件数据")
    @PostMapping("/startProcess/{schoolProcessId}")
    @Deprecated
    public ResponseJson startProcess(@RequestBody Map<String,Object> formData,@PathVariable String schoolProcessId){
        return schoolProcessService.startProcess(formData, schoolProcessId, myId());
    }

    @ApiOperation(value = "3-1.启动流程", notes = "请查看Model,需要schoolProcessId、formData、approver、sequential,如果sequential为false则passCount必传")
    @PostMapping("/startProcess/v1")
    public ResponseJson startProcess(@RequestBody @Validated({GroupOne.class}) ProcessBusinessData processBusinessData){
        if(!processBusinessData.getSequential()&&(processBusinessData.getPassCount()==null||processBusinessData.getPassCount()>processBusinessData.getApprover().size()||processBusinessData.getPassCount()<1)){
            return new ResponseJson(false, "并行流程的通过人数必须大于等于1并且小于等于审批人数");
        }
        if(processBusinessData.getApprover().size()>10){
            return new ResponseJson(false, "审批人最多10个");
        }
        if(processBusinessData.getCopyFor()!=null&&processBusinessData.getCopyFor().size()>10){
            return new ResponseJson(false,"抄送人最多10个");
        }
        processBusinessData.setInitiator(currentTeacher().getName());
        processBusinessData.setInitiatorId(myId());
        processBusinessData.setImgUrl(currentTeacher().getImgUrl());
        processBusinessData.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearTerm(processBusinessData,mySchoolId());
        return schoolProcessService.startProcess(processBusinessData);
    }



    @ApiOperation(value = "4.获取等待我审批的流程列表", notes = "只返回id,type,createTime,approver,imgUrl,initiator详细看Model",response = ProcessBusinessData.class)
    @PostMapping("/findWaitApprove")
    public ResponseJson findWaitApprove(
                    @RequestBody
                    @Validated
                    @ApiParam(value = "只需要pager属性,请查看Model",required = true)
                    ProcessBusinessData processBusinessData){
        processBusinessData.getPager().setIncludes("id","type","createTime","approver","imgUrl","initiator","canceDate","cancelReason","initiatorId","clearLeave","clearLeaveTime");
        return new ResponseJson(myApproveService.findWaitApprove(myId(), processBusinessData));
    }
    @ApiOperation(value = "5.获取我已审批列表", notes = "只返回id,type,createTime,approver,imgUrl,initiator详细看Model",response = ProcessBusinessData.class)
    @PostMapping("/findHasApprove")
    public ResponseJson findHasApprove(
            @RequestBody
            @Validated
            @ApiParam(value = "只需要pager属性,请查看Model",required = true)
                    ProcessBusinessData processBusinessData){
        processBusinessData.getPager().setIncludes("id","type","createTime","approver","imgUrl","initiator","canceDate","cancelReason","initiatorId","clearLeave","clearLeaveTime");
        return new ResponseJson(myApproveService.findHasApproveData(myId(), processBusinessData));
    }

    /**
     * 查找是否有教师未查看的抄送
     * @return
     */
    @ApiOperation(value = "6.查找是否有教师未查看的抄送", notes = "返回true or false",response = Boolean.class)
    @GetMapping("/findHasProcessCopyOrNot")
    public ResponseJson findHasProcessCopyOrNot(){
        ProcessCopy processCopy = new ProcessCopy();
        processCopy.setTeacherId(myId());
        processCopy.setLooked(false);
        long count = processCopyService.findProcessCopyCountByCondition(processCopy);
        return new ResponseJson(count>0);
    }

    /**
     * 获取用户的抄送列表
     * @param processCopy
     * @return
     */
    @ApiOperation(value = "7.获取用户的抄送列表", notes = "返回抄送对象列表",response = ProcessCopy.class)
    @PostMapping("/findProcessCopies")
    public ResponseJson findProcessCopies(
            @RequestBody
            @Validated
            @ApiParam(value = "只需要pager属性,请查看Model",required = true)
                    ProcessCopy processCopy){
        processCopy.setTeacherId(myId());
        List<ProcessCopyVo> list = processCopyService.findProcessCopyListByCondition(processCopy);
        return new ResponseJson(list);
    }


    /**
     * 获取查看流程
     * @param processBusinessDataId
     * @return
     */
    @ApiOperation(value = "8.获取查看流程详情", notes = "待审批列表、已审批列表、抄送列表、获取我的申请四个列表的详情都是这个接口.我的申请列表的详情里status=1,clearLeave存在并且是true,并且当前时间落在formData.beginTime和formData.endTime之间,clearLeaveTime为空的情况下显示销假按钮，如果clearLeave并且是true时,详情里要显示是否已销假和销假时间",response = ProcessBusinessData.class)
    @GetMapping("/findCheckProcessBusinessData/{processBusinessDataId}")
    public ResponseJson findCheckProcessBusinessData(@PathVariable String processBusinessDataId){
        ProcessBusinessData processBusinessData = processBusinessDataService.findProcessBusinessDataById(processBusinessDataId);
        return new ResponseJson(processBusinessData);
    }

    //完成审批
    @ApiOperation(value = "9.完成审批", notes = "待审批详情里的同意或者不同意按钮")
    @PostMapping("/approve/completeApprove/{processBusinessId}")
    public ResponseJson completeApprove(@RequestBody OaPeople oaPeople,@PathVariable String processBusinessId){
        oaPeople.setId(myId());
        oaPeople.setName(currentTeacher().getName());
        return myApproveService.completeApprove(oaPeople, processBusinessId);
    }

    @PostMapping("/findMyProcessApplies")
    @ApiOperation(value = "10.获取我的申请", notes = "如果数据项的status=1,clearLeave存在并且是true,并且当前时间落在formData.beginTime和formData.endTime之间,clearLeaveTime为空的情况下显示销假按钮",response = ProcessBusinessData.class)
    public ResponseJson findMyProcessApplies(
            @ApiParam(value = "只需要pager属性,请查看Model",required = true)
            @Validated
            @RequestBody ProcessBusinessData processBusinessData) {
        processBusinessData.setInitiatorId(myId());
        processBusinessData.getPager().setIncludes("id","type","initiator","createTime","status","formData","clearLeave","clearLeaveTime","imgUrl","cancelReason","canceDate","initiatorId");
        List<ProcessBusinessData> data = processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
        return new ResponseJson(data);
    }

    /**
     * 销假
     * @param processBusinessData
     * @return
     */
    @ApiOperation(value = "11.销假", notes = "我的申请列表中对可销假的申请类型进行销假")
    @PostMapping("/clearLeave")
    public ResponseJson clearLeave(@RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getId()) && StrUtil.isEmpty(processBusinessData.getClearLeaveTime())){
            return new ResponseJson(false,"确认到校时间不能为空");
        }
        return processBusinessDataService.clearLeave(processBusinessData);
    }

    /**
     * 获取总的统计数据
     * @param rangeTime
     * @return
     */
    @PostMapping("/findTotalStats")
    @ApiOperation(value = "12.获取总的统计数据", notes = "rangeTime=必传,如: [\"2019-01-01\", \"2019-02-20\"],pager=分页信息,page:页码,pageSize:每页多少条")
    public ResponseJson findTotalStats(@RequestBody @Validated OaStats os){
        return oaStatsService.findTotalStats(os, mySchoolId());
    }

    /**
     * 获取统计详情
     * @param processBusinessData
     * @return
     */
    @PostMapping("/findStatsDetail")
    @ApiOperation(value = "13.获取统计详情", notes = "schoolProcessId,rangeTime,pager必传,status和initiator可选")
    public ResponseJson findStatsDetail(@Validated({GroupTwo.class, Default.class}) @RequestBody ProcessBusinessData processBusinessData){
        return oaStatsService.findStatsDetail(processBusinessData);
    }

    /**
     * 获取抄送详情
     * @param processCopyId
     * @param processBusinessDataId
     * @return
     */
    @GetMapping("/findProcessCopyDetail/{processCopyId}/{processBusinessDataId}")
    @ApiOperation(value = "14.获取抄送详情", notes = "返回流程业务对象",response = ProcessBusinessData.class)
    public ResponseJson findProcessCopyDetail(@PathVariable String processCopyId,@PathVariable String processBusinessDataId){
        ProcessBusinessData processBusinessData = processBusinessDataService.findProcessBusinessDataById(processBusinessDataId);
        ProcessCopy processCopy = new ProcessCopy();
        processCopy.setId(processCopyId);
        processCopy.setLooked(true);
        processCopyService.updateProcessCopy(processCopy);
        return new ResponseJson(processBusinessData);
    }
    @PostMapping("/findFlowConditionByFormData")
    @ApiOperation(value = "15.根据流程表单信息匹配流程条件", notes = "传完整的流程表单数据,返回 data:匹配的条件信息,不存在流程条件data为空,data2:若匹配成功返回表单并赋值审核人抄送人等信息",response = ResponseJson.class)
    public ResponseJson findFlowConditionByFormData(@RequestBody ProcessBusinessData processBusinessData){
        processBusinessData.setInitiator(currentTeacher().getName());
        processBusinessData.setInitiatorId(myId());
        processBusinessData.setImgUrl(currentTeacher().getImgUrl());
        processBusinessData.setSchoolId(mySchoolId());
        return schoolProcessService.findFlowConditionByFormData(processBusinessData);
    }
    @PostMapping("/operation/urge/{id}")
    @ApiOperation(value = "16.发送短信通知审批人,传流程实例id", notes = "返回响应对象")
    public ResponseJson sendMessageByProcessDataById(
            @ApiParam(value = "流程实例id")
            @PathVariable("id") String id) {
        return   processBusinessDataService.sendMessageByProcessDataById(id);
    }
    @ApiOperation(value = "17.撤销流程-未审批通过的流程,传流程实例id", notes = "返回响应对象")
    @GetMapping("/operation/cancel/{id}")
    public ResponseJson cancelFlowByWait(@PathVariable String id){
        return processBusinessDataService.cancelFlow(id,new ProcessBusinessData());

    }
    @ApiOperation(value = "18.撤销流程-如果ProcessBusinessData中CancelReason不为null 重新发起审批流程", notes = "返回响应对象")
    @PostMapping("/operation/cancel/{id}")
    public ResponseJson cancelFlowByHas(@PathVariable String id,@RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getCancelReason())){
            return new ResponseJson(false,"撤销原因不能为空");
        }
        return processBusinessDataService.cancelFlow(id,processBusinessData);
    }
    @ApiOperation(value = "19.获取学校所有有效流程,包括自建流程", notes = "返回响应对象")
    @GetMapping("/select/findSchoolProcessForSelect")
    public ResponseJson findSchoolProcessForSelect(){
        List<SchoolProcess> schoolProcesses=schoolProcessService.findSchoolProcessForSelect(mySchoolId());
        return new ResponseJson(schoolProcesses);
    }
    @PostMapping("/ignore/findTotalMoney")
    @ApiOperation(value = "20.根据学校id及流程id获取审批成功统计的金额",notes = "返回响应对象ResponseJson",response = ResponseJson.class)
    public ResponseJson findTotalMoney(@RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getSchoolProcessId())){
            return new ResponseJson(false,"请正确选择流程类型");
        }
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime ==null && rangeTime.length != 2) {
            return new ResponseJson(false,"查询时间范围格式不正确");
        }
        rangeTime[0] = rangeTime[0] + " 00:00:00";
        rangeTime[1] = rangeTime[1] + " 23:59:59";
        processBusinessData.setRangeTime(rangeTime);
        processBusinessData.setSchoolId(mySchoolId());
        return oaStatsService.findTotalMoney(processBusinessData);
    }
    @GetMapping("/urgeApp/{id}")
    @ApiOperation(value = "21.推送信息通知审批人,传流程实例id", notes = "返回响应对象")
    public ResponseJson pushUrge(
            @ApiParam(value = "流程实例id")
            @PathVariable("id") String id) {
        return   processBusinessDataService.pushUrge(id);
    }
}
