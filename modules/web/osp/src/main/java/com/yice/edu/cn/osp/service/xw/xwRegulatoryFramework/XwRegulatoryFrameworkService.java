package com.yice.edu.cn.osp.service.xw.xwRegulatoryFramework;

import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.osp.feignClient.xw.xwRegulatoryFramework.XwRegulatoryFrameworkFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwRegulatoryFrameworkService {
    @Autowired
    private XwRegulatoryFrameworkFeign xwRegulatoryFrameworkFeign;

    public XwRegulatoryFramework findXwRegulatoryFrameworkById(String id) {
        return xwRegulatoryFrameworkFeign.findXwRegulatoryFrameworkById(id);
    }

    public XwRegulatoryFramework saveXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkFeign.saveXwRegulatoryFramework(xwRegulatoryFramework);
    }

    public List<XwRegulatoryFramework> findXwRegulatoryFrameworkListByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkFeign.findXwRegulatoryFrameworkListByCondition(xwRegulatoryFramework);
    }

    public XwRegulatoryFramework findOneXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkFeign.findOneXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }

    public long findXwRegulatoryFrameworkCountByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        return xwRegulatoryFrameworkFeign.findXwRegulatoryFrameworkCountByCondition(xwRegulatoryFramework);
    }

    public void updateXwRegulatoryFramework(XwRegulatoryFramework xwRegulatoryFramework) {
        xwRegulatoryFrameworkFeign.updateXwRegulatoryFramework(xwRegulatoryFramework);
    }

    public void deleteXwRegulatoryFramework(String id) {
        xwRegulatoryFrameworkFeign.deleteXwRegulatoryFramework(id);
    }

    public void deleteXwRegulatoryFrameworkByCondition(XwRegulatoryFramework xwRegulatoryFramework) {
        xwRegulatoryFrameworkFeign.deleteXwRegulatoryFrameworkByCondition(xwRegulatoryFramework);
    }
}
