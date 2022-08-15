package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsColumnFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwCmsColumnService {
    @Autowired
    private XwCmsColumnFeign xwCmsColumnFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public XwCmsColumn findXwCmsColumnById(String id) {
        return xwCmsColumnFeign.findXwCmsColumnById(id);
    }

    public Integer saveXwCmsColumn(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.saveXwCmsColumn(xwCmsColumn);
    }

    public void batchSaveXwCmsColumn(List<XwCmsColumn> xwCmsColumns){
        xwCmsColumnFeign.batchSaveXwCmsColumn(xwCmsColumns);
    }

    public List<XwCmsColumn> findXwCmsColumnListByCondition(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.findXwCmsColumnListByCondition(xwCmsColumn);
    }


    public XwCmsColumn findOneXwCmsColumnByCondition(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.findOneXwCmsColumnByCondition(xwCmsColumn);
    }

    public long findXwCmsColumnCountByCondition(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.findXwCmsColumnCountByCondition(xwCmsColumn);
    }

    public Boolean updateXwCmsColumn(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.updateXwCmsColumn(xwCmsColumn);
    }

    public void updateXwCmsColumnForAll(XwCmsColumn xwCmsColumn) {
        xwCmsColumnFeign.updateXwCmsColumnForAll(xwCmsColumn);
    }

    public void deleteXwCmsColumn(String id) {
        xwCmsColumnFeign.deleteXwCmsColumn(id);
    }

    public void deleteXwCmsColumnByCondition(XwCmsColumn xwCmsColumn) {
        xwCmsColumnFeign.deleteXwCmsColumnByCondition(xwCmsColumn);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<XwCmsColumn> findXwCmsColumnList(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.findXwCmsColumnList(xwCmsColumn);
    }

    public Boolean move(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnFeign.move(xwCmsColumn);
    }

    public List<XwCmsColumn> selectBannerAndApp(String mySchoolId) {
        return xwCmsColumnFeign.selectBannerAndApp(mySchoolId);
    }
}
