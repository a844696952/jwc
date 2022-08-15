package com.yice.edu.cn.osp.controller.jy.homeworkAnalyse;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.jy.homework.*;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkStudentService;
import com.yice.edu.cn.osp.service.jy.homeworkAnalyse.HomeworkAnalyseService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/homeworkAnalyse")
@Api(value = "/homeworkAnalyse", description = "作业分析模块")
public class HomeworkAnalyseController {

    @Autowired
    private HomeworkAnalyseService homeworkAnalyseService;
    @Autowired
    private HomeworkStudentService homeworkStudentService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private JwCourseService courseService;

    @Autowired
    private CurSchoolYearService curSchoolYearService;


    @GetMapping("/ignore/findHomeworkAnalyseById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworkAnalyseById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Homework homework = homeworkAnalyseService.findHomeworkAnalyseById(id);
        return new ResponseJson(homework);
    }

    @PostMapping("/ignore/findHomeworkAnalyseListByCondition")
    @ApiOperation(value = "根据条件查找布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworkAnalysesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Homework homework) {
        homework.getPager().setSortField("createTime");
        homework.getPager().setSortOrder("desc");
        if (ArrayUtil.isEmpty(homework.getTeacherIds())) {
            homework.setTeacherId(myId());
        }
        curSchoolYearService.setSchoolYearId(homework,mySchoolId());
        List<Homework> data = homeworkAnalyseService.findHomeworkAnalyseListByConditionNew(homework);
        data.stream().forEach(item->{
            JwCourse queryCourse = new JwCourse();
            queryCourse.setSchoolId(mySchoolId());
            queryCourse.setId(item.getSubjectId());
            JwCourse course = courseService.findOneJwCourseByCondition(queryCourse);
            if(course!=null) {
                item.setSubjectName(course.getAlias());
            }
        });
        long count = homeworkAnalyseService.findHomeworkAnalyseCountByConditionNew(homework);
        return new ResponseJson(data, count);

    }

    @GetMapping("/ignore/classes/findTeacherClassPostCourseListHomeworkAnalyse")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourseListHomeworkAnalyse() {
        TeacherClasses teacherClassesParam = new TeacherClasses();
        teacherClassesParam.setTeacherId(myId());
        List<TeacherClasses> teacherClasslist = teacherClassesService.findTeacherClassesListByCondition(teacherClassesParam);
        List<ClassVo> teacherResult = getClassCoureList(teacherClasslist);
        List<ClassVo> headMasterResult = Lists.newArrayList();
        TeacherPost teacherPostParam = new TeacherPost();
        teacherPostParam.setTeacherId(myId());
        teacherPostParam.setDdId(Constant.TEACHER_POST.CLASS_TEACHER);
        teacherPostParam.setSchoolId(mySchoolId());
        TeacherPost  teacherPost = teacherPostService.findOneTeacherPostByCondition(teacherPostParam);
        if(teacherPost!=null){
           //班主任
            teacherClassesParam = new TeacherClasses();
            teacherClassesParam.setClassesId(teacherPost.getClassId());
            List<TeacherClasses> headMasterClasslist = teacherClassesService.findTeacherClassesListByCondition(teacherClassesParam);
            headMasterResult  = getClassCoureList(headMasterClasslist);
        }
        return new ResponseJson(teacherResult,headMasterResult);
    }

