package com.yice.edu.cn.osp.service.xw.visitorManage;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDeviceGroupFeign;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqSchoolInitFeign;
import com.yice.edu.cn.osp.feignClient.xw.visitorManage.VisitorFeign;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.kq.KqZyPhotoCheckService;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class VisitorService {
    @Autowired
    private VisitorFeign visitorFeign;
    @Autowired
    private KqSchoolInitFeign kqSchoolInitFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private KqZyPhotoCheckService kqZyPhotoCheckService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqDeviceGroupFeign kqDeviceGroupFeign;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @CreateCache(name = Constant.Redis.H5_GETIMAGE_CODE, expire = Constant.Redis.H5_CODE_TIMEOUT * 60)
    private Cache<String, String> codeCache;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    private final static Logger logger = LoggerFactory.getLogger(VisitorService.class);


    public Visitor creatQRCode(Visitor visitor) {
        return visitorFeign.creatQRCode(visitor);
    }

    public Visitor findVisitorById(String id) {
        return visitorFeign.findVisitorById(id);
    }

    public Visitor saveVisitor(Visitor visitor) {
        return visitorFeign.saveVisitor(visitor);
    }

    public List<Visitor> findVisitorListByCondition(Visitor visitor) {
        return visitorFeign.findVisitorListByCondition(visitor);
    }

    public Visitor findOneVisitorByCondition(Visitor visitor) {
        return visitorFeign.findOneVisitorByCondition(visitor);
    }

    public long findVisitorCountByCondition(Visitor visitor) {
        return visitorFeign.findVisitorCountByCondition(visitor);
    }

    public void updateVisitor(Visitor visitor) {
        visitorFeign.updateVisitor(visitor);
    }

    public void deleteVisitor(String id) {
        visitorFeign.deleteVisitor(id);
    }

    public void deleteVisitorByCondition(Visitor visitor) {
        visitorFeign.deleteVisitorByCondition(visitor);
    }

    public ResponseJson saveVisitorApplyRecords(Visitor visitor , HttpServletRequest re) {
        visitor.setApplyStatus("2");
        visitor.setVisitorType("1");
        visitor.setAuditor(visitor.getTeacherName());
        visitor.setAuditorId(visitor.getTeacherId());
        visitor.setSchoolId(SimpleCryptoKit.decrypt(visitor.getSchoolId()));
        //陌生访客 提交表单 传教师 姓名 id
        if (visitor.getVisitorWay().equals("0")) {
            logger.info("H5刷脸开始");
            visitor.setVisitorCard("410222198706134038");
            Pattern pattern = Pattern.compile("^1\\d{10}$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorTel())) {
                if (!pattern.matcher(visitor.getVisitorTel()).matches()) {
                    return new ResponseJson(false, "联系人号码必须为11位");
                }
            }
            int hasDevice = 0;
            //判断学校用哪种出入校门禁设备。
            //获得本校密钥
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(visitor.getSchoolId());
            KqSchoolInit kqSchool = kqSchoolInitFeign.findOneKqSchoolInitByCondition(kqSchoolInit);
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
                return new ResponseJson(false, "请检查设备是否配置");
            }

            //验证码校验
            String code = codeCache.get(re.getSession().getId());
            if (!code.equalsIgnoreCase(visitor.getCode())) {
                return new ResponseJson(false, "验证码错误");
            }
            codeCache.remove(re.getSession().getId());
            DataReceiveResBean res =new DataReceiveResBean();
            if (hasDevice ==1){
                logger.info("有中移门禁设备，没有亿策门禁设备");
                res = zyPhotoCheck(res,kqSchool,visitor);
            }else if (hasDevice ==2){
                logger.info("没有中移设备，有亿策门禁设备");
                res = ycPhotoCheck(res,visitor);
            }else if(hasDevice == 3){
                logger.info("两个厂家的设备都有,两家都通过才可以");
                //找出校门口分组用的什么
                DataReceiveResBean dataReceiveResBean = zyPhotoCheck(res, kqSchool, visitor);
                DataReceiveResBean dataReceiveResBean1 = ycPhotoCheck(res, visitor);
                if (!dataReceiveResBean.getBean().get("state").equals("1")||!dataReceiveResBean1.getBean().get("state").equals("1")){
                    res.setReturnCode("9999");
                    HashMap bean = new HashMap<String ,String>();
                    bean.put("state","9");
                    res.setBean(bean);
                }
            }

            if (!res.getReturnCode().equals("0000")||!res.getBean().get("state").equals("1")) {
                return new ResponseJson(1, "校验不通过,请重新上传头像");
            }
            try {
                MultipartFile file = Base64DecodeMultipartFile.base64Convert(visitor.getVisitorImg());
                String path = QiniuUtil.uploadFile(file, Constant.Upload.VISITOR_IMAGE);
                if (!StrUtil.isEmpty(path)) {
                    visitor.setVisitorImg(path);
                    visitorFeign.saveVisitor(visitor);
                    //System.out.println(res);
                    //推送教师
                    String[] auditiorList = new String[]{visitor.getAuditorId()};
                    Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(auditiorList, "访客审批", "有人向你申请到校访问，快去看看吧！", Extras.VISITOR_NOTICE);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    //System.out.println("有人向你申请到校访问，快去看看吧");
                } else {
                    return new ResponseJson(false, "请上传图片");
                }
            } catch (Exception e) {
                //System.out.println("上传出错");
                e.printStackTrace();
            }
        } else if (visitor.getVisitorWay().equals("1")) {
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(visitor.getSchoolId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool == null) {
                return new ResponseJson(false, "请检查设备是否配置");
            }
            Pattern pattern = Pattern.compile("^1\\d{10}$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorTel())) {
                if (!pattern.matcher(visitor.getVisitorTel()).matches()) {
                    return new ResponseJson(false, "联系人号码必须为11位");
                }
            }
            Pattern pattern1 = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorCard())) {
                if (!pattern1.matcher(visitor.getVisitorCard()).matches()) {
                    return new ResponseJson(false, "身份证号码必须为18位");
                }
            }
            String code = codeCache.get(re.getSession().getId());
            if (!(code.equalsIgnoreCase(visitor.getCode()))) {
                return new ResponseJson(false, "验证码错误");
            }
            codeCache.remove(re.getSession().getId());
            visitorFeign.saveVisitor(visitor);
            String[] auditiorList = new String[]{visitor.getAuditorId()};
            Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(auditiorList, "访客审批", "有人向你申请到校访问，快去看看吧！", Extras.VISITOR_NOTICE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            //System.out.println("有人向你申请到校访问，快去看看吧！");
        }
        return new ResponseJson(0, "申请成功");
    }

    //验证码
    public void sendImg(HttpServletRequest req, HttpServletResponse res) {
        try {
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 150);
            codeCache.put(req.getSession().getId(), lineCaptcha.getCode());
            //System.out.println(lineCaptcha.getCode());
           // System.out.println(req.getSession().getId());
            OutputStream outputStream = res.getOutputStream();
            lineCaptcha.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataReceiveResBean ycPhotoCheck(DataReceiveResBean res,Visitor visitor){

        List<YcEnterBean> regist = new ArrayList<>();
        YcEnterBean ycEnterBean = new YcEnterBean();
        String userid = visitor.getVisitorName()+"MyTel"+visitor.getVisitorTel();
        ycEnterBean.setUserID(userid);
        logger.info(visitor.getVisitorImg());
        ycEnterBean.setImg_base64(visitor.getVisitorImg().split(",")[1]);
        regist.add(ycEnterBean);
        String ycres = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ADD_MEMBER, visitor.getSchoolId(), regist);
        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(ycres, YcVerifaceResBean.class);
        List<Object> beans1 = ycVerifaceResBean.getBeans();
        YcVerifacePersonBean person = JSON.parseObject(beans1.get(0).toString(), YcVerifacePersonBean.class);
        if (person.getResult().equals("success")) {
            res.setReturnCode("0000");
            HashMap bean = new HashMap<String ,String>();
            bean.put("state","1");
            res.setBean(bean);
        }else if (person.getResult().equals("this user is already existed!") ){
            ycres = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.EDIT_MEMBER, visitor.getSchoolId(), regist);
            YcVerifaceResBean editYcVerifaceResBean = JSON.parseObject(ycres, YcVerifaceResBean.class);
            List<Object> beans2 = editYcVerifaceResBean.getBeans();
            YcVerifacePersonBean person1 = JSON.parseObject(beans2.get(0).toString(), YcVerifacePersonBean.class);
            if (person1.getResult().equals("success")) {
                res.setReturnCode("0000");
                HashMap bean = new HashMap<String ,String>();
                bean.put("state","1");
                res.setBean(bean);
            }else {
                res.setReturnCode("9999");
                HashMap bean = new HashMap<String ,String>();
                bean.put("state","-1");
                res.setBean(bean);
            }
        }else {
            res.setReturnCode("9999");
            HashMap bean = new HashMap<String ,String>();
            bean.put("state","-1");
            res.setBean(bean);
        }

        return res;
    }

    public DataReceiveResBean zyPhotoCheck(DataReceiveResBean res,KqSchoolInit kqSchool,Visitor visitor){
        logger.info("H5刷脸请求中移");
        res = kqZyPhotoCheckService.zyPhotoCheck(isProd, kqSchool, visitor.getVisitorImg());
        logger.info("H5刷脸请求结束");
        return res;
    }



}
