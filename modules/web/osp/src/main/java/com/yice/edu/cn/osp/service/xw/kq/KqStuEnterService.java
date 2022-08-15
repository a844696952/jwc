package com.yice.edu.cn.osp.service.xw.kq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuStatusChange;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.zyDetector.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.common.pojo.xw.kq.ZyStuBean;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.StuStatusChangeFeign;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.osp.service.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-04 10:06
 * @Description: 考勤学生数据
 */
@Service
public class KqStuEnterService {
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private StuStatusChangeFeign stuStatusChangeFeign;
    private final static Logger logger = LoggerFactory.getLogger(KqStuEnterService.class);
    //获得分批列表（每批50人）
    public List<List<Student>> getBatchList(List<Student> list,int pageSize) {
        List<List<Student>> listArray = new ArrayList<List<Student>>();
       //总学生人数
       // int stuNum = list.size();
        if (list != null && pageSize > 0) {
            int listSize = list.size();
            if (listSize <= pageSize) {
                listArray.add(list);
                return listArray;
            }
            int batchSize = listSize / pageSize;
            int remain = listSize % pageSize;

            for (int i = 0; i < batchSize; i++) {
                int fromIndex = i * pageSize;
                int toIndex = fromIndex + pageSize;
                listArray.add(list.subList(fromIndex, toIndex));
            }
            if (remain > 0) {
                listArray.add(list.subList(listSize - remain, listSize));
            }
        }
            return listArray;
    }

