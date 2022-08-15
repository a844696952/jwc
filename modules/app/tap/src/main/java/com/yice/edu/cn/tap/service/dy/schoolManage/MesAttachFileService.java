package com.yice.edu.cn.tap.service.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.tap.feignClient.dy.schoolManage.MesAttachFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAttachFileService {
    @Autowired
    private MesAttachFileFeign mesAttachFileFeign;

    public MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.findOneMesAttachFileByCondition(mesAttachFile);
    }

    public List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile) {
        return mesAttachFileFeign.findMesAttachFileListByCondition(mesAttachFile);
    }
}
