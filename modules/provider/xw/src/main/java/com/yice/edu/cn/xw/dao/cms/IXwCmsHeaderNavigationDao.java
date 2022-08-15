package com.yice.edu.cn.xw.dao.cms;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwCmsHeaderNavigationDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsHeaderNavigation> findXwCmsHeaderNavigationListByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    long findXwCmsHeaderNavigationCountByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    XwCmsHeaderNavigation findOneXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    XwCmsHeaderNavigation findXwCmsHeaderNavigationById(@Param("id") String id);

    void saveXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    void updateXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    void updateXwCmsHeaderNavigationForAll(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    void deleteXwCmsHeaderNavigation(@Param("id") String id);

    void deleteXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation);

    void batchSaveXwCmsHeaderNavigation(List<XwCmsHeaderNavigation> xwCmsHeaderNavigations);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    void deleteXwCmsHeaderNavigationAll();
}
