package com.yice.edu.cn.osp.service.xw.doc;

import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import com.yice.edu.cn.osp.feignClient.xw.doc.WritingLeaderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WritingLeaderService {
    @Autowired
    private WritingLeaderFeign writingLeaderFeign;

    public WritingLeader findWritingLeaderById(String id) {
        return writingLeaderFeign.findWritingLeaderById(id);
    }

    public WritingLeader saveWritingLeader(WritingLeader writingLeader) {
        return writingLeaderFeign.saveWritingLeader(writingLeader);
    }

    public List<WritingLeader> findWritingLeaderListByCondition(WritingLeader writingLeader) {
        return writingLeaderFeign.findWritingLeaderListByCondition(writingLeader);
    }

    public WritingLeader findOneWritingLeaderByCondition(WritingLeader writingLeader) {
        return writingLeaderFeign.findOneWritingLeaderByCondition(writingLeader);
    }

    public long findWritingLeaderCountByCondition(WritingLeader writingLeader) {
        return writingLeaderFeign.findWritingLeaderCountByCondition(writingLeader);
    }

    public void updateWritingLeader(WritingLeader writingLeader) {
        writingLeaderFeign.updateWritingLeader(writingLeader);
    }

    public void deleteWritingLeader(String id) {
        writingLeaderFeign.deleteWritingLeader(id);
    }

    public void deleteWritingLeaderByCondition(WritingLeader writingLeader) {
        writingLeaderFeign.deleteWritingLeaderByCondition(writingLeader);
    }

    public void updateWriting(Writing writing){
        writingLeaderFeign.updateWriting(writing);
    }

    public void updateWritingAndLeader(Writing writing){
        writingLeaderFeign.updateWritingAndLeader(writing);
    }
}
