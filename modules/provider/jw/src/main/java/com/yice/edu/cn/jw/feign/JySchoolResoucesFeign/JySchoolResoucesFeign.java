package com.yice.edu.cn.jw.feign.JySchoolResoucesFeign;

import com.yice.edu.cn.common.pojo.jy.resources.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jySchoolResoucesFeign",path = "/jySchoolResouces")
public interface JySchoolResoucesFeign {
    @GetMapping("/findJySchoolResoucesById/{id}")
    JySchoolResouces findJySchoolResoucesById(@PathVariable("id") String id);
    @PostMapping("/saveJySchoolResouces")
    JySchoolResouces saveJySchoolResouces(JySchoolResouces jySchoolResouces);
    @PostMapping("/findJySchoolResoucesListByCondition")
    List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces);
    @PostMapping("/findOneJySchoolResoucesByCondition")
    JySchoolResouces findOneJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces);
    @PostMapping("/findJySchoolResoucesCountByCondition")
    long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces);
    @PostMapping("/updateJySchoolResouces")
    void updateJySchoolResouces(JySchoolResouces jySchoolResouces);
    @GetMapping("/deleteJySchoolResouces/{id}")
    void deleteJySchoolResouces(@PathVariable("id") String id);
    @PostMapping("/deleteJySchoolResoucesByCondition")
    void deleteJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces);
    @PostMapping("/batchSaveJySchoolResouces")
    void batchSaveJySchoolResouces(List<JySchoolResouces> jySchoolResoucesList);
    /**
     * 返回资源列表和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/findJySchoolResoucesList")
    List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces);

    /**
     * 返回资源和文件夹记录数
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/findJySchoolResoucesCount")
    long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces);

    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    @PostMapping("/updateManySchoolResouces")
    void updateManySchoolResouces(JyResouces jyResouces);

    /**
     * 批量收藏
     *      * @param rowData
     */
    @PostMapping("/insertManySchoolResouces")
    List<JyCollectionResource> insertManySchoolResouces(JySchoolResouces jySchoolResouces);

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/getFileList")
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/repeatSchoolResouces")
    int repeatSchoolResouces(JySchoolResouces jySchoolResouces);

    /**
     * 根据文件夹编号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesType")
    void deleteByResoucesType(JyResouces jyResouces);

    /**
     * 根据文件夹号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesId")
    void deleteByResoucesId(JyResouces jyResouces);
    @GetMapping("/deleteJyResoucesType/{id}")
    void deleteJyResoucesType(@PathVariable("id") String id);
    @PostMapping("/censusSchoolResouces")
    List<JySchoolResourceCensus> censusSchoolResouces(JySchoolResouces jySchoolResouces);
    @PostMapping("/censusSumSchoolResouces")
    Long censusSumSchoolResouces(JySchoolResouces jySchoolResouces);
    @PostMapping("/censusSumResoucesByDay")
    List<JySchoolResourcesByDay> censusSumResoucesByDay(JySchoolResouces jySchoolResouces);
}
