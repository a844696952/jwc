package com.yice.edu.cn.pcd.service.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import com.yice.edu.cn.pcd.feignClient.pcdData.PcdStudentDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdStudentDataService {
    @Autowired
    private PcdStudentDataFeign pcdStudentDataFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdStudentData findPcdStudentDataById(String id) {
        return pcdStudentDataFeign.findPcdStudentDataById(id);
    }

    public PcdStudentData savePcdStudentData(PcdStudentData pcdStudentData) {
        return pcdStudentDataFeign.savePcdStudentData(pcdStudentData);
    }

    public void batchSavePcdStudentData(List<PcdStudentData> pcdStudentDatas){
        pcdStudentDataFeign.batchSavePcdStudentData(pcdStudentDatas);
    }

    public List<PcdStudentData> findPcdStudentDataListByCondition(PcdStudentData pcdStudentData) {
        return pcdStudentDataFeign.findPcdStudentDataListByCondition(pcdStudentData);
    }

    public PcdStudentData findOnePcdStudentDataByCondition(PcdStudentData pcdStudentData) {
        return pcdStudentDataFeign.findOnePcdStudentDataByCondition(pcdStudentData);
    }

    public long findPcdStudentDataCountByCondition(PcdStudentData pcdStudentData) {
        return pcdStudentDataFeign.findPcdStudentDataCountByCondition(pcdStudentData);
    }

    public void updatePcdStudentData(PcdStudentData pcdStudentData) {
        pcdStudentDataFeign.updatePcdStudentData(pcdStudentData);
    }

    public void updatePcdStudentDataForAll(PcdStudentData pcdStudentData) {
        pcdStudentDataFeign.updatePcdStudentDataForAll(pcdStudentData);
    }

    public void deletePcdStudentData(String id) {
        pcdStudentDataFeign.deletePcdStudentData(id);
    }

    public void deletePcdStudentDataByCondition(PcdStudentData pcdStudentData) {
        pcdStudentDataFeign.deletePcdStudentDataByCondition(pcdStudentData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePcdStudentDataKong(PcdStudentData pcdStudentData){
        pcdStudentDataFeign.savePcdStudentDataKong(pcdStudentData);
    }
}
