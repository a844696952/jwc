package com.yice.edu.cn.dm.dao.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmActiveDao {
    List<DmActive> findDmActiveListByCondition(DmActive dmActive);

    DmActive findOneDmActiveByCondition(DmActive dmActive);

    long findDmActiveCountByCondition(DmActive dmActive);

    DmActive findDmActiveById(@Param("id") String id);

    void saveDmActive(DmActive dmActive);

    void updateDmActive(DmActive dmActive);

    void deleteDmActive(@Param("id") String id);

    void deleteDmActiveByCondition(DmActive dmActive);

    void batchSaveDmActive(List<DmActive> dmActives);

    void deleteManyDmActive(String[] rowData);

    List<DmActive> findDmActiveListByConditionLike(DmActive dmActive);

    long findDmActiveCountByConditionLike(DmActive dmActive);

    List<DmActive> findDmActiveListByConditionVue(DmActive dmActive);

}
