package com.yice.edu.cn.osp.feignClient.jy.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jySubjectMaterialExtendFeign",path = "/jySubjectMaterialExtend")
public interface JySubjectMaterialExtendFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findJySubjectMaterialExtendById/{id}")
    JySubjectMaterialExtend findJySubjectMaterialExtendById(@PathVariable("id") String id);
    @PostMapping("/saveJySubjectMaterialExtend")
    JySubjectMaterialExtend saveJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/batchSaveJySubjectMaterialExtend")
    void batchSaveJySubjectMaterialExtend(List<JySubjectMaterialExtend> jySubjectMaterialExtends);
    @PostMapping("/findJySubjectMaterialExtendListByCondition")
    List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/findOneJySubjectMaterialExtendByCondition")
    JySubjectMaterialExtend findOneJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/findJySubjectMaterialExtendCountByCondition")
    long findJySubjectMaterialExtendCountByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/updateJySubjectMaterialExtend")
    void updateJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/updateJySubjectMaterialExtendForAll")
    void updateJySubjectMaterialExtendForAll(JySubjectMaterialExtend jySubjectMaterialExtend);
    @GetMapping("/deleteJySubjectMaterialExtend/{id}")
    void deleteJySubjectMaterialExtend(@PathVariable("id") String id);
    @PostMapping("/deleteJySubjectMaterialExtendByCondition")
    void deleteJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);
    @PostMapping("/updateJySubjectMaterialExtendSort")
    void updateJySubjectMaterialExtendSort(List<JySubjectMaterialExtend> jsmeList);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
