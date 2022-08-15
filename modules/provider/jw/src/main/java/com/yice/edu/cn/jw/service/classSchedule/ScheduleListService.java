package com.yice.edu.cn.jw.service.classSchedule;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.*;
import com.yice.edu.cn.jw.dao.classSchedule.ScheduleListDao;
import org.apache.commons.collections4.list.PredicatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class ScheduleListService {
    @Autowired
    private ScheduleListDao scheduleListDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ClassScheduleService classScheduleService;
    @Autowired
    private PastClassScheduleService pastClassScheduleService;
    @Autowired
    private PastInitService pastInitService;
    @Autowired
    private ClassScheduleInitService classScheduleInitService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ScheduleList findScheduleListById(String id) {
        return scheduleListDao.findScheduleListById(id);
    }
    @Transactional
    public void saveScheduleList(ScheduleList scheduleList) {
        scheduleList.setId(sequenceId.nextId());
        scheduleListDao.saveScheduleList(scheduleList);
    }
    @Transactional(readOnly = true)
    public List<ScheduleList> findScheduleListListByCondition(ScheduleList scheduleList) {
        return scheduleListDao.findScheduleListListByCondition(scheduleList);
    }
    @Transactional(readOnly = true)
    public ScheduleList findOneScheduleListByCondition(ScheduleList scheduleList) {
        return scheduleListDao.findOneScheduleListByCondition(scheduleList);
    }
    @Transactional(readOnly = true)
    public long findScheduleListCountByCondition(ScheduleList scheduleList) {
        return scheduleListDao.findScheduleListCountByCondition(scheduleList);
    }
    @Transactional
    public void updateScheduleList(ScheduleList scheduleList) {
        scheduleListDao.updateScheduleList(scheduleList);
    }
    @Transactional
    public void updateScheduleListForAll(ScheduleList scheduleList) {
        scheduleListDao.updateScheduleListForAll(scheduleList);
    }
    @Transactional
    public void deleteScheduleList(String id) {
        scheduleListDao.deleteScheduleList(id);
    }
    @Transactional
    public void deleteScheduleListByCondition(ScheduleList scheduleList) {
        scheduleListDao.deleteScheduleListByCondition(scheduleList);
    }
    @Transactional
    public void batchSaveScheduleList(List<ScheduleList> scheduleLists){
        scheduleLists.forEach(scheduleList -> scheduleList.setId(sequenceId.nextId()));
        scheduleListDao.batchSaveScheduleList(scheduleLists);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional
    public ResponseJson saveScheduleListKong(ScheduleList scheduleList){
        ScheduleList scheduleList1 = new ScheduleList();
        scheduleList1.setSchoolId(scheduleList.getSchoolId());
        scheduleList1.setName(scheduleList.getName());
        long c = 0;
        c = findScheduleListCountByCondition(scheduleList1);
        if (c>0){
            return new ResponseJson(false,"课表名称重复!");
        }
        ScheduleList scheduleList2 = new ScheduleList();
        scheduleList2.setSchoolId(scheduleList.getSchoolId());
        scheduleList2.setFromToId(scheduleList.getFromToId());
        scheduleList2.setTerm(scheduleList.getTerm());
        c = findScheduleListCountByCondition(scheduleList2);
        if(c>0){
            return new ResponseJson(false,"课表学年学期已存在!");
        }
        String s = sequenceId.nextId();
        scheduleList.setId(s);
        scheduleListDao.saveScheduleList(scheduleList);
        return new ResponseJson(scheduleList);
    }


    /**
     * 将单个课表过期
     * @param scheduleList
     */
    @Transactional
    public void updateScheduleAndSavePastSchedule(ScheduleList scheduleList){
        String userId = scheduleList.getUserId();
        //查询当前发布的课表
        ScheduleList scheduleList1 = new ScheduleList();
        scheduleList1.setSchoolId(scheduleList.getSchoolId());
        scheduleList1.setType(1);
        scheduleList = findOneScheduleListByCondition(scheduleList1);

        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(scheduleList.getSchoolId());
        classSchedule.setScheduleId(scheduleList.getId());
        //查询出所有关联的课表
        List<ClassSchedule> classSchedules = classScheduleService.findClassScheduleListByConditions(classSchedule);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(scheduleList.getSchoolId());
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        //将课表转化为往期课表
        classSchedules.stream().forEach(f->{
            PastClassSchedule pastClassSchedule = new PastClassSchedule();
            BeanUtil.copyProperties(f,pastClassSchedule);
            pastClassSchedule.setCreateTime(DateUtil.now());
            pastClassSchedules.add(pastClassSchedule);
        });
        //添加往期课表的数量
        PastInit p = new PastInit();
        p.setCount(classScheduleInits.size());
        p.setSchoolId(scheduleList.getSchoolId());
        p.setScheduleId(scheduleList.getId());
        pastInitService.savePastInit(p);
        //将记录修改为已过期
        scheduleList.setType(2);
        scheduleList.setUserId(userId);
        updateScheduleList(scheduleList);
        //将往期课表存储到另一张表,保存当时节数的数量
        pastClassScheduleService.batchSavePastClassSchedule(pastClassSchedules);
        //删除课表记录
        classScheduleService.deletebatchdelete(classSchedule);
    }

    /**
     * 删除课表列表与所有与实际课表的数据
     * @param id
     */
    @Transactional
    public void deleteScheduleAndClassSchedule(String id){
        deleteScheduleList(id);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setScheduleId(id);
        classScheduleService.deletebatchdelete(classSchedule);
    }

    /**
     * 发布课表
     * @param scheduleList
     * @return
     */
    @Transactional
    public ResponseJson issueScheduleListKong(ScheduleList scheduleList){
        ScheduleList scheduleList1 = new ScheduleList();
        scheduleList1.setSchoolId(scheduleList.getSchoolId());
        scheduleList1.setType(1);
        scheduleList1 = findOneScheduleListByCondition(scheduleList1);
        if(scheduleList1 == null){
            updateScheduleList(scheduleList);
            return new ResponseJson();
        }else{
            return new ResponseJson(false,"当前已有发布课表，请先取消发布！");
        }

    }

    /**
     * 升学时，过期所有课表
     * @param schoolId
     */
    @Transactional
    public void pastDueScheduleList(String schoolId){
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setSchoolId(schoolId);
        Pager pager = new Pager();
        pager.setPaging(false);
        scheduleList.setPager(pager);
        List<ScheduleList> scheduleLists = scheduleListDao.findScheduleListListByCondition(scheduleList);
        Map<Integer, List<ScheduleList>> collect = scheduleLists.stream().collect(Collectors.groupingBy(ScheduleList::getType));
        //0-未发布，1-已发布，2-已过期
        List<ScheduleList> scheduleLists1 = collect.get(0);
        List<ScheduleList> scheduleLists2 = collect.get(1);
        scheduleLists1.addAll(scheduleLists2);
        scheduleLists1.stream().forEach(f->f.setType(2));

        //所有需过期的课表管理id
        List<String> scheduleIds = new ArrayList<>();
        scheduleLists1.stream().forEach(f->{
            scheduleIds.add(f.getId());
        });
        //查询出所有需要过期的实际课表数据
        List<ClassSchedule> classSchedules = classScheduleService.batchSeleteClassScheduleInScheduleId(scheduleIds);
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        //将课表数据转化为往期课表
        classSchedules.stream().forEach(f->{
            PastClassSchedule pastClassSchedule = new PastClassSchedule();
            BeanUtil.copyProperties(f,pastClassSchedule);
            pastClassSchedule.setCreateTime(DateUtil.now());
            pastClassSchedules.add(pastClassSchedule);
        });

        //将所有课表过期（删除）
        classScheduleService.batchDeleteClassScheduleInScheduleId(scheduleIds);
        //将课表添加到往期课表中
        if(pastClassSchedules!=null&&pastClassSchedules.size()>0){
            pastClassScheduleService.batchSavePastClassSchedule(pastClassSchedules);
        }
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(scheduleList.getSchoolId());
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        List<PastInit> pastInits = new ArrayList<>();
        scheduleLists1.stream().forEach(f->{
            //添加往期课表的数量
            PastInit p = new PastInit();
            p.setCount(classScheduleInits.size());
            p.setSchoolId(f.getSchoolId());
            p.setScheduleId(f.getId());
            pastInits.add(p);
        });
        //批量添加节数
        pastInitService.batchSavePastInit(pastInits);
        //过期所有未过期的课表
        scheduleListDao.batchUpdateScheduleList(scheduleLists1);
    }
}
