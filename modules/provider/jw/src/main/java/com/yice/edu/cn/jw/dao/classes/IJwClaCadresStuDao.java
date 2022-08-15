package com.yice.edu.cn.jw.dao.classes;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IJwClaCadresStuDao {
    List<JwClaCadresStu> findJwClaCadresStuListByCondition(JwClaCadresStu jwClaCadresStu);

    long findJwClaCadresStuCountByCondition(JwClaCadresStu jwClaCadresStu);

    JwClaCadresStu findJwClaCadresStuById(String id);

    void saveJwClaCadresStu(JwClaCadresStu jwClaCadresStu);

    void updateJwClaCadresStu(JwClaCadresStu jwClaCadresStu);

    void deleteJwClaCadresStu(String id);

    void deleteJwClaCadresStuByCondition(JwClaCadresStu jwClaCadresStu);

    void batchSaveJwClaCadresStu(List<JwClaCadresStu> jwClaCadresStus);
    
    List<JwClaCadresStu> findJwClaCadresStuInfoListByClassesId(JwClaCadresStu jwClaCadresStu);
    
    List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(JwClaCadresStu jwClaCadresStu);
    
    void updateJwClaCadresStuByClassesId(String newClassesId,String oldClassesId);
    
    void deleteClaCadresStuByClazzIdList(@Param("clazzIdList") List<String> clazzIdList);

    long checkStudentIdentity(@Param("studentId") String studentId);
}
