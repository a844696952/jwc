package com.yice.edu.cn.ewb.feignClient.subjectSource;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "knowledgePointFeign",path = "/knowledgePoint")
public interface KnowledgePointFeign {
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
    KnowledgePoint updateKnowledgePoint(KnowledgePoint knowledgePoint);
    @GetMapping("/deleteKnowledgePoint/{id}")
    void deleteKnowledgePoint(@PathVariable("id") String id);
    @PostMapping("/deleteKnowledgePointByCondition")
    void deleteKnowledgePointByCondition(KnowledgePoint knowledgePoint);
}
