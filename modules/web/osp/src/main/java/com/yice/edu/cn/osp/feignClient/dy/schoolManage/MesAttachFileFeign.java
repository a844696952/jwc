package com.yice.edu.cn.osp.feignClient.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",contextId = "mesAttachFileFeign",path = "/mesAttachFile")
public interface MesAttachFileFeign {
    @GetMapping("/findMesAttachFileById/{id}")
    MesAttachFile findMesAttachFileById(@PathVariable("id") String id);
    @PostMapping("/saveMesAttachFile")
    MesAttachFile saveMesAttachFile(MesAttachFile mesAttachFile);
    @PostMapping("/findMesAttachFileListByCondition")
    List<MesAttachFile> findMesAttachFileListByCondition(MesAttachFile mesAttachFile);
    @PostMapping("/findOneMesAttachFileByCondition")
    MesAttachFile findOneMesAttachFileByCondition(MesAttachFile mesAttachFile);
    @PostMapping("/findMesAttachFileCountByCondition")
    long findMesAttachFileCountByCondition(MesAttachFile mesAttachFile);
    @PostMapping("/updateMesAttachFile")
    void updateMesAttachFile(MesAttachFile mesAttachFile);
    @GetMapping("/deleteMesAttachFile/{id}")
    void deleteMesAttachFile(@PathVariable("id") String id);
    @PostMapping("/deleteMesAttachFileByCondition")
    void deleteMesAttachFileByCondition(MesAttachFile mesAttachFile);
}
