package com.yice.edu.cn.osp.feignClient.xw.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw",contextId = "docManagementFeign",path = "/docManagement")
public interface DocManagementFeign {

    @PostMapping("/findDocListByCondition")
    List<Doc> findDocListByCondition(Doc doc);

    @PostMapping("/findDocManagementCountByCondition")
    long findDocManagementCountByCondition(Doc doc);

    @GetMapping("/fingOneDocUpdateManagement/{docId}/{docObjectId}")
    Doc fingOneDocUpdateManagement(@PathVariable("docId") String docId,@PathVariable("docObjectId") String docObjectId);

    @PostMapping("/findDocManagementReadList")
    List<DocManagement> findDocManagementReadList(DocManagement docManagement);

    @PostMapping("/getDocManagementReadOrUnRead")
    List<Department> getDocManagementReadOrUnRead(DocManagement docManagement);

}
