package com.yice.edu.cn.jw.dao.appPerm;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppSchoolPermDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AppSchoolPerm> findAppSchoolPermListByCondition(AppSchoolPerm appSchoolPerm);

    long findAppSchoolPermCountByCondition(AppSchoolPerm appSchoolPerm);

    AppSchoolPerm findOneAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm);

    AppSchoolPerm findAppSchoolPermById(@Param("id") String id);

    void saveAppSchoolPerm(AppSchoolPerm appSchoolPerm);

    void updateAppSchoolPerm(AppSchoolPerm appSchoolPerm);

    void deleteAppSchoolPerm(@Param("id") String id);

    void deleteAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm);

    void batchSaveAppSchoolPerm(List<AppSchoolPerm> appSchoolPerms);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
