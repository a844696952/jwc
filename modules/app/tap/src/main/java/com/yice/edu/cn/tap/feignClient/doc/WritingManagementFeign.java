package com.yice.edu.cn.tap.feignClient.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/writingManagement")
public interface WritingManagementFeign {
    @GetMapping("/findWritingManagementById/{id}")
    WritingManagement findWritingManagementById(@PathVariable("id") String id);
    @PostMapping("/saveWritingManagement")
    WritingManagement saveWritingManagement(WritingManagement writingManagement);
    @PostMapping("/findWritingManagementListByCondition")
    List<WritingManagement> findWritingManagementListByCondition(WritingManagement writingManagement);
    @PostMapping("/findOneWritingManagementByCondition")
    WritingManagement findOneWritingManagementByCondition(WritingManagement writingManagement);
    @PostMapping("/findWritingManagementCountByCondition")
    long findWritingManagementCountByCondition(WritingManagement writingManagement);
    @PostMapping("/updateWritingManagement")
    void updateWritingManagement(WritingManagement writingManagement);
    @GetMapping("/deleteWritingManagement/{id}")
    void deleteWritingManagement(@PathVariable("id") String id);
    @PostMapping("/deleteWritingManagementByCondition")
    void deleteWritingManagementByCondition(WritingManagement writingManagement);

    /**
     * 额外的方法
     */

    @PostMapping("/findWritingAndManagementListByCondtion")
    List<Writing> findWritingAndManagementListByCondtion(WritingManagement writingManagement);

    @PostMapping("/lookAndupdateWritingAndWritingManagement")
    Writing lookAndupdateWritingAndWritingManagement(Writing writing);

    @PostMapping("/findWritingAndWritingManagement")
    List<Writing> findWritingAndWritingManagement(Writing writing);

    @PostMapping("/findWritingAndWritingManagementLong")
    long findWritingAndWritingManagementLong(Writing writing);

    @PostMapping("/getDocManagementReadOrUnRead")
    List<Department> getDocManagementReadOrUnRead(WritingManagement writingManagement);
}
