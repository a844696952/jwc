package com.yice.edu.cn.tap.service.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.feignClient.dm.schoolActive.DmActivityItemFeign;
import com.yice.edu.cn.tap.feignClient.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmActivityItemService {
    @Autowired
    private DmActivityItemFeign dmActivityItemFeign;

    @Autowired
    private StudentFeign studentFeign;

    public DmActivityItem findDmActivityItemById(String id) {
        return dmActivityItemFeign.findDmActivityItemById(id);
    }

    public DmActivityItem saveDmActivityItem(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.saveDmActivityItem(dmActivityItem);
    }

    public List<DmActivityItem> findDmActivityItemListByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findDmActivityItemListByCondition(dmActivityItem);
    }

    public DmActivityItem findOneDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findOneDmActivityItemByCondition(dmActivityItem);
    }

    public long findDmActivityItemCountByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findDmActivityItemCountByCondition(dmActivityItem);
    }

    public void updateDmActivityItem(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.updateDmActivityItem(dmActivityItem);
    }

    public void deleteDmActivityItem(String id) {
        dmActivityItemFeign.deleteDmActivityItem(id);
    }

    public void deleteDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.deleteDmActivityItemByCondition(dmActivityItem);
    }

    public List<DmActivityItem> findDmActivityItemsByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findDmActivityItemsByCondition(dmActivityItem);
    }

    public List<Student> findClassStudentByClassesId(DmActivitySiginUp dmActivitySiginUp) {
        return studentFeign.findClassStudentByClassesId(dmActivitySiginUp);
    }

    public void saveDmActivityItemAndPeople(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.saveDmActivityItemAndPeople(dmActivityItem);
    }

    public void updateDmActivityItemAndPeople(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.updateDmActivityItemAndPeople(dmActivityItem);
    }
}
