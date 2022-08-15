package com.yice.edu.cn.osp.controller.oa.schoolProcessApply;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.oa.processSort.ProcessSortService;
import com.yice.edu.cn.osp.service.oa.schoolProcess.SchoolProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolProcessApply")
public class SchoolProcessApplyController {
    @Autowired
    private SchoolProcessService schoolProcessService;
    @Autowired
    private ProcessSortService processSortService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @GetMapping("/listSchoolProcess")
    public ResponseJson listSchoolProcess(){
        List<SchoolProcess> schoolProcesses=schoolProcessService.listValidSchoolProcessBySchoolId(mySchoolId());
        List<ProcessSort> groups = processSortService.getProcessSortList(mySchoolId());
        List<Map<String,Object>> res = new ArrayList<>();//封装流程分组
        groups.forEach(g ->{
            List<SchoolProcess>  temp = schoolProcesses.stream().filter(f-> StrUtil.isNotEmpty(f.getGroupId()) ).filter(f ->
                        f.getGroupId().equals(g.getId())).filter(f -> Objects.nonNull(f)).collect(Collectors.toList());
            if(temp.size() > 0){
                Map<String,Object> map = new HashMap<>();
                map.put("groupName",g.getSortName());
                map.put("groupId",g.getId());
                map.put("groupFlows",temp);
                res.add(map);
            }
        });
        List<SchoolProcess>  def= schoolProcesses.stream().filter(f -> StrUtil.isEmpty(f.getGroupId())).collect(Collectors.toList());
        if(def.size() != 0){
            Map<String,Object> map = new HashMap<>();
            map.put("groupName","系统分类");
            map.put("groupId",-1);
            map.put("groupFlows",def);
            res.add(map);
        }
        return new ResponseJson(res);
    }
    @GetMapping("/startProcess/findSchoolProcessById/{schoolProcessId}")
    public ResponseJson getProcessForm(@PathVariable String schoolProcessId){
        SchoolProcess schoolProcess = schoolProcessService.findSchoolProcessByIdForStart(schoolProcessId,myId());
        if(Objects.isNull(schoolProcess)){
            return new ResponseJson(false,"当前学校流程不存在或者已删除");
        }
        return new ResponseJson(schoolProcess);
    }

    /**
     * 启动流程,作废
     * @param formData
     * @param schoolProcessId
     * @return
     */
    @PostMapping("/startProcess/start/{schoolProcessId}")
    @Deprecated
    public ResponseJson start(@RequestBody Map<String,Object> formData,@PathVariable String schoolProcessId){
        ResponseJson responseJson=schoolProcessService.startProcess(formData,schoolProcessId,myId());
        return responseJson;
    }
    //启动流程
    @PostMapping("/startProcess/start/v1")
    public ResponseJson start(@RequestBody ProcessBusinessData processBusinessData){
        processBusinessData.setInitiator(currentTeacher().getName());
        processBusinessData.setInitiatorId(myId());
        processBusinessData.setImgUrl(currentTeacher().getImgUrl());
        processBusinessData.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearTerm(processBusinessData,mySchoolId());
        return schoolProcessService.startProcess(processBusinessData);
    }
    @PostMapping("/ignore/findFlowConditionByFormData")
    public ResponseJson findFlowConditionByFormData(@RequestBody ProcessBusinessData processBusinessData){
        processBusinessData.setInitiator(currentTeacher().getName());
        processBusinessData.setInitiatorId(myId());
        processBusinessData.setImgUrl(currentTeacher().getImgUrl());
        processBusinessData.setSchoolId(mySchoolId());
        return schoolProcessService.findFlowConditionByFormData(processBusinessData);
    }

}
