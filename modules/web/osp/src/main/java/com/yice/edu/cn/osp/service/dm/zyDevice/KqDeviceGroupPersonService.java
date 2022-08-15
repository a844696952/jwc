package com.yice.edu.cn.osp.service.dm.zyDevice;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDeviceGroupPersonFeign;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDevicePersonFeign;
import com.yice.edu.cn.osp.feignClient.jw.staff.StaffFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolZyDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class KqDeviceGroupPersonService {
    @Autowired
    private KqDeviceGroupPersonFeign kqDeviceGroupPersonFeign;
    @Autowired
    private KqDevicePersonFeign kqDevicePersonFeign;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StaffFeign staffFeign;
    @Autowired
    private KqSchoolZyDevicesService kqSchoolZyDevicesService;

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    public KqDeviceGroupPerson findKqDeviceGroupPersonById(String id) {
        return kqDeviceGroupPersonFeign.findKqDeviceGroupPersonById(id);
    }

    public KqDeviceGroupPerson saveKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return kqDeviceGroupPersonFeign.saveKqDeviceGroupPerson(kqDeviceGroupPerson);
    }

    public List<KqDeviceGroupPerson> findKqDeviceGroupPersonListByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
    }

    public KqDeviceGroupPerson findOneKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return kqDeviceGroupPersonFeign.findOneKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
    }

    public long findKqDeviceGroupPersonCountByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return kqDeviceGroupPersonFeign.findKqDeviceGroupPersonCountByCondition(kqDeviceGroupPerson);
    }

    public void updateKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPersonFeign.updateKqDeviceGroupPerson(kqDeviceGroupPerson);
    }

    public void deleteKqDeviceGroupPerson(String id) {
        kqDeviceGroupPersonFeign.deleteKqDeviceGroupPerson(id);
    }

    public void deleteKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPersonFeign.deleteKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
    }
