package com.yice.edu.cn.osp.feignClient.xw.xwClassifiedManagement;

import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwClassifiedManagementFeign",path = "/xwClassifiedManagement")
public interface XwClassifiedManagementFeign {
    @GetMapping("/findXwClassifiedManagementById/{id}")
    XwClassifiedManagement findXwClassifiedManagementById(@PathVariable("id") String id);
    @PostMapping("/saveXwClassifiedManagement")
    XwClassifiedManagement saveXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement);
    @PostMapping("/findXwClassifiedManagementListByCondition")
    List<XwClassifiedManagement> findXwClassifiedManagementListByCondition(XwClassifiedManagement xwClassifiedManagement);
    @PostMapping("/findOneXwClassifiedManagementByCondition")
    XwClassifiedManagement findOneXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement);
    @PostMapping("/findXwClassifiedManagementCountByCondition")
    long findXwClassifiedManagementCountByCondition(XwClassifiedManagement xwClassifiedManagement);
    @PostMapping("/updateXwClassifiedManagement")
    void updateXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement);
    @GetMapping("/deleteXwClassifiedManagement/{id}")
    void deleteXwClassifiedManagement(@PathVariable("id") String id);
    @PostMapping("/deleteXwClassifiedManagementByCondition")
    void deleteXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement);



    @PostMapping("/findXwClassifiedManagementListByCondition2")
    List<XwClassifiedManagement> findXwClassifiedManagementListByCondition2(XwClassifiedManagement xwClassifiedManagement);
    @PostMapping("/findXwClassifiedManagementCountByCondition2")
    long findXwClassifiedManagementCountByCondition2(XwClassifiedManagement xwClassifiedManagement);


}
