package com.yice.edu.cn.osp.service.xw.kq;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcOriginalDataBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOriginDataBatchReceiveBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalDataTest;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuStatusChange;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceAlarmReceiverFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceBlacklistFeign;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDeviceGroupFeign;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDevicePersonFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqOriginalDataFeign;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.StuStatusChangeFeign;
import com.yice.edu.cn.osp.feignClient.xw.visitorManage.VisitorFeign;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifacePersonEnterService;
import com.yice.edu.cn.osp.service.xw.workerKq.KqWorkerDailyService;
import org.mvel2.util.Make;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class KqOriginalDataService {
    private static ExecutorService executorService;
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;
    @Autowired
    private KqDevicePersonFeign kqDevicePersonFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private VisitorFeign visitorFeign;
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @Autowired
    private YcVerifacePersonEnterService ycVerifacePersonEnterService;
    @Autowired
    private KqDeviceGroupFeign kqDeviceGroupFeign;
    @Autowired
    private YcVerifaceBlacklistFeign ycVerifaceBlacklistFeign;
    @Autowired
    private YcVerifaceAlarmReceiverFeign ycVerifaceAlarmReceiverFeign;
    @Autowired
    private StuStatusChangeFeign stuStatusChangeFeign;

    @CreateCache(name = Constant.Redis.YC_VERIFACE_BACK_ONLINE_SCHOOL, expire = Constant.Redis.YC_VERIFACE_BACK_ONLINE_SCHOOL_TIMEOUT,timeUnit = TimeUnit.DAYS)
    private Cache<String, Set<String>> ycVerifaceBackOnlineSchoolList;
    @CreateCache(name = Constant.Redis.YC_VERIFACE_BACK_ONLINE_DATAS, expire = Constant.Redis.YC_VERIFACE_BACK_ONLINE_DATAS_TIMEOUT,timeUnit = TimeUnit.DAYS)
    private Cache<String, YcVerifaceOriginDataBatchReceiveBean> ycVerifaceBackOnlineDatas;

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    private final static Logger logger = LoggerFactory.getLogger(KqOriginalDataService.class);
    /*----------------------------------------------------------------------------------------------------------------*/

    public KqOriginalData findKqOriginalDataById(String id) {
        return kqOriginalDataFeign.findKqOriginalDataById(id);
    }

    public KqOriginalData saveKqOriginalData(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
    }

    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
    }

    public KqOriginalData findOneKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findOneKqOriginalDataByCondition(kqOriginalData);
    }

    public long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataCountByCondition(kqOriginalData);
    }

    public void updateKqOriginalData(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.updateKqOriginalData(kqOriginalData);
    }

    public void deleteKqOriginalData(String id) {
        kqOriginalDataFeign.deleteKqOriginalData(id);
    }

    public void deleteKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.deleteKqOriginalDataByCondition(kqOriginalData);
    }

    public KqDailyStatistical judgePunchStatusByrules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.judgePunchStatusByrules(kqOriginalData);
    }
    public KqDailyStatistical dayBasicRecords(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.dayBasicRecords(kqOriginalData);
    }
    public KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.inTimeCountByRules(kqOriginalData);
    }
    public boolean statusOfKqOriginalData(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.statusOfKqOriginalData(kqOriginalData);
    }
 /*----------------------?????????????????????------------------------------------------------------------------------------------------------------------*/
//---??????begin-----
    //????????????
