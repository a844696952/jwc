package com.yice.edu.cn.jy.dao.resources;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyResoucesDao {
    List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces);

    JyResouces findOneJyResoucesByCondition(JyResouces jyResouces);

    long findJyResoucesCountByCondition(JyResouces jyResouces);

    JyResouces findJyResoucesById(@Param("id") String id);

    void saveJyResouces(JyResouces jyResouces);

    void updateJyResouces(JyResouces jyResouces);

    void deleteJyResouces(@Param("id") String id);

    void deleteJyResoucesByCondition(JyResouces jyResouces);

    void batchSaveJyResouces(List<JyResouces> jyResoucess);

    /**
     * 获取资源和文件夹列表
     * @param jyResouces
     * @return
     */
    List<JyResouces> findJyResoucesList(JyResouces jyResouces);

    /**
     * 获取资源和文件夹记录条数
     * @param jyResouces
     * @return
     */
    long findJyResoucesCount(JyResouces jyResouces);

    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    void updateManyResouces(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyResouces
     */
    void deleteManyResouces(JyResouces jyResouces);
    /**
     * 批量通过文件夹编号删除文件
     * @param jyResouces
     */
    void deleteManyResoucesByFileId(JyResouces jyResouces);
    /**
     * 批量分享
     *      * @param rowData
     */
    List<JySchoolResouces> insertManyResouces(JyResouces jyResouces);

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jyResouces
     * @return
     */
    int repeatResouces(JyResouces jyResouces);

    int changeResouces(JyResouces jyResouces);
}
