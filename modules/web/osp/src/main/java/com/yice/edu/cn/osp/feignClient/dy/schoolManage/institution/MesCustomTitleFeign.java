package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesCustomTitleFeign", path = "/mesCustomTitle")
public interface MesCustomTitleFeign {
    @GetMapping("/findMesCustomTitleById/{id}")
    MesCustomTitle findMesCustomTitleById(@PathVariable("id") String id);

    @PostMapping("/saveMesCustomTitle")
    MesCustomTitle saveMesCustomTitle(MesCustomTitle mesCustomTitle);

    @PostMapping("/findMesCustomTitleListByCondition")
    List<MesCustomTitle> findMesCustomTitleListByCondition(MesCustomTitle mesCustomTitle);

    @PostMapping("/findOneMesCustomTitleByCondition")
    MesCustomTitle findOneMesCustomTitleByCondition(MesCustomTitle mesCustomTitle);

    @PostMapping("/findMesCustomTitleCountByCondition")
    long findMesCustomTitleCountByCondition(MesCustomTitle mesCustomTitle);

    @PostMapping("/updateMesCustomTitle")
    void updateMesCustomTitle(MesCustomTitle mesCustomTitle);

    @GetMapping("/deleteMesCustomTitle/{id}")
    void deleteMesCustomTitle(@PathVariable("id") String id);

    @PostMapping("/deleteMesCustomTitleByCondition")
    void deleteMesCustomTitleByCondition(MesCustomTitle mesCustomTitle);
}
