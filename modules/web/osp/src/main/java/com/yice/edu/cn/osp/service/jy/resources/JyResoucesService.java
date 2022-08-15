package com.yice.edu.cn.osp.service.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.osp.feignClient.jy.resources.JyResoucesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyResoucesService {
    @Autowired
    private JyResoucesFeign jyResoucesFeign;

    public JyResouces findJyResoucesById(String id) {
        return jyResoucesFeign.findJyResoucesById(id);
    }

    public JyResouces saveJyResouces(JyResouces jyResouces) {
        return jyResoucesFeign.saveJyResouces(jyResouces);
    }

    public void batchSaveJyResouces(List<JyResouces> jyResoucesList) {
        jyResoucesFeign.batchSaveJyResouces(jyResoucesList);
    }

    public List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesListByCondition(jyResouces);
    }

    public JyResouces findOneJyResoucesByCondition(JyResouces jyResouces) {
        return jyResoucesFeign.findOneJyResoucesByCondition(jyResouces);
    }

    public long findJyResoucesCountByCondition(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesCountByCondition(jyResouces);
    }

    public void updateJyResouces(JyResouces jyResouces) {
        jyResoucesFeign.updateJyResouces(jyResouces);
    }

    public void deleteJyResouces(String id) {
        jyResoucesFeign.deleteJyResouces(id);
    }

    public void deleteJyResoucesByCondition(JyResouces jyResouces) {
        jyResoucesFeign.deleteJyResoucesByCondition(jyResouces);
    }
    /**
     * 返回资源列表和文件夹列表
     * @param jyResouces
     * @return
     */
    public List<JyResouces> findJyResoucesList(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesList(jyResouces);
    }
    /**
     * 返回资源和文件夹记录数
     * @param jyResouces
     * @return
     */
    public long findJyResoucesCount(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesCount(jyResouces);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    public void updateManyResouces(JyResouces jyResouces){
        jyResoucesFeign.updateManyResouces(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    public void deleteManyResouces(JyResouces jyResouces){
        jyResoucesFeign.deleteManyResouces(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    public void deleteManyResoucesByFileId(JyResouces jyResouces){
        jyResoucesFeign.deleteManyResoucesByFileId(jyResouces);
    }

    /**
     * 批量分享
     *      * @param rowData
     */
    public List<JySchoolResouces>  insertManyResouces(JyResouces jyResouces){
        return jyResoucesFeign.insertManyResouces(jyResouces);
    }
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jyResoucesFeign.getFileList(jyCollectionResource);
    }

    /**
     *
     * 去重复
     * @param jyResouces
     * @return
     */
    public int repeatResouces(JyResouces jyResouces){
        return jyResoucesFeign.repeatResouces(jyResouces);
    }

    /**
     * 移除分享
     * @param jyResouces
     */
    public void removeShare(JyResouces jyResouces){
        jyResoucesFeign.removeShare(jyResouces);
    }
    /**
     * 分享文件
     * @param jySchoolResouces
     */
    public void saveJySchoolResouces(JySchoolResouces jySchoolResouces){
        jyResoucesFeign.saveJySchoolResouces(jySchoolResouces);
    }
    public void deleteFile(JyResouces jyResouces){
        jyResoucesFeign.deleteFile(jyResouces);
    }

    public void deleteJyResoucesType(String id){

        jyResoucesFeign.deleteJyResoucesType(id);
    }

    public void noShare(JyResouces jyResouces){
        jyResoucesFeign.noShare(jyResouces);
    }



    public int changeResouces(JyResouces jyResouces){
        return jyResoucesFeign.changeResouces(jyResouces);
    }

}
