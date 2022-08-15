package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesTimeStatusFeign", path = "/mesTimeStatus")
public interface MesTimeStatusFeign {
    @GetMapping("/findMesTimeStatusById/{id}")
    MesTimeStatus findMesTimeStatusById(@PathVariable("id") String id);

    @PostMapping("/saveMesTimeStatus")
    MesTimeStatus saveMesTimeStatus(MesTimeStatus mesTimeStatus);

    @PostMapping("/findMesTimeStatusListByCondition")
    List<MesTimeStatus> findMesTimeStatusListByCondition(MesTimeStatus mesTimeStatus);

    @PostMapping("/findOneMesTimeStatusByCondition")
    MesTimeStatus findOneMesTimeStatusByCondition(MesTimeStatus mesTimeStatus);

    @PostMapping("/findMesTimeStatusCountByCondition")
    long findMesTimeStatusCountByCondition(MesTimeStatus mesTimeStatus);

    @PostMapping("/updateMesTimeStatus")
    void updateMesTimeStatus(MesTimeStatus mesTimeStatus);

    @GetMapping("/deleteMesTimeStatus/{id}")
    void deleteMesTimeStatus(@PathVariable("id") String id);

    @PostMapping("/deleteMesTimeStatusByCondition")
    void deleteMesTimeStatusByCondition(MesTimeStatus mesTimeStatus);
}
