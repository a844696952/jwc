package com.yice.edu.cn.osp.controller.xw.kq;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.*;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalDataTest;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceAccountService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceSeriesService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.kq.KqOriginalDataService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.visitorManage.VisitorService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.osp.controller.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-01 09:53
 * @Description: 终端设备识别记录上报接口
 */
@RestController
@RequestMapping("/kqOriginDataReceive")
public class KqOriginDataReceiveController {
    @Autowired
    private KqOriginalDataService kqOriginalDataService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private YcVerifaceAccountService ycVerifaceAccountService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @Autowired
    private YcVerifaceDeviceSeriesService ycVerifaceDeviceSeriesService;
    @CreateCache(name = Constant.Redis.H5_GETIMAGE_CODE, expire = Constant.Redis.H5_CODE_TIMEOUT * 60)
    private Cache<String, String> codeCache;
    private final static Logger logger = LoggerFactory.getLogger(KqOriginDataReceiveController.class);


    //H5访客申请接口
    @PostMapping("/visitorApply")
    @ApiOperation(value = "请求中移访客接口校验图片并入库)")
    public ResponseJson saveVisitorApplyRecords(
            @ApiParam(value = "访客记录", required = true)
            @RequestBody Visitor visitor,HttpServletRequest re)  {
        //System.out.println(re.getSession().getId());
        ResponseJson responseJson= visitorService.saveVisitorApplyRecords(visitor,re);
        return responseJson;
    }

    @GetMapping("/sendImg")
    @ApiOperation(value = "获取验证码)")
    public void sendImg(
            @ApiParam(value = "验证码", required = true)
                    HttpServletRequest req,
            HttpServletResponse res) {
        visitorService.sendImg(req, res);
    }

    //校验
    @PostMapping("/judgeCode")
    @ApiOperation(value = "校验验证码)")
    public ResponseJson judgeCode(
            @ApiParam(value = "访客记录", required = true)
            @RequestBody Visitor visitor,HttpServletRequest res) {
        String code = codeCache.get(res.getSession().getId());
        if (!code.equals(visitor.getCode())) {
            return new ResponseJson(false, "验证码错误");
        }
        codeCache.remove(res.getSession().getId());
        return new ResponseJson();
    }

