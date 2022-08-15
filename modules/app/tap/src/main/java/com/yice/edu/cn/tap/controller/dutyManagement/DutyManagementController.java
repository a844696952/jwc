package com.yice.edu.cn.tap.controller.dutyManagement;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
//import com.yice.edu.cn.common.pojo.jw.semester.SemesterTime;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.dutyManagement.DutyManagementService;
import com.yice.edu.cn.tap.service.dutyManagement.RoutineDutyFeedbackService;
import com.yice.edu.cn.tap.service.school.SchoolService;
import com.yice.edu.cn.tap.service.schoolYear.SchoolYearService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dutyManagement")
@Api(value = "/dutyManagement",description = "值班管理")
public class DutyManagementController {

    @Autowired
    private DutyManagementService dutyManagementService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private RoutineDutyFeedbackService routineDutyFeedbackService;
    @Autowired
    private TeacherService teacherService;

    /*@PostMapping("/saveCheckedDetail")
    @ApiOperation(value = "保存签到明细对象", notes = "返回保存好的签到明细对象", response=CheckedDetail.class)
    public ResponseJson saveCheckedDetail(
            @ApiParam(value = "签到明细对象", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        CheckedDetail s=dutyManagementService.saveCheckedDetail(checkedDetail);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCheckedDetailById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CheckedDetail checkedDetail=dutyManagementService.findCheckedDetailById(id);
        return new ResponseJson(checkedDetail);
    }

    @PostMapping("/update/updateCheckedDetail")
    @ApiOperation(value = "修改签到明细对象", notes = "返回响应对象")
    public ResponseJson updateCheckedDetail(
            @ApiParam(value = "被修改的签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        String date3 = DateUtil.now(); //添加签到时间
        String[] date = date3.split(" ");
        checkedDetail.setDateCheckIn( date[0]);
        checkedDetail.setTimeCheckIn(date[1]);
        checkedDetail.setDateTimeChecked(date3);
        dutyManagementService.updateCheckedDetail(checkedDetail);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCheckedDetailById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson lookCheckedDetailById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CheckedDetail checkedDetail=dutyManagementService.findCheckedDetailById(id);
        return new ResponseJson(checkedDetail);
    }

    @PostMapping("/findCheckedDetailsByCondition")
    @ApiOperation(value = "根据条件查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        List<CheckedDetail> data=dutyManagementService.findCheckedDetailListByCondition(checkedDetail);
        long count=dutyManagementService.findCheckedDetailCountByCondition(checkedDetail);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCheckedDetailByCondition")
    @ApiOperation(value = "根据条件查找单个签到明细,结果必须为单条数据", notes = "没有时返回空", response=CheckedDetail.class)
    public ResponseJson findOneCheckedDetailByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CheckedDetail checkedDetail){
        CheckedDetail one=dutyManagementService.findOneCheckedDetailByCondition(checkedDetail);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCheckedDetail/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCheckedDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dutyManagementService.deleteCheckedDetail(id);
        return new ResponseJson();
    }


    @PostMapping("/findCheckedDetailListByCondition")
    @ApiOperation(value = "根据条件查找签到明细列表", notes = "返回响应对象,不包含总条数", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        List<CheckedDetail> data=dutyManagementService.findCheckedDetailListByCondition(checkedDetail);
        return new ResponseJson(data);
    }
*/

