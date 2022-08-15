package com.yice.edu.cn.osp.service.xw.searchOwner;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.osp.feignClient.xw.searchOwner.XwSearchOwnerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwSearchOwnerService {
    @Autowired
    private XwSearchOwnerFeign xwSearchOwnerFeign;

    public XwSearchOwner findXwSearchOwnerById(String id) {
        return xwSearchOwnerFeign.findXwSearchOwnerById(id);
    }

    public XwSearchOwner saveXwSearchOwner(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.saveXwSearchOwner(xwSearchOwner);
    }

    public List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findXwSearchOwnerListByCondition(xwSearchOwner);
    }

    public XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findOneXwSearchOwnerByCondition(xwSearchOwner);
    }

    public long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findXwSearchOwnerCountByCondition(xwSearchOwner);
    }

    public void updateXwSearchOwner(XwSearchOwner xwSearchOwner) {
        xwSearchOwnerFeign.updateXwSearchOwner(xwSearchOwner);
    }

    public void deleteXwSearchOwner(String id) {
        xwSearchOwnerFeign.deleteXwSearchOwner(id);
    }

    public void deleteXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner) {
        xwSearchOwnerFeign.deleteXwSearchOwnerByCondition(xwSearchOwner);
    }

}
