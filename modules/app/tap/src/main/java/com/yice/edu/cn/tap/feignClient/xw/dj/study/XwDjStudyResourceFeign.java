package com.yice.edu.cn.tap.feignClient.xw.dj.study;

import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwDjStudyResourceFeign",path = "/xwDjStudyResource")
public interface XwDjStudyResourceFeign {
    @GetMapping("/findXwDjStudyResourceById/{id}")
    XwDjStudyResource findXwDjStudyResourceById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjStudyResource")
    XwDjStudyResource saveXwDjStudyResource(XwDjStudyResource xwDjStudyResource);
    @PostMapping("/findXwDjStudyResourceListByCondition")
    List<XwDjStudyResource> findXwDjStudyResourceListByCondition(XwDjStudyResource xwDjStudyResource);
    @PostMapping("/findOneXwDjStudyResourceByCondition")
    XwDjStudyResource findOneXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource);
    @PostMapping("/findXwDjStudyResourceCountByCondition")
    long findXwDjStudyResourceCountByCondition(XwDjStudyResource xwDjStudyResource);
    @PostMapping("/updateXwDjStudyResource")
    void updateXwDjStudyResource(XwDjStudyResource xwDjStudyResource);
    @GetMapping("/deleteXwDjStudyResource/{id}")
    void deleteXwDjStudyResource(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjStudyResourceByCondition")
    void deleteXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource);
}
