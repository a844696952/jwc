package com.yice.edu.cn.tap.feignClient.subjectSource;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;

@FeignClient(value="jy",path = "/materialItem")
public interface MaterialItemFeign {
    @GetMapping("/findMaterialItemById/{id}")
    MaterialItem findMaterialItemById(@PathVariable("id") String id);
    @PostMapping("/saveMaterialItem")
    MaterialItem saveMaterialItem(MaterialItem materialItem);
    @PostMapping("/findMaterialItemListByCondition")
    List<MaterialItem> findMaterialItemListByCondition(MaterialItem materialItem);
    @PostMapping("/findOneMaterialItemByCondition")
    MaterialItem findOneMaterialItemByCondition(MaterialItem materialItem);
    @PostMapping("/findMaterialItemCountByCondition")
    long findMaterialItemCountByCondition(MaterialItem materialItem);
    @PostMapping("/updateMaterialItem")
    void updateMaterialItem(MaterialItem materialItem);
    @GetMapping("/deleteMaterialItem/{id}")
    void deleteMaterialItem(@PathVariable("id") String id);
    @PostMapping("/deleteMaterialItemByCondition")
    void deleteMaterialItemByCondition(MaterialItem materialItem);
    @PostMapping("/findKnowledgePointListByItem")
    public List<KnowledgePoint> findKnowledgePointListByItem(MaterialItemKnowledge materialItemKnowledge);
    @PostMapping("/findKnowledgePointCountByItem")
    public long findKnowledgePointCountByItem(MaterialItemKnowledge materialItemKnowledge);
}
