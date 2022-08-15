package com.yice.edu.cn.jw.dao.standard;

import com.yice.edu.cn.common.pojo.general.standard.Standard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StandardDao {
    Standard findStandardById(String id);

    void saveStandard(Standard standard);

    List<Standard> findStandardListByCondition(Standard standard);

    long findStandardCountByCondition(Standard standard);

    void updateStandard(Standard standard);

    void deleteStandard(String id);
}
