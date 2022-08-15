package com.yice.edu.cn.jw.dao.classes;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;

@Mapper
public interface IJwClaCadresDao {
    List<JwClaCadres> findJwClaCadresListByCondition(JwClaCadres jwClaCadres);

    long findJwClaCadresCountByCondition(JwClaCadres jwClaCadres);

    JwClaCadres findJwClaCadresById(String id);

    void saveJwClaCadres(JwClaCadres jwClaCadres);

    void updateJwClaCadres(JwClaCadres jwClaCadres);

    void deleteJwClaCadres(String id);

    void deleteJwClaCadresByCondition(JwClaCadres jwClaCadres);

    void batchSaveJwClaCadres(List<JwClaCadres> jwClaCadress);
    
    List<JwClaCadres> findJwClaCadresListWithSName(JwClaCadres jwClaCadres);
    
    void updateJwClaCadresByClassesId(String newClassesId,String oldClassesId);
    
    void deleteClaCadresByClazzIdList(@Param("clazzIdList") List<String> clazzIdList);
}
