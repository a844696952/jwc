package com.yice.edu.cn.osp.service.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.osp.feignClient.xw.wage.WageTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageTypeService {
    @Autowired
    private WageTypeFeign wageTypeFeign;

    public WageType findWageTypeById(String id) {
        return wageTypeFeign.findWageTypeById(id);
    }

    public WageType saveWageType(WageType wageType) {
        return wageTypeFeign.saveWageType(wageType);
    }

    public List<WageType> findWageTypeListByCondition(WageType wageType) {
        return wageTypeFeign.findWageTypeListByCondition(wageType);
    }
    public List<WageType> findWageTypeListByConditionNotState(WageType wageType) {
        return wageTypeFeign.findWageTypeListByConditionNotState(wageType);
    }

    public WageType findOneWageTypeByCondition(WageType wageType) {
        return wageTypeFeign.findOneWageTypeByCondition(wageType);
    }

    public long findWageTypeCountByCondition(WageType wageType) {
        return wageTypeFeign.findWageTypeCountByCondition(wageType);
    }

    public void updateWageType(WageType wageType) {
        wageTypeFeign.updateWageType(wageType);
    }

    public void deleteWageType(String id) {
        wageTypeFeign.deleteWageType(id);
    }

    public void deleteWageTypeByCondition(WageType wageType) {
        wageTypeFeign.deleteWageTypeByCondition(wageType);
    }

    public List<WageType> findWageTypeListByCondition1(WageType wageType) {
        return wageTypeFeign.findWageTypeListByCondition1(wageType);
    }
    public void updateWageTypeState(String id){
        wageTypeFeign.updateWageTypeState(id);
    }

    public List<WageType> findWageTypeListByState(WageType wageType) {
        return wageTypeFeign.findWageTypeListByState(wageType);
    }
    public List<WageType> findWageAttributeListByTypeId(WageType wageType) {
        return wageTypeFeign.findWageAttributeListByTypeId(wageType);
    }
}
