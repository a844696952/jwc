package com.yice.edu.cn.dm.dao.school;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmSchoolHonourDao {
    List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour);

    DmSchoolHonour findOneDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour);

    long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour);

    DmSchoolHonour findDmSchoolHonourById(@Param("id") String id);

    void saveDmSchoolHonour(DmSchoolHonour dmSchoolHonour);

    void updateDmSchoolHonour(DmSchoolHonour dmSchoolHonour);

    void deleteDmSchoolHonour(@Param("id") String id);

    void deleteDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour);

    void batchSaveDmSchoolHonour(List<DmSchoolHonour> dmSchoolHonours);

    List<DmSchoolHonour> findDmSchoolHonourByactiveNameLike(DmSchoolHonour dmSchoolHonour);

    Long findDmSchoolHonourByactiveNameLikeCount(DmSchoolHonour dmSchoolHonour);
}
