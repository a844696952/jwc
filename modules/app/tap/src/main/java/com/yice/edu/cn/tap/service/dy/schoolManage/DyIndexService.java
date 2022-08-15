package com.yice.edu.cn.tap.service.dy.schoolManage;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.tap.feignClient.dy.schoolManage.MesInspectRecordFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherPostFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiezhi
 */
@Service
public class DyIndexService {



    private final TeacherPostFeign teacherPostFeign;

    private final MesInspectRecordFeign mesInspectRecordFeign;

    @Autowired
    public DyIndexService(TeacherPostFeign teacherPostFeign, MesInspectRecordFeign mesInspectRecordFeign) {
        this.teacherPostFeign = teacherPostFeign;
        this.mesInspectRecordFeign = mesInspectRecordFeign;
    }


    /**
     * 点击进入德育检查，根据登录的用户，分别访问的接口和做相应的处理
     * @param teacher 当前登录的教师用户
     * @return  相关的信息封装
     * */
    public ResponseJson entrance(Teacher teacher){
        List<TeacherPost> posts = teacher.getPostList();
        if(CollectionUtil.isEmpty(posts)){
            return new ResponseJson(false,"您暂无查看常规检查的权限");
        }
        boolean duty = false;
        //匹配是否有值日教师，从web端设置的来
        if(0 != mesInspectRecordFeign.judgeTeacher(teacher)){
            duty = true;
        }
        boolean classAdviser = posts.stream().anyMatch(p -> Objects.equals("班主任", p.getName()));
        int status = 1;
        if(duty){
            if(!classAdviser){
                status = 2;
            }
            //都进入检查界面，只不过带一个标识符status 是否有班主任职务
            return new ResponseJson(new ArrayList<>(),status);
        }else{
            //没有值日教师，再看有没有班主任
            if(classAdviser){
                //历史记录中【通知我的】页面列表页面
                status = 3;
                return new ResponseJson(new ArrayList<>(),status);
            }else{
                status = 4;
                return new ResponseJson("您暂无查看常规检查的权限",status);
            }
        }
    }

}
