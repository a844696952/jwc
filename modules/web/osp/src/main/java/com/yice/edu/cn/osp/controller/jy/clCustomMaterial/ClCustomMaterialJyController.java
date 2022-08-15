package com.yice.edu.cn.osp.controller.jy.clCustomMaterial;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterial;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.clCustomMaterial.ClCustomMaterialDataService;
import com.yice.edu.cn.osp.service.xw.clCustomMaterial.ClCustomMaterialService;
import com.yice.edu.cn.osp.service.xw.clCustomMaterial.ClWeightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/clCustomMaterialJy")
@Api(value = "/clCustomMaterialJy",description = "自定义材料表模块")
public class ClCustomMaterialJyController {
    @Autowired
    private ClCustomMaterialService clCustomMaterialService;
    @Autowired
    private ClCustomMaterialDataService clCustomMaterialDataService;
    @Autowired
    private ClWeightService clWeightService;

    @PostMapping("/saveClCustomMaterial")
    @ApiOperation(value = "保存自定义材料表对象", notes = "返回保存好的自定义材料表对象", response=ClCustomMaterial.class)
    public ResponseJson saveClCustomMaterial(
            @ApiParam(value = "自定义材料表对象", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){

        clCustomMaterial.setType(3);
        clCustomMaterial.setSchoolId(LoginInterceptor.mySchoolId());
        clCustomMaterialService.saveClCustomMaterialDataAndClWeight(clCustomMaterial);
        return new ResponseJson();
    }

    @GetMapping("/update/findClCustomMaterialById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找自定义材料表", notes = "返回响应对象", response=ClCustomMaterial.class)
    public ResponseJson findClCustomMaterialById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClCustomMaterial clCustomMaterial=clCustomMaterialService.findClCustomMaterialById(id);
        return new ResponseJson(clCustomMaterial);
    }

    @PostMapping("/update/updateClCustomMaterial")
    @ApiOperation(value = "修改自定义材料表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateClCustomMaterial(
            @ApiParam(value = "被修改的自定义材料表对象,对象属性不为空则修改", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.updateClCustomMaterial(clCustomMaterial);
        return new ResponseJson();
    }

    @PostMapping("/update/updateClCustomMaterialForAll")
    @ApiOperation(value = "修改自定义材料表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateClCustomMaterialForAll(
            @ApiParam(value = "被修改的自定义材料表对象", required = true)
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterialService.updateClCustomMaterialForAll(clCustomMaterial);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClCustomMaterialById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找自定义材料表", notes = "返回响应对象", response=ClCustomMaterial.class)
    public ResponseJson lookClCustomMaterialById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClCustomMaterial clCustomMaterial=clCustomMaterialService.findClCustomMaterialById(id);
        return new ResponseJson(clCustomMaterial);
    }

    @PostMapping("/findClCustomMaterialsByCondition")
    @ApiOperation(value = "根据条件查找自定义材料表", notes = "返回响应对象", response=ClCustomMaterial.class)
    public ResponseJson findClCustomMaterialsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterial.setSchoolId(LoginInterceptor.mySchoolId());
        clCustomMaterial.setType(3);
        Pager pager = new Pager();
        pager.setLike("cname");
        ClWeight clWeight = new ClWeight();
        clWeight.setSchoolId(LoginInterceptor.mySchoolId());
        clWeight.setStuOrTea(clCustomMaterial.getStuOrTea());
        clWeight.setType(clCustomMaterial.getType());
        clWeight = clWeightService.findOneClWeightByCondition(clWeight);
        List<ClCustomMaterial> data = clCustomMaterialService.findClCustomMaterialListByConditionKong(clCustomMaterial);
        long count = clCustomMaterialService.findClCustomMaterialCountByCondition(clCustomMaterial);
        return new ResponseJson(data,count,clWeight==null?0:clWeight.getWeight());
    }
    @PostMapping("/findOneClCustomMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个自定义材料表,结果必须为单条数据", notes = "没有时返回空", response=ClCustomMaterial.class)
    public ResponseJson findOneClCustomMaterialByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClCustomMaterial clCustomMaterial){
        ClCustomMaterial one=clCustomMaterialService.findOneClCustomMaterialByCondition(clCustomMaterial);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteClCustomMaterial/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClCustomMaterial(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ClCustomMaterialData clCustomMaterialData = new ClCustomMaterialData();
        clCustomMaterialData.setSchoolId(LoginInterceptor.mySchoolId());
        clCustomMaterialData.setParentId(id);
        long l = clCustomMaterialDataService.findClCustomMaterialDataCountByCondition(clCustomMaterialData);
        if(l>0){
            return new ResponseJson(false,"分类内有材料信息，无法删除，请先清空分类内的材料，再进行删除");
        }
        clCustomMaterialService.deleteClCustomMaterial(id);
        return new ResponseJson();
    }


    @PostMapping("/findClCustomMaterialListByCondition")
    @ApiOperation(value = "根据条件查找自定义材料表列表", notes = "返回响应对象,不包含总条数", response=ClCustomMaterial.class)
    public ResponseJson findClCustomMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClCustomMaterial clCustomMaterial){
        clCustomMaterial.setSchoolId(LoginInterceptor.mySchoolId());
        List<ClCustomMaterial> data=clCustomMaterialService.findClCustomMaterialListByCondition(clCustomMaterial);
        return new ResponseJson(data);
    }

    @PostMapping("ignore/uploadClCustomMaterial")
    public FileObj uploadClCustomMaterial(@RequestParam("file") MultipartFile file){

        FileObj fileObj = new FileObj();
        try{
            int size = 1024*1024*10;
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadClCustomMaterialFile(file, Constant.Upload.CL_CUSTOM_MATERIAL,size));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return fileObj;
    }

    @GetMapping("/ignore/exportTemplate/{stuOrTea}")
    public void exportTemplate(HttpServletResponse response,@PathVariable("stuOrTea") Integer stuOrTea){
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=clCustomMaterial.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = clCustomMaterialService.exportTemplate(stuOrTea);
            workbook.write(s);
        } catch (Exception e) {

        }
    }



}
