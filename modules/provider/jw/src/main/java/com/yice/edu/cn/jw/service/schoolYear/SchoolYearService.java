package com.yice.edu.cn.jw.service.schoolYear;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.exception.SchoolYearException;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.jw.dao.school.ISchoolDao;
import com.yice.edu.cn.jw.dao.schoolYear.ISchoolYearDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class SchoolYearService {
    @Autowired
    private ISchoolYearDao schoolYearDao;
    @Autowired
    private ISchoolDao schoolDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public SchoolYear findSchoolYearById(String id) {
        return schoolYearDao.findSchoolYearById(id);
    }
    @Transactional
    public void saveSchoolYear(SchoolYear schoolYear) {
        schoolYear.setId(sequenceId.nextId());
        schoolYearDao.saveSchoolYear(schoolYear);
    }
    @Transactional(readOnly = true)
    public List<SchoolYear> findSchoolYearListByCondition(SchoolYear schoolYear) {
        return schoolYearDao.findSchoolYearListByCondition(schoolYear);
    }
    @Transactional(readOnly = true)
    public SchoolYear findOneSchoolYearByCondition(SchoolYear schoolYear) {
        return schoolYearDao.findOneSchoolYearByCondition(schoolYear);
    }
    @Transactional(readOnly = true)
    public long findSchoolYearCountByCondition(SchoolYear schoolYear) {
        return schoolYearDao.findSchoolYearCountByCondition(schoolYear);
    }
    @Transactional
    public void updateSchoolYear(SchoolYear schoolYear) {
        schoolYearDao.updateSchoolYear(schoolYear);
    }
    @Transactional
    public void updateSchoolYearForAll(SchoolYear schoolYear) {
        schoolYearDao.updateSchoolYearForAll(schoolYear);
    }
    @Transactional
    public void deleteSchoolYear(String id) {
        schoolYearDao.deleteSchoolYear(id);
    }
    @Transactional
    public void deleteSchoolYearByCondition(SchoolYear schoolYear) {
        schoolYearDao.deleteSchoolYearByCondition(schoolYear);
    }
    @Transactional
    public void batchSaveSchoolYear(List<SchoolYear> schoolYears){
        schoolYears.forEach(schoolYear -> schoolYear.setId(sequenceId.nextId()));
        schoolYearDao.batchSaveSchoolYear(schoolYears);
    }

    public String[] findMaxSchoolYear(String schoolId){
        SchoolYear maxSchoolYear = schoolYearDao.findMaxSchoolYear(schoolId);
        if(Objects.nonNull(maxSchoolYear)){
            DateTime currentTime = DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY);
            if(currentTime.isBefore(DateUtil.parse(maxSchoolYear.getNextTermBegin(),Constant.DATE_FORMATTER_DAY)) && currentTime.isAfterOrEquals(DateUtil.parse(maxSchoolYear.getLastTermBegin(),Constant.DATE_FORMATTER_DAY))){
                //上学期时间段
                String startTime=DateUtil.format(DateUtil.beginOfMonth(DateUtil.parse(maxSchoolYear.getLastTermBegin())),Constant.DATE_FORMATTER_DAY);
                String endTime=DateUtil.format(DateUtil.endOfMonth(DateUtil.parse(maxSchoolYear.getNextTermBegin())),Constant.DATE_FORMATTER_DAY);
                return new String[]{startTime,endTime};
            }else{
                String endTime= DateUtil.format(DateUtil.endOfMonth(DateUtil.offset(DateUtil.parse(maxSchoolYear.getLastTermBegin()), DateField.YEAR,1)),Constant.DATE_FORMATTER_DAY)  ;
                String startTime=DateUtil.format(DateUtil.beginOfMonth(DateUtil.parse(maxSchoolYear.getNextTermBegin())),Constant.DATE_FORMATTER_DAY);
                return new String[]{startTime,endTime};
            }
        }
        return null;
    }

    public String[] findCurrentSchoolRange(String schoolId){
        SchoolYear maxSchoolYear = schoolYearDao.findMaxSchoolYear(schoolId);
        if(Objects.nonNull(maxSchoolYear)){
            DateTime currentTime = DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY);
            if(currentTime.isAfterOrEquals(DateUtil.parse(maxSchoolYear.getLastTermBegin(),Constant.DATE_FORMATTER_DAY))
            && currentTime.isBefore(DateUtil.parse(maxSchoolYear.getNextTermBegin(),Constant.DATE_FORMATTER_DAY))){
            //上学期
                String startTime=maxSchoolYear.getLastTermBegin();
                String endTime=maxSchoolYear.getNextTermBegin();
                return new String[]{startTime,endTime};
            }else{
                String startTime = maxSchoolYear.getNextTermBegin();
                String endTime = DateUtil.format(DateUtil.offset(DateUtil.parse(maxSchoolYear.getLastTermBegin()), DateField.YEAR,1),Constant.DATE_FORMATTER_DAY);
                return new String[]{startTime,endTime};
            }

        }
        return null;
    }






    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Cached(name = Constant.Redis.SCHOOL_YEAR,key = "#schoolId",expire = 3600)
    @Transactional(readOnly = true)
    public CurSchoolYear findCurSchoolYear(String schoolId) {
        SchoolYear schoolYear = new SchoolYear();
        Pager pager = new Pager().setPaging(true).setPageSize(1).setPage(1).setSortField("fromYear").setSortOrder(Pager.DESC);
        schoolYear.setPager(pager);
        schoolYear.setSchoolId(schoolId);
        List<SchoolYear> schoolYears = schoolYearDao.findSchoolYearListByCondition(schoolYear);
        if(schoolYears.size()==0){
            throw new SchoolYearException("没该学校的学年数据");
        }
        SchoolYear sy = schoolYears.get(0);
        if(sy.getLastTermBegin()==null||sy.getNextTermBegin()==null){
            throw new SchoolYearException("该学校没有上下学期的开始时间");
        }
        CurSchoolYear curSchoolYear = new CurSchoolYear();
        curSchoolYear.setSchoolYearId(schoolYears.get(0).getId());
        curSchoolYear.setFromTo(schoolYears.get(0).getFromTo());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (today.compareTo(sy.getLastTermBegin()) >= 0 && today.compareTo(sy.getNextTermBegin()) < 0) {
            curSchoolYear.setTerm(0);
        } else {
            curSchoolYear.setTerm(1);
        }
        return curSchoolYear;
    }
    //清除学校的当前学年学期的缓存
    @CacheInvalidate(name=Constant.Redis.SCHOOL_YEAR, key="#schoolId")
    public void clearCurSchoolYear(String schoolId){ }

}
