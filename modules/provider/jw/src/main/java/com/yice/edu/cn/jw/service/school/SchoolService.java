package com.yice.edu.cn.jw.service.school;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.jw.dao.school.ISchoolDao;
import com.yice.edu.cn.jw.dao.schoolYear.ISchoolYearDao;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import com.yice.edu.cn.jw.service.teacher.TeacherService;

import cn.hutool.core.date.DateUtil;

@Service
public class SchoolService {
    @Autowired
    private ISchoolDao schoolDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ISchoolYearDao schoolYearDao;
    @Autowired
    private SchoolYearService schoolYearService;
    @Transactional(readOnly = true)
    public School findSchoolById(String id) {
        return schoolDao.findSchoolById(id);
    }

    /**
     * 创建学校的时候,需要传输权限,字典数据给学校等
     * @param school
     */
    @Transactional
    public void saveSchool(School school) {
        String schoolId = sequenceId.nextId();
        school.setId(schoolId);
        schoolDao.saveSchool(school);
        //传输数据字典 todo
        //创建默认的管理员账号
        teacherService.createDefaultAdmin(school);
    }

    @Transactional(readOnly = true)
    public List<School> findSchoolListByCondition(School school) {
        return schoolDao.findSchoolListByCondition(school);
    }
    @Transactional(readOnly = true)
    public long findSchoolCountByCondition(School school) {
        return schoolDao.findSchoolCountByCondition(school);
    }

    /**
     *
     * @param school
     */
    @Transactional
    public void updateSchool(School school) {
        //判断是否有修改名称
        School s = schoolDao.findSchoolById(school.getId());
        if(!s.getName().equals(school.getName())){
            //名称发生编号 更改教师列表中本学校的冗余校名
            teacherService.updateSchoolName(school);
        }
        schoolDao.updateSchool(school);
    }
    @Transactional
    public void deleteSchool(String id) {
        schoolDao.deleteSchool(id);
    }
    @Transactional
    public void deleteSchoolByCondition(School school) {
        schoolDao.deleteSchoolByCondition(school);
    }


    @Cached(name = Constant.Redis.SCHOOL_VALID,key = "#schoolId")
    @Transactional(readOnly = true)
    public String[] getValidStatusFromCache(String schoolId) {
        School school = schoolDao.findSchoolById(schoolId);
        if(school==null){
            return null;
        }
        return new String[]{school.getStatus(),school.getOutTime()};
    }

    /**
     * 这里要走基础的创建学校方法 这样可以创建默认管理员
     */
    @Transactional
    public void saveSchoolAndSchoolYear(SchoolExt schoolExt) {
//        String id = sequenceId.nextId();
//        schoolExt.setId(id);
//        schoolDao.saveSchool(schoolExt);
        this.saveSchool(schoolExt);
        this.saveSchoolYear(schoolExt);
    }
    
    /**
     * 修改学校并且添加该学校的学年
     * @param schoolExt
     */
    @Transactional
    public void updateSchoolAndSaveSchoolYear(SchoolExt schoolExt) {
        this.updateSchool(schoolExt);
        this.saveSchoolYear(schoolExt);
    }
    
    /**
     * 创建学年
     * @param schoolExt
     */
    @Transactional
    public void saveSchoolYear(SchoolExt schoolExt) {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(sequenceId.nextId());
        schoolYear.setSchoolId(schoolExt.getId());
        schoolYear.setFromYear(schoolExt.getFromYear());
        schoolYear.setToYear(schoolExt.getToYear());
        schoolYear.setFromTo(schoolExt.getFromTo());
        schoolYear.setLastTermBegin(schoolExt.getLastTermBegin());
        schoolYear.setNextTermBegin(schoolExt.getNextTermBegin());
        schoolYearDao.saveSchoolYear(schoolYear);
    }
    
    @Transactional
	public Integer prepareRise(String schoolId) {
		School schoolInfo = findSchoolById(schoolId);
		String riseBeginTime = schoolInfo.getRiseBeginTime();
		if(riseBeginTime==null) {
          return Constant.SCHOOL_RISE_RECORD.NO_SET_TIME_RISE;
		}
		CurSchoolYear curSchoolYear = schoolYearService.findCurSchoolYear(schoolId);
		SchoolYear schoolYear  = schoolYearService.findSchoolYearById(curSchoolYear.getSchoolYearId());
		if(schoolYear==null) {
			return Constant.SCHOOL_RISE_RECORD.ERROR_RISE;
		}
		int nowYear = DateUtil.thisYear();
		if(schoolYear.getFromYear().intValue()==nowYear){
			return Constant.SCHOOL_RISE_RECORD.HAS_RISE;
		}
		
		String nowMonthAndDate = DateUtil.format(new Date(), "MM-dd");
		if(schoolYear.getToYear().intValue()==nowYear) {
			if(nowMonthAndDate.compareTo(riseBeginTime)>=0) {
				return Constant.SCHOOL_RISE_RECORD.BEGIN_RISE;
			}else {
				return Constant.SCHOOL_RISE_RECORD.NOT_BEGIN_RISE;
			}
		}
		if(schoolYear.getToYear().intValue()<nowYear) {
			if(nowMonthAndDate.compareTo(riseBeginTime)>=0) {
				return Constant.SCHOOL_RISE_RECORD.BEGIN_RISE;
			}else {
				return Constant.SCHOOL_RISE_RECORD.ERROR_MANY_YEARS_NO_RISE;
			}
		}
		return Constant.SCHOOL_RISE_RECORD.ERROR_RISE;
	}

    /**
     * 200表示正常，2001-学校到期，2002-学校禁用，2003-升学状态异常
     * 2004-正在升学
     * @param schoolId
     * @return
     */
    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId) {

        School schoolInfo = findSchoolById(schoolId);
        if (DateUtil.parseDate(schoolInfo.getOutTime()).before(DateUtil.date())) {
            return new ResponseJson(false, Constant.SCHOOL_CODE.SCHOOL_DUE, "学校已到期," + Constant.SCHOOL_CODE.SCHOOL_HIT);
        }
        if (!("42".equals(schoolInfo.getStatus()))) {
            return new ResponseJson(false, Constant.SCHOOL_CODE.SCHOOL_DISABLED, "学校已禁止使用," + Constant.SCHOOL_CODE.SCHOOL_HIT);
        }
        Integer integer = prepareRise(schoolId);
        switch (integer) {
            case 1:
            case 4:
            case 6:
            case 7:
                return new ResponseJson(false, Constant.SCHOOL_CODE.SCHOOL_ERROR, "升学状态异常," + Constant.SCHOOL_CODE.SCHOOL_HIT);
            case 5:
                return new ResponseJson(false, Constant.SCHOOL_CODE.SCHOOL_CAREERS, "学校正在升学," + Constant.SCHOOL_CODE.SCHOOL_HIT);
            default:
                return new ResponseJson(true, 200);
        }
    }

    @Transactional(readOnly = true)
    public long findSchoolNoRepetitionSchoolName(School school){
        return schoolDao.findSchoolNoRepetitionSchoolName(school);
    }
}
