package com.yice.edu.cn.tap.feignClient.source21;

import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Subject;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value="jy",contextId = "Source21Feign",path = "/source21")
public interface Source21Feign {
    @GetMapping("/getSubject")
    Map<Integer, List<Subject>> getSubject();
    @GetMapping("/getSubject/{stage}")
    List<Subject> getSubjects(@PathVariable("stage") String stage);
    @GetMapping("/getBooks")
    Map<String, List<Book>> getBooks();
    @GetMapping("/getBooksByVersion/{versionId}")
    List<Book> getBooksByVersion(@PathVariable("versionId") String versionId);
    @GetMapping("/getChaptersByBook/{bookId}")
    List<Chapter> getChaptersByBook(@PathVariable("bookId") String bookId);
    @GetMapping("/getCategorys/{bookId}")
    int getCategorys(@PathVariable("bookId") String bookId);
    @GetMapping("/getKnowledge")
    void getKnowledge();
    @GetMapping("/getVersionsBySubject/{stage}/{subjectId}")
    List<Version> getVersionsBySubject(@PathVariable("stage") String stage, @PathVariable("subjectId") String subjectId);
    @PostMapping("/getQuestions")
    APIResult<Question> getQuestions(@RequestBody SearchParam searchParam);
}
