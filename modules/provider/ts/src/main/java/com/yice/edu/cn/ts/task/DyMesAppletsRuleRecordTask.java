package com.yice.edu.cn.ts.task;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxData;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import com.yice.edu.cn.common.util.wx.WxUtil;
import com.yice.edu.cn.ts.feignClient.MesAppletsRuleRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class DyMesAppletsRuleRecordTask {

    @Autowired
    private MesAppletsRuleRecordFeign mesAppletsRuleRecordFeign;
    @Autowired
    private WxUtilService wxUtilService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Scheduled(cron = "0 0 19 ? * MON-FRI")
    @Async
    public void todayRuleRecordPush(){
        List<MesStudentRecordVo> list = mesAppletsRuleRecordFeign.findTodayRecordOidAndSid();
        if(CollUtil.isNotEmpty(list)){
            //获取微信推送凭证
            String accessToken = wxUtilService.getAccessToken(Constant.DYWECHAT.DY_PARENTS_APPID,Constant.DYWECHAT.
                    DY_PARENTS_APPSERECT,Constant.DYWECHAT.DY_PARENTS_REDIS_KEY);
            list.forEach(o -> {
                //推送消息
                Map<String, WxData> map = WxUtil.structureDataByMessage(o.getName(),"您的孩子今天的在校表现信息已更新！");
                WxPushDetail wxPushDetail = WxUtil.push(accessToken, Constant.DYWECHAT.DY_PARENTS_MESSAGE_TEM_ID,
                        o.getOpenId(), map, o.getImgUrl(), "pages/index/index?studentId=".concat(o.getStudentId()));
                mongoTemplate.insert(wxPushDetail);
            });
        }
    }

    @Scheduled(cron = "0 0 19 ? * FRI")
    @Async
    public void fridayRuleRecordPush(){
        List<String> calssIdList = mesAppletsRuleRecordFeign.findClassIdByWeeks();
        if(CollUtil.isNotEmpty(calssIdList)){
            //获取微信推送凭证
            String accessToken = wxUtilService.getAccessToken(Constant.DYWECHAT.DY_PARENTS_APPID,Constant.DYWECHAT.
                    DY_PARENTS_APPSERECT,Constant.DYWECHAT.DY_PARENTS_REDIS_KEY);
            MesAppletsRuleRecord mesAppletsRuleRecord = new MesAppletsRuleRecord();
            mesAppletsRuleRecord.setSearchType(1);
            calssIdList.forEach(classId -> {
                //推送总榜单数据
                mesAppletsRuleRecord.setClassesId(classId);
                List<MesAppletsRuleRecord> classRuleTotalList = mesAppletsRuleRecordFeign.findClassRuleTotalList(mesAppletsRuleRecord);
                //总榜前五
                List<MesAppletsRuleRecord> totalListFirstFive = classRuleTotalList.stream().filter(r -> r.getSortNum() == 1 || r.getSortNum() == 2 || r.getSortNum() == 3 || r.getSortNum() == 4 || r.getSortNum() == 5)
                        .collect(Collectors.toList());
                //推送总榜前五
                wxPushRuleRecordList(totalListFirstFive,accessToken,"恭喜，您的孩子本周总分排名前五名！");

                //总榜后三
                List<MesAppletsRuleRecord> totalListLastThree = null;
                if(classRuleTotalList.size() <= 3){
                    totalListLastThree = classRuleTotalList;
                }else {
                    totalListLastThree = classRuleTotalList.subList(classRuleTotalList.size() - 3,classRuleTotalList.size());
                }
                //推送总榜后三
                wxPushRuleRecordList(totalListLastThree,accessToken,"抱歉，您的孩子本周总分排名靠后~");

                //进步榜
                List<MesAppletsRuleRecord> classRuleAdvanceList = mesAppletsRuleRecordFeign.findClassRuleAdvanceList(mesAppletsRuleRecord);
                //进步榜前五
                List<MesAppletsRuleRecord> advanceFirstFive = classRuleAdvanceList.stream().filter(r -> r.getSortNum() == 1 || r.getSortNum() == 2 || r.getSortNum() == 3 || r.getSortNum() == 4 || r.getSortNum() == 5)
                        .collect(Collectors.toList());
                //推送进步榜前五
                wxPushRuleRecordList(advanceFirstFive,accessToken,"恭喜，您的孩子本周在校表现进步很大！");

                //退步榜
                List<MesAppletsRuleRecord> classRuleBackList = mesAppletsRuleRecordFeign.findClassRuleBackList(mesAppletsRuleRecord);
                //退步榜后三
                List<MesAppletsRuleRecord> backListLastThree = null;
                if(classRuleBackList.size() <= 3){
                    backListLastThree = classRuleBackList;
                }else {
                    backListLastThree = classRuleBackList.subList(classRuleBackList.size() - 3,classRuleBackList.size());
                }
                //推送退步榜后三
                wxPushRuleRecordList(backListLastThree,accessToken,"抱歉，您的孩子本周总分排名靠后~");
            });
        }
    }

    private void wxPushRuleRecordList(List<MesAppletsRuleRecord> mesStudentRecordVoList,String accessToken,String value){
        mesStudentRecordVoList.forEach(t -> {
            List<MesStudentRecordVo> mesStudentRecordVo = mesAppletsRuleRecordFeign.findMesStudentRecordVoBySid(t.getStudentId());
            if(CollUtil.isNotEmpty(mesStudentRecordVo)){
                mesStudentRecordVo.forEach(m -> {
                    //推送消息
                    Map<String, WxData> map = WxUtil.structureDataByMessage(m.getName(),value);
                    WxPushDetail wxPushDetail = WxUtil.push(accessToken, Constant.DYWECHAT.DY_PARENTS_MESSAGE_TEM_ID,
                            m.getOpenId(), map, m.getImgUrl(),"pages/notification/notification");
                    mongoTemplate.insert(wxPushDetail);
                });
            }
        });
    }


}
