package com.yice.edu.cn.bmp.service.visitorManage;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.bmp.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.bmp.feignClient.visitorManage.VisitorFeign;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.bmp.service.ycVeriface.YcVerifaceDeviceService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myParentId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class VisitorService {
    @Autowired
    private VisitorFeign visitorFeign;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqZyPhotoCheckService kqZyPhotoCheckService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ParentService parentService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    private final static Logger logger = LoggerFactory.getLogger(VisitorService.class);

    @PostMapping("/saveVisitorApplyRecords")
    @ApiOperation(value = "?????????????????????????????????????????????)")
    public ResponseJson saveVisitorApplyRecords(
            @ApiParam(value = "????????????", required = true)
            @RequestBody Visitor visitor) {
        //?????????????????????
        Student student = studentService.findStudentById(myStudentId());
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(student.getClassesId());
        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
        //????????????
        ParentStudent parentStudent = new ParentStudent();
        parentStudent.setParentId(myParentId());
        parentStudent.setStudentId(myStudentId());
        List<ParentStudentFile> bs = parentService.getBoundStudentListInCenter(parentStudent);
        ParentStudentFile psf = bs.stream().filter(c -> c.getName().equals(student.getName())).findFirst().get();
        //????????? ????????????
        visitor.setAuditor(teacher.getName());
        visitor.setAuditorId(teacher.getId());
        visitor.setApplyStatus("2");
        visitor.setStudentId(myStudentId());
        visitor.setParentId(myParentId());
        visitor.setVisitorType("0");

        if (visitor.getVisitorWay().equals("0")) {//??????
            logger.info("?????????????????????");
            visitor.setVisitorCard("410222198706134038");
            Pattern pattern = Pattern.compile("^1\\d{10}$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorTel())) {
                if (!pattern.matcher(visitor.getVisitorTel()).matches()) {
                    return new ResponseJson("????????????????????????11???");
                }
            }
            /*//?????????????????????????????????????????????*/
            int hasDevice = 0;
            //??????????????????
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(visitor.getSchoolId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if (kqSchool == null){
                logger.info(visitor.getSchoolId()+"?????????????????????");
            }else {
                hasDevice = hasDevice +1;
            }
            //???????????????????????????????????????
            YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
            ycVerifaceDevice.setSchoolId(visitor.getSchoolId());
            long ycVerifaceDeviceCount = ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
            if (ycVerifaceDeviceCount==0){
                logger.info(visitor.getSchoolId()+"????????????????????????");
            }else {
                hasDevice = hasDevice +2;
            }
            if (hasDevice == 0){
                return new ResponseJson(false, "???????????????????????????");
            }

           /* //???????????????
            String code = codeCache.get(re.getSession().getId());
            if (!code.equalsIgnoreCase(visitor.getCode())) {
                return new ResponseJson(false, "???????????????");
            }
            codeCache.remove(re.getSession().getId());*/
            DataReceiveResBean res =new DataReceiveResBean();
            if (hasDevice ==1){
                logger.info("????????????????????????????????????????????????");
                res = zyPhotoCheck(res,kqSchool,visitor);
            }else if (hasDevice ==2){
                logger.info("??????????????????????????????????????????");
                res = ycPhotoCheck(res,visitor);
            }else if(hasDevice == 3){
                logger.info("???????????????????????????,????????????????????????");
                //?????????????????????????????????
                DataReceiveResBean dataReceiveResBean = zyPhotoCheck(res, kqSchool, visitor);
                DataReceiveResBean dataReceiveResBean1 = ycPhotoCheck(res, visitor);
                if (!dataReceiveResBean.getBean().get("state").equals("1")||!dataReceiveResBean1.getBean().get("state").equals("1")){
                    res.setReturnCode("9999");
                    HashMap bean = new HashMap<String ,String>();
                    bean.put("state","9");
                    res.setBean(bean);
                }
            }






            if (!res.getReturnCode().equals("0000")||!res.getBean().get("state").equals("1")) {//tap bmp?????? ??????????????????
                return new ResponseJson(0, "?????????????????????");
            } else {
                try {
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(visitor.getVisitorImg());
                    String path = QiniuUtil.uploadFile(file, Constant.Upload.VISITOR_IMAGE);
                    if (!StrUtil.isEmpty(path)) {
                        visitor.setVisitorImg(path);
                        //???????????????
                        String[] teacherList = new String[]{teacher.getId()};
                        Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"????????????","????????????????????????????????????????????????",Extras.VISITOR_NOTICE);
                        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                        System.out.println("????????????????????????????????????????????????");
                        //?????????????????? ??????
                        visitor.setRelationShip(psf.getRelationship());
                        visitor.setStudentName(psf.getName());
                        visitorFeign.saveVisitor(visitor);
                    } else {
                        return new ResponseJson("???????????????");
                    }
                } catch (Exception e) {
                    System.out.println("????????????");
                    e.printStackTrace();
                }
            }
        } else if (visitor.getVisitorWay().equals("1")) {//??????
            KqSchoolInit kqSchoolInit = new KqSchoolInit();
            kqSchoolInit.setCustId(visitor.getSchoolId());
            KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
            if(kqSchool==null){
                return  new ResponseJson(false,"???????????????????????????");
            }
            Pattern pattern = Pattern.compile("^1\\d{10}$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorTel())) {
                if (!pattern.matcher(visitor.getVisitorTel()).matches()) {
                    return new ResponseJson(false,"????????????????????????11???");
                }
            }
            Pattern pattern1 = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
            if (!StringUtil.isNullOrEmpty(visitor.getVisitorCard())) {
                if (!pattern1.matcher(visitor.getVisitorCard()).matches()) {
                    return new ResponseJson(false,"????????????????????????18???");
                }
            }
            //???????????????
            String[] teacherList = new String[]{teacher.getId()};
            Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"????????????","????????????????????????????????????????????????",Extras.VISITOR_NOTICE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            System.out.println("????????????????????????????????????????????????");
            //?????????????????? ??????
            visitor.setRelationShip(psf.getRelationship());
            visitor.setStudentName(psf.getName());
            visitorFeign.saveVisitor(visitor);
        }
        return new ResponseJson("1", "????????????");
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
        logger.info("H5??????????????????");
        res = kqZyPhotoCheckService.zyPhotoCheck(isProd, kqSchool, visitor.getVisitorImg());
        logger.info("H5??????????????????");
        return res;
    }


}
