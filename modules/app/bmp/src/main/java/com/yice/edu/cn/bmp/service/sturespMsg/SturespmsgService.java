package com.yice.edu.cn.bmp.service.sturespMsg;

import com.yice.edu.cn.bmp.feignClient.sturespMsg.MyParentFeign;
import com.yice.edu.cn.bmp.feignClient.sturespMsg.MyStudentFeign;
import com.yice.edu.cn.bmp.feignClient.sturespMsg.SturespmsgFeign;
import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SturespmsgService {
    @Autowired
    private SturespmsgFeign sturespmsgFeign;

    @Autowired
    private MyStudentFeign myStudentFeign;

    @Autowired
    private MyParentFeign myParentFeign;

    public Sturespmsg findSturespmsgById(String id) {
        return sturespmsgFeign.findSturespmsgById(id);
    }

    public Sturespmsg saveSturespmsg(Sturespmsg sturespmsg) {
        return sturespmsgFeign.saveSturespmsg(sturespmsg);
    }

    public List<Sturespmsg> findSturespmsgListByCondition(Sturespmsg sturespmsg) {
        return sturespmsgFeign.findSturespmsgListByCondition(sturespmsg);
    }

    public Sturespmsg findOneSturespmsgByCondition(Sturespmsg sturespmsg) {
        return sturespmsgFeign.findOneSturespmsgByCondition(sturespmsg);
    }

    public long findSturespmsgCountByCondition(Sturespmsg sturespmsg) {
        return sturespmsgFeign.findSturespmsgCountByCondition(sturespmsg);
    }

    public void updateSturespmsg(Sturespmsg sturespmsg) {
        sturespmsgFeign.updateSturespmsg(sturespmsg);
    }

    public void deleteSturespmsg(String id) {
        sturespmsgFeign.deleteSturespmsg(id);
    }

    public void deleteSturespmsgByCondition(Sturespmsg sturespmsg) {
        sturespmsgFeign.deleteSturespmsgByCondition(sturespmsg);
    }

    public Student findOneStudentByCondition(Student student){
        return this.myStudentFeign.findOneStudentByCondition(student);
    }

    public Parent findOneParentByCondition(Parent parent){
        return this.myParentFeign.findOneParentByCondition(parent);
    }
}
