package com.yice.edu.cn.osp.service.oa.myApprove;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.feignClient.oa.myApprove.MyApproveFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyApproveService {
    @Autowired
    private MyApproveFeign myApproveFeign;
    public List<ProcessBusinessData> findWaitApprove(String id,ProcessBusinessData processBusinessData) {
        return myApproveFeign.findWaitApproveData(id,processBusinessData);
    }

    public long findWaitApproveCount(String id, ProcessBusinessData processBusinessData) {
        return myApproveFeign.findWaitApproveCount(id, processBusinessData);
    }

    public ResponseJson completeApprove(OaPeople oaPeople, String processBusinessId) {
        return myApproveFeign.completeApprove(oaPeople,processBusinessId);
    }

    public List<ProcessBusinessData> findHasApproveData(String myId, ProcessBusinessData processBusinessData) {
        return myApproveFeign.findHasApproveData(myId,processBusinessData);
    }

    public long findHasApproveCount(String myId, ProcessBusinessData processBusinessData) {
        return myApproveFeign.findHasApproveCount(myId,processBusinessData);
    }
}
