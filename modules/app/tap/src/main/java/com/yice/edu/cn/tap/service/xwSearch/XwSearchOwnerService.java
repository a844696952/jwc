package com.yice.edu.cn.tap.service.xwSearch;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.tap.feignClient.xwSearch.XwSearchOwnerFeign;
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



    public List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findXwSearchOwnerListByCondition(xwSearchOwner);
    }

    public XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findOneXwSearchOwnerByCondition(xwSearchOwner);
    }

    public long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerFeign.findXwSearchOwnerCountByCondition(xwSearchOwner);
    }

}
