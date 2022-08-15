package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsContentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsContentService {
    @Autowired
    private XwCmsContentFeign xwCmsContentFeign;

    public XwCmsContent findXwCmsContentById(String id) {
        return xwCmsContentFeign.findXwCmsContentById(id);
    }

    public void saveXwCmsContent(XwCmsContent xwCmsContent) {
        xwCmsContentFeign.saveXwCmsContent(xwCmsContent);
    }

    public List<XwCmsContent> findXwCmsContentListByCondition(XwCmsContent xwCmsContent) {
        return xwCmsContentFeign.findXwCmsContentListByCondition(xwCmsContent);
    }

    public XwCmsContent findOneXwCmsContentByCondition(XwCmsContent xwCmsContent) {
        return xwCmsContentFeign.findOneXwCmsContentByCondition(xwCmsContent);
    }

    public long findXwCmsContentCountByCondition(XwCmsContent xwCmsContent) {
        return xwCmsContentFeign.findXwCmsContentCountByCondition(xwCmsContent);
    }

    public void updateXwCmsContent(XwCmsContent xwCmsContent) {
        xwCmsContentFeign.updateXwCmsContent(xwCmsContent);
    }

    public void deleteXwCmsContent(String id) {
        xwCmsContentFeign.deleteXwCmsContent(id);
    }

    public void deleteXwCmsContentByCondition(XwCmsContent xwCmsContent) {
        xwCmsContentFeign.deleteXwCmsContentByCondition(xwCmsContent);
    }

    public Boolean saveXwCmsContentForLayout(XwCmsContent xwCmsContent) {
        return xwCmsContentFeign.saveXwCmsContentForLayout(xwCmsContent);
    }
}
