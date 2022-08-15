package com.yice.edu.cn.xw.dao.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHomeLayout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwCmsHomeLayoutDao {

    List<XwCmsHomeLayout> findAllCmsHomeLayout(String schoolId);

    XwCmsHomeLayout findCmsHomeLayoutByCid(String columnId);

    void updateXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout);

    void saveXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout);

    void deleteXwCmsHomeLayoutByCid(@Param("columnId") String columnId);

    List<String> findAllColumnIds();

    void deleteXwCmsHomeLayoutBySid(@Param("schoolId") String schoolId);

    void saveBatchXwCmsHomeLayout(List<XwCmsHomeLayout> xwCmsHomeLayoutList);

    void updateXwCmsHomeLayoutSort(@Param("sortNumber") Integer sortNumber, @Param("schoolId") String schoolId, @Param("num") Integer num);

    void deleteXwCmsHomeLayoutBySortNumber(@Param("sortNumber") Integer sortNumber, @Param("schoolId") String schoolId);

    List<String> findCmsHomeLayouTopRowCids(String schoolId);
}
