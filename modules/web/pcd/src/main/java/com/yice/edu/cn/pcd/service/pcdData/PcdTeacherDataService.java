package com.yice.edu.cn.pcd.service.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import com.yice.edu.cn.pcd.feignClient.pcdData.PcdTeacherDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdTeacherDataService {
    @Autowired
    private PcdTeacherDataFeign pcdTeacherDataFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdTeacherData findPcdTeacherDataById(String id) {
        return pcdTeacherDataFeign.findPcdTeacherDataById(id);
    }

    public PcdTeacherData savePcdTeacherData(PcdTeacherData pcdTeacherData) {
        return pcdTeacherDataFeign.savePcdTeacherData(pcdTeacherData);
    }

    public void batchSavePcdTeacherData(List<PcdTeacherData> pcdTeacherDatas){
        pcdTeacherDataFeign.batchSavePcdTeacherData(pcdTeacherDatas);
    }

    public List<PcdTeacherData> findPcdTeacherDataListByCondition(PcdTeacherData pcdTeacherData) {
        return pcdTeacherDataFeign.findPcdTeacherDataListByCondition(pcdTeacherData);
    }

    public PcdTeacherData findOnePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData) {
        return pcdTeacherDataFeign.findOnePcdTeacherDataByCondition(pcdTeacherData);
    }

    public long findPcdTeacherDataCountByCondition(PcdTeacherData pcdTeacherData) {
        return pcdTeacherDataFeign.findPcdTeacherDataCountByCondition(pcdTeacherData);
    }

    public void updatePcdTeacherData(PcdTeacherData pcdTeacherData) {
        pcdTeacherDataFeign.updatePcdTeacherData(pcdTeacherData);
    }

    public void updatePcdTeacherDataForAll(PcdTeacherData pcdTeacherData) {
        pcdTeacherDataFeign.updatePcdTeacherDataForAll(pcdTeacherData);
    }

    public void deletePcdTeacherData(String id) {
        pcdTeacherDataFeign.deletePcdTeacherData(id);
    }

    public void deletePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData) {
        pcdTeacherDataFeign.deletePcdTeacherDataByCondition(pcdTeacherData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePcdTeacherDataKong(PcdTeacherData pcdTeacherData){
        pcdTeacherDataFeign.savePcdTeacherDataKong(pcdTeacherData);
    }
}
