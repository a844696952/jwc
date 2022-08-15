package com.yice.edu.cn.dy.service.classManage.wxTempConfig;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import com.yice.edu.cn.dy.dao.classManage.wxTempConfig.IWxTemplateConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WxTemplateConfigService {
    @Autowired
    private IWxTemplateConfigDao wxTemplateConfigDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public WxTemplateConfig findWxTemplateConfigById(String id) {
        return wxTemplateConfigDao.findWxTemplateConfigById(id);
    }
    @Transactional
    public void saveWxTemplateConfig(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfig.setId(sequenceId.nextId());
        wxTemplateConfigDao.saveWxTemplateConfig(wxTemplateConfig);
    }
    @Transactional(readOnly = true)
    public List<WxTemplateConfig> findWxTemplateConfigListByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigDao.findWxTemplateConfigListByCondition(wxTemplateConfig);
    }
    @Transactional(readOnly = true)
    public WxTemplateConfig findOneWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigDao.findOneWxTemplateConfigByCondition(wxTemplateConfig);
    }
    @Transactional(readOnly = true)
    public long findWxTemplateConfigCountByCondition(WxTemplateConfig wxTemplateConfig) {
        return wxTemplateConfigDao.findWxTemplateConfigCountByCondition(wxTemplateConfig);
    }
    @Transactional
    public void updateWxTemplateConfig(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigDao.updateWxTemplateConfig(wxTemplateConfig);
    }
    @Transactional
    public void updateWxTemplateConfigForAll(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigDao.updateWxTemplateConfigForAll(wxTemplateConfig);
    }
    @Transactional
    public void deleteWxTemplateConfig(String id) {
        wxTemplateConfigDao.deleteWxTemplateConfig(id);
    }
    @Transactional
    public void deleteWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig) {
        wxTemplateConfigDao.deleteWxTemplateConfigByCondition(wxTemplateConfig);
    }
    @Transactional
    public void batchSaveWxTemplateConfig(List<WxTemplateConfig> wxTemplateConfigs){
        wxTemplateConfigs.forEach(wxTemplateConfig -> wxTemplateConfig.setId(sequenceId.nextId()));
        wxTemplateConfigDao.batchSaveWxTemplateConfig(wxTemplateConfigs);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
