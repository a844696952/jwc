package com.yice.edu.cn.osp.feignClient.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jyCollectionResourceFeign",path = "/jyCollectionResource")
public interface JyCollectionResourceFeign {
    @GetMapping("/findJyCollectionResourceById/{id}")
    JyCollectionResource findJyCollectionResourceById(@PathVariable("id") String id);
    @PostMapping("/saveJyCollectionResource")
    JyCollectionResource saveJyCollectionResource(JyCollectionResource jyCollectionResource);
    @PostMapping("/batchSaveJyCollectionResource")
    void batchSaveJyCollectionResource(List<JyCollectionResource> jyCollectionResources);
    @PostMapping("/findJyCollectionResourceListByCondition")
    List<JyCollectionResource> findJyCollectionResourceListByCondition(JyCollectionResource jyCollectionResource);
    @PostMapping("/findOneJyCollectionResourceByCondition")
    JyCollectionResource findOneJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource);
    @PostMapping("/findJyCollectionResourceCountByCondition")
    long findJyCollectionResourceCountByCondition(JyCollectionResource jyCollectionResource);
    @PostMapping("/updateJyCollectionResource")
    void updateJyCollectionResource(JyCollectionResource jyCollectionResource);
    @GetMapping("/deleteJyCollectionResource/{id}")
    void deleteJyCollectionResource(@PathVariable("id") String id);
    @PostMapping("/deleteJyCollectionResourceByCondition")
    void deleteJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource);
    /**
     * 返回资源列表和文件夹列表
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/findJyCollectionResourceList")
    List<JyCollectionResource> findJyCollectionResourceList(JyCollectionResource jyCollectionResource);

    /**
     * 返回资源和文件夹记录数
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/findJyCollectionResourceCount")
    long findJyCollectionResourceCount(JyCollectionResource jyCollectionResource);
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyCollectionResouces")
    void updateManyCollectionResouces(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyCollectionResource
     */
    @PostMapping("/deleteManyCollectionResouces")
    void deleteManyCollectionResouces(JyCollectionResource jyCollectionResource);
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/getFileList")
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/repeatCollectionResouces")
    int repeatCollectionResouces(JyCollectionResource jyCollectionResource);

    @GetMapping("/deleteJyResoucesType/{id}")
    void deleteJyResoucesType(@PathVariable("id") String id);

}
