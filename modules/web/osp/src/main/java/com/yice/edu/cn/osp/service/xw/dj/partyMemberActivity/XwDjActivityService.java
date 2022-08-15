package com.yice.edu.cn.osp.service.xw.dj.partyMemberActivity;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.xw.dj.commons.ActivityStateUtils;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberActivity.XwDjActivityFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class XwDjActivityService {
    @Autowired
    private XwDjActivityFeign xwDjActivityFeign;

    public XwDjActivity findXwDjActivityById(String id) {
        return xwDjActivityFeign.findXwDjActivityById(id);
    }
    public XwDjActivityAndUserVo findTeacherXwDjActivityById(XwDjActivityAndUserVo xwDjActivityAndUserVo) {
        return xwDjActivityFeign.findTeacherXwDjActivityById(xwDjActivityAndUserVo);
    }

    public XwDjActivity saveXwDjActivity(XwDjActivity xwDjActivity) {
        return xwDjActivityFeign.saveXwDjActivity(xwDjActivity);
    }

    public List<XwDjActivity> findXwDjActivityPartyMemberListByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityFeign.findXwDjActivityPartyMemberListByCondition(xwDjActivity);
    }

    public long findXwDjActivityPartyMemberCountByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityFeign.findXwDjActivityPartyMemberCountByCondition(xwDjActivity);
    }

    /**
    *@Description 获取当前活动列表
    *@Param [xwDjActivity]
    *@Return java.util.List<com.yice.edu.cn.common.pojo.xw.djActivity.partyMemberActivity.XwDjActivity>
    *@Author ly
    *@Date 2019/5/15
    *@Time 18:49
    */
    public List<XwDjActivity> findXwDjActivityListByCondition(XwDjActivity xwDjActivity) {
        List<XwDjActivity> xwDjActivityList = xwDjActivityFeign.findXwDjActivityListByCondition (xwDjActivity);
        if(Objects.isNull(xwDjActivity.getActivityState())){
           if(CollUtil.isNotEmpty (xwDjActivityList)){
            xwDjActivityList.stream ().filter (x->x.getActivityState ()!=0 && x.getActivityState ()!=3).forEach (x->
                    ActivityStateUtils.changeActiveState (x)
            );
           }
        }else{
            if(CollUtil.isNotEmpty(xwDjActivityList)){
                for (XwDjActivity x : xwDjActivityList) {
                    x.setActivityState(x.getTempState());
                }
            }
        }
        return xwDjActivityList;
    }
    public XwDjActivityAndUserVo findXwDjActivityIsSendByCondition(XwDjActivityUser xwDjActivityUser) {
        XwDjActivityAndUserVo xwDjActivityAndUserVo = xwDjActivityFeign.findXwDjActivityIsSendByCondition (xwDjActivityUser);
        return xwDjActivityAndUserVo;
    }
    public long findXwDjActivityIsSendCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityFeign.findXwDjActivityIsSendCountByCondition(xwDjActivityUser);
    }


    public XwDjActivity findOneXwDjActivityByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityFeign.findOneXwDjActivityByCondition(xwDjActivity);
    }

    public long findXwDjActivityCountByCondition(XwDjActivity xwDjActivity) {
        return xwDjActivityFeign.findXwDjActivityCountByCondition(xwDjActivity);
    }

    public void updateXwDjActivity(XwDjActivity xwDjActivity) {
        xwDjActivityFeign.updateXwDjActivity(xwDjActivity);
    }

    public void deleteXwDjActivity(String id) {
        xwDjActivityFeign.deleteXwDjActivity(id);
    }

    public void deleteXwDjActivityByCondition(XwDjActivity xwDjActivity) {
        xwDjActivityFeign.deleteXwDjActivityByCondition(xwDjActivity);
    }

    public void closeXwDjActivity(String id,String myId) {
        XwDjActivity xwDjActivity = new XwDjActivity();
        xwDjActivity.setOperateId(myId);
        xwDjActivity.setId(id);
        xwDjActivityFeign.closeXwDjActivity(xwDjActivity);
    }
}
