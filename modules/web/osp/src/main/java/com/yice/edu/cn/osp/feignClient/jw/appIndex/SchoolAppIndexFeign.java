package com.yice.edu.cn.osp.feignClient.jw.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolAppIndexFeign",path = "/schoolAppIndex")
public interface SchoolAppIndexFeign {
    @GetMapping("/findSchoolAppIndexById/{id}")
    SchoolAppIndex findSchoolAppIndexById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolAppIndex")
    SchoolAppIndex saveSchoolAppIndex(SchoolAppIndex schoolAppIndex);
    @PostMapping("/findSchoolAppIndexListByCondition")
    List<SchoolAppIndex> findSchoolAppIndexListByCondition(SchoolAppIndex schoolAppIndex);
    @PostMapping("/findOneSchoolAppIndexByCondition")
    SchoolAppIndex findOneSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex);
    @PostMapping("/findSchoolAppIndexCountByCondition")
    long findSchoolAppIndexCountByCondition(SchoolAppIndex schoolAppIndex);
    @PostMapping("/updateSchoolAppIndex")
    void updateSchoolAppIndex(SchoolAppIndex schoolAppIndex);
    @GetMapping("/deleteSchoolAppIndex/{id}")
    void deleteSchoolAppIndex(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolAppIndexByCondition")
    void deleteSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex);
    @PostMapping("/moveAppIndexes")
    void moveAppIndexes(List<SchoolAppIndex> schoolAppIndices);
    @PostMapping("/upsertSchoolAppIndex")
    void upsertSchoolAppIndex(SchoolAppIndex schoolAppIndex);
}
