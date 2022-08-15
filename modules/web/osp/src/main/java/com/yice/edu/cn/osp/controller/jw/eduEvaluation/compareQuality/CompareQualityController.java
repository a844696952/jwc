package com.yice.edu.cn.osp.controller.jw.eduEvaluation.compareQuality;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.eduEvaluation.CompareQualityDetailService;
import com.yice.edu.cn.osp.service.jw.eduEvaluation.CompareQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import static com.yice.edu.cn.common.easypoiplus.ExcelUtil.getDataValidationByExplicitListValues;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/compareQuality")
@Api(value = "/compareQuality",description = "综合素质表模块")
public class CompareQualityController {
    @Autowired
    private DdService ddService;

    @Autowired
    private CompareQualityService compareQualityService;
    @Autowired
    private CompareQualityDetailService compareQualityDetailService;

    @PostMapping("/save/saveCompareQuality")
    @ApiOperation(value = "保存综合素质表对象", notes = "返回保存好的综合素质表对象", response=CompareQuality.class)
    public ResponseJson saveCompareQuality(
            @ApiParam(value = "综合素质表对象", required = true)
            @RequestBody CompareQuality compareQuality){
       compareQuality.setSchoolId(mySchoolId());
        CompareQuality s=compareQualityService.saveCompareQuality(compareQuality);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCompareQualityById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找综合素质表", notes = "返回响应对象", response=CompareQuality.class)
    public ResponseJson findCompareQualityById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CompareQuality compareQuality=compareQualityService.findCompareQualityById(id);
        return new ResponseJson(compareQuality);
    }

    @PostMapping("/update/updateCompareQuality")
    @ApiOperation(value = "修改综合素质表对象", notes = "返回响应对象")
    public ResponseJson updateCompareQuality(
            @ApiParam(value = "被修改的综合素质表对象,对象属性不为空则修改", required = true)
            @RequestBody CompareQuality compareQuality){
        compareQualityService.updateCompareQuality(compareQuality);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCompareQualityById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找综合素质表", notes = "返回响应对象", response=CompareQuality.class)
    public ResponseJson lookCompareQualityById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CompareQuality compareQuality=compareQualityService.findCompareQualityById(id);
        return new ResponseJson(compareQuality);
    }

