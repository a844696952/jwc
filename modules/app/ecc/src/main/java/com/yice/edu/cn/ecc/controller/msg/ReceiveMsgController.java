package com.yice.edu.cn.ecc.controller.msg;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.msg.StudentMsg;
import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.ecc.service.msg.ReceiveMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.currentDmClassCard;
import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/receiveMsg")
@Api(value = "/receiveMsg",description = "消息接收表模块")
public class ReceiveMsgController {
    @Autowired
    private ReceiveMsgService receiveMsgService;

    @PostMapping("/saveReceiveMsg")
    @ApiOperation(value = "保存消息接收表对象", notes = "返回保存好的消息接收表对象", response= StudentReceiveMsg.class)
    public ResponseJson saveReceiveMsg(
            @ApiParam(value = "消息接收表对象", required = true)
            @RequestBody StudentReceiveMsg receiveMsg){
       receiveMsg.setSchoolId(mySchoolId());
        StudentReceiveMsg s=receiveMsgService.saveReceiveMsg(receiveMsg);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findReceiveMsgById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找消息接收表", notes = "返回响应对象", response=StudentReceiveMsg.class)
    public ResponseJson findReceiveMsgById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StudentReceiveMsg receiveMsg=receiveMsgService.findReceiveMsgById(id);
        return new ResponseJson(receiveMsg);
    }

    @PostMapping("/update/updateReceiveMsg")
    @ApiOperation(value = "修改消息接收表对象", notes = "返回响应对象")
    public ResponseJson updateReceiveMsg(
            @ApiParam(value = "被修改的消息接收表对象,对象属性不为空则修改", required = true)
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsgService.updateReceiveMsg(receiveMsg);
        return new ResponseJson();
    }

    @GetMapping("/look/lookReceiveMsgById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找消息接收表", notes = "返回响应对象", response=StudentReceiveMsg.class)
    public ResponseJson lookReceiveMsgById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StudentReceiveMsg receiveMsg=receiveMsgService.findReceiveMsgById(id);
        return new ResponseJson(receiveMsg);
    }

    @PostMapping("/findReceiveMsgsByCondition")
    @ApiOperation(value = "根据条件查找消息接收表", notes = "返回响应对象", response=StudentReceiveMsg.class)
    public ResponseJson findReceiveMsgsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsg.setSchoolId(mySchoolId());
        List<StudentReceiveMsg> data=receiveMsgService.findReceiveMsgListByCondition(receiveMsg);
        long count=receiveMsgService.findReceiveMsgCountByCondition(receiveMsg);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneReceiveMsgByCondition")
    @ApiOperation(value = "根据条件查找单个消息接收表,结果必须为单条数据", notes = "没有时返回空", response=StudentReceiveMsg.class)
    public ResponseJson findOneReceiveMsgByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentReceiveMsg receiveMsg){
        StudentReceiveMsg one=receiveMsgService.findOneReceiveMsgByCondition(receiveMsg);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteReceiveMsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteReceiveMsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        receiveMsgService.deleteReceiveMsg(id);
        return new ResponseJson();
    }


    @PostMapping("/findReceiveMsgListByCondition")
    @ApiOperation(value = "根据条件查找消息接收表列表", notes = "返回响应对象,不包含总条数", response=StudentReceiveMsg.class)
    public ResponseJson findReceiveMsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StudentReceiveMsg receiveMsg){
       receiveMsg.setSchoolId(mySchoolId());
        List<StudentReceiveMsg> data=receiveMsgService.findReceiveMsgListByCondition(receiveMsg);
        return new ResponseJson(data);
    }

    @PostMapping("/findStudentReceiveMsgListByCondition")
    @ApiOperation(value = "根据条件查找消息接收表列表", notes = "返回响应对象,不包含总条数", response=StudentReceiveMsg.class)
    public ResponseJson findStudentReceiveMsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated(GroupOne.class)
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsg.setSchoolId(mySchoolId());
        receiveMsg.setDmUser(currentDmClassCard().getUserName());
        receiveMsg.setClassesId(currentDmClassCard().getClassId());
        receiveMsg.setPager(new Pager().setPaging(false).setSortField("receiveTime").setSortOrder("DESC"));
        List<StudentReceiveMsg> data=receiveMsgService.findReceiveMsgListByCondition(receiveMsg);
        final String lastTime ;
        if(data != null && !data.isEmpty()){
            lastTime = data.get(0).getReceiveTime();

        }else{
            lastTime = null;
            data = new ArrayList<>();
        }
        final Map<Student, Long> collect = data.stream().collect(Collectors.groupingBy(StudentReceiveMsg::getStudent, Collectors.counting()));
        final List<StudentMsg> list = new ArrayList<>();
        collect.forEach((student,count)->{
            StudentMsg studentMsg = new StudentMsg();
            studentMsg.setCount(count.intValue());
            studentMsg.setStudentId(student.getId());
            studentMsg.setStudentName(student.getName());
            studentMsg.setStudentImg(student.getImgUrl());
            studentMsg.setStudentNo(student.getStudentNo());
            studentMsg.setTime(lastTime);
            list.add(studentMsg);
        });
        return new ResponseJson(list);
    }


    @PostMapping("/readTextMsgs")
    @ApiOperation(value = "阅读文字消息")
    public ResponseJson readTextMsgs(@RequestBody List<String> ids){
        receiveMsgService.readTextMsgs(ids);
        return new ResponseJson("阅读消息成功");
    }

    @PostMapping("/readAudioMsg/{id}")
    @ApiOperation(value = "阅读语音消息")
    public ResponseJson readAudioMsg(@PathVariable("id") String id){
        receiveMsgService.readAudioMsg(id);
        return new ResponseJson("阅读消息成功");
    }

}
