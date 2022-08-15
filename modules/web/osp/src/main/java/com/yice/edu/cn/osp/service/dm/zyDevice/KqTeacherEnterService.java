package com.yice.edu.cn.osp.service.dm.zyDevice;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.common.pojo.xw.kq.ZyStuBean;
import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.teacherattendance.XwTeacherImageInputFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.osp.service.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-04 10:06
 * @Description: 考勤教师数据
 */
@Service
public class KqTeacherEnterService {
    @Autowired
    private XwTeacherImageInputFeign xwTeacherImageInputFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    private final static Logger logger = LoggerFactory.getLogger(KqTeacherEnterService.class);
    //获得分批列表（每批50人）
    public List<List<Teacher>> getBatchList(List<Teacher> list, int pageSize) {
        List<List<Teacher>> listArray = new ArrayList<List<Teacher>>();
       //总教师人数
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

    public List requestZyStuEnter(Boolean isProd,String myKey,List<List<Teacher>> batchList,KqSchoolInit kqSchool ){

        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        List  updatelist  = new ArrayList();

        for (List<Teacher> teachers:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();
            List<Teacher>  updateTeachers = new ArrayList<Teacher>();
            for (Teacher teacher:teachers){

                if(teacher.getCheckStatus()!=null&&teacher.getCheckStatus().equals("2")){
                    updateTeachers.add(teacher);
                }else {
                //添加zy请求参数（部分参数加密）
                Map<String ,Object> bean = new HashMap<String ,Object>();
                bean.put("custId",kqSchool.getCustId());
                bean.put("staffKey",teacher.getId());
                ///学生姓名为空情况
                bean.put("prsnName", AESUtil.encrypt(teacher.getName(), myKey));
                bean.put("prsnTypeCd","1");

                String prsnAvtrUrlAddr="";
                try{
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(teacher.getImgUrl());
                    prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                }catch (Exception e){
                    logger.info("图片文件异常");
                    e.printStackTrace();
                }
                bean.put("prsnAvtrUrlAddr",prsnAvtrUrlAddr);
                beans.add(bean);
                }
            }

            if (updateTeachers.size()!=0){
                List<List<Teacher>> batchList1=new ArrayList<List<Teacher>>();
                batchList1.add(updateTeachers);
                updatelist.addAll( requestZyStuUpdate(isProd,myKey,batchList1,kqSchool));
                logger.info("走编辑更新了");
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
    public List<Teacher> getEnterTeacherData(List reslist, List<Teacher> teacherList) {
        List<String> ids = new ArrayList<String>();
        List<Teacher> enterStus = new ArrayList<Teacher>();
        for (Object o:reslist){
            ZyStuBean zyStuBean = JSON.parseObject(o.toString(), ZyStuBean.class);
            if (zyStuBean.getState().equals("0")){
                ids.add(zyStuBean.getStaffKey());
            }
        }
        for (String s: ids){
            for (Teacher teacher :teacherList){
                if (s.equals(teacher.getId())){
                    enterStus.add(teacher);
                }
            }
        }
       // System.out.println("通过的学生列表"+enterStus);
        return enterStus;
    }


    //上传图片至七牛，并更新学生信息
    public List<Teacher> uploadImgUrl(List<Teacher> enterStus) {
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (Teacher teacher:enterStus){
            //base64转MultipartFile file
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(teacher.getImgUrl());
            Teacher s = new Teacher();
            s.setId(teacher.getId());
            s.setCheckStatus(teacher.getCheckStatus());
            try{
                String path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_AVATAR);
                if (!StrUtil.isEmpty(path)){
                    s.setImgUrl(path);
                    teachers.add(s);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return teachers;

    }

    //将所有校验结果集中更新数据失败的剔除，
    public List<ZyStuBean> updateSuccessStu(List reslist, List<Teacher> qiniuStus) {
            List<ZyStuBean> zyStuBeans = new ArrayList<ZyStuBean>();
        //将所有中移状态修改为失败
            for (Object o:reslist){//每条中移记录
                ZyStuBean zyStuBean = JSON.parseObject(o.toString(), ZyStuBean.class);
                zyStuBean.setState("0");
                zyStuBeans.add(zyStuBean);
            }
            for (ZyStuBean o:zyStuBeans){//每条中移记录
                Boolean flag = false;
                for (Teacher s:qiniuStus){//所有七牛记录中有七牛成功记录就中移动记录设置为成功状态
                    if (o.getStaffKey().equals(s.getId())){
                        flag = true;
                    }
                }
                if (flag){
                    o.setState("2");
                }
            }
        return zyStuBeans;
    }

    //绑定人员至设备
    public Boolean boundDevices(Boolean isProd, List<Teacher> enterStus, KqSchoolInit kqSchool, List<ZyDeviceBean> zyDeviceBeans) {
        //添加ZY请求参数
        Map<String, String> map = new HashMap<>();
        List<String> deviceList = new ArrayList<String>();
        List<String> staffKeyList = new ArrayList<String>();
        for (ZyDeviceBean z :zyDeviceBeans){
            deviceList.add(z.getDeviceNo());
        }
        for (Teacher z :enterStus){
            staffKeyList.add(z.getId());
        }
        String devices = deviceList.toString();
        String  devices1 = devices.substring(1,devices.length()-1).replaceAll(", ",",");
        String staffKeys = staffKeyList.toString();
        String  staffKeys1 = staffKeys.substring(1,staffKeys.length()-1).replaceAll(", ",",");
        map.put("staffKey", staffKeys1);// 替换，人员标识，多个以英文逗号分隔，注意是和校园的人员标识
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
    public List requestZyStuUpdate(Boolean isProd,String myKey,List<List<Teacher>> batchList,KqSchoolInit kqSchool ){
        //循环请求中移
        //总响应（各个批次响应总和）
        List resBeanList = new ArrayList<List<Object>>();
        List list1 = new ArrayList();
        for (List<Teacher> teachers:batchList){
            Map<String ,Object> reqParam = new HashMap<String ,Object>();
            List beans = new ArrayList<HashMap>();

            for (Teacher teacher:teachers){
                //添加zy请求参数（部分参数加密）
                Map<String ,Object> bean = new HashMap<String ,Object>();
                bean.put("custId",kqSchool.getCustId());
                bean.put("staffKey",teacher.getId());
                bean.put("prsnName", AESUtil.encrypt(teacher.getName(), myKey));
                bean.put("prsnTypeCd","1");
                String prsnAvtrUrlAddr="";
                try{
                    MultipartFile file = Base64DecodeMultipartFile.base64Convert(teacher.getImgUrl());
                    prsnAvtrUrlAddr =  Base64.encode(file.getBytes());
                }catch (Exception e){
                    e.printStackTrace();
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
    public void  saveTeacherImage(List<Teacher> teacherList,String schoolId){
        List<XwTeacherImageInput> xwTeacherImageInputs = new ArrayList<>();
        for (Teacher t:teacherList){
            if (t.getCheckStatus()!=null&&t.getCheckStatus().equals("2")){
                //去做跟新
                XwTeacherImageInput x =new XwTeacherImageInput();
                x.setStatus("2");//2中移动标识
                x.setSchoolId(schoolId);
                x.setTeacherId(t.getId());
                XwTeacherImageInput x1=   xwTeacherImageInputFeign.findOneXwTeacherImageInputByCondition(x);
                x1.setImg(t.getImgUrl());
                xwTeacherImageInputFeign.updateXwTeacherImageInput(x1);
            }else {
                XwTeacherImageInput x =new XwTeacherImageInput();
                x.setStatus("2");//2中移动标识
                x.setImg(t.getImgUrl());
                x.setSchoolId(schoolId);
                x.setTeacherId(t.getId());
                xwTeacherImageInputs.add(x);
            }
        }
        if (xwTeacherImageInputs.size()!=0){
            xwTeacherImageInputFeign.batchSaveXwTeacherImageInput(xwTeacherImageInputs);
        }
    }

}
