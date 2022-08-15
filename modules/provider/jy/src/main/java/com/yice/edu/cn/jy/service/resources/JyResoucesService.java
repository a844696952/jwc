package com.yice.edu.cn.jy.service.resources;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesDao;
import com.yice.edu.cn.jy.dao.resources.IJyResoucesTypeDao;
import com.yice.edu.cn.jy.dao.resources.IJySchoolResoucesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyResoucesService {
    @Autowired
    private IJyResoucesDao jyResoucesDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJySchoolResoucesDao iJySchoolResoucesDao;
    @Autowired
    private IJyResoucesTypeDao iJyResoucesTypeDao;
    @Transactional(readOnly = true)
    public JyResouces findJyResoucesById(String id) {
        return jyResoucesDao.findJyResoucesById(id);
    }
    @Transactional
    public void saveJyResouces(JyResouces jyResouces) {
        jyResouces.setId(sequenceId.nextId());
        jyResoucesDao.saveJyResouces(jyResouces);
    }
    @Transactional(readOnly = true)
    public List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces) {
        return jyResoucesDao.findJyResoucesListByCondition(jyResouces);
    }
    @Transactional(readOnly = true)
    public JyResouces findOneJyResoucesByCondition(JyResouces jyResouces) {
        return jyResoucesDao.findOneJyResoucesByCondition(jyResouces);
    }
    @Transactional(readOnly = true)
    public long findJyResoucesCountByCondition(JyResouces jyResouces) {
        return jyResoucesDao.findJyResoucesCountByCondition(jyResouces);
    }
    @Transactional
    public void updateJyResouces(JyResouces jyResouces) {
        jyResoucesDao.updateJyResouces(jyResouces);
    }
    @Transactional
    public void deleteJyResouces(String id) {
        jyResoucesDao.deleteJyResouces(id);
        JySchoolResouces jySchoolResouces = new JySchoolResouces();
        jySchoolResouces.setResoucesId(id);
        iJySchoolResoucesDao.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }
    @Transactional
    public void deleteJyResoucesByCondition(JyResouces jyResouces) {
        jyResoucesDao.deleteJyResoucesByCondition(jyResouces);
    }
    @Transactional
    public void batchSaveJyResouces(List<JyResouces> jyResoucess){
        jyResoucess.forEach(jyResouces -> jyResouces.setId(sequenceId.nextId()));
        jyResoucesDao.batchSaveJyResouces(jyResoucess);
    }
    /**
     * 获取资源和文件夹列表
     * @param jyResouces
     * @return
     */
    @Transactional
    public List<JyResouces> findJyResoucesList(JyResouces jyResouces){
        return jyResoucesDao.findJyResoucesList(jyResouces);
    }

    /**
     * 获取资源和文件夹记录条数
     * @param jyResouces
     * @return
     */
    @Transactional
    public long findJyResoucesCount(JyResouces jyResouces){
        return jyResoucesDao.findJyResoucesCount(jyResouces);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @Transactional
    public void updateManyResouces(JyResouces jyResouces){
        if(jyResouces.getRowDatas().length>0){
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
                    jyResouces.getRowData()[i] =  null;
                }
            }
            jyResoucesDao.updateManyResouces(jyResouces);
        }
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @Transactional
    public void deleteManyResouces(JyResouces jyResouces){
        jyResoucesDao.deleteManyResouces(jyResouces);
    }
    /**
     * 批量通过文件夹编号删除文件
     * @param jyResouces
     */
    @Transactional
    public void deleteManyResoucesByFileId(JyResouces jyResouces){
        jyResoucesDao.deleteManyResoucesByFileId(jyResouces);
    }
    /**
     * 批量分享
     *      * @param rowData
     */
    @Transactional
    public void  insertManyResouces(JyResouces jyResouces){
        //只分享文件，不分享文件夹
        if (jyResouces.getRowData().length > 0) {
            //查询勾选的数据
            List<JySchoolResouces> resoucesList = jyResoucesDao.insertManyResouces(jyResouces);
            //批量插入到校本资源中
            iJySchoolResoucesDao.batchSaveJySchoolResouces(resoucesList);
            //把分享后的资源全部修改为已分享的状态
            jyResouces.setShareStatus(1);
            jyResoucesDao.updateManyResouces(jyResouces);
        }
    }
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @Transactional
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jyResoucesDao.getFileList(jyCollectionResource);
    }
    /**
     *
     * 去重复
     * @param jyResouces
     * @return
     */
    @Transactional
    public int repeatResouces(JyResouces jyResouces){
        return jyResoucesDao.repeatResouces(jyResouces);
    }

    /**
     * 移除分享
     */
    @Transactional
    public void removeShare(JyResouces jyResouces){
        //修改该资源的分享状态
        jyResouces.setShareStatus(0);
        jyResoucesDao.updateJyResouces(jyResouces);
        JySchoolResouces jySchoolResouces = new JySchoolResouces();
        jySchoolResouces.setResoucesId(jyResouces.getId());
        iJySchoolResoucesDao.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }
    @Transactional
    public void saveJySchoolResouces(JySchoolResouces jySchoolResouces){
        JyResouces jyResouces = jyResoucesDao.findJyResoucesById(jySchoolResouces.getId());
        iJySchoolResoucesDao.saveJySchoolResouces(jySchoolResouces);
        //修改该资源的分享状态
        jyResouces.setShareStatus(1);
        jyResoucesDao.updateJyResouces(jyResouces);
    }

    /**
     * 删除文件
     * @param jyResouces
     */
    @Transactional
    public void deleteFile(JyResouces jyResouces){
        if (jyResouces.getRowDatas().length > 0) {
            iJyResoucesTypeDao.deleteManyResoucesType(jyResouces);
            //删除文件编号的校本资源
            iJySchoolResoucesDao.deleteByResoucesType(jyResouces);
            //删除文件夹下面的所有文件
            jyResoucesDao.deleteManyResoucesByFileId(jyResouces);

        }
        if (jyResouces.getRowData().length > 0) {
            jyResoucesDao.deleteManyResouces(jyResouces);
            //删除文件编号的校本资源
            iJySchoolResoucesDao.deleteByResoucesId(jyResouces);
        }
    }
    @Transactional
    public void deleteJyResoucesType(String id){
        JyResouces jyResouces = new JyResouces();
        jyResouces.setRowDatas(new String[]{id});
        iJyResoucesTypeDao.deleteJyResoucesType(id);
        //删除文件夹，在删除文件夹下所有文件
        jyResoucesDao.deleteManyResoucesByFileId(jyResouces);
        iJySchoolResoucesDao.deleteByResoucesType(jyResouces);
    }
    @Transactional
    public void noShare(JyResouces jyResouces){
        jyResouces.setShareStatus(0);
        //先修改资源的状态
        jyResoucesDao.updateManyResouces(jyResouces);
        //删除对应的校本资源的文件
        //删除文件编号的校本资源
        iJySchoolResoucesDao.deleteByResoucesId(jyResouces);
    }

    /**
     *
     * 去重复
     * @param jyResouces
     * @return
     */
    @Transactional
    public int changeResouces(JyResouces jyResouces){
        return jyResoucesDao.changeResouces(jyResouces);
    }
}
