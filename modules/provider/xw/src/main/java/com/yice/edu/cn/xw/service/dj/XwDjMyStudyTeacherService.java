package com.yice.edu.cn.xw.service.dj;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.*;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjMyStudyTeacher;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.xw.dao.dj.IDjClassifyDao;
import com.yice.edu.cn.xw.dao.dj.IXwDjMyStudyTeacherDao;
import com.yice.edu.cn.xw.dao.dj.IXwDjStudyResourceDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class XwDjMyStudyTeacherService {
    @Autowired
    private IXwDjMyStudyTeacherDao xwDjMyStudyTeacherDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IXwDjStudyResourceDao xwDjStudyResourceDao;
    @Autowired
    private IDjClassifyDao iDjClassifyDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mot;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional
    public XwDjMyStudyTeacher findXwDjMyStudyTeacherById(String id) {
        XwDjMyStudyTeacher xw = xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherById(id);

        if (!StringUtils.isEmpty(xw) && xw.getState() == Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_NO_STUDY) {
            xw.setState(Constant.MY_STUDY_TEACHER_RESOURCE.TEACHER_STUDY);
            xwDjMyStudyTeacherDao.updateXwDjMyStudyTeacher(xw);

            //更新学习资源表的学习人数
            XwDjStudyResource xwDjStudyResource = xwDjStudyResourceDao.findXwDjStudyResourceById(xw.getStudyResourceId());
            XwDjStudyResource xwDjStudyResource1 = new XwDjStudyResource();
            xwDjStudyResource1.setId(xwDjStudyResource.getId());
            xwDjStudyResource1.setStudyNumber(xwDjStudyResource.getStudyNumber() + 1);
            xwDjStudyResourceDao.updateXwDjStudyResource(xwDjStudyResource1);

            return xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherById(id);
        } else {
            return xw;
        }
    }

    @Transactional
    public void saveXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        //同一个资源文件不能重复发送给同一个教师
        XwDjMyStudyTeacher xwDjMyStudyTeacher1 = new XwDjMyStudyTeacher();
        xwDjMyStudyTeacher1.setStudyResourceId(xwDjMyStudyTeacher.getStudyResourceId());
        xwDjMyStudyTeacher1.setTeacherId(xwDjMyStudyTeacher.getTeacherId());
        List<XwDjMyStudyTeacher> list = xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher1);
        if (!list.isEmpty()) return;

        String xwDjMyStudyTeacherId = sequenceId.nextId();
        xwDjMyStudyTeacher.setId(xwDjMyStudyTeacherId);
        xwDjMyStudyTeacherDao.saveXwDjMyStudyTeacher(xwDjMyStudyTeacher);

        //查询
        XwDjStudyResource xwDjStudyResource = xwDjStudyResourceDao.findXwDjStudyResourceById(xwDjMyStudyTeacher.getStudyResourceId());

        //设置学习人数
        XwDjStudyResource xwDjStudyResourceFind = new XwDjStudyResource();
        xwDjStudyResourceFind.setId(xwDjMyStudyTeacher.getStudyResourceId());
        xwDjStudyResourceFind.setTeacherNumber(xwDjStudyResource.getTeacherNumber() + 1);

        xwDjStudyResourceDao.updateXwDjStudyResource(xwDjStudyResourceFind);

        //推送消息
        String[] teacherId = new String[]{xwDjMyStudyTeacher.getTeacherId()};
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherId, "党建-学习平台通知", "您有一个新的学习任务，请及时完成！", Extras.XW_DJ_STUDY_TASK, xwDjMyStudyTeacher.getStudyResourceId());
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        System.out.println("学习任务通知：您有一个新的学习任务，请及时完成！");
        //存入数据库
        //saveActiveInfo(xwDjMyStudyTeacher.getStudyResourceId(), "学习任务通知", "您有一个新的学习任务，请及时完成！", Extras.XW_DJ_STUDY_TASK, teacherId);
    }

    /**
     * 学习资源发送教师保存到mongodb
     *
     * @param activityId
     * @param title
     * @param content
     * @param type
     * @param senders
     * @return
     */
    private boolean saveActiveInfo(String activityId, String title, String content, Integer type, String[] senders) {
        PushDetail pushDetail = new PushDetail();
        if (Objects.nonNull(senders) && senders.length > 0) {
            List<Receiver> receivers = new ArrayList<>();
            Receiver receiver = null;
            for (String sender : senders) {
                receiver = new Receiver();
                receiver.setReceiverId(sender);
                receiver.setIsRead(false);
                receivers.add(receiver);
            }
            Map map = new HashMap<>();
            map.put("title", title);
            map.put("content", content);
            pushDetail.setContent(map);
            pushDetail.setId(sequenceId.nextId());
            pushDetail.setSendDate(DateUtil.format(DateTime.now(), "yyyy-MM-dd HH:mm:ss"));
            pushDetail.setReceiverList(receivers);
            pushDetail.setType(type);
            pushDetail.setReferenceId(activityId);

        }
        if (Optional.ofNullable(pushDetail).isPresent()) {
            mot.insert(pushDetail);
        }
        return true;
    }


    @Transactional(readOnly = true)
    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        List<XwDjMyStudyTeacher> xw = xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);

        if (!xw.isEmpty()) {
            xw.stream().forEach(skt -> {
                skt.setTypeName(iDjClassifyDao.findDjClassifyById(skt.getStudyType()).getClassifyName());
            });
        }
        return xw;
    }

    @Transactional(readOnly = true)
    public XwDjMyStudyTeacher findOneXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherDao.findOneXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    @Transactional(readOnly = true)
    public long findXwDjMyStudyTeacherCountByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherCountByCondition(xwDjMyStudyTeacher);
    }

    @Transactional
    public void updateXwDjMyStudyTeacher(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherDao.updateXwDjMyStudyTeacher(xwDjMyStudyTeacher);
    }

    @Transactional
    public void deleteXwDjMyStudyTeacher(String id) {
        xwDjMyStudyTeacherDao.deleteXwDjMyStudyTeacher(id);
    }

    @Transactional
    public void deleteXwDjMyStudyTeacherByCondition(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        xwDjMyStudyTeacherDao.deleteXwDjMyStudyTeacherByCondition(xwDjMyStudyTeacher);
    }

    @Transactional
    public void batchSaveXwDjMyStudyTeacher(List<XwDjMyStudyTeacher> xwDjMyStudyTeachers) {
        xwDjMyStudyTeachers.forEach(xwDjMyStudyTeacher -> xwDjMyStudyTeacher.setId(sequenceId.nextId()));
        xwDjMyStudyTeacherDao.batchSaveXwDjMyStudyTeacher(xwDjMyStudyTeachers);
    }

    public List<StudyTeacherDto> findXwDjMyStudyTeacherListByTeacherId(StudyTeacherDto studyTeacherDto) {
        List<StudyTeacherDto> xw = xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherListByTeacherId(studyTeacherDto);
        if (!xw.isEmpty()) {
            xw.stream().forEach(skt -> {
                skt.setTypeName(iDjClassifyDao.findDjClassifyById(skt.getType()).getClassifyName());
            });
        }
        return xw;
    }

    public long findXwDjMyStudyTeacherCountByTeacherId(StudyTeacherDto studyTeacherDto) {
        return xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherCountByTeacherId(studyTeacherDto);
    }

    @Transactional(readOnly = true)
    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherListByStudyResourceId(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherListByCondition(xwDjMyStudyTeacher);
    }

    public List<XwDjMyStudyTeacher> findXwDjMyStudyTeacherByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        List<XwDjMyStudyTeacher> xw = xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherByTime(xwDjMyStudyTeacher);
        if (!xw.isEmpty()) {
            xw.stream().forEach(skt -> {
                skt.setTypeName(iDjClassifyDao.findDjClassifyById(skt.getStudyType()).getClassifyName());
            });
        }
        return xw;
    }

    public long findXwDjMyStudyTeacherCountByTime(XwDjMyStudyTeacher xwDjMyStudyTeacher) {
        return xwDjMyStudyTeacherDao.findXwDjMyStudyTeacherCountByTime(xwDjMyStudyTeacher);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


}
