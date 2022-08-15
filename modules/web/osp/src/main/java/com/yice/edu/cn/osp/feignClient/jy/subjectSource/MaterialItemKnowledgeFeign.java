package com.yice.edu.cn.osp.feignClient.jy.subjectSource;


import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jy",contextId = "materialItemKnowledgeFeign",path = "/materialItemKnowledge")
public interface MaterialItemKnowledgeFeign {
    @PostMapping("/batchSaveMaterialItemKnowledge")
    void batchSaveMaterialItemKnowledge(@RequestBody List<MaterialItemKnowledge> materialItemKnowledge);
}
