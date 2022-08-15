package com.yice.edu.cn.jy.service.resources;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.jy.dao.resources.IJyCollectionResourceDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyCollectionResourceService {
    @Autowired
    private IJyCollectionResourceDao jyCollectionResourceDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJyResoucesTypeDao iJyResoucesTypeDao;

    @Transactional(readOnly = true)
    public JyCollectionResource findJyCollectionResourceById(String id) {
        return jyCollectionResourceDao.findJyCollectionResourceById(id);
    }
    @Transactional
    public void saveJyCollectionResource(JyCollectionResource jyCollectionResource) {
        jyCollectionResource.setId(sequenceId.nextId());
        jyCollectionResourceDao.saveJyCollectionResource(jyCollectionResource);
    }

    @Transactional(readOnly = true)
    public List<JyCollectionResource> findJyCollectionResourceListByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceDao.findJyCollectionResourceListByCondition(jyCollectionResource);
    }
    @Transactional(readOnly = true)
    public JyCollectionResource findOneJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceDao.findOneJyCollectionResourceByCondition(jyCollectionResource);
    }
    @Transactional(readOnly = true)
    public long findJyCollectionResourceCountByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceDao.findJyCollectionResourceCountByCondition(jyCollectionResource);
    }
    @Transactional
    public void updateJyCollectionResource(JyCollectionResource jyCollectionResource) {
        jyCollectionResourceDao.updateJyCollectionResource(jyCollectionResource);
    }
    @Transactional
    public void deleteJyCollectionResource(String id) {
        jyCollectionResourceDao.deleteJyCollectionResource(id);
    }
    @Transactional
    public void deleteJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource) {
        jyCollectionResourceDao.deleteJyCollectionResourceByCondition(jyCollectionResource);
    }
    @Transactional
    public void batchSaveJyCollectionResource(List<JyCollectionResource> jyCollectionResources){
        jyCollectionResources.forEach(jyCollectionResource -> jyCollectionResource.setId(sequenceId.nextId()));
        jyCollectionResourceDao.batchSaveJyCollectionResource(jyCollectionResources);
    }
    /**
     * 获取资源和文件夹列表
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public List<JyCollectionResource> findJyCollectionResourceList(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceDao.findJyCollectionResourceList(jyCollectionResource);
    }

    /**
     * 获取资源和文件夹记录条数
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public long findJyCollectionResourceCount(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceDao.findJyCollectionResourceCount(jyCollectionResource);
    }

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @Transactional
    public void updateManyCollectionResouces(JyResouces jyResouces){
        if(jyResouces.getRowDatas().length>0){
            for(int i=0;i<jyResouces.getRowDatas().length;i++){
                //如果勾选的值，包含了移动的目标文件，移除
                if(jyResouces.getRowDatas()[i].equals(jyResouces.getFileId())){
                    //如果存在，把最后一个元素，赋值给他
                    jyResouces.getRowDatas()[i] = jyResouces.getRowDatas()[jyResouces.getRowDatas().length-1];
                }
            }
            iJyResoucesTypeDao.updateManyResoucesType(jyResouces);
        }
        if(jyResouces.getRowData().length>0){
            for(int i=0;i<jyResouces.getRowData().length;i++){
                //如果勾选的值，包含了移动的目标文件，移除
                if(jyResouces.getRowData()[i].equals(jyResouces.getFileId())){
                    //如果存在，把最后一个元素，赋值给他
                    jyResouces.getRowData()[i] = jyResouces.getRowData()[jyResouces.getRowData().length-1];
                }
            }
            jyCollectionResourceDao.updateManyCollectionResouces(jyResouces);
        }
    }
    /**
     * 批量删除文件
     * @param jyCollectionResource
     */
    @Transactional
    public void deleteManyCollectionResouces(JyCollectionResource jyCollectionResource){
        jyCollectionResourceDao.deleteManyCollectionResouces(jyCollectionResource);
    }
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceDao.getFileList(jyCollectionResource);
    }
    /**
     * 去重复
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public int repeatCollectionResouces(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceDao.repeatCollectionResouces(jyCollectionResource);
    }
    @Transactional
    public void deleteJyResoucesType(String id){
        JyCollectionResource jyCollectionResource= new JyCollectionResource();
        jyCollectionResource.setFileId(id);
        iJyResoucesTypeDao.deleteJyResoucesType(id);
        //删除文件夹，在删除文件夹下所有文件
        jyCollectionResourceDao.deleteJyCollectionResourceByCondition(jyCollectionResource);
    }
}
