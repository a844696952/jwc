package com.yice.edu.cn.yed.service.general.standard;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.general.standard.Standard;
import com.yice.edu.cn.yed.feignClient.general.standard.StandardFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardService {
    @Autowired
    private StandardFeign standardFeign;
    @Cached(name="StandardService.findStandardById", expire = 60,key = "#id")
    @CacheRefresh(refresh = 10,stopRefreshAfterLastAccess = 60)
    public Standard findStandardById(String id) {
        return standardFeign.findStandardById(id);
    }

    public Standard saveStandard(Standard standard) {
        return standardFeign.saveStandard(standard);
    }

    public List<Standard> findStandardListByCondition(Standard standard) {
        return standardFeign.findStandardListByCondition(standard);
    }

    public long findStandardCountByCondition(Standard standard) {
        return standardFeign.findStandardCountByCondition(standard);
    }

    public void updateStandard(Standard standard) {
        standardFeign.updateStandard(standard);
    }

    public void deleteStandard(String id) {
        standardFeign.deleteStandard(id);
    }
}
