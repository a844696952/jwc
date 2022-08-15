package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "dy", contextId = "mesInstitutionFeign", path = "/mesInstitution")
public interface MesInstitutionFeign {
    @GetMapping("/findMesInstitutionById/{id}")
    MesInstitution findMesInstitutionById(@PathVariable("id") String id);

    @PostMapping("/saveMesInstitution")
    MesInstitution saveMesInstitution(MesInstitution mesInstitution);

    @PostMapping("/findMesInstitutionListByCondition")
    List<MesInstitution> findMesInstitutionListByCondition(MesInstitution mesInstitution);

    @PostMapping("/findMesInstitutionsByCondition")
    List<MesInstitution> findMesInstitutionsByCondition(MesInstitution mesInstitution);

    @PostMapping("/findOneMesInstitutionByCondition")
    MesInstitution findOneMesInstitutionByCondition(MesInstitution mesInstitution);

    @PostMapping("/findMesInstitutionCountByCondition")
    long findMesInstitutionCountByCondition(MesInstitution mesInstitution);

    @PostMapping("/updateMesInstitution")
    void updateMesInstitution(MesInstitution mesInstitution);

    @PostMapping("/batchSaveMesInstitution")
    Map<String, List<MesCustomTitle>> batchSaveMesInstitution(List<MesInstitution> mesInstitutions);

    @GetMapping("/deleteMesInstitution/{id}")
    void deleteMesInstitution(@PathVariable("id") String id);

    @GetMapping("/deleteMesInstitutionWhereTimeStatusIdIsNull/{id}")
    void deleteMesInstitutionWhereTimeStatusIdIsNull(@PathVariable("id") String id);

    @PostMapping("/deleteMesInstitutionByCondition")
    void deleteMesInstitutionByCondition(MesInstitution mesInstitution);

    @GetMapping("/findAllJwClassesAndStudents/{schoolId}")
    List<MesInstitutionStudent> findAllJwClassesAndStudents(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findAllJwClassesBySchoolId/{schoolId}")
    List<MesInstitutionStudent> findAllJwClassesBySchoolId(@PathVariable("schoolId") String schoolId);

    @PostMapping("/findInstitutionScore")
    Map<String, Object> findInstitutionScore(MesCommonConfig mesCommonConfig);

    @GetMapping("/findSchoolYearsBySchoolId/{schoolId}")
    List<SchoolWeek> findSchoolYearsBySchoolId(@PathVariable("schoolId")String schoolId);


    @GetMapping("/findSchoolYearListBySchoolId/{schoolId}")
    List<SchoolWeek> findSchoolYearListBySchoolId(@PathVariable("schoolId")String schoolId);

    @PostMapping("/saveSchoolWeek")
    void saveSchoolWeek(List<SchoolWeek> schoolWeek);

    @PostMapping("/findMesInstitutionEditing")
    List<MesInstitution> findMesInstitutionEditing(MesInstitution mesInstitution);
}
