package com.yice.edu.cn.ecc.feignClient.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "examModeFeign",path = "/examMode")
public interface ExamModeFeign {

    @PostMapping("/findByCExamModeByTime")
    List<ExamMode> findByCExamModeByTime(ExamMode exam);
}
