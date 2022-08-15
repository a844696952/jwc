package com.yice.edu.cn.osp.service.xw.dj.partyMemberActivity;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.xw.dj.commons.ActivityStateUtils;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.osp.feignClient.xw.dj.partyMemberActivity.XwDjActivityUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class XwDjActivityUserService {
    @Autowired
    private XwDjActivityUserFeign xwDjActivityUserFeign;

    public String findXwDjActivityUserSignInQRCode(String id) {
        return xwDjActivityUserFeign.findXwDjActivityUserSignInQRCode(id);
    }
    public XwDjActivityUser findXwDjActivityUserById(String id) {
        return xwDjActivityUserFeign.findXwDjActivityUserById(id);
    }
    public boolean findXwDjActivityUserByActivityUserId(String id) {
        return xwDjActivityUserFeign.findXwDjActivityUserByActivityUserId(id);
    }

    public XwDjActivityUser saveXwDjActivityUser(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.saveXwDjActivityUser(xwDjActivityUser);
    }

    public void saveBatchXwDjActivityUser(XwDjActivity xwDjActivity) {
        List<XwDjActivityUser> activityUserList = xwDjActivity.getActivityUserList();
        if (activityUserList!=null&&activityUserList.size()!=0){
            activityUserList.get(0).setActivityId(xwDjActivity.getId());
            activityUserList.get(0).setSchoolId(xwDjActivity.getSchoolId());
        }
        xwDjActivityUserFeign.saveBatchXwDjActivityUser(activityUserList);
    }

    /**
    *@Description 获取当前用户接受活动列表
    *@Param [xwDjActivityUser]
    *@Return java.util.List<com.yice.edu.cn.common.pojo.xw.djActivity.partyMemberActivity.XwDjActivityAndUserVo>
    *@Date 2019/5/15
    *@Time 19:08
    */
    public List<XwDjActivityAndUserVo> findXwDjActivityUserListByCondition(XwDjActivityUser xwDjActivityUser) {
        List<XwDjActivityAndUserVo> xwDjActivityUserList = xwDjActivityUserFeign.findXwDjActivityUserListByCondition (xwDjActivityUser);
        //区分第一次还是带条件筛选
        if(Objects.isNull(xwDjActivityUser.getActivityState())){
            if(CollUtil.isNotEmpty (xwDjActivityUserList)){
                xwDjActivityUserList.stream ().filter (x->x.getActivityState ()!=0 && x.getActivityState ()!=3).forEach (x->
                        ActivityStateUtils.setState (x,false)
                );
            }
        }else{
            if(CollUtil.isNotEmpty(xwDjActivityUserList)){
                for (XwDjActivityAndUserVo x : xwDjActivityUserList) {
                    x.setActivityState(x.getTempState());
                }
            }
        }
        return xwDjActivityUserList;
    }

    public long findXwDjActivityUserListCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserListCountByCondition(xwDjActivityUser);
    }

    public XwDjActivityAndUserVo findXwDjActivityUserSignUpListByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserSignUpListByCondition(xwDjActivityUser);
    }
    public long findXwDjActivityUserSignUpListCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserSignUpListCountByCondition(xwDjActivityUser);
    }

    public XwDjActivityUser findOneXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findOneXwDjActivityUserByCondition(xwDjActivityUser);
    }

    public List<XwDjActivityUser> findXwDjActivityUserListCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserListCondition(xwDjActivityUser);
    }

    public long findXwDjActivityUserListCountCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserListCountCondition(xwDjActivityUser);
    }

    public long findXwDjActivityUserCountByCondition(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }

    public void updateXwDjActivityUser(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserFeign.updateXwDjActivityUser(xwDjActivityUser);
    }

    public void askForLeave(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserFeign.askForLeave(xwDjActivityUser);
    }
    public void signUp(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserFeign.signUp(xwDjActivityUser);
    }

    public void deleteXwDjActivityUser(String id) {
        xwDjActivityUserFeign.deleteXwDjActivityUser(id);
    }

    public void deleteXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser) {
        xwDjActivityUserFeign.deleteXwDjActivityUserByCondition(xwDjActivityUser);
    }

    public long findXwDjActivityUserCount(XwDjActivityUser xwDjActivityUser) {
        return xwDjActivityUserFeign.findXwDjActivityUserCount(xwDjActivityUser);
    }
}
