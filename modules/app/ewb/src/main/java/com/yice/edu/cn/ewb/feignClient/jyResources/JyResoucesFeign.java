package com.yice.edu.cn.ewb.feignClient.jyResources;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jyResoucesFeign",path = "/jyResouces")
public interface JyResoucesFeign {

    @PostMapping("/findJyResoucesListByCondition")
    List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces);

    @PostMapping("/findJyResoucesCountByCondition")
    long findJyResoucesCountByCondition(JyResouces jyResouces);

}
