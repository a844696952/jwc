package com.yice.edu.cn.osp.service.jw.student;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentScoreNum;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentClassInfo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.nation.NationFeign;
import com.yice.edu.cn.osp.feignClient.region.RegionFeign;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StudentService {
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private DdFeign ddFeign;
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private NationFeign nationFeign;
    @Autowired
    private RegionFeign regionFeign;
    @Autowired
    private JwClassesFeign jwClassesFeign;

    public Student findStudentById(String id) {
        return studentFeign.findStudentById(id);
    }

    public Student saveStudent(Student student) {
        Student s = new Student();
        return studentFeign.saveStudent(student);
    }

    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public long findStudentCountByCondition(Student student) {
        return studentFeign.findStudentCountByCondition(student);
    }

    public long findStudentCodeCountByCondition(Student student) {
        return studentFeign.findStudentCodeCountByCondition(student);
    }

    public void updateStudent(Student student) {
        studentFeign.updateStudent(student);
    }

    public void updateStudentNew(Student student) {
        studentFeign.updateStudentNew(student);
    }

    public void deleteStudent(String id) {
        studentFeign.deleteStudent(id);
    }

    public void deleteStudentByCondition(Student student) {
        studentFeign.deleteStudentByCondition(student);
    }

    public List<Student> findStudentListByConditionWithFamily(Student student) {
        return studentFeign.findStudentListByConditionWithFamily(student);
    }

    public long findStudentCountByConditionWithFamily(Student student) {
        return studentFeign.findStudentCountByConditionWithFamily(student);
    }

    public List<Student> findStudentListByConditionWithBmp(Student student) {
        return studentFeign.findStudentListByConditionWithBmp(student);
    }

    public List<Student> findStudentListForClassesByCondition(Student student) {
        return studentFeign.findStudentListForClassesByCondition(student);
    }

    public long findStudentCountForClassesByCondition(Student student) {
        return studentFeign.findStudentCountForClassesByCondition(student);
    }

    public long findStudentCountByConditionForUpdate(Student student) {
        return studentFeign.findStudentCountByConditionForUpdate(student);
    }

    public Workbook exportStudent(Student student) {
        List<Student> studentList = studentFeign.findStudentListForExportStudentByCondition(student);
        return ExcelExportUtil.exportExcel(new ExportParams("????????????", "??????"),
                Student.class, studentList);
    }

    public Workbook exportStudentClass(Student student) {
        List<Student> studentList = studentFeign.findStudentListForExportStudentByCondition(student);
        List<StudentClassInfo> list = studentList.stream().map(item -> new StudentClassInfo(item.getStudentNo(), item.getName(), item.getGradeName(), item.getClassesNumber())).collect(Collectors.toList());
        return ExcelExportUtil.exportExcel(new ExportParams("??????????????????", "??????"),
                StudentClassInfo.class, list);
    }


    public Workbook exportTemplate() {      // ??????????????????
        List<Student> studentList = new ArrayList<>();
        Student student = new Student();
        student.setName("??????");
        student.setSex("???/???");
        student.setGuardianContact("11?????????-????????????????????????????????????");
        student.setEmail("????????????");
        student.setNationality("??????/??????(?????????)/??????");
        student.setNationName("?????????????????????");
        student.setNativePlace("?????????????????????");
        student.setProvinceName("?????????(????????????)");
        student.setCityName("?????????(????????????)");
        student.setCountyName("?????????(????????????)");
        student.setAddress("?????????110???(????????????)");
        student.setPoliticsFace("??????/??????/??????(????????????)");
        student.setBirthday("2018-01-01(????????????)");
        student.setStudentNo("??????(??????)");
        student.setAdmissionDate("2018-01-01(????????????)");
        student.setStudentCode("?????????(????????????)");
        student.setGradeName("(??????:??????/??????)");
        student.setClassesNumber("?????? (7??????7  20??????20)");
        student.setEnrollYear("?????????????????????2010???");
        student.setStatus("???????????? (??????)");
        student.setBoarder("????????????(???/???)");
        studentList.add(student);
        return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????", "??????"),
                Student.class, studentList);
    }

    public Workbook exportClassTemplate() {
        List<StudentClassInfo> studentClassInfoList = new ArrayList<>();
        StudentClassInfo studentClassInfo = new StudentClassInfo();
        studentClassInfo.setName("??????");
        studentClassInfo.setStudentNo("??????(??????)");
        studentClassInfo.setGradeName("(??????:??????/??????/?????????)");
        studentClassInfo.setClassesNumber("?????? (7??????7  20??????20)");
        studentClassInfoList.add(studentClassInfo);
        return ExcelExportUtil.exportExcel(new ExportParams("??????????????????????????????", "??????"),
                StudentClassInfo.class, studentClassInfoList);

    }


    public Map<String, Object> uploadStudentClass(MultipartFile file, String schoolId) {
        Map<String, Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        Student query = new Student();
        try (InputStream is = file.getInputStream()) {
            List<StudentClassInfo> list = ExcelImportUtil.importExcel(is,
                    StudentClassInfo.class, params);
            list = list.stream().filter(s1 -> !isAllFieldNull(s1)).collect(Collectors.toList());

            Map map = checkOver10000OrEmpty(list);
            if (map != null) {
                return map;
            }
            List<String> errors = new ArrayList<>();

            Pager pager = new Pager();
            pager.setPaging(false);
            Map<String, List<JwClasses>> classMap = new HashMap<>();//????????????????????????
            Dd dd = new Dd();
            dd.setPager(pager);
            dd.setTypeId(Constant.DD_TYPE.GRADE);
            School school = schoolFeign.findSchoolById(mySchoolId());
            dd.setLevelType(school.getTypeId());  //????????????
            //????????????
            List<Dd> gradeList = ddFeign.findDdListByCondition(dd);

            List<String> gradeNameList = gradeList.stream().map(Dd::getName).collect(Collectors.toList());
            for (Dd grade : gradeList) {
                JwClasses queryClass = new JwClasses();
                queryClass.setGradeName(grade.getName());
                queryClass.setSchoolId(mySchoolId());
                queryClass.setPager(pager);
                List<JwClasses> classList = jwClassesFeign.findJwClassesListByCondition(queryClass);
                classMap.put(grade.getName(), classList);
            }
            List<Student> studentList = CollUtil.newArrayList();
            for (int i = 0; i < list.size(); i++) {
                StudentClassInfo item = list.get(i);
                StringBuilder error = new StringBuilder();
                Student student = null;
                if (StrUtil.isNotBlank(item.getStudentNo())) {
                    query.setSchoolId(schoolId);
                    query.setStudentNo(item.getStudentNo());
                    student = studentFeign.findOneStudentByCondition(query);
                }
                JwClasses clazz = null;
                if (student == null) {
                    error.append("???????????????;");
                } else {
                    if (!StrUtil.equals(item.getName(), student.getName())) {
                        error.append("????????????????????????;");
                    }
                }
                if (!gradeNameList.contains(item.getGradeName())) {
                    error.append("?????????????????????;");
                } else {
                    List<JwClasses> jwClasses = classMap.get(item.getGradeName());
                    clazz = jwClasses.stream().filter(c -> StrUtil.equals(item.getClassesNumber(), c.getNumber())).findFirst().orElse(null);
                    if (clazz == null) {
                        error.append("?????????????????????;");
                    }
                }
                int j = i - 1;
                while (j >= 0) {
                    if (list.get(j).equals(list.get(i))) {
                        error.append("??????" + (j + 1) + "???????????????;");
                    }
                    j--;
                }

                //????????????list
                if (error.length() > 0) {
                    error.insert(0, "???" + (i + 1) + "???,");
                    errors.add(error.toString());
                } else {
                    Student s = new Student();
                    s.setId(student.getId());
                    s.setClassesId(clazz.getId());
                    Student queryStu = new Student();
                    queryStu.setClassesId(clazz.getId());
                    JwClasses jwClasses = jwClassesFeign.findJwClassesById(clazz.getId()); //?????????????????????,?????????????????????????????????????????????????????????  ??????????????????????????????????????????
                    queryStu.setEnrollYear(jwClasses.getEnrollYear());
                    Student maxSeatNumberStudent = studentFeign.findOneStudentMaxSeatNumberByCondition(queryStu); //???????????????????????????
                    s.setSeatNumber(maxSeatNumberStudent.getSeatNumber() + 1);
                    studentList.add(s);
                }
            }

            if (errors.size() > 0) {
                result.put("code", "222");
                result.put("errors", errors);
                return result;
            }

            studentFeign.batchUpdateStudent(studentList);
            result.put("code", "200");
        } catch (Exception e) {
            System.out.println("excel" + e);
        }


        return result;
    }

    public Map<String, Object> uploadStudent(MultipartFile file, String schoolId) {
        Map<String, Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()) {
            List<Student> studentList = ExcelImportUtil.importExcel(is,
                    Student.class, params);
            //????????????
            studentList = studentList.stream().filter(s1 -> !isAllFieldNull(s1)).collect(Collectors.toList());

            //???????????????????????????10000????????????
            Map map = checkOver10000OrEmpty(studentList);
            if (map != null) {
                return map;
            }

            //????????????
            List<String> errors = new ArrayList<>();

//            Map<String, Integer> repeatm = new ConcurrentReaderHashMap();
//            Map<String,Integer> repeatmStudentNo = new ConcurrentReaderHashMap();

            //????????????????????????ConcurrentReaderHashMap
            final Map<String, Integer> studentCodeMap = new HashMap<>();
            final Map<String, Integer> studentNoMap = new HashMap<>();

            final Map<String, List<JwClasses>> gradeClassMap = new HashMap<>(); //?????????????????????????????????
            final Map<String, Map<String, List<Student>>> stuMap = new HashMap<>();//????????????????????????????????????????????????


            //??????????????????????????????????????????????????????????????????????????????
            //???????????????
            final Pager pager = new Pager();
            pager.setPaging(false);

            Nation nation = new Nation();
            nation.setPager(pager);
            //??????list
            final List<Nation> nationList = new ArrayList<>(nationFeign.findNationListByCondition(nation));

            Region region = new Region();
            region.setPager(pager);
            //?????????list
            final List<Region> regionList = new ArrayList<>(regionFeign.findRegionListByCondition(region));

            Dd dd = new Dd();
            dd.setPager(pager);
            dd.setTypeId(Constant.DD_TYPE.GRADE);
            School school = schoolFeign.findSchoolById(mySchoolId());
            dd.setLevelType(school.getTypeId());  //????????????
            //??????list
            final List<Dd> gradeList = new ArrayList<>(ddFeign.findDdListByCondition(dd));

            //????????????????????????????????????
            for (Dd grade : gradeList) {
                //?????????????????????
                JwClasses queryClass = new JwClasses();
                queryClass.setGradeName(grade.getName());
                queryClass.setSchoolId(mySchoolId());
                queryClass.setPager(pager);
                List<JwClasses> jwClassesList = jwClassesFeign.findJwClassesListByCondition(queryClass);//???????????????????????????
                gradeClassMap.put(grade.getName(), jwClassesList);
                //?????????????????????
                Student queryStudent = new Student();
                queryStudent.setSchoolId(mySchoolId());
                queryStudent.setGradeName(grade.getName());
                queryStudent.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
                queryStudent.setPager(pager);
                List<Student> stuList = studentFeign.findStudentListForExamByCondition(queryStudent);//???????????????????????????
                //????????????????????????????????????????????????
                Map<String, List<Student>> classStuMap = stuList.stream().collect(Collectors.groupingBy(Student::getClassesNumber));//??????????????????????????????????????????
                classStuMap.forEach((k, v) -> v.sort((map1, map2) -> map2.getSeatNumber().compareTo(map1.getSeatNumber())));
                stuMap.put(grade.getName(), classStuMap);    //??????????????????????????????
            }

            for (int i = 0; i < studentList.size(); i++) {

                Pattern pattern;
                Student s = studentList.get(i);
                StringBuffer error = new StringBuffer();//????????????

                if (StringUtil.isNullOrEmpty(s.getName())) {
                    error.append("?????????????????????");
                }
                if (!StringUtil.isNullOrEmpty(s.getSex()) && !(s.getSex().equals(Constant.SEX_TYPE.MAN) || s.getSex().equals(Constant.SEX_TYPE.WOMAN))) {
                    error.append("?????????????????????????????????");
                } else if(StringUtil.isNullOrEmpty(s.getSex())){
                    error.append("?????????????????????");
                }

                if (StringUtil.isNullOrEmpty(s.getGuardianContact())) {
                    error.append("???????????????????????????");
                } else {
                    pattern = Pattern.compile("^[\\d]{11}$");
                    if (!pattern.matcher(s.getGuardianContact()).matches()) {
                        error.append("??????????????????11?????????????????????");
                    }
                }

                if (!StringUtil.isNullOrEmpty(s.getEmail())) {
                    pattern = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$");
                    if (!pattern.matcher(s.getEmail()).matches()) {
                        error.append("????????????????????????");
                    }
                }

                if (StringUtil.isNullOrEmpty(s.getNationality())) {
                    error.append("?????????????????????");
                } else {
                    if (!(s.getNationality().equals(Constant.NATIONALITY_TYPE.CHINA) || s.getNationality().equals(Constant.NATIONALITY_TYPE.CHINA_HONGKONG_MACAO_TAIWAN) || s.getNationality().equals(Constant.NATIONALITY_TYPE.FOREIGN_COUNTRY))) {
                        error.append("????????????????????????/??????(?????????)/?????????");
                    }
                }

                if (!StringUtil.isNullOrEmpty(s.getNationName())) {
                    boolean flag = true;
                    for (Nation n : nationList) {
                        if (n.getName().equals(s.getNationName())) {
                            s.setNation(n.getId());
                            flag = false;
                        }
                    }
                    if(flag){
                        error.append("????????????????????????");
                    }
                }

                if (!StringUtil.isNullOrEmpty(s.getNativePlace()) && s.getNativePlace().length() > 50) {
                    error.append("????????????????????????50???");
                }


                if (!StringUtil.isNullOrEmpty(s.getProvinceName())) {
                    boolean flag = true;
                    for (Region r : regionList) {
                        if (r.getName().equals(s.getProvinceName())) {
                            s.setProvinceId(r.getId());
                            flag = false;
                        }
                    }
                    if(flag){
                        error.append("?????????????????????????????????");
                    }
                }
                if (!StringUtil.isNullOrEmpty(s.getCityName())) {
                    boolean flag = true;
                    for (Region r : regionList) {
                        if (r.getName().equals(s.getCityName())) {
                            s.setCityId(r.getId());
                            flag = false;
                        }
                    }
                    if(flag){
                        error.append("?????????????????????????????????");
                    }
                }
                if (!StringUtil.isNullOrEmpty(s.getCountyName())) {
                    boolean flag = true;
                    for (Region r : regionList) {
                        if (r.getName().equals(s.getCountyName())) {
                            s.setCountyId(r.getId());
                            flag = false;
                        }
                    }
                    if(flag){
                        error.append("?????????????????????????????????");
                    }
                }

                if (!StringUtil.isNullOrEmpty(s.getAddress()) && s.getAddress().length() > 100) {
                    error.append("??????????????????????????????100???");

                }

                if (!StringUtil.isNullOrEmpty(s.getPoliticsFace())) {
                    if ("null".equals(s.getPoliticsFace())){
                        s.setPoliticsFace(null);
                    }else if (!(s.getPoliticsFace().equals(Constant.POLITICS_FACE_TYPE.INNOCEENCE_TYPE) || s.getPoliticsFace().equals(Constant.POLITICS_FACE_TYPE.LEAGUE_MEMBERS_TYPE) || s.getPoliticsFace().equals(Constant.POLITICS_FACE_TYPE.COMMUNIST_TYPE))) {
                            error.append("??????????????????????????????/??????/?????????");
                        }

                }

                if (!StringUtil.isNullOrEmpty(s.getBirthday())) {
                    pattern = Pattern.compile("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
                    if (!pattern.matcher(s.getBirthday()).matches()) {
                        error.append("????????????????????????????????????2018-01-01???");
                    }
                }
                if(StringUtil.isNullOrEmpty(s.getStudentNo())){
                    error.append("??????????????????");
                }else if(!StringUtil.isNullOrEmpty(s.getStudentNo()) && s.getStudentNo().length()>30) {
                    error.append("?????????????????????30???");
                } else {
                    if (studentNoMap.get(s.getStudentNo()) != null && studentNoMap.get(s.getStudentNo()) > 0) {
                        error.append("?????????????????????");
                    } else {
                        studentNoMap.put(s.getStudentNo(), 1);
                        //???????????????????????????
                        s.setSchoolId(schoolId);
                        if (studentFeign.findStudentNoCountByStudentNo(s) > 0) {
                            error.append("??????????????????");
                        }
                    }
                }
                if (!StringUtil.isNullOrEmpty(s.getAdmissionDate())) {
                    pattern = Pattern.compile("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
                    if (!pattern.matcher(s.getAdmissionDate()).matches()) {
                        error.append("??????????????????????????????????????????2018-01-01???");
                    }
                }else{
                    error.append("??????????????????????????????");
                }

                if(!StringUtil.isNullOrEmpty(s.getStudentCode())){
                    if( s.getStudentCode().length()>30) {
                        error.append("????????????????????????30??????");
                    } else {
                        if (studentCodeMap.get(s.getStudentCode()) != null && studentCodeMap.get(s.getStudentCode()) > 0) {
                            error.append("????????????????????????");
                        } else {
                            studentCodeMap.put(s.getStudentCode(), 1);
                            //??????????????????????????????

                            if (studentFeign.findStudentCodeForUpdateByStudentCode(s.getStudentCode()) > 0) {
                                error.append("?????????????????????");
                            }
                        }
                    }
                }
                if(!StringUtil.isNullOrEmpty(s.getGradeName())) {

                    boolean flag = true;
                    for (Dd grade : gradeList) {
                        if (grade.getName().equals(s.getGradeName())) {
                            s.setGradeId(grade.getId());
                            flag = false;
                        }
                    }

                    if (flag) {
                        error.append("??????????????????????????????");
                    }else{
                        if(!StringUtil.isNullOrEmpty(s.getClassesNumber())){
                            boolean flag1 = true;
                            JwClasses jwClasses = new JwClasses();

                            for (int j = 0; j < gradeClassMap.get(s.getGradeName()).size(); j++) {
                                if (gradeClassMap.get(s.getGradeName()).get(j).getNumber().equals(s.getClassesNumber())) {
                                    jwClasses = gradeClassMap.get(s.getGradeName()).get(j);
                                    flag1 = false;
                                }
                            }
                            if(!flag1){
                                if(StringUtil.isNullOrEmpty(s.getEnrollYear())){
                                    error.append("???????????????????????????");
                                }else if( jwClasses.getEnrollYear().equals(s.getEnrollYear()) ){
                                    s.setClassesId(jwClasses.getId());
                                }else{
                                    error.append("??????????????????????????????");
                                }
                            } else {
                                error.append("?????????????????????????????????");
                            }
                        }else{
                            error.append("????????????????????????");
                        }
                    }
                }else{
                    error.append("????????????????????????");
                }
                if(StringUtil.isNullOrEmpty(s.getStatus())){
                    error.append("???????????????????????????");
                }else if(!(s.getStatus().equals(Constant.STUDENT_STATUS_TYPE.STUDY_HAS))){
                    error.append("??????????????????????????????");

                }
                if (!StringUtil.isNullOrEmpty(s.getBoarder()) && !(s.getBoarder().equals(Constant.STUDENT_BOARDER_TYPE.BOARDER_HAS) || s.getBoarder().equals(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT))) {
                    error.append("??????????????????:???/??????");
                }else if(StringUtil.isNullOrEmpty(s.getBoarder())){
                    error.append("???????????????????????????");
                }

                //????????????list
                if (error.length() > 0) {
                    error.insert(0, "???" + i + "???,");
                    errors.add(error.toString());
                } else {
                    //????????????????????? id??????????????????
                    s.setSchoolId(mySchoolId());
                    s.setDel("1");
                    s.setImgUrl(s.getSex().equals("4")?Constant.AVATAR.BOY:Constant.AVATAR.GIRL);
                    s.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
                }
            };

            if(errors.size()>0){
                result.put("code", "222");
                result.put("errors", errors);
                return result;
            }

            Map<String,List<Student>> studentListmap = studentList.stream().collect(Collectors.groupingBy(Student::getGradeName));   //?????????????????????????????????
            for(Map.Entry<String,List<Student>> entry : studentListmap.entrySet()){
                Map<String,List<Student>> stuListmap1 = entry.getValue().stream().collect(Collectors.groupingBy(Student::getClassesNumber));  //???????????????????????????????????????
                for (Map.Entry<String,List<Student>> entry1 :stuListmap1.entrySet()){
                    int maxSeat = 0;
                    if(stuMap.get(entry.getKey()).get(entry1.getKey())!=null) { //??????????????????????????????????????????????????????
                        maxSeat= stuMap.get(entry.getKey()).get(entry1.getKey()).get(0).getSeatNumber();
                    }
                    for (Student s1:entry1.getValue()) {
                        maxSeat++;                        //???????????????
                        s1.setSeatNumber(maxSeat);
                    }
                }
            }
            studentList.forEach(s->{
                s.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.STUDENT_DEFAULT_PWD)));
                s.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT);
            });
            studentFeign.batchSaveStudent(studentList);
            result.put("code", "200");
        }catch (Exception e){
            System.out.println("excel"+e);
        }


        return result;
    }

    //?????????????????????: ??????ture?????????????????????null  ??????false??????????????????????????????null
    public  boolean isAllFieldNull(Object obj){
        Class stuCla = (Class) obj.getClass();// ???????????????
        Field[] ff = stuCla.getDeclaredFields();//??????????????????
        boolean flag1 = true;
        try{
            for (Field f1 : ff) {//????????????
                if(f1.getName().equals("serialVersionUID"))
                    continue;
                f1.setAccessible(true); // ??????????????????????????????(??????????????????)
                Object val = f1.get(obj);// ?????????????????????
                if(val!=null&&!val.equals("null")) {//?????????1??????????????????,??????????????????????????????????????????
                    flag1 = false;
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return flag1;
    }

    public List<Student> findCorrespondencesByStudent(Student student) {

        return studentFeign.findCorrespondencesByStudent(student);
    }
    public List<Student> findTeacherClassList(Student student) {
        return studentFeign.findTeacherClassList(student);
    }
    public List<Student> findClassStudentByclassTitle(Student student) {
        return studentFeign.findClassStudentByclassTitle(student);
    }


    public Student findOneStudentMaxSeatNumberByCondition(Student student){
        return studentFeign.findOneStudentMaxSeatNumberByCondition(student);
    };

    public void findStudentRowNumber(Student student){  //????????????
        studentFeign.findStudentRowNumber(student);
    }

    public long batchUpdateStudentRegisterStatus(Student student){  //??????IM??????
        return studentFeign.batchUpdateStudentRegisterStatus(student);
    }

    public List<ClassesStudentScoreNum> findStudentCountClassesByCondition(Map map){
        return studentFeign.findStudentCountClassesByCondition(map);
    }

    public List<Map<String,Object>> findStudentListClassesByCondition(List<JwClasses> jwClassesList){
        return studentFeign.findStudentListClassesByCondition(jwClassesList);
    }
    //????????????
    public void batchUpdateStudent(List<Student> studentList){
        for(Student student:studentList){
            studentFeign.updateStudent(student);
        }
    }

    public List<Student> findStudentListByClassIdAndName(Student student) {
        return studentFeign.findStudentListByClassIdAndName(student);
    }

    public Map<String,String> uploadAvatar(MultipartFile file) {
        //??????????????????
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0,fileName.lastIndexOf("."));
        //???-??????
        String[] arr = name.split("-");
        Map<String,String> result = new HashMap<>();
        if(arr.length!=2|| StringUtils.isEmpty(arr[0])||StringUtils.isEmpty(arr[1])){
            result.put("code","2001");
            result.put("msg","??????["+name+"]???????????????");
        }else{
            Student student = new Student();
            student.setSchoolId(mySchoolId());
            student.setName(arr[0]);
            student.setStudentNo(arr[1]);
            student = studentFeign.findOneStudentByCondition(student);
            if(student!=null&&StringUtils.isNotEmpty(student.getId())){
                Student up = new Student();
                up.setId(student.getId());
                up.setImgUrl(QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR));
                studentFeign.updateStudent(up);
                result.put("code","2000");
                result.put("msg","????????????");
            }else {
                result.put("code","2002");
                result.put("msg","??????["+name+"]???????????????");
            }
        }
        return result;
    }

    public void resetPassword(List<String> ids) {
        studentFeign.resetPassword(ids);
    }

    public long findStudentNoCountByStudentNoExceptSelf(Student student) {
        return studentFeign.findStudentNoCountByStudentNoExceptSelf(student);
    }

    public long findStudentCountByNoAndSchoolId(Student student) {
        return studentFeign.findStudentNoCountByStudentNo(student);
    }


    private Map checkOver10000OrEmpty(List list) {
        Map<String, String> result = new HashMap<>();
        if (list.size() > 10000) {
            result.put("code", "202");  //??????????????????10000???
            result.put("error", "?????????????????????10000???");
            return result;
        } else if (list.size() == 0) {
            result.put("code", "201"); //????????????
            result.put("error", "?????????????????????");
            return result;
        }
        return null;
    }
}
