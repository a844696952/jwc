package com.yice.edu.cn.xw.dao.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwCmsColumnDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsColumn> findXwCmsColumnListByCondition(XwCmsColumn xwCmsColumn);

    long findXwCmsColumnCountByCondition(XwCmsColumn xwCmsColumn);

    XwCmsColumn findOneXwCmsColumnByCondition(XwCmsColumn xwCmsColumn);

    XwCmsColumn findXwCmsColumnById(@Param("id") String id);

    void saveXwCmsColumn(XwCmsColumn xwCmsColumn);

    void updateXwCmsColumn(XwCmsColumn xwCmsColumn);

    void updateXwCmsColumnForAll(XwCmsColumn xwCmsColumn);

    void deleteXwCmsColumn(@Param("id") String id);

    void deleteXwCmsColumnByCondition(XwCmsColumn xwCmsColumn);

    void batchSaveXwCmsColumn(List<XwCmsColumn> xwCmsColumns);

    List<XwCmsColumn> selectBannerAndApp(String schoolId);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
