package com.yice.edu.cn.osp.service.xw.xwClassifiedManagement;

import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.osp.feignClient.xw.xwClassifiedManagement.XwClassifiedManagementFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwClassifiedManagementService {
    @Autowired
    private XwClassifiedManagementFeign xwClassifiedManagementFeign;

    public XwClassifiedManagement findXwClassifiedManagementById(String id) {
        return xwClassifiedManagementFeign.findXwClassifiedManagementById(id);
    }

    public XwClassifiedManagement saveXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.saveXwClassifiedManagement(xwClassifiedManagement);
    }

    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.findXwClassifiedManagementListByCondition(xwClassifiedManagement);
    }

    public XwClassifiedManagement findOneXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.findOneXwClassifiedManagementByCondition(xwClassifiedManagement);
    }

    public long findXwClassifiedManagementCountByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.findXwClassifiedManagementCountByCondition(xwClassifiedManagement);
    }

    public void updateXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement) {
        xwClassifiedManagementFeign.updateXwClassifiedManagement(xwClassifiedManagement);
    }

    public void deleteXwClassifiedManagement(String id) {
        xwClassifiedManagementFeign.deleteXwClassifiedManagement(id);
    }

    public void deleteXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement) {
        xwClassifiedManagementFeign.deleteXwClassifiedManagementByCondition(xwClassifiedManagement);
    }


    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition2(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
    }
    public long findXwClassifiedManagementCountByCondition2(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementFeign.findXwClassifiedManagementCountByCondition2(xwClassifiedManagement);
    }


}
