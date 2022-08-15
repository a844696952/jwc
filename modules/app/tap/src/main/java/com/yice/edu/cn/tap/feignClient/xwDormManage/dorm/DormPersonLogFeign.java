package com.yice.edu.cn.tap.feignClient.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormPersonLogFeign",path = "/dormPersonLog")
public interface DormPersonLogFeign {

    @PostMapping("/findDormPersonLogListByCondition")
    List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonOutListByConditionWithStudent")
    List<DormPersonLog> findDormPersonOutListByConditionWithStudent(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonOutCountByConditionWithStudent")
    long findDormPersonOutCountByConditionWithStudent(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonOutListByConditionWithTeacher")
    List<DormPersonLog> findDormPersonOutListByConditionWithTeacher(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonOutCountByConditionWithTeacher")
    long findDormPersonOutCountByConditionWithTeacher(DormPersonLog dormPersonLog);

}
