package com.yice.edu.cn.osp.service.dm.kq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.ExportKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ExportStatisticsRecord;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.osp.feignClient.dm.kq.EccStudentKqRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EccStudentKqRecordService {
    @Autowired
    private EccStudentKqRecordFeign eccStudentKqRecordFeign;

    public List<ExportKqRecord> getExportKqData(ProtectedStudent protectedStudent) {
        return eccStudentKqRecordFeign.getExportKqData(protectedStudent);
    }

    public List<Map.Entry<String, List<ProtectedStudent>>> findStudentKqByCondition(ProtectedStudent protectedStudent) {
        List<ProtectedStudent> studentKqByCondition = eccStudentKqRecordFeign.findStudentKqByCondition(protectedStudent);
        if(CollUtil.isNotEmpty(studentKqByCondition)){
            //排除法定假日
            List<Integer> status = Arrays.asList(5, 6);
            Map<String, List<ProtectedStudent>> collect = studentKqByCondition.stream().filter(item -> !status.contains(item.getKqState())).collect(Collectors.groupingBy(ProtectedStudent::getRecordDate));
            List<Map.Entry<String, List<ProtectedStudent>>> collect1 = collect.entrySet().stream().sorted((o1, o2) -> {
                if (DateUtil.parse(o1.getKey(), Constant.DATE_FORMATTER_DAY).isBefore(DateUtil.parse(o2.getKey(), Constant.DATE_FORMATTER_DAY))) {
                    return -1;
                } else if (DateUtil.parse(o1.getKey(), Constant.DATE_FORMATTER_DAY).isAfter(DateUtil.parse(o2.getKey(), Constant.DATE_FORMATTER_DAY))) {
                    return 1;
                } else {
                    return 0;
                }
            }).collect(Collectors.toList());
            return collect1;
        }
        return new ArrayList();
    }
    
    
    

    /**
     * 考勤统计列表（页面显示）
     *
     * @param protectedStudent 查询条件
     * @return 数据
     */
    public List<ExportStatisticsRecord> getKqListByCondition(ProtectedStudent protectedStudent) {
        List<Map.Entry<String, List<ProtectedStudent>>> dataMap = findStudentKqByCondition(protectedStudent);
        if (CollUtil.isNotEmpty(dataMap)) {
            List<ExportStatisticsRecord> list = new ArrayList<>();
            dataMap.forEach(x->{
                List<ProtectedStudent> data   =x.getValue();
                ExportStatisticsRecord model = new ExportStatisticsRecord();
                model.setStudents(data);
                model.setGradeString(getGradeString(protectedStudent, data));
                model.setRecordDate(x.getKey());
                model.setYdNum(data.size());
                long wdNum = data.stream().filter(s -> s.getKqState() != null && s.getKqState() == 1).count();
                model.setWdNum((int) wdNum);
                long qjNum = data.stream().filter(s -> s.getKqState() != null && s.getKqState() == 4).count();
                model.setQjNum((int) qjNum);
                model.setSdNum(data.size() - (int) wdNum - (int) qjNum);
                long cdNum = data.stream().filter(s -> s.getKqState() != null && s.getKqState() == 2).count();
                model.setCdNum((int) cdNum);
                list.add(model);
            });
            return list;
        }
        return new ArrayList<>();
    }

    private String getGradeString(ProtectedStudent protectedStudent, List<ProtectedStudent> data) {
        //获取班级名称
        if (StrUtil.isNotBlank(protectedStudent.getClassId())) {
            return data.get(0).getGradeName() + "（" + data.get(0).getClassesNumber() + "）班";
        } else if (StrUtil.isBlank(protectedStudent.getClassId()) && StrUtil.isNotBlank(protectedStudent.getGradeId())) {
            return data.get(0).getGradeName();
        } else {
            return "全校";
        }
    }


}
