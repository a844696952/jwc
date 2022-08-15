package com.yice.edu.cn.osp.feignClient.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jyResoucesTypeFeign",path = "/jyResoucesType")
public interface JyResoucesTypeFeign {
    @GetMapping("/findJyResoucesTypeById/{id}")
    JyResoucesType findJyResoucesTypeById(@PathVariable("id") String id);
    @PostMapping("/saveJyResoucesType")
    JyResoucesType saveJyResoucesType(JyResoucesType jyResoucesType);
    @PostMapping("/findJyResoucesTypeListByCondition")
    List<JyResoucesType> findJyResoucesTypeListByCondition(JyResoucesType jyResoucesType);
    @PostMapping("/findOneJyResoucesTypeByCondition")
    JyResoucesType findOneJyResoucesTypeByCondition(JyResoucesType jyResoucesType);
    @PostMapping("/findJyResoucesTypeCountByCondition")
    long findJyResoucesTypeCountByCondition(JyResoucesType jyResoucesType);
    @PostMapping("/updateJyResoucesType")
    void updateJyResoucesType(JyResoucesType jyResoucesType);
    @GetMapping("/deleteJyResoucesType/{id}")
    void deleteJyResoucesType(@PathVariable("id") String id);
    @PostMapping("/deleteJyResoucesTypeByCondition")
    void deleteJyResoucesTypeByCondition(JyResoucesType jyResoucesType);
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyResoucesType")
    void updateManyResoucesType(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesType")
    void deleteManyResoucesType(JyResouces jyResouces);

    /**
     * 去重复
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/repeatType")
    int repeatType(JyResoucesType jyResoucesType);
    /**
     * 获取树形结构
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/getTreeList")
    List<JyResoucesType> getTreeList(JyResoucesType jyResoucesType);
    /**
     * 移动去重
     * @param jyResoucesType
     * @return
     */
    @PostMapping("/removeRepartResoucesType")
    List<JyResoucesType> removeRepartResoucesType(JyResouces jyResouces);
}
