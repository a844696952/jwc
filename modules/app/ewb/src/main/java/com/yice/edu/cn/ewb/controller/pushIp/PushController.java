package com.yice.edu.cn.ewb.controller.pushIp;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.ewb.service.classRegister.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pushIp")
@Api(value = "/pushIp",description = "推送Ip")
public class PushController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;  // JwClasses
    @Autowired
    private JwClassesService classesService;


    @PostMapping("/ip")
    @ApiOperation(value = "教师设备Ip：(equipmentIp)，班级id(id)", notes = "true")
    public ResponseJson pushIp( @RequestBody JwClasses jwClasses) {
        //此处需要 激光推送 告知安卓需要开机
        //给某个alias发送通知
/*        if (StringUtils.isNotBlank(ip)) {
            //推送ip 给安卓
            JpushApp app = JpushApp.getValueById(3);
            try {
                Push push;
                push = Push.newBuilder(app)
  *//*                      .setAlias("456")*//*
                        .setTag(classId)
                        .setMessage(Push.Message.newBuilder().setMsgContent(ip).setTitle("ip")
                                .setExtras(
                                        Extras.newBuilder()
                                                .setType(
                                                        Extras.EWB_PUSH_IP)
                                                .setId("1")
                                                .build()
                                )
                                .build()
                        ).build();
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }*/
            if(StringUtils.isBlank(jwClasses.getEquipmentIp())){
                return new ResponseJson(false,"ip为空");
            }
            if(StringUtils.isBlank(jwClasses.getId())){
                return new ResponseJson(false,"班级id为空");
            }
            if(classesService.findJwClassesById(jwClasses.getId())!=null){
         /*       JwClasses jwClasses = new JwClasses();
                jwClasses.setId(classId);
                jwClasses.setEquipmentIp(ip);*/
                classesService.updateJwClasses(jwClasses);
            }else{
                return  new ResponseJson(false,"班级不存在");
            }
            return  new ResponseJson(true,"保存班级设备ip成功");

    }

    @GetMapping("/getIp/{classId}")
    public ResponseJson getIp( @PathVariable String classId) {
        if(StringUtils.isBlank(classId)){
            return new ResponseJson(false,"班级id为空");
        }
        JwClasses  jwClass = classesService.findJwClassesById(classId);
        return  new ResponseJson(true,jwClass==null?"":jwClass.getEquipmentIp());
    }
}
