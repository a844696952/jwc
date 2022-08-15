package com.yice.edu.cn.osp.feignClient.wb.studentAccount;

import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "studentAccountFeign",path = "/studentAccount")
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
    int updateStudentAccount(StudentAccount studentAccount);
    @GetMapping("/deleteStudentAccount/{id}")
    void deleteStudentAccount(@PathVariable("id") String id);
    @PostMapping("/deleteStudentAccountByCondition")
    void deleteStudentAccountByCondition(StudentAccount studentAccount);
    @PostMapping("/batchSaveStudentAccount")
    void batchSaveStudentAccount(List<StudentAccount> studentAccounts);
    @PostMapping("/batchResetStudentPwd")
    int batchResetStudentPwd(List<String> listStudentIds);
    @GetMapping("/getMaxPadAccount")
    Integer getMaxPadAccount();
    @PostMapping("/batchDeleteByIds")
    void batchDeleteByIds(List<String> ids);
    @PostMapping("/findStudentAccountByImeiAndId")
    StudentAccount findStudentAccountByImeiAndId(StudentAccount studentAccount);

    @PostMapping("/findStudentAccountByStudentIds")
    List<StudentAccount> findStudentAccountByStudentIds(List<String> studentIds);

    @PostMapping("/batchUpdateStudentType")
    void batchUpdateStudentType(StudentAccount studentAccount);
    @PostMapping("/updateWisdomClassStudentAccount")
    Integer updateWisdomClassStudentAccount(StudentAccount studentAccount);
    @PostMapping("/findWisdomClassStudentAccountListByCondition")
    List<WisdomClassStudentAccount> findWisdomClassStudentAccountListByCondition(StudentAccount studentAccount);
    @PostMapping("/findWisdomClassStudentAccountCountByCondition")
    Long findWisdomClassStudentAccountCountByCondition(StudentAccount studentAccount);
}
