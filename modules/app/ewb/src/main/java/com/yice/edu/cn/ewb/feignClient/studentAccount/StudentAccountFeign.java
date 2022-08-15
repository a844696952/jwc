package com.yice.edu.cn.ewb.feignClient.studentAccount;

import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/studentAccount")
public interface StudentAccountFeign {
    @GetMapping("/findStudentAccountById/{id}")
    StudentAccount findStudentAccountById(@PathVariable("id") String id);
    @PostMapping("/saveStudentAccount")
    StudentAccount saveStudentAccount(StudentAccount studentAccount);
    @PostMapping("/findStudentAccountListByCondition")
    List<StudentAccount> findStudentAccountListByCondition(StudentAccount studentAccount);
    @PostMapping("/findOneStudentAccountByCondition")
    StudentAccount findOneStudentAccountByCondition(StudentAccount studentAccount);
    @PostMapping("/findStudentAccountCountByCondition")
    long findStudentAccountCountByCondition(StudentAccount studentAccount);
    @PostMapping("/updateStudentAccount")
    void updateStudentAccount(StudentAccount studentAccount);
    @GetMapping("/deleteStudentAccount/{id}")
    void deleteStudentAccount(@PathVariable("id") String id);
    @PostMapping("/deleteStudentAccountByCondition")
    void deleteStudentAccountByCondition(StudentAccount studentAccount);
    @PostMapping("/batchSaveStudentAccount")
    void batchSaveStudentAccount(List<StudentAccount> studentAccounts);
    //给智能笔提供学生imei
    @PostMapping("/findStudentAccountImei")
    List<StudentAccount> findStudentAccountImei(StudentAccount studentAccount);
}
