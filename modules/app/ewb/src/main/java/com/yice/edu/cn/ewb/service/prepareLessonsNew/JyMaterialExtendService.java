package com.yice.edu.cn.ewb.service.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.ewb.feignClient.prepareLessonsNew.JyMaterialExtendFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyMaterialExtendService {
    @Autowired
    private JyMaterialExtendFeign jyMaterialExtendFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public JyMaterialExtend findJyMaterialExtendById(String id) {
        return jyMaterialExtendFeign.findJyMaterialExtendById(id);
    }

    public JyMaterialExtend saveJyMaterialExtend(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendFeign.saveJyMaterialExtend(jyMaterialExtend);
    }

    public void batchSaveJyMaterialExtend(List<JyMaterialExtend> jyMaterialExtends){
        jyMaterialExtendFeign.batchSaveJyMaterialExtend(jyMaterialExtends);
    }

    public List<JyMaterialExtend> findJyMaterialExtendListByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendFeign.findJyMaterialExtendListByCondition(jyMaterialExtend);
    }

    public JyMaterialExtend findOneJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendFeign.findOneJyMaterialExtendByCondition(jyMaterialExtend);
    }

    public long findJyMaterialExtendCountByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendFeign.findJyMaterialExtendCountByCondition(jyMaterialExtend);
    }

    public void updateJyMaterialExtend(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendFeign.updateJyMaterialExtend(jyMaterialExtend);
    }

    public void updateJyMaterialExtendForAll(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendFeign.updateJyMaterialExtendForAll(jyMaterialExtend);
    }

    public void deleteJyMaterialExtend(String id) {
        jyMaterialExtendFeign.deleteJyMaterialExtend(id);
    }

    public void deleteJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendFeign.deleteJyMaterialExtendByCondition(jyMaterialExtend);
    }

    public List<Material> findMaterialListByCondition(JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendFeign.findMaterialListByCondition(jyMaterialExtend);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
