package com.yice.edu.cn.osp.service.xw.checkedDetail;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.osp.feignClient.xw.checkedDetail.CheckedDetailFeign;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckedDetailService {
    @Autowired
    private CheckedDetailFeign checkedDetailFeign;

    public CheckedDetail findCheckedDetailById(String id) {
        return checkedDetailFeign.findCheckedDetailById(id);
    }

    public CheckedDetail saveCheckedDetail(CheckedDetail checkedDetail) {
        return checkedDetailFeign.saveCheckedDetail(checkedDetail);
    }

    public List<CheckedDetail> findCheckedDetailListByCondition(CheckedDetail checkedDetail) {
        return checkedDetailFeign.findCheckedDetailListByCondition(checkedDetail);
    }

    public CheckedDetail findOneCheckedDetailByCondition(CheckedDetail checkedDetail) {
        return checkedDetailFeign.findOneCheckedDetailByCondition(checkedDetail);
    }

    public long findCheckedDetailCountByCondition(CheckedDetail checkedDetail) {
        return checkedDetailFeign.findCheckedDetailCountByCondition(checkedDetail);
    }

    public void updateCheckedDetail(CheckedDetail checkedDetail) {
        checkedDetailFeign.updateCheckedDetail(checkedDetail);
    }

    public void deleteCheckedDetail(String id) {
        checkedDetailFeign.deleteCheckedDetail(id);
    }

    public void deleteCheckedDetailByCondition(CheckedDetail checkedDetail) {
        checkedDetailFeign.deleteCheckedDetailByCondition(checkedDetail);
    }

    public Workbook exportCheckedDetail(CheckedDetail checkedDetail) {
        List<CheckedDetail> checkedDetailList = checkedDetailFeign.findCheckedDetailListByCondition(checkedDetail);
        if(!checkedDetailList.isEmpty()){
            checkedDetailList = checkedDetailList.stream().sorted((t1,t2)->{
                if(t1.getRecordDate().equals(t2.getRecordDate())){
                    return t1.getDutyTimeStart().compareTo(t2.getDutyTimeStart());
                }
                return t1.getRecordDate().compareTo(t2.getRecordDate());
            }).collect(Collectors.toList());
            return ExcelExportUtil.exportExcel(new ExportParams("值班明细","值班"),
                    CheckedDetail.class, checkedDetailList);
        }
        return ExcelExportUtil.exportExcel(new ExportParams("值班明细","值班"),
                CheckedDetail.class, null);
    }

    public List<CheckedDetail> findCheckedDetailListByCondition4like(CheckedDetail checkedDetail) {
        return checkedDetailFeign.findCheckedDetailListByCondition4like(checkedDetail);
    }

    public CheckedDetail updateCheckedDetail4Like(CheckedDetail checkedDetail) {
        return checkedDetailFeign.updateCheckedDetail4Like(checkedDetail);
    }

    public CheckedDetail deleteCheckedDetailByCondition4Like(CheckedDetail checkedDetail) {
        return checkedDetailFeign.deleteCheckedDetailByCondition4Like(checkedDetail);
    }

    public CheckedDetail updateTochangeCheckedDetail4Like(CheckedDetail checkedDetail) {
        return checkedDetailFeign.updateTochangeCheckedDetail4Like(checkedDetail);
    }
}
