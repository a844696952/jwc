package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.osp.feignClient.jw.building.BuildingFeign;
import com.yice.edu.cn.osp.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.department.DepartmentTeacherFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormBuildAdminFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormPersonFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DormPersonService {
    @Autowired
    private DormPersonFeign dormPersonFeign;
    @Autowired
    private DormFeign dormFeign;
    @Autowired
    private DormBuildAdminFeign dormBuildAdminFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private BuildingFeign buildingFeign;
    @Autowired
    private JwClassesFeign classesFeign;

    public DormPerson findDormPersonById(String id) {
        return dormPersonFeign.findDormPersonById(id);
    }

    public DormPerson saveDormPerson(DormPerson dormPerson) {
       return dormPersonFeign.saveDormPerson(dormPerson);
    }

    public List<DormPerson> findDormPersonListByCondition(DormPerson dormPerson) {
        return dormPersonFeign.findDormPersonListByCondition(dormPerson);
    }

    public DormPerson findOneDormPersonByCondition(DormPerson dormPerson) {
        return dormPersonFeign.findOneDormPersonByCondition(dormPerson);
    }

    public long findDormPersonCountByCondition(DormPerson dormPerson) {
        return dormPersonFeign.findDormPersonCountByCondition(dormPerson);
    }

    public long updateDormPerson(DormPerson dormPerson) {
       return dormPersonFeign.updateDormPerson(dormPerson);
    }

    public void deleteDormPerson(String id) {
        dormPersonFeign.deleteDormPerson(id);
    }

    public void deleteDormPersonByCondition(DormPerson dormPerson) {
        dormPersonFeign.deleteDormPersonByCondition(dormPerson);
    }

    /*------------------------------------------------------------------------------------*/
    public List<Student> findStudentListByConditionOnDorm(Student student) {
        return dormPersonFeign.findStudentListByConditionOnDorm(student);
    }

    public Long findStudentListCountByConditionOnDorm(Student student) {
        return dormPersonFeign.findStudentListCountByConditionOnDorm(student);
    }


    public List<Teacher> findTeacherListByConditionOnDorm(Teacher teacher) {
        return dormPersonFeign.findTeacherListByConditionOnDorm(teacher);
    }

    public Long findTeacherListCountByConditionOnDorm(Teacher teacher) {
        return dormPersonFeign.findTeacherListCountByConditionOnDorm(teacher);
    }


    public List<Teacher> findNoTeacherListByConditionOnDorm(Teacher teacher) {
        return dormPersonFeign.findNoTeacherListByConditionOnDorm(teacher);
    }

    public Long findNoTeacherListCountByConditionOnDorm(Teacher teacher) {
        return dormPersonFeign.findNoTeacherListCountByConditionOnDorm(teacher);
    }

    public long updateSaveDormPerson( DormPerson dormPerson){
        return dormPersonFeign.updateSaveDormPerson(dormPerson);
    }

    public List<DormPerson> findDormPersonListByConditionConnect(DormBuildVo dormBuildVo) {
        if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
            return dormPersonFeign.findDormPersonListConnectStudent(dormBuildVo);
        } else {
            return dormPersonFeign.findDormPersonListConnectTeacher(dormBuildVo);
        }

    }

    public  Map<String,Object> batchSaveDormPerson(List<DormPerson> dormPersonList) {
       Map<String,Object> batchMsg = new HashMap<>();
       List<String> errors = new ArrayList<>();
       dormPersonList.forEach(dormPerson -> {
           if(dormPerson.getId()!=null&&dormPerson.getPersonId()!=null){
               dormPerson.setSchoolId(mySchoolId());
               dormPerson.setIsDormLeader("0");
               long l = dormPersonFeign.updateSaveDormPerson(dormPerson);
                if (l<0){
                   if ("1".equals(dormPerson.getPersonType())){
                       Student student= studentFeign.findStudentById(dormPerson.getPersonId());
                       if (student!=null){
                           JwClasses jwClasses = classesFeign.findJwClassesById(student.getClassesId());
                           errors.add(student.getName()+" "+student.getStudentNo()+" "+jwClasses.getGradeName()+"("+jwClasses.getNumber()+")???");
                       }
                   }else {
                       Teacher teacher = teacherFeign.findTeacherById(dormPerson.getPersonId());
                       if (teacher!=null){
                           errors.add(teacher.getName()+" "+teacher.getWorkNumber());
                       }

                   }

                }

           }
       });

       if (errors.size()>0){
           batchMsg.put("202",errors);
       }else {
           batchMsg.put("200","????????????"+dormPersonList.size()+"???");
       }

       return batchMsg;
    }

    public DormBuildingPersonInfo getDormBuildingById(String id){
        return dormPersonFeign.getDormBuildingById(id);
    }


    public DormPerson findDormPersonByCondition(DormBuildVo dormBuildVo) {
        if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
           return dormPersonFeign.findDormPersonOneConnectStudent(dormBuildVo);
        } else {
            return dormPersonFeign.findDormPersonOneConnectTeacher(dormBuildVo);
        }
    }

    public List<DormPerson> batchFindDormPersonListByCondition(List<DormBuildVo> dormBuildVoList) {
        List<DormPerson> list = new ArrayList<>();
        if (dormBuildVoList!=null&&dormBuildVoList.size()>0){
            dormBuildVoList.forEach(dormBuildVo -> {
                if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
                    DormPerson dormPerson = dormPersonFeign.findDormPersonOneConnectStudent(dormBuildVo);
                    list.add(dormPerson);
                } else {
                    DormPerson dormPerson = dormPersonFeign.findDormPersonOneConnectTeacher(dormBuildVo);
                    list.add(dormPerson);
                }
            });
        }

        return list;
    }

    public void batchUpdateDormPerson(DormPerson dormPerson) {
        if (dormPerson!=null&&dormPerson.getIds().size()>0){
            List<String> ids = dormPerson.getIds();
            for (String id : ids) {
                dormPerson.setId(id);
                dormPersonFeign.updateDormPerson(dormPerson);
            }
        }
    }

    public void setDormLeader(DormPerson dormPerson) {
        DormPerson dormPersonFind = new DormPerson();
        dormPersonFind.setSchoolId(mySchoolId());
        dormPersonFind.setDormId(dormPerson.getDormId());
        dormPersonFind.setIsDormLeader("1");
        DormPerson oneDormPerson = dormPersonFeign.findOneDormPersonByCondition(dormPersonFind);
        if (oneDormPerson!=null&&oneDormPerson.getPersonId()!=null){
            oneDormPerson.setIsDormLeader("0");
            dormPersonFeign.updateDormPerson(oneDormPerson);
        }
        dormPerson.setIsDormLeader("1");
        dormPersonFeign.updateDormPerson(dormPerson);

    }

    public void leaveDormById(DormPerson dormPerson) {
        dormPersonFeign.leaveDorm(dormPerson);
    }


    public void batchLeaveDorm(DormPerson dormPerson) {
        List<String> ids = dormPerson.getIds();
        if (ids!=null&&ids.size()>0){
            for (String id : ids) {
              DormPerson d = new DormPerson();
              d.setId(id);
              d.setRemarks(dormPerson.getRemarks());
              this.leaveDormById(d);
            }
        }
    }




    public List<DormBuildingPersonInfo> getDormBuildBunkInfo(Dorm dorm) {
        List<DormBuildingPersonInfo> list = new ArrayList<>();
        List<DormBuildingPersonInfo> dormList= dormFeign.findDormListByTypeAndCategory(dorm);
        if (dormList!=null&&dormList.size()>0){
            list.addAll(dormList);
            dormList.forEach(d->{
                //??????????????????
                Building buildingFloor = buildingFeign.findBuildingById(d.getParentId());
                DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
                dormBuildingPersonInfo.setFloor(buildingFloor.getName());
                dormBuildingPersonInfo.setFloorId(buildingFloor.getId());
                dormBuildingPersonInfo.setPersonId(buildingFloor.getParentId());
                list.add(dormBuildingPersonInfo);
                //???????????????
                Building building = buildingFeign.findBuildingById(buildingFloor.getParentId());
                DormBuildingPersonInfo dormBuildingPersonInfo2 = new DormBuildingPersonInfo();
                dormBuildingPersonInfo2.setDormBuildName(building.getName());
                dormBuildingPersonInfo2.setDormBuildId(building.getId());
                dormBuildingPersonInfo2.setPersonId(building.getParentId());
                list.add(dormBuildingPersonInfo2);
            });
        }
        List<DormBuildingPersonInfo> collect = list.stream().distinct().collect(Collectors.toList());
        return collect;

    }


    public DormBuildingPersonInfo getDormBuildBunkInfoByDormId(String dormId) {
        DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
        Dorm dorm = new Dorm();
        dorm.setDormId(dormId);
        List<DormBuildingPersonInfo> dormList= dormFeign.findDormListByTypeAndCategory(dorm);
        if (dormList!=null&&dormList.size()>0){
            DormBuildingPersonInfo personInfo = dormList.get(0);
            dormBuildingPersonInfo.setDormName(personInfo.getDormName());
            dormBuildingPersonInfo.setDormId(personInfo.getDormId());
            Building buildingFloor = buildingFeign.findBuildingById(personInfo.getParentId());
            dormBuildingPersonInfo.setFloorId(buildingFloor.getId());
            dormBuildingPersonInfo.setFloor(buildingFloor.getName());
            Building building = buildingFeign.findBuildingById(buildingFloor.getParentId());
            dormBuildingPersonInfo.setDormBuildName(building.getName());
            dormBuildingPersonInfo.setDormBuildId(building.getId());
            return dormBuildingPersonInfo;
        }else {
            return null;
        }
    }

    public List<DormPerson> getDormByDormIdAndDormCategory(DormBuildVo dormBuildVo) {
        if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
            return dormPersonFeign.findDormPersonListConnectStudent(dormBuildVo);
        } else {
            List<DormPerson> dormPerson = dormPersonFeign.findDormPersonListConnectTeacher(dormBuildVo);
            dormPerson.forEach(teacher->{
                if (teacher.getPersonId()!=null){
                    List<String> deptList= new ArrayList<>();
                    List<DepartmentTeacher> department = departmentTeacherFeign.findDepartmentByTeacherId(teacher.getPersonId());
                    if (department!=null&&department.size()>0){
                        for (DepartmentTeacher departmentTeacher : department) {
                            String departmentName = departmentTeacher.getDepartmentName();
                            deptList.add(departmentName);
                        }
                        String deptStr= deptList.toString();
                        String deptName = deptStr.substring(1, deptStr.length() - 1);
                        teacher.setDepartmentTeacherStr(deptName);
                    }

                }

            });

            return dormPerson;
        }
    }


    public void adjustDormByCondition(List<DormPerson> dormPersonList) {
        dormPersonFeign.adjustDormByCondition(dormPersonList);
    }


    public List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo) {
        return dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
    }

    public Long findDormPersonInfoCountWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo) {
        return dormPersonFeign.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
    }

    public List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo) {
        List<DormBuildingPersonInfo> list = dormPersonFeign.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
        if (list!=null&&list.size()>0){
            list.forEach(info->{
                List<DepartmentTeacher> departmentTeacherList = departmentTeacherFeign.findDepartmentByTeacherId(info.getPersonId());
                if(departmentTeacherList!=null&&departmentTeacherList.size()>0){
                    String deptNameStr = null;
                    List<String> deptNameList = departmentTeacherList.stream().map(DepartmentTeacher::getDepartmentName).collect(Collectors.toList());
                    String s = deptNameList.toString();
                    deptNameStr = s.substring(1, s.length()-1);
                    info.setDepartmentTeacherStr(deptNameStr);
                }

            });
        }
        return list;
    }

    public Long findDormPersonInfoCountWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo) {
        return dormPersonFeign.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
    }

    public  Teacher findHeadmasterByClasses(TeacherClasses teacherClasses ){
        return teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
    }

    //???????????????????????????????????????????????????
    private DormBuildVo judgingUser(String dormCategory){
        //???????????????????????????
        DormBuildAdmin dormBuildTeacher = new DormBuildAdmin();
        dormBuildTeacher.setSchoolId(mySchoolId());
        dormBuildTeacher.setStaffId(myId());
        dormBuildTeacher.setStaffType(Constant.DORM_STAFF_TYPE.DORM_TEACHER);
        List<DormBuildAdmin> dormBuildAdminList = dormBuildAdminFeign.findDormBuildAdminListByCondition(dormBuildTeacher);
        //dormBuildAdminList???????????????????????????,?????????????????????????????????
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setSchoolId(mySchoolId());
        dormBuildVo.setDormCategory(dormCategory);
        //???????????????,??????????????????????????????
        if (dormBuildAdminList==null&&dormBuildAdminList.size()==0){
            DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
            dormBuildAdmin.setSchoolId(mySchoolId());
            dormBuildAdmin.setStaffId(myId());
            dormBuildAdmin.setStaffType(Constant.DORM_STAFF_TYPE.DORM_ADMIN);
            List<DormBuildAdmin> list = dormBuildAdminFeign.findDormBuildAdminListByCondition(dormBuildTeacher);
            if (list!=null&&list.size()>0){
                list.forEach(ls->{
                    List<String> dormBuildIds = dormBuildVo.getDormBuildIdList();
                    dormBuildIds.add(ls.getDormBuildId());
                    dormBuildVo.setDormBuildIdList(dormBuildIds);
                });
            }else {
                List<String> dormBuildIds = dormBuildVo.getDormBuildIdList();
                dormBuildIds.add("-1");
                dormBuildVo.setDormBuildIdList(dormBuildIds);
            }
        }
        return dormBuildVo;
    }

    //????????????
    public Workbook findEmptyDormByDormCategory(DormBuildVo dormBuildVo){
        List<Map<String, String>> emptyDormList = dormPersonFeign.findEmptyDormByDormCategory(dormBuildVo);
        if (emptyDormList!=null&&emptyDormList.size()>0){
            emptyDormList.forEach(emptyDorm->{
                if ("1".equals(emptyDorm.get("dorm_type"))){
                    emptyDorm.put("dorm_type","????????????");
                }
                if ("2".equals(emptyDorm.get("dorm_type"))){
                    emptyDorm.put("dorm_type","????????????");
                }
            });
        }
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("?????????", "dormBuildName"));
        entity.add(new ExcelExportEntity("??????", "floor"));
        entity.add(new ExcelExportEntity("????????????", "dormName"));
        entity.add(new ExcelExportEntity("??????", "bunk_name"));
        entity.add(new ExcelExportEntity("????????????", "dorm_type"));
        entity.add(new ExcelExportEntity("*??????", ""));
        if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)){
            entity.add(new ExcelExportEntity("*??????", ""));
            return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????(??????)","????????????"),
                    entity, emptyDormList);
        }else if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_TEACHER)||dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_MASTER)){
            entity.add(new ExcelExportEntity("*????????????", ""));
            return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????(?????????/????????????)","?????????/??????????????????"),
                    entity, emptyDormList);
        }else {
            return null;
        }

    }

    //??????????????????
    public Map<String,Object> importDormPerson(MultipartFile file,String dormCategory) {
        Map<String, Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()) {
            List<DormImport> list = ExcelImportUtil.importExcel(is,DormImport.class, params);
            list = list.stream().filter(s1 -> !isAllFieldNull(s1)).collect(Collectors.toList());
            List<DormImport> dormImportList = new ArrayList<>();
            dormImportList.addAll(list);

            if (dormImportList.size() > 10000) {
                result.put("code", "202");  //??????????????????10000???
                result.put("error", "?????????????????????10000???");
                return result;
            } else if (dormImportList.size() == 0) {
                result.put("code", "201"); //????????????
                result.put("error", "?????????????????????");
                return result;
            }

            if(dormCategory.equals(Constant.DORM_CATEGORY.DORM_STUDENT)){
                Map<String, Object> errorMap = this.importStudent(dormImportList, dormCategory);
                result.putAll(errorMap);
                return result;
            }else if (dormCategory.equals(Constant.DORM_CATEGORY.DORM_TEACHER)||dormCategory.equals(Constant.DORM_CATEGORY.DORM_MASTER)){
                Map<String, Object> errorMap = this.importTeacherAndNo(dormImportList, dormCategory);
                result.putAll(errorMap);
                return result;
            }else {
                return null;
            }


        } catch (Exception e) {
            System.out.println("excel" + e);
            result.put("code", "999");  //??????????????????10000???
            result.put("error", "????????????????????????!");

        }


        return result;
    }




    //?????????????????????: ??????ture?????????????????????null  ??????false??????????????????????????????null
    public boolean isAllFieldNull(Object obj) {
        Class stuCla = (Class) obj.getClass();// ???????????????
        Field[] ff = stuCla.getDeclaredFields();//??????????????????
        boolean flag1 = true;
        try {
            for (Field f1 : ff) {//????????????
                f1.setAccessible(true); // ??????????????????????????????(??????????????????)
                Object val = f1.get(obj);// ?????????????????????
                if (val != null && !val.equals("null")) {//?????????1??????????????????,??????????????????????????????????????????
                    flag1 = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return flag1;
    }

    //?????????????????????????????????????????????????????????
    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    private Map<String, Object> importStudent(List<DormImport> dormImportList, String dormCategory){
        Map<String, Object> result = new HashMap<>();
        //??????????????????
        List<DormImport> errors = new ArrayList<>();
        //??????????????????
        List<DormImport> dealDormImport = new ArrayList<>();

        //???????????????????????????,??????????????????,???????????????????????????????????????
        final List<Student> data = new ArrayList<>();//??????list
        final List<Map<String, String>> data2 = new ArrayList<>();//?????????????????????list
        final List<DormPerson> data3 = new ArrayList<>();//?????????????????????
        //????????????????????????
        Student student = new Student();
        Pager pager = new Pager();
        pager.setPaging(false);
        student.setPager(pager);
        student.setSchoolId(mySchoolId());
        List<Student> studentList = studentFeign.findStudentListByCondition(student);
        data.addAll(studentList);
        DormBuildVo dormBuildVo = this.judgingUser(dormCategory);
        List<Map<String, String>> emptyDormList = dormPersonFeign.findEmptyDormByDormCategory(dormBuildVo);
        data2.addAll(emptyDormList);
        DormBuildVo dormBuildVo3 = new DormBuildVo();
        Pager pager3 = new Pager();
        pager3.setPaging(false);
        dormBuildVo3.setPager(pager);
        dormBuildVo3.setSchoolId(mySchoolId());
        List<DormPerson> dormPersonList = dormPersonFeign.findDormPersonListConnectStudent(dormBuildVo);
        data3.addAll(dormPersonList);


        //??????excel?????????????????????
        List<DormImport> dormImportIsNullList = dormImportList.stream().filter(dormImport -> {
            if(dormImport.getPersonName() == null && dormImport.getStudentNo() == null){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        //??????????????????????????????????????????????????????
        List<DormImport> dormImportError = dormImportIsNullList.stream().filter(dormImport -> {
            if ((dormImport.getPersonName() == null||dormImport.getStudentNo() == null)) {
                if (dormImport.getTel()!=null){
                    dormImport.setError("???????????????????????????");
                    return true;
                }else {
                    dormImport.setError("???????????????????????????");
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        errors.addAll(dormImportError);
        //????????????????????????????????????????????????
        dormImportList = dormImportList.stream().filter(dormImport -> dormImport.getPersonName()!=null&&dormImport.getStudentNo()!=null).collect(Collectors.toList());
        //???excel???studentNo???????????????????????????
        Map<String, List<DormImport>> map = dormImportList.stream().collect(Collectors.groupingBy(k -> k.getStudentNo()));
        map.forEach((c,v)->{
            if(v.size()>1){
                for (DormImport dormImport : v) {
                    dormImport.setError("???????????????");
                }
                errors.addAll(v);
            }
            if (v.size()==1){
                dealDormImport.addAll(v);
            }
        });

        for (DormImport d : dealDormImport) {
            Student studentIsTrue = null;
            Map<String, String> emptyDormIsTrue = null;

            //????????????????????????????????????????????????????????????????????????
            String personName = this.replaceBlank(d.getPersonName());
            String studentNo = this.replaceBlank(d.getStudentNo());

            if (d.getDormType()!=null){
                if ("????????????".equals(d.getDormType())){
                    d.setDormType("1");
                }
                if ("????????????".equals(d.getDormType())){
                    d.setDormType("2");
                }
            }


            boolean sFlag1 = true;
            boolean sFlag2 = true;
            boolean sFlag3 = true;
            for (Student studentData : data) {
                if (personName.equals(studentData.getName())){
                    sFlag1 = false;
                }
                if (studentNo.equals(studentData.getStudentNo())){
                    sFlag2 = false;
                }
                if (personName.equals(studentData.getName())&&studentNo.equals(studentData.getStudentNo())){
                    sFlag3 = false;
                    if ("4".equals(studentData.getSex())){
                        studentData.setSex("1");
                    }
                    if ("5".equals(studentData.getSex())){
                        studentData.setSex("2");
                    }
                    studentIsTrue = studentData;
                }

            }
            if (sFlag1){
                d.setError("??????????????????");
                errors.add(d);
                continue;
            }
            if (sFlag2){
                d.setError("??????????????????");
                errors.add(d);
                continue;
            }
            if (sFlag3){
                d.setError("???????????????????????????");
                errors.add(d);
                continue;
            }



            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            boolean flag5 = true;

            for (Map<String, String> emptyDorm : data2) {
                if (emptyDorm.get("dormBuildName").equals(d.getDormBuildName())) {
                    flag1 = false;
                }

               if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())){
                    flag2 = false;
                }

                if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())){
                    flag3 = false;
                }

               if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())&&emptyDorm.get("bunk_name").equals(d.getBunkName())){
                    flag4 = false;
                }

               if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())&&emptyDorm.get("bunk_name").equals(d.getBunkName())&&emptyDorm.get("dorm_type").equals(d.getDormType())){
                    flag5 = false;
                    emptyDormIsTrue = emptyDorm;
                }

            }

            if (flag4){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag3){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag2){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag1){
                d.setError("????????????????????????");
                errors.add(d);
                continue;
            }
            if (flag5){
                d.setError("???????????????????????????");
                errors.add(d);
                continue;
            }


            boolean pFlag = false;

            for (DormPerson dormPerson : data3) {
                if (d.getStudentNo().equals(dormPerson.getStudentNo())){
                    pFlag = true;
                }

            }

            if (pFlag){
                d.setError("???????????????????????????");
                errors.add(d);
                continue;
            }


            if (studentIsTrue.getSex().equals(emptyDormIsTrue.get("dorm_type"))){
                DormPerson dormPerson = new DormPerson();
                dormPerson.setId(emptyDormIsTrue.get("id"));
                dormPerson.setPersonId(studentIsTrue.getId());
                dormPerson.setPersonType("1");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String moveIntoTime = dateFormat.format(new Date());
                dormPerson.setMoveIntoTime(moveIntoTime);
                dormPerson.setSchoolId(mySchoolId());
                dormPerson.setDormId(emptyDormIsTrue.get("dormId"));
                dormPerson.setIsDormLeader("0");
                long row = dormPersonFeign.updateSaveDormPerson(dormPerson);
                if (row==0){
                    d.setError("????????????????????????");
                    errors.add(d);
                    continue;
                }
            }else {
                d.setError("???????????????????????????????????????");
                errors.add(d);
                continue;
            }


        }


        if (errors.size()>0){
            result.put("code", "222");
            result.put("errors", errors);
            result.put("success","????????????"+(dormImportIsNullList.size()-errors.size())+"???,????????????"+errors.size()+"???");
        }else {
            if (dormImportIsNullList.size()>0){
                result.put("code", "200");
                result.put("success","????????????"+dormImportIsNullList.size()+"???");
            }else {
                result.put("code", "200");
                result.put("success","?????????????????????!");
            }

        }

        return result;

    }


    private Map<String, Object> importTeacherAndNo(List<DormImport> dormImportList, String dormCategory) {
        Map<String, Object> result = new HashMap<>();
        //??????????????????
        List<DormImport> errors = new ArrayList<>();
        //??????????????????
        List<DormImport> dealDormImport = new ArrayList<>();

        //??????????????????????????????????????????,??????????????????,???????????????????????????????????????
        final List<Teacher> data = new ArrayList<>();//?????????????????????list
        final List<Map<String, String>> data2 = new ArrayList<>();//?????????????????????list
        final List<DormPerson> data3 = new ArrayList<>();//?????????????????????
        //????????????????????????
        Teacher teacher = new Teacher();
        Pager pager = new Pager();
        pager.setPaging(false);
        teacher.setPager(pager);
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> teacherList = teacherFeign.findTeacherListByCondition(teacher);
        data.addAll(teacherList);
        DormBuildVo dormBuildVo = this.judgingUser(dormCategory);
        List<Map<String, String>> emptyDormList = dormPersonFeign.findEmptyDormByDormCategory(dormBuildVo);
        data2.addAll(emptyDormList);
        DormBuildVo dormBuildVo3 = new DormBuildVo();
        Pager pager3 = new Pager();
        pager3.setPaging(false);
        dormBuildVo3.setPager(pager);
        dormBuildVo3.setSchoolId(mySchoolId());
        List<DormPerson> dormPersonList = dormPersonFeign.findDormPersonListConnectTeacher(dormBuildVo3);
        data3.addAll(dormPersonList);


        //??????excel?????????????????????
        List<DormImport> dormImportIsNullList = dormImportList.stream().filter(dormImport -> {
            if(dormImport.getPersonName() == null && dormImport.getTel() == null){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        //??????????????????????????????????????????????????????
        List<DormImport> dormImportError = dormImportIsNullList.stream().filter(dormImport -> {
            if ((dormImport.getPersonName() == null||dormImport.getTel()  == null)) {
                if (dormImport.getStudentNo()!=null){
                    dormImport.setError("???????????????????????????");
                    return true;
                }else {
                    dormImport.setError("???????????????????????????");
                    return true;
                }

            }
            return false;
        }).collect(Collectors.toList());
        errors.addAll(dormImportError);
        //????????????????????????????????????????????????
        dormImportList = dormImportList.stream().filter(dormImport -> dormImport.getPersonName()!=null&&dormImport.getTel() !=null).collect(Collectors.toList());
        //???excel??????????????????????????????
        Map<String, List<DormImport>> map = dormImportList.stream().collect(Collectors.groupingBy(k -> k.getTel()));
        map.forEach((c,v)->{
            if(v.size()>1){
                for (DormImport dormImport : v) {
                    dormImport.setError("?????????????????????");
                }
                errors.addAll(v);
            }
            if (v.size()==1){
                dealDormImport.addAll(v);
            }
        });

        for (DormImport d : dealDormImport) {
            Teacher teacherIsTrue = null;
            Map<String, String> emptyDormIsTrue = null;

            //???????????????????????????????????????????????????????????????????????????
            String personName = this.replaceBlank(d.getPersonName());
            String tel = this.replaceBlank(d.getTel());


            if (d.getDormType()!=null){
                if ("????????????".equals(d.getDormType())){
                    d.setDormType("1");
                }
                if ("????????????".equals(d.getDormType())){
                    d.setDormType("2");
                }
            }


            boolean sFlag1 = true;
            boolean sFlag2 = true;
            boolean sFlag3 = true;
            for (Teacher teacherData : data) {
                if (personName.equals(teacherData.getName())){
                    sFlag1 = false;
                }
                if (tel.equals(teacherData.getTel())){
                    sFlag2 = false;
                }
                if (personName.equals(teacherData.getName())&&tel.equals(teacherData.getTel())){
                    sFlag3 = false;
                    if ("4".equals(teacherData.getSex())){
                        teacherData.setSex("1");
                    }
                    if ("5".equals(teacherData.getSex())){
                        teacherData.setSex("2");
                    }
                    teacherIsTrue = teacherData;
                }

            }
            if (sFlag1){
                d.setError("?????????????????????????????????");
                errors.add(d);
                continue;
            }
            if (sFlag2){
                d.setError("????????????????????????");
                errors.add(d);
                continue;
            }
            if (sFlag3){
                d.setError("??????/???????????????????????????????????????");
                errors.add(d);
                continue;
            }



            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            boolean flag5 = true;

            for (Map<String, String> emptyDorm : data2) {
                if (emptyDorm.get("dormBuildName").equals(d.getDormBuildName())) {
                    flag1 = false;
                }

                if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())){
                    flag2 = false;
                }

                if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())){
                    flag3 = false;
                }

                if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())&&emptyDorm.get("bunk_name").equals(d.getBunkName())){
                    flag4 = false;
                }

                if(emptyDorm.get("dormBuildName").equals(d.getDormBuildName())&&emptyDorm.get("floor").equals(d.getFloor())&&emptyDorm.get("dormName").equals(d.getDormName())&&emptyDorm.get("bunk_name").equals(d.getBunkName())&&emptyDorm.get("dorm_type").equals(d.getDormType())){
                    flag5 = false;
                    emptyDormIsTrue = emptyDorm;
                }

            }

            if (flag4){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag3){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag2){
                d.setError("?????????????????????");
                errors.add(d);
                continue;
            }
            if (flag1){
                d.setError("????????????????????????");
                errors.add(d);
                continue;
            }

            if (flag5){
                d.setError("???????????????????????????");
                errors.add(d);
                continue;
            }


            boolean pFlag = false;

            for (DormPerson dormPerson : data3) {
                if (d.getTel().equals(dormPerson.getPersonTel())){
                    pFlag = true;
                }

            }

            if (pFlag){
                d.setError("?????????/??????????????????????????????");
                errors.add(d);
                continue;
            }


            if (teacherIsTrue.getSex().equals(emptyDormIsTrue.get("dorm_type"))){
                DormPerson dormPerson = new DormPerson();
                dormPerson.setId(emptyDormIsTrue.get("id"));
                dormPerson.setPersonId(teacherIsTrue.getId());
                if (teacherIsTrue.getPersonType()==1){
                    dormPerson.setPersonType("2");
                }
                if (teacherIsTrue.getPersonType()==2){
                    dormPerson.setPersonType("3");
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String moveIntoTime = dateFormat.format(new Date());
                dormPerson.setMoveIntoTime(moveIntoTime);
                dormPerson.setSchoolId(mySchoolId());
                dormPerson.setDormId(emptyDormIsTrue.get("dormId"));
                dormPerson.setIsDormLeader("0");
                long row = dormPersonFeign.updateSaveDormPerson(dormPerson);
                if (row==0){
                    d.setError("????????????????????????");
                    errors.add(d);
                    continue;
                }
            }else {
                d.setError("??????/?????????????????????????????????????????????");
                errors.add(d);
                continue;
            }


        }
        
        if (errors.size()>0){
            result.put("code", "222");
            result.put("errors", errors);
            result.put("success","????????????"+(dormImportIsNullList.size()-errors.size())+"???,????????????"+errors.size()+"???");
        }else {
            if (dormImportIsNullList.size()>0){
                result.put("code", "200");
                result.put("success","????????????"+dormImportIsNullList.size()+"???");
            }else {
                result.put("code", "200");
                result.put("success","?????????????????????!");
            }
        }

        return result;
    }



    public Workbook exportDormPersonInfoStudent(String[] ids) {
        DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
        dormBuildingPersonInfo.setSchoolId(mySchoolId());
        dormBuildingPersonInfo.setIds(ids);
        List<DormBuildingPersonInfo> dormPersonInfoWithStudentList = dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
        if (dormPersonInfoWithStudentList!=null&&dormPersonInfoWithStudentList.size()>0){
            dormPersonInfoWithStudentList.forEach(dormPersonInfo->{
                if ("4".equals(dormPersonInfo.getSex())){
                    dormPersonInfo.setSex("???");
                }
                if ("5".equals(dormPersonInfo.getSex())){
                    dormPersonInfo.setSex("???");
                }
            });
        }
        return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????(??????)","????????????"),
                DormBuildingPersonInfo.class, dormPersonInfoWithStudentList);

    }


    public Workbook exportDormPersonInfoTeacher(String[] ids){
        DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
        dormBuildingPersonInfo.setSchoolId(mySchoolId());
        dormBuildingPersonInfo.setIds(ids);
        List<Map<String, Object>> personInfoTeacherList = new ArrayList<>();
        List<DormBuildingPersonInfo> dormPersonInfoWithTeacher = dormPersonFeign.findDormPersonInfoWithTeacher(dormBuildingPersonInfo);
        if (dormPersonInfoWithTeacher!=null&&dormPersonInfoWithTeacher.size()>0){
            dormPersonInfoWithTeacher.forEach(personInfo->{
                if ("4".equals(personInfo.getSex())){
                    personInfo.setSex("???");
                }
                if ("5".equals(personInfo.getSex())){
                    personInfo.setSex("???");
                }
                if ("2".equals(personInfo.getPersonType())){
                    personInfo.setPersonType("?????????");
                }
                if ("3".equals(personInfo.getPersonType())){
                    personInfo.setPersonType("?????????");
                }
                List<DepartmentTeacher> departmentTeacherList = departmentTeacherFeign.findDepartmentByTeacherId((String)personInfo.getPersonId());
                List<String> deptName = new ArrayList<>();
                if (departmentTeacherList!=null&&departmentTeacherList.size()>0){
                    for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                        deptName.add(departmentTeacher.getDepartmentName());
                    }
                }
                if (deptName!=null&&deptName.size()>0){
                    String s = deptName.toString();
                    String substring = s.substring(1, s.length() - 1);
                    personInfo.setDepartmentTeacherStr(substring);
                }
                Map<String, Object> map = BeanUtil.beanToMap(personInfo);
                personInfoTeacherList.add(map);
            });
        }

        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("?????????", "dormBuildName"));
        entity.add(new ExcelExportEntity("??????", "floor"));
        entity.add(new ExcelExportEntity("????????????", "dormName"));
        entity.add(new ExcelExportEntity("??????", "bunkName"));
        entity.add(new ExcelExportEntity("??????", "personName"));
        entity.add(new ExcelExportEntity("??????", "personType"));
        entity.add(new ExcelExportEntity("??????", "workNumber"));
        entity.add(new ExcelExportEntity("??????", "sex"));
        entity.add(new ExcelExportEntity("??????", "DepartmentTeacherStr"));
        return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????(??????/????????????)","??????/??????????????????"),
                    entity, personInfoTeacherList);


    }

    public Workbook exportErrorDormPersonInfo(List<DormImport> dormImport) {
        List<Map<String, Object>> mapList  =new ArrayList<>();
        dormImport.forEach(d->{
            Map<String, Object> map = BeanUtil.beanToMap(d);
            mapList.add(map);
        });
        List<DormImport> dormImport1 = dormImport.stream().filter(d->d.getError().equals("??????????????????????????????")||d.getTel()==null).collect(Collectors.toList());
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("?????????", "dormBuildName"));
        entity.add(new ExcelExportEntity("??????", "floor"));
        entity.add(new ExcelExportEntity("????????????", "dormName"));
        entity.add(new ExcelExportEntity("??????", "bunkName"));
        entity.add(new ExcelExportEntity("??????", "personName"));
        if (dormImport1.size()>0){
            entity.add(new ExcelExportEntity("??????", "studentNo"));
        }else {
            entity.add(new ExcelExportEntity("????????????", "tel"));
        }
        entity.add(new ExcelExportEntity("????????????", "error"));
       return ExcelExportUtil.exportExcel(new ExportParams("????????????????????????","????????????"),
               entity, mapList);
    }

    //??????????????????????????????????????????
    public List<DormBuildingPersonInfo> findStudentDormInfo(String[] personIds) {
        List<DormBuildingPersonInfo> list = new ArrayList<>();
        for (String personId : personIds) {
            DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
            dormBuildingPersonInfo.setPersonId(personId);
            List<DormBuildingPersonInfo> dormPersonInfoWithStudentList = dormPersonFeign.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
            if(dormPersonInfoWithStudentList!=null&&dormPersonInfoWithStudentList.size()>0){
                list.addAll(dormPersonInfoWithStudentList);
            }

        }
       return list;
    }

    //????????????????????????:????????????ids???????????????????????????
    public boolean findDormIsPersonByDormId(List<String> dormIds){
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setDormIdList(dormIds);
        long count = dormPersonFeign.findDormMoveIntoPersonNumByCondition(dormBuildVo);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }


    //????????????????????????:?????????????????????
    public void updateDormCapacityByDormId(Building building){
        dormPersonFeign.updateDormCapacityByDormId(building);
    }

    public long findDormMoveIntoPersonNumByCondition(DormBuildVo dormBuildVo){
        return dormPersonFeign.findDormMoveIntoPersonNumByCondition(dormBuildVo);
    }

    public Map<String, Object> arrangeDorm(List<DormPerson> dormPersonList) {
        return dormPersonFeign.arrangeDorm( dormPersonList);
    }

    //???????????????????????????
    public void deleteDormAndDormPerson(List<String> dormIdList){
        dormPersonFeign.deleteDormAndDormPerson(dormIdList);
    }
}
