package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",contextId = "teamTeachingPlanFeign",path = "/teamTeachingPlan")
public interface TeamTeachingPlanFeign {
    @PostMapping("/findTeamTeachingPlanListByCondition")
    public ResponseJson findTeamTeachingPlanListByCondition(@RequestBody TeamTeachingPlan teachingPlan);

    @GetMapping("/editTeamTeachingPlanById/{id}")
    public TeamTeachingPlan editTeamTeachingPlanById(@PathVariable("id") String id);

    @GetMapping("/lookTeamTeachingPlanById/{id}")
    public TeamTeachingPlan lookTeamTeachingPlanById(@PathVariable("id") String id);

    @PostMapping("/updateTeamTeachingPlan")
    public ResponseJson updateTeamTeachingPlan(@RequestBody TeamTeachingPlan teachingPlan);

    @GetMapping("/deleteTeamTeachingPlan/{id}")
    public ResponseJson deleteTeamTeachingPlan(@PathVariable("id") String id);

    @GetMapping(value = "/download/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable("id") String id);

    @GetMapping("/delete/lessonsFile/{id}")
    public ResponseJson deleteLessonsFile(@PathVariable("id") String id);

    // 查询教师的个人教案列表（未被选择提交的教案）
    @PostMapping("/findTeachingPlanListNotChosen")
    public ResponseJson findTeachingPlanListNotChosen(@RequestBody TeamTeachingPlan teachingPlan);

    @PostMapping("/findTeachingPlanList")
    public ResponseJson findTeachingPlanList(@RequestBody TeamTeachingPlan teachingPlan);
}
