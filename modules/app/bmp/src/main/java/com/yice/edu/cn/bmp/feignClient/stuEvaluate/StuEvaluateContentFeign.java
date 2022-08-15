package com.yice.edu.cn.bmp.feignClient.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "stuEvaluateContentFeign",path = "/stuEvaluateContent")
public interface StuEvaluateContentFeign {
    @PostMapping("/findStuEvaluateContentListByCondition")
    List<StuEvaluateContent> findStuEvaluateContentListByCondition(StuEvaluateContent stuEvaluateContent);
    @PostMapping("/findStuEvaluateContentCountByCondition")
   long findStuEvaluateContentCountByCondition(StuEvaluateContent stuEvaluateContent);
}
