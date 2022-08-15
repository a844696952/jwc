package com.yice.edu.cn.tap.service.dy.classManage;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.tap.feignClient.dy.classManage.MesAppletsPostPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MesAppletsPostPermService {
    @Autowired
    private MesAppletsPostPermFeign mesAppletsPostPermFeign;

    @Transactional(readOnly = true)
    public Set<Integer> findMesAppletsPostPermByPostId(List<TeacherPost> teacherPosts) {
        return mesAppletsPostPermFeign.findMesAppletsPostPermByPostId(teacherPosts);
    }
}
