package com.yice.edu.cn.osp.service.dm.honourRoll;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.osp.feignClient.dm.honourRoll.DmHonourRollStudentFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmHonourRollStudentService {
    @Autowired
    private DmHonourRollStudentFeign dmHonourRollStudentFeign;

    public DmHonourRollStudent findDmHonourRollStudentById(String id) {
        return dmHonourRollStudentFeign.findDmHonourRollStudentById(id);
    }

    public DmHonourRollStudent saveDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.saveDmHonourRollStudent(dmHonourRollStudent);
    }

    public List<DmHonourRollStudent> findDmHonourRollStudentListByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
    }

    public DmHonourRollStudent findOneDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.findOneDmHonourRollStudentByCondition(dmHonourRollStudent);
    }

    public long findDmHonourRollStudentCountByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.findDmHonourRollStudentCountByCondition(dmHonourRollStudent);
    }

    public void updateDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent) {
        dmHonourRollStudentFeign.updateDmHonourRollStudent(dmHonourRollStudent);
    }

    public void deleteDmHonourRollStudent(String id) {
        dmHonourRollStudentFeign.deleteDmHonourRollStudent(id);
    }

    public void deleteDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent) {
        dmHonourRollStudentFeign.deleteDmHonourRollStudentByCondition(dmHonourRollStudent);
    }

    public long findDmHonourRollStudentCountByConditions(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.findDmHonourRollStudentCountByConditions(dmHonourRollStudent);
    }

    public List<DmHonourRollStudent> findDmHonourRollStudentListByConditions(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
    }
    public Workbook explorerHonourRoll(DmHonourRollStudent dmHonourRollStudent) {
        Pager pager = new Pager();
        pager.setPaging(false);
        dmHonourRollStudent.setPager(pager);
        List<DmHonourRollStudent> dmHonourRollStudentList = dmHonourRollStudentFeign.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
        ExportParams exportParams = new ExportParams("光荣榜汇总","光荣榜汇总", ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        return ExcelExportUtil.exportExcel(exportParams,
                DmHonourRollStudent.class, dmHonourRollStudentList);
    }
}
