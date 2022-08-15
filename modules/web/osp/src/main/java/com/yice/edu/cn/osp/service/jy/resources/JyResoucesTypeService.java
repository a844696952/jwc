package com.yice.edu.cn.osp.service.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.osp.feignClient.jy.resources.JyResoucesTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyResoucesTypeService {
    @Autowired
    private JyResoucesTypeFeign jyResoucesTypeFeign;

    public JyResoucesType findJyResoucesTypeById(String id) {
        return jyResoucesTypeFeign.findJyResoucesTypeById(id);
    }

    public JyResoucesType saveJyResoucesType(JyResoucesType jyResoucesType) {
        return jyResoucesTypeFeign.saveJyResoucesType(jyResoucesType);
    }

    public List<JyResoucesType> findJyResoucesTypeListByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeFeign.findJyResoucesTypeListByCondition(jyResoucesType);
    }

    public JyResoucesType findOneJyResoucesTypeByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeFeign.findOneJyResoucesTypeByCondition(jyResoucesType);
    }

    public long findJyResoucesTypeCountByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeFeign.findJyResoucesTypeCountByCondition(jyResoucesType);
    }

    public void updateJyResoucesType(JyResoucesType jyResoucesType) {
        jyResoucesTypeFeign.updateJyResoucesType(jyResoucesType);
    }

    public void deleteJyResoucesType(String id) {
        jyResoucesTypeFeign.deleteJyResoucesType(id);
    }

    public void deleteJyResoucesTypeByCondition(JyResoucesType jyResoucesType) {
        jyResoucesTypeFeign.deleteJyResoucesTypeByCondition(jyResoucesType);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    public void updateManyResoucesType(JyResouces jyResouces){
        jyResoucesTypeFeign.updateManyResoucesType(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    public void deleteManyResoucesType(JyResouces jyResouces){
        jyResoucesTypeFeign.deleteManyResoucesType(jyResouces);
    }

    /**
     * 去重复
     * @param jyResoucesType
     */
    public int repeatType(JyResoucesType jyResoucesType){
        return jyResoucesTypeFeign.repeatType(jyResoucesType);
    }

    /**
     * 获取树形结构
     * @param jyResoucesType
     */
    public List<JyResoucesType> getTreeList(JyResoucesType jyResoucesType){
        return jyResoucesTypeFeign.getTreeList(jyResoucesType);
    }
    /**
     * 移动去重复
     * @param jyResouces
     */
    public List<JyResoucesType> removeRepartResoucesType(JyResouces jyResouces){
        return jyResoucesTypeFeign.removeRepartResoucesType(jyResouces);
    }
}
