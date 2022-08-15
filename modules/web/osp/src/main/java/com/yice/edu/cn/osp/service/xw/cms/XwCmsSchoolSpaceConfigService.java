package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsSchoolSpaceConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsSchoolSpaceConfigService {
    @Autowired
    private XwCmsSchoolSpaceConfigFeign xwCmsSchoolSpaceConfigFeign;

    public XwCmsSchoolSpaceConfig findXwCmsSchoolSpaceConfigById(String id) {
        return xwCmsSchoolSpaceConfigFeign.findXwCmsSchoolSpaceConfigById(id);
    }

    public XwCmsSchoolSpaceConfig saveXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigFeign.saveXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
    }

    public List<XwCmsSchoolSpaceConfig> findXwCmsSchoolSpaceConfigListByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigFeign.findXwCmsSchoolSpaceConfigListByCondition(xwCmsSchoolSpaceConfig);
    }

    public XwCmsSchoolSpaceConfig findOneXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigFeign.findOneXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }

    public long findXwCmsSchoolSpaceConfigCountByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        return xwCmsSchoolSpaceConfigFeign.findXwCmsSchoolSpaceConfigCountByCondition(xwCmsSchoolSpaceConfig);
    }

    public void updateXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        xwCmsSchoolSpaceConfigFeign.updateXwCmsSchoolSpaceConfig(xwCmsSchoolSpaceConfig);
    }

    public void deleteXwCmsSchoolSpaceConfig(String id) {
        xwCmsSchoolSpaceConfigFeign.deleteXwCmsSchoolSpaceConfig(id);
    }

    public void deleteXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig) {
        xwCmsSchoolSpaceConfigFeign.deleteXwCmsSchoolSpaceConfigByCondition(xwCmsSchoolSpaceConfig);
    }

    public String findSchoolIdBySecondDomain(String secondDomain){
        return xwCmsSchoolSpaceConfigFeign.findSchoolIdBySecondDomain(secondDomain);
    }
}
