package com.yice.edu.cn.tap.feignClient.dy.classManage;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(value = "dy", contextId = "mesAppletsPostPermFeign", path = "/mesAppletsPostPerm")
public interface MesAppletsPostPermFeign {
    @PostMapping("/findMesAppletsPostPermByPostId")
    Set<Integer> findMesAppletsPostPermByPostId(List<TeacherPost> teacherPosts);
}
