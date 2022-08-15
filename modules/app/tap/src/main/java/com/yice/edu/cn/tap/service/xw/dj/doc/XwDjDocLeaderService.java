package com.yice.edu.cn.tap.service.xw.dj.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocDepartment;
import com.yice.edu.cn.common.pojo.xw.document.SendObject;
import com.yice.edu.cn.tap.feignClient.doc.DepartmentFeign;
import com.yice.edu.cn.tap.feignClient.xw.dj.doc.XwDjDocLeaderFeign;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjDocLeaderService {
    @Autowired
    private XwDjDocLeaderFeign docLeaderFeign;
    @Autowired
    private DepartmentFeign departmentFeign;

    public List<Doc> findDocAndDocLeaderList(Doc doc) {
        return docLeaderFeign.findDocAndDocLeaderList(doc);
    }

    public void saveUpdateDocCompletion(Doc doc) {
        docLeaderFeign.saveUpdateDocCompletion(doc);
    }

    public long findDocCountByCondition(Doc doc) {
        return docLeaderFeign.findDocCountByCondition(doc);
    }

    public void saveUpdateDocLeaterCompletion(Doc doc) {
        docLeaderFeign.saveUpdateDocLeaterCompletion(doc);
    }

    public Doc saveDocManagement(List<SendObject> sendObjects, String docId) {
        List<Department> departmentList = departmentFeign.findDepartmentTreeToSchoolNotifyBySchoolId(LoginInterceptor.mySchoolId());
        DocDepartment docDepartment = new DocDepartment();
        docDepartment.setSendObjects(sendObjects);
        docDepartment.setDepartments(departmentList);
        return docLeaderFeign.saveDocManagement(docDepartment, docId);
    }
}
