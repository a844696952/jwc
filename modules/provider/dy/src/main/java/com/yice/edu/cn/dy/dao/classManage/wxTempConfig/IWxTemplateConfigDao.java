package com.yice.edu.cn.dy.dao.classManage.wxTempConfig;

import java.util.List;

import com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig.WxTemplateConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IWxTemplateConfigDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<WxTemplateConfig> findWxTemplateConfigListByCondition(WxTemplateConfig wxTemplateConfig);

    long findWxTemplateConfigCountByCondition(WxTemplateConfig wxTemplateConfig);

    WxTemplateConfig findOneWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig);

    WxTemplateConfig findWxTemplateConfigById(@Param("id") String id);

    void saveWxTemplateConfig(WxTemplateConfig wxTemplateConfig);

    void updateWxTemplateConfig(WxTemplateConfig wxTemplateConfig);

    void updateWxTemplateConfigForAll(WxTemplateConfig wxTemplateConfig);

    void deleteWxTemplateConfig(@Param("id") String id);

    void deleteWxTemplateConfigByCondition(WxTemplateConfig wxTemplateConfig);

    void batchSaveWxTemplateConfig(List<WxTemplateConfig> wxTemplateConfigs);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
