package com.yice.edu.cn.tap.controller.visitorManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.tap.service.visitorManage.KqSchoolInitService;
import com.yice.edu.cn.tap.service.visitorManage.YcApplyVistorService;
import com.yice.edu.cn.tap.service.visitorManage.ZyApplyVistorService;
import com.yice.edu.cn.tap.service.visitorManage.VisitorService;
import com.yice.edu.cn.tap.service.ycVeriface.YcVerifaceDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/visitor")
@Api(value = "/visitor", description = "模块")
public class  VisitorController {
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private ZyApplyVistorService zyApplyVistorService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @Autowired
    private YcApplyVistorService ycApplyVistorService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    private final static Logger logger = LoggerFactory.getLogger(VisitorController.class);
    @PostMapping("/sendMessage")
    @ApiOperation(value = "发信息)")
    public ResponseJson sendMessage(
            @ApiParam(value = "申请记录", required = true)
            @RequestBody Visitor visitor) {
        String s = visitorService.sendMessage(visitor);
        return new ResponseJson(s);

    }

    //教师端请求中移校验接口
    @PostMapping("/visitorApply")
    @ApiOperation(value = "请求中移访客接口校验图片)")
    public ResponseJson saveVisitorApplyRecords(
            @ApiParam(value = "申请记录", required = true)
            @RequestBody Visitor visitor) {
        //获得本校密钥
        if (visitor.getApplyStatus().equals("0")) {
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(visitor.getSchoolId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            int hasDevice = 0;
            if (kqSchool == null){
                logger.info(visitor.getSchoolId()+"没有中移动设备");
            }else {
                hasDevice = hasDevice +1;
            }
            //找该学校有没有亿策门禁设备
            YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
            ycVerifaceDevice.setSchoolId(visitor.getSchoolId());
            long ycVerifaceDeviceCount = ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
            if (ycVerifaceDeviceCount==0){
                logger.info(visitor.getSchoolId()+"没有亿策门禁设备");
            }else {
                hasDevice = hasDevice +2;
            }
            if (hasDevice == 0){
                return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
            }
            DataReceiveResBean res =new DataReceiveResBean();
           if (hasDevice == 1){
               logger.info(visitor.getSchoolId()+"只有中移动设备");
               res = zyApplyVistorService.requestZyVisitorEnter(isProd, kqSchool, visitor);
           }else if (hasDevice == 2){
               logger.info(visitor.getSchoolId()+"只有亿策门禁设备");
               res = ycApplyVistorService.requestVisitorEnter(isProd, visitor);
           }else if (hasDevice == 3){
               logger.info(visitor.getSchoolId()+"两种厂家都有");
               DataReceiveResBean dataReceiveResBean = zyApplyVistorService.requestZyVisitorEnter(isProd, kqSchool, visitor);
               DataReceiveResBean dataReceiveResBean1 = ycApplyVistorService.requestVisitorEnter(isProd, visitor);
               logger.info("中移访客校验结果"+dataReceiveResBean.getReturnCode());
               logger.info("亿策访客校验结果"+dataReceiveResBean1.getReturnCode());
               if (dataReceiveResBean.getReturnCode().equals("0700")&&dataReceiveResBean1.getReturnCode().equals("0700")){
                   res.setReturnCode("0700");
               }
               if (dataReceiveResBean.getReturnCode().equals("2999")&&dataReceiveResBean1.getReturnCode().equals("2999")){
                   res.setReturnCode("2999");
               }
               if (dataReceiveResBean.getReturnCode().equals("0000")||dataReceiveResBean1.getReturnCode().equals("0000")){
                   res.setReturnCode("0000");
               }
           }
            if (res.getReturnCode().equals("0070")){
                return new ResponseJson(0,"网络异常");
            }
            if(res.getReturnCode().equals("2999")){
                return new ResponseJson(0,"学校暂无设备信息");
            }
            if(res.getReturnCode().equals("4002")){
                return new ResponseJson(0,"身份证号码格式错误");
            }
            //人员类型  0 家长推送) 1 陌生人(发短信)
            if (res.getReturnCode().equals("0000")) {
                visitor.setDevices("0");
                //通过后下一步做绑定
                    visitorService.sendMsg(visitor);
                    visitorService.updateVisitor(visitor);
            } else {
                if (visitor.getVisitorWay().equals("0")) {
                    return new ResponseJson(0, "头像校验不通过");
                } else if (visitor.getVisitorWay().equals("1")) {
                    return new ResponseJson(0, "请上传真实身份证信息");
                }
                //推送家长  暂不做处理  人证 提示身份证错误   刷脸 提示头像错误
            }
        } else if (visitor.getApplyStatus().equals("1")) {
            visitorService.sendMsg(visitor);
            visitorService.updateVisitor(visitor);
        }
        return new ResponseJson(1, "审批成功");
    }

    @PostMapping("/saveVisitor")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = Visitor.class)
    public ResponseJson saveVisitor(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        Visitor s = visitorService.saveVisitor(visitor);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findVisitorById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/update/updateVisitor")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateVisitor(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Visitor visitor) {
        visitorService.updateVisitor(visitor);
        return new ResponseJson();
    }

