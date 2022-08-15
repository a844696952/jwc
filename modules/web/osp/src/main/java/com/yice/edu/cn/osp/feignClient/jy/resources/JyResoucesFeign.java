package com.yice.edu.cn.osp.feignClient.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="jy",contextId = "jyResoucesFeign",path = "/jyResouces")
public interface JyResoucesFeign {
    @GetMapping("/findJyResoucesById/{id}")
    JyResouces findJyResoucesById(@PathVariable("id") String id);
    @PostMapping("/saveJyResouces")
    JyResouces saveJyResouces(JyResouces jyResouces);
    @PostMapping("/findJyResoucesListByCondition")
    List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces);
    @PostMapping("/findOneJyResoucesByCondition")
    JyResouces findOneJyResoucesByCondition(JyResouces jyResouces);
    @PostMapping("/findJyResoucesCountByCondition")
    long findJyResoucesCountByCondition(JyResouces jyResouces);
    @PostMapping("/updateJyResouces")
    void updateJyResouces(JyResouces jyResouces);
    @GetMapping("/deleteJyResouces/{id}")
    void deleteJyResouces(@PathVariable("id") String id);
    @PostMapping("/deleteJyResoucesByCondition")
    void deleteJyResoucesByCondition(JyResouces jyResouces);
    @PostMapping("/batchSaveJyResouces")
    void batchSaveJyResouces(List<JyResouces> jyResoucess);
    /**
     * 返回资源列表和文件夹列表
     * @param jyResouces
     * @return
     */
    @PostMapping("/findJyResoucesList")
    List<JyResouces> findJyResoucesList(JyResouces jyResouces);

    /**
     * 返回资源和文件夹记录数
     * @param jyResouces
     * @return
     */
    @PostMapping("/findJyResoucesCount")
    long findJyResoucesCount(JyResouces jyResouces);

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyResouces")
    void updateManyResouces(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResouces")
    void deleteManyResouces(JyResouces jyResouces);

    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesByFileId")
    void deleteManyResoucesByFileId(JyResouces jyResouces);
    /**
     * 批量分享
     *      * @param rowData
     */
    @PostMapping("/insertManyResouces")
    List<JySchoolResouces>  insertManyResouces(JyResouces jyResouces);

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/getFileList")
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jyResouces
     * @return
     */
    @PostMapping("/repeatResouces")
    int repeatResouces(JyResouces jyResouces);

    /**
     * 移除分享
     * @param jyResouces
     */
    @PostMapping("/removeShare")
    void removeShare(JyResouces jyResouces);

    /**
     * 分享文件
     * @param jySchoolResouces
     */
    @PostMapping("/saveJySchoolResouces")
    void saveJySchoolResouces(JySchoolResouces jySchoolResouces);
    @PostMapping("/deleteFile")
    void deleteFile(JyResouces jyResouces);
    @GetMapping("/deleteJyResoucesType/{id}")
    void deleteJyResoucesType(@PathVariable("id") String id);
    @PostMapping("/noShare")
    void noShare(JyResouces jyResouces);
    /**
     * 去重复
     * @param jyResouces
     * @return
     */
    @PostMapping("/changeResouces")
    int changeResouces(JyResouces jyResouces);
}
