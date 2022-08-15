package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.myPaper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jw",contextId = "myPaperFeign",path = "/myPaper")
public interface MyPaperFeign {

    @PostMapping("/findOneTestPaperByCondition")
    Paper findOneTestPaperByCondition(Paper paper);

    @PostMapping("/updatePaperQuestion")
    Paper  updatePaperQuestion(Paper paper);

    @GetMapping("deletePaper/{id}")
    long deletePaper(@PathVariable("id") String id);

    @PostMapping("/findOnePaper")
    Paper findOnePaper(Paper paper);


    @PostMapping("/paperClone")
    Paper paperClone(Paper paper);

    @PostMapping("/coveringPaper")
    Paper coveringPaper(Paper paper);

}
