package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsStyleConfigFeign",path = "/xwCmsStyleConfig")
public interface XwCmsStyleConfigFeign {
    @GetMapping("/findXwCmsStyleConfigById/{id}")
    XwCmsStyleConfig findXwCmsStyleConfigById(@PathVariable("id") String id);

    @PostMapping("/saveXwCmsStyleConfig")
    XwCmsStyleConfig saveXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig);

    @PostMapping("/findXwCmsStyleConfigListByCondition")
    List<XwCmsStyleConfig> findXwCmsStyleConfigListByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    @PostMapping("/findOneXwCmsStyleConfigByCondition")
    XwCmsStyleConfig findOneXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    @PostMapping("/findXwCmsStyleConfigByDomain")
    XwCmsStyleConfig findXwCmsStyleConfigByDomain(XwCmsStyleConfig xwCmsStyleConfig);

    @PostMapping("/findXwCmsStyleConfigCountByCondition")
    long findXwCmsStyleConfigCountByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    @PostMapping("/updateXwCmsStyleConfig")
    void updateXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig);

    @GetMapping("/deleteXwCmsStyleConfig/{id}")
    void deleteXwCmsStyleConfig(@PathVariable("id") String id);

    @PostMapping("/deleteXwCmsStyleConfigByCondition")
    void deleteXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig);
}
