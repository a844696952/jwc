package com.yice.edu.cn.dm.dao.dmClassCardArea;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmClassCardAreaDao {
    List<DmClassCardArea> findDmClassCardAreaListByCondition(DmClassCardArea dmClassCardArea);

    long findDmClassCardAreaCountByCondition(DmClassCardArea dmClassCardArea);

    DmClassCardArea findOneDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea);

    DmClassCardArea findDmClassCardAreaById(@Param("id") String id);

    void saveDmClassCardArea(DmClassCardArea dmClassCardArea);

    void updateDmClassCardArea(DmClassCardArea dmClassCardArea);

    void deleteDmClassCardArea(@Param("id") String id);

    void deleteDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea);

    void batchSaveDmClassCardArea(List<DmClassCardArea> dmClassCardAreas);
}