    //中移动接收数据新接口（生产）
    @PostMapping("/dataReceive")
    @ApiOperation(value = "中移请求我方接口，传入中移打卡记录(userId和userName需要经过AES加密)", notes = "", response = DataReceiveResBean.class)
    public DataReceiveResBean zyDataReceiveTEXT(
            @ApiParam(value = "中移识别记录", required = true)
            @RequestBody KqOriginalDataTest kqOriginalDataTest) {
        DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
        HashMap beanMap = new HashMap<String, String>();
        try {

            if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedMessageType())){
                dataReceiveResBean.setReturnCode("2999");
                dataReceiveResBean.setReturnMessage("必传参数CapturedMessageType为空");
                beanMap.put("reqId", "");
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }else {
                String capturedMessageType = kqOriginalDataTest.getCapturedMessageType();
                if (capturedMessageType.equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&(
                        StrUtil.isEmpty(kqOriginalDataTest.getReqId()) || StrUtil.isEmpty(kqOriginalDataTest.getUserId())
                        || StrUtil.isEmpty(kqOriginalDataTest.getUserType()) || StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())
                        || StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime()) || StrUtil.isEmpty(kqOriginalDataTest.getCustId())
                        || StrUtil.isEmpty(kqOriginalDataTest.getDerectionFlag()) || StrUtil.isEmpty(kqOriginalDataTest.getDeviceNo())
                        || StrUtil.isEmpty(kqOriginalDataTest.getDeviceType()) || StrUtil.isEmpty(kqOriginalDataTest.getUserName())
                    )
                ) {
                    dataReceiveResBean.setReturnCode("2999");
                    dataReceiveResBean.setReturnMessage("必传参数为空");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }else if (capturedMessageType.equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)&&(
                                 StrUtil.isEmpty(kqOriginalDataTest.getReqId())
                                 || StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCustId())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDeviceNo())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDeviceType())
                        )){
                    dataReceiveResBean.setReturnCode("2999");
                    dataReceiveResBean.setReturnMessage("必传参数为空");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }
            }
            //获得密钥
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(kqOriginalDataTest.getCustId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool==null){
                dataReceiveResBean.setReturnCode("9999");
                dataReceiveResBean.setReturnMessage("CustId有误，请检查！");
                beanMap.put("reqId", kqOriginalDataTest.getReqId());
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }
            String myKey = kqSchool.getKey();
            //保存操作
            KqOriginalData kqOriginalData =   kqOriginalDataService.saveOriginalDataByUserTypeTEXT(kqOriginalDataTest ,myKey);
            //发送推送
            kqOriginalDataService.pushMassageToTerminalTEXT(kqOriginalData);
            //响应中移
            dataReceiveResBean.setReturnCode("0000");
            dataReceiveResBean.setReturnMessage("请求成功");
            beanMap.put("reqId", kqOriginalDataTest.getReqId());
            dataReceiveResBean.setBean(beanMap);
        } catch (Exception e) {
            //若出异常返回系统繁忙，此时请开发者稍候再试
            e.printStackTrace();
            dataReceiveResBean.setReturnCode("9999");
            dataReceiveResBean.setReturnMessage("系统繁忙，此时请开发者稍候再试");
            beanMap.put("reqId", "");
            dataReceiveResBean.setBean(beanMap);
            return dataReceiveResBean;
        }
        return dataReceiveResBean;
    }

    @PostMapping("/findTeacherByCondition")
    @ApiOperation(value = "查找教师)")
    public ResponseJson findTeacherByCondition(
            @ApiParam(value = "扫描二维码时的接口  返回教师信息", required = true)
            @RequestBody Visitor visitor) {
        Teacher teacher = new Teacher();
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        String schoolId=SimpleCryptoKit.decrypt(visitor.getSchoolId());
        teacher.setSchoolId(schoolId);
        teacher.setName(visitor.getTeacherName());
        Pager pager = new Pager();
        pager.setPaging(false);
        teacher.setPager(pager);
        List<Teacher> data = teacherService.findTeacherListByCondition(teacher);
        return new ResponseJson(data);

    }


//---测试用-
    //中移动接收数据新接口（测试）
    @PostMapping("/dataReceive222")
    @ApiOperation(value = "中移请求我方接口(测试用)", notes = "", response = DataReceiveResBean.class)
    public DataReceiveResBean zyDataReceive222(
            @ApiParam(value = "中移识别记录", required = true)
            @RequestBody KqOriginalDataTest kqOriginalDataTest) {
        DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
        HashMap beanMap = new HashMap<String, String>();
        try {
            if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedMessageType())){
                dataReceiveResBean.setReturnCode("2999");
                dataReceiveResBean.setReturnMessage("必传参数CapturedMessageType为空");
                beanMap.put("reqId", "");
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }else {
                String capturedMessageType = kqOriginalDataTest.getCapturedMessageType();
                if (capturedMessageType.equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&(
                        StrUtil.isEmpty(kqOriginalDataTest.getReqId()) || StrUtil.isEmpty(kqOriginalDataTest.getUserId())
                                || StrUtil.isEmpty(kqOriginalDataTest.getUserType()) || StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime()) || StrUtil.isEmpty(kqOriginalDataTest.getCustId())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDerectionFlag()) || StrUtil.isEmpty(kqOriginalDataTest.getDeviceNo())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDeviceType()) || StrUtil.isEmpty(kqOriginalDataTest.getUserName())
                )
                ) {
                    dataReceiveResBean.setReturnCode("2999");
                    dataReceiveResBean.setReturnMessage("必传参数为空");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }else if (capturedMessageType.equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)&&(
                        StrUtil.isEmpty(kqOriginalDataTest.getReqId())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime())
                                || StrUtil.isEmpty(kqOriginalDataTest.getCustId())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDeviceNo())
                                || StrUtil.isEmpty(kqOriginalDataTest.getDeviceType())
                )){
                    dataReceiveResBean.setReturnCode("2999");
                    dataReceiveResBean.setReturnMessage("必传参数为空");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }
            }
            //获得密钥
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(kqOriginalDataTest.getCustId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool==null){
                dataReceiveResBean.setReturnCode("9999");
                dataReceiveResBean.setReturnMessage("CustId有误，请检查！");
                beanMap.put("reqId", kqOriginalDataTest.getReqId());
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }
            String myKey = kqSchool.getKey();
            //保存操作
            KqOriginalData kqOriginalData =   kqOriginalDataService.saveOriginalDataByUserTypeForStrangeTEST(kqOriginalDataTest ,myKey);
            //发送推送
            kqOriginalDataService.pushMassageToTerminalForStrangeTEST(kqOriginalData);
            //响应中移
            dataReceiveResBean.setReturnCode("0000");
            dataReceiveResBean.setReturnMessage("请求成功");
            beanMap.put("reqId", kqOriginalDataTest.getReqId());
            dataReceiveResBean.setBean(beanMap);
        } catch (Exception e) {
            //若出异常返回系统繁忙，此时请开发者稍候再试
            e.printStackTrace();
            dataReceiveResBean.setReturnCode("9999");
            dataReceiveResBean.setReturnMessage("系统繁忙，此时请开发者稍候再试");
            beanMap.put("reqId", "");
            dataReceiveResBean.setBean(beanMap);
            return dataReceiveResBean;
        }
        return dataReceiveResBean;
    }
