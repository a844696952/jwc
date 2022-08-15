package com.yice.edu.cn.osp.service.dm.honourRoll;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.ExcelStyleUtil;
import com.yice.edu.cn.osp.feignClient.dm.honourRoll.DmHonourRollFeign;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DmHonourRollService {
    @Autowired
    private DmHonourRollFeign dmHonourRollFeign;
    @Autowired
    private TeacherClassesService teacherClassesService;

    public DmHonourRoll findDmHonourRollById(String id) {
        return dmHonourRollFeign.findDmHonourRollById(id);
    }

    public DmHonourRoll saveDmHonourRoll(DmHonourRoll dmHonourRoll) {
        DmHonourRoll s = dmHonourRollFeign.saveDmHonourRoll(dmHonourRoll);

        return s;
    }

    public List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll) {
        if(StringUtils.isBlank(dmHonourRoll.getClassId())){
            dmHonourRoll.setClassId(getFirstClassId());
        }
        return dmHonourRollFeign.findDmHonourRollListByCondition(dmHonourRoll);
    }

    public DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findOneDmHonourRollByCondition(dmHonourRoll);
    }

    public long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll) {
        if(StringUtils.isBlank(dmHonourRoll.getClassId())){
            dmHonourRoll.setClassId(getFirstClassId());
        }
        return dmHonourRollFeign.findDmHonourRollCountByCondition(dmHonourRoll);
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
    public Workbook explorerHonourRoll(DmHonourRoll dmHonourRoll) {
        Pager pager = new Pager();
        pager.setPaging(false);
        dmHonourRoll.setPager(pager);
        List<DmHonourRoll> dmHonourRolls = dmHonourRollFeign.findDmHonourRollListByCondition(dmHonourRoll);
        ExportParams exportParams = new ExportParams("光荣榜列表","光荣榜", ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        return ExcelExportUtil.exportExcel(exportParams,
                DmHonourRoll.class, dmHonourRolls);
    }
    public String getFirstClassId(){
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setPager(new Pager("update_time",Pager.ASC));
        teacherClasses.setSchoolId(mySchoolId());
        teacherClasses.setTeacherId(myId());
        List<Map<String, Object>> t = teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
        if(t.size()==0){
            return null;
        }
        return (String) t.get(0).get("classes_id");
    }
    public long findDmHonourRoll(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findDmHonourRoll(dmHonourRoll);
    }
}
