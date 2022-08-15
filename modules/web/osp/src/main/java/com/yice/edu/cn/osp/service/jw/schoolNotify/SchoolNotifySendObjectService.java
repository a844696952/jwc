package com.yice.edu.cn.osp.service.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.osp.feignClient.jw.schoolNotify.SchoolNotifySendObjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class SchoolNotifySendObjectService {
    @Autowired
    private SchoolNotifySendObjectFeign schoolNotifySendObjectFeign;

    public SchoolNotifySendObject findSchoolNotifySendObjectById(String id) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectById(id);
    }

    public SchoolNotifySendObject saveSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.saveSchoolNotifySendObject(schoolNotifySendObject);
    }

    public List<SchoolNotifySendObject> findSchoolNotifySendObjectListByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
    }

    public SchoolNotifySendObject findOneSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findOneSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }

    public long findSchoolNotifySendObjectCountByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
    }

    public void updateSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectFeign.updateSchoolNotifySendObject(schoolNotifySendObject);
    }
    public void updateSchoolNotifySendObject1(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectFeign.updateSchoolNotifySendObject1(schoolNotifySendObject);
    }

    public void deleteSchoolNotifySendObject(String id) {
        schoolNotifySendObjectFeign.deleteSchoolNotifySendObject(id);
    }

    public void deleteSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectFeign.deleteSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }


    public  List<Department> getSchoolNotifyReadDetail(SchoolNotifySendObject schoolNotifySendObject) {
        return  schoolNotifySendObjectFeign.getSchoolNotifyReadDetail(schoolNotifySendObject);
    }

    public  long getSchoolNotifyReadDetailCount(SchoolNotifySendObject schoolNotifySendObject) {
        return  schoolNotifySendObjectFeign.getSchoolNotifyReadDetailCount(schoolNotifySendObject);
    }

    public  List<SchoolNotify>  getSchoolNotifyToWebIndex(){
        List<SchoolNotify> schoolNotifyList=new ArrayList<>();
        SchoolNotifySendObject schoolNotifySendObject=new SchoolNotifySendObject();
        schoolNotifySendObject.setSchoolId(mySchoolId());
        schoolNotifySendObject.setObjectId(myId());
        schoolNotifySendObject.setDel("1");
        Pager p=new Pager();
        p.setSortField("createTime");
        p.setSortOrder("desc");
        p.setPage(1);
        p.setPageSize(3);
        schoolNotifySendObject.setPager(p);
        List<SchoolNotifySendObject> sendObjectList=  findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
        sendObjectList.forEach(schoolNotifySendObject1 -> {
            SchoolNotify schoolNotify=new SchoolNotify();
            schoolNotify.setId(schoolNotifySendObject1.getSchoolNotify().getId());
            schoolNotify.setSendObjectId(schoolNotifySendObject1.getId());
            schoolNotify.setTitle(schoolNotifySendObject1.getSchoolNotify().getTitle());
            schoolNotify.setContent(schoolNotifySendObject1.getSchoolNotify().getContent());
            schoolNotify.setCreateTime(schoolNotifySendObject1.getSchoolNotify().getCreateTime());
            schoolNotifyList.add(schoolNotify);
        });

        return  schoolNotifyList;
    }

}
