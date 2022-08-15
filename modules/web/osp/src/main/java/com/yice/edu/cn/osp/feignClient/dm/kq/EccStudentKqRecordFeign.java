package com.yice.edu.cn.osp.feignClient.dm.kq;

import com.yice.edu.cn.common.pojo.dm.kq.ExportKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm", contextId = "eccStudentKqRecordFeign", path = "/eccStudentKqRecord")
public interface EccStudentKqRecordFeign {
    @PostMapping("/getExportKqData")
    List<ExportKqRecord> getExportKqData(ProtectedStudent protectedStudent);

    @PostMapping("/findStudentKqByCondition")
    List<ProtectedStudent> findStudentKqByCondition(ProtectedStudent protectedStudent);

}
