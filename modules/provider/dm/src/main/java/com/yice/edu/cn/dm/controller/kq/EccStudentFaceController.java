package com.yice.edu.cn.dm.controller.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.dm.service.kq.EccStudentFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dengfengfeng
 */
@RequestMapping("/kqEccStudentFace")
@RestController
public class EccStudentFaceController {

    @Autowired
    private EccStudentFaceService eccStudentFaceService;


    @GetMapping("/findEccStudentFaceById/{id}")
    public EccStudentFace findEccStudentFaceById(@PathVariable("id")String id){
        return eccStudentFaceService.findEccStudentFaceById(id);
    }


    @PostMapping("/findEccStudentFaceCountByCondition")
    public long findEccStudentFaceCount(@RequestBody EccStudentFace eccStudentFace){
        return eccStudentFaceService.findEccStudentFaceCountByCondition(eccStudentFace);
    }

    @PostMapping("/findEccStudentFaceListByCondition")
    public List<EccStudentFace> findEccStudentFaceListByCondition(@RequestBody EccStudentFace eccStudentFace){
        return eccStudentFaceService.findEccStudentFaceListByCondition(eccStudentFace);
    }


    /**
     * 保存云班牌录入的人脸
     * @param eccStudentFace
     * @return
     */
    @PostMapping("/saveEccStudentFace")
    public EccStudentFace saveEccStudentFace(@RequestBody EccStudentFace eccStudentFace){
        eccStudentFaceService.saveEccStudentFace(eccStudentFace);
        return eccStudentFace;
    }

    /**
     * 修改云班牌录入的人脸
     * @param eccStudentFace
     * @return
     */
    @PostMapping("/updateEccStudentFace")
    public void updateEccStudentFace(@RequestBody EccStudentFace eccStudentFace){
        eccStudentFaceService.updateEccStudentFace(eccStudentFace);
    }

    @PostMapping("/findOneEccStudentFaceByCondition")
    public EccStudentFace findOneEccStudentFaceByCondition(@RequestBody EccStudentFace eccStudentFace){
        return eccStudentFaceService.findOneEccStudentFaceByCondition(eccStudentFace);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id){
        eccStudentFaceService.deleteEccStudentFace(id);
    }


    @DeleteMapping("/batchDeleteStudentFace/{schoolId}")
    public void batchDeleteStudentFace(@PathVariable("schoolId")String schoolId){
        eccStudentFaceService.batchDeleteEccStudentFace(schoolId);
    }

}
