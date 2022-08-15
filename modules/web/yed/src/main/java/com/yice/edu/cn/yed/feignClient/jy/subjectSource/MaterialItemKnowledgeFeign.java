package com.yice.edu.cn.yed.feignClient.jy.subjectSource;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;

@FeignClient(value="jy",contextId = "materialItemKnowledgeFeign",path = "/materialItemKnowledge")
public interface MaterialItemKnowledgeFeign {
    @GetMapping("/findMaterialItemKnowledgeById/{id}")
    MaterialItemKnowledge findMaterialItemKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveMaterialItemKnowledge")
    MaterialItemKnowledge saveMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge);
    @PostMapping("/findMaterialItemKnowledgeListByCondition")
    List<MaterialItemKnowledge> findMaterialItemKnowledgeListByCondition(MaterialItemKnowledge materialItemKnowledge);
    @PostMapping("/findOneMaterialItemKnowledgeByCondition")
    MaterialItemKnowledge findOneMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge);
    @PostMapping("/findMaterialItemKnowledgeCountByCondition")
    long findMaterialItemKnowledgeCountByCondition(MaterialItemKnowledge materialItemKnowledge);
    @PostMapping("/updateMaterialItemKnowledge")
    void updateMaterialItemKnowledge(MaterialItemKnowledge materialItemKnowledge);
    @GetMapping("/deleteMaterialItemKnowledge/{id}")
    void deleteMaterialItemKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteMaterialItemKnowledgeByCondition")
    void deleteMaterialItemKnowledgeByCondition(MaterialItemKnowledge materialItemKnowledge);
}
