package com.yice.edu.cn.osp.service.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttribute;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageAttributeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageAttributeService {
    @Autowired
    private WageAttributeFeign wageAttributeFeign;

    public WageAttribute findWageAttributeById(String id) {
        return wageAttributeFeign.findWageAttributeById(id);
    }

    public WageAttribute saveWageAttribute(WageAttribute wageAttribute) {
        return wageAttributeFeign.saveWageAttribute(wageAttribute);
    }

    public List<WageAttribute> findWageAttributeListByCondition(WageAttribute wageAttribute) {
        return wageAttributeFeign.findWageAttributeListByCondition(wageAttribute);
    }

    public WageAttribute findOneWageAttributeByCondition(WageAttribute wageAttribute) {
        return wageAttributeFeign.findOneWageAttributeByCondition(wageAttribute);
    }

    public long findWageAttributeCountByCondition(WageAttribute wageAttribute) {
        return wageAttributeFeign.findWageAttributeCountByCondition(wageAttribute);
    }

    public void updateWageAttribute(WageAttribute wageAttribute) {
        wageAttributeFeign.updateWageAttribute(wageAttribute);
    }

    public void deleteWageAttribute(String id) {
        wageAttributeFeign.deleteWageAttribute(id);
    }

    public void deleteWageAttributeByCondition(WageAttribute wageAttribute) {
        wageAttributeFeign.deleteWageAttributeByCondition(wageAttribute);
    }
}
