package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jw",contextId = "paperSubjectFeign",path = "/paperSubject")
public interface PaperSubjectFeign {

    @PostMapping("/savaPaperSubject")
    void savaPaperSubject(PaperSubject paperSubject);

    @GetMapping("/findOnePaperSubjectKong/{createUserId}")
    PaperSubject findOnePaperSubjectKong(@PathVariable("createUserId") String createUserId);


}
