package com.yice.edu.cn.osp.controller.xw.dutyArrment;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.osp.service.xw.checkedDetail.CheckedDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/personalDuty")
@Api(value = "/personalDuty",description = "个人值班")
public class PersonalDutyController {
    @Autowired
    private CheckedDetailService checkedDetailService;

    @PostMapping("/findPersonalDutysByCondition")
    @ApiOperation(value = "查找我参与的值班", notes = "返回响应对象,不包含总条数", response=DutyArrment.class)
    public ResponseJson findPersonalDutysByCondition(
            @ApiParam(value = "monthCheckTime:2019-02，要传入page对象")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        checkedDetail.setTeacherId(myId());
        if(checkedDetail.getMonthCheckTime() !=null){
            String[] recordDate = checkedDetail.getMonthCheckTime().split("-");
            String firstDay = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
            String lastDay = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
            checkedDetail.getPager().setRangeArray(new String[] {firstDay,lastDay });
            checkedDetail.getPager().setRangeField("recordDate");//符合范围内的数据
        }
        CheckedDetail cd = new CheckedDetail();
        BeanUtils.copyProperties(checkedDetail,cd);
        cd.getPager().setPaging(false);
        List<CheckedDetail> data = checkedDetailService.findCheckedDetailListByCondition(cd);
        if(!data.isEmpty()){
            data = data.stream().sorted((t1,t2)->{
                if(t1.getRecordDate().equals(t2.getRecordDate())){
                    return  t1.getDutyTimeStart().compareTo(t2.getDutyTimeStart());
                }
                return  t1.getRecordDate().compareTo(t2.getRecordDate());
            }).collect(Collectors.toList());
        }
        long count = 0L;
        if(!data.isEmpty()){
            count = data.size();
        }
        int num = (checkedDetail.getPager().getPageSize())*(checkedDetail.getPager().getPage()-1);//新增分页
        if(!data.isEmpty()){
            data = data.stream().skip(num).limit(checkedDetail.getPager().getPageSize()).collect(Collectors.toList());
        }

        return new ResponseJson(data,count);
    }

    @PostMapping("/look/lookPersonalDutyById/findPersonCheckedDetailsByCondition")
    @ApiOperation(value = "签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findPersonCheckedDetailsByCondition(
            @ApiParam(value = "recordDate,dutyArrmentId,要传page对象")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        checkedDetail.setTeacherId(myId());
        List<CheckedDetail> data=checkedDetailService.findCheckedDetailListByCondition(checkedDetail);
        long count=checkedDetailService.findCheckedDetailCountByCondition(checkedDetail);
        return new ResponseJson(data,count);
    }
}
