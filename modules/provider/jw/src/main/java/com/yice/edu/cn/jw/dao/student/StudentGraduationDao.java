package com.yice.edu.cn.jw.dao.student;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentGraduationDao {
    List<JwStudentGraduation> findJwStudentGraduationListByCondition(JwStudentGraduation jwStudentGraduation);

    JwStudentGraduation findOneJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);

    long findJwStudentGraduationCountByCondition(JwStudentGraduation jwStudentGraduation);

    JwStudentGraduation findJwStudentGraduationById(String id);

    void saveJwStudentGraduation(JwStudentGraduation jwStudentGraduation);

    void updateJwStudentGraduation(JwStudentGraduation jwStudentGraduation);

    void deleteJwStudentGraduation(String id);

    void deleteJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);

    void batchSaveJwStudentGraduation(List<JwStudentGraduation> jwStudentGraduations);

    void piLiangSaveJwStudentGraduation(JwStudentGraduation[] jwStudentGraduations);

    List<Integer> findJwStudentGraduationClassesByYear(JwStudentGraduation jwStudentGraduation);

    List<JwStudentGraduation> findStudentGraduationListForExportStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation);
}