public KqOriginalData saveOriginalDataByUserTypeTEXT(KqOriginalDataTest kqOriginalDataTest,String myKey){
    //????????????
    KqOriginalData kqOriginalData = new KqOriginalData();
    kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
    kqOriginalData.setReqId(kqOriginalDataTest.getReqId());
    kqOriginalData.setUserType(kqOriginalDataTest.getUserType());
    kqOriginalData.setCapturedImage(kqOriginalDataTest.getCapturedImage());
    if (kqOriginalData.getCapturedImage()!=null){
        String imgUrl = uploadImgUrl(kqOriginalData.getCapturedImage());
        kqOriginalData.setCapturedImage(imgUrl);
    }
    kqOriginalData.setCapturedTime(kqOriginalDataTest.getCapturedTime());
    kqOriginalData.setSchoolId(kqOriginalDataTest.getCustId());
    kqOriginalData.setDerectionFlag(kqOriginalDataTest.getDerectionFlag());
    kqOriginalData.setDeviceNo(kqOriginalDataTest.getDeviceNo());
    kqOriginalData.setDeviceType(kqOriginalDataTest.getDeviceType());
    kqOriginalData.setCapturedMessageType(kqOriginalDataTest.getCapturedMessageType());
    if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)){
        kqOriginalData.setUserId(AESUtil.decrypt(kqOriginalDataTest.getUserId(), myKey));//?????????
       // kqOriginalData.setUserId(kqOriginalDataTest.getUserId());//???????????????
    }
    //??????????????????
    KqDevicePerson kqDevicePerson =new KqDevicePerson();
    kqDevicePerson.setDeviceNo(kqOriginalData.getDeviceNo());
    kqDevicePerson.setSchoolId(kqOriginalData.getSchoolId());
    List<KqDevicePerson> kqDevicePersonList = kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
    List<KqDevicePerson> kqDeviceList =null;
    if (kqDevicePersonList!=null&&kqDevicePersonList.size()>0){
        kqDeviceList =   kqDevicePersonList.stream().filter( kqDevicePerson1 -> kqDevicePerson1.getDeviceName()!=null&&kqDevicePerson1.getGroupId()!=null&&kqDevicePerson1.getGroupName()!=null&&kqDevicePerson1.getGroupType()!=null).collect(Collectors.toList());
    }
    if (kqDeviceList!=null&&kqDeviceList.size()>0){
        kqOriginalData.setDeviceName(kqDeviceList.get(0).getDeviceName());
        kqOriginalData.setGroupName(kqDeviceList.get(0).getGroupName());
        kqOriginalData.setGroupId(kqDeviceList.get(0).getGroupId());
        kqOriginalData.setGroupType(kqDeviceList.get(0).getGroupType());
    }
    //?????????????????????????????????
    if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)){

        Teacher teacher = teacherFeign.findTeacherById(kqOriginalData.getUserId());
        if (teacher.getPersonType()!=null&&teacher.getPersonType()==2){
            kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF);
        };
        kqOriginalData.setName(teacher.getName());
        kqOriginalData.setPrsnAvtrUrlAddr(teacher.getImgUrl());
        kqOriginalData.setWorknumber(teacher.getWorkNumber());

    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&& (kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER))){
        //?????????????????????
        Student student = studentFeign.findStudentById(kqOriginalData.getUserId());
        /**/
        kqOriginalData.setStudentId(kqOriginalData.getUserId());
        kqOriginalData.setName(student.getName());
        kqOriginalData.setClassesNumber(student.getClassesNumber());
        kqOriginalData.setClassId(student.getClassesId());
        kqOriginalData.setGradeId(student.getGradeId());
        kqOriginalData.setGradeName(student.getGradeName());
        kqOriginalData.setPrsnAvtrUrlAddr(student.getImgUrl());
        kqOriginalData.setSeatNumber(student.getSeatNumber());
        if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")) {
            boolean isOk = statusOfKqOriginalData(kqOriginalData);
            //System.out.println(isOk);
            if (student.getNowStatus() != null && (student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT) || student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN))) {
                if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)) {
                    kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                } else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)) {
                    kqOriginalData.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE);//2??????
                }
            } else {
                kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
            }
        }
    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_VISITOR)){
        kqOriginalData.setName(AESUtil.decrypt(kqOriginalDataTest.getUserName(), myKey));//??????
        //kqOriginalData.setName(kqOriginalDataTest.getUserName());//???????????????
        String idCard =  kqOriginalData.getUserId();
        kqOriginalData.setVisiIdCard(idCard);
        if (idCard.equals("410222198706134038")){
            kqOriginalData.setVisiIdCard("--");
        }
        Visitor visitor = new Visitor();
        visitor.setApplyStatus("0");
        visitor.setVisitorCard(idCard);
        visitor.setVisitorName(kqOriginalData.getName());
        List<Visitor> visitors = visitorFeign.findVisitorListByCondition(visitor);
        //???????????????????????????
        if (visitors!=null&&visitors.size()>0){
            List<Visitor> vis = visitors.stream().filter( visitor1 ->
                    DateUtil.parse(kqOriginalData.getCapturedTime()).isAfterOrEquals(DateUtil.parse(visitor1.getStartTime())) && DateUtil.parse(kqOriginalData.getCapturedTime()).isBefore(DateUtil.parse(visitor1.getEndTime()))
            ).sorted(Comparator.comparing(Visitor::getCreateTime).reversed()).collect(Collectors.toList());
            if (vis!=null&&vis.size()>0){
                Visitor visitor2=vis.get(0);
                //???????????????
                if (visitor2.getStudentId()!=null&&!visitor2.getStudentId().equals("")){
                    Student student = studentFeign.findStudentById(visitor2.getStudentId());
                    String desc = student.getGradeName()+student.getClassesNumber()+"???"+student.getName()+"?????????";
                    kqOriginalData.setVisitorDesc(desc);
                }
            }
        }
        /**/
    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)){
        //?????????????????????????????????????????????
        kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER);
    }

    curSchoolYearService.setSchoolYearTerm(kqOriginalData,kqOriginalData.getSchoolId());
    kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
    return kqOriginalData;
}

    //?????????????????????????????????
    public void pushMassageToTerminalTEXT(KqOriginalData kqOriginalData) {
        if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)){
            //System.out.println(" //?????????????????????????????????");
            return;
        }
        //????????????????????????
        if (kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF)){
            //????????????
            KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
            kqWorkerDaily1.setKqDate(DateUtil.today());
            kqWorkerDaily1.setUserId(kqOriginalData.getUserId());
            List<KqWorkerDaily> kqDaily = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily1);
            if (kqDaily!=null||kqDaily.size()==1){
                KqWorkerDaily kqWorkerDaily = kqDaily.get(0);
                String capturedTime = kqOriginalData.getCapturedTime();
                kqWorkerDaily.setCaptureTime(capturedTime.substring(11, 16));
                kqWorkerDailyService.dailyRecords(kqWorkerDaily);
                //????????????
                String[] teacherArr = new String[]{kqOriginalData.getUserId()};
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherArr,"????????????","?????????????????????",Extras.XW_WORKER_CLOCK_IN_OUT);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
        }
        //????????????????????????
        else  if (kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER)){

            //??????????????????????????????
            if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")){
                //???????????????
                String[] studentList = new String[]{kqOriginalData.getStudentId()};
                Student student = new Student();
                student.setId(kqOriginalData.getStudentId());
                Student student1 = studentFeign.findStudentById(student.getId());
                //???????????????????????????
                if (kqOriginalData.getDerectionFlag().equals("0")){//0???1???-1?????????
                    final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"???????????????","?????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    //???????????????????????????
                    student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL);//????????????  ??????/??????/??????  1/2/3  ????????????
                    studentFeign.updateStudent(student);
                    //????????????????????????????????????
                    StuStatusChange sc= new StuStatusChange();
                    sc.setSchoolId(student1.getSchoolId());
                    sc.setStudentId(student1.getId());
                    sc.setStatus(student1.getNowStatus());
                    sc.setCreateDate(DateUtil.today());
                    sc.setCreateTime(DateUtil.now());
                    sc.setChangeTime(DateUtil.now());
                    sc.setClassId(student1.getClassesId());
                    stuStatusChangeFeign.saveStuStatusChange(sc);
                }else if (kqOriginalData.getDerectionFlag().equals("1")){
                    final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"???????????????","?????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    if (student1.getNowStatus()==null||!student1.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT)){
                        //???????????????????????????
                        student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);//????????????  ??????/??????/??????  1/3/2  ????????????
                        studentFeign.updateStudent(student);
                        //????????????????????????????????????
                        StuStatusChange sc= new StuStatusChange();
                        sc.setSchoolId(student1.getSchoolId());
                        sc.setStudentId(student1.getId());
                        sc.setStatus(student1.getNowStatus());
                        sc.setCreateDate(DateUtil.today());
                        sc.setCreateTime(DateUtil.now());
                        sc.setChangeTime(DateUtil.now());
                        sc.setClassId(student1.getClassesId());
                        stuStatusChangeFeign.saveStuStatusChange(sc);
                    }
                }
                //???????????????????????????
                boolean isNotClassTime=statusOfKqOriginalData(kqOriginalData);
                if (!isNotClassTime){
                    Student student2 = new Student();
                    student2.setClassesId(kqOriginalData.getClassId());
                    Pager pager= new Pager();
                    pager.setPaging(false);
                    pager.setIncludes("nowStatus");
                    student2.setPager(pager);
                    List<Student> students =studentFeign.findStudentListByCondition(student2);
                    List<Student> atSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL)).collect(Collectors.toList());
                    List<Student> noAtSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                    double d =0;
                    if (atSchoolStu!=null&&atSchoolStu.size()>0){
                        double atSchool = atSchoolStu.size();
                        double noAtSchool = 0;
                        if (noAtSchoolStu!=null&&noAtSchoolStu.size()>0){
                            noAtSchool = noAtSchoolStu.size();
                        }
                        double all = atSchool + noAtSchool;
                        d = atSchool/all;
                    }
                    //?????????????????????
                    if (d>=0.5) {
                        //???????????????
                        TeacherClasses teacherClasses = new TeacherClasses();
                        teacherClasses.setClassesId(kqOriginalData.getClassId());
                        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
                        String[] teacherList = new String[]{teacher.getId()};
                        if (kqOriginalData.getDerectionFlag().equals("0")) {//0???1???-1?????????
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"???????????????","??????????????????????????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                        }else if (kqOriginalData.getDerectionFlag().equals("1")){
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"???????????????","??????????????????????????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                        }
                    }
                }else {
                   // ???????????????????????????????????????
                }
            }else {
                // ?????????????????????
            }
        }
    }

    //?????????????????????
    public String uploadImgUrl(String image) {
        String path = "";
        //base64???MultipartFile file
        byte[] decode = Base64.decode(image);
        String encode = cn.hutool.core.codec.Base64.encode(decode);
        MultipartFile file = Base64DecodeMultipartFile.base64Convert("data:image/jpeg;base64,"+encode);
            try{
                path  = QiniuUtil.uploadFile(file, Constant.Upload.ZY_CAPTURED_IMAGE);
            }catch (Exception e){
                e.printStackTrace();
            }
        return path;
    }
