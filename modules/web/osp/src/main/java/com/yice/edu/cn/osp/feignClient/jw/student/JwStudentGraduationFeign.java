package com.yice.edu.cn.osp.feignClient.jw.student;

import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
@FeignClient(value="jw",contextId = "jwStudentGraduationFeign",path = "/jwStudentGraduation")
public interface JwStudentGraduationFeign {
    @GetMapping("/findJwStudentGraduationById/{id}")
    JwStudentGraduation findJwStudentGraduationById(@PathVariable("id") String id);
    @PostMapping("/saveJwStudentGraduation")
    JwStudentGraduation saveJwStudentGraduation(JwStudentGraduation jwStudentGraduation);
    @PostMapping("/findJwStudentGraduationListByCondition")
    List<JwStudentGraduation> findJwStudentGraduationListByCondition(JwStudentGraduation jwStudentGraduation);
    @PostMapping("/findOneJwStudentGraduationByCondition")
    JwStudentGraduation findOneJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);
    @PostMapping("/findJwStudentGraduationCountByCondition")
    long findJwStudentGraduationCountByCondition(JwStudentGraduation jwStudentGraduation);
    @PostMapping("/updateJwStudentGraduation")
    void updateJwStudentGraduation(JwStudentGraduation jwStudentGraduation);
    @GetMapping("/deleteJwStudentGraduation/{id}")
    void deleteJwStudentGraduation(@PathVariable("id") String id);
    @PostMapping("/deleteJwStudentGraduationByCondition")
    void deleteJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);
    @PostMapping("/batchSaveJwStudentGraduation")
    void batchSaveJwStudentGraduation(List<JwStudentGraduation> jwStudentGraduations);

    @PostMapping("/piLiangSaveJwStudentGraduation")
    void piLiangSaveJwStudentGraduation(JwStudentGraduation[] jwStudentGraduations);

    @PostMapping("/findJwStudentGraduationClassesByYear")
    List<Integer> findJwStudentGraduationClassesByYear(JwStudentGraduation jwStudentGraduation);

    @PostMapping("/findStudentGraduationListForExportStudentGraduationByCondition")
    List<JwStudentGraduation> findStudentGraduationListForExportStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);
}