    public List requestZyStuEnter(Boolean isProd,String myKey,List<List<Student>> batchList,KqSchoolInit kqSchool ){

        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        List  updatelist  = new ArrayList();

        for (List<Student> stuList:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();
            List<Student>  updateStudents = new ArrayList<Student>();
            for (Student student:stuList){
                if(student.getZyCheckStatus()!=null&&student.getZyCheckStatus().equals("0")){
                    updateStudents.add(student);
                }else {
                //添加zy请求参数（部分参数加密）
                Map<String ,Object> bean = new HashMap<String ,Object>();
                bean.put("custId",kqSchool.getCustId());
                bean.put("staffKey",student.getId());
                ///学生姓名为空情况
                bean.put("prsnName", AESUtil.encrypt(student.getName(), myKey));
                bean.put("prsnTypeCd","2");

                String prsnAvtrUrlAddr="";
                try{
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
                    prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                }catch (Exception e){
                    logger.info("中移base64图片转换异常"+e);
                }
                bean.put("prsnAvtrUrlAddr",prsnAvtrUrlAddr);
                beans.add(bean);
                }
            }

            if (updateStudents.size()!=0){
                List<List<Student>> batchList1=new ArrayList<List<Student>>();
                batchList1.add(updateStudents);
                updatelist = requestZyStuUpdate(isProd,myKey,batchList1,kqSchool);
            }
            if (beans.size()!=0){
                reqParam.put("beans",beans);
                //2分批次请求中移录入接口并返回结果
                String response = ZyDetector.postRequest(isProd,ZyDetector.ADD_MEMBER,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), JSON.toJSONString(reqParam));
                DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
                if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
                    return list1;
                }
                resBeanList.add(dataReceiveResBean.getBeans());
            }

        }
        for (Object list:resBeanList){
            list1.addAll((List)list);
        }
        if (updatelist.size()!=0){
            list1.addAll(updatelist);
        }
        return list1;
    }

    //获得校验通过的学生
    public List<Student> getEnterStuData(List reslist, List<Student> studentList) {
        List<String> stuIds = new ArrayList<String>();
        List<Student> enterStus = new ArrayList<Student>();
        for (Object o:reslist){
            ZyStuBean zyStuBean = JSON.parseObject(o.toString(), ZyStuBean.class);
            if (zyStuBean.getState().equals("0")){
                stuIds.add(zyStuBean.getStaffKey());
            }
        }
        for (String s: stuIds){
            for (Student student :studentList){
                if (s.equals(student.getId())){
                    enterStus.add(student);
                }
            }
        }
       // System.out.println("通过的学生列表"+enterStus);
        return enterStus;
    }


    //上传图片至七牛，并更新学生信息
    public List<Student> uploadImgUrl(List<Student> enterStus) {
        List<Student> students = new ArrayList<Student>();
        for (Student student:enterStus){
            //base64转MultipartFile file
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
            Student s = new Student();
            s.setId(student.getId());
            s.setZyCheckStatus("0");
            try{
                String path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_AVATAR);
                if (!StrUtil.isEmpty(path)){
                    s.setImgUrl(path);
                    students.add(s);
                }
            }catch (Exception e){
                logger.info("图片上传异常"+ e);
            }
        }
        return students;

    }

    //将所有校验结果集中更新学生数据失败的剔除，
    public List<ZyStuBean> updateSuccessStu(List reslist, List<Student> qiniuStus) {
            List<ZyStuBean> zyStuBeans = new ArrayList<ZyStuBean>();
        //将所有中移状态修改为失败
            for (Object o:reslist){//每条中移记录
                ZyStuBean zyStuBean = JSON.parseObject(o.toString(), ZyStuBean.class);
                zyStuBean.setState("1");
                zyStuBeans.add(zyStuBean);
            }
            for (ZyStuBean o:zyStuBeans){//每条中移记录
                Boolean flag = false;
                for (Student s:qiniuStus){//所有七牛记录中有七牛成功记录就中移动记录设置为成功状态
                    if (o.getStaffKey().equals(s.getId())){
                        flag = true;
                    }
                }
                if (flag){
                    o.setState("0");
                }
            }
        return zyStuBeans;
    }

    //绑定人员至设备
    public Boolean boundDevices(Boolean isProd, List<Student> enterStus, KqSchoolInit kqSchool, List<ZyDeviceBean> zyDeviceBeans) {
        //添加ZY请求参数
        Map<String, String> map = new HashMap<>();
        List<String> deviceList = new ArrayList<String>();
        List<String> staffKeyList = new ArrayList<String>();
        for (ZyDeviceBean z :zyDeviceBeans){
            deviceList.add(z.getDeviceNo());
        }
        for (Student z :enterStus){
            staffKeyList.add(z.getId());
        }
        String devices = deviceList.toString();
        String  devices1 = devices.substring(1,devices.length()-1).replaceAll(", ",",");
        String staffKeys = staffKeyList.toString();
        String  staffKeys1 = staffKeys.substring(1,staffKeys.length()-1).replaceAll(", ",",");
        map.put("staffKey", staffKeys1);// 替换，人员标识，多个以英文逗号分隔，注意是和校园的人员标识
        map.put("devices", deviceList.get(0));// 替换，设备编码，多个以英文逗号分隔
        map.put("devices", devices1);// 替换，设备编码，多个以英文逗号分隔
        map.put("custId",kqSchool.getCustId());
        String reqParam = AESUtil.encrypt(JSON.toJSONString(map), kqSchool.getKey());
        String response = ZyDetector.postRequest(isProd,ZyDetector.ADD_PERSON_DEVICE,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), reqParam);
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
            return false;
        }
        return true;
    }

    //编辑中移动已经录入的人员
    public List requestZyStuUpdate(Boolean isProd,String myKey,List<List<Student>> batchList,KqSchoolInit kqSchool ){
        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        for (List<Student> stuList:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();

            for (Student student:stuList){
                //添加zy请求参数（部分参数加密）
                Map<String ,Object> bean = new HashMap<String ,Object>();
                bean.put("custId",kqSchool.getCustId());
                bean.put("staffKey",student.getId());
                bean.put("prsnName", AESUtil.encrypt(student.getName(), myKey));
                bean.put("prsnTypeCd","2");
                String prsnAvtrUrlAddr="";
                try{
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
                    prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                }catch (Exception e){
                    logger.info("base64图片转换异常："+e);
                }
                bean.put("prsnAvtrUrlAddr",prsnAvtrUrlAddr);
                beans.add(bean);
            }
            reqParam.put("beans",beans);
            //2分批次请求中移录入接口并返回结果
            String response = ZyDetector.postRequest(isProd,ZyDetector.EDIT_MEMBER,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), JSON.toJSONString(reqParam));
            DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
            if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
                return list1;
            }
            resBeanList.add(dataReceiveResBean.getBeans());
        }
        for (Object list:resBeanList){
            list1.addAll((List)list);
        }
        return list1;


    }
    //删除中移动已经录入的人员
    public List<ZyStuBean> requestZyStuDelete(Boolean isProd, List<Student> enterStus, KqSchoolInit kqSchool) {
        //响应列表
        List<ZyStuBean> list= new ArrayList<ZyStuBean>();
        //添加ZY请求参数
        Map<String, String> map = new HashMap<>();
        List<String> staffKeyList = new ArrayList<String>();
        for (Student z :enterStus){
            staffKeyList.add(z.getId());
        }
        String staffKeys = staffKeyList.toString();
        String  staffKeys1 = staffKeys.substring(1,staffKeys.length()-1).replaceAll(", ",",");
        map.put("staffKey", staffKeys1);// 替换，人员标识，多个以英文逗号分隔，注意是和校园的人员标识
        map.put("custId",kqSchool.getCustId());
        String reqParam = AESUtil.encrypt(JSON.toJSONString(map), kqSchool.getKey());
        String response = ZyDetector.postRequest(isProd,ZyDetector.DELETE_MEMBER,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), reqParam);
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
            return list;
        }
        for (Object o :dataReceiveResBean.getBeans()){
            ZyStuBean zyStuBean = JSON.parseObject(o.toString(), ZyStuBean.class);
            list.add(zyStuBean);
        }
        return list;
    }


    public void sendStuNowStatusToParent(Student student) {
       String studentNowStatus= student.getNowStatus()!=null?student.getNowStatus():"";
       String[] stuIdArr = new String[1];
       stuIdArr[0]=student.getId();
       if (studentNowStatus.equals(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL)){
           final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuIdArr,"出入校通知","您的孩子已经安全到校！（此条消息由班主任推送）", Extras.XW_STUDENT_IN_OUT_SCHOOL);
           stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
       }else if (studentNowStatus.equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)){
           final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuIdArr,"出入校通知","您的孩子已经离开学校！（此条消息由班主任推送）", Extras.XW_STUDENT_IN_OUT_SCHOOL);
           stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
       }else if (studentNowStatus.equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT)||studentNowStatus.equals(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN)){
           final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuIdArr,"出入校通知","您的孩子请假已经通过审批！（此条消息由班主任推送）", Extras.XW_STUDENT_IN_OUT_SCHOOL);
           stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
       }
        //添加学生在校状态变化记录
        Student s = studentFeign.findStudentById(student.getId());
        StuStatusChange sc= new StuStatusChange();
        sc.setSchoolId(mySchoolId());
        sc.setStudentId(s.getId());
        sc.setStatus(s.getNowStatus());
        sc.setCreateDate(DateUtil.today());
        sc.setCreateTime(DateUtil.now());
        sc.setChangeTime(DateUtil.now());
        sc.setClassId(s.getClassesId());
        stuStatusChangeFeign.saveStuStatusChange(sc);

   }

    public boolean checkIsHeadmaster(Student student) {

        TeacherClasses teacherClasses = new TeacherClasses();
        Student student1 =   studentFeign.findStudentById(student.getId());
        teacherClasses.setClassesId(student1.getClassesId());
        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
        if (teacher!=null){
          return   teacher.getId().equals(myId())?true:false;
        }
        return false;
    }

    public List<Student> getAllCheckStu(Student student) {
        List<Student> studentList =new ArrayList<>();
        if (student.getId()!=null){
            Student studentById = studentFeign.findStudentById(student.getId());
            studentList.add(studentById);
        }else {
            Student student1 = new Student();
            if(student.getGradeId()!=null){
                student1.setGradeId(student.getGradeId());
            }
            if(student.getClassesId()!=null){
                student1.setClassesId(student.getClassesId());
            }
            if(student.getName()!=null){
                student1.setName(student.getName());
            }
            if(student.getZyCheckStatus()!=null){
                student1.setZyCheckStatus(student.getZyCheckStatus());
            }
            student1.setSchoolId(mySchoolId());
            studentList = studentFeign.findStudentListByCondition(student1);
        }

        List<Student> list = new ArrayList<>();
        //将照片转换成base64放到中移上传位
        for (Student student1 :studentList){
            String imgUrl = student1.getImgUrl();
            try {
                byte[] bytes = HttpKit.downloadFileToServer(imgUrl);
                String   prsnAvtrUrlAddr = Base64.encode(bytes);
                student1.setPrsnAvtrUrlAddr(prsnAvtrUrlAddr);
                list.add(student1);
            }catch (Exception e){
                logger.error("图片下载异常："+e);
            }
        }
        return list;
    }

    //编辑中移动已经录入的人员
    public List requestZyStuUpdateforCheck(Boolean isProd,String myKey,List<List<Student>> batchList,KqSchoolInit kqSchool ){
        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        for (List<Student> stuList:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();

            for (Student student:stuList){
                //添加zy请求参数（部分参数加密）
                Map<String ,Object> bean = new HashMap<String ,Object>();
                bean.put("custId",kqSchool.getCustId());
                bean.put("staffKey",student.getId());
                bean.put("prsnName", AESUtil.encrypt(student.getName(), myKey));
                bean.put("prsnTypeCd","2");
                String prsnAvtrUrlAddr=student.getPrsnAvtrUrlAddr();
               /* try{
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
                    prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                }catch (Exception e){
                    System.out.println("图片文件异常");
                    e.printStackTrace();
                }*/
                bean.put("prsnAvtrUrlAddr",prsnAvtrUrlAddr);
                beans.add(bean);
            }
            reqParam.put("beans",beans);
            //2分批次请求中移录入接口并返回结果
            String response = ZyDetector.postRequest(isProd,ZyDetector.EDIT_MEMBER,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), JSON.toJSONString(reqParam));
            DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
            if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
                return list1;
            }
            resBeanList.add(dataReceiveResBean.getBeans());
        }
        for (Object list:resBeanList){
            list1.addAll((List)list);
        }
        return list1;


    }
    public List requestZyStuEnterforCheck(Boolean isProd,String myKey,List<List<Student>> batchList,KqSchoolInit kqSchool ){

        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        List  updatelist  = new ArrayList();

        for (List<Student> stuList:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();
            List<Student>  updateStudents = new ArrayList<Student>();
            for (Student student:stuList){
                if(student.getZyCheckStatus()!=null&&student.getZyCheckStatus().equals("0")){
                    updateStudents.add(student);
                }else {
                    //添加zy请求参数（部分参数加密）
                    Map<String ,Object> bean = new HashMap<String ,Object>();
                    bean.put("custId",kqSchool.getCustId());
                    bean.put("staffKey",student.getId());
                    ///学生姓名为空情况
                    bean.put("prsnName", AESUtil.encrypt(student.getName(), myKey));
                    bean.put("prsnTypeCd","2");

                    String prsnAvtrUrlAddr=student.getPrsnAvtrUrlAddr();
                   /* try{
                        MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
                        prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                    }catch (Exception e){
                        System.out.println("图片文件异常");
                        e.printStackTrace();
                    }*/
                    bean.put("prsnAvtrUrlAddr",prsnAvtrUrlAddr);
                    beans.add(bean);
                }
            }

            if (updateStudents.size()!=0){
                List<List<Student>> batchList1=new ArrayList<List<Student>>();
                batchList1.add(updateStudents);
                updatelist = requestZyStuUpdateforCheck(isProd,myKey,batchList1,kqSchool);
            }
            if (beans.size()!=0){
                reqParam.put("beans",beans);
                //2分批次请求中移录入接口并返回结果
                String response = ZyDetector.postRequest(isProd,ZyDetector.ADD_MEMBER,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), JSON.toJSONString(reqParam));
                DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
                if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
                    return list1;
                }
                resBeanList.add(dataReceiveResBean.getBeans());
            }

        }
        for (Object list:resBeanList){
            list1.addAll((List)list);
        }
        if (updatelist.size()!=0){
            list1.addAll(updatelist);
        }
        return list1;
    }
    //上传图片至七牛，并更新学生信息
    public List<Student> uploadImgUrlForCheck(List<Student> enterStus) {
        List<Student> students = new ArrayList<Student>();
        for (Student student:enterStus){
            //base64转MultipartFile file
            Student s = new Student();
            s.setId(student.getId());
            s.setZyCheckStatus("0");
            students.add(s);
        }
        return students;
    }
}
