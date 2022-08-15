package com.yice.edu.cn.osp.controller.dm.dmScreenSaver;

import cn.hutool.core.date.DateUtil;
import com.qiniu.http.Response;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.dm.dmScreenSaver.DmScreenSaverMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmScreenSaverMsg")
@Api(value = "/dmScreenSaverMsg",description = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统模块")
public class DmScreenSaverMsgController {
    @Autowired
    private DmScreenSaverMsgService dmScreenSaverMsgService;
    @Autowired
    private FileTypeUtil fileTypeUtil;
    @PostMapping("/saveDmScreenSaverMsg")
    @ApiOperation(value = "保存创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", notes = "返回保存好的创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", response=DmScreenSaverMsg.class)
    public ResponseJson saveDmScreenSaverMsg(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsg.setSchoolId(mySchoolId());
        dmScreenSaverMsg.setTeacherId(myId());
        Date date = DateUtil.parse(dmScreenSaverMsg.getEndTime());
        //如果date小于当前时间，则返回-1,如果大于则返回-2,如果相等则返回0
        int compareTo = date.compareTo(DateUtil.date());
        if(compareTo==-1 || compareTo==0){
            //结束时间小于当前时间，则添加消息，需要更改为已结束
            dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.STOP);
        }else{
            //如果状态是4，则直接保存为草稿
            if(dmScreenSaverMsg.getStatus()==4){
                dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.DRAFT);
            }else{
                dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.RUNNING);
            }
        }
        DmScreenSaverMsg s = dmScreenSaverMsgService.saveDmScreenSaverMsg(dmScreenSaverMsg);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmScreenSaverMsgById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "返回响应对象", response=DmScreenSaverMsg.class)
    public ResponseJson findDmScreenSaverMsgById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmScreenSaverMsg dmScreenSaverMsg=dmScreenSaverMsgService.findDmScreenSaverMsgById(id);
        return new ResponseJson(dmScreenSaverMsg);
    }

    @PostMapping("/update/updateDmScreenSaverMsg")
    @ApiOperation(value = "修改创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", notes = "返回响应对象")
    public ResponseJson updateDmScreenSaverMsg(
            @ApiParam(value = "被修改的创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        Date date = DateUtil.parse(dmScreenSaverMsg.getEndTime());
        //如果date小于当前时间，则返回-1,如果大于则返回-2,如果相等则返回0
        int compareTo = date.compareTo(DateUtil.date());
        if(compareTo==-1 || compareTo==0){
            //结束时间小于当前时间，则添加消息，需要更改为已结束
            dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.STOP);
        }else{
            dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.RUNNING);
        }
        dmScreenSaverMsgService.updateDmScreenSaverMsg(dmScreenSaverMsg);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmScreenSaverMsgById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "返回响应对象", response=DmScreenSaverMsg.class)
    public ResponseJson lookDmScreenSaverMsgById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmScreenSaverMsg dmScreenSaverMsg=dmScreenSaverMsgService.findDmScreenSaverMsgById(id);
        return new ResponseJson(dmScreenSaverMsg);
    }

    @PostMapping("/findDmScreenSaverMsgsByCondition")
    @ApiOperation(value = "根据条件查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "返回响应对象", response=DmScreenSaverMsg.class)
    public ResponseJson findDmScreenSaverMsgsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
       dmScreenSaverMsg.setSchoolId(mySchoolId());
        List<DmScreenSaverMsg> data=dmScreenSaverMsgService.findDmScreenSaverMsgListByCondition(dmScreenSaverMsg);
        long count=dmScreenSaverMsgService.findDmScreenSaverMsgCountByCondition(dmScreenSaverMsg);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmScreenSaverMsgByCondition")
    @ApiOperation(value = "根据条件查找单个创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统,结果必须为单条数据", notes = "没有时返回空", response=DmScreenSaverMsg.class)
    public ResponseJson findOneDmScreenSaverMsgByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        DmScreenSaverMsg one=dmScreenSaverMsgService.findOneDmScreenSaverMsgByCondition(dmScreenSaverMsg);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmScreenSaverMsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmScreenSaverMsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmScreenSaverMsgService.deleteDmScreenSaverMsg(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmScreenSaverMsgListByCondition")
    @ApiOperation(value = "根据条件查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统列表", notes = "返回响应对象,不包含总条数", response=DmScreenSaverMsg.class)
    public ResponseJson findDmScreenSaverMsgListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
       dmScreenSaverMsg.setSchoolId(mySchoolId());
        List<DmScreenSaverMsg> data=dmScreenSaverMsgService.findDmScreenSaverMsgListByCondition(dmScreenSaverMsg);
        return new ResponseJson(data);
    }
    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if (suffixInt == 0) {
            return new ResponseJson(false, "不支持的文件格式");
        }
        //不包含文件后缀名
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + suffix);
        map.put("url", url);
        return new ResponseJson(map);
    }

    @PostMapping("/batchUpdateDmScreenSaverMsg")
    @ApiOperation(value = "批量修改消息密码")
    public void batchUpdateDmScreenSaverMsg(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsg.setSchoolId(mySchoolId());
        dmScreenSaverMsg.setTeacherId(myId());
        dmScreenSaverMsgService.batchUpdateDmScreenSaverMsg(dmScreenSaverMsg);
    }

    @PostMapping("/batchDeleteDmScreenSaverMsg")
    @ApiOperation(value = "批量删除消息")
    public void batchDeleteDmScreenSaverMsg(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgService.batchDeleteDmScreenSaverMsg(dmScreenSaverMsg);
    }

    @PostMapping("/stopDmScreenSaverMsg")
    @ApiOperation(value = "停止显示消息", notes = "返回响应对象")
    public ResponseJson stopDmScreenSaverMsg(
            @ApiParam(value = "被停止的对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg) {
        //修改消息的最后显示时间
        dmScreenSaverMsg.setEndTime(DateUtil.now());
        //设置消息停止
        dmScreenSaverMsg.setStatus(Constant.dmScreenSaver.STOP);
        dmScreenSaverMsgService.updateDmScreenSaverMsg(dmScreenSaverMsg);
        return new ResponseJson();
    }

    @PostMapping("deleteQiniuFile")
    @ApiOperation(value = "创建时间：2019-01-21。说明：用于删除七牛的文件", notes = "返回响应对象")
    public Response deleteQiniuFile(@ApiParam(value = "去删除七牛的文件,需要用到qinliuUrl", required = true) String qinliuUrl) {
        return QiniuUtil.deleteFile(qinliuUrl);
    }
    @PostMapping("/batchUpdateDmScreenSaverMsgStatus")
    @ApiOperation(value = "根据时间修改当前学校所有的消息状态")
    public void batchUpdateDmScreenSaverMsgStatus(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.batchUpdateDmScreenSaverMsgStatus(dmScreenSaverMsg);
    }



}
