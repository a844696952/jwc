package com.yice.edu.cn.ewb.service.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import com.yice.edu.cn.ewb.feignClient.smartPen.DmAnswerSheetFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmAnswerSheetService {
    @Autowired
    private DmAnswerSheetFeign dmAnswerSheetFeign;

    public DmAnswerSheet findDmAnswerSheetById(String id) {
        return dmAnswerSheetFeign.findDmAnswerSheetById(id);
    }

    public DmAnswerSheet saveDmAnswerSheet(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetFeign.saveDmAnswerSheet(dmAnswerSheet);
    }

    public List<DmAnswerSheet> findDmAnswerSheetListByCondition(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetFeign.findDmAnswerSheetListByCondition(dmAnswerSheet);
    }

    public DmAnswerSheet findOneDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetFeign.findOneDmAnswerSheetByCondition(dmAnswerSheet);
    }

    public long findDmAnswerSheetCountByCondition(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetFeign.findDmAnswerSheetCountByCondition(dmAnswerSheet);
    }

    public void updateDmAnswerSheet(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheetFeign.updateDmAnswerSheet(dmAnswerSheet);
    }

    public void deleteDmAnswerSheet(String id) {
        dmAnswerSheetFeign.deleteDmAnswerSheet(id);
    }

    public void deleteDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheetFeign.deleteDmAnswerSheetByCondition(dmAnswerSheet);
    }
}
