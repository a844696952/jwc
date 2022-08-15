package com.yice.edu.cn.jw.dao.appGuidance;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAppGuidanceDao {
    List<AppGuidance> findAppGuidanceListByCondition(AppGuidance appGuidance);

    AppGuidance findOneAppGuidanceByCondition(AppGuidance appGuidance);

    long findAppGuidanceCountByCondition(AppGuidance appGuidance);

    AppGuidance findAppGuidanceById(@Param("id") String id);

    void saveAppGuidance(AppGuidance appGuidance);

    void updateAppGuidance(AppGuidance appGuidance);

    void deleteAppGuidance(@Param("id") String id);

    void deleteAppGuidanceByCondition(AppGuidance appGuidance);

    void batchSaveAppGuidance(List<AppGuidance> appGuidances);

    long findAppGuidanceCountByCondition4Like(AppGuidance appGuidance);

    List<AppGuidance> findAppGuidanceListByCondition4Like(AppGuidance appGuidance);
}
