package com.yice.edu.cn.osp.service.dm.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.osp.feignClient.dm.classes.DmClassVideoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassVideoService {
    @Autowired
    private DmClassVideoFeign dmClassVideoFeign;

    public DmClassVideo findDmClassVideoById(String id) {
        return dmClassVideoFeign.findDmClassVideoById(id);
    }

    public DmClassVideo saveDmClassVideo(DmClassVideo dmClassVideo) {
        return dmClassVideoFeign.saveDmClassVideo(dmClassVideo);
    }

    public List<DmClassVideo> findDmClassVideoListByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoFeign.findDmClassVideoListByCondition(dmClassVideo);
    }

    public DmClassVideo findOneDmClassVideoByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoFeign.findOneDmClassVideoByCondition(dmClassVideo);
    }

    public long findDmClassVideoCountByCondition(DmClassVideo dmClassVideo) {
        return dmClassVideoFeign.findDmClassVideoCountByCondition(dmClassVideo);
    }

    public void updateDmClassVideo(DmClassVideo dmClassVideo) {
        dmClassVideoFeign.updateDmClassVideo(dmClassVideo);
    }

    public void deleteDmClassVideo(String id) {
        dmClassVideoFeign.deleteDmClassVideo(id);
    }

    public void deleteDmClassVideoByCondition(DmClassVideo dmClassVideo) {
        dmClassVideoFeign.deleteDmClassVideoByCondition(dmClassVideo);
    }

    public void batchDeleteDmClassVideo(List<String> idlist) {
        dmClassVideoFeign.batchDeleteDmClassVideo(idlist);
    }
}
