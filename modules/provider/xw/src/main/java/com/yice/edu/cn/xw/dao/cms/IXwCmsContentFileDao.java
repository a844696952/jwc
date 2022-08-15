package com.yice.edu.cn.xw.dao.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwCmsContentFileDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwCmsContentFile> findXwCmsContentFileListByCondition(XwCmsContentFile xwCmsContentFile);

    long findXwCmsContentFileCountByCondition(XwCmsContentFile xwCmsContentFile);

    XwCmsContentFile findOneXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile);

    XwCmsContentFile findXwCmsContentFileById(@Param("id") String id);

    void saveXwCmsContentFile(XwCmsContentFile xwCmsContentFile);

    void updateXwCmsContentFile(XwCmsContentFile xwCmsContentFile);

    void deleteXwCmsContentFile(@Param("id") String id);

    void deleteXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile);

    void batchSaveXwCmsContentFile(List<XwCmsContentFile> xwCmsContentFiles);

    void deleteFilesByReferenceId(@Param("referenceId") String referenceId);

    /**
     * 通过referenceId获取附件
     */
    List<XwCmsContentFile> findFileByReferenceId(@Param("referenceId") String referenceId);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
