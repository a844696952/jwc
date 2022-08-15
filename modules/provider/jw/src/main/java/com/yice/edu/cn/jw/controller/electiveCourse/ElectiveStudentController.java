package com.yice.edu.cn.jw.controller.electiveCourse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveSetService;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveStudentService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/electiveStudent")
@Api(value = "/electiveStudent",description = "选修学生关联表模块")
public class ElectiveStudentController {
    @Autowired
    private ElectiveStudentService electiveStudentService;
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private ElectiveSetService electiveSetService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ElectiveStudentController.class);

    @GetMapping("/findElectiveStudentById/{id}")
    @ApiOperation(value = "通过id查找选修学生关联表", notes = "返回选修学生关联表对象")
    public ElectiveStudent findElectiveStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return electiveStudentService.findElectiveStudentById(id);
    }

    @PostMapping("/saveElectiveStudent")
    @ApiOperation(value = "保存选修学生关联表", notes = "返回选修学生关联表对象")
    public Map saveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody ElectiveStudent electiveStudent){
        Map result=new HashMap();
        Map m=checkSaveValidate(electiveStudent);

        if((boolean)m.get("result")){
            //        long begin=System.currentTimeMillis();
            try {
//            logger.info("begin{}",begin);
                electiveStudentService.saveElectiveStudent(electiveStudent);
            } catch (Exception e) {
//            logger.info("end{}",System.currentTimeMillis()-begin);
                logger.error(electiveStudent.getStudentName()+"===>添加选课失败");
                result.put("result",false);
                m.put("code",Constant.ELECTIVE_ERR_CODE.SELECT_ERRO);
                result.put("msg","添加选课失败");
                return result;
            }
        }else {
            return m;
        }

        result.put("result",true);
        return result;
    }

    @PostMapping("/batchSaveElectiveStudent")
    @ApiOperation(value = "保存选修学生关联表", notes = "返回选修学生关联表对象")
    public Map batchSaveElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @RequestBody List<ElectiveStudent> electiveStudents){
        Map result=new HashMap();
        List<String> erroList=new ArrayList<>();
        electiveStudents.forEach(electiveStudent -> {
          Map m=  checkSaveValidate(electiveStudent);

          if((boolean)m.get("result")){
              try {
                  electiveStudentService.saveElectiveStudent(electiveStudent);
              } catch (Exception e) {
                  logger.error(electiveStudent.getStudentName()+"===>添加选课失败");
                  erroList.add(electiveStudent.getStudentName()+":添加选课失败");
              }
          }else {
              erroList.add(electiveStudent.getStudentName()+":"+m.get("msg"));
          }

        });


        if(erroList.size()>0){
            result.put("result",false);
            result.put("msg",erroList);
        }else {
            result.put("result",true);
        }
        return  result;
    }

    @PostMapping("/findElectiveStudentListByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表", notes = "返回选修学生关联表列表")
    public List<ElectiveStudent> findElectiveStudentListByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        return electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
    }
    @PostMapping("/findElectiveStudentCountByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表个数", notes = "返回选修学生关联表总个数")
    public long findElectiveStudentCountByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        return electiveStudentService.findElectiveStudentCountByCondition(electiveStudent);
    }

    @PostMapping("/updateElectiveStudent")
    @ApiOperation(value = "修改选修学生关联表", notes = "选修学生关联表对象必传")
    public void updateElectiveStudent(
            @ApiParam(value = "选修学生关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudentService.updateElectiveStudent(electiveStudent);
    }

    @GetMapping("/deleteElectiveStudent/{id}")
    @ApiOperation(value = "通过id删除选修学生关联表")
    public void deleteElectiveStudent(
            @ApiParam(value = "选修学生关联表对象", required = true)
            @PathVariable String id){
        ElectiveStudent electiveStudent=electiveStudentService.findElectiveStudentById(id);
       ElectiveCourse electiveCourse= electiveCourseService.findElectiveCourseById(electiveStudent.getElectiveId());
        electiveStudentService.deleteElectiveStudent(id);
        List<String> idList=new ArrayList<>();
        idList.add(electiveStudent.getStudentId());

        pushToStudent(idList,electiveCourse.getName(),electiveStudent.getElectiveId());
    }
    @PostMapping("/deleteElectiveStudentByCondition")
    @ApiOperation(value = "根据条件删除选修学生关联表")
    public void deleteElectiveStudentByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        electiveStudentService.deleteElectiveStudentByCondition(electiveStudent);
    }
    @PostMapping("/findOneElectiveStudentByCondition")
    @ApiOperation(value = "根据条件查找单个选修学生关联表,结果必须为单条数据", notes = "返回单个选修学生关联表,没有时为空")
    public ElectiveStudent findOneElectiveStudentByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        return electiveStudentService.findOneElectiveStudentByCondition(electiveStudent);
    }

    @PostMapping("/uploadElectiveStudent")
    public Map<String,Object> uploadElectiveStudent(@RequestBody List<ElectiveStudent> list){
        Map<String,Object> result=ValidateRules(list);
        if(result.get("code").equals("200")){
            list.forEach(v->{
                ElectiveStudent electiveStudent=new ElectiveStudent();
                electiveStudent.setSchoolId(v.getSchoolId());
                electiveStudent.setStudentName(v.getStudentName());
                electiveStudent.setStudentNo(v.getStudentNo());
                electiveStudent.setElectiveId(v.getElectiveId());

                ElectiveStudent e=  findOneElectiveStudentByCondition(electiveStudent);
                v.setId(e.getId());
                updateElectiveStudent(v);
            });
        }
        return  result;
    }


    public Map<String,Object>  ValidateRules(List<ElectiveStudent> list){
        //校验列表
        List<String> errors = new ArrayList<>();
        String ElectiveId=list.get(0).getElectiveId();

        ElectiveStudent electiveStudent=new ElectiveStudent();
        electiveStudent.setElectiveId(ElectiveId);
        if(findElectiveStudentCountByCondition(electiveStudent)!=list.size()){
            errors.add("学生数量不符合，请修改表格后重新导入");
        }


        list.forEach(c -> {
            int i = list.indexOf(c) + 1;//获取当前所在条数
            StringBuffer error = new StringBuffer();//异常提示

            electiveStudent.setStudentName(c.getStudentName());
            electiveStudent.setStudentNo(c.getStudentNo());
            long num=findElectiveStudentCountByCondition(electiveStudent);
            if(num==0){
                error.append("该学生不存在;");
            }
            if (c.getIsScore()==null) {
                error.append("学分不能为空;");
            }else{
                if (!c.getIsScore().equals(1)&&!c.getIsScore().equals(0)) {
                    error.append("学分只允许填是否");
                }
            }
            if (StringUtil.isNullOrEmpty(c.getStudentName())) {
                error.append("姓名不能为空;");
            }
            if (StringUtil.isNullOrEmpty(c.getGradeName())) {
                error.append("年级不能为空;");
            }

            if (StringUtil.isNullOrEmpty(c.getClassesName())) {
                error.append("班级不能为空;");
            }

            if (StringUtil.isNullOrEmpty(c.getJoinTime())) {
                error.append("入班时间不能为空;");
            }
            if (StringUtil.isNullOrEmpty(c.getSex())) {
                error.append("性别不能为空;");
            }



            //异常添加list
            if (error.length() > 0) {
                error.insert(0, "第" + i + "条,");
                errors.add(error.toString());
            }

        });

        //重复数据统计
        List<String> resultList = list.stream().collect(Collectors.collectingAndThen(Collectors
                .groupingBy(e->e.getStudentName()+e.getStudentNo(), Collectors.counting()), map->{
            map.values().removeIf(size -> size == 1);
            List<String> tempList = new ArrayList(map.keySet());
            return tempList;
        }));
        if(resultList.size()>0){
            errors.add("有"+resultList.size()+"条重复数据");
        }


        Map<String, Object> result = new HashMap<>();
        if (errors.size() <= 0) {
            result.put("code", "200");
        } else {
            result.put("code", "222");
            result.put("errors", errors);
        }

        return  result;
    }


    @PostMapping("/findMyElectiveStudentListByCondition")
    List<ElectiveStudent> findMyElectiveStudentListByCondition( @RequestBody ElectiveStudent electiveStudent){
      return   electiveStudentService.findMyElectiveStudentListByCondition(electiveStudent);

    }

    @PostMapping("/findMyElectiveStudentCountByCondition")
    long findMyElectiveStudentCountByCondition( @RequestBody ElectiveStudent electiveStudent){
        return   electiveStudentService.findMyElectiveStudentCountByCondition(electiveStudent);
    }
    @PostMapping("/findSchoolYearElectiveStudentsByCondition")
    List<ElectiveStudent> findSchoolYearElectiveStudentsByCondition( @RequestBody ElectiveStudent electiveStudent){
        return   electiveStudentService.findSchoolYearElectiveStudentsByCondition(electiveStudent);

    }

    @PostMapping("/findSchoolYearElectiveStudentsCountByCondition")
    long findSchoolYearElectiveStudentsCountByCondition( @RequestBody ElectiveStudent electiveStudent){
        return   electiveStudentService.findSchoolYearElectiveStudentsCountByCondition(electiveStudent);
    }

    @PostMapping("/getCanSelectCourseList")
    @ApiOperation(value = "选课报名情况页面，获取可选课程集合,传入班级id:classesId,studentId", notes = "返回响应对象", response=ElectiveCourse.class)
    public  List<ElectiveCourse> getCanSelectCourseList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveStudent electiveStudent){
        List<ElectiveCourse> electiveCourseList= electiveStudentService.getCanSelectCourseList(electiveStudent);

        //移除本身已选的的课程
        electiveCourseList.removeIf(e->{
            if(e.getStudentId()!=null){
                return  e.getStudentId().equals(electiveStudent.getStudentId());
            }else {
                return false;
            }
        });

        return  electiveCourseList;
    }


    @PostMapping("/getAlreadySelectCourseList")
    @ApiOperation(value = "获取已选课程集合", notes = "返回响应对象", response=ElectiveCourse.class)
    public  List<ElectiveCourse> getAlreadySelectCourseList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveStudent electiveStudent){
        List<ElectiveCourse> electiveCourseList=  electiveStudentService.getAlreadySelectCourseList(electiveStudent);
        DoReturnDataStatus(electiveCourseList);


        return  electiveCourseList;

    }

    @PostMapping("/getAlreadySelectCourseCount")
    long getAlreadySelectCourseCount(@RequestBody ElectiveStudent electiveStudent){
        return  electiveStudentService.getAlreadySelectCourseCount(electiveStudent);

    }

    @PostMapping("/findSchoolYearStudentScoreListByCondition")

    List<ElectiveStudent> findSchoolYearStudentScoreListByCondition( @RequestBody ElectiveStudent electiveStudent){

        return  electiveStudentService.findSchoolYearStudentScoreListByCondition(electiveStudent);
    }

    @PostMapping("/findSchoolYearStudentScoreCountByCondition")
    long findSchoolYearStudentScoreCountByCondition( @RequestBody ElectiveStudent electiveStudent){

        return  electiveStudentService.findSchoolYearStudentScoreCountByCondition(electiveStudent);
    }

    @PostMapping("/checkTimeRepeatCount")
    long checkTimeRepeatCount(@RequestBody ElectiveStudent es){
        return  electiveStudentService.checkTimeRepeatCount(es);
    }

    @PostMapping("/findElectiveStudentScoreListByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表", notes = "返回选修学生关联表列表")
    public List<ElectiveStudent> findElectiveStudentScoreListByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        return electiveStudentService.findElectiveStudentScoreListByCondition(electiveStudent);
    }
    @PostMapping("/findElectiveStudentScoreCountByCondition")
    @ApiOperation(value = "根据条件查找选修学生关联表列表个数", notes = "返回选修学生关联表总个数")
    public long findElectiveStudentScoreCountByCondition(
            @ApiParam(value = "选修学生关联表对象")
            @RequestBody ElectiveStudent electiveStudent){
        return electiveStudentService.findElectiveStudentScoreCountByCondition(electiveStudent);
    }


    private void DoReturnDataStatus(List<ElectiveCourse> electiveCourseList) {
        electiveCourseList.forEach(e->{
            //未开始: 1、未到报名开始时间
            if(DateUtil.date().before(DateUtil.parse(e.getSignUpStartTime()))){
                e.setSignUpStatus(1);
                //人数不足: 1、已到报名开始时间  2、未结课 3、未关闭 4、班级人数<最小开班人数
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()<e.getMinNum()&&DateUtil.date().before(DateUtil.parse(e.getCourseEndTime()))
                    ){
                e.setSignUpStatus(2);

                //可开课:1、已到报名开始时间  2、未结课 3、未关闭 4、班级人数>=最小开班人数 5、班级人数<最大开班人数 6、未到报名结束时间
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&e.getClassesPeopleNum()<e.getMaxNum()
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseStartTime()))
                    ){
                e.setSignUpStatus(3);
                //已报满:1、已到报名开始时间  2、未结课 3、未关闭 4、班级人数>=最小开班人数 5、班级人数=最大开班人数 6、未到报名结束时间
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpStartTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&e.getClassesPeopleNum().equals(e.getMaxNum())
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseStartTime()))
                    ){
                e.setSignUpStatus(4);

                //已开课:1、已过报名结束时间  2、未结课 3、未关闭 4、班级人数>=最小开班人数  5、已到课程开始时间 6、未到课程结束时间
            }else if(DateUtil.date().after(DateUtil.parse(e.getSignUpEndTime()))&&e.getClassEndStatus().equals(0)
                    &&e.getCloseStatus().equals(0)&&e.getClassesPeopleNum()>=e.getMinNum()&&DateUtil.date().after(DateUtil.parse(e.getCourseStartTime()))
                    &&DateUtil.date().before(DateUtil.parse(e.getCourseEndTime()))
                    ){
                e.setSignUpStatus(5);

                //已结束:1、 已结课 3、未关闭  5、已到课程结束时间
            }else if((e.getClassEndStatus().equals(1) &&e.getCloseStatus().equals(0))||
                    (DateUtil.date().after(DateUtil.parse(e.getCourseEndTime())) &&e.getCloseStatus().equals(0))
                    ){
                e.setSignUpStatus(6);
                //已关闭:1、已关闭
            }else if(e.getCloseStatus().equals(1)){
                e.setSignUpStatus(7);
            }

        });
    }

    public Map checkSaveValidate(ElectiveStudent electiveStudent){
        HashMap m=new HashMap();
        ElectiveCourse electiveCourse=electiveCourseService.findElectiveCourseAndStuCountById(electiveStudent.getElectiveId());
        long count=electiveStudentService.findElectiveStudentCountByCondition(new ElectiveStudent(){{
            this.setElectiveId(electiveStudent.getElectiveId()); }});
        if(count>=electiveCourse.getMaxNum()){
            m.put("result",false);
            m.put("code",Constant.ELECTIVE_ERR_CODE.PEOPLE_NUM_FULL);
            m.put("msg","您所申请的选修课人数已满");
            return m;
        }


        ElectiveSet electiveSet=new ElectiveSet();
        electiveSet.setGradeId(electiveStudent.getGradeId());
        electiveSet.setSchoolId(electiveStudent.getSchoolId());
        ElectiveSet electiveSet1=electiveSetService.findOneElectiveSetByCondition(electiveSet);



        ElectiveStudent es=new ElectiveStudent();
        es.setStudentId(electiveStudent.getStudentId());
        es.setGradeId(electiveStudent.getGradeId());
        es.setSchoolId(electiveStudent.getSchoolId());

        long alreadyNum=electiveStudentService.findElectiveStudentCountByCondition(es);
        if(electiveSet1==null){
            m.put("result",false);
            m.put("code",Constant.ELECTIVE_ERR_CODE.NOT_SET);
            m.put("msg","选修课未设置");
            return m;
        }else{
            if(alreadyNum>=electiveSet1.getMaxNum()){
                m.put("result",false);
                m.put("code",Constant.ELECTIVE_ERR_CODE.COURSE_FULL);
                m.put("msg","申请的课程已经达到最大数量");
                return m;
            }
        }




        ElectiveStudent es1=new ElectiveStudent();
        es1.setSchoolId(electiveStudent.getSchoolId());
        es1.setStudentId(electiveStudent.getStudentId());
        es1.setClassTimeWeek(electiveCourse.getClassTimeWeek());
        es1.setClassTimeSection(electiveCourse.getClassTimeSection());
        es1.setCourseStartTime(electiveCourse.getCourseStartTime());
        es1.setCourseEndTime(electiveCourse.getCourseEndTime());

        long repeatCount= electiveStudentService.checkTimeRepeatCount(es1);
        if(repeatCount>0){
            m.put("result",false);
            m.put("code",Constant.ELECTIVE_ERR_CODE.TIME_REPEAT);
            m.put("msg","已申请该时间段的其他选修课，上课时间不可重复");
            return m;
        }
        if(electiveCourse.getCloseStatus().equals(1)){
            m.put("result",false);
            m.put("code",Constant.ELECTIVE_ERR_CODE.COURSE_CLOSE);
            m.put("msg","申请的课程已关闭，请选择其他课程");
            return m;
        }
        if(DateUtil.date().after(DateUtil.parse(electiveCourse.getSignUpEndTime()+" 23:59:59"))){
            m.put("result",false);
            m.put("code",Constant.ELECTIVE_ERR_CODE.TIME_END);
            m.put("msg","所申请的课程报名时间已结束请选择其他课程");
            return m;
        }


        m.put("result",true);
        return  m;
    }


    public void pushToStudent(List<String> stuIdList,String title,String electiveId){
        if(stuIdList.size()>0){
            //处理推送
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePusByRefrenceId(stuIdList.toArray(new String[stuIdList.size()]),"选修课","你已从"+title+"选课中移除，点击查看详情。",Extras.ELECTIVE_COURSE_CLOSE,electiveId);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }

    }
}
