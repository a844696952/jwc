package com.yice.edu.cn.yed.feignClient.jy.knowledge;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeAndGradeMapVo;

@FeignClient(value="jy",contextId = "jyKnowledgeFeign",path = "/jyKnowledge")
public interface JyKnowledgeFeign {
    @GetMapping("/findJyKnowledgeById/{id}")
    JyKnowledge findJyKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveJyKnowledge")
    JyKnowledge saveJyKnowledge(JyKnowledge jyKnowledge);
    @PostMapping("/findJyKnowledgeListByCondition")
    List<JyKnowledge> findJyKnowledgeListByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/findOneJyKnowledgeByCondition")
    JyKnowledge findOneJyKnowledgeByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/findJyKnowledgeCountByCondition")
    long findJyKnowledgeCountByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/updateJyKnowledge")
    void updateJyKnowledge(JyKnowledge jyKnowledge);
    @GetMapping("/deleteJyKnowledge/{id}")
    void deleteJyKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteJyKnowledgeByCondition")
    void deleteJyKnowledgeByCondition(JyKnowledge jyKnowledge);
    @PostMapping("/deleteLogicById")
    void deleteLogicById(JyKnowledge jyKnowledge);
    @PostMapping("/uploadJyKnowledge")
    void uploadJyKnowledge(UploadKnowledgeAndGradeMapVo vo);
}
