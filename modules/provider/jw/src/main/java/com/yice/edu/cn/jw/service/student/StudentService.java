package com.yice.edu.cn.jw.service.student;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.student.*;
import com.yice.edu.cn.common.pojo.ts.jMessage.IMReturnResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.UserRigisterApiResult;
import com.yice.edu.cn.common.pojo.ts.jMessage.pojo.UserRegister;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.util.jmessage.api.JMessageUserApi;
import com.yice.edu.cn.jw.dao.classes.IJwClaCadresStuDao;
import com.yice.edu.cn.jw.dao.classes.IJwClassesDao;
import com.yice.edu.cn.jw.dao.parent.IParentDao;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.dao.student.IStudentFamilyDao;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private Logger log = LoggerFactory.getLogger("StudentService");

    private ExecutorService executorService;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJwClaCadresStuDao jwClaCadresStuDao;
    @Autowired
    private IStudentFamilyDao studentFamilyDao;
    @Autowired
    private IParentDao parentDao;
    @Autowired
    private IJwClassesDao jwClassesDao;


    @Transactional(readOnly = true)
    public Student findStudentById(String id) {
        return studentDao.findStudentById(id);
    }

    @Transactional(readOnly = true)
    public Student findOneStudentByCondition(Student student){
        return studentDao.findOneStudentByCondition(student);
    }

    @Transactional
    public int saveStudent(Student student) {

        int i =0;
        student.setId(sequenceId.nextId());
        if(StrUtil.hasEmpty(student.getImgUrl())){    //??????????????????
            if("4".equals(student.getSex())) {
                student.setImgUrl(Constant.AVATAR.BOY);
            }else if("5".equals(student.getSex())){
                student.setImgUrl(Constant.AVATAR.GIRL);
            }
        }
        studentDao.saveStudent(student);
        return i;
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListByCondition(Student student) {
        return studentDao.findStudentListByCondition(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListByConditionWithBmp(Student student) {
        return studentDao.findStudentListByConditionWithBmp(student);
    }

    @Transactional(readOnly = true)
    public long findStudentCountByCondition(Student student) {
        return studentDao.findStudentCountByCondition(student);
    }

    @Transactional(readOnly = true)
    public long findStudentCodeCountByCondition(Student student) {
        return studentDao.findStudentCodeCountByCondition(student);
    }

    @Transactional
    public void updateStudent(Student student) {
        Student student1 = studentDao.findStudentById(student.getId());
        if(student1.getClassesId()!=null && student.getClassesId()!=null && !student1.getClassesId().equals(student.getClassesId()) ){ //????????????????????????????????????????????????

            Student queryStu = new Student();
            queryStu.setClassesId(student.getClassesId());
            JwClasses jwClasses = jwClassesDao.findJwClassesById(student.getClassesId()); //?????????????????????,?????????????????????????????????????????????????????????  ??????????????????????????????????????????
            queryStu.setEnrollYear(jwClasses.getEnrollYear());
            Student maxSeatNumberStudent = studentDao.findOneStudentMaxSeatNumberByCondition(queryStu); //???????????????????????????
            student.setSeatNumber(maxSeatNumberStudent.getSeatNumber() + 1);

            //??????????????????????????????
            JwClaCadresStu claCadresStu  = new JwClaCadresStu();
            claCadresStu.setStudentId(student1.getId());
            jwClaCadresStuDao.deleteJwClaCadresStuByCondition(claCadresStu);
        }
        studentDao.updateStudent(student);
        getExecutor().execute(()->{
            //????????????im?????????????????????
            if( Constant.IM_TYPE.REGISTER_STATUS_HAS.equals(student.getRegisterStatus())){ //??????????????????IM

                if( (student.getName()!=null && !student.getName().equals(student1.getName())) || (student.getImgUrl()!=null && !student.getImgUrl().equals(student1.getImgUrl()) )  ){
                    //?????????????????????????????? im ??????
                    UserRegister userRegister = new UserRegister();
                    userRegister.setUserName(student.getId());   //?????????
                    if(student.getName()!=null){
                        userRegister.setShowName(student.getName()); //???????????????
                    }
                    if(student.getImgUrl()!=null){
                        userRegister.setDefaultHeadImage(student.getImgUrl());  //????????????
                    }
                    JMessageUserApi.updateUser(userRegister,Constant.APP_TYPE.BMP_TYPE);
                }
            }
        });
    }

    @Transactional
    public void updateStudentById(Student student) {
        studentDao.updateStudent(student);
    }


    @Transactional
    public void updateStudentNew(Student student) {
        if(student.getImgUrl()==null || "".equals(student.getImgUrl())){    //????????????????????????
            if(student.getSex()!=null){
                student.setImgUrl(student.getSex().equals("4")?Constant.AVATAR.BOY:Constant.AVATAR.GIRL);
            }
        }else if(Constant.AVATAR.BOY.equals(student.getImgUrl()) || Constant.AVATAR.GIRL.equals(student.getImgUrl())){
            if(student.getSex()!=null){
                student.setImgUrl(student.getSex().equals("4")?Constant.AVATAR.BOY:Constant.AVATAR.GIRL);
            }
        }
        this.updateStudent(student);

    }

    @Transactional
    public void deleteStudent(String id) {
        Student student1 = studentDao.findStudentById(id);
        if(!StringUtil.isNullOrEmpty(student1.getClassesId())){
            //??????????????????????????????
            JwClaCadresStu claCadresStu  = new JwClaCadresStu();
            claCadresStu.setStudentId(student1.getId());
            jwClaCadresStuDao.deleteJwClaCadresStuByCondition(claCadresStu);
        }
        studentDao.deleteStudent(id);
        ParentStudent p=new ParentStudent();
        p.setStudentId(id);
        parentDao.deleteParentStudentByCondition(p);//????????????id???????????????????????????
    }

    @Transactional
    public void deleteStudentByCondition(Student student) {
        studentDao.deleteStudentByCondition(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListByConditionWithFamily(Student student) {

        return studentDao.findStudentListByConditionWithFamily(student);
    }
    @Transactional(readOnly = true)
    public long findStudentCountByConditionWithFamily(Student student) {
        return studentDao.findStudentCountByConditionWithFamily(student);
    }

    @Transactional
    public  void batchSaveStudent(List<Student> studentList)  {

        studentList = studentList.stream().map(s->{
            s.setId(sequenceId.nextId());
            return s;
        }).collect(Collectors.toList());
        studentDao.batchSaveStudent(studentList); ;
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListForClassesByCondition(Student student) {
        return  studentDao.findStudentListForClassesByCondition(student);
    }
    @Transactional(readOnly = true)
    public long findStudentCountForClassesByCondition(Student student) {
        return studentDao.findStudentCountForClassesByCondition(student);
    }
    //(osp????????????????????????)
    @Transactional(readOnly = true)
    public List<Student> findCorrespondencesByStudent(Student student) {
        List<Student> studentList = studentDao.findStudentAndFamily(student);
        //??????id????????????
        List<String> classesId_tempList = new ArrayList<String>();//?????????????????????????????????id??????
        for (int i = 0; i < studentList.size(); i++) {//????????????id
            if (!classesId_tempList.contains(studentList.get(i).getClassesId())) {
                classesId_tempList.add(studentList.get(i).getClassesId());
            }
        }
        //??????id????????????
        List<String> id_tempList = new ArrayList<String>();//?????????????????????????????????id??????
        for (int j = 0; j < studentList.size(); j++) {//????????????id
            if (!id_tempList.contains(studentList.get(j).getId())) {
                id_tempList.add(studentList.get(j).getId());
            }
        }
        List<Student> student_all = new ArrayList<Student>();
        for(int k=0;k<classesId_tempList.size();k++){
            Student s = new Student();
            List<Student> studentList1 = new ArrayList<Student>();
            List<String> id_temp = new ArrayList<String>();
            String classId = classesId_tempList.get(k);
            s.setClassesId(classId);
            studentList.forEach(student1 -> {
                if(student1.getClassesId().equals(classId) && !id_temp.contains(student1.getId())){
                    s.setTitle(student1.getTitle());
                    studentList1.add(student1);
                    id_temp.add(student1.getId());
                }
            });
            student.setClassesId(classId);
            Long count = studentDao.findStudentCount1ByCondition(student);
            s.setCount(count);
            s.setChildren(studentList1);
            student_all.add(s);
        }

        Map<String,List<StudentFamily>>  mapList = new HashMap<>();//??????????????????
        for(int m=0;m<id_tempList.size();m++){
            String student_id = id_tempList.get(m);
            List<StudentFamily> studentFamilyList = new ArrayList<StudentFamily>();
            for(int k=0;k<studentList.size();k++){
                if(student_id==null){//??????????????????
                    StudentFamily s = new StudentFamily();
                    s.setName("");
                    s.setContactWay("");
                    s.setRelation("");
                    studentFamilyList.add(s);
                    break;
                }else{
                    if(id_tempList.get(m).equals(studentList.get(k).getId())){
                        StudentFamily s = new StudentFamily();
                        s.setName(studentList.get(k).getParentName());
                        s.setContactWay(studentList.get(k).getParentContact());
                        s.setRelation(studentList.get(k).getRelation());
                        studentFamilyList.add(s);
                    }
                }
                //mapList.put(student_id,studentFamilyList);
            }
            mapList.put(student_id,studentFamilyList);
        }

        for(int p=0;p<student_all.size();p++){
            List<Student> list = student_all.get(p).getChildren();
            for(int k=0;k<list.size();k++){
                List<StudentFamily> lm =  mapList.get(list.get(k).getId());
                list.get(k).setStudentFacmilyL(lm);
            }
        }

        //????????????
        List<Student> students = new ArrayList<>();
        student_all.forEach(student1 -> {
            if(student1.getDel()==null ||student1.getDel().equals("1")){//1?????????
                students.add(student1);
            }
        });
        return students;
    }

    //(app???????????????????????????)
    @Transactional(readOnly = true)
    public List<Student> findCorrespondencesByStudentApp(Student student) {
        List<Student> studentList = studentDao.findStudentAndFamily(student);
        //??????id????????????
        List<String> classesId_tempList = new ArrayList<String>();//?????????????????????????????????id??????
        for (int i = 0; i < studentList.size(); i++) {//????????????id
            if (!classesId_tempList.contains(studentList.get(i).getClassesId())) {
                classesId_tempList.add(studentList.get(i).getClassesId());
            }
        }
        //??????id????????????
        List<String> id_tempList = new ArrayList<String>();//?????????????????????????????????id??????
        for (int j = 0; j < studentList.size(); j++) {//????????????id
            if (!id_tempList.contains(studentList.get(j).getId())) {
                id_tempList.add(studentList.get(j).getId());
            }
        }
        List<Student> student_all = new ArrayList<Student>();
        for(int k=0;k<classesId_tempList.size();k++){
            Student s = new Student();
            List<Student> studentList1 = new ArrayList<Student>();
            List<String> id_temp = new ArrayList<String>();
            String classId = classesId_tempList.get(k);
            s.setClassesId(classId);
            studentList.forEach(student1 -> {
                if(student1.getClassesId().equals(classId) && !id_temp.contains(student1.getId())){
                    s.setTitle(student1.getTitle());
                    studentList1.add(student1);
                    id_temp.add(student1.getId());
                }
            });
            student.setClassesId(classId);
            Long count = studentDao.findStudentCount1ByCondition(student);
            s.setCount(count);
            s.setChildren(studentList1);
            student_all.add(s);
        }

        Map<String,List<StudentFamily>>  mapList = new HashMap<>();//??????????????????
        for(int m=0;m<id_tempList.size();m++){
            String student_id = id_tempList.get(m);
            List<StudentFamily> studentFamilyList = new ArrayList<StudentFamily>();
            for(int k=0;k<studentList.size();k++){
                if(student_id==null){//??????????????????
                    StudentFamily s = new StudentFamily();
                    s.setName("");
                    s.setContactWay("");
                    s.setRelation("");
                    studentFamilyList.add(s);
                    break;
                }else{
                    if(id_tempList.get(m).equals(studentList.get(k).getId())){
                        StudentFamily s = new StudentFamily();
                        s.setName(studentList.get(k).getParentName());
                        s.setContactWay(studentList.get(k).getParentContact());
                        s.setRelation(studentList.get(k).getRelation());
                        studentFamilyList.add(s);
                    }
                }
                //mapList.put(student_id,studentFamilyList);
            }
            mapList.put(student_id,studentFamilyList);
        }

        for(int p=0;p<student_all.size();p++){
            List<Student> list = student_all.get(p).getChildren();
            for(int k=0;k<list.size();k++){
                List<StudentFamily> lm =  mapList.get(list.get(k).getId());
                list.get(k).setStudentFacmilyL(lm);
            }
        }

        //????????????
        List<Student> students = new ArrayList<>();
        student_all.forEach(student1 -> {
            if(student1.getDel()==null ||student1.getDel().equals("1")){//1?????????
                students.add(student1);
            }
        });
        return students;
    }

    @Transactional(readOnly = true)
    public List<Student> findTeacherClassList(Student student) {//?????????????????????????????????
        return studentDao.findTeacherClassList(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findClassStudentByclassTitle(Student student) {
        List<Student> studentList = studentDao.findStudentClassStudentInfo(student);

        return studentList;

    }
    @Transactional(readOnly = true)
    public Student findOneStudentMaxSeatNumberByCondition(Student student){
        return studentDao.findOneStudentMaxSeatNumberByCondition(student);
    };

    //????????????????????????
    @Transactional(readOnly = true)
    public List<Student> findAllSchoolStudentListByCondition(Student student){
        return studentDao.findAllSchoolStudentListByCondition(student);
    }
    @Transactional(readOnly = true)
    public long findAllSchoolStudentCountByCondition(Student student) {
        return studentDao.findAllSchoolStudentCountByCondition(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListForExportStudentByCondition(Student student){
        return studentDao.findStudentListForExportStudentByCondition(student);
    }

    @Transactional //????????????
    public void findStudentRowNumber(Student student){

        List<Student> studentList = studentDao.findStudentListForYiJianPaiHaoByCondition(student);//??????????????????
        int maxSeat = 0;
        for (int i =0; i<studentList.size();i++){
            maxSeat+=1;         //?????????????????????
            Student stu = new Student();
            stu.setId(studentList.get(i).getId());
            stu.setSeatNumber(maxSeat);
            studentDao.updateStudent(stu);
        }

    }

    @Transactional(readOnly = true)
    public long findStudentCodeForUpdateByStudentCode(String studentCode){
        return   studentDao.findStudentCodeForUpdateByStudentCode(studentCode);
    }

    @Transactional(readOnly = true)
    public List<String> findStudentCodeAllListByCondition(Student student){
        return studentDao.findStudentCodeAllListByCondition(student);
    }

    @Transactional(readOnly = true)
    public long findStudentCountByConditionForUpdate(Student student){
        return   studentDao.findStudentCountByConditionForUpdate(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentByTitleim(Student student) {
        //1.??????????????????????????????????????????
        List<Student> studentList = studentDao.findStudentByTitleim(student);
        if(studentList.isEmpty()){
            return studentList;
        }
        //2.?????????id????????????
        Map<String, List<Student>> gMap = studentList.stream().collect(Collectors.groupingBy(Student::getId));
        List<Student> studentResult = new ArrayList<>();
        gMap.forEach((k,v)->{
            studentResult.add(v.get(0));
        });
        //3.?????????????????????
        studentResult.forEach(student1 ->{//??????????????????
            student1.setTitle(student.getTitle());
        });
        //4.?????????????????????????????????
        studentResult.forEach(s->{
            List<StudentFamily> list = new ArrayList<>();
            studentList.stream().forEach(t->{
                if(s.getId().equals(t.getId())){
                    if(t.getParentName()!=null && t.getRelation() !=null && t.getContactWay() !=null){
                        StudentFamily studentFamily = new StudentFamily();
                        studentFamily.setName(t.getParentName());//????????????
                        studentFamily.setRelation(t.getRelation());//????????????
                        studentFamily.setContactWay(t.getContactWay());//?????????????????????
                        list.add(studentFamily);
                    }
                }
            });
            if(!list.isEmpty()){
                s.setStudentFacmilyL(list);
            }
        });
        if(!studentResult.isEmpty()){
            Collections.sort(studentResult, Comparator.comparingInt(Student::getSeatNumber));
        }
        return studentResult;
    }
    //????????????IM
    public long batchUpdateStudentRegisterStatus(Student student){
        getExecutor().execute(() ->
                getImStudentStatus(student)
        );

        long rt = 0;

        return rt;
    }
    @Transactional(readOnly = true)
    public List<Student> findTeacherClassListim(Student student) {
        return studentDao.findTeacherClassListim(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentListByCondition4Like(Student student) {
        return studentDao.findStudentListByCondition4Like(student);
    }
    @Transactional(readOnly = true)
    public List<Student> findStudentListByConditionim(Student student) {
        return studentDao.findStudentListByConditionim(student);
    }

    @Transactional(readOnly = true)
    public List<ClassesStudentScoreNum> findStudentCountClassesByCondition(Map map){
        List<ClassesStudentScoreNum> classesStudentScoreNums= studentDao.findStudentCountClassesByCondition(map);
        classesStudentScoreNums.forEach(classesStudentScoreNum -> {
            if(classesStudentScoreNum.getStudentNum()==null){
                classesStudentScoreNum.setStudentNum(0);
            }
        });
        return classesStudentScoreNums;
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentInfoAndFamily(Student student) {
        Student s = studentDao.findStudentById(student.getId());
        StudentFamily studentFamily = new StudentFamily();
        studentFamily.setStudentId(s.getId());
        List<StudentFamily> studentFamilyList= studentFamilyDao.findStudentFamilyListByCondition(studentFamily);
        s.setStudentFacmilyL(studentFamilyList);
        List<Student> list = new ArrayList<>();
        list.add(s);
        return list;
    }
    //??????
    @Transactional
    public List<Map<String,Object>> findStudentListClassesByCondition(List<JwClasses> jwClassesList){
        Map<String,Object> map = new HashMap<>();
        map.put("classesIds",jwClassesList);
        map.put("schoolId",jwClassesList.get(0).getSchoolId());
        return studentDao.findStudentListClassesByCondition(map);
    }
    @Transactional(readOnly = true)
    public List<Student> findStudentListForExamByCondition(Student student){
        return studentDao.findStudentListForExamByCondition(student);
    }
    @Transactional(readOnly = true)
    public List<ExamStudentInfo> findStudentListByClazzIds(List<String> clazzIds) {
        return studentDao.findStudentListByClazzIds(clazzIds);
    }
    @Transactional(readOnly = true)
    public List<Student> findStudentWithParent(StuParentVo stuParentVo){
        return studentDao.findStudentWithParent(stuParentVo);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentLogin(StudentAccount studentAccount){
        return studentDao.findStudentLogin(studentAccount);
    }

    public List<StudentForSchoolNotify> findStudentListForSchoolNotify(StudentForSchoolNotify student) {
        return studentDao.findStudentListForSchoolNotify(student);
    }

    /**
     * ???????????????
     */
    public  ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }
    /**
     * im????????????
     */
    public void getImStudentStatus(Student student){

        student.setRegisterStatus(Constant.IM_TYPE.REGISTER_STATUS_NOT);//?????????IM
        student.setPager(new Pager().setPageSize(500).setIncludes("name","id","sex","imgUrl"));
        long b = 0 ;
        b= studentDao.findStudentCount1ByCondition(student);
        if( b == 0 ){

            return ;
        }
        b = b%500==0?(b/500):((b/500)+1);

        int i =  0 ;
        long c =0;
        do {

            IMReturnResult<List<UserRigisterApiResult>> result = JMessageUserApi.registerJMessageUsers(studentDao.findStudentListByConditionWithBmp(student).stream().map(s->{  //?????????
                UserRegister userRegister = new UserRegister();
                userRegister.setUserName(s.getId());
                userRegister.setShowName(s.getName());
                userRegister.setPassword(DigestUtil.md5Hex(s.getId()));  // id MD5????????? ???????????????
                userRegister.setSex(Integer.parseInt(s.getSex())-3);
                userRegister.setUserType("1");
                if(s.getImgUrl()!=null){
                    userRegister.setDefaultHeadImage(s.getImgUrl());
                }

                return userRegister;
            }).collect(Collectors.toList()),Constant.APP_TYPE.BMP_TYPE);
            if(result.getSuccess()){    //??????IM????????????????????????????????????
                Map<String, Object> updateStudentStatusMap = new HashMap<String, Object>();
                updateStudentStatusMap.put("studentIds",result.getData().stream().map(UserRigisterApiResult::getUsername).distinct().collect(Collectors.toList()));
                updateStudentStatusMap.put("schoolId",student.getSchoolId());

                studentDao.batchUpdateStudentRegisterStatus(updateStudentStatusMap);
            }
            i++;
            c =  studentDao.findStudentCount1ByCondition(student);

        }while ( i<b && c>0);
        return ;
    }

    @Transactional
    public  void batchUpdateStudent(List<Student> studentList)  {

      /*  studentList = studentList.stream().map(s->{
            s.setId(sequenceId.nextId());
            return s;
        }).collect(Collectors.toList());*/
        studentDao.batchUpdateStudent(studentList);
    }

    @Transactional
    public  Student getStudentLoginInfo(String id)  {
        return studentDao.getStudentLoginInfo(id);
    }


    public List<Student> findStudentListByStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return studentDao.findStudentListByStudentIdsAndSchoolId(kqDeviceGroupPerson);
    }
    public List<Student> findStudentListByExcludeStudentIdsAndSchoolId(KqDeviceGroupPerson kqDeviceGroupPerson) {
        return studentDao.findStudentListByExcludeStudentIdsAndSchoolId(kqDeviceGroupPerson);
    }
    public void updateStudentByCondition(Student studentData, Student studentCondition) {
        studentDao.updateStudentByCondition(studentData,studentCondition);
    }

    public List<Student> findStudentsByIds(String groupId) {
        return studentDao.findStudentsByIds(groupId);

    }

    public List<Student> findStudentListByClassIdAndName(Student student) {
        return studentDao.findStudentListByClassIdAndName(student);
    }

    public Map<String, Long> findStudentSummaryBySchool4Index(String schoolId) {
        return studentDao.findStudentSummaryBySchool4Index(schoolId);
    }

    public List<Map<String, Object>> findGradeStudentSummaryBySchool4Index(String schoolId) {
        return studentDao.findGradeStudentSummaryBySchool4Index(schoolId);
    }

    public List<Student> findStudentByIds(List<String> studentIds) {
        return studentDao.findStudentByIds(studentIds);
    }

    public List<Map<String, Object>> findSchoolStudentNowStatusNumGroupByClassesId(String schoolId) {
        return studentDao.findSchoolStudentNowStatusNumGroupByClassesId(schoolId);
    }
    public List<Map<String, Object>> findSchoolStudentNowStatusNum(String schoolId) {
        return studentDao.findSchoolStudentNowStatusNum(schoolId);
    }
    
    public List<StudentPojo> findStudentListByClassIdList(Student student){
        return studentDao.findStudentListByClassIdList(student);
    }

    public Long findStudentNoCountByStudentNo(String studentNo,String schoolId) {
        return studentDao.findStudentNoCountByStudentNo(studentNo, schoolId);
    }

    public void resetPassword(List<String> studentIds) {
        studentDao.resetPassword(studentIds,DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.STUDENT_DEFAULT_PWD)));
    }


    public long findStudentNoCountByStudentNoExceptSelf(String studentNo, String schoolId, String id) {
        return studentDao.findStudentNoCountByStudentNoExceptSelf(studentNo,schoolId,id);
    }

    public List<StudentPojo> findStudentListAndClassByClazzIdList(List<String> clazzIds) {
        return studentDao.findStudentListAndClassByClazzIdList(clazzIds);
    }

    public List<Student> findClassStudentByClassesId(DmActivitySiginUp dmActivitySiginUp) {
        List<Student> students = studentDao.findClassStudentByClassesId(dmActivitySiginUp.getClassesId());
        if(dmActivitySiginUp.getItemId()==null){
            return students;
        }
        List<String> studentIds = studentDao.selectSignUpStudentsByItemId(dmActivitySiginUp.getItemId(),dmActivitySiginUp.getClassesId());
        for (Student student : students) {
            student.setIsSignUp(0);
            for (String studentId : studentIds) {
                if(student.getId().equals(studentId)){
                    student.setIsSignUp(1);
                }
            }
        }
        return students;

    }
}