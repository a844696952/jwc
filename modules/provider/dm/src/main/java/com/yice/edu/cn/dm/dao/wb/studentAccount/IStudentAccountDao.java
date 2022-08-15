package com.yice.edu.cn.dm.dao.wb.studentAccount;

import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IStudentAccountDao {
    List<StudentAccount> findStudentAccountListByCondition(StudentAccount studentAccount);

    long findStudentAccountCountByCondition(StudentAccount studentAccount);

    StudentAccount findOneStudentAccountByCondition(StudentAccount studentAccount);

    StudentAccount findStudentAccountById(@Param("id") String id);

    int saveStudentAccount(StudentAccount studentAccount);

    int updateStudentAccount(StudentAccount studentAccount);

    void deleteStudentAccount(@Param("id") String id);

    void deleteStudentAccountByCondition(StudentAccount studentAccount);

    void batchSaveStudentAccount(List<StudentAccount> studentAccounts);

    int batchResetStudentPwd (@Param("studentIds") List<String> studentIds);
    //给智能笔提供学生imei
    List<StudentAccount> findStudentAccountImei(StudentAccount studentAccount);
    //查找最大的学生账号
    Integer getMaxPadAccount();

    void batchDeleteByIds(@Param("ids") List<String> ids);

    StudentAccount findStudentAccountByImeiAndId(StudentAccount studentAccount);

    List<StudentAccount> findStudentAccountByStudentIds(@Param("studentIds") List<String> studentIds);

    void batchUpdateStudentType(@Param("ids") List<String> ids,@Param("studentType") Integer studentType);

    void deleteStudentAccountByClassIds(@Param("clazzIds") List<String> ids);

    void deleteByClassIds(@Param("clazzIds") List<String> ids);

    List<WisdomClassStudentAccount> findWisdomClassStudentAccountListByCondition(StudentAccount studentAccount);

    Long findWisdomClassStudentAccountCountByCondition(StudentAccount studentAccount);

    Integer updateWisdomClassStudentAccount(StudentAccount studentAccount);

}
