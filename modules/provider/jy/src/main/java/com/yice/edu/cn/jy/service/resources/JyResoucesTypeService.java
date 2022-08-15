package com.yice.edu.cn.jy.service.resources;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyResoucesTypeService {
    @Autowired
    private IJyResoucesTypeDao jyResoucesTypeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JyResoucesType findJyResoucesTypeById(String id) {
        return jyResoucesTypeDao.findJyResoucesTypeById(id);
    }
    @Transactional
    public void saveJyResoucesType(JyResoucesType jyResoucesType) {
        jyResoucesType.setId(sequenceId.nextId());
        jyResoucesTypeDao.saveJyResoucesType(jyResoucesType);
    }
    @Transactional(readOnly = true)
    public List<JyResoucesType> findJyResoucesTypeListByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeDao.findJyResoucesTypeListByCondition(jyResoucesType);
    }
    @Transactional(readOnly = true)
    public JyResoucesType findOneJyResoucesTypeByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeDao.findOneJyResoucesTypeByCondition(jyResoucesType);
    }
    @Transactional(readOnly = true)
    public long findJyResoucesTypeCountByCondition(JyResoucesType jyResoucesType) {
        return jyResoucesTypeDao.findJyResoucesTypeCountByCondition(jyResoucesType);
    }
    @Transactional
    public void updateJyResoucesType(JyResoucesType jyResoucesType) {
        jyResoucesTypeDao.updateJyResoucesType(jyResoucesType);
    }
    @Transactional
    public void deleteJyResoucesType(String id) {


        jyResoucesTypeDao.deleteJyResoucesType(id);
    }
    @Transactional
    public void deleteJyResoucesTypeByCondition(JyResoucesType jyResoucesType) {
        jyResoucesTypeDao.deleteJyResoucesTypeByCondition(jyResoucesType);
    }
    @Transactional
    public void batchSaveJyResoucesType(List<JyResoucesType> jyResoucesTypes){
        jyResoucesTypes.forEach(jyResoucesType -> jyResoucesType.setId(sequenceId.nextId()));
        jyResoucesTypeDao.batchSaveJyResoucesType(jyResoucesTypes);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @Transactional
    public void updateManyResoucesType(JyResouces jyResouces){
        jyResoucesTypeDao.updateManyResoucesType(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @Transactional
    public void deleteManyResoucesType(JyResouces jyResouces){
        jyResoucesTypeDao.deleteManyResoucesType(jyResouces);
    }

    /**
     * 去重复
     * @param jyResoucesType
     */
    @Transactional
    public int repeatType(JyResoucesType jyResoucesType){
        return jyResoucesTypeDao.repeatType(jyResoucesType);
    }

    @Transactional
    public List<JyResoucesType> getTreeList(JyResoucesType jyResoucesType){
        return jyResoucesTypeDao.getTreeList(jyResoucesType);
    }
    @Transactional
    public List<JyResoucesType> removeRepartResoucesType(JyResouces jyResouces){
        return jyResoucesTypeDao.removeRepartResoucesType(jyResouces);
    }
}
