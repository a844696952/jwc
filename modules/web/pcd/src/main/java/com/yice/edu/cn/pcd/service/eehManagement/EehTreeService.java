package com.yice.edu.cn.pcd.service.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.pcd.feignClient.eehManagement.EehTreeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehTreeService {
    @Autowired
    private EehTreeFeign eehTreeFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public EehTree findEehTreeById(String id) {
        return eehTreeFeign.findEehTreeById(id);
    }

    public EehTree saveEehTree(EehTree eehTree) {
        return eehTreeFeign.saveEehTree(eehTree);
    }

    public void batchSaveEehTree(List<EehTree> eehTrees){
        eehTreeFeign.batchSaveEehTree(eehTrees);
    }

    public List<EehTree> findEehTreeListByCondition(EehTree eehTree) {
        return eehTreeFeign.findEehTreeListByCondition(eehTree);
    }

    public EehTree findOneEehTreeByCondition(EehTree eehTree) {
        return eehTreeFeign.findOneEehTreeByCondition(eehTree);
    }

    public long findEehTreeCountByCondition(EehTree eehTree) {
        return eehTreeFeign.findEehTreeCountByCondition(eehTree);
    }

    public void updateEehTree(EehTree eehTree) {
        eehTreeFeign.updateEehTree(eehTree);
    }

    public void updateEehTreeForAll(EehTree eehTree) {
        eehTreeFeign.updateEehTreeForAll(eehTree);
    }

    public void deleteEehTree(String id) {
        eehTreeFeign.deleteEehTree(id);
    }

    public void deleteEehTreeByCondition(EehTree eehTree) {
        eehTreeFeign.deleteEehTreeByCondition(eehTree);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<String> findChildEehId(String id){
        return eehTreeFeign.findChildEehId(id);
    }

    public List<EehTree> findEehSchoolListNoCondition(EehTree eehTree) {
        return eehTreeFeign.findEehSchoolListNoCondition(eehTree);
    }

}
