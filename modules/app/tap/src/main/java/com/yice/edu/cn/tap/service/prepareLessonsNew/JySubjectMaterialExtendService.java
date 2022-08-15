package com.yice.edu.cn.tap.service.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import com.yice.edu.cn.tap.feignClient.prepareLessonsNew.JySubjectMaterialExtendFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JySubjectMaterialExtendService {
    @Autowired
    private JySubjectMaterialExtendFeign jySubjectMaterialExtendFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public JySubjectMaterialExtend findJySubjectMaterialExtendById(String id) {
        return jySubjectMaterialExtendFeign.findJySubjectMaterialExtendById(id);
    }

    public JySubjectMaterialExtend saveJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendFeign.saveJySubjectMaterialExtend(jySubjectMaterialExtend);
    }

    public void batchSaveJySubjectMaterialExtend(List<JySubjectMaterialExtend> jySubjectMaterialExtends){
        jySubjectMaterialExtendFeign.batchSaveJySubjectMaterialExtend(jySubjectMaterialExtends);
    }

    public List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendFeign.findJySubjectMaterialExtendListByCondition(jySubjectMaterialExtend);
    }

    public JySubjectMaterialExtend findOneJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendFeign.findOneJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }

    public long findJySubjectMaterialExtendCountByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        return jySubjectMaterialExtendFeign.findJySubjectMaterialExtendCountByCondition(jySubjectMaterialExtend);
    }

    public void updateJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendFeign.updateJySubjectMaterialExtend(jySubjectMaterialExtend);
    }

    public void updateJySubjectMaterialExtendForAll(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendFeign.updateJySubjectMaterialExtendForAll(jySubjectMaterialExtend);
    }

    public void deleteJySubjectMaterialExtend(String id) {
        jySubjectMaterialExtendFeign.deleteJySubjectMaterialExtend(id);
    }

    public void deleteJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend) {
        jySubjectMaterialExtendFeign.deleteJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
