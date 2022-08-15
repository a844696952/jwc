package com.yice.edu.cn.xw.service.dj.partyMemberActivity;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.*;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.map.MapUtil;
import com.yice.edu.cn.common.util.qrcode.QRCodeUtil;
import com.yice.edu.cn.xw.dao.dj.partyMemberActivity.IXwDjActivityDao;
import com.yice.edu.cn.xw.dao.dj.partyMemberActivity.IXwDjActivityUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @decription:党建活动用户互动接口
 * @author ly
 */
@Service
public class XwDjActivityUserService {
    @Autowired
    private IXwDjActivityUserDao xwDjActivityUserDao;
    @Autowired
    private IXwDjActivityDao xwDjActivityDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mot;


    @Transactional(readOnly = true)
    public XwDjActivityUser findXwDjActivityUserById(String id) {
        return xwDjActivityUserDao.findXwDjActivityUserById(id);
    }
    @Transactional(readOnly = true)
    public boolean findXwDjActivityUserByActivityUserId(String id) {
        boolean flag=false;
        if (xwDjActivityUserDao.findXwDjActivityUserCountByActivityUserId(id)>0){
            flag=true;
        }else if (xwDjActivityDao.findXwDjActivityCountByActivityCreatorId(id)>0){
            flag=true;
        }
        return flag;
    }
    @Transactional(rollbackFor =Exception.class )
    public void saveXwDjActivityUser(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUser.setId(sequenceId.nextId());
        xwDjActivityUserDao.saveXwDjActivityUser(xwDjActivityUser);
    }
    @Transactional(readOnly = true)
    public List<XwDjActivityAndUserVo> findXwDjActivityUserListByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findXwDjActivityUserListByCondition(xwDjActivityUser);
    }
    @Transactional(readOnly = true)
    public List<XwDjActivityUser> findXwDjActivityUserListCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findXwDjActivityUserListCondition(xwDjActivityUser);
    }
    @Transactional(readOnly = true)
    public List<XwDjActivityUser> findUserListByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findUserListByCondition(xwDjActivityUser);
    }
    /**
     * @Description 获取发送列表总数
     * @Date  2019/5/14 13:44
     * @Param [xwDjActivityUser]
     * @return long
     **/
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public long findUserListCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }

    public List<XwDjActivityUser> findXwDjActivityUserSignUpListByCondition(XwDjActivityUser xwDjActivityUser) {
        autoSignIn(xwDjActivityUser);
        xwDjActivityUser.setIsSignUp(1);
        List<XwDjActivityUser> xwDjActivityUsers=null;
        if (xwDjActivityUser.getIsAbsence()!=null&&xwDjActivityUser.getIsAbsence()==1){
            xwDjActivityUsers= xwDjActivityUserDao.findIsAbsenceList(xwDjActivityUser);
        }else {
            xwDjActivityUsers = xwDjActivityUserDao.findUserListByCondition(xwDjActivityUser);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < xwDjActivityUsers.size(); i++) {
            try {
                if (sdf.parse(xwDjActivityUsers.get(i).getActivityEndTime()).getTime()<(new Date()).getTime()&&xwDjActivityUsers.get(i).getIsVacate()==0&&xwDjActivityUsers.get(i).getIsSignIn()==0&&xwDjActivityUsers.get(i).getActivityState()==1){
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
    public XwDjActivityAndUserVo count(XwDjActivityUser xwDjActivityUser) {
        XwDjActivityUser xwDjActivityUser1 = new XwDjActivityUser();
        xwDjActivityUser1.setActivityId(xwDjActivityUser.getActivityId());
        xwDjActivityUser1.setIsSignUp(1);
        XwDjActivityAndUserVo xwDjActivityAndUserVo = new XwDjActivityAndUserVo();
        xwDjActivityAndUserVo.setIsSignUpCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignIn(1);
        xwDjActivityAndUserVo.setIsSignInCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignIn(null);
        xwDjActivityUser1.setIsSignInLeader(1);
        xwDjActivityAndUserVo.setIsSignInLeaderCount(xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser1));
        xwDjActivityUser1.setIsSignInLeader(null);
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
    public long findSignUpUserListCountByCondition(XwDjActivityUser xwDjActivityUser) {
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
        if(xwDjActivityUser.getIsAbsence()!=null&&xwDjActivityUser.getIsAbsence()==1){
            xwDjActivityUser.setIsVacate(0);
            xwDjActivityUser.setIsSignIn(0);
            xwDjActivityUser.setIsSignUp(0);
        }
        xwDjActivityUser.setIsSignUp(1);
        return xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser);
    }
    @Transactional(readOnly = true)
    public XwDjActivityUser findOneXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findOneXwDjActivityUserByCondition(xwDjActivityUser);
    }
    @Transactional(readOnly = true)
    public long findXwDjActivityUserCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserDao.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }
    @Transactional
    public void updateXwDjActivityUser(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserDao.updateXwDjActivityUser(xwDjActivityUser);
    }
    @Transactional
    public void askForLeave(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserDao.askForLeave(xwDjActivityUser);
    }
    @Transactional
    public void signUp(XwDjActivityUser xwDjActivityUser) {
        Date date = new Date();
        DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=sdf.format(date);
        xwDjActivityUser.setSignInDate(dateStr);
        xwDjActivityUserDao.signUp(xwDjActivityUser);
    }

    @Transactional
    public void deleteXwDjActivityUser(String id) {
        xwDjActivityUserDao.deleteXwDjActivityUser(id);
    }
    @Transactional
    public void deleteXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserDao.deleteXwDjActivityUserByCondition(xwDjActivityUser);
    }

    /**
     * 调用时会把活动状态修改为1
     * @param xwDjActivityUsers
     */
    @Transactional
    public void saveBatchXwDjActivityUser(List<XwDjActivityUser> xwDjActivityUsers){
        if (xwDjActivityUsers==null||xwDjActivityUsers.size()==0){
            return;
        }
        String activityId = xwDjActivityUsers.get(0).getActivityId();
        String schoolId = xwDjActivityUsers.get(0).getSchoolId();
        xwDjActivityUsers.forEach(xwDjActivityUser -> {
            xwDjActivityUser.setId(sequenceId.nextId());
            xwDjActivityUser.setActivityId(activityId);
            xwDjActivityUser.setSchoolId(schoolId);
            xwDjActivityUser.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        });
        XwDjActivity xwDjActivity = new XwDjActivity();
        xwDjActivity.setId(activityId);
        xwDjActivity.setActivityState(1);
        xwDjActivityDao.updateXwDjActivityStatus(xwDjActivity);
        xwDjActivityUserDao.saveBatchXwDjActivityUser(xwDjActivityUsers);
        //推送消息
        String[] sendObjects = xwDjActivityUsers.stream ().map (x -> x.getActivityUserId ()).collect (Collectors.toList ()).toArray (new String[0]);
        //推送给活动发送对象
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(sendObjects,"党建-活动通知","您有一条新的活动通知，请及时查看！", Extras.XW_DJ_ACTIVITY,activityId);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        //saveActiveInfo(activityId,"党建-活动通知","您有一条新的活动通知，请及时查看！",Extras.XW_DJ_ACTIVITY,sendObjects);
    }

    /**
     * 新增活动通知
     * @param activityId
     * @param title
     * @param content
     * @param type
     * @param senders
     * @return
     */
    private  boolean saveActiveInfo(String activityId,String title,String content,Integer type,String [] senders){
        PushDetail pushDetail = new PushDetail ();
        if (Objects.nonNull (senders) && senders.length > 0) {
            List<Receiver> receivers = new ArrayList<> ();
            Receiver receiver = null;
            for (String sender : senders) {
                receiver = new Receiver ();
                receiver.setReceiverId (sender);
                receiver.setIsRead (false);
                receivers.add (receiver);
            }
            Map map = new HashMap<> ();
            map.put ("title", title);
            map.put ("content", content);
            pushDetail.setContent (map);
            pushDetail.setId (sequenceId.nextId());
            pushDetail.setReferenceId(activityId);
            pushDetail.setSendDate (DateUtil.format (DateTime.now (),"yyyy-MM-dd HH:mm:ss"));
            pushDetail.setReceiverList (receivers);
            pushDetail.setType (type);
        }
        if (Optional.ofNullable (pushDetail).isPresent ()) {
            mot.insert (pushDetail);
        }
        return true;
    }




    /**
     * 新建活动是调用，不会修改活动状态
     * @param xwDjActivityUsers
     */
    @Transactional
    public void batchSaveXwDjActivityUser(List<XwDjActivityUser> xwDjActivityUsers){
        if (xwDjActivityUsers==null||xwDjActivityUsers.size()==0){
            return;
        }
        String activityId = xwDjActivityUsers.get(0).getActivityId();
        xwDjActivityUsers.forEach(xwDjActivityUser -> {
            xwDjActivityUser.setId(sequenceId.nextId());
            xwDjActivityUser.setActivityId(activityId);

        });
        xwDjActivityUserDao.saveBatchXwDjActivityUser(xwDjActivityUsers);
    }

    public long findXwDjActivityUserListCountCondition(XwDjActivityUser xwDjActivityUser) {
        return  xwDjActivityUserDao.findUserListCountByCondition(xwDjActivityUser);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public String findXwDjActivityUserSignInQRCode(String id) {
        String idEncode=SimpleCryptoKit.encrypt(id);
        String url="https://www.ycjdedu.com/tap/xwDjActivityUser/update/signIn/"+idEncode;
        String encode =null;
        try {
            encode = QRCodeUtil.encodeNoLogoAndTitle(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        XwDjActivity xwDjActivity = new XwDjActivity();
        xwDjActivity.setId(id);
        xwDjActivity.setActivityQrCode(encode);
        xwDjActivityDao.updateXwDjActivity(xwDjActivity);
        return encode;
    }
    @Transactional
    public JSONObject signIn(XwDjActivityAndUserVo xwDjActivityAndUserVo) {
        JSONObject json=new JSONObject();
        String activityId = xwDjActivityAndUserVo.getActivityId();
        json.put("id",activityId);
        String activityUserId = xwDjActivityAndUserVo.getActivityUserId();
        XwDjActivity xwDjActivityById = xwDjActivityDao.findXwDjActivityById(activityId);
        Date date = new Date();
        String dateStr = DateUtil.format(date, Constant.DATE_FORMATTER);
        DateTime startTime = DateUtil.parse(xwDjActivityById.getActivityStartTime(),  Constant.DATE_FORMATTER);
        DateTime endTime = DateUtil.parse(xwDjActivityById.getActivityEndTime(),  Constant.DATE_FORMATTER);
        if ((date.getTime()+Constant.ACTIVITY_SIGNTIME.SIGNTIME)<startTime.getTime()){
            json.put("success",false);
            json.put("msg","签到未开始");
            return json;
        }
        if (date.getTime()>endTime.getTime()){
            json.put("success",false);
            json.put("msg","活动已结束");
            return json;
        }
        XwDjActivityUser user = new XwDjActivityUser();
        user.setActivityId(activityId);
        user.setActivityUserId(activityUserId);
        XwDjActivityUser oneXwDjActivityUserByCondition = xwDjActivityUserDao.findOneXwDjActivityUserByCondition(user);
        if (oneXwDjActivityUserByCondition==null||oneXwDjActivityUserByCondition.getIsSignUp()==0){
            json.put("success",false);
            json.put("msg","请先报名");
            return json;
        }
        if (oneXwDjActivityUserByCondition.getIsVacate()==1){
            json.put("success",false);
            json.put("msg","已请假");
            return json;
        }
        if (oneXwDjActivityUserByCondition.getIsSignIn()==1){
            json.put("success",false);
            json.put("msg","已签到");
            return json;
        }
        String activityPositionCode = xwDjActivityAndUserVo.getActivityPositionCode();
        String[] code = activityPositionCode.split(",");//"经度,纬度"
        String[] code1 = xwDjActivityById.getActivityPositionCode().split(",");//"纬度,经度"
        double algorithm = MapUtil.algorithm(Double.parseDouble(code[0]), Double.parseDouble(code[1]), Double.parseDouble(code1[1]), Double.parseDouble(code1[0]));
        if (algorithm> Constant.ACTIVITY_DISTANCE.DISTANCE){
            json.put("success",false);
            json.put("msg","签到失败，距离过远");
            return json;
        }
        XwDjActivityUser xwDjActivityUser = new XwDjActivityUser();
        xwDjActivityUser.setActivityId(activityId);
        xwDjActivityUser.setActivityUserId(activityUserId);
        xwDjActivityUser.setSignInDate(dateStr);
        xwDjActivityUserDao.signIn(xwDjActivityUser);
        json.put("success",true);
        json.put("msg","签到成功");
        return json;
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
}
