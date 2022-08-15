package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesSchoolEvaluationVo;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesSchoolEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@RestController
@RequestMapping("/mesSchoolEvaluation")
@Api(value = "/mesSchoolEvaluation", description = "学校评比")
public class MesSchoolEvaluationController {

    @Autowired
    private MesSchoolEvaluationService mesSchoolEvaluationService;

    @GetMapping("/saveMesSchoolEvaluation")
    @ApiOperation(value = "保存学校日评比", notes = "空")
    public void saveMesSchoolEvaluation() {
        mesSchoolEvaluationService.saveMesSchoolEvaluation();
    }

    @GetMapping("/saveMesSchoolEvaluationHonor")
    @ApiOperation(value = "保存德育评比荣誉", notes = "空")
    public void saveMesSchoolEvaluationHonor() {
        mesSchoolEvaluationService.saveMesSchoolEvaluationHonor();
    }


    @GetMapping("/saveMesSchoolEvaluationHonorByAdmin/{time}")
    @ApiOperation(value = "保存德育评比荣誉", notes = "空")
    public void saveMesSchoolEvaluationHonorByAdmin(@PathVariable("time") String time) {
        mesSchoolEvaluationService.saveMesSchoolEvaluationHonorByAdmin(time);
    }

    @PostMapping("/findMesSchoolEvaluationHonor")
    @ApiOperation(value = "查询德育评比荣誉")
    public List<List<MesClassHonor>> findMesSchoolEvaluationHonor(@RequestBody MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationService.findMesSchoolEvaluationHonor(mesClassHonor);
    }

    @PostMapping("/findMesSchoolEvaluationHonorNewest")
    @ApiOperation(value = "查询德育评比荣誉")
    public List<MesClassHonor> findMesSchoolEvaluationHonorNewest(@RequestBody MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationService.findMesSchoolEvaluationHonorNewest(mesClassHonor);
    }

    @GetMapping("/saveMesSchoolEvaluationByAdmin/{time}")
    @ApiOperation(value = "如果定时任务异常，可以手动保存学校日评比数据，参数：'2019-09-01'", notes = "空")
    public void saveMesSchoolEvaluationByAdmin(
            @ApiParam(value = "开始时间", required = true)
            @PathVariable("time") String time) {
        mesSchoolEvaluationService.saveMesSchoolEvaluationByAdmin(time);
    }

    @GetMapping("/selectTableWhetherToShow/{schoolId}")
    @ApiOperation(value = "通过学校id查找table页是否展示", notes = "返回公共配置表对象")
    public List<MesInstitution> selectTableWhetherToShow(
            @ApiParam(value = "学校id", required = true)
            @PathVariable("schoolId") String schoolId) {
        return mesSchoolEvaluationService.selectTableWhetherToShow(schoolId);
    }

    @PostMapping("/findMesSchoolEvaluationListByCondition")
    @ApiOperation(value = "查询学校评比分数列表")
    public Map findMesSchoolEvaluationListByCondition(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationService.findMesSchoolEvaluationListByCondition(mesSchoolEvaluationVo);
    }

    @PostMapping("/findEvaluationListByDay")
    @ApiOperation(value = "学校评比查询对象")
    public Map<String, Object> findEvaluationListByDay(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationService.findEvaluationListByDay(mesSchoolEvaluationVo);
    }


    @PostMapping("/findSchoolWeekOrMonthByCondition")
    @ApiOperation(value = "学期周数月数查询对象")
    public Map findSchoolWeekOrMonthByCondition(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        return mesSchoolEvaluationService.findSchoolWeekOrMonthByCondition(mesSchoolEvaluationVo);
    }
}
