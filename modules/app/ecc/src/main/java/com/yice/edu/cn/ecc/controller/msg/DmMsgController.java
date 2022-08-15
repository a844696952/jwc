package com.yice.edu.cn.ecc.controller.msg;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.MsgType;
import com.yice.edu.cn.common.pojo.dm.msg.Sender;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.oos.AvInfo;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.ecc.service.msg.DmMsgService;
import com.yice.edu.cn.ecc.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmMsg")
@Api(value = "/dmMsg",description = "云班牌-消息表模块")
public class DmMsgController {
    @Autowired
    private DmMsgService dmMsgService;

    @Autowired
    private StudentService studentService;

    @Autowired
    RestTemplate restTemplate;


//    @PostMapping("/saveDmMsg")
//    @ApiOperation(value = "保存云班牌-消息表对象", notes = "返回保存好的云班牌-消息表对象", response= DmMsg.class)
//    public ResponseJson saveDmMsg(
//            @ApiParam(value = "云班牌-消息表对象", required = true)
//            @RequestBody DmMsg dmMsg){
//        DmMsg s=dmMsgService.saveDmMsg(dmMsg);
//        return new ResponseJson(s);
//    }
//
//    @GetMapping("/update/findDmMsgById/{id}")
//    @ApiOperation(value = "去更新页面,通过id查找云班牌-消息表", notes = "返回响应对象", response=DmMsg.class)
//    public ResponseJson findDmMsgById(
//            @ApiParam(value = "去更新页面,需要用到的id", required = true)
//            @PathVariable String id){
//        DmMsg dmMsg=dmMsgService.findDmMsgById(id);
//        return new ResponseJson(dmMsg);
//    }
//
//    @PostMapping("/update/updateDmMsg")
//    @ApiOperation(value = "修改云班牌-消息表对象", notes = "返回响应对象")
//    public ResponseJson updateDmMsg(
//            @ApiParam(value = "被修改的云班牌-消息表对象,对象属性不为空则修改", required = true)
//            @RequestBody DmMsg dmMsg){
//        dmMsgService.updateDmMsg(dmMsg);
//        return new ResponseJson();
//    }

    @GetMapping("/look/lookDmMsgById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云班牌-消息表", notes = "返回响应对象", response=DmMsg.class)
    public ResponseJson lookDmMsgById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmMsg dmMsg=dmMsgService.findDmMsgById(id);
        return new ResponseJson(dmMsg);
    }

    @PostMapping("/findDmMsgsByCondition")
    @ApiOperation(value = "根据条件查找云班牌-消息表", notes = "返回响应对象", response=DmMsg.class)
    public ResponseJson findDmMsgsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMsg dmMsg){
        dmMsg.setSchoolId(mySchoolId());
        List<DmMsg> data=dmMsgService.findDmMsgListByCondition(dmMsg);
        long count=dmMsgService.findDmMsgCountByCondition(dmMsg);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmMsgByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌-消息表,结果必须为单条数据", notes = "没有时返回空", response=DmMsg.class)
    public ResponseJson findOneDmMsgByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmMsg dmMsg){
        dmMsg.setSchoolId(mySchoolId());
        DmMsg one=dmMsgService.findOneDmMsgByCondition(dmMsg);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmMsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmMsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmMsgService.deleteDmMsg(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmMsgListByCondition")
    @ApiOperation(value = "根据条件查找云班牌-消息表列表", notes = "返回响应对象,不包含总条数", response=DmMsg.class)
    public ResponseJson findDmMsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMsg dmMsg){
        dmMsg.setSchoolId(mySchoolId());
        List<DmMsg> data=dmMsgService.findDmMsgListByCondition(dmMsg);
        return new ResponseJson(data);
    }

    @PostMapping("/sendAudio")
    @ApiOperation(value = "发送音频")
    public ResponseJson sendAudio(
        MultipartFile file,
        @Validated(GroupOne.class)
        DmMsg dmMsg
    ){

        dmMsg.setSchoolId(mySchoolId());
        dmMsg.setMsgType(MsgType.AUDIO);
        final Student student = getStudentById(dmMsg.getStudentId());
        //TODO  可以增加切面或者拦截器来判断学生id，和teacherId是否存在
        if(student==null){
            return new ResponseJson(false,"发送失败,学生不存在");
        }
        dmMsg.setSenderId(student.getId());
        dmMsg.setSender(new Sender(student));

        final String url = QiniuUtil.commonUploadFile(file, "msg");
        dmMsg.setAudioUrl(url);
        final String avinfoUrl = Constant.RES_PRE + url+"?avinfo";

        try {
            final AvInfo avInfo = restTemplate.getForObject(avinfoUrl, AvInfo.class);
            dmMsg.setAudioDuration(Double.valueOf(avInfo.getFormat().getDuration()).intValue());
        } catch (RestClientException | NumberFormatException ignored) {

        }
        dmMsgService.sendMsg(dmMsg,student);
        return new ResponseJson("发送成功");
    }

    @PostMapping("/sendText")
    @ApiOperation(value = "发送文本")
    public ResponseJson sendText(
            @Validated(GroupTwo.class)
            @RequestBody DmMsg dmMsg
    ){
        dmMsg.setSchoolId(mySchoolId());
        dmMsg.setMsgType(MsgType.TEXT);
        final Student student = getStudentById(dmMsg.getStudentId());
        //TODO  可以增加切面或者拦截器来判断学生id，和teacherId是否存在
        if(student==null){
            return new ResponseJson(false,"发送失败,学生不存在");
        }
        dmMsg.setSenderId(student.getId());
        dmMsg.setSender(new Sender(student));
        dmMsgService.sendMsg(dmMsg, student);
        return new ResponseJson("发送成功");
    }

    /**
     * 分页查找消息
     * @param dmMsg
     * @return
     */
    @PostMapping("/getMsgPage")
    @ApiOperation(value = "分页查询消息")
    public ResponseJson getMsgPage(
            @Validated(GroupThree.class)
            @RequestBody DmMsg dmMsg
    ){
        dmMsg.setSchoolId(mySchoolId());
        List<DmMsg> list = dmMsgService.findDmMsgListByCondition(dmMsg);
        long count = dmMsgService.findDmMsgCountByCondition(dmMsg);
        return new ResponseJson(list,count);
    }

    private Student getStudentById(String id){
        return studentService.findStudentById(id);
    }

}
