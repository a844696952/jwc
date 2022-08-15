package com.yice.edu.cn.xw.dao.dj.information;

import com.yice.edu.cn.common.pojo.xw.dj.information.XwDjInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwDjInformationDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwDjInformation> findXwDjInformationListByCondition(XwDjInformation xwDjInformation);

    long findXwDjInformationCountByCondition(XwDjInformation xwDjInformation);

    XwDjInformation findOneXwDjInformationByCondition(XwDjInformation xwDjInformation);

    XwDjInformation findXwDjInformationById(@Param("id") String id);

    void saveXwDjInformation(XwDjInformation xwDjInformation);

    void updateXwDjInformation(XwDjInformation xwDjInformation);

    void deleteXwDjInformation(@Param("id") String id);

    void deleteXwDjInformationByCondition(XwDjInformation xwDjInformation);

    void batchSaveXwDjInformation(List<XwDjInformation> xwDjInformations);

    /**
     * 获取资讯列表
     *
     * @param xwDjInformation 查询实体
     * @return 列表数据
     */
    List<XwDjInformation> getListByCondition(XwDjInformation xwDjInformation);

    /**
     * 获取资讯列表总数
     *
     * @param xwDjInformation 查询实体
     * @return 结果
     */
    long getCountByCondition(XwDjInformation xwDjInformation);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    XwDjInformation selectXwDjInformationById(@Param("id") String id);



}
