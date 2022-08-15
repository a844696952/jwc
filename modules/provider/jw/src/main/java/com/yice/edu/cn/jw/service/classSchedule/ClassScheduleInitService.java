package com.yice.edu.cn.jw.service.classSchedule;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.jw.dao.classSchedule.IClassScheduleInitDao;
import com.yice.edu.cn.jw.feign.dm.DmClassCardFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassScheduleInitService {
    @Autowired
    private IClassScheduleInitDao classScheduleInitDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ClassScheduleNoonBreakService classScheduleNoonBreakService;
    @Autowired
    private DmClassCardFeign dmClassCardFeign;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ClassScheduleInit findClassScheduleInitById(String id) {
        return classScheduleInitDao.findClassScheduleInitById(id);
    }
    @Transactional
    public void saveClassScheduleInit(ClassScheduleInit classScheduleInit) {
        classScheduleInit.setId(sequenceId.nextId());
        classScheduleInitDao.saveClassScheduleInit(classScheduleInit);
    }
    @Transactional(readOnly = true)
    public List<ClassScheduleInit> findClassScheduleInitListByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitDao.findClassScheduleInitListByCondition(classScheduleInit);
    }
    @Transactional(readOnly = true)
    public ClassScheduleInit findOneClassScheduleInitByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitDao.findOneClassScheduleInitByCondition(classScheduleInit);
    }
    @Transactional(readOnly = true)
    public long findClassScheduleInitCountByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitDao.findClassScheduleInitCountByCondition(classScheduleInit);
    }
    @Transactional
    public void updateClassScheduleInit(ClassScheduleInit classScheduleInit) {
        classScheduleInitDao.updateClassScheduleInit(classScheduleInit);
    }
    @Transactional
    public void updateClassScheduleInitForAll(ClassScheduleInit classScheduleInit) {
        classScheduleInitDao.updateClassScheduleInitForAll(classScheduleInit);
    }
    @Transactional
    public void deleteClassScheduleInit(String id) {
        classScheduleInitDao.deleteClassScheduleInit(id);
    }
    @Transactional
    public void deleteClassScheduleInitByCondition(ClassScheduleInit classScheduleInit) {
        classScheduleInitDao.deleteClassScheduleInitByCondition(classScheduleInit);
    }
    @Transactional
    public void batchSaveClassScheduleInit(List<ClassScheduleInit> classScheduleInits){
        classScheduleInits.forEach(classScheduleInit -> classScheduleInit.setId(sequenceId.nextId()));
        classScheduleInitDao.batchSaveClassScheduleInit(classScheduleInits);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional
    public void batchSaveClassScheduleTime(List<ClassScheduleInit> classScheduleInits,Integer number,String schoolId){

       ClassScheduleInit classScheduleInit = new ClassScheduleInit();
       classScheduleInit.setSchoolId(schoolId);
       //删除掉本校的课表节数时间
       deleteClassScheduleInitByCondition(classScheduleInit);
       //批量添加本校节数时间
       batchSaveClassScheduleInit(classScheduleInits);

       ClassScheduleNoonBreak classScheduleNoonBreak = new ClassScheduleNoonBreak();
       classScheduleNoonBreak.setSchoolId(schoolId);
       //午休位子
       ClassScheduleNoonBreak classScheduleNoonBreak1 = classScheduleNoonBreakService.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
       if(classScheduleNoonBreak1!=null){
           classScheduleNoonBreak1.setNumber(number);
           classScheduleNoonBreakService.updateClassScheduleNoobBreakNumber(classScheduleNoonBreak1);
       }else{
           classScheduleNoonBreak1 = new ClassScheduleNoonBreak();
           classScheduleNoonBreak1.setNumber(number);
           classScheduleNoonBreak1.setSchoolId(schoolId);
           classScheduleNoonBreakService.saveClassScheduleNoonBreak(classScheduleNoonBreak1);
       }
       dmClassCardFeign.cacheInvalidateSchoolCourse(schoolId);
    }

    @Transactional
    public List<ClassScheduleInit> findListClassScheduleInitBySchool(ClassScheduleInit classScheduleInit){
        List<ClassScheduleInit> classScheduleInits = findClassScheduleInitListByCondition(classScheduleInit);
        if(classScheduleInits==null||classScheduleInits.size()==0){
            ClassScheduleInit classScheduleInit1 = new ClassScheduleInit();
            classScheduleInit1.setType(0);
            Pager pager = new Pager();
            pager.setSortField("number");
            pager.setPaging(false);
            pager.setSortOrder(Pager.ASC);
            classScheduleInit1.setPager(pager);
            classScheduleInits = findClassScheduleInitListByCondition(classScheduleInit1);
            if(classScheduleInits==null||classScheduleInits.size()==0){
               classScheduleInits =  initClassScheduleInit();
            }
        }
        return classScheduleInits;
    }

    /**
     * 节数初始数据
     */
    @Transactional
    public List<ClassScheduleInit> initClassScheduleInit(){
        List<ClassScheduleInit> classScheduleInits = new ArrayList<>();
        int start = 8;
        int end = 45;
        for(int i =0;i<Constant.CLASS_SCHEDULE.CLASS_SCHEDULE_NUMBER;i++){
            ClassScheduleInit c = new ClassScheduleInit();
            c.setType(0);
            c.setStartTime(start+":00");
            c.setEndTime(start+":"+end);
            c.setNumber(i+1);
            if(start!=11){
                start++;
            }else{
                start = 14;
            }
            classScheduleInits.add(c);
        }
        batchSaveClassScheduleInit(classScheduleInits);
        return classScheduleInits;
    }
}
