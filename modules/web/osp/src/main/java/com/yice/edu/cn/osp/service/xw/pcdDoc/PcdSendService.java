package com.yice.edu.cn.osp.service.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import com.yice.edu.cn.osp.feignClient.xw.pcdDoc.PcdSendFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcdSendService {
    @Autowired
    private PcdSendFeign pcdSendFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdSend findPcdSendById(String id) {
        return pcdSendFeign.findPcdSendById(id);
    }

    public PcdSend savePcdSend(PcdSend pcdSend) {
        return pcdSendFeign.savePcdSend(pcdSend);
    }

    public void batchSavePcdSend(List<PcdSend> pcdSends){
        pcdSendFeign.batchSavePcdSend(pcdSends);
    }

    public List<PcdSend> findPcdSendListByCondition(PcdSend pcdSend) {
        return pcdSendFeign.findPcdSendListByCondition(pcdSend);
    }

    public PcdSend findOnePcdSendByCondition(PcdSend pcdSend) {
        return pcdSendFeign.findOnePcdSendByCondition(pcdSend);
    }

    public long findPcdSendCountByCondition(PcdSend pcdSend) {
        return pcdSendFeign.findPcdSendCountByCondition(pcdSend);
    }

    public void updatePcdSend(PcdSend pcdSend) {
        pcdSendFeign.updatePcdSend(pcdSend);
    }

    public void updatePcdSendForAll(PcdSend pcdSend) {
        pcdSendFeign.updatePcdSendForAll(pcdSend);
    }

    public void deletePcdSend(String id) {
        pcdSendFeign.deletePcdSend(id);
    }

    public void deletePcdSendByCondition(PcdSend pcdSend) {
        pcdSendFeign.deletePcdSendByCondition(pcdSend);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public PcdDoc findOnePcdSendByPcdSend(PcdDocRevert pcdDocRevert){
        return pcdSendFeign.findOnePcdSendByPcdSend(pcdDocRevert);
    }

    public List<PcdSend> findPcdSendByListKong(PcdSend pcdSend){
        return pcdSendFeign.findPcdSendByListKong(pcdSend);
    }

    public long findPcdSendByCountKong(PcdSend pcdSend){
        return pcdSendFeign.findPcdSendByCountKong(pcdSend);
    }
}
