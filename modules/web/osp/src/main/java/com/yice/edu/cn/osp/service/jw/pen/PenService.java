package com.yice.edu.cn.osp.service.jw.pen;

import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import com.yice.edu.cn.osp.feignClient.jw.pen.PenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenService {
    @Autowired
    private PenFeign penFeign;

    public Pen findPenById(String id) {
        return penFeign.findPenById(id);
    }

    public Pen savePen(Pen pen) {
        return penFeign.savePen(pen);
    }

    public List<Pen> findPenListByCondition(Pen pen) {
        return penFeign.findPenListByCondition(pen);
    }

    public Pen findOnePenByCondition(Pen pen) {
        return penFeign.findOnePenByCondition(pen);
    }

    public long findPenCountByCondition(Pen pen) {
        return penFeign.findPenCountByCondition(pen);
    }

    public void updatePen(Pen pen) {
        penFeign.updatePen(pen);
    }

    public void deletePen(String id) {
        penFeign.deletePen(id);
    }

    public void deletePenByCondition(Pen pen) {
        penFeign.deletePenByCondition(pen);
    }
}
