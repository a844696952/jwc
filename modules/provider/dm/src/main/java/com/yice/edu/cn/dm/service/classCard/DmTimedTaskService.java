package com.yice.edu.cn.dm.service.classCard;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.dm.dao.dmTimedTask.IDmTimedTaskDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DmTimedTaskService {
    @Autowired
    private IDmTimedTaskDao dmTimedTaskDao;
    @Autowired
    private DmClassCardService dmClassCardService;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger log = Logger.getLogger("");

    @Transactional(readOnly = true)
    public DmTimedTask findDmTimedTaskById(String id) {
        return dmTimedTaskDao.findDmTimedTaskById(id);
    }

    @Transactional
    public void saveDmTimedTaskAll(DmTimedTask dmTimedTask) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取需要设置定时任务的设备id
        String[] str = dmTimedTask.getEquipments();
         //删除原有的定时设置
        dmTimedTaskDao.deleteDmTimedTaskAll(Arrays.asList(str));
        List<DmTimedTask> li = new ArrayList<>();

       for (String id : Arrays.asList(str)) {
            DmTimedTask task = new DmTimedTask();
            BeanUtils.copyProperties(dmTimedTask,task);
            String id1=sequenceId.nextId();
            task.setId(id1);
            task.setEquipmentId(id);
            task.setCreateTime(formatter.format(new Date()));
            li.add(task);
        }
        dmTimedTaskDao.batchSaveDmTimedTask(li);
        //推送定时任务
        if (str.length > 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(str)
                    .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.parse(dmTimedTask).toString()).setTitle("批量定时任务").setAlert("批量定时任务").setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_EQUIPMENT_TASK).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }

    @Transactional
    public void saveDmTimedTask(DmTimedTask dmTimedTask) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //判断是否配置了定时任务，有责修改，无责新增任务
        DmTimedTask dmTask = dmTimedTaskDao.findDmTimedTaskByEquipmentId(dmTimedTask.getEquipmentId());
        if (dmTask == null) {
            //新增
            dmTimedTask.setId(sequenceId.nextId());
            dmTimedTask.setCreateTime(formatter.format(new Date()));
            dmTimedTaskDao.saveDmTimedTask(dmTimedTask);
        } else {
            //修改
            dmTimedTask.setUpdateTime(formatter.format(new Date()));
            dmTimedTaskDao.updateDmTimedTaskByEquipmentId(dmTimedTask);
        }
        Push push = null;
        push = Push.newBuilder(JpushApp.ECC)
                .setAlias(dmTimedTask.getEquipmentId())
                .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.parse(dmTimedTask).toString()).setTitle("批量定时任务").setAlert("批量定时任务").setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_EQUIPMENT_TASK).build()).build())
                .setOptions(Push.Options.newBuilder().build())
                .build();
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));


    }

    @Transactional(readOnly = true)
    public List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskDao.findDmTimedTaskListByCondition(dmTimedTask);
    }

    @Transactional(readOnly = true)
    public DmTimedTask findOneDmTimedTaskByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskDao.findOneDmTimedTaskByCondition(dmTimedTask);
    }

    @Transactional(readOnly = true)
    public long findDmTimedTaskCountByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskDao.findDmTimedTaskCountByCondition(dmTimedTask);
    }

    @Transactional
    public void updateDmTimedTask(DmTimedTask dmTimedTask) {
        dmTimedTaskDao.updateDmTimedTask(dmTimedTask);
    }

    @Transactional
    public void deleteDmTimedTask(String id) {
        dmTimedTaskDao.deleteDmTimedTask(id);
    }

    @Transactional
    public void deleteDmTimedTaskByCondition(DmTimedTask dmTimedTask) {
        dmTimedTaskDao.deleteDmTimedTaskByCondition(dmTimedTask);
    }

    @Transactional
    public void batchSaveDmTimedTask(List<DmTimedTask> dmTimedTasks) {
        dmTimedTaskDao.batchSaveDmTimedTask(dmTimedTasks);
    }

    @Transactional
    public void informVersion(DmTimedTask dmTimedTask) {
        //获取需要设置定时任务的设备用户名
        String[] str = dmTimedTask.getEquipments();
        //默认更新失败
        dmTimedTask.setStatus("1");
        dmTimedTask.setRemark("通知A班牌");
        dmTimedTask.setInstallStatus(1);
        dmTimedTask.setDownStatus(1);
        dmClassCardService.setVersionAll(dmTimedTask);
        //推送班牌升级
        if (str.length > 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(str)
                    .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.toJsonStr(dmTimedTask)).setTitle("批量更新班牌").setAlert(JSONUtil.toJsonStr(dmTimedTask)).setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_VERSION_TASK).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }

}
