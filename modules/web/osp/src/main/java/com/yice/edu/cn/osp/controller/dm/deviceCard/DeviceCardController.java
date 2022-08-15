package com.yice.edu.cn.osp.controller.dm.deviceCard;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.osp.feignClient.dm.deviceCard.DeviceCardFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * 
* @ClassName: DeviceCardController  
* @Description: 设备卡Controller外部接口  
* @author xuchang  
* @date 2018年9月3日
 */

@RestController
@RequestMapping("/device/card")
@Api(value = "/deviceCard",description = "模块")
public class DeviceCardController {
	
    @Autowired
    private DeviceCardFeign feign;

    @CreateCache(name=Constant.Redis.ECC_CHECKIN_PROTECTEDSTUDENT ,timeUnit= TimeUnit.DAYS,expire=180)
    private Cache<String, ProtectedStudent> protectedStudentCache;


    @PostMapping("/update")
    @ApiOperation(value = "修改IC卡号", notes = "返回响应对象,result.success==true 为成功")
    public ResponseJson updateCardNumber(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Student student){
        // 清除学生缓存 dengfengfeng
        Student oStudent = feign.findStudentById(student.getId());

        final String schoolId = mySchoolId();
        final String oIccard = oStudent.getCardNumber();
        final ProtectedStudent protectedStudent = new ProtectedStudent(oStudent);

        protectedStudent.setIccard(student.getCardNumber());
        protectedStudentCache.put(StringUtils.linkStrWithUnderline(schoolId,student.getCardNumber()),protectedStudent);
        protectedStudentCache.remove(StringUtils.linkStrWithUnderline(schoolId,oIccard));

    	return feign.updateCardNumber(student);
    }


    @PostMapping("/findStudentByCondition")
    @ApiOperation(value = "根据条件查找学生IC绑定信息", notes = "返回学生信息列表")
    public ResponseJson findStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Student student){
    	student.setSchoolId(Optional.ofNullable(currentTeacher().getSchoolId()).orElse(""));
        return feign.findStudentListByCondition(student);
    }
    
    @GetMapping("/student/{id}")
    @ApiOperation(value = "根据学生id查找学生IC绑定信息", notes = "返回学生信息")
    public Student findStudentById(
            @ApiParam(value = "需要查询的学生Id")
            @PathVariable("id") String id){
        return feign.findStudentById(id);
    }
    
    @GetMapping("/unbundle")
    @ApiOperation(value = "根据学生id解绑IC卡(多个用,隔开)", notes = "返回响应对象")
    public void unbundleCard(
    		@ApiParam(value = "需要解绑的学生Id", required = true)
    		@NotNull
    		@RequestParam("id") String id){
//        Student oStudent = feign.findStudentById(id);
//
//        final String schoolId = mySchoolId();
//        final String oIccard = oStudent.getCardNumber();
//
//        protectedStudentCache.remove(StringUtils.linkStrWithUnderline(schoolId,oIccard));
    	feign.unbundleCard(id);
    }





}
