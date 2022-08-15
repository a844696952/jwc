package com.yice.edu.cn.dm.service.wb.studentAccount;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import com.yice.edu.cn.dm.dao.wb.studentAccount.IStudentAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentAccountService {
    @Autowired
    private IStudentAccountDao studentAccountDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public StudentAccount findStudentAccountById(String id) {
        return studentAccountDao.findStudentAccountById(id);
    }
    @Transactional
    public StudentAccount saveStudentAccount(StudentAccount studentAccount) {
        studentAccount.setId(sequenceId.nextId());
        int couont = studentAccountDao.saveStudentAccount(studentAccount);
        if(couont <= 0){
            studentAccount.setId(null);
        }
        return studentAccount;
    }
    @Transactional(readOnly = true)
    public List<StudentAccount> findStudentAccountListByCondition(StudentAccount studentAccount) {
        return studentAccountDao.findStudentAccountListByCondition(studentAccount);
    }
    @Transactional(readOnly = true)
    public StudentAccount findOneStudentAccountByCondition(StudentAccount studentAccount) {
        return studentAccountDao.findOneStudentAccountByCondition(studentAccount);
    }
    @Transactional(readOnly = true)
    public long findStudentAccountCountByCondition(StudentAccount studentAccount) {
        return studentAccountDao.findStudentAccountCountByCondition(studentAccount);
    }
    @Transactional
    public int updateStudentAccount(StudentAccount studentAccount) {
        return studentAccountDao.updateStudentAccount(studentAccount);
    }
    @Transactional
    public void deleteStudentAccount(String id) {
        studentAccountDao.deleteStudentAccount(id);
    }
    @Transactional
    public void deleteStudentAccountByCondition(StudentAccount studentAccount) {
        studentAccountDao.deleteStudentAccountByCondition(studentAccount);
    }
    @Transactional
    public void batchSaveStudentAccount(List<StudentAccount> studentAccounts){
        studentAccounts.forEach(studentAccount -> studentAccount.setId(sequenceId.nextId()));
        studentAccountDao.batchSaveStudentAccount(studentAccounts);
    }

    @Transactional
    public int batchResetStudentPwd(List<String> studentIds){
        return studentAccountDao.batchResetStudentPwd(studentIds);
    }

    @Transactional(readOnly = true)
    public List<StudentAccount> findStudentAccountImei(StudentAccount studentAccount) {
        return studentAccountDao.findStudentAccountImei(studentAccount);
    }

    /**
     * 查找最大的学生账号
     * @return
     */
    @Transactional
    public Integer getMaxPadAccount(){
        return studentAccountDao.getMaxPadAccount();
    }

    @Transactional
    public void batchDeleteByIds(List<String> ids){
        studentAccountDao.batchDeleteByIds(ids);
    }

    @Transactional(readOnly = true)
    public StudentAccount findStudentAccountByImeiAndId(StudentAccount studentAccount) {
        return studentAccountDao.findStudentAccountByImeiAndId(studentAccount);
    }

    @Transactional(readOnly = true)
    public List<StudentAccount> findStudentAccountByStudentIds(List<String> studentIds) {
        return studentAccountDao.findStudentAccountByStudentIds(studentIds);
    }

    public void batchUpdateStudentType(List<String> ids, Integer studentType) {
        studentAccountDao.batchUpdateStudentType(ids,studentType);
    }

    @Transactional
    public void deleteStudentAccountByClassIds(List<String> clazzIds) {
        studentAccountDao.deleteStudentAccountByClassIds(clazzIds);
    }

    @Transactional
    public List<WisdomClassStudentAccount> findWisdomClassStudentAccountListByCondition(StudentAccount studentAccount) {
        List<WisdomClassStudentAccount> list = studentAccountDao.findWisdomClassStudentAccountListByCondition(studentAccount);
        list.forEach(c -> {
            c.setClassesName(c.getGradeName()+c.getClassName()+"班");
        });
        return list;
    }

    @Transactional
    public Long findWisdomClassStudentAccountCountByCondition(StudentAccount studentAccount) {
        return studentAccountDao.findWisdomClassStudentAccountCountByCondition(studentAccount);
    }

    @Transactional
    public Integer updateWisdomClassStudentAccount(StudentAccount studentAccount) {
        return studentAccountDao.updateWisdomClassStudentAccount(studentAccount);
    }
}
