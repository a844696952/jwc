package com.yice.edu.cn.tap.feignClient.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",path = "/mesAttachFile")
public interface MesAttachFileFeign {
    @PostMapping("/findOneMesAttachFileByCondition")
    MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile);
    @PostMapping("/findMesAttachFileListByCondition")
    List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile);
}
