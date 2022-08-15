package com.yice.edu.cn.pcd.service.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdEducationData;
import com.yice.edu.cn.pcd.feignClient.pcdData.PcdEducationDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PcdEducationDataService {
    @Autowired
    private PcdEducationDataFeign pcdEducationDataFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdEducationData findPcdEducationDataById(String id) {
        return pcdEducationDataFeign.findPcdEducationDataById(id);
    }

    public PcdEducationData savePcdEducationData(PcdEducationData pcdEducationData) {
        return pcdEducationDataFeign.savePcdEducationData(pcdEducationData);
    }

    public void batchSavePcdEducationData(List<PcdEducationData> pcdEducationDatas){
        pcdEducationDataFeign.batchSavePcdEducationData(pcdEducationDatas);
    }

    public List<PcdEducationData> findPcdEducationDataListByCondition(PcdEducationData pcdEducationData) {
        return pcdEducationDataFeign.findPcdEducationDataListByCondition(pcdEducationData);
    }

    public PcdEducationData findOnePcdEducationDataByCondition(PcdEducationData pcdEducationData) {
        return pcdEducationDataFeign.findOnePcdEducationDataByCondition(pcdEducationData);
    }

    public long findPcdEducationDataCountByCondition(PcdEducationData pcdEducationData) {
        return pcdEducationDataFeign.findPcdEducationDataCountByCondition(pcdEducationData);
    }

    public void updatePcdEducationData(PcdEducationData pcdEducationData) {
        pcdEducationDataFeign.updatePcdEducationData(pcdEducationData);
    }

    public void updatePcdEducationDataForAll(PcdEducationData pcdEducationData) {
        pcdEducationDataFeign.updatePcdEducationDataForAll(pcdEducationData);
    }

    public void deletePcdEducationData(String id) {
        pcdEducationDataFeign.deletePcdEducationData(id);
    }

    public void deletePcdEducationDataByCondition(PcdEducationData pcdEducationData) {
        pcdEducationDataFeign.deletePcdEducationDataByCondition(pcdEducationData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePcdEducationDataKong(PcdEducationData pcdEducationData){
        pcdEducationDataFeign.savePcdEducationDataKong(pcdEducationData);
    }

    public Map<String,Object> findIndexDataByEehId(String eehId){
        return pcdEducationDataFeign.findIndexDataByEehId(eehId);
    }
}
