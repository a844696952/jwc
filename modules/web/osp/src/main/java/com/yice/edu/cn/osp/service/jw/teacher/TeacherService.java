package com.yice.edu.cn.osp.service.jw.teacher;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.common.pojo.kqsdk.TeacherInfo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherPostFeign;
import com.yice.edu.cn.osp.feignClient.login.LoginFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private LoginFeign loginFeign;
    @Autowired
    private TeacherPostFeign teacherPostFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public Teacher findTeacherById(String id) {
        return teacherFeign.findTeacherById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        //判断手机号
        Teacher t = new Teacher();
        t.setTel(teacher.getTel());
        long c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("手机号重复");
            return t;
        }
        t = new Teacher();
        t.setDocumentNum(teacher.getDocumentNum());
        c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {
            t.setCode("222");
            t.setMsg("证件号码重复");
            return t;
        }
        t = new Teacher();
        t.setWorkNumber(teacher.getWorkNumber());
        t.setSchoolId(mySchoolId());
        c = teacherFeign.findTeacherCountByCondition(t);
        if (c > 0) {//同个学校内限制
            t.setCode("222");
            t.setMsg("工号重复");
            return t;
        }
        teacher.setSchoolId(mySchoolId());
        School school = schoolFeign.findSchoolById(mySchoolId());
        if(school==null){
            t.setCode("222");
            t.setMsg("非正常学校");
            return t;
        }
        teacher.setSchoolName(school.getName());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        t = teacherFeign.saveTeacher(teacher);
        t.setCode("200");
        t.setMsg("添加成功");
        return t;
    }

    public List<Teacher> findTeacherListByCondition(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition(teacher);
    }
    public List<Teacher> findTeacherListByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition4Like(teacher);
    }

    public long findTeacherCountByCondition(Teacher teacher) {
        return teacherFeign.findTeacherCountByCondition(teacher);
    }
    public long findTeacherCountByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherCountByCondition4Like(teacher);
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherFeign.updateTeacher(teacher);
    }
    public Teacher updateTeacherNew(Teacher teacher) {
        return teacherFeign.updateTeacherNew(teacher);
    }

    public void deleteTeacher(String id) {
        teacherFeign.deleteTeacher(id);
    }

    public void deleteTeacherByCondition(Teacher teacher) {
        teacherFeign.deleteTeacherByCondition(teacher);
    }

    public List<Perm> findTeacherTreeMenu(String id) {
        if(currentTeacher().getStatus().equals(Constant.STATUS.TEACHER_ADMIN)){
            return teacherFeign.findTeacherAdminTreeMenu(mySchoolId());
        }
        return teacherFeign.findTeacherTreeMenu(id);
    }

    public List<String> findCheckedRolesByTeacherId(String id) {
        return teacherFeign.findCheckedRolesByTeacherId(id);
    }

    public void delsertTeacherRoles(TeacherRole teacherRole) {
        teacherFeign.delsertTeacherRoles(teacherRole);
    }


    public List<Perm> findTeacherFuncPermsByTeacherId(String id) {
        return teacherFeign.findTeacherFuncPermsByTeacherId(id);
    }

    public List<TeacherPost> findTeacherPost(String teacherId){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacherId);
        teacherPost.setPager(new Pager().setPaging(false).setIncludes("name","ddId","gradeId","gradeName","classId","className","sort").setSortField("sort"));
        return teacherPostFeign.findTeacherPostListByCondition(teacherPost);
    }
    public Teacher saveTeacherAdmin(Teacher teacher) {
        teacher.setStatus(Constant.STATUS.TEACHER_ADMIN);
        return teacherFeign.saveTeacher(teacher);
    }

    public void updateTeacherAdmin(Teacher teacher) {
        teacherFeign.updateTeacherAdmin(teacher);

    }

    public Workbook exportTeacher(TeacherVo teacher){
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPager(Optional.ofNullable(teacher.getPager()).orElse(new Pager()).setPaging(false).setLike("name").addExcludes("password"));
        List<TeacherShow> teacherList;
        if(Constant.STATUS.TEACHER_ADMIN.equals(currentTeacher().getStatus())||"70".equals(teacher.getPostId())||"71".equals(teacher.getPostId())||"72".equals(teacher.getPostId())){
            teacherList = teacherFeign.findTeacherListWithTeaching(teacher);
        }else if("73".equals(teacher.getPostId())){
            teacherList = teacherFeign.findTeacherListInClassByCondition(teacher);
        }else {
            teacher.setId(myId());
            teacherList = teacherFeign.findTeacherListWithTeaching(teacher);
        }

        return ExcelExportUtil.exportExcel(new ExportParams("教师列表","教师"),
                Teacher.class, teacherList);
    }
    public Workbook exportTemplate(){
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setName("姓名");
        teacher.setSex("男/女");
        teacher.setTel("11位数字-手机号，懂？");
        teacher.setPinyin("可以不填");
        teacher.setInitials("可以不填");
        teacher.setEnglishName("可以不填");
        teacher.setQq("可以不写");
        teacher.setWeixin("可以不写");
        teacher.setEmail("可以不写");
        teacher.setNationality("中国/中国(港澳台)/国外");
        teacher.setNationName("国外的可以不写");
        teacher.setNativePlace("国外的可以不写");
        teacher.setDocumentType("身份证/护照(国内的写身份证，国外的写护照)");
        teacher.setDocumentNum("请正确填写证件号码");
        teacher.setTeacherNum("可以不写");
        teacher.setProvinceName("福建省(可以不写)");
        teacher.setCityName("福州市(可以不写)");
        teacher.setCountyName("台江区(可以不写)");
        teacher.setAddress("鳌峰路110号(可以不写)");
        teacher.setPoliticalFace("群众/团员/党员(可以不写)");
        teacher.setBirthDate("2018-01-01(可以不写)");
        teacher.setTeacherAge("4(数值，可以不写)");
        teacher.setTopEdu("专科/本科/硕士/博士/MBA/EMBA(可以不写)");
        teacher.setGraduate("北京大学(可以不写)");
        teacher.setMajor("园林管理(可以不写)");
        teacher.setBeginTime("2018-01-01(可以不写)");
        teacher.setWorks("可以不写");
        teacher.setMaritalStatus("可以不写(格式：未婚、已婚、丧偶、离婚、其他)");
        teacherList.add(teacher);
        return ExcelExportUtil.exportExcel(new ExportParams("教师导入模板","教师"),
                Teacher.class, teacherList);
    }
    public Workbook exportTeachingTemplate(){
        List<TeachingInfo> list = new ArrayList<>();
        list.add(new TeachingInfo().setWorkNumber("123456")
        .setTeacherName("亿老师").setGradeName("一年级(ps:初一/高一)").setClassNumber("1")
        .setSubjectName("语文(ps:别名)"));
        return ExcelExportUtil.exportExcel(new ExportParams("教师授课导入模板","授课"),
                TeachingInfo.class, list);
    }

    public Map<String,Object> uploadTeacher(MultipartFile file){
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()){
            List<Teacher> list = ExcelImportUtil.importExcel(is,
                    Teacher.class, params);
            List<Teacher> teachers = list.stream().filter(t -> !isAllFieldNull(t)).map(t -> {
                t.setSchoolId(mySchoolId());
                t.setSchoolName(currentTeacher().getSchoolName());
                return t;
            }).collect(Collectors.toList());
            result = teacherFeign.batchSaveTeacher(teachers);
        }catch (Exception e){

        }
        return result;
    }
    public Map<String,Object> uploadTeaching(MultipartFile file){
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()){
            List<TeachingInfo> list = ExcelImportUtil.importExcel(is,
                    TeachingInfo.class, params);
            List<TeachingInfo> teachingInfos = list.stream().filter(t -> !isAllFieldNull(t)).collect(Collectors.toList());
            result = teacherClassesFeign.batchSaveTeaching(mySchoolId(),teachingInfos);
        } catch (Exception e){

        }
        return result;
    }
    //判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
    public static boolean isAllFieldNull(Object obj){
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        try{
            for (Field f : fs) {//遍历属性
                if(f.getName().equals("serialVersionUID")){
                    continue;
                }
                f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
                Object val = f.get(obj);// 得到此属性的值
                if(val!=null&&!val.equals("null")) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                    flag = false;
                    break;
                }
            }
        }catch (Exception e){
        }
        return flag;
    }
    public Teacher updateMySelf(Teacher teacher) {
        teacherFeign.updateTeacherAdmin(teacher);
        School school = schoolFeign.findSchoolById(teacher.getSchoolId());
        teacher.setSchool(school);
        return teacher;
    }

    public List<Teacher> findCorrespondencesByTeacher(Teacher teacher) {
        return teacherFeign.findCorrespondencesByTeacher(teacher);
    }

    public List<Teacher> findTeacherListInfoByCondition(Teacher teacher) {
        return teacherFeign.findTeacherListInfoByCondition(teacher);
    }
    public int batchUpdateTeacherRegisterStatus(Teacher teacher){
        return teacherFeign.batchUpdateTeacherRegisterStatus(teacher);
    }
    public List<Teacher> findTeacherListSchoolId(Teacher teacher) {
        return teacherFeign.findTeacherListSchoolId(teacher);
    }

    public List<Teacher> findTeacherImgListByCondition(Teacher teacher){
        return teacherFeign.findTeacherImgListByCondition(teacher);
    }
    public long findTeacherImgCountByCondition(Teacher teacher){
        return teacherFeign.findTeacherImgCountByCondition(teacher);
    }

    public Map<String,String> uploadAvatar(MultipartFile file) {
        //获取图片名称
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0,fileName.lastIndexOf("."));
        //按-分割
        String[] arr = name.split("-");
        Map<String,String> result = new HashMap<>();
        if(arr.length!=2||StringUtils.isEmpty(arr[0])||StringUtils.isEmpty(arr[1])){
            result.put("code","2001");
            result.put("msg","图片["+name+"]命名不规范");
        }else{
            Teacher teacher = new Teacher();
            teacher.setSchoolId(mySchoolId());
            teacher.setName(arr[0]);
            teacher.setTel(arr[1]);
            teacher = teacherFeign.findOneTeacherByCondition(teacher);
            if(teacher!=null&&StringUtils.isNotEmpty(teacher.getId())){
                Teacher up = new Teacher();
                up.setId(teacher.getId());
                up.setImgUrl(QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR));
                teacherFeign.updateTeacher(up);
                result.put("code","2000");
                result.put("msg","上传成功");
            }else {
                result.put("code","2002");
                result.put("msg","图片["+name+"]无对应教师");
            }
        }
        return result;
    }

    public List<TeacherClasses> findTeacherClasses(String myId) {
        return teacherClassesFeign.findTeacherClasses(myId);
    }

    /**
     * 查询教师拥有的班牌权限
     * 列表
     * @param teacher
     * @return
     */
    public List<Teacher> findTeacherManagerById(Teacher teacher) {
        return teacherFeign.findTeacherManagerById(teacher);
    }

    public ResponseJson findTeacherListByPost(TeacherVo teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPager(Optional.ofNullable(teacher.getPager()).orElse(new Pager().setPaging(false)).setLike("name").addExcludes("password"));
        if(Constant.STATUS.TEACHER_ADMIN.equals(currentTeacher().getStatus())||"70".equals(teacher.getPostId())||"71".equals(teacher.getPostId())||"72".equals(teacher.getPostId())){
            List<TeacherShow> data = teacherFeign.findTeacherListWithTeaching(teacher);
            long count = teacherFeign.findTeacherCountByCondition(teacher);
            return new ResponseJson(data, count);
        }else if("73".equals(teacher.getPostId())){
            List<TeacherShow> data = teacherFeign.findTeacherListInClassByCondition(teacher);
            long count = teacherFeign.findTeacherCountInClassByCondition(teacher);
            return new ResponseJson(data, count);
        }else {
            TeacherVo temp = new TeacherVo();
            temp.setId(myId());
            List<TeacherShow> data = teacherFeign.findTeacherListWithTeaching(temp);
            return new ResponseJson(data, 1l);
        }
    }

    /**
     * 导出教师授课信息
     * @param teacher
     * @return
     */
    public Workbook exportTeaching(TeacherVo teacher){
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(mySchoolId());
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setPager(Optional.ofNullable(teacher.getPager()).orElse(new Pager()).setLike("name").setPaging(false));

        List<TeachingInfo> list = teacherFeign.findTeachingInfoByCondition(teacher);
        return ExcelExportUtil.exportExcel(new ExportParams("教师授课信息列表","教师授课信息"),
                TeachingInfo.class, list);
    }

    public void batchUpdateTeacher(List<Teacher> teacherList) {
        for(Teacher teacher:teacherList){
            teacherFeign.updateTeacher(teacher);
        }
    }
}
