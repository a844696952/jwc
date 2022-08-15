package com.yice.edu.cn.osp.service.jy.handout;

import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import com.yice.edu.cn.osp.feignClient.jy.handout.HandoutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandoutService {
    @Autowired
    private HandoutFeign handoutFeign;

    public Handout findHandoutById(String id) {
        return handoutFeign.findHandoutById(id);
    }

    public Handout saveHandout(Handout handout) {
        return handoutFeign.saveHandout(handout);
    }

    public List<Handout> findHandoutListByCondition(Handout handout) {
        return handoutFeign.findHandoutListByCondition(handout);
    }

    public Handout findOneHandoutByCondition(Handout handout) {
        return handoutFeign.findOneHandoutByCondition(handout);
    }

    public long findHandoutCountByCondition(Handout handout) {
        return handoutFeign.findHandoutCountByCondition(handout);
    }

    public void updateHandout(Handout handout) {
        handoutFeign.updateHandout(handout);
    }

    public void deleteHandout(String id) {
        handoutFeign.deleteHandout(id);
    }

    public void deleteHandoutByCondition(Handout handout) {
        handoutFeign.deleteHandoutByCondition(handout);
    }


    public List<Handout> findHandoutsByCondition2(Handout handout){
        return handoutFeign.findHandoutsByCondition2(handout);
    }
    public long findHandoutCountByCondition2(Handout handout) {
        return handoutFeign.findHandoutCountByCondition2(handout);
    }

}
