package com.yice.edu.cn.osp.service.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcDataToRegisterPage;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceBlacklistFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifacePersonEnterFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.service.jw.staff.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class YcVerifacePersonEnterService {
    @Autowired
    private YcVerifacePersonEnterFeign ycVerifacePersonEnterFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StaffService staffService;
    @Autowired
    private YcVerifaceBlacklistFeign ycVerifaceBlacklistFeign;
    private final static Logger logger = LoggerFactory.getLogger(YcVerifacePersonEnterService.class);

    public YcVerifacePersonEnter findYcVerifacePersonEnterById(String id) {
        return ycVerifacePersonEnterFeign.findYcVerifacePersonEnterById(id);
    }

    public YcVerifacePersonEnter saveYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return ycVerifacePersonEnterFeign.saveYcVerifacePersonEnter(ycVerifacePersonEnter);
    }

    public List<YcVerifacePersonEnter> findYcVerifacePersonEnterListByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
    }

    public YcVerifacePersonEnter findOneYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return ycVerifacePersonEnterFeign.findOneYcVerifacePersonEnterByCondition(ycVerifacePersonEnter);
    }

    public long findYcVerifacePersonEnterCountByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return ycVerifacePersonEnterFeign.findYcVerifacePersonEnterCountByCondition(ycVerifacePersonEnter);
    }

    public void updateYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter) {
        ycVerifacePersonEnterFeign.updateYcVerifacePersonEnter(ycVerifacePersonEnter);
    }

    public void deleteYcVerifacePersonEnter(String id) {
        ycVerifacePersonEnterFeign.deleteYcVerifacePersonEnter(id);
    }

    public void deleteYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        ycVerifacePersonEnterFeign.deleteYcVerifacePersonEnterByCondition(ycVerifacePersonEnter);
    }

    public List<YcVerifacePersonEnter> findYcVerifacePersonEnterByPersonIdList(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    //获得分批列表（每批50人）
    public List<List<Student>> getBatchList(List<Student> list, int pageSize) {
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
    public List<List<Teacher>> getTeacherBatchList(List<Teacher> list, int pageSize) {
        List<List<Teacher>> listArray = new ArrayList<List<Teacher>>();
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
    public List<List<YcVerifaceBlacklist>> getBlacklistBatchList(List<YcVerifaceBlacklist> list, int pageSize) {
        List<List<YcVerifaceBlacklist>> listArray = new ArrayList<List<YcVerifaceBlacklist>>();
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
    //获得校验通过的学生
    public List<Student> getEnterStuData(List<YcVerifacePersonEnter> reslist, List<Student> studentList) {
        List<String> personIds = new ArrayList<String>();
        List<Student> enterStus = new ArrayList<Student>();
        for (YcVerifacePersonEnter personBean : reslist) {
            personIds.add(personBean.getPersonId());
        }
        for (String id : personIds) {
            for (Student student : studentList) {
                if (id.equals(student.getId())) {
                    enterStus.add(student);
                }
            }
        }
        return enterStus;
    }
    //获得校验通过的教师
    public List<Teacher> getEnterTeaData(List<YcVerifacePersonEnter> reslist, List<Teacher> teacherList) {
        List<String> personIds = new ArrayList<>();
        List<Teacher> enterTeas = new ArrayList<>();
        for (YcVerifacePersonEnter personBean : reslist) {
            personIds.add(personBean.getPersonId());
        }
        for (String id : personIds) {
            for (Teacher teacher : teacherList) {
                if (id.equals(teacher.getId())) {
                    enterTeas.add(teacher);
                }
            }
        }
        return enterTeas;
    }
    //获得校验通过的黑名单
   public List<YcVerifaceBlacklist> getEnterBlacklistData(List<YcVerifacePersonEnter> reslist, List<YcVerifaceBlacklist> blacklists) {
        List<String> personIds = new ArrayList<>();
        List<YcVerifaceBlacklist> enterbmans = new ArrayList<>();
        for (YcVerifacePersonEnter personBean : reslist) {
            personIds.add(personBean.getPersonId());
        }
        for (String id : personIds) {
            for (YcVerifaceBlacklist bman : blacklists) {
                if (id.equals(bman.getId())) {
                    enterbmans.add(bman);
                }
            }
        }
        return enterbmans;
    }
    //上传图片至七牛，并更新学生信息
    public List<Student> uploadImgUrl(List<Student> enterStus) {
        List<Student> students = new ArrayList<Student>();
        for (Student student : enterStus) {
            //base64转MultipartFile file
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(student.getPrsnAvtrUrlAddr());
            Student s = new Student();
            s.setId(student.getId());
            try {
                String path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_AVATAR);
                if (StrUtil.isNotEmpty(path)) {
                    s.setImgUrl(path);
                    students.add(s);
                }
            } catch (Exception e) {
                // logger.info("图片上传异常"+ e);
            }
        }
        return students;

    }
    //上传图片至七牛，并更新学生信息
    public List<Teacher> uploadTeaImgUrl(List<Teacher> enterStus) {
        List<Teacher> teachers = new ArrayList<>();
        for (Teacher teacher : enterStus) {
            //base64转MultipartFile file
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(teacher.getImgUrl());
            Teacher s = new Teacher();
            s.setId(teacher.getId());
            try {
                String path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_AVATAR);
                if (StrUtil.isNotEmpty(path)) {
                    s.setImgUrl(path);
                    teachers.add(s);
                }
            } catch (Exception e) {
                // logger.info("图片上传异常"+ e);
            }
        }
        return teachers;

    }
    //将所有校验结果集中更新学生数据失败的剔除，
    public List<YcVerifacePersonEnter> updateSuccessStu(List<YcVerifacePersonEnter> reslist, List<Student> qiniuStus) {
        List<YcVerifacePersonEnter> personBeans = new ArrayList<YcVerifacePersonEnter>();
        //将所有算法识别状态修改为失败
        for (YcVerifacePersonEnter personEnter : reslist) {//每条算法识别记录
            personEnter.setState("1");
            personBeans.add(personEnter);
        }
        for (YcVerifacePersonEnter o : personBeans) {//每条中移记录
            Boolean flag = false;
            for (Student s : qiniuStus) {//所有七牛记录中有七牛成功记录就中移动记录设置为成功状态
                if (o.getPersonId().equals(s.getId())) {
                    flag = true;
                }
            }
            if (flag) {
                o.setState("0");
            }
        }
        return personBeans;
    }
    //将所有校验结果集中更新学生数据失败的剔除，
    public List<YcVerifacePersonEnter> updateSuccessTea(List<YcVerifacePersonEnter> reslist, List<Teacher> qiniuStus) {
        List<YcVerifacePersonEnter> personBeans = new ArrayList<YcVerifacePersonEnter>();
        //将所有算法识别状态修改为失败
        for (YcVerifacePersonEnter personEnter : reslist) {//每条算法识别记录
            personEnter.setState("1");
            personBeans.add(personEnter);
        }
        for (YcVerifacePersonEnter o : personBeans) {//每条中移记录
            Boolean flag = false;
            for (Teacher s : qiniuStus) {//所有七牛记录中有七牛成功记录就中移动记录设置为成功状态
                if (o.getPersonId().equals(s.getId())) {
                    flag = true;
                }
            }
            if (flag) {
                o.setState("0");
            }
        }
        return personBeans;
    }
    //将所有校验结果集中更新学生数据失败的剔除，
    public List<YcVerifacePersonEnter> updateSuccessBlacklist(List<YcVerifacePersonEnter> reslist, List<YcVerifaceBlacklist> qiniuStus) {
        List<YcVerifacePersonEnter> personBeans = new ArrayList<YcVerifacePersonEnter>();
        //将所有算法识别状态修改为失败
        for (YcVerifacePersonEnter personEnter : reslist) {//每条算法识别记录
            personEnter.setState("1");
            personBeans.add(personEnter);
        }
        for (YcVerifacePersonEnter o : personBeans) {//每条中移记录
            Boolean flag = false;
            for (YcVerifaceBlacklist s : qiniuStus) {//所有七牛记录中有七牛成功记录就中移动记录设置为成功状态
                if (o.getPersonId().equals(s.getId())) {
                    flag = true;
                }
            }
            if (flag) {
                o.setState("0");
            }
        }
        return personBeans;
    }
    //学生请求算法校验并注册到人员表
    public List<YcVerifacePersonEnter> requestYcStuEnter(Boolean isProd, List<List<Student>> batchList) {
        List<YcEnterBean> firstEnter = new ArrayList<>();
        List<YcEnterBean> update = new ArrayList<>();
        for (List<Student> list : batchList) {
            //这批人是否已注册人员
            List<String> personIdList = list.stream().map(Student::getId).collect(Collectors.toList());
            YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
            ycVerifacePersonEnter.setSchoolId(mySchoolId());
            ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
            ycVerifacePersonEnter.setPersonIdList(personIdList);
            List<YcVerifacePersonEnter> ycVerifacePersonEnterByPersonIdList = findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
            List<String> enterPersonIdList = ycVerifacePersonEnterByPersonIdList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
            for (Student student : list) {
                boolean isEnter = enterPersonIdList.contains(student.getId());
                YcEnterBean ycEnterBean = new YcEnterBean();
                ycEnterBean.setUserID(student.getId());
                ycEnterBean.setImg_base64(StrUtil.isNotEmpty(student.getPrsnAvtrUrlAddr())?student.getPrsnAvtrUrlAddr().split(",")[1]:"");
                if (isEnter) {
                    //修改
                    update.add(ycEnterBean);
                } else {
                    //新增
                    firstEnter.add(ycEnterBean);
                }
            }
        }
        List<YcVerifacePersonEnter> ys = classifyPersonReq(isProd,Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT, firstEnter, update);
        return ys;
    }
    //教师请求算法校验并注册到人员表
    public List<YcVerifacePersonEnter> requestYcTeaEnter(Boolean isProd, List<List<Teacher>> batchList,Integer personType) {
        List<YcEnterBean> firstEnter = new ArrayList<>();
        List<YcEnterBean> update = new ArrayList<>();
        String personType1 =personType==1?Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER:Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF;
        for (List<Teacher> list : batchList) {
            //这批人是否已注册人员
            List<String> personIdList = list.stream().map(Teacher::getId).collect(Collectors.toList());
            YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
            ycVerifacePersonEnter.setSchoolId(mySchoolId());
            ycVerifacePersonEnter.setPersonType(personType1);
            ycVerifacePersonEnter.setPersonIdList(personIdList);
            List<YcVerifacePersonEnter> ycVerifacePersonEnterByPersonIdList = findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
            List<String> enterPersonIdList = ycVerifacePersonEnterByPersonIdList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
            for (Teacher teacher : list) {
                boolean isEnter = enterPersonIdList.contains(teacher.getId());
                YcEnterBean ycEnterBean = new YcEnterBean();
                ycEnterBean.setUserID(teacher.getId());
                ycEnterBean.setImg_base64(StrUtil.isNotEmpty(teacher.getImgUrl())?teacher.getImgUrl().split(",")[1]:"");
                if (isEnter) {
                    //修改
                    update.add(ycEnterBean);
                } else {
                    //新增
                    firstEnter.add(ycEnterBean);
                }
            }
        }
        List<YcVerifacePersonEnter> ys = classifyPersonReq(isProd,personType1, firstEnter, update);
        return ys;
    }
    public List<YcVerifacePersonEnter> requestYcBlackListEnter(Boolean isProd, List<List<YcVerifaceBlacklist>> batchList,Integer personType) {
        List<YcEnterBean> firstEnter = new ArrayList<>();
        List<YcEnterBean> update = new ArrayList<>();
        for (List<YcVerifaceBlacklist> list : batchList) {
            //这批人是否已注册人员
            List<String> personIdList = list.stream().map(YcVerifaceBlacklist::getId).collect(Collectors.toList());
            YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
            ycVerifacePersonEnter.setSchoolId(mySchoolId());
            ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BLACKPERSON);
            ycVerifacePersonEnter.setPersonIdList(personIdList);
            List<YcVerifacePersonEnter> ycVerifacePersonEnterByPersonIdList = findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
            List<String> enterPersonIdList = ycVerifacePersonEnterByPersonIdList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
            for (YcVerifaceBlacklist bman : list) {
                boolean isEnter = enterPersonIdList.contains(bman.getId());
                YcEnterBean ycEnterBean = new YcEnterBean();
                ycEnterBean.setUserID(bman.getId());
                ycEnterBean.setImg_base64(StrUtil.isNotEmpty(bman.getImageUrl())?bman.getImageUrl().split(",")[1]:"");
                if (isEnter) {
                    //修改
                    update.add(ycEnterBean);
                } else {
                    //新增
                    firstEnter.add(ycEnterBean);
                }
            }
        }
        List<YcVerifacePersonEnter> ys = classifyPersonReq(isProd,Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BLACKPERSON, firstEnter, update);
        return ys;
    }
    //获得校验成功的人员列表
    public List<YcVerifacePersonEnter> getCheckSuccessPersonList(Boolean isProd,List<YcVerifacePersonEnter> ys, List<Object> beans, String personType,List<YcEnterBean> enters) {
        List<YcEnterBean> update = new ArrayList<>();
        List<YcEnterBean> regist = new ArrayList<>();
        for (Object o : beans) {
            YcVerifacePersonBean person = JSON.parseObject(o.toString(), YcVerifacePersonBean.class);
            if (person.getResult().equals("success")) {
                YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
                ycVerifacePersonEnter.setPersonId(person.getUserId());
                ycVerifacePersonEnter.setPersonType(personType);
                ycVerifacePersonEnter.setSchoolId(mySchoolId());
                ys.add(ycVerifacePersonEnter);
            }else if (person.getResult().equals("this user is already existed!")&&CollectionUtil.isNotEmpty(enters)){
                    YcEnterBean upEnter = new YcEnterBean();
                    List<YcEnterBean> collect = enters.stream().filter(ycEnterBean -> ycEnterBean.getUserID()!=null&&ycEnterBean.getUserID().equals(person.getUserId())).collect(Collectors.toList());
                    upEnter.setUserID(person.getUserId());
                    upEnter.setImg_base64(collect.get(0).getImg_base64());
                    update.add(upEnter);
            }else if (person.getResult().equals("this user is  not exist!")&&CollectionUtil.isNotEmpty(enters)){
                YcEnterBean upEnter = new YcEnterBean();
                List<YcEnterBean> collect = enters.stream().filter(ycEnterBean -> ycEnterBean.getUserID()!=null&&ycEnterBean.getUserID().equals(person.getUserId())).collect(Collectors.toList());
                upEnter.setUserID(person.getUserId());
                upEnter.setImg_base64(collect.get(0).getImg_base64());
                regist.add(upEnter);
            }

        }
        if(update.size()>0){
            String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.EDIT_MEMBER, mySchoolId(), update);
            YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
            List<Object> beans1 = ycVerifaceResBean.getBeans();
            getCheckSuccessPersonList(isProd,ys, beans1, personType,update);
        }
        if(regist.size()>0){
            String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ADD_MEMBER, mySchoolId(), regist);
            YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
            List<Object> beans1 = ycVerifaceResBean.getBeans();
            getCheckSuccessPersonList(isProd,ys, beans1, personType,regist);
            ycVerifacePersonEnterFeign.batchSaveYcVerifacePersonEnter(ys);
        }
        return ys;
    }
    //人员分类请求
    public List<YcVerifacePersonEnter> classifyPersonReq(Boolean isProd,String personType,List<YcEnterBean> firstEnter,List<YcEnterBean> update){
        List<YcVerifacePersonEnter> ys = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(firstEnter)) {
            //请求人脸算法服务器校验头像
            String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ADD_MEMBER, mySchoolId(), firstEnter);
            YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
            List<Object> beans = ycVerifaceResBean.getBeans();
            getCheckSuccessPersonList(isProd,ys, beans, personType,firstEnter);
            //将成功的人添加到注册成功表
            ycVerifacePersonEnterFeign.batchSaveYcVerifacePersonEnter(ys);
        }
        if (CollectionUtil.isNotEmpty(update)) {
            //请求人脸算法服务器校验头像
            String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.EDIT_MEMBER, mySchoolId(), update);
            YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
            List<Object> beans = ycVerifaceResBean.getBeans();
            getCheckSuccessPersonList(isProd,ys, beans, personType,update);
        }
        return ys;
    }

    public List<Student> getAllCheckStu(Student student) {
        List<Student> studentList =new ArrayList<>();
        if (student.getId()!=null){
            Student studentById = studentFeign.findStudentById(student.getId());
            studentList.add(studentById);
        }else {
            Student student1 = new Student();
            student1.setSchoolId(mySchoolId());
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
                YcDataToRegisterPage ycDataToRegisterPage = fingStudentByYcCheckStatus(student);
                studentList =  ycDataToRegisterPage.getStudentList();
            }else {
                studentList = studentFeign.findStudentListByCondition(student1);
            }
        }

        List<Student> list = new ArrayList<>();
        //将照片转换成base64放到中移上传位
        for (Student student1 :studentList){
            String imgUrl = student1.getImgUrl();
            if (imgUrl==null||imgUrl.equals("/headProfile/women.png")||imgUrl.equals("/headProfile/man.png")||imgUrl.equals("/img/avatar/avatar.png")||imgUrl.equals("headProfile/boy.png")){
                continue;
            }
            try {
                byte[] bytes = HttpKit.downloadFileToServer(imgUrl);
                String   prsnAvtrUrlAddr = Base64.getEncoder().encodeToString(bytes);
                student1.setPrsnAvtrUrlAddr("1,"+prsnAvtrUrlAddr);
                list.add(student1);
            }catch (Exception e){
                logger.error("图片下载异常："+e);
            }
        }
        return list;
    }

    public List<Teacher> getAllCheckTea(Teacher teacher,Integer personType) {
        List<Teacher> teacherList =new ArrayList<>();
        if (teacher.getId()!=null){
            Teacher teacherById = teacherFeign.findTeacherById(teacher.getId());
            teacherList.add(teacherById);
        }else {
            teacher.setSchoolId(mySchoolId());
            teacher.getPager().setPaging(false);
            teacher.setPersonType(personType);
            if (teacher.getCheckStatus()!=null){
                YcDataToRegisterPage ycDataToRegisterPage = fingTeacherByYcCheckStatus(teacher,personType);
                teacherList = ycDataToRegisterPage.getTeacherList();
            }else {
                if (personType==1){
                    teacherList= teacherFeign.findTeacherListByCondition(teacher);
                }else if (personType ==2){
                    teacherList= findStaffsByCondition(teacher);
                }
            }

        }

        List<Teacher> list = new ArrayList<>();
        //将照片转换成base64放到中移上传位
        for (Teacher teacher1 :teacherList){
            String imgUrl = teacher1.getImgUrl();
            if (imgUrl==null||imgUrl.equals("/headProfile/women.png")||imgUrl.equals("/headProfile/man.png")||imgUrl.equals("/img/avatar/avatar.png")||imgUrl.equals("headProfile/boy.png")){
                continue;
            }
            try {
                byte[] bytes = HttpKit.downloadFileToServer(imgUrl);
                String   prsnAvtrUrlAddr = Base64.getEncoder().encodeToString(bytes);
                teacher1.setImgUrl("1,"+prsnAvtrUrlAddr);
                list.add(teacher1);
            }catch (Exception e){
                logger.error("图片下载异常："+e);
            }
        }
        return list;
    }
    public List<YcVerifaceBlacklist> getAllCheckBlacklist(YcVerifaceBlacklist blacklist) {
        List<YcVerifaceBlacklist> blacklists =new ArrayList<>();
        if (blacklist.getId()!=null){
            YcVerifaceBlacklist blacklistById = ycVerifaceBlacklistFeign.findYcVerifaceBlacklistById(blacklist.getId());
            blacklists.add(blacklistById);
        }else {
            blacklist.setSchoolId(mySchoolId());
            blacklist.getPager().setPaging(false);
            if (blacklist.getCheckStatus()!=null){
                YcDataToRegisterPage ycDataToRegisterPage = fingBlackListByYcCheckStatus(blacklist);
                blacklists = ycDataToRegisterPage.getBlacklists();
            }else {
                    blacklists= ycVerifaceBlacklistFeign.findYcVerifaceBlacklistListByCondition(blacklist);
            }

        }

        List<YcVerifaceBlacklist> list = new ArrayList<>();
        //将照片转换成base64放到中移上传位
        for (YcVerifaceBlacklist bman :blacklists){
            String imgUrl = bman.getImageUrl();
            if (imgUrl==null||imgUrl.equals("/headProfile/women.png")||imgUrl.equals("/headProfile/man.png")||imgUrl.equals("/img/avatar/avatar.png")||imgUrl.equals("headProfile/boy.png")){
                continue;
            }
            try {
                byte[] bytes = HttpKit.downloadFileToServer(imgUrl);
                String   prsnAvtrUrlAddr = Base64.getEncoder().encodeToString(bytes);
                bman.setImageUrl("1,"+prsnAvtrUrlAddr);
                list.add(bman);
            }catch (Exception e){
                logger.error("图片下载异常："+e);
            }
        }
        return list;
    }
    public List<Student> addStuCheckStatus(List<Student> data) {
        //查询学生在注册表是否存在
        List<String>  idList = data.stream().map(Student::getId).collect(Collectors.toList());
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonIdList(idList);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        List<YcVerifacePersonEnter>  existList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String>  existIdList =existList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        data = data.stream().filter(student -> {
           if (existIdList.contains(student.getId())){
               student.setZyCheckStatus("0");
           }else {
               student.setZyCheckStatus("1");
           }
            return true;
        }).collect(Collectors.toList());
        return data;
    }

    public List<Teacher> addTeacherCheckStatus(List<Teacher> data,String personType) {
        //查询学生在注册表是否存在
        List<String>  idList = data.stream().map(Teacher::getId).collect(Collectors.toList());
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonIdList(idList);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(personType);
        List<YcVerifacePersonEnter>  existList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String>  existIdList =existList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        data = data.stream().filter(teacher -> {
            if (existIdList.contains(teacher.getId())){
                teacher.setCheckStatus("0");
            }else {
                teacher.setCheckStatus("1");
            }
            return true;
        }).collect(Collectors.toList());
        return data;
    }
    public List<YcVerifaceBlacklist> addBlackListCheckStatus(List<YcVerifaceBlacklist> data,String personType) {
        //查询学生在注册表是否存在
        List<String>  idList = data.stream().map(YcVerifaceBlacklist::getId).collect(Collectors.toList());
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonIdList(idList);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(personType);
        List<YcVerifacePersonEnter>  existList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String>  existIdList =existList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        data = data.stream().filter(bman -> {
            if (existIdList.contains(bman.getId())){
                bman.setCheckStatus("0");
            }else {
                bman.setCheckStatus("1");
            }
            return true;
        }).collect(Collectors.toList());
        return data;
    }
    public YcDataToRegisterPage fingStudentByYcCheckStatus(Student student) {
        //查找所有注册表中有的学生
        String checkStatus = student.getZyCheckStatus();
        YcDataToRegisterPage ycDataToRegisterPage = new YcDataToRegisterPage();
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        List<YcVerifacePersonEnter>  existList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String>  existIdList =existList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setSchoolId(student.getSchoolId());
        kqDeviceGroupPerson.setClassesId(student.getClassesId());
        kqDeviceGroupPerson.setGradeId(student.getGradeId());
        kqDeviceGroupPerson.setName(student.getName());
        kqDeviceGroupPerson.setPersonIdList(existIdList);
        if (checkStatus.equals("0")){
            //查找校验成功的
            List<Student>  data = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(existIdList)){
                data = studentFeign.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
            }
            long count= data.size();
            kqDeviceGroupPerson.setPager(student.getPager());
            if (CollectionUtil.isNotEmpty(existIdList)){
            data = studentFeign.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
            }
            addStuCheckStatus(data);
            ycDataToRegisterPage.setCount(count);
            ycDataToRegisterPage.setStudentList(data);
        }else if (checkStatus.equals("1")){
            //查找未校验的
            List<Student>  data = studentFeign.findStudentListByExcludeStudentIdsAndSchoolId(kqDeviceGroupPerson);
            long count= data.size();
            kqDeviceGroupPerson.setPager(student.getPager());
            data = studentFeign.findStudentListByExcludeStudentIdsAndSchoolId(kqDeviceGroupPerson);
            addStuCheckStatus(data);
            ycDataToRegisterPage.setCount(count);
            ycDataToRegisterPage.setStudentList(data);
        }

        return ycDataToRegisterPage;
    }
    //按注册状态查找教师
    public YcDataToRegisterPage fingTeacherByYcCheckStatus(Teacher teacher,Integer personType) {
        //查找所有注册表中有的教师
        String checkStatus = teacher.getCheckStatus();
        YcDataToRegisterPage ycDataToRegisterPage = new YcDataToRegisterPage();
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        if (personType==1){
            ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER);
        }else if (personType==2){
            ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        }
        Pager pager = teacher.getPager();
        if(teacher.getPager()!=null){
           teacher.getPager().setPaging(false);
       }
        List<Teacher>  data = new ArrayList<>();
       if (personType==1){
            data = teacherFeign.findTeacherListByCondition(teacher);
       }else if (personType==2){
            data = findStaffsByCondition(teacher);
       }
        if (CollectionUtil.isEmpty(data)){
            return ycDataToRegisterPage;
        }
        addTeacherCheckStatus(data,ycVerifacePersonEnter.getPersonType());
        if (checkStatus.equals("0")){
            //查找校验成功的
            data = data.stream().filter(teacher1 -> teacher1.getCheckStatus().equals("0")).collect(Collectors.toList());
            ycDataToRegisterPage.setCount(data.size());
            if (pager!=null&&pager.getPaging()){
                data=  data.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
            }
            ycDataToRegisterPage.setTeacherList(data);

        }else if (checkStatus.equals("1")){
            data = data.stream().filter(teacher1 -> teacher1.getCheckStatus().equals("1")).collect(Collectors.toList());
            ycDataToRegisterPage.setCount(data.size());
            if (pager!=null){
                data=  data.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
            }
            ycDataToRegisterPage.setTeacherList(data);

        }
        return ycDataToRegisterPage;
    }
    public YcDataToRegisterPage fingBlackListByYcCheckStatus(YcVerifaceBlacklist bman) {
        //查找所有注册表中有的黑名单
        String checkStatus = bman.getCheckStatus();
        YcDataToRegisterPage ycDataToRegisterPage = new YcDataToRegisterPage();
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_BLACKPERSON);
        Pager pager = bman.getPager();
        if(bman.getPager()!=null){
            bman.getPager().setPaging(false);
        }
        List<YcVerifaceBlacklist>  data = new ArrayList<>();
        data = ycVerifaceBlacklistFeign.findYcVerifaceBlacklistListByCondition(bman);
        if (CollectionUtil.isEmpty(data)){
            return ycDataToRegisterPage;
        }
        addBlackListCheckStatus(data,ycVerifacePersonEnter.getPersonType());
        if (checkStatus.equals("0")){
            //查找校验成功的
            data = data.stream().filter(bman1 -> bman1.getCheckStatus().equals("0")).collect(Collectors.toList());
            ycDataToRegisterPage.setCount(data.size());
            if (pager!=null&&pager.getPaging()){
                data=  data.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
            }
            ycDataToRegisterPage.setBlacklists(data);

        }else if (checkStatus.equals("1")){
            data = data.stream().filter(bman1 -> bman1.getCheckStatus().equals("1")).collect(Collectors.toList());
            ycDataToRegisterPage.setCount(data.size());
            if (pager!=null){
                data=  data.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
            }
            ycDataToRegisterPage.setBlacklists(data);

        }
        return ycDataToRegisterPage;
    }
    public List<Teacher> findStaffsByCondition(Teacher staff) {
        staff.setSchoolId(mySchoolId());
        staff.setStatus("40");
       if (staff.getSearchCheckType()==null){
            staff.setSearchCheckType("1");
        }
        List<Teacher> data = staffService.findStaffListByCondition(staff);
       return data;
    }
    public long findStaffsCountByCondition(Teacher staff) {
        staff.setSchoolId(mySchoolId());
        staff.setStatus("40");
        if (staff.getSearchCheckType()==null){
            staff.setSearchCheckType("1");
        }
        long count = staffService.findStaffCountByCondition(staff);
        return count;
    }
    //从算法服务器删除人员
}
