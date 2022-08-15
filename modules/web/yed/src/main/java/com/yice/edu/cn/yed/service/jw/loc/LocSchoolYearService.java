package com.yice.edu.cn.yed.service.jw.loc;

import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import com.yice.edu.cn.yed.feignClient.jw.loc.LocSchoolYearFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocSchoolYearService {
    @Autowired
    private LocSchoolYearFeign locSchoolYearFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public LocSchoolYear findLocSchoolYearById(String id) {
        return locSchoolYearFeign.findLocSchoolYearById(id);
    }

    public LocSchoolYear saveLocSchoolYear(LocSchoolYear locSchoolYear) {
        return locSchoolYearFeign.saveLocSchoolYear(locSchoolYear);
    }

    public void batchSaveLocSchoolYear(List<LocSchoolYear> locSchoolYears){
        locSchoolYearFeign.batchSaveLocSchoolYear(locSchoolYears);
    }

    public List<LocSchoolYear> findLocSchoolYearListByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearFeign.findLocSchoolYearListByCondition(locSchoolYear);
    }

    public LocSchoolYear findOneLocSchoolYearByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearFeign.findOneLocSchoolYearByCondition(locSchoolYear);
    }

    public long findLocSchoolYearCountByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearFeign.findLocSchoolYearCountByCondition(locSchoolYear);
    }

    public void updateLocSchoolYear(LocSchoolYear locSchoolYear) {
        locSchoolYearFeign.updateLocSchoolYear(locSchoolYear);
    }

    public void updateLocSchoolYearForAll(LocSchoolYear locSchoolYear) {
        locSchoolYearFeign.updateLocSchoolYearForAll(locSchoolYear);
    }

    public void deleteLocSchoolYear(String id) {
        locSchoolYearFeign.deleteLocSchoolYear(id);
    }

    public void deleteLocSchoolYearByCondition(LocSchoolYear locSchoolYear) {
        locSchoolYearFeign.deleteLocSchoolYearByCondition(locSchoolYear);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
