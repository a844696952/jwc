package com.yice.edu.cn.osp.service.xw.psycholgConsult;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.xw.psycholgConsult.XwPshcholgConsultFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwPshcholgConsultService {
    @Autowired
    private XwPshcholgConsultFeign xwPshcholgConsultFeign;

    public XwPshcholgConsult findXwPshcholgConsultById(String id) {
        return xwPshcholgConsultFeign.findXwPshcholgConsultById(id);
    }

    public XwPshcholgConsult saveXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.saveXwPshcholgConsult(xwPshcholgConsult);
    }

    public List<XwPshcholgConsult> findXwPshcholgConsultListByCondition(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.findXwPshcholgConsultListByCondition(xwPshcholgConsult);
    }

    public XwPshcholgConsult findOneXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.findOneXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    public long findXwPshcholgConsultCountByCondition(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.findXwPshcholgConsultCountByCondition(xwPshcholgConsult);
    }

    public void updateXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultFeign.updateXwPshcholgConsult(xwPshcholgConsult);
    }

    public void deleteXwPshcholgConsult(String id) {
        xwPshcholgConsultFeign.deleteXwPshcholgConsult(id);
    }

    public void deleteXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultFeign.deleteXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    public List<XwPshcholgConsult> findXwPshcholgConsultByCondition2(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.findXwPshcholgConsultByCondition2(xwPshcholgConsult);
    }

    public Long findXwPshcholgConsultCountByCondition2(XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultFeign.findXwPshcholgConsultCountByCondition2(xwPshcholgConsult);
    }
}
