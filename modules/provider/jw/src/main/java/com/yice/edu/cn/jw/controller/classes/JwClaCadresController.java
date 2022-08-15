package com.yice.edu.cn.jw.controller.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.jw.service.classes.JwClaCadresService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jwClaCadres")
@Api(value = "/jwClaCadres",description = "班级职位信息模块")
public class JwClaCadresController {
    @Autowired
    private JwClaCadresService jwClaCadresService;

    @GetMapping("/findJwClaCadresById/{id}")
    @ApiOperation(value = "通过id查找班级职位信息", notes = "返回班级职位信息对象")
    public JwClaCadres findJwClaCadresById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jwClaCadresService.findJwClaCadresById(id);
    }

    @PostMapping("/saveJwClaCadres")
    @ApiOperation(value = "保存班级职位信息", notes = "返回班级职位信息对象")
    public JwClaCadres saveJwClaCadres(
            @ApiParam(value = "班级职位信息对象", required = true)
            @RequestBody JwClaCadres jwClaCadres){
        jwClaCadresService.saveJwClaCadres(jwClaCadres);
        return jwClaCadres;
    }

    @PostMapping("/findJwClaCadresListByCondition")
    @ApiOperation(value = "根据条件查找班级职位信息列表", notes = "返回班级职位信息列表")
    public List<JwClaCadres> findJwClaCadresListByCondition(
            @ApiParam(value = "班级职位信息对象")
            @RequestBody JwClaCadres jwClaCadres){
        return jwClaCadresService.findJwClaCadresListByCondition(jwClaCadres);
    }
    @PostMapping("/findJwClaCadresCountByCondition")
    @ApiOperation(value = "根据条件查找班级职位信息列表个数", notes = "返回班级职位信息总个数")
    public long findJwClaCadresCountByCondition(
            @ApiParam(value = "班级职位信息对象")
            @RequestBody JwClaCadres jwClaCadres){
        return jwClaCadresService.findJwClaCadresCountByCondition(jwClaCadres);
    }

    @PostMapping("/updateJwClaCadres")
    @ApiOperation(value = "修改班级职位信息", notes = "班级职位信息对象必传")
    public void updateJwClaCadres(
            @ApiParam(value = "班级职位信息对象,对象属性不为空则修改", required = true)
            @RequestBody JwClaCadres jwClaCadres){
        jwClaCadresService.updateJwClaCadres(jwClaCadres);
    }

    @GetMapping("/deleteJwClaCadres/{id}")
    @ApiOperation(value = "通过id删除班级职位信息")
    public void deleteJwClaCadres(
            @ApiParam(value = "班级职位信息对象", required = true)
            @PathVariable String id){
        jwClaCadresService.deleteJwClaCadres(id);
    }
    @PostMapping("/deleteJwClaCadresByCondition")
    @ApiOperation(value = "根据条件删除班级职位信息")
    public void deleteJwClaCadresByCondition(
            @ApiParam(value = "班级职位信息对象")
            @RequestBody JwClaCadres jwClaCadres){
        jwClaCadresService.deleteJwClaCadresByCondition(jwClaCadres);
    }
    
    @PostMapping("/findJwClaCadresListWithSName")
    @ApiOperation(value = "根据条件查找班级职位信息列表和学生名称", notes = "返回班级职位信息列表")
    public List<JwClaCadres> findJwClaCadresListWithSName(
            @ApiParam(value = "班级职位信息对象")
            @RequestBody JwClaCadres jwClaCadres){
        return jwClaCadresService.findJwClaCadresListWithSName(jwClaCadres);
    }
}
