package com.yice.edu.cn.osp.service.jw.exam.examManage;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.osp.feignClient.jw.exam.examManage.SchoolExamFeign;
import com.yice.edu.cn.osp.feignClient.jw.exam.examManage.StuScoreFeign;
import com.yice.edu.cn.osp.feignClient.jw.jwCourse.JwCourseFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class StuScoreService {
    private static ExecutorService executorService;
    private static final String BanJi = "班级";
    private static final String XingMing = "姓名";
    private static final String ZuoWeiHao = "座位号";
    private static final Logger logger = LoggerFactory.getLogger(StuScoreService.class);


    @Autowired
    private StuScoreFeign stuScoreFeign;
    @Autowired
    private SchoolExamFeign schoolExamFeign;
    @Autowired
    private StudentFeign studentFeign;


    public StuScore findStuScoreById(String id) {
        return stuScoreFeign.findStuScoreById(id);
    }

    public StuScore saveStuScore(StuScore stuScore) {
        return stuScoreFeign.saveStuScore(stuScore);
    }

    public List<StuScore> findStuScoreListByCondition(StuScore stuScore) {
        return stuScoreFeign.findStuScoreListByCondition(stuScore);
    }

    public List<StuScore> findStuScoreListByCondition1(StuScore stuScore) {
        return stuScoreFeign.findStuScoreListByCondition1(stuScore);
    }

    public StuScore findOneStuScoreByCondition(StuScore stuScore) {
        return stuScoreFeign.findOneStuScoreByCondition(stuScore);
    }

    public long findStuScoreCountByCondition(StuScore stuScore) {
        return stuScoreFeign.findStuScoreCountByCondition(stuScore);
    }

    public long findStuScoreCountByCondition1(StuScore stuScore) {
        return stuScoreFeign.findStuScoreCountByCondition1(stuScore);
    }

    public void updateStuScore(StuScore stuScore) {
        stuScoreFeign.updateStuScore(stuScore);
    }

    public void deleteStuScore(String id) {
        stuScoreFeign.deleteStuScore(id);
    }

    public void deleteStuScoreByschoolExamId(String id) {
        stuScoreFeign.deleteStuScoreByschoolExamId(id);
    }

    public void deleteStuScoreByCondition(StuScore stuScore) {
        stuScoreFeign.deleteStuScoreByCondition(stuScore);
    }

    public Workbook exportStuScoreTemplate(String id) {
        SchoolExam schoolExam = schoolExamFeign.findSchoolExamById(id);
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        try {
            exportEntityList.add(new ExcelExportEntity("班级", "number"));
            exportEntityList.add(new ExcelExportEntity("姓名", "name"));
            exportEntityList.add(new ExcelExportEntity("座位号", "seatNumber"));
            for (int i = 0; i < schoolExam.getCourses().size(); i++) {
                exportEntityList.add(new ExcelExportEntity(schoolExam.getCourses().get(i).getAlias()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        List<Map<String, Object>> stuScoreList = studentFeign.findStudentListClassesByCondition(schoolExam.getClasses());
        stuScoreList.sort((Map<String, Object> map1, Map<String, Object> map2) -> (Integer.parseInt(map1.get("number").toString()) == Integer.parseInt(map2.get("number").toString()) ? 0 : (Integer.parseInt(map1.get("number").toString()) > Integer.parseInt(map2.get("number").toString()) ? 1 : -1)));
     /*   for (int i = 0; i <stuScoreList.size() ; i++) {
            Object number = stuScoreList.get(i).get("number")+"班";
            stuScoreList.get(i).put("number",number);
        }*/
        return ExcelExportUtil.exportExcel(new ExportParams("成绩列表", "成绩"), exportEntityList, stuScoreList);
    }

    public Map<String, Object> uploadStuScore(MultipartFile file, String id) {
        Map<String, Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()) {
            List<Map<String, Object>> list = ExcelImportUtil.importExcel(is, Map.class, params);
            list = list.stream().filter(stringObjectMap -> !isAllFieldNull(stringObjectMap)).collect(Collectors.toList());
            if (list == null || list.size() <= 0) {
                result.put("size", 0);
                result.put("code", "201");
                result.put("error", "请勿上传空文件");
                return result;
            }
            if (list.size() > 10000) {
                result.put("code", "202");
                result.put("error", "超出导入上限：10000条");
                return result;
            }
            List<StuScore> stuScoreList = new ArrayList<>();
            List<Student> studentList = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            List<StuScore> stuScoreList1 = new ArrayList<>();
            SchoolExam schoolExam = null;
            Student student = null;
            StuScore stuScore = null;
            JwClasses jwClasses = null;
            JwCourse jwCourse = null;
            String classesNum = "";
            String stuName = "";
            String stuSeat = "";
            schoolExam = schoolExamFeign.findSchoolExamById(id);


            stuScore = new StuScore();
            stuScore.setSchoolExamId(id);
            stuScoreList1 = stuScoreFeign.findStuScoreListByCondition(stuScore);

            student = new Student();
            student.setGradeId(schoolExam.getGradeId());
            student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
            List<Student> stuList = studentFeign.findStudentListForExamByCondition(student);
            Map<String, List<Student>> studentMap = stuList.stream().collect(Collectors.groupingBy(Student::getClassesNumber));

            Map<String, JwClasses> classesMap = schoolExam.getClasses().stream().collect(Collectors.toMap(JwClasses::getNumber, JwClasses -> JwClasses));
            Map<String, JwCourse> courseMap = schoolExam.getCourses().stream().collect(Collectors.toMap(JwCourse::getAlias, JwCourse -> JwCourse));
            for (int i = 0; i < list.size(); i++) {
                Pattern pattern;
                int a = i + 1;//获取当前所在条数
                StringBuffer error = new StringBuffer();//异常提示
                if (list.get(0).keySet().size() - 3 < schoolExam.getCourses().size()) {
                    error.append("导入模板有误  ");
                }

                for (Map.Entry<String, Object> entry : list.get(i).entrySet()) {
                    boolean flag = false;
                    boolean flag1 = false;
                    boolean flag2 = false;
                    boolean flag3 = false;
                    boolean flag4 = false;
                    if (BanJi.equals(entry.getKey())) {
                        if (StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                            classesNum = entry.getValue().toString();
                            if (classesMap.get(classesNum) != null) {
                                jwClasses = classesMap.get(classesNum);
                                flag = true;
                            }
                            if (!flag) {
                                error.append("该班级不在本次考试中;  ");
                            }
                            continue;  //跳出本次循环
                        } else {
                            error.append("请填写班级;  ");
                            continue;
                        }

                    }

                    if (XingMing.equals(entry.getKey())) {
                        if (StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                            stuName = entry.getValue().toString();
                            if (studentMap.get(classesNum) != null) {
                                studentList = studentMap.get(classesNum);
                            }
                            for (Student s : studentList) {
                                if (s.getName().equals(stuName)) {
                                    flag2 = true;
                                }
                            }
                            if (!flag2) {
                                error.append(entry.getValue() + "不在本次考试中;  ");
                            }
                            continue;
                        } else {
                            error.append("请填写姓名;  ");
                            continue;
                        }

                    }
                    if (ZuoWeiHao.equals(entry.getKey())) {
                        if (StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                            stuSeat = entry.getValue().toString();
                            for (int j = 0; j < stuList.size(); j++) {
                                if (stuName.equals(stuList.get(j).getName()) && stuSeat.equals(String.valueOf(stuList.get(j).getSeatNumber())) && classesNum.equals(stuList.get(j).getClassesNumber())) {
                                    student = stuList.get(j);
                                    flag3 = true;
                                }
                            }
                            if (!flag3) {
                                error.append("考生信息有误;  ");
                            }
                            for (int j = 0; j < stuScoreList1.size(); j++) {
                                if (String.valueOf(stuScoreList1.get(j).getStudent().getSeatNumber()).equals(stuSeat)&&stuScoreList1.get(j).getStudent().getName().equals(stuName)&&stuScoreList1.get(j).getStudent().getClassesNumber().equals(classesNum)) {
                                    flag4 = true;
                                }
                            }
                            if (flag4) {
                                error.append("该学生已存在;  ");
                            }
                            continue;
                        } else {
                            error.append("请填写座位号;  ");
                            continue;
                        }
                    }
                    if (courseMap.get(entry.getKey()) != null) {
                        jwCourse = courseMap.get(entry.getKey());
                        flag1 = true;
                        if (StringUtils.isNotEmpty(entry.getValue() == null ? null : entry.getValue().toString())) {
                            /*pattern = Pattern.compile("-?(0|[1-9]\\d*)(\\.\\d+)?");*/
                            pattern = Pattern.compile("^(\\-|\\+?)(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,1})?$");
                            if (!pattern.matcher(entry.getValue().toString()).matches()) {
                                error.append(entry.getKey() + "分数必须为数字,且只能保留一位小数;  ");
                            } else {
                          /*    String s = String.valueOf(entry.getValue());
                                String[] b = s.split("\\.");
                                if (b.length > 1) {
                                    if (b[1].length() > 1) {
                                        error.append(entry.getKey() + "分数只允许一位小数;  ");
                                    }
                                }*/
                                if (courseMap.get(entry.getKey()).getTotalScore() < Double.parseDouble(String.valueOf(entry.getValue()))) {
                                    error.append("分数不能高于" + courseMap.get(entry.getKey()).getAlias() + "成绩总分;  ");
                                }
                                stuScore = new StuScore();
                                stuScore.setSchoolExamId(id);
                                stuScore.setClazz(jwClasses);
                                stuScore.setStudent(student);
                                stuScore.setCourse(jwCourse);
                                if (Double.parseDouble(entry.getValue().toString()) < 0) {
                                    stuScore.setScore(Double.parseDouble("-1"));
                                    stuScore.setMissing(true);
                                } else {
                                    stuScore.setScore(Double.parseDouble(entry.getValue().toString()));
                                    stuScore.setMissing(false);
                                }
                                stuScore.setCreateTime(DateUtil.now());
                                stuScoreList.add(stuScore);
                           }
                        } else {
                            error.append(entry.getKey() + "分数不能为空;  ");
                        }
                    }
                    //循环（map）结束后判断本次考试是否存在该课程
                    if (!flag1) {
                        error.append(entry.getKey() + "该课程不在本次考试中;  ");
                    }
                }
                //异常添加list
                if (error.length() > 0) {
                    error.insert(0, "第" + a + "条:");
                    errors.add(error.toString());
                }
            }
            if (errors.size() <= 0) {
                stuScoreFeign.batchSaveStuScore(stuScoreList);
                result.put("code", "200");
            } else {
                result.put("ScoreSize", list.size());
                result.put("code", "222");
                result.put("errors", errors);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public Workbook exportStuScore(String id) {
        SchoolExam schoolExam = schoolExamFeign.findSchoolExamById(id);
        List<ExcelExportEntity> exportEntityList = new ArrayList<>();
        try {
            exportEntityList.add(new ExcelExportEntity("班级", "number"));
            exportEntityList.add(new ExcelExportEntity("姓名", "name"));
            exportEntityList.add(new ExcelExportEntity("座位号", "seatNumber"));
            for (int i = 0; i < schoolExam.getCourses().size(); i++) {
                exportEntityList.add(new ExcelExportEntity(schoolExam.getCourses().get(i).getAlias(), schoolExam.getCourses().get(i).getId()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        List<Map<String, Object>> stuScoreList = stuScoreFeign.findStuScoreByschoolExamId(id);
        stuScoreList.sort((Map<String, Object> map1, Map<String, Object> map2) -> (Integer.parseInt(map1.get("number").toString()) == Integer.parseInt(map2.get("number").toString()) ? 0 : (Integer.parseInt(map1.get("number").toString()) > Integer.parseInt(map2.get("number").toString()) ? 1 : -1)));

        Double mainWastage;
            for (int k=0; k < stuScoreList.size(); k++){
                for (int j = 0; j < schoolExam.getCourses().size(); j++){
                     mainWastage=Double.parseDouble(stuScoreList.get(k).get(schoolExam.getCourses().get(j).getId()).toString());
                if(mainWastage.intValue()-mainWastage==0){
                    stuScoreList.get(k).put(schoolExam.getCourses().get(j).getId(),mainWastage.intValue());
                }else {
                    stuScoreList.get(k).put(schoolExam.getCourses().get(j).getId(),mainWastage);
                }
                }
            }




        return ExcelExportUtil.exportExcel(new ExportParams("成绩列表", "成绩"), exportEntityList, stuScoreList);
    }

    private boolean isAllFieldNull(Map<String, Object> map) {
        boolean allFiedNull = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && !StringUtils.isBlank(entry.getValue() + "")) {
                allFiedNull = false;
                break;
            }
        }
        return allFiedNull;
    }

    public List<StuScore> findStudentByScoreSection(StuScore stuScore) {
        return stuScoreFeign.findStudentByScoreSection(stuScore);
    }

    /**
     * 定义线程池
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }

    public List<StuScore> findStuScoresForDownload(String schoolExamId, List<String> courseIds) {
        return stuScoreFeign.findStuScoresForDownload(schoolExamId,courseIds);
    }
}
