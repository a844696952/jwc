package com.yice.edu.cn.osp.service.source21;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Subject;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.feignClient.jy.source21.Source21Feign;
import com.yice.edu.cn.osp.feignClient.jy.topics.TopicsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class Source21Service {
    @Autowired
    private Source21Feign source21Feign;
    @Autowired
    private TopicsFeign topicsFeign;

    /**
     * 获取全部学段的科目
     */
    public Map<Integer,List<Subject>> getSubject() {
        return source21Feign.getSubject();
    }

    /**
     * 我们平台通过学段获取21世纪的科目列表
     * @param stage
     */
    @Cached(key = "#stage",name = "21Subject",timeUnit = TimeUnit.DAYS,expire = 1)
    public List<Subject> getSubject4stage(String stage) {
        return source21Feign.getSubjects(stage);
    }

    /**
     * 获取所有书籍
     * @return
     */
    public Map<String,List<Book>> getBooks() {
        return source21Feign.getBooks();
    }
    /**
     * 获取指定版本的书籍
     * @return
     */
    @Cached(key = "#versionId",name = "21Book",timeUnit = TimeUnit.DAYS,expire = 1)
    public List<Book> getBooksByVersion(String versionId) {
        return source21Feign.getBooksByVersion(versionId);
    }

    /**
     * 获取章节
     */
    @Cached(key = "#bookId",name = "21Chapter",timeUnit = TimeUnit.DAYS,expire = 1)
    public List<Chapter> getChaptersByBook(String bookId){
        return source21Feign.getChaptersByBook(bookId);
    }
    /**
     * 获取章节
     */
    public int getCategorys(String bookId){
        return source21Feign.getCategorys(bookId);
    }

    /**
     * 获取所有的知识点
     * @return
     */
    public void getKnowledge(){
        source21Feign.getKnowledge();
    }

    /**
     * 通过科目获取版本信息
     * @param stage
     * @param subjectId
     * @return
     */
    public List<Version> getVersionsBySubject(String stage, String subjectId) {
        return source21Feign.getVersionsBySubject(stage,subjectId);
    }

    /**
     * 查询题目列表
     * @param searchParam
     * @return
     */
    public APIResult<Question> getQuestions(SearchParam searchParam){
        return source21Feign.getQuestions(searchParam);
    }

    /**
     * 获取题目详情
     * @param questionId
     */
    public Object getQuestionDetail(String questionId) {
        TopicParam topicParam = new TopicParam().setId(questionId)
                .setSchoolId(mySchoolId())
                .setSource(Constant.TOPIC_SOURCE.TWENTYONESHIJI)
                .setTeacherId(myId());
        return topicsFeign.findTopicDetail(topicParam);
    }
}
