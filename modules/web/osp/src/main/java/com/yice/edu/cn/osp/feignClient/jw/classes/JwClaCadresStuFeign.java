package com.yice.edu.cn.osp.feignClient.jw.classes;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;

@FeignClient(value="jw",contextId = "jwClaCadresStuFeign",path = "/jwClaCadresStu")
public interface JwClaCadresStuFeign {
    @GetMapping("/findJwClaCadresStuById/{id}")
    JwClaCadresStu findJwClaCadresStuById(@PathVariable("id") String id);
    @PostMapping("/saveJwClaCadresStu")
    JwClaCadresStu saveJwClaCadresStu(JwClaCadresStu jwClaCadresStu);
    @PostMapping("/findJwClaCadresStuListByCondition")
    List<JwClaCadresStu> findJwClaCadresStuListByCondition(JwClaCadresStu jwClaCadresStu);
    @PostMapping("/findJwClaCadresStuCountByCondition")
    long findJwClaCadresStuCountByCondition(JwClaCadresStu jwClaCadresStu);
    @PostMapping("/updateJwClaCadresStu")
    void updateJwClaCadresStu(JwClaCadresStu jwClaCadresStu);
    @GetMapping("/deleteJwClaCadresStu/{id}")
    void deleteJwClaCadresStu(@PathVariable("id") String id);
    @PostMapping("/deleteJwClaCadresStuByCondition")
    void deleteJwClaCadresStuByCondition(JwClaCadresStu jwClaCadresStu);
    @PostMapping("/updateStudentCadres")
    void updateStudentCadres(Map map);
    @PostMapping("/queryJwStudentByClassesId")
    public List<JwClaCadresStu> queryJwStudentByClassesId(String id);
}
