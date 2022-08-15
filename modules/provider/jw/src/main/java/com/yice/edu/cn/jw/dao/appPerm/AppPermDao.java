package com.yice.edu.cn.jw.dao.appPerm;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppPermDao {
    List<AppPerm> findAppPermListByCondition(AppPerm appPerm);

    long findAppPermCountByCondition(AppPerm appPerm);

    AppPerm findOneAppPermByCondition(AppPerm appPerm);

    AppPerm findAppPermById(@Param("id") String id);

    void saveAppPerm(AppPerm appPerm);

    void updateAppPerm(AppPerm appPerm);

    void deleteAppPerm(@Param("id") String id);

    void deleteAppPermByCondition(AppPerm appPerm);

    void batchSaveAppPerm(List<AppPerm> appPerms);

    List<AppPerm> findAppPermAndSchoolPermKong(AppPerm appPerm);

    void moveAppPerm(List<AppPerm> appPermList);

    List<Perm> findTeacherTreeMenuByTId(String teacherId);

    List<AppPerm> findSchoolAndAppPermRelation(List<SchoolPerm> schoolPerm);
}
