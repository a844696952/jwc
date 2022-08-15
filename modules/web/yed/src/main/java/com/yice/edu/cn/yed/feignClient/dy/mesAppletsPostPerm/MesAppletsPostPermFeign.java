package com.yice.edu.cn.yed.feignClient.dy.mesAppletsPostPerm;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesAppletsPostPermFeign", path = "/mesAppletsPostPerm")
public interface MesAppletsPostPermFeign {
    @GetMapping("/findMesAppletsPostPermById/{id}")
    MesAppletsPostPerm findMesAppletsPostPermById(@PathVariable("id") String id);

    @PostMapping("/saveMesAppletsPostPerm")
    MesAppletsPostPerm saveMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm);

    @PostMapping("/findMesAppletsPostPermListByCondition")
    List<MesAppletsPostPerm> findMesAppletsPostPermListByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    @PostMapping("/findOneMesAppletsPostPermByCondition")
    MesAppletsPostPerm findOneMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    @PostMapping("/findMesAppletsPostPermCountByCondition")
    long findMesAppletsPostPermCountByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    @PostMapping("/updateMesAppletsPostPerm")
    void updateMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm);

    @GetMapping("/deleteMesAppletsPostPerm/{id}")
    void deleteMesAppletsPostPerm(@PathVariable("id") String id);

    @PostMapping("/deleteMesAppletsPostPermByCondition")
    void deleteMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    @PostMapping("/findDdListByCondition")
    List<Dd> findDdListByCondition(Dd dd);

}
