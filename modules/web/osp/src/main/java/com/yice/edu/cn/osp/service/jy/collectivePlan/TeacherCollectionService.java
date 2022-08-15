package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.TeacherCollectionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCollectionService {
    @Autowired
    private TeacherCollectionFeign teacherCollectionFeign;

    public TeacherCollection findTeacherCollectionById(String id) {
        return teacherCollectionFeign.findTeacherCollectionById(id);
    }

    public TeacherCollection saveTeacherCollection(TeacherCollection teacherCollection) {
        return teacherCollectionFeign.saveTeacherCollection(teacherCollection);
    }

    public List<TeacherCollection> findTeacherCollectionListByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionFeign.findTeacherCollectionListByCondition(teacherCollection);
    }

    public TeacherCollection findOneTeacherCollectionByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionFeign.findOneTeacherCollectionByCondition(teacherCollection);
    }

    public long findTeacherCollectionCountByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionFeign.findTeacherCollectionCountByCondition(teacherCollection);
    }

    public void updateTeacherCollection(TeacherCollection teacherCollection) {
        teacherCollectionFeign.updateTeacherCollection(teacherCollection);
    }

    public void deleteTeacherCollection(String id) {
        teacherCollectionFeign.deleteTeacherCollection(id);
    }

    public void deleteTeacherCollectionByCondition(TeacherCollection teacherCollection) {
        teacherCollectionFeign.deleteTeacherCollectionByCondition(teacherCollection);
    }


    //对集体备课教案的修改次数  进行增加
    public void updateModifyCount(TeacherCollection teacherCollection) {
        teacherCollectionFeign.updateModifyCount(teacherCollection);
    }

    //对集体备课教案的 评论次数 进行增加
    public void updateCommentCount(TeacherCollection teacherCollection) {
        teacherCollectionFeign.updateCommentCount(teacherCollection);
    }
}
