package com.yice.edu.cn.jw.service.exam.buildPaper.reviewTask;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.*;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.jw.service.exam.examManage.SchoolExamService;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReviewTaskService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private SchoolYearService schoolYearService;


    //????????????
    public List<SchoolExam> findSchoolExamListByCondionKong(SchoolExam schoolExam, String teacherId,String schoolId) {
        //??????????????????
        CurSchoolYear curSchoolYear = schoolYearService.findCurSchoolYear(schoolId);
        long start = System.currentTimeMillis();
        Document document = new Document("$match", new Document("courses", new Document("$elemMatch", new Document("allUpload", true).append("answerSheet.answerSheetDatas.reviewTeachers._id", teacherId))).append("reviewedTeacherIds", new Document("$ne", teacherId)).append("fromTo",curSchoolYear.getFromTo()));
        Document document1 = new Document("$sort", new Document("createTime", -1));
        Document document2 = new Document("$skip", (schoolExam.getPager().getPage() - 1) * schoolExam.getPager().getPageSize());
        Document document3 = new Document("$limit", schoolExam.getPager().getPageSize());
        AggregateIterable<Document> aggregateIterable = mot.getCollection("schoolExam").aggregate(Arrays.asList(document, document1, document2, document3));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<SchoolExam> list = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document4 = mongoCursor.next();
            list.add(mot.getConverter().read(SchoolExam.class, document4));
        }
        Stream<SchoolExam> schoolExamStream = list.stream();
        List<List<JwCourse>> jwCourseList = schoolExamStream.map(SchoolExam::getCourses).collect(Collectors.toList());
        jwCourseList.stream().forEach(f->{//List<JwCourse>
            Stream<JwCourse> jwCourseStream = f.stream();
            jwCourseStream.forEach(s->{//JwCourse
                Stream<AnswerSheetData> answerSheetItemStream =  s.getAnswerSheet().getAnswerSheetDatas().stream();
                List<AnswerSheetData> answerSheetData = answerSheetItemStream.filter(x->{
                    for(int i = 0;i<x.getReviewTeachers().size();i++){
                        if(x.getReviewTeachers().get(i).getId().equals(teacherId)){
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
                s.getAnswerSheet().setAnswerSheetDatas(answerSheetData);
            });
        });
        for(int i =0;i<list.size();i++){
            int size = list.get(i).getCourses().size();
            for(int j = size-1;j>=0;j--){
                if(!(list.get(i).getCourses().get(j).getAnswerSheet().getAnswerSheetDatas()!=null&&list.get(i).getCourses().get(j).getAnswerSheet().getAnswerSheetDatas().size()>0)){
                    list.get(i).getCourses().remove(j);
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end-start);
        /*//???????????????????????????????????????????????????????????????
        List<SchoolExam> schoolExamList = new ArrayList<>();
        for(SchoolExam schoolExam1: list){
            List<JwCourse> jwCourseList = new ArrayList<>();
            for(JwCourse jwCourse : schoolExam1.getCourses()){
                List<AnswerSheetData> answerSheetDataList = new ArrayList<>();
                boolean flags = false;
                for(AnswerSheetData answerSheetData:jwCourse.getAnswerSheet().getAnswerSheetDatas()){
                    for(ReviewTeacher reviewTeacher:answerSheetData.getReviewTeachers()){
                        if(reviewTeacher.getId().equals(teacherId)){
                            answerSheetDataList.add(answerSheetData);
                            flags = true;
                            break;
                        }
                    }
                }
                if(flags){
                    jwCourse.getAnswerSheet().setAnswerSheetDatas(answerSheetDataList);
                    jwCourseList.add(jwCourse);
                }
            }
            if(jwCourseList!=null&&jwCourseList.size()>0){
                schoolExam1.setCourses(jwCourseList);
                schoolExamList.add(schoolExam1);
            }
        }

        */


        return list;
    }

    //????????????
    public long findSchoolExamLongByCondionKong(SchoolExam schoolExam, String teacherId,String schoolId) {
        //??????????????????
        CurSchoolYear curSchoolYear = schoolYearService.findCurSchoolYear(schoolId);
        Document document = new Document("$match", new Document("courses", new Document("$elemMatch", new Document("allUpload", true).append("answerSheet.answerSheetDatas.reviewTeachers._id", teacherId))).append("reviewedTeacherIds", new Document("$ne", teacherId)).append("fromTo",curSchoolYear.getFromTo()));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("schoolExam").aggregate(Arrays.asList(document));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        List<SchoolExam> list = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document1 = mongoCursor.next();
            list.add(mot.getConverter().read(SchoolExam.class, document1));
        }

        return list.size();
    }

    //?????????????????????????????????????????????????????????
    public List<StuScore> findSchoolExamStuScoreKong(AnswerSheetData answerSheetData, String courseId, String schoolExamId, String teacherId) {
        ReviewTeacher reviewTeacher = new ReviewTeacher();
        int count = 0;

        if (answerSheetData != null && answerSheetData.getReviewTeachers() != null) {
            count = answerSheetData.getReviewTeachers().size();
        }
        //????????????????????????????????????
        for (int i = 0; i < count; i++) {
            if (answerSheetData.getReviewTeachers().get(i).getId().equals(teacherId)) {
                reviewTeacher = answerSheetData.getReviewTeachers().get(i);
                break;
            }
        }

        //????????????????????????????????????
        Document document = new Document("$match", new Document("schoolExamId", schoolExamId).append("course._id", courseId));
        Document document1 = new Document("$sort", new Document("_id", 1));
        Document document2 = new Document();
        Document document3 = new Document();
        if (count != 0) {
            document2 = new Document("$skip", reviewTeacher.getAnswerSheetRange()[0]);
            document3 = new Document("$limit", reviewTeacher.getAnswerSheetRange()[1]-reviewTeacher.getAnswerSheetRange()[0]);
        }


        //????????????????????????????????????????????????,????????????
        Document document4 = new Document("$match", new Document("courses.answerSheet.reviewed", false));


        List<StuScore> stuScoreList = new ArrayList<>();
        AggregateIterable<Document> aggregateIterable;
        if (count != 0) {
            aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document, document1, document2, document3));
        } else {
            aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document, document1));
        }

        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document documentStuScore = mongoCursor.next();
            stuScoreList.add(mot.getConverter().read(StuScore.class, documentStuScore));
        }

        return stuScoreList;
    }

    //??????????????????????????????????????????
    public ResponseJson findSchoolExamStuScoreNewInterface(AnswerSheetData answerSheetData, String courseId, String schoolExamId, String teacherId){
        int typeId = answerSheetData.getTypeId();
        List<StuScore> list = findSchoolExamStuScoreKong(answerSheetData, courseId, schoolExamId, teacherId);
        //??????????????????
        int listSize = list.size();
        //????????????(??????)?????????
        int size = 0;
        for(int i =list.size()-1;i>=0;i--){
            if(list.get(i).getMissing()||list.get(i).getLostPaper()){
                size++;
                //???????????????????????????????????????????????????????????????????????????
                if(listSize==size){
                    updateStuScoreSchoolExamKong(list.get(i),typeId,teacherId,answerSheetData.getAnswerSheetItems().get(0).getNum());
                    return new ResponseJson(false,"?????????????????????????????????????????????");
                }
                list.remove(i);
            }
        }

        StuScore stuScore = new StuScore();
        /*String[] stuScoreId =new String[2];*/
        int count = list.size();
        int index = 0;
        int[] indexCount = new int[2];
        indexCount[1] = count;
        book:for(int i =0;i<count;i++){
                for(int j = 0;j<list.get(i).getAnswerSheetDatas().size();j++){
                    if(typeId==list.get(i).getAnswerSheetDatas().get(j).getTypeId()&&(list.get(i).getAnswerSheetDatas().get(j).getReviewed()==null||!list.get(i).getAnswerSheetDatas().get(j).getReviewed())&&answerSheetData.getAnswerSheetItems().get(0).getNum().equals(list.get(i).getAnswerSheetDatas().get(j).getAnswerSheetItems().get(0).getNum())){
                        stuScore = list.get(i);
                        index = i+1>count?0:i+1;
                        break book;
                    }
                }
        }
        indexCount[0] = index;
        return new ResponseJson(stuScore,indexCount);
    }

    public int findStuScoreSchoolExamKongCount(AnswerSheetData answerSheetData, String courseId, String schoolExamId, String teacherId) {
        ReviewTeacher reviewTeacher = new ReviewTeacher();
        int count = 0;

        if (answerSheetData != null && answerSheetData.getReviewTeachers() != null) {
            count = answerSheetData.getReviewTeachers().size();
        }
        //????????????????????????????????????
        for (int i = 0; i < count; i++) {
            if (answerSheetData.getReviewTeachers().get(i).getId().equals(teacherId)) {
                reviewTeacher = answerSheetData.getReviewTeachers().get(i);
                break;
            }
        }

        Document document = new Document("$match", new Document("schoolExamId", schoolExamId).append("course._id", courseId));
        Document document1 = new Document("$sort", new Document("_id", 1));
        Document document2 = new Document();
        Document document3 = new Document();
        if (count != 0) {
            document2 = new Document("$skip", reviewTeacher.getAnswerSheetRange()[0]);
            document3 = new Document("$limit", reviewTeacher.getAnswerSheetRange()[1]-reviewTeacher.getAnswerSheetRange()[0]);
        }

        //????????????????????????????????????????????????,????????????
        Document document4 = new Document("$match", new Document("courses.answerSheet.reviewed", false));


        List<StuScore> stuScoreList = new ArrayList<>();
        AggregateIterable<Document> aggregateIterable;
        if (count != 0) {
            aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document, document1, document2, document3));
        } else {
            aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document, document1));
        }

        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document documentStuScore = mongoCursor.next();
            stuScoreList.add(mot.getConverter().read(StuScore.class, documentStuScore));
        }
        return stuScoreList.size();
    }


    public void updateStuScoreSchoolExamKong(StuScore stuScore, Integer typeId, String teacherId, Integer num) {
        //????????????????????????Id?????????Id????????????
        String courseId = stuScore.getCourse().getId();
        String schoolExamId = stuScore.getSchoolExamId();
        Update updateStuScore = new Update();
        endStuScore:for(int i = 0;i<stuScore.getAnswerSheetDatas().size();i++){
            for(int j = 0;j<stuScore.getAnswerSheetDatas().get(i).getAnswerSheetItems().size();j++){
                if(stuScore.getAnswerSheetDatas().get(i).getAnswerSheetItems().get(j).getNum()==num){
                    updateStuScore.set("answerSheetDatas."+i,stuScore.getAnswerSheetDatas().get(i));
                    break endStuScore;
                }
            }
        }

        //????????????????????????
        Query query = new Query(Criteria.where("_id").is(stuScore.getId()));
        //??????????????????????????????
        StuScore stuScore1 = stuScore;
        if(stuScore.getAnswerSheetDatas().size()>0){
            stuScore1 = mot.findAndModify(query, updateStuScore,FindAndModifyOptions.options().returnNew(true), StuScore.class);
        }
        //???????????????????????????????????????
        List<AnswerSheetData> sheetData = stuScore1.getAnswerSheetDatas();
        boolean flag = true;
            //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            for (int i = 0; i < sheetData.size(); i++) {
                if (!(sheetData.get(i).getReviewed() != null && sheetData.get(i).getReviewed())) {
                    flag = false;
                    break;
                }
            }
        //??????????????????????????????????????????????????????
        Criteria criteria1 = Criteria.where("_id").is(schoolExamId);
        Query query2 = new Query(criteria1);
        //????????????????????????
        SchoolExam schoolExam1 = mot.findOne(query2, SchoolExam.class);

        //???????????????????????????????????????
        if (flag) {
            Double d = 0.0;
            Double s = 0.0;
            for (int i = 0; i < sheetData.size(); i++) {
                for (int j = 0; j < sheetData.get(i).getAnswerSheetItems().size(); j++) {
                    d+= (double)Math.round(sheetData.get(i).getAnswerSheetItems().get(j).getYourScore()*10)/10;
                    s+= (double)Math.round(sheetData.get(i).getAnswerSheetItems().get(j).getScore()*10)/10;
                }
            }
            Update update = new Update();
            update.set("reviewed", true);
            if (!(stuScore.getLostPaper() || stuScore.getMissing())) {
                update.set("score", (double)Math.round(d*10)/10);
                stuScore1 = mot.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), StuScore.class);
                //?????????????????????????????????????????????
                if (stuScore1.getImgList() != null && stuScore1.getImgList().size() > 0&&stuScore1.getImgList().get(0)!=null) {
                    MasterMapTopciPosition mtp = new MasterMapTopciPosition();
                    //????????????????????????
                    mtp.setImgList(stuScore1.getImgList());
                    List<PostilTopciPosition> list = new ArrayList<>();
                    //????????????
                    PostilTopciPosition postilTopciPositionScore = new PostilTopciPosition();
                    postilTopciPositionScore.setTypeId(0);
                    postilTopciPositionScore.setYouScore(d);
                    postilTopciPositionScore.setScore((double)Math.round(s*10)/10);
                    TopicPosition topicPosition = new TopicPosition();
                    topicPosition.setX(0.0);
                    topicPosition.setY(0.0);
                    topicPosition.setHeight(0.0);
                    topicPosition.setPage(1);
                    topicPosition.setWidth(0.0);
                    TopicPosition[] topicPositions = new TopicPosition[1];
                    topicPositions[0] = topicPosition;
                    postilTopciPositionScore.setTopicPositionList(topicPositions);
                    list.add(postilTopciPositionScore);
                    JwCourse jwCoursex = schoolExam1.getCourses().stream().filter(f->
                                courseId.equals(f.getId())
                            ).findFirst().get();
                    for (int j = 0; j < jwCoursex.getAnswerSheet().getAnswerSheetDatas().size(); j++) {
                            //??????????????????????????????????????????
                            count:for (AnswerSheetItem answerSheetItem : jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getAnswerSheetItems()) {
                                PostilTopciPosition postilTopciPosition1 = new PostilTopciPosition();
                                postilTopciPosition1.setTopicPositionList(answerSheetItem.getTopicPositions());
                                for (AnswerSheetData answerSheetData : stuScore1.getAnswerSheetDatas()) {
                                    if (jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getTypeId() == answerSheetData.getTypeId()) {
                                        for (AnswerSheetItem answerSheetItems : answerSheetData.getAnswerSheetItems()) {
                                            if (answerSheetItem.getNum().equals(answerSheetItems.getNum())) {
                                                postilTopciPosition1.setScore((double)Math.round(answerSheetItems.getScore()*10)/10);
                                                postilTopciPosition1.setYouScore((double)Math.round(answerSheetItems.getYourScore()*10)/10);
                                                postilTopciPosition1.setTypeId(answerSheetItems.getTypeId());
                                                if(answerSheetItems.getPostil()!=null){
                                                    postilTopciPosition1.setPostil(answerSheetItems.getPostil());
                                                }
                                                list.add(postilTopciPosition1);
                                                continue count;
                                            }
                                        }
                                    }
                                }
                            }
                        //8????????????
                        if(jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getTypeId()==8||jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getTypeId()==4){
                            PostilTopciPosition postilTopciPosition2 = new PostilTopciPosition();
                            postilTopciPosition2.setTypeId(8);
                            Double score1 = 0.0;
                            Double youScore1 = 0.0;
                            postilTopciPosition2.setTopicPositionList(jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getTopicPositions());
                            for(AnswerSheetData answerSheetData :stuScore1.getAnswerSheetDatas()){
                                if((8==answerSheetData.getTypeId()||4==answerSheetData.getTypeId()) && jwCoursex.getAnswerSheet().getAnswerSheetDatas().get(j).getAnswerSheetItems().get(0).getNum()==answerSheetData.getAnswerSheetItems().get(0).getNum()){
                                    for(AnswerSheetItem answerSheetItem :answerSheetData.getAnswerSheetItems()){
                                        score1+=answerSheetItem.getScore();
                                        youScore1+=answerSheetItem.getYourScore();
                                    }
                                    score1 = (double)Math.round(score1*10)/10;
                                    youScore1 = (double)Math.round(youScore1*10)/10;
                                }
                            }
                            postilTopciPosition2.setScore(score1);
                            postilTopciPosition2.setYouScore(youScore1);
                            list.add(postilTopciPosition2);
                        }

                    }
                    mtp.setPtpList(list);
                    final String stuScoreId = stuScore.getId();
                    new Thread() {
                        @Override
                        public void run() {
                            drawTextAndImg(Constant.RES_PRE, mtp, stuScoreId, mot);
                        }
                    }.start();

                }

            }

        }


        //??????????????????????????????????????????????????????
        Document document = new Document("$match", new Document("schoolExamId", schoolExamId));
        Document document1 = new Document("$sort", new Document("_id", 1));
        List<StuScore> stuScoreList1 = new ArrayList<>();
        AggregateIterable<Document> aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document, document1));
        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document documentStuScore = mongoCursor.next();
            stuScoreList1.add(mot.getConverter().read(StuScore.class, documentStuScore));
        }
        //?????????????????????????????????
        int size = schoolExam1.getCourses().size();
        //?????????????????????????????????
        int count = stuScoreList1.size();

        //??????????????????????????????????????????
        List<StuScore> stuScores = new ArrayList<>();
        //???????????????????????????????????????????????????????????????
        for (int i = 0; i < count; i++) {
            if (courseId.equals(stuScoreList1.get(i).getCourse().getId())) {
                stuScores.add(stuScoreList1.get(i));
            }
        }

        //???????????????????????????????????????????????????????????????????????????????????????
        boolean bool = true;
        //?????????????????????????????????????????????
        int stuScouresCount = stuScores.size();
        look:
        for (int i = 0; i < stuScouresCount; i++) {
            //???????????????????????????
            List<AnswerSheetData> answerSheetData = stuScores.get(i).getAnswerSheetDatas();
            int ansertSheetDatasCount = stuScores.get(i).getAnswerSheetDatas().size();
            for (int j = 0; j < ansertSheetDatasCount; j++) {
                if (typeId == answerSheetData.get(j).getTypeId()&&answerSheetData.get(j).getAnswerSheetItems().get(0).getNum().equals(num)) {
                    if (!(answerSheetData.get(j).getReviewed() != null && answerSheetData.get(j).getReviewed())) {
                        bool = false;
                        break look;
                    }
                }
            }
        }
        //????????????????????????????????????????????????????????????????????????????????????????????????
        if (bool) {
            Criteria criteria4 = Criteria.where("_id").is(schoolExamId);
            Query query3 = new Query(criteria4);
            Update update = new Update();
            lo:
            for (int i = 0; i < size; i++) {
                List<JwCourse> jwCourses = schoolExam1.getCourses();
                for (int j = 0; j < jwCourses.get(i).getAnswerSheet().getAnswerSheetDatas().size(); j++) {
                    if (jwCourses.get(i).getId().equals(courseId) && jwCourses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getTypeId().equals(typeId)&&jwCourses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getAnswerSheetItems().get(0).getNum().equals(num)) {
                        update.set("courses." + i + ".answerSheet.answerSheetDatas." + j + ".reviewed", true);
                        break lo;
                    }
                }
            }
            schoolExam1 = mot.findAndModify(query3, update, FindAndModifyOptions.options().returnNew(true), SchoolExam.class);
        }

        boolean flags = true;
        //?????????????????????????????????false?????????flags??????false
        loop:
        for (int i = 0; i < stuScores.size(); i++) {
            if (!(stuScores.get(i).getReviewed() != null && stuScores.get(i).getReviewed())) {
                flags = false;
                break loop;
            }
        }

        //???????????????????????????????????????????????????????????????
        if (flags) {
            Criteria criteria2 = Criteria.where("_id").is(schoolExamId).andOperator(Criteria.where("courses._id").is(courseId));
            Query query3 = new Query(criteria2);
            Update update = new Update();
            for (int i = 0; i < size; i++) {
                if (schoolExam1.getCourses().get(i).getId().equals(courseId)) {
                    update.set("courses." + i + ".allReview", true);
                }
            }
            schoolExam1 = mot.findAndModify(query3, update, FindAndModifyOptions.options().returnNew(true), SchoolExam.class);
        }


        //???????????????????????????????????????
        ReviewTeacher reviewTeacher = new ReviewTeacher();
        for (int i = 0; i < size; i++) {//??????
            List<JwCourse> courses = schoolExam1.getCourses();
            end:
            for (int j = 0; j < courses.get(i).getAnswerSheet().getAnswerSheetDatas().size(); j++) {//??????
                if (courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds() != null) {
                    for (int p = 0; p < courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds().size(); p++) {
                        if (teacherId.equals(courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds().get(p))) {
                            //????????????????????????????????????????????????????????????????????????????????????????????????????????????
                            continue end;
                        }
                    }
                }
                for (int k = 0; k < courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers().size(); k++) {
                    //????????????????????????????????????????????????
                    if (courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers() != null && teacherId.equals(courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers().get(k).getId())) {
                        //???????????????????????????????????????
                        reviewTeacher = courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers().get(k);

                        //???????????????????????????????????????????????????
                        List<StuScore> stuScoreList2 = new ArrayList<>();
                        for (int z = 0; z < count; z++) {
                            if (courses.get(i).getId().equals(stuScoreList1.get(z).getCourse().getId())) {
                                stuScoreList2.add(stuScoreList1.get(z));
                            }
                        }
                        //?????????????????????????????????Id
                        Integer typeIdSchoolExam = courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getTypeId();
                        Integer num1 = courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getAnswerSheetItems().get(0).getNum();
                        //??????????????????????????????????????????????????????????????????????????????
                        boolean flagSchoolExam1 = true;

                        schoolExam1:
                        for (int x = reviewTeacher.getAnswerSheetRange()[0]; x < reviewTeacher.getAnswerSheetRange()[1]; x++) {
                            //????????????????????????
                            if(stuScoreList2 != null && stuScoreList2.size() > 0){
                                if ( stuScoreList2.get(x) != null && stuScoreList2.get(x).getAnswerSheetDatas() != null) {
                                    for (int y = 0; y < stuScoreList2.get(x).getAnswerSheetDatas().size(); y++) {
                                        if (typeIdSchoolExam == stuScoreList2.get(x).getAnswerSheetDatas().get(y).getTypeId()&&num1.equals(stuScoreList2.get(x).getAnswerSheetDatas().get(y).getAnswerSheetItems().get(0).getNum())) {
                                            //??????????????????????????????????????????false??????null????????????
                                            if (!(stuScoreList2.get(x).getAnswerSheetDatas().get(y).getReviewed() != null && stuScoreList2.get(x).getAnswerSheetDatas().get(y).getReviewed())) {
                                                flagSchoolExam1 = false;
                                                break schoolExam1;
                                            }
                                        }
                                    }
                                }
                            } else{
                                flagSchoolExam1 = false;
                                break ;
                            }

                        }
                        //?????????????????????????????????reviewedTeacherIds?????????
                        if (flagSchoolExam1) {
                            List<String> reviewedTeacherIds = new ArrayList<>();
                            if (courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds() != null) {
                                reviewedTeacherIds = courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds();
                            }
                            boolean s = true;
                            for (int x = 0; x < reviewedTeacherIds.size(); x++) {
                                if (teacherId.equals(reviewedTeacherIds.get(x))) {
                                    s = false;
                                }
                            }
                            if (s) {
                                reviewedTeacherIds.add(teacherId);
                                Criteria criteria2 = Criteria.where("_id").is(schoolExamId);
                                Query query3 = new Query(criteria2);
                                Update update = new Update();
                                update.set("courses." + i + ".answerSheet.answerSheetDatas." + j + ".reviewedTeacherIds", reviewedTeacherIds);
                                schoolExam1 = mot.findAndModify(query3, update, FindAndModifyOptions.options().returnNew(true), SchoolExam.class);
                            }

                        }
                    }
                }
            }






        }
        //????????????????????????????????????????????????????????????
        //????????????????????????????????????????????????reviewedTeacherIds(List<String>)???????????????????????????Id
        boolean flagSchoolExam = true;
        for (int i = 0; i < size; i++) {
            List<JwCourse> courses = schoolExam1.getCourses();
            go:
            for (int j = 0; j <courses.get(i).getAnswerSheet().getAnswerSheetDatas().size(); j++) {
                //????????????????????????????????????
                if (courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers() != null) {
                    for (int k = 0; k < courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers().size(); k++) {
                        //??????????????????????????????
                        if (teacherId.equals(courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewTeachers().get(k).getId())) {
                            //?????????????????????????????????????????????????????????????????????
                            if (courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds() != null) {
                                for (int z = 0; z < courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds().size(); z++) {
                                    //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????flagSchoolExam??????
                                    if (teacherId.equals(courses.get(i).getAnswerSheet().getAnswerSheetDatas().get(j).getReviewedTeacherIds().get(z))) {
                                        continue go;
                                    }
                                }
                                //????????????????????????????????????????????????????????????flagSchoolExam??????false
                                flagSchoolExam = false;
                                break go;
                            } else {
                                flagSchoolExam = false;
                                break go;
                            }
                        }
                    }
                }

            }
        }


        if (flagSchoolExam) {
            List<String> reviewedTeacherIds = new ArrayList<>();
            //??????????????????????????????
            if (schoolExam1.getReviewedTeacherIds() != null) {
                reviewedTeacherIds = schoolExam1.getReviewedTeacherIds();
            }
            boolean boo = true;
            for (int i = 0; i < reviewedTeacherIds.size(); i++) {
                //????????????????????????????????????
                if (teacherId.equals(schoolExam1.getReviewedTeacherIds().get(i))) {
                    boo = false;
                }
            }
            if (boo) {
                reviewedTeacherIds.add(teacherId);
                Criteria criteria2 = Criteria.where("_id").is(schoolExamId);
                Query query3 = new Query(criteria2);
                Update update = new Update();
                update.set("reviewedTeacherIds", reviewedTeacherIds);
                mot.updateFirst(query3, update, "schoolExam");
            }

        }

    }

    //????????????????????????(?????????)
    public List<SchoolExam> findStudentSchoolExamList(Student student){
        Document document = new Document("$match",new Document("student._id",student.getId()).append("reviewed",true));
        Document document1 = new Document("$sort",new Document(student.getPager().getSortField(),("asc").equals(student.getPager().getSortOrder())?1:-1));
        Document document2 = new Document("$group",new Document("_id","$schoolExamId"));
        Document document3  = new Document("$skip",(student.getPager().getPage() - 1) * student.getPager().getPageSize());
        Document document4 = new Document("$limit",student.getPager().getPageSize());
        Document document5 = new Document("$lookup",new Document("from","schoolExam").append("localField","_id").append("foreignField","_id").append("as","schoolExam"));
        Document document6 = new Document("$unwind","$schoolExam");
        Document document7 = new Document("$replaceRoot",new Document("newRoot","$schoolExam"));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document,document1,document2,document3,document4,document5,document6,document7));
        List<SchoolExam> schoolExams = new ArrayList<>();
        MongoCursor<Document> mongoCursor  = aggregateIterable.iterator();

        while (mongoCursor.hasNext()){
            Document documentStuScore = mongoCursor.next();
            schoolExams.add(mot.getConverter().read(SchoolExam.class,documentStuScore));
        }
        schoolExams.forEach(f->{
            f.setExamRate(null);
            f.setReviewedTeacherIds(null);
            f.getCourses().forEach(c->{
                c.setAnswerSheet(null);
            });
        });

        return schoolExams;
    }

    //?????????????????????????????????????????????
    public StuScore findOneStuScoreByScoreCond(ScoreCond scoreCond){
        Criteria criteria = Criteria.where("student._id").is(scoreCond.getStudentId()).andOperator(Criteria.where("schoolExamId").is(scoreCond.getSchoolExamId()),Criteria.where("course._id").is(scoreCond.getCourseId()));

        Query query = new Query(criteria);

        StuScore stuScore = mot.findOne(query,StuScore.class,"stuScore");
        return stuScore;

    }

    //???????????????????????????????????????id->stuScoreId???????????????num
    public ResponseJson findAnswerSheetItemOne(AnswerSheetItem answerSheetItem){
        Criteria criteria = Criteria.where("_id").is(answerSheetItem.getStuScoreId());
        Query query = new Query(criteria);
        StuScore stuScore = mot.findOne(query,StuScore.class);
        String img = new String();
        String youAnswer = new String();
        String course = stuScore.getCourse().getId();
        TopicPosition[] positions = null;
        boolean flag = false;
        flags:for(AnswerSheetData a :stuScore.getAnswerSheetDatas()){
            for(AnswerSheetItem a2 : a.getAnswerSheetItems()){
                if(a2.getNum().equals(answerSheetItem.getNum())){
                    //?????????
                    if(a.getTypeId()==8){
                        youAnswer =  a2.getYourAnswer();
                    }else{
                        img = a.getImg();
                        flag = true;
                    }
                    break flags;
                }
            }
        }


        Criteria criteria1 = Criteria.where("_id").is(stuScore.getSchoolExamId());
        Query query1 = new Query(criteria1);
        SchoolExam schoolExam = mot.findOne(query1,SchoolExam.class);
        PaperTopics paperTopics = new PaperTopics();
        to:for(JwCourse f: schoolExam.getCourses()){
            if(f.getId().equals(course)){
                //??????????????????
                if(f.getPaperId()!=null){
                    Paper paper = new Paper();
                    paper.setId(f.getPaperId());
                    paper = paperService.findOneTestPaperByCondition(paper);
                    int num = 1;
                    of:for(int i = 0;i<paper.getSubject().size();i++){
                        for(int j = 0;j<paper.getSubject().get(i).getTopicsList().size();j++){
                            if(num == answerSheetItem.getNum()){
                                paperTopics = paper.getSubject().get(i).getTopicsList().get(j);
                                break of;
                            }
                            num++;
                        }
                    }
                }
                //??????????????????
                for(AnswerSheetData answerSheetData :f.getAnswerSheet().getAnswerSheetDatas()){
                    for(AnswerSheetItem answerSheetItem1 :answerSheetData.getAnswerSheetItems()){
                        if(answerSheetItem1.getNum().equals(answerSheetItem.getNum())){
                            positions = answerSheetItem1.getTopicPositions();
                            break to;
                        }
                    }
                }
            }
        }

        if(flag){
            paperTopics.setImg(img);
            paperTopics.setTopicPositions(positions);
        }else{
            paperTopics.setYouAnswer(youAnswer);
        }
        return new ResponseJson(paperTopics);



    }

    //??????????????????????????????
    public long findStudentSchoolExamCount(Student student){
        Document document = new Document("$match",new Document("student._id",student.getId()).append("reviewed",true));
        Document document1 = new Document("$sort",new Document(student.getPager().getSortField(),-1));
        Document document2 = new Document("$group",new Document("_id","$schoolExamId"));
        AggregateIterable<Document> aggregateIterable = mot.getCollection("stuScore").aggregate(Arrays.asList(document,document1,document2));
        MongoCursor<Document> cursor = aggregateIterable.iterator();
        List<StuScore> stuScores = new ArrayList<>();
        while (cursor.hasNext()){
            Document documentStuScore = cursor.next();
            stuScores.add(mot.getConverter().read(StuScore.class,documentStuScore));
        }
        return stuScores.size();
    }



    public static void drawTextAndImg(String judgePath, MasterMapTopciPosition mmtp, String stuScoreId, MongoTemplate mot) {
        long startTime = System.currentTimeMillis();
        List<String> imgList = mmtp.getImgList();
        List<String> newimgList = new ArrayList<>();
        List<PostilTopciPosition> ptpList = mmtp.getPtpList();
        //???,???????????????
        byte[] byteOne = HttpKit.downloadFileToServer("upload/avatar/2019/0528/2efef6ce1cae48c7.png");
        byte[] byteTwo =  HttpKit.downloadFileToServer("upload/avatar/2019/0528/5a9c0cd59fcc4291.png");
        byte[] byteThree = HttpKit.downloadFileToServer("upload/avatar/2019/0528/c35a9e5c303c46f5.png");
        //???????????????
        for (int idx = 0; idx < imgList.size(); idx++) {
            //?????????????????????????????????Graphics2d?????????
            String filePath = imgList.get(idx);
            byte[] byte1 = HttpKit.downloadFileToServer(filePath);
            filePath = judgePath + filePath;
            ImageIcon imgIcon = new ImageIcon(byte1);
            Image img = imgIcon.getImage();
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.drawImage(img, 0, 0, null);

            Double scale = 4800 * (width * 1.0 / 4654) / 420;     //????????????????????? ??? ????????????????????????

            g.setColor(getColor("#EE3B3B"));   //???????????????????????????
            String fontType = "??????";
            Double fontSize = 5.93 * scale;      //??????????????????
            Font font = new Font(fontType, Font.BOLD, fontSize.intValue());
            g.setFont(font);                   //????????????



            //??????ptplist??????????????? ?????????
            for (PostilTopciPosition ptp : ptpList) {
                //???????????????????????????????????????????????????????????? ??????????????????
                Integer ptpPage = ptp.getTopicPositionList()[0].getPage();
                if (ptpPage != idx + 1) continue;

                //????????????????????????
                Double ptpX = ptp.getTopicPositionList()[0].getX();
                Double ptpY = ptp.getTopicPositionList()[0].getY();
                Double ptpWidth = ptp.getTopicPositionList()[0].getWidth();
                Double ptpHeight = ptp.getTopicPositionList()[0].getHeight();
                Double picSizestard = 10d*scale;
                int typeId = ptp.getTypeId();

                //??????????????????????????????????????????
                FontMetrics metrics = g.getFontMetrics(font);

                //??????????????????

                //??????????????????
                Double score = ptp.getScore();
                Double youScore = ptp.getYouScore();
                double s = score;
                double y = youScore;
                Integer num = (int) s;
                Integer num1 = (int) y;
                Double d = score - num;
                Double d1 = youScore - num1;
                String txt1 = new String();
                //????????????????????????????????????????????????
                if(ptp.getTypeId()!=1&&ptp.getTypeId()!=2&&ptp.getTypeId()!=3&&ptp.getTypeId()!=9){
                    //?????????????????????????????????

                    if (d == 0 && d1 == 0) {
                        txt1 = Integer.toString(num1) + "/" + Integer.toString(num);
                    }else if(d==0&&d1!=0){
                        txt1 = Double.toString(ptp.getYouScore())+"/"+Integer.toString(num);
                    }else if(d!=0&&d1==0){
                        txt1 = Integer.toString(num1)+"/"+Double.toString(ptp.getScore());
                    } else {
                        txt1 = Double.toString(ptp.getYouScore()) + "/" + Double.toString(ptp.getScore());
                    }

                }
                String txt = txt1;
                int txtWidth = metrics.stringWidth(txt);
                int txtHeight = metrics.getHeight();
                Double txtX = (ptpX + ptpWidth) * scale - txtWidth*1.3;    ///M
                Double txtY = ptpY * scale;


                byte[] byte2 = null;
                if (youScore - score < 0.000001d && youScore - score > -0.000001d) {
                    byte2 = byteOne;
                } else if (youScore < score && youScore > 0.000001d) {
                    byte2 = byteTwo;
                } else if (youScore < 0.000001d) {
                    byte2 = byteThree;
                }



                ImageIcon imgJudge = new ImageIcon(byte2);
                Image imgright = imgJudge.getImage();
                /*g.drawImage(imgright, picX.intValue(), picY.intValue(), picSize.intValue(), picSize.intValue(), null);*/
                if(typeId == 1 || typeId == 2 || typeId == 3 || typeId == 9 ||typeId == 4){                  //?????????
                    Double picSize = ptpHeight*scale;
                    Double picX = ptpX * scale ;
                    Double picY = ptpY* scale;

                    g.drawImage(imgright, picX.intValue(), picY.intValue(), picSize.intValue(), picSize.intValue(), null);

                }
               /* else if(){             //?????????

                    Double picSize = ptpHeight*scale;
                    Double picX = (ptpX + ptpWidth*0.7 )* scale ;
                    Double picY = ptpY * scale;
                    g.drawString(txt, txtX.intValue(), txtY.intValue());
                    g.drawImage(imgright, picX.intValue(), picY.intValue(), picSize.intValue(), picSize.intValue(), null);

                }*/
                else if(typeId == 8){             //?????????
                    g.drawString(txt, txtX.intValue(), txtY.intValue());

                }else if(typeId==0){
                    int sumPicSize = (new Double(60*scale)).intValue();
                    BufferedImage buffImg = new BufferedImage(sumPicSize, sumPicSize, BufferedImage.TYPE_INT_RGB);
                    Graphics2D gd = buffImg.createGraphics();
                    //????????????  start
                    buffImg = gd.getDeviceConfiguration().createCompatibleImage(sumPicSize, sumPicSize, Transparency.TRANSLUCENT);
                    gd=buffImg.createGraphics();
                    gd.drawImage(buffImg, 0, 0, null);
                    //????????????  end
                    double y1 = youScore;
                    Integer num3 = (int) y1;
                    Double d3 = youScore - num1;
                    String sumScore;
                    if(d3==0){
                        sumScore = num3.toString();
                    }else {
                        sumScore = youScore.toString();
                    }

                    Double sfontSize = 20*scale;           //????????????????????????
                    Font sfont = new Font(fontType, Font.PLAIN, sfontSize.intValue());

                    gd.setFont(sfont);
                    gd.setColor(getColor("#EE3B3B"));        //???????????????????????????
                    int sumScoreX = (new Double(147*scale)).intValue();
                    int sumScoreY = (new Double(27.34*scale)).intValue();
                    int angle = -15;
                    gd.rotate(Math.toRadians(angle));
                    gd.drawString(sumScore, 0, sumPicSize/2);
                    //??????????????????????????????????????????
                    FontMetrics summetrics = gd.getFontMetrics(sfont);
                    int sumHeight = summetrics.getHeight();
                    int sumWidth = summetrics.stringWidth(sumScore);

                    Line2D lin1 = new Line2D.Float(sumWidth*0.35f, sumPicSize*0.6f, sumWidth*0.7f, sumPicSize*0.6f);
                    Line2D lin2 = new Line2D.Float(sumWidth*0.35f, sumPicSize*0.65f, sumWidth*0.7f, sumPicSize*0.65f);
                    gd.setStroke(new BasicStroke(8.0f));
                    gd.draw(lin1);
                    gd.draw(lin2);

                    g.drawImage(buffImg, sumScoreX, sumScoreY-sumPicSize/2, null);

                    gd.dispose();

                }else{             //?????????

                    Double picpicSizetemp = ptpHeight<ptpWidth?ptpHeight:ptpWidth;
                    Double picSize = picpicSizetemp*0.5*scale;
                    Double picX = (ptpX + (ptpWidth)*0.25) * scale;
                    Double picY = (ptpY + (ptpHeight)*0.25) * scale;

                    g.drawImage(imgright, picX.intValue(), picY.intValue(), picSize.intValue(), picSize.intValue(), null);

                    g.drawString(txt, txtX.intValue(), txtY.intValue());

                    drawNotes(g, ptp, font, scale);
                    g.setFont(font);
                }

            }
            g.dispose();

            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bimage, "JPEG", out);
                byte[] bytes = out.toByteArray();
                String s = Base64.getEncoder().encodeToString(bytes);
                s = "data:image/jpg;base64," + s;
                MultipartFile file = Base64DecodeMultipartFile.base64Convert(s);
                String imgUrl = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_REVIEW_IMG);
                newimgList.add(imgUrl);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Query query = new Query(Criteria.where("_id").is(stuScoreId));
        Update update = new Update();
        update.set("newImgList", newimgList);
        mot.updateMulti(query, update, "stuScore");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "??????");
    }

    // color #2395439
    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
    public static void drawNotes(Graphics2D g, PostilTopciPosition ptp, Font notesfont, Double scale){
        //????????????????????????
        String noteString = ptp.getPostil();
        Double ptpX = ptp.getTopicPositionList()[0].getX();
        Double ptpY = ptp.getTopicPositionList()[0].getY();
        Double ptpWidth = ptp.getTopicPositionList()[0].getWidth();
        Double ptpHeight = ptp.getTopicPositionList()[0].getHeight();
        Double picSize = 10d*scale;
        //??????????????????????????????????????????
        FontMetrics metrics = g.getFontMetrics(notesfont);
        int noteHeight = metrics.getHeight();

        //????????????????????????
        Double notesX = (ptpX + ptpWidth*0.05) * scale;    ///M
        Double notesY = (ptpY +ptpHeight)* scale - noteHeight*2;

        //????????????????????????
        Double scaledptpWidth = ptpWidth*scale;
        int lineWidth = (int)(scaledptpWidth.intValue()*0.4);

        List<String> notesList = new ArrayList<>();
        int maxWidth = 0;
        maxWidth = notesSplit(g,noteString, notesList, notesfont, lineWidth);

        Double noteX = notesX;
        Double noteY = ptpY * scale;

        g.setFont(notesfont);
        for(int noteidx = 0; noteidx<notesList.size(); noteidx++){
            String note = notesList.get(noteidx);
            noteY = notesY + noteHeight*(noteidx);
            g.drawString(note, noteX.intValue(), noteY.intValue());
        }

    }

    public static int notesSplit(Graphics2D g,String noteString, List<String> notesList, Font font, int lineWidth){
        //??????????????????????????????????????????
        FontMetrics metrics = g.getFontMetrics(font);
        String[] noteArray = new String[0];
        if(noteString!=null&&noteString.length()>0){
            noteArray  = noteString.split(" ");
        }

        int maxWidth = 0;
        String newline = "";
        int newlineWidth = 0;
        String nonChinese = "";
        int nonChineseWidth = 0;
        int restWidth = lineWidth;

        for (int i=0; i<noteArray.length; i++){                       //???????????????

            String words = noteArray[i];
            for (int j=0; j<words.length(); j++){                       //???????????????????????????
                char word = words.charAt(j);
                //???????????????
                if ( (word>='a'&&word<='z') || (word>='A'&&word<='Z') || (word>='0'&&word<='9') || word==','|| word=='.'|| word=='\''|| word=='?'|| word=='!'){

                    String temp = nonChinese + word;
                    int tempWidth = nonChineseWidth + metrics.charWidth(word);
                    if (nonChineseWidth > restWidth ){                //??????????????????????????????????????????
                        if (restWidth<lineWidth){                     //???????????????????????????????????????
                            notesList.add(newline);
                            if(newlineWidth > maxWidth){
                                maxWidth = newlineWidth;
                            }
                            nonChinese = temp;
                            nonChineseWidth = tempWidth;
                            restWidth = lineWidth;

                            newline = "";
                            newlineWidth = 0;

                        }
                        else if(restWidth==lineWidth){                //?????????????????????????????????????????????????????????????????????
                            notesList.add(nonChinese);
                            if(nonChineseWidth > maxWidth){
                                maxWidth = nonChineseWidth;
                            }
                            nonChinese = Character.toString(word);
                            nonChineseWidth = metrics.charWidth(word);
                            restWidth = lineWidth;

                            newline = "";
                            newlineWidth = 0;
                        }
                    }
                    else{
                        nonChinese += Character.toString(word);
                        nonChineseWidth += metrics.charWidth(word);
                    }
                }
                //??????
                else{
                    //?????????????????????
                    if (nonChinese.length() > 0){
                        newline += nonChinese;
                        newlineWidth += nonChineseWidth;
                        restWidth = restWidth-nonChineseWidth;
                        nonChinese = "";
                        nonChineseWidth = 0;

                    }

                    int wordWidth = metrics.charWidth(word);
                    if(wordWidth>restWidth){

                        notesList.add(newline);
                        if(newlineWidth > maxWidth){
                            maxWidth = newlineWidth;
                        }
                        newline = Character.toString(word);
                        newlineWidth = wordWidth;
                        restWidth = lineWidth-newlineWidth;
                    }
                    else{
                        newline += word;
                        newlineWidth += wordWidth;
                        restWidth = restWidth - wordWidth;

                    }
                }
            }

            //?????????????????????
            if (nonChinese.length() > 0){
                newline += nonChinese;
                newlineWidth += nonChineseWidth;
                restWidth = restWidth-nonChineseWidth;
                nonChinese = "";
                nonChineseWidth = 0;

            }

            newline += " ";
            newlineWidth += metrics.charWidth(' ');
            restWidth -= metrics.charWidth(' ');

        }

        if(newline.length() > 0){
            notesList.add(newline);
            if(newlineWidth > maxWidth){
                maxWidth = newlineWidth;
            }
        }

        return maxWidth;
    }




}