package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "collectivePlanTeacherFeign",path = "/collectivePlanTeacher")
public interface CollectivePlanTeacherFeign {
    @GetMapping("/findCollectivePlanTeacherById/{id}")
    CollectivePlanTeacher findCollectivePlanTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveCollectivePlanTeacher")
    CollectivePlanTeacher saveCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher);
    @PostMapping("/findCollectivePlanTeacherListByCondition")
    List<CollectivePlanTeacher> findCollectivePlanTeacherListByCondition(CollectivePlanTeacher collectivePlanTeacher);
    @PostMapping("/findOneCollectivePlanTeacherByCondition")
    CollectivePlanTeacher findOneCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher);
    @PostMapping("/findCollectivePlanTeacherCountByCondition")
    long findCollectivePlanTeacherCountByCondition(CollectivePlanTeacher collectivePlanTeacher);
    @PostMapping("/updateCollectivePlanTeacher")
    void updateCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher);
    @GetMapping("/deleteCollectivePlanTeacher/{id}")
    void deleteCollectivePlanTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteCollectivePlanTeacherByCondition")
    void deleteCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher);
    //查询当前讨论组中的老师
    @PostMapping("/findOneCollectivePlanByCollectivePlanId")
    List<CollectivePlanTeacher> findOneCollectivePlanByCollectivePlanId(CollectivePlanTeacher collectivePlanTeacher);

}
