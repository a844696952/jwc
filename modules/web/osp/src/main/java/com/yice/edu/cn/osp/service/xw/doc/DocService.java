package com.yice.edu.cn.osp.service.xw.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.osp.feignClient.xw.doc.DepartmentFeign;
import com.yice.edu.cn.osp.feignClient.xw.doc.DocFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocService {
    @Autowired
    private DocFeign docFeign;
    @Autowired
    private DepartmentFeign departmentFeign;

    public Doc findDocById(String id) {
        return docFeign.findDocById(id);
    }

    public Doc saveDoc(Doc doc) {
        return docFeign.saveDoc(doc);
    }

    public List<Doc> findDocListByCondition(Doc doc) {
        return docFeign.findDocListByCondition(doc);
    }

    public Doc findOneDocByCondition(Doc doc) {
        return docFeign.findOneDocByCondition(doc);
    }

    public long findDocCountByCondition(Doc doc) {
        return docFeign.findDocCountByCondition(doc);
    }

    public void updateDoc(Doc doc) {
        docFeign.updateDoc(doc);
    }

    public void deleteDoc(String id) {
        docFeign.deleteDoc(id);
    }

    public void deleteDocByCondition(Doc doc) {
        docFeign.deleteDocByCondition(doc);
    }

}
