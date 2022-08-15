package com.yice.edu.cn.jy.service.handout;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.jy.dao.handout.IHandoutDao;
import com.yice.edu.cn.jy.dao.handout.IHandoutFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HandoutService {
    @Autowired
    private IHandoutDao handoutDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IHandoutFileDao handoutFileDao;
    @Transactional(readOnly = true)
    public Handout findHandoutById(String id) {
        return handoutDao.findHandoutById(id);
    }
    @Transactional
    public void saveHandout(Handout handout) {
        handout.setId(sequenceId.nextId());
        List<HandoutFile> handoutFiles = handout.getHandoutFiles();
        handout.setFileNumber(handoutFiles.size());
        handout.setCreateTime(DateUtil.now());
        handoutDao.saveHandout(handout);
        if(handoutFiles!=null){
            handoutFiles.forEach(handoutFile -> {
                handoutFile.setId(sequenceId.nextId());
                handoutFile.setHandoutId(handout.getId());
                handoutFile.setSchoolId(handout.getSchoolId());
                handoutFile.setCreateTime(DateUtil.now());
            });
            handoutFileDao.batchSaveHandoutFile(handoutFiles);
        }
    }
    @Transactional(readOnly = true)
    public List<Handout> findHandoutListByCondition(Handout handout) {
        return handoutDao.findHandoutListByCondition(handout);
    }
    @Transactional(readOnly = true)
    public Handout findOneHandoutByCondition(Handout handout) {
        return handoutDao.findOneHandoutByCondition(handout);
    }
    @Transactional(readOnly = true)
    public long findHandoutCountByCondition(Handout handout) {
        return handoutDao.findHandoutCountByCondition(handout);
    }
    @Transactional
    public void updateHandout(Handout handout) {
        List<HandoutFile> handoutFiles = handout.getHandoutFiles();
        handout.setFileNumber(handoutFiles.size());
        handoutDao.updateHandout(handout);
        if(handoutFiles!=null){
            handoutFiles.forEach(handoutFile -> {
                handoutFile.setId(sequenceId.nextId());
                handoutFile.setHandoutId(handout.getId());
                handoutFile.setSchoolId(handout.getSchoolId());
                handoutFile.setCreateTime(DateUtil.now());
            });
            HandoutFile con = new HandoutFile();
            con.setHandoutId(handout.getId());
            handoutFileDao.deleteHandoutFileByCondition(con);
            handoutFileDao.batchSaveHandoutFile(handoutFiles);
        }
    }
    @Transactional
    public void deleteHandout(String id) {
        handoutDao.deleteHandout(id);
        HandoutFile con = new HandoutFile();
        con.setHandoutId(id);
        handoutFileDao.deleteHandoutFileByCondition(con);
    }
    @Transactional
    public void deleteHandoutByCondition(Handout handout) {
        handoutDao.deleteHandoutByCondition(handout);
    }
    @Transactional
    public void batchSaveHandout(List<Handout> handouts){
        handoutDao.batchSaveHandout(handouts);
    }

    @Transactional(readOnly = true)
    public List<Handout> findHandoutsByCondition2(Handout handout) {
        String[] searchTimeZone = handout.getSearchTimeZone();

        if (searchTimeZone!=null&&searchTimeZone.length>0){
            handout.setSearchStearTime(searchTimeZone[0]);
            handout.setSearchEndTime(searchTimeZone[1]);
        }
        return handoutDao.findHandoutsByCondition2(handout);
    }
    @Transactional(readOnly = true)
    public long findHandoutCountByCondition2(Handout handout) {
        String[] searchTimeZone = handout.getSearchTimeZone();

        if (searchTimeZone!=null&&searchTimeZone.length>0){
            handout.setSearchStearTime(searchTimeZone[0]);
            handout.setSearchEndTime(searchTimeZone[1]);
        }
        return handoutDao.findHandoutCountByCondition2(handout);
    }


}