//---??????end-----
//------------?????????begin--------------
//????????????
    public KqOriginalData saveOriginalDataByUserTypeForStrangeTEST(KqOriginalDataTest kqOriginalDataTest,String myKey){
    //????????????
    KqOriginalData kqOriginalData = new KqOriginalData();
    kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
    kqOriginalData.setReqId(kqOriginalDataTest.getReqId());
    kqOriginalData.setUserType(kqOriginalDataTest.getUserType());
    kqOriginalData.setCapturedImage(kqOriginalDataTest.getCapturedImage());
    if (kqOriginalData.getCapturedImage()!=null){
        String imgUrl = uploadImgUrl(kqOriginalData.getCapturedImage());
        kqOriginalData.setCapturedImage(imgUrl);
    }
    kqOriginalData.setCapturedTime(kqOriginalDataTest.getCapturedTime());
    kqOriginalData.setSchoolId(kqOriginalDataTest.getCustId());
    kqOriginalData.setDerectionFlag(kqOriginalDataTest.getDerectionFlag());
    kqOriginalData.setDeviceNo(kqOriginalDataTest.getDeviceNo());
    kqOriginalData.setDeviceType(kqOriginalDataTest.getDeviceType());
    kqOriginalData.setCapturedMessageType(kqOriginalDataTest.getCapturedMessageType());
    if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)){
       // kqOriginalData.setUserId(AESUtil.decrypt(kqOriginalDataTest.getUserId(), myKey));//?????????
         kqOriginalData.setUserId(kqOriginalDataTest.getUserId());//???????????????
    }

        //??????????????????
        KqDevicePerson kqDevicePerson =new KqDevicePerson();
        kqDevicePerson.setDeviceNo(kqOriginalData.getDeviceNo());
        kqDevicePerson.setSchoolId(kqOriginalData.getSchoolId());
        List<KqDevicePerson> kqDevicePersonList = kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
        List<KqDevicePerson> kqDeviceList =null;
        if (kqDevicePersonList!=null&&kqDevicePersonList.size()>0){
            kqDeviceList =   kqDevicePersonList.stream().filter( kqDevicePerson1 -> kqDevicePerson1.getDeviceName()!=null&&kqDevicePerson1.getGroupId()!=null&&kqDevicePerson1.getGroupName()!=null&&kqDevicePerson1.getGroupType()!=null).collect(Collectors.toList());
        }
        if (kqDeviceList!=null&&kqDeviceList.size()>0){
            kqOriginalData.setDeviceName(kqDeviceList.get(0).getDeviceName());
            kqOriginalData.setGroupName(kqDeviceList.get(0).getGroupName());
            kqOriginalData.setGroupId(kqDeviceList.get(0).getGroupId());
            kqOriginalData.setGroupType(kqDeviceList.get(0).getGroupType());
        }

    //?????????????????????????????????
    if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)){

        Teacher teacher = teacherFeign.findTeacherById(kqOriginalData.getUserId());
        if (teacher.getPersonType()!=null&&teacher.getPersonType()==2){
            kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF);
        };
        kqOriginalData.setName(teacher.getName());
        kqOriginalData.setPrsnAvtrUrlAddr(teacher.getImgUrl());
        kqOriginalData.setWorknumber(teacher.getWorkNumber());

    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&& (kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER))){
        //?????????????????????
        Student student = studentFeign.findStudentById(kqOriginalData.getUserId());
        /**/
        kqOriginalData.setStudentId(kqOriginalData.getUserId());
        kqOriginalData.setName(student.getName());
        kqOriginalData.setClassesNumber(student.getClassesNumber());
        kqOriginalData.setClassId(student.getClassesId());
        kqOriginalData.setGradeId(student.getGradeId());
        kqOriginalData.setGradeName(student.getGradeName());
        kqOriginalData.setPrsnAvtrUrlAddr(student.getImgUrl());
        kqOriginalData.setSeatNumber(student.getSeatNumber());
        if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")){
            boolean isOk=statusOfKqOriginalData(kqOriginalData);
            //System.out.println(isOk);
            if (student.getNowStatus()!=null&&(student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT)||student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN))){
                if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)) {
                    kqOriginalData.setPassStatus(isOk?Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL:Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                }else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)){
                    kqOriginalData.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE);//2??????
                }
            }else {
                kqOriginalData.setPassStatus(isOk?Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL:Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
            }
        }
    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_VISITOR)){
       // kqOriginalData.setName(AESUtil.decrypt(kqOriginalDataTest.getUserName(), myKey));//??????
        kqOriginalData.setName(kqOriginalDataTest.getUserName());//???????????????
        String idCard =  kqOriginalData.getUserId();
        kqOriginalData.setVisiIdCard(idCard);
        if (idCard.equals("410222198706134038")){
            kqOriginalData.setVisiIdCard("--");
        }
        Visitor visitor = new Visitor();
        visitor.setApplyStatus("0");
        visitor.setVisitorCard(idCard);
        visitor.setVisitorName(kqOriginalData.getName());
        List<Visitor> visitors = visitorFeign.findVisitorListByCondition(visitor);
        //???????????????????????????
        if (visitors!=null&&visitors.size()>0){
            List<Visitor> vis = visitors.stream().filter( visitor1 ->
                    DateUtil.parse(kqOriginalData.getCapturedTime()).isAfterOrEquals(DateUtil.parse(visitor1.getStartTime())) && DateUtil.parse(kqOriginalData.getCapturedTime()).isBefore(DateUtil.parse(visitor1.getEndTime()))
            ).sorted(Comparator.comparing(Visitor::getCreateTime).reversed()).collect(Collectors.toList());
            if (vis!=null&&vis.size()>0){
                Visitor visitor2=vis.get(0);
                //???????????????
                if (visitor2.getStudentId()!=null&&!visitor2.getStudentId().equals("")){
                    Student student = studentFeign.findStudentById(visitor2.getStudentId());
                    String desc = student.getGradeName()+student.getClassesNumber()+"???"+student.getName()+"?????????";
                    kqOriginalData.setVisitorDesc(desc);
                }
            }
        }
        /**/
    }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)){
        //?????????????????????????????????????????????
        kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER);
    }

    curSchoolYearService.setSchoolYearTerm(kqOriginalData,kqOriginalData.getSchoolId());
    kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
    return kqOriginalData;
}


    //?????????????????????????????????
    public void pushMassageToTerminalForStrangeTEST(KqOriginalData kqOriginalData) {
        if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BLACKPERSON)){
            ycVerifaceAlarmReceiverFeign.sendPushToAlarmReceiver(kqOriginalData.getSchoolId(),kqOriginalData.getUserType());
            return;
        }
        //????????????????????????
        if (kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF)){
            //????????????
            KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
            kqWorkerDaily1.setKqDate(DateUtil.today());
            kqWorkerDaily1.setUserId(kqOriginalData.getUserId());
            List<KqWorkerDaily> kqDaily = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily1);
            if (kqDaily!=null&&kqDaily.size()==1){
                KqWorkerDaily kqWorkerDaily = kqDaily.get(0);
                String capturedTime = kqOriginalData.getCapturedTime();
                kqWorkerDaily.setCaptureTime(capturedTime.substring(11, 16));
                kqWorkerDailyService.dailyRecords(kqWorkerDaily);
                //????????????
                String[] teacherArr = new String[]{kqOriginalData.getUserId()};
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherArr,"????????????","?????????????????????",Extras.XW_WORKER_CLOCK_IN_OUT);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
        }
        //????????????????????????
        else  if (kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER)){

            //??????????????????????????????
            if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")){
                //???????????????
                String[] studentList = new String[]{kqOriginalData.getStudentId()};
                Student student = new Student();
                student.setId(kqOriginalData.getStudentId());
                student.setSchoolId(kqOriginalData.getSchoolId());
                Student student1 = studentFeign.findStudentById(student.getId());
                //???????????????????????????
                if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)){//0???1???-1?????????
                    final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"???????????????","?????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    //???????????????????????????
                    student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL);//????????????  ??????/??????/??????  1/2/3  ????????????
                    studentFeign.updateStudent(student);
                    //????????????????????????????????????
                    StuStatusChange sc= new StuStatusChange();
                    sc.setSchoolId(student.getSchoolId());
                    sc.setStudentId(student.getId());
                    sc.setStatus(student.getNowStatus());
                    sc.setCreateDate(DateUtil.today());
                    sc.setCreateTime(DateUtil.now());
                    sc.setChangeTime(DateUtil.now());
                    sc.setClassId(student1.getClassesId());
                    stuStatusChangeFeign.saveStuStatusChange(sc);
                }else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)){
                    final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"???????????????","?????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    if (student1.getNowStatus()==null||!student1.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT)){
                        //????????????????????????????????????????????????
                        student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);// ?????????????????? "0"??????;"1"??????(??????);"2"??????(??????);"3"??????
                        studentFeign.updateStudent(student);
                        //????????????????????????????????????
                        StuStatusChange sc= new StuStatusChange();
                        sc.setSchoolId(student.getSchoolId());
                        sc.setStudentId(student.getId());
                        sc.setStatus(student.getNowStatus());
                        sc.setCreateDate(DateUtil.today());
                        sc.setCreateTime(DateUtil.now());
                        sc.setChangeTime(DateUtil.now());
                        sc.setClassId(student1.getClassesId());
                        stuStatusChangeFeign.saveStuStatusChange(sc);
                    }
                }
                //???????????????????????????
                boolean isNotClassTime=statusOfKqOriginalData(kqOriginalData);
                if (!isNotClassTime){
                    Student student2 = new Student();
                    student2.setClassesId(kqOriginalData.getClassId());
                    Pager pager= new Pager();
                    pager.setPaging(false);
                    pager.setIncludes("nowStatus");
                    student2.setPager(pager);
                    List<Student> students =studentFeign.findStudentListByCondition(student2);
                    List<Student> atSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL)).collect(Collectors.toList());
                    List<Student> noAtSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                    double d =0;
                    if (atSchoolStu!=null&&atSchoolStu.size()>0){
                        double atSchool = atSchoolStu.size();
                        double noAtSchool = 0;
                        if (noAtSchoolStu!=null&&noAtSchoolStu.size()>0){
                            noAtSchool = noAtSchoolStu.size();
                        }
                        double all = atSchool + noAtSchool;
                        d = atSchool/all;
                    }
                    //?????????????????????
                    if (d>=0.5) {
                        //???????????????
                        TeacherClasses teacherClasses = new TeacherClasses();
                        teacherClasses.setClassesId(kqOriginalData.getClassId());
                        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
                        String[] teacherList = new String[]{teacher.getId()};
                        if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)) {//0???1???-1?????????
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"???????????????","??????????????????????????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                        }else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)){
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"???????????????","??????????????????????????????????????????????????????",Extras.XW_STUDENT_IN_OUT_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                        }
                    }
                }else {
                    // ???????????????????????????????????????
                }
            }else {
                // ?????????????????????
            }
        }
    }

