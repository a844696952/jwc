package com.yice.edu.cn.tap.feignClient.xw.dj.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(value = "xw",contextId = "XwDjDocManagementFeign",path = "/xwDjDocManagement")
public interface XwDjDocManagementFeign {

    @PostMapping("/findDocListByCondition")
    List<Doc> findDocListByCondition(Doc doc);

    @PostMapping("/findDocManagementCountByCondition")
    long findDocManagementCountByCondition(Doc doc);

    @GetMapping("/fingOneDocUpdateManagement/{docId}/{docObjectId}")
    Doc fingOneDocUpdateManagement(@PathVariable("docId") String docId, @PathVariable("docObjectId") String docObjectId);

    @PostMapping("/findDocManagementReadList")
    List<DocManagement> findDocManagementReadList(DocManagement docManagement);

    @PostMapping("/getDocManagementReadOrUnRead")
    List<Department> getDocManagementReadOrUnRead(DocManagement docManagement);
}
