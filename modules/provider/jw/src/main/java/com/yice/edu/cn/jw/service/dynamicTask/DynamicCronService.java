package com.yice.edu.cn.jw.service.dynamicTask;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import com.yice.edu.cn.jw.dao.dynamicTask.IDynamicCronDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DynamicCronService {
    @Autowired
    private IDynamicCronDao dynamicCronDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DynamicCron findDynamicCronById(String id) {
        return dynamicCronDao.findDynamicCronById(id);
    }
    @Transactional
    public void saveDynamicCron(DynamicCron dynamicCron) {
        dynamicCron.setId(sequenceId.nextId());
        dynamicCronDao.saveDynamicCron(dynamicCron);
        DynamicCron resetDynamicCron = dynamicCronDao.findDynamicCronById("0");
        if (resetDynamicCron==null){
            DynamicCron resetDynamicCron1 = new DynamicCron();
            resetDynamicCron1.setType("0");
            resetDynamicCron1.setId("0");
            resetDynamicCron1.setCron("0 0 5 * * ?");
            resetDynamicCron1.setTaskDescription("定时更新动态定时任务列表用的任务");
            resetDynamicCron1.setCreateTime(DateUtil.now());
            dynamicCronDao.saveDynamicCron(resetDynamicCron1);
        }
    }
    @Transactional(readOnly = true)
    public List<DynamicCron> findDynamicCronListByCondition(DynamicCron dynamicCron) {
        return dynamicCronDao.findDynamicCronListByCondition(dynamicCron);
    }
    @Transactional(readOnly = true)
    public DynamicCron findOneDynamicCronByCondition(DynamicCron dynamicCron) {
        return dynamicCronDao.findOneDynamicCronByCondition(dynamicCron);
    }
    @Transactional(readOnly = true)
    public long findDynamicCronCountByCondition(DynamicCron dynamicCron) {
        return dynamicCronDao.findDynamicCronCountByCondition(dynamicCron);
    }
    @Transactional
    public void updateDynamicCron(DynamicCron dynamicCron) {
        dynamicCronDao.updateDynamicCron(dynamicCron);
    }
    @Transactional
    public void deleteDynamicCron(String id) {
        dynamicCronDao.deleteDynamicCron(id);
    }
    @Transactional
    public void deleteDynamicCronByCondition(DynamicCron dynamicCron) {
        dynamicCronDao.deleteDynamicCronByCondition(dynamicCron);
    }
    @Transactional
    public void batchSaveDynamicCron(List<DynamicCron> dynamicCrons){
        dynamicCrons.forEach(dynamicCron -> dynamicCron.setId(sequenceId.nextId()));
        dynamicCronDao.batchSaveDynamicCron(dynamicCrons);
    }

}
