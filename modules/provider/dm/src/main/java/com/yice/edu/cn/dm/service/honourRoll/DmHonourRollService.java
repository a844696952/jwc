package com.yice.edu.cn.dm.service.honourRoll;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.dm.dao.classCard.IDmClassCardDao;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollDao;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollStudentDao;
import com.yice.edu.cn.dm.service.classCard.DmClassCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DmHonourRollService {
    @Autowired
    private DmHonourRollDao dmHonourRollDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DmHonourRollStudentDao dmHonourRollStudentDao;
    @Autowired
    private IDmClassCardDao dmClassCardDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Transactional(readOnly = true)
    public DmHonourRoll findDmHonourRollById(String id) {
        DmHonourRoll dmHonourRoll = dmHonourRollDao.findDmHonourRollById(id);
        DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
        Pager pager = new Pager();
        pager.setPaging(false);
        dmHonourRollStudent.setPager(pager);
        dmHonourRollStudent.setType(dmHonourRoll.getType());
        dmHonourRollStudent.setHonourId(dmHonourRoll.getId());
        List<DmHonourRollStudent> dmHonourRollStudentList = dmHonourRollStudentDao.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
        String[] strings = new String[dmHonourRollStudentList.size()];
        for(int i=0;i<dmHonourRollStudentList.size();i++){
            strings[i] = dmHonourRollStudentList.get(i).getStudentId();
        }
        dmHonourRoll.setStudentList(strings);
        return dmHonourRoll;
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveDmHonourRoll(DmHonourRoll dmHonourRoll) {

        DmHonourRoll dmHonourRoll1 = new DmHonourRoll();
        dmHonourRoll1.setSchoolId(dmHonourRoll.getSchoolId());
        dmHonourRoll1.setClassId(dmHonourRoll.getClassId());
        dmHonourRoll1.setClassCardId(dmHonourRoll.getClassCardId());
        dmHonourRoll1.setStatus(2);
//        dmHonourRoll1.setType(dmHonourRoll.getType());
        dmHonourRollDao.stopHonourRoll(dmHonourRoll1);
        String id = sequenceId.nextId();
        dmHonourRoll.setId(id);
        //把获奖的人的名单，冗余到表中
        dmHonourRoll.setStudentName(dmHonourRollDao.getStudentName(dmHonourRoll.getStudentList()));
        List<DmHonourRollStudent> dmHonourRollStudentList = new ArrayList<>();
        //通过班级编号，获取到班级所在的班牌的编号
        DmClassCard d = new DmClassCard();
        d.setClassId(dmHonourRoll.getClassId());
        DmClassCard dmClassCard = dmClassCardDao.findOneDmClassCardByCondition(d);
        //通过班级编号，获取到班级所在的班牌的编号
        dmHonourRoll.setClassCardId(dmClassCard.getId());
        dmHonourRollDao.saveDmHonourRoll(dmHonourRoll);
        Arrays.stream(dmHonourRoll.getStudentList()).forEach(e->{
            DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
            dmHonourRollStudent.setId(sequenceId.nextId());
            dmHonourRollStudent.setStudentId(e);
            dmHonourRollStudent.setHonourId(id);
            dmHonourRollStudent.setType(dmHonourRoll.getType());
            dmHonourRollStudent.setTypeName(dmHonourRoll.getTypeName());
            dmHonourRollStudent.setHonourTime(dmHonourRoll.getHonourTime());
            dmHonourRollStudent.setClassId(dmHonourRoll.getClassId());
            dmHonourRollStudentList.add(dmHonourRollStudent);
        });
        if(dmHonourRollStudentList.size()>0){
            //批量新增数据
            dmHonourRollStudentDao.batchSaveDmHonourRollStudent(dmHonourRollStudentList);
        }
        //新增屏保，开始推送信息
        JpushApp app = JpushApp.getValueById(2);
        DmHonourRoll dh = new DmHonourRoll();
        dh.setId(id);
        dh.setTypeName(dmHonourRoll.getTypeName());
        dh.setHonourTime(dmHonourRoll.getHonourTime());
        try {
            Push push = Push.newBuilder(JpushApp.BMP)
                    .setAlias(dmHonourRoll.getStudentList())
                    .setNotification(Push.Notification.newBuilder().setAlert("恭喜，您的孩子表现优异，被选中"+dmHonourRoll.getTypeName()+"，快去看看吧！").setExtras(Extras.newBuilder().setType(Extras.BMP_HONOUR_ROLL).setJSON(JSONUtil.toJsonStr(dh)).build()).setSound(Constant.JPUSH.SOUND_1).build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Transactional(readOnly = true)
    public List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollDao.findDmHonourRollListByCondition(dmHonourRoll);
    }
    @Transactional(readOnly = true)
    public DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollDao.findOneDmHonourRollByCondition(dmHonourRoll);
    }
    @Transactional(readOnly = true)
    public long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollDao.findDmHonourRollCountByCondition(dmHonourRoll);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateDmHonourRoll(DmHonourRoll dmHonourRoll) {
        DmHonourRollStudent dmHonourRollStudents = new DmHonourRollStudent();
        dmHonourRollStudents.setHonourId(dmHonourRoll.getId());
        //批量删除数据
        dmHonourRollStudentDao.deleteDmHonourRollStudentByCondition(dmHonourRollStudents);
        //把获奖的人的名单，冗余到表中
        dmHonourRoll.setStudentName(dmHonourRollDao.getStudentName(dmHonourRoll.getStudentList()));
        List<DmHonourRollStudent> dmHonourRollStudentList = new ArrayList<>();
        dmHonourRollDao.updateDmHonourRoll(dmHonourRoll);
        Arrays.stream(dmHonourRoll.getStudentList()).forEach(e->{
            DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
            dmHonourRollStudent.setId(sequenceId.nextId());
            dmHonourRollStudent.setStudentId(e);
            dmHonourRollStudent.setHonourId(dmHonourRoll.getId());
            dmHonourRollStudent.setType(dmHonourRoll.getType());
            dmHonourRollStudent.setTypeName(dmHonourRoll.getTypeName());
            dmHonourRollStudent.setUpdateTime(DateUtil.now());
            dmHonourRollStudent.setHonourTime(dmHonourRoll.getHonourTime());
            dmHonourRollStudent.setClassId(dmHonourRoll.getClassId());
            dmHonourRollStudentList.add(dmHonourRollStudent);
        });
        if(dmHonourRollStudentList.size()>0){
            //批量新增数据
            dmHonourRollStudentDao.batchSaveDmHonourRollStudent(dmHonourRollStudentList);
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteDmHonourRoll(String id) {
        dmHonourRollDao.deleteDmHonourRoll(id);
        DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
        dmHonourRollStudent.setHonourId(id);
        dmHonourRollStudentDao.deleteDmHonourRollStudentByCondition(dmHonourRollStudent);
    }
    @Transactional
    public void deleteDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        dmHonourRollDao.deleteDmHonourRollByCondition(dmHonourRoll);
    }
    @Transactional
    public void batchSaveDmHonourRoll(List<DmHonourRoll> dmHonourRolls){
        dmHonourRolls.forEach(dmHonourRoll -> dmHonourRoll.setId(sequenceId.nextId()));
        dmHonourRollDao.batchSaveDmHonourRoll(dmHonourRolls);
    }
    @Transactional(readOnly = true)
    public long findDmHonourRoll(DmHonourRoll dmHonourRoll) {
        return dmHonourRollDao.findDmHonourRoll(dmHonourRoll);
    }

    @Transactional(readOnly = true)
    public DmHonourRoll findDmHonourRollByStudentId(String id){
        DmHonourRoll dmHonourRoll = dmHonourRollDao.findDmHonourRollByStudentId(id);
        if(dmHonourRoll != null){
            dmHonourRoll.setMsg("恭喜!您的孩子表现优异，被选中"+dmHonourRoll.getTypeName()+",快去看看吧!");
        }
        return dmHonourRoll;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDmHonourRollByClassId(DmDeleteData dmDeleteData){
        dmHonourRollDao.deleteDmHonourRollByClassId(dmDeleteData);
    }
}
