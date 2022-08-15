package com.yice.edu.cn.xw.dao.wage;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttributeType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IWageAttributeTypeDao {
    List<WageAttributeType> findWageAttributeTypeListByCondition(WageAttributeType wageAttributeType);

    WageAttributeType findOneWageAttributeTypeByCondition(WageAttributeType wageAttributeType);

    long findWageAttributeTypeCountByCondition(WageAttributeType wageAttributeType);

    WageAttributeType findWageAttributeTypeById(@Param("id") String id);

    void saveWageAttributeType(WageAttributeType wageAttributeType);

    void updateWageAttributeType(WageAttributeType wageAttributeType);

    void deleteWageAttributeType(@Param("id") String id);

    void deleteWageAttributeTypeByCondition(WageAttributeType wageAttributeType);

    void batchSaveWageAttributeType(List<WageAttributeType> wageAttributeTypes);

    void deleteWageAttributeTypeByTypeId(@Param("id") String id);

    List<WageAttributeType> findWageAttributeTypeByTypeId(@Param("id") String id);

}