    @PostMapping("/findCheckedDetailListForAppTapByCondition")
    @ApiOperation(value = "tap查询根据条件查找值班情况列表，小红点功能，教师权限", notes = "返回响应对象")
    public ResponseJson findCheckedDetailListForAppTapByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        checkedDetail.setTeacherId(LoginInterceptor.myId());
        checkedDetail.setPager(getPagerForCheckedDetail());
        checkedDetail.setFindStartTime(getFirstDayOfMonth1( Convert.toInt( checkedDetail.getYear() ),Convert.toInt( checkedDetail.getMonth() ) ) );
        checkedDetail.setFindEndTime(getLastDayOfMonth1( Convert.toInt( checkedDetail.getYear() ),Convert.toInt( checkedDetail.getMonth() ) ));
        List<CheckedDetail> data = dutyManagementService.findCheckedDetailListForAppTapByCondition(checkedDetail);
        Map<String,List<CheckedDetail>> map = data.stream().collect(Collectors.groupingBy(CheckedDetail::getRecordDate));//按记录日期分组
        List<String> list = new ArrayList<>();
        map.forEach( (k,v)->{
            list.add(k);
        });
        return new ResponseJson(list);
    }

    @PostMapping("/findCheckedDetailsForAppTapByConditionNew")
    @ApiOperation(value = "根据条件查找值班情况，传入当日时间返回当天的值班情况列表，教师权限", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsForAppTapByConditionNew(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        checkedDetail.setTeacherId(LoginInterceptor.myId());
        checkedDetail.setPager(getPagerForCheckedDetail());//不分页
        checkedDetail.getPager().setSortField("dutyTimeStart"); //排序字段
        checkedDetail.getPager().setSortOrder("asc");
        List<CheckedDetail> data=dutyManagementService.findCheckedDetailListByCondition(checkedDetail);
       // long count=dutyManagementService.findCheckedDetailCountByCondition(checkedDetail);
        return new ResponseJson(data);
    }

    @PostMapping("/findCheckedDetailsForAppTapByConditionNewPrincipal")
    @ApiOperation(value = "根据条件查找值班情况，传入当日时间返回当天的值班情况列表，校长权限", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsForAppTapByConditionNewPrincipal(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());

        checkedDetail.setPager(getPagerForCheckedDetail());//不分页   sql  group  by 分组
        checkedDetail.getPager().setSortField("dutyTimeStart"); //排序字段
        checkedDetail.getPager().setSortOrder("asc");
        List<CheckedDetail> data=dutyManagementService.findCheckedDetailsForAppTapByConditionNewPrincipal(checkedDetail);
        return new ResponseJson(data);
    }

    @PostMapping("/findCheckedDetailsForAppTapByConditionNewTeacherName")
    @ApiOperation(value = "根据条件查找签到明细,返回签到情况名单,教师权限/校长权限", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsForAppTapByConditionNewTeacherName(
            @ApiParam(value = "属性不为空则作为条件查询,传入dutyArrmentId，recordDate")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        checkedDetail.setPager(getPagerForCheckedDetail());
        checkedDetail.getPager().setSortField("dateTimeChecked");
        checkedDetail.getPager().setSortOrder("asc");
        List<CheckedDetail> data=dutyManagementService.findCheckedDetailListByCondition(checkedDetail);
        Map<String,List<CheckedDetail>> listMap = groupByingForSignIn(data);
        return new ResponseJson(listMap);
    }

    @PostMapping("/findRoutineDutyFeedbackListForTapByConditionNewTeachers")
    @ApiOperation(value = "根据条件查找常规值班反馈列表,返回值班反馈列表，教师权限/校长权限", notes = "返回响应对象,不包含总条数", response=RoutineDutyFeedback.class)
    public ResponseJson findRoutineDutyFeedbackListForTapByConditionNewTeachers(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        //routineDutyFeedback.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager =  new Pager();
        if(routineDutyFeedback.getPager()!=null){
            pager.setPage(routineDutyFeedback.getPager().getPage());
            pager.setPageSize(routineDutyFeedback.getPager().getPageSize());
        }else {
            pager.setPaging(false);  //不分页
        }
        routineDutyFeedback.setPager(pager);
       // routineDutyFeedback.setPager(getPagerForCheckedDetail());
        routineDutyFeedback.getPager().setSortField("feedbackTime");
        routineDutyFeedback.getPager().setSortOrder("asc");

        List<RoutineDutyFeedback> dutyFeedbackList = routineDutyFeedbackService.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
        return new ResponseJson(dutyFeedbackList);
    }

    @PostMapping("/findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal")
    @ApiOperation(value = "根据条件查找值班统计情况,返回情况统计名单，校长权限", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson  findDutyStatisticsForAppTapByConditionNewPrincipal(
            @ApiParam(value = "属性不为空则作为条件查询,传入timeType， 1 本月，2 上月，3 半年内")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(LoginInterceptor.mySchoolId());
        Map<String,String> timeMap =   getTimeForTap(checkedDetail.getTimeType());
        if(MapUtil.isNotEmpty(timeMap)){
            checkedDetail.setFindStartTime(timeMap.get("searchStartTime"));
            checkedDetail.setFindEndTime(timeMap.get("searchEndTime"));
        }else{
            return new ResponseJson(false,"暂未设置学期！");
        }
        List<CheckedDetail> detailList = dutyManagementService.findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(checkedDetail);
        Map<String,List<CheckedDetail>> stringListMap = detailList.stream().collect(Collectors.groupingBy(CheckedDetail::getSignIn));
        List<CheckedDetail> data = new ArrayList<>();
        List<CheckedDetail> data1  =  new ArrayList<>();
        List<CheckedDetail> data2  =  new ArrayList<>();
        Map<String,List<CheckedDetail>> mapHas = new HashMap<>();
        Map<String,List<CheckedDetail>> mapNot = new HashMap<>();
        Map<String,List<CheckedDetail>> mapLate = new HashMap<>();
        List<CheckedDetail> listHas = new ArrayList<>();
        List<CheckedDetail> listNot = new ArrayList<>();
        List<CheckedDetail> listLate = new ArrayList<>();
        Map<String,List<CheckedDetail>> mapSign = new HashMap<>();

        data1 = stringListMap.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS); //已签到
        if(data1!=null && data1.size()>0){
             mapHas = data1.stream().collect(Collectors.groupingBy(CheckedDetail::getTeacherId)); //教师名字分组
             mapHas =  groupByingForDuty(mapHas);
             listHas =  getMapForeach(mapHas,listHas);
             mapSign.put("SIGN_IN_HAS",listHas) ;
        }else{
            mapSign.put("SIGN_IN_HAS",listHas) ;
        }
        data = stringListMap.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_NOT);  //未签到
        if(data!=null &&  data.size()>0){
            mapNot = data.stream().collect(Collectors.groupingBy(CheckedDetail::getTeacherId)); //教师名字分组
            mapNot =  groupByingForDuty(mapNot);
            listNot =  getMapForeach(mapNot,listNot);
            mapSign.put("SIGN_IN_NOT",listNot) ;
        }else{
            mapSign.put("SIGN_IN_NOT",listNot) ;
        }
        data2 = stringListMap.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE);  //迟到
        if(data2!=null &&  data2.size()>0){
            mapLate = data2.stream().collect(Collectors.groupingBy(CheckedDetail::getTeacherId)); //教师名字分组
            mapLate =  groupByingForDuty(mapLate);
            listLate =  getMapForeach(mapLate,listLate);
            mapSign.put("SIGN_IN_LATE",listLate) ;
        }else{
            mapSign.put("SIGN_IN_LATE",listLate) ;
        }

        return new ResponseJson(mapSign);
    }

    @PostMapping("/update/updateCheckedDetailForTapNewTeacher")
    @ApiOperation(value = "修改签到明细对象,app打卡签到", notes = "返回响应对象")
    public ResponseJson updateCheckedDetailForTapNewTeacher(
            @ApiParam(value = "被修改的签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){

            int compareToResult = signInTimeCompare(checkedDetail.getDutyTimeStart(), checkedDetail.getDutyTimeEnd(), checkedDetail.getPunchTimeLater(), checkedDetail.getRecordDate()); //进行值班时间比较
            // 迟到  正常  晚于无法签到  提前无法签到

            if (compareToResult == 3) {   //已超出值班时间，无法签到
                return new ResponseJson(Constant.DUTY_SIGN_IN_TIME_TYPE.SIGN_IN_TIME_BEHIND);
            }
            if (compareToResult == 4) {  //未到值班规定时间，无法签到
                return new ResponseJson(Constant.DUTY_SIGN_IN_TIME_TYPE.SIGN_IN_TIME_BEFORE);
            }
            String today = DateUtil.now(); //添加签到时间
            String[] date = today.split(" ");
            checkedDetail.setDateCheckIn(date[0]);
            checkedDetail.setTimeCheckIn(date[1]);
            checkedDetail.setDateTimeChecked(today);
            if (compareToResult == 1) {
                checkedDetail.setSignIn(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE); //已签到  迟到
            }
            if (compareToResult == 2) {
                checkedDetail.setSignIn(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS); //已签到
            }
            dutyManagementService.updateCheckedDetailForTapNewTeacher(checkedDetail);
        return new ResponseJson(Constant.DUTY_SIGN_IN_TIME_TYPE.SIGN_IN_TIME_IN);  //签到成功
    }

    @PostMapping("/saveRoutineDutyFeedbackForTapNewTeacher")
    @ApiOperation(value = "保存常规值班反馈表对象,教师权限", notes = "返回保存好的常规值班反馈表对象", response=RoutineDutyFeedback.class)
    public ResponseJson saveRoutineDutyFeedbackForTapNewTeacher(
            @ApiParam(value = "常规值班反馈表对象", required = true)
            @RequestBody RoutineDutyFeedback routineDutyFeedback){
        routineDutyFeedback.setSchoolId(LoginInterceptor.mySchoolId());
        routineDutyFeedback.setTeacherId(LoginInterceptor.myId());
        Teacher teacher = teacherService.findTeacherById(LoginInterceptor.myId());
        routineDutyFeedback.setTeacherImgUrl(teacher.getImgUrl()); //增加反馈人头像
        routineDutyFeedback.setTeacherName(teacher.getName());
        String date = DateUtil.now();
        routineDutyFeedback.setFeedbackTime(date); //反馈时间
        RoutineDutyFeedback s=routineDutyFeedbackService.saveRoutineDutyFeedback(routineDutyFeedback);
        return new ResponseJson(s);
    }


    public static String getFirstDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data = sdf.format(cal.getTime());
        return data;
    }

    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data =  sdf.format(cal.getTime());
        return data;
    }
    //app时间段筛选
    public Map getTimeForTap(String timeType){
        Map<String,String> timeMap=new HashMap();
        String searchStartTime=null;
        String searchEndTime=null;
        switch (timeType) {
            case "1":     //获取当月时间
                Date date = DateUtil.date();  //获取当天时间
                int year =  DateUtil.year(date) ;
                int month =  DateUtil.month(date)+1; //月份加1
                Date yesterday = DateUtil.yesterday();

                searchStartTime= getFirstDayOfMonth1(year,month);
                searchEndTime =  DateUtil.format(yesterday, "yyyy-MM-dd");
                break;
            case "2":    //获取上个月时间
                Date date1 = DateUtil.lastMonth();
                int year1 =  DateUtil.year(date1) ;
                int month1 =  DateUtil.month(date1)+1; //月份加1

                searchStartTime= getFirstDayOfMonth1(year1,month1);
                searchEndTime = getLastDayOfMonth1(year1,month1);
                break;
            case "3":   //获取半年内时间
                Date date2 = DateUtil.date();  //获取当天时间
                int year2 =  DateUtil.year(date2) ;
                int month2 =  DateUtil.month(date2)-5;//半年+6个月
                searchStartTime= getFirstDayOfMonth1(year2,month2);
                searchEndTime = DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
                break;
        }
        timeMap.put("searchStartTime",searchStartTime);
        timeMap.put("searchEndTime",searchEndTime);
        return timeMap;
    }

    //分组排序
    public Map groupByingForDuty(Map<String,List<CheckedDetail>> map){
        map.forEach( (k,v) ->{

            v.forEach( s->{
                String time =s.getRecordDate()+" "+s.getDutyTimeStart();
                Date date = DateUtil.parse(time,"yyyy-MM-dd HH:mm");
                s.setRecordTime( date.getTime());

            });
            v.sort((CheckedDetail map1, CheckedDetail map2) -> (map1.getRecordTime()) == (map2.getRecordTime()) ? 0 : (map1.getRecordTime()) > (map2.getRecordTime()) ? -1 : 1);
        });
        return map;
    }

    //签到状态分组
    public Map groupByingForSignIn(List<CheckedDetail> list){
        Map<String,List<CheckedDetail>> map = list.stream().collect(Collectors.groupingBy(CheckedDetail::getSignIn));//签到情况分组
        Map<String,List<CheckedDetail>> listMap = new HashMap<>();
        List<CheckedDetail> list1 = new ArrayList<>();
        List<CheckedDetail> listHas = new ArrayList<>();
        List<CheckedDetail> listNot = new ArrayList<>();
        List<CheckedDetail> listLate = new ArrayList<>();
        if(map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS)!=null && map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS).size()>0) {
            listMap.put("SIGN_IN_HAS",map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS))  ;//已签到
            map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_HAS).forEach(s -> {
                CheckedDetail checkedDetail = new CheckedDetail();
                checkedDetail.setTeacherId(s.getTeacherId());
                checkedDetail.setTeacherName(s.getTeacherName());
                checkedDetail.setTeacherImgul(s.getTeacherImgul());
                listHas.add(checkedDetail);
            });
        }else{
            listMap.put("SIGN_IN_HAS",list1);
        }
        if(map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_NOT)!=null && map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_NOT).size()>0) {
            listMap.put("SIGN_IN_NOT",map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_NOT));//未签到
            map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_NOT).forEach(a -> {
                CheckedDetail checkedDetail = new CheckedDetail();
                checkedDetail.setTeacherId(a.getTeacherId());
                checkedDetail.setTeacherName(a.getTeacherName());
                checkedDetail.setTeacherImgul(a.getTeacherImgul());
                listNot.add(checkedDetail);
            });
        }else{
            listMap.put("SIGN_IN_NOT",list1);
        }
        if(map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE)!=null && map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE).size()>0) {
            listMap.put("SIGN_IN_LATE",map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE)); //迟到
            map.get(Constant.DUTY_SIGN_IN_CONDITION.SIGN_IN_LATE).forEach(b -> {
                CheckedDetail checkedDetail = new CheckedDetail();
                checkedDetail.setTeacherId(b.getTeacherId());
                checkedDetail.setTeacherName(b.getTeacherName());
                checkedDetail.setTeacherImgul(b.getTeacherImgul());
                listLate.add(checkedDetail);
            });
        }else{
            listMap.put("SIGN_IN_LATE",list1);
        }
        listMap.put("SIGN_IN_HAS_NAME",listHas);//已签到名单
        listMap.put("SIGN_IN_NOT_NAME",listNot);//未签到名单
        listMap.put("SIGN_IN_LATE_NAME",listLate);//迟到名单
        return listMap;
    }

    //签到时间对比
    public int signInTimeCompare(String startTime,String endTime,String punchTimeLater,String recordDate){
        startTime = recordDate+" "+startTime+":00";  //值班开始时间
        endTime = recordDate+" "+endTime+":00";      //值班结束时间
        punchTimeLater = punchTimeLater+":00"; //提前打卡时间
        Date dateStart = DateUtil.parse(startTime,"yyyy-MM-dd HH:mm:ss");
        Date dateEnd = DateUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss");
        Date datePunch = DateUtil.parse(punchTimeLater,"yyyy-MM-dd HH:mm:ss");
        Date date = DateUtil.date();
        int compareToStart = date.compareTo(dateStart);  //  -1  0  1
        int compareToEnd = date.compareTo(dateEnd);      //  -1  0  1
        int compareToPunch = date.compareTo(datePunch);  //  -1  0  1
        if ((compareToStart == 1) && (compareToEnd == -1 || compareToEnd == 0)) {
           return 1;  //值班时间内签到         迟到状态
        }
        if( ( compareToPunch == 1 || compareToPunch ==0 ) && (compareToStart == -1 || compareToStart == 0 ) ){
         return 2;    //值班开始时间之前签到   正常签到
        }
        if( compareToEnd ==1 ){
            return 3; //超过值班结束时间签到   无法签到
        }
        if( compareToPunch == -1){
            return  4;  //提前打卡，无法签到
        }
        return 0;
    }


    public Pager getPagerForCheckedDetail(){
        Pager pager = new Pager();
        pager.setPaging(false);
        return pager;
    }

    public List getMapForeach(Map<String,List<CheckedDetail>> map,List<CheckedDetail> list ){
        map.forEach((k,v)->{
            CheckedDetail checkedDetail4 = new CheckedDetail();
            checkedDetail4.setTeacherName(v.get(0).getTeacherName());
            checkedDetail4.setTeacherImgul(v.get(0).getTeacherImgul());
            checkedDetail4.setTel(v.get(0).getTel());
            checkedDetail4.setCheckedDetailList(v);
            list.add(checkedDetail4);
        });
        return list;
    }

}



