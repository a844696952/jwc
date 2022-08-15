package com.yice.edu.cn.osp.service.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial.ClCustomMaterialDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClCustomMaterialDataService {
    @Autowired
    private ClCustomMaterialDataFeign clCustomMaterialDataFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClCustomMaterialData findClCustomMaterialDataById(String id) {
        return clCustomMaterialDataFeign.findClCustomMaterialDataById(id);
    }

    public ClCustomMaterialData saveClCustomMaterialData(ClCustomMaterialData clCustomMaterialData) {
        return clCustomMaterialDataFeign.saveClCustomMaterialData(clCustomMaterialData);
    }

    public void batchSaveClCustomMaterialData(List<ClCustomMaterialData> clCustomMaterialDatas){
        clCustomMaterialDataFeign.batchSaveClCustomMaterialData(clCustomMaterialDatas);
    }

    public List<ClCustomMaterialData> findClCustomMaterialDataListByCondition(ClCustomMaterialData clCustomMaterialData) {
        return clCustomMaterialDataFeign.findClCustomMaterialDataListByCondition(clCustomMaterialData);
    }

    public ClCustomMaterialData findOneClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData) {
        return clCustomMaterialDataFeign.findOneClCustomMaterialDataByCondition(clCustomMaterialData);
    }

    public long findClCustomMaterialDataCountByCondition(ClCustomMaterialData clCustomMaterialData) {
        return clCustomMaterialDataFeign.findClCustomMaterialDataCountByCondition(clCustomMaterialData);
    }

    public void updateClCustomMaterialData(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialDataFeign.updateClCustomMaterialData(clCustomMaterialData);
    }

    public void updateClCustomMaterialDataForAll(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialDataFeign.updateClCustomMaterialDataForAll(clCustomMaterialData);
    }

    public void deleteClCustomMaterialData(String id) {
        clCustomMaterialDataFeign.deleteClCustomMaterialData(id);
    }

    public void deleteClCustomMaterialDataByCondition(ClCustomMaterialData clCustomMaterialData) {
        clCustomMaterialDataFeign.deleteClCustomMaterialDataByCondition(clCustomMaterialData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public Long findClCustomMaterialDataMaxWeight(ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataFeign.findClCustomMaterialDataMaxWeight(clCustomMaterialData);
    }

    public void saveClCustomMaterialDataAndClWeight(ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataFeign.saveClCustomMaterialDataAndClWeight(clCustomMaterialData);
    }

    public List<ClCustomMaterialData> findClCustomMaterialDataListByConditionKong(ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataFeign.findClCustomMaterialDataListByConditionKong(clCustomMaterialData);
    }

    public long findClCustomMaterialDataCountByConditionKong(ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataFeign.findClCustomMaterialDataCountByConditionKong(clCustomMaterialData);
    }

}
