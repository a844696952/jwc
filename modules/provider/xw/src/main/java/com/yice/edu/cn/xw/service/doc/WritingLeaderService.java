package com.yice.edu.cn.xw.service.doc;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import com.yice.edu.cn.xw.dao.doc.WritingDao;
import com.yice.edu.cn.xw.dao.doc.WritingLeaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class WritingLeaderService {
    @Autowired
    private WritingLeaderDao writingLeaderDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WritingDao writingDao;

    @Transactional(readOnly = true)
    public WritingLeader findWritingLeaderById(String id) {
        return writingLeaderDao.findWritingLeaderById(id);
    }
    @Transactional
    public void saveWritingLeader(WritingLeader writingLeader) {
        writingLeader.setId(sequenceId.nextId());
        writingLeaderDao.saveWritingLeader(writingLeader);
    }
    @Transactional(readOnly = true)
    public List<WritingLeader> findWritingLeaderListByCondition(WritingLeader writingLeader) {
        return writingLeaderDao.findWritingLeaderListByCondition(writingLeader);
    }
    @Transactional(readOnly = true)
    public WritingLeader findOneWritingLeaderByCondition(WritingLeader writingLeader) {
        return writingLeaderDao.findOneWritingLeaderByCondition(writingLeader);
    }
    @Transactional(readOnly = true)
    public long findWritingLeaderCountByCondition(WritingLeader writingLeader) {
        return writingLeaderDao.findWritingLeaderCountByCondition(writingLeader);
    }
    @Transactional
    public void updateWritingLeader(WritingLeader writingLeader) {
        writingLeaderDao.updateWritingLeader(writingLeader);
    }
    @Transactional
    public void deleteWritingLeader(String id) {
        writingLeaderDao.deleteWritingLeader(id);
    }
    @Transactional
    public void deleteWritingLeaderByCondition(WritingLeader writingLeader) {
        writingLeaderDao.deleteWritingLeaderByCondition(writingLeader);
    }
    @Transactional
    public void batchSaveWritingLeader(List<WritingLeader> writingLeaders){
        writingLeaders.forEach(writingLeader -> writingLeader.setId(sequenceId.nextId()));
        writingLeaderDao.batchSaveWritingLeader(writingLeaders);
    }

    //??????
    @Transactional
    public  void updateWriting(Writing writing){
        writing.setWritingType(3);
        //????????????????????????
        writing.setReject(1);
        writingDao.updateWriting(writing);
        //??????????????????????????????????????????
        WritingLeader writingLeader = new WritingLeader();
        writingLeader.setWritingId(writing.getId());
        writingLeader.setType(1);
        //????????????????????????
        WritingLeader writingLeader1 = findOneWritingLeaderByCondition(writingLeader);

        //?????????????????????????????????
        writingLeader1.setRemarks(writing.getRemarks());
        updateWritingLeader(writingLeader1);

        //?????????????????????
        String[] userId = new String[]{writing.getUserId()};

        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(userId,"????????????","?????????????????????????????????????????????????????????",Extras.XW_WRITING_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

    }

    //????????????
    @Transactional
    public void updateWritingAndLeader(Writing writing){
        writing.setWritingType(2);
        writingDao.updateWriting(writing);
        //??????????????????????????????????????????

        //??????????????????
        WritingLeader writingLeader = new WritingLeader();
        //????????????????????????????????????
        writingLeader.setLeaderId(writing.getLeaderId());
        writingLeader.setWritingId(writing.getId());
        WritingLeader writingLeader1 = findOneWritingLeaderByCondition(writingLeader);

        //????????????????????????
        writingLeader1.setRemarks(writing.getRemarks());
        writingLeader1.setType(2);

        updateWritingLeader(writingLeader1);
        if(writing.getWritingManagementList()!=null){
            String[] sendObject = new String[writing.getWritingManagementList().size()];
            for (int i = 0;i<writing.getWritingManagementList().size();i++){
                sendObject[i] = writing.getWritingManagementList().get(i).getWritingObjectId();
            }

            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(sendObject,"????????????","???????????????????????????????????????????????????",Extras.XW_WRITING_TYPE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

        }

        //?????????????????????
        String[] userId = new String[]{writing.getUserId()};
        final Push push1 = Push.newBuilder(JpushApp.TAP).getSimplePush(userId,"????????????","????????????????????????????????????????????????????????????",Extras.XW_WRITING_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));



    }


}