//------------?????????end--------------
/*--------------------------------------------------??????????????????-------------------------------------------------------------------------*/

    public KqOriginalDataTest fillOtherInfo( KqOriginalDataTest kt){
        if (kt.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)){
            //????????????
            kt.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER);
            //return kt;
        }else {
            YcVerifacePersonEnter enter = new YcVerifacePersonEnter();
            enter.setSchoolId(kt.getSchoolId());
            enter.setPersonId(kt.getUserId());
            //????????????id?????????????????????????????????????????????????????????????????????
            List<YcVerifacePersonEnter> enters = ycVerifacePersonEnterService.findYcVerifacePersonEnterListByCondition(enter);
            if (CollectionUtil.isNotEmpty(enters)){
                kt.setUserType(enters.get(0).getPersonType());
            }else {
                kt.setUserType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER);
                kt.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER);

                //??????????????????????????????????????????

                KqOriginalDataService.getExecutor().execute(() ->{
                    try {
                        List<YcEnterBean> deleteList =new ArrayList<>();
                        YcEnterBean deleteMan = new YcEnterBean();
                        deleteMan.setUserID(kt.getUserId());
                        deleteList.add(deleteMan);
                        String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.DELETE_MEMBER, kt.getSchoolId(), deleteList);
                        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
                        if (ycVerifaceResBean.getSuccess()!=null&&ycVerifaceResBean.getSuccess()){
                            logger.info("????????????????????????????????????????????????");
                        }else {
                            logger.info("????????????????????????????????????????????????");
                        }
                    }catch (Exception e){
                        logger.error("??????????????????????????????????????????????????????",e);
                    }finally {
                        logger.info("????????????????????????????????????????????????");
                    }
                });
                //kt.setUserId(null);
            }

        }
        //????????????
        //????????????id????????????????????????????????????????????????????????????
        YcVerifaceDevice device = null;
        if (StrUtil.isNotEmpty(kt.getDeviceType())&&kt.getDeviceType().equals("1")){
            String deviceSign = kt.getDeviceId();
            YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
            ycVerifaceDevice.setDeviceSign(deviceSign);
            ycVerifaceDevice.setSchoolId(kt.getSchoolId());
            ycVerifaceDevice.setType("1");
            List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
            if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)&&ycVerifaceDeviceListByCondition.size()==1){
                device=ycVerifaceDeviceListByCondition.get(0);
            }
        }else {
            device  = ycVerifaceDeviceService.findYcVerifaceDeviceById(kt.getDeviceId());
        }

        if (device!=null){
            kt.setDerectionFlag(device.getDerectionFlag());
            kt.setCustId(device.getSchoolId());
            kt.setDeviceType(device.getType());
            kt.setDeviceNo(device.getId());
            kt.setDeviceName(device.getDeviceName());
            kt.setDeviceGroupId(device.getDeviceGroupId());
            kt.setDeviceLocation(device.getDeviceLocationName());
            if (StrUtil.isNotEmpty(device.getType())&&device.getType().equals("0")){
                List<String> capturePersonTypeList = device.getCapturePersonTypeList();
                if (CollectionUtil.isEmpty(capturePersonTypeList)||(CollectionUtil.isNotEmpty(capturePersonTypeList)&&!capturePersonTypeList.contains(kt.getUserType()))){
                   return null;
                }
            }
        }
        //????????????
        //???????????????????????????
        return kt;
    }

    public void batchFillOtherInfo( String SchoolId){
        YcVerifaceOriginDataBatchReceiveBean datas = ycVerifaceBackOnlineDatas.get(SchoolId);
        long l = System.currentTimeMillis();
        String schoolId = datas.getSchoolID();
        String catchDate = datas.getCatchDate();
        List<YcOriginalDataBean> beans = datas.getBeans();
        List<KqOriginalData> kqOriginalDatas = new ArrayList<>();
        List<YcOriginalDataBean> noUidList = beans.stream().filter(ycOriginalDataBean -> StrUtil.isEmpty(ycOriginalDataBean.getUserId())).collect(Collectors.toList());
        Map<String, List<YcOriginalDataBean>> ycDataMap = beans.stream().filter(ycOriginalDataBean -> StrUtil.isNotEmpty(ycOriginalDataBean.getUserId())).collect(Collectors.groupingBy(YcOriginalDataBean::getUserId));
        Set<String> deviceSet = beans.stream().filter(ycOriginalDataBean -> {
            String deviceType = ycOriginalDataBean.getDeviceType();
            if (StrUtil.isNotEmpty(deviceType)&&deviceType.equals("1")){//?????????????????????
                YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
                ycVerifaceDevice.setDeviceSign(ycOriginalDataBean.getDeviceId());
                ycVerifaceDevice.setSchoolId(schoolId);
                List<YcVerifaceDevice> ycDeviceList  = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
                if (CollectionUtil.isNotEmpty(ycDeviceList)&&ycDeviceList.size()==1){
                    String id = ycDeviceList.get(0).getId();
                    ycOriginalDataBean.setDeviceId(id);
                }
            }
            return true;
        }).map(YcOriginalDataBean::getDeviceId).collect(Collectors.toSet());

        HashMap<String, YcVerifaceDevice> deviceInfoMap = new HashMap<>();
        HashMap<String, KqDeviceGroup> groupInfoMap = new HashMap<>();

        for (String did:deviceSet){
            YcVerifaceDevice device = ycVerifaceDeviceService.findYcVerifaceDeviceById(did);
            KqDeviceGroup group = kqDeviceGroupFeign.findKqDeviceGroupById(device.getDeviceGroupId());
            groupInfoMap.put(did,group);
            deviceInfoMap.put(did,device);
        }

        //??????????????????????????????
        List<String> personIdList = ycDataMap.keySet().stream().collect(Collectors.toList());
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonIdList(personIdList );
        ycVerifacePersonEnter.setSchoolId(schoolId);
        List<YcVerifacePersonEnter> personEnterList = ycVerifacePersonEnterService.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        Map<String, List<YcVerifacePersonEnter>> personEnterMap = personEnterList.stream().collect(Collectors.groupingBy(YcVerifacePersonEnter::getPersonId));
        //System.out.println("??????????????????"+(System.currentTimeMillis()-l));
        for (YcOriginalDataBean ycOri:noUidList){
            KqOriginalData kqOriginalData = new KqOriginalData();
            kqOriginalData.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER);
            kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_YC);
            kqOriginalData.setCapturedTime(ycOri.getCapturedTime());
            kqOriginalData.setCapturedImage(ycOri.getCapturedImage());
            //???????????????
            kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER);
            YcVerifaceDevice device = deviceInfoMap.get(ycOri.getDeviceId());
            KqDeviceGroup group = groupInfoMap.get(ycOri.getDeviceId());
            if (device!=null){
                //?????????????????????
                if (StrUtil.isNotEmpty(device.getType())&&device.getType().equals("0")){
                    List<String> capturePersonTypeList = device.getCapturePersonTypeList();
                    if (CollectionUtil.isEmpty(capturePersonTypeList)||(CollectionUtil.isNotEmpty(capturePersonTypeList)&&!capturePersonTypeList.contains(kqOriginalData.getUserType()))){
                        continue;
                    }
                }
                kqOriginalData.setDerectionFlag(device.getDerectionFlag());
                kqOriginalData.setSchoolId(device.getSchoolId());
                kqOriginalData.setDeviceType(device.getType());
                kqOriginalData.setDeviceNo(device.getId());
                kqOriginalData.setDeviceName(device.getDeviceName());
                kqOriginalData.setDormitoryId(device.getDormitoryId());
                kqOriginalData.setDormitoryName(device.getDormitoryName());
                kqOriginalData.setGroupId(group.getId());
                kqOriginalData.setGroupName(group.getGroupName());
                kqOriginalData.setGroupType(group.getGroupType());
            }
            if (kqOriginalData.getCapturedImage()!=null){
                String imgUrl = ycUploadImgUrl(kqOriginalData.getCapturedImage());
                kqOriginalData.setCapturedImage(imgUrl);
            }
            //System.out.println("???????????????");
            kqOriginalData.setCreateTime(DateUtil.now());
            if (kqOriginalData.getDeviceType().equals("0")){
                kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_CAMERA);
            }else if (kqOriginalData.getDeviceType().equals("0")){
                kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_DOOR);
            }
            kqOriginalDatas.add(kqOriginalData);
        }

        for (String id:ycDataMap.keySet()){
            //System.out.println("?????????");
            List<YcOriginalDataBean> ycOriginalDataBeans = ycDataMap.get(id);
            String personType = null;
            YcVerifaceBlacklist blacklistById = null;
            Teacher teacher = null;
            Student student = null;
            Visitor oneVisitor =null;
            if (CollectionUtil.isNotEmpty(personEnterMap)){
                List<YcVerifacePersonEnter> ycVerifacePersonEnters = personEnterMap.get(id);
                if (CollectionUtil.isNotEmpty(ycVerifacePersonEnters)){
                    personType =  ycVerifacePersonEnters.get(0).getPersonType();
                }
            }
            if (personType!=null){
                if (personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BLACKPERSON)){
                     blacklistById = ycVerifaceBlacklistFeign.findYcVerifaceBlacklistById(id);
                }
                if (personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER)||personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF)){
                    teacher = teacherFeign.findTeacherById(id);
                }
                if (personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT)||personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BOARDER)){
                    student = studentFeign.findStudentById(id);
                }
                if (personType.equals(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_VISITOR)){
                    oneVisitor = visitorFeign.findVisitorById(id);
                }
             }
            for (YcOriginalDataBean yd:ycOriginalDataBeans){
                KqOriginalData kqOriginalData = new KqOriginalData();
                kqOriginalData.setUserType(personType);
                kqOriginalData.setUserId(yd.getUserId());
                kqOriginalData.setCapturedMessageType(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL);
                kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_YC);
                kqOriginalData.setCapturedTime(yd.getCapturedTime());
                kqOriginalData.setCapturedImage(yd.getCapturedImage());
                YcVerifaceDevice device = deviceInfoMap.get(yd.getDeviceId());
                KqDeviceGroup group = groupInfoMap.get(yd.getDeviceId());
                if (device!=null){
                    if (StrUtil.isNotEmpty(device.getType())&&device.getType().equals("0")){
                        List<String> capturePersonTypeList = device.getCapturePersonTypeList();
                        if (CollectionUtil.isEmpty(capturePersonTypeList)||(CollectionUtil.isNotEmpty(capturePersonTypeList)&&!capturePersonTypeList.contains(kqOriginalData.getUserType()))){
                            continue;
                        }
                    }
                    kqOriginalData.setDerectionFlag(device.getDerectionFlag());
                    kqOriginalData.setSchoolId(device.getSchoolId());
                    kqOriginalData.setDeviceType(device.getType());
                    kqOriginalData.setDeviceNo(device.getId());
                    kqOriginalData.setDeviceName(device.getDeviceName());
                    kqOriginalData.setDormitoryId(device.getDormitoryId());
                    kqOriginalData.setDormitoryName(device.getDormitoryName());
                    kqOriginalData.setGroupId(group.getId());
                    kqOriginalData.setGroupName(group.getGroupName());
                    kqOriginalData.setGroupType(group.getGroupType());
                }
                if (kqOriginalData.getCapturedImage()!=null){
                    String imgUrl = ycUploadImgUrl(kqOriginalData.getCapturedImage());
                    kqOriginalData.setCapturedImage(imgUrl);
                    //kqOriginalData.setCapturedImage("Imagurl");
                }
                if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)
                        &&(kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF))){
                    if (teacher.getPersonType()!=null&&teacher.getPersonType()==2){
                        kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF);
                    };
                    kqOriginalData.setName(teacher.getName());
                    kqOriginalData.setPrsnAvtrUrlAddr(teacher.getImgUrl());
                    kqOriginalData.setWorknumber(teacher.getWorkNumber());
                    //*
                }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)
                        && (kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER))){
                    //?????????????????????
                    kqOriginalData.setStudentId(kqOriginalData.getUserId());
                    kqOriginalData.setName(student.getName());
                    kqOriginalData.setClassesNumber(student.getClassesNumber());
                    kqOriginalData.setClassId(student.getClassesId());
                    kqOriginalData.setGradeId(student.getGradeId());
                    kqOriginalData.setGradeName(student.getGradeName());
                    kqOriginalData.setPrsnAvtrUrlAddr(student.getImgUrl());
                    kqOriginalData.setSeatNumber(student.getSeatNumber());
                    if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")) {
                        boolean isOk = statusOfKqOriginalData(kqOriginalData);
                        if (student.getNowStatus() != null && (student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT) || student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN))) {
                            if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)) {
                                kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                            } else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)) {
                                kqOriginalData.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE);//2??????
                            }
                        } else {
                            kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                        }
                    }
                    //*
                }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_VISITOR)){
                    String idCard =  kqOriginalData.getUserId();
                    kqOriginalData.setVisiIdCard(kqOriginalData.getUserId());
                    if (oneVisitor.getApplyStatus().equals("0")){
                        //???????????????????????????
                        if ( DateUtil.parse(kqOriginalData.getCapturedTime()).isAfterOrEquals(DateUtil.parse(oneVisitor.getStartTime())) && DateUtil.parse(kqOriginalData.getCapturedTime()).isBefore(DateUtil.parse(oneVisitor.getEndTime()))){
                            //???????????????
                            if (oneVisitor.getStudentId()!=null&&!oneVisitor.getStudentId().equals("")){
                                Student student1 = studentFeign.findStudentById(oneVisitor.getStudentId());
                                String desc = student1.getGradeName()+student1.getClassesNumber()+"???"+student1.getName()+"?????????";
                                kqOriginalData.setVisitorDesc(desc);
                            }
                        }
                    }
                    //*
                }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BLACKPERSON)){
                    kqOriginalData.setName(blacklistById.getName());
                    kqOriginalData.setUserDesc(blacklistById.getDescription());
                    kqOriginalData.setSex(blacklistById.getSex());
                    kqOriginalData.setPrsnAvtrUrlAddr(blacklistById.getImageUrl());
                    //*
                }
                kqOriginalData.setCreateTime(DateUtil.now());
                if (kqOriginalData.getDeviceType().equals("0")){
                    kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_CAMERA);
                }else if (kqOriginalData.getDeviceType().equals("0")){
                    kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_DOOR);
                }
                kqOriginalDatas.add(kqOriginalData);
            }


        }

        //????????????????????????
        kqOriginalDataFeign.batchSaveKqOriginalData(kqOriginalDatas);
        //System.out.println("????????????"+kqOriginalDatas.size()+"???");
        //??????????????????
        //????????????????????????????????????
        Set<String> list = ycVerifaceBackOnlineSchoolList.get(schoolId);
        if (CollectionUtil.isEmpty(list)){
            list=new HashSet<String>();
        }
        list.add(catchDate);
        ycVerifaceBackOnlineSchoolList.put(schoolId,list);
        //System.out.println(ycVerifaceBackOnlineSchoolList.get(schoolId));
        //System.out.println("???????????????"+(System.currentTimeMillis()-l));

    }
    //????????????,??????????????????,????????????
    public void saveBatchDatoToRedis(YcVerifaceOriginDataBatchReceiveBean data) {
        String key = data.getSchoolID()+data.getCatchDate()+Math.floor(Math.random()*100);
        ycVerifaceBackOnlineDatas.put(key,data);
        KqOriginalDataService.getExecutor().execute(() ->{
            try {
                batchFillOtherInfo(key);
                ycVerifaceBackOnlineDatas.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    public KqOriginalData saveYcVerifaceOriginalData(KqOriginalDataTest kqOriginalDataTest) {

        //????????????
        KqOriginalData kqOriginalData = new KqOriginalData();
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_YC);
        kqOriginalData.setReqId(kqOriginalDataTest.getReqId());
        kqOriginalData.setUserType(kqOriginalDataTest.getUserType());
        kqOriginalData.setCapturedImage(kqOriginalDataTest.getCapturedImage());
        kqOriginalData.setDeviceLocation(kqOriginalDataTest.getDeviceLocation());
        if (kqOriginalData.getCapturedImage()!=null){
            String imgUrl = ycUploadImgUrl(kqOriginalDataTest.getCapturedImage());
            kqOriginalData.setCapturedImage(imgUrl);
        }
        kqOriginalData.setCapturedTime(kqOriginalDataTest.getCapturedTime());
        kqOriginalData.setSchoolId(kqOriginalDataTest.getCustId());
        kqOriginalData.setDerectionFlag(kqOriginalDataTest.getDerectionFlag());
        kqOriginalData.setDeviceNo(kqOriginalDataTest.getDeviceNo());
        kqOriginalData.setDeviceName(kqOriginalDataTest.getDeviceName());
        kqOriginalData.setDeviceType(kqOriginalDataTest.getDeviceType());
        kqOriginalData.setCapturedMessageType(kqOriginalDataTest.getCapturedMessageType());
        kqOriginalData.setUserId(kqOriginalDataTest.getUserId());
        if (StrUtil.isNotEmpty(kqOriginalDataTest.getDeviceGroupId())){
            KqDeviceGroup kqDeviceGroupById = kqDeviceGroupFeign.findKqDeviceGroupById(kqOriginalDataTest.getDeviceGroupId());
            if (kqDeviceGroupById!=null){
                kqOriginalData.setGroupName(kqDeviceGroupById.getGroupName());
                kqOriginalData.setGroupId(kqDeviceGroupById.getId());
                kqOriginalData.setGroupType(kqDeviceGroupById.getGroupType());
            }
        }

        //?????????????????????????????????
        if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&(kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER)
                ||kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF))){

            Teacher teacher = teacherFeign.findTeacherById(kqOriginalData.getUserId());
            kqOriginalData.setUserType(teacher.getPersonType()==1?Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER:Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF);
            kqOriginalData.setName(teacher.getName());
            kqOriginalData.setPrsnAvtrUrlAddr(teacher.getImgUrl());
            kqOriginalData.setWorknumber(teacher.getWorkNumber());

        }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)
                && (kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_DAY_STUDENT)||kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BOARDER))){
            //?????????????????????
            Student student = studentFeign.findStudentById(kqOriginalData.getUserId());
            /**/
            kqOriginalData.setStudentId(kqOriginalData.getUserId());
            kqOriginalData.setName(student.getName());
            kqOriginalData.setClassesNumber(student.getClassesNumber());
            kqOriginalData.setClassId(student.getClassesId());
            kqOriginalData.setGradeId(student.getGradeId());
            kqOriginalData.setGradeName(student.getGradeName());
            kqOriginalData.setPrsnAvtrUrlAddr(student.getImgUrl());
            kqOriginalData.setSeatNumber(student.getSeatNumber());
            if (kqOriginalData.getGroupType()!=null&&kqOriginalData.getGroupType().contains("0")) {
                boolean isOk = statusOfKqOriginalData(kqOriginalData);
                if (student.getNowStatus() != null && (student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT) || student.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN))) {
                    if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN)) {
                        kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                    } else if (kqOriginalData.getDerectionFlag().equals(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT)) {
                        kqOriginalData.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE);//2??????
                    }
                } else {
                    kqOriginalData.setPassStatus(isOk ? Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL : Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);//1??????3??????
                }
            }
        }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalDataTest.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_VISITOR)){
            kqOriginalData.setName(kqOriginalDataTest.getUserId().split("#")[0]);//???????????????
            String idCard =  kqOriginalData.getUserId();
            kqOriginalData.setVisiIdCard(idCard);
            if (idCard.equals("410222198706134038")){
                kqOriginalData.setVisiIdCard("--");
            }
            Visitor visitor = new Visitor();
            visitor.setApplyStatus("0");
            visitor.setVisitorCard(idCard);
            visitor.setVisitorName(kqOriginalDataTest.getUserId().split("#")[0]);
            List<Visitor> visitors = visitorFeign.findVisitorListByCondition(visitor);
            //???????????????????????????
            if (visitors!=null&&visitors.size()>0){
                List<Visitor> vis = visitors.stream().filter( visitor1 ->
                        DateUtil.parse(kqOriginalData.getCapturedTime()).isAfterOrEquals(DateUtil.parse(visitor1.getStartTime())) && DateUtil.parse(kqOriginalData.getCapturedTime()).isBefore(DateUtil.parse(visitor1.getEndTime()))
                ).sorted(Comparator.comparing(Visitor::getCreateTime).reversed()).collect(Collectors.toList());
                if (vis!=null&&vis.size()>0){
                    Visitor visitor2=vis.get(0);
                    //???????????????
                    if (visitor2.getStudentId()!=null&&!visitor2.getStudentId().equals("")){
                        Student student = studentFeign.findStudentById(visitor2.getStudentId());
                        String desc = student.getGradeName()+student.getClassesNumber()+"???"+student.getName()+"?????????";
                        kqOriginalData.setVisitorDesc(desc);
                    }
                }
            }
            /**/
        }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_MORMAL)&&kqOriginalData.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BLACKPERSON)){
            YcVerifaceBlacklist blacklistById = ycVerifaceBlacklistFeign.findYcVerifaceBlacklistById(kqOriginalData.getUserId());
            kqOriginalData.setName(blacklistById.getName());
            kqOriginalData.setUserDesc(blacklistById.getDescription());
            kqOriginalData.setSex(blacklistById.getSex());
            kqOriginalData.setPrsnAvtrUrlAddr(blacklistById.getImageUrl());
            //*
        }else if (kqOriginalData.getCapturedMessageType().equals(Constant.KQ_ORIGINAL_DATA.CAPTURE_MESSAGE_TYPE_STRANGER)){
            //?????????????????????????????????????????????
            //??????????????????
            kqOriginalData.setUserType(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER);
        }
        curSchoolYearService.setSchoolYearTerm(kqOriginalData,kqOriginalData.getSchoolId());
        if (kqOriginalData.getDeviceType().equals("0")){
            kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_CAMERA);
        }else if (kqOriginalData.getDeviceType().equals("0")){
            kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_TYPE_DOOR);
        }
        kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
        return kqOriginalData;
    }

    //?????????????????????
    public String ycUploadImgUrl(String image) {
        String path = "";
        //base64???MultipartFile file
       /// byte[] decode = Base64.decode(image);
       /// String encode = cn.hutool.core.codec.Base64.encode(decode);
       int i=1;
         do{
            try{
                MultipartFile file = Base64DecodeMultipartFile.base64Convert("data:image/jpeg;base64,"+image);
                path  = QiniuUtil.uploadFile(file, Constant.Upload.YC_CAPTURED_IMAGE);
            }catch (Exception e){
                logger.error("?????????????????????????????????",e);
            }
            if(StrUtil.isNotEmpty(path)){
                i=2;
            }else {
                i++;
            }
        }while (i!=2);

        return path;
    }


    /*--------------------------------------------------??????????????????end-------------------------------------------------------------------------*/
    /**
     * ???????????????
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }
}

