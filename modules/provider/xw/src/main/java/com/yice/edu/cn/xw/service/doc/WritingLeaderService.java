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

    //驳回
    @Transactional
    public  void updateWriting(Writing writing){
        writing.setWritingType(3);
        //设定驳回查看状态
        writing.setReject(1);
        writingDao.updateWriting(writing);
        //新建关联对象表，修改驳回意见
        WritingLeader writingLeader = new WritingLeader();
        writingLeader.setWritingId(writing.getId());
        writingLeader.setType(1);
        //首先找到该条记录
        WritingLeader writingLeader1 = findOneWritingLeaderByCondition(writingLeader);

        //添加此条记录的驳回意见
        writingLeader1.setRemarks(writing.getRemarks());
        updateWritingLeader(writingLeader1);

        //推送给创建用户
        String[] userId = new String[]{writing.getUserId()};

        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(userId,"公文通知","您有一条公文申请已被驳回，请注意查收！",Extras.XW_WRITING_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

    }

    //审批通过
    @Transactional
    public void updateWritingAndLeader(Writing writing){
        writing.setWritingType(2);
        writingDao.updateWriting(writing);
        //新建关联表对象，修改审批意见

        //找到此条记录
        WritingLeader writingLeader = new WritingLeader();
        //单条记录只有单个领导批阅
        writingLeader.setLeaderId(writing.getLeaderId());
        writingLeader.setWritingId(writing.getId());
        WritingLeader writingLeader1 = findOneWritingLeaderByCondition(writingLeader);

        //添加备注修改状态
        writingLeader1.setRemarks(writing.getRemarks());
        writingLeader1.setType(2);

        updateWritingLeader(writingLeader1);
        if(writing.getWritingManagementList()!=null){
            String[] sendObject = new String[writing.getWritingManagementList().size()];
            for (int i = 0;i<writing.getWritingManagementList().size();i++){
                sendObject[i] = writing.getWritingManagementList().get(i).getWritingObjectId();
            }

            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(sendObject,"公文通知","您有一条新的公文通知，请及时查收！",Extras.XW_WRITING_TYPE);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

        }

        //推送给创建用户
        String[] userId = new String[]{writing.getUserId()};
        final Push push1 = Push.newBuilder(JpushApp.TAP).getSimplePush(userId,"公文通知","您有一条公文申请已通过批阅，请注意查收！",Extras.XW_WRITING_TYPE);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));



    }


}
