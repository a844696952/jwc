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
    private Cache<String, YcVerifaceDoorChangeResBean> ycVerifaceDoorDevicePersonChangeList;//key:设备id，value：变化列表

    //-------------yc-------------------
    //获得该分组下的所有yc门禁设备
    public List<YcVerifaceDevice> ycGetThisGroupAllDoorDevice(KqDeviceGroupPerson kqDeviceGroupPerson) {
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycDevicePersonList = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        return  ycDevicePersonList;
    }

    //通过人员类型快速绑定
    public Map<String, List<String>> ycFastBindDeviceAndPersonByGroupAndPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> boundPersonList = new ArrayList<>();
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得该分组下的所有设备
        List<YcVerifaceDevice> groupDeviceList = ycGetThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = groupDeviceList.stream().map(YcVerifaceDevice::getId).collect(Collectors.toList());
        //获得该类型所有已经中移动录入且未绑定该分组的人员id数组
        for (String s : kqDeviceGroupPerson.getFastBound()) {
            String type = s;
            List<String> personIdList = new ArrayList<>();
            if (type.equals("2")) {//人员类型(1教师，2学生，3其他职工)
                personIdList = ycFindEnterStudentWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("1")) {
                personIdList = ycFindEnterTeacherWithUnboundByPersonType(kqDeviceGroupPerson);
            } else if (type.equals("3")) {
                personIdList = ycFindEnterStaffWithUnboundByPersonType(kqDeviceGroupPerson);
            }
            if (personIdList.size() > 0) {//有要进行绑定的人员
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
                //System.out.println("该类型的人员已经全部绑定过了，不再重新绑定=======>>>>>>" + type);
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
        //todo 保存到缓存，websoket通知门禁设备来取
        //找到分组下的所有设备
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setDeviceGroupId(groupId);
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)){
            for (YcVerifaceDevice device:ycVerifaceDeviceListByCondition){
                if (StrUtil.isEmpty(device.getDeviceSign())){
                    //System.out.println("设备未绑定实际设备!");
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
                    //System.out.println("保存变化至缓存成功"+doorChangeResBean);
                    device.setCacheKey(cacheKey);
                    //通知设备来拉取
                    ycVerifaceDeviceService.toldDevicePeopleListHaveChange(device,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_INSERT);
                }
            }
        }
    }

    private void toldDeviceDeletePeople( List<KqDeviceGroupPerson> boundPersonList,String groupId){
        //System.out.println(boundPersonList);
        //send bound messge to each device of group
        //save changes to redis and send hint to device
        //todo 保存到缓存，websoket通知设备来取
        //找到分组下的所有设备
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setDeviceGroupId(groupId);
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)){
            for (YcVerifaceDevice device:ycVerifaceDeviceListByCondition){
                if (StrUtil.isEmpty(device.getDeviceSign())){
                    //System.out.println("设备未绑定实际设备!");
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
                    //System.out.println("保存变化至缓存成功"+doorChangeResBean);
                    //通知设备来拉取
                    ycVerifaceDeviceService.toldDevicePeopleListHaveChange(device,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_DELETE);
                }
            }
        }
    }

    static  <T> Predicate<T> distinctByKey(Function<? super T,?> keyExtractor){
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE) == null;
    }

    //查找未绑定该分组的所有已注册学生
    private List<String> ycFindEnterStudentWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("2");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //查找已经绑定的学生
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中yc的学生
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        List<YcVerifacePersonEnter> studentListByCondition = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allStudentIdList = studentListByCondition.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //从所有注册学生中剔除,得到未绑定该分组的所有已注册学生
        allStudentIdList.removeAll(boundStudentIdList);
        return allStudentIdList;
    }

    //查找未绑定该分组的所有已注册教师
    private List<String> ycFindEnterTeacherWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("1");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //System.out.println(kqDeviceGroupPerson);
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的教师
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER);
        List<YcVerifacePersonEnter> teacherImgList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allTeacherIdList = teacherImgList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //从所有注册教师中剔除,得到未绑定该分组的所有已注册教师
        allTeacherIdList.removeAll(boundTeacherIdList);
        return allTeacherIdList;
    }

    //查找未绑定该分组的所有已注册职工
    private List<String> ycFindEnterStaffWithUnboundByPersonType(KqDeviceGroupPerson kqDeviceGroupPerson) {
        kqDeviceGroupPerson.setPersonType("3");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setFactoryType("0");
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的职工
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        List<YcVerifacePersonEnter> staffListByCondition = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
        List<String> allStaffIdList = staffListByCondition.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //System.out.println(allStaffIdList.size()+"<<<<查找本校所有已经注册中移动的职工");
        //从所有注册职工中剔除,得到未绑定该分组的所有已注册职工
        allStaffIdList.removeAll(boundTeacherIdList);
        return allStaffIdList;
    }

    //查找已绑定该分组的所有已注册教师
    public List<Teacher> ycFindAllBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //查找已经绑定的教师
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("1");//(1教师，2学生，3其他职工)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("0");
        k.setPager(null);
        //查找已经绑定的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"已绑定该分组的所有已注册教师");
        //查找本校所有已经注册中移动的教师
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
        //System.out.println(teacherImgList.size()+"该分组的所有已注册教师");
        List<Teacher> teacherWithThisGroup = teacherList.stream().filter(t -> boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //查找已绑定该分组的所有已注册职工
    public List<Teacher> ycFindAllBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //查找已经绑定的职工
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        k.setPersonType("3");//(1教师，2学生，3其他职工)
        k.setSchoolId(mySchoolId());
        k.setFactoryType("0");
        k.setPager(null);
        //查找已经绑定的职工
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已经注册中移动的职工
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

    //查找已绑定该分组的所有已注册学生
    public List<Student> ycFindAllBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //根据分组id和人员类型查找所有该分组下的学生id列表
        kqDeviceGroupPerson.getPager().setPaging(false);
        KqDeviceGroupPerson k = new KqDeviceGroupPerson();
        k.setSchoolId(mySchoolId());
        k.setPersonType("2");
        k.setFactoryType("0");
        k.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        //kqDeviceGroupPerson.setPersonType("2");//(1教师，2学生，3其他职工)
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(k);
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
    public List<Teacher> ycFindAllUnBoundStaffByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterTeacherIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //获得该类型所有已经绑定该分组的职工
        //查找已经绑定的职工
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("3");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setFactoryType("0");
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        //查找已经绑定的职工
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有的已注册职工
        Teacher staff = new Teacher();
        staff.setSchoolId(mySchoolId());
        staff.setStatus("40");
        if (staff.getSearchCheckType()==null){
            staff.setSearchCheckType("1");//类型 1：全部，2：通过，3：未校验或者校验不通过的
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

    //获得所有未绑定该分组的已注册教师
    public List<Teacher> ycFindAllUnBoundTeacherByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterTeacherIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //查找已经绑定的教师
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("1");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("0");
        //查找已经绑定该分组的教师
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundTeacherIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //System.out.println(boundTeacherIdList.size()+"已绑定的教师数量");
        //查找本校所有已经注册中移动的教师
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
        //System.out.println(teacherImgList.size()+"教师的数量");
        List<Teacher> teacherWithThisGroup = teacherImgList.stream().filter(t -> enterTeacherIdList.contains(t.getId())&&!boundTeacherIdList.contains(t.getId())).collect(Collectors.toList());
        return teacherWithThisGroup;
    }

    //获得所有未绑定该分组的已注册学生
    public List<Student> ycFindAllUnBoundStudentByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        //all register teacher
        YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
        ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT);
        ycVerifacePersonEnter.setSchoolId(mySchoolId());
        List<YcVerifacePersonEnter> enterList = ycVerifacePersonEnterFeign.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
        List<String> enterStudentIdList = enterList.stream().map(YcVerifacePersonEnter::getPersonId).collect(Collectors.toList());
        //bound group student
        KqDeviceGroupPerson kqDeviceGroupPerson1 = new KqDeviceGroupPerson();
        kqDeviceGroupPerson1.setPersonType("2");//(1教师，2学生，3其他职工)
        kqDeviceGroupPerson1.setSchoolId(mySchoolId());
        kqDeviceGroupPerson1.setDeviceGroupId(kqDeviceGroupPerson.getDeviceGroupId());
        kqDeviceGroupPerson1.setFactoryType("0");
        kqDeviceGroupPerson1.setPager(null);
        //查找已经绑定的学生
        List<KqDeviceGroupPerson> kqDeviceGroupPersonList = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson1);
        List<String> boundStudentIdList = kqDeviceGroupPersonList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //查找本校所有已注册学生
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        List<Student> studentListByCondition = studentFeign.findStudentListByCondition(student);
        List<Student> studentWithThisGroup = studentListByCondition.stream().filter(t -> enterStudentIdList.contains(t.getId())&&!boundStudentIdList.contains(t.getId())).collect(Collectors.toList());
        //其他查询参数
        if (StrUtil.isNotEmpty(kqDeviceGroupPerson.getClassesId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getGradeId())||StrUtil.isNotEmpty(kqDeviceGroupPerson.getName())){
            studentWithThisGroup =  ycSeachByCondition(studentWithThisGroup,kqDeviceGroupPerson);
        }
        return studentWithThisGroup;
    }
    //其他查询参数
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

    //通过设备分组id批量绑定人员
    public Map<String, List<String>> ycBindDeviceAndPersonByGroupAndPersons(KqDeviceGroupPerson kqDeviceGroupPerson) {
        List<KqDeviceGroupPerson> boundPersonList = new ArrayList<>();
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得该分组下的所有设备
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
        //将成功的批次生成人员与分组对应关系批量存入数据库
        if (CollectionUtil.isNotEmpty(boundPersonList)){
            kqDeviceGroupPersonFeign.batchSaveKqDeviceGroupPerson(boundPersonList);
            //保存到缓存，websoket通知设备来取
            toldDeviceInsertPeople(boundPersonList, kqDeviceGroupPerson.getDeviceGroupId());
        }
        //返回不成功的批次
        return failureMapWhitType;
    }


    //解绑该分组下一批人员
    public Map<String, List<String>> ycPersonsUnbindDeviceByGroup(KqDeviceGroupPerson kqDeviceGroupPerson) {
        Map<String, List<String>> failureMapWhitType = new HashMap<>();
        //获得该分组下的所有门禁设备
        List<YcVerifaceDevice> kqDevicePersonList = ycGetThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(YcVerifaceDevice::getId).collect(Collectors.toList());
        List<KqDeviceGroupPerson> allPersonList = new ArrayList<>();
        for (String t:kqDeviceGroupPerson.getMapForPersonIdListByType().keySet()){
            String type = t;
            //查找出每个预绑定人员已经绑定的设备
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
        //返回不成功的批次
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
    /*//重新绑定某一分组下的人员和设备
    public List<String> ycDeviceChangeGroup(String nowGroupId){
        List<String> successMan = new ArrayList<>();
        List<String> failureMan = new ArrayList<>();
        //获得绑定该分组的所有人
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        kqDeviceGroupPerson.setDeviceGroupId(nowGroupId);
        List<KqDeviceGroupPerson> nowGroupPersons = kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        List<String> personIdList = nowGroupPersons.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
        //获得该分组下的所有门禁设备
        List<KqDevicePerson> kqDevicePersonList = getThisGroupAllDoorDevice(kqDeviceGroupPerson);
        List<String> wantBindDevices = kqDevicePersonList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        //该分组所有人绑定更新后的该分组所有设备
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
            //请求中移动绑定人员
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
