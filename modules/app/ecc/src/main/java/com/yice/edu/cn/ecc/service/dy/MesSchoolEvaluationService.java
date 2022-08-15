package com.yice.edu.cn.ecc.service.dy;

import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.ecc.feignClient.dy.MesSchoolEvaluationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesSchoolEvaluationService {
    @Autowired
    private MesSchoolEvaluationFeign mesSchoolEvaluationFeign;

    public List<List<MesClassHonor>> findMesSchoolEvaluationHonor(MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationFeign.findMesSchoolEvaluationHonor(mesClassHonor);
    }

    public List<MesClassHonor> findMesSchoolEvaluationHonorNewest(MesClassHonor mesClassHonor) {
        return mesSchoolEvaluationFeign.findMesSchoolEvaluationHonorNewest(mesClassHonor);
    }
}
