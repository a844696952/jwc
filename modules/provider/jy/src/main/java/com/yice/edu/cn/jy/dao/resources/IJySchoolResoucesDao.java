package com.yice.edu.cn.jy.dao.resources;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resources.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJySchoolResoucesDao {
    List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces);

    JySchoolResouces findOneJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces);

    long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces);

    JySchoolResouces findJySchoolResoucesById(@Param("id") String id);

    void saveJySchoolResouces(JySchoolResouces jySchoolResouces);

    void updateJySchoolResouces(JySchoolResouces jySchoolResouces);

    void deleteJySchoolResouces(@Param("id") String id);

    void deleteJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces);

    void batchSaveJySchoolResouces(List<JySchoolResouces> jySchoolResouces);

    /**
     * 获取资源和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces);

    /**
     * 获取资源和文件夹记录条数
     * @param jySchoolResouces
     * @return
     */
    long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces);

    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    void updateManySchoolResouces(JyResouces jyResouces);

    /**
     * 批量收藏
     *      * @param rowData
     */
    List<JyCollectionResource> insertManySchoolResouces(JySchoolResouces jySchoolResouces);

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jySchoolResouces
     * @return
     */
    int repeatSchoolResouces(JySchoolResouces jySchoolResouces);
    //批量删除指定文件夹id
    void deleteByResoucesType(JyResouces jyResouces);
    //批量删除指定的文件id
    void deleteByResoucesId(JyResouces jyResouces);

    List<JySchoolResourceCensus> censusSchoolResouces(JySchoolResouces jySchoolResouces);
    Long censusSumSchoolResouces(JySchoolResouces jySchoolResouces);
    List<JySchoolResourcesByDay> censusSumResoucesByDay(JySchoolResouces jySchoolResouces);
}
