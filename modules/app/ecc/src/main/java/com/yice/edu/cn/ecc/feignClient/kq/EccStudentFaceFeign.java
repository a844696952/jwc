package com.yice.edu.cn.ecc.feignClient.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "eccStudentFaceFeign",path = "/kqEccStudentFace")
public interface EccStudentFaceFeign {

    @PostMapping("/saveEccStudentFace")
    EccStudentFace saveEccStudentFace(EccStudentFace eccStudentFace);

    @PostMapping("/updateEccStudentFace")
    void updateEccStudentFace(EccStudentFace eccStudentFace);

    @PostMapping("/findEccStudentFaceCountByCondition")
    long findEccStudentFaceCountByCondition(EccStudentFace eccStudentFace);

    @PostMapping("/findEccStudentFaceListByCondition")
    List<EccStudentFace> findEccStudentFaceListByCondition(EccStudentFace eccStudentFace);

    @PostMapping("/findOneEccStudentFaceByCondition")
    EccStudentFace findOneEccStudentFaceByCondition(EccStudentFace eccStudentFace);

    @GetMapping("/findEccStudentFaceById/{id}")
    EccStudentFace findEccStudentFaceById(@PathVariable("id") String id);

    @DeleteMapping("/delete/{id}")
    void deleteFace(@PathVariable("id")String faceId);

}
