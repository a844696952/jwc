package com.yice.edu.cn.ecc.controller.dy;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.ecc.service.dy.MesInspectRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesInspectRecord")
@Api(value = "/mesInspectRecord",description = "检查记录表模块")
public class MesInspectRecordController {
    @Autowired
    private MesInspectRecordService mesInspectRecordService;

    @GetMapping("/findMesInspectRecordListByClassId/{classId}")
    @ApiOperation(value = "根据班级ID查询最新六条检查记录", notes = "返回检查记录表对象", response=MesInspectRecord.class)
    public ResponseJson findMesInspectRecordListByClassId(
            @ApiParam(value = "检查记录表对象", required = true)
            @PathVariable("classId") String classId){
        Map<String, List<MesInspectRecord>> data=mesInspectRecordService.findMesInspectRecordListByClassId(classId);
        return new ResponseJson(data);
    }
}
