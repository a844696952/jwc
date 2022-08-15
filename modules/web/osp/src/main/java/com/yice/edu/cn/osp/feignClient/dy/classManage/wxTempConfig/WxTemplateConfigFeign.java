package com.yice.edu.cn.osp.feignClient.dy.classManage.wxTempConfig;

import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",contextId = "wxTemplateConfigFeign",path = "/wxTemplateConfig")
public interface WxTemplateConfigFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findWxTemplateConfigById/{id}")
    WxTemplateConfig findWxTemplateConfigById(@PathVariable("id") String id);
    @PostMapping("/saveWxTemplateConfig")
    WxTemplateConfig saveWxTemplateConfig(WxTemplateConfig wxTemplateConfig);
    @PostMapping("/batchSaveWxTemplateConfig")
    void batchSaveWxTemplateConfig(List<WxTemplateConfig> wxTemplateConfigs);
    @PostMapping("/findWxTemplateConfigListByCondition")
    List<WxTemplateConfig> findWxTemplateConfigListByCondition(WxTemplateConfig wxTemplateConfig);
    @PostMapping("/findOneWxTemplateConfigByCondition")
    WxTemplateConfig findOneWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig);
    @PostMapping("/findWxTemplateConfigCountByCondition")
    long findWxTemplateConfigCountByCondition(WxTemplateConfig wxTemplateConfig);
    @PostMapping("/updateWxTemplateConfig")
    void updateWxTemplateConfig(WxTemplateConfig wxTemplateConfig);
    @PostMapping("/updateWxTemplateConfigForAll")
    void updateWxTemplateConfigForAll(WxTemplateConfig wxTemplateConfig);
    @GetMapping("/deleteWxTemplateConfig/{id}")
    void deleteWxTemplateConfig(@PathVariable("id") String id);
    @PostMapping("/deleteWxTemplateConfigByCondition")
    void deleteWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
