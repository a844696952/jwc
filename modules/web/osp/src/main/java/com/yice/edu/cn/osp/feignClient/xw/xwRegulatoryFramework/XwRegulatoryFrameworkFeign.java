package com.yice.edu.cn.osp.feignClient.xw.xwRegulatoryFramework;

import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwRegulatoryFrameworkFeign",path = "/xwRegulatoryFramework")
public interface XwRegulatoryFrameworkFeign {
    @GetMapping("/findXwRegulatoryFrameworkById/{id}")
    XwRegulatoryFramework findXwRegulatoryFrameworkById(@PathVariable("id") String id);
    @PostMapping("/saveXwRegulatoryFramework")
    XwRegulatoryFramework saveXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework);
    @PostMapping("/findXwRegulatoryFrameworkListByCondition")
    List<XwRegulatoryFramework> findXwRegulatoryFrameworkListByCondition(XwRegulatoryFramework xwRegulatoryFramework);
    @PostMapping("/findOneXwRegulatoryFrameworkByCondition")
    XwRegulatoryFramework findOneXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework);
    @PostMapping("/findXwRegulatoryFrameworkCountByCondition")
    long findXwRegulatoryFrameworkCountByCondition(XwRegulatoryFramework xwRegulatoryFramework);
    @PostMapping("/updateXwRegulatoryFramework")
    void updateXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework);
    @GetMapping("/deleteXwRegulatoryFramework/{id}")
    void deleteXwRegulatoryFramework(@PathVariable("id") String id);
    @PostMapping("/deleteXwRegulatoryFrameworkByCondition")
    void deleteXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework);
}
