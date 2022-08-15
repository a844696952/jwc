package com.yice.edu.cn.tap.service.dm.classCard;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.tap.feignClient.classes.JwClassesFeign;
import com.yice.edu.cn.tap.feignClient.dm.classCard.DmClassCardFeign;
import com.yice.edu.cn.tap.feignClient.dm.dmLockScreenPhoto.DmLockScreenPhotoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassCardService {
    @Autowired
    private DmClassCardFeign dmClassCardFeign;
    @Autowired
    private JwClassesFeign jwClassesFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DmLockScreenPhotoFeign dmLockScreenPhotoFeign;

    /**
     * 批量屏幕锁屏：1.先更改班牌表中锁屏字段；2.极光推送到移动端
     * @param classCardLock
     */
    public ResponseJson batchChangeLockStatusByIds(ClassCardLock classCardLock) {
        ResponseJson responseJson = new ResponseJson();
        dmClassCardFeign.batchChangeLockStatusByIds(classCardLock);
        String[] ids = classCardLock.getIds();
        String lockStatus = classCardLock.getLockStatus();
        //解锁
        if("0".equals(lockStatus)){
            sendUnLockDmScreenMsg(ids);
        }else if("1".equals(lockStatus)){//锁屏
            responseJson = sendLockDmScreenMsg(ids);
        }
        return responseJson;
    }

    /**
     * 屏幕锁屏：1.先更改班牌表中锁屏字段；2.极光推送到移动端
     * @param id
     */
    public ResponseJson lockDmScreen(String id) {
        dmClassCardFeign.lockDmScreen(id);
        return sendLockDmScreenMsg(new String[]{id});
    }

    public void unLockDmScreen(String id) {
        dmClassCardFeign.unLockDmScreen(id);
        sendUnLockDmScreenMsg(new String[]{id});
    }

    //极光推送：锁屏
    private ResponseJson sendLockDmScreenMsg(String[] ids){
        ResponseJson responseJson = new ResponseJson();
        ResponseJson.Result result = new ResponseJson.Result();
        DmLockScreenPhoto dmLockScreenPhoto = getDmLockScreenPhotoByStatus();
        if(dmLockScreenPhoto == null){
            result.setSuccess(false);
            result.setMsg("请设置屏保相片！");
            responseJson.setResult(result);
            return responseJson;
        }
        //推送班牌锁屏
        if (ids.length > 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(ids)
                    .setMessage(Push.Message.newBuilder().setMsgContent(dmLockScreenPhoto.getContent()).setTitle("云班牌锁屏")
                            .setAlert(dmLockScreenPhoto.getContent()).setContentType("111").setExtras(Extras.newBuilder()
                                    .setId("1").setType(Extras.DM_SCREEN_LOCK).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
        return responseJson;
    }
    //极光推送：解锁
    private void sendUnLockDmScreenMsg(String[] ids){
        //推送班牌解锁
        if (ids.length > 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(ids)
                    .setMessage(Push.Message.newBuilder().setMsgContent("UNLOCK").setTitle("云班牌解锁").setContentType("000").setExtras(Extras.newBuilder().setId("0").setType(Extras.DM_SCREEN_UNLOCK).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }


    public List<DmClassCard> getDmClassCardByTeacherId(String id,String lockStatus) {
        return dmClassCardFeign.getDmClassCardByTeacherId(id,lockStatus);
    }

    private DmLockScreenPhoto getDmLockScreenPhotoByStatus(){
        DmLockScreenPhoto dmLockScreenPhoto = new DmLockScreenPhoto();
        dmLockScreenPhoto.setStatus(1);
        DmLockScreenPhoto record = dmLockScreenPhotoFeign.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
        return record;
    }

    /**
     * 通过学生id 查询 班牌
     * @param studentId
     * @return
     */
    public DmClassCard findDmClassCardByStudentId(String studentId) {
        return dmClassCardFeign.findDmClassCardByStudentId(studentId);
    }
}
