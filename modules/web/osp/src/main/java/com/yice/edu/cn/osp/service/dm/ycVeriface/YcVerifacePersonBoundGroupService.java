package com.yice.edu.cn.osp.service.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceDoorChangeResBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceDeviceFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifacePersonEnterFeign;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class YcVerifacePersonBoundGroupService {
    @Autowired
    private KqDeviceGroupPersonFeign kqDeviceGroupPersonFeign;
    @Autowired
    private YcVerifaceDeviceFeign ycVerifaceDeviceFeign;
    @Autowired
    private YcVerifacePersonEnterFeign ycVerifacePersonEnterFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StaffFeign staffFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    YcVerifaceDeviceService ycVerifaceDeviceService;


    @CreateCache(name = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST, expire = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String, YcVerifaceDoorChangeResBean> ycVerifaceDoorDevicePersonChangeList;//key:??????id???value???????????????

    //-------------yc-------------------
    //???????????????????????????yc????????????
    public List<YcVerifaceDevice> ycGetThisGroupAllDoorDevice(KqDeviceGroupPerson kqDeviceGroupPerson) {
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycDevicePersonList = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        return  ycDevicePersonList;
    }

    //??????????????????????????????
    public Map<String, List<String>> ycFastBindDeviceAndPersonByGroupAndPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> boundPersonList = new ArrayList<>();
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //?????????????????????????????????
        List<YcVerifaceDevice> groupDeviceList = ycGetThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = groupDeviceList.stream().map(YcVerifaceDevice::getId).collect(Collectors.toList());
        //????????????????????????????????????????????????????????????????????????id??????
        for (String s : kqDeviceGroupPerson.getFastBound()) {
            String type = s;
            List<String> personIdList = new ArrayList<>();
            if (type.equals("2")) {//????????????(1?????????2?????????3????????????)
                personIdList = ycFindEnterStudentWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("1")) {
                personIdList = ycFindEnterTeacherWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("3")) {
                personIdList = ycFindEnterStaffWithUnboundByPersonType(kqDeviceGroupPerson);
            }
            if (personIdList.size() > 0) {//???????????????????????????
                for (String personId:personIdList){
                    KqDeviceGroupPerson boundPerson = new KqDeviceGroupPerson();
                    boundPerson.setFactoryType("0");
                    boundPerson.setSchoolId(mySchoolId());
                    boundPerson.setPersonId(personId);
                    boundPerson.setPersonType(type);
                    boundPerson.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
                    boundPersonList.add(boundPerson);
                }
            } else {
                failureMapWhitType.put(type, new ArrayList<String>());
                //System.out.println("???????????????????????????????????????????????????????????????=======>>>>>>" + type);
            }


        }
        if (CollectionUtil.isNotEmpty(boundPersonList)){
            kqDeviceGroupPersonFeign.batchSaveKqDeviceGroupPerson(boundPersonList);
            toldDeviceInsertPeople(boundPersonList, kqDeviceGroupPerson.getDeviceGroupId());
         }

        return failureMapWhitType;
    }

    private void toldDeviceInsertPeople( List<KqDeviceGroupPerson> boundPersonList,String groupId){
        //System.out.println(boundPersonList);
        //send bound messge to each device of group
        //save changes to redis and send hint to device
        //todo ??????????????????websoket????????????????????????
        //??????????????????????????????
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setDeviceGroupId(groupId);
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)){
            for (YcVerifaceDevice device:ycVerifaceDeviceListByCondition){
                if (StrUtil.isEmpty(device.getDeviceSign())){
                    //System.out.println("???????????????????????????!");
                    continue;
                }
                device.setToGetfeasList(boundPersonList);
                YcVerifaceDoorChangeResBean ycVerifaceDoorChangeResBean = ycVerifaceDeviceService.ycVerifaceDoorPeoplePullFea(device);
                if (ycVerifaceDoorChangeResBean!=null&&CollectionUtil.isNotEmpty(ycVerifaceDoorChangeResBean.getResetList())){
                    YcVerifaceDoorChangeResBean   doorChangeResBean =  new YcVerifaceDoorChangeResBean();
                    List<YcVerifacePersonBean> insetList = doorChangeResBean.getInsetList();
                    insetList.addAll(ycVerifaceDoorChangeResBean.getResetList());
                    insetList = insetList.stream().filter(distinctByKey(n -> n.getUserId())).collect(Collectors.toList());
                    doorChangeResBean.setInsetList(insetList);
                    String cacheKey = device.getDeviceSign()+"&"+System.currentTimeMillis()+"&"+Math.floor(Math.random()*1000);
                    ycVerifaceDoorDevicePersonChangeList.put(cacheKey,doorChangeResBean);
                    //System.out.println("???????????????????????????"+doorChangeResBean);
                    device.setCacheKey(cacheKey);
                    //?????????????????????
                    ycVerifaceDeviceService.toldDevicePeopleListHaveChange(device,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_INSERT);
                }
            }
        }
    }

    private void toldDeviceDeletePeople( List<KqDeviceGroupPerson> boundPersonList,String groupId){
        //System.out.println(boundPersonList);
        //send bound messge to each device of group
        //save changes to redis and send hint to device
        //todo ??????????????????websoket??????????????????
        //??????????????????????????????
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setDeviceGroupId(groupId);
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)){
            for (YcVerifaceDevice device:ycVerifaceDeviceListByCondition){
                if (StrUtil.isEmpty(device.getDeviceSign())){
                    //System.out.println("???????????????????????????!");
                    continue;
                }
                device.setToGetfeasList(boundPersonList);
                YcVerifaceDoorChangeResBean ycVerifaceDoorChangeResBean = ycVerifaceDeviceService.ycVerifaceDoorPeoplePullFea(device);
                if (ycVerifaceDoorChangeResBean!=null&&CollectionUtil.isNotEmpty(ycVerifaceDoorChangeResBean.getResetList())){
                    YcVerifaceDoorChangeResBean   doorChangeResBean =  new YcVerifaceDoorChangeResBean();
                    List<YcVerifacePersonBean> deleteList = doorChangeResBean.getDeleteList();
                    deleteList.addAll(ycVerifaceDoorChangeResBean.getResetList());
                    deleteList = deleteList.stream().filter(distinctByKey(n -> n.getUserId())).collect(Collectors.toList());
                    doorChangeResBean.setDeleteList(deleteList);
                    String cacheKey = device.getDeviceSign()+"&"+System.currentTimeMillis()+"&"+Math.floor(Math.random()*1000);
                    ycVerifaceDoorDevicePersonChangeList.put(cacheKey,doorChangeResBean);
                    device.setCacheKey(cacheKey);
                    //System.out.println("???????????????????????????"+doorChangeResBean);
                    //?????????????????????
                    ycVerifaceDeviceService.toldDevicePeopleListHaveChange(device,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_DELETE);
                }
            }
        }
    }

    static  <T> Predicate<T> distinctByKey(Function<? super T,?> keyExtractor){
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE) == null;
    }

    //????????????????????????????????????????????????
    private List<String> ycFindEnterStudentWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("2");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //?????????????????????????????????yc?????????
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        List<YcVerifacePersonEnter> studentListByCondition = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allStudentIdList = studentListByCondition.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //??????????????????????????????,????????????????????????????????????????????????
        allStudentIdList.removeAll(boundStudentIdList);
        return allStudentIdList;
    }

    //????????????????????????????????????????????????
    private List<String> ycFindEnterTeacherWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("1");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //System.out.println(kqDeviceGroupPerson);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER);
        List<YcVerifacePersonEnter> teacherImgList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allTeacherIdList = teacherImgList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //??????????????????????????????,????????????????????????????????????????????????
        allTeacherIdList.removeAll(boundTeacherIdList);
        return allTeacherIdList;
    }

    //????????????????????????????????????????????????
    private List<String> ycFindEnterStaffWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("3");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        List<YcVerifacePersonEnter> staffListByCondition = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allStaffIdList = staffListByCondition.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //System.out.println(allStaffIdList.size()+"<<<<????????????????????????????????????????????????");
        //??????????????????????????????,????????????????????????????????????????????????
        allStaffIdList.removeAll(boundTeacherIdList);
        return allStaffIdList;
    }

    //????????????????????????????????????????????????
    public List<Teacher> ycFindAllBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("1");//(1?????????2?????????3????????????)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("0");
        k.setPager(null);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"??????????????????????????????????????????");
        //????????????????????????????????????????????????
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
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
        List<Teacher> teacherList = teacherFeign.findTeacherListByCondition(teacher);
        //System.out.println(teacherImgList.size()+"?????????????????????????????????");
        List<Teacher> teacherWithThisGroup = teacherList.stream().filter(t -> boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //????????????????????????????????????????????????
    public List<Teacher> ycFindAllBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //???????????????????????????
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("3");//(1?????????2?????????3????????????)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("0");
        k.setPager(null);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????????????????
        Teacher staff = new Teacher();
        staff.setPersonType(Constant.PERSON_TYPE.STAFF);
        staff.setSchoolId(mySchoolId());
        staff.setPager(new Pager());
        staff.getPager().addExcludes("password");
        staff.getPager().setPaging(false);
        staff.getPager().setLike("name");
        staff.setStatus("40");
        if (staff.getSearchCheckType()==null){
            staff.setSearchCheckType("1");
        }
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
    public List<Student> ycFindAllBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //????????????id????????????????????????????????????????????????id??????
        kqDeviceGroupPerson.getPager().setPaging(false);
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setSchoolId(mySchoolId());
        k.setPersonType("2");
        k.setFactoryType("0");
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        //kqDeviceGroupPerson.setPersonType("2");//(1?????????2?????????3????????????)
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
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
    public List<Teacher> ycFindAllUnBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterTeacherIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //???????????????????????????????????????????????????
        //???????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("3");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setFactoryType("0");
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //????????????????????????????????????
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setStatus("40");
        if (staff.getSearchCheckType()==null){
            staff.setSearchCheckType("1");//?????? 1????????????2????????????3????????????????????????????????????
        }
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
        List<Teacher> staffWithThisGroup = staffListByCondition.stream().filter(s -> enterTeacherIdList.contains(s.getId())&&!boundTeacherIdList.contains(s.getId())).collect(Collectors.toList());
        return staffWithThisGroup;
    }

    //????????????????????????????????????????????????
    public List<Teacher> ycFindAllUnBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterTeacherIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //???????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("1");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("0");
        //????????????????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"????????????????????????");
        //????????????????????????????????????????????????
        Teacher teacher = new Teacher();
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
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
        List<Teacher> teacherImgList = teacherFeign.findTeacherListByCondition(teacher);
        //System.out.println(teacherImgList.size()+"???????????????");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> enterTeacherIdList.contains(t.getId())&&!boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //????????????????????????????????????????????????
    public List<Student> ycFindAllUnBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterStudentIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //bound group student
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("2");//(1?????????2?????????3????????????)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("0");
        kqDeviceGroupPerson1.setPager(null);
        //???????????????????????????
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //?????????????????????????????????
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<Student> studentWithThisGroup = studentListByCondition.stream().filter(t -> enterStudentIdList.contains(t.getId())&&!boundStudentIdList.contains(t.getId())).collect(Collectors.toList());
        //??????????????????
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  ycSeachByCondition(studentWithThisGroup,kqDeviceGroupPerson);
        }
        return studentWithThisGroup;
    }
    //??????????????????
    public List<Student> ycSeachByCondition(List<Student> studentWithThisGroup,KqDeviceGroupPerson kqDeviceGroupPerson){

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
    public Map<String, List<String>> ycBindDeviceAndPersonByGroupAndPersons(KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> boundPersonList = new ArrayList<>();
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //?????????????????????????????????
        List<YcVerifaceDevice> kqDevicePersonList = ycGetThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(YcVerifaceDevice::getId).collect(Collectors.toList());
        for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
            String type = t;
            List<String> list = kqDeviceGroupPerson.getMapForPersonIdListByType().get(type);
            List<String> personIdList = list.stream().filter(s -> s!=null&&!s.equals("")).collect(Collectors.toList());
            if (personIdList.size()==0){continue;}
            for (String id :personIdList){
                KqDeviceGroupPerson boundPerson = new KqDeviceGroupPerson();
                boundPerson.setSchoolId(mySchoolId());
                boundPerson.setPersonIdList(personIdList);
                boundPerson.setFactoryType("0");
                boundPerson.setPersonType(type);
                boundPerson.setPersonId(id);
                boundPerson.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
                boundPersonList.add(boundPerson);
            }
        }
        //????????????????????????????????????????????????????????????????????????
        if (CollectionUtil.isNotEmpty(boundPersonList)){
            kqDeviceGroupPersonFeign.batchSaveKqDeviceGroupPerson(boundPersonList);
            //??????????????????websoket??????????????????
            toldDeviceInsertPeople(boundPersonList, kqDeviceGroupPerson.getDeviceGroupId());
        }
        //????????????????????????
        return failureMapWhitType;
    }


    //??????????????????????????????
    public Map<String, List<String>> ycPersonsUnbindDeviceByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //???????????????????????????????????????
        List<YcVerifaceDevice> kqDevicePersonList = ycGetThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(YcVerifaceDevice::getId).collect(Collectors.toList());
        List<KqDeviceGroupPerson> allPersonList = new ArrayList<>();
        for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
            String type = t;
            //???????????????????????????????????????????????????
            List<String> list = kqDeviceGroupPerson.getMapForPersonIdListByType().get(type);
            List<String> personIdList = list.stream().filter(s -> s!=null&&!s.equals("")).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(personIdList)){continue;}
            for (String personId:personIdList){
                KqDeviceGroupPerson deleteMan = new KqDeviceGroupPerson();
                deleteMan.setPersonType(type);
                deleteMan.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
                deleteMan.setPersonId(personId);
                deleteMan.setFactoryType("0");
                allPersonList.add(deleteMan);
            }
            //delete persons form the group
            ycBeatchDeletePersonsWithGroup(personIdList,type,kqDeviceGroupPerson.getDeviceGroupId());
        }
        //send change to device
        //save changes to redis and send hint to device
        //todo
        toldDeviceDeletePeople(allPersonList,kqDeviceGroupPerson.getDeviceGroupId());
        //????????????????????????
        return failureMapWhitType;
    }

    public void ycBeatchDeletePersonsWithGroup(List<String> successMan, String type, String deviceGroupId) {
        for (String id :successMan){
            KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
            kqDeviceGroupPerson.setPersonType(type);
            kqDeviceGroupPerson.setDeviceGroupId(deviceGroupId);
            kqDeviceGroupPerson.setPersonId(id);
            kqDeviceGroupPerson.setFactoryType("0");
            kqDeviceGroupPersonFeign.deleteKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
        }
    }
    /*//?????????????????????????????????????????????
    public List<String> ycDeviceChangeGroup(String nowGroupId){
        List<String> successMan = new ArrayList<>();
        List<String> failureMan = new ArrayList<>();
        //?????????????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setDeviceGroupId(nowGroupId);
        List<KqDeviceGroupPerson> nowGroupPersons = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> personIdList = nowGroupPersons.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //???????????????????????????????????????
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //?????????????????????????????????????????????????????????
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setPersonIdList(personIdList);
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
            Boolean aBoolean =true;//= kqSchoolZyDevicesService.boundDevices(isProd, idList, kqSchool, deviceIds);
            if (aBoolean) {
                successMan.add(id);
            } else {
                failureMan.add(id);
            }
        }
        return failureMan;
    }*/
}
