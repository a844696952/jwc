package com.yice.edu.cn.tap.feignClient.doc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.DocLeader;
import com.yice.edu.cn.common.pojo.xw.document.SendObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="xw",path = "/docLeader")
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
