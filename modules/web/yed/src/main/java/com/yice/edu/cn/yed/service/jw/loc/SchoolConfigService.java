package com.yice.edu.cn.yed.service.jw.loc;

import com.yice.edu.cn.common.pojo.loc.SchoolConfig;
import com.yice.edu.cn.yed.feignClient.jw.loc.SchoolConfigFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolConfigService {
    @Autowired
    private SchoolConfigFeign schoolConfigFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolConfig findSchoolConfigById(String id) {
        return schoolConfigFeign.findSchoolConfigById(id);
    }

    public SchoolConfig saveSchoolConfig(SchoolConfig schoolConfig) {
        return schoolConfigFeign.saveSchoolConfig(schoolConfig);
    }

    public void batchSaveSchoolConfig(List<SchoolConfig> schoolConfigs){
        schoolConfigFeign.batchSaveSchoolConfig(schoolConfigs);
    }

    public List<SchoolConfig> findSchoolConfigListByCondition(SchoolConfig schoolConfig) {
        return schoolConfigFeign.findSchoolConfigListByCondition(schoolConfig);
    }

    public SchoolConfig findOneSchoolConfigByCondition(SchoolConfig schoolConfig) {
        return schoolConfigFeign.findOneSchoolConfigByCondition(schoolConfig);
    }

    public long findSchoolConfigCountByCondition(SchoolConfig schoolConfig) {
        return schoolConfigFeign.findSchoolConfigCountByCondition(schoolConfig);
    }

    public void updateSchoolConfig(SchoolConfig schoolConfig) {
        schoolConfigFeign.updateSchoolConfig(schoolConfig);
    }

    public void updateSchoolConfigForAll(SchoolConfig schoolConfig) {
        schoolConfigFeign.updateSchoolConfigForAll(schoolConfig);
    }

    public void deleteSchoolConfig(String id) {
        schoolConfigFeign.deleteSchoolConfig(id);
    }

    public void deleteSchoolConfigByCondition(SchoolConfig schoolConfig) {
        schoolConfigFeign.deleteSchoolConfigByCondition(schoolConfig);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void saveSchoolConfigKong(SchoolConfig schoolConfig){
        schoolConfigFeign.saveSchoolConfigKong(schoolConfig);
    }
}
