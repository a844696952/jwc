package com.yice.edu.cn.osp.feignClient.jw.classes;

import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.classes.rise.RiseClazzVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value="jw",contextId = "jwClassesFeign",path = "/jwClasses")
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
    void updateStuClass(Map<String,String> map);
    @PostMapping("/findJwClassesListWithStuNum")
    List<JwClasses> findJwClassesListWithStuNum(JwClasses jwClasses);

    @PostMapping("/findOneJwClassesByCondition")
    JwClasses findOneJwClassesByCondition(JwClasses jwClasses);

    @PostMapping("/getClassesNumber")
    List<JwClasses> getClassesNumber(JwClasses jwClasses);
    
    @PostMapping("/riseClazz")
    public void riseClazz(RiseClazzVo riseClazzVo);
    
    @PostMapping("/findMaxClassesNo")
	public Integer findMaxClassesNoByCondition(JwClasses jwClasses);

    @PostMapping("/findJwClassessByConditionForExam")
    List<JwClasses> findJwClassessByConditionForExam(JwClasses jwClasses);

    @PostMapping("/findClassListByJwClassesKong")
    List<JwClasses> findClassListByJwClassesKong(JwClasses jwClasses);
}
