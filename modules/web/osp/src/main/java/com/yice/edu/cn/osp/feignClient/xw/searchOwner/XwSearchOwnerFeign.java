package com.yice.edu.cn.osp.feignClient.xw.searchOwner;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwSearchOwnerFeign",path = "/xwSearchOwner")
public interface XwSearchOwnerFeign {
    @GetMapping("/findXwSearchOwnerById/{id}")
    XwSearchOwner findXwSearchOwnerById(@PathVariable("id") String id);
    @PostMapping("/saveXwSearchOwner")
    XwSearchOwner saveXwSearchOwner(XwSearchOwner xwSearchOwner);
    @PostMapping("/findXwSearchOwnerListByCondition")
    List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner);
    @PostMapping("/findOneXwSearchOwnerByCondition")
    XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner);
    @PostMapping("/findXwSearchOwnerCountByCondition")
    long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner);
    @PostMapping("/updateXwSearchOwner")
    void updateXwSearchOwner(XwSearchOwner xwSearchOwner);
    @GetMapping("/deleteXwSearchOwner/{id}")
    void deleteXwSearchOwner(@PathVariable("id") String id);
    @PostMapping("/deleteXwSearchOwnerByCondition")
    void deleteXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner);
}
