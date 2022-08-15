package com.yice.edu.cn.common.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.feign.CurSchoolYearFeign;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurSchoolYearService {
    @Autowired
    private CurSchoolYearFeign curSchoolYearFeign;

    /**
     * 保存业务数据时调用
     * @param curSchoolYear
     * @param schoolId
     */
    public void setSchoolYearTerm(CurSchoolYear curSchoolYear,String schoolId){
        CurSchoolYear cur = curSchoolYearFeign.findCurSchoolYear(schoolId);
        if(StrUtil.isEmpty(curSchoolYear.getSchoolYearId())){
            curSchoolYear.setSchoolYearId(cur.getSchoolYearId());
        }
        if(StrUtil.isEmpty(curSchoolYear.getFromTo())){
            curSchoolYear.setFromTo(cur.getFromTo());
        }
        if(curSchoolYear.getTerm()==null){
            curSchoolYear.setTerm(cur.getTerm());
        }
    }

    /**
     * 查询数据时调用
     * @param curSchoolYear
     * @param schoolId
     */
    public void setSchoolYearId(CurSchoolYear curSchoolYear,String schoolId){
        CurSchoolYear cur = curSchoolYearFeign.findCurSchoolYear(schoolId);
        curSchoolYear.setSchoolYearId(cur.getSchoolYearId());
    }
}
