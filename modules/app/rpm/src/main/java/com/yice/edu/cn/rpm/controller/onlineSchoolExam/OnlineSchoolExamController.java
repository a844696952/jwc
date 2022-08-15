package com.yice.edu.cn.rpm.controller.onlineSchoolExam;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetItem;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.validateClass.GroupSeven;
import com.yice.edu.cn.common.pojo.validateClass.GroupSix;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.rpm.service.onlineSchoolExam.OnlineSchoolExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.yice.edu.cn.rpm.interceptor.LoginInterceptor.mySchoolId;

@RequestMapping("/onlineSchoolExam")
@Api("考试相关接口")
@RestController
public class OnlineSchoolExamController {
    @Autowired
    private OnlineSchoolExamService onlineSchoolExamService;
    @Autowired
    private Validator validator;
    @GetMapping("/findOnlineSchoolExamNow")
    @ApiOperation(value = "获取学校的可用考试列表",response = SchoolExam.class)
    public ResponseJson findOnlineSchoolExamNow(){
        List<SchoolExam> schoolExams=onlineSchoolExamService.findOnlineSchoolExamNow(mySchoolId());
        return new ResponseJson(schoolExams);
    }
    @GetMapping("/findOnlineSchoolExamById/{schoolExamId}/{courseId}")
    @ApiOperation(value = "根据考试id和科目id获取科目数据",response = SchoolExam.class)
    public ResponseJson findOnlineSchoolExamById(@PathVariable String schoolExamId,@PathVariable String courseId){
        SchoolExam schoolExam = onlineSchoolExamService.findSchoolExamById(schoolExamId);
        if(schoolExam==null){
            return new ResponseJson(false, "没有查找到相关考试");
        }
        for (int i = schoolExam.getCourses().size()-1; i >=0 ; i--) {
            JwCourse course = schoolExam.getCourses().get(i);
            if(!course.getId().equals(courseId)){
                schoolExam.getCourses().remove(i);
            }else{
                course.getAnswerSheet().setAnswerSheetStore(null);
                for (AnswerSheetData answerSheetData : course.getAnswerSheet().getAnswerSheetDatas()) {
                    answerSheetData.setReviewTeachers(null);
                }
            }
        }
        return new ResponseJson(schoolExam);
    }
    @PostMapping(value = "/uploadImgs",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,headers = "content-type=multipart/form-data")
    @ApiOperation(value = "多文件上传,文件表单名称统一为files,最多支持20个,单个文件size小于3MB,返回路径列表,使用https://res.ycjdedu.com拼接后使用",notes = "请使用postman测试" ,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseJson uploadImgs( @RequestParam("files")MultipartFile[] files){
        if(files.length==0){
            return new ResponseJson(false, "请选择文件上传");
        }
        if(files.length>20){
            return new ResponseJson(false, "最多支持20个文件");
        }
        List<String> r = new ArrayList<>();
        for (MultipartFile file : files) {
            if(file.getSize()>3*1024*1024){
                return new ResponseJson(false, file.getOriginalFilename()+"大小超过3MB");
            }
        }
        for (MultipartFile file : files) {
            r.add(QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_REVIEW_IMG));
        }
        return new ResponseJson(r);
    }


    @PostMapping("/commitStuScore")
    @ApiOperation(value = "提交学生成绩数据")
    public ResponseJson commitStuScore(@RequestBody List<StuScore> stuScores){
        if(stuScores.size()==0){
            return new ResponseJson(false, "学生成绩列表不能为空");
        }

        for (StuScore stuScore : stuScores) {
            if(stuScore.getMissing()==null){
                return new ResponseJson(false, "是否缺考不能为空");
            }
            if(stuScore.getLostPaper()==null){
                return new ResponseJson(false, "是否缺卷不能为空");
            }

            Set<ConstraintViolation<StuScore>> validateResult = validator.validate(stuScore,stuScore.getMissing()||stuScore.getLostPaper()? GroupSeven.class: GroupSix.class);
            if(validateResult.size()>0){
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<StuScore> violation : validateResult) {
                    sb.append(String.format("{字段%s验证不通过,规则是:%s},",violation.getPropertyPath(),violation.getMessageTemplate()));
                }
                return new ResponseJson(false, sb.toString());
            }

        }
        StuScore stuScore = stuScores.get(0);
        SchoolExam schoolExam=onlineSchoolExamService.findSchoolExamById(stuScore.getSchoolExamId());
        String id = stuScore.getCourse().getId();
        JwCourse currentCourse=null;
        for (JwCourse course : schoolExam.getCourses()) {
            if(course.getId().equals(id)){
                currentCourse=course;
            }
        }
        if(currentCourse==null){
            return new ResponseJson(false,"当前考试不存在该课程:"+stuScore.getCourse().getName());
        }
        if(currentCourse.getAllUpload()!=null&&currentCourse.getAllUpload()){
            return new ResponseJson(false,"该科目成绩已上传完毕");
        }
        //验证个数必须是参考学生总数
        if(schoolExam.getTotalNum()!=stuScores.size()){
            return new ResponseJson(false, "上传成绩数量必输是参考学生总人数");
        }
        //验证所有考生都有成绩
        List<ExamStudentInfo> examStudentInfos = onlineSchoolExamService.findExamStudentsBySchoolExamId(schoolExam.getId());
        for (ExamStudentInfo examStudentInfo : examStudentInfos) {
            if(!hasScore(stuScores,examStudentInfo.getId())){
                return new ResponseJson(false,examStudentInfo.getExamNo()+"考号的考生成绩没有上传");
            }
        }

