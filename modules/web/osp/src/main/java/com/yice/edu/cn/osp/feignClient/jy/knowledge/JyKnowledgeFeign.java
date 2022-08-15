package com.yice.edu.cn.osp.feignClient.jy.knowledge;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;

@FeignClient(value="jy",contextId = "jyKnowledgeFeign",path = "/jyKnowledge")
public interface JyKnowledgeFeign {
    @GetMapping("/findJyKnowledgeById/{id}")
    JyKnowledge findJyKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/findJyKnowledgeListByCondition")
    List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/findOneJyKnowledgeByCondition")
    JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/findJyKnowledgeCountByCondition")
    long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/uploadJyKnowledge")
    void uploadJyKnowledge(Map<String,Object> map);
    
}
