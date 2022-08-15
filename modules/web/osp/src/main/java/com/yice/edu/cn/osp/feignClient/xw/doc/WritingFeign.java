package com.yice.edu.cn.osp.feignClient.xw.doc;

import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "writingFeign",path = "/writing")
public interface WritingFeign {
    @GetMapping("/findWritingById/{id}")
    Writing findWritingById(@PathVariable("id") String id);
    @PostMapping("/saveWriting")
    Writing saveWriting(DocDepartment docDepartment);
    @PostMapping("/findWritingListByCondition")
    List<Writing> findWritingListByCondition(Writing writing);
    @PostMapping("/findOneWritingByCondition")
    Writing findOneWritingByCondition(Writing writing);
    @PostMapping("/findWritingCountByCondition")
    long findWritingCountByCondition(Writing writing);
    @PostMapping("/updateWriting")
    void updateWriting(DocDepartment docDepartment);
    @GetMapping("/deleteWriting/{id}")
    void deleteWriting(@PathVariable("id") String id);
    @PostMapping("/deleteWritingByCondition")
    void deleteWritingByCondition(Writing writing);

    /**
     *
     *额外方法
     *
     */
    @PostMapping("/getdepartmentUpdate")
    Boolean getdepartmentUpdate(DocDepartment docDepartment);
    @GetMapping("/getWritingRejectUpdate/{id}")
    Writing getWritingRejectUpdate(@PathVariable("id") String id);

}
