package com.yice.edu.cn.osp.feignClient.dm.deviceCard;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="dm",contextId = "deviceCardFeign",path = "/device/card")
public interface DeviceCardFeign {
	
	@PostMapping("/update")
    public ResponseJson updateCardNumber(@RequestBody Student student);


    @PostMapping("/findStudentByCondition")
    public ResponseJson findStudentListByCondition( @RequestBody Student student);
    
    @GetMapping("/student/{id}")
    public Student findStudentById(@PathVariable("id") String id);
    
    @GetMapping(value="/unbundle",consumes = "application/json")
    public void unbundleCard( @RequestParam("id") String id);


    
}
