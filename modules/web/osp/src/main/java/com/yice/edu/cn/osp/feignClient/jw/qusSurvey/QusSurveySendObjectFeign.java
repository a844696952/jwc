package com.yice.edu.cn.osp.feignClient.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySendObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveySendObjectFeign",path = "/qusSurveySendObject")
public interface QusSurveySendObjectFeign {
    @GetMapping("/findQusSurveySendObjectById/{id}")
    QusSurveySendObject findQusSurveySendObjectById(@PathVariable("id") String id);
    @PostMapping("/saveQusSurveySendObject")
    QusSurveySendObject saveQusSurveySendObject(QusSurveySendObject qusSurveySendObject);
    @PostMapping("/findQusSurveySendObjectListByCondition")
    List<QusSurveySendObject> findQusSurveySendObjectListByCondition(QusSurveySendObject qusSurveySendObject);
    @PostMapping("/findOneQusSurveySendObjectByCondition")
    QusSurveySendObject findOneQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject);
    @PostMapping("/findQusSurveySendObjectCountByCondition")
    long findQusSurveySendObjectCountByCondition(QusSurveySendObject qusSurveySendObject);
    @PostMapping("/updateQusSurveySendObject")
    void updateQusSurveySendObject(QusSurveySendObject qusSurveySendObject);
    @GetMapping("/deleteQusSurveySendObject/{id}")
    void deleteQusSurveySendObject(@PathVariable("id") String id);
    @PostMapping("/deleteQusSurveySendObjectByCondition")
    void deleteQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject);
}
