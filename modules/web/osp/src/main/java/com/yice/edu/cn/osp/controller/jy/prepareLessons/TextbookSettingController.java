package com.yice.edu.cn.osp.controller.jy.prepareLessons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.osp.service.jy.prepareLessons.TextbookSettingService;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;

@RestController
@RequestMapping("/textbookSetting")
@Api(value = "/textbookSetting",description = "模块")
public class TextbookSettingController {
    @Autowired
    private TextbookSettingService textbookSettingService;
    
    @GetMapping("/findSubjectByAnnualPeriodId/{annualPeriodId}")
    @ApiOperation(value = "通过学段id查找科目", notes = "返回对象")
    public List<SubjectMaterial> findSubjectByAnnualPeriodId(
            @ApiParam(value = "学段id", required = true)
            @PathVariable String annualPeriodId){
        return textbookSettingService.findSubjectByAnnualPeriodId(annualPeriodId);
    }
    
    @GetMapping("/findGradeBySubjectId/{subjectId}")
    @ApiOperation(value = "通过科目id查找年级", notes = "返回对象")
    public List<SubjectMaterial> findGradeBySubjectId(
            @ApiParam(value = "科目id", required = true)
            @PathVariable String subjectId){
        return textbookSettingService.findGradeBySubjectId(subjectId);
    }
    
    @GetMapping("/findMaterialBySubjectMaterialId/{subjectMaterialId}")
    @ApiOperation(value = "通过所属科目年级id查找教材", notes = "返回对象")
    public List<Material> findMaterialBySubjectMaterialId(
            @ApiParam(value = "所属科目年级id", required = true)
            @PathVariable String subjectMaterialId){
    	List<Material> materials=textbookSettingService.findMaterialBySubjectMaterialId(subjectMaterialId);
        return materials;
    }
    
    
    
    @GetMapping("/findTextbookSettingById")
    @ApiOperation(value = "通过教师id查找", notes = "返回对象")
    public List<TextbookSetting> findTextbookSettingByTeacherId(){
        return textbookSettingService.findTextbookSettingByTeacherId(currentTeacher().getId());
    }

    @PostMapping("/saveTextbookSetting")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ResponseJson saveTextbookSetting(
            @ApiParam(value = "对象", required = true)
            @RequestBody Material material){
    	TextbookSetting textbookSetting=new TextbookSetting();
    	textbookSetting.setIconPath(material.getImage());
    	textbookSetting.setSchoolId(currentTeacher().getSchoolId());
    	textbookSetting.setTeacherId(currentTeacher().getId());
    	textbookSetting.setTextbookId(material.getId());
    	textbookSetting.setTextbookName(material.getName());
    	textbookSetting.setSubjectMaterialId(material.getSubjectMaterialId());
        ResponseJson status = textbookSettingService.saveTextbookSetting(textbookSetting);
        return status;
    }

    @GetMapping("/deleteTextbookSetting/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteTextbookSetting(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        textbookSettingService.deleteTextbookSetting(id);
    }
    
    @GetMapping("/findLastSetting")
    @ApiOperation(value = "通过老师id获取最近设置的教材")
    public TextbookSetting findLastSettingbyTeacherId(){
        return textbookSettingService.findLastSettingbyTeacherId(currentTeacher().getId());
    }
    
}
