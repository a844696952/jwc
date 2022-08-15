package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value="jw",path = "/jwClasses")
public interface JwClassesFeign {
    @GetMapping("/findJwClassesById/{id}")
    JwClasses findJwClassesById(@PathVariable("id") String id);
    @PostMapping("/saveJwClasses")
    JwClasses saveJwClasses(JwClasses jwClasses);
    @PostMapping("/findJwClassesListByCondition")
    List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses);
    @PostMapping("/findJwClassesCountByCondition")
    long findJwClassesCountByCondition(JwClasses jwClasses);
    @PostMapping("/updateJwClasses")
    void updateJwClasses(JwClasses jwClasses);
    @GetMapping("/deleteJwClasses/{id}")
    void deleteJwClasses(@PathVariable("id") String id);
    @PostMapping("/batchCreateClasses")
    Boolean batchCreateClasses(CreateClassesVo createClassesVo);
    @PostMapping("/findJwClassesListByConditionAndRelate")
    List<JwClasses> findJwClassesListByConditionAndRelate(JwClasses jwClasses);
    @PostMapping("/updateStuClass")
    void updateStuClass(Map<String, String> map);
    @PostMapping("/findJwClassesListWithStuNum")
    List<JwClasses> findJwClassesListWithStuNum(JwClasses jwClasses);
    @PostMapping("/riseTopClasses")
    public void riseTopClasses(Map<String, String> map);
    @PostMapping("/riseGeneralClasses")
    public void riseGeneralClasses(Map<String, String> map);
    @GetMapping("/getClassTree/{schoolId}")
    List<JwClasses> getClassTree(@PathVariable("schoolId") String id);
    @PostMapping("/findJwClassesListByCondition")
    List<JwClasses> findClassesByGradeId(JwClasses jwClasses);
}
