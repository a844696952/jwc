package com.yice.edu.cn.osp.feignClient.xw.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "docLeaderFeign",path = "/docLeader")
public interface DocLeaderFeign {

    /**
     * 自己增加的方法
     */
    @PostMapping("/findDocAndDocLeaderList")
    List<Doc> findDocAndDocLeaderList(Doc doc);

    @PostMapping("/saveUpdateDocCompletion")
    void  saveUpdateDocCompletion(Doc doc);

    @PostMapping("/saveUpdateDocLeaterCompletion")
    void  saveUpdateDocLeaterCompletion(Doc doc);

    @PostMapping("/findDocCountByCondition")
    long findDocCountByCondition(Doc doc);

    @PostMapping("/saveDocManagement/{docId}")
    Doc saveDocManagement(@RequestBody DocDepartment docDepartment, @PathVariable("docId") String docId);

}
