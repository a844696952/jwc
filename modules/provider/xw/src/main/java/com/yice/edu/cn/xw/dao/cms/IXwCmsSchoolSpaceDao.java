package com.yice.edu.cn.xw.dao.cms;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwCmsSchoolSpaceDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsSchoolSpace> findXwCmsSchoolSpaceListByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);

    long findXwCmsSchoolSpaceCountByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);

    XwCmsSchoolSpace findOneXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);

    XwCmsSchoolSpace findXwCmsSchoolSpaceById(@Param("id") String id);

    XwCmsSchoolSpace findXwCmsSchoolSpaceByDomain(@Param("secondDomain")String secondDomain);

    List<XwCmsSchoolSpace> findProvinceList();

    void saveXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace);

    void updateXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace);

    void updateXwCmsSchoolSpaceBySchoolId(XwCmsSchoolSpace xwCmsSchoolSpace);

    void deleteXwCmsSchoolSpace(@Param("id") String id);

    void deleteXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);

    void batchSaveXwCmsSchoolSpace(List<XwCmsSchoolSpace> xwCmsSchoolSpaces);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
