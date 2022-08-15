package com.yice.edu.cn.yed.feignClient.jy.knowledgePoint;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jy",contextId = "knowledgePointFeign",path = "/knowledgePoint")
public interface KnowledgePointFeign {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findKnowledgePointById/{id}")
    KnowledgePoint findKnowledgePointById(@PathVariable("id") String id);
    @PostMapping("/saveKnowledgePoint")
    KnowledgePoint saveKnowledgePoint(KnowledgePoint knowledgePoint);
    @PostMapping("/findKnowledgePointListByCondition")
    List<KnowledgePoint> findKnowledgePointListByCondition(KnowledgePoint knowledgePoint);
    @PostMapping("/findOneKnowledgePointByCondition")
    KnowledgePoint findOneKnowledgePointByCondition(KnowledgePoint knowledgePoint);
    @PostMapping("/findKnowledgePointCountByCondition")
    long findKnowledgePointCountByCondition(KnowledgePoint knowledgePoint);
    @PostMapping("/updateKnowledgePoint")
    void updateKnowledgePoint(KnowledgePoint knowledgePoint);
    @PostMapping("/updateKnowledgePointForNotNull")
    void updateKnowledgePointForAll(KnowledgePoint knowledgePoint);
    @GetMapping("/deleteKnowledgePoint/{id}")
    void deleteKnowledgePoint(@PathVariable("id") String id);
    @PostMapping("/deleteKnowledgePointByCondition")
    void deleteKnowledgePointByCondition(KnowledgePoint knowledgePoint);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/uploadKnowledgePoint")
    Map<String,Object> uploadKnowledgePoint(@RequestBody List<KnowledgePoint> knowledgePoints);
}
