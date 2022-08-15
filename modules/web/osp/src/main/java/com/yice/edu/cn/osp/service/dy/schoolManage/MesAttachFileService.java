package com.yice.edu.cn.osp.service.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.MesAttachFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAttachFileService {
    @Autowired
    private MesAttachFileFeign mesAttachFileFeign;

    public MesAttachFile findMesAttachFileById(String id) {
        return mesAttachFileFeign.findMesAttachFileById(id);
    }

    public MesAttachFile saveMesAttachFile(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.saveMesAttachFile(mesAttachFile);
    }

    public List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.findMesAttachFileListByCondition(mesAttachFile);
    }

    public MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.findOneMesAttachFileByCondition(mesAttachFile);
    }

    public long findMesAttachFileCountByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.findMesAttachFileCountByCondition(mesAttachFile);
    }

    public void updateMesAttachFile(MesAttachFile mesAttachFile) {
        mesAttachFileFeign.updateMesAttachFile(mesAttachFile);
    }

    public void deleteMesAttachFile(String id) {
        mesAttachFileFeign.deleteMesAttachFile(id);
    }

    public void deleteMesAttachFileByCondition(MesAttachFile mesAttachFile) {
        mesAttachFileFeign.deleteMesAttachFileByCondition(mesAttachFile);
    }
}
