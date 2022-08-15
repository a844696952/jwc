package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import com.yice.edu.cn.xw.dao.cms.IXwCmsStyleConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class XwCmsStyleConfigService {
    @Autowired
    private IXwCmsStyleConfigDao xwCmsStyleConfigDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private XwCmsHomeLayoutService xwCmsHomeLayoutService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwCmsStyleConfig findXwCmsStyleConfigById(String id) {
        return xwCmsStyleConfigDao.findXwCmsStyleConfigById(id);
    }

    @Transactional
    public void saveXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig) {
        if  (xwCmsStyleConfigDao.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig)!=null){
            return;
        }
        xwCmsStyleConfig.setId(sequenceId.nextId());
        xwCmsStyleConfig.setSkinId("1");
        xwCmsStyleConfig.setLayoutMode(1);
        xwCmsStyleConfig.setIsShowLogo(1);
        xwCmsStyleConfig.setIsShowSchoolName(1);
        xwCmsStyleConfig.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        xwCmsStyleConfigDao.saveXwCmsStyleConfig(xwCmsStyleConfig);
        xwCmsHomeLayoutService.initCmsHomeLayout(xwCmsStyleConfig.getSchoolId());
    }

    @Transactional
    public List<XwCmsStyleConfig> findXwCmsStyleConfigListByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        List<XwCmsStyleConfig> xwCmsStyleConfigListByCondition = xwCmsStyleConfigDao.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
        if (CollectionUtil.isEmpty(xwCmsStyleConfigListByCondition)){
            saveXwCmsStyleConfig(xwCmsStyleConfig);
            xwCmsStyleConfigListByCondition = xwCmsStyleConfigDao.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
        }
        return xwCmsStyleConfigListByCondition;
    }

    @Transactional(readOnly = true)
    public XwCmsStyleConfig findOneXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigDao.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    @Transactional(readOnly = true)
    public XwCmsStyleConfig findXwCmsStyleConfigByDomain(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigDao.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    @Transactional(readOnly = true)
    public long findXwCmsStyleConfigCountByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigDao.findXwCmsStyleConfigCountByCondition(xwCmsStyleConfig);
    }

    @Transactional
    public void updateXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigDao.updateXwCmsStyleConfig(xwCmsStyleConfig);
    }

    @Transactional
    public void deleteXwCmsStyleConfig(String id) {
        xwCmsStyleConfigDao.deleteXwCmsStyleConfig(id);
    }

    @Transactional
    public void deleteXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigDao.deleteXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    @Transactional
    public void batchSaveXwCmsStyleConfig(List<XwCmsStyleConfig> xwCmsStyleConfigs) {
        xwCmsStyleConfigs.forEach(xwCmsStyleConfig -> xwCmsStyleConfig.setId(sequenceId.nextId()));
        xwCmsStyleConfigDao.batchSaveXwCmsStyleConfig(xwCmsStyleConfigs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
