package com.yice.edu.cn.dm.controller.deviceCard;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.dm.service.deviceCard.DeviceCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: DeviceCardController  
* @Description: 设备卡Controller  
* @author xuchang  
* @date 2018年9月3日
 */

@RestController
@RequestMapping("/device/card")
@Api(value = "/device/card",description = "设备卡模块")
public class DeviceCardController {
	
    @Autowired
    private DeviceCardService deviceService;


    @PostMapping("/update")
    @ApiOperation(value = "修改IC卡号", notes = "返回响应对象")
    public ResponseJson updateCardNumber(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Student student){
        return deviceService.updateCardNumber(student);
    }


    @PostMapping("/findStudentByCondition")
    @ApiOperation(value = "根据条件查找学生IC绑定信息", notes = "返回响应对象")
    public ResponseJson findStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Student student){
        return deviceService.findStudentListByCondition(student);
    }
    
    @GetMapping("/student/{id}")
    @ApiOperation(value = "根据学生id查找学生IC绑定信息", notes = "返回响应对象")
    public Student findStudentById(
            @ApiParam(value = "需要查询的学生Id")
            @PathVariable String id){
        return deviceService.findStudentbyId(id);
    }
    
    @GetMapping("/unbundle")
    @ApiOperation(value = "根据学生id解绑IC卡", notes = "返回响应对象")
    public void unbundleCard(
            @ApiParam(value = "需要解绑的学生Id(多个用,隔开)", required = true)
            @NotNull
            @RequestParam("id") String id){
    	List<String> ids= Arrays.asList(id.split(","));
    	ids.forEach(ele->{
    		Student stu=new Student();
        	stu.setId(ele);
        	stu.setCardNumber(CommonClassConstants.STUDENTSTATUS.ICARD_UNBUNDLE);
            deviceService.unbundle(stu);
            stu=null;
    	});
    }





}