    @GetMapping("/look/lookVisitorById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson lookVisitorById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/findVisitorsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Visitor visitor) {
        Visitor v = new Visitor();
        v.setSchoolId(mySchoolId());
        v.setAuditorId(myId());
        v.setApplyStatus("2");
        List<Visitor> list = visitorService.findVisitorListByCondition(v);
        if (list.size() > 0) {
            for (Visitor vi : list) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(vi.getEndTime())) && vi.getApplyStatus().equals("2")) {
                    vi.setApplyStatus("3");
                    visitorService.updateVisitor(vi);
                }
            }
        }
        visitor.setSchoolId(mySchoolId());
        visitor.setAuditorId(myId());
        visitor.setApplyStatus("2");
        visitor.getPager().setSortOrder(Pager.DESC);
        visitor.getPager().setSortField("createTime");
        List<Visitor> data = visitorService.findVisitorListByCondition(visitor);
        long count = visitorService.findVisitorCountByCondition(visitor);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findVisitorsByCondition4")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorsByCondition4(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Visitor visitor) {
        Visitor vv = new Visitor();
        vv.setSchoolId(mySchoolId());
        vv.setAuditorId(myId());
        List<Visitor> list = visitorService.findVisitorListByCondition(vv);
        if (list.size() > 0) {
            for (Visitor vi : list) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(vi.getEndTime())) && vi.getApplyStatus().equals("2")) {
                    vi.setApplyStatus("3");
                    visitorService.updateVisitor(vi);
                }
            }
            visitor.setSchoolId(mySchoolId());
            visitor.setAuditorId(myId());
            visitor.getPager().setSortField("updateTime");
            visitor.getPager().setSortOrder(Pager.DESC);
            List<Visitor> list1= visitorService.findVisitorListByCondition(visitor);
            List<Visitor> data = list1.stream().filter(c -> !(c.getApplyStatus().equals("2"))).collect(Collectors.toList());
            long count1 = visitorService.findVisitorCountByCondition(visitor);
            Visitor v = new Visitor();
            v.setSchoolId(mySchoolId());
            v.setAuditorId(myId());
            v.setApplyStatus("2");
            long count2 = visitorService.findVisitorCountByCondition(v);
            long count = count1 - count2;
            return new ResponseJson(data, count);
        }
        return new ResponseJson();
    }

    @PostMapping("/findOneVisitorByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = Visitor.class)
    public ResponseJson findOneVisitorByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        Visitor one = visitorService.findOneVisitorByCondition(visitor);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteVisitor/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteVisitor(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        visitorService.deleteVisitor(id);
        return new ResponseJson();
    }


    @PostMapping("/findVisitorListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = Visitor.class)
    public ResponseJson findVisitorListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        List<Visitor> data = visitorService.findVisitorListByCondition(visitor);
        return new ResponseJson(data);
    }

}
