package com.yice.edu.cn.yed.service.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.yed.feignClient.baseData.eehManagement.EehTreeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehTreeService {
    @Autowired
    private EehTreeFeign eehTreeFeign;

    public EehTree findEehTreeById(String id) {
        return eehTreeFeign.findEehTreeById(id);
    }

    public EehTree saveEehTree(EehTree eehTree) {
        return eehTreeFeign.saveEehTree(eehTree);
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

    public void deleteEehTree(String id) {
        eehTreeFeign.deleteEehTree(id);
    }

    public void deleteEehTreeByCondition(EehTree eehTree) {
        eehTreeFeign.deleteEehTreeByCondition(eehTree);
    }

    public List<EehTree> findAllTreeMenu(String type){
        return eehTreeFeign.findAllTreeMenu(type);
    }

    public EehTree lookEehTreeNewById(String id) {
        return eehTreeFeign.lookEehTreeNewById(id);
    }
}
