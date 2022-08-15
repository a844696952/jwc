package com.yice.edu.cn.ecc.controller.sturespMsg;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.parentMsg.ParentMessage;
import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.ecc.service.parent.ParentService;
import com.yice.edu.cn.ecc.service.parentMsg.ParentmsgService;
import com.yice.edu.cn.ecc.service.sturespMsg.SturespmsgService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sturespmsg")
@Api(value = "/sturespmsg",description = "模块")
public class SturespmsgController {
    @Autowired
    private ParentmsgService parentmsgService;

    @Autowired
    private SturespmsgService sturespmsgService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ParentService parentService;


    @PostMapping("/saveSturespmsg")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=Sturespmsg.class)
    public ResponseJson saveSturespmsg(
            @ApiParam(value = "对象", required = true)
            @RequestBody Sturespmsg sturespmsg){
        if(StringUtil.isNullOrEmpty(sturespmsg.getContent())){
            return new ResponseJson(false,"消息内容不能为空");
        }

        if(StringUtil.isNullOrEmpty(sturespmsg.getSchoolId())){
            return new ResponseJson(false,"schoolId不能为空");
        }

        /*if(StringUtil.isNullOrEmpty(sturespmsg.getStuCardNo())){
            return new ResponseJson(false,"学生IC卡号不能为空");
        }*/

        /*查询家长学生绑定表,获取家长id*/

        ParentStudent parentStudent = new ParentStudent();
        parentStudent.setStudentId(sturespmsg.getStudentId());
        List<ParentStudent> parentStudentList = parentService.findParentStudentListByCondition(parentStudent);
        if(parentStudentList == null){
            return new ResponseJson(false,"学生家长未绑定");
        }else if(parentStudentList.size()>1){
            return new ResponseJson(false,"数据异常");
        }
        Sturespmsg s = null;
        try {
            String parentId = parentStudentList.get(0).getParentId();
            sturespmsg.setMType("1");
            sturespmsg.setMStatus(0);
            sturespmsg.setParentCardno(parentId);
            sturespmsg.setParentId(sturespmsg.getStudentId());
            s = sturespmsgService.saveSturespmsg(sturespmsg);
            Push push = null;
            JSONObject json = new JSONObject();
            Extras extras =  Extras.newBuilder()
                    .setId(parentId).setType(Extras.SYSTEM_BROADCAST_PARENTMSG)
                    .setJSON(json.toString())
                    .build();

            push = Push.newBuilder(JpushApp.ECC)
                    .setAlias(parentId)
                    .setMessage(Push.Message.newBuilder()
                            .setMsgContent(sturespmsg.getContent())
                            .setAlert(sturespmsg.getContent())
                            .setTitle("学生回复")
                            .setExtras(extras)
                            .build()
                    )

                    .setOptions(Push.Options.newBuilder().build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }catch(Throwable tw){
            tw.printStackTrace();
        }

        Sturespmsg temp = new Sturespmsg();
        temp.setSchoolId(sturespmsg.getSchoolId());
        temp.setStudentId(sturespmsg.getStudentId());
        temp.setPmsgId(sturespmsg.getPmsgId());
        temp.setContent(sturespmsg.getContent());
        temp.setSendTime(sturespmsg.getSendTime());
        return this.getParentStudentTalks(temp);
    }

    @PostMapping(value = "/getParentStudentTalks")
    @ApiOperation(value = "返回班牌家長學生交流信息", notes = "返回班牌家長學生交流信息")
    public ResponseJson getParentStudentTalks(@ApiParam(value = "班牌學生回復與家長家长", required = true)
                                              @RequestBody Sturespmsg sturespmsg){
        if(StringUtil.isNullOrEmpty(sturespmsg.getSchoolId())){
            return new ResponseJson(false,"学校id不能为空");
        }

        /*if(StringUtil.isNullOrEmpty(sturespmsg.getStuCardNo())){
            return new ResponseJson(false,"学生卡号不能为空");
        }*/

        //
        if(StringUtil.isNullOrEmpty(sturespmsg.getPmsgId())){
            return new ResponseJson(false,"消息id不能为空");
        }

        if(sturespmsg.getMStatus() == null){
            sturespmsg.setMStatus(0);
        }

        Parentmsg p = parentmsgService.findParentmsgById(sturespmsg.getPmsgId());

        List<ParentMessage> msgList = new ArrayList<ParentMessage>();
        if(StringUtil.isNullOrEmpty(sturespmsg.getSendTime())){
            sturespmsg.setSendTime( DateUtil.now());
        }
        msgList.add(new ParentMessage(p.getContent(),p.getSendTime(),p.getMStatus(),"2"));
        msgList.add(new ParentMessage(sturespmsg.getContent(),sturespmsg.getSendTime(),sturespmsg.getMStatus(),"1"));
        return new ResponseJson(msgList);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.date()+"===="+DateUtil.now());
    }
    @PostMapping("/updateSturespmsg")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSturespmsg(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Sturespmsg sturespmsg){
        sturespmsgService.updateSturespmsg(sturespmsg);
        return new ResponseJson();
    }

    @GetMapping("/findSturespmsgById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=Sturespmsg.class)
    public ResponseJson findSturespmsgById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Sturespmsg sturespmsg=sturespmsgService.findSturespmsgById(id);
        return new ResponseJson(sturespmsg);
    }

    @GetMapping("/deleteSturespmsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSturespmsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        sturespmsgService.deleteSturespmsg(id);
        return new ResponseJson();
    }


    @PostMapping("/findSturespmsgListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=Sturespmsg.class)
    public ResponseJson findSturespmsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Sturespmsg sturespmsg){
       //sturespmsg.setSchoolId(mySchoolId());
        List<Sturespmsg> data=sturespmsgService.findSturespmsgListByCondition(sturespmsg);
        return new ResponseJson(data);
    }



}
