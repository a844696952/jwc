package com.yice.edu.cn.xw.service.dormManage.dorm;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.xw.dao.department.IDepartmentTeacherDao;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormDao;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonDao;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonLogDao;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonOutDao;
import com.yice.edu.cn.xw.dao.dormManage.houseApplican.IHouseApplicanStudentsDao;
import com.yice.edu.cn.xw.dao.student.IStudentDao;
import com.yice.edu.cn.xw.dao.teacher.ITeacherClassesDao;
import com.yice.edu.cn.xw.dao.teacher.ITeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DormPersonService {
    @Autowired
    private IDormPersonDao dormPersonDao;
    @Autowired
    private IDormPersonLogDao dormPersonLogDao;
    @Autowired
    private IDormPersonOutDao dormPersonOutDao;
    @Autowired
    private IDormDao dormDao;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ITeacherClassesDao teacherClassesDao;
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private IDepartmentTeacherDao departmentTeacherDao;
    @Autowired
    private IHouseApplicanStudentsDao houseApplicanStudentsDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DormPerson findDormPersonById(String id) {
        return dormPersonDao.findDormPersonById(id);
    }
    @Transactional
    public void saveDormPerson(DormPerson dormPerson) {
        dormPerson.setId(sequenceId.nextId());
        dormPersonDao.saveDormPerson(dormPerson);
    }
    @Transactional(readOnly = true)
    public List<DormPerson> findDormPersonListByCondition(DormPerson dormPerson) {
        return dormPersonDao.findDormPersonListByCondition(dormPerson);
    }
    @Transactional(readOnly = true)
    public DormPerson findOneDormPersonByCondition(DormPerson dormPerson) {
        return dormPersonDao.findOneDormPersonByCondition(dormPerson);
    }
    @Transactional(readOnly = true)
    public long findDormPersonCountByCondition(DormPerson dormPerson) {
        return dormPersonDao.findDormPersonCountByCondition(dormPerson);
    }
    @Transactional
    public long updateDormPerson(DormPerson dormPerson) {
        return dormPersonDao.updateDormPerson(dormPerson);
    }
    @Transactional
    public void deleteDormPerson(String id) {
        dormPersonDao.deleteDormPerson(id);
    }
    @Transactional
    public void deleteDormPersonByCondition(DormPerson dormPerson) {
        dormPersonDao.deleteDormPersonByCondition(dormPerson);
    }
    @Transactional
    public void batchSaveDormPerson(List<DormPerson> dormPersons){
        dormPersons.forEach(dormPerson -> dormPerson.setId(sequenceId.nextId()));
        dormPersonDao.batchSaveDormPerson(dormPersons);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public List<Student> findStudentListByConditionOnDorm(Student student) {
        return dormPersonDao.findStudentListByConditionOnDorm(student);

    }
    @Transactional
    public Long findStudentListCountByConditionOnDorm(Student student) {
        return dormPersonDao.findStudentListCountByConditionOnDorm(student);

    }

    @Transactional
    public List<Teacher> findTeacherListByConditionOnDorm(Teacher teacher) {
        return dormPersonDao.findTeacherListByConditionOnDorm(teacher);

    }
    @Transactional
    public Long findTeacherListCountByConditionOnDorm( Teacher teacher) {
        return dormPersonDao.findTeacherListCountByConditionOnDorm(teacher);

    }

    @Transactional
    public List<Teacher> findNoTeacherListByConditionOnDorm(Teacher teacher) {
        return dormPersonDao.findNoTeacherListByConditionOnDorm(teacher);

    }
    @Transactional
    public Long findNoTeacherListCountByConditionOnDorm( Teacher teacher) {
        return dormPersonDao.findNoTeacherCountListByConditionOnDorm(teacher);

    }
    @Transactional
    public List<DormPerson> findDormPersonListConnectTeacher(DormBuildVo dormBuildVo) {
        return dormPersonDao.findDormPersonListConnectTeacher(dormBuildVo);
    }

    @Transactional
    public List<DormPerson> findDormPersonListConnectStudent(DormBuildVo dormBuildVo) {
        return dormPersonDao.findDormPersonListConnectStudent(dormBuildVo);
    }
    @Transactional
    public DormBuildingPersonInfo getDormBuildingById(String id){
        return dormPersonDao.getDormBuildingById(id);
    }
    @Transactional
    public DormPerson findDormPersonOneConnectTeacher(DormBuildVo dormBuildVo) {
        return dormPersonDao.findDormPersonOneConnectTeacher(dormBuildVo);
    }

    @Transactional
    public DormPerson findDormPersonOneConnectStudent(DormBuildVo dormBuildVo) {
        return dormPersonDao.findDormPersonOneConnectStudent(dormBuildVo);
    }
    //??????
    @Transactional
    public void  leaveDorm(DormPerson dormPerson){
        //??????????????????????????????????????????
        DormPerson dormPersonById = dormPersonDao.findDormPersonById(dormPerson.getId());
        dormPersonById.setRemarks(dormPerson.getRemarks());
        //??????
        long l = dormPersonDao.leaveDorm(dormPerson);
        if (l>0){
            //?????????????????????????????????????????????
            this.saveDormPersonLogAndUpdatePersonNum(dormPersonById,"2");
            //??????????????????
            this.saveDormPersonOut(dormPersonById);
            if (dormPersonById.getPersonType().equals("1")){
                //???????????????????????????,???????????????
                Student student = new Student();
                student.setId(dormPersonById.getPersonId());
                student.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT);
                studentDao.updateStudentById(student);
            }

        }
    }

    private void saveDormPersonOut(DormPerson dormPerson) {
        if (dormPerson.getPersonType().equals("1")){
            Student student = studentDao.findStudentById(dormPerson.getPersonId());
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setClassesId(student.getClassesId());
            Teacher headmaster = teacherClassesDao.findHeadmasterByClasses(teacherClasses);
            DormPersonOut dormPersonOut = new DormPersonOut();
            dormPersonOut.setId(sequenceId.nextId());
            dormPersonOut.setPersonName(student.getName());
            dormPersonOut.setPersonId(student.getId());
            dormPersonOut.setPersonType("1");
            dormPersonOut.setSex(student.getSex());
            dormPersonOut.setGuardianContact(student.getGuardianContact());
            dormPersonOut.setImgUrl(student.getImgUrl());
            dormPersonOut.setStudentNo(student.getStudentNo());
            dormPersonOut.setClassesId(student.getClassesId());
            dormPersonOut.setSchoolId(student.getSchoolId());
            if (headmaster!=null){
                dormPersonOut.setTeacherName(headmaster.getName());
                dormPersonOut.setTeacherTel(headmaster.getTel());
            }
            SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
            String outTime = sDF.format(new Date());
            dormPersonOut.setOutTime(outTime);
            dormPersonOutDao.saveDormPersonOut(dormPersonOut);
        }else{
            Teacher teacher = teacherDao.findTeacherById(dormPerson.getPersonId());
            DormPersonOut dormPersonOut = new DormPersonOut();
            dormPersonOut.setId(sequenceId.nextId());
            dormPersonOut.setPersonName(teacher.getName());
            dormPersonOut.setPersonId(teacher.getId());
            dormPersonOut.setSex(teacher.getSex());
            dormPersonOut.setImgUrl(teacher.getImgUrl());
            dormPersonOut.setPersonType(dormPerson.getPersonType());
            dormPersonOut.setWorkNumber(teacher.getWorkNumber());
            dormPersonOut.setTel(teacher.getTel());
            dormPersonOut.setSchoolId(teacher.getSchoolId());
            List<String> departmentStrList = new ArrayList<>();
            List<DepartmentTeacher> departmentList= departmentTeacherDao.findDepartmentByTeacherId(teacher.getId());
            if (departmentList.size()>0){
                departmentList.forEach(d->{
                    departmentStrList.add(d.getDepartmentName());
                });

            }
            String departmentStr = departmentStrList.toString();
            departmentStr =  departmentStr.substring(1,departmentStr.length()-1);
            dormPersonOut.setDepartments(departmentStr);
            SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
            String outTime = sDF.format(new Date());
            dormPersonOut.setOutTime(outTime);
            dormPersonOutDao.saveDormPersonOut(dormPersonOut);
        }

    }
    @Transactional
    public List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonDao.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
    }
    @Transactional
    public long findDormPersonInfoCountWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonDao.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
    }
    @Transactional
    public List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonDao.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
    }
    @Transactional
    public long findDormPersonInfoCountWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo){
        return dormPersonDao.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
    }
    @Transactional
    public List<Map<String, String>> findEmptyDormByDormCategory(DormBuildVo dormBuildVo){
        return dormPersonDao.findEmptyDormByDormCategory(dormBuildVo);
    }
    @Transactional
    public long updateSaveDormPerson(DormPerson dormPerson){
        long l = dormPersonDao.updateSaveDormPerson(dormPerson);
        if (l>0){
            //???????????????????????????????????????
            this.saveDormPersonLogAndUpdatePersonNum(dormPerson,"0");
            //??????????????????????????????
            DormPersonOut dormPersonOut = new DormPersonOut();
            dormPersonOut.setPersonId(dormPerson.getPersonId());
            dormPersonOutDao.deleteDormPersonOutByCondition(dormPersonOut);
            if (dormPerson.getPersonType().equals("1")){
                //???????????????????????????,???????????????
                Student student = new Student();
                student.setId(dormPerson.getPersonId());
                student.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_HAS);
                studentDao.updateStudentById(student);

            }

        }
        return l;
    }


    //?????????????????????????????????????????????
    private void saveDormPersonLogAndUpdatePersonNum(DormPerson dormPerson,String optType){
        //????????????????????????
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setSchoolId(dormPerson.getSchoolId());
        dormBuildVo.setDormId(dormPerson.getDormId());
        long count = this.findDormMoveIntoPersonNumByCondition(dormBuildVo);
        Dorm dorm = new Dorm();
        dorm.setDormId(dormPerson.getDormId());
        dorm.setPersonNum((int) count);
        dormDao.updateDormByDormId(dorm);
        //??????????????????
        DormBuildingPersonInfo dormBuildingPersonInfo = this.getDormBuildingById(dormPerson.getId());
        DormPersonLog dormPersonLog = new DormPersonLog();
        dormPersonLog.setId(sequenceId.nextId());
        dormPersonLog.setDormBuildName(dormBuildingPersonInfo.getDormBuildName());
        dormPersonLog.setFloor(dormBuildingPersonInfo.getFloor());
        dormPersonLog.setDormName(dormBuildingPersonInfo.getDormName());
        dormPersonLog.setBunkName(dormBuildingPersonInfo.getBunkName());
        dormPersonLog.setPersonId(dormPerson.getPersonId());
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
        String optime = sDF.format(new Date());
        dormPersonLog.setOptime(optime);
        dormPersonLog.setPersonType(dormPerson.getPersonType());
        dormPersonLog.setOptType(optType);
        dormPersonLog.setSchoolId(dormPerson.getSchoolId());
        dormPersonLog.setRemarks(dormPerson.getRemarks());
        dormPersonLogDao.saveDormPersonLog(dormPersonLog);
    }


    @Transactional
    public void leaveDormByClassesId(String classesId){

        dormPersonDao.leaveDormByClassesId(classesId);
    }
    @Transactional
    public long findDormMoveIntoPersonNumByCondition(DormBuildVo dormBuildVo){
        return dormPersonDao.findDormMoveIntoPersonNumByCondition(dormBuildVo);
    }

    @Transactional
    public void adjustDormByCondition(List<DormPerson> dormPersonList) {
        if (dormPersonList!=null&&dormPersonList.size()>0){
            dormPersonList.forEach(dormPerson -> {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String moveIntoTime = dateFormat.format(new Date());
                //??????????????????????????????????????????
                DormPerson bunkDormPerson = dormPersonDao.findDormPersonById(dormPerson.getId());
                //?????????????????????id???????????????????????????
                DormPerson dP = new DormPerson();
                dP.setPersonId(dormPerson.getAdjustPersonId());
                DormPerson adjustDormPerson = dormPersonDao.findOneDormPersonByCondition(dP);
                //1.????????????
                if (bunkDormPerson!=null&&bunkDormPerson.getPersonId()!=null){
                    //????????????????????????????????????
                    DormPerson bunkDormPersonB = new DormPerson();
                    bunkDormPersonB.setId(bunkDormPerson.getId());
                    bunkDormPersonB.setPersonId(adjustDormPerson.getPersonId());
                    bunkDormPersonB.setPersonType(adjustDormPerson.getPersonType());
                    bunkDormPersonB.setRemarks(adjustDormPerson.getRemarks());
                    bunkDormPersonB.setMoveIntoTime(moveIntoTime);
                    bunkDormPersonB.setSchoolId(adjustDormPerson.getSchoolId());
                    bunkDormPersonB.setDormId(bunkDormPerson.getDormId());
                    //1.??????????????????????????????????????????
                    if (adjustDormPerson.getDormId().equals(bunkDormPerson.getDormId())){
                        //1.1?????????????????????
                        bunkDormPersonB.setIsDormLeader(adjustDormPerson.getIsDormLeader());
                    }else {
                        bunkDormPersonB.setIsDormLeader("0");
                    }
                    dormPersonDao.updateDormPerson(bunkDormPersonB);
                    //????????????
                    this.saveDormPersonLogAndUpdatePersonNum(bunkDormPersonB,"1");
                    //??????????????????????????????????????????????????????
                    DormPerson dormPersonE = new DormPerson();
                    dormPersonE.setId(adjustDormPerson.getId());
                    dormPersonE.setPersonId(bunkDormPerson.getPersonId());
                    dormPersonE.setPersonType(bunkDormPerson.getPersonType());
                    dormPersonE.setRemarks(bunkDormPerson.getRemarks());
                    dormPersonE.setMoveIntoTime(moveIntoTime);
                    dormPersonE.setSchoolId(bunkDormPerson.getSchoolId());
                    dormPersonE.setDormId(adjustDormPerson.getDormId());
                    //1.??????????????????????????????????????????
                    if (adjustDormPerson.getDormId().equals(bunkDormPerson.getDormId())){
                        //1.1?????????????????????
                        dormPersonE.setIsDormLeader(bunkDormPerson.getIsDormLeader());
                    }else {
                        dormPersonE.setIsDormLeader("0");
                    }
                    dormPersonDao.updateDormPerson(dormPersonE);
                    //????????????
                    this.saveDormPersonLogAndUpdatePersonNum(dormPersonE,"1");
                }else {
                    //2.????????????,?????????????????????
                    bunkDormPerson.setPersonId(adjustDormPerson.getPersonId());
                    bunkDormPerson.setPersonType(adjustDormPerson.getPersonType());
                    bunkDormPerson.setRemarks(adjustDormPerson.getRemarks());
                    bunkDormPerson.setMoveIntoTime(moveIntoTime);
                    //1.??????????????????????????????????????????
                    if (adjustDormPerson.getDormId().equals(bunkDormPerson.getDormId())){
                        //1.1?????????????????????
                        bunkDormPerson.setIsDormLeader(adjustDormPerson.getIsDormLeader());
                    }else {
                        bunkDormPerson.setIsDormLeader("0");
                    }
                    dormPersonDao.updateSaveDormPerson(bunkDormPerson);
                    //????????????
                    this.saveDormPersonLogAndUpdatePersonNum(bunkDormPerson,"1");
                    //???????????????????????????
                    dormPersonDao.leaveDorm(adjustDormPerson);
                    //????????????????????????
                    DormPerson dormPersonById = dormPersonDao.findDormPersonById(adjustDormPerson.getId());
                    DormBuildVo dormBuildVo = new DormBuildVo();
                    dormBuildVo.setSchoolId(dormPersonById.getSchoolId());
                    dormBuildVo.setDormId(dormPersonById.getDormId());
                    long count = this.findDormMoveIntoPersonNumByCondition(dormBuildVo);
                    Dorm dorm = new Dorm();
                    dorm.setDormId(dormPersonById.getDormId());
                    dorm.setPersonNum((int) count);
                    dormDao.updateDormByDormId(dorm);

                }
            });
        }
    }


    //???????????????????????????
    public  Map<String, Object> arrangeDorm(List<DormPerson> dormPersonList) {
        Map<String, Object> result = new HashMap<>();
        //??????????????????
        List<DormPerson> isDormPersonList = dormPersonList.stream().filter(dormPerson -> "0".equals(dormPerson.getIsDorm())).collect(Collectors.toList());
        if (isDormPersonList.size()>0&&isDormPersonList!=null){
            isDormPersonList.forEach(isDormPerson->{
                //???????????????????????????????????????????????????
                this.saveHouseApplicanStudents(isDormPerson);
            });
        }
        //??????????????????
        List<DormPerson> noDormPersonList = dormPersonList.stream().filter(dormPerson -> "1".equals(dormPerson.getIsDorm())).collect(Collectors.toList());
        if (noDormPersonList.size()>0&&noDormPersonList!=null){
            List<DormPerson> list = new ArrayList<>();
            final String[] teacherId = new String[1];
            noDormPersonList.forEach(dormPerson -> {
                dormPerson.setPersonType("1");
                SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd ");
                String moveIntoTime = sDF.format(new Date());
                dormPerson.setMoveIntoTime(moveIntoTime);
                dormPerson.setIsDormLeader("0");
                long l = this.updateSaveDormPerson(dormPerson);
                if (l<=0){
                    list.add(dormPerson);
                }else {
                    //???????????????????????????????????????????????????
                    this.saveHouseApplicanStudents(dormPerson);
                    //???????????????????????????
                    String[] stuId = new String[]{dormPerson.getPersonId()};
                    final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuId,"????????????","????????????????????????????????????", Extras.XW_DORM_PERSON_BMP);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                    //???????????????????????????
                    Student student = studentDao.findStudentById(dormPerson.getPersonId());
                    TeacherClasses teacherClasses = new TeacherClasses();
                    teacherClasses.setClassesId(student.getClassesId());
                    Teacher teacher = teacherClassesDao.findHeadmasterByClasses(teacherClasses);
                    teacherId[0] = teacher.getId();
                }
            });
            houseApplicanStudentsIsExist(dormPersonList);
            if (list.size()>0){
                result.put("code", "222");
                result.put("errors", list);
                result.put("success","????????????"+(dormPersonList.size()-list.size())+"???,????????????"+list.size()+"???");
            }else {
                result.put("code", "200");
                result.put("success","????????????"+dormPersonList.size()+"???");
            }

            if (dormPersonList.size()-list.size()>0){
                final Push teacherPush = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherId,"????????????","??????????????????????????????????????????", Extras.XW_DORM_PERSON_TAP);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(teacherPush));
            }


        }
        return result;
    }

    private void saveHouseApplicanStudents(DormPerson dormPerson) {
        DormBuildingPersonInfo dormBuildingPersonInfo = this.getDormBuildingById(dormPerson.getId());
        HouseApplicanStudents houseApplicanStudentsChild = new HouseApplicanStudents();
        houseApplicanStudentsChild.setDormitoryId(dormBuildingPersonInfo.getDormBuildId());
        houseApplicanStudentsChild.setDormitoryName(dormBuildingPersonInfo.getDormBuildName());
        houseApplicanStudentsChild.setFloorId(dormBuildingPersonInfo.getFloorId());
        houseApplicanStudentsChild.setFloorName(dormBuildingPersonInfo.getFloor());
        houseApplicanStudentsChild.setBedRoomId(dormBuildingPersonInfo.getDormId());
        houseApplicanStudentsChild.setBedRoomName(dormBuildingPersonInfo.getDormName());
        houseApplicanStudentsChild.setBedCode(dormBuildingPersonInfo.getBunkName());
        houseApplicanStudentsChild.setStudentId(dormPerson.getPersonId());
        houseApplicanStudentsChild.setHouseApplicanId(dormPerson.getHouseApplicanId());
        houseApplicanStudentsChild.setType("2");
        houseApplicanStudentsDao.saveHouseApplicanStudents1(houseApplicanStudentsChild);
    }

    //????????????????????????  ????????????????????????????????????????????????
    //???????????????????????????, ???????????????????????????
    private void houseApplicanStudentsIsExist(List<DormPerson> dormPersonList) {
        //?????????????????????id List
        List<String> dormPersonStudentList = dormPersonList.stream().map(DormPerson::getPersonId).collect(Collectors.toList());
        //?????????????????? ?????????????????????id list
        HouseApplicanStudents houseApplicanStudents = new HouseApplicanStudents();
        houseApplicanStudents.setSchoolId(dormPersonList.get(0).getSchoolId());
        //collect1  ???????????????????????????????????????id??????
        List<HouseApplicanStudents> houseApplicanStudentsAll = houseApplicanStudentsDao.findHouseApplicanStudents(houseApplicanStudents)
                .stream().filter(ele -> ele.getTypes().equals("1")).collect(Collectors.toList());
        String  houseapplicantid ;
        for (int i = 0; i < houseApplicanStudentsAll.size(); i++) {
            String s = houseApplicanStudentsAll.get(i).getStudentsId();
            houseapplicantid = houseApplicanStudentsAll.get(i).getHouseApplicanId();
            String[] array = s.split(",");
            //??????????????????listId
            List<String> listId = Arrays.asList(array);
            int k = 0;
            for (int y = 0; y < listId.size(); y++){
                if (dormPersonStudentList.indexOf(listId.get(y)) != -1){
                    k++;
                }
            }
            //?????????????????? ??????????????????  ??? ????????????????????????
            if (k == listId.size()){
                HouseApplicanStudents houseApplicanStudents2= new HouseApplicanStudents();
                houseApplicanStudents2.setHouseApplicanId(houseapplicantid);
                houseApplicanStudents2.setType("2");
                for (int x =0; x < listId.size(); x++){
                    houseApplicanStudents2.setStudentId(listId.get(x));
                    DormBuildingPersonInfo d = new DormBuildingPersonInfo();
                    d.setPersonId(listId.get(x));
                    List<DormBuildingPersonInfo> studentDorm = dormPersonDao.findDormPersonInfoWithStudent(d);
                    houseApplicanStudents2.setDormitoryId(studentDorm.get(0).getDormBuildId());
                    houseApplicanStudents2.setDormitoryName(studentDorm.get(0).getDormBuildName());
                    houseApplicanStudents2.setFloorId(studentDorm.get(0).getFloorId());
                    houseApplicanStudents2.setFloorName(studentDorm.get(0).getFloor());
                    houseApplicanStudents2.setBedRoomId(studentDorm.get(0).getDormId());
                    houseApplicanStudents2.setBedRoomName(studentDorm.get(0).getDormName());
                    houseApplicanStudents2.setBedCode(studentDorm.get(0).getBunkName());
                    houseApplicanStudentsDao.saveHouseApplicanStudents1(houseApplicanStudents2);
                }
            }

        }
    }

    @Transactional
    public void updateDormCapacityByDormId( Building building){
        //????????????????????????????????????
        if ("107".equals(building.getTypeId())){
            //?????????????????????????????????
            DormPerson dormPerson = new DormPerson();
            dormPerson.setDormId(building.getId());
            dormPerson.setSchoolId(building.getSchoolId());
            int dormCount = (int)dormPersonDao.findDormPersonCountByCondition(dormPerson);
            //???????????????????????????0?????????,????????????????????????,??????,????????????????????????
            if (dormCount>0){
                //??????????????????
                String capacity = building.getCapacity();
                int buildingCapacity = Integer.parseInt(capacity);
                if (dormCount>buildingCapacity){
                    //?????????????????????
                    dormPersonDao.deleteDormPersonByCondition(dormPerson);
                    List<DormPerson> dormPersonAddList = new ArrayList<>();
                    for (int i = 1; i <= buildingCapacity; i++) {
                        DormPerson dormPersonAdd = new DormPerson();
                        dormPersonAdd.setId(sequenceId.nextId());
                        dormPersonAdd.setDormId(building.getId());
                        dormPersonAdd.setSchoolId(building.getSchoolId());
                        dormPersonAdd.setBunkName(i+"??????");
                        dormPersonAdd.setBunkSort(i);
                        dormPersonAddList.add(dormPersonAdd);

                    }
                    dormPersonDao.batchSaveDormPerson(dormPersonAddList);
                }
                if (dormCount<buildingCapacity){
                    List<DormPerson> dormPersonAddList = new ArrayList<>();
                    for (int i = 1; i <= buildingCapacity-dormCount; i++) {
                        DormPerson dormPersonAdd = new DormPerson();
                        dormPersonAdd.setId(sequenceId.nextId());
                        dormPersonAdd.setDormId(building.getId());
                        dormPersonAdd.setSchoolId(building.getSchoolId());
                        dormPersonAdd.setBunkName(dormCount+i+"??????");
                        dormPersonAdd.setBunkSort(dormCount+i);
                        dormPersonAddList.add(dormPersonAdd);
                    }
                    dormPersonDao.batchSaveDormPerson(dormPersonAddList);
                }
            }

        }

    }


    /**
     * ????????????????????????????????????
     * @param dormIdList
     */
    @Transactional
    public void deleteDormAndDormPerson(List<String> dormIdList){
        dormDao.batchDeleteDormByDormIdList(dormIdList);
        dormPersonDao.batchDeleteDormPersonByDormIdList(dormIdList);
    }

}
