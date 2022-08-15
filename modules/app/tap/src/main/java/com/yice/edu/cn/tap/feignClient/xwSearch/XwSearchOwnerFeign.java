package com.yice.edu.cn.tap.feignClient.xwSearch;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/xwSearchOwner")
public interface XwSearchOwnerFeign {
    @GetMapping("/findXwSearchOwnerById/{id}")
    XwSearchOwner findXwSearchOwnerById(@PathVariable("id") String id);
    @PostMapping("/findXwSearchOwnerListByCondition")
    List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner);
    @PostMapping("/findOneXwSearchOwnerByCondition")
    XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner);
    @PostMapping("/findXwSearchOwnerCountByCondition")
    long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner);

}
