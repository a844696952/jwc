package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsStyleConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsStyleConfigService {
    @Autowired
    private XwCmsStyleConfigFeign xwCmsStyleConfigFeign;
    @Autowired
    private SchoolFeign schoolFeign;

    public XwCmsStyleConfig findXwCmsStyleConfigById(String id) {
        return xwCmsStyleConfigFeign.findXwCmsStyleConfigById(id);
    }

    public XwCmsStyleConfig saveXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigFeign.saveXwCmsStyleConfig(xwCmsStyleConfig);
    }

    public List<XwCmsStyleConfig> findXwCmsStyleConfigListByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigFeign.findXwCmsStyleConfigListByCondition(xwCmsStyleConfig);
    }

    public XwCmsStyleConfig findOneXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigFeign.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }

    public XwCmsStyleConfig findXwCmsStyleConfigByDomain(XwCmsStyleConfig xwCmsStyleConfig) {
        XwCmsStyleConfig xwCmsStyleConfigByDomain = xwCmsStyleConfigFeign.findXwCmsStyleConfigByDomain(xwCmsStyleConfig);
        if (xwCmsStyleConfigByDomain==null || (xwCmsStyleConfigByDomain.getIsShowLogo()==0 && xwCmsStyleConfigByDomain.getIsShowSchoolName()==0)){
            return xwCmsStyleConfigByDomain;
        }
        School schoolById = schoolFeign.findSchoolById(xwCmsStyleConfig.getSchoolId());
        if (xwCmsStyleConfigByDomain.getIsShowLogo()==1){
            xwCmsStyleConfigByDomain.setSchoolBadge(schoolById.getSchoolBadge());
        }
        if (xwCmsStyleConfigByDomain.getIsShowSchoolName()==1){
            xwCmsStyleConfigByDomain.setName(schoolById.getName());
        }
        return xwCmsStyleConfigByDomain;
    }

    public long findXwCmsStyleConfigCountByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        return xwCmsStyleConfigFeign.findXwCmsStyleConfigCountByCondition(xwCmsStyleConfig);
    }

    public void updateXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigFeign.updateXwCmsStyleConfig(xwCmsStyleConfig);
    }

    public void deleteXwCmsStyleConfig(String id) {
        xwCmsStyleConfigFeign.deleteXwCmsStyleConfig(id);
    }

    public void deleteXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig) {
        xwCmsStyleConfigFeign.deleteXwCmsStyleConfigByCondition(xwCmsStyleConfig);
    }
}
