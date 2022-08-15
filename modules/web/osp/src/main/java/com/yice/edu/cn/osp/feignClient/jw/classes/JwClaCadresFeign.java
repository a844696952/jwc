package com.yice.edu.cn.osp.feignClient.jw.classes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;

@FeignClient(value="jw",contextId = "jwClaCadresFeign",path = "/jwClaCadres")
public interface JwClaCadresFeign {
    @GetMapping("/findJwClaCadresById/{id}")
    JwClaCadres findJwClaCadresById(@PathVariable("id") String id);
    @PostMapping("/saveJwClaCadres")
    JwClaCadres saveJwClaCadres(JwClaCadres jwClaCadres);
    @PostMapping("/findJwClaCadresListByCondition")
    List<JwClaCadres> findJwClaCadresListByCondition(JwClaCadres jwClaCadres);
    @PostMapping("/findJwClaCadresCountByCondition")
    long findJwClaCadresCountByCondition(JwClaCadres jwClaCadres);
    @PostMapping("/updateJwClaCadres")
    void updateJwClaCadres(JwClaCadres jwClaCadres);
    @GetMapping("/deleteJwClaCadres/{id}")
    void deleteJwClaCadres(@PathVariable("id") String id);
    @PostMapping("/deleteJwClaCadresByCondition")
    void deleteJwClaCadresByCondition(JwClaCadres jwClaCadres);
    @PostMapping("/findJwClaCadresListWithSName")
    List<JwClaCadres> findJwClaCadresListWithSName(JwClaCadres jwClaCadres);  
}
