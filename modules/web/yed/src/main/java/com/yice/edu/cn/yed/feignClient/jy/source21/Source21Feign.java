package com.yice.edu.cn.yed.feignClient.jy.source21;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="jy",contextId = "Source21Feign",path = "/source21")
public interface Source21Feign {
    @GetMapping("/getCategorys/{bookId}")
    Integer getCategorys(@PathVariable("bookId") String bookId);
    @GetMapping("/downKnowledgeByStage/{stage}")
    void downKnowledgeByStage(@PathVariable("stage")String stage);
    @GetMapping("/synchronizeType")
    void synchronizeType();
}
