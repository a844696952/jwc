package com.yice.edu.cn.dy.dao.schoolManage.attachFile;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesAttachFileDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile);

    long findMesAttachFileCountByCondition(MesAttachFile mesAttachFile);

    MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile);

    MesAttachFile findMesAttachFileById(@Param("id") String id);

    void saveMesAttachFile(MesAttachFile mesAttachFile);

    void updateMesAttachFile(MesAttachFile mesAttachFile);

    void deleteMesAttachFile(@Param("id") String id);

    void deleteMesAttachFileByCondition(MesAttachFile mesAttachFile);

    void batchSaveMesAttachFile(List<MesAttachFile> mesAttachFiles);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
