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
                           errors.add(student.getName()+" "+student.getStudentNo()+" "+jwClasses.getGradeName()+"("+jwClasses.getNumber()+")班");
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
           batchMsg.put("200","成功添加"+dormPersonList.size()+"人");
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
                //查询宿舍楼层
                Building buildingFloor = buildingFeign.findBuildingById(d.getParentId());
                DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
                dormBuildingPersonInfo.setFloor(buildingFloor.getName());
                dormBuildingPersonInfo.setFloorId(buildingFloor.getId());
                dormBuildingPersonInfo.setPersonId(buildingFloor.getParentId());
                list.add(dormBuildingPersonInfo);
                //查询宿舍楼
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

    //根据当前登录人判断登录人的数据权限
    private DormBuildVo judgingUser(String dormCategory){
        //判断该登录人的权限
        DormBuildAdmin dormBuildTeacher = new DormBuildAdmin();
        dormBuildTeacher.setSchoolId(mySchoolId());
        dormBuildTeacher.setStaffId(myId());
        dormBuildTeacher.setStaffType(Constant.DORM_STAFF_TYPE.DORM_TEACHER);
        List<DormBuildAdmin> dormBuildAdminList = dormBuildAdminFeign.findDormBuildAdminListByCondition(dormBuildTeacher);
        //dormBuildAdminList有值时则为管理老师,可以查看该校的所有宿舍
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setSchoolId(mySchoolId());
        dormBuildVo.setDormCategory(dormCategory);
        //为宿管老师,只可以查看管理的楼栋
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

    //导出模板
    public Workbook findEmptyDormByDormCategory(DormBuildVo dormBuildVo){
        List<Map<String, String>> emptyDormList = dormPersonFeign.findEmptyDormByDormCategory(dormBuildVo);
        if (emptyDormList!=null&&emptyDormList.size()>0){
            emptyDormList.forEach(emptyDorm->{
                if ("1".equals(emptyDorm.get("dorm_type"))){
                    emptyDorm.put("dorm_type","男生宿舍");
                }
                if ("2".equals(emptyDorm.get("dorm_type"))){
                    emptyDorm.put("dorm_type","女生宿舍");
                }
            });
        }
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("宿舍楼", "dormBuildName"));
        entity.add(new ExcelExportEntity("楼层", "floor"));
        entity.add(new ExcelExportEntity("宿舍名称", "dormName"));
        entity.add(new ExcelExportEntity("床位", "bunk_name"));
        entity.add(new ExcelExportEntity("宿舍类型", "dorm_type"));
        entity.add(new ExcelExportEntity("*姓名", ""));
        if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)){
            entity.add(new ExcelExportEntity("*学号", ""));
            return ExcelExportUtil.exportExcel(new ExportParams("宿舍人员导入模板(学生)","学生宿舍"),
                    entity, emptyDormList);
        }else if (dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_TEACHER)||dormBuildVo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_MASTER)){
            entity.add(new ExcelExportEntity("*联系方式", ""));
            return ExcelExportUtil.exportExcel(new ExportParams("宿舍人员导入模板(教职工/非教职工)","教职工/非教职工宿舍"),
                    entity, emptyDormList);
        }else {
            return null;
        }

    }

    //导入住宿人员
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
                result.put("code", "202");  //导入数量超过10000条
                result.put("error", "超出导入上限：10000条");
                return result;
            } else if (dormImportList.size() == 0) {
                result.put("code", "201"); //导入为空
                result.put("error", "请勿上传空文件");
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
            result.put("code", "999");  //导入数量超过10000条
            result.put("error", "请上传正确的文件!");

        }


        return result;
    }




    //判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
    public boolean isAllFieldNull(Object obj) {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] ff = stuCla.getDeclaredFields();//得到属性集合
        boolean flag1 = true;
        try {
            for (Field f1 : ff) {//遍历属性
                f1.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
                Object val = f1.get(obj);// 得到此属性的值
                if (val != null && !val.equals("null")) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                    flag1 = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return flag1;
    }

    //除字符串中的空格、回车、换行符、制表符
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
        //错误信息集合
        List<DormImport> errors = new ArrayList<>();
        //处理完的集合
        List<DormImport> dealDormImport = new ArrayList<>();

        //一次查出所有的学生,宿舍床位信息,已入住学生信息减少查库次数
        final List<Student> data = new ArrayList<>();//学生list
        final List<Map<String, String>> data2 = new ArrayList<>();//所有空床位信息list
        final List<DormPerson> data3 = new ArrayList<>();//已入住学生信息
        //设置查询时不分页
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


        //去除excel中未安排的宿舍
        List<DormImport> dormImportIsNullList = dormImportList.stream().filter(dormImport -> {
            if(dormImport.getPersonName() == null && dormImport.getStudentNo() == null){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        //将姓名或学号填写不完整的错误信息排除
        List<DormImport> dormImportError = dormImportIsNullList.stream().filter(dormImport -> {
            if ((dormImport.getPersonName() == null||dormImport.getStudentNo() == null)) {
                if (dormImport.getTel()!=null){
                    dormImport.setError("填写的信息不正确；");
                    return true;
                }else {
                    dormImport.setError("必填信息不能为空；");
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        errors.addAll(dormImportError);
        //获取姓名和学号都填写了的信息列表
        dormImportList = dormImportList.stream().filter(dormImport -> dormImport.getPersonName()!=null&&dormImport.getStudentNo()!=null).collect(Collectors.toList());
        //将excel中studentNo重复学号的信息排除
        Map<String, List<DormImport>> map = dormImportList.stream().collect(Collectors.groupingBy(k -> k.getStudentNo()));
        map.forEach((c,v)->{
            if(v.size()>1){
                for (DormImport dormImport : v) {
                    dormImport.setError("学号重复；");
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

            //去除学生姓名和学号中的空格、回车、换行符、制表符
            String personName = this.replaceBlank(d.getPersonName());
            String studentNo = this.replaceBlank(d.getStudentNo());

            if (d.getDormType()!=null){
                if ("男生宿舍".equals(d.getDormType())){
                    d.setDormType("1");
                }
                if ("女生宿舍".equals(d.getDormType())){
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
                d.setError("学生不存在；");
                errors.add(d);
                continue;
            }
            if (sFlag2){
                d.setError("学号不存在；");
                errors.add(d);
                continue;
            }
            if (sFlag3){
                d.setError("学生与学号不匹配；");
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
                d.setError("该床位不存在；");
                errors.add(d);
                continue;
            }
            if (flag3){
                d.setError("该宿舍不存在；");
                errors.add(d);
                continue;
            }
            if (flag2){
                d.setError("该楼层不存在；");
                errors.add(d);
                continue;
            }
            if (flag1){
                d.setError("该宿舍楼不存在；");
                errors.add(d);
                continue;
            }
            if (flag5){
                d.setError("该宿舍类型不正确；");
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
                d.setError("该学生已安排床位；");
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
                    d.setError("该床位已被占用；");
                    errors.add(d);
                    continue;
                }
            }else {
                d.setError("学生性别与宿舍类型不匹配；");
                errors.add(d);
                continue;
            }


        }


        if (errors.size()>0){
            result.put("code", "222");
            result.put("errors", errors);
            result.put("success","导入成功"+(dormImportIsNullList.size()-errors.size())+"条,导入失败"+errors.size()+"条");
        }else {
            if (dormImportIsNullList.size()>0){
                result.put("code", "200");
                result.put("success","导入成功"+dormImportIsNullList.size()+"条");
            }else {
                result.put("code", "200");
                result.put("success","请勿上传空模板!");
            }

        }

        return result;

    }


    private Map<String, Object> importTeacherAndNo(List<DormImport> dormImportList, String dormCategory) {
        Map<String, Object> result = new HashMap<>();
        //错误信息集合
        List<DormImport> errors = new ArrayList<>();
        //处理完的集合
        List<DormImport> dealDormImport = new ArrayList<>();

        //一次查出所有的教师及非教职工,宿舍床位信息,已入住学生信息减少查库次数
        final List<Teacher> data = new ArrayList<>();//教师及非教职工list
        final List<Map<String, String>> data2 = new ArrayList<>();//所有空床位信息list
        final List<DormPerson> data3 = new ArrayList<>();//已入住学生信息
        //设置查询时不分页
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


        //去除excel中未安排的宿舍
        List<DormImport> dormImportIsNullList = dormImportList.stream().filter(dormImport -> {
            if(dormImport.getPersonName() == null && dormImport.getTel() == null){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        //将姓名或电话填写不完整的错误信息排除
        List<DormImport> dormImportError = dormImportIsNullList.stream().filter(dormImport -> {
            if ((dormImport.getPersonName() == null||dormImport.getTel()  == null)) {
                if (dormImport.getStudentNo()!=null){
                    dormImport.setError("填写的信息不正确；");
                    return true;
                }else {
                    dormImport.setError("必填信息不能为空；");
                    return true;
                }

            }
            return false;
        }).collect(Collectors.toList());
        errors.addAll(dormImportError);
        //获取姓名和电话都填写了的信息列表
        dormImportList = dormImportList.stream().filter(dormImport -> dormImport.getPersonName()!=null&&dormImport.getTel() !=null).collect(Collectors.toList());
        //将excel中电话重复的信息排除
        Map<String, List<DormImport>> map = dormImportList.stream().collect(Collectors.groupingBy(k -> k.getTel()));
        map.forEach((c,v)->{
            if(v.size()>1){
                for (DormImport dormImport : v) {
                    dormImport.setError("联系方式重复；");
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

            //去除教职工姓名和电话中的空格、回车、换行符、制表符
            String personName = this.replaceBlank(d.getPersonName());
            String tel = this.replaceBlank(d.getTel());


            if (d.getDormType()!=null){
                if ("男生宿舍".equals(d.getDormType())){
                    d.setDormType("1");
                }
                if ("女生宿舍".equals(d.getDormType())){
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
                d.setError("教师或非教职工不存在；");
                errors.add(d);
                continue;
            }
            if (sFlag2){
                d.setError("联系方式不存在；");
                errors.add(d);
                continue;
            }
            if (sFlag3){
                d.setError("教师/非教职工与联系方式不匹配；");
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
                d.setError("该床位不存在；");
                errors.add(d);
                continue;
            }
            if (flag3){
                d.setError("该宿舍不存在；");
                errors.add(d);
                continue;
            }
            if (flag2){
                d.setError("该楼层不存在；");
                errors.add(d);
                continue;
            }
            if (flag1){
                d.setError("该宿舍楼不存在；");
                errors.add(d);
                continue;
            }

            if (flag5){
                d.setError("该宿舍类型不正确；");
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
                d.setError("该教师/非教职工已安排床位；");
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
                    d.setError("该床位已被占用；");
                    errors.add(d);
                    continue;
                }
            }else {
                d.setError("教师/非教职工性别与宿舍类型不匹配；");
                errors.add(d);
                continue;
            }


        }
        
        if (errors.size()>0){
            result.put("code", "222");
            result.put("errors", errors);
            result.put("success","导入成功"+(dormImportIsNullList.size()-errors.size())+"条,导入失败"+errors.size()+"条");
        }else {
            if (dormImportIsNullList.size()>0){
                result.put("code", "200");
                result.put("success","导入成功"+dormImportIsNullList.size()+"条");
            }else {
                result.put("code", "200");
                result.put("success","请勿上传空模板!");
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
                    dormPersonInfo.setSex("男");
                }
                if ("5".equals(dormPersonInfo.getSex())){
                    dormPersonInfo.setSex("女");
                }
            });
        }
        return ExcelExportUtil.exportExcel(new ExportParams("宿舍人员入住信息(学生)","学生宿舍"),
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
                    personInfo.setSex("男");
                }
                if ("5".equals(personInfo.getSex())){
                    personInfo.setSex("女");
                }
                if ("2".equals(personInfo.getPersonType())){
                    personInfo.setPersonType("教职工");
                }
                if ("3".equals(personInfo.getPersonType())){
                    personInfo.setPersonType("教职工");
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
        entity.add(new ExcelExportEntity("宿舍楼", "dormBuildName"));
        entity.add(new ExcelExportEntity("楼层", "floor"));
        entity.add(new ExcelExportEntity("宿舍名称", "dormName"));
        entity.add(new ExcelExportEntity("床位", "bunkName"));
        entity.add(new ExcelExportEntity("姓名", "personName"));
        entity.add(new ExcelExportEntity("类型", "personType"));
        entity.add(new ExcelExportEntity("工号", "workNumber"));
        entity.add(new ExcelExportEntity("性别", "sex"));
        entity.add(new ExcelExportEntity("部门", "DepartmentTeacherStr"));
        return ExcelExportUtil.exportExcel(new ExportParams("宿舍人员入住信息(教师/非教职工)","教师/非教职工宿舍"),
                    entity, personInfoTeacherList);


    }

    public Workbook exportErrorDormPersonInfo(List<DormImport> dormImport) {
        List<Map<String, Object>> mapList  =new ArrayList<>();
        dormImport.forEach(d->{
            Map<String, Object> map = BeanUtil.beanToMap(d);
            mapList.add(map);
        });
        List<DormImport> dormImport1 = dormImport.stream().filter(d->d.getError().equals("姓名或学号不能为空；")||d.getTel()==null).collect(Collectors.toList());
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("宿舍楼", "dormBuildName"));
        entity.add(new ExcelExportEntity("楼层", "floor"));
        entity.add(new ExcelExportEntity("宿舍名称", "dormName"));
        entity.add(new ExcelExportEntity("床位", "bunkName"));
        entity.add(new ExcelExportEntity("姓名", "personName"));
        if (dormImport1.size()>0){
            entity.add(new ExcelExportEntity("学号", "studentNo"));
        }else {
            entity.add(new ExcelExportEntity("联系方式", "tel"));
        }
        entity.add(new ExcelExportEntity("错误信息", "error"));
       return ExcelExportUtil.exportExcel(new ExportParams("宿舍导入错误信息","错误信息"),
               entity, mapList);
    }

    //查看申请人员是否已经入住宿舍
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

    //对接楼栋管理方法:根据宿舍ids查询宿舍下是否有人
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


    //对接楼栋管理方法:修改宿舍的容量
    public void updateDormCapacityByDormId(Building building){
        dormPersonFeign.updateDormCapacityByDormId(building);
    }

    public long findDormMoveIntoPersonNumByCondition(DormBuildVo dormBuildVo){
        return dormPersonFeign.findDormMoveIntoPersonNumByCondition(dormBuildVo);
    }

    public Map<String, Object> arrangeDorm(List<DormPerson> dormPersonList) {
        return dormPersonFeign.arrangeDorm( dormPersonList);
    }

    //对接删除场地的方法
    public void deleteDormAndDormPerson(List<String> dormIdList){
        dormPersonFeign.deleteDormAndDormPerson(dormIdList);
    }
}
