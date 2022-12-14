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
    //???????????????????????????????????????
    public List<KqDevicePerson> getThisGroupAllDoorDevice(KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDevicePerson kqDevicePerson = new KqDevicePerson();
        kqDevicePerson.setGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDevicePerson.setSchoolId(mySchoolId());
        kqDevicePerson.setDeviceType("2");
        List<KqDevicePerson> kqDevicePersonList = kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
        return  kqDevicePersonList;
    }

    //??????????????????????????????
    public Map<String, List<String>> fastBindDeviceAndPersonByGroupAndPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //???????????????????????????
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //?????????????????????????????????
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //????????????????????????????????????????????????????????????????????????id??????
        for (String s : kqDeviceGroupPerson.getFastBound()) {
            String type = s;
            List<String> personIdList = new ArrayList<>();
            if (type.equals("2")) {//????????????(1?????????2?????????3????????????)
                personIdList = findEnterStudentWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("1")) {
                personIdList = findEnterTeacherWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("3")) {
                personIdList = findEnterStaffWithUnboundByPersonType(kqDeviceGroupPerson);
            }
            if (personIdList.size() > 0) {//???????????????????????????
                //???????????????????????????????????????????????????
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
                //// ??????????????????????????????100????????????????????????????????????????????????&&&&&&.????????????????????????????????????????????????????????????
                List<String> successMan = new ArrayList<>();
                List<String> failureMan = new ArrayList<>();
                for (String id : maps.keySet()) {
                    //???????????????????????????
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
                //????????????????????????????????????????????????????????????????????????
                beatchSavePersonByGroup(successMan, type, kqDeviceGroupPerson.getDeviceGroupId());
                //????????????????????????
                if (failureMan.size()>0){
                    failureMapWhitType.put(type, failureMan);
                }
            } else {
                failureMapWhitType.put(type, new ArrayList<String>());
                //System.out.println("???????????????????????????????????????????????????????????????=======>>>>>>" + type);
            }

        }
        return failureMapWhitType;
    }

    //????????????????????????
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
        //????????????
        kqDeviceGroupPersonFeign.batchSaveKqDeviceGroupPerson(kqDeviceGroupPersonList);
    }

    //????????????????????????????????????????????????
    private List<String> findEnterStudentWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("2");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setZyCheckStatus("0");
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<String> allStudentIdList = studentListByCondition.stream().map(Student::getId).collect(Collectors.toList());
        //??????????????????????????????,????????????????????????????????????????????????
        allStudentIdList.removeAll(boundStudentIdList);
        return allStudentIdList;
    }

    //????????????????????????????????????????????????
    private List<String> findEnterTeacherWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("1");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //System.out.println(kqDeviceGroupPerson);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
        teacher.setPager(new Pager());
        teacher.getPager().addExcludes("password");
        teacher.getPager().setPaging(false);
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> teacherImgList = teacherFeign.findTeacherImgListByCondition(teacher);
        List<String> allTeacherIdList = teacherImgList.stream().map(Teacher::getId).collect(Collectors.toList());
        //??????????????????????????????,????????????????????????????????????????????????
        allTeacherIdList.removeAll(boundTeacherIdList);
        return allTeacherIdList;
    }

    //????????????????????????????????????????????????
    private List<String> findEnterStaffWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("3");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("1");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        Pager pager = staff.getPager() == null ? new Pager().setPaging(false) : staff.getPager();
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
        List<Teacher> staffListByCondition = staffFeign.findStaffListByCondition(staff);
        List<String> allStaffIdList = staffListByCondition.stream().map(Teacher::getId).collect(Collectors.toList());
        //System.out.println(allStaffIdList.size()+"<<<<????????????????????????????????????????????????");
        //??????????????????????????????,????????????????????????????????????????????????
        allStaffIdList.removeAll(boundTeacherIdList);
        return allStaffIdList;
    }

    //????????????????????????????????????????????????
    public List<Teacher> findAllBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("1");//(1?????????2?????????3????????????)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("1");
        k.setPager(null);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"??????????????????????????????????????????");
        //????????????????????????????????????????????????
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
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
        //System.out.println(teacherImgList.size()+"?????????????????????????????????");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //????????????????????????????????????????????????
    public List<Teacher> findAllBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("3");//(1?????????2?????????3????????????)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("1");
        k.setPager(null);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
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

    //????????????????????????????????????????????????
    public List<Student> findAllBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //????????????id????????????????????????????????????????????????id??????
        kqDeviceGroupPerson.getPager().setPaging(false);
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setSchoolId(mySchoolId());
        k.setPersonType("2");
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setFactoryType("1");
        //kqDeviceGroupPerson.setPersonType("2");//(1?????????2?????????3????????????)
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        //System.out.println(kqDeviceGroupPersonList);
        //System.out.println("???????????????????????????"+kqDeviceGroupPersonList.size());
        //????????????id??????????????????????????????????????????
        List<String> studentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        kqDeviceGroupPerson.setPersonIdList(studentIdList);
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        List<Student> studentWithThisGroup =new ArrayList<>();
        if (kqDeviceGroupPersonList.size()>0){
             studentWithThisGroup = studentFeign.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
            //System.out.println("???????????????????????????====>"+studentWithThisGroup.size());
        }
        return studentWithThisGroup;
    }

    //????????????????????????????????????????????????
    public List<Teacher> findAllUnBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????????????????????????????
        //???????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("3");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("1");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setCheckStatus("2");
        staff.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
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

    //????????????????????????????????????????????????
    public List<Teacher> findAllUnBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("1");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("1");
        //????????????????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"????????????????????????");
        //????????????????????????????????????????????????
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setCheckStatus("2");
        teacher.setSearchCheckType("2");//?????? 1????????????2????????????3????????????????????????????????????
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
        //System.out.println(teacherImgList.size()+"???????????????");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> !boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //???????????????????????????????????????
    public List<Student> findAllUnBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("2");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setPager(null);
        kqDeviceGroupPerson1.setFactoryType("1");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //?????????????????????????????????
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setZyCheckStatus("0");
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<Student> studentWithThisGroup = studentListByCondition.stream().filter(t -> !boundStudentIdList.contains(t.getId())).collect(Collectors.toList());
        //??????????????????
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  seachByCondition(studentWithThisGroup,kqDeviceGroupPerson);
        }
        return studentWithThisGroup;
    }

    //??????????????????
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

    //??????????????????id??????????????????
    public Map<String, List<String>> BindDeviceAndPersonByGroupAndPersons(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //???????????????????????????
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //?????????????????????????????????
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
       for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
           String type = t;
           //???????????????????????????????????????????????????
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
               //???????????????????????????
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
           //????????????????????????????????????????????????????????????????????????
           beatchSavePersonByGroup(successMan, type, kqDeviceGroupPerson.getDeviceGroupId());
           failureMapWhitType.put(type, failureMan);
       }
        //????????????????????????
        return failureMapWhitType;
    }

    //??????????????????????????????
    public Map<String, List<String>> personsUnbindDeviceByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //???????????????????????????
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return null;
        }
        //???????????????????????????????????????
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
            String type = t;
            //???????????????????????????????????????????????????
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
                //???????????????????????????
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
            //????????????????????????????????????????????????????????????????????????
            if (successMan.size()>0){
                beatchDeletePersonsWithGroup(successMan,type,kqDeviceGroupPerson.getDeviceGroupId());
            }
            if (failureMan.size()>0){
                failureMapWhitType.put(type, failureMan);
            }
        }
        //????????????????????????
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

    //?????????????????????????????????????????????
    public List<String> deviceChangeGroup(String nowGroupId){
        List<String> successMan = new ArrayList<>();
        List<String> failureMan = new ArrayList<>();
        //???????????????????????????
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return failureMan;
        }
        //?????????????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setDeviceGroupId(nowGroupId);
        kqDeviceGroupPerson.setFactoryType("1");
        List<KqDeviceGroupPerson> nowGroupPersons = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> personIdList = nowGroupPersons.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //???????????????????????????????????????
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //?????????????????????????????????????????????????????????
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
            //???????????????????????????
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
