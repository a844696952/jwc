package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsContentFeign",path = "/xwCmsContent")
public interface XwCmsContentFeign {
    @PostMapping("/findXwCmsContentById/{id}")
    XwCmsContent findXwCmsContentById(@PathVariable("id") String id);
    @PostMapping("/saveXwCmsContent")
    void saveXwCmsContent(XwCmsContent xwCmsContent);
    @PostMapping("/findXwCmsContentListByCondition")
    List<XwCmsContent> findXwCmsContentListByCondition(XwCmsContent xwCmsContent);
    @PostMapping("/findOneXwCmsContentByCondition")
    XwCmsContent findOneXwCmsContentByCondition(XwCmsContent xwCmsContent);
    @PostMapping("/findXwCmsContentCountByCondition")
    long findXwCmsContentCountByCondition(XwCmsContent xwCmsContent);
    @PostMapping("/updateXwCmsContent")
    void updateXwCmsContent(XwCmsContent xwCmsContent);
    @GetMapping("/deleteXwCmsContent/{id}")
    void deleteXwCmsContent(@PathVariable("id") String id);
    @PostMapping("/deleteXwCmsContentByCondition")
    void deleteXwCmsContentByCondition(XwCmsContent xwCmsContent);
    @PostMapping("/saveXwCmsContentForLayout")
    Boolean saveXwCmsContentForLayout(XwCmsContent xwCmsContent);
}
