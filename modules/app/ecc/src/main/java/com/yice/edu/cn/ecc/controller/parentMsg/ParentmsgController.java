package com.yice.edu.cn.ecc.controller.parentMsg;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.parentMsg.ParentMessage;
import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.ecc.service.parentMsg.ParentmsgService;
import com.yice.edu.cn.ecc.service.sturespMsg.SturespmsgService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parentmsg")
@Api(value = "/parentmsg",description = "班牌家长推送信息表模块")
public class ParentmsgController {
    @Autowired
    private ParentmsgService parentmsgService;

    @Autowired
    private SturespmsgService sturespmsgService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping(value = "/saveParentmsg")
    @ApiOperation(value = "保存班牌家长推送信息表对象", notes = "返回保存好的班牌家长推送信息表对象", response=Parentmsg.class)
    public ResponseJson saveParentmsg(
            @ApiParam(value = "班牌家长推送信息表对象", required = true)
            @RequestBody Parentmsg parentmsg){

        if(StringUtil.isNullOrEmpty(parentmsg.getCardNo())){
            return new ResponseJson(false,"班牌用户名不能为空");
        }

        if(StringUtil.isNullOrEmpty(parentmsg.getContent())){
            return new ResponseJson(false,"消息内容不能为空");
        }


        Parentmsg s = parentmsgService.saveParentmsg(parentmsg);
        try{
            Push push = null;
            JSONObject json = new JSONObject();
            json.put("studentname",parentmsg.getStudentName());
            Extras extras =  Extras.newBuilder()
                    .setId(parentmsg.getId()).setType(Extras.SYSTEM_BROADCAST_PARENTMSG)
                    .setJSON(json.toString())
                    .build();

            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(parentmsg.getCardNo())
                    .setMessage(Push.Message.newBuilder()
                            .setMsgContent(parentmsg.getContent())
                            .setAlert(parentmsg.getContent())
                            .setTitle(parentmsg.getStudentName())
                            .setExtras(extras)
                            .build()
                    )

                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }catch(Throwable tw){
            tw.printStackTrace();
        }


        return new ResponseJson(s);
    }



    @PostMapping("/updateParentmsg")
    @ApiOperation(value = "修改班牌家长推送信息表对象", notes = "返回响应对象")
    public ResponseJson updateParentmsg(
            @ApiParam(value = "被修改的班牌家长推送信息表对象,对象属性不为空则修改", required = true)
            @RequestBody Parentmsg parentmsg){
        parentmsgService.updateParentmsg(parentmsg);

        return new ResponseJson();
    }

    @GetMapping("/findParentmsgById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班牌家长推送信息表", notes = "返回响应对象", response=Parentmsg.class)
    public ResponseJson findParentmsgById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Parentmsg parentmsg=parentmsgService.findParentmsgById(id);
        return new ResponseJson(parentmsg);
    }

    @GetMapping("/deleteParentmsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteParentmsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        parentmsgService.deleteParentmsg(id);
        return new ResponseJson();
    }


    @PostMapping("/findParentmsgListByCondition")
    @ApiOperation(value = "根据条件查找班牌家长推送信息表列表", notes = "返回响应对象,不包含总条数", response=Parentmsg.class)
    public ResponseJson findParentmsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Parentmsg parentmsg){
       //parentmsg.setSchoolId(mySchoolId());
        parentmsg.setMStatus(parentmsg.getStatus());
        List<Parentmsg> data=parentmsgService.findParentmsgListByCondition(parentmsg);
        return new ResponseJson(data);
    }

    @PostMapping("/deleteParentmsgByCondition")
    @ApiOperation(value = "根据家长id删除班牌家长推送信息表")
    public ResponseJson deleteParentmsgByCondition(
            @ApiParam(value = "班牌家长推送信息表对象")
            @RequestBody Parentmsg parentmsg){
        parentmsgService.deleteParentmsgByCondition(parentmsg);
        return new ResponseJson();
    }

    @PostMapping("/updateParentmsgByStuCardNo")
    @ApiOperation(value = "修改班牌家长推送信息表对象", notes = "返回响应对象")
    public ResponseJson updateParentmsgByStuCardNo(
            @ApiParam(value = "被修改的班牌家长推送信息表对象,对象属性不为空则修改", required = true)
            @RequestBody Parentmsg parentmsg){
        if(StringUtil.isNullOrEmpty(parentmsg.getId())){
            return new ResponseJson(false,"消息id不能为空");
        }
        parentmsgService.updateParentmsgByStuCardNo(parentmsg);

        return new ResponseJson();
    }


}
