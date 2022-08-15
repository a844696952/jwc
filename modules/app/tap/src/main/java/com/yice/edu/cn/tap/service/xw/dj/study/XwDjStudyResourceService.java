package com.yice.edu.cn.tap.service.xw.dj.study;

import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.tap.feignClient.xw.dj.study.XwDjStudyResourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjStudyResourceService {
    @Autowired
    private XwDjStudyResourceFeign xwDjStudyResourceFeign;

    public XwDjStudyResource findXwDjStudyResourceById(String id) {
        return xwDjStudyResourceFeign.findXwDjStudyResourceById(id);
    }

    public XwDjStudyResource saveXwDjStudyResource(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceFeign.saveXwDjStudyResource(xwDjStudyResource);
    }

    public List<XwDjStudyResource> findXwDjStudyResourceListByCondition(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceFeign.findXwDjStudyResourceListByCondition(xwDjStudyResource);
    }

    public XwDjStudyResource findOneXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceFeign.findOneXwDjStudyResourceByCondition(xwDjStudyResource);
    }

    public long findXwDjStudyResourceCountByCondition(XwDjStudyResource xwDjStudyResource) {
        return xwDjStudyResourceFeign.findXwDjStudyResourceCountByCondition(xwDjStudyResource);
    }

    public void updateXwDjStudyResource(XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResourceFeign.updateXwDjStudyResource(xwDjStudyResource);
    }

    public void deleteXwDjStudyResource(String id) {
        xwDjStudyResourceFeign.deleteXwDjStudyResource(id);
    }

    public void deleteXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource) {
        xwDjStudyResourceFeign.deleteXwDjStudyResourceByCondition(xwDjStudyResource);
    }
}
