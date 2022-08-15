package com.yice.edu.cn.ewb.controller.wisdomclassroom;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.StudentInfo;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.ewb.service.wisdomclassroom.TopicDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicDetail")
@Api(value = "/topicDetail",description = "习题详情模块")
public class TopicDetailController {
    @Autowired
    private TopicDetailService topicDetailService;



    @PostMapping("/findHomeworkListByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<TopicDetail> findHomeworkListByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody TopicDetail topicDetail){
        return topicDetailService.findTopicDetailListByCondition(topicDetail);
    }

    @GetMapping("/findStudentAnswerListBySidAndCid")
    @ApiOperation(value = "根据课堂检测id和学生id来查询该学生的答题情况", notes = "返回该学生的答题情况")
    public List<StudentInfo> findStudentAnswerListBySidAndCid(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestParam(value = "studentId") String studentId, @RequestParam(value = "classTestId") String classTestId
    ){
        return topicDetailService.findStudentAnswerListBySidAndCid(studentId,classTestId);
    }

}
