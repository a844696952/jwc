package com.yice.edu.cn.xw.service.dj.partyMemberActivity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.xw.dao.dj.partyMemberActivity.IXwDjActivityDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberActivity.IXwDjActivityUserDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberFile.IXwDjAttachmentFileDao;
import com.yice.edu.cn.xw.service.dj.partyMemberFile.XwDjAttachmentFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class XwDjActivityService {
    @Autowired
    private IXwDjActivityDao xwDjActivityDao;
    @Autowired
    private IXwDjActivityUserDao xwDjActivityUserDao;
    @Autowired
    private IXwDjAttachmentFileDao xwDjAttachmentFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private XwDjAttachmentFileService xwDjAttachmentFileService;

    @Autowired
    private XwDjActivityUserService xwDjActivityUserService;

    public XwDjActivityAndUserVo findTeacherXwDjActivityById(XwDjActivity xwDjActivityAndUserVo) {
        XwDjActivityUser xwDjActivityUser = new XwDjActivityUser();
        xwDjActivityUser.setActivityId(xwDjActivityAndUserVo.getId());
        autoSignIn(xwDjActivityUser);
        XwDjActivityAndUserVo xwDjActivity = xwDjActivityDao.findTeacherXwDjActivityById(xwDjActivityAndUserVo);
        if (xwDjActivity==null){
            return new XwDjActivityAndUserVo();
        }
        //设置附件
        setDjActiveParams(xwDjActivity);
        //设置Status
        setStatus(xwDjActivity);
        return xwDjActivity;
    }
    @Transactional(readOnly = true)
    public XwDjActivity findXwDjActivityById(String id) {
        XwDjActivity xwDjActivity = xwDjActivityDao.findXwDjActivityById(id);
        //设置附件
        setDjActiveParams(xwDjActivity);
        return xwDjActivity;
    }

    private void setDjActiveParams(XwDjActivity xwDjActivity) {
        XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
        if(!Objects.isNull(xwDjActivity)){
            xwDjAttachmentFile.setReferenceId(xwDjActivity.getId());
            List<XwDjAttachmentFile> list = xwDjAttachmentFileDao.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
            xwDjActivity.setAttachmentFileList(list);
            //设置签到责任人
            XwDjActivityUser xwDjActivityUser = new XwDjActivityUser();
            xwDjActivityUser.setActivityId(xwDjActivity.getId());
            xwDjActivityUser.setIsSignInLeader(1);
            List<XwDjActivityUser> userList = xwDjActivityUserDao.findXwDjActivityUserListIsSignUpLeader(xwDjActivityUser);
            xwDjActivity.setActivityUserList(userList);
        }
    }

    public List<XwDjActivityUser> findXwDjActivityListByCondition(XwDjActivityUser xwDjActivityUser) {
        autoSignIn(xwDjActivityUser);
        List<XwDjActivityUser> xwDjActivityUsers=null;
        if (xwDjActivityUser.getIsAbsence()!=null&&xwDjActivityUser.getIsAbsence()==1){
            xwDjActivityUsers= xwDjActivityUserDao.findIsAbsenceList(xwDjActivityUser);
        }else {
            xwDjActivityUsers = xwDjActivityUserDao.findUserListByCondition(xwDjActivityUser);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < xwDjActivityUsers.size(); i++) {
            try {
                if (xwDjActivityUsers.get(i).getIsSignUp()==1&&sdf.parse(xwDjActivityUsers.get(i).getActivityEndTime()).getTime()<(new Date()).getTime()&&xwDjActivityUsers.get(i).getIsVacate()==0&&xwDjActivityUsers.get(i).getIsSignIn()==0&&xwDjActivityUsers.get(i).getActivityState()==1){
                    xwDjActivityUsers.get(i).setIsAbsence(1);
                }else {
                    xwDjActivityUsers.get(i).setIsAbsence(0);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return xwDjActivityUsers;
    }
    @Transactional(readOnly = true)
    public long findXwDjActivityListCountByCondition(XwDjActivityUser xwDjActivityUser) {
        XwDjActivity xwDjActivityById = xwDjActivityDao.findXwDjActivityById(xwDjActivityUser.getActivityId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =null;
        try {
            date = sdf.parse(xwDjActivityById.getActivityEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (xwDjActivityUser.getIsAbsence()!=null&&xwDjActivityUser.getIsAbsence()==1&&(System.currentTimeMillis())<date.getTime()){
            return 0;
        }
        if(xwDjActivityUser.getIsAbsence()!=null && xwDjActivityUser.getIsAbsence()==1){
            xwDjActivityUser.setIsSignUp(1);
            xwDjActivityUser.setIsVacate(0);
            xwDjActivityUser.setIsSignIn(0);
        }
        long count = xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser);
        return count;
    }

    /**
     * 统计签到缺席报名发送请假人数
     * @param xwDjActivityUser
     * @return
     */
    @Transactional(readOnly = true)
    public XwDjActivityAndUserVo count(XwDjActivityUser xwDjActivityUser) {
        XwDjActivityUser xwDjActivityUser1 = new XwDjActivityUser();
        XwDjActivityAndUserVo xwDjActivityAndUserVo = new XwDjActivityAndUserVo();
        xwDjActivityUser1.setActivityId(xwDjActivityUser.getActivityId());
        xwDjActivityAndUserVo.setIsSendCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignInLeader(1);
        xwDjActivityAndUserVo.setIsSignInLeaderCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignInLeader(null);
        xwDjActivityUser1.setIsSignUp(1);
        xwDjActivityAndUserVo.setIsSignUpCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignIn(1);
        xwDjActivityAndUserVo.setIsSignInCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignIn(null);
        xwDjActivityUser1.setIsVacate(1);
        xwDjActivityAndUserVo.setIsVacateCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsVacate(null);
        XwDjActivity xwDjActivityById = xwDjActivityDao.findXwDjActivityById(xwDjActivityUser1.getActivityId());
        if (xwDjActivityById.getActivityState()==1){
        xwDjActivityAndUserVo.setIsAbsenceCount(xwDjActivityUserDao.findIsAbsenceCount(xwDjActivityUser1));
        }else {
            xwDjActivityAndUserVo.setIsAbsenceCount(0);
        }
        return xwDjActivityAndUserVo;
    }
    @Transactional(readOnly = true)
    public List<XwDjActivity> findXwDjActivityPartyMemberListByCondition(XwDjActivity xwDjActivity) {
        if(xwDjActivity.getSearchStartTime()!=null){
            xwDjActivity.setSearchStartTime(DateUtils.getOriginalDateTime(xwDjActivity.getSearchStartTime(),1));
        }
        if(xwDjActivity.getSearchEndTime()!=null){
            xwDjActivity.setSearchEndTime(DateUtils.getOriginalDateTime(xwDjActivity.getSearchEndTime(),2));
        }
        return xwDjActivityDao.findXwDjActivityPartyMemberListByCondition(xwDjActivity);
    }

    @Transactional(readOnly = true)
    public long findXwDjActivityPartyMemberCountByCondition(XwDjActivity xwDjActivity) {
        if(xwDjActivity.getSearchStartTime()!=null){
            xwDjActivity.setSearchStartTime(DateUtils.getOriginalDateTime(xwDjActivity.getSearchStartTime(),1));
        }
        if(xwDjActivity.getSearchEndTime()!=null){
            xwDjActivity.setSearchEndTime(DateUtils.getOriginalDateTime(xwDjActivity.getSearchEndTime(),2));
        }
        return xwDjActivityDao.findXwDjActivityPartyMemberCountByCondition(xwDjActivity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveXwDjActivity(XwDjActivity xwDjActivity) {
        if(StringUtils.isNoneBlank(xwDjActivity.getActivityContent()) && StringUtils.isNoneBlank(xwDjActivity.getActivityTitle())){
            String id = sequenceId.nextId();
            //保存活动表信息
            xwDjActivity.setId(id);
            xwDjActivity.setActivityState(0);
            if(StringUtils.isBlank (xwDjActivity.getApplyStopTime ())){
                //没有截止时间 默认是活动开始时间
                xwDjActivity.setApplyStopTime (xwDjActivity.getActivityStartTime ());
            }

            xwDjActivityDao.saveXwDjActivity(xwDjActivity);
            if(CollUtil.isNotEmpty(xwDjActivity.getAttachmentFileList())){
                List<XwDjAttachmentFile> attachmentFiles = xwDjActivity.getAttachmentFileList().stream().map(x -> {
                    x.setReferenceId(id);
                    x.setSchoolId(xwDjActivity.getSchoolId());
                    x.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    return x;
                }).collect(toList());
                //保存附件信息
                xwDjAttachmentFileService.batchSaveXwDjAttachmentFile(attachmentFiles);
            }
            if(CollUtil.isNotEmpty(xwDjActivity.getActivityUserList())){
                List<XwDjActivityUser>  activityUsers = xwDjActivity.getActivityUserList().stream().map(x -> {
                    x.setActivityId(id);
                    x.setIsSignInLeader(1);
                    x.setSchoolId(xwDjActivity.getSchoolId());
                    x.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    return x;
                }).collect(toList());
                //保存签到负责人信息
                xwDjActivityUserService.batchSaveXwDjActivityUser(activityUsers);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<XwDjActivity> findXwDjActivityListByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityDao.findXwDjActivityListByCondition(xwDjActivity);
    }
    @Transactional(readOnly = true)
    public long findXwDjActivityTeacherCountByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityDao.findXwDjActivityTeacherCountByCondition(xwDjActivity);
    }
    @Transactional(readOnly = true)
    public List<XwDjActivity> findXwDjActivityTeacherListByCondition(XwDjActivity xwDjActivity) {
        List<XwDjActivity> list = xwDjActivityDao.findXwDjActivityTeacherListByCondition(xwDjActivity);
        if(CollUtil.isNotEmpty(list)){
            list.forEach(a->{
                a.setActivityUserId(xwDjActivity.getActivityUserId());
                XwDjActivityAndUserVo teacherXwDjActivityById = xwDjActivityDao.findTeacherXwDjActivityById(a);
                setStatus(teacherXwDjActivityById);
                a.setStatus(teacherXwDjActivityById.getStatus());
            });
        }

        return list;
    }

    @Transactional(readOnly = true)
    public long findXwDjActivityCountByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityDao.findXwDjActivityCountByCondition(xwDjActivity);
    }
    @Transactional(readOnly = true)
    public XwDjActivity findOneXwDjActivityByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityDao.findOneXwDjActivityByCondition(xwDjActivity);
    }
    @Transactional
    public void updateXwDjActivity(XwDjActivity xwDjActivity) {
        xwDjActivityDao.updateXwDjActivity(xwDjActivity);
        String id = xwDjActivity.getId();
        //更新附件
        List<XwDjAttachmentFile> fileList = xwDjActivity.getAttachmentFileList();
        XwDjAttachmentFile xwDjAttachmentFile = new XwDjAttachmentFile();
        xwDjAttachmentFile.setReferenceId(id);
        xwDjAttachmentFileDao.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
        for (XwDjAttachmentFile djAttachmentFile : fileList) {
            djAttachmentFile.setId(sequenceId.nextId());
            djAttachmentFile.setReferenceId(id);
            djAttachmentFile.setSchoolId(xwDjActivity.getSchoolId());
            djAttachmentFile.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            xwDjAttachmentFileDao.saveXwDjAttachmentFile(djAttachmentFile);
        }
        //更新负责人
        List<XwDjActivityUser> userList = xwDjActivity.getActivityUserList();
        xwDjActivityUserDao.deleteXwDjActivityUserByActivityId(id);
        for (XwDjActivityUser djActivityUser : userList) {
            djActivityUser.setId(sequenceId.nextId());
            djActivityUser.setIsSignInLeader(1);
            djActivityUser.setActivityId(id);
            djActivityUser.setSchoolId(xwDjActivity.getSchoolId());
            djActivityUser.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            xwDjActivityUserDao.saveXwDjActivityUser(djActivityUser);
        }
    }
    @Transactional
    public void deleteXwDjActivity(String id) {
        xwDjActivityDao.deleteXwDjActivity(id);
    }
    @Transactional
    public void deleteXwDjActivityByCondition(XwDjActivity xwDjActivity) {
        xwDjActivityDao.deleteXwDjActivityByCondition(xwDjActivity);
    }
    @Transactional
    public void batchSaveXwDjActivity(List<XwDjActivity> xwDjActivitys){
        xwDjActivitys.forEach(xwDjActivity -> xwDjActivity.setId(sequenceId.nextId()));
        xwDjActivityDao.batchSaveXwDjActivity(xwDjActivitys);
    }

    @Transactional
    public void closeXwDjActivity(XwDjActivity xwDjActivity) {
        xwDjActivityDao.closeXwDjActivity(xwDjActivity);
    }
    public void autoSignIn(XwDjActivityUser xwDjActivityUser){
        String activityId = xwDjActivityUser.getActivityId();
        XwDjActivity xwDjActivity = xwDjActivityDao.findXwDjActivityById(activityId);
        if (xwDjActivity.getIsSiginIn()==0){
            return;
        }
        DateTime startTime = DateUtil.parse(xwDjActivity.getActivityStartTime(),  Constant.DATE_FORMATTER);
        Date date = new Date();
        if ((date.getTime()+Constant.ACTIVITY_SIGNTIME.SIGNTIME)>startTime.getTime()){
            xwDjActivityUser.setSignInDate(DateUtil.format(date,"yyyy-MM-dd HH:mm:ss"));
            xwDjActivityUserDao.autoSignIn(xwDjActivityUser);
        }
    }
    public void setStatus(XwDjActivityAndUserVo xwDjActivityAndUserVo){
        long currentTime = (new Date()).getTime();
        long applyStopTime = DateUtil.parse(xwDjActivityAndUserVo.getApplyStopTime(), Constant.DATE_FORMATTER).getTime();
        long activityEndTime = DateUtil.parse(xwDjActivityAndUserVo.getActivityEndTime(), Constant.DATE_FORMATTER).getTime();
        long activityStartTime = DateUtil.parse(xwDjActivityAndUserVo.getActivityStartTime(), Constant.DATE_FORMATTER).getTime();
        if (xwDjActivityAndUserVo.getIsSignUp()==0){//未报名
            if (currentTime>applyStopTime&&currentTime<=activityEndTime){
                xwDjActivityAndUserVo.setStatus(1);//活动报名时间已截止，无法报名
            }else if(currentTime>activityEndTime){
                xwDjActivityAndUserVo.setStatus(2);//活动已结束，无法报名
            }else {
                xwDjActivityAndUserVo.setStatus(3);//报名中
            }
        }else {
            if (xwDjActivityAndUserVo.getActivityState()==3){
                xwDjActivityAndUserVo.setStatus(4);//活动由于意外情况已停止
            }else if (xwDjActivityAndUserVo.getIsVacate()==1) {
                xwDjActivityAndUserVo.setStatus(6);//已请假
            }else if ((activityStartTime-Constant.ACTIVITY_SIGNTIME.SIGNTIME)>currentTime){
                xwDjActivityAndUserVo.setStatus(5);//活动未开始
            }else if (xwDjActivityAndUserVo.getIsSignIn()==1){
                xwDjActivityAndUserVo.setStatus(8);//已签到
            }else if(currentTime<=activityEndTime){
                xwDjActivityAndUserVo.setStatus(7);//待签到
            }else {
                xwDjActivityAndUserVo.setStatus(9);//缺席
            }
        }
    }
}
