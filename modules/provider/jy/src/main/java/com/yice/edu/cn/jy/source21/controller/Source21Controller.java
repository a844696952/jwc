package com.yice.edu.cn.jy.source21.controller;

import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Subject;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.source21.service.Source21Service;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 21世纪题目资源接口
 */
@RestController
@RequestMapping("/source21")
public class Source21Controller {
    @Autowired
    private Source21Service source21Service;

    @GetMapping("/getSubject")
    @ApiOperation(value = "获取所有科目")
    public Map<Integer, List<Subject>> getSubject(){
        return source21Service.getSubject();
    }
    @GetMapping("/getSubject/{stage}")
    @ApiOperation(value = "通过学段 获取对应科目")
    public List<Subject> getSubjects(@PathVariable("stage") String stage){
        return source21Service.getSubject4stage(stage);
    }
    @GetMapping("/getBooks")
    public Map<String, List<Book>> getBooks(){
        return source21Service.getBooks();
    }
    @GetMapping("/getBooksByVersion/{versionId}")
    public List<Book> getBooksByVersion(@PathVariable("versionId") String versionId){
        return source21Service.getBooksByVersion(versionId);
    }
    @GetMapping("/getChaptersByBook/{bookId}")
    public List<Chapter> getChaptersByBook(@PathVariable("bookId") String bookId){
        return source21Service.getChaptersByBook(bookId);
    }
    @GetMapping("/getCategorys/{bookId}")
    public Integer getCategorys(@PathVariable("bookId") String bookId){
        return source21Service.getCategorys(bookId);
    }
    @GetMapping("/getKnowledge")
    public void getKnowledge(){
        source21Service.getKnowledge();
    }
    @GetMapping("/downKnowledgeByStage/{stage}")
    public void downKnowledgeByStage(@PathVariable("stage")String stage){
        source21Service.downKnowledgeByStage(stage);
    }
    @GetMapping("/getVersionsBySubject/{stage}/{subjectId}")
    public List<Version> getVersionsBySubject(@PathVariable("stage") String stage, @PathVariable("subjectId") String subjectId){
        return source21Service.getVersionsBySubject(stage,subjectId);
    }
    @PostMapping("/getQuestions")
    public APIResult<Question> getQuestions(@RequestBody SearchParam searchParam){
        return source21Service.getQuestions(searchParam);
    }
    @GetMapping("/synchronizeType")
    public void synchronizeType(){
        source21Service.synchronizeType();
    }
}