        boolean hasPaper= StrUtil.isNotEmpty(currentCourse.getPaperId());
        Integer subjectiveNum = currentCourse.getAnswerSheet().getSubjectiveNum();
        //验证主观题是否都有图片
        for (StuScore score : stuScores) {
            List<AnswerSheetData> answerSheetDatas = score.getAnswerSheetDatas();
            //如果缺考或者缺卷
            if(score.getMissing()){
                score.setScore(-1d);
                score.setReviewed(true);
            }else if(score.getLostPaper()){
                score.setScore(0d);
                score.setReviewed(true);
            }else if(answerSheetDatas!=null&&answerSheetDatas.size()>0){
                for (int i = 0; i < answerSheetDatas.size(); i++) {
                    AnswerSheetData answerSheetData = answerSheetDatas.get(i);
                    if(answerSheetData.getSubjective()&& StrUtil.isEmpty(answerSheetData.getImg())){
                        return new ResponseJson(false, score.getStudent().getName()+"("+score.getStudent().getExamNo()+")的主观题必须上传图片");
                    }
                    if(!answerSheetData.getSubjective()){//客观题直接设置成已阅
                        answerSheetData.setReviewed(true);
                    }
                    //判断是否客观题，如果是客观题则需要optionNum,answer,yourAnswer,right,yourScore
                    if(!answerSheetData.getSubjective()){
                        for (AnswerSheetItem answerSheetItem : answerSheetData.getAnswerSheetItems()) {
                            if(answerSheetItem.getOptionNum()==null
                                    ||ArrayUtil.isEmpty(answerSheetItem.getAnswer())
                                    ||StrUtil.isEmpty(answerSheetItem.getYourAnswer())
                                    ||answerSheetItem.getYourScore()==null
                                    ||answerSheetItem.getRight()==null){
                                return new ResponseJson(false,"客观题的话,answerSheetItem对象中optionNum,answer,yourAnswer,right,yourScore必传");
                            }
                        }
                    }
                    //如果有卷子的话，复制topicId,topicSource,和knowledges到答题卡里
                    if(hasPaper){
                        for (int j = 0; j < answerSheetData.getAnswerSheetItems().size(); j++) {
                            AnswerSheetItem answerSheetItem = answerSheetData.getAnswerSheetItems().get(j);
                            AnswerSheetItem existItem = currentCourse.getAnswerSheet().getAnswerSheetDatas().get(i).getAnswerSheetItems().get(j);
                            answerSheetItem.setTopicId(existItem.getTopicId());
                            answerSheetItem.setTopicSource(existItem.getTopicSource());
                            answerSheetItem.setKnowledges(existItem.getKnowledges());
                        }
                    }
                }
                if(subjectiveNum==0){//如果没有主观题的话,需要设置该科目成绩为已阅，并计算成绩
                    score.setReviewed(true);
                    BigDecimal bigDecimal = new BigDecimal(0);
                    for (AnswerSheetData answerSheetData : answerSheetDatas) {
                        for (AnswerSheetItem answerSheetItem : answerSheetData.getAnswerSheetItems()) {
                            bigDecimal=bigDecimal.add(BigDecimal.valueOf(answerSheetItem.getYourScore()));
                        }
                    }
                    score.setScore(bigDecimal.doubleValue());
                }
            }else{
                return new ResponseJson(false,"没有缺考或者缺卷,答题卡数据必传");
            }

        }
        if(subjectiveNum==0){//没有主观题的情况，设置本次考试的该科目为allReview
            currentCourse.setAllReview(true);
            onlineSchoolExamService.updateSchoolExam(schoolExam);
        }
        String msg=onlineSchoolExamService.commitStuScore(stuScores);

        return msg==null?new ResponseJson():new ResponseJson(false,msg);
    }
    private boolean hasScore(List<StuScore> stuScores,String studentId){
        for (StuScore stuScore : stuScores) {
            if(studentId.equals(stuScore.getStudent().getId())){
                return true;
            }
        }
        return false;
    }
    @GetMapping("/findExamStudentsBySchoolExamId/{schoolExamId}")
    @ApiOperation(value = "根据考试id获取考生列表")
    public ResponseJson findExamStudentsBySchoolExamId(@PathVariable String schoolExamId){
        List<ExamStudentInfo> examStudentInfos = onlineSchoolExamService.findExamStudentsBySchoolExamId(schoolExamId);
        return new ResponseJson(examStudentInfos);

    }


}
