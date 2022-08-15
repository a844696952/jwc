package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.TeamTeachingPlanFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamTeachingPlanService {
    @Autowired
    private TeamTeachingPlanFeign teamTeachingPlanFeign;


    public ResponseJson findTeamTeachingPlanListByCondition(TeamTeachingPlan teachingPlan) {

        return teamTeachingPlanFeign.findTeamTeachingPlanListByCondition(teachingPlan);
    }

    public TeamTeachingPlan editTeamTeachingPlanById(String id) {
        return teamTeachingPlanFeign.editTeamTeachingPlanById(id);
    }

    public TeamTeachingPlan lookTeamTeachingPlanById(String id) {
        return teamTeachingPlanFeign.lookTeamTeachingPlanById(id);
    }

    public ResponseJson updaTeamteTeachingPlan(TeamTeachingPlan teachingPlan) {

        return teamTeachingPlanFeign.updateTeamTeachingPlan(teachingPlan);
    }

    public ResponseJson deleteTeamTeachingPlan(String id) {

        return teamTeachingPlanFeign.deleteTeamTeachingPlan(id);
    }

    /*    public void downloadTeachingPlan(String id) {

            teamTeachingPlanFeign.downloadTeachingPlan(id);
        }*/
    public ResponseEntity<byte[]> downloadTeachingPlan(String id) throws IOException {
        ResponseEntity<byte[]> responseEntity = teamTeachingPlanFeign.download(id);
        return responseEntity;
    }

    public ResponseJson deleteLessonsFile(String id) {

        return teamTeachingPlanFeign.deleteLessonsFile(id);
    }


    // 查询教师的个人教案列表（未被选择提交的教案）
    public ResponseJson findTeachingPlanListNotChosen(TeamTeachingPlan teachingPlan) {

        return teamTeachingPlanFeign.findTeachingPlanListNotChosen(teachingPlan);
    }

    public ResponseJson findTeachingPlanList(TeamTeachingPlan teachingPlan) {

        return teamTeachingPlanFeign.findTeachingPlanList(teachingPlan);
    }
}
