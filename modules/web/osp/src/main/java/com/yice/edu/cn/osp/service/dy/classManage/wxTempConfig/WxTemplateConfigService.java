package com.yice.edu.cn.osp.service.dy.classManage.wxTempConfig;

import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import com.yice.edu.cn.osp.feignClient.dy.classManage.wxTempConfig.WxTemplateConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxTemplateConfigService {
    @Autowired
    private WxTemplateConfigFeign wxTemplateConfigFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public WxTemplateConfig findWxTemplateConfigById(String id) {
        return wxTemplateConfigFeign.findWxTemplateConfigById(id);
    }

    public WxTemplateConfig saveWxTemplateConfig(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigFeign.saveWxTemplateConfig(wxTemplateConfig);
    }

    public void batchSaveWxTemplateConfig(List<WxTemplateConfig> wxTemplateConfigs){
        wxTemplateConfigFeign.batchSaveWxTemplateConfig(wxTemplateConfigs);
    }

    public List<WxTemplateConfig> findWxTemplateConfigListByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigFeign.findWxTemplateConfigListByCondition(wxTemplateConfig);
    }

    public WxTemplateConfig findOneWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigFeign.findOneWxTemplateConfigByCondition(wxTemplateConfig);
    }

    public long findWxTemplateConfigCountByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigFeign.findWxTemplateConfigCountByCondition(wxTemplateConfig);
    }

    public void updateWxTemplateConfig(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigFeign.updateWxTemplateConfig(wxTemplateConfig);
    }

    public void updateWxTemplateConfigForAll(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigFeign.updateWxTemplateConfigForAll(wxTemplateConfig);
    }

    public void deleteWxTemplateConfig(String id) {
        wxTemplateConfigFeign.deleteWxTemplateConfig(id);
    }

    public void deleteWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigFeign.deleteWxTemplateConfigByCondition(wxTemplateConfig);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
