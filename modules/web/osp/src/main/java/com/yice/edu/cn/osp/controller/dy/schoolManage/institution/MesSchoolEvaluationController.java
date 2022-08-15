package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesSchoolEvaluationVo;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesSchoolEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesSchoolEvaluation")
@Api(value = "/mesSchoolEvaluation", description = "学校评比")
public class MesSchoolEvaluationController {

    @Autowired
    private MesSchoolEvaluationService mesSchoolEvaluationService;

    @PostMapping("/findMesSchoolEvaluationListByCondition")
    @ApiOperation(value = "查询学校评比分数列表")
    public ResponseJson findMesSchoolEvaluationListByCondition(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        mesSchoolEvaluationVo.setSchoolId(mySchoolId());
        Map map = mesSchoolEvaluationService.findMesSchoolEvaluationListByCondition(mesSchoolEvaluationVo);
        if (map.get("msg") != null) {
            return new ResponseJson(false, (String) map.get("msg"));
        }
        return new ResponseJson(map);
    }

    @PostMapping("/exportMesSchoolEvaluation")
    @ApiOperation(value = "根据id导出Excel", notes = "返回响应对象")
    public void exportMesSchoolEvaluation(
            @ApiParam(value = "被删除记录的id", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo, HttpServletResponse response) {
        mesSchoolEvaluationVo.setSchoolId(mySchoolId());
        Workbook workbook = mesSchoolEvaluationService.exportMesSchoolEvaluation(mesSchoolEvaluationVo);
        response.setHeader("Content-Disposition", "attachment;filename=Excel.xls");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/saveMesSchoolEvaluationHonorByAdmin/{time}")
    @ApiOperation(value = "保存德育评比荣誉", notes = "空")
    public void saveMesSchoolEvaluationHonorByAdmin(@PathVariable("time") String time) {
        mesSchoolEvaluationService.saveMesSchoolEvaluationHonorByAdmin(time);
    }

    @PostMapping("/findSchoolWeekOrMonthByCondition")
    @ApiOperation(value = "学校评比查询当前学期的周或月数")
    public ResponseJson findSchoolWeekOrMonthByCondition(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        mesSchoolEvaluationVo.setSchoolId(mySchoolId());
        Map map = mesSchoolEvaluationService.findSchoolWeekOrMonthByCondition(mesSchoolEvaluationVo);
        if (map.get("msg") != null) {
            return new ResponseJson(false, (String) map.get("msg"));
        }
        return new ResponseJson(map);
    }

    @PostMapping("/findEvaluationListByDay")
    @ApiOperation(value = "查询日评比列表")
    public ResponseJson findEvaluationListByDay(
            @ApiParam(value = "学校评比查询对象", required = true)
            @RequestBody MesSchoolEvaluationVo mesSchoolEvaluationVo) {
        mesSchoolEvaluationVo.setSchoolId(mySchoolId());
        Map<String, Object> data = mesSchoolEvaluationService.findEvaluationListByDay(mesSchoolEvaluationVo);
        return new ResponseJson(data);
    }

    @GetMapping("/saveMesSchoolEvaluationByAdmin/{time}")
    @ApiOperation(value = "如果定时任务异常，可以手动保存学校日评比数据，参数：'2019-09-01'")
    public void saveMesSchoolEvaluationByAdmin(
            @ApiParam(value = "开始时间", required = true)
            @PathVariable("time") String time)  {
        mesSchoolEvaluationService.saveMesSchoolEvaluationByAdmin(time);
    }

    @GetMapping("/saveMesSchoolEvaluationHonor")
    @ApiOperation(value = "保存德育评比荣誉")
    public void saveMesSchoolEvaluationHonor() {
        mesSchoolEvaluationService.saveMesSchoolEvaluationHonor();
    }

    @GetMapping("/selectTableWhetherToShow")
    @ApiOperation(value = "查询周/月/学期table页是否展示")
    public ResponseJson selectTableWhetherToShow() {
        List<MesInstitution> data = mesSchoolEvaluationService.selectTableWhetherToShow(mySchoolId());
        return new ResponseJson(data);
    }

    @PostMapping("/findMesSchoolEvaluationHonor")
    @ApiOperation(value = "保存德育评比荣誉")
    public ResponseJson findMesSchoolEvaluationHonor(@RequestBody MesClassHonor mesClassHonor) {
        mesClassHonor.setSchoolId(mySchoolId());
        List<List<MesClassHonor>> data= mesSchoolEvaluationService.findMesSchoolEvaluationHonor(mesClassHonor);
        return new ResponseJson(data);
    }

    @PostMapping("/findMesSchoolEvaluationHonorNewest")
    @ApiOperation(value = "保存德育评比荣誉")
    public ResponseJson findMesSchoolEvaluationHonorNewest(@RequestBody MesClassHonor mesClassHonor) {
        mesClassHonor.setSchoolId(mySchoolId());
        List<MesClassHonor> data= mesSchoolEvaluationService.findMesSchoolEvaluationHonorNewest(mesClassHonor);
        return new ResponseJson(data);
    }
}
