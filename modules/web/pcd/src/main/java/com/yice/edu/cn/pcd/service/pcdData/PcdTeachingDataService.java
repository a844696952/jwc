package com.yice.edu.cn.pcd.service.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import com.yice.edu.cn.pcd.feignClient.pcdData.PcdTeachingDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdTeachingDataService {
    @Autowired
    private PcdTeachingDataFeign pcdTeachingDataFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdTeachingData findPcdTeachingDataById(String id) {
        return pcdTeachingDataFeign.findPcdTeachingDataById(id);
    }

    public PcdTeachingData savePcdTeachingData(PcdTeachingData pcdTeachingData) {
        return pcdTeachingDataFeign.savePcdTeachingData(pcdTeachingData);
    }

    public void batchSavePcdTeachingData(List<PcdTeachingData> pcdTeachingDatas){
        pcdTeachingDataFeign.batchSavePcdTeachingData(pcdTeachingDatas);
    }

    public List<PcdTeachingData> findPcdTeachingDataListByCondition(PcdTeachingData pcdTeachingData) {
        return pcdTeachingDataFeign.findPcdTeachingDataListByCondition(pcdTeachingData);
    }

    public PcdTeachingData findOnePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData) {
        return pcdTeachingDataFeign.findOnePcdTeachingDataByCondition(pcdTeachingData);
    }

    public long findPcdTeachingDataCountByCondition(PcdTeachingData pcdTeachingData) {
        return pcdTeachingDataFeign.findPcdTeachingDataCountByCondition(pcdTeachingData);
    }

    public void updatePcdTeachingData(PcdTeachingData pcdTeachingData) {
        pcdTeachingDataFeign.updatePcdTeachingData(pcdTeachingData);
    }

    public void updatePcdTeachingDataForAll(PcdTeachingData pcdTeachingData) {
        pcdTeachingDataFeign.updatePcdTeachingDataForAll(pcdTeachingData);
    }

    public void deletePcdTeachingData(String id) {
        pcdTeachingDataFeign.deletePcdTeachingData(id);
    }

    public void deletePcdTeachingDataByCondition(PcdTeachingData pcdTeachingData) {
        pcdTeachingDataFeign.deletePcdTeachingDataByCondition(pcdTeachingData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePcdTeachingDataKong(PcdTeachingData pcdTeachingData){
        pcdTeachingDataFeign.savePcdTeachingDataKong(pcdTeachingData);
    }

    public PcdTeachingData findPcdTeachingDataByIdKong(String id){
        return pcdTeachingDataFeign.findPcdTeachingDataByIdKong(id);
    }
}
