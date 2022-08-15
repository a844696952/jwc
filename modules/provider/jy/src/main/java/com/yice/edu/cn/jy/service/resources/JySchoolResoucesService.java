package com.yice.edu.cn.jy.service.resources;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resources.*;
import com.yice.edu.cn.jy.dao.resources.IJyCollectionResourceDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesTypeDao;
import com.yice.edu.cn.jy.dao.resources.IJySchoolResoucesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JySchoolResoucesService {
    @Autowired
    private IJySchoolResoucesDao jySchoolResoucesDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJyResoucesDao iJyResoucesDao;
    @Autowired
    private IJyResoucesTypeDao iJyResoucesTypeDao;
    @Autowired
    private IJyCollectionResourceDao iJyCollectionResourceDao;
    @Transactional(readOnly = true)
    public JySchoolResouces findJySchoolResoucesById(String id) {
        return jySchoolResoucesDao.findJySchoolResoucesById(id);
    }
    @Transactional
    public void saveJySchoolResouces(JySchoolResouces jySchoolResouces) {
        jySchoolResouces.setId(sequenceId.nextId());
        JyResouces jyResouces = new JyResouces();
        //修改该资源的分享状态
        jyResouces.setShareStatus(1);
        jyResouces.setId(jySchoolResouces.getId());
        iJyResoucesDao.updateJyResouces(jyResouces);
        jySchoolResoucesDao.saveJySchoolResouces(jySchoolResouces);
    }

    @Transactional(readOnly = true)
    public List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesDao.findJySchoolResoucesListByCondition(jySchoolResouces);
    }
    @Transactional(readOnly = true)
    public JySchoolResouces findOneJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesDao.findOneJySchoolResoucesByCondition(jySchoolResouces);
    }
    @Transactional(readOnly = true)
    public long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesDao.findJySchoolResoucesCountByCondition(jySchoolResouces);
    }
    @Transactional
    public void updateJySchoolResouces(JySchoolResouces jySchoolResouces) {
        jySchoolResoucesDao.updateJySchoolResouces(jySchoolResouces);
    }
    @Transactional
    public void deleteJySchoolResouces(String id) {
        jySchoolResoucesDao.deleteJySchoolResouces(id);
    }
    @Transactional
    public void deleteJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces) {
        jySchoolResoucesDao.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }
    @Transactional
    public void batchSaveJySchoolResouces(List<JySchoolResouces> jySchoolResoucess){
        jySchoolResoucess.forEach(jySchoolResouces -> jySchoolResouces.setId(sequenceId.nextId()));
        jySchoolResoucesDao.batchSaveJySchoolResouces(jySchoolResoucess);
    }
    /**
     * 获取资源和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    @Transactional
    public List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.findJySchoolResoucesList(jySchoolResouces);
    }

   

    /**
     * 获取资源和文件夹记录条数
     * @param jySchoolResouces
     * @return
     */
    @Transactional
    public long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.findJySchoolResoucesCount(jySchoolResouces);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    @Transactional
    public void updateManySchoolResouces(JyResouces jyResouces){

        if(jyResouces.getRowDatas().length>0){
            ArrayList<String> objArray = new ArrayList<String>();
            for(int i=0;i<jyResouces.getRowDatas().length;i++){
                //如果勾选的值，包含了移动的目标文件，移除
                if(jyResouces.getRowDatas()[i].equals(jyResouces.getFileId())){
                    //如果存在，把最后一个元素，赋值给他
                    jyResouces.getRowDatas()[i] = null;
                }
            }
            iJyResoucesTypeDao.updateManyResoucesType(jyResouces);
        }
        if(jyResouces.getRowData().length>0){
            for(int i=0;i<jyResouces.getRowData().length;i++){
                //如果勾选的值，包含了移动的目标文件，移除
                if(jyResouces.getRowData()[i].equals(jyResouces.getFileId())){
                    //如果存在，把最后一个元素，赋值给他
                    jyResouces.getRowData()[i] = null;
                }
            }
            jySchoolResoucesDao.updateManySchoolResouces(jyResouces);
        }
    }

    /**
     * 批量收藏
     *      * @param rowData
     */
    @Transactional
    public List<JyCollectionResource> insertManySchoolResouces(JySchoolResouces jySchoolResouces){
        //只分享文件，不分享文件夹
        if(jySchoolResouces.getRowData().length>0){
            JyCollectionResource jyCollectionResource1 = new JyCollectionResource();
            jyCollectionResource1.setTeacherId(jySchoolResouces.getTeacherId());
            List<JyCollectionResource> jyCollectionResourceList = iJyCollectionResourceDao.findJyCollectionResourceListByCondition(jyCollectionResource1);
            //查询勾选的数据
            List<JyCollectionResource> jyCollectionResources = jySchoolResoucesDao.insertManySchoolResouces(jySchoolResouces);
            jyCollectionResources.stream().forEach(e->{
                e.setSchoolResoucesId(e.getId());
                jyCollectionResourceList.stream().forEach(p->{
                    //如果同一个讲师分享的文件同名了，则需要做重名处理
                    if(e.getName().equals(p.getName()) && jySchoolResouces.getTeacherId().equals(p.getTeacherId())){
                        e.setName(p.getName()+"(副本)");
                    }
                });
            });
            //批量插入到校本资源中
            iJyCollectionResourceDao.batchSaveJyCollectionResource(jyCollectionResources);
        }
        return jySchoolResoucesDao.insertManySchoolResouces(jySchoolResouces);
    }

    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jySchoolResoucesDao.getFileList(jyCollectionResource);
    }

    /**
     * 去重复
     * @param jySchoolResouces
     * @return
     */
    @Transactional
    public int repeatSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.repeatSchoolResouces(jySchoolResouces);
    }

    //批量删除指定文件夹id
    @Transactional
    public void deleteByResoucesType(JyResouces jyResouces){
        jySchoolResoucesDao.deleteByResoucesType(jyResouces);
    }
    //批量删除指定的文件id
    @Transactional
    public void deleteByResoucesId(JyResouces jyResouces){
        jySchoolResoucesDao.deleteByResoucesId(jyResouces);
    }
    @Transactional
    public void deleteJyResoucesType(String id){
        JySchoolResouces jySchoolResouces = new JySchoolResouces();
        jySchoolResouces.setFileId(id);
        //删除文件夹，在删除其下所有文件
        iJyResoucesTypeDao.deleteJyResoucesType(id);
        jySchoolResoucesDao.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }

    @Transactional
    public List<JySchoolResourceCensus> censusSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.censusSchoolResouces(jySchoolResouces);
    }

    @Transactional
    public Long censusSumSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.censusSumSchoolResouces(jySchoolResouces);
    }
    @Transactional
    public List<JySchoolResourcesByDay> censusSumResoucesByDay(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesDao.censusSumResoucesByDay(jySchoolResouces);
    }
}
