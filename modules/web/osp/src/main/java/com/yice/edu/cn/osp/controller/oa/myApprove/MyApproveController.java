package com.yice.edu.cn.osp.controller.oa.myApprove;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.service.oa.myApprove.MyApproveService;
import com.yice.edu.cn.osp.service.oa.processBusinessData.ProcessBusinessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

/**
 * 我的审批
 */
@RestController
@RequestMapping("/myApprove")
public class MyApproveController {
    @Autowired
    private MyApproveService myApproveService;
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;
    @PostMapping("/find/findWaitApprove")
    public ResponseJson findWaitApprove(@RequestBody
                                        @Validated
                                        ProcessBusinessData processBusinessData){
        //处理时间 默认加上时分秒
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2) {
            rangeTime[0] = rangeTime[0] + " 00:00:00";
            rangeTime[1] = rangeTime[1] + " 23:59:59";
            processBusinessData.setRangeTime(rangeTime);
        }
        List<ProcessBusinessData> processBusinessDatas= myApproveService.findWaitApprove(myId(),processBusinessData);
        long count=myApproveService.findWaitApproveCount(myId(),processBusinessData);
        return new ResponseJson(processBusinessDatas,count);
    }
    @PostMapping("/find/findHasApprove")
    public ResponseJson findHasApprove(@RequestBody
                                        @Validated
                                        ProcessBusinessData processBusinessData){
        //处理时间 默认加上时分秒
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2) {
            rangeTime[0] = rangeTime[0] + " 00:00:00";
            rangeTime[1] = rangeTime[1] + " 23:59:59";
            processBusinessData.setRangeTime(rangeTime);
        }
        List<ProcessBusinessData> processBusinessDatas= myApproveService.findHasApproveData(myId(),processBusinessData);
        long count=myApproveService.findHasApproveCount(myId(),processBusinessData);
        return new ResponseJson(processBusinessDatas,count);
    }

    @GetMapping("/look/findProcessBusinessDataById/{id}")
    public ResponseJson findProcessBusinessDataById(@PathVariable String id){
        return new ResponseJson(processBusinessDataService.findProcessBusinessDataById(id));
    }

    //完成审批
    @PostMapping("/approve/completeApprove/{processBusinessId}")
    public ResponseJson completeApprove(@RequestBody OaPeople oaPeople,@PathVariable String processBusinessId){
        oaPeople.setId(myId());
        oaPeople.setName(currentTeacher().getName());
        return myApproveService.completeApprove(oaPeople, processBusinessId);

    }
}
