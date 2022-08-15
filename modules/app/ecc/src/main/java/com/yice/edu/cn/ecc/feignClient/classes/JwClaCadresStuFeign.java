package com.yice.edu.cn.ecc.feignClient.classes;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "jwClaCadresStuFeign",path = "/jwClaCadresStu")
public interface JwClaCadresStuFeign {
    @PostMapping("/findStuAndCadresByClassesIdAndName")
    @ApiOperation(value = "根据班级id查询学生和对应的班级职务", notes = "返回学生和职务信息列表")
    public List<JwClaCadresStu> findStuAndCadresByClassesIdAndName(
            @ApiParam(value = "学生信息")
            @RequestBody JwClaCadresStu jwClaCadresStu);
    @PostMapping("/queryJwStudentByClassesId")
    public List<JwClaCadresStu> queryJwStudentByClassesId(String id);

    @GetMapping("/checkStudentIdentity/{studentId}")
    long checkStudentIdentity(@PathVariable("studentId") String studentId);
}
