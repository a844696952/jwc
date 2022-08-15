package com.yice.edu.cn.osp.service.wb.studentAccount;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.wb.studentAccount.StudentAccountFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StudentAccountService {
    @Autowired
    private StudentAccountFeign studentAccountFeign;

    @Autowired
    private StudentFeign studentFeign;


    @CreateCache(name = Constant.Redis.PAD_STUDENT_ACCOUNT)
    private Cache<String, Integer> materialItemCache;

    public StudentAccount findStudentAccountById(String id) {
        return studentAccountFeign.findStudentAccountById(id);
    }

    public StudentAccount saveStudentAccount(StudentAccount studentAccount) {
        return studentAccountFeign.saveStudentAccount(studentAccount);

    }

    public List<StudentAccount> findStudentAccountListByCondition(StudentAccount studentAccount) {
        return studentAccountFeign.findStudentAccountListByCondition(studentAccount);
    }

    public StudentAccount findOneStudentAccountByCondition(StudentAccount studentAccount) {
        return studentAccountFeign.findOneStudentAccountByCondition(studentAccount);
    }

    public long findStudentAccountCountByCondition(StudentAccount studentAccount) {
        return studentAccountFeign.findStudentAccountCountByCondition(studentAccount);
    }

    /**
     * 根据ID判断是修改还是新增
     * @param studentAccount
     */
    public ResponseJson saveOrUpdateStudentAccount(StudentAccount studentAccount) {
        ResponseJson responseJson = new ResponseJson();
        ResponseJson.Result result = new ResponseJson.Result();
        if(studentAccount.getImei() != null){
            studentAccount.setImei(studentAccount.getImei().replace(" ",""));
        }
        StudentAccount one = null;
        if(studentAccount.getId() != null){
            one = studentAccountFeign.findStudentAccountById(studentAccount.getId());
            if(one.getImei() == null){
                one.setImei("");
            }
        }
        //先校验当IMEI不为空时，imei是否重复
        if(null != studentAccount.getImei() && one != null ? (one.getImei().equals(studentAccount.getImei()) ? false : true) : true){
            studentAccount.setSchoolId(mySchoolId());
            StudentAccount account = findStudentAccountByImeiAndId(studentAccount);
            if(account != null){
                result.setSuccess(false);
                result.setMsg("已存在IMEI！");
                responseJson.setResult(result);
                return responseJson;
            }
        }
        //判断前端传入的id是否为空，如果为空则插入，否则修改
        if(null == studentAccount.getId()){
            saveStudentAccount(studentAccount);
        }else{
            int updateCount = studentAccountFeign.updateStudentAccount(studentAccount);
            if(updateCount <= 0){//校验是否修改成功
                result.setSuccess(false);
                result.setMsg("修改失败！");
                responseJson.setResult(result);
            }
        }
        return responseJson;
    }

    public void deleteStudentAccount(String id) {
        studentAccountFeign.deleteStudentAccount(id);
    }

    public void deleteStudentAccountByCondition(StudentAccount studentAccount) {
        studentAccountFeign.deleteStudentAccountByCondition(studentAccount);
    }

    public Workbook exportStudentAccount(StudentAccount studentAccount){
        studentAccount.setPager(null);
        List<StudentAccount> studentAccountList = studentAccountFeign.findStudentAccountListByCondition(studentAccount);
        studentAccountList.forEach(s -> {
            s.setClassName(s.getGradeName()+s.getClassName()+"班");
        });
        return ExcelExportUtil.exportExcel(new ExportParams(),
                StudentAccount.class, studentAccountList);
    }

    /**
     * 批量保存学生账号
     * 1.根据学校ID,年级id,班级id查询学生列表
     * 2.根据学生id批量生成学生账号
     * @param studentAccount
     */
    public ResponseJson batchSaveStudentAccount(StudentAccount studentAccount) {
        //根据根据学校ID,年级id,班级id查询学生列表
        List<Student> listStudent = findStudentListByCondition(studentAccount);
        if(null == listStudent || listStudent.size() <= 0){
            return new ResponseJson(false, "根据学校id，年级id，班级id未查询到学生数据");
        }
        //添加判断，如果班级下所有学生已经拥有账号，则不创建
        List<String> studentIds = new ArrayList<>();
        listStudent.forEach(student->{
            studentIds.add(student.getId());
        });
        List<StudentAccount> beforList = studentAccountFeign.findStudentAccountByStudentIds(studentIds);
        if(beforList != null && beforList.size() == listStudent.size()){
            return new ResponseJson(false, "该班级已创建学生账号！");
        }

        //从缓存中获取PAD_STUDENT_ACCOUNT
        Integer padAccount = getIncr(Constant.Redis.PAD_STUDENT_ACCOUNT_KEY);
        if(padAccount == null){
            //如果缓存中的padAccount为空则从数据库中查询最大的学生账号maxPadAccount
            Integer maxPadAccount = getMaxStudentAccount();
            //如果数据库中maxPadAccount为空，则给学生账号默认值,否则为库中学生账号最大值
            if(maxPadAccount == null){
                setIncr(Constant.Redis.PAD_STUDENT_ACCOUNT_KEY,Constant.PadStudentAccount.PAD_STUDENT_ACCOUNT_INTI_VALUE);
                padAccount = Constant.PadStudentAccount.PAD_STUDENT_ACCOUNT_INTI_VALUE;
            }else{
                padAccount = maxPadAccount+1;
            }
        }

        List<StudentAccount> listStuAccount = new ArrayList<>();
        for (Student stu:listStudent) {
            StudentAccount stuAccount = new StudentAccount();
            stuAccount.setSchoolId(studentAccount.getSchoolId());
            stuAccount.setClassId(studentAccount.getClassId());
            stuAccount.setPadAccount(String.valueOf(padAccount));
            stuAccount.setPadPassword(String.valueOf(Constant.PadStudentAccount.PAD_STUDENT_PASSWORD));
            stuAccount.setStudentId(stu.getId());
            listStuAccount.add(stuAccount);
            padAccount++;
        }
        //更新cache中pad_student_account的值
        setIncr(Constant.Redis.PAD_STUDENT_ACCOUNT_KEY,padAccount);
        //批量新增学生账号
        studentAccountFeign.batchSaveStudentAccount(listStuAccount);
        //再次根据studentids查询是否该班级下所有学生都已成功创建账号
        List<StudentAccount> afterList = studentAccountFeign.findStudentAccountByStudentIds(studentIds);
        if(afterList == null){
            return new ResponseJson(false, "批量生成学生账号失败！");
        }else if(afterList.size() != listStuAccount.size()){
             List<String> afterStudentIdList = afterList.stream().map(StudentAccount::getStudentId).collect(Collectors.toList());
             studentIds.removeAll(afterStudentIdList);
             studentIds.forEach(studentId->{
                 StudentAccount account = new StudentAccount();
                 account.setStudentId(studentId);
                 account.setSchoolId(studentAccount.getSchoolId());
                 saveStudentAccount(account);
             });
        }
        return new ResponseJson();
    }


    /**
     * 根据id重置密码
     * @param studentId
     */
    public void resetStudentPwd(String studentId) {
        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setStudentId(studentId);
        studentAccount.setPadPassword(String.valueOf(Constant.PadStudentAccount.PAD_STUDENT_PASSWORD));
        studentAccountFeign.updateStudentAccount(studentAccount);
    }

    /**
     * 批量重置学生密码
     * @param studentAccount
     */
    public ResponseJson batchResetStudentPwd(StudentAccount studentAccount) {
        //根据根据学校ID,年级id,班级id查询学生列表
        List<Student> listStudent = findStudentListByCondition(studentAccount);
        if(null == listStudent || listStudent.size() <= 0){
            return new ResponseJson(false, "根据学校id，年级id，班级id未查询到学生数据");
        }
        List<String> listStudentIds = new ArrayList<>();
        for (Student student:listStudent) {
            listStudentIds.add(student.getId());
        }
        //批量重置学生密码
        int resetCount = studentAccountFeign.batchResetStudentPwd(listStudentIds);
        if(resetCount <= 0){
            return new ResponseJson(false, "该班级尚未创建学生账号！");
        }
        return new ResponseJson();
    }

    /**
     * @Description: 获取自增长值
     * @param key key
     * @return
     */
    public  Integer getIncr(String key) {
        Integer value = materialItemCache.get(key);
//        if(value == null){
//            setIncr(Constant.Redis.PAD_STUDENT_ACCOUNT_KEY,Constant.Redis.PAD_STUDENT_ACCOUNT_INTI_VALUE);
//            value = Constant.Redis.PAD_STUDENT_ACCOUNT_INTI_VALUE;
//        }
        return value;
    }

    /**
     * @Description: 初始化自增长值
     * @param key key
     * @param value 当前值
     */
    public void setIncr(String key, int value) {
        materialItemCache.put(key,value);
    }

    /**
     * 根据根据学校ID,年级id,班级id查询学生列表
     * @param studentAccount
     * @return
     */
    private List<Student> findStudentListByCondition(StudentAccount studentAccount){
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        student.setGradeId(studentAccount.getGradeId());
        student.setClassesId(studentAccount.getClassId());
        return studentFeign.findStudentListByCondition(student);
    }

    /**
     * 批量删除
     * @param ids
     */
    public void batchDeleteByIds(List<String> ids) {
        //批量重置学生密码
        studentAccountFeign.batchDeleteByIds(ids);
    }

    /**
     * 通过imei查询学生账号
     * @param studentAccount
     * @return
     */
    private StudentAccount findStudentAccountByImeiAndId(StudentAccount studentAccount){
        return studentAccountFeign.findStudentAccountByImeiAndId(studentAccount);
    }

    private int getMaxStudentAccount(){
        return studentAccountFeign.getMaxPadAccount();
    }

    public void batchUpdateStudentType(StudentAccount studentAccount) {
        studentAccountFeign.batchUpdateStudentType(studentAccount);

    }

    /**
     * 智慧课堂笔盒绑定绑定批量保存学生账号
     * @param studentAccount
     * @return
     */
    public ResponseJson batchSaveWisdomClassStudentAccount(StudentAccount studentAccount) {

        //根据根据学校ID,年级id,班级id查询学生列表
        List<Student> listStudent = findStudentListByCondition(studentAccount);
        if(null == listStudent || listStudent.size() <= 0){
            return new ResponseJson(false, "根据学校id，年级id，班级id未查询到学生数据");
        }
        //添加判断，如果班级下所有学生已经拥有账号，则不创建
        List<String> studentIds = new ArrayList<>();
        listStudent.forEach(student->{
            studentIds.add(student.getId());
        });
        List<StudentAccount> beforList = studentAccountFeign.findStudentAccountByStudentIds(studentIds);
        if(beforList != null && beforList.size() == listStudent.size()){
            Integer i = studentAccountFeign.updateWisdomClassStudentAccount(studentAccount);
            if(i <= 0){
                return new ResponseJson(false,"批量操作失败!");
            }
            return new ResponseJson(true,"批量操作成功");
        }
        List<StudentAccount> listStuAccount = new ArrayList<>();
        StudentAccount stuAccount = null;
        for (Student stu:listStudent) {
            stuAccount = new StudentAccount();
            stuAccount.setSchoolId(studentAccount.getSchoolId());
            stuAccount.setClassId(studentAccount.getClassId());

            stuAccount.setStudentId(stu.getId());
            stuAccount.setPenFactory(studentAccount.getPenFactory());
            listStuAccount.add(stuAccount);

        }
        studentAccountFeign.batchSaveStudentAccount(listStuAccount);
        return new ResponseJson(true,"批量操作成功");
    }

    public List<WisdomClassStudentAccount> findWisdomClassStudentAccountListByCondition(StudentAccount studentAccount) {
        return studentAccountFeign.findWisdomClassStudentAccountListByCondition(studentAccount);
    }

    public Long findWisdomClassStudentAccountCountByCondition(StudentAccount studentAccount){
        return studentAccountFeign.findWisdomClassStudentAccountCountByCondition(studentAccount);
    }

    public Workbook exportWisdomClassStudentAccount(StudentAccount studentAccount) {
        studentAccount.setPager(null);
        List<WisdomClassStudentAccount> list = studentAccountFeign.findWisdomClassStudentAccountListByCondition(studentAccount);
        return ExcelExportUtil.exportExcel(new ExportParams(), WisdomClassStudentAccount.class, list);
    }
}
