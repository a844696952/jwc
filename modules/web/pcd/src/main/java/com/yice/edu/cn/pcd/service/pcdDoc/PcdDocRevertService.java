package com.yice.edu.cn.pcd.service.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.pcd.feignClient.pcdDoc.PcdDocRevertFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdDocRevertService {
    @Autowired
    private PcdDocRevertFeign pcdDocRevertFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdDocRevert findPcdDocRevertById(String id) {
        return pcdDocRevertFeign.findPcdDocRevertById(id);
    }

    public PcdDocRevert savePcdDocRevert(PcdDocRevert pcdDocRevert) {
        return pcdDocRevertFeign.savePcdDocRevert(pcdDocRevert);
    }

    public void batchSavePcdDocRevert(List<PcdDocRevert> pcdDocReverts){
        pcdDocRevertFeign.batchSavePcdDocRevert(pcdDocReverts);
    }

    public List<PcdDocRevert> findPcdDocRevertListByCondition(PcdDocRevert pcdDocRevert) {
        return pcdDocRevertFeign.findPcdDocRevertListByCondition(pcdDocRevert);
    }

    public PcdDocRevert findOnePcdDocRevertByCondition(PcdDocRevert pcdDocRevert) {
        return pcdDocRevertFeign.findOnePcdDocRevertByCondition(pcdDocRevert);
    }

    public long findPcdDocRevertCountByCondition(PcdDocRevert pcdDocRevert) {
        return pcdDocRevertFeign.findPcdDocRevertCountByCondition(pcdDocRevert);
    }

    public void updatePcdDocRevert(PcdDocRevert pcdDocRevert) {
        pcdDocRevertFeign.updatePcdDocRevert(pcdDocRevert);
    }

    public void updatePcdDocRevertForAll(PcdDocRevert pcdDocRevert) {
        pcdDocRevertFeign.updatePcdDocRevertForAll(pcdDocRevert);
    }

    public void deletePcdDocRevert(String id) {
        pcdDocRevertFeign.deletePcdDocRevert(id);
    }

    public void deletePcdDocRevertByCondition(PcdDocRevert pcdDocRevert) {
        pcdDocRevertFeign.deletePcdDocRevertByCondition(pcdDocRevert);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePcdDocRevertKong(PcdDocRevert pcdDocRevert){
        pcdDocRevertFeign.savePcdDocRevertKong(pcdDocRevert);
    }

    public List<PcdDocRevert> findAndUpdatePcdDocRevertById(PcdDocRevert pcdDocRevert){
        return pcdDocRevertFeign.findAndUpdatePcdDocRevertById(pcdDocRevert);
    }

    public List<PcdDocRevert> findPcdDocRevertByPcdDocRevertListKong(PcdDocRevert pcdDocRevert){
        return pcdDocRevertFeign.findPcdDocRevertByPcdDocRevertListKong(pcdDocRevert);
    }

    public long findPcdDocRevertByPcdDocRevertLongKong(PcdDocRevert pcdDocRevert){
        return pcdDocRevertFeign.findPcdDocRevertByPcdDocRevertLongKong(pcdDocRevert);
    }
}
