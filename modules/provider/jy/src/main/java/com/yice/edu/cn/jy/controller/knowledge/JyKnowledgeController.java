package com.yice.edu.cn.jy.controller.knowledge;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeAndGradeMapVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.UploadKnowledgeVo;
import com.yice.edu.cn.jy.service.knowledge.JyKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jyKnowledge")
@Api(value = "/jyKnowledge",description = "知识点树模块")
public class JyKnowledgeController {
    @Autowired
    private JyKnowledgeService jyKnowledgeService;

    @GetMapping("/findJyKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找知识点树", notes = "返回知识点树对象")
    public JyKnowledge findJyKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jyKnowledgeService.findJyKnowledgeById(id);
    }

    @PostMapping("/saveJyKnowledge")
    @ApiOperation(value = "保存知识点树", notes = "返回知识点树对象")
    public JyKnowledge saveJyKnowledge(
            @ApiParam(value = "知识点树对象", required = true)
            @RequestBody JyKnowledge jyKnowledge){
        jyKnowledgeService.saveJyKnowledge(jyKnowledge);
        return jyKnowledge;
    }

    @PostMapping("/findJyKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找知识点树列表", notes = "返回知识点树列表")
    public List<JyKnowledge> findJyKnowledgeListByCondition(
            @ApiParam(value = "知识点树对象")
            @RequestBody JyKnowledge jyKnowledge){
        return jyKnowledgeService.findJyKnowledgeListByCondition(jyKnowledge);
    }
    @PostMapping("/findJyKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找知识点树列表个数", notes = "返回知识点树总个数")
    public long findJyKnowledgeCountByCondition(
            @ApiParam(value = "知识点树对象")
            @RequestBody JyKnowledge jyKnowledge){
        return jyKnowledgeService.findJyKnowledgeCountByCondition(jyKnowledge);
    }

    @PostMapping("/updateJyKnowledge")
    @ApiOperation(value = "修改知识点树", notes = "知识点树对象必传")
    public void updateJyKnowledge(
            @ApiParam(value = "知识点树对象,对象属性不为空则修改", required = true)
            @RequestBody JyKnowledge jyKnowledge){
        jyKnowledgeService.updateJyKnowledge(jyKnowledge);
    }

    @GetMapping("/deleteJyKnowledge/{id}")
    @ApiOperation(value = "通过id删除知识点树")
    public void deleteJyKnowledge(
            @ApiParam(value = "知识点树对象", required = true)
            @PathVariable String id){
        jyKnowledgeService.deleteJyKnowledge(id);
    }
    @PostMapping("/deleteJyKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除知识点树")
    public void deleteJyKnowledgeByCondition(
            @ApiParam(value = "知识点树对象")
            @RequestBody JyKnowledge jyKnowledge){
        jyKnowledgeService.deleteJyKnowledgeByCondition(jyKnowledge);
    }
    @PostMapping("/findOneJyKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个知识点树,结果必须为单条数据", notes = "返回单个知识点树,没有时为空")
    public JyKnowledge findOneJyKnowledgeByCondition(
            @ApiParam(value = "知识点树对象")
            @RequestBody JyKnowledge jyKnowledge){
        return jyKnowledgeService.findOneJyKnowledgeByCondition(jyKnowledge);
    }
    
    @PostMapping("/deleteLogicById")
    @ApiOperation(value = "通过id删除知识点树和其子节点")
    public void deleteByLogic(
    		@ApiParam(value = "知识点树对象")
    		@RequestBody JyKnowledge jyKnowledge){
        jyKnowledgeService.deleteByLogic(jyKnowledge);
    }
    
    @PostMapping("/uploadJyKnowledge")
    @ApiOperation(value = "批量添加知识点")
    public void uploadJyKnowledge(@RequestBody UploadKnowledgeAndGradeMapVo vo) {
    	List<UploadKnowledgeVo> jyKnowledgesVo = vo.getJyKnowledgesVoList();
    	Map<String,String> gradeMap = vo.getGradeMap();
    	jyKnowledgeService.batchSaveJyKnowledge(jyKnowledgesVo,gradeMap);
    }
}
