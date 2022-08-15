package com.yice.edu.cn.dm.service.smartPen;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import com.yice.edu.cn.dm.dao.smartPen.IDmCodeResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmCodeResourceService {
    @Autowired
    private IDmCodeResourceDao dmCodeResourceDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public DmCodeResource findDmCodeResourceById(String id) {
        return dmCodeResourceDao.findDmCodeResourceById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDmCodeResource(DmCodeResource dmCodeResource) {
        dmCodeResource.setId(sequenceId.nextId());
        String time = DateUtil.now();
        dmCodeResource.setCreateDate(time);
        dmCodeResource.setCreateTime(time);
        dmCodeResourceDao.saveDmCodeResource(dmCodeResource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<DmCodeResource> findDmCodeResourceListByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceDao.findDmCodeResourceListByCondition(dmCodeResource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public DmCodeResource findOneDmCodeResourceByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceDao.findOneDmCodeResourceByCondition(dmCodeResource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findDmCodeResourceCountByCondition(DmCodeResource dmCodeResource) {
        return dmCodeResourceDao.findDmCodeResourceCountByCondition(dmCodeResource);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDmCodeResource(DmCodeResource dmCodeResource) {
        dmCodeResource.setCreateDate(DateUtil.now());
        dmCodeResourceDao.updateDmCodeResource(dmCodeResource);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDmCodeResource(String id) {
        dmCodeResourceDao.deleteDmCodeResource(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDmCodeResourceByCondition(DmCodeResource dmCodeResource) {
        dmCodeResourceDao.deleteDmCodeResourceByCondition(dmCodeResource);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveDmCodeResource(List<DmCodeResource> dmCodeResources) {
        String createDate = DateUtil.now();
        dmCodeResources.forEach(dmCodeResource -> {
            dmCodeResource.setId(sequenceId.nextId());
            dmCodeResource.setCreateDate(createDate);
            dmCodeResource.setCreateTime(createDate);
        });
        dmCodeResourceDao.batchSaveDmCodeResource(dmCodeResources);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