//-------------zy-------------------
    //获得该分组下的所有门禁设备
    public List<KqDevicePerson> getThisGroupAllDoorDevice(KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDevicePerson kqDevicePerson = new KqDevicePerson();
        kqDevicePerson.setGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDevicePerson.setSchoolId(mySchoolId());
        kqDevicePerson.setDeviceType("2");
        List<KqDevicePerson> kqDevicePersonList = kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
        return  kqDevicePersonList;
    }

    //通过人员类型快速绑定
    public Map<String, List<String>> fastBindDeviceAndPersonByGroupAndPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得学校密钥等信息
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //获得该分组下的所有设备
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //获得该类型所有已经中移动录入且未绑定该分组的人员id数组
        for (String s : kqDeviceGroupPerson.getFastBound()) {
            String type = s;
            List<String> personIdList = new ArrayList<>();
            if (type.equals("2")) {//人员类型(1教师，2学生，3其他职工)
                personIdList = findEnterStudentWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("1")) {
                personIdList = findEnterTeacherWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("3")) {
                personIdList = findEnterStaffWithUnboundByPersonType(kqDeviceGroupPerson);
            }
            if (personIdList.size() > 0) {//有要进行绑定的人员
                //查找出每个预绑定人员已经绑定的设备
                KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
                kqDeviceGroupPerson1.setSchoolId(mySchoolId());
                kqDeviceGroupPerson1.setPersonIdList(personIdList);
                kqDeviceGroupPerson1.setFactoryType("1");
                List<KqDeviceGroupPerson> eachPersonWithdeviceList = kqDeviceGroupPersonFeign.findPersonsBoundDevices(kqDeviceGroupPerson1);
                Map<String, List<String>> maps = eachPersonWithdeviceList.stream().collect(Collectors.toMap(KqDeviceGroupPerson::getPersonId, KqDeviceGroupPerson::getDeviceNoList, (key1, key2) -> key2));
                /*for (String id : maps.keySet()) {
                    maps.get(id).addAll(wantBindDevices);
                }*/
                List<String> firstTimeBindPerson = new ArrayList<>();
                for (String id : personIdList) {
                    if (maps.keySet().contains(id)) {
                        maps.get(id).addAll(wantBindDevices);
                    } else {
                        firstTimeBindPerson.add(id);
                    }
                }
                for (String id : firstTimeBindPerson) {
                    maps.put(id, wantBindDevices);
                }
                //// 将该人员数据分批次（100人）请求中移动绑定该分组所有设备&&&&&&.暂时无法实现分批次，先按依此绑定实现功能
                List<String> successMan = new ArrayList<>();
                List<String> failureMan = new ArrayList<>();
                for (String id : maps.keySet()) {
                    //请求中移动绑定人员
                    List<String> idList = new ArrayList<>();
                    idList.add(id);
                    List<String> deviceIds = maps.get(id);
                    Boolean aBoolean = kqSchoolZyDevicesService.boundDevices(isProd, idList, kqSchool, deviceIds);
                    if (aBoolean) {
                        successMan.add(id);
                    } else {
                        failureMan.add(id);
                    }
                }
                //将成功的批次生成人员与分组对应关系批量存入数据库
                beatchSavePersonByGroup(successMan, type, kqDeviceGroupPerson.getDeviceGroupId());
                //返回不成功的批次
                if (failureMan.size()>0){
                    failureMapWhitType.put(type, failureMan);
                }
            } else {
                failureMapWhitType.put(type, new ArrayList<String>());
                //System.out.println("该类型的人员已经全部绑定过了，不再重新绑定=======>>>>>>" + type);
            }

        }
        return failureMapWhitType;
    }

    //批量保存绑定数据
    private void beatchSavePersonByGroup(List<String> successMan, String type, String deviceGroupId) {
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = new ArrayList<>();
        for (String personId : successMan) {
            KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
            kqDeviceGroupPerson.setPersonId(personId);
            kqDeviceGroupPerson.setDeviceGroupId(deviceGroupId);
            kqDeviceGroupPerson.setPersonType(type);
            kqDeviceGroupPerson.setSchoolId(mySchoolId());
            kqDeviceGroupPerson.setFactoryType("1");
            kqDeviceGroupPersonList.add(kqDeviceGroupPerson);
        }
        //批量保存
        kqDeviceGroupPersonFeign.batchSaveKqDeviceGroupPerson(kqDeviceGroupPersonList);
    }

    //查找未绑定该分组的所有已注册学生
    private List<String> findEnterStudentWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("2");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //查找已经绑定的学生
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的学生
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setZyCheckStatus("0");
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<String> allStudentIdList = studentListByCondition.stream().map(Student::getId).collect(Collectors.toList());
        //从所有注册学生中剔除,得到未绑定该分组的所有已注册学生
        allStudentIdList.removeAll(boundStudentIdList);
        return allStudentIdList;
    }

    //查找未绑定该分组的所有已注册教师
    private List<String> findEnterTeacherWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("1");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //System.out.println(kqDeviceGroupPerson);
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的教师
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        teacher.setPager(new Pager());
        teacher.getPager().addExcludes("password");
        teacher.getPager().setPaging(false);
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> teacherImgList = teacherFeign.findTeacherImgListByCondition(teacher);
        List<String> allTeacherIdList = teacherImgList.stream().map(Teacher::getId).collect(Collectors.toList());
        //从所有注册教师中剔除,得到未绑定该分组的所有已注册教师
        allTeacherIdList.removeAll(boundTeacherIdList);
        return allTeacherIdList;
    }

    //查找未绑定该分组的所有已注册职工
    private List<String> findEnterStaffWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("3");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的职工
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        Pager pager = staff.getPager() == null ? new Pager().setPaging(false) : staff.getPager();
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        List<Teacher> staffListByCondition = staffFeign.findStaffListByCondition(staff);
        List<String> allStaffIdList = staffListByCondition.stream().map(Teacher::getId).collect(Collectors.toList());
        //System.out.println(allStaffIdList.size()+"<<<<查找本校所有已经注册中移动的职工");
        //从所有注册职工中剔除,得到未绑定该分组的所有已注册职工
        allStaffIdList.removeAll(boundTeacherIdList);
        return allStaffIdList;
    }

    //查找已绑定该分组的所有已注册教师
    public List<Teacher> findAllBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //查找已经绑定的教师
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("1");//(1教师，2学生，3其他职工)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("1");
        k.setPager(null);
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"已绑定该分组的所有已注册教师");
        //查找本校所有已经注册中移动的教师
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPager(new Pager());
        teacher.getPager().addExcludes("password");
        teacher.getPager().setPaging(false);
        teacher.getPager().setLike("name");
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            teacher.setName(kqDeviceGroupPerson.getName());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getWorkNumber())){
            teacher.setWorkNumber(kqDeviceGroupPerson.getWorkNumber());
        }
        List<Teacher> teacherImgList = teacherFeign.findTeacherImgListByCondition(teacher);
        //System.out.println(teacherImgList.size()+"该分组的所有已注册教师");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //查找已绑定该分组的所有已注册职工
    public List<Teacher> findAllBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //查找已经绑定的职工
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("3");//(1教师，2学生，3其他职工)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("1");
        k.setPager(null);
        //查找已经绑定的职工
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的职工
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        staff.setPager(new Pager());
        staff.getPager().addExcludes("password");
        staff.getPager().setPaging(false);
        staff.getPager().setLike("name");
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            staff.setName(kqDeviceGroupPerson.getName());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getWorkNumber())){
            staff.setWorkNumber(kqDeviceGroupPerson.getWorkNumber());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getDepartmentId())){
            staff.setDepartmentId(kqDeviceGroupPerson.getDepartmentId());
        }
        List<Teacher> staffListByCondition = staffFeign.findStaffListByCondition(staff);
        List<Teacher> staffWithThisGroup = staffListByCondition.stream().filter(s -> boundTeacherIdList.contains(s.getId())).collect(Collectors.toList());
        return staffWithThisGroup;
    }

    //查找已绑定该分组的所有已注册学生
    public List<Student> findAllBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //根据分组id和人员类型查找所有该分组下的学生id列表
        kqDeviceGroupPerson.getPager().setPaging(false);
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setSchoolId(mySchoolId());
        k.setPersonType("2");
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setFactoryType("1");
        //kqDeviceGroupPerson.setPersonType("2");//(1教师，2学生，3其他职工)
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        //System.out.println(kqDeviceGroupPersonList);
        //System.out.println("已经绑定的学生列表"+kqDeviceGroupPersonList.size());
        //根据学生id列表查找所有学生相关信息列表
        List<String> studentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        kqDeviceGroupPerson.setPersonIdList(studentIdList);
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        List<Student> studentWithThisGroup =new ArrayList<>();
        if (kqDeviceGroupPersonList.size()>0){
             studentWithThisGroup = studentFeign.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
            //System.out.println("已经绑定的学生列表====>"+studentWithThisGroup.size());
        }
        return studentWithThisGroup;
    }

    //获得所有未绑定该分组的已注册职工
    public List<Teacher> findAllUnBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //获得该类型所有已经绑定该分组的职工
        //查找已经绑定的职工
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("3");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("1");
        //查找已经绑定的职工
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有的已注册职工
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        staff.setPager(new Pager());
        staff.getPager().addExcludes("password");
        staff.getPager().setPaging(false);
        staff.getPager().setLike("name");
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            staff.setName(kqDeviceGroupPerson.getName());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getWorkNumber())){
            staff.setWorkNumber(kqDeviceGroupPerson.getWorkNumber());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getDepartmentId())){
            staff.setDepartmentId(kqDeviceGroupPerson.getDepartmentId());
        }
        List<Teacher> staffListByCondition = staffFeign.findStaffListByCondition(staff);
        List<Teacher> staffWithThisGroup = staffListByCondition.stream().filter(s -> !boundTeacherIdList.contains(s.getId())).collect(Collectors.toList());
        return staffWithThisGroup;
    }

    //获得所有未绑定该分组的已注册教师
    public List<Teacher> findAllUnBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //查找已经绑定的教师
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("1");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("1");
        //查找已经绑定该分组的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"已绑定的教师数量");
        //查找本校所有已经注册中移动的教师
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//类型 1：全部，2：通过，3：未校验或者校验不通过的
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPager(new Pager());
        teacher.getPager().addExcludes("password");
        teacher.getPager().setPaging(false);
        teacher.getPager().setLike("name");
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            teacher.setName(kqDeviceGroupPerson.getName());
        }
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getWorkNumber())){
            teacher.setWorkNumber(kqDeviceGroupPerson.getWorkNumber());
        }
        List<Teacher> teacherImgList = teacherFeign.findTeacherImgListByCondition(teacher);
        //System.out.println(teacherImgList.size()+"教师的数量");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> !boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //获得所有未绑定该分组的学生
    public List<Student> findAllUnBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("2");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setPager(null);
        kqDeviceGroupPerson1.setFactoryType("1");
        //查找已经绑定的学生
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已注册学生
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setZyCheckStatus("0");
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<Student> studentWithThisGroup = studentListByCondition.stream().filter(t -> !boundStudentIdList.contains(t.getId())).collect(Collectors.toList());
        //其他查询参数
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  seachByCondition(studentWithThisGroup,kqDeviceGroupPerson);
        }
        return studentWithThisGroup;
    }

    //其他查询参数
    public List<Student> seachByCondition(List<Student> studentWithThisGroup,KqDeviceGroupPerson kqDeviceGroupPerson){

        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getClassesId()!=null&&t.getClassesId().equals(kqDeviceGroupPerson.getClassesId())
                    &&t.getGradeId()!=null&& t.getGradeId().equals(kqDeviceGroupPerson.getGradeId())
                    && t.getName().matches(".*"+kqDeviceGroupPerson.getName()+".*")
            ).collect(Collectors.toList());
        }else if (StrUtil.isEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getGradeId()!=null&& t.getGradeId().equals(kqDeviceGroupPerson.getGradeId())
                    && t.getName().matches(".*"+kqDeviceGroupPerson.getName()+".*")
            ).collect(Collectors.toList());
        }else if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getClassesId()!=null&&t.getClassesId().equals(kqDeviceGroupPerson.getClassesId())
                    && t.getName().matches(".*"+kqDeviceGroupPerson.getName()+".*")
            ).collect(Collectors.toList());
        }else if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getClassesId()!=null&&t.getClassesId().equals(kqDeviceGroupPerson.getClassesId())
                    &&t.getGradeId()!=null&& t.getGradeId().equals(kqDeviceGroupPerson.getGradeId())
            ).collect(Collectors.toList());
        }else if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getClassesId()!=null&&t.getClassesId().equals(kqDeviceGroupPerson.getClassesId())).collect(Collectors.toList());
        }else if (StrUtil.isEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getGradeId()!=null&& t.getGradeId().equals(kqDeviceGroupPerson.getGradeId())).collect(Collectors.toList());
        }else if (StrUtil.isEmpty(kqDeviceGroupPerson.getClassesId())&&StrUtil.isEmpty(kqDeviceGroupPerson.getGradeId())&&StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  studentWithThisGroup.stream().filter(t->t.getName().matches(".*"+kqDeviceGroupPerson.getName()+".*")).collect(Collectors.toList());
        }
        return studentWithThisGroup;
    }

    //通过设备分组id批量绑定人员
    public Map<String, List<String>> BindDeviceAndPersonByGroupAndPersons(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得学校密钥等信息
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //获得该分组下的所有设备
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
       for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
           String type = t;
           //查找出每个预绑定人员已经绑定的设备
           List<String> list = kqDeviceGroupPerson.getMapForPersonIdListByType().get(type);
           List<String> personIdList = list.stream().filter(s -> s!=null&&!s.equals("")).collect(Collectors.toList());
           if (personIdList.size()==0){continue;}
           KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
           kqDeviceGroupPerson1.setSchoolId(mySchoolId());
           kqDeviceGroupPerson1.setPersonIdList(personIdList);
           kqDeviceGroupPerson1.setFactoryType("1");
           List<KqDeviceGroupPerson> eachPersonWithdeviceList = kqDeviceGroupPersonFeign.findPersonsBoundDevices(kqDeviceGroupPerson1);
           Map<String, List<String>> maps = eachPersonWithdeviceList.stream().collect(Collectors.toMap(KqDeviceGroupPerson::getPersonId, KqDeviceGroupPerson::getDeviceNoList, (key1, key2) -> key2));
           List<String> firstTimeBindPerson = new ArrayList<>();
           for (String id : personIdList) {
               if (maps.keySet().contains(id)) {
                   maps.get(id).addAll(wantBindDevices);
               } else {
                   firstTimeBindPerson.add(id);
               }
           }
           for (String id : firstTimeBindPerson) {
               maps.put(id, wantBindDevices);
           }
           List<String> successMan = new ArrayList<>();
           List<String> failureMan = new ArrayList<>();
           for (String id : maps.keySet()) {
               //请求中移动绑定人员
               List<String> idList = new ArrayList<>();
               idList.add(id);
               List<String> deviceIds = maps.get(id);
               Boolean aBoolean = kqSchoolZyDevicesService.boundDevices(isProd, idList, kqSchool, deviceIds);
               if (aBoolean) {
                   successMan.add(id);
               } else {
                   failureMan.add(id);
               }
           }
           //将成功的批次生成人员与分组对应关系批量存入数据库
           beatchSavePersonByGroup(successMan, type, kqDeviceGroupPerson.getDeviceGroupId());
           failureMapWhitType.put(type, failureMan);
       }
        //返回不成功的批次
        return failureMapWhitType;
    }

    //解绑该分组下一批人员
    public Map<String, List<String>> personsUnbindDeviceByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得学校密钥等信息
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //获得该分组下的所有门禁设备
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
            String type = t;
            //查找出每个预绑定人员已经绑定的设备
            List<String> list = kqDeviceGroupPerson.getMapForPersonIdListByType().get(type);
            List<String> personIdList = list.stream().filter(s -> s!=null&&!s.equals("")).collect(Collectors.toList());
            if (personIdList.size()==0){continue;}
            KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
            kqDeviceGroupPerson1.setSchoolId(mySchoolId());
            kqDeviceGroupPerson1.setPersonIdList(personIdList);
            kqDeviceGroupPerson1.setFactoryType("1");
            List<KqDeviceGroupPerson> eachPersonWithdeviceList = kqDeviceGroupPersonFeign.findPersonsBoundDevices(kqDeviceGroupPerson1);
            if (eachPersonWithdeviceList.size()==0){
                List<String> successMan = new ArrayList<>();
                successMan.addAll(personIdList);
                beatchDeletePersonsWithGroup(successMan,type,kqDeviceGroupPerson.getDeviceGroupId());
                continue;
            }
            Map<String, List<String>> maps = eachPersonWithdeviceList.stream().collect(Collectors.toMap(KqDeviceGroupPerson::getPersonId, KqDeviceGroupPerson::getDeviceNoList, (key1, key2) -> key2));
            List<String> firstTimeBindPerson = new ArrayList<>();
            for (String id : personIdList) {
                if (maps.keySet().contains(id)) {
                    //maps.get(id).addAll(wantBindDevices);
                    maps.get(id).removeAll(wantBindDevices);
                } else {
                    firstTimeBindPerson.add(id);
                }
            }
            for (String id : firstTimeBindPerson) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("00000000000000");
                maps.put(id,strings);
            }
            List<String> successMan = new ArrayList<>();
            List<String> failureMan = new ArrayList<>();
            for (String id : maps.keySet()) {
                //请求中移动绑定人员
                List<String> idList = new ArrayList<>();
                idList.add(id);
                List<String> deviceIds = maps.get(id);
                Boolean aBoolean = kqSchoolZyDevicesService.boundDevices(isProd, idList, kqSchool, deviceIds);
                if (aBoolean) {
                    successMan.add(id);
                } else {
                    failureMan.add(id);
                }
            }
            //将成功的批次生成人员与分组对应关系批量存入数据库
            if (successMan.size()>0){
                beatchDeletePersonsWithGroup(successMan,type,kqDeviceGroupPerson.getDeviceGroupId());
            }
            if (failureMan.size()>0){
                failureMapWhitType.put(type, failureMan);
            }
        }
        //返回不成功的批次
        return failureMapWhitType;
    }

    public void beatchDeletePersonsWithGroup(List<String> successMan, String type, String deviceGroupId) {
        for (String id :successMan){
            KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
            kqDeviceGroupPerson.setPersonType(type);
            kqDeviceGroupPerson.setDeviceGroupId(deviceGroupId);
            kqDeviceGroupPerson.setPersonId(id);
            kqDeviceGroupPerson.setFactoryType("1");
            kqDeviceGroupPersonFeign.deleteKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
        }
    }

    //重新绑定某一分组下的人员和设备
    public List<String> deviceChangeGroup(String nowGroupId){
        List<String> successMan = new ArrayList<>();
        List<String> failureMan = new ArrayList<>();
        //获得学校密钥等信息
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return failureMan;
        }
        //获得绑定该分组的所有人
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setDeviceGroupId(nowGroupId);
        kqDeviceGroupPerson.setFactoryType("1");
        List<KqDeviceGroupPerson> nowGroupPersons = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> personIdList = nowGroupPersons.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //获得该分组下的所有门禁设备
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //该分组所有人绑定更新后的该分组所有设备
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setPersonIdList(personIdList);
        kqDeviceGroupPerson1.setFactoryType("1");
        List<KqDeviceGroupPerson> eachPersonWithdeviceList = kqDeviceGroupPersonFeign.findPersonsBoundDevices(kqDeviceGroupPerson1);
        Map<String, List<String>> maps = eachPersonWithdeviceList.stream().collect(Collectors.toMap(KqDeviceGroupPerson::getPersonId, KqDeviceGroupPerson::getDeviceNoList, (key1, key2) -> key2));
        List<String> firstTimeBindPerson = new ArrayList<>();
        for (String id : personIdList) {
            if (maps.keySet().contains(id)) {
               // maps.get(id).addAll(wantBindDevices);
            } else {
                firstTimeBindPerson.add(id);
            }
        }
        for (String id : firstTimeBindPerson) {
            maps.put(id, wantBindDevices);
        }

        for (String id : maps.keySet()) {
            //请求中移动绑定人员
            List<String> idList = new ArrayList<>();
            idList.add(id);
            List<String> deviceIds = maps.get(id);
            Boolean aBoolean = kqSchoolZyDevicesService.boundDevices(isProd, idList, kqSchool, deviceIds);
            if (aBoolean) {
                successMan.add(id);
            } else {
                failureMan.add(id);
            }
        }
        return failureMan;
    }

}
