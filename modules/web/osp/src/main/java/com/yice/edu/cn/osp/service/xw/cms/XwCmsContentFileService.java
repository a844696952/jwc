package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsContentFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsContentFileService {
    @Autowired
    private XwCmsContentFileFeign xwCmsContentFileFeign;

    public XwCmsContentFile findXwCmsContentFileById(String id) {
        return xwCmsContentFileFeign.findXwCmsContentFileById(id);
    }

    public XwCmsContentFile saveXwCmsContentFile(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileFeign.saveXwCmsContentFile(xwCmsContentFile);
    }

    public List<XwCmsContentFile> findXwCmsContentFileListByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileFeign.findXwCmsContentFileListByCondition(xwCmsContentFile);
    }

    public XwCmsContentFile findOneXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileFeign.findOneXwCmsContentFileByCondition(xwCmsContentFile);
    }

    public long findXwCmsContentFileCountByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileFeign.findXwCmsContentFileCountByCondition(xwCmsContentFile);
    }

    public void updateXwCmsContentFile(XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileFeign.updateXwCmsContentFile(xwCmsContentFile);
    }

    public void deleteXwCmsContentFile(String id) {
        xwCmsContentFileFeign.deleteXwCmsContentFile(id);
    }

    public void deleteXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileFeign.deleteXwCmsContentFileByCondition(xwCmsContentFile);
    }
}
