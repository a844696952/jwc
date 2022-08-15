package com.yice.edu.cn.ewb.feignClient.prepareLessons;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;



@FeignClient(value="jy",contextId = "teachingPlanFeign",path = "/teachingPlan")
public interface TeachingPlanFeign {
	

    @PostMapping("/findTeachingPlanListByCondition")
    ResponseJson findTeachingPlanListByCondition(@RequestBody TeachingPlan teachingPlan);
    
    
    @GetMapping("/lookTeachingPlanById/{id}")
    TeachingPlan lookTeachingPlanById(@PathVariable("id") String id);

    @GetMapping(value="/downloadTeachingPlan/{id}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id);

    @PostMapping("/findTeachingPlanList")
    ResponseJson findTeachingPlanList(@RequestBody TeachingPlan teachingPlan);

    @PostMapping("/findMaterialInformation")
    ResponseJson findMaterialInformation(@RequestBody TeachingPlan teachingPlan);

    
}
