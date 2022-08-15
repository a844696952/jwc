package com.yice.edu.cn.jy.dao.resources;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyCollectionResourceDao {
    List<JyCollectionResource> findJyCollectionResourceListByCondition(JyCollectionResource jyCollectionResource);

    JyCollectionResource findOneJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource);

    long findJyCollectionResourceCountByCondition(JyCollectionResource jyCollectionResource);

    JyCollectionResource findJyCollectionResourceById(@Param("id") String id);

    void saveJyCollectionResource(JyCollectionResource jyCollectionResource);

    void updateJyCollectionResource(JyCollectionResource jyCollectionResource);

    void deleteJyCollectionResource(@Param("id") String id);

    void deleteJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource);

    void batchSaveJyCollectionResource(List<JyCollectionResource> jyCollectionResources);
    /**
     * 获取资源和文件夹列表
     * @param jyCollectionResource
     * @return
     */
    List<JyCollectionResource> findJyCollectionResourceList(JyCollectionResource jyCollectionResource);

    /**
     * 获取资源和文件夹记录条数
     * @param jyCollectionResource
     * @return
     */
    long findJyCollectionResourceCount(JyCollectionResource jyCollectionResource);

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    void updateManyCollectionResouces(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyCollectionResource
     */
    void deleteManyCollectionResouces(JyCollectionResource jyCollectionResource);

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource);

    /**
     * 去重复
     * @param jyCollectionResource
     * @return
     */
    int repeatCollectionResouces(JyCollectionResource jyCollectionResource);
}
