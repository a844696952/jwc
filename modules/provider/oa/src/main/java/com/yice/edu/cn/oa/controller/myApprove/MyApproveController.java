package com.yice.edu.cn.oa.controller.myApprove;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.oa.service.myApprove.MyApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myApprove")
public class MyApproveController {
    @Autowired
    private MyApproveService myApproveService;



    @PostMapping("/findWaitApproveData/{id}")
    public List<ProcessBusinessData> findWaitApproveData(@PathVariable String id, @RequestBody ProcessBusinessData processBusinessData){
        return myApproveService.findWaitApproveData(id,processBusinessData);
    }
    @PostMapping("/findWaitApproveCount/{id}")
    public long findWaitApproveCount(@PathVariable String id, @RequestBody ProcessBusinessData processBusinessData){
        return myApproveService.findWaitApproveCount(id,processBusinessData);
    }
    @PostMapping("/findHasApproveData/{id}")
    public List<ProcessBusinessData> findHasApproveData(@PathVariable String id, @RequestBody ProcessBusinessData processBusinessData){
        return myApproveService.findHasApproveData(id,processBusinessData);
    }
    @PostMapping("/findHasApproveCount/{id}")
    public long findHasApproveCount(@PathVariable String id, @RequestBody ProcessBusinessData processBusinessData){
        return myApproveService.findHasApproveCount(id,processBusinessData);
    }

    /**
     * 完成审批，包含同意和不同意
     * @param oaPeople
     * @param processBusinessId
     */
    @PostMapping("/completeApprove/{processBusinessId}")
    public ResponseJson completeApprove(@RequestBody OaPeople oaPeople, @PathVariable("processBusinessId") String processBusinessId){
        return myApproveService.completeApprove(oaPeople, processBusinessId);
    }

}
