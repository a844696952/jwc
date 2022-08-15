package com.yice.edu.cn.osp.service.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttributeType;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageAttributeTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageAttributeTypeService {
    @Autowired
    private WageAttributeTypeFeign wageAttributeTypeFeign;

    public WageAttributeType findWageAttributeTypeById(String id) {
        return wageAttributeTypeFeign.findWageAttributeTypeById(id);
    }

    public WageAttributeType saveWageAttributeType(WageAttributeType wageAttributeType) {
        return wageAttributeTypeFeign.saveWageAttributeType(wageAttributeType);
    }

    public List<WageAttributeType> findWageAttributeTypeListByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeFeign.findWageAttributeTypeListByCondition(wageAttributeType);
    }

    public WageAttributeType findOneWageAttributeTypeByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeFeign.findOneWageAttributeTypeByCondition(wageAttributeType);
    }

    public long findWageAttributeTypeCountByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeFeign.findWageAttributeTypeCountByCondition(wageAttributeType);
    }

    public void updateWageAttributeType(WageAttributeType wageAttributeType) {
        wageAttributeTypeFeign.updateWageAttributeType(wageAttributeType);
    }

    public void deleteWageAttributeType(String id) {
        wageAttributeTypeFeign.deleteWageAttributeType(id);
    }

    public void deleteWageAttributeTypeByCondition(WageAttributeType wageAttributeType) {
        wageAttributeTypeFeign.deleteWageAttributeTypeByCondition(wageAttributeType);
    }

    public List<WageAttributeType>findWageAttributeTypeByTypeId(String id){
        return  wageAttributeTypeFeign.findWageAttributeTypeByTypeId(id);

    }
}
