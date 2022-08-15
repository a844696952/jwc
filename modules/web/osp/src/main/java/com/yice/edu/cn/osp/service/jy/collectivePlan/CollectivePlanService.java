package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.CollectivePlanFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectivePlanService {
    @Autowired
    private CollectivePlanFeign collectivePlanFeign;

    public CollectivePlan findCollectivePlanById(String id) {
        return collectivePlanFeign.findCollectivePlanById(id);
    }

    public CollectivePlan saveCollectivePlan(CollectivePlan collectivePlan) {
        collectivePlan.setTeacherIdss(collectivePlan.getTeacherIdss().stream().filter(s->!s.equals(collectivePlan.getTeacherId())).collect(Collectors.toList()));
        return collectivePlanFeign.saveCollectivePlan(collectivePlan);
    }

    public List<CollectivePlan> findCollectivePlanListByCondition(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findCollectivePlanListByCondition(collectivePlan);
    }

    public CollectivePlan findOneCollectivePlanByCondition(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findOneCollectivePlanByCondition(collectivePlan);
    }

    public long findCollectivePlanCountByCondition(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findCollectivePlanCountByCondition(collectivePlan);
    }

    public void updateCollectivePlan(CollectivePlan collectivePlan) {
        collectivePlanFeign.updateCollectivePlan(collectivePlan);
    }

    public void deleteCollectivePlan(String id) {
        collectivePlanFeign.deleteCollectivePlan(id);
    }

    public void deleteCollectivePlanByCondition(CollectivePlan collectivePlan) {
        collectivePlanFeign.deleteCollectivePlanByCondition(collectivePlan);
    }
    //根据条件查找讨论组是否重名
    public List<CollectivePlan> findCollectivePlanByPlanName(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findCollectivePlanByPlanName(collectivePlan);
    }
    //查询我创建的备课组
    public List<CollectivePlan> findCollectivePlanList(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findCollectivePlanList(collectivePlan);
    }
    //查询集体备课首用讨论组
    public List<CollectivePlan> findCollectivePlanListByTeacherId(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findCollectivePlanListByTeacherId(collectivePlan);
    }
    //查询学年
    public List<CollectivePlan> findSchoolYear(CollectivePlan collectivePlan) {
        return collectivePlanFeign.findSchoolYear(collectivePlan);
    }
}
