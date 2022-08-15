package com.yice.edu.cn.yed.feignClient.baseData.schoolPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolPermFeign",path = "/schoolPerm")
public interface SchoolPermFeign {
    @GetMapping("/findSchoolPermById/{id}")
    SchoolPerm findSchoolPermById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolPerm")
    SchoolPerm saveSchoolPerm(SchoolPerm schoolPerm);
    @PostMapping("/findSchoolPermListByCondition")
    List<SchoolPerm> findSchoolPermListByCondition(SchoolPerm schoolPerm);
    @PostMapping("/findSchoolPermCountByCondition")
    long findSchoolPermCountByCondition(SchoolPerm schoolPerm);
    @PostMapping("/updateSchoolPerm")
    void updateSchoolPerm(SchoolPerm schoolPerm);
    @GetMapping("/deleteSchoolPerm/{id}")
    void deleteSchoolPerm(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolPermByCondition")
    void deleteSchoolPermByCondition(SchoolPerm schoolPerm);
    @GetMapping("/findAllTreeMenu")
    List<SysPerm> findAllTreeMenu();
    @GetMapping("/deleteSchoolPermRecursive/{id}")
    void deleteSchoolPermRecursive(@PathVariable("id") String id);
    @PostMapping("/updatePerms/{schoolId}")
    void updatePerms(@PathVariable("schoolId") String schoolId, List<String> checkedIds);
    @GetMapping("/syncUpdate")
    void syncUpdate();
    @PostMapping("/batchUpdateSortNum")
    void batchUpdateSortNum(List<SchoolPerm> perms);

    @GetMapping("/findSchoolAndAppPermRelation/{id}")
    ResponseJson findSchoolAndAppPermRelation(@PathVariable("id")String id);
}
