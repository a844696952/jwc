package com.yice.edu.cn.ecc.service.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.ecc.feignClient.kq.EccStudentFaceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EccStudentFaceService {

    @Autowired
    private EccStudentFaceFeign eccStudentFaceFeign;


    public EccStudentFace saveEccStudentFace(EccStudentFace eccStudentFace) {
        return eccStudentFaceFeign.saveEccStudentFace(eccStudentFace);
    }

    public void updateEccStudentFace(EccStudentFace eccStudentFace) {

        eccStudentFaceFeign.updateEccStudentFace(eccStudentFace);
    }

    public long findEccStudentFaceCountByCondition(EccStudentFace eccStudentFace) {
        return eccStudentFaceFeign.findEccStudentFaceCountByCondition(eccStudentFace);
    }

    public List<EccStudentFace> findEccStudentFaceListByCondition(EccStudentFace eccStudentFace) {
        return eccStudentFaceFeign.findEccStudentFaceListByCondition(eccStudentFace);
    }

    public EccStudentFace findOneEccStudentFaceByCondition(EccStudentFace eccStudentFace) {
        return eccStudentFaceFeign.findOneEccStudentFaceByCondition(eccStudentFace);
    }

    public EccStudentFace findEccStudentFaceById(String faceId) {
        return eccStudentFaceFeign.findEccStudentFaceById(faceId);
    }

    public void deleteFace(String faceId) {
        eccStudentFaceFeign.deleteFace(faceId);
    }
}
