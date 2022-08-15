package com.yice.edu.cn.tap.service.xw.dj.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.tap.feignClient.xw.dj.doc.XwDjDocManagementFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XwDjDocManagementService {


    @Autowired
    private XwDjDocManagementFeign docManagementFeign;

    public List<Doc> findDocListByCondition(Doc doc){
        return docManagementFeign.findDocListByCondition(doc);
    }

    public long findDocManagementCountByCondition(Doc doc){
        return docManagementFeign.findDocManagementCountByCondition(doc);
    }

    public Doc fingOneDocUpdateManagement(String docId,String docObjectId){
        return docManagementFeign.fingOneDocUpdateManagement(docId,docObjectId);
    }

    public List<DocManagement> findDocManagementReadList(DocManagement docManagement){
        return docManagementFeign.findDocManagementReadList(docManagement);
    }

    public List<Department> getDocManagementReadOrUnRead(DocManagement docManagement){
        return docManagementFeign.getDocManagementReadOrUnRead(docManagement);
    }
}
