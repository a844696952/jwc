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
            return new ResponseJson(false,"??????????????????!");
        }
        ScheduleList scheduleList2 = new ScheduleList();
        scheduleList2.setSchoolId(scheduleList.getSchoolId());
        scheduleList2.setFromToId(scheduleList.getFromToId());
        scheduleList2.setTerm(scheduleList.getTerm());
        c = findScheduleListCountByCondition(scheduleList2);
        if(c>0){
            return new ResponseJson(false,"???????????????????????????!");
        }
        String s = sequenceId.nextId();
        scheduleList.setId(s);
        scheduleListDao.saveScheduleList(scheduleList);
        return new ResponseJson(scheduleList);
    }


    /**
     * ?????????????????????
     * @param scheduleList
     */
    @Transactional
    public void updateScheduleAndSavePastSchedule(ScheduleList scheduleList){
        String userId = scheduleList.getUserId();
        //???????????????????????????
        ScheduleList scheduleList1 = new ScheduleList();
        scheduleList1.setSchoolId(scheduleList.getSchoolId());
        scheduleList1.setType(1);
        scheduleList = findOneScheduleListByCondition(scheduleList1);

        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setSchoolId(scheduleList.getSchoolId());
        classSchedule.setScheduleId(scheduleList.getId());
        //??????????????????????????????
        List<ClassSchedule> classSchedules = classScheduleService.findClassScheduleListByConditions(classSchedule);
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(scheduleList.getSchoolId());
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        //??????????????????????????????
        classSchedules.stream().forEach(f->{
            PastClassSchedule pastClassSchedule = new PastClassSchedule();
            BeanUtil.copyProperties(f,pastClassSchedule);
            pastClassSchedule.setCreateTime(DateUtil.now());
            pastClassSchedules.add(pastClassSchedule);
        });
        //???????????????????????????
        PastInit p = new PastInit();
        p.setCount(classScheduleInits.size());
        p.setSchoolId(scheduleList.getSchoolId());
        p.setScheduleId(scheduleList.getId());
        pastInitService.savePastInit(p);
        //???????????????????????????
        scheduleList.setType(2);
        scheduleList.setUserId(userId);
        updateScheduleList(scheduleList);
        //????????????????????????????????????,???????????????????????????
        pastClassScheduleService.batchSavePastClassSchedule(pastClassSchedules);
        //??????????????????
        classScheduleService.deletebatchdelete(classSchedule);
    }

    /**
     * ???????????????????????????????????????????????????
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
     * ????????????
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
            return new ResponseJson(false,"????????????????????????????????????????????????");
        }

    }

    /**
     * ??????????????????????????????
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
        //0-????????????1-????????????2-?????????
        List<ScheduleList> scheduleLists1 = collect.get(0);
        List<ScheduleList> scheduleLists2 = collect.get(1);
        scheduleLists1.addAll(scheduleLists2);
        scheduleLists1.stream().forEach(f->f.setType(2));

        //??????????????????????????????id
        List<String> scheduleIds = new ArrayList<>();
        scheduleLists1.stream().forEach(f->{
            scheduleIds.add(f.getId());
        });
        //????????????????????????????????????????????????
        List<ClassSchedule> classSchedules = classScheduleService.batchSeleteClassScheduleInScheduleId(scheduleIds);
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        //????????????????????????????????????
        classSchedules.stream().forEach(f->{
            PastClassSchedule pastClassSchedule = new PastClassSchedule();
            BeanUtil.copyProperties(f,pastClassSchedule);
            pastClassSchedule.setCreateTime(DateUtil.now());
            pastClassSchedules.add(pastClassSchedule);
        });

        //?????????????????????????????????
        classScheduleService.batchDeleteClassScheduleInScheduleId(scheduleIds);
        //?????????????????????????????????
        if(pastClassSchedules!=null&&pastClassSchedules.size()>0){
            pastClassScheduleService.batchSavePastClassSchedule(pastClassSchedules);
        }
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(scheduleList.getSchoolId());
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        List<PastInit> pastInits = new ArrayList<>();
        scheduleLists1.stream().forEach(f->{
            //???????????????????????????
            PastInit p = new PastInit();
            p.setCount(classScheduleInits.size());
            p.setSchoolId(f.getSchoolId());
            p.setScheduleId(f.getId());
            pastInits.add(p);
        });
        //??????????????????
        pastInitService.batchSavePastInit(pastInits);
        //??????????????????????????????
        scheduleListDao.batchUpdateScheduleList(scheduleLists1);
    }
}
