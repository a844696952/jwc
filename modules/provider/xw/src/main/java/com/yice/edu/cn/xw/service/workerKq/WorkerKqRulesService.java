package com.yice.edu.cn.xw.service.workerKq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.pojo.xw.workerKq.PunchRules;
import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.common.util.cron.CronUtil;
import com.yice.edu.cn.xw.feignClient.HolidayFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.DynamicCronFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.ResetDynamicTaskFeign;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class WorkerKqRulesService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private SpecialDataService specialDataService;
    @Autowired
    private HolidayFeign holidayFeign;
    @Autowired
    private DynamicCronFeign dynamicCronFeign;
    @Autowired
    private ResetDynamicTaskFeign resetDynamicTaskFeign;
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public WorkerKqRules findWorkerKqRulesById(String id) {
        return mot.findById(id, WorkerKqRules.class);
    }

    public void saveWorkerKqRules(WorkerKqRules workerKqRules) {
        workerKqRules.setCreateTime(DateUtil.now());
        workerKqRules.setId(sequenceId.nextId());
        mot.insert(workerKqRules);
    }

    public List<WorkerKqRules> findWorkerKqRulesListByCondition(WorkerKqRules workerKqRules) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(WorkerKqRules.class)).find(MongoKit.buildMatchDocument(workerKqRules));
        Pager pager = workerKqRules.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<WorkerKqRules> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(WorkerKqRules.class, document)));
        return list;
    }

    public long findWorkerKqRulesCountByCondition(WorkerKqRules workerKqRules) {
        return mot.getCollection(mot.getCollectionName(WorkerKqRules.class)).countDocuments(MongoKit.buildMatchDocument(workerKqRules));
    }

    public WorkerKqRules findOneWorkerKqRulesByCondition(WorkerKqRules workerKqRules) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(WorkerKqRules.class)).find(MongoKit.buildMatchDocument(workerKqRules));
        MongoKit.appendProjection(query, workerKqRules.getPager());
        return mot.getConverter().read(WorkerKqRules.class, query.first());
    }

    public void updateWorkerKqRules(WorkerKqRules workerKqRules) {
        workerKqRules.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(workerKqRules.getId())), MongoKit.update(workerKqRules), WorkerKqRules.class);
    }

    public void deleteWorkerKqRules(String id) {
        mot.remove(query(where("id").is(id)), WorkerKqRules.class);
    }

    public void deleteWorkerKqRulesByCondition(WorkerKqRules workerKqRules) {
        mot.getCollection(mot.getCollectionName(WorkerKqRules.class)).deleteMany(MongoKit.buildMatchDocument(workerKqRules));
    }

    public void batchSaveWorkerKqRules(List<WorkerKqRules> workerKqRuless) {
        workerKqRuless.forEach(workerKqRules -> workerKqRules.setId(sequenceId.nextId()));
        mot.insertAll(workerKqRuless);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    //当天哪种考勤
    // 1普通  2特殊
    public Integer basicOrSpecial(String id) {
        String today = DateUtil.today();
        SpecialData specialData = new SpecialData();
        specialData.setKqGroupId(id);
        List<SpecialData> list2 = specialDataService.findSpecialDataListByCondition(specialData);
        if (list2.size() > 0) {
            for (SpecialData ss : list2) {
                if (DateUtil.parse(today).isAfterOrEquals(DateUtil.parse(ss.getStartTime())) && DateUtil.parse(today).isBeforeOrEquals(DateUtil.parse(ss.getEndTime()))) {
                    return 2;
                }
            }
        }
        WorkerKqRules workerKqRulesById = workerKqRulesService.findWorkerKqRulesById(id);
        if (workerKqRulesById != null) {
            String[] punchDay = workerKqRulesById.getPunchDay();
            for (String p : punchDay) {
                if (p.equals(String.valueOf(DateUtil.dayOfWeek(DateUtil.parse(today))))) {
                    return 1;
                }
            }
        }
        return 3;
    }

    //特殊是否打卡  特殊需要打卡 1  特殊无需打卡 0  当日无特殊考勤记录 -1
    public Integer ifSpecialNeedCard(String id) {
        String today = DateUtil.today();
        SpecialData specialData = new SpecialData();
        specialData.setKqGroupId(id);
        List<SpecialData> basicDataList = specialDataService.findSpecialDataListByCondition(specialData);
        if (basicDataList.size() > 0) {
            for (SpecialData sp : basicDataList) {
                if (DateUtil.parse(today).isAfterOrEquals(DateUtil.parse(sp.getStartTime())) && DateUtil.parse(today).isBeforeOrEquals(DateUtil.parse(sp.getEndTime()))) {
                    if (sp.getType().equals("0")) {
                        return 0;
                    } else if (sp.getType().equals("1")) {
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    //普通是否打卡  需要打卡 1  无需打卡 0 当日无普通考勤记录 -1
    public Integer ifBasicNeedCard(String id) {
        String today = DateUtil.today();
        WorkerKqRules workerKqRules = workerKqRulesService.findWorkerKqRulesById(id);

        if (workerKqRules != null) {
            //是否自动排休
            if (workerKqRules.getAutoSortHoliday().equals("1")) {
                Holiday holiday = new Holiday();
                holiday.setYear(DateUtil.today().substring(0, 4));
                holiday.setDate(DateUtil.today().substring(5));
                List<Holiday> list1 = holidayFeign.findHolidayListByCondition(holiday);
                if (list1.size() > 0) {
                    for (Holiday ll : list1) {
                        if (today.substring(5).equals(ll.getDate()) && today.substring(0, 4).equals(ll.getYear())) {
                            if (ll.getType().equals("0")) {
                                return 0;
                            }else if (ll.getType().equals("1")){
                                return 1;
                            }
                        }
                    }
                }
            }
            //是否在考勤日期内
            for (int i = 0; i < workerKqRules.getPunchDay().length; i++) {
                if (workerKqRules.getPunchDay()[i].equals(String.valueOf(DateUtil.dayOfWeek(DateUtil.parse(DateUtil.today()))))) {
                    return 1;
                }
            }
            return 0;
        }
        return -1;
    }

    public void createSendTask(WorkerKqRules workerKqRules) {
        PunchRules punchRules = workerKqRules.getPunchRules();
        String punchNumber = workerKqRules.getPunchNumber();
        if (punchNumber.equals("1")){
            String startTime =punchRules.getMorningIn();
            try {
                Calendar nowTime = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                Date date = simpleDateFormat.parse(startTime);
                nowTime.setTime(date);
                nowTime.add(Calendar.MINUTE, 1);
                startTime = df.format(nowTime.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将开始时间改为cron表达式"2000-01-01 12:11:00"
            String cron="error";
            try {
                startTime ="2000-01-01 "+startTime+":00";
                cron = CronUtil.getCron("day",startTime);
                int i = cron.indexOf("?");
                cron = cron.substring(0,i+1);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            //设置类型，验证表中是否有重复的时间任务，保存到mycron表
            DynamicCron dynamicCron =new DynamicCron();
            dynamicCron.setType(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_MORNINGIN);//延时发家长
            dynamicCron.setCron(cron);
            Pager pager = new Pager();
            pager.setPaging(false);
            dynamicCron.setPager(pager);
            List<DynamicCron> dynamicCrons =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron);
            if (dynamicCrons==null||dynamicCrons.size()==0){
                dynamicCron.setTaskDescription("教师上班打卡推送给考勤组管理员");
                dynamicCronFeign.saveDynamicCron(dynamicCron);
            }else {
                //System.out.println("已经存在相同任务");
            }
            ////resetDynamicTaskFeign.resetDynamicTask();
            //System.out.println("更新动态任务成功---》》》");
        }else if (punchNumber.equals("2")){
            String startTime =  punchRules.getMorningIn();
            String startTime1 =  punchRules.getNoonIn();
            //时间加十分钟
            try {
                Calendar nowTime = Calendar.getInstance();
                Calendar nowTime1 = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                Date date = simpleDateFormat.parse(startTime);
                Date date1 = simpleDateFormat.parse(startTime1);
                nowTime.setTime(date);
                nowTime1.setTime(date1);
                nowTime.add(Calendar.MINUTE, 1);
                nowTime1.add(Calendar.MINUTE, 1);
                startTime = df.format(nowTime.getTime());
                startTime1 = df.format(nowTime1.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String cron="error";
            String cron1="error";
            try {
                startTime ="2000-01-01 "+startTime+":00";
                startTime1 ="2000-01-01 "+startTime1+":00";
                cron = CronUtil.getCron("day",startTime);
                cron1 = CronUtil.getCron("day",startTime1);
                int i = cron.indexOf("?");
                int k = cron1.indexOf("?");
                cron = cron.substring(0,i+1);
                cron1 = cron1.substring(0,k+1);
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("获取cron表达式出错了，直接退出方法");
                return;
            }
            //设置类型，验证表中是否有重复的时间任务，保存到mycron表
            DynamicCron dynamicCron =new DynamicCron();
            DynamicCron dynamicCron1 =new DynamicCron();
            dynamicCron.setType(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_MORNINGIN);//延时发家长
            dynamicCron1.setType(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_NOONIN);//准点发教师
            dynamicCron.setCron(cron);
            dynamicCron1.setCron(cron1);
            Pager pager = new Pager();
            pager.setPaging(false);
            dynamicCron.setPager(pager);
            dynamicCron1.setPager(pager);
            List<DynamicCron> dynamicCrons =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron);
            List<DynamicCron> dynamicCrons1 =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron1);
            if (dynamicCrons==null||dynamicCrons.size()==0){
                dynamicCron.setTaskDescription("教师上班打卡推送给考勤组管理员");
                dynamicCronFeign.saveDynamicCron(dynamicCron);
                //System.out.println("教师上班打卡推送给考勤组管理员推送任务入库成功");
            }else {
                //System.out.println("已经存在相同任务");
            }
            if (dynamicCrons1==null||dynamicCrons1.size()==0){
                dynamicCron1.setTaskDescription("教师上班打卡推送给考勤组管理员");
                dynamicCronFeign.saveDynamicCron(dynamicCron1);
                //System.out.println("教师上班打卡推送给考勤组管理员推送任务入库成功");
            }else {
                //System.out.println("已经存在相同任务");
            }
            ////resetDynamicTaskFeign.resetDynamicTask();
            //System.out.println("更新动态任务成功---》》》");
        }

    }

    public void sendToKqManager(String taskId) {
        DynamicCron dynamicCronById = dynamicCronFeign.findDynamicCronById(taskId);
        if (dynamicCronById ==null){
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -1);
        String startTime = df.format(nowTime.getTime());
        KqWorkerDaily wDaily = new KqWorkerDaily();
        WorkerKqRules workerKqRules = new WorkerKqRules();
        PunchRules punchRules = new PunchRules();
        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_MORNINGIN)){
            punchRules.setMorningIn(startTime);
        }else if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_NOONIN)){
            punchRules.setNoonIn(startTime);
        }
        workerKqRules.setPunchRules(punchRules);
        wDaily.setTodayStandardRules(workerKqRules);
        wDaily.setKqDate(DateUtil.today());
        //获得所有这个时间开始上班的考勤组id
        List<KqWorkerDaily> workerKqRuleList =  kqWorkerDailyService.findKqWorkerDailyListByCondition(wDaily);
        if (workerKqRuleList==null||workerKqRuleList.size()==0){
            //System.out.println("没有考勤数据，需要删除任务记录");
            //没有学校在这个任务时间,删除这条任务记录
            dynamicCronFeign.deleteDynamicCron(taskId);
            return;
        }
       /* Map<String,String> mapStr =  workerKqRuleList.stream().collect(Collectors.toMap(workerKqRule->workerKqRule.getGroupId(), workerKqRule -> JSON.toJSONString(workerKqRule) ));
        Set<String> groupIdSet =  mapStr.keySet();*/
        WorkerKqRules wRules = new WorkerKqRules();
        List<WorkerKqRules> workerKqRulesList = findWorkerKqRulesListByCondition(wRules);
        for (WorkerKqRules group:workerKqRulesList){
            List<Teacher> kqGroupManager = group.getKqGroupManager();
            List<String> managerIdList = kqGroupManager.stream().map(Teacher::getId).collect(Collectors.toList());
            if (managerIdList==null||managerIdList.size()==0){
                //System.out.println("这个组没有管理员");
                continue;
            }
            //查找未打卡的员工
            KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
            kqWorkerDaily.setKqDate(DateUtil.today());
            kqWorkerDaily.setGroupId(group.getId());
            kqWorkerDaily.setTodayIsHoliday("0");
            PunchRules thePunchRules = new PunchRules();
            if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_MORNINGIN)){
                thePunchRules.setMorningInStatus("3");
            }else if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_NOONIN)){
                thePunchRules.setNoonInStatus("3");
            }
            kqWorkerDaily.setPunchRules(thePunchRules);
            kqWorkerDaily.setTodayStandardRules(workerKqRules);
            List<KqWorkerDaily> noInMans = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
            if (noInMans!=null&&noInMans.size()>0){
                int size = noInMans.size();
                String alert = "还有人还没打卡，快去看看吧！";
                String[] managerIdArr = new String[managerIdList.size()];
                String nowTime1 = DateUtil.now();
                Map<String,Object> hashMap = new HashMap<>();
                hashMap.put("sendtime",nowTime1);
                final Push push = Push.newBuilder(JpushApp.TAP).getComplexPush(managerIdList.toArray(managerIdArr),"考勤通知",alert, Extras.XW_WORKER_MANAGE_CLOCK_IN_PUSH,hashMap);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                //System.out.println(push);
            }else {
                //System.out.println("该时刻没有缺卡的员工");
            }
        }

    }

}

