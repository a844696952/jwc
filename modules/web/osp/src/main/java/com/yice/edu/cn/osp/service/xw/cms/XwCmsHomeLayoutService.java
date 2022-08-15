package com.yice.edu.cn.osp.service.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHomeLayout;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsLayoutCondition;
import com.yice.edu.cn.osp.feignClient.xw.cms.XwCmsHomeLayoutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class XwCmsHomeLayoutService {
    @Autowired
    private XwCmsHomeLayoutFeign xwCmsHomeLayoutFeign;

    public Boolean saveCmsHomeLayout(List<Map<String,Object>> rows, String schoolId){
        return xwCmsHomeLayoutFeign.saveCmsHomeLayout(rows,schoolId);
    }

    public List<Map<String,Object>> findAllCmsHomeLayout(String schoolId) {
        return xwCmsHomeLayoutFeign.findAllCmsHomeLayout(schoolId);
    }

    public Boolean initCmsHomeLayout(String schoolId){
        return xwCmsHomeLayoutFeign.initCmsHomeLayout(schoolId);
    }

    public List<XwCmsColumn> findLiftHomeLayoutList( String schoolId) {
        return xwCmsHomeLayoutFeign.findLiftHomeLayoutList(schoolId);
    }

    public List<XwCmsColumn> findRigntHomeLayoutList(String schoolId) {
        return xwCmsHomeLayoutFeign.findRigntHomeLayoutList(schoolId);
    }

    public void updateXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout) {
        xwCmsHomeLayoutFeign.updateXwCmsHomeLayout(xwCmsHomeLayout);
    }

    public Boolean addOrDeleteXwCmsHomeLayoutRow(XwCmsLayoutCondition xwCmsLayoutCondition) {
        return xwCmsHomeLayoutFeign.addOrDeleteXwCmsHomeLayoutRow(xwCmsLayoutCondition);
    }

    public Boolean findCmsHomeLayoutByCid(String columnId) {
        return xwCmsHomeLayoutFeign.findCmsHomeLayoutByCid(columnId);
    }

    public Boolean checkCmsHomeLayouTopRow(XwCmsHomeLayout xwCmsHomeLayout) {
        return xwCmsHomeLayoutFeign.checkCmsHomeLayouTopRow(xwCmsHomeLayout);
    }
}
