package com.yice.edu.cn.osp.feignClient.jy.prepareLessons;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;



@FeignClient(value="jy",contextId = "teachingPlanFeign",path = "/teachingPlan")
public interface TeachingPlanFeign {
	

    @PostMapping("/findTeachingPlanListByCondition")
    public ResponseJson findTeachingPlanListByCondition(@RequestBody TeachingPlan teachingPlan);
    

    @GetMapping("/editTeachingPlanById/{id}")
    public TeachingPlan editTeachingPlanById(@PathVariable("id") String id);
    
    @GetMapping("/lookTeachingPlanById/{id}")
    public TeachingPlan lookTeachingPlanById(@PathVariable("id") String id);

    @PostMapping("/saveTeachingPlan")
    public ResponseJson saveTeachingPlan(@RequestBody TeachingPlan teachingPlan);

    @PostMapping("/updateTeachingPlan")
    public ResponseJson updateTeachingPlan( @RequestBody TeachingPlan teachingPlan);

    @GetMapping("/deleteTeachingPlan/{id}")
    public ResponseJson deleteTeachingPlan(@PathVariable("id") String id);
    
    @GetMapping(value="/downloadTeachingPlan/{id}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<byte[]> downloadTeachingPlan(@PathVariable("id") String id); 
    
    @GetMapping("/delete/lessonsFile/{id}")
    public ResponseJson deleteLessonsFile(@PathVariable("id") String id);

    @PostMapping("/findTeachingPlanList")
    public ResponseJson findTeachingPlanList(@RequestBody TeachingPlan teachingPlan);

    
}
