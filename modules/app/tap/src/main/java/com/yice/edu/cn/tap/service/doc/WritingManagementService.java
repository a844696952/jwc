package com.yice.edu.cn.tap.service.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import com.yice.edu.cn.tap.feignClient.doc.WritingManagementFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class WritingManagementService {
    @Autowired
    private WritingManagementFeign writingManagementFeign;

    public WritingManagement findWritingManagementById(String id) {
        return writingManagementFeign.findWritingManagementById(id);
    }

    public WritingManagement saveWritingManagement(WritingManagement writingManagement) {
        return writingManagementFeign.saveWritingManagement(writingManagement);
    }

    public List<WritingManagement> findWritingManagementListByCondition(WritingManagement writingManagement) {
        return writingManagementFeign.findWritingManagementListByCondition(writingManagement);
    }

    public WritingManagement findOneWritingManagementByCondition(WritingManagement writingManagement) {
        return writingManagementFeign.findOneWritingManagementByCondition(writingManagement);
    }

    public long findWritingManagementCountByCondition(WritingManagement writingManagement) {
        return writingManagementFeign.findWritingManagementCountByCondition(writingManagement);
    }

    public void updateWritingManagement(WritingManagement writingManagement) {
        writingManagementFeign.updateWritingManagement(writingManagement);
    }

    public void deleteWritingManagement(String id) {
        writingManagementFeign.deleteWritingManagement(id);
    }

    public void deleteWritingManagementByCondition(WritingManagement writingManagement) {
        writingManagementFeign.deleteWritingManagementByCondition(writingManagement);
    }

    /**
     * 额外
     * @param writingManagement
     * @return
     */

    public List<Writing> findWritingAndManagementListByCondtion(WritingManagement writingManagement){
        return writingManagementFeign.findWritingAndManagementListByCondtion(writingManagement);
    }

    public Writing lookAndupdateWritingAndWritingManagement(@RequestBody  Writing writing){
        return writingManagementFeign.lookAndupdateWritingAndWritingManagement(writing);
    }

    public List<Writing> findWritingAndWritingManagement(Writing writing){
        return writingManagementFeign.findWritingAndWritingManagement(writing);
    }

    public long findWritingAndWritingManagementLong(Writing writing){
        return writingManagementFeign.findWritingAndWritingManagementLong(writing);
    }

    public List<Department> getDocManagementReadOrUnRead(WritingManagement writingManagement){
        return writingManagementFeign.getDocManagementReadOrUnRead(writingManagement);
    }

}
