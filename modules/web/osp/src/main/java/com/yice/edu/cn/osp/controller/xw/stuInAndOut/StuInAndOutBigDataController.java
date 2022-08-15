package com.yice.edu.cn.osp.controller.xw.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutBigData;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.ClassTimeService;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.StuInAndOutBigDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuInAndOutBigData")
@Api(value = "/stuInAndOutBigData",description = "学生出入校大数据")
public class StuInAndOutBigDataController {
    @Autowired
    private StuInAndOutBigDataService stuInAndOutBigDataService;
   @GetMapping("/ignore/findStuInAndOutBigData")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ClassTime.class)
    public ResponseJson findStuInAndOutBigData(){

       StuInAndOutBigData stuInAndOutBigData =  stuInAndOutBigDataService.findStuInAndOutBigData();
        return new ResponseJson(stuInAndOutBigData);
    }
    @PostMapping("/ignore/findStuInDataNextpage")
    @ApiOperation(value = "查找学生入校下一页数据，需要传当前页面显示的最后一条的id和完整的capturedTime，要查异常下一页传passStatus = '3'", notes = "返回响应对象,不包含总条数", response=ClassTime.class)
    public ResponseJson findStuInDataNextpage(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData KqOriginalData
    ){

        KqOriginalData.setDerectionFlag(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN);
        Pager pager = new Pager();
        pager.setPaging(true);
        pager.setPage(1);
        pager.setPageSize(10);
        /*pager.setRangeField("capturedTime");
        String[] zone = new String[2];
        zone[0] = KqOriginalData.getCapturedTime();
        zone[1] = DateUtil.today()+" 23:59:59";
        pager.setRangeArray(zone);*/
        pager.setIncludes("id","capturedImage","derectionFlag","passStatus","gradeName","classesNumber","capturedTime","name");
        pager.setSortOrder("desc");
        pager.setSortField("capturedTime");
        KqOriginalData.setPager(pager);
        List<KqOriginalData> someTimeAgoKqOriginalDataListByCondition = stuInAndOutBigDataService.findSomeTimeAgoKqOriginalDataListByCondition(KqOriginalData);
        return new ResponseJson(someTimeAgoKqOriginalDataListByCondition);
    }
    @PostMapping("/ignore/findStuOutDataNextpage")
    @ApiOperation(value = "查找学生出校下一页数据，需要传当前页面显示的最后一条的id和完整的capturedTime，要查异常下一页传passStatus = '3'", notes = "返回响应对象,不包含总条数", response=ClassTime.class)
    public ResponseJson findStuOutDataNextpage(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData KqOriginalData
    ){

        KqOriginalData.setDerectionFlag(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT);
        Pager pager = new Pager();
        pager.setPaging(true);
        pager.setPage(1);
        pager.setPageSize(10);
        /*pager.setRangeField("capturedTime");
        String[] zone = new String[2];
        zone[0] = KqOriginalData.getCapturedTime();
        zone[1] = DateUtil.today()+" 23:59:59";
        pager.setRangeArray(zone);*/
        pager.setIncludes("id","capturedImage","derectionFlag","passStatus","gradeName","classesNumber","capturedTime","name");
        pager.setSortOrder("desc");
        pager.setSortField("capturedTime");
        KqOriginalData.setPager(pager);
        List<KqOriginalData> someTimeAgoKqOriginalDataListByCondition = stuInAndOutBigDataService.findSomeTimeAgoKqOriginalDataListByCondition(KqOriginalData);
        return new ResponseJson(someTimeAgoKqOriginalDataListByCondition);
    }
}
