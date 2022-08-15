package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "teacherCollectionFeign",path = "/teacherCollection")
public interface TeacherCollectionFeign {
    @GetMapping("/findTeacherCollectionById/{id}")
    TeacherCollection findTeacherCollectionById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherCollection")
    TeacherCollection saveTeacherCollection(TeacherCollection teacherCollection);
    @PostMapping("/findTeacherCollectionListByCondition")
    List<TeacherCollection> findTeacherCollectionListByCondition(TeacherCollection teacherCollection);
    @PostMapping("/findOneTeacherCollectionByCondition")
    TeacherCollection findOneTeacherCollectionByCondition(TeacherCollection teacherCollection);
    @PostMapping("/findTeacherCollectionCountByCondition")
    long findTeacherCollectionCountByCondition(TeacherCollection teacherCollection);
    @PostMapping("/updateTeacherCollection")
    void updateTeacherCollection(TeacherCollection teacherCollection);
    @GetMapping("/deleteTeacherCollection/{id}")
    void deleteTeacherCollection(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherCollectionByCondition")
    void deleteTeacherCollectionByCondition(TeacherCollection teacherCollection);
    //对集体备课教案的修改次数  进行增加
    @PostMapping("/updateModifyCount")
    void updateModifyCount(TeacherCollection teacherCollection);
    //对集体备课教案的 评论次数 进行增加
    @PostMapping("/updateCommentCount")
    void updateCommentCount(TeacherCollection teacherCollection);
}
