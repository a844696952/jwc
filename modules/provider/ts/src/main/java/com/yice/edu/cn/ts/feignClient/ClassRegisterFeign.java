package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "classRegisterFeign",path = "/classRegister")
public interface ClassRegisterFeign {

    @PostMapping("/updateClassRegisterStatus")
    void updateClassRegisterStatus(ClassRegister classRegister);

}
