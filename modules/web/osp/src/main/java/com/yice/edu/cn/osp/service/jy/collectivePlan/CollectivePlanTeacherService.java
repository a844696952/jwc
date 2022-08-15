package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.CollectivePlanTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectivePlanTeacherService {
    @Autowired
    private CollectivePlanTeacherFeign collectivePlanTeacherFeign;

    public CollectivePlanTeacher findCollectivePlanTeacherById(String id) {
        return collectivePlanTeacherFeign.findCollectivePlanTeacherById(id);
    }

    public CollectivePlanTeacher saveCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherFeign.saveCollectivePlanTeacher(collectivePlanTeacher);
    }

    public List<CollectivePlanTeacher> findCollectivePlanTeacherListByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherFeign.findCollectivePlanTeacherListByCondition(collectivePlanTeacher);
    }

    public CollectivePlanTeacher findOneCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherFeign.findOneCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }

    public long findCollectivePlanTeacherCountByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherFeign.findCollectivePlanTeacherCountByCondition(collectivePlanTeacher);
    }

    public void updateCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher) {
        collectivePlanTeacherFeign.updateCollectivePlanTeacher(collectivePlanTeacher);
    }

    public void deleteCollectivePlanTeacher(String id) {
        collectivePlanTeacherFeign.deleteCollectivePlanTeacher(id);
    }

    public void deleteCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        collectivePlanTeacherFeign.deleteCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }
    //查询当前讨论组中的老师
    public List<CollectivePlanTeacher> findOneCollectivePlanByCollectivePlanId(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherFeign.findOneCollectivePlanByCollectivePlanId(collectivePlanTeacher);
    }
}