//---测试用-

//---本公司人脸识别项目begin----------
    //校内pc登陆，并返回账号id，用于其他业务标识
    @PostMapping("/loginSchoolPcCameraAccount")
    @ApiOperation(value = "亿策人脸识别校内pc登陆，返回账号id", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean loginSchoolPcCameraAccount( @ApiParam(value = "亿策人脸账号对象", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        if (StrUtil.isEmpty(ycVerifaceAccount.getAccount())||StrUtil.isEmpty(ycVerifaceAccount.getPassword())){
            res.setReturnCode("9999");
            res.setReturnMessage("password or account wrong!");
            res.setAccountId(null);
            return res;
        }
        String account = ycVerifaceAccount.getAccount();
        String password = ycVerifaceAccount.getPassword();
        YcVerifaceAccount ycVerifaceAccount1 = new YcVerifaceAccount();
        ycVerifaceAccount1.setPassword(password);
        ycVerifaceAccount1.setAccount(account);
        YcVerifaceAccount oneYcVerifaceAccountByCondition = ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(ycVerifaceAccount1);
        if (oneYcVerifaceAccountByCondition!=null){
            //创建缓存，保存设备账号和设备状态
            ycVerifaceAccountService.createAccountCache(oneYcVerifaceAccountByCondition);
            res.setReturnCode("0000");
            res.setReturnMessage("success");
            res.setAccountId(oneYcVerifaceAccountByCondition.getId());
            res.setSchoolId(oneYcVerifaceAccountByCondition.getSchoolId());
            return res;
        }
        res.setReturnCode("9999");
        res.setReturnMessage("password or account wrong!");
        res.setAccountId(null);
        return res;
    }

    //门禁设备登录
    @PostMapping("/loginSchoolDoorVerifaceAccount")
    @ApiOperation(value = "亿策人脸门禁设备账号登陆，返回设备id，账号id，学校名称", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean loginSchoolDoorVerifaceAccount( @ApiParam(value = "亿策人脸门禁账号对象", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        if (StrUtil.isEmpty(ycVerifaceAccount.getAccount())||StrUtil.isEmpty(ycVerifaceAccount.getPassword())){
            res.setReturnCode("2999");
            res.setReturnMessage("password or account wrong!");
            res.setAccountId(null);
            res.setSchoolId(null);
            res.setSchoolName(null);
            res.setToken(null);
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceAccount.getDeviceId())){
            res.setReturnCode("1999");
            res.setReturnMessage("deviseId wrong!");
            res.setAccountId(null);
            res.setSchoolId(null);
            res.setSchoolName(null);
            res.setToken(null);
            return res;
        }
        String account = ycVerifaceAccount.getAccount();
        String password = ycVerifaceAccount.getPassword();
        String deviceId = ycVerifaceAccount.getDeviceId();
        YcVerifaceAccount ycVerifaceAccount1 = new YcVerifaceAccount();
        ycVerifaceAccount1.setPassword(password);
        ycVerifaceAccount1.setAccount(account);
        ycVerifaceAccount1.setType("1");
        YcVerifaceAccount oneYcVerifaceAccountByCondition = ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(ycVerifaceAccount1);
        if (oneYcVerifaceAccountByCondition==null){
            res.setReturnCode("3999");
            res.setReturnMessage("password or account wrong!");
            res.setAccountId(null);
            res.setSchoolId(null);
            res.setSchoolName(null);
            res.setToken(null);
            return res;
        }
        String accountId = oneYcVerifaceAccountByCondition.getId();
        String schoolId = oneYcVerifaceAccountByCondition.getSchoolId();
        //先找设备属于哪个学校
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setDeviceSign(deviceId);
        ycVerifaceDevice.setSchoolId(schoolId);
        ycVerifaceDevice.setAccountId(accountId);
        YcVerifaceDevice oneDoorDevice = ycVerifaceDeviceService.findOneYcVerifaceDeviceByCondition(ycVerifaceDevice);
        if (oneDoorDevice==null){
            res.setReturnCode("1999");
            res.setReturnMessage("deviseId wrong!");
            res.setAccountId(null);
            res.setSchoolId(null);
            res.setSchoolName(null);
            res.setToken(null);
            return res;
        }
        //查找学校的名称
        School schoolById = schoolService.findSchoolById(schoolId);
        if (schoolById==null){
            res.setReturnCode("9999");
            res.setReturnMessage("school wrong,please contact administrator!");
            res.setAccountId(null);
            res.setSchoolId(null);
            res.setSchoolName(null);
            res.setToken(null);
            return res;
        }
        String schoolname = schoolById.getName();
        //创建缓存，保存设备账号和设备状态
        ycVerifaceAccountService.createAccountCache(oneYcVerifaceAccountByCondition);
        ycVerifaceAccountService.setDoorDeviceCache(oneDoorDevice);
        res.setReturnCode("0000");
        res.setReturnMessage("Login Success");
        res.setAccountId(accountId);
        res.setSchoolId(schoolId);
        res.setSchoolName(schoolname);
        //创建包含设备id的token
        String token = JwtUtil.createJWT(deviceId, "{}", null, -1);
        res.setToken(token);
        return res;
    }


    //校内pc获取账号下设备列表
    @PostMapping("/schoolPcCameraAccountFindDevice")
    @ApiOperation(value = "亿策人脸识别校内pc用账号id查找账号下设备列表", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcCameraAccountFindDevice(@ApiParam(value = "亿策人脸账号对象", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String accountId = ycVerifaceAccount.getAccountId();
        if (StrUtil.isEmpty(accountId)){
            res.setReturnCode("9999");
            res.setReturnMessage("accountId wrong!");
            return res;
        }
        YcVerifaceAccount account = ycVerifaceAccountService.findYcVerifaceAccountById(accountId);
        if (account==null){
            //创建缓存，保存设备账号和设备状态
            res.setReturnCode("9999");
            res.setReturnMessage("accountId wrong!");
            return res;
        }
        YcVerifaceDevice device = new YcVerifaceDevice();
        device.setAccountId(account.getId());
        device.setSchoolId(account.getSchoolId());
        device.setType("0");
        Pager pager = new Pager();
        pager.setIncludes("_id","deviceName","deviceFactoryName","deviceLocationName","derectionFlag","videoStreamAddress","deviceSign","deviceSeriesName");
        pager.setPaging(false);
        device.setPager(pager);
        List<YcVerifaceDevice> ycVerifaceDeviceList = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(device);
        for (YcVerifaceDevice y:ycVerifaceDeviceList){
            y.setDeviceId(y.getId());
            y.setId(null);
        }
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        YcVerifaceDeviceSeries deviceSeries = new YcVerifaceDeviceSeries();
        deviceSeries.setSeriesType("0");
        Pager pager1 = new Pager();
        pager1.setIncludes("factoryName","seriesName","videoStreamAddress");
        pager1.setPaging(false);
        deviceSeries.setPager(pager1);
        List<YcVerifaceDeviceSeries> allSeries = ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(deviceSeries);
        res.setExamples(allSeries);
        res.setBeans(ycVerifaceDeviceList);
        //查找厂家流地址示例
        return res;
    }

    //校内PC修改摄像头状态
    @PostMapping("/schoolPcUpdateCameraDevice")
    @ApiOperation(value = "亿策人脸识别校内pc更新账号下设备列表", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcUpdateCameraDevice(@ApiParam(value = "亿策人脸账号对象", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String accountId = ycVerifaceDevice.getAccountId();
        if (StrUtil.isEmpty(accountId)){
            res.setReturnCode("9999");
            res.setReturnMessage("account not found");
            return res;
        }
        YcVerifaceAccount account = ycVerifaceAccountService.findYcVerifaceAccountById(accountId);
        if (account==null){
            res.setReturnCode("9999");
            res.setReturnMessage("account not found");
            return res;
        }
        YcVerifaceDevice device = new YcVerifaceDevice();
        device.setVideoStreamAddress(ycVerifaceDevice.getVideoStreamAddress());
        device.setId(ycVerifaceDevice.getDeviceId());
        device.setDeviceSign(ycVerifaceDevice.getDeviceSign());
        ycVerifaceDeviceService.updateYcVerifaceDevice(device);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }

    //校内PC发送心跳
    @PostMapping("/schoolPcHeartbeat")
    @ApiOperation(value = "亿策人脸识别校内pc心跳", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcHeartbeat(@ApiParam(value = "亿策人脸账号对象", required = true)  @RequestBody YcVerifaceHeartbeatBean ycVerifaceHeartbeatBean){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String accountId = ycVerifaceHeartbeatBean.getAccountId();
        List<YcVerifaceDevice> beans = ycVerifaceHeartbeatBean.getBeans();
        YcVerifaceAccount account = ycVerifaceAccountService.findYcVerifaceAccountById(accountId);
        if (account==null){
            res.setReturnCode("9999");
            res.setReturnMessage("account not found");
            return res;
        }
        account.setChildren(beans);
        ycVerifaceAccountService.heartbeat(account);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }

    //接收单条识别记录，保存记录并做相关业务
    @PostMapping("/ycVerifaceDataReceive")
    @ApiOperation(value = "亿策人脸识别单数据接收", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean ycVerifaceDataReceive( @ApiParam(value = "原始记录对象", required = true) @RequestBody KqOriginalDataTest kqOriginalDataTest){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        //基础参数校验
        if (StrUtil.isEmpty(kqOriginalDataTest.getSchoolId())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数schoolId为空");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getDeviceId())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数deviceId为空");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数capturedTime为空");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数capturedImage为空");
            return res;
        }
        //基础校验通过，校验人员类型
        if (StrUtil.isEmpty(kqOriginalDataTest.getUserId())){
            //陌生人
            kqOriginalDataTest.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER);
        }else {
            //陌生人
            kqOriginalDataTest.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL);
        }
        //填充其他数据
        KqOriginalDataTest filled = kqOriginalDataService.fillOtherInfo(kqOriginalDataTest);
        if (filled==null){
            //响应算法服务器
            res.setReturnCode("0000");
            res.setReturnMessage("请求成功");
            return res;
        }
        //保存记录并调用业务
        KqOriginalData kqOriginalData = kqOriginalDataService.saveYcVerifaceOriginalData(filled);

        //发送推送
        kqOriginalDataService.pushMassageToTerminalForStrangeTEST(kqOriginalData);

        //响应算法服务器
        res.setReturnCode("0000");
        res.setReturnMessage("请求成功");
        return res;
    }

    //按天批量接收识别记录，保存记录并创建定时任务
    @PostMapping("/ycVerifaceDataBatchReceive")
    @ApiOperation(value = "亿策人脸识别断网重连批量数据接收", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean ycVerifaceDataBatchReceive( @ApiParam(value = "原始记录对象", required = true) @RequestBody YcVerifaceOriginDataBatchReceiveBean ycVerifaceOriginDataBatchReceiveBean){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        //基础参数校验
        if (StrUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getSchoolID())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数schoolId为空");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getCatchDate())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数catchDate为空");
            return res;
        }
        if (CollectionUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getBeans())){
            res.setReturnCode("2999");
            res.setReturnMessage("必传参数beans为空");
            return res;
        }
        //先保存至缓存，启动线程保存数据
        kqOriginalDataService.saveBatchDatoToRedis(ycVerifaceOriginDataBatchReceiveBean);
        res.setReturnCode("0000");
        res.setReturnMessage("请求成功");
        return res;


    }

    //亿策门禁设备拉取人员列表
    @PostMapping("/ycVerifaceDoorPullPeopleChange")
    @ApiOperation(value = "亿策门禁设备拉取人员列表 ", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean ycVerifaceDoorPullPeopleChange(@ApiParam(value = "亿策门禁设备对象", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice,HttpServletRequest request){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("登录信息失效");
            return res;
        }
        //System.out.println(token);
        String deviceSign =null;
        try{
            Claims claims = JwtUtil.parseJWT(token);
            deviceSign = claims.getId();
        }catch (Exception e){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("解析token失败");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode("8999");
            res.setReturnMessage("deviceId 不能为空");
            return res;
        }
        if (StrUtil.isEmpty(deviceSign)||!deviceSign.equals(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("deviceId与登录账号冲突，请重新登录");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getSchoolId())){
            res.setReturnCode("8999");
            res.setReturnMessage("schoolId 不能为空");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getChangeCode())){
            res.setReturnCode("8999");
            res.setReturnMessage("changeCode 不能为空");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getCacheKey())){
            res.setReturnCode("8999");
            res.setReturnMessage("cacheKey 不能为空");
            return res;
        }

        YcVerifaceDevice device1  = new YcVerifaceDevice();
        device1.setDeviceSign(ycVerifaceDevice.getDeviceId());
        device1.setType("1");
        device1.setSchoolId(ycVerifaceDevice.getSchoolId());
        List<YcVerifaceDevice> deviceList = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(device1);
        if (CollectionUtil.isEmpty(deviceList)||deviceList.size()!=1){
            res.setPeopleListChangBean(null);
            res.setReturnCode("9999");
            res.setReturnMessage("设备未录入，请联系管理员");
            return res;
        }
        YcVerifaceDevice ycVerifaceDevice1 = deviceList.get(0);
        ycVerifaceDevice1.setChangeCode(ycVerifaceDevice.getChangeCode());
        ycVerifaceDevice1.setCacheKey(ycVerifaceDevice.getCacheKey());
        YcVerifaceDoorChangeResBean resBean = ycVerifaceDeviceService.ycVerifaceDoorPullPeopleChange(ycVerifaceDevice1);
        if (resBean==null){
            res.setPeopleListChangBean(null);
            res.setReturnCode("9999");
            res.setReturnMessage("获取特征值失败，请稍后重试");
            return res;
        }
        res.setPeopleListChangBean(resBean);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }

    //亿策门禁拉取结果回传
    @PostMapping("/ycVerifaceDoorPullResult")
    @ApiOperation(value = "亿策门禁设备拉取人员列表 ", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean ycVerifaceDoorPullResult(@ApiParam(value = "亿策门禁设备对象", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice,HttpServletRequest request){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("登录信息失效");
            return res;
        }
        //System.out.println(token);
        String deviceSign =null;
        try{
            Claims claims = JwtUtil.parseJWT(token);
            deviceSign = claims.getId();
        }catch (Exception e){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("解析token失败");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode("8999");
            res.setReturnMessage("deviceId 不能为空");
            return res;
        }
        if (StrUtil.isEmpty(deviceSign)||!deviceSign.equals(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("deviceId与登录账号冲突，请重新登录");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getSchoolId())){
            res.setReturnCode("8999");
            res.setReturnMessage("schoolId 不能为空");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getCacheKey())){
            res.setReturnCode("8999");
            res.setReturnMessage("cacheKey 不能为空");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getPullResult())){
            res.setReturnCode("8999");
            res.setReturnMessage("pullResult 不能为空");
            return res;
        }
        //清除缓存
        ycVerifaceDeviceService.checkPullResultAndAdjustCache(ycVerifaceDevice);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }



    //---本公司人脸识别项目end------------
}
