package com.yice.edu.cn.dm.dao.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmAttachmentFileDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmAttachmentFile> findDmAttachmentFileListByCondition(DmAttachmentFile dmAttachmentFile);

    long findDmAttachmentFileCountByCondition(DmAttachmentFile dmAttachmentFile);

    DmAttachmentFile findOneDmAttachmentFileByCondition(DmAttachmentFile dmAttachmentFile);

    DmAttachmentFile findDmAttachmentFileById(@Param("id") String id);

    void saveDmAttachmentFile(DmAttachmentFile dmAttachmentFile);

    void updateDmAttachmentFile(DmAttachmentFile dmAttachmentFile);

    void deleteDmAttachmentFile(@Param("id") String id);

    void deleteDmAttachmentFileByCondition(DmAttachmentFile dmAttachmentFile);

    void batchSaveDmAttachmentFile(List<DmAttachmentFile> dmAttachmentFiles);

    List<DmAttachmentFile> findDmAttachmentFileListByReferenceId(@Param("referenceId") String referenceId);

    void deleteDmAttachmentFileByActivityId(@Param("activityId") String activityId);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
