package com.yice.edu.cn.xw.dao.cms;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwCmsSchoolSpaceConfigDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsSchoolSpaceConfig> findXwCmsSchoolSpaceConfigListByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    long findXwCmsSchoolSpaceConfigCountByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    XwCmsSchoolSpaceConfig findOneXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    String findSchoolIdBySecondDomain(@Param("secondDomain")String secondDomain);

    XwCmsSchoolSpaceConfig findXwCmsSchoolSpaceConfigById(@Param("id") String id);

    XwCmsSchoolSpaceConfig findSchoolSpaceConfigById(@Param("schoolId")String schoolId);

    void saveXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    void updateXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    void deleteXwCmsSchoolSpaceConfig(@Param("id") String id);

    void deleteXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);

    void batchSaveXwCmsSchoolSpaceConfig(List<XwCmsSchoolSpaceConfig> xwCmsSchoolSpaceConfigs);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
