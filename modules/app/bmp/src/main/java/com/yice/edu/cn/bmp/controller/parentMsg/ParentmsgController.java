package com.yice.edu.cn.bmp.controller.parentMsg;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.bmp.constants.ParentmsgConstants;
import com.yice.edu.cn.bmp.service.parentMsg.ParentmsgService;
import com.yice.edu.cn.bmp.service.sturespMsg.SturespmsgService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.parentMsg.ParentMessage;
import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parentmsg")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "/parentmsg",description = "班牌家长推送信息表模块")
public class ParentmsgController {
    @Autowired
    private ParentmsgService parentmsgService;

    @Autowired
    private SturespmsgService sturespmsgService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HttpServletRequest request;

    private String getParentId(){
        String token = request.getHeader(Constant.TOKEN);

        try {
            Claims claims = null;
            claims = JwtUtil.parseJWT(token);
            if(claims!=null){
                return claims.getId();
            }
        } catch (Exception e) {
            System.out.println("解析token失败:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ResponseJson failNull(String errorMesssage){
        return new ResponseJson(false,errorMesssage);
    }

    @PostMapping(value = "/saveParentmsg")
    @ApiOperation(value = "保存班牌家长推送信息表对象", notes = "返回保存好的班牌家长推送信息表对象", response=Parentmsg.class)
    public ResponseJson saveParentmsg(
            @ApiParam(value = "班牌家长推送信息表对象", required = true)
            @RequestBody Parentmsg parentmsg){

        /*根据家长获得学生信息*/
        parentmsg.setParentId(this.getParentId());
        /*DmParentRelation relation = dmParentRelationService.findDmParentRelationById(parentmsg.getParentId());
        if(relation == null){
            return new ResponseJson(false,"尚未录入亲子绑定信息");
        }*/
        if(StringUtil.isNullOrEmpty(parentmsg.getStudentId())){
            return this.failNull(ParentmsgConstants.ErrorMessage.STUDENT_ID_NOTNULL);
        }

        Student sd = new Student();
        sd.setId(parentmsg.getStudentId());
        Student st = this.parentmsgService.findStudentById(sd.getId());
        if(st == null){
            return this.failNull(ParentmsgConstants.ErrorMessage.STUDENT_NOT_EXIST);
        }

        /*根据学生id获取班级用户名*/
        parentmsg.setStudentName(st.getName());
        parentmsg.setClassNo(st.getClassesId());
        parentmsg.setStudentId(st.getId());
        parentmsg.setSchoolId(st.getSchoolId());
        parentmsg.setMStatus(0);
        parentmsg.setMType(1);

        DmClassCard dccCond = new DmClassCard();
        dccCond.setClassId(st.getClassesId());
        DmClassCard dcc = parentmsgService.findOneDmClassCardByCondition(dccCond);
        if(dcc == null){
            return this.failNull(ParentmsgConstants.ErrorMessage.STUDENT_CLASS_ECC_INFO_NOTEXIST);
        }

        parentmsg.setCardNo(dcc.getUserName());

        if(StringUtil.isNullOrEmpty(parentmsg.getCardNo())){
            return this.failNull(ParentmsgConstants.ErrorMessage.ECC_USERNAME_NOTNULL);
        }

        if(StringUtil.isNullOrEmpty(parentmsg.getContent())){
            return this.failNull(ParentmsgConstants.ErrorMessage.MESSAGE_CONTENT_NOTNULL);
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
        parentmsgService.updateParentmsgByStuCardNo(parentmsg);

        return new ResponseJson();
    }

    @PostMapping("/findParentAndStuRespMsgListByCondition")
    @ApiOperation(value = "查询家长已发送的消息和学生回复的消息并按时间排序", notes = "返回响应对象")
    public ResponseJson findParentAndStuRespMsgListByCondition(
            @ApiParam(value = "查询家长已发送的消息和学生回复的消息并按时间排序", required = true)
            @RequestBody Parentmsg parentmsg){
        parentmsg.setStudentId(parentmsg.getStudentId());
        //parentmsg.setSendTime(DateUtil.formatDateTime(new Date()));
        List<Parentmsg> data = parentmsgService.findParentmsgListByCondition(parentmsg);

        Sturespmsg sturespmsg = new Sturespmsg();
        sturespmsg.setParentCardno(parentmsg.getParentId());
        //此处设置parentid为学生卡号
        sturespmsg.setParentId(parentmsg.getStudentId());

        //sturespmsg.setSendTime(DateUtil.formatDateTime(new Date()));
        List<Sturespmsg> studata = sturespmsgService.findSturespmsgListByCondition(sturespmsg);
        List<ParentMessage> msgList = new ArrayList<ParentMessage>();
        for(Parentmsg p : data){
            msgList.add(new ParentMessage(p.getContent(),p.getSendTime(),p.getMStatus(),"parent"));
        }
        for(Sturespmsg s : studata){
            msgList.add(new ParentMessage(s.getContent(),s.getSendTime(),s.getMStatus(),"student"));

        }

        msgList.sort(new Comparator<ParentMessage>() {
            @Override
            public int compare(ParentMessage a, ParentMessage b) {
                Date d1 = DateUtil.parse(a.getSendTime());
                Date d2 = DateUtil.parse(b.getSendTime());
                return d1.before(d2) ? -1 :0;
            }
        });
        return new ResponseJson(msgList);
    }
}