    @PostMapping("/find/findCompareQualitysByCondition")
    @ApiOperation(value = "根据条件查找综合素质表", notes = "返回响应对象", response=CompareQuality.class)
    public ResponseJson findCompareQualitysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQuality compareQuality){
       compareQuality.setSchoolId(mySchoolId());
        List<CompareQuality> data=compareQualityService.findCompareQualityListByCondition(compareQuality);
        long count=compareQualityService.findCompareQualityCountByCondition(compareQuality);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneCompareQualityByCondition")
    @ApiOperation(value = "根据条件查找单个综合素质表,结果必须为单条数据", notes = "没有时返回空", response=CompareQuality.class)
    public ResponseJson findOneCompareQualityByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CompareQuality compareQuality){
        CompareQuality one=compareQualityService.findOneCompareQualityByCondition(compareQuality);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCompareQuality/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCompareQuality(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        compareQualityService.deleteCompareQuality(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findCompareQualityListByCondition")
    @ApiOperation(value = "根据条件查找综合素质表列表", notes = "返回响应对象,不包含总条数", response=CompareQuality.class)
    public ResponseJson findCompareQualityListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQuality compareQuality){
       compareQuality.setSchoolId(mySchoolId());
        List<CompareQuality> data=compareQualityService.findCompareQualityListByCondition(compareQuality);
        return new ResponseJson(data);
    }


    @GetMapping("/find/exportTemplate")
    @ApiOperation(value = "导出模板", notes = "返回响应对象,不包含总条数", response=CompareQuality.class)
    public void exportTemplate(HttpServletResponse response){

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=compareQuality.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = compareQualityService.exportTemplate();
            createCustomRowAndName(workbook);
            workbook.write(s);
        } catch (Exception e) {

        }

    }

    //生成下拉框
    private void createCustomRowAndName(Workbook workbook) {
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        data.sort(Comparator.comparing(e -> Integer.valueOf(e.getId())));
        Set<String> gradeNameSet=new LinkedHashSet<>();
        data.forEach(data1 ->{
            gradeNameSet.add(data1.getName());
        } );
        final Sheet sheet1 = workbook.getSheet("综合素质");
        final DataValidation dataValidation1 = getDataValidationByExplicitListValues(gradeNameSet, 2, 0, 1, 0);
        sheet1.addValidationData(dataValidation1);

        Set<String> classNameSet=new LinkedHashSet<>();
        for (int i = 1; i <21; i++) {
            classNameSet.add(i+"");
        }
        final DataValidation dataValidation2 = getDataValidationByExplicitListValues(classNameSet

                , 2, 0, 2, 0);
        sheet1.addValidationData(dataValidation2);

    }


    @PostMapping("/upload/uploadCompareQuality")
    public ResponseJson uploadCompareQuality(@RequestParam("gradeName")String gradeName,@RequestParam("id")String id,@RequestParam("file")MultipartFile file) {
        return new ResponseJson(compareQualityService.uploadCompareQuality(file,gradeName,id));
    }



    @PostMapping("/find/findCompareQualityDetailsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail){
        List<CompareQualityDetail> data=compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);
        long count=compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
        return new ResponseJson(data,count);
    }

    @PostMapping("/update/updateCompareQualityDetail")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateCompareQualityDetail(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetailService.updateCompareQualityDetail(compareQualityDetail);
        return new ResponseJson();
    }


    @PostMapping("/delete/deleteCompareQualityDetailByIdList")
    @ApiOperation(value = "根据id集合删除", notes = "返回响应对象")
    public ResponseJson deleteCompareQualityDetailByIdList(
            @ApiParam(value = "被删除记录的id集合", required = true)
            @RequestBody List<String> idList){
        compareQualityDetailService.deleteCompareQualityDetailByIdList(idList);
        return new ResponseJson();
    }

    @PostMapping("/find/getClassTypeList")
    @ApiOperation(value = "根据id查找班级类型列表", notes = "返回响应对象")
    public ResponseJson getClassTypeList(
            @ApiParam(value = "档案的id", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        List<String> classNameList=compareQualityDetailService.getClassTypeList(compareQualityDetail);
        return new ResponseJson(classNameList);
    }


    @PostMapping("/find/findCompareQualityDetailCountByCondition")
    @ApiOperation(value = "根据条件查找数量", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityDetailCountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail){
        long count=compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
        return new ResponseJson(count);
    }

    @PostMapping("/ignore/findCompareQualityForStu")
    @ApiOperation(value = "传入学籍号:stuNum", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityForStu(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail) {
        if (StrUtil.isEmpty(compareQualityDetail.getStuNum())) {
            return new ResponseJson(CollUtil.newArrayList(), 0);
        }
        compareQualityDetail.setSchoolId(mySchoolId());
        List<CompareQualityDetail> data = compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);
        long count = compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
        return new ResponseJson(data, count);
    }


    @PostMapping("/delete/deleteCompareQualityByIdList")
    @ApiOperation(value = "根据id集合删除", notes = "返回响应对象")
    public ResponseJson deleteCompareQualityByIdList(
            @ApiParam(value = "被删除记录的id集合", required = true)
            @RequestBody List<String> idList){
        compareQualityService.deleteCompareQualityByIdList(idList);
        return new ResponseJson();
    }

    @PostMapping("/find/findCompareQualityCountByCondition")
    @ApiOperation(value = "根据条件查找综合素质表", notes = "返回响应对象", response=CompareQuality.class)
    public ResponseJson findCompareQualityCountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQuality compareQuality){
        compareQuality.setSchoolId(mySchoolId());
        long count=compareQualityService.findCompareQualityCountByCondition(compareQuality);
        return new ResponseJson(count);
    }


    @PostMapping("/moveCompareQualityDataToHistory")
    @ApiOperation(value = "根据班级id移动综合素质成绩的数据到历史记录表")
    public ResponseJson  moveCompareQualityDataToHistory(
            @ApiParam(value = "综合素质表集合对象")
            @RequestBody List<String> classIdList){
        compareQualityService.moveCompareQualityDataToHistory(classIdList);
        return new ResponseJson(null);
    }

}
