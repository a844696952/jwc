package com.yice.edu.cn.bmp.feignClient.xwDormManage;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormPersonLogFeign",path = "/dormPersonLog")
public interface DormPersonLogFeign {

    @PostMapping("/findDormPersonLogListByCondition")
    List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog);


}
