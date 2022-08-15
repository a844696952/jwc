package com.yice.edu.cn.tap.service.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.tap.feignClient.doc.DepartmentFeign;
import com.yice.edu.cn.tap.feignClient.doc.WritingFeign;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WritingService {
    @Autowired
    private WritingFeign writingFeign;
    @Autowired
    private DepartmentFeign departmentFeign;

    public Writing findWritingById(String id) {
        return writingFeign.findWritingById(id);
    }

    public Writing saveWriting(Writing writing) {
        List<Department> departmentList = departmentFeign.findDepartmentTreeToSchoolNotifyBySchoolId(LoginInterceptor.mySchoolId());
        DocDepartment docDepartment = new DocDepartment();
        docDepartment.setDepartments(departmentList);
        docDepartment.setWriting(writing);
        return writingFeign.saveWriting(docDepartment);
    }


    public List<Writing> findWritingListByCondition(Writing writing) {
        return writingFeign.findWritingListByCondition(writing);
    }

    public Writing findOneWritingByCondition(Writing writing) {
        return writingFeign.findOneWritingByCondition(writing);
    }

    public long findWritingCountByCondition(Writing writing) {
        return writingFeign.findWritingCountByCondition(writing);
    }

    public void updateWriting(Writing writing) {
        List<Department> departmentList = departmentFeign.findDepartmentTreeToSchoolNotifyBySchoolId(LoginInterceptor.mySchoolId());
        DocDepartment docDepartment = new DocDepartment();
        docDepartment.setDepartments(departmentList);
        docDepartment.setWriting(writing);
        writingFeign.updateWriting(docDepartment);
    }

    public void deleteWriting(String id) {
        writingFeign.deleteWriting(id);
    }

    public void deleteWritingByCondition(Writing writing) {
        writingFeign.deleteWritingByCondition(writing);
    }

    public Boolean getdepartmentUpdate(String id){
        List<Department> departmentList = departmentFeign.findDepartmentTreeToSchoolNotifyBySchoolId(LoginInterceptor.mySchoolId());
        DocDepartment docDepartment = new DocDepartment();
        docDepartment.setDepartments(departmentList);
        docDepartment.setId(id);
        return writingFeign.getdepartmentUpdate(docDepartment);

    }

    public Writing getWritingRejectUpdate(String id){
        return writingFeign.getWritingRejectUpdate(id);
    }
}
