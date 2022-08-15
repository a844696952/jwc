package com.yice.edu.cn.pcd.service.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.pcd.feignClient.pcdDoc.PcdDocFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdDocService {
    @Autowired
    private PcdDocFeign pcdDocFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdDoc findPcdDocById(String id) {
        return pcdDocFeign.findPcdDocById(id);
    }

    public PcdDoc savePcdDoc(PcdDoc pcdDoc) {
        return pcdDocFeign.savePcdDoc(pcdDoc);
    }

    public void batchSavePcdDoc(List<PcdDoc> pcdDocs){
        pcdDocFeign.batchSavePcdDoc(pcdDocs);
    }

    public List<PcdDoc> findPcdDocListByCondition(PcdDoc pcdDoc) {
        return pcdDocFeign.findPcdDocListByCondition(pcdDoc);
    }

    public PcdDoc findOnePcdDocByCondition(PcdDoc pcdDoc) {
        return pcdDocFeign.findOnePcdDocByCondition(pcdDoc);
    }

    public long findPcdDocCountByCondition(PcdDoc pcdDoc) {
        return pcdDocFeign.findPcdDocCountByCondition(pcdDoc);
    }

    public void updatePcdDoc(PcdDoc pcdDoc) {
        pcdDocFeign.updatePcdDoc(pcdDoc);
    }

    public void updatePcdDocForAll(PcdDoc pcdDoc) {
        pcdDocFeign.updatePcdDocForAll(pcdDoc);
    }

    public void deletePcdDoc(String id) {
        pcdDocFeign.deletePcdDoc(id);
    }

    public void deletePcdDocByCondition(PcdDoc pcdDoc) {
        pcdDocFeign.deletePcdDocByCondition(pcdDoc);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<PcdDoc> findListPcdDocByPcdDocKong(PcdDoc pcdDoc){
        return pcdDocFeign.findListPcdDocByPcdDocKong(pcdDoc);
    }

    public long findCountPcdDocByPcdDocKong(PcdDoc pcdDoc){
        return pcdDocFeign.findCountPcdDocByPcdDocKong(pcdDoc);
    }

    public void savePcdDocKong(PcdDoc pcdDoc){
        pcdDocFeign.savePcdDocKong(pcdDoc);
    }

    public PcdDoc saveForwardDoc(PcdDoc pcdDoc) {
        return pcdDocFeign.saveForwardDoc(pcdDoc);
    }
}
