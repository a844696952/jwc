package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesCommonConfigFeign", path = "/mesCommonConfig")
public interface MesCommonConfigFeign {
    @GetMapping("/findMesCommonConfigById/{id}")
    MesCommonConfig findMesCommonConfigById(@PathVariable("id") String id);

    @PostMapping("/saveMesCommonConfig")
    MesCommonConfig saveMesCommonConfig(MesCommonConfig mesCommonConfig);

    @PostMapping("/findMesCommonConfigListByCondition")
    List<MesCommonConfig> findMesCommonConfigListByCondition(MesCommonConfig mesCommonConfig);

    @PostMapping("/findOneMesCommonConfigByCondition")
    MesCommonConfig findOneMesCommonConfigByCondition(MesCommonConfig mesCommonConfig);

    @PostMapping("/findMesCommonConfigCountByCondition")
    long findMesCommonConfigCountByCondition(MesCommonConfig mesCommonConfig);

    @PostMapping("/updateMesCommonConfig")
    void updateMesCommonConfig(MesCommonConfig mesCommonConfig);

    @GetMapping("/deleteMesCommonConfig/{id}")
    void deleteMesCommonConfig(@PathVariable("id") String id);

    @PostMapping("/deleteMesCommonConfigByCondition")
    void deleteMesCommonConfigByCondition(MesCommonConfig mesCommonConfig);
}