    private List<ClassVo> getClassCoureList(List<TeacherClasses> teacherClasslist) {
        List<ClassVo> result = Lists.newArrayList();
        Map<String, List<TeacherClasses>> map = teacherClasslist.stream().collect(Collectors.groupingBy(t -> t.getClassesId()));
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
                String classId = it.next().toString();
                TeacherClasses teacherClasses =  teacherClasslist.stream().filter(o -> o.getClassesId().equals(classId)).collect(Collectors.toList()).get(0);
                ClassVo classVo = ClassVo.builder().gradeId(teacherClasses.getGradeId()).classId(teacherClasses.getClassesId()).className(teacherClasses.getClassesName()).classFullName(teacherClasses.getGradeName()+"("+teacherClasses.getClassesName()+")班").build();
                List<TeacherClasses> teacherClassesList = map.get(classId);
                List<CourseVo> list = teacherClassesList.stream().map(t -> CourseVo.builder().courseId(t.getCourseId()).courseName(t.getCourseName()).build()).collect(Collectors.toList());
                //去重
                list = list.stream().distinct().collect(Collectors.toList());
                classVo.setList(list);
                result.add(classVo);
        }
        Collections.sort(result);
        return result;
    }

    @PostMapping("/ignore/findHomeworkStudentListSubmitNotAnalyseListByCondition")
    @ApiOperation(value = "获取未提交学生列表列表", notes = "返回学生作业状态表列表")
    public ResponseJson findHomeworkStudentListAnalyseListByCondition(
            @ApiParam(value = "查询对象")
            @RequestBody RemarkHomeworkQueryVo remarkHomeworkQueryVo) {

        HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homeworkObj = new Homework();

        homeworkObj.setId(remarkHomeworkQueryVo.getId());
        homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE);
        homeworkStudent.setHomeworkObj(homeworkObj);

        List<HomeworkStudent> homeworkStudentList = new ArrayList<HomeworkStudent>();
        if (remarkHomeworkQueryVo.getType().intValue() == 3) {
            homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
            homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
        }
        return new ResponseJson(homeworkStudentList);
    }


    @PostMapping("/ignore/findHomeworkStudentTopicListAnalyseListByCondition")
    @ApiOperation(value = "获取线上作业学生做题情况列表", notes = "返回学生作业状态表列表")
    public ResponseJson findHomeworkStudentTopicListAnalyseListByCondition(
            @ApiParam(value = "查询对象")
            @RequestBody RemarkHomeworkQueryVo remarkHomeworkQueryVo) {

        RemarkHomeworkViewVo returnVo = new RemarkHomeworkViewVo();
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homeworkObj = new Homework();
        homeworkObj.setId(remarkHomeworkQueryVo.getId());
        homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE);
        homeworkStudent.setHomeworkObj(homeworkObj);
        List<HomeworkStudent> homeworkStudentList = new ArrayList<HomeworkStudent>();  //找到本次作业的学生作业记录
        Homework homework = homeworkAnalyseService.findHomeworkAnalyseById(remarkHomeworkQueryVo.getId());
        if (remarkHomeworkQueryVo.getType().intValue() == 4) {
            {
                homeworkStudentList = homeworkAnalyseService.findHomeworkStudentListAnalyseByCondition(homeworkStudent);
                if (!(homeworkStudentList.size() == 0)) {
                    //方案二  查出所有学生的做题记录
                    List<String> listStudentId = new ArrayList<>();
                    for (HomeworkStudent hStudent : homeworkStudentList) {
                        listStudentId.add(hStudent.getStudentId());  //得到studentId
                    }
                    TopicsRecord topicsRecord = new TopicsRecord();
                    topicsRecord.setStudentIds(listStudentId); //学生id列表
                    topicsRecord.setChannelId(remarkHomeworkQueryVo.getId());//作业Id
                    List<TopicsRecord> topicsRecords = homeworkAnalyseService.findTopicsRecordListAnalyseByCondition(topicsRecord);
                    //做题记录按学生id分组
                    Map<String, List<TopicsRecord>> topicsStuMap = topicsRecords.stream().collect(Collectors.groupingBy(TopicsRecord::getStudentId));

                    //封装
                    for (int i = 0; i < homeworkStudentList.size(); i++) {
                        int wrong_a = 0;
                        HomeworkStudent homeworkStudent1 = homeworkStudentList.get(i);  //接收HomeworkStudent对象
                        Homework homework1 = homeworkStudent1.getHomeworkObj();         //接收Homework对象
                        List<TopicsRecord> topicsRecordList1 = topicsStuMap.get(homeworkStudent1.getStudentId());
                        for (int j = 0; j < topicsRecordList1.size(); j++) {    //学生单次作业的题目列表
                            TopicsRecord topicsRecords1 = topicsRecordList1.get(j);    //接收TopicsRecord对象
                            homework1.getTopicArr()[j].setTopicNumber(j + 1);//题号
                            homework1.getTopicArr()[j].setStudentAnswer(topicsRecords1.getAnswer());//记录学生答案
                            homework1.getTopicArr()[j].setCorrect(topicsRecords1.getCorrect());//判断做对做错未做答
                            if (topicsRecords1.getCorrect() != 1) {
                                wrong_a++;   //错题记录
                            }
                        }
                       // homeworkStudent1.setHomeworkObj(homework1);  //返回处理好的Homework对象
                        if (homework.getTopicArr().length > 0) { //计算错题率
                            double b = NumberUtil.div((double) wrong_a, (double) homework.getTopicArr().length, 2);
                            homeworkStudent1.setWrongTopic(b);
                        }
                       // homeworkStudentList.set(i,homeworkStudent1);  //赋值
                    }
                }
                for (int i = 0; i < homework.getTopicArr().length; i++) {    //为题目创建选项(判断、单选)
                    List<String> list = new ArrayList<>();
                    List<String> list1 = new ArrayList<>();
                    List<HomeworkStudentAnalyseTopicOption> list2 = new ArrayList<>();
                    homework.getTopicArr()[i].setHomeworkAnalyseStudentName(list);
                    homework.getTopicArr()[i].setHomeworkAnalyseStudentAnswer(list1);
                    homework.getTopicArr()[i].setHomeworkStudentAnalyseTopicOption(list2);
                    List<HomeworkStudentAnalyseTopicOption> h1 = new ArrayList<>();
                    Integer b = homework.getTopicArr()[i].getTypeId();
                    if (b != null && (b == 1)) {
                        int[] panduanId = new int[]{65, 66, 67};
                        String[] panduanName = new String[]{"对", "错", "空"};
                        for (int k = 0; k < 3; k++) {
                            HomeworkStudentAnalyseTopicOption hsato1 = new HomeworkStudentAnalyseTopicOption();
                            List<String> stringList1 = new ArrayList<>();
                            List<String> stringList2 = new ArrayList<>();
                            h1.add(hsato1);
                            h1.get(k).setOptionId(panduanId[k]);
                            h1.get(k).setOptionName(panduanName[k]);
                            h1.get(k).setHomeworkAnalyseStudentAnswer(stringList1);
                            h1.get(k).setHomeworkAnalyseStudentName(stringList2);
                            homework.getTopicArr()[i].getHomeworkStudentAnalyseTopicOption().add(h1.get(k));
                        }
                    }
                    if (b != null && (b == 2)) {

                        for (int k = 0; k < Integer.parseInt(homework.getTopicArr()[i].getOptionNum()); k++) { //最后一个选项为未做答
                            HomeworkStudentAnalyseTopicOption hsato1 = new HomeworkStudentAnalyseTopicOption();
                            List<String> stringList1 = new ArrayList<>();
                            List<String> stringList2 = new ArrayList<>();
                            h1.add(hsato1);
                            h1.get(k).setOptionId((k + 65));
                            h1.get(k).setOptionName("" + (char) (int) h1.get(k).getOptionId());
                            h1.get(k).setHomeworkAnalyseStudentAnswer(stringList1);
                            h1.get(k).setHomeworkAnalyseStudentName(stringList2);
                            homework.getTopicArr()[i].getHomeworkStudentAnalyseTopicOption().add(h1.get(k));
                        }
                        if (true) {
                            int oId = Integer.parseInt(homework.getTopicArr()[i].getOptionNum());
                            HomeworkStudentAnalyseTopicOption hsato1 = new HomeworkStudentAnalyseTopicOption();
                            List<String> stringList1 = new ArrayList<>();
                            List<String> stringList2 = new ArrayList<>();
                            h1.add(hsato1);
                            h1.get(oId).setOptionId(oId + 65);
                            h1.get(oId).setOptionName("空");
                            h1.get(oId).setHomeworkAnalyseStudentAnswer(stringList1);
                            h1.get(oId).setHomeworkAnalyseStudentName(stringList2);
                            homework.getTopicArr()[i].getHomeworkStudentAnalyseTopicOption().add(h1.get(oId));
                        }
                    }
                    int notFinished = 0; //未完成人数统计
                    int wrongNumber = 0;//答错人数统计
                    for (int j = 0; j < homeworkStudentList.size(); j++) {
                        Integer a = homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getTypeId();

                        if (a != null && (a == 3 || a == 4 || a == 5 || a == 6)) {  //主观题
                            homework.getTopicArr()[i].getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                            homework.getTopicArr()[i].getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                            if (StringUtil.isNullOrEmpty(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer())) {
                                notFinished++;  //记录未完成人数
                            }
                            if(a==3){
                            if ( homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer()!=null &&  !homework.getTopicArr()[i].getAnswer().equals(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer())) {
                                wrongNumber++;   //统计答错人数
                            }
                            }
                        }
                        if (a != null && a == 1) {   //判断
                            if ( homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer()!=null &&  !homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer().equals(homework.getTopicArr()[i].getAnswer()) ) {
                                wrongNumber++; //统计答错人数
                            }
                            if (("对").equals(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer())) {
                                h1.get(0).getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                                h1.get(0).getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                            } else if (("错").equals(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer())) {
                                h1.get(1).getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                                h1.get(1).getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                            } else {
                                h1.get(2).getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                                h1.get(2).getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                                notFinished++;//记录未完成人数

                            }
                            homework.getTopicArr()[i].setHomeworkStudentAnalyseTopicOption(h1);
                        }

                        if (a != null && a == 2) {   //单选
                            if ( homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer()!=null &&  !homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer().equals(homework.getTopicArr()[i].getAnswer()) ) {
                                wrongNumber++;  //统计答错人数
                            }
                            boolean status = true;
                            for (int k = 0; k < Integer.parseInt(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getOptionNum()); k++) { //最后一个选项为未做答

                                if (homework.getTopicArr()[i].getHomeworkStudentAnalyseTopicOption().get(k).getOptionName().equals(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer())) {
                                    h1.get(k).getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                                    h1.get(k).getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                                    status = false;
                                }

                            }
                            if (status) {
                                h1.get((Integer.parseInt(homework.getTopicArr()[i].getOptionNum()) )).getHomeworkAnalyseStudentName().add(homeworkStudentList.get(j).getStudentName());
                                h1.get((Integer.parseInt(homework.getTopicArr()[i].getOptionNum()) )).getHomeworkAnalyseStudentAnswer().add(homeworkStudentList.get(j).getHomeworkObj().getTopicArr()[i].getStudentAnswer());
                                notFinished++;//记录未完成人数
                            }
                        }

                    }
                    homework.getTopicArr()[i].setErrorStudentNumber(wrongNumber);
                    homework.getTopicArr()[i].setNotFinishedStudentNumber(notFinished);
                    homework.getTopicsCounts().get(i).setWrongNum( homework.getTopicArr()[i].getErrorStudentNumber() + homework.getTopicArr()[i].getNotFinishedStudentNumber() );
                }
                Boolean a = false;
                for (int i = 0; i < homework.getTopicArr().length; i++) {
                    homework.getTopicArr()[i].setTopicNumber((i + 1));   //设置题号
                    homework.getTopicArr()[i].setAllStudentNumber(homework.getOverdueNum() + homework.getPunctualNum());//统计人数
                    if ((homework.getTopicArr()[i].getAllStudentNumber()) > 0) {
                        a = true;
                        double b = NumberUtil.div((double) (homework.getTopicArr()[i].getErrorStudentNumber() + homework.getTopicArr()[i].getNotFinishedStudentNumber()), (double) (homework.getTopicArr()[i].getAllStudentNumber()), 2);
                        homework.getTopicArr()[i].setClassesErrorRate(b); //本道题班级错误率
                    } else {
                        homework.getTopicArr()[i].setClassesErrorRate(0.00);
                    }
                }

                homeworkStudentList.stream().forEach( hst ->{
                    for (int i = 0; i <homework.getTopicArr().length ; i++) {
                        hst.getHomeworkObj().getTopicArr()[i].setClassesErrorRate(homework.getTopicArr()[i].getClassesErrorRate());
                    }
                });
                if (a) {
                    Topics[] topics = homework.getTopicArr().clone();//
                    for (int i = 0; i < topics.length; i++) {
                        for (int j = 0; j < topics.length - 1; j++) {
                            if (topics[j].getClassesErrorRate() < topics[j + 1].getClassesErrorRate()) {
                                Topics topics1 = topics[j];
                                topics[j] = topics[j + 1];
                                topics[j + 1] = topics1;
                            }
                        }
                    }
                    homework.setWrrorTopicArr(topics);
                }

            }
        }
        returnVo.setHomeworkStudentList(homeworkStudentList);
        return new ResponseJson(returnVo, homework);
    }
}
