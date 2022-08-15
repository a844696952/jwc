package com.yice.edu.cn.osp.feignClient.jy.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jyMaterialExtendFeign",path = "/jyMaterialExtend")
public interface JyMaterialExtendFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findJyMaterialExtendById/{id}")
    JyMaterialExtend findJyMaterialExtendById(@PathVariable("id") String id);
    @PostMapping("/saveJyMaterialExtend")
    JyMaterialExtend saveJyMaterialExtend(JyMaterialExtend jyMaterialExtend);
    @PostMapping("/batchSaveJyMaterialExtend")
    void batchSaveJyMaterialExtend(List<JyMaterialExtend> jyMaterialExtends);
    @PostMapping("/findJyMaterialExtendListByCondition")
    List<JyMaterialExtend> findJyMaterialExtendListByCondition(JyMaterialExtend jyMaterialExtend);
    @PostMapping("/findOneJyMaterialExtendByCondition")
    JyMaterialExtend findOneJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend);
    @PostMapping("/findJyMaterialExtendCountByCondition")
    long findJyMaterialExtendCountByCondition(JyMaterialExtend jyMaterialExtend);
    @PostMapping("/updateJyMaterialExtend")
    void updateJyMaterialExtend(JyMaterialExtend jyMaterialExtend);
    @PostMapping("/updateJyMaterialExtendForAll")
    void updateJyMaterialExtendForAll(JyMaterialExtend jyMaterialExtend);
    @GetMapping("/deleteJyMaterialExtend/{id}")
    void deleteJyMaterialExtend(@PathVariable("id") String id);
    @PostMapping("/deleteJyMaterialExtendByCondition")
    void deleteJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findMaterialListByCondition")
    List<Material> findMaterialListByCondition(JyMaterialExtend jyMaterialExtend);
}
