package com.yice.edu.cn.ecc.service.dy;

import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.ecc.feignClient.dy.MesInspectRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MesInspectRecordService {
    @Autowired
    private MesInspectRecordFeign mesInspectRecordFeign;

    public Map<String, List<MesInspectRecord>> findMesInspectRecordListByClassId(String classId) {
        return mesInspectRecordFeign.findMesInspectRecordListByClassId(classId);
    }
}
