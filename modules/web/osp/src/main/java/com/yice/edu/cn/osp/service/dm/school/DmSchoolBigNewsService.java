package com.yice.edu.cn.osp.service.dm.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.osp.feignClient.dm.school.DmSchoolBigNewsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmSchoolBigNewsService {
    @Autowired
    private DmSchoolBigNewsFeign dmSchoolBigNewsFeign;

    public DmSchoolBigNews findDmSchoolBigNewsById(String id) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsById(id);
    }

    public DmSchoolBigNews saveDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.saveDmSchoolBigNews(dmSchoolBigNews);
    }

    public List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
    }

    public DmSchoolBigNews findOneDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findOneDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }

    public long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
    }

    public void updateDmSchoolBigNews(DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNewsFeign.updateDmSchoolBigNews(dmSchoolBigNews);
    }

    public void deleteDmSchoolBigNews(String id) {
        dmSchoolBigNewsFeign.deleteDmSchoolBigNews(id);
    }

    public void deleteDmSchoolBigNewsByCondition(DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNewsFeign.deleteDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }

    public List<DmSchoolBigNews> findDmSchoolBigNewsListByactiveNameLike(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsListByactiveNameLike(dmSchoolBigNews);
    }

    public Long findDmSchoolBigNewsListByactiveNameLikeCount(DmSchoolBigNews dmSchoolBigNews) {
        return dmSchoolBigNewsFeign.findDmSchoolBigNewsListByactiveNameLikeCount(dmSchoolBigNews);
    }
}
