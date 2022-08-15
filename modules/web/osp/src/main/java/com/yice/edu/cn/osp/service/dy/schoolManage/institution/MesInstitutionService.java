package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesInstitutionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class MesInstitutionService {
    @Autowired
    private MesInstitutionFeign mesInstitutionFeign;

    public MesInstitution findMesInstitutionById(String id) {
        return mesInstitutionFeign.findMesInstitutionById(id);
    }

    public MesInstitution saveMesInstitution(MesInstitution mesInstitution) {
        return mesInstitutionFeign.saveMesInstitution(mesInstitution);
    }

    public List<MesInstitution> findMesInstitutionListByCondition(MesInstitution mesInstitution) {
        return mesInstitutionFeign.findMesInstitutionListByCondition(mesInstitution);
    }

    public List<MesInstitution> findMesInstitutionsByCondition(MesInstitution mesInstitution) {
        return mesInstitutionFeign.findMesInstitutionsByCondition(mesInstitution);
    }

    public MesInstitution findOneMesInstitutionByCondition(MesInstitution mesInstitution) {
        return mesInstitutionFeign.findOneMesInstitutionByCondition(mesInstitution);
    }

    public long findMesInstitutionCountByCondition(MesInstitution mesInstitution) {
        return mesInstitutionFeign.findMesInstitutionCountByCondition(mesInstitution);
    }

    public void updateMesInstitution(MesInstitution mesInstitution) {
        mesInstitutionFeign.updateMesInstitution(mesInstitution);
    }

    public Map<String, List<MesCustomTitle>> batchSaveMesInstitution(List<MesInstitution> mesInstitutions) {
        if (CollUtil.isEmpty(mesInstitutions)) {
            return null;
        }
        for (MesInstitution mesInstitution : mesInstitutions) {
            mesInstitution.setSchoolId(mySchoolId());
            if (CollUtil.isEmpty(mesInstitution.getChildren())){
                continue;
            }
            for (MesInstitution child : mesInstitution.getChildren()) {
                child.setSchoolId(mySchoolId());
            }
        }
        return mesInstitutionFeign.batchSaveMesInstitution(mesInstitutions);
    }

    public void deleteMesInstitution(String id) {
        mesInstitutionFeign.deleteMesInstitution(id);
    }

    public void deleteMesInstitutionWhereTimeStatusIdIsNull(String id) {
        mesInstitutionFeign.deleteMesInstitutionWhereTimeStatusIdIsNull(id);
    }

    public void deleteMesInstitutionByCondition(MesInstitution mesInstitution) {
        mesInstitutionFeign.deleteMesInstitutionByCondition(mesInstitution);
    }

    public List<MesInstitutionStudent> findAllJwClassesAndStudents(String schoolId) {
        return mesInstitutionFeign.findAllJwClassesAndStudents(schoolId);
    }

    public List<MesInstitutionStudent> findAllJwClassesBySchoolId(String schoolId) {
        return mesInstitutionFeign.findAllJwClassesBySchoolId(schoolId);
    }

    public Map<String, Object> findInstitutionScore(MesCommonConfig mesCommonConfig) {
        return mesInstitutionFeign.findInstitutionScore(mesCommonConfig);
    }

    public List<SchoolWeek> findSchoolYearsBySchoolId(String schoolId) {
        return mesInstitutionFeign.findSchoolYearsBySchoolId(schoolId);
    }

    public List<SchoolWeek> findSchoolYearListBySchoolId(String schoolId) {
        return mesInstitutionFeign.findSchoolYearListBySchoolId(schoolId);
    }

    public void saveSchoolWeek(List<SchoolWeek> schoolWeeks) {
        if (CollUtil.isEmpty(schoolWeeks)){
            return;
        }
        for (SchoolWeek schoolWeek : schoolWeeks) {
            schoolWeek.setSchoolId(mySchoolId());
        }
        mesInstitutionFeign.saveSchoolWeek(schoolWeeks);
    }

    public List<MesInstitution> findMesInstitutionEditing(MesInstitution mesInstitution) {
        return mesInstitutionFeign.findMesInstitutionEditing(mesInstitution);
    }
}
