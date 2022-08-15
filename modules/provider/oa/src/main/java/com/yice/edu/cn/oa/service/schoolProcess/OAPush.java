package com.yice.edu.cn.oa.service.schoolProcess;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.oa.process.ProcessForm;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 封装oa推送
 */
public class OAPush {
    /**
     *
     * @param processBusinessData
     * @param alias
     * @param title
     * @param alert
     * @param type
     * @param stringRedisTemplate
     */
    public static void push(ProcessBusinessData processBusinessData, String[] alias, String title, String alert,
                            Integer type, StringRedisTemplate stringRedisTemplate){
        push(processBusinessData,alias,title,alert,type,stringRedisTemplate,null);
    }
    public static void push(ProcessBusinessData processBusinessData, String[] alias, String title, String alert,
                            Integer type, StringRedisTemplate stringRedisTemplate,String copyId){
        Push push = Push.newBuilder(JpushApp.TAP).setAlias(alias)
                .setPushDetail(PushDetail.newBuilder().setReceiverIds(Arrays.asList(alias)).setType(type).setContent(getPushData(processBusinessData,type)).build())
                .setNotification(
                        Push.Notification.newBuilder().setTitle(title).setAlert(alert)
                                .setExtras(Extras.newBuilder().setType(type).setJSON(JSONUtil.toJsonStr(Stream.of(processBusinessData.getId()).map(v -> {
                                    Map<String,String> map = Maps.newHashMap();
                                    map.put("id",v);
                                    if(StrUtil.isNotEmpty(copyId)){
                                        map.put("copyId",copyId);
                                    }
                                    return map;
                                }).findAny().get())).build())
                                .setSound(Constant.JPUSH.SOUND_1)
                                .build()
                ).setMessage(Push.Message.newBuilder().setTitle(title).setAlert(alert)
                        .setExtras(Extras.newBuilder().setId(processBusinessData.getId()).setType(type).build())
                        .build())
                .build();
/*        Console.log("alias :\t{} \n" +
                "title :\t{} \n" +
                "alert :\t{} \n" +
                "type  :\t{}  \n" +
                "copyId:\t  \n{}" +
                "map   :\t{}   \n",Arrays.toString(alias),title,alert,type,copyId,getPushData(processBusinessData,type));*/
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }
    private static Map<String,Object> getPushData(ProcessBusinessData ps,Integer type) {
        Map<String,Object> res = new HashMap<>();
        res.put("type", ps.getType());
        res.put("id",ps.getId());
        res.put("status", ps.getStatus());
        res.put("initiator", ps.getInitiator());
        res.put("initiatorId", ps.getInitiatorId());
       /* if(StrUtil.isNotEmpty(ps.getCancelReason()) && !ps.getType().contains("-")){
            ps.setType(ps.getType()+"-[撤销]");
        }*/
        Map<String, Object> data = ps.getFormData();
        if(StrUtil.isNotEmpty(ps.getProcessLibId())){
            switch(ps.getProcessLibId()) {
                case "20181027163655001"://请假申请
                    res.put("leaveType", data.get("leaveType"));
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("cancelReason",ps.getCancelReason());
                    res.put("clearLeave", ps.getClearLeave());
                    res.put("clearLeaveTime", ps.getClearLeaveTime());

                   /* if(data.containsKey("leaveTypeName")){
                        res.put("leaveTypeName",data.get("leaveTypeName"));
                    }else{
                        res.put("leaveTypeName", getLocalDataSource(ps.getProcessForms(),"leaveType", MapUtil.getStr(data,"leaveType")));
                    }*/
                   // 都去获取一遍
                    res.put("leaveTypeName", getLocalDataSource(ps.getProcessForms(),"leaveType",data.get("leaveType") ));
                    break;
                case "20181027163655002"://出差申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("travelAddress", data.get("travelAddress"));
                    break;
                case "20181027163655003"://加班申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("duration", data.get("duration"));
                    break;
                case "20181027163655004"://报销申请

                    res.put("compensateTypeName", getLocalDataSource(ps.getProcessForms(),"compensateType",data.get("compensateType")));
                    res.put("compensateType", data.get("compensateType"));
                    res.put("endTime", data.get("endTime"));
                    break;
                case "20181027163655005"://车辆申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("duration", data.get("duration"));
                    break;
                case "20181027163655006"://会议室申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("room", data.get("room"));
                    break;
                case "20181027163655007"://办公用品申请
                    res.put("officeItems", data.get("officeItems"));
                    break;
                case "20181027163655008"://维修用品申请
                    res.put("repairItems", data.get("repairItems"));
                    break;
                case "20181027163655009"://专用教室申请
                    res.put("room", data.get("room"));
                    res.put("applyTime", data.get("applyTime"));
                    res.put("timeIntervalName", data.get("timeIntervalName"));
                    break;
                case "20181027163655010"://公出申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("duration", data.get("duration"));
                    break;
                case "20181027163655011"://私出申请
                    res.put("beginTime", data.get("beginTime"));
                    res.put("endTime", data.get("endTime"));
                    res.put("duration", data.get("duration"));
                    break;
                case "1121702486948855808"://补卡申请
                    res.put("signInPoint", data.get("signInPoint"));
                    res.put("signInTime", data.get("signInTime"));
                    res.put("reason", data.get("reason"));
                    break;
                case "1121702486948855809"://采购申请
                    res.put("purchaseName", data.get("purchaseName"));
                    res.put("purchaseMoney", data.get("purchaseMoney"));
                    res.put("reason", data.get("reason"));
                    break;
                case "1121702486948855810"://调课申请
                    res.put("adjustmentDate", data.get("adjustmentDate"));
                    res.put("timeInterval", getLocalDataSource(ps.getProcessForms(),"timeInterval", data.get("timeInterval")));
                    res.put("classHour", getLocalDataSource(ps.getProcessForms(),"classHour", data.get("classHour")));
                    break;
                default:
            }
        }
        //新增title
        /**
         *  * 7-OA办公,审批任务
         *  * 8-OA办公,审批结果
         *  * 9-OA办公,收到抄送
         */
        switch (type){
            case 7:
                res.put("title",StrUtil.format("{}提交的{}需要您的审批",ps.getInitiator(),ps.getType()));
                break;
            case 8:
                if(ps.getStatus() == Constant.OA.SUCCESS_COMPLETE){
                    res.put("title",StrUtil.format("你提交的{}已通过，请知晓",ps.getType()));
                }else if(ps.getStatus() == Constant.OA.FAIL_COMPLETE){
                    res.put("title",StrUtil.format("你提交的{}已拒绝，请知晓",ps.getType()));
                }
                break;
            case 9:
                if(StrUtil.isEmpty(ps.getClearLeaveTime())){
                    res.put("title",StrUtil.format("{}提交的{}抄送给你，请知晓",ps.getInitiator(),ps.getType()));
                }else{
                    res.put("title",StrUtil.format("{}提交的确认到校通知抄送给你，请知晓",ps.getInitiator()));
                }
                break;
                default:
        }
        return res;
    }

    private static String getLocalDataSource(List<ProcessForm> processForms, String key,Object value) {
        if(CollUtil.isNotEmpty(processForms) && Objects.nonNull(value)){
            Optional<String> ps =processForms.stream()
                    .filter(pf-> key.equals(pf.getName()) && CollUtil.isNotEmpty(pf.getLocalDatasource()) )
                    .map(fm->{
                        String defStr="";
                        if("String".equals(fm.getDataType())){
                            defStr=  fm.getLocalDatasource().stream().filter(f->Objects.equals(value.toString(),f.getId())).map(m-> m.getName()).findAny().orElse("");
                        }else if("Array".equals(fm.getDataType())){
                            ArrayList<String> arr = (ArrayList<String>) value;
                           defStr = fm.getLocalDatasource().stream().filter(f -> CollUtil.contains(arr, f.getId())).map(m -> m.getName()).collect(Collectors.joining(","));
                        }
                            return defStr;
                    }).findFirst();
            return ps.orElse("");
        }
        return "";
    }


}
