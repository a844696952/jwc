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
 * @Description: ????????????????????????????????????
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


    //H5??????????????????
    @PostMapping("/visitorApply")
    @ApiOperation(value = "?????????????????????????????????????????????)")
    public ResponseJson saveVisitorApplyRecords(
            @ApiParam(value = "????????????", required = true)
            @RequestBody Visitor visitor,HttpServletRequest re)  {
        //System.out.println(re.getSession().getId());
        ResponseJson responseJson= visitorService.saveVisitorApplyRecords(visitor,re);
        return responseJson;
    }

    @GetMapping("/sendImg")
    @ApiOperation(value = "???????????????)")
    public void sendImg(
            @ApiParam(value = "?????????", required = true)
                    HttpServletRequest req,
            HttpServletResponse res) {
        visitorService.sendImg(req, res);
    }

    //??????
    @PostMapping("/judgeCode")
    @ApiOperation(value = "???????????????)")
    public ResponseJson judgeCode(
            @ApiParam(value = "????????????", required = true)
            @RequestBody Visitor visitor,HttpServletRequest res) {
        String code = codeCache.get(res.getSession().getId());
        if (!code.equals(visitor.getCode())) {
            return new ResponseJson(false, "???????????????");
        }
        codeCache.remove(res.getSession().getId());
        return new ResponseJson();
    }

    //??????????????????????????????????????????
    @PostMapping("/dataReceive")
    @ApiOperation(value = "???????????????????????????????????????????????????(userId???userName????????????AES??????)", notes = "", response = DataReceiveResBean.class)
    public DataReceiveResBean zyDataReceiveTEXT(
            @ApiParam(value = "??????????????????", required = true)
            @RequestBody KqOriginalDataTest kqOriginalDataTest) {
        DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
        HashMap beanMap = new HashMap<String, String>();
        try {

            if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedMessageType())){
                dataReceiveResBean.setReturnCode("2999");
                dataReceiveResBean.setReturnMessage("????????????CapturedMessageType??????");
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
                    dataReceiveResBean.setReturnMessage("??????????????????");
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
                    dataReceiveResBean.setReturnMessage("??????????????????");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }
            }
            //????????????
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(kqOriginalDataTest.getCustId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool==null){
                dataReceiveResBean.setReturnCode("9999");
                dataReceiveResBean.setReturnMessage("CustId?????????????????????");
                beanMap.put("reqId", kqOriginalDataTest.getReqId());
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }
            String myKey = kqSchool.getKey();
            //????????????
            KqOriginalData kqOriginalData =   kqOriginalDataService.saveOriginalDataByUserTypeTEXT(kqOriginalDataTest ,myKey);
            //????????????
            kqOriginalDataService.pushMassageToTerminalTEXT(kqOriginalData);
            //????????????
            dataReceiveResBean.setReturnCode("0000");
            dataReceiveResBean.setReturnMessage("????????????");
            beanMap.put("reqId", kqOriginalDataTest.getReqId());
            dataReceiveResBean.setBean(beanMap);
        } catch (Exception e) {
            //???????????????????????????????????????????????????????????????
            e.printStackTrace();
            dataReceiveResBean.setReturnCode("9999");
            dataReceiveResBean.setReturnMessage("?????????????????????????????????????????????");
            beanMap.put("reqId", "");
            dataReceiveResBean.setBean(beanMap);
            return dataReceiveResBean;
        }
        return dataReceiveResBean;
    }

    @PostMapping("/findTeacherByCondition")
    @ApiOperation(value = "????????????)")
    public ResponseJson findTeacherByCondition(
            @ApiParam(value = "???????????????????????????  ??????????????????", required = true)
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


//---?????????-
    //??????????????????????????????????????????
    @PostMapping("/dataReceive222")
    @ApiOperation(value = "????????????????????????(?????????)", notes = "", response = DataReceiveResBean.class)
    public DataReceiveResBean zyDataReceive222(
            @ApiParam(value = "??????????????????", required = true)
            @RequestBody KqOriginalDataTest kqOriginalDataTest) {
        DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
        HashMap beanMap = new HashMap<String, String>();
        try {
            if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedMessageType())){
                dataReceiveResBean.setReturnCode("2999");
                dataReceiveResBean.setReturnMessage("????????????CapturedMessageType??????");
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
                    dataReceiveResBean.setReturnMessage("??????????????????");
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
                    dataReceiveResBean.setReturnMessage("??????????????????");
                    beanMap.put("reqId", "");
                    dataReceiveResBean.setBean(beanMap);
                    return dataReceiveResBean;
                }
            }
            //????????????
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(kqOriginalDataTest.getCustId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool==null){
                dataReceiveResBean.setReturnCode("9999");
                dataReceiveResBean.setReturnMessage("CustId?????????????????????");
                beanMap.put("reqId", kqOriginalDataTest.getReqId());
                dataReceiveResBean.setBean(beanMap);
                return dataReceiveResBean;
            }
            String myKey = kqSchool.getKey();
            //????????????
            KqOriginalData kqOriginalData =   kqOriginalDataService.saveOriginalDataByUserTypeForStrangeTEST(kqOriginalDataTest ,myKey);
            //????????????
            kqOriginalDataService.pushMassageToTerminalForStrangeTEST(kqOriginalData);
            //????????????
            dataReceiveResBean.setReturnCode("0000");
            dataReceiveResBean.setReturnMessage("????????????");
            beanMap.put("reqId", kqOriginalDataTest.getReqId());
            dataReceiveResBean.setBean(beanMap);
        } catch (Exception e) {
            //???????????????????????????????????????????????????????????????
            e.printStackTrace();
            dataReceiveResBean.setReturnCode("9999");
            dataReceiveResBean.setReturnMessage("?????????????????????????????????????????????");
            beanMap.put("reqId", "");
            dataReceiveResBean.setBean(beanMap);
            return dataReceiveResBean;
        }
        return dataReceiveResBean;
    }
