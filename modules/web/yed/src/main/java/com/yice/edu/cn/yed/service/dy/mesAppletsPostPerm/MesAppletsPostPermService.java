package com.yice.edu.cn.yed.service.dy.mesAppletsPostPerm;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import com.yice.edu.cn.yed.feignClient.dy.mesAppletsPostPerm.MesAppletsPostPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAppletsPostPermService {
    @Autowired
    private MesAppletsPostPermFeign mesAppletsPostPermFeign;

    public MesAppletsPostPerm findMesAppletsPostPermById(String id) {
        return mesAppletsPostPermFeign.findMesAppletsPostPermById(id);
    }

    public MesAppletsPostPerm saveMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermFeign.saveMesAppletsPostPerm(mesAppletsPostPerm);
    }

    public List<MesAppletsPostPerm> findMesAppletsPostPermListByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermFeign.findMesAppletsPostPermListByCondition(mesAppletsPostPerm);
    }

    public MesAppletsPostPerm findOneMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermFeign.findOneMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    public long findMesAppletsPostPermCountByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        return mesAppletsPostPermFeign.findMesAppletsPostPermCountByCondition(mesAppletsPostPerm);
    }

    public void updateMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermFeign.updateMesAppletsPostPerm(mesAppletsPostPerm);
    }

    public void deleteMesAppletsPostPerm(String id) {
        mesAppletsPostPermFeign.deleteMesAppletsPostPerm(id);
    }

    public void deleteMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermFeign.deleteMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    public List<Dd> findDdListByCondition(Dd dd) {
        return mesAppletsPostPermFeign.findDdListByCondition(dd);
    }
}
