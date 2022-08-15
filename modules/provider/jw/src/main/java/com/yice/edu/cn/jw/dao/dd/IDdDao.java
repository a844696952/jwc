package com.yice.edu.cn.jw.dao.dd;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDdDao {
    List<Dd> findDdListByCondition(Dd dd);

    long findDdCountByCondition(Dd dd);

    Dd findDdById(String id);

    void saveDd(Dd dd);

    void updateDd(Dd dd);

    void deleteDd(String id);

    void batchSaveDd(List<Dd> dds);

    List<Dd> findJwClassesList(Dd dd);

    String selectSchoolTypeIdBySchoolId(String schoolId);
}
