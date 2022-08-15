package com.yice.edu.cn.tap.service.doc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.tap.feignClient.doc.DocFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocService {
    @Autowired
    private DocFeign docFeign;

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
