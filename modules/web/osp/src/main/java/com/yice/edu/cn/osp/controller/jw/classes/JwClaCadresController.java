package com.yice.edu.cn.osp.controller.jw.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.osp.service.jw.classes.JwClaCadresService;

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
    @ApiOperation(value = "通过id查找班级职位信息", notes = "返回响应对象")
    public ResponseJson findJwClaCadresById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        JwClaCadres jwClaCadres=jwClaCadresService.findJwClaCadresById(id);
        return new ResponseJson(jwClaCadres);
    }

    @PostMapping("/saveJwClaCadres")
    @ApiOperation(value = "保存班级职位信息对象", notes = "返回响应对象")
    public ResponseJson saveJwClaCadres(
            @ApiParam(value = "班级职位信息对象", required = true)
            @RequestBody JwClaCadres jwClaCadres){
    	
    	JwClaCadres queryJwClaCadres = new JwClaCadres();
    	queryJwClaCadres.setName(jwClaCadres.getName());
    	queryJwClaCadres.setDel(Constant.DELSIGN.NORMAL);
    	long count = jwClaCadresService.findJwClaCadresCountByCondition(jwClaCadres);
    	if(count>0) {
    		return new ResponseJson(false,"职位名单重复!");
    	}
    	
    	jwClaCadres.setType(CommonClassConstants.USERDEFINED.IS_USER_DEFINED);
    	jwClaCadres.setDel(Constant.DELSIGN.NORMAL);
        JwClaCadres s=jwClaCadresService.saveJwClaCadres(jwClaCadres);
        return new ResponseJson(s);
    }
    @PostMapping("/updateJwClaCadres")
    @ApiOperation(value = "修改班级职位信息对象", notes = "返回响应对象")
    public ResponseJson updateJwClaCadres(
            @ApiParam(value = "被修改的班级职位信息对象,对象属性不为空则修改", required = true)
            @RequestBody JwClaCadres jwClaCadres){
        jwClaCadresService.updateJwClaCadres(jwClaCadres);
        return new ResponseJson();
    }

    @PostMapping("/findJwClaCadressByCondition")
    @ApiOperation(value = "根据条件查找班级职位信息", notes = "返回响应对象")
    public ResponseJson findJwClaCadressByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwClaCadres jwClaCadres){
        List<JwClaCadres> data=jwClaCadresService.findJwClaCadresListByCondition(jwClaCadres);
        return new ResponseJson(data);
    }
    @GetMapping("/deleteJwClaCadres/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJwClaCadres(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jwClaCadresService.deleteJwClaCadres(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteJwClaCadresByCondition")
    @ApiOperation(value = "根据条件删除班级职位信息", notes = "返回响应对象")
    public ResponseJson deleteJwClaCadresByCondition(
            @ApiParam(value = "被删除的班级职位信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody JwClaCadres jwClaCadres){
        jwClaCadresService.deleteJwClaCadresByCondition(jwClaCadres);
        return new ResponseJson();
    }
    
    @PostMapping("/findJwClaCadresListWithSName")
    @ApiOperation(value = "根据条件查找班级职位信息", notes = "返回响应对象")
    public ResponseJson findJwClaCadresListWithSName(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwClaCadres jwClaCadres){
        List<JwClaCadres> data=jwClaCadresService.findJwClaCadresListWithSName(jwClaCadres);
        return new ResponseJson(data);
    }
    
    @PostMapping("/managerStudentCadres/findCadresStudent")
    @ApiOperation(value = "根据条件查找班级职位信息", notes = "返回响应对象")
    public ResponseJson findCadresStudent(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwClaCadres jwClaCadres){
        List<JwClaCadres> data=jwClaCadresService.findJwClaCadresListWithSName(jwClaCadres);
        return new ResponseJson(data);
    }
}
