package com.yice.edu.cn.osp.service.xw.dj.information;


import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import com.yice.edu.cn.osp.feignClient.xw.dj.information.XwDjInformationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjInformationService {
    @Autowired
    private XwDjInformationFeign xwDjInformationFeign;

    public XwDjInformation findXwDjInformationById(String id) {
        return xwDjInformationFeign.findXwDjInformationById(id);
    }

    public XwDjInformation saveXwDjInformation(XwDjInformation xwDjInformation) {
        return xwDjInformationFeign.saveXwDjInformation(xwDjInformation);
    }

    public List<XwDjInformation> findXwDjInformationListByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationFeign.findXwDjInformationListByCondition(xwDjInformation);
    }

    public XwDjInformation findOneXwDjInformationByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationFeign.findOneXwDjInformationByCondition(xwDjInformation);
    }

    public long findXwDjInformationCountByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationFeign.findXwDjInformationCountByCondition(xwDjInformation);
    }

    public void updateXwDjInformation(XwDjInformation xwDjInformation) {
        xwDjInformationFeign.updateXwDjInformation(xwDjInformation);
    }

    public void deleteXwDjInformation(String id) {
        xwDjInformationFeign.deleteXwDjInformation(id);
    }

    public void deleteXwDjInformationByCondition(XwDjInformation xwDjInformation) {
        xwDjInformationFeign.deleteXwDjInformationByCondition(xwDjInformation);
    }

    public XwDjInformation selectXwDjInformationById(String id) {
        return xwDjInformationFeign.selectXwDjInformationById(id);
    }

    public Page<XwDjInformation> getPageByCondition(XwDjInformation xwDjInformation) {
        return xwDjInformationFeign.getPageByCondition(xwDjInformation);
    }

    public void editXwDjInformation(XwDjInformation xwDjInformation) {
        xwDjInformationFeign.editXwDjInformation(xwDjInformation);
    }
}
