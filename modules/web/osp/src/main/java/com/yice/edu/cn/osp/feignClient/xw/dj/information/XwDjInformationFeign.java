package com.yice.edu.cn.osp.feignClient.xw.dj.information;

import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwDjInformationFeign",path = "/xwDjInformation")
public interface XwDjInformationFeign {
    @GetMapping("/findXwDjInformationById/{id}")
    XwDjInformation findXwDjInformationById(@PathVariable("id") String id);
    @PostMapping("/saveXwDjInformation")
    XwDjInformation saveXwDjInformation(XwDjInformation xwDjInformation);
    @PostMapping("/findXwDjInformationListByCondition")
    List<XwDjInformation> findXwDjInformationListByCondition(XwDjInformation xwDjInformation);
    @PostMapping("/findOneXwDjInformationByCondition")
    XwDjInformation findOneXwDjInformationByCondition(XwDjInformation xwDjInformation);
    @PostMapping("/findXwDjInformationCountByCondition")
    long findXwDjInformationCountByCondition(XwDjInformation xwDjInformation);
    @PostMapping("/updateXwDjInformation")
    void updateXwDjInformation(XwDjInformation xwDjInformation);
    @GetMapping("/deleteXwDjInformation/{id}")
    void deleteXwDjInformation(@PathVariable("id") String id);
    @PostMapping("/deleteXwDjInformationByCondition")
    void deleteXwDjInformationByCondition(XwDjInformation xwDjInformation);
    @GetMapping("/selectXwDjInformationById/{id}")
    XwDjInformation selectXwDjInformationById(@PathVariable("id") String id);

    @PostMapping("/getPageByCondition")
    Page<XwDjInformation> getPageByCondition(XwDjInformation xwDjInformation);

    @PostMapping("/editXwDjInformation")
    void editXwDjInformation(XwDjInformation xwDjInformation);
}
