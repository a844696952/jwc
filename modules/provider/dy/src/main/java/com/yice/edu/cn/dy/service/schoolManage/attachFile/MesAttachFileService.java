package com.yice.edu.cn.dy.service.schoolManage.attachFile;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.dy.dao.schoolManage.attachFile.IMesAttachFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesAttachFileService {
    @Autowired
    private IMesAttachFileDao mesAttachFileDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesAttachFile findMesAttachFileById(String id) {
        return mesAttachFileDao.findMesAttachFileById(id);
    }
    @Transactional
    public void saveMesAttachFile(MesAttachFile mesAttachFile) {
        mesAttachFile.setId(sequenceId.nextId());
        mesAttachFileDao.saveMesAttachFile(mesAttachFile);
    }
    @Transactional(readOnly = true)
    public List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile);
    }
    @Transactional(readOnly = true)
    public MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileDao.findOneMesAttachFileByCondition(mesAttachFile);
    }
    @Transactional(readOnly = true)
    public long findMesAttachFileCountByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileDao.findMesAttachFileCountByCondition(mesAttachFile);
    }
    @Transactional
    public void updateMesAttachFile(MesAttachFile mesAttachFile) {
        mesAttachFileDao.updateMesAttachFile(mesAttachFile);
    }
    @Transactional
    public void deleteMesAttachFile(String id) {
        mesAttachFileDao.deleteMesAttachFile(id);
    }
    @Transactional
    public void deleteMesAttachFileByCondition(MesAttachFile mesAttachFile) {
        mesAttachFileDao.deleteMesAttachFileByCondition(mesAttachFile);
    }
    @Transactional
    public void batchSaveMesAttachFile(List<MesAttachFile> mesAttachFiles){
        mesAttachFiles.forEach(mesAttachFile -> mesAttachFile.setId(sequenceId.nextId()));
        mesAttachFileDao.batchSaveMesAttachFile(mesAttachFiles);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
