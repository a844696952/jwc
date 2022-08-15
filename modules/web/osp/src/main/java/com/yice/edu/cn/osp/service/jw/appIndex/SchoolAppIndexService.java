package com.yice.edu.cn.osp.service.jw.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import com.yice.edu.cn.osp.feignClient.jw.appIndex.SchoolAppIndexFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolAppIndexService {
    @Autowired
    private SchoolAppIndexFeign schoolAppIndexFeign;

    public SchoolAppIndex findSchoolAppIndexById(String id) {
        return schoolAppIndexFeign.findSchoolAppIndexById(id);
    }

    public SchoolAppIndex saveSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexFeign.saveSchoolAppIndex(schoolAppIndex);
    }

    public List<SchoolAppIndex> findSchoolAppIndexListByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexFeign.findSchoolAppIndexListByCondition(schoolAppIndex);
    }

    public SchoolAppIndex findOneSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexFeign.findOneSchoolAppIndexByCondition(schoolAppIndex);
    }

    public long findSchoolAppIndexCountByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexFeign.findSchoolAppIndexCountByCondition(schoolAppIndex);
    }

    public void updateSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        schoolAppIndexFeign.updateSchoolAppIndex(schoolAppIndex);
    }

    public void deleteSchoolAppIndex(String id) {
        schoolAppIndexFeign.deleteSchoolAppIndex(id);
    }

    public void deleteSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex) {
        schoolAppIndexFeign.deleteSchoolAppIndexByCondition(schoolAppIndex);
    }

    public void moveAppIndexes(List<SchoolAppIndex> schoolAppIndices) {
        schoolAppIndexFeign.moveAppIndexes(schoolAppIndices);
    }

    public void upsertSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        schoolAppIndexFeign.upsertSchoolAppIndex(schoolAppIndex);
    }
}
