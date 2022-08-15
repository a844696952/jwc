package com.yice.edu.cn.dm.service.classes;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.dm.dao.classes.IDmClassVideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmClassVideoService {
    @Autowired
    private IDmClassVideoDao dmClassVideoDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmClassVideo findDmClassVideoById(String id) {
        return dmClassVideoDao.findDmClassVideoById(id);
    }
    @Transactional
    public void saveDmClassVideo(DmClassVideo dmClassVideo) {
        dmClassVideo.setId(sequenceId.nextId());
        dmClassVideoDao.saveDmClassVideo(dmClassVideo);
    }
    @Transactional(readOnly = true)
    public List<DmClassVideo> findDmClassVideoListByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoDao.findDmClassVideoListByCondition(dmClassVideo);
    }
    @Transactional(readOnly = true)
    public DmClassVideo findOneDmClassVideoByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoDao.findOneDmClassVideoByCondition(dmClassVideo);
    }
    @Transactional(readOnly = true)
    public long findDmClassVideoCountByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoDao.findDmClassVideoCountByCondition(dmClassVideo);
    }
    @Transactional
    public void updateDmClassVideo(DmClassVideo dmClassVideo) {
        dmClassVideoDao.updateDmClassVideo(dmClassVideo);
    }
    @Transactional
    public void deleteDmClassVideo(String id) {
        dmClassVideoDao.deleteDmClassVideo(id);
    }
    @Transactional
    public void deleteDmClassVideoByCondition(DmClassVideo dmClassVideo) {
        dmClassVideoDao.deleteDmClassVideoByCondition(dmClassVideo);
    }
    @Transactional
    public void batchSaveDmClassVideo(List<DmClassVideo> dmClassVideos){
        dmClassVideos.forEach(dmClassVideo -> dmClassVideo.setId(sequenceId.nextId()));
        dmClassVideoDao.batchSaveDmClassVideo(dmClassVideos);
    }

    public void batchDeleteDmClassVideo(List<String> idlist) {
        dmClassVideoDao.batchDeleteDmClassVideo(idlist);
    }
}
