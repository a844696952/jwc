package com.yice.edu.cn.osp.service.dm.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.osp.feignClient.dm.classes.DmClassDescFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassDescService {
    @Autowired
    private DmClassDescFeign dmClassDescFeign;

    public DmClassDesc findDmClassDescById(String id) {
        return dmClassDescFeign.findDmClassDescById(id);
    }

    public DmClassDesc saveDmClassDesc(DmClassDesc dmClassDesc) {
        return dmClassDescFeign.saveDmClassDesc(dmClassDesc);
    }

    public List<DmClassDesc> findDmClassDescListByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescFeign.findDmClassDescListByCondition(dmClassDesc);
    }

    public DmClassDesc findOneDmClassDescByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescFeign.findOneDmClassDescByCondition(dmClassDesc);
    }

    public long findDmClassDescCountByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescFeign.findDmClassDescCountByCondition(dmClassDesc);
    }

    public void updateDmClassDesc(DmClassDesc dmClassDesc) {
        dmClassDescFeign.updateDmClassDesc(dmClassDesc);
    }

    public void deleteDmClassDesc(String id) {
        dmClassDescFeign.deleteDmClassDesc(id);
    }

    public void deleteDmClassDescByCondition(DmClassDesc dmClassDesc) {
        dmClassDescFeign.deleteDmClassDescByCondition(dmClassDesc);
    }

    public void clearClassDescAndPhoto(String classId){
        dmClassDescFeign.clearClassDescAndPhoto(classId);
    }

    public void batchClearClassDescAndPhoto(String classIds){
        dmClassDescFeign.batchClearClassDescAndPhoto(classIds);
    }

    public List<JwClasses> findJwClassesListByCardCondition(JwClasses jwClasses) {
        return dmClassDescFeign.findJwClassesListByCardCondition(jwClasses);
    }
    public List<JwClasses> findDmClassesListByCardCondition(JwClasses jwClasses) {
        return dmClassDescFeign.findDmClassesListByCardCondition(jwClasses);
    }

    public long findJwClassesCountByCardCondition(JwClasses jwClasses) {
        return dmClassDescFeign.findJwClassesCountByCardCondition(jwClasses);
    }

    public long findDmClassesCountByCardCondition(JwClasses jwClasses) {
        return dmClassDescFeign.findDmClassesCountByCardCondition(jwClasses);
    }
}
