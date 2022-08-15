package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesUserAuthInstitutionFeign", path = "/mesUserAuthInstitution")
public interface MesUserAuthInstitutionFeign {
    @GetMapping("/findMesUserAuthInstitutionById/{id}")
    MesUserAuthInstitution findMesUserAuthInstitutionById(@PathVariable("id") String id);

    @PostMapping("/saveMesUserAuthInstitution")
    MesUserAuthInstitution saveMesUserAuthInstitution(MesUserAuthInstitution mesUserAuthInstitution);

    @PostMapping("/findMesUserAuthInstitutionListByCondition")
    List<MesUserAuthInstitution> findMesUserAuthInstitutionListByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    @PostMapping("/findOneMesUserAuthInstitutionByCondition")
    MesUserAuthInstitution findOneMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    @PostMapping("/findMesUserAuthInstitutionCountByCondition")
    long findMesUserAuthInstitutionCountByCondition(MesUserAuthInstitution mesUserAuthInstitution);

    @PostMapping("/updateMesUserAuthInstitution")
    void updateMesUserAuthInstitution(MesInstitution mesInstitution);

    @GetMapping("/deleteMesUserAuthInstitution/{id}")
    void deleteMesUserAuthInstitution(@PathVariable("id") String id);

    @PostMapping("/deleteMesUserAuthInstitutionByCondition")
    void deleteMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution);
}
