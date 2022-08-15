package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.xw.dao.cms.IXwCmsSchoolSpaceConfigDao;
import com.yice.edu.cn.xw.feignClient.jw.schoolddatecenter.SchoolDateCenterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwCmsSchoolSpaceConfigService {
    @Autowired
    private IXwCmsSchoolSpaceConfigDao xwCmsSchoolSpaceConfigDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SchoolDateCenterFeign schoolDateCenterFeign;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwCmsSchoolSpaceConfig findXwCmsSchoolSpaceConfigById(String id) {
        return xwCmsSchoolSpaceConfigDao.findXwCmsSchoolSpaceConfigById(id);
    }

    @Transactional(readOnly = true)
    public String findSchoolIdBySecondDomain(String secondDomain){
        return xwCmsSchoolSpaceConfigDao.findSchoolIdBySecondDomain(secondDomain);
    }


    @Transactional
    public void saveXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        xwCmsSchoolSpaceConfig.setId(sequenceId.nextId());
        xwCmsSchoolSpaceConfig.setCreateTime(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        xwCmsSchoolSpaceConfigDao.saveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
    }

    @Transactional(readOnly = true)
    public XwCmsSchoolSpaceConfig findSchoolSpaceConfigById(String schoolId){
        return xwCmsSchoolSpaceConfigDao.findSchoolSpaceConfigById(schoolId);
    }

    @Transactional(readOnly = true)
    public List<XwCmsSchoolSpaceConfig> findXwCmsSchoolSpaceConfigListByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigDao.findXwCmsSchoolSpaceConfigListByCondition(xwCmsSchoolSpaceConfig);
    }
    @Transactional(readOnly = true)
    public XwCmsSchoolSpaceConfig findOneXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigDao.findOneXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }
    @Transactional(readOnly = true)
    public long findXwCmsSchoolSpaceConfigCountByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigDao.findXwCmsSchoolSpaceConfigCountByCondition(xwCmsSchoolSpaceConfig);
    }
    @Transactional
    public void updateXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        xwCmsSchoolSpaceConfigDao.updateXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
    }
    @Transactional
    public void deleteXwCmsSchoolSpaceConfig(String id) {
        xwCmsSchoolSpaceConfigDao.deleteXwCmsSchoolSpaceConfig(id);
    }
    @Transactional
    public void deleteXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        xwCmsSchoolSpaceConfigDao.deleteXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }
    @Transactional
    public void batchSaveXwCmsSchoolSpaceConfig(List<XwCmsSchoolSpaceConfig> xwCmsSchoolSpaceConfigs){
        xwCmsSchoolSpaceConfigs.forEach(xwCmsSchoolSpaceConfig -> xwCmsSchoolSpaceConfig.setId(sequenceId.nextId()));
        xwCmsSchoolSpaceConfigDao.batchSaveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfigs);
    }

    @Transactional(readOnly = true)
    public SchoolDateCenter findSchoolCompusCenterBySchoolId(String shcoolId){
        return  schoolDateCenterFeign.findSchoolCompusCenterBySchoolId(shcoolId);
    }

/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
