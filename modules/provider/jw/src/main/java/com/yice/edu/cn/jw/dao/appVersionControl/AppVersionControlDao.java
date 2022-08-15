package com.yice.edu.cn.jw.dao.appVersionControl;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppVersionControlDao {
    List<AppVersionControl> findAppVersionControlListByCondition(AppVersionControl appVersionControl);

    long findAppVersionControlCountByCondition(AppVersionControl appVersionControl);

    AppVersionControl findOneAppVersionControlByCondition(AppVersionControl appVersionControl);

    AppVersionControl findAppVersionControlById(@Param("id") String id);

    void saveAppVersionControl(AppVersionControl appVersionControl);

    void updateAppVersionControl(AppVersionControl appVersionControl);

    void deleteAppVersionControl(@Param("id") String id);

    void deleteAppVersionControlByCondition(AppVersionControl appVersionControl);

    void batchSaveAppVersionControl(List<AppVersionControl> appVersionControls);

    //查询是否有最新版本
    List<AppVersionControl> findVersionControlUptodate(AppVersionControl appVersionControl);

    //查询对应类型（IOS1，Android2）对应端（教师端1，家长端2）大于等于版本号的记录
    List<AppVersionControl> findSaveVersionControlNumber(AppVersionControl appVersionControl);

}
