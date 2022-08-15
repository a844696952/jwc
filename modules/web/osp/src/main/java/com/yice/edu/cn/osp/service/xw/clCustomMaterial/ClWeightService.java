package com.yice.edu.cn.osp.service.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import com.yice.edu.cn.osp.feignClient.xw.clCustomMaterial.ClWeightFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClWeightService {
    @Autowired
    private ClWeightFeign clWeightFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClWeight findClWeightById(String id) {
        return clWeightFeign.findClWeightById(id);
    }

    public ClWeight saveClWeight(ClWeight clWeight) {
        return clWeightFeign.saveClWeight(clWeight);
    }

    public void batchSaveClWeight(List<ClWeight> clWeights){
        clWeightFeign.batchSaveClWeight(clWeights);
    }

    public List<ClWeight> findClWeightListByCondition(ClWeight clWeight) {
        return clWeightFeign.findClWeightListByCondition(clWeight);
    }

    public ClWeight findOneClWeightByCondition(ClWeight clWeight) {
        return clWeightFeign.findOneClWeightByCondition(clWeight);
    }

    public long findClWeightCountByCondition(ClWeight clWeight) {
        return clWeightFeign.findClWeightCountByCondition(clWeight);
    }

    public void updateClWeight(ClWeight clWeight) {
        clWeightFeign.updateClWeight(clWeight);
    }

    public void updateClWeightForAll(ClWeight clWeight) {
        clWeightFeign.updateClWeightForAll(clWeight);
    }

    public void deleteClWeight(String id) {
        clWeightFeign.deleteClWeight(id);
    }

    public void deleteClWeightByCondition(ClWeight clWeight) {
        clWeightFeign.deleteClWeightByCondition(clWeight);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public Long addMaxWeight(ClWeight clWeight){
        return clWeightFeign.addMaxWeight(clWeight);
    }
}
