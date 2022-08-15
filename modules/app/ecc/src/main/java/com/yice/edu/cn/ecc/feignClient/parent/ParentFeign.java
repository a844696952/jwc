package com.yice.edu.cn.ecc.feignClient.parent;

import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient(value="jw",path = "/parent")
public interface ParentFeign {

    @PostMapping("/findParentStudentListByCondition")
    List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent);
}
