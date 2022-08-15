package com.yice.edu.cn.osp.feignClient.xw.doc;

import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "writingLeaderFeign",path = "/writingLeader")
public interface WritingLeaderFeign {
    @GetMapping("/findWritingLeaderById/{id}")
    WritingLeader findWritingLeaderById(@PathVariable("id") String id);
    @PostMapping("/saveWritingLeader")
    WritingLeader saveWritingLeader(WritingLeader writingLeader);
    @PostMapping("/findWritingLeaderListByCondition")
    List<WritingLeader> findWritingLeaderListByCondition(WritingLeader writingLeader);
    @PostMapping("/findOneWritingLeaderByCondition")
    WritingLeader findOneWritingLeaderByCondition(WritingLeader writingLeader);
    @PostMapping("/findWritingLeaderCountByCondition")
    long findWritingLeaderCountByCondition(WritingLeader writingLeader);
    @PostMapping("/updateWritingLeader")
    void updateWritingLeader(WritingLeader writingLeader);
    @GetMapping("/deleteWritingLeader/{id}")
    void deleteWritingLeader(@PathVariable("id") String id);
    @PostMapping("/deleteWritingLeaderByCondition")
    void deleteWritingLeaderByCondition(WritingLeader writingLeader);

    /**
     * 额外方法
     */
    @PostMapping("/updateWriting")
    void updateWriting(Writing writing);

    @PostMapping("/updateWritingAndLeader")
    void  updateWritingAndLeader(Writing writing);
}
