package com.yice.edu.cn.ewb.feignClient.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "classRegisterFeign",path = "/classRegister")
public interface ClassRegisterFeign {
    @GetMapping("/findClassRegisterById/{id}")
    ClassRegister findClassRegisterById(@PathVariable("id") String id);
    @PostMapping("/saveClassRegister")
    ClassRegister saveClassRegister(ClassRegister classRegister);
    @PostMapping("/findClassRegisterListByCondition")
    List<ClassRegister> findClassRegisterListByCondition(ClassRegister classRegister);
    @PostMapping("/findOneClassRegisterByCondition")
    ClassRegister findOneClassRegisterByCondition(ClassRegister classRegister);
    @PostMapping("/findClassRegisterCountByCondition")
    long findClassRegisterCountByCondition(ClassRegister classRegister);
    @PostMapping("/updateClassRegister")
    void updateClassRegister(ClassRegister classRegister);
    @GetMapping("/deleteClassRegister/{id}")
    void deleteClassRegister(@PathVariable("id") String id);
    @PostMapping("/deleteClassRegisterByCondition")
    void deleteClassRegisterByCondition(ClassRegister classRegister);
}
