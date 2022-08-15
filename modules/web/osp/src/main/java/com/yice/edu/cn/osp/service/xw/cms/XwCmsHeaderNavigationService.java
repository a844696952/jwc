package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsHeaderNavigationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsHeaderNavigationService {
    @Autowired
    private XwCmsHeaderNavigationFeign xwCmsHeaderNavigationFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public XwCmsHeaderNavigation findXwCmsHeaderNavigationById(String id) {
        return xwCmsHeaderNavigationFeign.findXwCmsHeaderNavigationById(id);
    }

    public XwCmsHeaderNavigation saveXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationFeign.saveXwCmsHeaderNavigation(xwCmsHeaderNavigation);
    }

    public void batchSaveXwCmsHeaderNavigation(List<XwCmsHeaderNavigation> xwCmsHeaderNavigations){
        xwCmsHeaderNavigationFeign.batchSaveXwCmsHeaderNavigation(xwCmsHeaderNavigations);
    }

    public List<XwCmsHeaderNavigation> findXwCmsHeaderNavigationListByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationFeign.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
    }

    public XwCmsHeaderNavigation findOneXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationFeign.findOneXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }

    public long findXwCmsHeaderNavigationCountByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationFeign.findXwCmsHeaderNavigationCountByCondition(xwCmsHeaderNavigation);
    }

    public void updateXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationFeign.updateXwCmsHeaderNavigation(xwCmsHeaderNavigation);
    }

    public void updateXwCmsHeaderNavigationForAll(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationFeign.updateXwCmsHeaderNavigationForAll(xwCmsHeaderNavigation);
    }

    public void deleteXwCmsHeaderNavigation(String id) {
        xwCmsHeaderNavigationFeign.deleteXwCmsHeaderNavigation(id);
    }

    public void deleteXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationFeign.deleteXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<XwCmsHeaderNavigation> saveXwCmsHeaderNavigationList(List<XwCmsHeaderNavigation> xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationFeign.saveXwCmsHeaderNavigationList(xwCmsHeaderNavigation);
    }



}
