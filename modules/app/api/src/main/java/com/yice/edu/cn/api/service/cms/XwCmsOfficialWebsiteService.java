package com.yice.edu.cn.api.service.cms;

import com.yice.edu.cn.api.feign.cms.SchoolDateCenterFeign;
import com.yice.edu.cn.api.feign.cms.XwCmsOfficialWebsiteFeign;
import com.yice.edu.cn.api.feign.school.SchoolFeign;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XwCmsOfficialWebsiteService {

    @Autowired
    private XwCmsOfficialWebsiteFeign xwCmsOfficialWebsiteFeign;
    @Autowired
    private SchoolDateCenterFeign schoolDateCenterFeign;
    @Autowired
    private SchoolFeign schoolFeign;


    public XwCmsStyleConfig findXwCmsStyleConfigByDomain(XwCmsStyleConfig xwCmsStyleConfig) {
        XwCmsStyleConfig xwCmsStyleConfigByDomain = xwCmsOfficialWebsiteFeign.findXwCmsStyleConfigByDomain(xwCmsStyleConfig.getSchoolId());
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

    public ResponseJson findXwCmsContentsByCondition(XwCmsContent xwCmsContent){
        return xwCmsOfficialWebsiteFeign.findXwCmsContentsByCondition(xwCmsContent);
    }

    public ResponseJson lookXwCmsContentById(String id) {
        return xwCmsOfficialWebsiteFeign.lookXwCmsContentById(id);
    }

    public SchoolDateCenter findSchoolCompusCenterBySchoolId(String schoolId){
       return schoolDateCenterFeign.findSchoolCompusCenterBySchoolId(schoolId);
    }


    public ResponseJson findDomainStatus(String secondDomain){
        return xwCmsOfficialWebsiteFeign.findDomainStatus(secondDomain);
    }


    public ResponseJson findXwCmsColumnsByCondition(XwCmsColumn xwCmsColumn){
        return xwCmsOfficialWebsiteFeign.findXwCmsColumnsByCondition(xwCmsColumn);
    }

    public ResponseJson findXwCmsHeaderNavigationsByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation){
        return xwCmsOfficialWebsiteFeign.findXwCmsHeaderNavigationsByCondition(xwCmsHeaderNavigation);
    }

    public ResponseJson findAllCmsHomeLayout(String schoolId){
        return xwCmsOfficialWebsiteFeign.findAllCmsHomeLayout(schoolId);
    }

    public String findSchoolIdBySecondDomain(String secondDomain){
        return  xwCmsOfficialWebsiteFeign.findSchoolIdBySecondDomain(secondDomain);
    }

}
