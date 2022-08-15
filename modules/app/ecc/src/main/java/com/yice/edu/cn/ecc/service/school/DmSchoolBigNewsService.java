package com.yice.edu.cn.ecc.service.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.ecc.feignClient.school.DmSchoolBigNewsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmSchoolBigNewsService {
    @Autowired
    private DmSchoolBigNewsFeign dmSchoolBigNewsFeign;

    public DmSchoolBigNews findDmSchoolBigNewsById(String id) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsById(id);
    }

    public List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
    }

    public long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
    }
}
