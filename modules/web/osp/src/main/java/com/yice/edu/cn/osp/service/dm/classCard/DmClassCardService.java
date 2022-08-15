package com.yice.edu.cn.osp.service.dm.classCard;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.feignClient.dm.classCard.DmClassCardFeign;
import com.yice.edu.cn.osp.feignClient.dm.dmLockScreenPhoto.DmLockScreenPhotoFeign;
import com.yice.edu.cn.osp.feignClient.dm.dmStudentAttendant.DmStudentAttendantFeign;
import com.yice.edu.cn.osp.feignClient.dm.honourRoll.DmHonourRollFeign;
import com.yice.edu.cn.osp.feignClient.dm.honourRoll.DmHonourRollStudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private DmHonourRollFeign dmHonourRollFeign;
    @Autowired
    private DmHonourRollStudentFeign dmHonourRollStudentFeign;
    @Autowired
    private DmStudentAttendantFeign dmStudentAttendantFeign;

    public DmClassCard findDmClassCardById(String id) {
        return dmClassCardFeign.findDmClassCardById(id);
    }

    public DmClassCard saveDmClassCard(DmClassCard dmClassCard) {
        return dmClassCardFeign.saveDmClassCard(dmClassCard);
    }

    public List<DmClassCard> findDmClassCardListByCondition(DmClassCard dmClassCard) {
        return dmClassCardFeign.findDmClassCardListByCondition(dmClassCard);
    }

    public DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard) {
        return dmClassCardFeign.findOneDmClassCardByCondition(dmClassCard);
    }

    public long findDmClassCardCountByCondition(DmClassCard dmClassCard) {
        return dmClassCardFeign.findDmClassCardCountByCondition(dmClassCard);
    }

    public void updateDmClassCard(DmClassCard dmClassCard) {
        dmClassCardFeign.updateDmClassCard(dmClassCard);
    }

    public void deleteDmClassCard(String id) {
        dmClassCardFeign.deleteDmClassCard(id);
    }



    public void deleteDmClassCardByCondition(DmClassCard dmClassCard) {
        dmClassCardFeign.deleteDmClassCardByCondition(dmClassCard);
    }

    public List<DmClassCard> findDmClassCardListByclassId(DmClassCard dmClassCard) {
        return dmClassCardFeign.findDmClassCardListByclassId(dmClassCard);
    }



    public Workbook exportDmClassCard(DmClassCard dmClassCard) {
        List<DmClassCard> dmClassCardList = dmClassCardFeign.findDmClassCardToXls(dmClassCard);
        return ExcelExportUtil.exportExcel(new ExportParams("设备列表", "设备列表信息"),
                DmClassCard.class, dmClassCardList);
    }

    public List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard) {
        return dmClassCardFeign.findDmClassCardUser(dmClassCard);

    }

    public Boolean startboot(DmClassCard dmClassCard) {
        return dmClassCardFeign.startboot(dmClassCard);
    }

    public Boolean shutdown(DmClassCard dmClassCard) {
        return dmClassCardFeign.shutdown(dmClassCard);
    }

    public Boolean reboot(DmClassCard dmClassCard) {
        return dmClassCardFeign.reboot(dmClassCard);
    }

    public void updateEquipmentName(DmClassCard dmClassCard) {
        dmClassCardFeign.updateEquipmentName(dmClassCard);
    }

    public void dmClassCardStatus(DmClassCard dmClassCard) {
        dmClassCardFeign.dmClassCardStatus(dmClassCard);
    }


    /**
     * 批量屏幕锁屏：1.先更改班牌表中锁屏字段；2.极光推送到移动端
     *
     * @param classCardLock
     */
    public ResponseJson batchChangeLockStatusByIds(ClassCardLock classCardLock) {
        ResponseJson responseJson = new ResponseJson();
        dmClassCardFeign.batchChangeLockStatusByIds(classCardLock);
        String[] ids = classCardLock.getIds();
        String lockStatus = classCardLock.getLockStatus();
        //解锁
        if ("0".equals(lockStatus)) {
            sendUnLockDmScreenMsg(ids);
        } else if ("1".equals(lockStatus)) {//锁屏
            responseJson = sendLockDmScreenMsg(ids);
        }
        return responseJson;
    }


    /**
     * 屏幕锁屏：1.先更改班牌表中锁屏字段；2.极光推送到移动端
     *
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
    public ResponseJson sendLockDmScreenMsg(String[] ids) {
        ResponseJson responseJson = new ResponseJson();
        ResponseJson.Result result = new ResponseJson.Result();
        DmLockScreenPhoto dmLockScreenPhoto = getDmLockScreenPhotoByStatus();
        if (dmLockScreenPhoto == null) {
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
    public void sendUnLockDmScreenMsg(String[] ids) {
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

    public DmLockScreenPhoto getDmLockScreenPhotoByStatus() {
        DmLockScreenPhoto dmLockScreenPhoto = new DmLockScreenPhoto();
        dmLockScreenPhoto.setStatus(1);
        DmLockScreenPhoto record = dmLockScreenPhotoFeign.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
        return record;
    }

    public List<DmClassCard> getDmClassCardByTeacherId(String id, String lockStatus) {
        return dmClassCardFeign.getDmClassCardByTeacherId(id, lockStatus);
    }


    public void updateDmClassManage(DmClassCard dmClassCard) {
        dmClassCardFeign.updateDmClassManage(dmClassCard);
    }

    public List<DmClassCard> getDmClassCardTree(DmClassCard dmClassCard) {
        List<DmClassCard> data =dmClassCardFeign.getDmClassCardTree(dmClassCard);
        data = ObjectKit.buildTree(data,"-1").stream().
                filter((DmClassCard d) -> Objects.nonNull(d.getChildren()))
                .sorted(Comparator.comparing(DmClassCard::getId)).peek(x->{
                    x.setChildren(x.getChildren().stream().map(d->{
                        d.setName(d.getName()+"班");
                        return d;
                    }).collect(Collectors.toList()));;
                }).collect(Collectors.toList());
        return data;
    }

    public Object[] findDmClassCardIdByTeacherId(DmClassCard dmClassCard) {
        return  dmClassCardFeign.findDmClassCardIdByTeacherId(dmClassCard).toArray();
    }
    public void cleraDmClassManage(DmClassCard dmClassCard) {
        dmClassCardFeign.cleraDmClassManage(dmClassCard);
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
