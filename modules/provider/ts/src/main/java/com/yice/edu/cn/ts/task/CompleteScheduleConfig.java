package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import com.yice.edu.cn.ts.feignClient.ClassTimeFeign;
import com.yice.edu.cn.ts.feignClient.StuInAndOutStartTimeFeign;
import com.yice.edu.cn.ts.feignClient.WorkerKqRulesFeign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*@Configuration*/
@Component
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {

    /*----具体任务-------------------------------------------*/
    @Autowired
    private ClassTimeFeign classTimeFeign;
    @Autowired
    private WorkerKqRulesFeign workerKqRulesFeign;
    @Autowired
    private StuInAndOutStartTimeFeign stuInAndOutStartTimeFeign;

    /*-----------------------------------------------*/
    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledTask> scheduledTasks = null;
    private Map<String, ScheduledTask> taskFutures = new ConcurrentHashMap<String, ScheduledTask>();

    public ScheduledTaskRegistrar getTaskRegistrar() {
        return taskRegistrar;
    }


    @Mapper
    public interface CronMapper {
        @Select("select * from dynamic_cron order by type asc ")
        List<DynamicCron> getCron();
    }

    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;

    @SuppressWarnings("unchecked")
    private Set<ScheduledTask> getScheduledFutures() {
        if (this.scheduledTasks == null) {

               /* this.scheduledTasks =taskRegistrar.getScheduledTasks();
                System.out.println(scheduledTasks);
                if (scheduledTasks == null){*/
            this.scheduledTasks = new LinkedHashSet(16);
            /*   }
             */
        }
        return scheduledTasks;
    }


    /**
     * 添加任务
     *
     * @param taskId
     * @param triggerTask
     */
    public void addTriggerTask(String taskId, TriggerTask triggerTask) {
        if (taskFutures.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        ScheduledTask scheduledTask = taskRegistrar.scheduleTriggerTask(triggerTask);

        //System.out.println("任务的数量" + getScheduledFutures().size());
        getScheduledFutures();
        this.scheduledTasks.add(scheduledTask);
        taskFutures.put(taskId, scheduledTask);
    }


    /**
     * 取消任务
     *
     * @param taskId
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledTask scheduledTask = taskFutures.get(taskId);
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
        taskFutures.remove(taskId);
        getScheduledFutures().remove(scheduledTask);
    }


    /**
     * 重置任务
     *
     * @param taskId
     * @param triggerTask
     */
    public void resetTriggerTask(String taskId, TriggerTask triggerTask) {
        cancelTriggerTask(taskId);
        addTriggerTask(taskId, triggerTask);
    }

    /**
     * 重置动态任务列表
     */
    public void resetDynamicTask() {
        Set<String> s = taskFutures.keySet();
        //s.remove("0");
        Iterator<String> ss = s.iterator();
        while (ss.hasNext()) {
            cancelTriggerTask(ss.next());
        }
        configureTasks(this.taskRegistrar);
    }
    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        boolean flag = true;
        int i = 0;
        List<DynamicCron> f = cronMapper.getCron();
        while (flag) {
            if (f != null && f.size() > 0 && i < f.size()) {
                final int k = i;
                final DynamicCron d = f.get(k);
                this.addTriggerTask(k + "", new TriggerTask(
                        //1.添加任务内容(Runnable)
                        () -> {
                            if (d.getType().equals(Constant.DYNAMIC_CRON.TASK_REFRESH)) {
                                Set<String> s = taskFutures.keySet();
                                Iterator<String> ss = s.iterator();
                                while (ss.hasNext()) {
                                    cancelTriggerTask(ss.next());
                                }
                                configureTasks(taskRegistrar);
                            }
                            if (d.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_ARRIVE_SCHOOL)||d.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_IN_OUT_SCHOOL)) {
                                //发送给这个时间上课的学校发通知
                                final String taskId = d.getId();
                                classTimeFeign.sendToSchoolTeacherStuNowStatus(taskId);
                            }
                            if (d.getType().equals(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_ARRIVE_SCHOOL)||d.getType().equals(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_IN_OUT_SCHOOL)) {
                                //发送给这个时间上课的学校发通知
                                final String taskId = d.getId();
                                stuInAndOutStartTimeFeign.sendToSchoolTeacherStuNowStatus(taskId);
                            }
                            if (d.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_MORNINGIN)||d.getType().equals(Constant.DYNAMIC_CRON.TASK_TEACHER_CLOCK_IN_NOONIN)) {
                                //发送给这个时间上课的考勤组管理员发通知
                                final String taskId = d.getId();
                                workerKqRulesFeign.sendToKqManager(taskId);
                            }
                            if (d.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_CLASS_END_TIME_CHECK_STATUS)){
                                final String taskId = d.getId();
                                classTimeFeign.statisStudentNowStatusAfterSchool(taskId);
                            }
                        },
                        //2.设置执行周期(Trigger)
                        //2.1 从数据库获取执行周期
                        triggerContext -> {
                            List<DynamicCron> cron = cronMapper.getCron();
                            //2.3 返回执行周期(Date)
                            return new CronTrigger(cron.get(k).getCron()).nextExecutionTime(triggerContext);
                        }
                ));

                i++;
            } else {
                flag = false;
            }
        }
    }
}


