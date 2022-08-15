package com.yice.edu.cn.ecc.feignClient.dy;

import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(value="dy",contextId = "mesInspectRecordFeign",path = "/mesInspectRecord")
public interface MesInspectRecordFeign {
    @GetMapping("/findMesInspectRecordListByClassId/{classId}")
    Map<String, List<MesInspectRecord>> findMesInspectRecordListByClassId(@PathVariable("classId") String classId);
}
