package com.yice.edu.cn.dm.controller.classCard;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.jw.classes.rise.ElectronicClazzCardBatchAddVo;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.dm.service.classCard.DmClassCardService;
import com.yice.edu.cn.dm.service.kq.EccStudentFaceService;
import com.yice.edu.cn.dm.service.parentMsg.ParentmsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassCard")
@Api(value = "/dmClassCard",description = "云班牌设备绑定模块")
public class DmClassCardController {
    @Autowired
    private DmClassCardService dmClassCardService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EccStudentFaceService eccStudentFaceService;

    @Autowired
    private ParentmsgService parentmsgService;

    @GetMapping("/findDmClassCardById/{id}")
    @ApiOperation(value = "通过id查找班牌", notes = "返回对象")
    public DmClassCard findDmClassCardById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return dmClassCardService.findDmClassCardById(id);
    }

    @PostMapping("/saveDmClassCard")
    @ApiOperation(value = "保存一条班牌绑定信息", notes = "返回对象")
    public DmClassCard saveDmClassCard(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.saveDmClassCard(dmClassCard);
        return dmClassCard;
    }

    @PostMapping("/findDmClassCardListByCondition")
    @ApiOperation(value = "根据条件查找班牌列表", notes = "返回列表")
    public List<DmClassCard> findDmClassCardListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findDmClassCardListByCondition(dmClassCard);
    }

    @PostMapping("/findDmClassCardCountByCondition")
    @ApiOperation(value = "根据条件查找班牌列表个数", notes = "返回总个数")
    public long findDmClassCardCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findDmClassCardCountByCondition(dmClassCard);
    }

    @PostMapping("/updateDmClassCard")
    @ApiOperation(value = "修改一条班牌绑定信息", notes = "对象必传")
    public void updateDmClassCard(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.updateDmClassCard(dmClassCard);
    }

    @GetMapping("/deleteDmClassCard/{id}")
    @ApiOperation(value = "通过id删除班牌信息")
    public void deleteDmClassCard(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        dmClassCardService.deleteDmClassCard(id);
    }

    @GetMapping("/relieveDmClassCard/{id}")
    @ApiOperation(value = "通过id解除班牌的绑定")
    public void relieveDmClassCard(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setId(id);
        dmClassCard.setGradeId("");
        dmClassCard.setClassId("");
        //dmClassCard.setRelationStatus(1);
        dmClassCardService.updateDmClassCard(dmClassCard);
        //dmClassCardService.deleteDmClassCard(id);
    }

    @PostMapping("/deleteDmClassCardByCondition")
    @ApiOperation(value = "根据条件删除班牌信息")
    public void deleteDmClassCardByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.deleteDmClassCardByCondition(dmClassCard);
    }

    @PostMapping("/findOneDmClassCardByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmClassCard findOneDmClassCardByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findOneDmClassCardByCondition(dmClassCard);
    }

    @PostMapping("/findDmClassCardListByclassId")
    @ApiOperation(value = "根据条件查查询已绑定的班级", notes = "返回列表")
    public List<DmClassCard> findDmClassCardListByclassId(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findDmClassCardListByclassId(dmClassCard);
    }

    @PostMapping("/relieveDmClassCardAll")
    @ApiOperation(value = "通过id批量解绑班牌")
    public void relieveDmClassCardAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        //批量解绑
        dmClassCardService.relieveDmClassCardAll(dmClassCard.getRowData());
    }

    @PostMapping("/findDmClassCardToXls")
    @ApiOperation(value = "根据条件查找班牌列表进行导出xls", notes = "页面返回xls文件")
    public List<DmClassCard> findDmClassCardToXls(
            @ApiParam(value = "对象")
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findDmClassCardToXls(dmClassCard);
    }

    @PostMapping("/findDmClassCardUser")
    @ApiOperation(value = "查询用户账号密码", notes = "返回对象")
    public List<DmClassCard> findDmClassCardUser(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        List<DmClassCard> cardList = dmClassCardService.findDmClassCardUser(dmClassCard);
        return cardList;
    }

    @PostMapping("/dmClassCardStatus")
    @ApiOperation(value = "通过用户名修改设备状态和版本号", notes = "返回对象")
    public void dmClassCardStatus(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.dmClassCardStatus(dmClassCard);
    }

    @PostMapping("/updateEquipmentName")
    @ApiOperation(value = "修改设备状态", notes = "返回对象")
    public void updateEquipmentName(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.updateEquipmentName(dmClassCard);
    }

    @PostMapping("/startboot")
    @ApiOperation(value = "开机", notes = "返回对象")
    public Boolean startboot(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        //此处需要 激光推送 告知安卓需要开机
        //给某个alias发送通知
        String[] str = dmClassCard.getRowData();
        if (str.length != 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(str)
                    .setMessage(Push.Message.newBuilder().setMsgContent("1111").setTitle("开机").setAlert("开机").setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_EQUIPMENT_SHUTDOWN).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/shutdown")
    @ApiOperation(value = "关机", notes = "返回对象")
    public Boolean shutdown(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        //此处需要 激光推送 告知安卓需要关机
        //给某个alias发送通知
        String[] str = dmClassCard.getRowData();
        if (str.length != 0) {
            Push push = null;
            //给某个alias发送通知
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(str)
                    .setMessage(Push.Message.newBuilder().setMsgContent("1111").setTitle("关机").setAlert("关机").setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_EQUIPMENT_SHUTDOWN).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/reboot")
    @ApiOperation(value = "重启", notes = "返回对象")
    public Boolean reboot(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        //给某个alias发送通知
        String[] str = dmClassCard.getRowData();
        if (str.length != 0) {
            Push push = null;
            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(str)
                    .setMessage(Push.Message.newBuilder().setMsgContent("1111").setTitle("重启").setAlert("重启").setContentType("1111").setExtras(Extras.newBuilder().setId("1").setType(Extras.SYSTEM_EQUIPMENT_REBOOT).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/batchChangeLockStatusByIds")
    @ApiOperation(value = "修改设备状态", notes = "返回对象")
    public void batchChangeLockStatusByIds(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassCardLock classCardLock) {
        dmClassCardService.batchChangeLockStatusByIds(classCardLock);
    }

    @GetMapping("/lockDmScreen/{id}")
    @ApiOperation(value = "通过id锁屏")
    public void lockDmScreen(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        dmClassCardService.lockDmScreen(id);
    }

    @GetMapping("/unLockDmScreen/{id}")
    @ApiOperation(value = "通过id解锁屏")
    public void unLockDmScreen(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        dmClassCardService.unLockDmScreen(id);
    }

    @GetMapping("/getDmClassCardByTeacherId/{id}/{lockStatus}")
    @ApiOperation(value = "通过讲师id与lockStatus获取所管理的班牌")
    public List<DmClassCard> getDmClassCardByTeacherId(
            @ApiParam(value = "对象", required = true)
            @PathVariable(value = "id") String id,@PathVariable(value = "lockStatus") String lockStatus) {
        return dmClassCardService.getDmClassCardByTeacherId(id,lockStatus);
    }

    @PostMapping("/updateDmClassManage")
    @ApiOperation(value = "批量配置班牌权限", notes = "返回对象")
    public void updateDmClassManage(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.updateDmClassManage(dmClassCard);
    }

    @PostMapping("/getDmClassCardTree")
    @ApiOperation(value = "班牌树形结构班牌", notes = "返回对象")
    public List<DmClassCard> getDmClassCardTree(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.getDmClassCardTree(dmClassCard);
    }

    @PostMapping("/findDmClassCardIdByTeacherId")
    @ApiOperation(value = "查询该教师已经拥有权限的班牌", notes = "返回对象")
    public  List<DmClassCard> findDmClassCardIdByTeacherId(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        return dmClassCardService.findDmClassCardIdByTeacherId(dmClassCard);
    }
    @PostMapping("/cleraDmClassManage")
    @ApiOperation(value = "批量清除配置班牌权限", notes = "返回对象")
    public void cleraDmClassManage(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCardService.cleraDmClassManage(dmClassCard);
    }
    @GetMapping("/findDmClassCardByStudentId/{studentId}")
    @ApiOperation(value = "通过studentId查找班牌", notes = "返回对象")
    public DmClassCard findDmClassCardByStudentId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String studentId) {
        return dmClassCardService.findDmClassCardByStudentId(studentId);
    }

    @GetMapping("/findSchoolByUserName/{userName}")
    @ApiOperation(value = "通过studentId查找班牌", notes = "返回对象")
    public DmClassCard findSchoolByUserName(@PathVariable(value = "userName")String userName){
        return dmClassCardService.selectSchoolByUserName(userName);
    }


    @GetMapping("/findDmClassMsgCardById/{id}")
    @ApiOperation(value = "通过id查找班牌", notes = "返回对象")
    public DmClassCard findDmClassMsgCardById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return dmClassCardService.findDmClassMsgCardById(id);
    }

    @PostMapping("/deleteDmData")
    @ApiOperation(value = "删升班所要删除的数据", notes = "")
    public void deleteDmData(
    		@RequestBody List<String> clazzIdList) {
    	DmDeleteData deleteList = new DmDeleteData();
    	deleteList.setClassIdList(clazzIdList);
        dmClassCardService.deleteDmData(deleteList);
    }


    @DeleteMapping("/batchEccStudentFace/{schoolId}")
    @ApiOperation(value = "删除人脸数据和人脸特征值")
    public void batchEccStudentFace(@PathVariable("schoolId") String schoolId){
        eccStudentFaceService.batchDeleteEccStudentFace(schoolId);
    }

    @DeleteMapping("/cacheInvalidateSchoolCourse/{schoolId}")
    @ApiOperation(value = "删除缓存课表信息")
    public void cacheInvalidateSchoolCourse(@PathVariable("schoolId") String schoolId){
        eccStudentFaceService.cacheInvalidateSchoolCourse(schoolId);
}


    @DeleteMapping("/deleteParentMsgBySchoolId/{schoolId}")
    @ApiOperation(value = "删除该学校的家校互动消息", notes = "删除两天前的家长消息")
    public void deleteParentMsgBySchoolId(@PathVariable("schoolId")String schoolId){
        parentmsgService.deleteParentMsgBySchoolId(schoolId);
    }


    @DeleteMapping("/batchDeleteEccKqRecord/{schoolId}")
    @ApiOperation(value = "删除云班牌考勤记录", notes = "删除云班牌考勤记录")
    public void batchDeleteEccKqRecord(@PathVariable("schoolId") String schoolId){
        eccStudentFaceService.batchDeleteEccKqRecord(schoolId);
    }

    @DeleteMapping("/deleteDmKqData/{schoolId}")
    @ApiOperation(value = "删除电子班牌考勤相关数据", notes = "删除电子班牌考勤相关数据")
    public void deleteDmKqData(@PathVariable("schoolId") String schoolId){
        eccStudentFaceService.deleteDmKqData(schoolId);
    }


}
