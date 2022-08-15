package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsHeaderNavigationFeign",path = "/xwCmsHeaderNavigation")
public interface XwCmsHeaderNavigationFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findXwCmsHeaderNavigationById/{id}")
    XwCmsHeaderNavigation findXwCmsHeaderNavigationById(@PathVariable("id") String id);
    @PostMapping("/saveXwCmsHeaderNavigation")
    XwCmsHeaderNavigation saveXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @PostMapping("/batchSaveXwCmsHeaderNavigation")
    void batchSaveXwCmsHeaderNavigation(List<XwCmsHeaderNavigation> xwCmsHeaderNavigations);
    @PostMapping("/findXwCmsHeaderNavigationListByCondition")
    List<XwCmsHeaderNavigation> findXwCmsHeaderNavigationListByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @PostMapping("/findOneXwCmsHeaderNavigationByCondition")
    XwCmsHeaderNavigation findOneXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @PostMapping("/findXwCmsHeaderNavigationCountByCondition")
    long findXwCmsHeaderNavigationCountByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @PostMapping("/updateXwCmsHeaderNavigation")
    void updateXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @PostMapping("/updateXwCmsHeaderNavigationForNotNull")
    void updateXwCmsHeaderNavigationForAll(XwCmsHeaderNavigation xwCmsHeaderNavigation);
    @GetMapping("/deleteXwCmsHeaderNavigation/{id}")
    void deleteXwCmsHeaderNavigation(@PathVariable("id") String id);
    @PostMapping("/deleteXwCmsHeaderNavigationByCondition")
    void deleteXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/saveXwCmsHeaderNavigationList")
    List<XwCmsHeaderNavigation> saveXwCmsHeaderNavigationList(List<XwCmsHeaderNavigation> xwCmsHeaderNavigation);

}
