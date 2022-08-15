package com.yice.edu.cn.ecc.service.honourRoll;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.ecc.feignClient.honourRoll.DmHonourRollFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DmHonourRollService {
    @Autowired
    private DmHonourRollFeign dmHonourRollFeign;
    public DmHonourRoll findDmHonourRollById(String id) {
        return dmHonourRollFeign.findDmHonourRollById(id);
    }

    public DmHonourRoll saveDmHonourRoll(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.saveDmHonourRoll(dmHonourRoll);
    }


    public DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findOneDmHonourRollByCondition(dmHonourRoll);
    }


    public void updateDmHonourRoll(DmHonourRoll dmHonourRoll) {
        dmHonourRollFeign.updateDmHonourRoll(dmHonourRoll);
    }

    public void deleteDmHonourRoll(String id) {
        dmHonourRollFeign.deleteDmHonourRoll(id);
    }

    public void deleteDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        dmHonourRollFeign.deleteDmHonourRollByCondition(dmHonourRoll);
    }

}
