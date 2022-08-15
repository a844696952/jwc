package com.yice.edu.cn.ecc.controller.classCard;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.annotation.SysLog;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCardReq;
import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.ecc.service.classCard.ClassCardService;
import com.yice.edu.cn.ecc.service.classCard.DmLogService;
import com.yice.edu.cn.ecc.service.classCard.TimedTaskService;
import com.yice.edu.cn.ecc.service.classes.JwClassesService;
import com.yice.edu.cn.ecc.service.school.SchoolService;
import com.yice.edu.cn.ecc.service.school.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/dmClassCard")
@Api(value = "/dmClassCard",description = "云班牌设备管理模块")
public class ClassCardController {

    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private DmLogService dmLogService;
    @Autowired
    private ClassCardService classCardService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private TimedTaskService timedTaskService;
    @CreateCache(name = Constant.Redis.ECC_LOGIN)
    private Cache<String, DmClassCard> dmClassCardCache;

    @Autowired
    private SchoolYearService schoolYearService;

    @PostMapping("/dmClassCardLog")
    @SysLog("云班牌登录")
    @ApiOperation(value = "云班牌登录", notes = "返回响应对象")
    public ResponseJson findDmClassCardsByCondition(
            @Validated
            @RequestBody DmClassCardReq dmClassCard) {

        if (StringUtils.isBlank(dmClassCard.getUserName())) {
            return new ResponseJson(false, "用户名为空");
        }
        if (StringUtils.isBlank(dmClassCard.getPassWord())) {
            return new ResponseJson(false, "密码为空");
        }

        //验证用户名是否存在
        DmClassCard dm = new DmClassCard();
        dm.setUserName(dmClassCard.getUserName());
        dm.setPassword(dmClassCard.getPassWord());
        List<DmClassCard> data2 = classCardService.findDmClassCardUser(dm);
        if (data2.size() == 0) {
            return new ResponseJson(false, "用户名或密码错误");
        }
        if (StringUtils.isBlank(dmClassCard.getVersion())) {
            return new ResponseJson(false, "班牌版本号为空");
        }
        if (StringUtils.isBlank(dmClassCard.getEquipmentName())) {
            return new ResponseJson(false, "设备唯一标识为空");
        }
        if(CollUtil.isNotEmpty(data2) && StringUtils.isEmpty(data2.get(0).getClassId())){
            return new ResponseJson(false, "该账号未绑定班级,请联系管理员");
        }
        dm.setVersion(dmClassCard.getVersion());
        dm.setNewVersion(dmClassCard.getVersion());
        dm.setEquipmentName(dmClassCard.getEquipmentName());
        //判断当前学校是否升
        ResponseJson responseJson = schoolService.findSchoolExpireOrSchoolYear(data2.get(0).getSchoolId());
        if(!responseJson.getResult().isSuccess()){
            return responseJson;
        }
        try {
            //插入登陆后返回的数据
            classCardService.dmClassCardStatus(dm);
            if (data2.size() != 0) {
                if(StringUtils.isNotBlank(data2.get(0).getSchoolId())){
                    CurSchoolYear curSchoolYear = schoolYearService.findCurSchoolYear(data2.get(0).getSchoolId());
                    if(Objects.nonNull(curSchoolYear) && StringUtils.isNotBlank(curSchoolYear.getSchoolYearId())){
                        data2.get(0).setSchoolYearId(curSchoolYear.getSchoolYearId());
                    }
                }
                String token = JwtUtil.createJWT(dm.getUserName(), JSONObject.toJSONString(data2.get(0)), null, -1);
                dm.setPassword(null);
                data2.get(0).setPassword(null);
                data2.get(0).setTeacherId(null);
                return new ResponseJson(data2.get(0), token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseJson(data2.get(0));
    }

    @PostMapping("/dmClassCardStatus")
    @ApiOperation(value = "退出登陆", notes = "返回响应对象")
    public ResponseJson dmClassCardStatus(
            @Validated
            @RequestBody DmClassCardReq dmClassCard) {
        if (dmClassCard.getUserName() == null) {
            return new ResponseJson(false, "用户名为空");
        }
        DmClassCard req = new DmClassCard();
        req.setUserName(dmClassCard.getUserName());
        classCardService.dmClassCardStatus(req);
        return new ResponseJson();
    }

    @PostMapping("/dmClassCardTimedTask")
    @ApiOperation(value = "定时任务拉取时间数据、是否有最新版本", notes = "返回响应对象")
    public ResponseJson dmClassCardTimedTask(
            @RequestBody DmClassCardReq dmClassCard) {
        if (dmClassCard.getUserName() == null) {
            return new ResponseJson(false, "用户名为空");
        }
        DmTimedTask task = new DmTimedTask();
        task.setEquipmentId(dmClassCard.getUserName());
        List<DmTimedTask> timedTask = timedTaskService.findDmTimedTaskListByCondition(task);
        if (timedTask.size() > 0) {
            return new ResponseJson(timedTask.get(0));
        }
        return new ResponseJson(false, "暂无设置定时任务");
    }

    @PostMapping("/dmClassCardVersion")
    @ApiOperation(value = "查询是否有最新版本", notes = "返回响应对象")
    public ResponseJson dmClassCardVersion(
            @RequestBody DmClassCardReq dmClassCard) {
        if (dmClassCard.getUserName() == null) {
            return new ResponseJson(false, "用户名为空");
        }
        DmClassCard dm = new DmClassCard();
        dm.setUserName(dmClassCard.getUserName());
        List<DmClassCard> data2 = classCardService.findDmClassCardUser(dm);
        if (data2.size() > 0) {
            if (ObjectUtil.equal(data2.get(0).getVersion(), data2.get(0).getNewVersion())) {
                return new ResponseJson("当前是最新版本");
            } else {
                return new ResponseJson(data2.get(0).getNewVersion(), data2.get(0).getApkUrl());
            }
        }
        return new ResponseJson(false, "用户不存在");
    }

    @GetMapping("/getPassword/{userName}/{pwd}")
    @ApiOperation(value = "验证班牌密码是否正确", notes = "返回响应对象")
    public ResponseJson getPassword(@PathVariable("userName") String userName, @PathVariable("pwd") String pwd) {
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setUserName(userName);
        dmClassCard.setPassword(pwd);
        dmClassCard.setSchoolId(mySchoolId());
        DmClassCard cnt = classCardService.findOneDmClassCardByCondition(dmClassCard);
        if (cnt == null) {
            return new ResponseJson(false, "密码不正确");
        } else {
            classCardService.cacheInvalidateDmClassCard(cnt.getUserName());
            return new ResponseJson();
        }
    }

    @GetMapping("/getUpdateCardMsg/{id}")
    @ApiOperation(value = "查询班牌更新信息", notes = "返回响应对象")
    public ResponseJson getUpdateMsg(@PathVariable("id") String id) {
        DmClassCard dm = classCardService.findDmClassMsgCardById(id);
        return new ResponseJson(dm);

    }

    @DeleteMapping("/batchEccStudentFace/{schoolId}")
    @ApiOperation(value = "批量删除学校人脸数据", notes = "返回响应对象")
    public ResponseJson batchEccStudentFace(@PathVariable("schoolId")String schoolId,@RequestParam("token")String token){
        classCardService.batchEccStudentFace(schoolId);
        return new ResponseJson(true);
    }


    @DeleteMapping("/deleteParentMsgBySchoolId/{schoolId}")
    @ApiOperation(value = "删除该学校的家校互动消息", notes = "删除两天前的家长消息")
    public void deleteParentMsgBySchoolId(@PathVariable("schoolId")String schoolId){
        classCardService.deleteParentMsgBySchoolId(schoolId);
    }

    @DeleteMapping("/batchDeleteEccKqRecord/{schoolId}")
    @ApiOperation(value = "删除该学校考勤记录", notes = "删除该学校考勤记录")
    public void batchDeleteEccKqRecord(@PathVariable("schoolId")String schoolId){
        classCardService.batchDeleteEccKqRecord(schoolId);
    }

    @GetMapping("/checkToken/{token}")
    @ApiOperation(value ="检测是否需要升班", notes = "返回响应对象")
    public ResponseJson checkToken(@PathVariable("token")String token){
        return   classCardService.checkToken(token);
    }


    @PostMapping("/setDmClassCardUpdateStatus")
    @ApiOperation(value = "安卓通知班牌更新阶段状态", notes = "返回响应对象")
    public ResponseJson setDmClassCardUpdateStatus( @RequestBody DmClassCard dmClassCard) {
        if(dmClassCard.getDownStatus()!=null){
            dmClassCard.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(ObjectUtil.equal(dmClassCard.getInstallStatus(),"0")){
            dmClassCard.setUpdateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }

        classCardService.updateDmClassCard(dmClassCard);
        return new ResponseJson();
    }

    @GetMapping("/getOperationCardMsg/{id}")
    @ApiOperation(value = "查询操作日志信息", notes = "返回响应对象")
    public ResponseJson getOperationCardMsg(@PathVariable("id") String id) {
        DmLog dm = new DmLog();
        dm.setCardId(id);
        List<DmLog> li = dmLogService.findDmLogListByCondition(dm);
        return new ResponseJson(li);
    }



}



