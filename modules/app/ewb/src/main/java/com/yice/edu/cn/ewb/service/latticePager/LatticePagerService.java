package com.yice.edu.cn.ewb.service.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import com.yice.edu.cn.ewb.feignClient.latticePager.LatticePagerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LatticePagerService {



    @Autowired
    private LatticePagerFeign latticePagerFeign;

    public ResponseJson findLatticePagerReference(DmPagerBackground dmPagerBackground) {
        return latticePagerFeign.findLatticePagerReference(dmPagerBackground);
    }
}
