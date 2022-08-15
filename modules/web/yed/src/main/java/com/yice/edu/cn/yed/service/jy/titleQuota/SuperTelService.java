package com.yice.edu.cn.yed.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import com.yice.edu.cn.yed.feignClient.jy.titleQuota.SuperTelFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperTelService {
    @Autowired
    private SuperTelFeign superTelFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SuperTel findSuperTelById(String id) {
        return superTelFeign.findSuperTelById(id);
    }

    public SuperTel saveSuperTel(SuperTel superTel) {
        return superTelFeign.saveSuperTel(superTel);
    }

    public void batchSaveSuperTel(List<SuperTel> superTels){
        superTelFeign.batchSaveSuperTel(superTels);
    }

    public List<SuperTel> findSuperTelListByCondition(SuperTel superTel) {
        return superTelFeign.findSuperTelListByCondition(superTel);
    }

    public SuperTel findOneSuperTelByCondition(SuperTel superTel) {
        return superTelFeign.findOneSuperTelByCondition(superTel);
    }

    public long findSuperTelCountByCondition(SuperTel superTel) {
        return superTelFeign.findSuperTelCountByCondition(superTel);
    }

    public void updateSuperTel(SuperTel superTel) {
        superTelFeign.updateSuperTel(superTel);
    }

    public void updateSuperTelForAll(SuperTel superTel) {
        superTelFeign.updateSuperTelForAll(superTel);
    }

    public void deleteSuperTel(String id) {
        superTelFeign.deleteSuperTel(id);
    }

    public void deleteSuperTelByCondition(SuperTel superTel) {
        superTelFeign.deleteSuperTelByCondition(superTel);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