//---?????????-

//---???????????????????????????begin----------
    //??????pc????????????????????????id???????????????????????????
    @PostMapping("/loginSchoolPcCameraAccount")
    @ApiOperation(value = "????????????????????????pc?????????????????????id", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean loginSchoolPcCameraAccount( @ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
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
            //????????????????????????????????????????????????
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

    //??????????????????
    @PostMapping("/loginSchoolDoorVerifaceAccount")
    @ApiOperation(value = "???????????????????????????????????????????????????id?????????id???????????????", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean loginSchoolDoorVerifaceAccount( @ApiParam(value = "??????????????????????????????", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
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
        //??????????????????????????????
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
        //?????????????????????
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
        //????????????????????????????????????????????????
        ycVerifaceAccountService.createAccountCache(oneYcVerifaceAccountByCondition);
        ycVerifaceAccountService.setDoorDeviceCache(oneDoorDevice);
        res.setReturnCode("0000");
        res.setReturnMessage("Login Success");
        res.setAccountId(accountId);
        res.setSchoolId(schoolId);
        res.setSchoolName(schoolname);
        //??????????????????id???token
        String token = JwtUtil.createJWT(deviceId, "{}", null, -1);
        res.setToken(token);
        return res;
    }


    //??????pc???????????????????????????
    @PostMapping("/schoolPcCameraAccountFindDevice")
    @ApiOperation(value = "????????????????????????pc?????????id???????????????????????????", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcCameraAccountFindDevice(@ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceAccount ycVerifaceAccount){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String accountId = ycVerifaceAccount.getAccountId();
        if (StrUtil.isEmpty(accountId)){
            res.setReturnCode("9999");
            res.setReturnMessage("accountId wrong!");
            return res;
        }
        YcVerifaceAccount account = ycVerifaceAccountService.findYcVerifaceAccountById(accountId);
        if (account==null){
            //????????????????????????????????????????????????
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
        //???????????????????????????
        return res;
    }

    //??????PC?????????????????????
    @PostMapping("/schoolPcUpdateCameraDevice")
    @ApiOperation(value = "????????????????????????pc???????????????????????????", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcUpdateCameraDevice(@ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice){
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

    //??????PC????????????
    @PostMapping("/schoolPcHeartbeat")
    @ApiOperation(value = "????????????????????????pc??????", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean schoolPcHeartbeat(@ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceHeartbeatBean ycVerifaceHeartbeatBean){
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

    //?????????????????????????????????????????????????????????
    @PostMapping("/ycVerifaceDataReceive")
    @ApiOperation(value = "?????????????????????????????????", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean ycVerifaceDataReceive( @ApiParam(value = "??????????????????", required = true) @RequestBody KqOriginalDataTest kqOriginalDataTest){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        //??????????????????
        if (StrUtil.isEmpty(kqOriginalDataTest.getSchoolId())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????schoolId??????");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getDeviceId())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????deviceId??????");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedTime())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????capturedTime??????");
            return res;
        }
        if (StrUtil.isEmpty(kqOriginalDataTest.getCapturedImage())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????capturedImage??????");
            return res;
        }
        //???????????????????????????????????????
        if (StrUtil.isEmpty(kqOriginalDataTest.getUserId())){
            //?????????
            kqOriginalDataTest.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER);
        }else {
            //?????????
            kqOriginalDataTest.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL);
        }
        //??????????????????
        KqOriginalDataTest filled = kqOriginalDataService.fillOtherInfo(kqOriginalDataTest);
        if (filled==null){
            //?????????????????????
            res.setReturnCode("0000");
            res.setReturnMessage("????????????");
            return res;
        }
        //???????????????????????????
        KqOriginalData kqOriginalData = kqOriginalDataService.saveYcVerifaceOriginalData(filled);

        //????????????
        kqOriginalDataService.pushMassageToTerminalForStrangeTEST(kqOriginalData);

        //?????????????????????
        res.setReturnCode("0000");
        res.setReturnMessage("????????????");
        return res;
    }

    //??????????????????????????????????????????????????????????????????
    @PostMapping("/ycVerifaceDataBatchReceive")
    @ApiOperation(value = "????????????????????????????????????????????????", notes = "", response = YcVerifaceAccount.class)
    public YcDataReceiveResBean ycVerifaceDataBatchReceive( @ApiParam(value = "??????????????????", required = true) @RequestBody YcVerifaceOriginDataBatchReceiveBean ycVerifaceOriginDataBatchReceiveBean){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        //??????????????????
        if (StrUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getSchoolID())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????schoolId??????");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getCatchDate())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????catchDate??????");
            return res;
        }
        if (CollectionUtil.isEmpty(ycVerifaceOriginDataBatchReceiveBean.getBeans())){
            res.setReturnCode("2999");
            res.setReturnMessage("????????????beans??????");
            return res;
        }
        //?????????????????????????????????????????????
        kqOriginalDataService.saveBatchDatoToRedis(ycVerifaceOriginDataBatchReceiveBean);
        res.setReturnCode("0000");
        res.setReturnMessage("????????????");
        return res;


    }

    //????????????????????????????????????
    @PostMapping("/ycVerifaceDoorPullPeopleChange")
    @ApiOperation(value = "???????????????????????????????????? ", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean ycVerifaceDoorPullPeopleChange(@ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice,HttpServletRequest request){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("??????????????????");
            return res;
        }
        //System.out.println(token);
        String deviceSign =null;
        try{
            Claims claims = JwtUtil.parseJWT(token);
            deviceSign = claims.getId();
        }catch (Exception e){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("??????token??????");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode("8999");
            res.setReturnMessage("deviceId ????????????");
            return res;
        }
        if (StrUtil.isEmpty(deviceSign)||!deviceSign.equals(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("deviceId???????????????????????????????????????");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getSchoolId())){
            res.setReturnCode("8999");
            res.setReturnMessage("schoolId ????????????");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getChangeCode())){
            res.setReturnCode("8999");
            res.setReturnMessage("changeCode ????????????");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getCacheKey())){
            res.setReturnCode("8999");
            res.setReturnMessage("cacheKey ????????????");
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
            res.setReturnMessage("????????????????????????????????????");
            return res;
        }
        YcVerifaceDevice ycVerifaceDevice1 = deviceList.get(0);
        ycVerifaceDevice1.setChangeCode(ycVerifaceDevice.getChangeCode());
        ycVerifaceDevice1.setCacheKey(ycVerifaceDevice.getCacheKey());
        YcVerifaceDoorChangeResBean resBean = ycVerifaceDeviceService.ycVerifaceDoorPullPeopleChange(ycVerifaceDevice1);
        if (resBean==null){
            res.setPeopleListChangBean(null);
            res.setReturnCode("9999");
            res.setReturnMessage("???????????????????????????????????????");
            return res;
        }
        res.setPeopleListChangBean(resBean);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }

    //??????????????????????????????
    @PostMapping("/ycVerifaceDoorPullResult")
    @ApiOperation(value = "???????????????????????????????????? ", notes = "", response = YcVerifaceDevice.class)
    public YcDataReceiveResBean ycVerifaceDoorPullResult(@ApiParam(value = "????????????????????????", required = true)  @RequestBody YcVerifaceDevice ycVerifaceDevice,HttpServletRequest request){
        YcDataReceiveResBean res = new YcDataReceiveResBean();
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("??????????????????");
            return res;
        }
        //System.out.println(token);
        String deviceSign =null;
        try{
            Claims claims = JwtUtil.parseJWT(token);
            deviceSign = claims.getId();
        }catch (Exception e){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("??????token??????");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode("8999");
            res.setReturnMessage("deviceId ????????????");
            return res;
        }
        if (StrUtil.isEmpty(deviceSign)||!deviceSign.equals(ycVerifaceDevice.getDeviceId())){
            res.setReturnCode(String.valueOf(Constant.HAVEN_LOGIN));
            res.setReturnMessage("deviceId???????????????????????????????????????");
            return res;
        }
        if (StrUtil.isEmpty(ycVerifaceDevice.getSchoolId())){
            res.setReturnCode("8999");
            res.setReturnMessage("schoolId ????????????");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getCacheKey())){
            res.setReturnCode("8999");
            res.setReturnMessage("cacheKey ????????????");
            return res;
        }

        if (StrUtil.isEmpty(ycVerifaceDevice.getPullResult())){
            res.setReturnCode("8999");
            res.setReturnMessage("pullResult ????????????");
            return res;
        }
        //????????????
        ycVerifaceDeviceService.checkPullResultAndAdjustCache(ycVerifaceDevice);
        res.setReturnCode("0000");
        res.setReturnMessage("succee");
        return res;
    }



    //---???????????????????????????end------------
}
