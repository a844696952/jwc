package com.yice.edu.cn.xw.dao.cms;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwCmsStyleConfigDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsStyleConfig> findXwCmsStyleConfigListByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    long findXwCmsStyleConfigCountByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    XwCmsStyleConfig findOneXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    XwCmsStyleConfig findXwCmsStyleConfigById(@Param("id") String id);

    void saveXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig);

    void updateXwCmsStyleConfig(XwCmsStyleConfig xwCmsStyleConfig);

    void deleteXwCmsStyleConfig(@Param("id") String id);

    void deleteXwCmsStyleConfigByCondition(XwCmsStyleConfig xwCmsStyleConfig);

    void batchSaveXwCmsStyleConfig(List<XwCmsStyleConfig> xwCmsStyleConfigs);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
