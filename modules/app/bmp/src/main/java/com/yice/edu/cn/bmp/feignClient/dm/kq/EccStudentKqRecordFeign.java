package com.yice.edu.cn.bmp.feignClient.dm.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "eccStudentKqRecordFeign",path = "/eccStudentKqRecord")
public interface EccStudentKqRecordFeign {

    @PostMapping("/findOneEccStudentKqRecordByCondition")
    EccStudentKqRecord findOneEccStudentKqRecordByCondition(EccStudentKqRecord kqOriginalData);

    @PostMapping("/dk")
    EccStudentKqRecord dk(EccStudentKqRecord record);

    @PostMapping("/findEccStudentKqRecordListByCondition")
    List<EccStudentKqRecord> findEccStudentKqRecordListByCondition(EccStudentKqRecord record);

    @PostMapping("/findCurrentKqListByCondition")
    List<ProtectedStudent> findCurrentKqListByCondition(ProtectedStudent protectedStudent);

    @PostMapping("/findKqListByStudentId")
    List<ProtectedStudent> findKqListByStudentId(ProtectedStudent protectedStudent);

    @PostMapping("/findStudentKqByCondition")
    List<ProtectedStudent> findStudentKqByCondition(ProtectedStudent protectedStudent);

    @PostMapping("/dkRecord")
    String dkRecord(Student student);
}
