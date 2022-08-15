package com.yice.edu.cn.jy.dao.resources;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyResoucesTypeDao {
    List<JyResoucesType> findJyResoucesTypeListByCondition(JyResoucesType jyResoucesType);

    JyResoucesType findOneJyResoucesTypeByCondition(JyResoucesType jyResoucesType);

    long findJyResoucesTypeCountByCondition(JyResoucesType jyResoucesType);

    JyResoucesType findJyResoucesTypeById(@Param("id") String id);

    void saveJyResoucesType(JyResoucesType jyResoucesType);

    void updateJyResoucesType(JyResoucesType jyResoucesType);

    void deleteJyResoucesType(@Param("id") String id);

    void deleteJyResoucesTypeByCondition(JyResoucesType jyResoucesType);

    void batchSaveJyResoucesType(List<JyResoucesType> jyResoucesTypes);
    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    void updateManyResoucesType(JyResouces jyResouces);
    /**
     * 批量删除文件
     * @param jyResouces
     */
    void deleteManyResoucesType(JyResouces jyResouces);

    /**
     * 去重复
     * @param jyResoucesType
     * @return
     */
    int repeatType(JyResoucesType jyResoucesType);

    List<JyResoucesType> getTreeList(JyResoucesType jyResoucesType);

    List<JyResoucesType> removeRepartResoucesType(JyResouces jyResouces);

}
