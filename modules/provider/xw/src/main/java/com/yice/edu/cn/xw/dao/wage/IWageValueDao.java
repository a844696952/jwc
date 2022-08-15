package com.yice.edu.cn.xw.dao.wage;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.wage.WageValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IWageValueDao {
    List<WageValue> findWageValueListByCondition(WageValue wageValue);

    WageValue findOneWageValueByCondition(WageValue wageValue);

    long findWageValueCountByCondition(WageValue wageValue);

    WageValue findWageValueById(@Param("id") String id);

    void saveWageValue(WageValue wageValue);

    void updateWageValue(WageValue wageValue);

    void deleteWageValue(@Param("id") String id);

    void deleteWageValueByCondition(WageValue wageValue);

    void batchSaveWageValue(List<WageValue> wageValues);

    void deleteWageValueByRecordId(@Param("id") String id);
}
