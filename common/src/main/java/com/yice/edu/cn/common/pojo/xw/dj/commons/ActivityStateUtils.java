package com.yice.edu.cn.common.pojo.xw.dj.commons;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;

/**
 * @author ly
 * @date ：Created in 2019/5/15 18:55
 * @description：
 * @modified By：
 * @version:
 */
public class ActivityStateUtils {

    private ActivityStateUtils(){}


    /**
    *@Description :改变党建状态
    *@Param [x]
    *@Return void
    *@Date 2019/5/15
    *@Time 19:04
    */
    public static void changeActiveState(XwDjActivity x) {
        if(DateTime.now ().before (DateUtil.parse (x.getApplyStopTime (), Constant.DATE_FORMAT.FORMAT))){
            //报名中
            x.setActivityState (1);
        }else{
            setState (x,false);
        }
    }

    /**
    *@Description :设置状态
    *@Param [x]
    *@Return void
    *@Date 2019/5/15
    *@Time 19:05
    */
    public static void setState(XwDjActivity x,boolean isShow) {
        if(isShow){
            if(DateTime.now ().before ((DateUtil.parse (x.getActivityStartTime (), Constant.DATE_FORMAT.FORMAT))) &&
                    DateTime.now ().after (DateUtil.parse (x.getApplyStopTime (),Constant.DATE_FORMAT.FORMAT))){
                x.setActivityState (2);
            }
        }else{
            if(DateTime.now ().before ((DateUtil.parse (x.getActivityStartTime (), Constant.DATE_FORMAT.FORMAT)))){
                //未开始
                x.setActivityState (2);
            }
        }
        if(DateTime.now ().before ((DateUtil.parse (x.getActivityEndTime (),Constant.DATE_FORMAT.FORMAT))) &&
                DateTime.now ().after (DateUtil.parse (x.getActivityStartTime (),Constant.DATE_FORMAT.FORMAT))){
            //进行中
            x.setActivityState (4);
        }
        if(DateTime.now ().after (DateUtil.parse (x.getActivityEndTime (),Constant.DATE_FORMAT.FORMAT))){
            //已结束
            x.setActivityState (5);
        }
    }

}
