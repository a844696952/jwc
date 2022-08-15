package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesUserAuthInstitutionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesUserAuthInstitutionService {
    @Autowired
    private MesUserAuthInstitutionFeign mesUserAuthInstitutionFeign;

    public MesUserAuthInstitution findMesUserAuthInstitutionById(String id) {
        return mesUserAuthInstitutionFeign.findMesUserAuthInstitutionById(id);
    }

    public MesUserAuthInstitution saveMesUserAuthInstitution(MesUserAuthInstitution mesInstitution) {
        return mesUserAuthInstitutionFeign.saveMesUserAuthInstitution(mesInstitution);
    }

    public List<MesUserAuthInstitution> findMesUserAuthInstitutionListByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionFeign.findMesUserAuthInstitutionListByCondition(mesUserAuthInstitution);
    }

    public MesUserAuthInstitution findOneMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionFeign.findOneMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }

    public long findMesUserAuthInstitutionCountByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionFeign.findMesUserAuthInstitutionCountByCondition(mesUserAuthInstitution);
    }

    public void updateMesUserAuthInstitution(MesInstitution mesInstitution) {
        mesUserAuthInstitutionFeign.updateMesUserAuthInstitution(mesInstitution);
    }

    public void deleteMesUserAuthInstitution(String id) {
        mesUserAuthInstitutionFeign.deleteMesUserAuthInstitution(id);
    }

    public void deleteMesUserAuthInstitutionByCondition(MesUserAuthInstitution mesUserAuthInstitution) {
        mesUserAuthInstitutionFeign.deleteMesUserAuthInstitutionByCondition(mesUserAuthInstitution);
    }
}
