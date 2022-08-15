package com.yice.edu.cn.xw.dao.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwCmsContentDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsContent> findXwCmsContentListByCondition(XwCmsContent xwCmsContent);

    long findXwCmsContentCountByCondition(XwCmsContent xwCmsContent);

    XwCmsContent findOneXwCmsContentByCondition(XwCmsContent xwCmsContent);

    XwCmsContent findXwCmsContentById(@Param("id") String id);

    void saveXwCmsContent(XwCmsContent xwCmsContent);

    void updateXwCmsContent(XwCmsContent xwCmsContent);

    void deleteXwCmsContent(@Param("id") String id);

    void deleteXwCmsContentByCondition(XwCmsContent xwCmsContent);

    void batchSaveXwCmsContent(List<XwCmsContent> xwCmsContents);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void updateXwCmsContentForLayout(XwCmsContent xwCmsContent);
}
