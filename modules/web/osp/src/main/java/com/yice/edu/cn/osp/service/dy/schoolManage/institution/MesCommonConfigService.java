package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesCommonConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesCommonConfigService {
    @Autowired
    private MesCommonConfigFeign mesCommonConfigFeign;

    public MesCommonConfig findMesCommonConfigById(String id) {
        return mesCommonConfigFeign.findMesCommonConfigById(id);
    }

    public MesCommonConfig saveMesCommonConfig(MesCommonConfig mesCommonConfig) {
        return mesCommonConfigFeign.saveMesCommonConfig(mesCommonConfig);
    }

    public List<MesCommonConfig> findMesCommonConfigListByCondition(MesCommonConfig mesCommonConfig) {
        return mesCommonConfigFeign.findMesCommonConfigListByCondition(mesCommonConfig);
    }

    public MesCommonConfig findOneMesCommonConfigByCondition(MesCommonConfig mesCommonConfig) {
        return mesCommonConfigFeign.findOneMesCommonConfigByCondition(mesCommonConfig);
    }

    public long findMesCommonConfigCountByCondition(MesCommonConfig mesCommonConfig) {
        return mesCommonConfigFeign.findMesCommonConfigCountByCondition(mesCommonConfig);
    }

    public void updateMesCommonConfig(MesCommonConfig mesCommonConfig) {
        mesCommonConfigFeign.updateMesCommonConfig(mesCommonConfig);
    }

    public void deleteMesCommonConfig(String id) {
        mesCommonConfigFeign.deleteMesCommonConfig(id);
    }

    public void deleteMesCommonConfigByCondition(MesCommonConfig mesCommonConfig) {
        mesCommonConfigFeign.deleteMesCommonConfigByCondition(mesCommonConfig);
    }
}
