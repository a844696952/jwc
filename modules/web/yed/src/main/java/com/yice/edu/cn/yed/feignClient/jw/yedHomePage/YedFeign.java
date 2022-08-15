package com.yice.edu.cn.yed.feignClient.jw.yedHomePage;


import com.yice.edu.cn.common.pojo.jw.yed.*;
import com.yice.edu.cn.common.pojo.jw.yed.JournalCircle;
import com.yice.edu.cn.common.pojo.jw.yed.StudentCheckWork;
import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.common.pojo.jw.yed.Yed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "jw",contextId = "yedFeign",path = "/yedHomePage")
public interface YedFeign {

    @GetMapping("/findJournalStatic/{educationBureauId}")
    List<JournalCircle> findJournalStatic(@PathVariable("educationBureauId") String educationBureauId);

    @GetMapping("/findNewbornList")
    List<Yed> findNewbornList(@Param("educationBureauId") String educationBureauId);

/*    @PostMapping("/findSchoolHomeworkNumByDateAndStatus")
    List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(@RequestBody HomeworkCountQueryVo vo);

    @PostMapping("/findSchoolHomeworkNumByDate")
    List<HomeworkCountViewVo> findSchoolHomeworkNumByDate(@RequestBody HomeworkCountQueryVo vo);*/

    @GetMapping("/findSpaceByRoleList/{educationBureauId}")
    List<Yed> findSpaceByRoleList(@PathVariable("educationBureauId") String educationBureauId);

    @PostMapping("/findSchoolNotifiedListByCondition")
    List<SchoolNotified> findSchoolNotifiedListByCondition(@RequestBody SchoolNotified schoolNotified);


    @GetMapping("/findStudentCheckWork/{educationBureauId}")
    StudentCheckWork findStudentCheckWork(@PathVariable("educationBureauId") String educationBureauId);

    @PostMapping("/findEnrolmentList")
    List<Object> findEnrolmentList(@RequestBody Enrolment enrolment);

   /* @GetMapping("/findSchoolByEducation/{educationBureauId}")
    List<School>findSchoolByEducation(@PathVariable("educationBureauId") String educationBureauId);*/

    @GetMapping("/findTaskSituation/{educationBureauId}")
    List<AreaOperationVolume>findTaskSituation(@PathVariable("educationBureauId") String educationBureauId);
}