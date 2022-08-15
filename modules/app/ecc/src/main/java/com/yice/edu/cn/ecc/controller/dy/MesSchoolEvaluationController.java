package com.yice.edu.cn.ecc.controller.dy;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.ecc.service.dy.MesSchoolEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/mesSchoolEvaluation")
@Api(value = "/mesSchoolEvaluation", description = "学校评比")
public class MesSchoolEvaluationController {

    @Autowired
    private MesSchoolEvaluationService mesSchoolEvaluationService;

    @PostMapping("/findMesSchoolEvaluationHonor")
    @ApiOperation(value = "查询德育评比荣誉")
    public ResponseJson findMesSchoolEvaluationHonor(@RequestBody MesClassHonor mesClassHonor) {
        mesClassHonor.setSchoolId(mySchoolId());
        List<List<MesClassHonor>> data= mesSchoolEvaluationService.findMesSchoolEvaluationHonor(mesClassHonor);
        return new ResponseJson(data);
    }

    @PostMapping("/findMesSchoolEvaluationHonorNewest")
    @ApiOperation(value = "查询德育评比荣誉")
    public ResponseJson findMesSchoolEvaluationHonorNewest(@RequestBody MesClassHonor mesClassHonor) {
        mesClassHonor.setSchoolId(mySchoolId());
        List<MesClassHonor> data= mesSchoolEvaluationService.findMesSchoolEvaluationHonorNewest(mesClassHonor);
        return new ResponseJson(data);
    }
}
