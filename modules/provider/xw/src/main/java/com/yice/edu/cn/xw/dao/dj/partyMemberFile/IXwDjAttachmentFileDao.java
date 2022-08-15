package com.yice.edu.cn.xw.dao.dj.partyMemberFile;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwDjAttachmentFileDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwDjAttachmentFile> findXwDjAttachmentFileListByCondition(XwDjAttachmentFile xwDjAttachmentFile);

    long findXwDjAttachmentFileCountByCondition(XwDjAttachmentFile xwDjAttachmentFile);

    XwDjAttachmentFile findOneXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile);

    XwDjAttachmentFile findXwDjAttachmentFileById(@Param("id") String id);

    void saveXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile);

    void updateXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile);

    void deleteXwDjAttachmentFile(@Param("id") String id);

    void deleteXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile);

    void batchSaveXwDjAttachmentFile(List<XwDjAttachmentFile> xwDjAttachmentFiles);

    void deleteXwDjAttachmentFileByReferenceId(@Param("id") String id);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
