package com.yice.edu.cn.osp.service.jw.exam.examManage;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.ReviewTeacher;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.*;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.answerSheet.AnswerSheetFeign;
import com.yice.edu.cn.osp.feignClient.jw.exam.examManage.ExamStudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.exam.examManage.SchoolExamFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SchoolExamService {
    @Autowired
    private SchoolExamFeign schoolExamFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private ExamStudentFeign examStudentFeign;
    @Autowired
    private AnswerSheetFeign answerSheetFeign;
    @Autowired
    private PaperService paperService;


    public SchoolExam findSchoolExamById(String id) {
        return schoolExamFeign.findSchoolExamById(id);
    }

    public SchoolExam saveSchoolExam(SchoolExam schoolExam) {
        return schoolExamFeign.saveSchoolExam(schoolExam);
    }

    public ResponseJson saveSchoolExamForOnline(SchoolExam schoolExam) {
        ResponseJson responseJson = this.handleReviewAndSoOn(schoolExam);
        if (!responseJson.getResult().isSuccess()) {
            return responseJson;
        }
        schoolExam.setExamRate(new ExamRate());
        SchoolExam insertedSchoolExam = schoolExamFeign.saveSchoolExam(schoolExam);
        ExamStudent examStudent = new ExamStudent();
        examStudent.setSchoolExamId(insertedSchoolExam.getId());
        examStudent.setExamStudentInfos((List<ExamStudentInfo>) responseJson.getData());
        examStudentFeign.saveExamStudent(examStudent);
        paperService.setPaperFlags(schoolExam,true);
        return new ResponseJson();
    }

    private ResponseJson handleReviewAndSoOn(SchoolExam schoolExam) {
        //处理考生和阅卷分配
        List<JwClasses> classes = schoolExam.getClasses();
        List<String> clazzList = classes.stream().flatMap(clazz -> Stream.of(clazz.getId())).collect(Collectors.toList());
        List<ExamStudentInfo> examStudentInfos = studentFeign.findStudentListByClazzIds(clazzList);
        if (examStudentInfos.size() == 0) {
            return new ResponseJson(false, "所在班级没有学生");
        }
        //计算每个班级有多少学生,并放入班级对象
        Map<String, Long> groupByClassId = examStudentInfos.stream().collect(Collectors.groupingBy(ExamStudentInfo::getClassesId, Collectors.counting()));
        classes.forEach(clazz->{
            if(groupByClassId.get(clazz.getId())!=null){
                clazz.setStudentCount(groupByClassId.get(clazz.getId())+"");
            }else{
                clazz.setStudentCount("0");
            }

        });
        if(schoolExam.getAutoGenExamNo()!=null&&!schoolExam.getAutoGenExamNo()){
            //获取用户手动导入的
            if(CollectionUtil.isEmpty(schoolExam.getExamStudentInfos())){
                return new ResponseJson(false,"学生考号没有上传");
            }
            if(schoolExam.getExamStudentInfos().size()!=examStudentInfos.size()){
                return new ResponseJson(false,"学生数量错误");
            }
            for (ExamStudentInfo examStudentInfo : examStudentInfos) {
                for (int i = 0; i < schoolExam.getExamStudentInfos().size(); i++) {
                    ExamStudentInfo esi = schoolExam.getExamStudentInfos().get(i);
                    if(examStudentInfo.getStudentNo().equalsIgnoreCase(esi.getStudentNo())){
                        examStudentInfo.setExamNo(esi.getExamNo());
                        break;
                    }
                }
                if(examStudentInfo.getExamNo()==null){
                    return new ResponseJson(false,examStudentInfo.getName()+"学生考号缺失或学籍号错误");
                }
            }
        }else{
            //生成8位考号
            for (ExamStudentInfo examStudentInfo : examStudentInfos) {
                String examNo;
                do {
                    examNo = RandomUtil.randomNumbers(8);
                } while (this.existExamNo(examStudentInfos, examNo));
                examStudentInfo.setExamNo(examNo);

            }
        }


        List<JwCourse> courses = schoolExam.getCourses();
        for (JwCourse course : courses) {
            //分配阅卷老师的阅题范围
            List<AnswerSheetData> answerSheetDatas = course.getAnswerSheet().getAnswerSheetDatas();
            for (int i = 0; i < answerSheetDatas.size(); i++) {
                if (answerSheetDatas.get(i).getSubjective()) {
                    reviewAssign(answerSheetDatas.get(i).getReviewTeachers(), examStudentInfos.size());
                }
            }
        }
        schoolExam.setTotalNum(examStudentInfos.size());
        return new ResponseJson(examStudentInfos);
    }

    //判断考生里是否已有该考号
    private boolean existExamNo(List<ExamStudentInfo> examStudentInfos, String examNo) {
        for (ExamStudentInfo examStudentInfo : examStudentInfos) {
            if (examNo.equals(examStudentInfo.getExamNo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 阅卷分配
     *
     * @param reviewTeachers 阅卷老师集合
     * @param total          总的考生人数
     */
    private void reviewAssign(List<ReviewTeacher> reviewTeachers, int total) {
        int size = reviewTeachers.size();
        if (size > total) {//阅卷老师居然比学生人数多,剔除多余老师
            int more = size - total;
            for (int i = 0; i < more; i++) {
                reviewTeachers.remove(size - 1 - i);
            }
            size = total;
        }
        int count = total / size;
        int remand = total % size;
        for (int i = 0; i < size; i++) {
            ReviewTeacher reviewTeacher = reviewTeachers.get(i);
            int[] range;
            if (i != size - 1) {
                range = new int[]{count * i, count * (i + 1)};
            } else {
                range = new int[]{count * i, count * (i + 1) + remand};
            }
            reviewTeacher.setAnswerSheetRange(range);
        }

    }


    public List<SchoolExam> findSchoolExamListByCondition1(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamListByCondition1(schoolExam);
    }


    public SchoolExam findOneSchoolExamByCondition(SchoolExam schoolExam) {
        return schoolExamFeign.findOneSchoolExamByCondition(schoolExam);
    }

    public long findSchoolExamCountByCondition1(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamCountByCondition1(schoolExam);
    }
    public List<SchoolExam> findSchoolExamListByCondition(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamListByCondition(schoolExam);
    }
    public long findSchoolExamCountByCondition(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamCountByCondition(schoolExam);
    }
    public void updateSchoolExam(SchoolExam schoolExam) {
        schoolExamFeign.updateSchoolExam(schoolExam);
    }

    public void deleteSchoolExam(String id) {
        schoolExamFeign.deleteSchoolExam(id);
    }

    public void deleteSchoolExamByCondition(SchoolExam schoolExam) {
        schoolExamFeign.deleteSchoolExamByCondition(schoolExam);
    }

    public List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamListByCondition2(schoolExam);
    }

    public long findSchoolExamCountByCondition2(SchoolExam schoolExam) {
        return schoolExamFeign.findSchoolExamCountByCondition2(schoolExam);
    }

    public ResponseJson updateSchoolExamForOnline(SchoolExam schoolExam) {
        SchoolExam schoolExam1 = schoolExamFeign.findSchoolExamById(schoolExam.getId());
        paperService.setPaperFlags(schoolExam1,false);
        paperService.setPaperFlags(schoolExam,true);
        ResponseJson responseJson = this.handleReviewAndSoOn(schoolExam);
        if (!responseJson.getResult().isSuccess()) {
            return responseJson;
        }
        schoolExamFeign.updateSchoolExam(schoolExam);
        ExamStudent examStudent = new ExamStudent();
        examStudent.setSchoolExamId(schoolExam.getId());
        examStudentFeign.deleteExamStudentByCondition(examStudent);
        examStudent.setExamStudentInfos((List<ExamStudentInfo>) responseJson.getData());
        examStudentFeign.saveExamStudent(examStudent);

        return new ResponseJson();
    }

    public List<StuScore> findStuScoresByCond(ScoreCond scoreCond) {
        return schoolExamFeign.findStuScoresByCond(scoreCond);
    }

    public long findStuScoreCountByCond(ScoreCond scoreCond) {
        return schoolExamFeign.findStuScoreCountByCond(scoreCond);
    }

    public long checkSchoolExamNum(SchoolExam schoolExam1) {
        return schoolExamFeign.checkSchoolExamNum(schoolExam1);
    }


    public SchoolExam deleteSchoolExamById(String id){
        return schoolExamFeign.deleteSchoolExamById(id);
    }

    public List<ExamStudentInfo> getStudentInfos(List<String> ids) {
        return studentFeign.findStudentListByClazzIds(ids);
    }

    public List<JwCourse>  findListCoursePaper(String schoolExamId){
        return schoolExamFeign.findListCoursePaper(schoolExamId);
    }
}
