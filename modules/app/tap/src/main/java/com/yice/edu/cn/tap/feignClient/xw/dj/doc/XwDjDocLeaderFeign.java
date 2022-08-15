package com.yice.edu.cn.tap.feignClient.xw.dj.doc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.DocLeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "xw", contextId = "xwDjDocLeaderFeign", path = "/xwDjDocLeader")
public interface XwDjDocLeaderFeign {
    @GetMapping("/findDocLeaderById/{id}")
    DocLeader findDocLeaderById(@PathVariable("id") String id);

    @PostMapping("/saveDocLeader")
    DocLeader saveDocLeader(DocLeader docLeader);

    @PostMapping("/findDocLeaderListByCondition")
    List<DocLeader> findDocLeaderListByCondition(DocLeader docLeader);

    @PostMapping("/findOneDocLeaderByCondition")
    DocLeader findOneDocLeaderByCondition(DocLeader docLeader);

    @PostMapping("/findDocLeaderCountByCondition")
    long findDocLeaderCountByCondition(DocLeader docLeader);

    @PostMapping("/updateDocLeader")
    void updateDocLeader(DocLeader docLeader);

    @GetMapping("/deleteDocLeader/{id}")
    void deleteDocLeader(@PathVariable("id") String id);

    @PostMapping("/deleteDocLeaderByCondition")
    void deleteDocLeaderByCondition(DocLeader docLeader);

    /**
     * 自己增加的方法
     */
    @PostMapping("/findDocAndDocLeaderList")
    List<Doc> findDocAndDocLeaderList(Doc doc);

    @PostMapping("/saveUpdateDocCompletion")
    void saveUpdateDocCompletion(Doc doc);

    @PostMapping("/saveUpdateDocLeaterCompletion")
    void saveUpdateDocLeaterCompletion(Doc doc);

    @PostMapping("/findDocCountByCondition")
    long findDocCountByCondition(Doc doc);

    @PostMapping("/saveDocManagement/{docId}")
    Doc saveDocManagement(@RequestBody DocDepartment docDepartment, @PathVariable("docId") String docId);
}
