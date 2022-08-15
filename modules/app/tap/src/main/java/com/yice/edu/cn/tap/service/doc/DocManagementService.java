package com.yice.edu.cn.tap.service.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.tap.feignClient.doc.DocManagementFeign;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocManagementService {
    @Autowired
    private DocManagementFeign docManagementFeign;

    @Autowired
    private WritingService writingService;

    @Autowired
    private DocLeaderService docLeaderService;

    @Autowired
    private WritingManagementService writingManagementService;

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

    public long[] getHomePageRedDot(){
        Doc doc = new Doc();
        doc.setUserId(LoginInterceptor.myId());
        //收文管理我批阅的是否有我批阅的
        doc.setDocumentType(1);
        long count = docLeaderService.findDocCountByCondition(doc);


        //收文管理-我接收的-还未查看的
        doc.setType(1);
        doc.setDocumentType(null);
        long count1 = findDocManagementCountByCondition(doc);


        //发文管理-我发送的——被驳回并且未被查看的
        Writing writing = new Writing();
        writing.setUserId(LoginInterceptor.myId());//当前用户
        writing.setWritingType(3);//驳回状态
        writing.setReject(1);//未被查看
        long count2 = writingService.findWritingCountByCondition(writing);

        //发文管理-我批阅的-待我批阅
        writing.setUserId(null);
        writing.setWritingType(null);
        writing.setReject(null);
        writing.setLeaderId(LoginInterceptor.myId());
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setWritingType(1);
        long count3 = writingService.findWritingCountByCondition(writing);
        //发文管理-我接收的-还未查看的
        writing.setLeaderId(null);
        writing.setSchoolId(null);
        writing.setSendObjectId(LoginInterceptor.myId());
        writing.setType(1);
        long count4 = writingManagementService.findWritingAndWritingManagementLong(writing);
        //
        long[] longs = new long[]{count1,count,count2,count3,count4};
        return longs;
    }

    //收文管理-我接收的-还未查看的
    public long  findMyReceive(){
        Doc doc = new Doc();
        doc.setUserId(LoginInterceptor.myId());
        doc.setType(1);
        doc.setDocumentType(null);
        long count1 = findDocManagementCountByCondition(doc);
        return  count1;
    }
}
