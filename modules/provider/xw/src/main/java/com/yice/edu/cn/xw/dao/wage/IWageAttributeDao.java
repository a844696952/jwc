package com.yice.edu.cn.xw.dao.wage;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IWageAttributeDao {
    List<WageAttribute> findWageAttributeListByCondition(WageAttribute wageAttribute);

    WageAttribute findOneWageAttributeByCondition(WageAttribute wageAttribute);

    long findWageAttributeCountByCondition(WageAttribute wageAttribute);

    WageAttribute findWageAttributeById(@Param("id") String id);

    void saveWageAttribute(WageAttribute wageAttribute);

    void updateWageAttribute(WageAttribute wageAttribute);

    void deleteWageAttribute(@Param("id") String id);

    void deleteWageAttributeByCondition(WageAttribute wageAttribute);

    void batchSaveWageAttribute(List<WageAttribute> wageAttributes);
}
