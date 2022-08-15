package com.yice.edu.cn.osp.controller.oa.leaveStatis;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.service.oa.processBusinessData.ProcessBusinessDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/leaveStatis")
@Api(value = "/leaveStatis",description = "工资条模块")
public class LeaveStatisController {
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;


    @PostMapping("/calculateLeaveStatis")
    public ResponseJson calculateLeaveStatis(@RequestBody ProcessBusinessData processBusinessData){
        //处理时间 默认加上时分秒
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2) {
            rangeTime[0] = rangeTime[0] + " 00:00:00";
            rangeTime[1] = rangeTime[1] + " 23:59:59";
            processBusinessData.setRangeTime(rangeTime);
        }
        processBusinessData.setSchoolId(mySchoolId());
        return processBusinessDataService.calculateLeaveStatis(processBusinessData);
    }
}
